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
@Table(name = "catalogo_edo_civil")
public class Cat_Edo_Civil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_edo_civil;

    private String desc_edo_civil;
    private Integer estatus;

    
    
    public Integer getId_edo_civil() {
        return id_edo_civil;
    }

    public void setId_edo_civil(Integer id_edo_civil) {
        this.id_edo_civil = id_edo_civil;
    }

    public String getDesc_edo_civil() {
        return desc_edo_civil;
    }

    public void setDesc_edo_civil(String desc_edo_civil) {
        this.desc_edo_civil = desc_edo_civil;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

}
