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
@Table(name = "catalogo_tipo_riesgo_incapacidad")
public class Cat_Tipo_Riesgo_Incapacidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipo_riesgo;
    private String descripcion_riesgo;
    private Integer cve_original;

    public Integer getId_tipo_riesgo() {
        return id_tipo_riesgo;
    }

    public void setId_tipo_riesgo(Integer id_tipo_riesgo) {
        this.id_tipo_riesgo = id_tipo_riesgo;
    }

    public String getDescripcion_riesgo() {
        return descripcion_riesgo;
    }

    public void setDescripcion_riesgo(String descripcion_riesgo) {
        this.descripcion_riesgo = descripcion_riesgo;
    }

    public Integer getCve_original() {
        return cve_original;
    }

    public void setCve_original(Integer cve_original) {
        this.cve_original = cve_original;
    }

}
