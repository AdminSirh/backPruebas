/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author akalvarez19
 */
public class MedicoDTO {

    private String altura;
    private String peso;
    private Integer tipo_sangre_id;
    private String enfermedades;
    private String alergias;
    private String medico_cabecera;
    private String telefono_medico;
    //  private String contacto_caso_accidente;
    private String clinica;
    private Integer persona_id;
    private Integer enfermedades_si_no;
    private Integer alergias_si_no;
    // private String telefono_contacto_caso_accidente;

    public MedicoDTO(String altura, String peso, Integer tipo_sangre_id, String enfermedades, String alergias, String medico_cabecera, String telefono_medico, String clinica, Integer persona_id, Integer enfermedades_si_no, Integer alergias_si_no) {
        this.altura = altura;
        this.peso = peso;
        this.tipo_sangre_id = tipo_sangre_id;
        this.enfermedades = enfermedades;
        this.alergias = alergias;
        this.medico_cabecera = medico_cabecera;
        this.telefono_medico = telefono_medico;
        //this.contacto_caso_accidente = contacto_caso_accidente;
        this.clinica = clinica;
        this.persona_id = persona_id;
        this.enfermedades_si_no = enfermedades_si_no;
        this.alergias_si_no = alergias_si_no;
        //this.telefono_contacto_caso_accidente = telefono_contacto_caso_accidente;
    }

    public MedicoDTO() {
        super();
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public Integer getTipo_sangre_id() {
        return tipo_sangre_id;
    }

    public void setTipo_sangre_id(Integer tipo_sangre_id) {
        this.tipo_sangre_id = tipo_sangre_id;
    }

    public String getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(String enfermedades) {
        this.enfermedades = enfermedades;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getMedico_cabecera() {
        return medico_cabecera;
    }

    public void setMedico_cabecera(String medico_cabecera) {
        this.medico_cabecera = medico_cabecera;
    }

    public String getTelefono_medico() {
        return telefono_medico;
    }

    public void setTelefono_medico(String telefono_medico) {
        this.telefono_medico = telefono_medico;
    }

    public String getClinica() {
        return clinica;
    }

    public void setClinica(String clinica) {
        this.clinica = clinica;
    }

    public Integer getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(Integer persona_id) {
        this.persona_id = persona_id;
    }

    public Integer getEnfermedades_si_no() {
        return enfermedades_si_no;
    }

    public void setEnfermedades_si_no(Integer enfermedades_si_no) {
        this.enfermedades_si_no = enfermedades_si_no;
    }

    public Integer getAlergias_si_no() {
        return alergias_si_no;
    }

    public void setAlergias_si_no(Integer alergias_si_no) {
        this.alergias_si_no = alergias_si_no;
    }

}
