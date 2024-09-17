/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.controller;

import com.backB.backB.entity.JwtRequest;
import com.backB.backB.entity.JwtResponse;
import com.backB.backB.security.JwtUtils;
import com.backB.backB.security.service.UserDetailsServiceImpl;
import com.backB.backB.security.service.UsuarioPrincipal;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rroscero23
 */
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/generate-token")
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            // Intentamos autenticar con las credenciales proporcionadas
            autenticar(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (Exception e) {
            // Manejar cualquier excepción lanzada durante la autenticación
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        // Si la autenticación es exitosa, generamos el token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);

        // Retornamos el token generado
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/actual-usuario")
    public UsuarioPrincipal obtenerUsuarioActual(Principal principal) {
        return (UsuarioPrincipal) this.userDetailsService.loadUserByUsername(principal.getName());
    }

    private void autenticar(String username, String password) throws Exception {
        try {
            //Para verificar si el usuario existe, si no lanzar UsernameNotFoundException
            userDetailsService.loadUserByUsername(username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USUARIO DESHABILITADO");
        } catch (UsernameNotFoundException e) {
            throw new Exception("USUARIO NO ENCONTRADO");
        } catch (BadCredentialsException e) {
            throw new Exception("CREDENCIALES INVALIDAS");
        }
    }

}
