/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rroscero23
 */
public class OperadorTrenDTO {

    private Integer expediente;
    private String descansos;
    private Double pagoDomingo;
    private Double pagoLunes;
    private Double pagoMartes;
    private Double pagoMiercoles;
    private Double pagoJueves;
    private Double pagoViernes;
    private Double pagoSabado;

    public OperadorTrenDTO() {
    }

    public OperadorTrenDTO(Integer expediente, String descansos, Double pagoDomingo, Double pagoLunes, Double pagoMartes, Double pagoMiercoles, Double pagoJueves, Double pagoViernes, Double pagoSabado) {
        this.expediente = expediente;
        this.descansos = descansos;
        this.pagoDomingo = pagoDomingo;
        this.pagoLunes = pagoLunes;
        this.pagoMartes = pagoMartes;
        this.pagoMiercoles = pagoMiercoles;
        this.pagoJueves = pagoJueves;
        this.pagoViernes = pagoViernes;
        this.pagoSabado = pagoSabado;
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

    public Double getPagoDomingo() {
        return pagoDomingo;
    }

    public void setPagoDomingo(Double pagoDomingo) {
        this.pagoDomingo = pagoDomingo;
    }

    public Double getPagoLunes() {
        return pagoLunes;
    }

    public void setPagoLunes(Double pagoLunes) {
        this.pagoLunes = pagoLunes;
    }

    public Double getPagoMartes() {
        return pagoMartes;
    }

    public void setPagoMartes(Double pagoMartes) {
        this.pagoMartes = pagoMartes;
    }

    public Double getPagoMiercoles() {
        return pagoMiercoles;
    }

    public void setPagoMiercoles(Double pagoMiercoles) {
        this.pagoMiercoles = pagoMiercoles;
    }

    public Double getPagoJueves() {
        return pagoJueves;
    }

    public void setPagoJueves(Double pagoJueves) {
        this.pagoJueves = pagoJueves;
    }

    public Double getPagoViernes() {
        return pagoViernes;
    }

    public void setPagoViernes(Double pagoViernes) {
        this.pagoViernes = pagoViernes;
    }

    public Double getPagoSabado() {
        return pagoSabado;
    }

    public void setPagoSabado(Double pagoSabado) {
        this.pagoSabado = pagoSabado;
    }

}
