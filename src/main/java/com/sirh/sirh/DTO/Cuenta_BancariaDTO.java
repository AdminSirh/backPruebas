package com.sirh.sirh.DTO;

/**
 *
 * @author oguerrero23
 */
public class Cuenta_BancariaDTO {
   private Integer id_cuenta_bancaria;
   private String cuenta_bancaria;
   private Integer banco_id;
   private Integer trabajador_id;

    public Cuenta_BancariaDTO() {
        super();
    }

    public Cuenta_BancariaDTO(Integer id_cuenta_bancaria, String cuenta_bancaria, Integer banco_id, Integer trabajador_id) {
        this.id_cuenta_bancaria = id_cuenta_bancaria;
        this.cuenta_bancaria = cuenta_bancaria;
        this.banco_id = banco_id;
        this.trabajador_id = trabajador_id;
    }

    public Integer getId_cuenta_bancaria() {
        return id_cuenta_bancaria;
    }

    public String getCuenta_bancaria() {
        return cuenta_bancaria;
    }

    public Integer getBanco_id() {
        return banco_id;
    }

    public Integer getTrabajador_id() {
        return trabajador_id;
    }

    public void setId_cuenta_bancaria(Integer id_cuenta_bancaria) {
        this.id_cuenta_bancaria = id_cuenta_bancaria;
    }

    public void setCuenta_bancaria(String cuenta_bancaria) {
        this.cuenta_bancaria = cuenta_bancaria;
    }

    public void setBanco_id(Integer banco_id) {
        this.banco_id = banco_id;
    }

    public void setTrabajador_id(Integer trabajador_id) {
        this.trabajador_id = trabajador_id;
    }

    
}
