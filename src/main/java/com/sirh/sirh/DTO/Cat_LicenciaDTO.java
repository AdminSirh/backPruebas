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
public class Cat_LicenciaDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_licencia;
    
    private String tipo_licencia;

    
    
    public Integer getId_licencia() {
        return id_licencia;
    }

    public void setId_licencia(Integer id_licencia) {
        this.id_licencia = id_licencia;
    }

    public String getTipo_licencia() {
        return tipo_licencia;
    }

    public void setTipo_licencia(String tipo_licencia) {
        this.tipo_licencia = tipo_licencia;
    }

    
    
}
