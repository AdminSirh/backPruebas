/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.entity.Submenu_Rol;
import java.util.Optional;

/**
 *
 * @author dguerrero18
 */
public interface SubMenuRolService {

    public Submenu_Rol save(Submenu_Rol submenu_rol);

    public Submenu_Rol findOne(Integer submenu_id);

    public void deleteById(Integer id_rol);

    public Optional<Submenu_Rol> findBySubmenuId(Integer submenu_id);

    public Submenu_Rol findBySubMenuRoles(Integer submenu_id);

    public void delete_Rol(Integer submenu_id, Integer rol_id);

    public Submenu_Rol findBySubMenuid(Integer submenu_id);
}
