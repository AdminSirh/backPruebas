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
@Table(name = "captura_semanal_resumen_incidencias_ra15")
public class Captura_Semanal_Resumen_IncidenciasRA15 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_resumen;
    private Double horas_turno;
    private Double horas_doble;
    private Double horas_triple;
    private Double dias_laborados;
    private Integer total_faltas;
    private Double total_paseos;
    private Integer prima_dominical;
    private Double festivo;
    private Double omisiones;
    private Double dif_paseos;
    private Double dif_prima;
    private Double dif_tiempo_extra;
    private Double dif_sueldo;
    private Integer dias_pago;
    private Integer dias_cve_27;

    public Integer getId_resumen() {
        return id_resumen;
    }

    public void setId_resumen(Integer id_resumen) {
        this.id_resumen = id_resumen;
    }

    public Double getHoras_turno() {
        return horas_turno;
    }

    public void setHoras_turno(Double horas_turno) {
        this.horas_turno = horas_turno;
    }

    public Double getHoras_doble() {
        return horas_doble;
    }

    public void setHoras_doble(Double horas_doble) {
        this.horas_doble = horas_doble;
    }

    public Double getHoras_triple() {
        return horas_triple;
    }

    public void setHoras_triple(Double horas_triple) {
        this.horas_triple = horas_triple;
    }

    public Double getDias_laborados() {
        return dias_laborados;
    }

    public void setDias_laborados(Double dias_laborados) {
        this.dias_laborados = dias_laborados;
    }

    public Integer getTotal_faltas() {
        return total_faltas;
    }

    public void setTotal_faltas(Integer total_faltas) {
        this.total_faltas = total_faltas;
    }

    public Double getTotal_paseos() {
        return total_paseos;
    }

    public void setTotal_paseos(Double total_paseos) {
        this.total_paseos = total_paseos;
    }

    public Integer getPrima_dominical() {
        return prima_dominical;
    }

    public void setPrima_dominical(Integer prima_dominical) {
        this.prima_dominical = prima_dominical;
    }

    public Double getFestivo() {
        return festivo;
    }

    public void setFestivo(Double festivo) {
        this.festivo = festivo;
    }

    public Double getOmisiones() {
        return omisiones;
    }

    public void setOmisiones(Double omisiones) {
        this.omisiones = omisiones;
    }

    public Double getDif_paseos() {
        return dif_paseos;
    }

    public void setDif_paseos(Double dif_paseos) {
        this.dif_paseos = dif_paseos;
    }

    public Double getDif_prima() {
        return dif_prima;
    }

    public void setDif_prima(Double dif_prima) {
        this.dif_prima = dif_prima;
    }

    public Double getDif_tiempo_extra() {
        return dif_tiempo_extra;
    }

    public void setDif_tiempo_extra(Double dif_tiempo_extra) {
        this.dif_tiempo_extra = dif_tiempo_extra;
    }

    public Double getDif_sueldo() {
        return dif_sueldo;
    }

    public void setDif_sueldo(Double dif_sueldo) {
        this.dif_sueldo = dif_sueldo;
    }

    public Integer getDias_pago() {
        return dias_pago;
    }

    public void setDias_pago(Integer dias_pago) {
        this.dias_pago = dias_pago;
    }

    public Integer getDias_cve_27() {
        return dias_cve_27;
    }

    public void setDias_cve_27(Integer dias_cve_27) {
        this.dias_cve_27 = dias_cve_27;
    }

}
