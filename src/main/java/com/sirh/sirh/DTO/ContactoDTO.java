/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author nreyes22
 */
public class ContactoDTO {
    
    private String telefono_particular;
    private String telefono_celular;
    private String mail;
    private String nombre_emergencias;
    private String telefono_emergencias;
    private Integer persona_id;

    public ContactoDTO(String telefono_particular, String telefono_celular, String mail, String nombre_emergencias, String telefono_emergencias, Integer persona_id) {
        this.telefono_particular = telefono_particular;
        this.telefono_celular = telefono_celular;
        this.mail = mail;
        this.nombre_emergencias = nombre_emergencias;
        this.telefono_emergencias = telefono_emergencias;
        this.persona_id = persona_id;
    }

    public ContactoDTO() {
    }

    
    
    
    public String getTelefono_particular() {
        return telefono_particular;
    }

    public void setTelefono_particular(String telefono_particular) {
        this.telefono_particular = telefono_particular;
    }

    public String getTelefono_celular() {
        return telefono_celular;
    }

    public void setTelefono_celular(String telefono_celular) {
        this.telefono_celular = telefono_celular;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre_emergencias() {
        return nombre_emergencias;
    }

    public void setNombre_emergencias(String nombre_emergencias) {
        this.nombre_emergencias = nombre_emergencias;
    }

    public String getTelefono_emergencias() {
        return telefono_emergencias;
    }

    public void setTelefono_emergencias(String telefono_emergencias) {
        this.telefono_emergencias = telefono_emergencias;
    }

    public Integer getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(Integer persona_id) {
        this.persona_id = persona_id;
    }
    
    
}
