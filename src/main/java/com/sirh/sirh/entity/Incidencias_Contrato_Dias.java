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
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author oguerrero23
 */


@Entity
@Table(name = "incidencias_contrato_dias")
public class Incidencias_Contrato_Dias implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_incidencia_contrato_dia;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incidencias_id")
    private Cat_Incidencias cat_incidencias;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contrato_id")
    private Cat_Tipo_Contrato cat_Tipo_Contrato;
    
    private Integer dias;

    public Incidencias_Contrato_Dias(Integer id_incidencia_contrato_dia, Cat_Incidencias cat_incidencias, Cat_Tipo_Contrato cat_Tipo_Contrato, Integer dias) {
        this.id_incidencia_contrato_dia = id_incidencia_contrato_dia;
        this.cat_incidencias = cat_incidencias;
        this.cat_Tipo_Contrato = cat_Tipo_Contrato;
        this.dias = dias;
    }

    public Incidencias_Contrato_Dias() {
        super();
    }

    
    
    public Integer getId_incidencia_contrato_dia() {
        return id_incidencia_contrato_dia;
    }

    public void setId_incidencia_contrato_dia(Integer id_incidencia_contrato_dia) {
        this.id_incidencia_contrato_dia = id_incidencia_contrato_dia;
    }

    public Cat_Incidencias getCat_incidencias() {
        return cat_incidencias;
    }

    public void setCat_incidencias(Cat_Incidencias cat_incidencias) {
        this.cat_incidencias = cat_incidencias;
    }

    public Cat_Tipo_Contrato getCat_Tipo_Contrato() {
        return cat_Tipo_Contrato;
    }

    public void setCat_Tipo_Contrato(Cat_Tipo_Contrato cat_Tipo_Contrato) {
        this.cat_Tipo_Contrato = cat_Tipo_Contrato;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }
    
    
    
    
}
