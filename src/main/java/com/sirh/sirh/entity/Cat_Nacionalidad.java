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
@Table(name = "catalogo_nacionalidad")
public class Cat_Nacionalidad implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_nacionalidad;    
    private String desc_nacionalidad;
    private Integer estatus;

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

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
}
