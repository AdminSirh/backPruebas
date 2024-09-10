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
 * @author rroscero23
 */
@Entity
@Table(name = "relacion_calculos_sdi_claves")
public class Rela_Calculos_Sdi_Cve implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_relacion;
    private String valor_campo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incidencia_id")
    private Cat_Incidencias cat_Incidencias;

    public Integer getId_relacion() {
        return id_relacion;
    }

    public void setId_relacion(Integer id_relacion) {
        this.id_relacion = id_relacion;
    }

    public String getValor_campo() {
        return valor_campo;
    }

    public void setValor_campo(String valor_campo) {
        this.valor_campo = valor_campo;
    }

    public Cat_Incidencias getCat_Incidencias() {
        return cat_Incidencias;
    }

    public void setCat_Incidencias(Cat_Incidencias cat_Incidencias) {
        this.cat_Incidencias = cat_Incidencias;
    }

}
