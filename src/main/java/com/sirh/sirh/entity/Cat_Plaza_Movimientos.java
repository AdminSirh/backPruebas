package com.sirh.sirh.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "catalogo_plaza_movimientos")
public class Cat_Plaza_Movimientos implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plaza_movimientos")
    private Integer id_plaza_movimientos;
    
    private String clave_movimiento;
    private String descripcion;
    private Integer estatus;

    public Cat_Plaza_Movimientos(Integer id_plaza_movimientos, String clave_movimiento, String descripcion, Integer estatus) {
        this.id_plaza_movimientos = id_plaza_movimientos;
        this.clave_movimiento = clave_movimiento;
        this.descripcion = descripcion;
        this.estatus = estatus;
    }
    
    

    public Cat_Plaza_Movimientos() {
        super();
    }

    public Integer getId_plaza_movimientos() {
        return id_plaza_movimientos;
    }

    public void setId_plaza_movimientos(Integer id_plaza_movimientos) {
        this.id_plaza_movimientos = id_plaza_movimientos;
    }

    public String getClave_movimiento() {
        return clave_movimiento;
    }

    public void setClave_movimiento(String clave_movimiento) {
        this.clave_movimiento = clave_movimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
    
    
}
