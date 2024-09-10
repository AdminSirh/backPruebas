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

public class Cat_NominaDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ nomina")
    private Integer id_nomina;
    private String desc_nomina;

    public Integer getId_nomina() {
        return id_nomina;
    }

    public void setId_nomina(Integer id_nomina) {
        this.id_nomina = id_nomina;
    }

    public String getDesc_nomina() {
        return desc_nomina;
    }

    public void setDesc_nomina(String desc_nomina) {
        this.desc_nomina = desc_nomina;
    }
    
    
    
}
