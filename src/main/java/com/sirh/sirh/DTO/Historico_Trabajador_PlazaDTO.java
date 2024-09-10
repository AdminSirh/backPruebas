/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author oguerrero23
 */
public class Historico_Trabajador_PlazaDTO {
    private Integer id_historico_trabajador_plaza;
    
    private Integer trabajador_plaza_id;
    private Integer plaza_id;
    private Integer areas_id;
    private Integer tipo_contrato_id;
    private Integer tipo_nomina_id;
    private Integer ubicacion_id;
    
    @CreatedDate
    private LocalDate fecha_registro;

    public Historico_Trabajador_PlazaDTO(Integer id_historico_trabajador_plaza, Integer trabajador_plaza_id, Integer plaza_id, Integer areas_id, Integer tipo_contrato_id, Integer tipo_nomina_id, Integer ubicacion_id, LocalDate fecha_registro) {
        this.id_historico_trabajador_plaza = id_historico_trabajador_plaza;
        this.trabajador_plaza_id = trabajador_plaza_id;
        this.plaza_id = plaza_id;
        this.areas_id = areas_id;
        this.tipo_contrato_id = tipo_contrato_id;
        this.tipo_nomina_id = tipo_nomina_id;
        this.ubicacion_id = ubicacion_id;
        this.fecha_registro = fecha_registro;
    }

    public Integer getId_historico_trabajador_plaza() {
        return id_historico_trabajador_plaza;
    }

    public void setId_historico_trabajador_plaza(Integer id_historico_trabajador_plaza) {
        this.id_historico_trabajador_plaza = id_historico_trabajador_plaza;
    }

    public Integer getTrabajador_plaza_id() {
        return trabajador_plaza_id;
    }

    public void setTrabajador_plaza_id(Integer trabajador_plaza_id) {
        this.trabajador_plaza_id = trabajador_plaza_id;
    }

    public Integer getPlaza_id() {
        return plaza_id;
    }

    public void setPlaza_id(Integer plaza_id) {
        this.plaza_id = plaza_id;
    }

    public Integer getAreas_id() {
        return areas_id;
    }

    public void setAreas_id(Integer areas_id) {
        this.areas_id = areas_id;
    }

    public Integer getTipo_contrato_id() {
        return tipo_contrato_id;
    }

    public void setTipo_contrato_id(Integer tipo_contrato_id) {
        this.tipo_contrato_id = tipo_contrato_id;
    }

    public Integer getTipo_nomina_id() {
        return tipo_nomina_id;
    }

    public void setTipo_nomina_id(Integer tipo_nomina_id) {
        this.tipo_nomina_id = tipo_nomina_id;
    }

    public Integer getUbicacion_id() {
        return ubicacion_id;
    }

    public void setUbicacion_id(Integer ubicacion_id) {
        this.ubicacion_id = ubicacion_id;
    }

    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    
}
