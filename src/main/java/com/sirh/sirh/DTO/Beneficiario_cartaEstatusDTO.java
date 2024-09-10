/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author dguerrero18
 */
public class Beneficiario_cartaEstatusDTO {
    private Integer estatus;
    public Beneficiario_cartaEstatusDTO(Integer estatus) {
      this.estatus = estatus;
    }
    public Beneficiario_cartaEstatusDTO() {
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
}
