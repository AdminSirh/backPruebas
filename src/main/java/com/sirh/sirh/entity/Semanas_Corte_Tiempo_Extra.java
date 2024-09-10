/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author rroscero23
 */
@Entity
@Table(name = "semanas_corte_tiempo_extra")
public class Semanas_Corte_Tiempo_Extra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_semana_corte;

    private Integer anio;

    private Integer mes;

    private Integer semana;

    private LocalDate fecha_inicial;

    private LocalDate fecha_final;

    private Integer periodo_aplicacion;

    public Semanas_Corte_Tiempo_Extra() {
    }

    public Semanas_Corte_Tiempo_Extra(Integer id_semana_corte, Integer anio, Integer mes, Integer semana, LocalDate fecha_inicial, LocalDate fecha_final, Integer periodo_aplicacion) {
        this.id_semana_corte = id_semana_corte;
        this.anio = anio;
        this.mes = mes;
        this.semana = semana;
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
        this.periodo_aplicacion = periodo_aplicacion;
    }

    public Integer getId_semana_corte() {
        return id_semana_corte;
    }

    public void setId_semana_corte(Integer id_semana_corte) {
        this.id_semana_corte = id_semana_corte;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
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

    public Integer getPeriodo_aplicacion() {
        return periodo_aplicacion;
    }

    public void setPeriodo_aplicacion(Integer periodo_aplicacion) {
        this.periodo_aplicacion = periodo_aplicacion;
    }

   

}
