/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author rroscero23
 */
public class Incidencias_RA_TransportacionDTO {

    private String inicial_descripcion;
    private String descripcion;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private Integer num_dias;
    private String observaciones;

    public Incidencias_RA_TransportacionDTO() {
    }

    public Incidencias_RA_TransportacionDTO(String inicial_descripcion, String descripcion, LocalDate fecha_inicio, LocalDate fecha_fin, Integer num_dias, String observaciones) {
        this.inicial_descripcion = inicial_descripcion;
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.num_dias = num_dias;
        this.observaciones = observaciones;
    }

    public String getInicial_descripcion() {
        return inicial_descripcion;
    }

    public void setInicial_descripcion(String inicial_descripcion) {
        this.inicial_descripcion = inicial_descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
}
