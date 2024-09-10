/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author rroscero23
 */
public class Semanas_Periodos_PagoDTO {

    private LocalDate fecha_inicial_semana;
    private LocalDate fecha_final_semana;

    public Semanas_Periodos_PagoDTO() {
    }

    public Semanas_Periodos_PagoDTO(LocalDate fecha_inicial_semana, LocalDate fecha_final_semana) {
        this.fecha_inicial_semana = fecha_inicial_semana;
        this.fecha_final_semana = fecha_final_semana;
    }

    public LocalDate getFecha_inicial_semana() {
        return fecha_inicial_semana;
    }

    public void setFecha_inicial_semana(LocalDate fecha_inicial_semana) {
        this.fecha_inicial_semana = fecha_inicial_semana;
    }

    public LocalDate getFecha_final_semana() {
        return fecha_final_semana;
    }

    public void setFecha_final_semana(LocalDate fecha_final_semana) {
        this.fecha_final_semana = fecha_final_semana;
    }

    
}
