/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import javax.persistence.Temporal;

/**
 *
 * @author rroscero23
 */
@Entity
@Table(name = "historico_pensiones_alimenticias")
public class HistoricoPensionesAlimenticias implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pension_historico;
    private Integer trabajador_id;
    private Double porcentaje;
    private String juzgado;
    private LocalDate fecha_recepcion;
    private String expediente_caso;
    private String referencia;
    private String oficio;
    private String periodo_aplicacion;
    private Boolean estatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "beneficiario_pa_id")
    private Pension_Alimenticia_B Pension_Alimenticia_B;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "montos_id")
    private Pension_Alimenticia_Montos Pension_Alimenticia_Montos;
    private Double cuota_fija;
    private LocalDate fecha_baja_pension;
    private LocalDate fecha_movimiento;
    private Integer modalidad;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String rfc;
    private String calle;
    private String numero_oficial;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(columnDefinition = "integer", name = "colonia_id")
    private Cat_Colonia cat_Colonia;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(columnDefinition = "integer", name = "banco_id")
    private Cat_Banco cat_Banco;
    private String cuentabeneficiario;
    private Integer id_fdo_ahorro;
    private Boolean fdo_trabajador;
    private Boolean fdo_empresa;
    private Boolean fdo_interes;
    private Boolean apl_nomina;
    private Boolean apl_finiq;
    private Boolean vales_fin_anio;
    private Boolean id_deposito_bancario;
    private Double porcentaje_descuento;
    private Integer periodo_gen;
    private Double anualidad;
    private Integer pago_descuento;
    private Double descuento;

    public HistoricoPensionesAlimenticias() {
        super();
    }

    public HistoricoPensionesAlimenticias(Integer id_pension_historico, Integer trabajador_id, Double porcentaje, String juzgado, LocalDate fecha_recepcion, String expediente_caso, String referencia, String oficio, String periodo_aplicacion, Boolean estatus, Pension_Alimenticia_B Pension_Alimenticia_B, Pension_Alimenticia_Montos Pension_Alimenticia_Montos, Double cuota_fija, LocalDate fecha_baja_pension, LocalDate fecha_movimiento, Integer modalidad, String nombre, String apellido_paterno, String apellido_materno, String rfc, String calle, String numero_oficial, Cat_Colonia cat_Colonia, Cat_Banco cat_Banco, String cuentabeneficiario, Integer id_fdo_ahorro, Boolean fdo_trabajador, Boolean fdo_empresa, Boolean fdo_interes, Boolean apl_nomina, Boolean apl_finiq, Boolean vales_fin_anio, Boolean id_deposito_bancario, Double porcentaje_descuento, Integer periodo_gen, Double anualidad, Integer pago_descuento, Double descuento) {
        this.id_pension_historico = id_pension_historico;
        this.trabajador_id = trabajador_id;
        this.porcentaje = porcentaje;
        this.juzgado = juzgado;
        this.fecha_recepcion = fecha_recepcion;
        this.expediente_caso = expediente_caso;
        this.referencia = referencia;
        this.oficio = oficio;
        this.periodo_aplicacion = periodo_aplicacion;
        this.estatus = estatus;
        this.Pension_Alimenticia_B = Pension_Alimenticia_B;
        this.Pension_Alimenticia_Montos = Pension_Alimenticia_Montos;
        this.cuota_fija = cuota_fija;
        this.fecha_baja_pension = fecha_baja_pension;
        this.fecha_movimiento = fecha_movimiento;
        this.modalidad = modalidad;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.rfc = rfc;
        this.calle = calle;
        this.numero_oficial = numero_oficial;
        this.cat_Colonia = cat_Colonia;
        this.cat_Banco = cat_Banco;
        this.cuentabeneficiario = cuentabeneficiario;
        this.id_fdo_ahorro = id_fdo_ahorro;
        this.fdo_trabajador = fdo_trabajador;
        this.fdo_empresa = fdo_empresa;
        this.fdo_interes = fdo_interes;
        this.apl_nomina = apl_nomina;
        this.apl_finiq = apl_finiq;
        this.vales_fin_anio = vales_fin_anio;
        this.id_deposito_bancario = id_deposito_bancario;
        this.porcentaje_descuento = porcentaje_descuento;
        this.periodo_gen = periodo_gen;
        this.anualidad = anualidad;
        this.pago_descuento = pago_descuento;
        this.descuento = descuento;
    }

    public Integer getId_pension_historico() {
        return id_pension_historico;
    }

    public void setId_pension_historico(Integer id_pension_historico) {
        this.id_pension_historico = id_pension_historico;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(String juzgado) {
        this.juzgado = juzgado;
    }

    public LocalDate getFecha_recepcion() {
        return fecha_recepcion;
    }

    public void setFecha_recepcion(LocalDate fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    public String getExpediente_caso() {
        return expediente_caso;
    }

    public void setExpediente_caso(String expediente_caso) {
        this.expediente_caso = expediente_caso;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public String getPeriodo_aplicacion() {
        return periodo_aplicacion;
    }

    public void setPeriodo_aplicacion(String periodo_aplicacion) {
        this.periodo_aplicacion = periodo_aplicacion;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }

    public Pension_Alimenticia_B getPension_Alimenticia_B() {
        return Pension_Alimenticia_B;
    }

    public void setPension_Alimenticia_B(Pension_Alimenticia_B Pension_Alimenticia_B) {
        this.Pension_Alimenticia_B = Pension_Alimenticia_B;
    }

    public Pension_Alimenticia_Montos getPension_Alimenticia_Montos() {
        return Pension_Alimenticia_Montos;
    }

    public void setPension_Alimenticia_Montos(Pension_Alimenticia_Montos Pension_Alimenticia_Montos) {
        this.Pension_Alimenticia_Montos = Pension_Alimenticia_Montos;
    }

    public Double getCuota_fija() {
        return cuota_fija;
    }

    public void setCuota_fija(Double cuota_fija) {
        this.cuota_fija = cuota_fija;
    }

    public LocalDate getFecha_baja_pension() {
        return fecha_baja_pension;
    }

    public void setFecha_baja_pension(LocalDate fecha_baja_pension) {
        this.fecha_baja_pension = fecha_baja_pension;
    }

    public LocalDate getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDate fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public Integer getModalidad() {
        return modalidad;
    }

    public void setModalidad(Integer modalidad) {
        this.modalidad = modalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero_oficial() {
        return numero_oficial;
    }

    public void setNumero_oficial(String numero_oficial) {
        this.numero_oficial = numero_oficial;
    }

    public Cat_Colonia getCat_Colonia() {
        return cat_Colonia;
    }

    public void setCat_Colonia(Cat_Colonia cat_Colonia) {
        this.cat_Colonia = cat_Colonia;
    }

    public Cat_Banco getCat_Banco() {
        return cat_Banco;
    }

    public void setCat_Banco(Cat_Banco cat_Banco) {
        this.cat_Banco = cat_Banco;
    }

    public String getCuentabeneficiario() {
        return cuentabeneficiario;
    }

    public void setCuentabeneficiario(String cuentabeneficiario) {
        this.cuentabeneficiario = cuentabeneficiario;
    }

    public Integer getId_fdo_ahorro() {
        return id_fdo_ahorro;
    }

    public void setId_fdo_ahorro(Integer id_fdo_ahorro) {
        this.id_fdo_ahorro = id_fdo_ahorro;
    }

    public Boolean getFdo_trabajador() {
        return fdo_trabajador;
    }

    public void setFdo_trabajador(Boolean fdo_trabajador) {
        this.fdo_trabajador = fdo_trabajador;
    }

    public Boolean getFdo_empresa() {
        return fdo_empresa;
    }

    public void setFdo_empresa(Boolean fdo_empresa) {
        this.fdo_empresa = fdo_empresa;
    }

    public Boolean getFdo_interes() {
        return fdo_interes;
    }

    public void setFdo_interes(Boolean fdo_interes) {
        this.fdo_interes = fdo_interes;
    }

    public Boolean getApl_nomina() {
        return apl_nomina;
    }

    public void setApl_nomina(Boolean apl_nomina) {
        this.apl_nomina = apl_nomina;
    }

    public Boolean getApl_finiq() {
        return apl_finiq;
    }

    public void setApl_finiq(Boolean apl_finiq) {
        this.apl_finiq = apl_finiq;
    }

    public Boolean getVales_fin_anio() {
        return vales_fin_anio;
    }

    public void setVales_fin_anio(Boolean vales_fin_anio) {
        this.vales_fin_anio = vales_fin_anio;
    }

    public Boolean getId_deposito_bancario() {
        return id_deposito_bancario;
    }

    public void setId_deposito_bancario(Boolean id_deposito_bancario) {
        this.id_deposito_bancario = id_deposito_bancario;
    }

    public Double getPorcentaje_descuento() {
        return porcentaje_descuento;
    }

    public void setPorcentaje_descuento(Double porcentaje_descuento) {
        this.porcentaje_descuento = porcentaje_descuento;
    }

    public Integer getPeriodo_gen() {
        return periodo_gen;
    }

    public void setPeriodo_gen(Integer periodo_gen) {
        this.periodo_gen = periodo_gen;
    }

    public Double getAnualidad() {
        return anualidad;
    }

    public void setAnualidad(Double anualidad) {
        this.anualidad = anualidad;
    }

    public Integer getPago_descuento() {
        return pago_descuento;
    }

    public void setPago_descuento(Integer pago_descuento) {
        this.pago_descuento = pago_descuento;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    

}
