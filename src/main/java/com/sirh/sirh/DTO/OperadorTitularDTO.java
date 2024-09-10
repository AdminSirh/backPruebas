/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rroscero23
 */
public class OperadorTitularDTO {

    private Integer expediente;
    private String descansos;
    private Double entrada_semana;
    private Double salida_semana;
    private Double horas_trab_semana;
    private Double pago_semana;
    private Double entrada_sabado;
    private Double salida_sabado;
    private Double horas_trab_sabado;
    private Double pago_sabado;
    private Double entra_domingo;
    private Double salida_domingo;
    private Double horas_trab_domingo;
    private Double pago_domingo;

    public OperadorTitularDTO() {
    }

    public OperadorTitularDTO(Integer expediente, String descansos, Double entrada_semana, Double salida_semana, Double horas_trab_semana, Double pago_semana, Double entrada_sabado, Double salida_sabado, Double horas_trab_sabado, Double pago_sabado, Double entra_domingo, Double salida_domingo, Double horas_trab_domingo, Double pago_domingo) {
        this.expediente = expediente;
        this.descansos = descansos;
        this.entrada_semana = entrada_semana;
        this.salida_semana = salida_semana;
        this.horas_trab_semana = horas_trab_semana;
        this.pago_semana = pago_semana;
        this.entrada_sabado = entrada_sabado;
        this.salida_sabado = salida_sabado;
        this.horas_trab_sabado = horas_trab_sabado;
        this.pago_sabado = pago_sabado;
        this.entra_domingo = entra_domingo;
        this.salida_domingo = salida_domingo;
        this.horas_trab_domingo = horas_trab_domingo;
        this.pago_domingo = pago_domingo;
    }

    public Integer getExpediente() {
        return expediente;
    }

    public void setExpediente(Integer expediente) {
        this.expediente = expediente;
    }

    public String getDescansos() {
        return descansos;
    }

    public void setDescansos(String descansos) {
        this.descansos = descansos;
    }

    public Double getEntrada_semana() {
        return entrada_semana;
    }

    public void setEntrada_semana(Double entrada_semana) {
        this.entrada_semana = entrada_semana;
    }

    public Double getSalida_semana() {
        return salida_semana;
    }

    public void setSalida_semana(Double salida_semana) {
        this.salida_semana = salida_semana;
    }

    public Double getHoras_trab_semana() {
        return horas_trab_semana;
    }

    public void setHoras_trab_semana(Double horas_trab_semana) {
        this.horas_trab_semana = horas_trab_semana;
    }

    public Double getPago_semana() {
        return pago_semana;
    }

    public void setPago_semana(Double pago_semana) {
        this.pago_semana = pago_semana;
    }

    public Double getEntrada_sabado() {
        return entrada_sabado;
    }

    public void setEntrada_sabado(Double entrada_sabado) {
        this.entrada_sabado = entrada_sabado;
    }

    public Double getSalida_sabado() {
        return salida_sabado;
    }

    public void setSalida_sabado(Double salida_sabado) {
        this.salida_sabado = salida_sabado;
    }

    public Double getHoras_trab_sabado() {
        return horas_trab_sabado;
    }

    public void setHoras_trab_sabado(Double horas_trab_sabado) {
        this.horas_trab_sabado = horas_trab_sabado;
    }

    public Double getPago_sabado() {
        return pago_sabado;
    }

    public void setPago_sabado(Double pago_sabado) {
        this.pago_sabado = pago_sabado;
    }

    public Double getEntra_domingo() {
        return entra_domingo;
    }

    public void setEntra_domingo(Double entra_domingo) {
        this.entra_domingo = entra_domingo;
    }

    public Double getSalida_domingo() {
        return salida_domingo;
    }

    public void setSalida_domingo(Double salida_domingo) {
        this.salida_domingo = salida_domingo;
    }

    public Double getHoras_trab_domingo() {
        return horas_trab_domingo;
    }

    public void setHoras_trab_domingo(Double horas_trab_domingo) {
        this.horas_trab_domingo = horas_trab_domingo;
    }

    public Double getPago_domingo() {
        return pago_domingo;
    }

    public void setPago_domingo(Double pago_domingo) {
        this.pago_domingo = pago_domingo;
    }

}
