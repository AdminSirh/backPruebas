/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

/**
 *
 * @author jarellano22
 */
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "submenu_roles")
public class Submenu_Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @ManyToOne
    @JoinColumn(name = "submenu_id")
    Submenu submenu_id;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    Rol rol_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Submenu getSubmenu() {
        return submenu_id;
    }

    public void setSubmenu(Submenu submenu_id) {
        this.submenu_id = submenu_id;
    }

    public Rol getRol() {
        return rol_id;
    }

    public void setRol(Rol rol_id) {
        this.rol_id = rol_id;
    }

}
