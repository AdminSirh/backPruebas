
package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author nreyes22
 */

@Entity
@Table(name = "catalogo_puesto")
public class Cat_Puesto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_puesto;
    
    private Integer codigo_puesto;
    private Integer nivel;
    private String puesto;
    
    private String puesto_oficilia_mayor;
    private double sueldo_diario;
    private double sueldo_diario_doble;
    private double sueldo_hora;
    private double sueldo_hora_doble;
    private double sueldo_hora_triple;
    private double sdi_inicial;
    
    @CreatedDate
    private LocalDate fecha_movimiento;
    private Integer status;
    private double sueldo_mensual;
    private double cantidad_adicional_mensual;
    private String hw_status;
    private String hwx_hora;
    private String autorizacion_oficialia_mayor;
    private Integer num_plazas_autorizadas;
    private Integer autorizadopor_oficialia_mayor;
    private String base_confianza;
    private String clave_RH;
    private double sueldo_quincenal_tabulado;
    private double dif_sueldo_diario;
    private double dif_sueldo_hora;
    private double dif_sueldo_mensual;
    private double dif_cant_adicmens;
    private double isr_mens_prest;
    private double isr_mensual;
    private double cuota_imss;
    private double sueldo_mensual_neto;
    private String codigo_rh;
    private double sueldo_estructura;
    private double sueldoHora7;
    @CreatedDate
    private LocalDate fecha_captura;
    
    public Cat_Puesto() {
    }

    public Cat_Puesto(Integer id_puesto, Integer codigo_puesto, Integer nivel, String puesto, String puesto_oficilia_mayor, double sueldo_diario, double sueldo_diario_doble, double sueldo_hora, double sueldo_hora_doble, double sueldo_hora_triple, double sdi_inicial, LocalDate fecha_movimiento, Integer status, double sueldo_mensual, double cantidad_adicional_mensual, String hw_status, String hwx_hora, String autorizacion_oficialia_mayor, Integer num_plazas_autorizadas, Integer autorizadopor_oficialia_mayor, String base_confianza, String clave_RH, double sueldo_quincenal_tabulado, double dif_sueldo_diario, double dif_sueldo_hora, double dif_sueldo_mensual, double dif_cant_adicmens, double isr_mens_prest, double isr_mensual, double cuota_imss, double sueldo_mensual_neto, String codigo_rh, double sueldo_estructura, double sueldoHora7, LocalDate fecha_captura) {
        this.id_puesto = id_puesto;
        this.codigo_puesto = codigo_puesto;
        this.nivel = nivel;
        this.puesto = puesto;
        this.puesto_oficilia_mayor = puesto_oficilia_mayor;
        this.sueldo_diario = sueldo_diario;
        this.sueldo_diario_doble = sueldo_diario_doble;
        this.sueldo_hora = sueldo_hora;
        this.sueldo_hora_doble = sueldo_hora_doble;
        this.sueldo_hora_triple = sueldo_hora_triple;
        this.sdi_inicial = sdi_inicial;
        this.fecha_movimiento = fecha_movimiento;
        this.status = status;
        this.sueldo_mensual = sueldo_mensual;
        this.cantidad_adicional_mensual = cantidad_adicional_mensual;
        this.hw_status = hw_status;
        this.hwx_hora = hwx_hora;
        this.autorizacion_oficialia_mayor = autorizacion_oficialia_mayor;
        this.num_plazas_autorizadas = num_plazas_autorizadas;
        this.autorizadopor_oficialia_mayor = autorizadopor_oficialia_mayor;
        this.base_confianza = base_confianza;
        this.clave_RH = clave_RH;
        this.sueldo_quincenal_tabulado = sueldo_quincenal_tabulado;
        this.dif_sueldo_diario = dif_sueldo_diario;
        this.dif_sueldo_hora = dif_sueldo_hora;
        this.dif_sueldo_mensual = dif_sueldo_mensual;
        this.dif_cant_adicmens = dif_cant_adicmens;
        this.isr_mens_prest = isr_mens_prest;
        this.isr_mensual = isr_mensual;
        this.cuota_imss = cuota_imss;
        this.sueldo_mensual_neto = sueldo_mensual_neto;
        this.codigo_rh = codigo_rh;
        this.sueldo_estructura = sueldo_estructura;
        this.sueldoHora7 = sueldoHora7;
        this.fecha_captura = fecha_captura;
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

    public String getPuesto_oficilia_mayor() {
        return puesto_oficilia_mayor;
    }

    public void setPuesto_oficilia_mayor(String puesto_oficilia_mayor) {
        this.puesto_oficilia_mayor = puesto_oficilia_mayor;
    }

    public double getSueldo_diario() {
        return sueldo_diario;
    }

    public void setSueldo_diario(double sueldo_diario) {
        this.sueldo_diario = sueldo_diario;
    }

    public double getSueldo_diario_doble() {
        return sueldo_diario_doble;
    }

    public void setSueldo_diario_doble(double sueldo_diario_doble) {
        this.sueldo_diario_doble = sueldo_diario_doble;
    }

    public double getSueldo_hora() {
        return sueldo_hora;
    }

    public void setSueldo_hora(double sueldo_hora) {
        this.sueldo_hora = sueldo_hora;
    }

    public double getSueldo_hora_doble() {
        return sueldo_hora_doble;
    }

    public void setSueldo_hora_doble(double sueldo_hora_doble) {
        this.sueldo_hora_doble = sueldo_hora_doble;
    }

    public double getSueldo_hora_triple() {
        return sueldo_hora_triple;
    }

    public void setSueldo_hora_triple(double sueldo_hora_triple) {
        this.sueldo_hora_triple = sueldo_hora_triple;
    }

    public double getSdi_inicial() {
        return sdi_inicial;
    }

    public void setSdi_inicial(double sdi_inicial) {
        this.sdi_inicial = sdi_inicial;
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

    public double getCantidad_adicional_mensual() {
        return cantidad_adicional_mensual;
    }

    public void setCantidad_adicional_mensual(double cantidad_adicional_mensual) {
        this.cantidad_adicional_mensual = cantidad_adicional_mensual;
    }

    public String getHw_status() {
        return hw_status;
    }

    public void setHw_status(String hw_status) {
        this.hw_status = hw_status;
    }

    public String getHwx_hora() {
        return hwx_hora;
    }

    public void setHwx_hora(String hwx_hora) {
        this.hwx_hora = hwx_hora;
    }

    public String getAutorizacion_oficialia_mayor() {
        return autorizacion_oficialia_mayor;
    }

    public void setAutorizacion_oficialia_mayor(String autorizacion_oficialia_mayor) {
        this.autorizacion_oficialia_mayor = autorizacion_oficialia_mayor;
    }

    public Integer getNum_plazas_autorizadas() {
        return num_plazas_autorizadas;
    }

    public void setNum_plazas_autorizadas(Integer num_plazas_autorizadas) {
        this.num_plazas_autorizadas = num_plazas_autorizadas;
    }

    public Integer getAutorizadopor_oficialia_mayor() {
        return autorizadopor_oficialia_mayor;
    }

    public void setAutorizadopor_oficialia_mayor(Integer autorizadopor_oficialia_mayor) {
        this.autorizadopor_oficialia_mayor = autorizadopor_oficialia_mayor;
    }

    public String getBase_confianza() {
        return base_confianza;
    }

    public void setBase_confianza(String base_confianza) {
        this.base_confianza = base_confianza;
    }

    public String getClave_RH() {
        return clave_RH;
    }

    public void setClave_RH(String clave_RH) {
        this.clave_RH = clave_RH;
    }

    public double getSueldo_quincenal_tabulado() {
        return sueldo_quincenal_tabulado;
    }

    public void setSueldo_quincenal_tabulado(double sueldo_quincenal_tabulado) {
        this.sueldo_quincenal_tabulado = sueldo_quincenal_tabulado;
    }

    public double getDif_sueldo_diario() {
        return dif_sueldo_diario;
    }

    public void setDif_sueldo_diario(double dif_sueldo_diario) {
        this.dif_sueldo_diario = dif_sueldo_diario;
    }

    public double getDif_sueldo_hora() {
        return dif_sueldo_hora;
    }

    public void setDif_sueldo_hora(double dif_sueldo_hora) {
        this.dif_sueldo_hora = dif_sueldo_hora;
    }

    public double getDif_sueldo_mensual() {
        return dif_sueldo_mensual;
    }

    public void setDif_sueldo_mensual(double dif_sueldo_mensual) {
        this.dif_sueldo_mensual = dif_sueldo_mensual;
    }

    public double getDif_cant_adicmens() {
        return dif_cant_adicmens;
    }

    public void setDif_cant_adicmens(double dif_cant_adicmens) {
        this.dif_cant_adicmens = dif_cant_adicmens;
    }

    public double getIsr_mens_prest() {
        return isr_mens_prest;
    }

    public void setIsr_mens_prest(double isr_mens_prest) {
        this.isr_mens_prest = isr_mens_prest;
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

    public double getSueldo_mensual_neto() {
        return sueldo_mensual_neto;
    }

    public void setSueldo_mensual_neto(double sueldo_mensual_neto) {
        this.sueldo_mensual_neto = sueldo_mensual_neto;
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

    public LocalDate getFecha_captura() {
        return fecha_captura;
    }

    public void setFecha_captura(LocalDate fecha_captura) {
        this.fecha_captura = fecha_captura;
    }

    
}
