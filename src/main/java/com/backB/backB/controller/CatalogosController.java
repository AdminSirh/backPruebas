/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.controller;

import backB.backB.exception.OutputEntity;
import com.backB.backB.DTO.Cat_Si_NoDTO;
import com.backB.backB.config.SystemControllerLog;
import com.backB.backB.entity.Cat_Genero;
import com.backB.backB.entity.Cat_Si_No;
import com.backB.backB.service.CatalogosService;
import com.backB.backB.util.Response;
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

/**
 *
 * @author rroscero23
 */
@RestController
@RequestMapping("catalogos")
public class CatalogosController {

    @Autowired
    CatalogosService catalogosService;

    // Listar catálogo Si_No
    @SystemControllerLog(operation = "listarSiNo", type = "Se listó catalogo SI NO")
    @GetMapping(value = "/listarSi_No")
    public ResponseEntity<List<Cat_Si_NoDTO>> listarCat_Si_No() {
        try {
            List<Cat_Si_NoDTO> result = catalogosService.findAllSiNo();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Listar todos los datos de Si_No
    @GetMapping(value = "/listarDatosSi_No")
    public ResponseEntity<List<Cat_Si_No>> listarDatosSi_No() {
        try {
            List<Cat_Si_No> result = catalogosService.findAllDatosSiNo();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Guardar un nuevo registro de Si_No
    @PostMapping("/guardarSiNo")
    public ResponseEntity<Cat_Si_No> guardarCatalogoSi_No(@RequestBody Cat_Si_No cat_Si_No) {
        try {
            Cat_Si_No savedEntity = catalogosService.saveSiNo(cat_Si_No);
            return new ResponseEntity<>(savedEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar Si_No
    @PostMapping(value = "/actualizarSiNo/{id}")
    public ResponseEntity<OutputEntity<String>> updateSiNo(@RequestBody Cat_Si_NoDTO cat_Si_No, @PathVariable Integer id) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateSiNo(id, cat_Si_No);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Catálogo Si No Actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar un Si_No por ID
    @GetMapping(value = "/buscarSiNo/{id}")
    public ResponseEntity<Cat_Si_No> findOneSiNo(@PathVariable Integer id) {
        try {
            Cat_Si_No result = catalogosService.findOneSiNo(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cambiar el estado de un Si_No
    @GetMapping(value = "/estadoSiNo/{id}/{estatus}")
    public ResponseEntity<Cat_Si_No> estatusSiNo(@PathVariable Integer id, @PathVariable Integer estatus) {
        try {
            Cat_Si_No result = catalogosService.estatusSiNo(id, estatus);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //******************* CATALOGO GENERO ************************************
    @SystemControllerLog(operation = "listarGeneros", type = "Se listó catalogo Género")
    @GetMapping(value = "/listarDatosGenero")
    public ResponseEntity<Cat_Genero> listarDatosCat_Genero() {
        try {
            List<Cat_Genero> result = catalogosService.findAllDatosGenero();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/guardarGenero")
    public ResponseEntity<Cat_Genero> guardarCatalogoGenero(@RequestBody Cat_Genero cat_Genero) {
        try {
            return new ResponseEntity<>(catalogosService.saveGenero(cat_Genero), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarGenero/{id}")
    public ResponseEntity<OutputEntity<Cat_Genero>> findOneGenero(@PathVariable Integer id) {
        OutputEntity<Cat_Genero> out = new OutputEntity<>();
        try {
            Cat_Genero result = catalogosService.findOneGenero(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarGenero/{id}")
    public ResponseEntity<OutputEntity<String>> updateGenero(@RequestBody Cat_Genero genero, @PathVariable("id") Integer id_genero) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateGenero(genero, id_genero);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de género modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping(value = "/cambioEstatusGenero/{id}/{estatus}")
    public ResponseEntity<Cat_Genero> cambioEstatusGenero(@PathVariable Integer id, @PathVariable Integer estatus) {
        try {
            Cat_Genero result = catalogosService.cambioEstatusGenero(id, estatus);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
