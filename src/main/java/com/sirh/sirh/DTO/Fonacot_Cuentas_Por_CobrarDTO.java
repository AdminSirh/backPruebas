/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author oguerrero23
 */
public class Fonacot_Cuentas_Por_CobrarDTO {
    private Integer id_cuentas_cobrar;
    private Double saldo_actual;
    private Double monto_pago;
    private Integer periodo_pago_actual;
    private Integer nomina_id;
    private Double saldo_vacs;
    private Integer trabajador_id;

    public Fonacot_Cuentas_Por_CobrarDTO() {
        super();
    }

    public Fonacot_Cuentas_Por_CobrarDTO(Integer id_cuentas_cobrar, Double saldo_actual, Double monto_pago, Integer periodo_pago_actual, Integer nomina_id, Double saldo_vacs, Integer trabajador_id) {
        this.id_cuentas_cobrar = id_cuentas_cobrar;
        this.saldo_actual = saldo_actual;
        this.monto_pago = monto_pago;
        this.periodo_pago_actual = periodo_pago_actual;
        this.nomina_id = nomina_id;
        this.saldo_vacs = saldo_vacs;
        this.trabajador_id = trabajador_id;
    }

    public Integer getId_cuentas_cobrar() {
        return id_cuentas_cobrar;
    }

    public void setId_cuentas_cobrar(Integer id_cuentas_cobrar) {
        this.id_cuentas_cobrar = id_cuentas_cobrar;
    }

    public Double getSaldo_actual() {
        return saldo_actual;
    }

    public void setSaldo_actual(Double saldo_actual) {
        this.saldo_actual = saldo_actual;
    }

    public Double getMonto_pago() {
        return monto_pago;
    }

    public void setMonto_pago(Double monto_pago) {
        this.monto_pago = monto_pago;
    }

    public Integer getPeriodo_pago_actual() {
        return periodo_pago_actual;
    }

    public void setPeriodo_pago_actual(Integer periodo_pago_actual) {
        this.periodo_pago_actual = periodo_pago_actual;
    }

    public Integer getNomina_id() {
        return nomina_id;
    }

    public void setNomina_id(Integer nomina_id) {
        this.nomina_id = nomina_id;
    }

    public Double getSaldo_vacs() {
        return saldo_vacs;
    }

    public void setSaldo_vacs(Double saldo_vacs) {
        this.saldo_vacs = saldo_vacs;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }
    
    
}
