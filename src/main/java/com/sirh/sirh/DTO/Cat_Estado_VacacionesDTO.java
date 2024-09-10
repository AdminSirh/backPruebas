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
public class Cat_Estado_VacacionesDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_estado_vacaciones;

    private String desc_estado_vacaciones;

    
    
    public Integer getId_estado_vacaciones() {
        return id_estado_vacaciones;
    }

    public void setId_estado_vacaciones(Integer id_estado_vacaciones) {
        this.id_estado_vacaciones = id_estado_vacaciones;
    }

    public String getDesc_estado_vacaciones() {
        return desc_estado_vacaciones;
    }

    public void setDesc_estado_vacaciones(String desc_estado_vacaciones) {
        this.desc_estado_vacaciones = desc_estado_vacaciones;
    }

}
