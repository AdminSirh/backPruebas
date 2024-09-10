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

public class Cat_Tipo_NominaDTO implements Serializable {
    
    
    private Integer id_tipo_nomina;
    
    private String desc_nomina;
    private String descripcion;
    private Integer estatus;

    public Cat_Tipo_NominaDTO() {
        super();
    }

    public Cat_Tipo_NominaDTO(Integer id_tipo_nomina, String desc_nomina, String descripcion, Integer estatus) {
        this.id_tipo_nomina = id_tipo_nomina;
        this.desc_nomina = desc_nomina;
        this.descripcion = descripcion;
        this.estatus = estatus;
    }

    public Integer getId_tipo_nomina() {
        return id_tipo_nomina;
    }

    public void setId_tipo_nomina(Integer id_tipo_nomina) {
        this.id_tipo_nomina = id_tipo_nomina;
    }

    public String getDesc_nomina() {
        return desc_nomina;
    }

    public void setDesc_nomina(String desc_nomina) {
        this.desc_nomina = desc_nomina;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
    
}
