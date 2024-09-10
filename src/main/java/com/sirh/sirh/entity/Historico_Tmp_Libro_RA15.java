/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author rroscero23
 */
@Entity
@Table(name = "historico_tmp_libro_ra_15")
public class Historico_Tmp_Libro_RA15 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_planta;
    private Integer anio;
    private Integer expediente;
    private String descansos;
    private Integer relevo_1;
    private Integer relevo_2;
    private Integer descanso_1;
    private Integer descanso_2;
    private String corrida;
    private Double pago_semana;
    private String corrida_sabado;
    private Double pago_sabado;
    private String corrida_domingo;
    private Double pago_domingo;
    private Integer estatus;
    private Integer permuta;
    private Integer libro;
    private Integer nombramiento;
    private Integer permuta_1;
    private Integer nombramiento_1;
    private String comentario_1;
    private LocalDate fecha_movimiento_1;
    private Integer permuta_2;
    private String nombramiento_2;
    private String comentario_2;
    private LocalDate fecha_movimiento_2;
    private String linea;
    private String deposito;
    private String unidad;
    private Double entra_semana;
    private Double sale_semana;
    private Double horas_trab_semana;
    private Double entra_sabado;
    private Double sale_sabado;
    private Double horas_trab_sabado;
    private Double entra_domingo;
    private Double sale_domingo;
    private Double horas_trab_domingo;

    public Historico_Tmp_Libro_RA15() {
    }

    public Historico_Tmp_Libro_RA15(Integer id_planta, Integer anio, Integer expediente, String descansos, Integer relevo_1, Integer relevo_2, Integer descanso_1, Integer descanso_2, String corrida, Double pago_semana, String corrida_sabado, Double pago_sabado, String corrida_domingo, Double pago_domingo, Integer estatus, Integer permuta, Integer libro, Integer nombramiento, Integer permuta_1, Integer nombramiento_1, String comentario_1, LocalDate fecha_movimiento_1, Integer permuta_2, String nombramiento_2, String comentario_2, LocalDate fecha_movimiento_2, String linea, String deposito, String unidad, Double entra_semana, Double sale_semana, Double horas_trab_semana, Double entra_sabado, Double sale_sabado, Double horas_trab_sabado, Double entra_domingo, Double sale_domingo, Double horas_trab_domingo) {
        this.id_planta = id_planta;
        this.anio = anio;
        this.expediente = expediente;
        this.descansos = descansos;
        this.relevo_1 = relevo_1;
        this.relevo_2 = relevo_2;
        this.descanso_1 = descanso_1;
        this.descanso_2 = descanso_2;
        this.corrida = corrida;
        this.pago_semana = pago_semana;
        this.corrida_sabado = corrida_sabado;
        this.pago_sabado = pago_sabado;
        this.corrida_domingo = corrida_domingo;
        this.pago_domingo = pago_domingo;
        this.estatus = estatus;
        this.permuta = permuta;
        this.libro = libro;
        this.nombramiento = nombramiento;
        this.permuta_1 = permuta_1;
        this.nombramiento_1 = nombramiento_1;
        this.comentario_1 = comentario_1;
        this.fecha_movimiento_1 = fecha_movimiento_1;
        this.permuta_2 = permuta_2;
        this.nombramiento_2 = nombramiento_2;
        this.comentario_2 = comentario_2;
        this.fecha_movimiento_2 = fecha_movimiento_2;
        this.linea = linea;
        this.deposito = deposito;
        this.unidad = unidad;
        this.entra_semana = entra_semana;
        this.sale_semana = sale_semana;
        this.horas_trab_semana = horas_trab_semana;
        this.entra_sabado = entra_sabado;
        this.sale_sabado = sale_sabado;
        this.horas_trab_sabado = horas_trab_sabado;
        this.entra_domingo = entra_domingo;
        this.sale_domingo = sale_domingo;
        this.horas_trab_domingo = horas_trab_domingo;
    }

    public Integer getId_planta() {
        return id_planta;
    }

    public void setId_planta(Integer id_planta) {
        this.id_planta = id_planta;
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

    public Integer getRelevo_1() {
        return relevo_1;
    }

    public void setRelevo_1(Integer relevo_1) {
        this.relevo_1 = relevo_1;
    }

    public Integer getRelevo_2() {
        return relevo_2;
    }

    public void setRelevo_2(Integer relevo_2) {
        this.relevo_2 = relevo_2;
    }

    public Integer getDescanso_1() {
        return descanso_1;
    }

    public void setDescanso_1(Integer descanso_1) {
        this.descanso_1 = descanso_1;
    }

    public Integer getDescanso_2() {
        return descanso_2;
    }

    public void setDescanso_2(Integer descanso_2) {
        this.descanso_2 = descanso_2;
    }

    public String getCorrida() {
        return corrida;
    }

    public void setCorrida(String corrida) {
        this.corrida = corrida;
    }

    public Double getPago_semana() {
        return pago_semana;
    }

    public void setPago_semana(Double pago_semana) {
        this.pago_semana = pago_semana;
    }

    public String getCorrida_sabado() {
        return corrida_sabado;
    }

    public void setCorrida_sabado(String corrida_sabado) {
        this.corrida_sabado = corrida_sabado;
    }

    public Double getPago_sabado() {
        return pago_sabado;
    }

    public void setPago_sabado(Double pago_sabado) {
        this.pago_sabado = pago_sabado;
    }

    public String getCorrida_domingo() {
        return corrida_domingo;
    }

    public void setCorrida_domingo(String corrida_domingo) {
        this.corrida_domingo = corrida_domingo;
    }

    public Double getPago_domingo() {
        return pago_domingo;
    }

    public void setPago_domingo(Double pago_domingo) {
        this.pago_domingo = pago_domingo;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getPermuta() {
        return permuta;
    }

    public void setPermuta(Integer permuta) {
        this.permuta = permuta;
    }

    public Integer getLibro() {
        return libro;
    }

    public void setLibro(Integer libro) {
        this.libro = libro;
    }

    public Integer getNombramiento() {
        return nombramiento;
    }

    public void setNombramiento(Integer nombramiento) {
        this.nombramiento = nombramiento;
    }

    public Integer getPermuta_1() {
        return permuta_1;
    }

    public void setPermuta_1(Integer permuta_1) {
        this.permuta_1 = permuta_1;
    }

    public Integer getNombramiento_1() {
        return nombramiento_1;
    }

    public void setNombramiento_1(Integer nombramiento_1) {
        this.nombramiento_1 = nombramiento_1;
    }

    public String getComentario_1() {
        return comentario_1;
    }

    public void setComentario_1(String comentario_1) {
        this.comentario_1 = comentario_1;
    }

    public LocalDate getFecha_movimiento_1() {
        return fecha_movimiento_1;
    }

    public void setFecha_movimiento_1(LocalDate fecha_movimiento_1) {
        this.fecha_movimiento_1 = fecha_movimiento_1;
    }

    public Integer getPermuta_2() {
        return permuta_2;
    }

    public void setPermuta_2(Integer permuta_2) {
        this.permuta_2 = permuta_2;
    }

    public String getNombramiento_2() {
        return nombramiento_2;
    }

    public void setNombramiento_2(String nombramiento_2) {
        this.nombramiento_2 = nombramiento_2;
    }

    public String getComentario_2() {
        return comentario_2;
    }

    public void setComentario_2(String comentario_2) {
        this.comentario_2 = comentario_2;
    }

    public LocalDate getFecha_movimiento_2() {
        return fecha_movimiento_2;
    }

    public void setFecha_movimiento_2(LocalDate fecha_movimiento_2) {
        this.fecha_movimiento_2 = fecha_movimiento_2;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getDeposito() {
        return deposito;
    }

    public void setDeposito(String deposito) {
        this.deposito = deposito;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Double getEntra_semana() {
        return entra_semana;
    }

    public void setEntra_semana(Double entra_semana) {
        this.entra_semana = entra_semana;
    }

    public Double getSale_semana() {
        return sale_semana;
    }

    public void setSale_semana(Double sale_semana) {
        this.sale_semana = sale_semana;
    }

    public Double getHoras_trab_semana() {
        return horas_trab_semana;
    }

    public void setHoras_trab_semana(Double horas_trab_semana) {
        this.horas_trab_semana = horas_trab_semana;
    }

    public Double getEntra_sabado() {
        return entra_sabado;
    }

    public void setEntra_sabado(Double entra_sabado) {
        this.entra_sabado = entra_sabado;
    }

    public Double getSale_sabado() {
        return sale_sabado;
    }

    public void setSale_sabado(Double sale_sabado) {
        this.sale_sabado = sale_sabado;
    }

    public Double getHoras_trab_sabado() {
        return horas_trab_sabado;
    }

    public void setHoras_trab_sabado(Double horas_trab_sabado) {
        this.horas_trab_sabado = horas_trab_sabado;
    }

    public Double getEntra_domingo() {
        return entra_domingo;
    }

    public void setEntra_domingo(Double entra_domingo) {
        this.entra_domingo = entra_domingo;
    }

    public Double getSale_domingo() {
        return sale_domingo;
    }

    public void setSale_domingo(Double sale_domingo) {
        this.sale_domingo = sale_domingo;
    }

    public Double getHoras_trab_domingo() {
        return horas_trab_domingo;
    }

    public void setHoras_trab_domingo(Double horas_trab_domingo) {
        this.horas_trab_domingo = horas_trab_domingo;
    }

    
}
