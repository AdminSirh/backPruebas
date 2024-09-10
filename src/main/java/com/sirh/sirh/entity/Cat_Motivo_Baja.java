/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author rrosero23
 */
@Entity
@Table (name = "catalogo_motivo_baja")
public class Cat_Motivo_Baja implements Serializable {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    
    @Column(nullable = true)
    private Integer id_motivo_baja;
    
    private String descripcion; 
    
    private Integer estatus;

    public Integer getId_motivo_baja() {
        return id_motivo_baja;
    }

    public void setId_motivo_baja(Integer id_motivo_baja) {
        this.id_motivo_baja = id_motivo_baja;
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
