
package com.sirh.sirh.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author nreyes22
 */

@Entity
@Table(name = "catalogo_periodo_impuesto")
public class Cat_Periodo_Impuesto implements Serializable {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_periodo_impuesto;

    private String descripcion;

    public Cat_Periodo_Impuesto(Integer id_periodo_impuesto, String descripcion) {
        this.id_periodo_impuesto = id_periodo_impuesto;
        this.descripcion = descripcion;
    }

    public Cat_Periodo_Impuesto() {
    }

    public Integer getId_periodo_impuesto() {
        return id_periodo_impuesto;
    }

    public void setId_periodo_impuesto(Integer id_periodo_impuesto) {
        this.id_periodo_impuesto = id_periodo_impuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }




}
