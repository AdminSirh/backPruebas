/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author nreyes22
 */

@Entity
@Table(name = "catalogo_municipio")
public class Cat_Municipio implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_municipio;
    
    private String desc_municipio;
    private Integer estatus;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado")
    private Cat_Estado cat_estado;
    

    public Integer getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(Integer id_municipio) {
        this.id_municipio = id_municipio;
    }

    public String getDesc_municipio() {
        return desc_municipio;
    }

    public void setDesc_municipio(String desc_municipio) {
        this.desc_municipio = desc_municipio;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Cat_Estado getCat_estado() {
        return cat_estado;
    }

    public void setCat_estado(Cat_Estado cat_estado) {
        this.cat_estado = cat_estado;
    }

  
    
}
