 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * @author rrosero23
 */
@Entity
@Table(name = "pension_a_beneficiario")
public class Pension_Alimenticia_B implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_beneficiario_pa;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String rfc;
    private String calle;
    private String numero_oficial;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(columnDefinition="integer", name = "colonia_id")
    private Cat_Colonia cat_Colonia;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(columnDefinition="integer", name = "banco_id")
    private Cat_Banco cat_Banco;

    private String cuentabeneficiario;

    public Pension_Alimenticia_B() {
    }

    public Pension_Alimenticia_B(Integer id_beneficiario_pa, String nombre, String apellido_paterno, String apellido_materno, String rfc, String calle, String numero_oficial, Cat_Colonia cat_Colonia, Cat_Banco cat_Banco, String cuentabeneficiario) {
        this.id_beneficiario_pa = id_beneficiario_pa;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.rfc = rfc;
        this.calle = calle;
        this.numero_oficial = numero_oficial;
        this.cat_Colonia = cat_Colonia;
        this.cat_Banco = cat_Banco;
        this.cuentabeneficiario = cuentabeneficiario;
    }

    public Integer getId_beneficiario_pa() {
        return id_beneficiario_pa;
    }

    public void setId_beneficiario_pa(Integer id_beneficiario_pa) {
        this.id_beneficiario_pa = id_beneficiario_pa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero_oficial() {
        return numero_oficial;
    }

    public void setNumero_oficial(String numero_oficial) {
        this.numero_oficial = numero_oficial;
    }

    public Cat_Colonia getCat_Colonia() {
        return cat_Colonia;
    }

    public void setCat_Colonia(Cat_Colonia cat_Colonia) {
        this.cat_Colonia = cat_Colonia;
    }

    public Cat_Banco getCat_Banco() {
        return cat_Banco;
    }

    public void setCat_Banco(Cat_Banco cat_Banco) {
        this.cat_Banco = cat_Banco;
    }

    public String getCuentabeneficiario() {
        return cuentabeneficiario;
    }

    public void setCuentabeneficiario(String cuentabeneficiario) {
        this.cuentabeneficiario = cuentabeneficiario;
    }

}
