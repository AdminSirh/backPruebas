/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.AyudaDTO;
import com.sirh.sirh.DTO.AyudaDatosDTO;
import com.sirh.sirh.entity.Ayuda;
import com.sirh.sirh.entity.Ayuda_Dias_Permiso;
import com.sirh.sirh.service.AyudaService;
import com.sirh.sirh.util.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author akalvarez19
 */
@RestController
@RequestMapping("ayuda")
public class AyudaController {

    @Autowired
    AyudaService ayudaService;

    //******************* SERVICIOS AYUDA  ************************************
    //******************** GUARDAR AYUDA************************
    @PostMapping(value = "/guardarAyuda")
    public ResponseEntity<OutputEntity<String>> guardarAyuda(@RequestBody Ayuda ayuda) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            ayudaService.saveAyuda(ayuda);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de incidencia guardados con éxito");
            //out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "");
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** EDITAR AYUDA ************************
    @PostMapping(value = "/editarAyuda/{id_ayuda}")
    public ResponseEntity<OutputEntity<String>> editarAyudaTrabajador(@RequestBody AyudaDTO ayuda, @PathVariable Integer id_ayuda) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            ayudaService.editarAyuda(ayuda, id_ayuda);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de incidencia modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** BUSCAR AYUDA POR ID ************************
    @GetMapping(value = "/buscarAyudaId/{id_ayuda}")
    public ResponseEntity<OutputEntity<Ayuda>> findAyuda(@PathVariable("id_ayuda") Integer id_ayuda) {
        OutputEntity<Ayuda> out = new OutputEntity<>();
        try {
            Ayuda result = ayudaService.findAyudaID(id_ayuda);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** BUSCAR AYUDA POR ID TRABAJADOR ************************
    //Buscar contacto por Id_Persona 
    @GetMapping(value = "/buscarAyudaIDT/{trabajador_id}")
    public ResponseEntity<OutputEntity<List<Ayuda>>> buscarAyudaTrabajador(@PathVariable("trabajador_id") Integer trabajador_id) {
        OutputEntity<List<Ayuda>> out = new OutputEntity<>();
        try {
            List<Ayuda> result = ayudaService.findAyudaIDT(trabajador_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** BUSCAR AYUDA POR ID DE LA INCIDENCIA ************************
    //Buscar contacto por Id_Persona 
    @GetMapping(value = "/buscarAyudaSiExiste/{id_incidencia}")
    public ResponseEntity<OutputEntity<List<AyudaDTO>>> buscarAyudaSiExiste(@PathVariable("id_incidencia") Integer id_incidencia) {
        OutputEntity<List<AyudaDTO>> out = new OutputEntity<>();
        try {
            List<AyudaDTO> result = ayudaService.findAyudaIfExists(id_incidencia);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarDiasAyudaPermiso")
    public ResponseEntity<Ayuda_Dias_Permiso> listarDiasAyudaPermiso() {
        try {
            List<Ayuda_Dias_Permiso> result = ayudaService.findAllDiasPermiso();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarAyudaDiasPermiso/{id}")
    public ResponseEntity<OutputEntity<Ayuda_Dias_Permiso>> findAyudaDiasPermiso(@PathVariable Integer id) {
        OutputEntity<Ayuda_Dias_Permiso> out = new OutputEntity<>();
        try {
            Ayuda_Dias_Permiso result = ayudaService.findAyudaDiasPermiso(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** EDITAR AYUDA DIAS PERMISO ************************
    @PostMapping(value = "/editarAyudaDiasPermiso/{id_ayuda_dias_permiso}")
    public ResponseEntity<OutputEntity<String>> editarAyudaDiasPermiso(@RequestBody Ayuda_Dias_Permiso adp, @PathVariable Integer id_ayuda_dias_permiso) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            ayudaService.editarAyudaDiasPermiso(adp, id_ayuda_dias_permiso);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de días de permiso modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping(value = "/guardarAyudaDiasPermiso")
    public ResponseEntity<Ayuda_Dias_Permiso> guardarAyudaDiasPermiso(@RequestBody Ayuda_Dias_Permiso ayuda_dias_permiso) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            //Se retorna el registro generado para guardar el id como llave foránea de la tabla principal
            return new ResponseEntity<>(ayudaService.saveAyudaDiasPermiso(ayuda_dias_permiso), HttpStatus.CREATED);

        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarAyudas")
    public ResponseEntity<List<AyudaDatosDTO>> findAllAyudas(
            @RequestParam(value = "estadoIncidenciaParametro", required = false) Integer estadoIncidenciaParametro,
            @RequestParam(value = "fechaInicioParametro", required = false) String fechaInicioParametro,
            @RequestParam(value = "fechaFinParametro", required = false) String fechaFinParametro) throws ParseException {

        Date fechaInicio = (fechaInicioParametro != null) ? new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicioParametro) : null;
        Date fechaFin = (fechaFinParametro != null) ? new SimpleDateFormat("yyyy-MM-dd").parse(fechaFinParametro) : null;

        try {
            List<AyudaDatosDTO> result = ayudaService.findAllAyudas(estadoIncidenciaParametro, fechaInicio, fechaFin);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
