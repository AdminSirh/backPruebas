/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.DTO;

/**
 *
 * @author rocke
 */
public class InformacionPlazasDTO {
    
    private Integer numero_plaza;
    private Integer id_area;
    private String puesto;
    private Integer estatus_plaza_id;
    private String nombre;
    private String apellido_materno;
    private String apellido_paterno;
    private String numero_expediente;
    
    

    public InformacionPlazasDTO(Integer numero_plaza, Integer id_area, String puesto, Integer estatus_plaza_id, String nombre, String apellido_materno, String apellido_paterno, String numero_expediente) {
        this.numero_plaza = numero_plaza;
        this.id_area = id_area;
        this.puesto = puesto;
        this.estatus_plaza_id = estatus_plaza_id;
        this.nombre = nombre;
        this.apellido_materno = apellido_materno;
        this.apellido_paterno = apellido_paterno;
        this.numero_expediente = numero_expediente;
    }

    public InformacionPlazasDTO() {
    }

    public Integer getNumero_plaza() {
        return numero_plaza;
    }

    public void setNumero_plaza(Integer numero_plaza) {
        this.numero_plaza = numero_plaza;
    }
    

    public Integer getId_area() {
        return id_area;
    }

    public void setId_area(Integer id_area) {
        this.id_area = id_area;
    }
    
    

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Integer getEstatus_plaza_id() {
        return estatus_plaza_id;
    }

    public void setEstatus_plaza_id(Integer estatus_plaza_id) {
        this.estatus_plaza_id = estatus_plaza_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getNumero_expediente() {
        return numero_expediente;
    }

    public void setNumero_expediente(String numero_expediente) {
        this.numero_expediente = numero_expediente;
    }
}
