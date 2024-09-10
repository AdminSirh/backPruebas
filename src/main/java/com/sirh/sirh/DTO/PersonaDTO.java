/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.sirh.sirh.entity.Cat_Edo_Civil;
import com.sirh.sirh.entity.Cat_Genero;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author jarellano22
 */
public class PersonaDTO implements Serializable {

    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private Cat_Genero cat_genero;
    private Cat_Edo_Civil cat_estado_civil;
    private String curp;
    @CreatedDate
    private LocalDate fecha_captura;
    private Integer expediente_si_no;

    public PersonaDTO(String nombre, String apellido_paterno, String apellido_materno, Cat_Genero cat_genero, Cat_Edo_Civil cat_estado_civil, String curp, LocalDate fecha_captura, Integer expediente_si_no) {
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.cat_genero = cat_genero;
        this.cat_estado_civil = cat_estado_civil;
        this.curp = curp;
        this.fecha_captura = fecha_captura;
        this.expediente_si_no = expediente_si_no;
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

    public Cat_Edo_Civil getCat_estado_civil() {
        return cat_estado_civil;
    }

    public void setCat_estado_civil(Cat_Edo_Civil cat_estado_civil) {
        this.cat_estado_civil = cat_estado_civil;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public LocalDate getFecha_captura() {
        return fecha_captura;
    }

    public void setFecha_captura(LocalDate fecha_captura) {
        this.fecha_captura = fecha_captura;
    }

    public Integer getExpediente_si_no() {
        return expediente_si_no;
    }

    public void setExpediente_si_no(Integer expediente_si_no) {
        this.expediente_si_no = expediente_si_no;
    }

    
}
