/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import java.io.Serializable;

/**
 *
 * @author akalvarez19
 */
public class HorarioDTO implements Serializable {
    private Integer id_libro;
    private Integer tipo_clave_id;
    private Integer anio;
    private Integer trabajador_id;
    private Integer estatus;

    public HorarioDTO(Integer id_libro, Integer tipo_clave_id, Integer anio, Integer trabajador_id, Integer estatus) {
        this.id_libro = id_libro;
        this.tipo_clave_id = tipo_clave_id;
        this.anio = anio;
        this.trabajador_id = trabajador_id;
        this.estatus = estatus;
    }

    public Integer getId_libro() {
        return id_libro;
    }

    public void setId_libro(Integer id_libro) {
        this.id_libro = id_libro;
    }

    public Integer getTipo_clave_id() {
        return tipo_clave_id;
    }

    public void setTipo_clave_id(Integer tipo_clave_id) {
        this.tipo_clave_id = tipo_clave_id;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
    
}
