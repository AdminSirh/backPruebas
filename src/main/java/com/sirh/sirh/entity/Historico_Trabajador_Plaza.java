package com.sirh.sirh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author oguerrero23
 */
@Entity
@Table(name="historico_trabajador_plaza")
public class Historico_Trabajador_Plaza implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_historico_trabajador_plaza;
    
    private Integer trabajador_plaza_id;
    private Integer trabajador_id;
    private Integer plaza_id;
    private Integer areas_id;
    private Integer tipo_contrato_id;
    private Integer tipo_nomina_id;
    private Integer ubicacion_id;
    private String observaciones;
    private Integer plaza_movimientos_id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_inicial;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_final;
    
    @CreatedDate
    private LocalDate fecha_registro;

    public Historico_Trabajador_Plaza() {
        super();
    }

    public Historico_Trabajador_Plaza(Integer id_historico_trabajador_plaza, Integer trabajador_plaza_id, Integer trabajador_id, Integer plaza_id, Integer areas_id, Integer tipo_contrato_id, Integer tipo_nomina_id, Integer ubicacion_id, String observaciones, Integer plaza_movimientos_id, LocalDate fecha_inicial, LocalDate fecha_final, LocalDate fecha_registro) {
        this.id_historico_trabajador_plaza = id_historico_trabajador_plaza;
        this.trabajador_plaza_id = trabajador_plaza_id;
        this.trabajador_id = trabajador_id;
        this.plaza_id = plaza_id;
        this.areas_id = areas_id;
        this.tipo_contrato_id = tipo_contrato_id;
        this.tipo_nomina_id = tipo_nomina_id;
        this.ubicacion_id = ubicacion_id;
        this.observaciones = observaciones;
        this.plaza_movimientos_id = plaza_movimientos_id;
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
        this.fecha_registro = fecha_registro;
    }

    public Integer getId_historico_trabajador_plaza() {
        return id_historico_trabajador_plaza;
    }

    public void setId_historico_trabajador_plaza(Integer id_historico_trabajador_plaza) {
        this.id_historico_trabajador_plaza = id_historico_trabajador_plaza;
    }

    public Integer getTrabajador_plaza_id() {
        return trabajador_plaza_id;
    }

    public void setTrabajador_plaza_id(Integer trabajador_plaza_id) {
        this.trabajador_plaza_id = trabajador_plaza_id;
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

    public Integer getTipo_contrato_id() {
        return tipo_contrato_id;
    }

    public void setTipo_contrato_id(Integer tipo_contrato_id) {
        this.tipo_contrato_id = tipo_contrato_id;
    }

    public Integer getTipo_nomina_id() {
        return tipo_nomina_id;
    }

    public void setTipo_nomina_id(Integer tipo_nomina_id) {
        this.tipo_nomina_id = tipo_nomina_id;
    }

    public Integer getUbicacion_id() {
        return ubicacion_id;
    }

    public void setUbicacion_id(Integer ubicacion_id) {
        this.ubicacion_id = ubicacion_id;
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

    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    
}
