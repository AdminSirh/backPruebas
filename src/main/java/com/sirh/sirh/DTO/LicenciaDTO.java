/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author dguerrero18
 */
public class LicenciaDTO {

    private Integer id_licencia;
    private Integer tipo_licencia_id;
    private String num_licencia;
    private Integer persona_id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate inicio_vig_licencia;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fin_vig_licencia;

    public LicenciaDTO() {
    }

    public LicenciaDTO(Integer id_licencia, Integer tipo_licencia_id, String num_licencia, Integer persona_id, LocalDate inicio_vig_licencia, LocalDate fin_vig_licencia) {
        this.id_licencia = id_licencia;
        this.tipo_licencia_id = tipo_licencia_id;
        this.num_licencia = num_licencia;
        this.persona_id = persona_id;
        this.inicio_vig_licencia = inicio_vig_licencia;
        this.fin_vig_licencia = fin_vig_licencia;
    }

    public Integer getId_licencia() {
        return id_licencia;
    }

    public void setId_licencia(Integer id_licencia) {
        this.id_licencia = id_licencia;
    }

    public Integer getTipo_licencia_id() {
        return tipo_licencia_id;
    }

    public void setTipo_licencia_id(Integer tipo_licencia_id) {
        this.tipo_licencia_id = tipo_licencia_id;
    }

    public String getNum_licencia() {
        return num_licencia;
    }

    public void setNum_licencia(String num_licencia) {
        this.num_licencia = num_licencia;
    }

    public Integer getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(Integer persona_id) {
        this.persona_id = persona_id;
    }

    public LocalDate getInicio_vig_licencia() {
        return inicio_vig_licencia;
    }

    public void setInicio_vig_licencia(LocalDate inicio_vig_licencia) {
        this.inicio_vig_licencia = inicio_vig_licencia;
    }

    public LocalDate getFin_vig_licencia() {
        return fin_vig_licencia;
    }

    public void setFin_vig_licencia(LocalDate fin_vig_licencia) {
        this.fin_vig_licencia = fin_vig_licencia;
    }

    
}
