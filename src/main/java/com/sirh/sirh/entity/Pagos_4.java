/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table (name = "pagos_4")
public class Pagos_4 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pago_4;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trabajador_id")
    private Trabajador trabajador;
    private Double monto;
    private Integer periodo_aplicacion;
    private Integer anio_aplicacion;
    private Integer puesto_temporal_id;
    private Double sueldo_temporal;
    private Double sueldo_temporal_mensual;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_registro;
    private Integer taxi_electrico;
    private Double sdi;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "puesto_id")
    private Cat_Puesto cat_Puesto;

    public Pagos_4() {
        super();
    }

    public Pagos_4(Integer id_pago_4, Trabajador trabajador, Double monto, Integer periodo_aplicacion, Integer anio_aplicacion, Integer puesto_temporal_id, Double sueldo_temporal, Double sueldo_temporal_mensual, LocalDate fecha_registro, Integer taxi_electrico, Double sdi, Cat_Puesto cat_Puesto) {
        this.id_pago_4 = id_pago_4;
        this.trabajador = trabajador;
        this.monto = monto;
        this.periodo_aplicacion = periodo_aplicacion;
        this.anio_aplicacion = anio_aplicacion;
        this.puesto_temporal_id = puesto_temporal_id;
        this.sueldo_temporal = sueldo_temporal;
        this.sueldo_temporal_mensual = sueldo_temporal_mensual;
        this.fecha_registro = fecha_registro;
        this.taxi_electrico = taxi_electrico;
        this.sdi = sdi;
        this.cat_Puesto = cat_Puesto;
    }

    public Integer getId_pago_4() {
        return id_pago_4;
    }

    public void setId_pago_4(Integer id_pago_4) {
        this.id_pago_4 = id_pago_4;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
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

    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Integer getTaxi_electrico() {
        return taxi_electrico;
    }

    public void setTaxi_electrico(Integer taxi_electrico) {
        this.taxi_electrico = taxi_electrico;
    }

    public Double getSdi() {
        return sdi;
    }

    public void setSdi(Double sdi) {
        this.sdi = sdi;
    }

    public Cat_Puesto getCat_Puesto() {
        return cat_Puesto;
    }

    public void setCat_Puesto(Cat_Puesto cat_Puesto) {
        this.cat_Puesto = cat_Puesto;
    }

    
}
