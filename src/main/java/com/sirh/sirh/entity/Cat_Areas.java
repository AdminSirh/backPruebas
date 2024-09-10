/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

/**
 *
 * @author jarellano22
 */
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "catalogo_areas")
public class Cat_Areas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_area")
    private Integer idArea; 
    private Integer codigo_area_superior;
    private String desc_area;
    private String determinante;
    private Integer codigo_area;
    private String presupuesto;
    private String codigo_personal;
    private String hw_status;
    private Integer hw_area;
    private Integer hw_bis;
    private Integer programa;
    private String doc_autorizacion;
    
    //Agregar para evitar errores en el formato de la fecha 
    @CreatedDate
    private LocalDate fecha_movimiento;
    private Integer estatus;
    @CreatedDate
    private LocalDate fecha_captura;

    public Cat_Areas() {
        super();
    }

    public Cat_Areas(Integer idArea, Integer codigo_area_superior, String desc_area, String determinante, Integer codigo_area, String presupuesto, String codigo_personal, String hw_status, Integer hw_area, Integer hw_bis, Integer programa, String doc_autorizacion, LocalDate fecha_movimiento, Integer estatus, LocalDate fecha_captura) {
        this.idArea = idArea;
        this.codigo_area_superior = codigo_area_superior;
        this.desc_area = desc_area;
        this.determinante = determinante;
        this.codigo_area = codigo_area;
        this.presupuesto = presupuesto;
        this.codigo_personal = codigo_personal;
        this.hw_status = hw_status;
        this.hw_area = hw_area;
        this.hw_bis = hw_bis;
        this.programa = programa;
        this.doc_autorizacion = doc_autorizacion;
        this.fecha_movimiento = fecha_movimiento;
        this.estatus = estatus;
        this.fecha_captura = fecha_captura;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getCodigo_area_superior() {
        return codigo_area_superior;
    }

    public void setCodigo_area_superior(Integer codigo_area_superior) {
        this.codigo_area_superior = codigo_area_superior;
    }

    public String getDesc_area() {
        return desc_area;
    }

    public void setDesc_area(String desc_area) {
        this.desc_area = desc_area;
    }

    public String getDeterminante() {
        return determinante;
    }

    public void setDeterminante(String determinante) {
        this.determinante = determinante;
    }

    public Integer getCodigo_area() {
        return codigo_area;
    }

    public void setCodigo_area(Integer codigo_area) {
        this.codigo_area = codigo_area;
    }

    public String getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(String presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getCodigo_personal() {
        return codigo_personal;
    }

    public void setCodigo_personal(String codigo_personal) {
        this.codigo_personal = codigo_personal;
    }

    public String getHw_status() {
        return hw_status;
    }

    public void setHw_status(String hw_status) {
        this.hw_status = hw_status;
    }

    public Integer getHw_area() {
        return hw_area;
    }

    public void setHw_area(Integer hw_area) {
        this.hw_area = hw_area;
    }

    public Integer getHw_bis() {
        return hw_bis;
    }

    public void setHw_bis(Integer hw_bis) {
        this.hw_bis = hw_bis;
    }

    public Integer getPrograma() {
        return programa;
    }

    public void setPrograma(Integer programa) {
        this.programa = programa;
    }

    public String getDoc_autorizacion() {
        return doc_autorizacion;
    }

    public void setDoc_autorizacion(String doc_autorizacion) {
        this.doc_autorizacion = doc_autorizacion;
    }

    public LocalDate getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDate fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public LocalDate getFecha_captura() {
        return fecha_captura;
    }

    public void setFecha_captura(LocalDate fecha_captura) {
        this.fecha_captura = fecha_captura;
    }

    
   
}