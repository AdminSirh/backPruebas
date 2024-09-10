/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

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
import javax.persistence.Temporal;

/**
 *
 * @author rroscero23
 */
@Entity
@Table(name = "captura_semanal_ra_10")
public class Captura_Semanal_RA10 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ra_10;
    private LocalDate fecha_inicial;
    private LocalDate fecha_final;
    private Integer periodo_generacion;
    private Integer periodo_aplicacion;
    private Boolean martes;
    private Boolean miercoles;
    private Boolean jueves;
    private Boolean viernes;
    private Boolean sabado;
    private Boolean domingo;
    private Boolean lunes;
    private Boolean administrativo;
    private Boolean operativo;
    private Double total_semana;
    private String comentario;
    private Integer trabajador_id;
    private Integer trabajador_ausente_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "puesto_cubrir_id")
    private Cat_Puesto cat_Puesto;

    private Integer motivo_id;
    private Integer estatus;

    public Captura_Semanal_RA10() {
        super();
    }

    public Captura_Semanal_RA10(Integer id_ra_10, LocalDate fecha_inicial, LocalDate fecha_final, Integer periodo_generacion, Integer periodo_aplicacion, Boolean martes, Boolean miercoles, Boolean jueves, Boolean viernes, Boolean sabado, Boolean domingo, Boolean lunes, Boolean administrativo, Boolean operativo, Double total_semana, String comentario, Integer trabajador_id, Integer trabajador_ausente_id, Cat_Puesto cat_Puesto, Integer motivo_id, Integer estatus) {
        this.id_ra_10 = id_ra_10;
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
        this.periodo_generacion = periodo_generacion;
        this.periodo_aplicacion = periodo_aplicacion;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
        this.domingo = domingo;
        this.lunes = lunes;
        this.administrativo = administrativo;
        this.operativo = operativo;
        this.total_semana = total_semana;
        this.comentario = comentario;
        this.trabajador_id = trabajador_id;
        this.trabajador_ausente_id = trabajador_ausente_id;
        this.cat_Puesto = cat_Puesto;
        this.motivo_id = motivo_id;
        this.estatus = estatus;
    }

    public Integer getId_ra_10() {
        return id_ra_10;
    }

    public void setId_ra_10(Integer id_ra_10) {
        this.id_ra_10 = id_ra_10;
    }

    public LocalDate getFecha_inicial() {
        return fecha_inicial;
    }

    public void setFecha_inicial(LocalDate fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    public LocalDate getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(LocalDate fecha_final) {
        this.fecha_final = fecha_final;
    }

    public Integer getPeriodo_generacion() {
        return periodo_generacion;
    }

    public void setPeriodo_generacion(Integer periodo_generacion) {
        this.periodo_generacion = periodo_generacion;
    }

    public Integer getPeriodo_aplicacion() {
        return periodo_aplicacion;
    }

    public void setPeriodo_aplicacion(Integer periodo_aplicacion) {
        this.periodo_aplicacion = periodo_aplicacion;
    }

    public Boolean getMartes() {
        return martes;
    }

    public void setMartes(Boolean martes) {
        this.martes = martes;
    }

    public Boolean getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(Boolean miercoles) {
        this.miercoles = miercoles;
    }

    public Boolean getJueves() {
        return jueves;
    }

    public void setJueves(Boolean jueves) {
        this.jueves = jueves;
    }

    public Boolean getViernes() {
        return viernes;
    }

    public void setViernes(Boolean viernes) {
        this.viernes = viernes;
    }

    public Boolean getSabado() {
        return sabado;
    }

    public void setSabado(Boolean sabado) {
        this.sabado = sabado;
    }

    public Boolean getDomingo() {
        return domingo;
    }

    public void setDomingo(Boolean domingo) {
        this.domingo = domingo;
    }

    public Boolean getLunes() {
        return lunes;
    }

    public void setLunes(Boolean lunes) {
        this.lunes = lunes;
    }

    public Boolean getAdministrativo() {
        return administrativo;
    }

    public void setAdministrativo(Boolean administrativo) {
        this.administrativo = administrativo;
    }

    public Boolean getOperativo() {
        return operativo;
    }

    public void setOperativo(Boolean operativo) {
        this.operativo = operativo;
    }

    public Double getTotal_semana() {
        return total_semana;
    }

    public void setTotal_semana(Double total_semana) {
        this.total_semana = total_semana;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getTrabajador_ausente_id() {
        return trabajador_ausente_id;
    }

    public void setTrabajador_ausente_id(Integer trabajador_ausente_id) {
        this.trabajador_ausente_id = trabajador_ausente_id;
    }

    public Cat_Puesto getCat_Puesto() {
        return cat_Puesto;
    }

    public void setCat_Puesto(Cat_Puesto cat_Puesto) {
        this.cat_Puesto = cat_Puesto;
    }

    public Integer getMotivo_id() {
        return motivo_id;
    }

    public void setMotivo_id(Integer motivo_id) {
        this.motivo_id = motivo_id;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    

}
