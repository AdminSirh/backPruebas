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
@Table(name = "catalogo_estado_vacaciones")
public class Cat_Estado_Vacaciones implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_estado_vacaciones;

    private String desc_estado_vacaciones;
    private Integer estatus;

    
    
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

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

}
