/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author jarellano22
 */
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



public class Cat_AreasDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_area")
    private Integer idArea;
    private String desc_area;

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public void setDesc_area(String desc_area) {
        this.desc_area = desc_area;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public String getDesc_area() {
        return desc_area;
    }

}
