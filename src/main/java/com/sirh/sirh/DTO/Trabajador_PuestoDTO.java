/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rrosero23
 */
public class Trabajador_PuestoDTO {

    private Integer expedienteId;
    private Integer idTipoContrato;
    private Integer idTipoNomina;
    private String tipoNomina;
    private Integer numeroPlaza;
    private Integer nivelDto;
    private Integer idPuesto;
    private String puestoDto;
    private Double sueldoDiarioDto;
    private String descAreaDto;
    private String nombreDto;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String numeroExpediente;
    private Integer idEstatusPlaza;
    private String descripcionDto;

    public Trabajador_PuestoDTO(Integer expedienteId, Integer idTipoContrato, Integer idTipoNomina, String tipoNomina, Integer numeroPlaza, Integer nivelDto, Integer idPuesto, String puestoDto, Double sueldoDiarioDto, String descAreaDto, String nombreDto, String apellidoPaterno, String apellidoMaterno, String numeroExpediente, Integer idEstatusPlaza, String descripcionDto) {
        this.expedienteId = expedienteId;
        this.idTipoContrato = idTipoContrato;
        this.idTipoNomina = idTipoNomina;
        this.tipoNomina = tipoNomina;
        this.numeroPlaza = numeroPlaza;
        this.nivelDto = nivelDto;
        this.idPuesto = idPuesto;
        this.puestoDto = puestoDto;
        this.sueldoDiarioDto = sueldoDiarioDto;
        this.descAreaDto = descAreaDto;
        this.nombreDto = nombreDto;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.numeroExpediente = numeroExpediente;
        this.idEstatusPlaza = idEstatusPlaza;
        this.descripcionDto = descripcionDto;
    }

    public Trabajador_PuestoDTO() {

    }

    public Integer getExpedienteId() {
        return expedienteId;
    }

    public void setExpedienteId(Integer expedienteId) {
        this.expedienteId = expedienteId;
    }

    public Integer getIdTipoContrato() {
        return idTipoContrato;
    }

    public void setIdTipoContrato(Integer idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
    }

    public Integer getIdTipoNomina() {
        return idTipoNomina;
    }

    public void setIdTipoNomina(Integer idTipoNomina) {
        this.idTipoNomina = idTipoNomina;
    }

    public String getTipoNomina() {
        return tipoNomina;
    }

    public void setTipoNomina(String tipoNomina) {
        this.tipoNomina = tipoNomina;
    }

    public Integer getNumeroPlaza() {
        return numeroPlaza;
    }

    public void setNumeroPlaza(Integer numeroPlaza) {
        this.numeroPlaza = numeroPlaza;
    }

    public Integer getNivelDto() {
        return nivelDto;
    }

    public void setNivelDto(Integer nivelDto) {
        this.nivelDto = nivelDto;
    }

    public Integer getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(Integer idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getPuestoDto() {
        return puestoDto;
    }

    public void setPuestoDto(String puestoDto) {
        this.puestoDto = puestoDto;
    }

    public Double getSueldoDiarioDto() {
        return sueldoDiarioDto;
    }

    public void setSueldoDiarioDto(Double sueldoDiarioDto) {
        this.sueldoDiarioDto = sueldoDiarioDto;
    }

    public String getDescAreaDto() {
        return descAreaDto;
    }

    public void setDescAreaDto(String descAreaDto) {
        this.descAreaDto = descAreaDto;
    }

    public String getNombreDto() {
        return nombreDto;
    }

    public void setNombreDto(String nombreDto) {
        this.nombreDto = nombreDto;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    public Integer getIdEstatusPlaza() {
        return idEstatusPlaza;
    }

    public void setIdEstatusPlaza(Integer idEstatusPlaza) {
        this.idEstatusPlaza = idEstatusPlaza;
    }

    public String getDescripcionDto() {
        return descripcionDto;
    }

    public void setDescripcionDto(String descripcionDto) {
        this.descripcionDto = descripcionDto;
    }

}
