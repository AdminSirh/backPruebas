/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

/**
 *
 * @author rrosero23
 */
@Entity
@Table(name = "pension_a_orden_judicial")
public class Pension_Alimenticia_OJ implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pension;
    private Integer trabajador_id;
    private Double porcentaje;
    private Integer modalidad;
    private String juzgado;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_recepcion;
    private Double cuota_fija;
    private String expediente_caso;
    private String referencia;
    private String oficio;
    private Integer periodo_aplicacion;
    private Boolean estatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "beneficiario_pa_id")
    private Pension_Alimenticia_B Pension_Alimenticia_B;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "montos_id")
    private Pension_Alimenticia_Montos Pension_Alimenticia_Montos;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_baja_pension;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_movimiento;

    public Pension_Alimenticia_OJ() {
    }

    public Pension_Alimenticia_OJ(Integer id_pension, Integer trabajador_id, Double porcentaje, Integer modalidad, String juzgado, LocalDate fecha_recepcion, Double cuota_fija, String expediente_caso, String referencia, String oficio, Integer periodo_aplicacion, Boolean estatus, Pension_Alimenticia_B Pension_Alimenticia_B, Pension_Alimenticia_Montos Pension_Alimenticia_Montos, LocalDate fecha_baja_pension, LocalDate fecha_movimiento) {
        this.id_pension = id_pension;
        this.trabajador_id = trabajador_id;
        this.porcentaje = porcentaje;
        this.modalidad = modalidad;
        this.juzgado = juzgado;
        this.fecha_recepcion = fecha_recepcion;
        this.cuota_fija = cuota_fija;
        this.expediente_caso = expediente_caso;
        this.referencia = referencia;
        this.oficio = oficio;
        this.periodo_aplicacion = periodo_aplicacion;
        this.estatus = estatus;
        this.Pension_Alimenticia_B = Pension_Alimenticia_B;
        this.Pension_Alimenticia_Montos = Pension_Alimenticia_Montos;
        this.fecha_baja_pension = fecha_baja_pension;
        this.fecha_movimiento = fecha_movimiento;
    }

    public Integer getId_pension() {
        return id_pension;
    }

    public void setId_pension(Integer id_pension) {
        this.id_pension = id_pension;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Integer getModalidad() {
        return modalidad;
    }

    public void setModalidad(Integer modalidad) {
        this.modalidad = modalidad;
    }

    public String getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(String juzgado) {
        this.juzgado = juzgado;
    }

    public LocalDate getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(LocalDate fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public Double getCuota_fija() {
        return cuota_fija;
    }

    public void setCuota_fija(Double cuota_fija) {
        this.cuota_fija = cuota_fija;
    }

    public String getExpediente_caso() {
        return expediente_caso;
    }

    public void setExpediente_caso(String expediente_caso) {
        this.expediente_caso = expediente_caso;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public Integer getPeriodo_aplicacion() {
        return periodo_aplicacion;
    }

    public void setPeriodo_aplicacion(Integer periodo_aplicacion) {
        this.periodo_aplicacion = periodo_aplicacion;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }

    public Pension_Alimenticia_B getPension_Alimenticia_B() {
        return Pension_Alimenticia_B;
    }

    public void setPension_Alimenticia_B(Pension_Alimenticia_B Pension_Alimenticia_B) {
        this.Pension_Alimenticia_B = Pension_Alimenticia_B;
    }

    public Pension_Alimenticia_Montos getPension_Alimenticia_Montos() {
        return Pension_Alimenticia_Montos;
    }

    public void setPension_Alimenticia_Montos(Pension_Alimenticia_Montos Pension_Alimenticia_Montos) {
        this.Pension_Alimenticia_Montos = Pension_Alimenticia_Montos;
    }

    public LocalDate getFecha_baja_pension() {
        return fecha_baja_pension;
    }

    public void setFecha_baja_pension(LocalDate fecha_baja_pension) {
        this.fecha_baja_pension = fecha_baja_pension;
    }

    public LocalDate getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDate fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    
}
