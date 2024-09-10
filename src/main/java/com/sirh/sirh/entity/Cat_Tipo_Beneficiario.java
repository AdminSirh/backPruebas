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
 * @author nreyes22
 */

@Entity
@Table(name = "catalogo_tipo_beneficiario")
public class Cat_Tipo_Beneficiario implements Serializable {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipo_beneficiario;
    
    private String desc_tipo_beneficiario;
    private Integer estatus;
    private String desc_tipo_tabulador;

    public Cat_Tipo_Beneficiario(Integer id_tipo_beneficiario, String desc_tipo_beneficiario, Integer estatus, String desc_tipo_tabulador) {
        this.id_tipo_beneficiario = id_tipo_beneficiario;
        this.desc_tipo_beneficiario = desc_tipo_beneficiario;
        this.estatus = estatus;
        this.desc_tipo_tabulador = desc_tipo_tabulador;
    }

    public Cat_Tipo_Beneficiario() {
    }

    
    
    
    public Integer getId_tipo_beneficiario() {
        return id_tipo_beneficiario;
    }

    public void setId_tipo_beneficiario(Integer id_tipo_beneficiario) {
        this.id_tipo_beneficiario = id_tipo_beneficiario;
    }

    public String getDesc_tipo_beneficiario() {
        return desc_tipo_beneficiario;
    }

    public void setDesc_tipo_beneficiario(String desc_tipo_beneficiario) {
        this.desc_tipo_beneficiario = desc_tipo_beneficiario;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getDesc_tipo_tabulador() {
        return desc_tipo_tabulador;
    }

    public void setDesc_tipo_tabulador(String desc_tipo_tabulador) {
        this.desc_tipo_tabulador = desc_tipo_tabulador;
    }


    

}
