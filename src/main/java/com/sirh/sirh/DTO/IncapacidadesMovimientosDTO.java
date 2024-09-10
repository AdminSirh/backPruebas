/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.sirh.sirh.entity.Cat_Motivo_Incapacidad;
import com.sirh.sirh.entity.Cat_Tipo_Control_Incapacidad;
import com.sirh.sirh.entity.Cat_Tipo_Riesgo_Incapacidad;
import com.sirh.sirh.entity.Cat_Tipo_Secuelas_Incapacidad;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author rrosero23
 */
public class IncapacidadesMovimientosDTO {

    private String num_imss;

    private LocalDateTime fecha_inicial;

    private String folio;

    private Integer dias_incapacidad;

    private Double porcentaje_cobro_imss;

    //Rama de incapacidad (Debe ajustarse a ramas proporcionadas por el SUA)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motivo_incapacidad_id")
    private Cat_Motivo_Incapacidad cat_Motivo_Incapacidad;

    //Se debe de usar la clave original para la generación de los txt correspondientes en el SUA
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_riesgo_id")
    private Cat_Tipo_Riesgo_Incapacidad cat_Tipo_Riesgo_Incapacidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_secuela_id")
    private Cat_Tipo_Secuelas_Incapacidad cat_Tipo_Secuelas_Incapacidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_control_incap_id")
    private Cat_Tipo_Control_Incapacidad cat_Tipo_Control_Incapacidad;

    private LocalDateTime fecha_final;

    public IncapacidadesMovimientosDTO() {

    }

    public IncapacidadesMovimientosDTO(String num_imss, LocalDateTime fecha_inicial, String folio, Integer dias_incapacidad, Double porcentaje_cobro_imss, Cat_Motivo_Incapacidad cat_Motivo_Incapacidad, Cat_Tipo_Riesgo_Incapacidad cat_Tipo_Riesgo_Incapacidad, Cat_Tipo_Secuelas_Incapacidad cat_Tipo_Secuelas_Incapacidad, Cat_Tipo_Control_Incapacidad cat_Tipo_Control_Incapacidad, LocalDateTime fecha_final) {
        this.num_imss = num_imss;
        this.fecha_inicial = fecha_inicial;
        this.folio = folio;
        this.dias_incapacidad = dias_incapacidad;
        this.porcentaje_cobro_imss = porcentaje_cobro_imss;
        this.cat_Motivo_Incapacidad = cat_Motivo_Incapacidad;
        this.cat_Tipo_Riesgo_Incapacidad = cat_Tipo_Riesgo_Incapacidad;
        this.cat_Tipo_Secuelas_Incapacidad = cat_Tipo_Secuelas_Incapacidad;
        this.cat_Tipo_Control_Incapacidad = cat_Tipo_Control_Incapacidad;
        this.fecha_final = fecha_final;
    }

    public String getNum_imss() {
        return num_imss;
    }

    public void setNum_imss(String num_imss) {
        this.num_imss = num_imss;
    }

    public LocalDateTime getFecha_inicial() {
        return fecha_inicial;
    }

    public void setFecha_inicial(LocalDateTime fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Integer getDias_incapacidad() {
        return dias_incapacidad;
    }

    public void setDias_incapacidad(Integer dias_incapacidad) {
        this.dias_incapacidad = dias_incapacidad;
    }

    public Double getPorcentaje_cobro_imss() {
        return porcentaje_cobro_imss;
    }

    public void setPorcentaje_cobro_imss(Double porcentaje_cobro_imss) {
        this.porcentaje_cobro_imss = porcentaje_cobro_imss;
    }

    public Cat_Motivo_Incapacidad getCat_Motivo_Incapacidad() {
        return cat_Motivo_Incapacidad;
    }

    public void setCat_Motivo_Incapacidad(Cat_Motivo_Incapacidad cat_Motivo_Incapacidad) {
        this.cat_Motivo_Incapacidad = cat_Motivo_Incapacidad;
    }

    public Cat_Tipo_Riesgo_Incapacidad getCat_Tipo_Riesgo_Incapacidad() {
        return cat_Tipo_Riesgo_Incapacidad;
    }

    public void setCat_Tipo_Riesgo_Incapacidad(Cat_Tipo_Riesgo_Incapacidad cat_Tipo_Riesgo_Incapacidad) {
        this.cat_Tipo_Riesgo_Incapacidad = cat_Tipo_Riesgo_Incapacidad;
    }

    public Cat_Tipo_Secuelas_Incapacidad getCat_Tipo_Secuelas_Incapacidad() {
        return cat_Tipo_Secuelas_Incapacidad;
    }

    public void setCat_Tipo_Secuelas_Incapacidad(Cat_Tipo_Secuelas_Incapacidad cat_Tipo_Secuelas_Incapacidad) {
        this.cat_Tipo_Secuelas_Incapacidad = cat_Tipo_Secuelas_Incapacidad;
    }

    public Cat_Tipo_Control_Incapacidad getCat_Tipo_Control_Incapacidad() {
        return cat_Tipo_Control_Incapacidad;
    }

    public void setCat_Tipo_Control_Incapacidad(Cat_Tipo_Control_Incapacidad cat_Tipo_Control_Incapacidad) {
        this.cat_Tipo_Control_Incapacidad = cat_Tipo_Control_Incapacidad;
    }

    public LocalDateTime getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(LocalDateTime fecha_final) {
        this.fecha_final = fecha_final;
    }

    

}
