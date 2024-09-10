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

/**
 *
 * @author rrosero23
 */
@Entity
@Table(name = "percepciones_por_nomina")
public class Percepciones_Por_Nomina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id_percepcion_nomina;

    private Integer tipo_nomina_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incidencia_id")
    private Cat_Incidencias cat_Incidencias;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_origen_documento_id")
    private Cat_Tipo_Origen_Documento cat_Tipo_Origen_Documento;

    public Integer getId_percepcion_nomina() {
        return id_percepcion_nomina;
    }

    public void setId_percepcion_nomina(Integer id_percepcion_nomina) {
        this.id_percepcion_nomina = id_percepcion_nomina;
    }

    public Integer getTipo_nomina_id() {
        return tipo_nomina_id;
    }

    public void setTipo_nomina_id(Integer tipo_nomina_id) {
        this.tipo_nomina_id = tipo_nomina_id;
    }

    public Cat_Incidencias getCat_Incidencias() {
        return cat_Incidencias;
    }

    public void setCat_Incidencias(Cat_Incidencias cat_Incidencias) {
        this.cat_Incidencias = cat_Incidencias;
    }

    public Cat_Tipo_Origen_Documento getCat_Tipo_Origen_Documento() {
        return cat_Tipo_Origen_Documento;
    }

    public void setCat_Tipo_Origen_Documento(Cat_Tipo_Origen_Documento cat_Tipo_Origen_Documento) {
        this.cat_Tipo_Origen_Documento = cat_Tipo_Origen_Documento;
    }

}
