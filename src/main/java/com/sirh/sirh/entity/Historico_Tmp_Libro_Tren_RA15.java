/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
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
 * @author rroscero23
 */
@Entity
@Table(name = "historico_tmp_libro_tren_ra_15")
public class Historico_Tmp_Libro_Tren_RA15 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_planta_tren;
    private Integer anio;
    private Integer expediente;
    private String descansos;
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dia_id")
    private Cat_Dias cat_Dias;
    private Double pago;
    private String corrida;
    private Integer estatus;

    public Integer getId_planta_tren() {
        return id_planta_tren;
    }

    public void setId_planta_tren(Integer id_planta_tren) {
        this.id_planta_tren = id_planta_tren;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getExpediente() {
        return expediente;
    }

    public void setExpediente(Integer expediente) {
        this.expediente = expediente;
    }

    public String getDescansos() {
        return descansos;
    }

    public void setDescansos(String descansos) {
        this.descansos = descansos;
    }

    public Cat_Dias getCat_Dias() {
        return cat_Dias;
    }

    public void setCat_Dias(Cat_Dias cat_Dias) {
        this.cat_Dias = cat_Dias;
    }

    public Double getPago() {
        return pago;
    }

    public void setPago(Double pago) {
        this.pago = pago;
    }

    public String getCorrida() {
        return corrida;
    }

    public void setCorrida(String corrida) {
        this.corrida = corrida;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

}
