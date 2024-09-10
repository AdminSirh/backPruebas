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
@Table(name = "pagos_deducciones_5")
public class Pagos_Deducciones_5 implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pago_deduccion_5;
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cat_incidencia_id")
    private Cat_Incidencias cat_Incidencias;
    
    private Integer periodo_generacion;
    private Double valor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pago_5_id")
    private Pagos_5 pagos_5;
    
    public Pagos_Deducciones_5() {
        super();
    }

    public Pagos_Deducciones_5(Integer id_pago_deduccion_5, Cat_Incidencias cat_Incidencias, Integer periodo_generacion, Double valor, Pagos_5 pagos_5) {
        this.id_pago_deduccion_5 = id_pago_deduccion_5;
        this.cat_Incidencias = cat_Incidencias;
        this.periodo_generacion = periodo_generacion;
        this.valor = valor;
        this.pagos_5 = pagos_5;
    }

    public Integer getId_pago_deduccion_5() {
        return id_pago_deduccion_5;
    }

    public void setId_pago_deduccion_5(Integer id_pago_deduccion_5) {
        this.id_pago_deduccion_5 = id_pago_deduccion_5;
    }

    public Cat_Incidencias getCat_Incidencias() {
        return cat_Incidencias;
    }

    public void setCat_Incidencias(Cat_Incidencias cat_Incidencias) {
        this.cat_Incidencias = cat_Incidencias;
    }

    public Integer getPeriodo_generacion() {
        return periodo_generacion;
    }

    public void setPeriodo_generacion(Integer periodo_generacion) {
        this.periodo_generacion = periodo_generacion;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Pagos_5 getPagos_5() {
        return pagos_5;
    }

    public void setPagos_5(Pagos_5 pagos_5) {
        this.pagos_5 = pagos_5;
    }

    
}
