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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author rroscero23
 */
@Entity
@Table(name = "catalogo_bimestres_sdi")
public class Cat_Bimestres_Sdi implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_bimestre;
    private String bimestre;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private Integer estatus;

    public Cat_Bimestres_Sdi() {
        super();
    }

    public Cat_Bimestres_Sdi(Integer id_bimestre, String bimestre, LocalDate fecha_inicio, LocalDate fecha_fin, Integer estatus) {
        this.id_bimestre = id_bimestre;
        this.bimestre = bimestre;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.estatus = estatus;
    }

    public Integer getId_bimestre() {
        return id_bimestre;
    }

    public void setId_bimestre(Integer id_bimestre) {
        this.id_bimestre = id_bimestre;
    }

    public String getBimestre() {
        return bimestre;
    }

    public void setBimestre(String bimestre) {
        this.bimestre = bimestre;
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

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    

}
