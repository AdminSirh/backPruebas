/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author rrosero23
 */
@Entity
@Table(name = "pension_a_montos")
public class Pension_Alimenticia_Montos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_montos;
    private Integer id_fdo_ahorro;
    private Boolean fdo_trabajador;
    private Boolean fdo_empresa;
    private Boolean fdo_interes;
    private Double descuento;
    private Boolean apl_nomina;
    private Boolean apl_finiq;
    private Boolean vales_fin_anio;
    private Boolean id_deposito_bancario;
    private Double porcentaje_descuent;
    private Integer periodo_gen;
    private Double anualidad;
    private Integer pago_descuento;

    public Pension_Alimenticia_Montos() {
    }

    public Pension_Alimenticia_Montos(Integer id_montos, Integer id_fdo_ahorro, Boolean fdo_trabajador, Boolean fdo_empresa, Boolean fdo_interes, Double descuento, Boolean apl_nomina, Boolean apl_finiq, Boolean vales_fin_anio, Boolean id_deposito_bancario, Double porcentaje_descuent, Integer periodo_gen, Double anualidad, Integer pago_descuento) {
        this.id_montos = id_montos;
        this.id_fdo_ahorro = id_fdo_ahorro;
        this.fdo_trabajador = fdo_trabajador;
        this.fdo_empresa = fdo_empresa;
        this.fdo_interes = fdo_interes;
        this.descuento = descuento;
        this.apl_nomina = apl_nomina;
        this.apl_finiq = apl_finiq;
        this.vales_fin_anio = vales_fin_anio;
        this.id_deposito_bancario = id_deposito_bancario;
        this.porcentaje_descuent = porcentaje_descuent;
        this.periodo_gen = periodo_gen;
        this.anualidad = anualidad;
        this.pago_descuento = pago_descuento;
    }

    

    public Integer getId_montos() {
        return id_montos;
    }

    public void setId_montos(Integer id_montos) {
        this.id_montos = id_montos;
    }

    public Integer getId_fdo_ahorro() {
        return id_fdo_ahorro;
    }

    public void setId_fdo_ahorro(Integer id_fdo_ahorro) {
        this.id_fdo_ahorro = id_fdo_ahorro;
    }

    public Boolean getFdo_trabajador() {
        return fdo_trabajador;
    }

    public void setFdo_trabajador(Boolean fdo_trabajador) {
        this.fdo_trabajador = fdo_trabajador;
    }

    public Boolean getFdo_empresa() {
        return fdo_empresa;
    }

    public void setFdo_empresa(Boolean fdo_empresa) {
        this.fdo_empresa = fdo_empresa;
    }

    public Boolean getFdo_interes() {
        return fdo_interes;
    }

    public void setFdo_interes(Boolean fdo_interes) {
        this.fdo_interes = fdo_interes;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Boolean getApl_nomina() {
        return apl_nomina;
    }

    public void setApl_nomina(Boolean apl_nomina) {
        this.apl_nomina = apl_nomina;
    }

    public Boolean getApl_finiq() {
        return apl_finiq;
    }

    public void setApl_finiq(Boolean apl_finiq) {
        this.apl_finiq = apl_finiq;
    }

    public Boolean getVales_fin_anio() {
        return vales_fin_anio;
    }

    public void setVales_fin_anio(Boolean vales_fin_anio) {
        this.vales_fin_anio = vales_fin_anio;
    }

    public Boolean getId_deposito_bancario() {
        return id_deposito_bancario;
    }

    public void setId_deposito_bancario(Boolean id_deposito_bancario) {
        this.id_deposito_bancario = id_deposito_bancario;
    }

    public Double getAnualidad() {
        return anualidad;
    }

    public void setAnualidad(Double anualidad) {
        this.anualidad = anualidad;
    }

    public Integer getPago_descuento() {
        return pago_descuento;
    }

    public void setPago_descuento(Integer pago_descuento) {
        this.pago_descuento = pago_descuento;
    }

    public Double getPorcentaje_descuent() {
        return porcentaje_descuent;
    }

    public void setPorcentaje_descuent(Double porcentaje_descuent) {
        this.porcentaje_descuent = porcentaje_descuent;
    }

    public Integer getPeriodo_gen() {
        return periodo_gen;
    }

    public void setPeriodo_gen(Integer periodo_gen) {
        this.periodo_gen = periodo_gen;
    }

}
