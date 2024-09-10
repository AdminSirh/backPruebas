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
public class Cat_NacionalidadDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_nacionalidad;

    private String desc_nacionalidad;

    public Integer getId_nacionalidad() {
        return id_nacionalidad;
    }

    public void setId_nacionalidad(Integer id_nacionalidad) {
        this.id_nacionalidad = id_nacionalidad;
    }

    public String getDesc_nacionalidad() {
        return desc_nacionalidad;
    }

    public void setDesc_nacionalidad(String desc_nacionalidad) {
        this.desc_nacionalidad = desc_nacionalidad;
    }

}
