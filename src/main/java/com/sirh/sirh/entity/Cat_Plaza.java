
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

/**
 *
 * @author nreyes22
 */

@Entity
@Table(name = "catalogo_plaza")
public class Cat_Plaza implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_plaza;
   
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "puesto_id")
    private Cat_Puesto cat_puesto;
    
    private Integer estatus_plaza_id;
    
    
    private Integer numero_plaza;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "situacion_plaza_id")
    private Cat_Situacion_Plaza cat_Situacion_Plaza;

    public Cat_Plaza() {
        super();
    }

    public Cat_Plaza(Integer id_plaza, Cat_Puesto cat_puesto, Integer estatus_plaza_id, Integer numero_plaza, Cat_Situacion_Plaza cat_Situacion_Plaza) {
        this.id_plaza = id_plaza;
        this.cat_puesto = cat_puesto;
        this.estatus_plaza_id = estatus_plaza_id;
        this.numero_plaza = numero_plaza;
        this.cat_Situacion_Plaza = cat_Situacion_Plaza;
    }

    public Integer getId_plaza() {
        return id_plaza;
    }

    public void setId_plaza(Integer id_plaza) {
        this.id_plaza = id_plaza;
    }

    public Cat_Puesto getCat_puesto() {
        return cat_puesto;
    }

    public void setCat_puesto(Cat_Puesto cat_puesto) {
        this.cat_puesto = cat_puesto;
    }

    public Integer getEstatus_plaza_id() {
        return estatus_plaza_id;
    }

    public void setEstatus_plaza_id(Integer estatus_plaza_id) {
        this.estatus_plaza_id = estatus_plaza_id;
    }

    public Integer getNumero_plaza() {
        return numero_plaza;
    }

    public void setNumero_plaza(Integer numero_plaza) {
        this.numero_plaza = numero_plaza;
    }

    public Cat_Situacion_Plaza getCat_Situacion_Plaza() {
        return cat_Situacion_Plaza;
    }

    public void setCat_Situacion_Plaza(Cat_Situacion_Plaza cat_Situacion_Plaza) {
        this.cat_Situacion_Plaza = cat_Situacion_Plaza;
    }

}
