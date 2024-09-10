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
 * @author rrosero23
 */
@Entity
@Table (name = "catalogo_tipo_credito")
public class Cat_Tipo_Credito implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Integer id_tipo_credito; 
    private String descripcion; 

    public Integer getId_tipo_credito() {
        return id_tipo_credito;
    }

    public void setId_tipo_credito(Integer id_tipo_credito) {
        this.id_tipo_credito = id_tipo_credito;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
