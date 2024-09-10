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

@Entity
@Table(name = "catalogo_credito_infonavit")
public class Cat_Credito_Infonavit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_credito;

    private String credito_infonavit;
    private Integer estatus;

    
    
    public Integer getId_credito() {
        return id_credito;
    }

    public void setId_credito(Integer id_credito) {
        this.id_credito = id_credito;
    }

    public String getCredito_infonavit() {
        return credito_infonavit;
    }

    public void setCredito_infonavit(String credito_infonavit) {
        this.credito_infonavit = credito_infonavit;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

}
