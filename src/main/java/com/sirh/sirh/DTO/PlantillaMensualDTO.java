/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rroscero23
 */
public class PlantillaMensualDTO {

    private Integer anio;
    private Integer mes;
    private Integer trabajador_id;
    private Integer numero_plaza;
    private String tipo;
    private Integer nivel;
    private Integer codigo_puesto;
    private String puesto;
    private Double sueldo_mensual;
    private String codigo_area;
    private Integer puesto_id;
    private Integer idArea;
    private Integer tipo_contrato_id;
    private Integer tipo_nomina_id;
    private Integer prestaciones;
    private Double sueldo_mensual_bruto;

    public PlantillaMensualDTO() {
    }

    public PlantillaMensualDTO(Integer anio, Integer mes, Integer trabajador_id, Integer numero_plaza, String tipo, Integer nivel, Integer codigo_puesto, String puesto, Double sueldo_mensual, String codigo_area, Integer puesto_id, Integer idArea, Integer tipo_contrato_id, Integer tipo_nomina_id, Integer prestaciones, Double sueldo_mensual_bruto) {
        this.anio = anio;
        this.mes = mes;
        this.trabajador_id = trabajador_id;
        this.numero_plaza = numero_plaza;
        this.tipo = tipo;
        this.nivel = nivel;
        this.codigo_puesto = codigo_puesto;
        this.puesto = puesto;
        this.sueldo_mensual = sueldo_mensual;
        this.codigo_area = codigo_area;
        this.puesto_id = puesto_id;
        this.idArea = idArea;
        this.tipo_contrato_id = tipo_contrato_id;
        this.tipo_nomina_id = tipo_nomina_id;
        this.prestaciones = prestaciones;
        this.sueldo_mensual_bruto = sueldo_mensual_bruto;
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

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getNumero_plaza() {
        return numero_plaza;
    }

    public void setNumero_plaza(Integer numero_plaza) {
        this.numero_plaza = numero_plaza;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getCodigo_puesto() {
        return codigo_puesto;
    }

    public void setCodigo_puesto(Integer codigo_puesto) {
        this.codigo_puesto = codigo_puesto;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Double getSueldo_mensual() {
        return sueldo_mensual;
    }

    public void setSueldo_mensual(Double sueldo_mensual) {
        this.sueldo_mensual = sueldo_mensual;
    }

    public String getCodigo_area() {
        return codigo_area;
    }

    public void setCodigo_area(String codigo_area) {
        this.codigo_area = codigo_area;
    }

    public Integer getPuesto_id() {
        return puesto_id;
    }

    public void setPuesto_id(Integer puesto_id) {
        this.puesto_id = puesto_id;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getTipo_contrato_id() {
        return tipo_contrato_id;
    }

    public void setTipo_contrato_id(Integer tipo_contrato_id) {
        this.tipo_contrato_id = tipo_contrato_id;
    }

    public Integer getTipo_nomina_id() {
        return tipo_nomina_id;
    }

    public void setTipo_nomina_id(Integer tipo_nomina_id) {
        this.tipo_nomina_id = tipo_nomina_id;
    }

    public Integer getPrestaciones() {
        return prestaciones;
    }

    public void setPrestaciones(Integer prestaciones) {
        this.prestaciones = prestaciones;
    }

    public Double getSueldo_mensual_bruto() {
        return sueldo_mensual_bruto;
    }

    public void setSueldo_mensual_bruto(Double sueldo_mensual_bruto) {
        this.sueldo_mensual_bruto = sueldo_mensual_bruto;
    }

}
