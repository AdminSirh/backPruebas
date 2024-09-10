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
@Table(name = "catalogo_tipo_origen_documento")
public class Cat_Tipo_Origen_Documento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id_tipo_origen_documento;

    private String descripcion;

    private Integer estatus;

    public Integer getId_tipo_origen_documento() {
        return id_tipo_origen_documento;
    }

    public void setId_tipo_origen_documento(Integer id_tipo_origen_documento) {
        this.id_tipo_origen_documento = id_tipo_origen_documento;
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
