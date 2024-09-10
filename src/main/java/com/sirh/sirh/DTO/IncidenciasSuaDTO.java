/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author rrosero23
 */
public class IncidenciasSuaDTO {

    private String num_imss;

    private LocalDate fecha_inicio;

    public IncidenciasSuaDTO() {
    }

    public IncidenciasSuaDTO(String num_imss, LocalDate fecha_inicio) {
        this.num_imss = num_imss;
        this.fecha_inicio = fecha_inicio;
    }

    public String getNum_imss() {
        return num_imss;
    }

    public void setNum_imss(String num_imss) {
        this.num_imss = num_imss;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    
}
