package com.sirh.sirh.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author oguerrero23
 */
public class Trabajador_Prestamo_PersonalDTO {
   private String apellido_paternoDTO;
   private String apellido_maternoDTO;
   private String nombreDTO;
   private String curpDTO;
   private String numero_expedienteDTO;
   private String desc_generoDTO;
   private String desc_edo_civilDTO;
   private LocalDate fecha_ingresoDTO;
   private String desc_areaDTO;
   private Integer codigo_areaDTO;
   private Integer id_trabajadorDTO;
   
    public Trabajador_Prestamo_PersonalDTO() {
        super();
    }

    public Trabajador_Prestamo_PersonalDTO(String apellido_paternoDTO, String apellido_maternoDTO, String nombreDTO, String curpDTO, String numero_expedienteDTO, String desc_generoDTO, String desc_edo_civilDTO, LocalDate fecha_ingresoDTO, String desc_areaDTO, Integer codigo_areaDTO, Integer id_trabajadorDTO) {
        this.apellido_paternoDTO = apellido_paternoDTO;
        this.apellido_maternoDTO = apellido_maternoDTO;
        this.nombreDTO = nombreDTO;
        this.curpDTO = curpDTO;
        this.numero_expedienteDTO = numero_expedienteDTO;
        this.desc_generoDTO = desc_generoDTO;
        this.desc_edo_civilDTO = desc_edo_civilDTO;
        this.fecha_ingresoDTO = fecha_ingresoDTO;
        this.desc_areaDTO = desc_areaDTO;
        this.codigo_areaDTO = codigo_areaDTO;
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

    public String getCurpDTO() {
        return curpDTO;
    }

    public void setCurpDTO(String curpDTO) {
        this.curpDTO = curpDTO;
    }

    public String getNumero_expedienteDTO() {
        return numero_expedienteDTO;
    }

    public void setNumero_expedienteDTO(String numero_expedienteDTO) {
        this.numero_expedienteDTO = numero_expedienteDTO;
    }

    public String getDesc_generoDTO() {
        return desc_generoDTO;
    }

    public void setDesc_generoDTO(String desc_generoDTO) {
        this.desc_generoDTO = desc_generoDTO;
    }

    public String getDesc_edo_civilDTO() {
        return desc_edo_civilDTO;
    }

    public void setDesc_edo_civilDTO(String desc_edo_civilDTO) {
        this.desc_edo_civilDTO = desc_edo_civilDTO;
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

    public Integer getCodigo_areaDTO() {
        return codigo_areaDTO;
    }

    public void setCodigo_areaDTO(Integer codigo_areaDTO) {
        this.codigo_areaDTO = codigo_areaDTO;
    }

    public Integer getId_trabajadorDTO() {
        return id_trabajadorDTO;
    }

    public void setId_trabajadorDTO(Integer id_trabajadorDTO) {
        this.id_trabajadorDTO = id_trabajadorDTO;
    }

   
}
