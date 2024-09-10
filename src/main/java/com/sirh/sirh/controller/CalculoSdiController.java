/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.SdiDTO;
import com.sirh.sirh.entity.Cat_Bimestres_Sdi;
import com.sirh.sirh.entity.Cat_Tipo_Nomina;
import com.sirh.sirh.entity.Tmp_Sdi_Calculos_Bim;
import com.sirh.sirh.entity.Rela_Calculos_Sdi_Cve;
import com.sirh.sirh.entity.Tmp_Sdi_Acum_Bim;
import com.sirh.sirh.service.CalculoSdiService;
import com.sirh.sirh.service.CatalogosService;
import com.sirh.sirh.util.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author rroscero23
 */
@RestController
@RequestMapping("calculoSdi")
public class CalculoSdiController {

    @Autowired
    CalculoSdiService calculoSdiService;

    @Autowired
    CatalogosService catalogosService;

    @GetMapping("/encontrarRelacionCalculo/{valorCampo}")
    public Rela_Calculos_Sdi_Cve findRelacionCalculoIncidencia(@PathVariable("valorCampo") String valorCampo) {
        return calculoSdiService.findRelacionCalculoIncidencia(valorCampo);
    }

    @GetMapping(value = "/calculoSdiNomina/{nomina_id}/{bimestre_id}")
    public SseEmitter calcularSdiPorNomina(@PathVariable("nomina_id") Integer nomina_id,
            @RequestParam List<Integer> periodos_considerados,
            @PathVariable("bimestre_id") Integer idBimestre) {
        SseEmitter emitter = new SseEmitter();

        new Thread(() -> {
            try {
                calculoSdiService.calcularSdiPorNomina(nomina_id, periodos_considerados, idBimestre, emitter);
            } catch (IOException e) {
                // Imprimir el error en la consola usando System.out.println
                System.out.println("Error durante el cálculo del SDI: " + e.getMessage());
                e.printStackTrace(System.out);
                emitter.completeWithError(e);
            } catch (Exception e) {
                // Imprimir el error en la consola usando System.out.println
                System.out.println("Error inesperado durante el cálculo del SDI: " + e.getMessage());
                e.printStackTrace(System.out);
                emitter.completeWithError(e);
            }
        }).start();

        return emitter;
    }

    @GetMapping(value = "/calculaSdiNuevoIngreso/{idTrabajador}/{idBimestre}")
    public void calcularSdiNuevoIngreso(@PathVariable("idTrabajador") Integer idTrabajador,
            @PathVariable("idBimestre") Integer idBimestre) {
        try {
            calculoSdiService.calcularSdiNuevoIngreso(idTrabajador, idBimestre);
        } catch (Exception e) {
            // Manejo de la excepción, por ejemplo, lanzando una excepción de respuesta HTTP
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al calcular el SDI Individual", e);
        }
    }

    @GetMapping("/exportarConceptosCalculados/{nomina_id}/{bimestre_id}")
    public void exportarConceptosCalculados(HttpServletResponse response,
            @PathVariable("nomina_id") Integer idNomina,
            @PathVariable("bimestre_id") Integer idBimestre) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //Para obtener el nombre de la nómina del catálogo
        Cat_Tipo_Nomina resultNomina = catalogosService.findByIdNomina(idNomina);
        String nombreNomina = resultNomina.getDesc_nomina();
        //Para obtener el bimestre del catálogo
        Cat_Bimestres_Sdi resultBimestre = catalogosService.findByIdBimestre(idBimestre);
        String bimestre = resultBimestre.getBimestre();
        String fileName = "personal_calculos_sdi_" + nombreNomina + "bimestre_" + bimestre + ".xlsx";
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
        calculoSdiService.exportarConceptosCalculados(response, idNomina, idBimestre);
    }

    @GetMapping("/exportarCalculosVarios")
    public void exportarCalculosVarios(HttpServletResponse response,
            @RequestParam List<Integer> periodos) throws IOException {
        if (periodos == null || periodos.isEmpty()) {
            throw new IllegalArgumentException("La lista de periodos no puede estar vacía");
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = LocalDateTime.now().format(dateFormatter);
        String periodosStr = periodos.stream()
                .map(Object::toString)
                .collect(Collectors.joining("-"));
        String fileName = "personal_acumulados_sdi_varios_" + currentDateTime + "_" + periodosStr + ".xlsx";
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
        calculoSdiService.exportarCalculosObtenidosSdiVarios(response, periodos);
    }

    @GetMapping("/exportarCalculosObtenidosSdiTransportacion")
    public void exportarCalculosObtenidosSdiTransportacion(HttpServletResponse response,
            @RequestParam List<Integer> periodos) throws IOException {
        if (periodos == null || periodos.isEmpty()) {
            throw new IllegalArgumentException("La lista de periodos no puede estar vacía");
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = LocalDateTime.now().format(dateFormatter);
        String periodosStr = periodos.stream()
                .map(Object::toString)
                .collect(Collectors.joining("-"));
        String fileName = "personal_acumulados_sdi_transportacion_" + currentDateTime + "_" + periodosStr + ".xlsx";
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
        calculoSdiService.exportarCalculosObtenidosSdiTransportacion(response, periodos);
    }

    @GetMapping("/exportarCalculosObtenidosConfianza")
    public void exportarCalculosObtenidosConfianza(HttpServletResponse response,
            @RequestParam List<Integer> periodos) throws IOException {
        if (periodos == null || periodos.isEmpty()) {
            throw new IllegalArgumentException("La lista de periodos no puede estar vacía");
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = LocalDateTime.now().format(dateFormatter);
        String periodosStr = periodos.stream()
                .map(Object::toString)
                .collect(Collectors.joining("-"));
        String fileName = "personal_acumulados_sdi_confianza_" + currentDateTime + "_" + periodosStr + ".xlsx";
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
        calculoSdiService.exportarCalculosObtenidosConfianza(response, periodos);
    }

    @GetMapping("/exportarCalculosObtenidosSdiEstructura")
    public void exportarCalculosObtenidosSdiEstructura(HttpServletResponse response,
            @RequestParam List<Integer> periodos) throws IOException {
        if (periodos == null || periodos.isEmpty()) {
            throw new IllegalArgumentException("La lista de periodos no puede estar vacía");
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = LocalDateTime.now().format(dateFormatter);
        String periodosStr = periodos.stream()
                .map(Object::toString)
                .collect(Collectors.joining("-"));
        String fileName = "personal_acumulados_sdi_estructura_" + currentDateTime + "_" + periodosStr + ".xlsx";
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
        calculoSdiService.exportarCalculosObtenidosSdiEstructura(response, periodos);
    }

    @GetMapping("/findTrabajadoresNominaBimestreSdi")
    public List<SdiDTO> encontrarTrabajadoresNominaBimestre(@RequestParam("idNomina") Integer idNomina, @RequestParam(required = false) Integer idBimestre) {
        return calculoSdiService.findBimestresSdi(idNomina, idBimestre);
    }

    @GetMapping("/existeBimestreCalculado/{idBimestre}")
    public Boolean existeBimestreCalculado(@PathVariable("idBimestre") Integer idBimestre) {
        return calculoSdiService.existeBimestreCalculado(idBimestre);
    }

    @GetMapping("/existeBimestreCalculadoTrabajador/{idTrabajador}/{idBimestre}")
    public Boolean existeBimestreCalculadoTrabajador(@PathVariable("idTrabajador") Integer idTrabajador,
            @PathVariable("idBimestre") Integer idBimestre) {
        return calculoSdiService.existeBimestreCalculadoTrabajador(idTrabajador, idBimestre);
    }

    @GetMapping("/existeBimestreAcumulado/{idBimestre}")
    public Boolean existeBimestreAcumulado(@PathVariable("idBimestre") Integer idBimestre) {
        return calculoSdiService.existeBimestreAcumulado(idBimestre);
    }

    @GetMapping("/encontrarCalculoBimTrab/{idBimestre}/{idTrabajador}")
    public ResponseEntity<Tmp_Sdi_Calculos_Bim> findDetalleCalculoBimTrab(@PathVariable Integer idBimestre, @PathVariable Integer idTrabajador) {
        try {
            List<Tmp_Sdi_Calculos_Bim> result = calculoSdiService.findDetalleCalculoBimTrab(idBimestre, idTrabajador);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/txtSuaModifSalarioBimestre/{idBimestre}/{idNomina}")
    public ResponseEntity<InputStreamResource> descargaTxtSua(@PathVariable("idBimestre") Integer idBimestre,
            @PathVariable("idNomina") Integer idNomina) {
        String content = calculoSdiService.generarTxSuaModif(idBimestre, idNomina);
        //Para obtener el bimestre del catálogo
        Cat_Bimestres_Sdi result = catalogosService.findByIdBimestre(idBimestre);
        String bimestre = result.getBimestre();
        //Para obtener el nombre de la nómina del catálogo
        Cat_Tipo_Nomina resultNomina = catalogosService.findByIdNomina(idNomina);
        String nombreNomina = resultNomina.getDesc_nomina();
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        String filename = nombreNomina + "_bimestre" + bimestre + "_carga_sua" + ".txt";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                .contentType(MediaType.parseMediaType("text/plain"))
                .contentLength(content.length())
                .body(new InputStreamResource(inputStream));
    }

    @GetMapping("/txtIdseModifSalarioBimestre/{idBimestre}/{idNomina}//{idTipoMovimiento}")
    public ResponseEntity<InputStreamResource> descargaTxtIdse(@PathVariable("idBimestre") Integer idBimestre,
            @PathVariable("idNomina") Integer idNomina,
            @PathVariable("idTipoMovimiento") Integer idTipoMovimiento) {
        String content = calculoSdiService.generarTxIdseModif(idBimestre, idNomina, idTipoMovimiento);
        //Para obtener el bimestre del catálogo
        Cat_Bimestres_Sdi result = catalogosService.findByIdBimestre(idBimestre);
        String bimestre = result.getBimestre();
        //Para obtener el nombre de la nómina del catálogo
        Cat_Tipo_Nomina resultNomina = catalogosService.findByIdNomina(idNomina);
        String nombreNomina = resultNomina.getDesc_nomina();
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        String filename = nombreNomina + "_bimestre" + bimestre + "_carga_idse" + ".txt";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                .contentType(MediaType.parseMediaType("text/plain"))
                .contentLength(content.length())
                .body(new InputStreamResource(inputStream));
    }

    @PostMapping("/borraAcumuladoBimestre/{idBimestre}")
    public ResponseEntity<String> deleteSdiBimester(@PathVariable("idBimestre") Integer idBimestre) {
        try {
            calculoSdiService.borrarAcumuladosBimestre(idBimestre);
            return new ResponseEntity<>("Proceso completado exitosamente.", HttpStatus.OK);
        } catch (RuntimeException e) {
            // Manejo de la excepción y respuesta en caso de error
            return new ResponseEntity<>("Error al ejecutar el procedimiento almacenado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/borraCalculoBimestre/{idBimestre}")
    public ResponseEntity<String> borrarCalculosBimestre(@PathVariable("idBimestre") Integer idBimestre) {
        try {
            calculoSdiService.borrarCalculosBimestre(idBimestre);
            return new ResponseEntity<>("Proceso completado exitosamente.", HttpStatus.OK);
        } catch (RuntimeException e) {
            // Manejo de la excepción y respuesta en caso de error
            return new ResponseEntity<>("Error al ejecutar el procedimiento almacenado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
