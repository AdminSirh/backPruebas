/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author oguerrero23
 */

@Entity
@Table(name = "catalogo_percepcion")
public class Cat_Percepcion implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_percepcion;
    private String concepto;
    private Integer tipo_dato_id;
    private Double limite;
    private String observaciones;
    private Integer en_reporte;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nomina_id")
    private Cat_Tipo_Nomina cat_Tipo_Nomina;

    public Cat_Percepcion() {
        super();
    }

    public Cat_Percepcion(Integer id_percepcion, String concepto, Integer tipo_dato_id, Double limite, String observaciones, Integer en_reporte, Cat_Tipo_Nomina cat_Tipo_Nomina) {
        this.id_percepcion = id_percepcion;
        this.concepto = concepto;
        this.tipo_dato_id = tipo_dato_id;
        this.limite = limite;
        this.observaciones = observaciones;
        this.en_reporte = en_reporte;
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
    }

    public Integer getId_percepcion() {
        return id_percepcion;
    }

    public void setId_percepcion(Integer id_percepcion) {
        this.id_percepcion = id_percepcion;
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

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
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

    public Cat_Tipo_Nomina getCat_Tipo_Nomina() {
        return cat_Tipo_Nomina;
    }

    public void setCat_Tipo_Nomina(Cat_Tipo_Nomina cat_Tipo_Nomina) {
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
    }
    
    
}
