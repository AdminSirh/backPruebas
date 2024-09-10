/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.SubMenuDTO;
import com.sirh.sirh.entity.Submenu;
import com.sirh.sirh.entity.Submenu_Rol;
import com.sirh.sirh.service.SubMenuRolService;
import com.sirh.sirh.service.SubMenuService;
import com.sirh.sirh.util.Response;
import java.util.List;
import java.util.Set;
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
 * @author jarellano22
 */
@RestController
@RequestMapping("submenu")
public class SubMenuController {

    @Autowired
    SubMenuService subMenuService;

    @Autowired
    SubMenuRolService subMenuRolService;

    //Submenu solo activos 
    @GetMapping(value = "/listarSubMenu/{menu_id}")
    public ResponseEntity<Submenu> listarSubMenu(@RequestBody @PathVariable Integer menu_id) {
        try {
            List<Submenu> result = subMenuService.findAllSubMenu(menu_id);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Submenu todos
    @GetMapping(value = "/listarSubMenuAll/{menu_id}")
    public ResponseEntity<Submenu> listarSubMenuAll(@RequestBody @PathVariable Integer menu_id) {
        try {
            List<Submenu> result = subMenuService.findAll(menu_id);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarSubmenu/{id}")
    public ResponseEntity<Submenu> buscarSubmenu(@PathVariable Integer id) {
        try {
            Submenu result = subMenuService.findOne(id);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/estatusSubmenu/{id}/{activo}")
    public ResponseEntity<Submenu> estatusSubmenu(@RequestBody @PathVariable Integer id, @PathVariable Integer activo) {
        try {
            return new ResponseEntity<>(subMenuService.activo(id, activo), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/actualizarSubmenu/{id}")
    public ResponseEntity<OutputEntity<String>> actualizarSubmenu(@PathVariable Integer id, @RequestBody SubMenuDTO Submenu) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            subMenuService.actualizarSubmenu(id, Submenu);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Sub-Menu Actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/guardaSubmenu")
    public ResponseEntity<OutputEntity<String>> guardaSubmenu(@RequestBody Submenu Submenu) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            subMenuService.save(Submenu);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Submenu Guardado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping(value = "/buscarSubmenuRol/{id}")
    public ResponseEntity<OutputEntity<Set>> buscarSubmenuRol(@PathVariable Integer id) {
        OutputEntity<Set> out = new OutputEntity<>();
        try {
            Submenu result = subMenuService.findOne(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result.getRoles());
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/guardarSubmenuRol")
    public ResponseEntity<OutputEntity<String>> guardarSubmenuRol(@RequestBody Submenu_Rol submenu_rol) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            subMenuRolService.save(submenu_rol);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Rol Asignado");
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            out.error();
            // System.out.println("usuario rol " + submenu_rol.get + e);
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping(value = "/eliminarRol/{submenu_id}/{rol_id}")
    public ResponseEntity<OutputEntity<Integer>> eliminarRol(@RequestBody @PathVariable Integer submenu_id, @PathVariable Integer rol_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            subMenuRolService.delete_Rol(submenu_id, rol_id);
            out.success(Response.DELETED.getCode(), Response.DELETED.getKey(), null);
            return ResponseEntity.ok(out);
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
