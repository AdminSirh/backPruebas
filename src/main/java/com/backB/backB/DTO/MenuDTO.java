/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.DTO;
import java.io.Serializable;

/**
 *
 * @author jarellano22
 */
public class MenuDTO implements Serializable {


    private String menuNombre;
    private String descripcion;
    private Integer orden;

    public MenuDTO(String menuNombre, String descripcion, Integer orden) {
        this.menuNombre = menuNombre;
        this.descripcion = descripcion;
        this.orden = orden;
    }

    public MenuDTO() {
        super();
    }
    
    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getMenuNombre() {
        return menuNombre;
    }

    public void setMenuNombre(String menuNombre) {
        this.menuNombre = menuNombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
