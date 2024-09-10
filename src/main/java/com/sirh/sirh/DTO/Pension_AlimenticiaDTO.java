/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rrosero23
 */
public class Pension_AlimenticiaDTO {

    private Integer id_pension;
    private String oficio;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private Integer id_trabajador;
    private String nombreT;
    private String apellidoPatT;
    private String apellidoMatT;

    public Pension_AlimenticiaDTO(Integer id_pension, String oficio, String nombre, String apellido_paterno, String apellido_materno, Integer id_trabajador, String nombreT, String apellidoPatT, String apellidoMatT) {
        this.id_pension = id_pension;
        this.oficio = oficio;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.id_trabajador = id_trabajador;
        this.nombreT = nombreT;
        this.apellidoPatT = apellidoPatT;
        this.apellidoMatT = apellidoMatT;
    }

    public Integer getId_pension() {
        return id_pension;
    }

    public void setId_pension(Integer id_pension) {
        this.id_pension = id_pension;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
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

    public Integer getId_trabajador() {
        return id_trabajador;
    }

    public void setId_trabajador(Integer id_trabajador) {
        this.id_trabajador = id_trabajador;
    }

    public String getNombreT() {
        return nombreT;
    }

    public void setNombreT(String nombreT) {
        this.nombreT = nombreT;
    }

    public String getApellidoPatT() {
        return apellidoPatT;
    }

    public void setApellidoPatT(String apellidoPatT) {
        this.apellidoPatT = apellidoPatT;
    }

    public String getApellidoMatT() {
        return apellidoMatT;
    }

    public void setApellidoMatT(String apellidoMatT) {
        this.apellidoMatT = apellidoMatT;
    }

}
