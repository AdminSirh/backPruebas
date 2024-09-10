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
@Table(name = "catalogo_depositos")
public class Cat_Depositos implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_deposito;
    
    private String desc_deposito;
    private Integer estatus;

    public Cat_Depositos(Integer id_deposito, String desc_deposito, Integer estatus) {
        this.id_deposito = id_deposito;
        this.desc_deposito = desc_deposito;
        this.estatus = estatus;
    }

    public Cat_Depositos() {
    }

    public Integer getId_deposito() {
        return id_deposito;
    }

    public void setId_deposito(Integer id_deposito) {
        this.id_deposito = id_deposito;
    }

    public String getDesc_deposito() {
        return desc_deposito;
    }

    public void setDesc_deposito(String desc_deposito) {
        this.desc_deposito = desc_deposito;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

   
    
    
    
}
