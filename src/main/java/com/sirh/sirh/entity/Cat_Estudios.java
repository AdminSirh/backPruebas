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
 * @author nreyes22
 */
@Entity
@Table(name = "catalogo_estudios")
public class Cat_Estudios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_grado;

    private String desc_grado;
    private Integer estatus;

    
    
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
