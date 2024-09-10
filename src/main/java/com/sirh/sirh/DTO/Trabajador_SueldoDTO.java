/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rroscero23
 */
public class Trabajador_SueldoDTO {

    private Integer idPuesto;
    private String puesto;
    private Integer nivel;
    private Double sueldo_diario;
    private Double sueldoMensual;
    private Double sueldoHora8;
    private Double sueldoHora7;

    public Trabajador_SueldoDTO() {
    }

    public Trabajador_SueldoDTO(Integer idPuesto, String puesto, Integer nivel, Double sueldo_diario, Double sueldoMensual, Double sueldoHora8, Double sueldoHora7) {
        this.idPuesto = idPuesto;
        this.puesto = puesto;
        this.nivel = nivel;
        this.sueldo_diario = sueldo_diario;
        this.sueldoMensual = sueldoMensual;
        this.sueldoHora8 = sueldoHora8;
        this.sueldoHora7 = sueldoHora7;
    }

    public Integer getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(Integer idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Double getSueldo_diario() {
        return sueldo_diario;
    }

    public void setSueldo_diario(Double sueldo_diario) {
        this.sueldo_diario = sueldo_diario;
    }

    public Double getSueldoMensual() {
        return sueldoMensual;
    }

    public void setSueldoMensual(Double sueldoMensual) {
        this.sueldoMensual = sueldoMensual;
    }

    public Double getSueldoHora8() {
        return sueldoHora8;
    }

    public void setSueldoHora8(Double sueldoHora8) {
        this.sueldoHora8 = sueldoHora8;
    }

    public Double getSueldoHora7() {
        return sueldoHora7;
    }

    public void setSueldoHora7(Double sueldoHora7) {
        this.sueldoHora7 = sueldoHora7;
    }

}
