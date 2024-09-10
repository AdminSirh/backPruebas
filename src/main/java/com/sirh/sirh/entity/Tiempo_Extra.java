/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author rroscero23
 */
@Entity
@Table(name = "tiempo_extra")
public class Tiempo_Extra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tiempo_extra;

    private Integer trabajador_id;

    private Integer periodo_aplicacion_id;

    private Integer periodo_generacion_id;

    private String semana_incidencias;

    private Integer nomina_id;

    @CreatedDate
    private LocalDate fecha_inicio;

    private String tiempo_extra_1;

    private String tiempo_extra_2;

    private String tiempo_extra_3;

    private String tiempo_extra_4;

    private String tiempo_extra_5;

    private String tiempo_extra_6;

    private String tiempo_extra_7;

    private String paseos_lab_1;

    private String paseos_lab_2;

    private String paseos_lab_3;

    private String paseos_lab_4;

    private String paseos_lab_5;

    private String paseos_lab_6;

    private String paseos_lab_7;

    private String te_doble_1;

    private String te_doble_2;

    private String te_doble_3;

    private String te_doble_4;

    private String te_doble_5;

    private String te_doble_6;

    private String te_doble_7;

    private String te_triple_1;

    private String te_triple_2;

    private String te_triple_3;

    private String te_triple_4;

    private String te_triple_5;

    private String te_triple_6;

    private String te_triple_7;

    private Double total_te_doble;

    private Double total_te_triple;

    private Double total_te_paseos_doble;

    private Double total_te_festivo_doble;

    private Integer estatus;

    private Double dif_cve_19;

    public Integer getId_tiempo_extra() {
        return id_tiempo_extra;
    }

    public void setId_tiempo_extra(Integer id_tiempo_extra) {
        this.id_tiempo_extra = id_tiempo_extra;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getPeriodo_aplicacion_id() {
        return periodo_aplicacion_id;
    }

    public void setPeriodo_aplicacion_id(Integer periodo_aplicacion_id) {
        this.periodo_aplicacion_id = periodo_aplicacion_id;
    }

    public Integer getPeriodo_generacion_id() {
        return periodo_generacion_id;
    }

    public void setPeriodo_generacion_id(Integer periodo_generacion_id) {
        this.periodo_generacion_id = periodo_generacion_id;
    }

    public String getSemana_incidencias() {
        return semana_incidencias;
    }

    public void setSemana_incidencias(String semana_incidencias) {
        this.semana_incidencias = semana_incidencias;
    }

    public Integer getNomina_id() {
        return nomina_id;
    }

    public void setNomina_id(Integer nomina_id) {
        this.nomina_id = nomina_id;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getTiempo_extra_1() {
        return tiempo_extra_1;
    }

    public void setTiempo_extra_1(String tiempo_extra_1) {
        this.tiempo_extra_1 = tiempo_extra_1;
    }

    public String getTiempo_extra_2() {
        return tiempo_extra_2;
    }

    public void setTiempo_extra_2(String tiempo_extra_2) {
        this.tiempo_extra_2 = tiempo_extra_2;
    }

    public String getTiempo_extra_3() {
        return tiempo_extra_3;
    }

    public void setTiempo_extra_3(String tiempo_extra_3) {
        this.tiempo_extra_3 = tiempo_extra_3;
    }

    public String getTiempo_extra_4() {
        return tiempo_extra_4;
    }

    public void setTiempo_extra_4(String tiempo_extra_4) {
        this.tiempo_extra_4 = tiempo_extra_4;
    }

    public String getTiempo_extra_5() {
        return tiempo_extra_5;
    }

    public void setTiempo_extra_5(String tiempo_extra_5) {
        this.tiempo_extra_5 = tiempo_extra_5;
    }

    public String getTiempo_extra_6() {
        return tiempo_extra_6;
    }

    public void setTiempo_extra_6(String tiempo_extra_6) {
        this.tiempo_extra_6 = tiempo_extra_6;
    }

    public String getTiempo_extra_7() {
        return tiempo_extra_7;
    }

    public void setTiempo_extra_7(String tiempo_extra_7) {
        this.tiempo_extra_7 = tiempo_extra_7;
    }

    public String getPaseos_lab_1() {
        return paseos_lab_1;
    }

    public void setPaseos_lab_1(String paseos_lab_1) {
        this.paseos_lab_1 = paseos_lab_1;
    }

    public String getPaseos_lab_2() {
        return paseos_lab_2;
    }

    public void setPaseos_lab_2(String paseos_lab_2) {
        this.paseos_lab_2 = paseos_lab_2;
    }

    public String getPaseos_lab_3() {
        return paseos_lab_3;
    }

    public void setPaseos_lab_3(String paseos_lab_3) {
        this.paseos_lab_3 = paseos_lab_3;
    }

    public String getPaseos_lab_4() {
        return paseos_lab_4;
    }

    public void setPaseos_lab_4(String paseos_lab_4) {
        this.paseos_lab_4 = paseos_lab_4;
    }

    public String getPaseos_lab_5() {
        return paseos_lab_5;
    }

    public void setPaseos_lab_5(String paseos_lab_5) {
        this.paseos_lab_5 = paseos_lab_5;
    }

    public String getPaseos_lab_6() {
        return paseos_lab_6;
    }

    public void setPaseos_lab_6(String paseos_lab_6) {
        this.paseos_lab_6 = paseos_lab_6;
    }

    public String getPaseos_lab_7() {
        return paseos_lab_7;
    }

    public void setPaseos_lab_7(String paseos_lab_7) {
        this.paseos_lab_7 = paseos_lab_7;
    }

    public String getTe_doble_1() {
        return te_doble_1;
    }

    public void setTe_doble_1(String te_doble_1) {
        this.te_doble_1 = te_doble_1;
    }

    public String getTe_doble_2() {
        return te_doble_2;
    }

    public void setTe_doble_2(String te_doble_2) {
        this.te_doble_2 = te_doble_2;
    }

    public String getTe_doble_3() {
        return te_doble_3;
    }

    public void setTe_doble_3(String te_doble_3) {
        this.te_doble_3 = te_doble_3;
    }

    public String getTe_doble_4() {
        return te_doble_4;
    }

    public void setTe_doble_4(String te_doble_4) {
        this.te_doble_4 = te_doble_4;
    }

    public String getTe_doble_5() {
        return te_doble_5;
    }

    public void setTe_doble_5(String te_doble_5) {
        this.te_doble_5 = te_doble_5;
    }

    public String getTe_doble_6() {
        return te_doble_6;
    }

    public void setTe_doble_6(String te_doble_6) {
        this.te_doble_6 = te_doble_6;
    }

    public String getTe_doble_7() {
        return te_doble_7;
    }

    public void setTe_doble_7(String te_doble_7) {
        this.te_doble_7 = te_doble_7;
    }

    public String getTe_triple_1() {
        return te_triple_1;
    }

    public void setTe_triple_1(String te_triple_1) {
        this.te_triple_1 = te_triple_1;
    }

    public String getTe_triple_2() {
        return te_triple_2;
    }

    public void setTe_triple_2(String te_triple_2) {
        this.te_triple_2 = te_triple_2;
    }

    public String getTe_triple_3() {
        return te_triple_3;
    }

    public void setTe_triple_3(String te_triple_3) {
        this.te_triple_3 = te_triple_3;
    }

    public String getTe_triple_4() {
        return te_triple_4;
    }

    public void setTe_triple_4(String te_triple_4) {
        this.te_triple_4 = te_triple_4;
    }

    public String getTe_triple_5() {
        return te_triple_5;
    }

    public void setTe_triple_5(String te_triple_5) {
        this.te_triple_5 = te_triple_5;
    }

    public String getTe_triple_6() {
        return te_triple_6;
    }

    public void setTe_triple_6(String te_triple_6) {
        this.te_triple_6 = te_triple_6;
    }

    public String getTe_triple_7() {
        return te_triple_7;
    }

    public void setTe_triple_7(String te_triple_7) {
        this.te_triple_7 = te_triple_7;
    }

    public Double getTotal_te_doble() {
        return total_te_doble;
    }

    public void setTotal_te_doble(Double total_te_doble) {
        this.total_te_doble = total_te_doble;
    }

    public Double getTotal_te_triple() {
        return total_te_triple;
    }

    public void setTotal_te_triple(Double total_te_triple) {
        this.total_te_triple = total_te_triple;
    }

    public Double getTotal_te_paseos_doble() {
        return total_te_paseos_doble;
    }

    public void setTotal_te_paseos_doble(Double total_te_paseos_doble) {
        this.total_te_paseos_doble = total_te_paseos_doble;
    }

    public Double getTotal_te_festivo_doble() {
        return total_te_festivo_doble;
    }

    public void setTotal_te_festivo_doble(Double total_te_festivo_doble) {
        this.total_te_festivo_doble = total_te_festivo_doble;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Double getDif_cve_19() {
        return dif_cve_19;
    }

    public void setDif_cve_19(Double dif_cve_19) {
        this.dif_cve_19 = dif_cve_19;
    }

    

}
