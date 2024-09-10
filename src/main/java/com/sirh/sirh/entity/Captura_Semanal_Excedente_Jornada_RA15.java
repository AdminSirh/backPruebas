/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
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
@Table(name = "captura_semanal_excedente_jornada_ra_15")
public class Captura_Semanal_Excedente_Jornada_RA15 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_excedente_jornada;
    private Double domingo;
    private Double lunes;
    private Double martes;
    private Double miercoles;
    private Double jueves;
    private Double viernes;
    private Double sabado;

    public Integer getId_excedente_jornada() {
        return id_excedente_jornada;
    }

    public void setId_excedente_jornada(Integer id_excedente_jornada) {
        this.id_excedente_jornada = id_excedente_jornada;
    }

    public Double getDomingo() {
        return domingo;
    }

    public void setDomingo(Double domingo) {
        this.domingo = domingo;
    }

    public Double getLunes() {
        return lunes;
    }

    public void setLunes(Double lunes) {
        this.lunes = lunes;
    }

    public Double getMartes() {
        return martes;
    }

    public void setMartes(Double martes) {
        this.martes = martes;
    }

    public Double getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(Double miercoles) {
        this.miercoles = miercoles;
    }

    public Double getJueves() {
        return jueves;
    }

    public void setJueves(Double jueves) {
        this.jueves = jueves;
    }

    public Double getViernes() {
        return viernes;
    }

    public void setViernes(Double viernes) {
        this.viernes = viernes;
    }

    public Double getSabado() {
        return sabado;
    }

    public void setSabado(Double sabado) {
        this.sabado = sabado;
    }

}
