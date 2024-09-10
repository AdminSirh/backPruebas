/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
@Table(name = "registro_plazas_consolidado")
public class Registro_Plazas_Consolidado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_consolidado;
    private Integer anio;
    private Integer mes;
    private String tipo;
    private Integer puesto_id;
    private Integer nivel;
    private Long plazas_autorizadas;
    private LocalDate fecha_movimiento;
    private Integer estatus;

    public Registro_Plazas_Consolidado() {
        super();
    }

    public Registro_Plazas_Consolidado(Integer id_consolidado, Integer anio, Integer mes, String tipo, Integer puesto_id, Integer nivel, Long plazas_autorizadas, LocalDate fecha_movimiento, Integer estatus) {
        this.id_consolidado = id_consolidado;
        this.anio = anio;
        this.mes = mes;
        this.tipo = tipo;
        this.puesto_id = puesto_id;
        this.nivel = nivel;
        this.plazas_autorizadas = plazas_autorizadas;
        this.fecha_movimiento = fecha_movimiento;
        this.estatus = estatus;
    }

    public Integer getId_consolidado() {
        return id_consolidado;
    }

    public void setId_consolidado(Integer id_consolidado) {
        this.id_consolidado = id_consolidado;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getPuesto_id() {
        return puesto_id;
    }

    public void setPuesto_id(Integer puesto_id) {
        this.puesto_id = puesto_id;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Long getPlazas_autorizadas() {
        return plazas_autorizadas;
    }

    public void setPlazas_autorizadas(Long plazas_autorizadas) {
        this.plazas_autorizadas = plazas_autorizadas;
    }

    public LocalDate getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDate fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

   

}
