/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author rrosero23
 */
public class Cat_Tipo_ContratoDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name="id_tipo_contrato")
    private Integer id_tipo_contrato; 
    private String tipo_nomina; 
    private String descripcion;

    public Integer getId_tipo_contrato() {
        return id_tipo_contrato;
    }

    public void setId_tipo_contrato(Integer id_tipo_contrato) {
        this.id_tipo_contrato = id_tipo_contrato;
    }

    public String getTipo_nomina() {
        return tipo_nomina;
    }

    public void setTipo_nomina(String tipo_nomina) {
        this.tipo_nomina = tipo_nomina;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
