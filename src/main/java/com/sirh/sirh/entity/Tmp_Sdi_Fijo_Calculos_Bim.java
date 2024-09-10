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
 * @author rroscero23
 */
@Entity
@Table(name = "tmp_sdi_fijo_calculos_bimestre")
public class Tmp_Sdi_Fijo_Calculos_Bim implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_sdi_fijo;
    private Double fijo_01;
    private Double fijo_02;
    private Double fijo_03;
    private Double fijo_04;
    private Double fijo_05;
    private Double fijo_06;
    private Double fijo_07;
    private Double fijo_08;
    private Double fijo_09;
    private Double fijo_10;

    public Integer getId_sdi_fijo() {
        return id_sdi_fijo;
    }

    public void setId_sdi_fijo(Integer id_sdi_fijo) {
        this.id_sdi_fijo = id_sdi_fijo;
    }

    public Double getFijo_01() {
        return fijo_01;
    }

    public void setFijo_01(Double fijo_01) {
        this.fijo_01 = fijo_01;
    }

    public Double getFijo_02() {
        return fijo_02;
    }

    public void setFijo_02(Double fijo_02) {
        this.fijo_02 = fijo_02;
    }

    public Double getFijo_03() {
        return fijo_03;
    }

    public void setFijo_03(Double fijo_03) {
        this.fijo_03 = fijo_03;
    }

    public Double getFijo_04() {
        return fijo_04;
    }

    public void setFijo_04(Double fijo_04) {
        this.fijo_04 = fijo_04;
    }

    public Double getFijo_05() {
        return fijo_05;
    }

    public void setFijo_05(Double fijo_05) {
        this.fijo_05 = fijo_05;
    }

    public Double getFijo_06() {
        return fijo_06;
    }

    public void setFijo_06(Double fijo_06) {
        this.fijo_06 = fijo_06;
    }

    public Double getFijo_07() {
        return fijo_07;
    }

    public void setFijo_07(Double fijo_07) {
        this.fijo_07 = fijo_07;
    }

    public Double getFijo_08() {
        return fijo_08;
    }

    public void setFijo_08(Double fijo_08) {
        this.fijo_08 = fijo_08;
    }

    public Double getFijo_09() {
        return fijo_09;
    }

    public void setFijo_09(Double fijo_09) {
        this.fijo_09 = fijo_09;
    }

    public Double getFijo_10() {
        return fijo_10;
    }

    public void setFijo_10(Double fijo_10) {
        this.fijo_10 = fijo_10;
    }

}
