/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;


/**
 *
 * @author jarellano22
 */
@Entity
@Table(name = "documentacion")
public class Documentacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_documentacion;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String ruta;
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_documento_id")
    private Cat_Tipo_Documento cat_tipo_documento;
       
    @NotEmpty
    private Integer persona_id;
    @NotEmpty
    private String extencion;

    @NotEmpty
    @CreatedDate
    private LocalDate fecha_captura;

    @NotEmpty
    @CreatedDate
    private LocalDate fecha_modificacion;
    @NotEmpty
    private Long tamano;
    @NotEmpty
    private String nombre_real;

    public Documentacion() {
    }
    
    public Documentacion(Integer id_documentacion, String nombre, String ruta, Cat_Tipo_Documento cat_tipo_documento, Integer persona_id, String extencion, LocalDate fecha_captura, LocalDate fecha_modificacion, Long tamano, String nombre_real) {
        this.id_documentacion = id_documentacion;
        this.nombre = nombre;
        this.ruta = ruta;
        this.cat_tipo_documento = cat_tipo_documento;
        this.persona_id = persona_id;
        this.extencion = extencion;
        this.fecha_captura = fecha_captura;
        this.fecha_modificacion = fecha_modificacion;
        this.tamano = tamano;
        this.nombre_real = nombre_real;
    }

    public Integer getId_documentacion() {
        return id_documentacion;
    }

    public void setId_documentacion(Integer id_documentacion) {
        this.id_documentacion = id_documentacion;
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

    public Cat_Tipo_Documento getCat_tipo_documento() {
        return cat_tipo_documento;
    }

    public void setCat_tipo_documento(Cat_Tipo_Documento cat_tipo_documento) {
        this.cat_tipo_documento = cat_tipo_documento;
    }

    public Integer getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(Integer persona_id) {
        this.persona_id = persona_id;
    }

    public String getExtencion() {
        return extencion;
    }

    public void setExtencion(String extencion) {
        this.extencion = extencion;
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

    public Long getTamano() {
        return tamano;
    }

    public void setTamano(Long tamano) {
        this.tamano = tamano;
    }

    public String getNombre_real() {
        return nombre_real;
    }

    public void setNombre_real(String nombre_real) {
        this.nombre_real = nombre_real;
    }

    
}
