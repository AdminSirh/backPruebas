/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.InformacionPlazasDTO;
import com.sirh.sirh.DTO.Trabajador_PuestoDTO;
import com.sirh.sirh.DTO.Trabajador_SueldoDTO;
import com.sirh.sirh.DTO.Trabajador_plazaDTO;
import com.sirh.sirh.entity.Trabajador_plaza;
import com.sirh.sirh.service.PlazaService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author rrosero23
 */
@RestController
@RequestMapping("plaza")
public class PlazaController {

    @Autowired
    PlazaService plazaService;

    @GetMapping(value = "/buscarPorPuestoYEstatus/{puestoId}/{estatusPlazaId}")
    public ResponseEntity<List<Trabajador_plazaDTO>> findByPuestoAndEstatus(@RequestParam("puesto_id") Integer puestoId, @RequestParam("estatus_plaza_id") Integer estatusPlazaId) {
        List<Trabajador_plazaDTO> trabajadorPlazaData = plazaService.findByPuestoAndEstatus(puestoId, estatusPlazaId);
        return new ResponseEntity<>(trabajadorPlazaData, HttpStatus.OK);
    }

    @GetMapping(value = "/buscarPlazaTrabajador/{trabajador_id}")
    public ResponseEntity<List<Trabajador_PuestoDTO>> findPlazaTrabajadorByIdTrabajador(@PathVariable("trabajador_id") Integer trabajador_id) {
        List<Trabajador_PuestoDTO> trabajadorPlazaData = plazaService.findPlazaTrabajadorByIdTrabajador(trabajador_id);
        return new ResponseEntity<>(trabajadorPlazaData, HttpStatus.OK);
    }

    @GetMapping(value = "/buscarPorPuesto/{puestoId}")
    public ResponseEntity<List<Trabajador_plazaDTO>> findByPuesto(@RequestParam("puesto_id") Integer puestoId) {
        List<Trabajador_plazaDTO> trabajadorPlazaData = plazaService.findByPuesto(puestoId);
        return new ResponseEntity<>(trabajadorPlazaData, HttpStatus.OK);
    }

    @GetMapping("/tiposDecontratos/")
    public List<Trabajador_plazaDTO> buscarUnoOVariosTipodeContratos(@RequestParam List<Integer> tipoContratoIds) {
        return plazaService.findTiposDeContrato(tipoContratoIds);
    }

    @GetMapping("/tipoDeContratoAndPuesto/")
    public ResponseEntity<List<Trabajador_plazaDTO>> buscarPorTipodeContratoYPuesto(@RequestParam List<Integer> tipoContratoIds, @RequestParam List<Integer> puestoIds) {

        List<Trabajador_plazaDTO> trabajadoresPlazas = plazaService.findByTipoContratoIdAndPuestoId(tipoContratoIds, puestoIds);

        if (trabajadoresPlazas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trabajadoresPlazas, HttpStatus.OK);
    }

    @GetMapping("/buscarEstatusPuestoPlaza")
    public ResponseEntity<List<Trabajador_plazaDTO>> buscarPorPuestoEstatusYTipoDeContrato(@RequestParam(name = "puesto_id") Integer puestoId,@RequestParam(name = "estatus_plaza_id") Integer estatusPlazaId,
            @RequestParam List<Integer> tipoContratoId) {
        {

            List<Trabajador_plazaDTO> trabajadoresPlaza = plazaService.findByPuestoAndEstatusAndTipoDeContrato(puestoId, estatusPlazaId, tipoContratoId);

            if (trabajadoresPlaza.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(trabajadoresPlaza);
        }

    }

    @GetMapping("/buscarByNumeroPlaza/{numero_plaza}")
    public ResponseEntity<List<InformacionPlazasDTO>> findByNumPlaza(@PathVariable Integer numero_plaza) {
        try {
            List<InformacionPlazasDTO> result = plazaService.findByNumPlaza(numero_plaza);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/estatusPlazasDisp/")
    public ResponseEntity<List<InformacionPlazasDTO>> findEstatusPlaza(@RequestParam List<Integer> estatus_plaza_id) {
        try {
            List<InformacionPlazasDTO> result = plazaService.findByEstatusPlaza(estatus_plaza_id);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/areaPlaza/{id_area}")
    public ResponseEntity<List<InformacionPlazasDTO>> findAreaPlaza(@PathVariable Integer id_area) {
        try {
            List<InformacionPlazasDTO> result = plazaService.findByAreaPlaza(id_area);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/areaAndEstatusPlaza")
    public ResponseEntity<List<InformacionPlazasDTO>> buscarAreaAndEstatusPlaza(
            @RequestParam(name = "area_id") Integer id_area,
            @RequestParam List<Integer> estatus_plaza_id) {
        try {
            List<InformacionPlazasDTO> result = plazaService.findByAreaAndEstatusPlaza(id_area, estatus_plaza_id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/encontrarSueldo/{id_trabajador}")
    public ResponseEntity<Trabajador_SueldoDTO> buscarAreaAndEstatusPlaza(
            @PathVariable Integer id_trabajador) {
        try {
            Trabajador_SueldoDTO result = plazaService.findSueldosTrabajador(id_trabajador);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
