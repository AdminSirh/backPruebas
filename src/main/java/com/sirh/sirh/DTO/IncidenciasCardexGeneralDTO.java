/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rrosero23
 */
public class IncidenciasCardexGeneralDTO {

    private String inicial_descripcion;
    private Integer id_trabajador;
    private String numero_expediente;
    private Integer cantidad_incidencias;

    public IncidenciasCardexGeneralDTO() {

    }

    public IncidenciasCardexGeneralDTO(String inicial_descripcion, Integer id_trabajador, String numero_expediente, Integer cantidad_incidencias) {
        this.inicial_descripcion = inicial_descripcion;
        this.id_trabajador = id_trabajador;
        this.numero_expediente = numero_expediente;
        this.cantidad_incidencias = cantidad_incidencias;
    }

    public String getInicial_descripcion() {
        return inicial_descripcion;
    }

    public void setInicial_descripcion(String inicial_descripcion) {
        this.inicial_descripcion = inicial_descripcion;
    }

    public Integer getId_trabajador() {
        return id_trabajador;
    }

    public void setId_trabajador(Integer id_trabajador) {
        this.id_trabajador = id_trabajador;
    }

    public String getNumero_expediente() {
        return numero_expediente;
    }

    public void setNumero_expediente(String numero_expediente) {
        this.numero_expediente = numero_expediente;
    }

    public Integer getCantidad_incidencias() {
        return cantidad_incidencias;
    }

    public void setCantidad_incidencias(Integer cantidad_incidencias) {
        this.cantidad_incidencias = cantidad_incidencias;
    }

}
