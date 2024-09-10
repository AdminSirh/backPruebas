/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sirh.sirh.entity.Cat_Incidencias;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author akalvarez19
 */
public class IncidenciasDTO {
    
    private Integer trabajador_id;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incidencia_id")
    private Cat_Incidencias cat_incidencias;
    
    
    @CreatedDate
    private LocalDate fecha_inicio;
    
    
    @CreatedDate
    private LocalDate fecha_fin;
    
    private Integer num_dias;
    
    private String observaciones;
    
    private String folio;
    
    private String bis;
    
    private Integer estatus;
    
    private Integer estado_incidencia_id;
    
    private Double corrida_vacaciones;
    
    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_incidencia_id")
    private Cat_Estado_Incidencia cat_estado_incidencia;*/

    public IncidenciasDTO() {
    }

    public IncidenciasDTO(Integer trabajador_id, Cat_Incidencias cat_incidencias, LocalDate fecha_inicio, LocalDate fecha_fin, Integer num_dias, String observaciones, String folio, String bis, Integer estatus, Integer estado_incidencia_id, Double corrida_vacaciones) {
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
