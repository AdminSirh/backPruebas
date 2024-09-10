/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author nreyes22
 */
public class Cat_GeneroDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id_genero;
    private String desc_genero;

    
    
    public Integer getId_genero() {
        return id_genero;
    }

    public void setId_genero(Integer id_genero) {
        this.id_genero = id_genero;
    }

    public String getDesc_genero() {
        return desc_genero;
    }

    public void setDesc_genero(String desc_genero) {
        this.desc_genero = desc_genero;
    }

}
