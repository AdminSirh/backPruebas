/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.entity;

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
@Table(name = "catalogo_genero")
public class Cat_Genero implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_genero;

    private String desc_genero;
    private Integer estatus;

    
    
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

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

}
