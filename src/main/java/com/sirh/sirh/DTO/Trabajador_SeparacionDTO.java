/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author oguerrero23
 */
public class Trabajador_SeparacionDTO {

    private String apellido_paternoDTO;
    private String apellido_maternoDTO;
    private String nombreDTO;
    private String rfcDTO;
    private String imssDTO;
    private LocalDate fecha_nacimientoDTO;
    private String numero_expedienteDTO;
    private LocalDate fecha_ingresoDTO;
    private String desc_areaDTO;
    private String nominaDTO;
    private String puestoDTO;
    private Integer id_trabajadorDTO;

    public Trabajador_SeparacionDTO() {
        super();
    }

    public Trabajador_SeparacionDTO(String apellido_paternoDTO, String apellido_maternoDTO, String nombreDTO, String rfcDTO, String imssDTO, LocalDate fecha_nacimientoDTO, String numero_expedienteDTO, LocalDate fecha_ingresoDTO, String desc_areaDTO, String nominaDTO, String puestoDTO, Integer id_trabajadorDTO) {
        this.apellido_paternoDTO = apellido_paternoDTO;
        this.apellido_maternoDTO = apellido_maternoDTO;
        this.nombreDTO = nombreDTO;
        this.rfcDTO = rfcDTO;
        this.imssDTO = imssDTO;
        this.fecha_nacimientoDTO = fecha_nacimientoDTO;
        this.numero_expedienteDTO = numero_expedienteDTO;
        this.fecha_ingresoDTO = fecha_ingresoDTO;
        this.desc_areaDTO = desc_areaDTO;
        this.nominaDTO = nominaDTO;
        this.puestoDTO = puestoDTO;
        this.id_trabajadorDTO = id_trabajadorDTO;
    }

    public String getApellido_paternoDTO() {
        return apellido_paternoDTO;
    }

    public void setApellido_paternoDTO(String apellido_paternoDTO) {
        this.apellido_paternoDTO = apellido_paternoDTO;
    }

    public String getApellido_maternoDTO() {
        return apellido_maternoDTO;
    }

    public void setApellido_maternoDTO(String apellido_maternoDTO) {
        this.apellido_maternoDTO = apellido_maternoDTO;
    }

    public String getNombreDTO() {
        return nombreDTO;
    }

    public void setNombreDTO(String nombreDTO) {
        this.nombreDTO = nombreDTO;
    }

    public String getRfcDTO() {
        return rfcDTO;
    }

    public void setRfcDTO(String rfcDTO) {
        this.rfcDTO = rfcDTO;
    }

    public String getImssDTO() {
        return imssDTO;
    }

    public void setImssDTO(String imssDTO) {
        this.imssDTO = imssDTO;
    }

    public LocalDate getFecha_nacimientoDTO() {
        return fecha_nacimientoDTO;
    }

    public void setFecha_nacimientoDTO(LocalDate fecha_nacimientoDTO) {
        this.fecha_nacimientoDTO = fecha_nacimientoDTO;
    }

    public String getNumero_expedienteDTO() {
        return numero_expedienteDTO;
    }

    public void setNumero_expedienteDTO(String numero_expedienteDTO) {
        this.numero_expedienteDTO = numero_expedienteDTO;
    }

    public LocalDate getFecha_ingresoDTO() {
        return fecha_ingresoDTO;
    }

    public void setFecha_ingresoDTO(LocalDate fecha_ingresoDTO) {
        this.fecha_ingresoDTO = fecha_ingresoDTO;
    }

    public String getDesc_areaDTO() {
        return desc_areaDTO;
    }

    public void setDesc_areaDTO(String desc_areaDTO) {
        this.desc_areaDTO = desc_areaDTO;
    }

    public String getNominaDTO() {
        return nominaDTO;
    }

    public void setNominaDTO(String nominaDTO) {
        this.nominaDTO = nominaDTO;
    }

    public String getPuestoDTO() {
        return puestoDTO;
    }

    public void setPuestoDTO(String puestoDTO) {
        this.puestoDTO = puestoDTO;
    }

    public Integer getId_trabajadorDTO() {
        return id_trabajadorDTO;
    }

    public void setId_trabajadorDTO(Integer id_trabajadorDTO) {
        this.id_trabajadorDTO = id_trabajadorDTO;
    }

    

}
