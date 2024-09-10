
package com.sirh.sirh.DTO;

import com.sirh.sirh.entity.Cat_Parentesco;
import com.sirh.sirh.entity.Cat_Tipo_Beneficiario;

/**
 *
 * @author nreyes22
 */
public class BeneficiarioDTO {
    
    private Integer id_beneficiario;
    
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private Cat_Parentesco cat_Parentesco;
    private Integer trabajador_id;
    private Double porcentaje;
    private Cat_Tipo_Beneficiario cat_tipo_beneficiario ;

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
    
    
    
    
}
