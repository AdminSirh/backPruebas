/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author oguerrero23
 */
public class Trabajador_FonacotDTO {
    private String expedienteDTO;
    private String nombreDTO;
    private String apellido_paternoDTO;
    private String apellido_maternoDTO;
    private Integer id_tipo_nominaDTO;

    public Trabajador_FonacotDTO(String expedienteDTO, String nombreDTO, String apellido_paternoDTO, String apellido_maternoDTO, Integer id_tipo_nominaDTO) {
        this.expedienteDTO = expedienteDTO;
        this.nombreDTO = nombreDTO;
        this.apellido_paternoDTO = apellido_paternoDTO;
        this.apellido_maternoDTO = apellido_maternoDTO;
        this.id_tipo_nominaDTO = id_tipo_nominaDTO;
    }

    public String getExpedienteDTO() {
        return expedienteDTO;
    }

    public void setExpedienteDTO(String expedienteDTO) {
        this.expedienteDTO = expedienteDTO;
    }

    public String getNombreDTO() {
        return nombreDTO;
    }

    public void setNombreDTO(String nombreDTO) {
        this.nombreDTO = nombreDTO;
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

    public Integer getId_tipo_nominaDTO() {
        return id_tipo_nominaDTO;
    }

    public void setId_tipo_nominaDTO(Integer id_tipo_nominaDTO) {
        this.id_tipo_nominaDTO = id_tipo_nominaDTO;
    }
}
