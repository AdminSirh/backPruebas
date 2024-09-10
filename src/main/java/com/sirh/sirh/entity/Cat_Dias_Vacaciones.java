/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author oguerrero23
 */
@Entity
@Table(name = "catalogo_dias_vacaciones")
public class Cat_Dias_Vacaciones implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_dias_vacaciones;
    
    private Integer dias;
    private Integer tipo_dias_vacaciones_id;
    private Integer anios_inicio;
    private Double anios_fin;

    public Cat_Dias_Vacaciones() {
        super();
    }

    
    public Cat_Dias_Vacaciones(Integer id_dias_vacaciones, Integer dias, Integer tipo_dias_vacaciones_id, Integer anios_inicio, Double anios_fin) {
        this.id_dias_vacaciones = id_dias_vacaciones;
        this.dias = dias;
        this.tipo_dias_vacaciones_id = tipo_dias_vacaciones_id;
        this.anios_inicio = anios_inicio;
        this.anios_fin = anios_fin;
    }

    public Integer getId_dias_vacaciones() {
        return id_dias_vacaciones;
    }

    public void setId_dias_vacaciones(Integer id_dias_vacaciones) {
        this.id_dias_vacaciones = id_dias_vacaciones;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Integer getTipo_dias_vacaciones_id() {
        return tipo_dias_vacaciones_id;
    }

    public void setTipo_dias_vacaciones_id(Integer tipo_dias_vacaciones_id) {
        this.tipo_dias_vacaciones_id = tipo_dias_vacaciones_id;
    }

    public Integer getAnios_inicio() {
        return anios_inicio;
    }

    public void setAnios_inicio(Integer anios_inicio) {
        this.anios_inicio = anios_inicio;
    }

    public Double getAnios_fin() {
        return anios_fin;
    }

    public void setAnios_fin(Double anios_fin) {
        this.anios_fin = anios_fin;
    }
}
