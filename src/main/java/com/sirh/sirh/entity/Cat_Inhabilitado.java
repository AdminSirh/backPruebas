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
@Table(name = "catalogo_inhabilitado")
public class Cat_Inhabilitado implements Serializable{
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_inhabilitado;
    
    private String inhabilitado;
    private Integer estatus;

    public Integer getId_inhabilitado() {
        return id_inhabilitado;
    }

    public void setId_inhabilitado(Integer id_inhabilitado) {
        this.id_inhabilitado = id_inhabilitado;
    }

    public String getInhabilitado() {
        return inhabilitado;
    }

    public void setInhabilitado(String inhabilitado) {
        this.inhabilitado = inhabilitado;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    
    
    
  }
