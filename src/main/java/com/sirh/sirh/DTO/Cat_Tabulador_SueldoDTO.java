/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author akalvarez19
 */

public class Cat_Tabulador_SueldoDTO implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_tabulador_sueldo")
    private Integer id_tabulador_sueldo;
    private String sueldo_mensual;

    public Integer getId_tabulador_sueldo() {
        return id_tabulador_sueldo;
    }

    public void setId_tabulador_sueldo(Integer id_tabulador_sueldo) {
        this.id_tabulador_sueldo = id_tabulador_sueldo;
    }

    public String getSueldo_mensual() {
        return sueldo_mensual;
    }

    public void setSueldo_mensual(String sueldo_mensual) {
        this.sueldo_mensual = sueldo_mensual;
    }
    
}
