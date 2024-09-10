/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.sirh.sirh.entity.Cat_Edo_Civil;
import com.sirh.sirh.entity.Cat_Estado;
import com.sirh.sirh.entity.Cat_Estudios;
import com.sirh.sirh.entity.Cat_Genero;
import com.sirh.sirh.entity.Cat_Nacionalidad;
import com.sirh.sirh.entity.Cat_Si_No;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author jarellano22
 */
public class PersonaGeneralesDTO implements Serializable {


    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private Cat_Genero cat_genero;
    private String rfc;

    @CreatedDate
    private LocalDate fecha_modificacion;
    private String matricula_smn;
    private Cat_Edo_Civil cat_estado_civil;
    private String num_imss;

    private Cat_Estudios cat_estudios;
    private String curp;

    private Cat_Si_No credito_infonavit_si_no;
    private Cat_Si_No cargo_admon_pub_si_no;
    private Cat_Si_No inhabilitado_admon_pub_si_no;
    private LocalDate fecha_matrimonio;

    private LocalDate fecha_nacimiento;
    private Cat_Si_No hijos_si_no;

    private Cat_Nacionalidad cat_nacionalidad;
    private Cat_Estado cat_estado_nacimiento;

    public PersonaGeneralesDTO() {
    }

    public PersonaGeneralesDTO(String nombre, String apellido_paterno, String apellido_materno, Cat_Genero cat_genero, String rfc, LocalDate fecha_modificacion, String matricula_smn, Cat_Edo_Civil cat_estado_civil, String num_imss, Cat_Estudios cat_estudios, String curp, Cat_Si_No credito_infonavit_si_no, Cat_Si_No cargo_admon_pub_si_no, Cat_Si_No inhabilitado_admon_pub_si_no, LocalDate fecha_matrimonio, LocalDate fecha_nacimiento, Cat_Si_No hijos_si_no, Cat_Nacionalidad cat_nacionalidad, Cat_Estado cat_estado_nacimiento) {
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.cat_genero = cat_genero;
        this.rfc = rfc;
        this.fecha_modificacion = fecha_modificacion;
        this.matricula_smn = matricula_smn;
        this.cat_estado_civil = cat_estado_civil;
        this.num_imss = num_imss;
        this.cat_estudios = cat_estudios;
        this.curp = curp;
        this.credito_infonavit_si_no = credito_infonavit_si_no;
        this.cargo_admon_pub_si_no = cargo_admon_pub_si_no;
        this.inhabilitado_admon_pub_si_no = inhabilitado_admon_pub_si_no;
        this.fecha_matrimonio = fecha_matrimonio;
        this.fecha_nacimiento = fecha_nacimiento;
        this.hijos_si_no = hijos_si_no;
        this.cat_nacionalidad = cat_nacionalidad;
        this.cat_estado_nacimiento = cat_estado_nacimiento;
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

    public LocalDate getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(LocalDate fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
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

    public Cat_Si_No getCredito_infonavit_si_no() {
        return credito_infonavit_si_no;
    }

    public void setCredito_infonavit_si_no(Cat_Si_No credito_infonavit_si_no) {
        this.credito_infonavit_si_no = credito_infonavit_si_no;
    }

    public Cat_Si_No getCargo_admon_pub_si_no() {
        return cargo_admon_pub_si_no;
    }

    public void setCargo_admon_pub_si_no(Cat_Si_No cargo_admon_pub_si_no) {
        this.cargo_admon_pub_si_no = cargo_admon_pub_si_no;
    }

    public Cat_Si_No getInhabilitado_admon_pub_si_no() {
        return inhabilitado_admon_pub_si_no;
    }

    public void setInhabilitado_admon_pub_si_no(Cat_Si_No inhabilitado_admon_pub_si_no) {
        this.inhabilitado_admon_pub_si_no = inhabilitado_admon_pub_si_no;
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

    public Cat_Si_No getHijos_si_no() {
        return hijos_si_no;
    }

    public void setHijos_si_no(Cat_Si_No hijos_si_no) {
        this.hijos_si_no = hijos_si_no;
    }

    public Cat_Nacionalidad getCat_nacionalidad() {
        return cat_nacionalidad;
    }

    public void setCat_nacionalidad(Cat_Nacionalidad cat_nacionalidad) {
        this.cat_nacionalidad = cat_nacionalidad;
    }

    public Cat_Estado getCat_estado_nacimiento() {
        return cat_estado_nacimiento;
    }

    public void setCat_estado_nacimiento(Cat_Estado cat_estado_nacimiento) {
        this.cat_estado_nacimiento = cat_estado_nacimiento;
    }

    

}
