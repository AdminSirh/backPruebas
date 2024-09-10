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
@Table(name = "catalogo_tipo_incapacidad")
public class Cat_Tipo_Incapacidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipo_incapacidad;
    private String descripcion_tipo_incapacidad;
    private String inicial_tipo_incapacidad;

    public Integer getId_tipo_incapacidad() {
        return id_tipo_incapacidad;
    }

    public void setId_tipo_incapacidad(Integer id_tipo_incapacidad) {
        this.id_tipo_incapacidad = id_tipo_incapacidad;
    }

    public String getDescripcion_tipo_incapacidad() {
        return descripcion_tipo_incapacidad;
    }

    public void setDescripcion_tipo_incapacidad(String descripcion_tipo_incapacidad) {
        this.descripcion_tipo_incapacidad = descripcion_tipo_incapacidad;
    }

    public String getInicial_tipo_incapacidad() {
        return inicial_tipo_incapacidad;
    }

    public void setInicial_tipo_incapacidad(String inicial_tipo_incapacidad) {
        this.inicial_tipo_incapacidad = inicial_tipo_incapacidad;
    }

}
