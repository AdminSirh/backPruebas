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
@Table(name = "catalogo_tipo_tabulador")
public class Cat_Tabulador implements Serializable{
    
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_tipo_tabulador")
    private Integer id_tipo_tabulador;
    private String desc_tipo_tabulador;
    private String clave_tipo_tabulador;
    private Integer estatus;

    public Integer getId_tipo_tabulador() {
        return id_tipo_tabulador;
    }

    public void setId_tipo_tabulador(Integer id_tipo_tabulador) {
        this.id_tipo_tabulador = id_tipo_tabulador;
    }

    public String getDesc_tipo_tabulador() {
        return desc_tipo_tabulador;
    }

    public void setDesc_tipo_tabulador(String desc_tipo_tabulador) {
        this.desc_tipo_tabulador = desc_tipo_tabulador;
    }

    public String getClave_tipo_tabulador() {
        return clave_tipo_tabulador;
    }

    public void setClave_tipo_tabulador(String clave_tipo_tabulador) {
        this.clave_tipo_tabulador = clave_tipo_tabulador;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
    
}
