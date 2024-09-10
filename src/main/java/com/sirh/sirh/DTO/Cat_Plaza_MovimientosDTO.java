package com.sirh.sirh.DTO;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Cat_Plaza_MovimientosDTO implements Serializable{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id_plaza_movimientos;
//
    private String clave_movimiento;
    private String descripcion;

//    public Cat_Plaza_MovimientosDTO(String clave_movimiento, String descripcion) {
//        this.clave_movimiento = clave_movimiento;
//        this.descripcion = descripcion;
//    }
//    public Integer getId_plaza_movimientos() {
//        return id_plaza_movimientos;
//    }
//
//    public void setId_plaza_movimientos(Integer id_plaza_movimientos) {
//        this.id_plaza_movimientos = id_plaza_movimientos;
//    }
//
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

}
