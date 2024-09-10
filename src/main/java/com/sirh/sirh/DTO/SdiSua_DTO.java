/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rroscero23
 */
public class SdiSua_DTO {

    private String num_imss;
    private Double salario_mixto;

    public SdiSua_DTO() {
    }

    public SdiSua_DTO(String num_imss, Double salario_mixto) {
        this.num_imss = num_imss;
        this.salario_mixto = salario_mixto;
    }

    public String getNum_imss() {
        return num_imss;
    }

    public void setNum_imss(String num_imss) {
        this.num_imss = num_imss;
    }

    public Double getSalario_mixto() {
        return salario_mixto;
    }

    public void setSalario_mixto(Double salario_mixto) {
        this.salario_mixto = salario_mixto;
    }

}
