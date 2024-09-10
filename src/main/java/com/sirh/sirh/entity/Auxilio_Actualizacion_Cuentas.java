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
 * @author oguerrero23
 */
@Entity
@Table(name = "auxilio_actualizacion_cuentas")
public class Auxilio_Actualizacion_Cuentas implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_auxilio_actualizacion_cuentas;
    
    private Integer auxilio_cuentas_cobrar_id;
    private Integer concepto;
    private Double saldo_anterior;
    private Double abono;
    private Double saldo_actual;
    private Integer periodo;
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nomina_id")
    private Cat_Tipo_Nomina cat_Tipo_Nomina;
    private Integer estatus;
    private Double saldo_ant_vac;
    private Double abono_vac;
    private Double descto_norm;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trabajador_id")
    private Trabajador trabajador;
    @CreatedDate
    private LocalDate fecha_movimiento;

    public Auxilio_Actualizacion_Cuentas() {
        super();
    }

    public Auxilio_Actualizacion_Cuentas(Integer id_auxilio_actualizacion_cuentas, Integer auxilio_cuentas_cobrar_id, Integer concepto, Double saldo_anterior, Double abono, Double saldo_actual, Integer periodo, Cat_Tipo_Nomina cat_Tipo_Nomina, Integer estatus, Double saldo_ant_vac, Double abono_vac, Double descto_norm, Trabajador trabajador, LocalDate fecha_movimiento) {
        this.id_auxilio_actualizacion_cuentas = id_auxilio_actualizacion_cuentas;
        this.auxilio_cuentas_cobrar_id = auxilio_cuentas_cobrar_id;
        this.concepto = concepto;
        this.saldo_anterior = saldo_anterior;
        this.abono = abono;
        this.saldo_actual = saldo_actual;
        this.periodo = periodo;
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
        this.estatus = estatus;
        this.saldo_ant_vac = saldo_ant_vac;
        this.abono_vac = abono_vac;
        this.descto_norm = descto_norm;
        this.trabajador = trabajador;
        this.fecha_movimiento = fecha_movimiento;
    }

    public Integer getId_auxilio_actualizacion_cuentas() {
        return id_auxilio_actualizacion_cuentas;
    }

    public void setId_auxilio_actualizacion_cuentas(Integer id_auxilio_actualizacion_cuentas) {
        this.id_auxilio_actualizacion_cuentas = id_auxilio_actualizacion_cuentas;
    }

    public Integer getAuxilio_cuentas_cobrar_id() {
        return auxilio_cuentas_cobrar_id;
    }

    public void setAuxilio_cuentas_cobrar_id(Integer auxilio_cuentas_cobrar_id) {
        this.auxilio_cuentas_cobrar_id = auxilio_cuentas_cobrar_id;
    }

    public Integer getConcepto() {
        return concepto;
    }

    public void setConcepto(Integer concepto) {
        this.concepto = concepto;
    }

    public Double getSaldo_anterior() {
        return saldo_anterior;
    }

    public void setSaldo_anterior(Double saldo_anterior) {
        this.saldo_anterior = saldo_anterior;
    }

    public Double getAbono() {
        return abono;
    }

    public void setAbono(Double abono) {
        this.abono = abono;
    }

    public Double getSaldo_actual() {
        return saldo_actual;
    }

    public void setSaldo_actual(Double saldo_actual) {
        this.saldo_actual = saldo_actual;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Cat_Tipo_Nomina getCat_Tipo_Nomina() {
        return cat_Tipo_Nomina;
    }

    public void setCat_Tipo_Nomina(Cat_Tipo_Nomina cat_Tipo_Nomina) {
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Double getSaldo_ant_vac() {
        return saldo_ant_vac;
    }

    public void setSaldo_ant_vac(Double saldo_ant_vac) {
        this.saldo_ant_vac = saldo_ant_vac;
    }

    public Double getAbono_vac() {
        return abono_vac;
    }

    public void setAbono_vac(Double abono_vac) {
        this.abono_vac = abono_vac;
    }

    public Double getDescto_norm() {
        return descto_norm;
    }

    public void setDescto_norm(Double descto_norm) {
        this.descto_norm = descto_norm;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public LocalDate getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDate fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    
}
