/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 *
 * @author akalvarez19
 */

public class Cat_TabuladorDTO implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    

    private Integer id_tipo_tabulador;
    private String desc_tipo_tabulador;

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
    
    
}
