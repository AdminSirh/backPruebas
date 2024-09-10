/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.sirh.sirh.entity.Persona;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author oguerrero23
 */
public class Expedientes_TrabajadorDTO {
    //atributos tabla Expedientes
    
    private Integer id_expediente;
    private String numero_expediente;
    private Integer asignado;
    private Integer estatus;
    private LocalDate fecha_registro;
    
    //atributos tabla Trabajador
    
    private Integer expediente_id;
    private Persona persona_id;

    public Expedientes_TrabajadorDTO() {
        super();
    }

    public Expedientes_TrabajadorDTO(Integer id_expediente, String numero_expediente, Integer asignado, Integer estatus, LocalDate fecha_registro, Integer expediente_id, Persona persona_id) {
        this.id_expediente = id_expediente;
        this.numero_expediente = numero_expediente;
        this.asignado = asignado;
        this.estatus = estatus;
        this.fecha_registro = fecha_registro;
        this.expediente_id = expediente_id;
        this.persona_id = persona_id;
    }

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

    public Integer getAsignado() {
        return asignado;
    }

    public void setAsignado(Integer asignado) {
        this.asignado = asignado;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Integer getExpediente_id() {
        return expediente_id;
    }

    public void setExpediente_id(Integer expediente_id) {
        this.expediente_id = expediente_id;
    }

    public Persona getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(Persona persona_id) {
        this.persona_id = persona_id;
    }

    
}
