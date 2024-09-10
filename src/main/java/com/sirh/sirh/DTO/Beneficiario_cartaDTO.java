package com.sirh.sirh.DTO;

import com.sirh.sirh.entity.Cat_Parentesco;


/**
 *
 * @author dguerrero18
 */
public class Beneficiario_cartaDTO {
    private Integer id_beneficiario_carta;
    
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private Cat_Parentesco cat_Parentesco;
    private Integer persona_id;
    private Double porcentaje;
    private Integer tipo_beneficiario_carta_id ;

    public Integer getId_beneficiario_carta() {
        return id_beneficiario_carta;
    }

    public void setId_beneficiario_carta(Integer id_beneficiario_carta) {
        this.id_beneficiario_carta = id_beneficiario_carta;
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

    public Cat_Parentesco getCat_Parentesco() {
        return cat_Parentesco;
    }

    public void setCat_Parentesco(Cat_Parentesco cat_Parentesco) {
        this.cat_Parentesco = cat_Parentesco;
    }

    public Integer getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(Integer persona_id) {
        this.persona_id = persona_id;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Integer getTipo_beneficiario_carta_id() {
        return tipo_beneficiario_carta_id;
    }

    public void setTipo_beneficiario_carta_id(Integer tipo_beneficiario_carta_id) {
        this.tipo_beneficiario_carta_id = tipo_beneficiario_carta_id;
    }

     
    
    
    
}
