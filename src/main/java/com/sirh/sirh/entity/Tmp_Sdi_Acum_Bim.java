/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

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
import javax.persistence.Temporal;

/**
 *
 * @author rroscero23
 */
@Entity
@Table(name = "tmp_sdi_acumulados_bimestre")
public class Tmp_Sdi_Acum_Bim implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_sdi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bimestre_id")
    private Cat_Bimestres_Sdi cat_Bimestres_Sdi;

    private Integer trabajador_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clave_id")
    private Tmp_Sdi_Acum_Bimestre_Cves tmp_Sdi_Acum_Bimestre_Cves;

    private LocalDate fecha_egreso;
    private Integer pago_id_nomina;
    private Integer pago_id_puesto;
    private Double pago_sueldo;
    private Integer id_cambios;

    public Tmp_Sdi_Acum_Bim() {
        super();
    }

    public Tmp_Sdi_Acum_Bim(Integer id_sdi, Cat_Bimestres_Sdi cat_Bimestres_Sdi, Integer trabajador_id, Tmp_Sdi_Acum_Bimestre_Cves tmp_Sdi_Acum_Bimestre_Cves, LocalDate fecha_egreso, Integer pago_id_nomina, Integer pago_id_puesto, Double pago_sueldo, Integer id_cambios) {
        this.id_sdi = id_sdi;
        this.cat_Bimestres_Sdi = cat_Bimestres_Sdi;
        this.trabajador_id = trabajador_id;
        this.tmp_Sdi_Acum_Bimestre_Cves = tmp_Sdi_Acum_Bimestre_Cves;
        this.fecha_egreso = fecha_egreso;
        this.pago_id_nomina = pago_id_nomina;
        this.pago_id_puesto = pago_id_puesto;
        this.pago_sueldo = pago_sueldo;
        this.id_cambios = id_cambios;
    }

    public Integer getId_sdi() {
        return id_sdi;
    }

    public void setId_sdi(Integer id_sdi) {
        this.id_sdi = id_sdi;
    }

    public Cat_Bimestres_Sdi getCat_Bimestres_Sdi() {
        return cat_Bimestres_Sdi;
    }

    public void setCat_Bimestres_Sdi(Cat_Bimestres_Sdi cat_Bimestres_Sdi) {
        this.cat_Bimestres_Sdi = cat_Bimestres_Sdi;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Tmp_Sdi_Acum_Bimestre_Cves getTmp_Sdi_Acum_Bimestre_Cves() {
        return tmp_Sdi_Acum_Bimestre_Cves;
    }

    public void setTmp_Sdi_Acum_Bimestre_Cves(Tmp_Sdi_Acum_Bimestre_Cves tmp_Sdi_Acum_Bimestre_Cves) {
        this.tmp_Sdi_Acum_Bimestre_Cves = tmp_Sdi_Acum_Bimestre_Cves;
    }

    public LocalDate getFecha_egreso() {
        return fecha_egreso;
    }

    public void setFecha_egreso(LocalDate fecha_egreso) {
        this.fecha_egreso = fecha_egreso;
    }

    public Integer getPago_id_nomina() {
        return pago_id_nomina;
    }

    public void setPago_id_nomina(Integer pago_id_nomina) {
        this.pago_id_nomina = pago_id_nomina;
    }

    public Integer getPago_id_puesto() {
        return pago_id_puesto;
    }

    public void setPago_id_puesto(Integer pago_id_puesto) {
        this.pago_id_puesto = pago_id_puesto;
    }

    public Double getPago_sueldo() {
        return pago_sueldo;
    }

    public void setPago_sueldo(Double pago_sueldo) {
        this.pago_sueldo = pago_sueldo;
    }

    public Integer getId_cambios() {
        return id_cambios;
    }

    public void setId_cambios(Integer id_cambios) {
        this.id_cambios = id_cambios;
    }
    
    
    
}
