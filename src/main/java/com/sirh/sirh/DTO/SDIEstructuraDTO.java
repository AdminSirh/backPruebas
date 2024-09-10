/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import java.time.LocalDate;

/**
 *
 * @author rroscero23
 */
public class SDIEstructuraDTO {

    private Integer id_trabajador;
    private String expediente;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String num_imss_primeros_10;
    private String num_imss_digito_verificador;
    private String rfc;
    private String desc_genero;
    private String hijos_si_no;
    private Double sueldo_diario;
    private String desc_nomina;
    private LocalDate fecha_ingreso;
    private Double total_cve_1;
    private Double total_cve_10;
    private Double total_cve_18;
    private Double total_cve_21;
    private Double total_cve_28;
    private Double total_cve_1002;
    private Double total_cve_1021;
    private Double total_cve_1210;
    private Double total_cve_1211;
    private Double total_cve_1212;

    public SDIEstructuraDTO() {
    }

    public SDIEstructuraDTO(Integer id_trabajador, String expediente, String nombre, String apellido_paterno, String apellido_materno, String num_imss_primeros_10, String num_imss_digito_verificador, String rfc, String desc_genero, String hijos_si_no, Double sueldo_diario, String desc_nomina, LocalDate fecha_ingreso, Double total_cve_1, Double total_cve_10, Double total_cve_18, Double total_cve_21, Double total_cve_28, Double total_cve_1002, Double total_cve_1021, Double total_cve_1210, Double total_cve_1211, Double total_cve_1212) {
        this.id_trabajador = id_trabajador;
        this.expediente = expediente;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.num_imss_primeros_10 = num_imss_primeros_10;
        this.num_imss_digito_verificador = num_imss_digito_verificador;
        this.rfc = rfc;
        this.desc_genero = desc_genero;
        this.hijos_si_no = hijos_si_no;
        this.sueldo_diario = sueldo_diario;
        this.desc_nomina = desc_nomina;
        this.fecha_ingreso = fecha_ingreso;
        this.total_cve_1 = total_cve_1;
        this.total_cve_10 = total_cve_10;
        this.total_cve_18 = total_cve_18;
        this.total_cve_21 = total_cve_21;
        this.total_cve_28 = total_cve_28;
        this.total_cve_1002 = total_cve_1002;
        this.total_cve_1021 = total_cve_1021;
        this.total_cve_1210 = total_cve_1210;
        this.total_cve_1211 = total_cve_1211;
        this.total_cve_1212 = total_cve_1212;
    }

    public Integer getId_trabajador() {
        return id_trabajador;
    }

    public void setId_trabajador(Integer id_trabajador) {
        this.id_trabajador = id_trabajador;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getNum_imss_primeros_10() {
        return num_imss_primeros_10;
    }

    public void setNum_imss_primeros_10(String num_imss_primeros_10) {
        this.num_imss_primeros_10 = num_imss_primeros_10;
    }

    public String getNum_imss_digito_verificador() {
        return num_imss_digito_verificador;
    }

    public void setNum_imss_digito_verificador(String num_imss_digito_verificador) {
        this.num_imss_digito_verificador = num_imss_digito_verificador;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDesc_genero() {
        return desc_genero;
    }

    public void setDesc_genero(String desc_genero) {
        this.desc_genero = desc_genero;
    }

    public String getHijos_si_no() {
        return hijos_si_no;
    }

    public void setHijos_si_no(String hijos_si_no) {
        this.hijos_si_no = hijos_si_no;
    }

    public Double getSueldo_diario() {
        return sueldo_diario;
    }

    public void setSueldo_diario(Double sueldo_diario) {
        this.sueldo_diario = sueldo_diario;
    }

    public String getDesc_nomina() {
        return desc_nomina;
    }

    public void setDesc_nomina(String desc_nomina) {
        this.desc_nomina = desc_nomina;
    }

    public LocalDate getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(LocalDate fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public Double getTotal_cve_1() {
        return total_cve_1;
    }

    public void setTotal_cve_1(Double total_cve_1) {
        this.total_cve_1 = total_cve_1;
    }

    public Double getTotal_cve_10() {
        return total_cve_10;
    }

    public void setTotal_cve_10(Double total_cve_10) {
        this.total_cve_10 = total_cve_10;
    }

    public Double getTotal_cve_18() {
        return total_cve_18;
    }

    public void setTotal_cve_18(Double total_cve_18) {
        this.total_cve_18 = total_cve_18;
    }

    public Double getTotal_cve_21() {
        return total_cve_21;
    }

    public void setTotal_cve_21(Double total_cve_21) {
        this.total_cve_21 = total_cve_21;
    }

    public Double getTotal_cve_28() {
        return total_cve_28;
    }

    public void setTotal_cve_28(Double total_cve_28) {
        this.total_cve_28 = total_cve_28;
    }

    public Double getTotal_cve_1002() {
        return total_cve_1002;
    }

    public void setTotal_cve_1002(Double total_cve_1002) {
        this.total_cve_1002 = total_cve_1002;
    }

    public Double getTotal_cve_1021() {
        return total_cve_1021;
    }

    public void setTotal_cve_1021(Double total_cve_1021) {
        this.total_cve_1021 = total_cve_1021;
    }

    public Double getTotal_cve_1210() {
        return total_cve_1210;
    }

    public void setTotal_cve_1210(Double total_cve_1210) {
        this.total_cve_1210 = total_cve_1210;
    }

    public Double getTotal_cve_1211() {
        return total_cve_1211;
    }

    public void setTotal_cve_1211(Double total_cve_1211) {
        this.total_cve_1211 = total_cve_1211;
    }

    public Double getTotal_cve_1212() {
        return total_cve_1212;
    }

    public void setTotal_cve_1212(Double total_cve_1212) {
        this.total_cve_1212 = total_cve_1212;
    }

}
