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
@Table(name = "monto_vales")
public class Monto_Vales implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_monto_vales;
    private Integer dia_inicio;
    private Integer dia_fin;
    private Double monto_confianza;
    private Double monto_base;

    public Monto_Vales() {
        super();
    }

    public Monto_Vales(Integer id_monto_vales, Integer dia_inicio, Integer dia_fin, Double monto_confianza, Double monto_base) {
        this.id_monto_vales = id_monto_vales;
        this.dia_inicio = dia_inicio;
        this.dia_fin = dia_fin;
        this.monto_confianza = monto_confianza;
        this.monto_base = monto_base;
    }

    public Integer getId_monto_vales() {
        return id_monto_vales;
    }

    public void setId_monto_vales(Integer id_monto_vales) {
        this.id_monto_vales = id_monto_vales;
    }

    public Integer getDia_inicio() {
        return dia_inicio;
    }

    public void setDia_inicio(Integer dia_inicio) {
        this.dia_inicio = dia_inicio;
    }

    public Integer getDia_fin() {
        return dia_fin;
    }

    public void setDia_fin(Integer dia_fin) {
        this.dia_fin = dia_fin;
    }

    public Double getMonto_confianza() {
        return monto_confianza;
    }

    public void setMonto_confianza(Double monto_confianza) {
        this.monto_confianza = monto_confianza;
    }

    public Double getMonto_base() {
        return monto_base;
    }

    public void setMonto_base(Double monto_base) {
        this.monto_base = monto_base;
    }
    
    
}
