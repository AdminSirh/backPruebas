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
@Table (name = "catalogo_situacion_plaza")
public class Cat_Situacion_Plaza implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_situacion;
    private String codigo_situacion;
    private String situacion;
    private Integer estatus;

    public Cat_Situacion_Plaza() {
        super();
    }

    public Cat_Situacion_Plaza(Integer id_situacion, String codigo_situacion, String situacion, Integer estatus) {
        this.id_situacion = id_situacion;
        this.codigo_situacion = codigo_situacion;
        this.situacion = situacion;
        this.estatus = estatus;
    }

    public Integer getId_situacion() {
        return id_situacion;
    }

    public void setId_situacion(Integer id_situacion) {
        this.id_situacion = id_situacion;
    }

    public String getCodigo_situacion() {
        return codigo_situacion;
    }

    public void setCodigo_situacion(String codigo_situacion) {
        this.codigo_situacion = codigo_situacion;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
    
}
