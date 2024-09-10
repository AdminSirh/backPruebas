/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.entity.Propiedades_Nomina;
import com.sirh.sirh.service.PropiedadesNominaService;
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
 * @author rroscero23
 */
@RestController
@RequestMapping("propiedadesNomina")
public class PropiedadesNominaController {

    @Autowired
    PropiedadesNominaService propiedadesNominaService;

    @GetMapping(value = "/listarPropiedadesNomina")
    public ResponseEntity<Propiedades_Nomina> listaPropiedadesNominas() {
        try {
            List<Propiedades_Nomina> result = propiedadesNominaService.findAllPropiedadesNomina();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/encontrarPropiedadesNomina/{nomina_id}")
    public ResponseEntity<Propiedades_Nomina> findPropiedadesNomina(@PathVariable("nomina_id") Integer nomina_id) {
        try {
            List<Propiedades_Nomina> result = propiedadesNominaService.findPropiedadesNomina(nomina_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarPropiedadNomina/{id_propiedad}")
    public ResponseEntity<OutputEntity<String>> updatePropiedadNomina(@RequestBody Propiedades_Nomina propiedadesNomina,
            @PathVariable("id_propiedad") Integer id_propiedad) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            propiedadesNominaService.updatePropiedadNomina(propiedadesNomina, id_propiedad);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de propiedad modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping(value = "/buscarPropiedad/{id_propiedad}")
    public ResponseEntity<OutputEntity<Propiedades_Nomina>> findOnePropiedad(@PathVariable("id_propiedad") Integer id_propiedad) {
        OutputEntity<Propiedades_Nomina> out = new OutputEntity<>();
        try {
            Propiedades_Nomina result = propiedadesNominaService.findOnePropiedad(id_propiedad);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findSalMinFiniqPropiedad/{id_nomina}")
    public ResponseEntity<Double> findSalMinFiniquito(@PathVariable("id_nomina") Integer id_nomina) {
        try {
            Double salarioMinimo = propiedadesNominaService.findSalMinFiniquito(id_nomina);
            return new ResponseEntity<>(salarioMinimo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findSalMinPropiedad/{id_nomina}")
    public ResponseEntity<Double> findSalMinimo(@PathVariable("id_nomina") Integer id_nomina) {
        try {
            Double salarioMinimo = propiedadesNominaService.findSalMinimo(id_nomina);
            return new ResponseEntity<>(salarioMinimo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
