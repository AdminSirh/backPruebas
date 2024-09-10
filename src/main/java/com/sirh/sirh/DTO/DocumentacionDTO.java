/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.sirh.sirh.entity.Cat_Tipo_Documento;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;

/**
 *
 * @author jarellano22
 */
public class DocumentacionDTO implements Serializable {


    private Cat_Tipo_Documento cat_tipo_documento;
    private Integer persona_id;
    @CreatedDate
    private LocalDate fecha_captura;
    private LocalDate fecha_modificacion;

    public DocumentacionDTO(Cat_Tipo_Documento cat_tipo_documento, Integer persona_id, LocalDate fecha_captura, LocalDate fecha_modificacion) {
        this.cat_tipo_documento = cat_tipo_documento;
        this.persona_id = persona_id;
        this.fecha_captura = fecha_captura;
        this.fecha_modificacion = fecha_modificacion;
    }

    public Cat_Tipo_Documento getCat_tipo_documento() {
        return cat_tipo_documento;
    }

    public void setCat_tipo_documento(Cat_Tipo_Documento cat_tipo_documento) {
        this.cat_tipo_documento = cat_tipo_documento;
    }

    public Integer getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(Integer persona_id) {
        this.persona_id = persona_id;
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
    

}
