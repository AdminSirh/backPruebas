/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
@Table(name = "registro_solicitudes_fonacot")
public class Registro_Solicitudes_Fonacot implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_solicitud;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trabajador_id")
    private Trabajador trabajador;
    
    private Integer estatus;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_movimiento;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_captura;

    public Registro_Solicitudes_Fonacot() {
        super();
    }

    public Registro_Solicitudes_Fonacot(Integer id_solicitud, Trabajador trabajador, Integer estatus, LocalDate fecha_movimiento, LocalDate fecha_captura) {
        this.id_solicitud = id_solicitud;
        this.trabajador = trabajador;
        this.estatus = estatus;
        this.fecha_movimiento = fecha_movimiento;
        this.fecha_captura = fecha_captura;
    }

    public Integer getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(Integer id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public LocalDate getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDate fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public LocalDate getFecha_captura() {
        return fecha_captura;
    }

    public void setFecha_captura(LocalDate fecha_captura) {
        this.fecha_captura = fecha_captura;
    }

    
}
