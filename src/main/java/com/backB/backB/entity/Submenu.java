/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.entity;

/**
 *
 * @author jarellano22
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "submenu")
public class Submenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    @NotEmpty
    @Column(name = "nombre")
    private String submenuNombre;
    @Column(name = "descripcion")
    private String descripcion;

    private Integer activo;
    private Integer orden;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "submenu_roles",
            joinColumns = @JoinColumn(name = "submenu_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )

    private Set<Rol> roles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "menu_id")
    // @Column(name = "menu_id")
    private Menu menu;

    public Submenu() {
    }

    public Submenu(Integer id, String submenuNombre, String descripcion, Menu menu, Integer activo, Integer orden) {
        this.id = id;
        this.submenuNombre = submenuNombre;
        this.descripcion = descripcion;
        this.menu = menu;
        this.activo = activo;
        this.orden = orden;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubmenuNombre() {
        return submenuNombre;
    }

    public void setSubmenuNombre(String submenuNombre) {
        this.submenuNombre = submenuNombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

}
