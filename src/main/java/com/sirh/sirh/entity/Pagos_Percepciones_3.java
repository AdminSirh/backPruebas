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
@Table(name = "pagos_percepciones_3")
public class Pagos_Percepciones_3 implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pago_percepcion_3;
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cat_incidencia_id")
    private Cat_Incidencias cat_Incidencias;
    
    private Integer periodo_generacion;
    private Double valor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pago_3_id")
    private Pagos_3 pagos_3;
    
    public Pagos_Percepciones_3() {
        super();
    }

    public Pagos_Percepciones_3(Integer id_pago_percepcion_3, Cat_Incidencias cat_Incidencias, Integer periodo_generacion, Double valor, Pagos_3 pagos_3) {
        this.id_pago_percepcion_3 = id_pago_percepcion_3;
        this.cat_Incidencias = cat_Incidencias;
        this.periodo_generacion = periodo_generacion;
        this.valor = valor;
        this.pagos_3 = pagos_3;
    }

    public Integer getId_pago_percepcion_3() {
        return id_pago_percepcion_3;
    }

    public void setId_pago_percepcion_3(Integer id_pago_percepcion_3) {
        this.id_pago_percepcion_3 = id_pago_percepcion_3;
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

    public Pagos_3 getPagos_3() {
        return pagos_3;
    }

    public void setPagos_3(Pagos_3 pagos_3) {
        this.pagos_3 = pagos_3;
    }

    
}
