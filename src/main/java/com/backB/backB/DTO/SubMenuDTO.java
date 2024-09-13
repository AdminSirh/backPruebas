/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.DTO;

import java.io.Serializable;

/**
 *
 * @author dguerrero18
 */
public class SubMenuDTO implements Serializable{
     private String submenuNombre;

    public String getSubmenuNombre() {
        return submenuNombre;
    }

    public void setSubmenuNombre(String submenuNombre) {
        this.submenuNombre = submenuNombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    private String descripcion;
}