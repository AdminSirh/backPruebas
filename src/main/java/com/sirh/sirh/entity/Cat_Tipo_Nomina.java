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
 * @author oguerrero23
 */
@Entity
@Table(name="catalogo_tipo_nomina")
public class Cat_Tipo_Nomina implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipo_nomina;
    
    private String desc_nomina;
    private String descripcion;
    private Integer estatus;
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_tabulador_id")
    private Cat_Tabulador cat_Tipo_Tabulador;

    public Cat_Tipo_Nomina() {
        super();
    }

    public Cat_Tipo_Nomina(Cat_Tabulador cat_Tipo_Tabulador, Integer id_tipo_nomina, String desc_nomina, String descripcion, Integer estatus) {
        this.id_tipo_nomina = id_tipo_nomina;
        this.desc_nomina = desc_nomina;
        this.descripcion = descripcion;
        this.estatus = estatus;
        this.cat_Tipo_Tabulador = cat_Tipo_Tabulador;
    }

    public Cat_Tabulador getCat_Tipo_Tabulador() {
        return cat_Tipo_Tabulador;
    }

    public void setCat_Tipo_Tabulador(Cat_Tabulador cat_Tipo_Tabulador) {
        this.cat_Tipo_Tabulador = cat_Tipo_Tabulador;
    }
    
    public Integer getId_tipo_nomina() {
        return id_tipo_nomina;
    }

    public void setId_tipo_nomina(Integer id_tipo_nomina) {
        this.id_tipo_nomina = id_tipo_nomina;
    }

    public String getDesc_nomina() {
        return desc_nomina;
    }

    public void setDesc_nomina(String desc_nomina) {
        this.desc_nomina = desc_nomina;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
    
    
}
