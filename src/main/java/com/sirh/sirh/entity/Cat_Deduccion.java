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
@Table(name = "catalogo_deduccion")
public class Cat_Deduccion implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_deduccion;
    private String concepto;
    private Integer tipo_dato_id;
    private String observaciones;
    private Integer en_reporte;
    private Integer en_descuentos_programados;

    public Cat_Deduccion() {
    }

    public Cat_Deduccion(Integer id_deduccion, String concepto, Integer tipo_dato_id, String observaciones, Integer en_reporte, Integer en_descuentos_programados) {
        this.id_deduccion = id_deduccion;
        this.concepto = concepto;
        this.tipo_dato_id = tipo_dato_id;
        this.observaciones = observaciones;
        this.en_reporte = en_reporte;
        this.en_descuentos_programados = en_descuentos_programados;
    }

    public Integer getId_deduccion() {
        return id_deduccion;
    }

    public void setId_deduccion(Integer id_deduccion) {
        this.id_deduccion = id_deduccion;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getTipo_dato_id() {
        return tipo_dato_id;
    }

    public void setTipo_dato_id(Integer tipo_dato_id) {
        this.tipo_dato_id = tipo_dato_id;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getEn_reporte() {
        return en_reporte;
    }

    public void setEn_reporte(Integer en_reporte) {
        this.en_reporte = en_reporte;
    }

    public Integer getEn_descuentos_programados() {
        return en_descuentos_programados;
    }

    public void setEn_descuentos_programados(Integer en_descuentos_programados) {
        this.en_descuentos_programados = en_descuentos_programados;
    }

    
}
