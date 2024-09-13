/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.controller;

import com.backB.backB.entity.Rol;
import com.backB.backB.service.RolService;
import com.backB.backB.util.Response;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import backB.backB.exception.OutputEntity;

/**
 *
 * @author nreyes22
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("rol")
public class RolController {

    @Autowired
    RolService rolService;

    @GetMapping(value = "/listarRoles")
    public ResponseEntity<OutputEntity<List<Rol>>> listarRol() {
        OutputEntity<List<Rol>> out = new OutputEntity<>();
        try {
            List<Rol> result = rolService.findAll();

            if (result.isEmpty()) {

                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/userRoles")
    @ResponseBody
    public Set<String> getUserRoles(Principal principal) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
            .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
}

}