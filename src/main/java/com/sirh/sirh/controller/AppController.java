/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.entity.Menu;
import com.sirh.sirh.entity.Rol;
import com.sirh.sirh.entity.Submenu_Rol;
import com.sirh.sirh.entity.Usuario;
import com.sirh.sirh.service.MenuService;
import com.sirh.sirh.service.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author jarellano22
 */
@RestController
@SessionAttributes({"usuario"})
public class AppController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    MenuService menuService;

    @ModelAttribute("usuario")
    public Usuario usuario() {
        return new Usuario();
    }

    @GetMapping(value = "/session")
    public ResponseEntity sirh(Authentication auth, HttpSession session) {
        try {
            String Username = auth.getName();
            Usuario usuario = usuarioService.findByUsuarioSession(Username);
            List datos = new ArrayList<>();
            datos.add(usuario.getId());
            datos.add(usuario.getNombre());
            datos.add(usuario.getAp_paterno());
            return new ResponseEntity(datos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/menu")
    public ResponseEntity<Menu> listarMenu(Authentication auth, HttpSession session) {
        try {
            String Username = auth.getName();
            Usuario usuario = usuarioService.findByUsuarioSession(Username);
            List<Submenu_Rol> submenu_Rol = new ArrayList<>();
            for (Rol rol : usuario.getRoles()) {
                submenu_Rol.addAll(menuService.findBySubMenuRoles(rol.getId()));
            }
            List<Submenu_Rol> Menu = submenu_Rol.stream().filter(distinctByKey(p -> p.getSubmenu())).collect(Collectors.toList());
            return new ResponseEntity(Menu, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @GetMapping(value = "/prueba")
    public ResponseEntity<Submenu_Rol> prueba() {
        try {
            //   List<Submenu_Rol> submenu_Rol = menuService.findBySubMenuRoles(2);
            return new ResponseEntity(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/sessionRoles")
    public ResponseEntity sessionRoles(Authentication auth, HttpSession session) {
        try {
            String Username = auth.getName();
            Usuario usuario = usuarioService.findByUsuarioSession(Username);
            List datos = new ArrayList<>();

            datos.add(usuario.getRoles());
            System.out.println("roles " + usuario.getRoles().size());
            return new ResponseEntity(datos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
