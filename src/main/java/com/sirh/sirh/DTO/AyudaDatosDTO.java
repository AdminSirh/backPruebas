/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author rroscero23
 */
public class AyudaDatosDTO {

    private String numero_expediente;

    private String nombre_trabajador;

    private String apellido_paterno_trabajador;

    private String apellido_materno_trabajador;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_emision;

    //De la tabla de incidencias
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_inicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_fin;

    private Integer num_dias;

    //Tabla de ayudas
    private Double monto;

    private Integer periodo_pago_id;

    private String nombre_ayuda;

    private String apellido_paterno_ayuda;

    private String apellido_materno_ayuda;

    private String desc_parentesco;

    //Catalogo de incidencias
    private String inicial_descripcion;

    //Tabla Incidencias
    private String folio;

    //Tabla Ayudas
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_recepcion;

    //Tabla Catálogo Tipo Nómina
    private String desc_nomina;

    public AyudaDatosDTO() {
    }

    public AyudaDatosDTO(String numero_expediente, String nombre_trabajador, String apellido_paterno_trabajador, String apellido_materno_trabajador, LocalDate fecha_emision, LocalDate fecha_inicio, LocalDate fecha_fin, Integer num_dias, Double monto, Integer periodo_pago_id, String nombre_ayuda, String apellido_paterno_ayuda, String apellido_materno_ayuda, String desc_parentesco, String inicial_descripcion, String folio, LocalDate fecha_recepcion, String desc_nomina) {
        this.numero_expediente = numero_expediente;
        this.nombre_trabajador = nombre_trabajador;
        this.apellido_paterno_trabajador = apellido_paterno_trabajador;
        this.apellido_materno_trabajador = apellido_materno_trabajador;
        this.fecha_emision = fecha_emision;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.num_dias = num_dias;
        this.monto = monto;
        this.periodo_pago_id = periodo_pago_id;
        this.nombre_ayuda = nombre_ayuda;
        this.apellido_paterno_ayuda = apellido_paterno_ayuda;
        this.apellido_materno_ayuda = apellido_materno_ayuda;
        this.desc_parentesco = desc_parentesco;
        this.inicial_descripcion = inicial_descripcion;
        this.folio = folio;
        this.fecha_recepcion = fecha_recepcion;
        this.desc_nomina = desc_nomina;
    }

    public String getNumero_expediente() {
        return numero_expediente;
    }

    public void setNumero_expediente(String numero_expediente) {
        this.numero_expediente = numero_expediente;
    }

    public String getNombre_trabajador() {
        return nombre_trabajador;
    }

    public void setNombre_trabajador(String nombre_trabajador) {
        this.nombre_trabajador = nombre_trabajador;
    }

    public String getApellido_paterno_trabajador() {
        return apellido_paterno_trabajador;
    }

    public void setApellido_paterno_trabajador(String apellido_paterno_trabajador) {
        this.apellido_paterno_trabajador = apellido_paterno_trabajador;
    }

    public String getApellido_materno_trabajador() {
        return apellido_materno_trabajador;
    }

    public void setApellido_materno_trabajador(String apellido_materno_trabajador) {
        this.apellido_materno_trabajador = apellido_materno_trabajador;
    }

    public LocalDate getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(LocalDate fecha_emision) {
        this.fecha_emision = fecha_emision;
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

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Integer getPeriodo_pago_id() {
        return periodo_pago_id;
    }

    public void setPeriodo_pago_id(Integer periodo_pago_id) {
        this.periodo_pago_id = periodo_pago_id;
    }

    public String getNombre_ayuda() {
        return nombre_ayuda;
    }

    public void setNombre_ayuda(String nombre_ayuda) {
        this.nombre_ayuda = nombre_ayuda;
    }

    public String getApellido_paterno_ayuda() {
        return apellido_paterno_ayuda;
    }

    public void setApellido_paterno_ayuda(String apellido_paterno_ayuda) {
        this.apellido_paterno_ayuda = apellido_paterno_ayuda;
    }

    public String getApellido_materno_ayuda() {
        return apellido_materno_ayuda;
    }

    public void setApellido_materno_ayuda(String apellido_materno_ayuda) {
        this.apellido_materno_ayuda = apellido_materno_ayuda;
    }

    public String getDesc_parentesco() {
        return desc_parentesco;
    }

    public void setDesc_parentesco(String desc_parentesco) {
        this.desc_parentesco = desc_parentesco;
    }

    public String getInicial_descripcion() {
        return inicial_descripcion;
    }

    public void setInicial_descripcion(String inicial_descripcion) {
        this.inicial_descripcion = inicial_descripcion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDate getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(LocalDate fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public String getDesc_nomina() {
        return desc_nomina;
    }

    public void setDesc_nomina(String desc_nomina) {
        this.desc_nomina = desc_nomina;
    }

    
    
}
