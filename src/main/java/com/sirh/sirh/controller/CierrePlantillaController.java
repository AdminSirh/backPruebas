/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.service.CierrePlantillaService;
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
@RequestMapping("/cierrePlantilla")
public class CierrePlantillaController {

    @Autowired(required = false)
    private CierrePlantillaService cierrePlantillaService;

    @GetMapping("/ejecutoCierreMensual/{anio}/{mes}")
    public ResponseEntity<Boolean> boolEjecutoCierreMensual(
            @PathVariable("anio") Integer anio,
            @PathVariable("mes") Integer mes) {
        try {
            Boolean existe = cierrePlantillaService.boolEjecutoCierreMensual(anio, mes);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar estado del cierre mensual", e);
        }
    }

}
