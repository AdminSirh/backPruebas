/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rroscero23
 */
public class Captura_Semanal_Resumen_IncidenciasRA15DTO {

    private String numero_expediente;
    private String nombre_completo;
    private Double sueldo_hora;
    private Integer id_resumen;
    private Double horas_turno;
    private Double horas_doble;
    private Double horas_triple;
    private Double dias_laborados;
    private Integer total_faltas;
    private Double total_paseos;
    private Integer prima_dominical;
    private Double festivo;
    private Double omisiones;
    private Double dif_paseos;
    private Double dif_prima;
    private Double dif_tiempo_extra;
    private Double dif_sueldo;
    private Integer dias_pago;
    private Integer dias_cve_27;

    public Captura_Semanal_Resumen_IncidenciasRA15DTO() {
    }

    public Captura_Semanal_Resumen_IncidenciasRA15DTO(String numero_expediente, String nombre_completo, Double sueldo_hora, Integer id_resumen, Double horas_turno, Double horas_doble, Double horas_triple, Double dias_laborados, Integer total_faltas, Double total_paseos, Integer prima_dominical, Double festivo, Double omisiones, Double dif_paseos, Double dif_prima, Double dif_tiempo_extra, Double dif_sueldo, Integer dias_pago, Integer dias_cve_27) {
        this.numero_expediente = numero_expediente;
        this.nombre_completo = nombre_completo;
        this.sueldo_hora = sueldo_hora;
        this.id_resumen = id_resumen;
        this.horas_turno = horas_turno;
        this.horas_doble = horas_doble;
        this.horas_triple = horas_triple;
        this.dias_laborados = dias_laborados;
        this.total_faltas = total_faltas;
        this.total_paseos = total_paseos;
        this.prima_dominical = prima_dominical;
        this.festivo = festivo;
        this.omisiones = omisiones;
        this.dif_paseos = dif_paseos;
        this.dif_prima = dif_prima;
        this.dif_tiempo_extra = dif_tiempo_extra;
        this.dif_sueldo = dif_sueldo;
        this.dias_pago = dias_pago;
        this.dias_cve_27 = dias_cve_27;
    }

    public String getNumero_expediente() {
        return numero_expediente;
    }

    public void setNumero_expediente(String numero_expediente) {
        this.numero_expediente = numero_expediente;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public Double getSueldo_hora() {
        return sueldo_hora;
    }

    public void setSueldo_hora(Double sueldo_hora) {
        this.sueldo_hora = sueldo_hora;
    }

    public Integer getId_resumen() {
        return id_resumen;
    }

    public void setId_resumen(Integer id_resumen) {
        this.id_resumen = id_resumen;
    }

    public Double getHoras_turno() {
        return horas_turno;
    }

    public void setHoras_turno(Double horas_turno) {
        this.horas_turno = horas_turno;
    }

    public Double getHoras_doble() {
        return horas_doble;
    }

    public void setHoras_doble(Double horas_doble) {
        this.horas_doble = horas_doble;
    }

    public Double getHoras_triple() {
        return horas_triple;
    }

    public void setHoras_triple(Double horas_triple) {
        this.horas_triple = horas_triple;
    }

    public Double getDias_laborados() {
        return dias_laborados;
    }

    public void setDias_laborados(Double dias_laborados) {
        this.dias_laborados = dias_laborados;
    }

    public Integer getTotal_faltas() {
        return total_faltas;
    }

    public void setTotal_faltas(Integer total_faltas) {
        this.total_faltas = total_faltas;
    }

    public Double getTotal_paseos() {
        return total_paseos;
    }

    public void setTotal_paseos(Double total_paseos) {
        this.total_paseos = total_paseos;
    }

    public Integer getPrima_dominical() {
        return prima_dominical;
    }

    public void setPrima_dominical(Integer prima_dominical) {
        this.prima_dominical = prima_dominical;
    }

    public Double getFestivo() {
        return festivo;
    }

    public void setFestivo(Double festivo) {
        this.festivo = festivo;
    }

    public Double getOmisiones() {
        return omisiones;
    }

    public void setOmisiones(Double omisiones) {
        this.omisiones = omisiones;
    }

    public Double getDif_paseos() {
        return dif_paseos;
    }

    public void setDif_paseos(Double dif_paseos) {
        this.dif_paseos = dif_paseos;
    }

    public Double getDif_prima() {
        return dif_prima;
    }

    public void setDif_prima(Double dif_prima) {
        this.dif_prima = dif_prima;
    }

    public Double getDif_tiempo_extra() {
        return dif_tiempo_extra;
    }

    public void setDif_tiempo_extra(Double dif_tiempo_extra) {
        this.dif_tiempo_extra = dif_tiempo_extra;
    }

    public Double getDif_sueldo() {
        return dif_sueldo;
    }

    public void setDif_sueldo(Double dif_sueldo) {
        this.dif_sueldo = dif_sueldo;
    }

    public Integer getDias_pago() {
        return dias_pago;
    }

    public void setDias_pago(Integer dias_pago) {
        this.dias_pago = dias_pago;
    }

    public Integer getDias_cve_27() {
        return dias_cve_27;
    }

    public void setDias_cve_27(Integer dias_cve_27) {
        this.dias_cve_27 = dias_cve_27;
    }

}
