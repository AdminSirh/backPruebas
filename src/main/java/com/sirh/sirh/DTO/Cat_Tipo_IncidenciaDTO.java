/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author akalvarez19
 */
public class Cat_Tipo_IncidenciaDTO {

    private Integer id_tipo_incidencia;
    private String descripcion;
    private Integer estatus;

    public Integer getId_tipo_incidencia() {
        return id_tipo_incidencia;
    }

    public void setId_tipo_incidencia(Integer id_tipo_incidencia) {
        this.id_tipo_incidencia = id_tipo_incidencia;
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
