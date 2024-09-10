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
@Table (name = "tmp_movimientos")
public class Tmp_Movimientos implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_movimiento;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trabajador_id")
    private Trabajador trabajador;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "percepcion_id")
    private Cat_Incidencias cat_Incidencias;
    private Integer periodo_aplicacion;
    private Integer periodo_generacion;
    private Integer anio_aplicacion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nomina_id")
    private Cat_Tipo_Nomina cat_Tipo_Nomina;
    private Double valor_1;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_creacion;

    public Tmp_Movimientos() {
        super();
    }

    public Tmp_Movimientos(Integer id_movimiento, Trabajador trabajador, Cat_Incidencias cat_Incidencias, Integer periodo_aplicacion, Integer periodo_generacion, Integer anio_aplicacion, Cat_Tipo_Nomina cat_Tipo_Nomina, Double valor_1, LocalDate fecha_creacion) {
        this.id_movimiento = id_movimiento;
        this.trabajador = trabajador;
        this.cat_Incidencias = cat_Incidencias;
        this.periodo_aplicacion = periodo_aplicacion;
        this.periodo_generacion = periodo_generacion;
        this.anio_aplicacion = anio_aplicacion;
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
        this.valor_1 = valor_1;
        this.fecha_creacion = fecha_creacion;
    }

    public Integer getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(Integer id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Cat_Incidencias getCat_Incidencias() {
        return cat_Incidencias;
    }

    public void setCat_Incidencias(Cat_Incidencias cat_Incidencias) {
        this.cat_Incidencias = cat_Incidencias;
    }

    public Integer getPeriodo_aplicacion() {
        return periodo_aplicacion;
    }

    public void setPeriodo_aplicacion(Integer periodo_aplicacion) {
        this.periodo_aplicacion = periodo_aplicacion;
    }

    public Integer getPeriodo_generacion() {
        return periodo_generacion;
    }

    public void setPeriodo_generacion(Integer periodo_generacion) {
        this.periodo_generacion = periodo_generacion;
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

    public Double getValor_1() {
        return valor_1;
    }

    public void setValor_1(Double valor_1) {
        this.valor_1 = valor_1;
    }

    public LocalDate getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDate fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    
}
