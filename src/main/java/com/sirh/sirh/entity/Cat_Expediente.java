/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

/**
 *
 * @author jarellano22
 */
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "catalogo_expedientes")
public class Cat_Expediente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_expediente;

    private String numero_expediente;
    @NotEmpty
    private Integer asignado;
    private Integer estatus;
    @CreatedDate
    private LocalDate fecha_registro;

    public Cat_Expediente() {
        super();
    }

    public Cat_Expediente(Integer id_expediente, String numero_expediente, Integer asignado, Integer estatus, LocalDate fecha_registro) {
        this.id_expediente = id_expediente;
        this.numero_expediente = numero_expediente;
        this.asignado = asignado;
        this.estatus = estatus;
        this.fecha_registro = fecha_registro;
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

    
}
