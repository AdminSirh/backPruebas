/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author oguerrero23
 */
public class Registro_Solicitudes_FonacotDTO {
    
    private String rfc;
    private String num_imss;
    private String curp;
    private String desc_genero;
    private String apellido_paterno;
    private String apellido_materno;
    private String nombre;
    private LocalDate fecha_ingreso;
    private Double sueldo_mensual_neto;
    private Double sueldo_mensual;
    private String numero_expediente;
    private Integer prestaciones_si_no;
    private Integer tipo_nomina_id;
    private Double sdi_inicial;

    public Registro_Solicitudes_FonacotDTO() {
        super();
    }

    public Registro_Solicitudes_FonacotDTO(String rfc, String num_imss, String curp, String desc_genero, String apellido_paterno, String apellido_materno, String nombre, LocalDate fecha_ingreso, Double sueldo_mensual_neto, Double sueldo_mensual, String numero_expediente, Integer prestaciones_si_no, Integer tipo_nomina_id, Double sdi_inicial) {
        this.rfc = rfc;
        this.num_imss = num_imss;
        this.curp = curp;
        this.desc_genero = desc_genero;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.nombre = nombre;
        this.fecha_ingreso = fecha_ingreso;
        this.sueldo_mensual_neto = sueldo_mensual_neto;
        this.sueldo_mensual = sueldo_mensual;
        this.numero_expediente = numero_expediente;
        this.prestaciones_si_no = prestaciones_si_no;
        this.tipo_nomina_id = tipo_nomina_id;
        this.sdi_inicial = sdi_inicial;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNum_imss() {
        return num_imss;
    }

    public void setNum_imss(String num_imss) {
        this.num_imss = num_imss;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getDesc_genero() {
        return desc_genero;
    }

    public void setDesc_genero(String desc_genero) {
        this.desc_genero = desc_genero;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(LocalDate fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public Double getSueldo_mensual_neto() {
        return sueldo_mensual_neto;
    }

    public void setSueldo_mensual_neto(Double sueldo_mensual_neto) {
        this.sueldo_mensual_neto = sueldo_mensual_neto;
    }

    public Double getSueldo_mensual() {
        return sueldo_mensual;
    }

    public void setSueldo_mensual(Double sueldo_mensual) {
        this.sueldo_mensual = sueldo_mensual;
    }

    public String getNumero_expediente() {
        return numero_expediente;
    }

    public void setNumero_expediente(String numero_expediente) {
        this.numero_expediente = numero_expediente;
    }

    public Integer getPrestaciones_si_no() {
        return prestaciones_si_no;
    }

    public void setPrestaciones_si_no(Integer prestaciones_si_no) {
        this.prestaciones_si_no = prestaciones_si_no;
    }

    public Integer getTipo_nomina_id() {
        return tipo_nomina_id;
    }

    public void setTipo_nomina_id(Integer tipo_nomina_id) {
        this.tipo_nomina_id = tipo_nomina_id;
    }

    public Double getSdi_inicial() {
        return sdi_inicial;
    }

    public void setSdi_inicial(Double sdi_inicial) {
        this.sdi_inicial = sdi_inicial;
    }

    
}
