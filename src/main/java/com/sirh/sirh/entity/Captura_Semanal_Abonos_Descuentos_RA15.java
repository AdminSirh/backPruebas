/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author rroscero23
 */
@Entity
@Table(name = "captura_semanal_abonos_descuentos_ra_15")
public class Captura_Semanal_Abonos_Descuentos_RA15 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_abono_descuento;
    private Double abono_jornada;
    private Double descuento_jornada;
    private Double abono_doble;
    private Double descuento_doble;
    private Double abono_triple;
    private Double descuento_triple;
    private Double abono_descanso;
    private Double descuento_descanso;
    private Double abono_prima;
    private Double descuento_prima;
    private Double abono_festivo;
    private Double descuento_festivo;
    private Double abono_dias_lab;
    private Double descuento_dias_lab;
    private Double abono_dif_suplencia;
    private Double descuento_dif_suplencia;
    private String observaciones;
    private Double descuento_omisiones;
    private String observaciones_omisiones;

    public Integer getId_abono_descuento() {
        return id_abono_descuento;
    }

    public void setId_abono_descuento(Integer id_abono_descuento) {
        this.id_abono_descuento = id_abono_descuento;
    }

    public Double getAbono_jornada() {
        return abono_jornada;
    }

    public void setAbono_jornada(Double abono_jornada) {
        this.abono_jornada = abono_jornada;
    }

    public Double getDescuento_jornada() {
        return descuento_jornada;
    }

    public void setDescuento_jornada(Double descuento_jornada) {
        this.descuento_jornada = descuento_jornada;
    }

    public Double getAbono_doble() {
        return abono_doble;
    }

    public void setAbono_doble(Double abono_doble) {
        this.abono_doble = abono_doble;
    }

    public Double getDescuento_doble() {
        return descuento_doble;
    }

    public void setDescuento_doble(Double descuento_doble) {
        this.descuento_doble = descuento_doble;
    }

    public Double getAbono_triple() {
        return abono_triple;
    }

    public void setAbono_triple(Double abono_triple) {
        this.abono_triple = abono_triple;
    }

    public Double getDescuento_triple() {
        return descuento_triple;
    }

    public void setDescuento_triple(Double descuento_triple) {
        this.descuento_triple = descuento_triple;
    }

    public Double getAbono_descanso() {
        return abono_descanso;
    }

    public void setAbono_descanso(Double abono_descanso) {
        this.abono_descanso = abono_descanso;
    }

    public Double getDescuento_descanso() {
        return descuento_descanso;
    }

    public void setDescuento_descanso(Double descuento_descanso) {
        this.descuento_descanso = descuento_descanso;
    }

    public Double getAbono_prima() {
        return abono_prima;
    }

    public void setAbono_prima(Double abono_prima) {
        this.abono_prima = abono_prima;
    }

    public Double getDescuento_prima() {
        return descuento_prima;
    }

    public void setDescuento_prima(Double descuento_prima) {
        this.descuento_prima = descuento_prima;
    }

    public Double getAbono_festivo() {
        return abono_festivo;
    }

    public void setAbono_festivo(Double abono_festivo) {
        this.abono_festivo = abono_festivo;
    }

    public Double getDescuento_festivo() {
        return descuento_festivo;
    }

    public void setDescuento_festivo(Double descuento_festivo) {
        this.descuento_festivo = descuento_festivo;
    }

    public Double getAbono_dias_lab() {
        return abono_dias_lab;
    }

    public void setAbono_dias_lab(Double abono_dias_lab) {
        this.abono_dias_lab = abono_dias_lab;
    }

    public Double getDescuento_dias_lab() {
        return descuento_dias_lab;
    }

    public void setDescuento_dias_lab(Double descuento_dias_lab) {
        this.descuento_dias_lab = descuento_dias_lab;
    }

    public Double getAbono_dif_suplencia() {
        return abono_dif_suplencia;
    }

    public void setAbono_dif_suplencia(Double abono_dif_suplencia) {
        this.abono_dif_suplencia = abono_dif_suplencia;
    }

    public Double getDescuento_dif_suplencia() {
        return descuento_dif_suplencia;
    }

    public void setDescuento_dif_suplencia(Double descuento_dif_suplencia) {
        this.descuento_dif_suplencia = descuento_dif_suplencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Double getDescuento_omisiones() {
        return descuento_omisiones;
    }

    public void setDescuento_omisiones(Double descuento_omisiones) {
        this.descuento_omisiones = descuento_omisiones;
    }

    public String getObservaciones_omisiones() {
        return observaciones_omisiones;
    }

    public void setObservaciones_omisiones(String observaciones_omisiones) {
        this.observaciones_omisiones = observaciones_omisiones;
    }

}
