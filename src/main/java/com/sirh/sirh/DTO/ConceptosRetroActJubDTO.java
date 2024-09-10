/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rroscero23
 */
public class ConceptosRetroActJubDTO {

    private Integer trabajador_id;
    private Integer cat_incidencia_id;
    private Integer periodo_aplicacion;
    private Double valor;
    private Double sueldo_mensual;
    private Integer id_puesto;

    public ConceptosRetroActJubDTO() {
    }

    public ConceptosRetroActJubDTO(Integer trabajador_id, Integer cat_incidencia_id, Integer periodo_aplicacion, Double valor, Double sueldo_mensual, Integer id_puesto) {
        this.trabajador_id = trabajador_id;
        this.cat_incidencia_id = cat_incidencia_id;
        this.periodo_aplicacion = periodo_aplicacion;
        this.valor = valor;
        this.sueldo_mensual = sueldo_mensual;
        this.id_puesto = id_puesto;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getCat_incidencia_id() {
        return cat_incidencia_id;
    }

    public void setCat_incidencia_id(Integer cat_incidencia_id) {
        this.cat_incidencia_id = cat_incidencia_id;
    }

    public Integer getPeriodo_aplicacion() {
        return periodo_aplicacion;
    }

    public void setPeriodo_aplicacion(Integer periodo_aplicacion) {
        this.periodo_aplicacion = periodo_aplicacion;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getSueldo_mensual() {
        return sueldo_mensual;
    }

    public void setSueldo_mensual(Double sueldo_mensual) {
        this.sueldo_mensual = sueldo_mensual;
    }

    public Integer getId_puesto() {
        return id_puesto;
    }

    public void setId_puesto(Integer id_puesto) {
        this.id_puesto = id_puesto;
    }

}
