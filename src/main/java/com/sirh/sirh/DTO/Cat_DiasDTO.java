
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author akalvarez19
 */
public class Cat_DiasDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Integer id_dia;
    private String dia;
    private Integer estatus;


    public Integer getId_dia() {
        return id_dia;
    }

    public void setId_dia(Integer id_dia) {
        this.id_dia = id_dia;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
    
}
