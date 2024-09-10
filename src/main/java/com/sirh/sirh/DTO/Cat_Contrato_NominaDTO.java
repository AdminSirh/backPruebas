/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

import com.sirh.sirh.entity.Cat_Tipo_Contrato;
import com.sirh.sirh.entity.Cat_Tipo_Nomina;
import java.io.Serializable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author oguerrero23
 */


public class Cat_Contrato_NominaDTO implements Serializable{
   
    private Integer id_tipo_contrato_nomina;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_contrato_id")
    private Cat_Tipo_Contrato cat_Tipo_Contrato;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_nomina_id")
    private Cat_Tipo_Nomina cat_Tipo_Nomina;

    public Cat_Contrato_NominaDTO() {
        super();
    }

    public Cat_Contrato_NominaDTO(Integer id_tipo_contrato_nomina, Cat_Tipo_Contrato cat_Tipo_Contrato, Cat_Tipo_Nomina cat_Tipo_Nomina) {
        this.id_tipo_contrato_nomina = id_tipo_contrato_nomina;
        this.cat_Tipo_Contrato = cat_Tipo_Contrato;
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
    }

    public Integer getId_tipo_contrato_nomina() {
        return id_tipo_contrato_nomina;
    }

    public void setId_tipo_contrato_nomina(Integer id_tipo_contrato_nomina) {
        this.id_tipo_contrato_nomina = id_tipo_contrato_nomina;
    }

    public Cat_Tipo_Contrato getCat_Tipo_Contrato() {
        return cat_Tipo_Contrato;
    }

    public void setCat_Tipo_Contrato(Cat_Tipo_Contrato cat_Tipo_Contrato) {
        this.cat_Tipo_Contrato = cat_Tipo_Contrato;
    }

    

    public Cat_Tipo_Nomina getCat_Tipo_Nomina() {
        return cat_Tipo_Nomina;
    }

    public void setCat_Tipo_Nomina(Cat_Tipo_Nomina cat_Tipo_Nomina) {
        this.cat_Tipo_Nomina = cat_Tipo_Nomina;
    }
    


}
