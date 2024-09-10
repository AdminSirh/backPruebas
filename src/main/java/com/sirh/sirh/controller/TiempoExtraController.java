/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.entity.Tiempo_Extra;
import com.sirh.sirh.service.TiempoExtraService;
import com.sirh.sirh.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author rroscero23
 */
@RestController
@RequestMapping("tiempoExtra")
public class TiempoExtraController {

    @Autowired
    TiempoExtraService tiempoExtraService;

    @GetMapping(value = "/encontrarTiempoExtra/{trabajadorId}/{periodoApl}/{periodoGen}/{semanaIncidencias}")
    public ResponseEntity<OutputEntity<Tiempo_Extra>> findEspecificTiempoExtra(@PathVariable("trabajadorId") Integer trabajadorId,
            @PathVariable("periodoApl") Integer periodoApl,
            @PathVariable("periodoGen") Integer periodoGen,
            @PathVariable("semanaIncidencias") String semanaIncidencias) {
        OutputEntity<Tiempo_Extra> out = new OutputEntity<>();
        try {
            Tiempo_Extra result = tiempoExtraService.findTiempoExtra(trabajadorId, periodoApl, periodoGen, semanaIncidencias);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarTiempoExtraId/{id_tiempo_extra}")
    public ResponseEntity<OutputEntity<Tiempo_Extra>> findTiempoExtra(@PathVariable("id_tiempo_extra") Integer id_tiempo_extra) {
        OutputEntity<Tiempo_Extra> out = new OutputEntity<>();
        try {
            Tiempo_Extra result = tiempoExtraService.findTiempoExtraID(id_tiempo_extra);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/guardarTiempoExtra")
    public ResponseEntity<Tiempo_Extra> guardarTiempoExtra(@RequestBody Tiempo_Extra tiempo_Extra) {
        try {
            return new ResponseEntity<>(tiempoExtraService.saveTiempoExtra(tiempo_Extra), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/estatusTiempoExtra/{id_tiempo_extra}/{estatus}")
    public ResponseEntity<Tiempo_Extra> estatusTiempoExtra(@RequestBody @PathVariable("id_tiempo_extra") Integer id_tiempo_extra, @PathVariable("estatus") Integer estatus) {
        try {
            return new ResponseEntity<>(tiempoExtraService.estatusTiempoExtra(id_tiempo_extra, estatus), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarTiempoExtra/{id_tiempo_extra}")
    public ResponseEntity<OutputEntity<String>> updateTiempoExtra(@RequestBody Tiempo_Extra tiempo_extra, @PathVariable("id_tiempo_extra") Integer id_tiempo_extra) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            tiempoExtraService.updateTiempoExtra(tiempo_extra, id_tiempo_extra);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de Teimpo Extra modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping(value = "/updateCve19/{diferenciaTiempoExtra}/{id_tiempo_extra}")
    public ResponseEntity<OutputEntity<String>> updateCve19(@PathVariable("diferenciaTiempoExtra") Double diferenciaTiempoExtra,
            @PathVariable("id_tiempo_extra") Integer id_tiempo_extra) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            tiempoExtraService.updateCve19(diferenciaTiempoExtra, id_tiempo_extra);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "CVE 19 modificada con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
}
