package com.sirh.sirh.DTO;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Cat_CargoDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cargo;

    private String cargo_admon_pub;

    private Integer estatus;
    
    public Integer getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(Integer id_cargo) {
        this.id_cargo = id_cargo;
    }

    public String getCargo_admon_pub() {
        return cargo_admon_pub;
    }

    public void setCargo_admon_pub(String cargo_admon_pub) {
        this.cargo_admon_pub = cargo_admon_pub;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
    

}
