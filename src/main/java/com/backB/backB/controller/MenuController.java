/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.controller;

import com.backB.backB.DTO.MenuDTO;
import com.backB.backB.entity.Menu;
import com.backB.backB.service.MenuService;
import com.backB.backB.util.Response;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import backB.backB.exception.Exceptions;
import backB.backB.exception.OutputEntity;

/**
 *
 * @author jarellano22
 */
@RestController
@RequestMapping("menu")
@CrossOrigin("*")
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping(value = "/listarMenu")
    public ResponseEntity<Menu> listarMenu() {
        try {
            List<Menu> result = menuService.findAllMenu();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/buscarMenu/{id}")
    public ResponseEntity<OutputEntity<Menu>> buscarMenu(@PathVariable Integer id) {
        OutputEntity<Menu> out = new OutputEntity<>();
        try {
            Menu menu = menuService.findOne(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), menu);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/actualizarMenu/{id}")
    public ResponseEntity<OutputEntity<String>> actualizarMenu(@PathVariable Integer id, @RequestBody MenuDTO menu) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            menuService.actualizarMenu(id, menu);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Menu Actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/guardarMenu")
    public ResponseEntity<OutputEntity<String>> guardarMenu(@RequestBody Menu menu
    ) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            if (menuService.existsByNombre(menu.getMenuNombre()) != null) {
                throw new Exceptions(Response.MENUEXISTE.getKey(), Response.MENUEXISTE.getCode());
            }
            menuService.save(menu);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Menu Guardado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exceptions e) {
            out.failed(e.getCode(), e.getMessages(), null);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    
   
}
