/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author rroscero23
 */
@Entity
@Table(name = "ayuda_dias_permiso")
public class Ayuda_Dias_Permiso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id_ayuda_dias;

    private LocalDate dia_1;

    private LocalDate dia_2;

    private LocalDate dia_3;

    private LocalDate dia_4;

    private LocalDate dia_5;

    private LocalDate dia_6;

    
    
    public Ayuda_Dias_Permiso(){
        
    }

    public Ayuda_Dias_Permiso(Integer id_ayuda_dias, LocalDate dia_1, LocalDate dia_2, LocalDate dia_3, LocalDate dia_4, LocalDate dia_5, LocalDate dia_6) {
        this.id_ayuda_dias = id_ayuda_dias;
        this.dia_1 = dia_1;
        this.dia_2 = dia_2;
        this.dia_3 = dia_3;
        this.dia_4 = dia_4;
        this.dia_5 = dia_5;
        this.dia_6 = dia_6;
    }

    public Integer getId_ayuda_dias() {
        return id_ayuda_dias;
    }

    public void setId_ayuda_dias(Integer id_ayuda_dias) {
        this.id_ayuda_dias = id_ayuda_dias;
    }

    public LocalDate getDia_1() {
        return dia_1;
    }

    public void setDia_1(LocalDate dia_1) {
        this.dia_1 = dia_1;
    }

    public LocalDate getDia_2() {
        return dia_2;
    }

    public void setDia_2(LocalDate dia_2) {
        this.dia_2 = dia_2;
    }

    public LocalDate getDia_3() {
        return dia_3;
    }

    public void setDia_3(LocalDate dia_3) {
        this.dia_3 = dia_3;
    }

    public LocalDate getDia_4() {
        return dia_4;
    }

    public void setDia_4(LocalDate dia_4) {
        this.dia_4 = dia_4;
    }

    public LocalDate getDia_5() {
        return dia_5;
    }

    public void setDia_5(LocalDate dia_5) {
        this.dia_5 = dia_5;
    }

    public LocalDate getDia_6() {
        return dia_6;
    }

    public void setDia_6(LocalDate dia_6) {
        this.dia_6 = dia_6;
    }

    
}
