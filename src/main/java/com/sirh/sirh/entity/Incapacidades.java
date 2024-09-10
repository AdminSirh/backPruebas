package com.sirh.sirh.entity;

import java.io.Serializable;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "incapacidades")
public class Incapacidades implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_incapacidad;

    private String folio;
    private Integer expediente;
    private LocalDateTime fecha_inicial;
    private LocalDateTime fecha_final;
    private Integer dias_incapacidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motivo_incapacidad_id")
    private Cat_Motivo_Incapacidad cat_Motivo_Incapacidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_incapacidad_id")
    private Cat_Tipo_Incapacidad cat_Tipo_Incapacidad;
    private Double porcentaje_cobro_imss;
    private Double salario_diario_integrado;
    private Double subtotal;
    private Double importe_cobrar_imss;
    private Double saldo_importe_cobrar_imss;
    private Integer dias_cotizables;
    private String st1;
    private String st2;
    private Integer status;
    private Integer historico_id;
    private String origen;
    private Integer autorizar;
    private Integer pagada;
    private Double diferencia_favor;
    @CreatedDate
    private LocalDateTime fecha_autorizacion;
    private Integer expediente_autorizacion;
    private LocalDateTime fecha_movimiento;
    private String cadena_pago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_riesgo_id")
    private Cat_Tipo_Riesgo_Incapacidad cat_Tipo_Riesgo_Incapacidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_secuela_id")
    private Cat_Tipo_Secuelas_Incapacidad cat_Tipo_Secuelas_Incapacidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_control_incap_id")
    private Cat_Tipo_Control_Incapacidad cat_Tipo_Control_Incapacidad;

    @CreatedDate
    private LocalDateTime fecha_cambio;

    private Integer id_original;

    public Incapacidades() {
        super();
    }

    public Incapacidades(Integer id_incapacidad, String folio, Integer expediente, LocalDateTime fecha_inicial, LocalDateTime fecha_final, Integer dias_incapacidad, Cat_Motivo_Incapacidad cat_Motivo_Incapacidad, Cat_Tipo_Incapacidad cat_Tipo_Incapacidad, Double porcentaje_cobro_imss, Double salario_diario_integrado, Double subtotal, Double importe_cobrar_imss, Double saldo_importe_cobrar_imss, Integer dias_cotizables, String st1, String st2, Integer status, Integer historico_id, String origen, Integer autorizar, Integer pagada, Double diferencia_favor, LocalDateTime fecha_autorizacion, Integer expediente_autorizacion, LocalDateTime fecha_movimiento, String cadena_pago, Cat_Tipo_Riesgo_Incapacidad cat_Tipo_Riesgo_Incapacidad, Cat_Tipo_Secuelas_Incapacidad cat_Tipo_Secuelas_Incapacidad, Cat_Tipo_Control_Incapacidad cat_Tipo_Control_Incapacidad, LocalDateTime fecha_cambio, Integer id_original) {
        this.id_incapacidad = id_incapacidad;
        this.folio = folio;
        this.expediente = expediente;
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
        this.dias_incapacidad = dias_incapacidad;
        this.cat_Motivo_Incapacidad = cat_Motivo_Incapacidad;
        this.cat_Tipo_Incapacidad = cat_Tipo_Incapacidad;
        this.porcentaje_cobro_imss = porcentaje_cobro_imss;
        this.salario_diario_integrado = salario_diario_integrado;
        this.subtotal = subtotal;
        this.importe_cobrar_imss = importe_cobrar_imss;
        this.saldo_importe_cobrar_imss = saldo_importe_cobrar_imss;
        this.dias_cotizables = dias_cotizables;
        this.st1 = st1;
        this.st2 = st2;
        this.status = status;
        this.historico_id = historico_id;
        this.origen = origen;
        this.autorizar = autorizar;
        this.pagada = pagada;
        this.diferencia_favor = diferencia_favor;
        this.fecha_autorizacion = fecha_autorizacion;
        this.expediente_autorizacion = expediente_autorizacion;
        this.fecha_movimiento = fecha_movimiento;
        this.cadena_pago = cadena_pago;
        this.cat_Tipo_Riesgo_Incapacidad = cat_Tipo_Riesgo_Incapacidad;
        this.cat_Tipo_Secuelas_Incapacidad = cat_Tipo_Secuelas_Incapacidad;
        this.cat_Tipo_Control_Incapacidad = cat_Tipo_Control_Incapacidad;
        this.fecha_cambio = fecha_cambio;
        this.id_original = id_original;
    }

    public Integer getId_incapacidad() {
        return id_incapacidad;
    }

    public void setId_incapacidad(Integer id_incapacidad) {
        this.id_incapacidad = id_incapacidad;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Integer getExpediente() {
        return expediente;
    }

    public void setExpediente(Integer expediente) {
        this.expediente = expediente;
    }

    public LocalDateTime getFecha_inicial() {
        return fecha_inicial;
    }

    public void setFecha_inicial(LocalDateTime fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    public LocalDateTime getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(LocalDateTime fecha_final) {
        this.fecha_final = fecha_final;
    }

    public Integer getDias_incapacidad() {
        return dias_incapacidad;
    }

    public void setDias_incapacidad(Integer dias_incapacidad) {
        this.dias_incapacidad = dias_incapacidad;
    }

    public Cat_Motivo_Incapacidad getCat_Motivo_Incapacidad() {
        return cat_Motivo_Incapacidad;
    }

    public void setCat_Motivo_Incapacidad(Cat_Motivo_Incapacidad cat_Motivo_Incapacidad) {
        this.cat_Motivo_Incapacidad = cat_Motivo_Incapacidad;
    }

    public Cat_Tipo_Incapacidad getCat_Tipo_Incapacidad() {
        return cat_Tipo_Incapacidad;
    }

    public void setCat_Tipo_Incapacidad(Cat_Tipo_Incapacidad cat_Tipo_Incapacidad) {
        this.cat_Tipo_Incapacidad = cat_Tipo_Incapacidad;
    }

    public Double getPorcentaje_cobro_imss() {
        return porcentaje_cobro_imss;
    }

    public void setPorcentaje_cobro_imss(Double porcentaje_cobro_imss) {
        this.porcentaje_cobro_imss = porcentaje_cobro_imss;
    }

    public Double getSalario_diario_integrado() {
        return salario_diario_integrado;
    }

    public void setSalario_diario_integrado(Double salario_diario_integrado) {
        this.salario_diario_integrado = salario_diario_integrado;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getImporte_cobrar_imss() {
        return importe_cobrar_imss;
    }

    public void setImporte_cobrar_imss(Double importe_cobrar_imss) {
        this.importe_cobrar_imss = importe_cobrar_imss;
    }

    public Double getSaldo_importe_cobrar_imss() {
        return saldo_importe_cobrar_imss;
    }

    public void setSaldo_importe_cobrar_imss(Double saldo_importe_cobrar_imss) {
        this.saldo_importe_cobrar_imss = saldo_importe_cobrar_imss;
    }

    public Integer getDias_cotizables() {
        return dias_cotizables;
    }

    public void setDias_cotizables(Integer dias_cotizables) {
        this.dias_cotizables = dias_cotizables;
    }

    public String getSt1() {
        return st1;
    }

    public void setSt1(String st1) {
        this.st1 = st1;
    }

    public String getSt2() {
        return st2;
    }

    public void setSt2(String st2) {
        this.st2 = st2;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getHistorico_id() {
        return historico_id;
    }

    public void setHistorico_id(Integer historico_id) {
        this.historico_id = historico_id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Integer getAutorizar() {
        return autorizar;
    }

    public void setAutorizar(Integer autorizar) {
        this.autorizar = autorizar;
    }

    public Integer getPagada() {
        return pagada;
    }

    public void setPagada(Integer pagada) {
        this.pagada = pagada;
    }

    public Double getDiferencia_favor() {
        return diferencia_favor;
    }

    public void setDiferencia_favor(Double diferencia_favor) {
        this.diferencia_favor = diferencia_favor;
    }

    public LocalDateTime getFecha_autorizacion() {
        return fecha_autorizacion;
    }

    public void setFecha_autorizacion(LocalDateTime fecha_autorizacion) {
        this.fecha_autorizacion = fecha_autorizacion;
    }

    public Integer getExpediente_autorizacion() {
        return expediente_autorizacion;
    }

    public void setExpediente_autorizacion(Integer expediente_autorizacion) {
        this.expediente_autorizacion = expediente_autorizacion;
    }

    public LocalDateTime getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDateTime fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public String getCadena_pago() {
        return cadena_pago;
    }

    public void setCadena_pago(String cadena_pago) {
        this.cadena_pago = cadena_pago;
    }

    public Cat_Tipo_Riesgo_Incapacidad getCat_Tipo_Riesgo_Incapacidad() {
        return cat_Tipo_Riesgo_Incapacidad;
    }

    public void setCat_Tipo_Riesgo_Incapacidad(Cat_Tipo_Riesgo_Incapacidad cat_Tipo_Riesgo_Incapacidad) {
        this.cat_Tipo_Riesgo_Incapacidad = cat_Tipo_Riesgo_Incapacidad;
    }

    public Cat_Tipo_Secuelas_Incapacidad getCat_Tipo_Secuelas_Incapacidad() {
        return cat_Tipo_Secuelas_Incapacidad;
    }

    public void setCat_Tipo_Secuelas_Incapacidad(Cat_Tipo_Secuelas_Incapacidad cat_Tipo_Secuelas_Incapacidad) {
        this.cat_Tipo_Secuelas_Incapacidad = cat_Tipo_Secuelas_Incapacidad;
    }

    public Cat_Tipo_Control_Incapacidad getCat_Tipo_Control_Incapacidad() {
        return cat_Tipo_Control_Incapacidad;
    }

    public void setCat_Tipo_Control_Incapacidad(Cat_Tipo_Control_Incapacidad cat_Tipo_Control_Incapacidad) {
        this.cat_Tipo_Control_Incapacidad = cat_Tipo_Control_Incapacidad;
    }

    public LocalDateTime getFecha_cambio() {
        return fecha_cambio;
    }

    public void setFecha_cambio(LocalDateTime fecha_cambio) {
        this.fecha_cambio = fecha_cambio;
    }

    public Integer getId_original() {
        return id_original;
    }

    public void setId_original(Integer id_original) {
        this.id_original = id_original;
    }

    
}
