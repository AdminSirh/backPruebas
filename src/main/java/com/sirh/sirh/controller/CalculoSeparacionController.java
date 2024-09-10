/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.FiniquitoDTO;
import com.sirh.sirh.service.CalculoSeparacionService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author rroscero23
 */
@RestController
@RequestMapping("calculoSeparacion")
public class CalculoSeparacionController {

    @Autowired
    CalculoSeparacionService calculoSeparacionService;

    @GetMapping(value = "/calculoFiniquito/{idTrabajador}/{finiquito}/{jubilacion}/{pagoMarcha}/{fRenunciaVol}/{fRecisionContrato}/{fRemocionCargo}/{anios}/{meses}/{dias}/{fechaIngreso}/{fechaBaja}")
    public FiniquitoDTO calculaFiniquitoTrabajador(
            @PathVariable("idTrabajador") Integer idTrabajador,
            @PathVariable("finiquito") Boolean finiquito,
            @PathVariable("jubilacion") Boolean jubilacion,
            @PathVariable("pagoMarcha") Boolean pagoMarcha,
            @PathVariable("fRenunciaVol") Boolean fRenunciaVol,
            @PathVariable("fRecisionContrato") Boolean fRecisionContrato,
            @PathVariable("fRemocionCargo") Boolean fRemocionCargo,
            @PathVariable("anios") Double anios,
            @PathVariable("meses") Double meses,
            @PathVariable("dias") Double dias,
            @PathVariable("fechaIngreso") String fechaIngreso,
            @PathVariable("fechaBaja") String fechaBaja) throws ParseException {
        // Definir el formato de fecha esperado
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // Convertir las cadenas de fecha a instancias de LocalDate
            LocalDate localFechaIngreso = LocalDate.parse(fechaIngreso, dateFormatter);
            LocalDate localFechaBaja = LocalDate.parse(fechaBaja, dateFormatter);

            // Llama al servicio para calcular el finiquito y obtén el DTO resultante
            FiniquitoDTO finiquitoDTO = calculoSeparacionService.calculaFiniquitoTrabajador(
                    idTrabajador,
                    finiquito,
                    jubilacion,
                    pagoMarcha,
                    fRenunciaVol,
                    fRecisionContrato,
                    fRemocionCargo,
                    anios, meses, dias,
                    localFechaIngreso,
                    localFechaBaja);

            // Devuelve el DTO calculado
            return finiquitoDTO;
        } catch (DateTimeParseException e) {
            // En caso de error en el parseo, devuelve una respuesta de error con el código 400
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de fecha inválido", e);
        } catch (Exception e) {
            // En caso de error general, devuelve una respuesta de error con el código 500
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al calcular el Finiquito del Trabajador", e);
        }
    }

    @GetMapping(value = "/calcularIsrAnual/{total}/{idNomina}/{aniosServicio}/{esIndemnizacion}")
    public ResponseEntity<Double> calcularISR(@PathVariable("total") Double total,
            @PathVariable("idNomina") Integer idNomina,
            @PathVariable("aniosServicio") Double aniosServicio,
            @PathVariable("esIndemnizacion") Boolean esIndemnizacion) {
        try {
            Double isrCalculado = calculoSeparacionService.calcularIsrAnual(total, idNomina, aniosServicio, esIndemnizacion);
            return ResponseEntity.ok(isrCalculado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
