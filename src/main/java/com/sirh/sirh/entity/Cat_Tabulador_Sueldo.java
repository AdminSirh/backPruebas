/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author akalvarez19
 */
@Entity
@Table(name = "catalogo_tabulador_sueldo")
public class Cat_Tabulador_Sueldo implements Serializable{
 
    protected static final String PK = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_tabulador_sueldo")
    private Integer id_tabulador_sueldo;
    private Integer id_tabulador_hist;
    private Integer id_tipo_tabulador;
    private Integer id_usuario;
    private Integer nivel;
    private String sueldo_mensual;
    private String cantidad_adicional_mensual;
    private String sueldo_mensual_neto;
    private String sueldo_estructura;
    private String sueldo_diario;
    private String sueldo_diario_doble;
    private String sueldo_hora;
    private String sueldo_hora_doble;
    private String sueldo_hora_triple;
    private String sueldo_hora7;
    private String dif_sueldo_mensual;
    private String dif_sueldo_dario;
    private String dif_sueldo_hora;
    private String dif_cant_adic_mens;
    private String isr_mens_prest;
    private String isr_mensual;
    private String cuota_imss;
    private String sdi_inicial;
    private LocalDate fecha_update;
    private Integer estatus;

    public Cat_Tabulador_Sueldo() {
        super();
    }

    public Cat_Tabulador_Sueldo(Integer id_tabulador_sueldo, Integer id_tabulador_hist, Integer id_tipo_tabulador, Integer id_usuario, Integer nivel, String sueldo_mensual, String cantidad_adicional_mensual, String sueldo_mensual_neto, String sueldo_estructura, String sueldo_diario, String sueldo_diario_doble, String sueldo_hora, String sueldo_hora_doble, String sueldo_hora_triple, String sueldo_hora7, String dif_sueldo_mensual, String dif_sueldo_dario, String dif_sueldo_hora, String dif_cant_adic_mens, String isr_mens_prest, String isr_mensual, String cuota_imss, String sdi_inicial, LocalDate fecha_update, Integer estatus) {
        this.id_tabulador_sueldo = id_tabulador_sueldo;
        this.id_tabulador_hist = id_tabulador_hist;
        this.id_tipo_tabulador = id_tipo_tabulador;
        this.id_usuario = id_usuario;
        this.nivel = nivel;
        this.sueldo_mensual = sueldo_mensual;
        this.cantidad_adicional_mensual = cantidad_adicional_mensual;
        this.sueldo_mensual_neto = sueldo_mensual_neto;
        this.sueldo_estructura = sueldo_estructura;
        this.sueldo_diario = sueldo_diario;
        this.sueldo_diario_doble = sueldo_diario_doble;
        this.sueldo_hora = sueldo_hora;
        this.sueldo_hora_doble = sueldo_hora_doble;
        this.sueldo_hora_triple = sueldo_hora_triple;
        this.sueldo_hora7 = sueldo_hora7;
        this.dif_sueldo_mensual = dif_sueldo_mensual;
        this.dif_sueldo_dario = dif_sueldo_dario;
        this.dif_sueldo_hora = dif_sueldo_hora;
        this.dif_cant_adic_mens = dif_cant_adic_mens;
        this.isr_mens_prest = isr_mens_prest;
        this.isr_mensual = isr_mensual;
        this.cuota_imss = cuota_imss;
        this.sdi_inicial = sdi_inicial;
        this.fecha_update = fecha_update;
        this.estatus = estatus;
    }

    public Integer getId_tabulador_sueldo() {
        return id_tabulador_sueldo;
    }

    public void setId_tabulador_sueldo(Integer id_tabulador_sueldo) {
        this.id_tabulador_sueldo = id_tabulador_sueldo;
    }

    public Integer getId_tabulador_hist() {
        return id_tabulador_hist;
    }

    public void setId_tabulador_hist(Integer id_tabulador_hist) {
        this.id_tabulador_hist = id_tabulador_hist;
    }

    public Integer getId_tipo_tabulador() {
        return id_tipo_tabulador;
    }

    public void setId_tipo_tabulador(Integer id_tipo_tabulador) {
        this.id_tipo_tabulador = id_tipo_tabulador;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getSueldo_mensual() {
        return sueldo_mensual;
    }

    public void setSueldo_mensual(String sueldo_mensual) {
        this.sueldo_mensual = sueldo_mensual;
    }

    public String getCantidad_adicional_mensual() {
        return cantidad_adicional_mensual;
    }

    public void setCantidad_adicional_mensual(String cantidad_adicional_mensual) {
        this.cantidad_adicional_mensual = cantidad_adicional_mensual;
    }

    public String getSueldo_mensual_neto() {
        return sueldo_mensual_neto;
    }

    public void setSueldo_mensual_neto(String sueldo_mensual_neto) {
        this.sueldo_mensual_neto = sueldo_mensual_neto;
    }

    public String getSueldo_estructura() {
        return sueldo_estructura;
    }

    public void setSueldo_estructura(String sueldo_estructura) {
        this.sueldo_estructura = sueldo_estructura;
    }

    public String getSueldo_diario() {
        return sueldo_diario;
    }

    public void setSueldo_diario(String sueldo_diario) {
        this.sueldo_diario = sueldo_diario;
    }

    public String getSueldo_diario_doble() {
        return sueldo_diario_doble;
    }

    public void setSueldo_diario_doble(String sueldo_diario_doble) {
        this.sueldo_diario_doble = sueldo_diario_doble;
    }

    public String getSueldo_hora() {
        return sueldo_hora;
    }

    public void setSueldo_hora(String sueldo_hora) {
        this.sueldo_hora = sueldo_hora;
    }

    public String getSueldo_hora_doble() {
        return sueldo_hora_doble;
    }

    public void setSueldo_hora_doble(String sueldo_hora_doble) {
        this.sueldo_hora_doble = sueldo_hora_doble;
    }

    public String getSueldo_hora_triple() {
        return sueldo_hora_triple;
    }

    public void setSueldo_hora_triple(String sueldo_hora_triple) {
        this.sueldo_hora_triple = sueldo_hora_triple;
    }

    public String getSueldo_hora7() {
        return sueldo_hora7;
    }

    public void setSueldo_hora7(String sueldo_hora7) {
        this.sueldo_hora7 = sueldo_hora7;
    }

    public String getDif_sueldo_mensual() {
        return dif_sueldo_mensual;
    }

    public void setDif_sueldo_mensual(String dif_sueldo_mensual) {
        this.dif_sueldo_mensual = dif_sueldo_mensual;
    }

    public String getDif_sueldo_dario() {
        return dif_sueldo_dario;
    }

    public void setDif_sueldo_dario(String dif_sueldo_dario) {
        this.dif_sueldo_dario = dif_sueldo_dario;
    }

    public String getDif_sueldo_hora() {
        return dif_sueldo_hora;
    }

    public void setDif_sueldo_hora(String dif_sueldo_hora) {
        this.dif_sueldo_hora = dif_sueldo_hora;
    }

    public String getDif_cant_adic_mens() {
        return dif_cant_adic_mens;
    }

    public void setDif_cant_adic_mens(String dif_cant_adic_mens) {
        this.dif_cant_adic_mens = dif_cant_adic_mens;
    }

    public String getIsr_mens_prest() {
        return isr_mens_prest;
    }

    public void setIsr_mens_prest(String isr_mens_prest) {
        this.isr_mens_prest = isr_mens_prest;
    }

    public String getIsr_mensual() {
        return isr_mensual;
    }

    public void setIsr_mensual(String isr_mensual) {
        this.isr_mensual = isr_mensual;
    }

    public String getCuota_imss() {
        return cuota_imss;
    }

    public void setCuota_imss(String cuota_imss) {
        this.cuota_imss = cuota_imss;
    }

    public String getSdi_inicial() {
        return sdi_inicial;
    }

    public void setSdi_inicial(String sdi_inicial) {
        this.sdi_inicial = sdi_inicial;
    }

    public LocalDate getFecha_update() {
        return fecha_update;
    }

    public void setFecha_update(LocalDate fecha_update) {
        this.fecha_update = fecha_update;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    
}
