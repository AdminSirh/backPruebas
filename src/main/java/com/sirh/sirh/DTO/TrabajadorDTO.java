
package com.sirh.sirh.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author nreyes22
 */
public class TrabajadorDTO {
    private Integer expediente_id;
    private Integer extraccion_sindical_si_no;
    private Integer prestaciones_si_no;
    private Integer comisionado_si_no;
    private LocalDate fecha_antiguedad;
    private LocalDate fecha_ingreso;
    private Integer persona_id;

    public TrabajadorDTO() {
    }

    public TrabajadorDTO(Integer expediente_id, Integer extraccion_sindical_si_no, Integer prestaciones_si_no, Integer comisionado_si_no, LocalDate fecha_antiguedad, LocalDate fecha_ingreso, Integer persona_id) {
        this.expediente_id = expediente_id;
        this.extraccion_sindical_si_no = extraccion_sindical_si_no;
        this.prestaciones_si_no = prestaciones_si_no;
        this.comisionado_si_no = comisionado_si_no;
        this.fecha_antiguedad = fecha_antiguedad;
        this.fecha_ingreso = fecha_ingreso;
        this.persona_id = persona_id;
    }

    public Integer getExpediente_id() {
        return expediente_id;
    }

    public void setExpediente_id(Integer expediente_id) {
        this.expediente_id = expediente_id;
    }

    public Integer getExtraccion_sindical_si_no() {
        return extraccion_sindical_si_no;
    }

    public void setExtraccion_sindical_si_no(Integer extraccion_sindical_si_no) {
        this.extraccion_sindical_si_no = extraccion_sindical_si_no;
    }

    public Integer getPrestaciones_si_no() {
        return prestaciones_si_no;
    }

    public void setPrestaciones_si_no(Integer prestaciones_si_no) {
        this.prestaciones_si_no = prestaciones_si_no;
    }

    public Integer getComisionado_si_no() {
        return comisionado_si_no;
    }

    public void setComisionado_si_no(Integer comisionado_si_no) {
        this.comisionado_si_no = comisionado_si_no;
    }

    public LocalDate getFecha_antiguedad() {
        return fecha_antiguedad;
    }

    public void setFecha_antiguedad(LocalDate fecha_antiguedad) {
        this.fecha_antiguedad = fecha_antiguedad;
    }

    public LocalDate getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(LocalDate fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public Integer getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(Integer persona_id) {
        this.persona_id = persona_id;
    }

    
}
