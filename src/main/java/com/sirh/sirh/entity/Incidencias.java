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
import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author akalvarez19
 */
@Entity
@Table(name = "incidencias")
public class Incidencias implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_incidencia; 
    
    private Integer trabajador_id;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incidencia_id")
    private Cat_Incidencias cat_incidencias;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    private LocalDate fecha_inicio;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    private LocalDate fecha_fin;
       
    private Integer num_dias;
    
    private String observaciones;
    
    private String folio;
    
    private String bis;
    
    private Integer estatus;
    
    private Integer estado_incidencia_id;
    
    private Double corrida_vacaciones;

    public Incidencias() {
    }

    public Incidencias(Integer id_incidencia, Integer trabajador_id, Cat_Incidencias cat_incidencias, LocalDate fecha_inicio, LocalDate fecha_fin, Integer num_dias, String observaciones, String folio, String bis, Integer estatus, Integer estado_incidencia_id, Double corrida_vacaciones) {
        this.id_incidencia = id_incidencia;
        this.trabajador_id = trabajador_id;
        this.cat_incidencias = cat_incidencias;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.num_dias = num_dias;
        this.observaciones = observaciones;
        this.folio = folio;
        this.bis = bis;
        this.estatus = estatus;
        this.estado_incidencia_id = estado_incidencia_id;
        this.corrida_vacaciones = corrida_vacaciones;
    }

    public Integer getId_incidencia() {
        return id_incidencia;
    }

    public void setId_incidencia(Integer id_incidencia) {
        this.id_incidencia = id_incidencia;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Cat_Incidencias getCat_incidencias() {
        return cat_incidencias;
    }

    public void setCat_incidencias(Cat_Incidencias cat_incidencias) {
        this.cat_incidencias = cat_incidencias;
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

    public Double getCorrida_vacaciones() {
        return corrida_vacaciones;
    }

    public void setCorrida_vacaciones(Double corrida_vacaciones) {
        this.corrida_vacaciones = corrida_vacaciones;
    }

    
}
