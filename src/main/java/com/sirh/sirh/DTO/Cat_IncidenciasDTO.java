/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.sirh.sirh.entity.Cat_Tipo_Incidencia;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author akalvarez19
 */
public class Cat_IncidenciasDTO {
    
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
    
    
    private Integer estatus;

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

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    
    
}
