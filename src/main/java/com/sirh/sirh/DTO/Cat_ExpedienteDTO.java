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
 * @author jarellano22
 */
public class Cat_ExpedienteDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_expediente;
    private String numero_expediente;

    public Integer getId_expediente() {
        return id_expediente;
    }

    public void setId_expediente(Integer id_expediente) {
        this.id_expediente = id_expediente;
    }

    public String getNumero_expediente() {
        return numero_expediente;
    }

    public void setNumero_expediente(String numero_expediente) {
        this.numero_expediente = numero_expediente;
    }

}
