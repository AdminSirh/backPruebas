/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author nreyes22
 */
public class Cat_PuestoDTO {
    
    private Integer id_puesto;
    
    private Integer codigo_puesto;
    private Integer nivel;
    private String puesto;
    private double sueldo_diario;
    private double sueldo_diarioDoble;
    private double sueldo_hora;
    private double sueldo_horaDoble;
    private double sueldo_horaTriple;
    private LocalDate fecha_movimiento;
    private Integer status;
    private double sueldo_mensual;
    private double cantidad_adicionalMensual;
    private Integer num_plazasAutorizadas;
    private double sueldo_quincenalTabulado;
    private double dif_sueldoDiario;
    private double dif_sueldoHora;
    private double dif_sueldoMensual;
    private double dif_cantAdicMens;
    private double isr_mensPrest;
    private double isr_mensual;
    private double cuota_imss;
    private double sueldo_mensualNeto;
    private String codigo_rh;
    private double sueldo_estructura;
    private double sueldoHora7;

    public Cat_PuestoDTO(Integer id_puesto, Integer codigo_puesto, Integer nivel, String puesto, double sueldo_diario, double sueldo_diarioDoble, double sueldo_hora, double sueldo_horaDoble, double sueldo_horaTriple, LocalDate fecha_movimiento, Integer status, double sueldo_mensual, double cantidad_adicionalMensual, Integer num_plazasAutorizadas, double sueldo_quincenalTabulado, double dif_sueldoDiario, double dif_sueldoHora, double dif_sueldoMensual, double dif_cantAdicMens, double isr_mensPrest, double isr_mensual, double cuota_imss, double sueldo_mensualNeto, String codigo_rh, double sueldo_estructura, double sueldoHora7) {
        this.id_puesto = id_puesto;
        this.codigo_puesto = codigo_puesto;
        this.nivel = nivel;
        this.puesto = puesto;
        this.sueldo_diario = sueldo_diario;
        this.sueldo_diarioDoble = sueldo_diarioDoble;
        this.sueldo_hora = sueldo_hora;
        this.sueldo_horaDoble = sueldo_horaDoble;
        this.sueldo_horaTriple = sueldo_horaTriple;
        this.fecha_movimiento = fecha_movimiento;
        this.status = status;
        this.sueldo_mensual = sueldo_mensual;
        this.cantidad_adicionalMensual = cantidad_adicionalMensual;
        this.num_plazasAutorizadas = num_plazasAutorizadas;
        this.sueldo_quincenalTabulado = sueldo_quincenalTabulado;
        this.dif_sueldoDiario = dif_sueldoDiario;
        this.dif_sueldoHora = dif_sueldoHora;
        this.dif_sueldoMensual = dif_sueldoMensual;
        this.dif_cantAdicMens = dif_cantAdicMens;
        this.isr_mensPrest = isr_mensPrest;
        this.isr_mensual = isr_mensual;
        this.cuota_imss = cuota_imss;
        this.sueldo_mensualNeto = sueldo_mensualNeto;
        this.codigo_rh = codigo_rh;
        this.sueldo_estructura = sueldo_estructura;
        this.sueldoHora7 = sueldoHora7;
    }

    public Integer getId_puesto() {
        return id_puesto;
    }

    public void setId_puesto(Integer id_puesto) {
        this.id_puesto = id_puesto;
    }

    public Integer getCodigo_puesto() {
        return codigo_puesto;
    }

    public void setCodigo_puesto(Integer codigo_puesto) {
        this.codigo_puesto = codigo_puesto;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSueldo_diario() {
        return sueldo_diario;
    }

    public void setSueldo_diario(double sueldo_diario) {
        this.sueldo_diario = sueldo_diario;
    }

    public double getSueldo_diarioDoble() {
        return sueldo_diarioDoble;
    }

    public void setSueldo_diarioDoble(double sueldo_diarioDoble) {
        this.sueldo_diarioDoble = sueldo_diarioDoble;
    }

    public double getSueldo_hora() {
        return sueldo_hora;
    }

    public void setSueldo_hora(double sueldo_hora) {
        this.sueldo_hora = sueldo_hora;
    }

    public double getSueldo_horaDoble() {
        return sueldo_horaDoble;
    }

    public void setSueldo_horaDoble(double sueldo_horaDoble) {
        this.sueldo_horaDoble = sueldo_horaDoble;
    }

    public double getSueldo_horaTriple() {
        return sueldo_horaTriple;
    }

    public void setSueldo_horaTriple(double sueldo_horaTriple) {
        this.sueldo_horaTriple = sueldo_horaTriple;
    }

    public LocalDate getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDate fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public double getSueldo_mensual() {
        return sueldo_mensual;
    }

    public void setSueldo_mensual(double sueldo_mensual) {
        this.sueldo_mensual = sueldo_mensual;
    }

    public double getCantidad_adicionalMensual() {
        return cantidad_adicionalMensual;
    }

    public void setCantidad_adicionalMensual(double cantidad_adicionalMensual) {
        this.cantidad_adicionalMensual = cantidad_adicionalMensual;
    }

    public Integer getNum_plazasAutorizadas() {
        return num_plazasAutorizadas;
    }

    public void setNum_plazasAutorizadas(Integer num_plazasAutorizadas) {
        this.num_plazasAutorizadas = num_plazasAutorizadas;
    }

    public double getSueldo_quincenalTabulado() {
        return sueldo_quincenalTabulado;
    }

    public void setSueldo_quincenalTabulado(double sueldo_quincenalTabulado) {
        this.sueldo_quincenalTabulado = sueldo_quincenalTabulado;
    }

    public double getDif_sueldoDiario() {
        return dif_sueldoDiario;
    }

    public void setDif_sueldoDiario(double dif_sueldoDiario) {
        this.dif_sueldoDiario = dif_sueldoDiario;
    }

    public double getDif_sueldoHora() {
        return dif_sueldoHora;
    }

    public void setDif_sueldoHora(double dif_sueldoHora) {
        this.dif_sueldoHora = dif_sueldoHora;
    }

    public double getDif_sueldoMensual() {
        return dif_sueldoMensual;
    }

    public void setDif_sueldoMensual(double dif_sueldoMensual) {
        this.dif_sueldoMensual = dif_sueldoMensual;
    }

    public double getDif_cantAdicMens() {
        return dif_cantAdicMens;
    }

    public void setDif_cantAdicMens(double dif_cantAdicMens) {
        this.dif_cantAdicMens = dif_cantAdicMens;
    }

    public double getIsr_mensPrest() {
        return isr_mensPrest;
    }

    public void setIsr_mensPrest(double isr_mensPrest) {
        this.isr_mensPrest = isr_mensPrest;
    }

    public double getIsr_mensual() {
        return isr_mensual;
    }

    public void setIsr_mensual(double isr_mensual) {
        this.isr_mensual = isr_mensual;
    }

    public double getCuota_imss() {
        return cuota_imss;
    }

    public void setCuota_imss(double cuota_imss) {
        this.cuota_imss = cuota_imss;
    }

    public double getSueldo_mensualNeto() {
        return sueldo_mensualNeto;
    }

    public void setSueldo_mensualNeto(double sueldo_mensualNeto) {
        this.sueldo_mensualNeto = sueldo_mensualNeto;
    }

    public String getCodigo_rh() {
        return codigo_rh;
    }

    public void setCodigo_rh(String codigo_rh) {
        this.codigo_rh = codigo_rh;
    }

    public double getSueldo_estructura() {
        return sueldo_estructura;
    }

    public void setSueldo_estructura(double sueldo_estructura) {
        this.sueldo_estructura = sueldo_estructura;
    }

    public double getSueldoHora7() {
        return sueldoHora7;
    }

    public void setSueldoHora7(double sueldoHora7) {
        this.sueldoHora7 = sueldoHora7;
    }

    
}
