/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.DTO;

import com.backB.backB.entity.Rol;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author nreyes22
 */
public class UsuarioGuardarDTO {
    
    private String nombre;
    private String ap_paterno;
    private String ap_materno;
    private String username;
    private String password;
    private Integer activo;
    private Integer trabajador_id;
    
    private Set<Rol> roles = new HashSet<>();

    public UsuarioGuardarDTO(String nombre, String ap_paterno, String ap_materno, String username, String password, Integer activo, Integer trabajador_id) {
        this.nombre = nombre;
        this.ap_paterno = ap_paterno;
        this.ap_materno = ap_materno;
        this.username = username;
        this.password = password;
        this.activo = activo;
        this.trabajador_id = trabajador_id;
    }

    public UsuarioGuardarDTO() {
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAp_paterno() {
        return ap_paterno;
    }

    public void setAp_paterno(String ap_paterno) {
        this.ap_paterno = ap_paterno;
    }

    public String getAp_materno() {
        return ap_materno;
    }

    public void setAp_materno(String ap_materno) {
        this.ap_materno = ap_materno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    
    
}
