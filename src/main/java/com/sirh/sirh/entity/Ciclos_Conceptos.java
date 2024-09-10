package com.sirh.sirh.entity;

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
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author oguerrero23
 */

@Entity
@Table (name = "ciclos_conceptos")
public class Ciclos_Conceptos implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ciclo;
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nomina_id")
    private Cat_Tipo_Nomina cat_Tipo_Nomina;
    private Integer anio;
    private Integer mes;
    private Integer semana;
    
    private LocalDate fecha_inicial;
    private LocalDate fecha_final;
    private Integer periodo_aplicacion;
    private Integer estatus;

    public Ciclos_Conceptos() {
        super();
    }

    public Ciclos_Conceptos(Integer id_ciclo, Cat_Tipo_Nomina cat_Tipo_Nomina, Integer anio, Integer mes, Integer semana, LocalDate fecha_inicial, LocalDate fecha_final, Integer periodo_aplicacion, Integer estatus) {
        this.id_ciclo = id_ciclo;
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
        this.anio = anio;
        this.mes = mes;
        this.semana = semana;
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
        this.periodo_aplicacion = periodo_aplicacion;
        this.estatus = estatus;
    }

    public Integer getId_ciclo() {
        return id_ciclo;
    }

    public void setId_ciclo(Integer id_ciclo) {
        this.id_ciclo = id_ciclo;
    }

    public Cat_Tipo_Nomina getCat_Tipo_Nomina() {
        return cat_Tipo_Nomina;
    }

    public void setCat_Tipo_Nomina(Cat_Tipo_Nomina cat_Tipo_Nomina) {
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
    }

    public LocalDate getFecha_inicial() {
        return fecha_inicial;
    }

    public void setFecha_inicial(LocalDate fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    public LocalDate getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(LocalDate fecha_final) {
        this.fecha_final = fecha_final;
    }

    public Integer getPeriodo_aplicacion() {
        return periodo_aplicacion;
    }

    public void setPeriodo_aplicacion(Integer periodo_aplicacion) {
        this.periodo_aplicacion = periodo_aplicacion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    
}
