/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.Calculo_Dif_Cve19DTO;
import com.sirh.sirh.DTO.Listado_RA_10DTO;
import com.sirh.sirh.DTO.TrabajadorPagoRA10;
import com.sirh.sirh.entity.Captura_Semanal_RA10;
import com.sirh.sirh.service.Captura_Semanal_RA10Service;
import com.sirh.sirh.util.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author rroscero23
 */
@RestController
@RequestMapping("ra10")
public class CapturaSemanalRA10Controller {

    @Autowired
    Captura_Semanal_RA10Service captura_Semanal_RA10Service;

    @PostMapping("/guardarRa10")
    public ResponseEntity<?> saveRa10(@RequestBody Captura_Semanal_RA10 captura_Semanal_RA10) {
        try {
            return new ResponseEntity<>(captura_Semanal_RA10Service.saveRa10(captura_Semanal_RA10), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/actualizarRa10/{id_ra_10}")
    public ResponseEntity<OutputEntity<String>> actualizarRa10(
            @RequestBody Captura_Semanal_RA10 captura_Semanal_RA10, @PathVariable Integer id_ra_10) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            captura_Semanal_RA10Service.updateRa10(captura_Semanal_RA10, id_ra_10);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Registro Actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping(value = "/encuentraRegistrosCapturaSemanalTrabajador/{id_trabajador}")
    public ResponseEntity<Captura_Semanal_RA10> findCapturasTrabajador(@RequestBody @PathVariable("id_trabajador") Integer id_trabajador) {
        try {
            List<Captura_Semanal_RA10> result = captura_Semanal_RA10Service.findCapturasTrabajador(id_trabajador);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarCapturaSemanal/{id_ra_10}")
    public ResponseEntity<OutputEntity<Captura_Semanal_RA10>> buscarCapturaSemanal(@PathVariable Integer id_ra_10) {
        OutputEntity<Captura_Semanal_RA10> out = new OutputEntity<>();
        try {
            Captura_Semanal_RA10 result = captura_Semanal_RA10Service.findOneCapturaSemanalRa10(id_ra_10);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/cambioEstatusCapturaRA10/{id_ra_10}/{estatus}")
    public ResponseEntity<Captura_Semanal_RA10> cambioEstatusCaptura(@RequestBody @PathVariable Integer id_ra_10, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(captura_Semanal_RA10Service.cambioEstatusCaptura(id_ra_10, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/capturaSemanal")
    public ResponseEntity<List<Captura_Semanal_RA10>> findCapturaSemanal(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicial,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFinal,
            @RequestParam(required = false) Integer trabajador_id) {
        List<Captura_Semanal_RA10> capturaSemanalList = captura_Semanal_RA10Service.findCapturaSemanalByFechas(fechaInicial, fechaFinal, trabajador_id);
        if (capturaSemanalList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(capturaSemanalList);
    }

    @GetMapping(value = "/findListadoExcel/{periodo_generacion}")
    public ResponseEntity<TrabajadorPagoRA10> findListadoExcel(@RequestBody @PathVariable("periodo_generacion") Integer periodo_generacion) {
        try {
            List<TrabajadorPagoRA10> result = captura_Semanal_RA10Service.findListadoExcel(periodo_generacion);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/encontrarAreaYPeriodoGen")
    public ResponseEntity<List<Listado_RA_10DTO>> findAreaAndPeriodoGen(
            @RequestParam(required = false) Integer id_area,
            @RequestParam(required = false) Integer periodo_generacion) {
        List<Listado_RA_10DTO> listaTrabajadores = captura_Semanal_RA10Service.findAreaAndPeriodoGen(id_area,
                periodo_generacion);
        if (listaTrabajadores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaTrabajadores);
    }

    @GetMapping("/exportTrabajadoresConMontosRA10")
    public ResponseEntity<String> exportExcel(HttpServletResponse response, Integer periodo_generacion) throws IOException {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=diferenciaSueldoOrdinarioPeriodo" + periodo_generacion + ".xlsx";
            response.setHeader(headerKey, headerValue);
            captura_Semanal_RA10Service.exportTrabajadoresConMontosRA10(response, periodo_generacion);
            return ResponseEntity.ok("Excel generado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/existeCapturaRA10/{trabajador_id}/{periodo_generacion}")
    public Boolean existeCapturaRa10PorIdTrabajador(@PathVariable Integer trabajador_id, @PathVariable Integer periodo_generacion) {
        return captura_Semanal_RA10Service.existeCapturaRa10PorIdTrabajador(trabajador_id, periodo_generacion);
    }

    @GetMapping("/buscarCapturaPeriodoTrabajador/{trabajador_id}/{periodo_generacion}")
    public ResponseEntity<List<Captura_Semanal_RA10>> buscarCapturaPeriodoTrabajador(
            @PathVariable Integer trabajador_id,
            @PathVariable Integer periodo_generacion) {
        List<Captura_Semanal_RA10> listadoRa10Trabajador = captura_Semanal_RA10Service.buscarCapturaPeriodoTrabajador(trabajador_id,
                periodo_generacion);
        if (listadoRa10Trabajador.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listadoRa10Trabajador);
    }

    @GetMapping("/calcularCve19TiempoExtra/{trabajador_id}/{periodo_generacion}/{total_te_doble}/{total_te_paseos_doble}/{total_te_festivo_doble}/{horas_extras_laboradas_domingo}")
    public ResponseEntity<Calculo_Dif_Cve19DTO> calcularCve19DiferenciaTiempoExtra(
            @PathVariable Integer trabajador_id,
            @PathVariable Integer periodo_generacion,
            @PathVariable Double total_te_doble,
            @PathVariable Double total_te_paseos_doble,
            @PathVariable Double total_te_festivo_doble,
            @PathVariable Double horas_extras_laboradas_domingo) {

        Calculo_Dif_Cve19DTO resultado = captura_Semanal_RA10Service.calcularCve19DiferenciaTiempoExtra(
                trabajador_id,
                periodo_generacion,
                total_te_doble,
                total_te_paseos_doble,
                total_te_festivo_doble,
                horas_extras_laboradas_domingo);

        if (resultado != null) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
