/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.DTO;

import com.backB.backB.util.RolNombre;

/**
 *
 * @author rroscero23
 */
public class AdminLogDTO {

    private String id_incidencia_generada;
    private RolNombre creators_role;
    private String user_name;

    public AdminLogDTO() {
    }

    public AdminLogDTO(String id_incidencia_generada, RolNombre creators_role, String user_name) {
        this.id_incidencia_generada = id_incidencia_generada;
        this.creators_role = creators_role;
        this.user_name = user_name;
    }

    public String getId_incidencia_generada() {
        return id_incidencia_generada;
    }

    public void setId_incidencia_generada(String id_incidencia_generada) {
        this.id_incidencia_generada = id_incidencia_generada;
    }

    public RolNombre getCreators_role() {
        return creators_role;
    }

    public void setCreators_role(RolNombre creators_role) {
        this.creators_role = creators_role;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

}
