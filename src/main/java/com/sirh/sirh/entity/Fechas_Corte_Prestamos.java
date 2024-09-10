/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author oguerrero23
 */
@Entity
@Table(name = "fechas_corte_prestamos")
public class Fechas_Corte_Prestamos implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_fecha_corte_prestamo;
    @CreatedDate
    private LocalDate fecha_corte;
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nomina_id")
    private Cat_Tipo_Nomina cat_Tipo_Nomina;
    private Integer incidencia_id;

    public Fechas_Corte_Prestamos() {
        super();
    }

    public Fechas_Corte_Prestamos(Integer id_fecha_corte_prestamo, LocalDate fecha_corte, Cat_Tipo_Nomina cat_Tipo_Nomina, Integer incidencia_id) {
        this.id_fecha_corte_prestamo = id_fecha_corte_prestamo;
        this.fecha_corte = fecha_corte;
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
        this.incidencia_id = incidencia_id;
    }

    public Integer getId_fecha_corte_prestamo() {
        return id_fecha_corte_prestamo;
    }

    public void setId_fecha_corte_prestamo(Integer id_fecha_corte_prestamo) {
        this.id_fecha_corte_prestamo = id_fecha_corte_prestamo;
    }

    public LocalDate getFecha_corte() {
        return fecha_corte;
    }

    public void setFecha_corte(LocalDate fecha_corte) {
        this.fecha_corte = fecha_corte;
    }

    public Cat_Tipo_Nomina getCat_Tipo_Nomina() {
        return cat_Tipo_Nomina;
    }

    public void setCat_Tipo_Nomina(Cat_Tipo_Nomina cat_Tipo_Nomina) {
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
    }

    public Integer getIncidencia_id() {
        return incidencia_id;
    }

    public void setIncidencia_id(Integer incidencia_id) {
        this.incidencia_id = incidencia_id;
    }
}
