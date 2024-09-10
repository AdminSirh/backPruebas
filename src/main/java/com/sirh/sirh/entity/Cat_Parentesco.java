/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

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
@Entity
@Table(name = "catalogo_parentesco")
public class Cat_Parentesco implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_parentesco")
    private Integer id_parentesco;
    private String desc_parentesco;
    private Integer estatus;

    public Integer getId_parentesco() {
        return id_parentesco;
    }

    public void setId_parentesco(Integer id_parentesco) {
        this.id_parentesco = id_parentesco;
    }

    public String getDesc_parentesco() {
        return desc_parentesco;
    }

    public void setDesc_parentesco(String desc_parentesco) {
        this.desc_parentesco = desc_parentesco;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
    
}
