/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author oguerrero23
 */
@Entity
@Table (name="tmp_calculos")
public class Tmp_Calculos implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_calculo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trabajador_id")
    private Trabajador trabajador;
    
    private Integer periodo_generacion;
    private Integer periodo_aplicacion;
    private Integer anio_aplicacion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nomina_id")
    private Cat_Tipo_Nomina cat_Tipo_Nomina;
    private Double valor;
    private Double gravable;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_creacion;
    private Integer puesto_temporal_id;
    private Double sueldo_temporal;
    private Double sueldo_temporal_mensual;
    private Double sdi;

    public Tmp_Calculos() {
        super();
    }

    public Tmp_Calculos(Integer id_calculo, Trabajador trabajador, Integer periodo_generacion, Integer periodo_aplicacion, Integer anio_aplicacion, Cat_Tipo_Nomina cat_Tipo_Nomina, Double valor, Double gravable, LocalDate fecha_creacion, Integer puesto_temporal_id, Double sueldo_temporal, Double sueldo_temporal_mensual, Double sdi) {
        this.id_calculo = id_calculo;
        this.trabajador = trabajador;
        this.periodo_generacion = periodo_generacion;
        this.periodo_aplicacion = periodo_aplicacion;
        this.anio_aplicacion = anio_aplicacion;
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
        this.valor = valor;
        this.gravable = gravable;
        this.fecha_creacion = fecha_creacion;
        this.puesto_temporal_id = puesto_temporal_id;
        this.sueldo_temporal = sueldo_temporal;
        this.sueldo_temporal_mensual = sueldo_temporal_mensual;
        this.sdi = sdi;
    }

    public Integer getId_calculo() {
        return id_calculo;
    }

    public void setId_calculo(Integer id_calculo) {
        this.id_calculo = id_calculo;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Integer getPeriodo_generacion() {
        return periodo_generacion;
    }

    public void setPeriodo_generacion(Integer periodo_generacion) {
        this.periodo_generacion = periodo_generacion;
    }

    public Integer getPeriodo_aplicacion() {
        return periodo_aplicacion;
    }

    public void setPeriodo_aplicacion(Integer periodo_aplicacion) {
        this.periodo_aplicacion = periodo_aplicacion;
    }

    public Integer getAnio_aplicacion() {
        return anio_aplicacion;
    }

    public void setAnio_aplicacion(Integer anio_aplicacion) {
        this.anio_aplicacion = anio_aplicacion;
    }

    public Cat_Tipo_Nomina getCat_Tipo_Nomina() {
        return cat_Tipo_Nomina;
    }

    public void setCat_Tipo_Nomina(Cat_Tipo_Nomina cat_Tipo_Nomina) {
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getGravable() {
        return gravable;
    }

    public void setGravable(Double gravable) {
        this.gravable = gravable;
    }

    public LocalDate getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDate fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Integer getPuesto_temporal_id() {
        return puesto_temporal_id;
    }

    public void setPuesto_temporal_id(Integer puesto_temporal_id) {
        this.puesto_temporal_id = puesto_temporal_id;
    }

    public Double getSueldo_temporal() {
        return sueldo_temporal;
    }

    public void setSueldo_temporal(Double sueldo_temporal) {
        this.sueldo_temporal = sueldo_temporal;
    }

    public Double getSueldo_temporal_mensual() {
        return sueldo_temporal_mensual;
    }

    public void setSueldo_temporal_mensual(Double sueldo_temporal_mensual) {
        this.sueldo_temporal_mensual = sueldo_temporal_mensual;
    }

    public Double getSdi() {
        return sdi;
    }

    public void setSdi(Double sdi) {
        this.sdi = sdi;
    }

    
    
}
