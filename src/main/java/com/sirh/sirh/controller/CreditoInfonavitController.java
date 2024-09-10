/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirh.sirh.entity.CreditoInfonavit;
import com.sirh.sirh.entity.Historico_Credito_Infonavit;
import com.sirh.sirh.util.Response;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import sirh.sirh.exception.OutputEntity;
import com.sirh.sirh.service.CreditoInfonavitService;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author rrosero23
 */
@RestController
@RequestMapping("creditoInfonavit")
public class CreditoInfonavitController {

    @Autowired
    CreditoInfonavitService creditoInfonavitService;

    @GetMapping(value = "/buscarCreditoInfonavit/{id_credito_infonavit}")
    public ResponseEntity<OutputEntity<CreditoInfonavit>> findOneCreditoInfonavit(@PathVariable Integer id_credito_infonavit) {
        OutputEntity<CreditoInfonavit> out = new OutputEntity<>();
        try {
            CreditoInfonavit result = creditoInfonavitService.findOneCreditoInfonavit(id_credito_infonavit);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarCreditoInfonavit")
    public ResponseEntity<CreditoInfonavit> listarCreditoInfonavit() {
        try {
            List<CreditoInfonavit> result = creditoInfonavitService.findAllDatosCreditoInfonavit();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarCreditoInfonavit")
    public ResponseEntity<CreditoInfonavit> guardarCreditoInfonavit(@RequestBody CreditoInfonavit creditoInfonavit) {
        try {
            return new ResponseEntity<>(creditoInfonavitService.saveCreditoInfonavit(creditoInfonavit), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/actualizarCreditoInfonavit/{id}")
    public ResponseEntity<OutputEntity<String>> updateCreditoInfonavit(@RequestBody CreditoInfonavit creditoInfonavit, @PathVariable Integer id) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            creditoInfonavitService.updateCreditoInfonavit(creditoInfonavit, id);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Crédito Actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping("/buscarCreditoInfonavitTrabajador/{trabajador_id}")
    public ResponseEntity<OutputEntity<List<CreditoInfonavit>>> findCreditoInfonavitTrabajador(@PathVariable("trabajador_id") Integer trabajador_id) {
        try {
            List<CreditoInfonavit> result = creditoInfonavitService.findCreditoInfonavitTrabajador(trabajador_id);
            return new ResponseEntity(result, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/buscarCreditoHistoricoInfonavitTrabajador/{trabajador_id}")
    public ResponseEntity<OutputEntity<List<Historico_Credito_Infonavit>>> findCreditoInfonavitHistoricoTrabajador(@PathVariable("trabajador_id") Integer trabajador_id) {
        try {
            List<Historico_Credito_Infonavit> result = creditoInfonavitService.findCreditoInfonavitHistoricoTrabajador(trabajador_id);
            return new ResponseEntity(result, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }  

    @GetMapping("actualizaEstatusMotivoBaja/{idCreditoInfonavit}/{motivoBajaId}")
    public ResponseEntity<Object> updateMotivoBajaId(@PathVariable Integer idCreditoInfonavit,
            @PathVariable Integer motivoBajaId) {
        try {
            // Ejecuta la lógica de actualización del motivo de baja
            creditoInfonavitService.updateMotivoBajaId(idCreditoInfonavit, motivoBajaId);

            // Crea un mapa con la respuesta que quieres devolver
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("message", "Motivo Baja ID updated successfully for CreditoInfonavit with ID: " + idCreditoInfonavit);

            // Devuelve un objeto ResponseEntity con el mapa como cuerpo y el estado HTTP 200 OK
            return ResponseEntity.ok(responseMap);

        } catch (EntityNotFoundException e) {
            // Si no se encuentra la entidad, devuelve un objeto ResponseEntity con el estado HTTP 404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("actualizaEstatusCredito/{idCreditoInfonavit}/{estatusCreditoId}")
    public ResponseEntity<Object> updateEstatusCredito(@PathVariable Integer idCreditoInfonavit,
            @PathVariable Integer estatusCreditoId) {
        try {
            creditoInfonavitService.updateEstatusCredito(idCreditoInfonavit, estatusCreditoId);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("message", "Estatus Credito ID updated successfully for CreditoInfonavit with ID: " + idCreditoInfonavit);
            return ResponseEntity.ok(responseMap);
        } catch (EntityNotFoundException e) {
            // Si no se encuentra la entidad, devuelve un objeto ResponseEntity con el estado HTTP 404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping(value = "/actualizarFechaEventoYEstatusCredito/{id}")
    public ResponseEntity<OutputEntity<String>> updateFechaEventoAndEstatusCredito(@RequestBody CreditoInfonavit creditoInfonavit, @PathVariable Integer id) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            creditoInfonavitService.updateFechaEventoAndEstatusCredito(creditoInfonavit, id);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Fecha Evento actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
}
