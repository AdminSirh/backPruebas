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

@Entity
@Table(name = "catalogo_motivo_cancelacion")
public class Cat_Motivo_Cancelacion implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_motivo_cancelacion;
    
    private String descripcion;
    private Integer estatus;

    public Cat_Motivo_Cancelacion() {
        super();
    }

    public Cat_Motivo_Cancelacion(Integer id_motivo_cancelacion, String descripcion, Integer estatus) {
        this.id_motivo_cancelacion = id_motivo_cancelacion;
        this.descripcion = descripcion;
        this.estatus = estatus;
    }

    public Integer getId_motivo_cancelacion() {
        return id_motivo_cancelacion;
    }

    public void setId_motivo_cancelacion(Integer id_motivo_cancelacion) {
        this.id_motivo_cancelacion = id_motivo_cancelacion;
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
