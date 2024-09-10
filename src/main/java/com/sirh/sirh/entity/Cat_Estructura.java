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
@Table(name = "catalogo_estructura")
public class Cat_Estructura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_estructura;

    private String desc_estructura;
    private Integer estatus;

    
    
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

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

}
