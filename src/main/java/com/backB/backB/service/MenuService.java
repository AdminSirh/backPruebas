/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.backB.backB.service;

import com.backB.backB.DTO.MenuDTO;
import com.backB.backB.entity.Menu;
import com.backB.backB.entity.Submenu_Rol;
import java.util.List;

/**
 *
 * @author jarellano22
 */
public interface MenuService {

    public List<Menu> findAllMenu();

    public List<Submenu_Rol> findBySubMenuRoles(Integer rol_id);

    public Menu findOne(Integer id);

    public Menu actualizarMenu(Integer id, MenuDTO gMenu);
    
    public Menu save(Menu menu); 
    
    public Menu existsByNombre(String nombre);

}
