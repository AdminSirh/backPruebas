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
import javax.persistence.Temporal;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author rroscero23
 */
@Entity
@Table(name = "tmp_sdi_calculos_bimestre")
public class Tmp_Sdi_Calculos_Bim implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_sdi_calculo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bimestre_id")
    private Cat_Bimestres_Sdi cat_Bimestres_Sdi;
    private Integer trabajador_id;
    private Integer dias_prima_vac_antig;
    private Integer anios_antig;
    private Double vales_pag;
    private Double quinquenio_pag;
    private Double incentivo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sdi_fijo_id")
    private Tmp_Sdi_Fijo_Calculos_Bim tmp_Sdi_Fijo_Calculos_Bim;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sdi_variable_id")
    private Tmp_Sdi_Variable_Calculos_Bim tmp_Sdi_Variable_Calculos_Bim;

    @CreatedDate
    private LocalDate fecha_corte;
    @CreatedDate
    private LocalDate fecha_egreso;
    //En sustitución a la tabla TMP_SDI_CALCULOS_VAR_BIMESTRE que contine exactamente los mismos campos
    private Integer cambio_nivel;

    public Tmp_Sdi_Calculos_Bim() {
        super();
    }

    public Tmp_Sdi_Calculos_Bim(Integer id_sdi_calculo, Cat_Bimestres_Sdi cat_Bimestres_Sdi, Integer trabajador_id, Integer dias_prima_vac_antig, Integer anios_antig, Double vales_pag, Double quinquenio_pag, Double incentivo, Tmp_Sdi_Fijo_Calculos_Bim tmp_Sdi_Fijo_Calculos_Bim, Tmp_Sdi_Variable_Calculos_Bim tmp_Sdi_Variable_Calculos_Bim, LocalDate fecha_corte, LocalDate fecha_egreso, Integer cambio_nivel) {
        this.id_sdi_calculo = id_sdi_calculo;
        this.cat_Bimestres_Sdi = cat_Bimestres_Sdi;
        this.trabajador_id = trabajador_id;
        this.dias_prima_vac_antig = dias_prima_vac_antig;
        this.anios_antig = anios_antig;
        this.vales_pag = vales_pag;
        this.quinquenio_pag = quinquenio_pag;
        this.incentivo = incentivo;
        this.tmp_Sdi_Fijo_Calculos_Bim = tmp_Sdi_Fijo_Calculos_Bim;
        this.tmp_Sdi_Variable_Calculos_Bim = tmp_Sdi_Variable_Calculos_Bim;
        this.fecha_corte = fecha_corte;
        this.fecha_egreso = fecha_egreso;
        this.cambio_nivel = cambio_nivel;
    }

    public Integer getId_sdi_calculo() {
        return id_sdi_calculo;
    }

    public void setId_sdi_calculo(Integer id_sdi_calculo) {
        this.id_sdi_calculo = id_sdi_calculo;
    }

    public Cat_Bimestres_Sdi getCat_Bimestres_Sdi() {
        return cat_Bimestres_Sdi;
    }

    public void setCat_Bimestres_Sdi(Cat_Bimestres_Sdi cat_Bimestres_Sdi) {
        this.cat_Bimestres_Sdi = cat_Bimestres_Sdi;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getDias_prima_vac_antig() {
        return dias_prima_vac_antig;
    }

    public void setDias_prima_vac_antig(Integer dias_prima_vac_antig) {
        this.dias_prima_vac_antig = dias_prima_vac_antig;
    }

    public Integer getAnios_antig() {
        return anios_antig;
    }

    public void setAnios_antig(Integer anios_antig) {
        this.anios_antig = anios_antig;
    }

    public Double getVales_pag() {
        return vales_pag;
    }

    public void setVales_pag(Double vales_pag) {
        this.vales_pag = vales_pag;
    }

    public Double getQuinquenio_pag() {
        return quinquenio_pag;
    }

    public void setQuinquenio_pag(Double quinquenio_pag) {
        this.quinquenio_pag = quinquenio_pag;
    }

    public Double getIncentivo() {
        return incentivo;
    }

    public void setIncentivo(Double incentivo) {
        this.incentivo = incentivo;
    }

    public Tmp_Sdi_Fijo_Calculos_Bim getTmp_Sdi_Fijo_Calculos_Bim() {
        return tmp_Sdi_Fijo_Calculos_Bim;
    }

    public void setTmp_Sdi_Fijo_Calculos_Bim(Tmp_Sdi_Fijo_Calculos_Bim tmp_Sdi_Fijo_Calculos_Bim) {
        this.tmp_Sdi_Fijo_Calculos_Bim = tmp_Sdi_Fijo_Calculos_Bim;
    }

    public Tmp_Sdi_Variable_Calculos_Bim getTmp_Sdi_Variable_Calculos_Bim() {
        return tmp_Sdi_Variable_Calculos_Bim;
    }

    public void setTmp_Sdi_Variable_Calculos_Bim(Tmp_Sdi_Variable_Calculos_Bim tmp_Sdi_Variable_Calculos_Bim) {
        this.tmp_Sdi_Variable_Calculos_Bim = tmp_Sdi_Variable_Calculos_Bim;
    }

    public LocalDate getFecha_corte() {
        return fecha_corte;
    }

    public void setFecha_corte(LocalDate fecha_corte) {
        this.fecha_corte = fecha_corte;
    }

    public LocalDate getFecha_egreso() {
        return fecha_egreso;
    }

    public void setFecha_egreso(LocalDate fecha_egreso) {
        this.fecha_egreso = fecha_egreso;
    }

    public Integer getCambio_nivel() {
        return cambio_nivel;
    }

    public void setCambio_nivel(Integer cambio_nivel) {
        this.cambio_nivel = cambio_nivel;
    }

    
}
