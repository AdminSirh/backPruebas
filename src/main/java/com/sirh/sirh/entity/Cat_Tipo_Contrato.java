/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author akalvarez19
 */
@Entity 
@Table (name="catalogo_tipo_contrato")
public class Cat_Tipo_Contrato implements Serializable {
    
    protected static final String PK = "id";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_tipo_contrato")
    private Integer id_tipo_contrato;
    private String tipo_nomina;
    private String descripcion;
    private Integer estatus;

    public Cat_Tipo_Contrato(Integer id_tipo_contrato, String tipo_nomina, String descripcion, Integer estatus) {
        this.id_tipo_contrato = id_tipo_contrato;
        this.tipo_nomina = tipo_nomina;
        this.descripcion = descripcion;
        this.estatus = estatus;
    }

    public Cat_Tipo_Contrato() {
        super();
    }

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

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
}
