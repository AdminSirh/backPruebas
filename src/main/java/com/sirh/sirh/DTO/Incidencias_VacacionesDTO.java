/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.sirh.sirh.entity.Cat_Incidencias;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author oguerrero23
 */
public class Incidencias_VacacionesDTO {
    //Atributos Tabla Incidencias
    private Integer id_incidencia; 
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private Cat_Incidencias cat_incidencias;
    private Integer trabajador_id;
    private Integer num_dias;
    private String observaciones;
    private String folio;
    private String bis;
    private Integer estatus;
    private Integer estado_incidencia_id;
    

    //Atributos Tabla Vacaciones
    
    //private Integer trabajador_id;
    private Integer expediente;
    private Integer dias_vacaciones;
    private Integer dias_paseos;
    //private Date fecha_inicio;
    //private Date fecha_fin;
    private Double periodo_vacacional;
    private String antiguedad;
    private Integer dias_totales;
    private Integer estado_vacaciones_id;
    private Integer id_solicitud;
    private Integer dias_disponibles;
    private Integer periodo_aplicacion;
    private Integer motivo_cancelacion;
    private String referencia_cancelacion;
    private LocalDate fecha_inicio_anio_periodo_vacacional;
    private LocalDate fecha_fin_anio_periodo_vacacional;
    private Double dias_proporcionales;
    //private String observaciones;
    private LocalDate fecha_movimiento;
    private Integer periodo_aplicacion_fin;
    private Integer a_discontinua;
    private Integer genera_pago_si_no;
    private Integer incidencias_id;

    public Incidencias_VacacionesDTO() {
        super();
    }

    public Incidencias_VacacionesDTO(Integer id_incidencia, LocalDate fecha_inicio, LocalDate fecha_fin, Cat_Incidencias cat_incidencias, Integer trabajador_id, Integer num_dias, String observaciones, String folio, String bis, Integer estatus, Integer estado_incidencia_id, Integer expediente, Integer dias_vacaciones, Integer dias_paseos, Double periodo_vacacional, String antiguedad, Integer dias_totales, Integer estado_vacaciones_id, Integer id_solicitud, Integer dias_disponibles, Integer periodo_aplicacion, Integer motivo_cancelacion, String referencia_cancelacion, LocalDate fecha_inicio_anio_periodo_vacacional, LocalDate fecha_fin_anio_periodo_vacacional, Double dias_proporcionales, LocalDate fecha_movimiento, Integer periodo_aplicacion_fin, Integer a_discontinua, Integer genera_pago_si_no, Integer incidencias_id) {
        this.id_incidencia = id_incidencia;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.cat_incidencias = cat_incidencias;
        this.trabajador_id = trabajador_id;
        this.num_dias = num_dias;
        this.observaciones = observaciones;
        this.folio = folio;
        this.bis = bis;
        this.estatus = estatus;
        this.estado_incidencia_id = estado_incidencia_id;
        this.expediente = expediente;
        this.dias_vacaciones = dias_vacaciones;
        this.dias_paseos = dias_paseos;
        this.periodo_vacacional = periodo_vacacional;
        this.antiguedad = antiguedad;
        this.dias_totales = dias_totales;
        this.estado_vacaciones_id = estado_vacaciones_id;
        this.id_solicitud = id_solicitud;
        this.dias_disponibles = dias_disponibles;
        this.periodo_aplicacion = periodo_aplicacion;
        this.motivo_cancelacion = motivo_cancelacion;
        this.referencia_cancelacion = referencia_cancelacion;
        this.fecha_inicio_anio_periodo_vacacional = fecha_inicio_anio_periodo_vacacional;
        this.fecha_fin_anio_periodo_vacacional = fecha_fin_anio_periodo_vacacional;
        this.dias_proporcionales = dias_proporcionales;
        this.fecha_movimiento = fecha_movimiento;
        this.periodo_aplicacion_fin = periodo_aplicacion_fin;
        this.a_discontinua = a_discontinua;
        this.genera_pago_si_no = genera_pago_si_no;
        this.incidencias_id = incidencias_id;
    }

    public Integer getId_incidencia() {
        return id_incidencia;
    }

    public void setId_incidencia(Integer id_incidencia) {
        this.id_incidencia = id_incidencia;
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

    public Cat_Incidencias getCat_incidencias() {
        return cat_incidencias;
    }

    public void setCat_incidencias(Cat_Incidencias cat_incidencias) {
        this.cat_incidencias = cat_incidencias;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getNum_dias() {
        return num_dias;
    }

    public void setNum_dias(Integer num_dias) {
        this.num_dias = num_dias;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getBis() {
        return bis;
    }

    public void setBis(String bis) {
        this.bis = bis;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Integer getEstado_incidencia_id() {
        return estado_incidencia_id;
    }

    public void setEstado_incidencia_id(Integer estado_incidencia_id) {
        this.estado_incidencia_id = estado_incidencia_id;
    }

    public Integer getExpediente() {
        return expediente;
    }

    public void setExpediente(Integer expediente) {
        this.expediente = expediente;
    }

    public Integer getDias_vacaciones() {
        return dias_vacaciones;
    }

    public void setDias_vacaciones(Integer dias_vacaciones) {
        this.dias_vacaciones = dias_vacaciones;
    }

    public Integer getDias_paseos() {
        return dias_paseos;
    }

    public void setDias_paseos(Integer dias_paseos) {
        this.dias_paseos = dias_paseos;
    }

    public Double getPeriodo_vacacional() {
        return periodo_vacacional;
    }

    public void setPeriodo_vacacional(Double periodo_vacacional) {
        this.periodo_vacacional = periodo_vacacional;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
    }

    public Integer getDias_totales() {
        return dias_totales;
    }

    public void setDias_totales(Integer dias_totales) {
        this.dias_totales = dias_totales;
    }

    public Integer getEstado_vacaciones_id() {
        return estado_vacaciones_id;
    }

    public void setEstado_vacaciones_id(Integer estado_vacaciones_id) {
        this.estado_vacaciones_id = estado_vacaciones_id;
    }

    public Integer getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(Integer id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public Integer getDias_disponibles() {
        return dias_disponibles;
    }

    public void setDias_disponibles(Integer dias_disponibles) {
        this.dias_disponibles = dias_disponibles;
    }

    public Integer getPeriodo_aplicacion() {
        return periodo_aplicacion;
    }

    public void setPeriodo_aplicacion(Integer periodo_aplicacion) {
        this.periodo_aplicacion = periodo_aplicacion;
    }

    public Integer getMotivo_cancelacion() {
        return motivo_cancelacion;
    }

    public void setMotivo_cancelacion(Integer motivo_cancelacion) {
        this.motivo_cancelacion = motivo_cancelacion;
    }

    public String getReferencia_cancelacion() {
        return referencia_cancelacion;
    }

    public void setReferencia_cancelacion(String referencia_cancelacion) {
        this.referencia_cancelacion = referencia_cancelacion;
    }

    public LocalDate getFecha_inicio_anio_periodo_vacacional() {
        return fecha_inicio_anio_periodo_vacacional;
    }

    public void setFecha_inicio_anio_periodo_vacacional(LocalDate fecha_inicio_anio_periodo_vacacional) {
        this.fecha_inicio_anio_periodo_vacacional = fecha_inicio_anio_periodo_vacacional;
    }

    public LocalDate getFecha_fin_anio_periodo_vacacional() {
        return fecha_fin_anio_periodo_vacacional;
    }

    public void setFecha_fin_anio_periodo_vacacional(LocalDate fecha_fin_anio_periodo_vacacional) {
        this.fecha_fin_anio_periodo_vacacional = fecha_fin_anio_periodo_vacacional;
    }

    public Double getDias_proporcionales() {
        return dias_proporcionales;
    }

    public void setDias_proporcionales(Double dias_proporcionales) {
        this.dias_proporcionales = dias_proporcionales;
    }

    public LocalDate getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDate fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public Integer getPeriodo_aplicacion_fin() {
        return periodo_aplicacion_fin;
    }

    public void setPeriodo_aplicacion_fin(Integer periodo_aplicacion_fin) {
        this.periodo_aplicacion_fin = periodo_aplicacion_fin;
    }

    public Integer getA_discontinua() {
        return a_discontinua;
    }

    public void setA_discontinua(Integer a_discontinua) {
        this.a_discontinua = a_discontinua;
    }

    public Integer getGenera_pago_si_no() {
        return genera_pago_si_no;
    }

    public void setGenera_pago_si_no(Integer genera_pago_si_no) {
        this.genera_pago_si_no = genera_pago_si_no;
    }

    public Integer getIncidencias_id() {
        return incidencias_id;
    }

    public void setIncidencias_id(Integer incidencias_id) {
        this.incidencias_id = incidencias_id;
    }

    
}
