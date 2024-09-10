package com.sirh.sirh.DTO;

import java.io.Serializable;


public class Cat_MunicipioDTO implements Serializable{
    
    private String desc_municipio;
    private Integer id_estado;

    public Integer getId_estado() {
        return id_estado;
    }

    public void setId_estado(Integer id_estado) {
        this.id_estado = id_estado;
    }
    

    

    public String getDesc_municipio() {
        return desc_municipio;
    }

    public void setDesc_municipio(String desc_municipio) {
        this.desc_municipio = desc_municipio;
    }


    
    
}
