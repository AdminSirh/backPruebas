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
@Table(name = "catalogo_motivo_incapacidad")
public class Cat_Motivo_Incapacidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_motivo_incapacidad;
    private String descripcion_incapacidad;
    private String inicial_motivo_incapacidad;

    public Integer getId_motivo_incapacidad() {
        return id_motivo_incapacidad;
    }

    public void setId_motivo_incapacidad(Integer id_motivo_incapacidad) {
        this.id_motivo_incapacidad = id_motivo_incapacidad;
    }

    public String getDescripcion_incapacidad() {
        return descripcion_incapacidad;
    }

    public void setDescripcion_incapacidad(String descripcion_incapacidad) {
        this.descripcion_incapacidad = descripcion_incapacidad;
    }

    public String getInicial_motivo_incapacidad() {
        return inicial_motivo_incapacidad;
    }

    public void setInicial_motivo_incapacidad(String inicial_motivo_incapacidad) {
        this.inicial_motivo_incapacidad = inicial_motivo_incapacidad;
    }

}
