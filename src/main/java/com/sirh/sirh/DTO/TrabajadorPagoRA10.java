/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rroscero23
 */
public class TrabajadorPagoRA10 {

    private String expediente;
    private Double total_semana;

    public TrabajadorPagoRA10() {
    }

    public TrabajadorPagoRA10(String expediente, Double total_semana) {
        this.expediente = expediente;
        this.total_semana = total_semana;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public Double getTotal_semana() {
        return total_semana;
    }

    public void setTotal_semana(Double total_semana) {
        this.total_semana = total_semana;
    }

}
