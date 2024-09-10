
package com.sirh.sirh.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author nreyes22
 */
@Entity
@Table(name = "catalogo_colonia")
public class Cat_Colonia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_colonia;
    private String desc_colonia;
    private String cod_postal;
    private String tipo_asentamiento;
    private Integer estatus;
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_municipio")
    private Cat_Municipio cat_municipio;

    public Cat_Colonia(Integer id_colonia, String desc_colonia, String cod_postal, String tipo_asentamiento, Integer estatus, Cat_Municipio cat_municipio) {
        this.id_colonia = id_colonia;
        this.desc_colonia = desc_colonia;
        this.cod_postal = cod_postal;
        this.tipo_asentamiento = tipo_asentamiento;
        this.estatus = estatus;
        this.cat_municipio = cat_municipio;
    }

    public Cat_Colonia() {
        super();
    }

    
    public Integer getId_colonia() {
        return id_colonia;
    }

    public void setId_colonia(Integer id_colonia) {
        this.id_colonia = id_colonia;
    }

    public String getDesc_colonia() {
        return desc_colonia;
    }

    public void setDesc_colonia(String desc_colonia) {
        this.desc_colonia = desc_colonia;
    }

    public String getCod_postal() {
        return cod_postal;
    }

    public void setCod_postal(String cod_postal) {
        this.cod_postal = cod_postal;
    }

    public String getTipo_asentamiento() {
        return tipo_asentamiento;
    }

    public void setTipo_asentamiento(String tipo_asentamiento) {
        this.tipo_asentamiento = tipo_asentamiento;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Cat_Municipio getCat_municipio() {
        return cat_municipio;
    }

    public void setCat_municipio(Cat_Municipio cat_municipio) {
        this.cat_municipio = cat_municipio;
    }
 
  

}
