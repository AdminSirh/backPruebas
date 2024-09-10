
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
@Table(name = "catalogo_impuesto_concepto")
public class Cat_Impuesto_Concepto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_impuesto_concepto;
    
    private String descripcion;

    public Cat_Impuesto_Concepto(Integer id_impuesto_concepto, String descripcion) {
        this.id_impuesto_concepto = id_impuesto_concepto;
        this.descripcion = descripcion;
    }

    public Cat_Impuesto_Concepto() {
    }

    public Integer getId_impuesto_concepto() {
        return id_impuesto_concepto;
    }

    public void setId_impuesto_concepto(Integer id_impuesto_concepto) {
        this.id_impuesto_concepto = id_impuesto_concepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
    
}
