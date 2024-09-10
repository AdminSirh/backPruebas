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
@Table(name = "catalogo_tipo_secuelas_incapacidad")
public class Cat_Tipo_Secuelas_Incapacidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipo_secuela;
    private String descripcion;
    private Integer cve_original;

    public Integer getId_tipo_secuela() {
        return id_tipo_secuela;
    }

    public void setId_tipo_secuela(Integer id_tipo_secuela) {
        this.id_tipo_secuela = id_tipo_secuela;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCve_original() {
        return cve_original;
    }

    public void setCve_original(Integer cve_original) {
        this.cve_original = cve_original;
    }

}
