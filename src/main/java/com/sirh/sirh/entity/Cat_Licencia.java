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
@Table(name = "catalogo_licencia")
public class Cat_Licencia implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_licencia;
    
    private String tipo_licencia;
    private Integer estatus;

    
    public Integer getId_licencia() {
        return id_licencia;
    }

    public void setId_licencia(Integer id_licencia) {
        this.id_licencia = id_licencia;
    }

    public String getTipo_licencia() {
        return tipo_licencia;
    }

    public void setTipo_licencia(String tipo_licencia) {
        this.tipo_licencia = tipo_licencia;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    
    
    
    
    

}
