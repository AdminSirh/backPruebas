/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.ConceptosRetroActJubDTO;
import com.sirh.sirh.service.CalculoRetroactivoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rroscero23
 */
@RestController
@RequestMapping("calculoRetroactivo")
public class CalculoRetroactivoController {

    @Autowired
    private CalculoRetroactivoService calculoRetroactivoService;

    @PostMapping("/calcularJubilados")
    public ResponseEntity<String> calcularRetroactivo(@RequestParam List<String> expedientesDescartados,
            @RequestParam Integer periodoInicio,
            @RequestParam Integer periodoFin,
            @RequestParam Double porcentajeIncrPension,
            @RequestParam Double porcentajeIncrAyudaTransp,
            @RequestParam Double porcentajeIncrVales,
            @RequestParam Double porcentajeIncrPensAtrasada) {
        try {
            calculoRetroactivoService.calculaRetroactivoJubilados(expedientesDescartados, periodoInicio, periodoFin, porcentajeIncrPension, porcentajeIncrAyudaTransp, porcentajeIncrVales, porcentajeIncrPensAtrasada);
            return ResponseEntity.ok("Cálculo de retroactivo realizado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/insertarTmpMovimientosJubilados")
    public ResponseEntity<String> insertarEnTmpMovimientos() {
        try {
            calculoRetroactivoService.insertarEnTmpMovimientos();
            return ResponseEntity.ok("Se guardaron los movimientos.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value = "/pruebaQueryConceptos/{periodoInicial}/{periodoFinal}/{trabajadoresDescartados}/")
    public ResponseEntity<List<ConceptosRetroActJubDTO>> conceptosRetroactivoJubilados(@PathVariable Integer periodoInicial,
            @PathVariable Integer periodoFinal,
            @PathVariable List<Integer> trabajadoresDescartados) {
        try {
            List<ConceptosRetroActJubDTO> trabajador = calculoRetroactivoService.conceptosRetroactivoJubilados(periodoInicial, periodoFinal, trabajadoresDescartados);
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/pruebaQueryCalculo/{periodoInicial}/{periodoFinal}/{trabajadoresDescartados}/")
    public ResponseEntity<List<ConceptosRetroActJubDTO>> calculosRetroactivoJubilados(@PathVariable Integer periodoInicial,
            @PathVariable Integer periodoFinal,
            @PathVariable List<Integer> trabajadoresDescartados) {
        try {
            List<ConceptosRetroActJubDTO> trabajador = calculoRetroactivoService.calculosRetroactivoJubilados(periodoInicial, periodoFinal, trabajadoresDescartados);
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
