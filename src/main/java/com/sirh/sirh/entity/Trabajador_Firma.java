/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author oguerrero23
 */
@Entity
@Table(name = "trabajador_firma")
public class Trabajador_Firma implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_trabajador_firma;
    
    private String nombre;
    private String ruta;
    private Integer trabajador_id;
    private String extencion;
    private String tamano;
    private String nombre_real;
    @CreatedDate
    private LocalDate fecha_captura;
    @CreatedDate
    private LocalDate fecha_modificacion;
    private Integer estatus;
    
    public Trabajador_Firma() {
        super();
    }

    public Trabajador_Firma(Integer id_trabajador_firma, String nombre, String ruta, Integer trabajador_id, String extencion, String tamano, String nombre_real, LocalDate fecha_captura, LocalDate fecha_modificacion, Integer estatus) {
        this.id_trabajador_firma = id_trabajador_firma;
        this.nombre = nombre;
        this.ruta = ruta;
        this.trabajador_id = trabajador_id;
        this.extencion = extencion;
        this.tamano = tamano;
        this.nombre_real = nombre_real;
        this.fecha_captura = fecha_captura;
        this.fecha_modificacion = fecha_modificacion;
        this.estatus = estatus;
    }

    public Integer getId_trabajador_firma() {
        return id_trabajador_firma;
    }

    public void setId_trabajador_firma(Integer id_trabajador_firma) {
        this.id_trabajador_firma = id_trabajador_firma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public String getExtencion() {
        return extencion;
    }

    public void setExtencion(String extencion) {
        this.extencion = extencion;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getNombre_real() {
        return nombre_real;
    }

    public void setNombre_real(String nombre_real) {
        this.nombre_real = nombre_real;
    }

    public LocalDate getFecha_captura() {
        return fecha_captura;
    }

    public void setFecha_captura(LocalDate fecha_captura) {
        this.fecha_captura = fecha_captura;
    }

    public LocalDate getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(LocalDate fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    
}
