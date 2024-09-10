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
@Table (name = "pagos_calculos_percepcion_3")
public class Pagos_Calculos_Percepcion_3 implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_calculo_percepcion_3;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pago_3_id")
    private Pagos_3 pagos_3;
    private Integer periodo_generacion;
    private Double monto;
    private Double gravable;
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cat_incidencia_id")
    private Cat_Incidencias cat_Incidencias;

    public Pagos_Calculos_Percepcion_3() {
        super();
    }

    public Pagos_Calculos_Percepcion_3(Integer id_calculo_percepcion_3, Pagos_3 pagos_3, Integer periodo_generacion, Double monto, Double gravable, Cat_Incidencias cat_Incidencias) {
        this.id_calculo_percepcion_3 = id_calculo_percepcion_3;
        this.pagos_3 = pagos_3;
        this.periodo_generacion = periodo_generacion;
        this.monto = monto;
        this.gravable = gravable;
        this.cat_Incidencias = cat_Incidencias;
    }

    public Integer getId_calculo_percepcion_3() {
        return id_calculo_percepcion_3;
    }

    public void setId_calculo_percepcion_3(Integer id_calculo_percepcion_3) {
        this.id_calculo_percepcion_3 = id_calculo_percepcion_3;
    }

    public Pagos_3 getPagos_3() {
        return pagos_3;
    }

    public void setPagos_3(Pagos_3 pagos_3) {
        this.pagos_3 = pagos_3;
    }

    public Integer getPeriodo_generacion() {
        return periodo_generacion;
    }

    public void setPeriodo_generacion(Integer periodo_generacion) {
        this.periodo_generacion = periodo_generacion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getGravable() {
        return gravable;
    }

    public void setGravable(Double gravable) {
        this.gravable = gravable;
    }

    public Cat_Incidencias getCat_Incidencias() {
        return cat_Incidencias;
    }

    public void setCat_Incidencias(Cat_Incidencias cat_Incidencias) {
        this.cat_Incidencias = cat_Incidencias;
    }

    
}
