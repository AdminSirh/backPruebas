/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author akalvarez19
 */
@Entity
@Table(name = "catalogo_sangre")
public class Cat_Sangre implements Serializable {
    
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_sangre")
    private Integer id_sangre;
    private String tipo_sangre;
    private Integer estatus;

    public Integer getId_sangre() {
        return id_sangre;
    }

    public void setId_sangre(Integer id_sangre) {
        this.id_sangre = id_sangre;
    }

    public String getTipo_sangre() {
        return tipo_sangre;
    }

    public void setTipo_sangre(String tipo_sangre) {
        this.tipo_sangre = tipo_sangre;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
    
}
