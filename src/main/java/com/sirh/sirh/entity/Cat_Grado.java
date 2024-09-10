/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

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

@Entity
@Table(name = "catalogo_grado")
public class Cat_Grado implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_grado;
    
    private String desc_grado;
    
    private Integer estatus;
    
    public Cat_Grado() {
        super();
    }

    
    public Cat_Grado(Integer id_grado, String desc_grado, Integer estatus) {
        this.id_grado = id_grado;
        this.desc_grado = desc_grado;
        this.estatus = estatus;
    }

    public Integer getId_grado() {
        return id_grado;
    }

    public void setId_grado(Integer id_grado) {
        this.id_grado = id_grado;
    }

    public String getDesc_grado() {
        return desc_grado;
    }

    public void setDesc_grado(String desc_grado) {
        this.desc_grado = desc_grado;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
    
    
}
