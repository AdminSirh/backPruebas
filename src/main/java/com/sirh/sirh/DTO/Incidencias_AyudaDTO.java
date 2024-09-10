/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.sirh.sirh.entity.Cat_Incidencias;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author rroscero23
 */
public class Incidencias_AyudaDTO {

    //Atributos Tabla Incidencias
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private Cat_Incidencias cat_incidencias;
    private Integer trabajador_id;
    private Integer num_dias;
    private String observaciones;
    private String folio;
    private String bis;
    private Integer estatus;
    private Integer estado_incidencia_id;

    //Atributos Tabla Ayuda
    private LocalDate fecha_recepcion;
    private LocalDate fecha_emision;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private Integer incidencia_id;
    private String origen;
    private Integer periodo_pago_id;
    private Integer parentesco_id;
    private Double monto;

    public Incidencias_AyudaDTO() {
    }

    public Incidencias_AyudaDTO(LocalDate fecha_inicio, LocalDate fecha_fin, Cat_Incidencias cat_incidencias, Integer trabajador_id, Integer num_dias, String observaciones, String folio, String bis, Integer estatus, Integer estado_incidencia_id, LocalDate fecha_recepcion, LocalDate fecha_emision, String nombre, String apellido_paterno, String apellido_materno, Integer incidencia_id, String origen, Integer periodo_pago_id, Integer parentesco_id, Double monto) {
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.cat_incidencias = cat_incidencias;
        this.trabajador_id = trabajador_id;
        this.num_dias = num_dias;
        this.observaciones = observaciones;
        this.folio = folio;
        this.bis = bis;
        this.estatus = estatus;
        this.estado_incidencia_id = estado_incidencia_id;
        this.fecha_recepcion = fecha_recepcion;
        this.fecha_emision = fecha_emision;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.incidencia_id = incidencia_id;
        this.origen = origen;
        this.periodo_pago_id = periodo_pago_id;
        this.parentesco_id = parentesco_id;
        this.monto = monto;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Cat_Incidencias getCat_incidencias() {
        return cat_incidencias;
    }

    public void setCat_incidencias(Cat_Incidencias cat_incidencias) {
        this.cat_incidencias = cat_incidencias;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getNum_dias() {
        return num_dias;
    }

    public void setNum_dias(Integer num_dias) {
        this.num_dias = num_dias;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getBis() {
        return bis;
    }

    public void setBis(String bis) {
        this.bis = bis;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getEstado_incidencia_id() {
        return estado_incidencia_id;
    }

    public void setEstado_incidencia_id(Integer estado_incidencia_id) {
        this.estado_incidencia_id = estado_incidencia_id;
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

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    

}
