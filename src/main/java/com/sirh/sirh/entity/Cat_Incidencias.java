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
 * @author akalvarez19
 */
@Entity
@Table(name = "catalogo_incidencias")
public class Cat_Incidencias implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_incidencia;

    private String descripcion;

    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_incidencia_id")
    private Cat_Tipo_Incidencia cat_tipo_incidencia;

    private Integer coa_id;

    private String inicial_descripcion;

    private Integer valor_binario;

    private Integer inoperable;
    
    private Integer estatus;

    private Double valor_variable;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_dato_id")
    private Cat_Tipo_Dato_Incidencias cat_tipo_dato_incidencias;

    private String color_kardex;

    private Integer estatus_kardex;

    private Integer cve_nomina;

    private String cve_sat;

    private String propiedad;

    private String descripcion_propiedad;

    private String respaldo_legal;

    private Integer limite_percepciones;

    public Integer getId_incidencia() {
        return id_incidencia;
    }

    public void setId_incidencia(Integer id_incidencia) {
        this.id_incidencia = id_incidencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cat_Tipo_Incidencia getCat_tipo_incidencia() {
        return cat_tipo_incidencia;
    }

    public void setCat_tipo_incidencia(Cat_Tipo_Incidencia cat_tipo_incidencia) {
        this.cat_tipo_incidencia = cat_tipo_incidencia;
    }

    public Integer getCoa_id() {
        return coa_id;
    }

    public void setCoa_id(Integer coa_id) {
        this.coa_id = coa_id;
    }

    public String getInicial_descripcion() {
        return inicial_descripcion;
    }

    public void setInicial_descripcion(String inicial_descripcion) {
        this.inicial_descripcion = inicial_descripcion;
    }

    public Integer getValor_binario() {
        return valor_binario;
    }

    public void setValor_binario(Integer valor_binario) {
        this.valor_binario = valor_binario;
    }

    public Integer getInoperable() {
        return inoperable;
    }

    public void setInoperable(Integer inoperable) {
        this.inoperable = inoperable;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Double getValor_variable() {
        return valor_variable;
    }

    public void setValor_variable(Double valor_variable) {
        this.valor_variable = valor_variable;
    }

    public Cat_Tipo_Dato_Incidencias getCat_tipo_dato_incidencias() {
        return cat_tipo_dato_incidencias;
    }

    public void setCat_tipo_dato_incidencias(Cat_Tipo_Dato_Incidencias cat_tipo_dato_incidencias) {
        this.cat_tipo_dato_incidencias = cat_tipo_dato_incidencias;
    }

    public String getColor_kardex() {
        return color_kardex;
    }

    public void setColor_kardex(String color_kardex) {
        this.color_kardex = color_kardex;
    }

    public Integer getEstatus_kardex() {
        return estatus_kardex;
    }

    public void setEstatus_kardex(Integer estatus_kardex) {
        this.estatus_kardex = estatus_kardex;
    }

    public Integer getCve_nomina() {
        return cve_nomina;
    }

    public void setCve_nomina(Integer cve_nomina) {
        this.cve_nomina = cve_nomina;
    }

    public String getCve_sat() {
        return cve_sat;
    }

    public void setCve_sat(String cve_sat) {
        this.cve_sat = cve_sat;
    }

    public String getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(String propiedad) {
        this.propiedad = propiedad;
    }

    public String getDescripcion_propiedad() {
        return descripcion_propiedad;
    }

    public void setDescripcion_propiedad(String descripcion_propiedad) {
        this.descripcion_propiedad = descripcion_propiedad;
    }

    public String getRespaldo_legal() {
        return respaldo_legal;
    }

    public void setRespaldo_legal(String respaldo_legal) {
        this.respaldo_legal = respaldo_legal;
    }

    public Integer getLimite_percepciones() {
        return limite_percepciones;
    }

    public void setLimite_percepciones(Integer limite_percepciones) {
        this.limite_percepciones = limite_percepciones;
    }

}
