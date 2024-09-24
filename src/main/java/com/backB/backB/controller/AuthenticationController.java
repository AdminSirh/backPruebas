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
import io.jsonwebtoken.JwtException;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestHeader;
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

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAuthToken(@RequestHeader("Authorization") String token) {
        try {
            // Eliminar "Bearer " del token si está presente
            token = token.substring(7);

            System.out.println("token " + token);

            // Validar el token actual antes de proceder
            if (!jwtUtils.isTokenExpired(token)) {
                // Extraer el nombre de usuario solo si el token no ha expirado
                String username = jwtUtils.extractUsername(token);

                // Cargar los detalles del usuario
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Verificar si el token es válido con los detalles del usuario
                if (jwtUtils.validateToken(token, userDetails)) {
                    // Si es válido, generar un nuevo token
                    String newToken = jwtUtils.refreshToken(token);
                    return ResponseEntity.ok(new JwtResponse(newToken));
                }
            }
            // Si el token es inválido o expirado
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token is invalid or expired.");
        } catch (JwtException | IllegalArgumentException e) {
            // En caso de token mal formado o cualquier excepción relacionada
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token.");
        }
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
            throw new Exception("Usuario deshabilitado");
        } catch (UsernameNotFoundException e) {
            throw new Exception("Usuario no encontrado");
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciales inválidas");
        }
    }

}
