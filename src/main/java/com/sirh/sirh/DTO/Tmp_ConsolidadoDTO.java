/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author rroscero23
 */
public class Tmp_ConsolidadoDTO {

    private Integer anio;
    private Integer mes;
    private String tipo;
    private Integer puesto_id;
    private Integer nivel;
    private Long numero_plazas_autorizadas;
    private Date fecha_movimiento;
    private Integer estatus;

    public Tmp_ConsolidadoDTO() {
    }

    public Tmp_ConsolidadoDTO(Integer anio, Integer mes, String tipo, Integer puesto_id, Integer nivel, Long numero_plazas_autorizadas, Date fecha_movimiento, Integer estatus) {
        this.anio = anio;
        this.mes = mes;
        this.tipo = tipo;
        this.puesto_id = puesto_id;
        this.nivel = nivel;
        this.numero_plazas_autorizadas = numero_plazas_autorizadas;
        this.fecha_movimiento = fecha_movimiento;
        this.estatus = estatus;
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

    public Long getNumero_plazas_autorizadas() {
        return numero_plazas_autorizadas;
    }

    public void setNumero_plazas_autorizadas(Long numero_plazas_autorizadas) {
        this.numero_plazas_autorizadas = numero_plazas_autorizadas;
    }

    public Date getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(Date fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

}
