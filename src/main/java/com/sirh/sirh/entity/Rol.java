/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.entity;

/**
 *
 * @author jarellano22
 */
import com.sirh.sirh.util.RolNombre;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Rol implements Serializable {

    /**
     * Primary key.
     */
    //protected static final String PK = "id";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private RolNombre name;

    public Rol() {
    }

    public Rol(RolNombre name) {
        this.name = name;
    }

    public Rol(int id, RolNombre name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RolNombre getRolNombre() {
        return name;
    }

    public void setRolNombre(RolNombre name) {
        this.name = name;
    }

}
