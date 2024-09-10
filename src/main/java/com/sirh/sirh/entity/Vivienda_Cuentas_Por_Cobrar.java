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
@Table(name = "vivienda_cuentas_por_cobrar")
public class Vivienda_Cuentas_Por_Cobrar implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_vivienda_cuentas_cobrar;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trabajador_id")
    private Trabajador trabajador;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nomina_id")
    private Cat_Tipo_Nomina cat_Tipo_Nomina;
    
    private Double saldo_inicial;
    private Double saldo_actual;
    private Double saldo_vacs;
    private Double monto_pago;
    private Integer numero_periodos_pago;
    private Integer periodo_pago_actual;
    private Double remanente_ultimo_pago;
    private String criterio_pago;
    private Double monto_criterio_pago;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_inicio;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_fin;
    
    private Integer periodo_inicial;
    private Integer destino;
    private String referencia;
    private Double saldo_actual_pr;
    private Double remanente_ultimo_pago_pr;
    private Integer estatus;
    private Integer numero_deuda;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    private LocalDate fecha_movimiento;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_cancelacion_cuenta;
    
    private String fecha_referencia;
    
    private Double retencion_norm;
    private Double retencion_vac;
    private Double devolucion;

    public Vivienda_Cuentas_Por_Cobrar() {
        super();
    }

    public Vivienda_Cuentas_Por_Cobrar(Integer id_vivienda_cuentas_cobrar, Trabajador trabajador, Cat_Tipo_Nomina cat_Tipo_Nomina, Double saldo_inicial, Double saldo_actual, Double saldo_vacs, Double monto_pago, Integer numero_periodos_pago, Integer periodo_pago_actual, Double remanente_ultimo_pago, String criterio_pago, Double monto_criterio_pago, LocalDate fecha_inicio, LocalDate fecha_fin, Integer periodo_inicial, Integer destino, String referencia, Double saldo_actual_pr, Double remanente_ultimo_pago_pr, Integer estatus, Integer numero_deuda, LocalDate fecha_movimiento, LocalDate fecha_cancelacion_cuenta, String fecha_referencia, Double retencion_norm, Double retencion_vac, Double devolucion) {
        this.id_vivienda_cuentas_cobrar = id_vivienda_cuentas_cobrar;
        this.trabajador = trabajador;
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
        this.saldo_inicial = saldo_inicial;
        this.saldo_actual = saldo_actual;
        this.saldo_vacs = saldo_vacs;
        this.monto_pago = monto_pago;
        this.numero_periodos_pago = numero_periodos_pago;
        this.periodo_pago_actual = periodo_pago_actual;
        this.remanente_ultimo_pago = remanente_ultimo_pago;
        this.criterio_pago = criterio_pago;
        this.monto_criterio_pago = monto_criterio_pago;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.periodo_inicial = periodo_inicial;
        this.destino = destino;
        this.referencia = referencia;
        this.saldo_actual_pr = saldo_actual_pr;
        this.remanente_ultimo_pago_pr = remanente_ultimo_pago_pr;
        this.estatus = estatus;
        this.numero_deuda = numero_deuda;
        this.fecha_movimiento = fecha_movimiento;
        this.fecha_cancelacion_cuenta = fecha_cancelacion_cuenta;
        this.fecha_referencia = fecha_referencia;
        this.retencion_norm = retencion_norm;
        this.retencion_vac = retencion_vac;
        this.devolucion = devolucion;
    }

    public Integer getId_vivienda_cuentas_cobrar() {
        return id_vivienda_cuentas_cobrar;
    }

    public void setId_vivienda_cuentas_cobrar(Integer id_vivienda_cuentas_cobrar) {
        this.id_vivienda_cuentas_cobrar = id_vivienda_cuentas_cobrar;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Cat_Tipo_Nomina getCat_Tipo_Nomina() {
        return cat_Tipo_Nomina;
    }

    public void setCat_Tipo_Nomina(Cat_Tipo_Nomina cat_Tipo_Nomina) {
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
    }

    public Double getSaldo_inicial() {
        return saldo_inicial;
    }

    public void setSaldo_inicial(Double saldo_inicial) {
        this.saldo_inicial = saldo_inicial;
    }

    public Double getSaldo_actual() {
        return saldo_actual;
    }

    public void setSaldo_actual(Double saldo_actual) {
        this.saldo_actual = saldo_actual;
    }

    public Double getSaldo_vacs() {
        return saldo_vacs;
    }

    public void setSaldo_vacs(Double saldo_vacs) {
        this.saldo_vacs = saldo_vacs;
    }

    public Double getMonto_pago() {
        return monto_pago;
    }

    public void setMonto_pago(Double monto_pago) {
        this.monto_pago = monto_pago;
    }

    public Integer getNumero_periodos_pago() {
        return numero_periodos_pago;
    }

    public void setNumero_periodos_pago(Integer numero_periodos_pago) {
        this.numero_periodos_pago = numero_periodos_pago;
    }

    public Integer getPeriodo_pago_actual() {
        return periodo_pago_actual;
    }

    public void setPeriodo_pago_actual(Integer periodo_pago_actual) {
        this.periodo_pago_actual = periodo_pago_actual;
    }

    public Double getRemanente_ultimo_pago() {
        return remanente_ultimo_pago;
    }

    public void setRemanente_ultimo_pago(Double remanente_ultimo_pago) {
        this.remanente_ultimo_pago = remanente_ultimo_pago;
    }

    public String getCriterio_pago() {
        return criterio_pago;
    }

    public void setCriterio_pago(String criterio_pago) {
        this.criterio_pago = criterio_pago;
    }

    public Double getMonto_criterio_pago() {
        return monto_criterio_pago;
    }

    public void setMonto_criterio_pago(Double monto_criterio_pago) {
        this.monto_criterio_pago = monto_criterio_pago;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Integer getPeriodo_inicial() {
        return periodo_inicial;
    }

    public void setPeriodo_inicial(Integer periodo_inicial) {
        this.periodo_inicial = periodo_inicial;
    }

    public Integer getDestino() {
        return destino;
    }

    public void setDestino(Integer destino) {
        this.destino = destino;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Double getSaldo_actual_pr() {
        return saldo_actual_pr;
    }

    public void setSaldo_actual_pr(Double saldo_actual_pr) {
        this.saldo_actual_pr = saldo_actual_pr;
    }

    public Double getRemanente_ultimo_pago_pr() {
        return remanente_ultimo_pago_pr;
    }

    public void setRemanente_ultimo_pago_pr(Double remanente_ultimo_pago_pr) {
        this.remanente_ultimo_pago_pr = remanente_ultimo_pago_pr;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getNumero_deuda() {
        return numero_deuda;
    }

    public void setNumero_deuda(Integer numero_deuda) {
        this.numero_deuda = numero_deuda;
    }

    public LocalDate getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDate fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public LocalDate getFecha_cancelacion_cuenta() {
        return fecha_cancelacion_cuenta;
    }

    public void setFecha_cancelacion_cuenta(LocalDate fecha_cancelacion_cuenta) {
        this.fecha_cancelacion_cuenta = fecha_cancelacion_cuenta;
    }

    public String getFecha_referencia() {
        return fecha_referencia;
    }

    public void setFecha_referencia(String fecha_referencia) {
        this.fecha_referencia = fecha_referencia;
    }

    public Double getRetencion_norm() {
        return retencion_norm;
    }

    public void setRetencion_norm(Double retencion_norm) {
        this.retencion_norm = retencion_norm;
    }

    public Double getRetencion_vac() {
        return retencion_vac;
    }

    public void setRetencion_vac(Double retencion_vac) {
        this.retencion_vac = retencion_vac;
    }

    public Double getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Double devolucion) {
        this.devolucion = devolucion;
    }

    
}
