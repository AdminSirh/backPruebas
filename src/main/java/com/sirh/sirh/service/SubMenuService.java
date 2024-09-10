/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.SubMenuDTO;
import com.sirh.sirh.entity.Submenu;
import java.util.List;

/**
 *
 * @author jarellano22
 */
public interface SubMenuService {

    public List<Submenu> findAllSubMenu(Integer menu_id);
    
    public List<Submenu> findAll(Integer menu_id);

    public Submenu findOne(Integer id);

    public Submenu actualizarSubmenu(Integer id, SubMenuDTO Submenu);

    public Submenu activo(Integer id, Integer activo);

    public Submenu save(Submenu menu);

    public Submenu existsByNombre(String nombre);
}
