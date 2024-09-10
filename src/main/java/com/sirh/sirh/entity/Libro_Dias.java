/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import org.springframework.data.annotation.LastModifiedDate;

/**
 *
 * @author akalvarez19
 */
@Entity
@Table(name = "libro_dias")
public class Libro_Dias implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_libro_dias;

    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dia_id")
    private Cat_Dias cat_dias;

    private String horario_entrada;

    private String horario_salida;

    private Double horas_trabajadas;

    private Double horas_pago;

    @CreatedDate
    private LocalDateTime fecha_captura;

    @LastModifiedDate
    private LocalDateTime fecha_movimiento;

    private Integer estatus;
    private Integer trabajador_id;

    private Integer dia_descanso;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deposito_id")
    private Cat_Depositos cat_Depositos;
    private Integer expediente;

    public Libro_Dias() {
        super();
    }

    public Libro_Dias(Integer id_libro_dias, Cat_Dias cat_dias, String horario_entrada, String horario_salida, Double horas_trabajadas, Double horas_pago, LocalDateTime fecha_captura, LocalDateTime fecha_movimiento, Integer estatus, Integer trabajador_id, Integer dia_descanso, Cat_Depositos cat_Depositos, Integer expediente) {
        this.id_libro_dias = id_libro_dias;
        this.cat_dias = cat_dias;
        this.horario_entrada = horario_entrada;
        this.horario_salida = horario_salida;
        this.horas_trabajadas = horas_trabajadas;
        this.horas_pago = horas_pago;
        this.fecha_captura = fecha_captura;
        this.fecha_movimiento = fecha_movimiento;
        this.estatus = estatus;
        this.trabajador_id = trabajador_id;
        this.dia_descanso = dia_descanso;
        this.cat_Depositos = cat_Depositos;
        this.expediente = expediente;
    }

    public Integer getId_libro_dias() {
        return id_libro_dias;
    }

    public void setId_libro_dias(Integer id_libro_dias) {
        this.id_libro_dias = id_libro_dias;
    }

    public Cat_Dias getCat_dias() {
        return cat_dias;
    }

    public void setCat_dias(Cat_Dias cat_dias) {
        this.cat_dias = cat_dias;
    }

    public String getHorario_entrada() {
        return horario_entrada;
    }

    public void setHorario_entrada(String horario_entrada) {
        this.horario_entrada = horario_entrada;
    }

    public String getHorario_salida() {
        return horario_salida;
    }

    public void setHorario_salida(String horario_salida) {
        this.horario_salida = horario_salida;
    }

    public Double getHoras_trabajadas() {
        return horas_trabajadas;
    }

    public void setHoras_trabajadas(Double horas_trabajadas) {
        this.horas_trabajadas = horas_trabajadas;
    }

    public Double getHoras_pago() {
        return horas_pago;
    }

    public void setHoras_pago(Double horas_pago) {
        this.horas_pago = horas_pago;
    }

    public LocalDateTime getFecha_captura() {
        return fecha_captura;
    }

    public void setFecha_captura(LocalDateTime fecha_captura) {
        this.fecha_captura = fecha_captura;
    }

    public LocalDateTime getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDateTime fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getDia_descanso() {
        return dia_descanso;
    }

    public void setDia_descanso(Integer dia_descanso) {
        this.dia_descanso = dia_descanso;
    }

    public Cat_Depositos getCat_Depositos() {
        return cat_Depositos;
    }

    public void setCat_Depositos(Cat_Depositos cat_Depositos) {
        this.cat_Depositos = cat_Depositos;
    }

    public Integer getExpediente() {
        return expediente;
    }

    public void setExpediente(Integer expediente) {
        this.expediente = expediente;
    }
}
