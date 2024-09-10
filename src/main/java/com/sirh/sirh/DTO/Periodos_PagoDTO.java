/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author oguerrero23
 */
public class Periodos_PagoDTO {
    private Integer id_periodo_pago;
    
    private String mes;
    private Integer anio;
    private Integer periodo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_inicial;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_final;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_inicial_sueldo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_final_sueldo;
    private Integer dias_incluidos;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_limite;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_corte;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_entrega_caja;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_entrega_caja_anticipada;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_pago;
    private Integer pagado;
    private Integer id_nomina;
    private Integer estimulo_puntualidad;
    private Integer estimulo_apego_reglamento;
    private Integer ayuda_transporte;
    private Integer quinquenio_mensual;
    private Integer vales_despensa;
    private Integer aguinaldo;
    private Integer especial;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_movimiento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_inicial_das;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_final_das;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_inicial_est_vac;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_final_est_vac;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_imss;
    private Integer pago_pens;

    public Periodos_PagoDTO() {
       super();
    }

    public Periodos_PagoDTO(Integer id_periodo_pago, String mes, Integer anio, Integer periodo, LocalDate fecha_inicial, LocalDate fecha_final, LocalDate fecha_inicial_sueldo, LocalDate fecha_final_sueldo, Integer dias_incluidos, LocalDate fecha_limite, LocalDate fecha_corte, LocalDate fecha_entrega_caja, LocalDate fecha_entrega_caja_anticipada, LocalDate fecha_pago, Integer pagado, Integer id_nomina, Integer estimulo_puntualidad, Integer estimulo_apego_reglamento, Integer ayuda_transporte, Integer quinquenio_mensual, Integer vales_despensa, Integer aguinaldo, Integer especial, LocalDate fecha_movimiento, LocalDate fecha_inicial_das, LocalDate fecha_final_das, LocalDate fecha_inicial_est_vac, LocalDate fecha_final_est_vac, LocalDate fecha_imss, Integer pago_pens) {
        this.id_periodo_pago = id_periodo_pago;
        this.mes = mes;
        this.anio = anio;
        this.periodo = periodo;
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
        this.fecha_inicial_sueldo = fecha_inicial_sueldo;
        this.fecha_final_sueldo = fecha_final_sueldo;
        this.dias_incluidos = dias_incluidos;
        this.fecha_limite = fecha_limite;
        this.fecha_corte = fecha_corte;
        this.fecha_entrega_caja = fecha_entrega_caja;
        this.fecha_entrega_caja_anticipada = fecha_entrega_caja_anticipada;
        this.fecha_pago = fecha_pago;
        this.pagado = pagado;
        this.id_nomina = id_nomina;
        this.estimulo_puntualidad = estimulo_puntualidad;
        this.estimulo_apego_reglamento = estimulo_apego_reglamento;
        this.ayuda_transporte = ayuda_transporte;
        this.quinquenio_mensual = quinquenio_mensual;
        this.vales_despensa = vales_despensa;
        this.aguinaldo = aguinaldo;
        this.especial = especial;
        this.fecha_movimiento = fecha_movimiento;
        this.fecha_inicial_das = fecha_inicial_das;
        this.fecha_final_das = fecha_final_das;
        this.fecha_inicial_est_vac = fecha_inicial_est_vac;
        this.fecha_final_est_vac = fecha_final_est_vac;
        this.fecha_imss = fecha_imss;
        this.pago_pens = pago_pens;
    }

    public Integer getId_periodo_pago() {
        return id_periodo_pago;
    }

    public void setId_periodo_pago(Integer id_periodo_pago) {
        this.id_periodo_pago = id_periodo_pago;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
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

    public LocalDate getFecha_inicial_sueldo() {
        return fecha_inicial_sueldo;
    }

    public void setFecha_inicial_sueldo(LocalDate fecha_inicial_sueldo) {
        this.fecha_inicial_sueldo = fecha_inicial_sueldo;
    }

    public LocalDate getFecha_final_sueldo() {
        return fecha_final_sueldo;
    }

    public void setFecha_final_sueldo(LocalDate fecha_final_sueldo) {
        this.fecha_final_sueldo = fecha_final_sueldo;
    }

    public Integer getDias_incluidos() {
        return dias_incluidos;
    }

    public void setDias_incluidos(Integer dias_incluidos) {
        this.dias_incluidos = dias_incluidos;
    }

    public LocalDate getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(LocalDate fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public LocalDate getFecha_corte() {
        return fecha_corte;
    }

    public void setFecha_corte(LocalDate fecha_corte) {
        this.fecha_corte = fecha_corte;
    }

    public LocalDate getFecha_entrega_caja() {
        return fecha_entrega_caja;
    }

    public void setFecha_entrega_caja(LocalDate fecha_entrega_caja) {
        this.fecha_entrega_caja = fecha_entrega_caja;
    }

    public LocalDate getFecha_entrega_caja_anticipada() {
        return fecha_entrega_caja_anticipada;
    }

    public void setFecha_entrega_caja_anticipada(LocalDate fecha_entrega_caja_anticipada) {
        this.fecha_entrega_caja_anticipada = fecha_entrega_caja_anticipada;
    }

    public LocalDate getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(LocalDate fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public Integer getPagado() {
        return pagado;
    }

    public void setPagado(Integer pagado) {
        this.pagado = pagado;
    }

    public Integer getId_nomina() {
        return id_nomina;
    }

    public void setId_nomina(Integer id_nomina) {
        this.id_nomina = id_nomina;
    }

    public Integer getEstimulo_puntualidad() {
        return estimulo_puntualidad;
    }

    public void setEstimulo_puntualidad(Integer estimulo_puntualidad) {
        this.estimulo_puntualidad = estimulo_puntualidad;
    }

    public Integer getEstimulo_apego_reglamento() {
        return estimulo_apego_reglamento;
    }

    public void setEstimulo_apego_reglamento(Integer estimulo_apego_reglamento) {
        this.estimulo_apego_reglamento = estimulo_apego_reglamento;
    }

    public Integer getAyuda_transporte() {
        return ayuda_transporte;
    }

    public void setAyuda_transporte(Integer ayuda_transporte) {
        this.ayuda_transporte = ayuda_transporte;
    }

    public Integer getQuinquenio_mensual() {
        return quinquenio_mensual;
    }

    public void setQuinquenio_mensual(Integer quinquenio_mensual) {
        this.quinquenio_mensual = quinquenio_mensual;
    }

    public Integer getVales_despensa() {
        return vales_despensa;
    }

    public void setVales_despensa(Integer vales_despensa) {
        this.vales_despensa = vales_despensa;
    }

    public Integer getAguinaldo() {
        return aguinaldo;
    }

    public void setAguinaldo(Integer aguinaldo) {
        this.aguinaldo = aguinaldo;
    }

    public Integer getEspecial() {
        return especial;
    }

    public void setEspecial(Integer especial) {
        this.especial = especial;
    }

    public LocalDate getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDate fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public LocalDate getFecha_inicial_das() {
        return fecha_inicial_das;
    }

    public void setFecha_inicial_das(LocalDate fecha_inicial_das) {
        this.fecha_inicial_das = fecha_inicial_das;
    }

    public LocalDate getFecha_final_das() {
        return fecha_final_das;
    }

    public void setFecha_final_das(LocalDate fecha_final_das) {
        this.fecha_final_das = fecha_final_das;
    }

    public LocalDate getFecha_inicial_est_vac() {
        return fecha_inicial_est_vac;
    }

    public void setFecha_inicial_est_vac(LocalDate fecha_inicial_est_vac) {
        this.fecha_inicial_est_vac = fecha_inicial_est_vac;
    }

    public LocalDate getFecha_final_est_vac() {
        return fecha_final_est_vac;
    }

    public void setFecha_final_est_vac(LocalDate fecha_final_est_vac) {
        this.fecha_final_est_vac = fecha_final_est_vac;
    }

    public LocalDate getFecha_imss() {
        return fecha_imss;
    }

    public void setFecha_imss(LocalDate fecha_imss) {
        this.fecha_imss = fecha_imss;
    }

    public Integer getPago_pens() {
        return pago_pens;
    }

    public void setPago_pens(Integer pago_pens) {
        this.pago_pens = pago_pens;
    }

    
}
