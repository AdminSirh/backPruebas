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
 * @author nreyes22
 */
public class Cat_InhabilitadoDTO {
   
    private Integer id_inhabilitado;
    private String inhabilitado;

    
    
    public Integer getId_inhabilitado() {
        return id_inhabilitado;
    }

    public void setId_inhabilitado(Integer id_inhabilitado) {
        this.id_inhabilitado = id_inhabilitado;
    }

    public String getInhabilitado() {
        return inhabilitado;
    }

    public void setInhabilitado(String inhabilitado) {
        this.inhabilitado = inhabilitado;
    }

   
    
}
