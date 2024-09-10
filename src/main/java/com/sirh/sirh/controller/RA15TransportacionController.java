/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.CapturaSemanalRA15DTO;
import com.sirh.sirh.DTO.Captura_Semanal_Resumen_IncidenciasRA15DTO;
import com.sirh.sirh.DTO.OperadorTitularDTO;
import com.sirh.sirh.DTO.OperadorTrenDTO;
import com.sirh.sirh.DTO.Trabajador_RA15DTO;
import com.sirh.sirh.entity.Captura_Semanal_RA15;
import com.sirh.sirh.entity.Historico_Captura_Semanal_RA15;
import com.sirh.sirh.entity.Historico_Tmp_Libro_RA15;
import com.sirh.sirh.entity.Historico_Tmp_Libro_Tren_RA15;
import com.sirh.sirh.service.RA15_TransportacionService;
import com.sirh.sirh.util.Response;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author rroscero23
 */
@RestController
@RequestMapping("ra15")
public class RA15TransportacionController {

    @Autowired
    RA15_TransportacionService rA15_TransportacionService;

    @GetMapping(value = "/buscarPorTrabajadoryPeriodo/{trabajadorId}/{periodoId}")
    public ResponseEntity<List<Captura_Semanal_RA15>> findByIdTrabajadorAndIdPeriodo(@PathVariable("trabajadorId") Integer trabajadorId, @PathVariable("periodoId") Integer periodoId) {
        List<Captura_Semanal_RA15> capturaSemanalRa15 = rA15_TransportacionService.findByIdTrabajadorAndIdPeriodo(trabajadorId, periodoId);
        return new ResponseEntity<>(capturaSemanalRa15, HttpStatus.OK);
    }

    @GetMapping(value = "/buscarPorExpedienteyPeriodo/{numero_expediente}/{periodoId}")
    public ResponseEntity<List<Captura_Semanal_RA15>> findByIdTrabajadorExpedienteAndIdPeriodo(@PathVariable("numero_expediente") String numero_expediente, @PathVariable("periodoId") Integer periodoId) {
        List<Captura_Semanal_RA15> capturaSemanalRa15 = rA15_TransportacionService.findByIdTrabajadorExpedienteAndIdPeriodo(numero_expediente, periodoId);
        return new ResponseEntity<>(capturaSemanalRa15, HttpStatus.OK);
    }

    @PostMapping("/guardarCapturaSemanal")
    public ResponseEntity<Captura_Semanal_RA15> saveCapturaSemanal(@RequestBody Captura_Semanal_RA15 captura_Semanal_RA15) {
        try {
            return new ResponseEntity<>(rA15_TransportacionService.saveCapturaSemanal(captura_Semanal_RA15), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizarCapturaSemanal/{id_ra}")
    public ResponseEntity<OutputEntity<String>> updateCapturaSemanal(@RequestBody Captura_Semanal_RA15 captura_Semanal_RA15, @PathVariable("id_ra") Integer id_ra) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            rA15_TransportacionService.updateCapturaSemanal(captura_Semanal_RA15, id_ra);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos Captura Semanal actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }

    }

    @GetMapping(value = "/obtenerDatosCSVPeriodo/{periodo_id}")
    public ResponseEntity<List<CapturaSemanalRA15DTO>> reporteCSVCapturaSemanal(@PathVariable("periodo_id") Integer periodo_id) {
        List<CapturaSemanalRA15DTO> data = rA15_TransportacionService.reporteCSVCapturaSemanal(periodo_id);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(value = "/buscarHistoricoRA15/{trabajador_id}/{periodo_id}")
    public ResponseEntity<OutputEntity<Historico_Captura_Semanal_RA15>> buscarHistoricoRA15(@PathVariable("trabajador_id") Integer trabajador_id, @PathVariable("periodo_id") Integer periodo_id) {
        OutputEntity<Historico_Captura_Semanal_RA15> out = new OutputEntity<>();
        try {
            Historico_Captura_Semanal_RA15 result = rA15_TransportacionService.findHistoricoRA15(trabajador_id, periodo_id);

            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listadoTrabajadoresRA15/{periodo_id}")
    public ResponseEntity<List<Trabajador_RA15DTO>> trabajadoresRA15Periodo(@PathVariable Integer periodo_id) {
        try {
            List<Trabajador_RA15DTO> trabajador = rA15_TransportacionService.trabajadoresRA15Periodo(periodo_id);
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/obtenerDatosCSVResumen/{periodo_id}")
    public ResponseEntity<List<Captura_Semanal_Resumen_IncidenciasRA15DTO>> reporteCSVCapturaSemanalResumen(@PathVariable Integer periodo_id) {
        try {
            List<Captura_Semanal_Resumen_IncidenciasRA15DTO> resumen = rA15_TransportacionService.reporteCSVCapturaSemanalResumen(periodo_id);
            return new ResponseEntity<>(resumen, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarDatosPlantas")
    public ResponseEntity<Historico_Tmp_Libro_RA15> listarAllDatosPlantas() {
        try {
            List<Historico_Tmp_Libro_RA15> result = rA15_TransportacionService.listarAllDatosPlantas();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/guardarPlantas")
    public ResponseEntity<OutputEntity<String>> guardarPlantas(@RequestBody Historico_Tmp_Libro_RA15 historico_Tmp_Libro_RA15) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            rA15_TransportacionService.saveNewPlantas(historico_Tmp_Libro_RA15);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de plantas guardados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping("/importarPlantas")
    public ResponseEntity<Void> importarPlantasExcel(@RequestParam("archivo") MultipartFile archivoExcel,
            @RequestParam("nombreHoja") String nombreHoja,
            @RequestParam("anio") Integer anio) {
        try {
            rA15_TransportacionService.importarPlantasExcel(archivoExcel, nombreHoja, anio);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/listarPlantasActivas")
    public ResponseEntity<List<Historico_Tmp_Libro_RA15>> listarPlantasActivas() {
        try {
            List<Historico_Tmp_Libro_RA15> result = rA15_TransportacionService.findAllPlantasActivas();
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarPlantasActivasTren")
    public ResponseEntity<List<Historico_Tmp_Libro_Tren_RA15>> listarPlantasActivasTren() {
        try {
            List<Historico_Tmp_Libro_Tren_RA15> result = rA15_TransportacionService.findAllPlantasActivasTrenLigero();
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarIdPlanta/{id_planta}")
    public ResponseEntity<OutputEntity<Historico_Tmp_Libro_RA15>> findOnePlanta(@PathVariable("id_planta") Integer id_planta) {
        OutputEntity<Historico_Tmp_Libro_RA15> out = new OutputEntity<>();
        try {
            Historico_Tmp_Libro_RA15 result = rA15_TransportacionService.findOnePlanta(id_planta);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarPlanta/{id_planta}")
    public ResponseEntity<OutputEntity<String>> updatePlanta(@RequestBody Historico_Tmp_Libro_RA15 plantas, @PathVariable("id_planta") Integer id_planta) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            rA15_TransportacionService.updatePlanta(plantas, id_planta);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de planta modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping(value = "/buscarPlantasOperadoresTitularesActivos")
    public ResponseEntity<OutputEntity<List<OperadorTitularDTO>>> findActiveOpTitular() {
        OutputEntity<List<OperadorTitularDTO>> out = new OutputEntity<>();
        try {
            List<OperadorTitularDTO> result = rA15_TransportacionService.findActiveOpTitular();
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarPlantasOperadoresTrenActivos")
    public ResponseEntity<OutputEntity<List<OperadorTrenDTO>>> findActiveOpTitularTren() {
        OutputEntity<List<OperadorTrenDTO>> out = new OutputEntity<>();
        try {
            List<OperadorTrenDTO> result = rA15_TransportacionService.findActiveOpTitularTren();
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/activar")
    public void activa() {
        rA15_TransportacionService.findRelevos();
    }

    @GetMapping("/existsByAnioAndEstatusTmpHistorico/{anio}")
    public Boolean existsByAnioAndEstatusTmpHistorico(@PathVariable Integer anio) {
        return rA15_TransportacionService.existsByAnioAndEstatusTmpHistorico(anio);
    }

    @GetMapping("/existsByAnioAndEstatusTmpHistoricoTren/{anio}")
    public Boolean existsByAnioAndEstatusTmpHistoricoTren(@PathVariable Integer anio) {
        return rA15_TransportacionService.existsByAnioAndEstatusTmpHistoricoTren(anio);
    }

    @GetMapping("/exportarPlantasAExcel")
    public void exportPlantasIntoExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=plantas_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        rA15_TransportacionService.exportPlantasToExcel(response);
    }

    @GetMapping("/encontrarLineaPorExpediente/{expediente}")
    public String encontrarLineaTrabajadorPorExpediente(@PathVariable Integer expediente) {
        return rA15_TransportacionService.findLineaByExpediete(expediente);
    }
}
