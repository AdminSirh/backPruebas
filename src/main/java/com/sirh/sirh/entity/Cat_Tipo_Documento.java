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
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author jarellano22
 */
@Entity
@Table(name = "catalogo_tipo_documento")
public class Cat_Tipo_Documento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipo_documento;
    @NotEmpty
    private String documento;
    @NotEmpty
    private Integer estatus;

    public Cat_Tipo_Documento(Integer id_tipo_documento, String documento, Integer estatus) {
        this.id_tipo_documento = id_tipo_documento;
        this.documento = documento;
        this.estatus = estatus;
    }

    public Cat_Tipo_Documento() {
        super();
    }

    public Integer getId_tipo_documento() {
        return id_tipo_documento;
    }

    public void setId_tipo_documento(Integer id_tipo_documento) {
        this.id_tipo_documento = id_tipo_documento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

}
