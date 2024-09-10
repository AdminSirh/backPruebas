/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rroscero23
 */
public class Sdi_IdseDTO {

    private String num_imss;
    private String apellido_paterno;
    private String apellido_materno;
    private String nombre;
    private Double sal_base_cot;
    private String numero_expediente;
    private String rfc;

    public Sdi_IdseDTO() {
    }

    public Sdi_IdseDTO(String num_imss, String apellido_paterno, String apellido_materno, String nombre, Double sal_base_cot, String numero_expediente, String rfc) {
        this.num_imss = num_imss;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.nombre = nombre;
        this.sal_base_cot = sal_base_cot;
        this.numero_expediente = numero_expediente;
        this.rfc = rfc;
    }

    public String getNum_imss() {
        return num_imss;
    }

    public void setNum_imss(String num_imss) {
        this.num_imss = num_imss;
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

    public Double getSal_base_cot() {
        return sal_base_cot;
    }

    public void setSal_base_cot(Double sal_base_cot) {
        this.sal_base_cot = sal_base_cot;
    }

    public String getNumero_expediente() {
        return numero_expediente;
    }

    public void setNumero_expediente(String numero_expediente) {
        this.numero_expediente = numero_expediente;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

}
