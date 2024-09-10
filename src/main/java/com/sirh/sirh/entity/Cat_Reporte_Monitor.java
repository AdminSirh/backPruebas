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
@Table (name = "catalogo_reporte_monitor")
public class Cat_Reporte_Monitor implements Serializable{
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    
    private Integer id_reporte_monitor; 
    
    private String descripcion; 
    
    private Integer estatus; 

    public Integer getId_reporte_monitor() {
        return id_reporte_monitor;
    }

    public void setId_reporte_monitor(Integer id_reporte_monitor) {
        this.id_reporte_monitor = id_reporte_monitor;
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
