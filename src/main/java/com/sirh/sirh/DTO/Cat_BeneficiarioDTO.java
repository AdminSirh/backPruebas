/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 *
 * @author akalvarez19
 */

public class Cat_BeneficiarioDTO implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_tipo_beneficiario")
    private Integer id_tipo_beneficiario;
    private String desc_tipo_beneficiario;

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
    
    
}
