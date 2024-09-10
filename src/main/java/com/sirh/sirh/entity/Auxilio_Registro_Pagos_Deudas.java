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
@Table(name = "auxilio_registro_pagos_deudas")
public class Auxilio_Registro_Pagos_Deudas implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_auxilio_registro_pago;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "auxilio_cuentas_cobrar_id")
    private Auxilio_Cuentas_Por_Cobrar cuentas_Por_Cobrar;

    private Double monto_pago;
    private Integer trabajador_id;
    private Integer periodo_pago;
    private Double saldo_deuda;
    private Integer estatus;
    private String justificacion;

    public Auxilio_Registro_Pagos_Deudas() {
        super();
    }

    public Auxilio_Registro_Pagos_Deudas(Integer id_auxilio_registro_pago, Auxilio_Cuentas_Por_Cobrar cuentas_Por_Cobrar, Double monto_pago, Integer trabajador_id, Integer periodo_pago, Double saldo_deuda, Integer estatus, String justificacion) {
        this.id_auxilio_registro_pago = id_auxilio_registro_pago;
        this.cuentas_Por_Cobrar = cuentas_Por_Cobrar;
        this.monto_pago = monto_pago;
        this.trabajador_id = trabajador_id;
        this.periodo_pago = periodo_pago;
        this.saldo_deuda = saldo_deuda;
        this.estatus = estatus;
        this.justificacion = justificacion;
    }

    public Integer getId_auxilio_registro_pago() {
        return id_auxilio_registro_pago;
    }

    public void setId_auxilio_registro_pago(Integer id_auxilio_registro_pago) {
        this.id_auxilio_registro_pago = id_auxilio_registro_pago;
    }

    public Auxilio_Cuentas_Por_Cobrar getCuentas_Por_Cobrar() {
        return cuentas_Por_Cobrar;
    }

    public void setCuentas_Por_Cobrar(Auxilio_Cuentas_Por_Cobrar cuentas_Por_Cobrar) {
        this.cuentas_Por_Cobrar = cuentas_Por_Cobrar;
    }

    public Double getMonto_pago() {
        return monto_pago;
    }

    public void setMonto_pago(Double monto_pago) {
        this.monto_pago = monto_pago;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getPeriodo_pago() {
        return periodo_pago;
    }

    public void setPeriodo_pago(Integer periodo_pago) {
        this.periodo_pago = periodo_pago;
    }

    public Double getSaldo_deuda() {
        return saldo_deuda;
    }

    public void setSaldo_deuda(Double saldo_deuda) {
        this.saldo_deuda = saldo_deuda;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
    
    
    
}
