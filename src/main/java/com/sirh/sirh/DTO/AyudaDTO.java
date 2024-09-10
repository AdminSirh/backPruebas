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
 * @author akalvarez19
 */
public class AyudaDTO {

    private Integer id_ayuda;

    private Integer ayuda_dias_permiso_id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_recepcion;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_emision;

    private Integer parentesco_id;

    private String nombre;

    private String apellido_paterno;

    private String apellido_materno;

    private String origen;
  
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_evento;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dia_1;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dia_2;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dia_3;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dia_4;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dia_5;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dia_6;

    public AyudaDTO() {
        super();
    }

    public AyudaDTO(Integer id_ayuda, Integer ayuda_dias_permiso_id, LocalDate fecha_recepcion, LocalDate fecha_emision, Integer parentesco_id, String nombre, String apellido_paterno, String apellido_materno, String origen, LocalDate fecha_evento, LocalDate dia_1, LocalDate dia_2, LocalDate dia_3, LocalDate dia_4, LocalDate dia_5, LocalDate dia_6) {
        this.id_ayuda = id_ayuda;
        this.ayuda_dias_permiso_id = ayuda_dias_permiso_id;
        this.fecha_recepcion = fecha_recepcion;
        this.fecha_emision = fecha_emision;
        this.parentesco_id = parentesco_id;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.origen = origen;
        this.fecha_evento = fecha_evento;
        this.dia_1 = dia_1;
        this.dia_2 = dia_2;
        this.dia_3 = dia_3;
        this.dia_4 = dia_4;
        this.dia_5 = dia_5;
        this.dia_6 = dia_6;
    }

    public Integer getId_ayuda() {
        return id_ayuda;
    }

    public void setId_ayuda(Integer id_ayuda) {
        this.id_ayuda = id_ayuda;
    }

    public Integer getAyuda_dias_permiso_id() {
        return ayuda_dias_permiso_id;
    }

    public void setAyuda_dias_permiso_id(Integer ayuda_dias_permiso_id) {
        this.ayuda_dias_permiso_id = ayuda_dias_permiso_id;
    }

    public LocalDate getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(LocalDate fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public LocalDate getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(LocalDate fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public Integer getParentesco_id() {
        return parentesco_id;
    }

    public void setParentesco_id(Integer parentesco_id) {
        this.parentesco_id = parentesco_id;
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

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public LocalDate getFecha_evento() {
        return fecha_evento;
    }

    public void setFecha_evento(LocalDate fecha_evento) {
        this.fecha_evento = fecha_evento;
    }

    public LocalDate getDia_1() {
        return dia_1;
    }

    public void setDia_1(LocalDate dia_1) {
        this.dia_1 = dia_1;
    }

    public LocalDate getDia_2() {
        return dia_2;
    }

    public void setDia_2(LocalDate dia_2) {
        this.dia_2 = dia_2;
    }

    public LocalDate getDia_3() {
        return dia_3;
    }

    public void setDia_3(LocalDate dia_3) {
        this.dia_3 = dia_3;
    }

    public LocalDate getDia_4() {
        return dia_4;
    }

    public void setDia_4(LocalDate dia_4) {
        this.dia_4 = dia_4;
    }

    public LocalDate getDia_5() {
        return dia_5;
    }

    public void setDia_5(LocalDate dia_5) {
        this.dia_5 = dia_5;
    }

    public LocalDate getDia_6() {
        return dia_6;
    }

    public void setDia_6(LocalDate dia_6) {
        this.dia_6 = dia_6;
    }

    
    
}
