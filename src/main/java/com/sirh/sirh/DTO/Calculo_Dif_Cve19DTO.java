/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rroscero23
 */
public class Calculo_Dif_Cve19DTO {

    Integer id_trabajador;
    Integer periodo_generacion;
    Double sueldo_hora_suplencia;
    Double sueldo_hora_actual;
    Double total_horas_dobles;
    Double diferencia_prima_dominical;
    Double diferencia_pagar_tiempo_extra;

    public Integer getId_trabajador() {
        return id_trabajador;
    }

    public void setId_trabajador(Integer id_trabajador) {
        this.id_trabajador = id_trabajador;
    }

    public Integer getPeriodo_generacion() {
        return periodo_generacion;
    }

    public void setPeriodo_generacion(Integer periodo_generacion) {
        this.periodo_generacion = periodo_generacion;
    }

    public Double getSueldo_hora_suplencia() {
        return sueldo_hora_suplencia;
    }

    public void setSueldo_hora_suplencia(Double sueldo_hora_suplencia) {
        this.sueldo_hora_suplencia = sueldo_hora_suplencia;
    }

    public Double getSueldo_hora_actual() {
        return sueldo_hora_actual;
    }

    public void setSueldo_hora_actual(Double sueldo_hora_actual) {
        this.sueldo_hora_actual = sueldo_hora_actual;
    }

    public Double getTotal_horas_dobles() {
        return total_horas_dobles;
    }

    public void setTotal_horas_dobles(Double total_horas_dobles) {
        this.total_horas_dobles = total_horas_dobles;
    }

    public Double getDiferencia_prima_dominical() {
        return diferencia_prima_dominical;
    }

    public void setDiferencia_prima_dominical(Double diferencia_prima_dominical) {
        this.diferencia_prima_dominical = diferencia_prima_dominical;
    }

    public Double getDiferencia_pagar_tiempo_extra() {
        return diferencia_pagar_tiempo_extra;
    }

    public void setDiferencia_pagar_tiempo_extra(Double diferencia_pagar_tiempo_extra) {
        this.diferencia_pagar_tiempo_extra = diferencia_pagar_tiempo_extra;
    }

}
