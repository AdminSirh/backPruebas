
package com.sirh.sirh.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author nreyes22
 */

@Entity
@Table(name = "catalogo_impuestos_nomina")
public class Cat_Impuestos_Nomina implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_impuesto;
    
    private String periodo;
    private Double limite_inferior;
    private Double limite_superior;
    private Double cuota_fija_porcentaje;
    private Double cuota_fija_dinero;
    private Double porcentaje_excedente_limite_inferior;
    private String articulo;
    private Integer anio;
    @CreatedDate
    private LocalDate fecha_inicio;
    @CreatedDate
    private LocalDate fecha_fin;
    private String origen;
    private Integer estatus;
    private String vigencia;
    private Integer nomina_id;
    private Integer impuesto_concepto_id;

    public Cat_Impuestos_Nomina() {
    }

    public Cat_Impuestos_Nomina(Integer id_impuesto, String periodo, Double limite_inferior, Double limite_superior, Double cuota_fija_porcentaje, Double cuota_fija_dinero, Double porcentaje_excedente_limite_inferior, String articulo, Integer anio, LocalDate fecha_inicio, LocalDate fecha_fin, String origen, Integer estatus, String vigencia, Integer nomina_id, Integer impuesto_concepto_id) {
        this.id_impuesto = id_impuesto;
        this.periodo = periodo;
        this.limite_inferior = limite_inferior;
        this.limite_superior = limite_superior;
        this.cuota_fija_porcentaje = cuota_fija_porcentaje;
        this.cuota_fija_dinero = cuota_fija_dinero;
        this.porcentaje_excedente_limite_inferior = porcentaje_excedente_limite_inferior;
        this.articulo = articulo;
        this.anio = anio;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.origen = origen;
        this.estatus = estatus;
        this.vigencia = vigencia;
        this.nomina_id = nomina_id;
        this.impuesto_concepto_id = impuesto_concepto_id;
    }

    public Integer getId_impuesto() {
        return id_impuesto;
    }

    public void setId_impuesto(Integer id_impuesto) {
        this.id_impuesto = id_impuesto;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Double getLimite_inferior() {
        return limite_inferior;
    }

    public void setLimite_inferior(Double limite_inferior) {
        this.limite_inferior = limite_inferior;
    }

    public Double getLimite_superior() {
        return limite_superior;
    }

    public void setLimite_superior(Double limite_superior) {
        this.limite_superior = limite_superior;
    }

    public Double getCuota_fija_porcentaje() {
        return cuota_fija_porcentaje;
    }

    public void setCuota_fija_porcentaje(Double cuota_fija_porcentaje) {
        this.cuota_fija_porcentaje = cuota_fija_porcentaje;
    }

    public Double getCuota_fija_dinero() {
        return cuota_fija_dinero;
    }

    public void setCuota_fija_dinero(Double cuota_fija_dinero) {
        this.cuota_fija_dinero = cuota_fija_dinero;
    }

    public Double getPorcentaje_excedente_limite_inferior() {
        return porcentaje_excedente_limite_inferior;
    }

    public void setPorcentaje_excedente_limite_inferior(Double porcentaje_excedente_limite_inferior) {
        this.porcentaje_excedente_limite_inferior = porcentaje_excedente_limite_inferior;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public Integer getNomina_id() {
        return nomina_id;
    }

    public void setNomina_id(Integer nomina_id) {
        this.nomina_id = nomina_id;
    }

    public Integer getImpuesto_concepto_id() {
        return impuesto_concepto_id;
    }

    public void setImpuesto_concepto_id(Integer impuesto_concepto_id) {
        this.impuesto_concepto_id = impuesto_concepto_id;
    }

    
    
}
