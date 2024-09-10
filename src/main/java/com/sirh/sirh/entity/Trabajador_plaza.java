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
import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author oguerrero23
 */
@Entity
@Table(name="trabajador_plaza")
public class Trabajador_plaza implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_trabajador_plaza;
    
    
    private Integer trabajador_id;
    private Integer plaza_id;
    private Integer areas_id;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_contrato_id")
    private Cat_Tipo_Contrato cat_tipo_contrato;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_nomina_id")
    private Cat_Tipo_Nomina cat_Tipo_Nomina;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ubicacion_id")
    private Cat_Ubicacion cat_Ubicacion;
    @CreatedDate
    private LocalDate fecha_captura;
    
    private String observaciones;
    private Integer plaza_movimientos_id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_inicial;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_final;
    
    public Trabajador_plaza() {
        super();
    }

    public Trabajador_plaza(Integer id_trabajador_plaza, Integer trabajador_id, Integer plaza_id, Integer areas_id, Cat_Tipo_Contrato cat_tipo_contrato, Cat_Tipo_Nomina cat_Tipo_Nomina, Cat_Ubicacion cat_Ubicacion, LocalDate fecha_captura, String observaciones, Integer plaza_movimientos_id, LocalDate fecha_inicial, LocalDate fecha_final) {
        this.id_trabajador_plaza = id_trabajador_plaza;
        this.trabajador_id = trabajador_id;
        this.plaza_id = plaza_id;
        this.areas_id = areas_id;
        this.cat_tipo_contrato = cat_tipo_contrato;
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
        this.cat_Ubicacion = cat_Ubicacion;
        this.fecha_captura = fecha_captura;
        this.observaciones = observaciones;
        this.plaza_movimientos_id = plaza_movimientos_id;
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
    }

    public Integer getId_trabajador_plaza() {
        return id_trabajador_plaza;
    }

    public void setId_trabajador_plaza(Integer id_trabajador_plaza) {
        this.id_trabajador_plaza = id_trabajador_plaza;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getPlaza_id() {
        return plaza_id;
    }

    public void setPlaza_id(Integer plaza_id) {
        this.plaza_id = plaza_id;
    }

    public Integer getAreas_id() {
        return areas_id;
    }

    public void setAreas_id(Integer areas_id) {
        this.areas_id = areas_id;
    }

    public Cat_Tipo_Contrato getCat_tipo_contrato() {
        return cat_tipo_contrato;
    }

    public void setCat_tipo_contrato(Cat_Tipo_Contrato cat_tipo_contrato) {
        this.cat_tipo_contrato = cat_tipo_contrato;
    }

    public Cat_Tipo_Nomina getCat_Tipo_Nomina() {
        return cat_Tipo_Nomina;
    }

    public void setCat_Tipo_Nomina(Cat_Tipo_Nomina cat_Tipo_Nomina) {
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
    }

    public Cat_Ubicacion getCat_Ubicacion() {
        return cat_Ubicacion;
    }

    public void setCat_Ubicacion(Cat_Ubicacion cat_Ubicacion) {
        this.cat_Ubicacion = cat_Ubicacion;
    }

    public LocalDate getFecha_captura() {
        return fecha_captura;
    }

    public void setFecha_captura(LocalDate fecha_captura) {
        this.fecha_captura = fecha_captura;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getPlaza_movimientos_id() {
        return plaza_movimientos_id;
    }

    public void setPlaza_movimientos_id(Integer plaza_movimientos_id) {
        this.plaza_movimientos_id = plaza_movimientos_id;
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

    
}
