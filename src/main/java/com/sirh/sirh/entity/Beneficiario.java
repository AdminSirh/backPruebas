package com.sirh.sirh.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author nreyes22
 */

@Entity
@Table(name = "beneficiario")
public class Beneficiario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_beneficiario;
    
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    
    private Double porcentaje;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentesco_id")
    private Cat_Parentesco cat_Parentesco;
    
    private Integer trabajador_id;
  
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_beneficiario_id")
    private Cat_Tipo_Beneficiario cat_tipo_beneficiario ;
    
    private Integer estatus;

    public Integer getId_beneficiario() {
        return id_beneficiario;
    }

    public void setId_beneficiario(Integer id_beneficiario) {
        this.id_beneficiario = id_beneficiario;
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

    public Cat_Tipo_Beneficiario getCat_tipo_beneficiario() {
        return cat_tipo_beneficiario;
    }

    public void setCat_tipo_beneficiario(Cat_Tipo_Beneficiario cat_tipo_beneficiario) {
        this.cat_tipo_beneficiario = cat_tipo_beneficiario;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
    
    
    
    
}
