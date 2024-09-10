package com.sirh.sirh.DTO;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author oguerrero23
 */
public class ValidacionesDTO implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_validaciones;
    
    private Integer trabajador_id;
    private Integer con_contrato_si_no;
    private Integer activo_en_nomina_si_no;
    private Integer activo_en_personal_si_no;
    private Integer habilita_si_no;
    private Integer sancion_si_no;

    public ValidacionesDTO() {
        super();
    }
    
    public ValidacionesDTO(Integer id_validaciones, Integer trabajador_id, Integer con_contrato_si_no, Integer activo_en_nomina_si_no, Integer activo_en_personal_si_no, Integer habilita_si_no, Integer sancion_si_no) {
        this.id_validaciones = id_validaciones;
        this.trabajador_id = trabajador_id;
        this.con_contrato_si_no = con_contrato_si_no;
        this.activo_en_nomina_si_no = activo_en_nomina_si_no;
        this.activo_en_personal_si_no = activo_en_personal_si_no;
        this.habilita_si_no = habilita_si_no;
        this.sancion_si_no = sancion_si_no;
    }

    public Integer getId_validaciones() {
        return id_validaciones;
    }

    public void setId_validaciones(Integer id_validaciones) {
        this.id_validaciones = id_validaciones;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getCon_contrato_si_no() {
        return con_contrato_si_no;
    }

    public void setCon_contrato_si_no(Integer con_contrato_si_no) {
        this.con_contrato_si_no = con_contrato_si_no;
    }

    public Integer getActivo_en_nomina_si_no() {
        return activo_en_nomina_si_no;
    }

    public void setActivo_en_nomina_si_no(Integer activo_en_nomina_si_no) {
        this.activo_en_nomina_si_no = activo_en_nomina_si_no;
    }

    public Integer getActivo_en_personal_si_no() {
        return activo_en_personal_si_no;
    }

    public void setActivo_en_personal_si_no(Integer activo_en_personal_si_no) {
        this.activo_en_personal_si_no = activo_en_personal_si_no;
    }

    public Integer getHabilita_si_no() {
        return habilita_si_no;
    }

    public void setHabilita_si_no(Integer habilita_si_no) {
        this.habilita_si_no = habilita_si_no;
    }

    public Integer getSancion_si_no() {
        return sancion_si_no;
    }

    public void setSancion_si_no(Integer sancion_si_no) {
        this.sancion_si_no = sancion_si_no;
    }
    
    
}
