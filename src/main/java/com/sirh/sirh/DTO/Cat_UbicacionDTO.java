/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author oguerrero23
 */


public class Cat_UbicacionDTO implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ubicacion;
    
    private String desc_ubicacion;
    private Integer estatus;
    private Integer clave;

    public Cat_UbicacionDTO() {
        super();
    }

    public Cat_UbicacionDTO(Integer id_ubicacion, String desc_ubicacion, Integer estatus, Integer clave) {
        this.id_ubicacion = id_ubicacion;
        this.desc_ubicacion = desc_ubicacion;
        this.estatus = estatus;
        this.clave = clave;
    }

    public Integer getId_ubicacion() {
        return id_ubicacion;
    }

    public void setId_ubicacion(Integer id_ubicacion) {
        this.id_ubicacion = id_ubicacion;
    }

    public String getDesc_ubicacion() {
        return desc_ubicacion;
    }

    public void setDesc_ubicacion(String desc_ubicacion) {
        this.desc_ubicacion = desc_ubicacion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getClave() {
        return clave;
    }

    public void setClave(Integer clave) {
        this.clave = clave;
    }

  
}
