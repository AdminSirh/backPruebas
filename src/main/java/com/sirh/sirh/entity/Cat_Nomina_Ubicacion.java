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
 * @author oguerrero23
 */
@Entity
@Table(name = "catalogo_tipo_nomina_ubicacion")
public class Cat_Nomina_Ubicacion implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipo_nomina_ubicacion;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_nomina_id")
    private Cat_Tipo_Nomina cat_Tipo_Nomina;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ubicacion_id")
    private Cat_Ubicacion cat_Ubicacion;

    public Cat_Nomina_Ubicacion() {
        super();
    }

    public Cat_Nomina_Ubicacion(Integer id_tipo_nomina_ubicacion, Cat_Tipo_Nomina cat_Tipo_Nomina, Cat_Ubicacion cat_Ubicacion) {
        this.id_tipo_nomina_ubicacion = id_tipo_nomina_ubicacion;
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
        this.cat_Ubicacion = cat_Ubicacion;
    }

    public Integer getId_tipo_nomina_ubicacion() {
        return id_tipo_nomina_ubicacion;
    }

    public void setId_tipo_nomina_ubicacion(Integer id_tipo_nomina_ubicacion) {
        this.id_tipo_nomina_ubicacion = id_tipo_nomina_ubicacion;
    }

    public Cat_Tipo_Nomina getCat_Tipo_Nomina() {
        return cat_Tipo_Nomina;
    }

    public void setCat_Tipo_Nomina(Cat_Tipo_Nomina cat_Tipo_Nomina) {
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
    }

    public Cat_Ubicacion getCat_Ubicacion() {
        return cat_Ubicacion;
    }

    public void setCat_Ubicacion(Cat_Ubicacion cat_Ubicacion) {
        this.cat_Ubicacion = cat_Ubicacion;
    }

}
