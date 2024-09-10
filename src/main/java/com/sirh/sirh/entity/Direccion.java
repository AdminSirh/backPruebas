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
 * @author nreyes22
 */

@Entity
@Table(name = "direccion")
public class Direccion implements Serializable {
    
     protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_direccion;
    
    private String calle;
    private String num_exterior;
    private String num_interior;
    private Integer colonia_id;
    private Integer persona_id;

    public Integer getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(Integer id_direccion) {
        this.id_direccion = id_direccion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNum_exterior() {
        return num_exterior;
    }

    public void setNum_exterior(String num_exterior) {
        this.num_exterior = num_exterior;
    }

    public String getNum_interior() {
        return num_interior;
    }

    public void setNum_interior(String num_interior) {
        this.num_interior = num_interior;
    }

    public Integer getColonia_id() {
        return colonia_id;
    }

    public void setColonia_id(Integer colonia_id) {
        this.colonia_id = colonia_id;
    }

    public Integer getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(Integer persona_id) {
        this.persona_id = persona_id;
    }
       
}
