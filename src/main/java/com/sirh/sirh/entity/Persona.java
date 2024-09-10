/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

/**
 *
 * @author jarellano22
 */
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "persona")
public class Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_persona;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido_paterno;

    private String apellido_materno;

    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genero_id")
    private Cat_Genero cat_genero;

    private String rfc;

    private Integer expediente_si_no; // --> **

    @CreatedDate
    private LocalDate fecha_captura;

    @CreatedDate
    private LocalDate fecha_modificacion;

    @CreatedDate
    private LocalDate fecha_inactividad;

    private String matricula_smn;

    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_civil_id")
    private Cat_Edo_Civil cat_estado_civil;

    private String num_imss;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grado_estudios_id")
    private Cat_Estudios cat_estudios;

    @NotEmpty
    private String curp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credito_infonavit_si_no")
    private Cat_Si_No cat_credito_infonavit_si_no;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cargo_admon_pub_si_no")
    private Cat_Si_No cat_cargo_admon_pub_si_no;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inhabilitado_admon_pub_si_no")
    private Cat_Si_No cat_inhabilitado_admon_pub_si_no;

    @CreatedDate
    private LocalDate fecha_matrimonio;
    @CreatedDate
    private LocalDate fecha_nacimiento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hijos_si_no")
    private Cat_Si_No cat_hijos_si_no;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nacionalidad_id")
    private Cat_Nacionalidad cat_nacionalidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_nacimiento_id")
    private Cat_Estado cat_estado;

    public Persona() {
        super();
    }

    public Persona(Integer id_persona, String nombre, String apellido_paterno, String apellido_materno, Cat_Genero cat_genero, String rfc, Integer expediente_si_no, LocalDate fecha_captura, LocalDate fecha_modificacion, LocalDate fecha_inactividad, String matricula_smn, Cat_Edo_Civil cat_estado_civil, String num_imss, Cat_Estudios cat_estudios, String curp, Cat_Si_No cat_credito_infonavit_si_no, Cat_Si_No cat_cargo_admon_pub_si_no, Cat_Si_No cat_inhabilitado_admon_pub_si_no, LocalDate fecha_matrimonio, LocalDate fecha_nacimiento, Cat_Si_No cat_hijos_si_no, Cat_Nacionalidad cat_nacionalidad, Cat_Estado cat_estado) {
        this.id_persona = id_persona;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.cat_genero = cat_genero;
        this.rfc = rfc;
        this.expediente_si_no = expediente_si_no;
        this.fecha_captura = fecha_captura;
        this.fecha_modificacion = fecha_modificacion;
        this.fecha_inactividad = fecha_inactividad;
        this.matricula_smn = matricula_smn;
        this.cat_estado_civil = cat_estado_civil;
        this.num_imss = num_imss;
        this.cat_estudios = cat_estudios;
        this.curp = curp;
        this.cat_credito_infonavit_si_no = cat_credito_infonavit_si_no;
        this.cat_cargo_admon_pub_si_no = cat_cargo_admon_pub_si_no;
        this.cat_inhabilitado_admon_pub_si_no = cat_inhabilitado_admon_pub_si_no;
        this.fecha_matrimonio = fecha_matrimonio;
        this.fecha_nacimiento = fecha_nacimiento;
        this.cat_hijos_si_no = cat_hijos_si_no;
        this.cat_nacionalidad = cat_nacionalidad;
        this.cat_estado = cat_estado;
    }

    public Integer getId_persona() {
        return id_persona;
    }

    public void setId_persona(Integer id_persona) {
        this.id_persona = id_persona;
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

    public Cat_Genero getCat_genero() {
        return cat_genero;
    }

    public void setCat_genero(Cat_Genero cat_genero) {
        this.cat_genero = cat_genero;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Integer getExpediente_si_no() {
        return expediente_si_no;
    }

    public void setExpediente_si_no(Integer expediente_si_no) {
        this.expediente_si_no = expediente_si_no;
    }

    public LocalDate getFecha_captura() {
        return fecha_captura;
    }

    public void setFecha_captura(LocalDate fecha_captura) {
        this.fecha_captura = fecha_captura;
    }

    public LocalDate getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(LocalDate fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public LocalDate getFecha_inactividad() {
        return fecha_inactividad;
    }

    public void setFecha_inactividad(LocalDate fecha_inactividad) {
        this.fecha_inactividad = fecha_inactividad;
    }

    public String getMatricula_smn() {
        return matricula_smn;
    }

    public void setMatricula_smn(String matricula_smn) {
        this.matricula_smn = matricula_smn;
    }

    public Cat_Edo_Civil getCat_estado_civil() {
        return cat_estado_civil;
    }

    public void setCat_estado_civil(Cat_Edo_Civil cat_estado_civil) {
        this.cat_estado_civil = cat_estado_civil;
    }

    public String getNum_imss() {
        return num_imss;
    }

    public void setNum_imss(String num_imss) {
        this.num_imss = num_imss;
    }

    public Cat_Estudios getCat_estudios() {
        return cat_estudios;
    }

    public void setCat_estudios(Cat_Estudios cat_estudios) {
        this.cat_estudios = cat_estudios;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Cat_Si_No getCat_credito_infonavit_si_no() {
        return cat_credito_infonavit_si_no;
    }

    public void setCat_credito_infonavit_si_no(Cat_Si_No cat_credito_infonavit_si_no) {
        this.cat_credito_infonavit_si_no = cat_credito_infonavit_si_no;
    }

    public Cat_Si_No getCat_cargo_admon_pub_si_no() {
        return cat_cargo_admon_pub_si_no;
    }

    public void setCat_cargo_admon_pub_si_no(Cat_Si_No cat_cargo_admon_pub_si_no) {
        this.cat_cargo_admon_pub_si_no = cat_cargo_admon_pub_si_no;
    }

    public Cat_Si_No getCat_inhabilitado_admon_pub_si_no() {
        return cat_inhabilitado_admon_pub_si_no;
    }

    public void setCat_inhabilitado_admon_pub_si_no(Cat_Si_No cat_inhabilitado_admon_pub_si_no) {
        this.cat_inhabilitado_admon_pub_si_no = cat_inhabilitado_admon_pub_si_no;
    }

    public LocalDate getFecha_matrimonio() {
        return fecha_matrimonio;
    }

    public void setFecha_matrimonio(LocalDate fecha_matrimonio) {
        this.fecha_matrimonio = fecha_matrimonio;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public Cat_Si_No getCat_hijos_si_no() {
        return cat_hijos_si_no;
    }

    public void setCat_hijos_si_no(Cat_Si_No cat_hijos_si_no) {
        this.cat_hijos_si_no = cat_hijos_si_no;
    }

    public Cat_Nacionalidad getCat_nacionalidad() {
        return cat_nacionalidad;
    }

    public void setCat_nacionalidad(Cat_Nacionalidad cat_nacionalidad) {
        this.cat_nacionalidad = cat_nacionalidad;
    }

    public Cat_Estado getCat_estado() {
        return cat_estado;
    }

    public void setCat_estado(Cat_Estado cat_estado) {
        this.cat_estado = cat_estado;
    }

    
}
