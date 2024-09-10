/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rroscero23
 */
public class SdiDTO {

    private Integer id_trabajador;
    private Integer id_bimestre;
    private String desc_bimestre;
    private String numero_expediente;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;

    public SdiDTO() {
    }

    public SdiDTO(Integer id_trabajador, Integer id_bimestre, String desc_bimestre, String numero_expediente, String nombre, String apellido_paterno, String apellido_materno) {
        this.id_trabajador = id_trabajador;
        this.id_bimestre = id_bimestre;
        this.desc_bimestre = desc_bimestre;
        this.numero_expediente = numero_expediente;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
    }

    public Integer getId_trabajador() {
        return id_trabajador;
    }

    public void setId_trabajador(Integer id_trabajador) {
        this.id_trabajador = id_trabajador;
    }

    public Integer getId_bimestre() {
        return id_bimestre;
    }

    public void setId_bimestre(Integer id_bimestre) {
        this.id_bimestre = id_bimestre;
    }

    public String getDesc_bimestre() {
        return desc_bimestre;
    }

    public void setDesc_bimestre(String desc_bimestre) {
        this.desc_bimestre = desc_bimestre;
    }

    public String getNumero_expediente() {
        return numero_expediente;
    }

    public void setNumero_expediente(String numero_expediente) {
        this.numero_expediente = numero_expediente;
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

}
