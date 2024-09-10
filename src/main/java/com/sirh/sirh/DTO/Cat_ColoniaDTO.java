/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.sirh.sirh.entity.Cat_Municipio;
import java.io.Serializable;

/**
 * @author nreyes22
 *
 */
public class Cat_ColoniaDTO implements Serializable {

    private Cat_Municipio cat_municipio;
    private String desc_colonia;
    private String cod_postal;
    private String tipo_asentamiento;
    private Integer estatus;  

    public Cat_ColoniaDTO(Cat_Municipio cat_municipio, String desc_colonia, String cod_postal, String tipo_asentamiento, Integer estatus) {
        this.cat_municipio = cat_municipio;
        this.desc_colonia = desc_colonia;
        this.cod_postal = cod_postal;
        this.tipo_asentamiento = tipo_asentamiento;
        this.estatus = estatus;
    }
    
    public Cat_ColoniaDTO(){
        super();
    }

    public Cat_Municipio getCat_municipio() {
        return cat_municipio;
    }

    public String getDesc_colonia() {
        return desc_colonia;
    }

    public String getCod_postal() {
        return cod_postal;
    }

    public String getTipo_asentamiento() {
        return tipo_asentamiento;
    }

    public Integer getEstatus() {
        return estatus;
    }

}
