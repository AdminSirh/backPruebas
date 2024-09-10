/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author akalvarez19
 */
@Entity
@Table(name = "ayuda")
public class Ayuda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ayuda;

    @CreatedDate
    private LocalDate fecha_recepcion;
    @CreatedDate
    private LocalDate fecha_emision;

    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private Integer incidencia_id;
    private String origen;
    private Integer periodo_pago_id;
    private Integer parentesco_id;
    private Integer estatus;
    private Integer trabajador_id;
    private Double monto;
    private Integer ayuda_dias_id;
    @CreatedDate
    private LocalDate fecha_evento;

    public Ayuda() {
        super();
    }

    public Ayuda(Integer id_ayuda, LocalDate fecha_recepcion, LocalDate fecha_emision, String nombre, String apellido_paterno, String apellido_materno, Integer incidencia_id, String origen, Integer periodo_pago_id, Integer parentesco_id, Integer estatus, Integer trabajador_id, Double monto, Integer ayuda_dias_id, LocalDate fecha_evento) {
        this.id_ayuda = id_ayuda;
        this.fecha_recepcion = fecha_recepcion;
        this.fecha_emision = fecha_emision;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.incidencia_id = incidencia_id;
        this.origen = origen;
        this.periodo_pago_id = periodo_pago_id;
        this.parentesco_id = parentesco_id;
        this.estatus = estatus;
        this.trabajador_id = trabajador_id;
        this.monto = monto;
        this.ayuda_dias_id = ayuda_dias_id;
        this.fecha_evento = fecha_evento;
    }

    public Integer getId_ayuda() {
        return id_ayuda;
    }

    public void setId_ayuda(Integer id_ayuda) {
        this.id_ayuda = id_ayuda;
    }

    public LocalDate getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(LocalDate fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public LocalDate getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(LocalDate fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public Integer getIncidencia_id() {
        return incidencia_id;
    }

    public void setIncidencia_id(Integer incidencia_id) {
        this.incidencia_id = incidencia_id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Integer getPeriodo_pago_id() {
        return periodo_pago_id;
    }

    public void setPeriodo_pago_id(Integer periodo_pago_id) {
        this.periodo_pago_id = periodo_pago_id;
    }

    public Integer getParentesco_id() {
        return parentesco_id;
    }

    public void setParentesco_id(Integer parentesco_id) {
        this.parentesco_id = parentesco_id;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Integer getAyuda_dias_id() {
        return ayuda_dias_id;
    }

    public void setAyuda_dias_id(Integer ayuda_dias_id) {
        this.ayuda_dias_id = ayuda_dias_id;
    }

    public LocalDate getFecha_evento() {
        return fecha_evento;
    }

    public void setFecha_evento(LocalDate fecha_evento) {
        this.fecha_evento = fecha_evento;
    }

    

}
