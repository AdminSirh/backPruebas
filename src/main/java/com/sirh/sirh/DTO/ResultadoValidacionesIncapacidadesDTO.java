/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.sirh.sirh.entity.Incapacidades;
import java.time.LocalDate;
/**
 *
 * @author rroscero23
 */
public class ResultadoValidacionesIncapacidadesDTO {

    private Integer expediente;
    private LocalDate fechaInicioIncapacidad;
    private LocalDate fechaFinIncapacidad;
    private LocalDate fechaLiberacionIncapacidad;
    private boolean extemporanea;
    private Incapacidades incapacidadContinua;
    private Incapacidades incapacidadInicial;

    public ResultadoValidacionesIncapacidadesDTO() {
    }

    public ResultadoValidacionesIncapacidadesDTO(Integer expediente, LocalDate fechaInicioIncapacidad, LocalDate fechaFinIncapacidad, LocalDate fechaLiberacionIncapacidad, boolean extemporanea, Incapacidades incapacidadContinua, Incapacidades incapacidadInicial) {
        this.expediente = expediente;
        this.fechaInicioIncapacidad = fechaInicioIncapacidad;
        this.fechaFinIncapacidad = fechaFinIncapacidad;
        this.fechaLiberacionIncapacidad = fechaLiberacionIncapacidad;
        this.extemporanea = extemporanea;
        this.incapacidadContinua = incapacidadContinua;
        this.incapacidadInicial = incapacidadInicial;
    }

    public Integer getExpediente() {
        return expediente;
    }

    public void setExpediente(Integer expediente) {
        this.expediente = expediente;
    }

    public LocalDate getFechaInicioIncapacidad() {
        return fechaInicioIncapacidad;
    }

    public void setFechaInicioIncapacidad(LocalDate fechaInicioIncapacidad) {
        this.fechaInicioIncapacidad = fechaInicioIncapacidad;
    }

    public LocalDate getFechaFinIncapacidad() {
        return fechaFinIncapacidad;
    }

    public void setFechaFinIncapacidad(LocalDate fechaFinIncapacidad) {
        this.fechaFinIncapacidad = fechaFinIncapacidad;
    }

    public LocalDate getFechaLiberacionIncapacidad() {
        return fechaLiberacionIncapacidad;
    }

    public void setFechaLiberacionIncapacidad(LocalDate fechaLiberacionIncapacidad) {
        this.fechaLiberacionIncapacidad = fechaLiberacionIncapacidad;
    }

    public boolean isExtemporanea() {
        return extemporanea;
    }

    public void setExtemporanea(boolean extemporanea) {
        this.extemporanea = extemporanea;
    }

    public Incapacidades getIncapacidadContinua() {
        return incapacidadContinua;
    }

    public void setIncapacidadContinua(Incapacidades incapacidadContinua) {
        this.incapacidadContinua = incapacidadContinua;
    }

    public Incapacidades getIncapacidadInicial() {
        return incapacidadInicial;
    }

    public void setIncapacidadInicial(Incapacidades incapacidadInicial) {
        this.incapacidadInicial = incapacidadInicial;
    }

    
}
