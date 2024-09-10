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
@Table (name = "pagos_calculos_deduccion_2")
public class Pagos_Calculos_Deduccion_2 implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_calculo_deduccion_2;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pago_2_id")
    private Pagos_2 pagos_2;
    private Integer periodo_generacion;
    private Double monto;
    private Double gravable;
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cat_incidencia_id")
    private Cat_Incidencias cat_Incidencias;

    public Pagos_Calculos_Deduccion_2() {
        super();
    }

    public Pagos_Calculos_Deduccion_2(Integer id_calculo_deduccion_2, Pagos_2 pagos_2, Integer periodo_generacion, Double monto, Double gravable, Cat_Incidencias cat_Incidencias) {
        this.id_calculo_deduccion_2 = id_calculo_deduccion_2;
        this.pagos_2 = pagos_2;
        this.periodo_generacion = periodo_generacion;
        this.monto = monto;
        this.gravable = gravable;
        this.cat_Incidencias = cat_Incidencias;
    }

    public Integer getId_calculo_deduccion_2() {
        return id_calculo_deduccion_2;
    }

    public void setId_calculo_deduccion_2(Integer id_calculo_deduccion_2) {
        this.id_calculo_deduccion_2 = id_calculo_deduccion_2;
    }

    public Pagos_2 getPagos_2() {
        return pagos_2;
    }

    public void setPagos_2(Pagos_2 pagos_2) {
        this.pagos_2 = pagos_2;
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
