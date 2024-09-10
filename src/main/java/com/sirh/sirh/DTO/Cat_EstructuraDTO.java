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
public class Cat_EstructuraDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_estructura;
    private String desc_estructura;

    
    
    public Integer getId_estructura() {
        return id_estructura;
    }

    public void setId_estructura(Integer id_estructura) {
        this.id_estructura = id_estructura;
    }

    public String getDesc_estructura() {
        return desc_estructura;
    }

    public void setDesc_estructura(String desc_estructura) {
        this.desc_estructura = desc_estructura;
    }

}
