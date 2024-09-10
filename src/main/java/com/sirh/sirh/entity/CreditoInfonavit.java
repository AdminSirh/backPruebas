/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
 * @author rrosero23
 */
@Entity
@Table(name = "credito_infonavit")
public class CreditoInfonavit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id_credito_infonavit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trabajador_id")
    private Trabajador trabajador;

    private String numero_de_credito;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_credito_id")
    private Cat_Tipo_Credito cat_tipo_credito;

    private String descripcion_tipo_credito;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_de_recepcion;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_de_aplicacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estatus_credito_id")
    private Cat_Estatus_Credito cat_Estatus_Credito;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_movimiento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motivo_baja_id")
    private Cat_Motivo_Baja cat_Motivo_Baja;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_evento;

    public CreditoInfonavit() {
    }

    public CreditoInfonavit(Integer id_credito_infonavit, Trabajador trabajador, String numero_de_credito, Cat_Tipo_Credito cat_tipo_credito, String descripcion_tipo_credito, LocalDate fecha_de_recepcion, LocalDate fecha_de_aplicacion, Cat_Estatus_Credito cat_Estatus_Credito, LocalDate fecha_movimiento, Cat_Motivo_Baja cat_Motivo_Baja, LocalDate fecha_evento) {
        this.id_credito_infonavit = id_credito_infonavit;
        this.trabajador = trabajador;
        this.numero_de_credito = numero_de_credito;
        this.cat_tipo_credito = cat_tipo_credito;
        this.descripcion_tipo_credito = descripcion_tipo_credito;
        this.fecha_de_recepcion = fecha_de_recepcion;
        this.fecha_de_aplicacion = fecha_de_aplicacion;
        this.cat_Estatus_Credito = cat_Estatus_Credito;
        this.fecha_movimiento = fecha_movimiento;
        this.cat_Motivo_Baja = cat_Motivo_Baja;
        this.fecha_evento = fecha_evento;
    }

    public Integer getId_credito_infonavit() {
        return id_credito_infonavit;
    }

    public void setId_credito_infonavit(Integer id_credito_infonavit) {
        this.id_credito_infonavit = id_credito_infonavit;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public String getNumero_de_credito() {
        return numero_de_credito;
    }

    public void setNumero_de_credito(String numero_de_credito) {
        this.numero_de_credito = numero_de_credito;
    }

    public Cat_Tipo_Credito getCat_tipo_credito() {
        return cat_tipo_credito;
    }

    public void setCat_tipo_credito(Cat_Tipo_Credito cat_tipo_credito) {
        this.cat_tipo_credito = cat_tipo_credito;
    }

    public String getDescripcion_tipo_credito() {
        return descripcion_tipo_credito;
    }

    public void setDescripcion_tipo_credito(String descripcion_tipo_credito) {
        this.descripcion_tipo_credito = descripcion_tipo_credito;
    }

    public LocalDate getFecha_de_recepcion() {
        return fecha_de_recepcion;
    }

    public void setFecha_de_recepcion(LocalDate fecha_de_recepcion) {
        this.fecha_de_recepcion = fecha_de_recepcion;
    }

    public LocalDate getFecha_de_aplicacion() {
        return fecha_de_aplicacion;
    }

    public void setFecha_de_aplicacion(LocalDate fecha_de_aplicacion) {
        this.fecha_de_aplicacion = fecha_de_aplicacion;
    }

    public Cat_Estatus_Credito getCat_Estatus_Credito() {
        return cat_Estatus_Credito;
    }

    public void setCat_Estatus_Credito(Cat_Estatus_Credito cat_Estatus_Credito) {
        this.cat_Estatus_Credito = cat_Estatus_Credito;
    }

    public LocalDate getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDate fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public Cat_Motivo_Baja getCat_Motivo_Baja() {
        return cat_Motivo_Baja;
    }

    public void setCat_Motivo_Baja(Cat_Motivo_Baja cat_Motivo_Baja) {
        this.cat_Motivo_Baja = cat_Motivo_Baja;
    }

    public LocalDate getFecha_evento() {
        return fecha_evento;
    }

    public void setFecha_evento(LocalDate fecha_evento) {
        this.fecha_evento = fecha_evento;
    }

    
}
