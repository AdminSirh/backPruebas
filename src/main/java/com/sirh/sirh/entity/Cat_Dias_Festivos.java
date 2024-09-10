/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author rrosero23
 */
@Entity
@Table(name = "catalogo_dias_festivos")
public class Cat_Dias_Festivos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id_festivos;
    private String descripcion;
    private Integer estatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nomina_id")
    private Cat_Tipo_Nomina cat_Tipo_Nomina;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private Integer contrato_colectivo;

    public Cat_Dias_Festivos() {
        super();
    }

    public Cat_Dias_Festivos(Integer id_festivos, String descripcion, Integer estatus, Cat_Tipo_Nomina cat_Tipo_Nomina, LocalDate fecha, Integer contrato_colectivo) {
        this.id_festivos = id_festivos;
        this.descripcion = descripcion;
        this.estatus = estatus;
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
        this.fecha = fecha;
        this.contrato_colectivo = contrato_colectivo;
    }

    public Integer getId_festivos() {
        return id_festivos;
    }

    public void setId_festivos(Integer id_festivos) {
        this.id_festivos = id_festivos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Cat_Tipo_Nomina getCat_Tipo_Nomina() {
        return cat_Tipo_Nomina;
    }

    public void setCat_Tipo_Nomina(Cat_Tipo_Nomina cat_Tipo_Nomina) {
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getContrato_colectivo() {
        return contrato_colectivo;
    }

    public void setContrato_colectivo(Integer contrato_colectivo) {
        this.contrato_colectivo = contrato_colectivo;
    }

    
}
