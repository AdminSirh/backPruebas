/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author rrosero23
 */
public class IncapacidadesDTO {

    private String num_imss;

    private LocalDateTime fecha_inicial;

    private String folio;

    private Integer dias_incapacidad;

    public IncapacidadesDTO() {

    }

    public IncapacidadesDTO(String num_imss, LocalDateTime fecha_inicial, String folio, Integer dias_incapacidad) {
        this.num_imss = num_imss;
        this.fecha_inicial = fecha_inicial;
        this.folio = folio;
        this.dias_incapacidad = dias_incapacidad;
    }

    public String getNum_imss() {
        return num_imss;
    }

    public void setNum_imss(String num_imss) {
        this.num_imss = num_imss;
    }

    public LocalDateTime getFecha_inicial() {
        return fecha_inicial;
    }

    public void setFecha_inicial(LocalDateTime fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Integer getDias_incapacidad() {
        return dias_incapacidad;
    }

    public void setDias_incapacidad(Integer dias_incapacidad) {
        this.dias_incapacidad = dias_incapacidad;
    }

    

}
