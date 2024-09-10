/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author rroscero23
 */
@Entity
@Table(name = "tmp_retro_jubilados")
public class Tmp_Retro_Jubilados implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_retro;
    private Integer trabajador_id;
    private Integer puesto_id;
    private Integer periodo_id;
    private Double sueldo;
    private Double inci_02;
    private Double inci_30;
    private Double inci_33;
    private Double inci_44;
    private Double inci_251;
    private Double p_29;
    private Double p_30;
    private Double p_33;
    private Double p_44;
    private Double p_251;
    private Double p_76;
    private Integer anio;
    private Double retro_cve_29;
    private Double retro_cve_30;
    private Double retro_cve_33;
    private Double retro_cve_44;
    private Double retro_cve_76;
    private Double retro_cve_251;

    public Integer getId_retro() {
        return id_retro;
    }

    public void setId_retro(Integer id_retro) {
        this.id_retro = id_retro;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    public Integer getPuesto_id() {
        return puesto_id;
    }

    public void setPuesto_id(Integer puesto_id) {
        this.puesto_id = puesto_id;
    }

    public Integer getPeriodo_id() {
        return periodo_id;
    }

    public void setPeriodo_id(Integer periodo_id) {
        this.periodo_id = periodo_id;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public Double getInci_02() {
        return inci_02;
    }

    public void setInci_02(Double inci_02) {
        this.inci_02 = inci_02;
    }

    public Double getInci_30() {
        return inci_30;
    }

    public void setInci_30(Double inci_30) {
        this.inci_30 = inci_30;
    }

    public Double getInci_33() {
        return inci_33;
    }

    public void setInci_33(Double inci_33) {
        this.inci_33 = inci_33;
    }

    public Double getInci_44() {
        return inci_44;
    }

    public void setInci_44(Double inci_44) {
        this.inci_44 = inci_44;
    }

    public Double getInci_251() {
        return inci_251;
    }

    public void setInci_251(Double inci_251) {
        this.inci_251 = inci_251;
    }

    public Double getP_29() {
        return p_29;
    }

    public void setP_29(Double p_29) {
        this.p_29 = p_29;
    }

    public Double getP_30() {
        return p_30;
    }

    public void setP_30(Double p_30) {
        this.p_30 = p_30;
    }

    public Double getP_33() {
        return p_33;
    }

    public void setP_33(Double p_33) {
        this.p_33 = p_33;
    }

    public Double getP_44() {
        return p_44;
    }

    public void setP_44(Double p_44) {
        this.p_44 = p_44;
    }

    public Double getP_251() {
        return p_251;
    }

    public void setP_251(Double p_251) {
        this.p_251 = p_251;
    }

    public Double getP_76() {
        return p_76;
    }

    public void setP_76(Double p_76) {
        this.p_76 = p_76;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Double getRetro_cve_29() {
        return retro_cve_29;
    }

    public void setRetro_cve_29(Double retro_cve_29) {
        this.retro_cve_29 = retro_cve_29;
    }

    public Double getRetro_cve_30() {
        return retro_cve_30;
    }

    public void setRetro_cve_30(Double retro_cve_30) {
        this.retro_cve_30 = retro_cve_30;
    }

    public Double getRetro_cve_33() {
        return retro_cve_33;
    }

    public void setRetro_cve_33(Double retro_cve_33) {
        this.retro_cve_33 = retro_cve_33;
    }

    public Double getRetro_cve_44() {
        return retro_cve_44;
    }

    public void setRetro_cve_44(Double retro_cve_44) {
        this.retro_cve_44 = retro_cve_44;
    }

    public Double getRetro_cve_76() {
        return retro_cve_76;
    }

    public void setRetro_cve_76(Double retro_cve_76) {
        this.retro_cve_76 = retro_cve_76;
    }

    public Double getRetro_cve_251() {
        return retro_cve_251;
    }

    public void setRetro_cve_251(Double retro_cve_251) {
        this.retro_cve_251 = retro_cve_251;
    }

}
