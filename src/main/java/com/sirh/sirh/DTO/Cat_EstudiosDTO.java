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
public class Cat_EstudiosDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_grado;

    private String desc_grado;

    public Integer getId_grado() {
        return id_grado;
    }

    public void setId_grado(Integer id_grado) {
        this.id_grado = id_grado;
    }

    public String getDesc_grado() {
        return desc_grado;
    }

    public void setDesc_grado(String desc_grado) {
        this.desc_grado = desc_grado;
    }

}
