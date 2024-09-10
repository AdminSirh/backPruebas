/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

/**
 *
 * @author rroscero23
 */
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

@Entity
@Table(name = "propiedades_por_nomina")
public class Propiedades_Nomina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_propiedad;

    private Integer nomina_id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "incidencia_id")
    private Cat_Incidencias cat_Incidencias;
    private Double valor;
    private LocalDate fecha_movimiento;

    public Propiedades_Nomina() {
        super();
    }

    public Propiedades_Nomina(Integer id_propiedad, Integer nomina_id, Cat_Incidencias cat_Incidencias, Double valor, LocalDate fecha_movimiento) {
        this.id_propiedad = id_propiedad;
        this.nomina_id = nomina_id;
        this.cat_Incidencias = cat_Incidencias;
        this.valor = valor;
        this.fecha_movimiento = fecha_movimiento;
    }

    public Integer getId_propiedad() {
        return id_propiedad;
    }

    public void setId_propiedad(Integer id_propiedad) {
        this.id_propiedad = id_propiedad;
    }

    public Integer getNomina_id() {
        return nomina_id;
    }

    public void setNomina_id(Integer nomina_id) {
        this.nomina_id = nomina_id;
    }

    public Cat_Incidencias getCat_Incidencias() {
        return cat_Incidencias;
    }

    public void setCat_Incidencias(Cat_Incidencias cat_Incidencias) {
        this.cat_Incidencias = cat_Incidencias;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDate fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    
}
