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
 * @author rrosero23
 */
@Entity
@Table(name = "catalogo_horario")
public class Cat_Horario implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_horario; 
    private Integer estatus;
    private String ca;
    private String horas_acumuladas;
    private Integer lunes;
    private Integer martes;
    private Integer miercoles;
    private Integer jueves; 
    private Integer viernes; 
    private Integer sabado;
    private Integer domingo; 

    public Integer getId_horario() {
        return id_horario;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public String getCa() {
        return ca;
    }

    public String getHoras_acumuladas() {
        return horas_acumuladas;
    }

    public Integer getLunes() {
        return lunes;
    }

    public Integer getMartes() {
        return martes;
    }

    public Integer getMiercoles() {
        return miercoles;
    }

    public Integer getJueves() {
        return jueves;
    }

    public Integer getViernes() {
        return viernes;
    }

    public Integer getSabado() {
        return sabado;
    }

    public Integer getDomingo() {
        return domingo;
    }

    public void setId_horario(Integer id_horario) {
        this.id_horario = id_horario;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public void setHoras_acumuladas(String horas_acumuladas) {
        this.horas_acumuladas = horas_acumuladas;
    }

    public void setLunes(Integer lunes) {
        this.lunes = lunes;
    }

    public void setMartes(Integer martes) {
        this.martes = martes;
    }

    public void setMiercoles(Integer miercoles) {
        this.miercoles = miercoles;
    }

    public void setJueves(Integer jueves) {
        this.jueves = jueves;
    }

    public void setViernes(Integer viernes) {
        this.viernes = viernes;
    }

    public void setSabado(Integer sabado) {
        this.sabado = sabado;
    }

    public void setDomingo(Integer domingo) {
        this.domingo = domingo;
    }
}
