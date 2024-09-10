/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.entity.Monto_Vales;
import com.sirh.sirh.entity.Periodos_Pago;
import com.sirh.sirh.entity.Tmp_Movimientos;
import com.sirh.sirh.service.ValesService;
import com.sirh.sirh.util.Response;
import java.util.List;
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
 * @author oguerrero23
 */
@RestController
@RequestMapping("vales")
public class ValesController {
    @Autowired
    ValesService valesService;
    
    
    @GetMapping(value = "/listarMontos")
    public ResponseEntity<Monto_Vales> listarMontos() {
        try {
            List<Monto_Vales> result = valesService.findAllMontos();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/guardarMonto")
    public ResponseEntity<Monto_Vales> guardarMonto(@RequestBody Monto_Vales monto_vales) {
        try {
            return new ResponseEntity<>(valesService.saveMonto(monto_vales), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //Buscar monto por ID 
    @GetMapping(value = "/buscarMonto/{id_monto_vales}")
    public ResponseEntity<OutputEntity<Monto_Vales>> buscarMonto(@PathVariable Integer id_monto_vales) {
        OutputEntity<Monto_Vales> out = new OutputEntity<>();
        try {
            Monto_Vales result = valesService.findOneMonto(id_monto_vales);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/buscarMontoDias/{dias_trabajados}")
    public ResponseEntity<Monto_Vales> buscarMontoDias(@PathVariable("dias_trabajados") Integer dias_trabajados) {
        try {
            Monto_Vales result = valesService.findMontosVales(dias_trabajados);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(value = "/editarMonto/{id_monto_vales}")
    public ResponseEntity<OutputEntity<String>> editarMonto(@RequestBody Monto_Vales monto_vales, @PathVariable("id_monto_vales") Integer id_monto_vales) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            valesService.updateMonto(monto_vales, id_monto_vales);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos del monto modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    
    //******************* PERIODOS DE PAGO  ************************************
    @GetMapping(value = "/buscarPeriodosVales/{nomina_id}")
    public ResponseEntity<List<Periodos_Pago>> buscarPeriodosVales(@PathVariable("nomina_id") Integer nomina_id) {
        try {
            List<Periodos_Pago> result = valesService.findPeriodosVales(nomina_id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //******************* TMP_MOVIMIENTOS (2) DIAS LABORADOS EN EL AÑO *********
    @PostMapping("/guardarMovimientos2")
    public ResponseEntity<Tmp_Movimientos> guardarMovimientos2(@RequestBody Tmp_Movimientos movimientos) {
        try {
            return new ResponseEntity<>(valesService.saveMovimientos2(movimientos), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //******************* TMP_MOVIMIENTOS (244) TOTAL DE INCIDENCIAS POR AÑO *********
    @PostMapping("/guardarMovimientos244")
    public ResponseEntity<Tmp_Movimientos> guardarMovimientos244(@RequestBody Tmp_Movimientos movimientos) {
        try {
            return new ResponseEntity<>(valesService.saveMovimientos244(movimientos), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //******************* TMP_MOVIMIENTOS (249) VALES DE FIN DE AÑO *********
    @PostMapping("/guardarMovimientos249")
    public ResponseEntity<Tmp_Movimientos> guardarMovimientos249(@RequestBody Tmp_Movimientos movimientos) {
        try {
            return new ResponseEntity<>(valesService.saveMovimientos249(movimientos), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
