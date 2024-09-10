
package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author nreyes22
 */
@Entity
@Table(name = "trabajador")
public class Trabajador implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_trabajador;
    
    //private Integer expediente_id;
    private Integer extraccion_sindical_si_no;
    private Integer prestaciones_si_no;
    private Integer comisionado_si_no;
    @CreatedDate
    private LocalDate fecha_antiguedad;
    @CreatedDate
    private LocalDate fecha_ingreso;
    //private Integer persona_id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "expediente_id",nullable = true)
    private Cat_Expediente cat_expediente;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "persona_id", referencedColumnName = "id_persona", nullable = true)
    private Persona persona;
    
    public Trabajador() {
    }

    public Trabajador(Integer id_trabajador, Integer extraccion_sindical_si_no, Integer prestaciones_si_no, Integer comisionado_si_no, LocalDate fecha_antiguedad, LocalDate fecha_ingreso, Cat_Expediente cat_expediente, Persona persona) {
        this.id_trabajador = id_trabajador;
        this.extraccion_sindical_si_no = extraccion_sindical_si_no;
        this.prestaciones_si_no = prestaciones_si_no;
        this.comisionado_si_no = comisionado_si_no;
        this.fecha_antiguedad = fecha_antiguedad;
        this.fecha_ingreso = fecha_ingreso;
        this.cat_expediente = cat_expediente;
        this.persona = persona;
    }

    public Integer getId_trabajador() {
        return id_trabajador;
    }

    public void setId_trabajador(Integer id_trabajador) {
        this.id_trabajador = id_trabajador;
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

    public Cat_Expediente getCat_expediente() {
        return cat_expediente;
    }

    public void setCat_expediente(Cat_Expediente cat_expediente) {
        this.cat_expediente = cat_expediente;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    
}
