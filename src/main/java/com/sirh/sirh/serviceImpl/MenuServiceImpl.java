/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.MenuDTO;
import com.sirh.sirh.entity.Menu;

import com.sirh.sirh.entity.Submenu_Rol;
import com.sirh.sirh.repository.MenuRepository;
import com.sirh.sirh.repository.SubMenuRolRepository;
import com.sirh.sirh.service.MenuService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

/**
 *
 * @author jarellano22
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private SubMenuRolRepository subMenuRolRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Menu> findAllMenu() {
        return menuRepository.findAll().stream().map(menu -> modelMapper.map(menu, Menu.class)).collect(Collectors.toList());
    }

    @Override
    public List<Submenu_Rol> findBySubMenuRoles(Integer rol_id) {
        return subMenuRolRepository.findBySubMenuRoles(rol_id);
    }

    @Override
    public Menu findOne(Integer id) {
        return menuRepository.findById(id).get();
    }

    @Override
    public Menu actualizarMenu(Integer id, MenuDTO menu) {
        Menu menur = menuRepository.findById(id).get();
        Menu menus = menur;
        menus.setMenuNombre(menu.getMenuNombre());
        menus.setDescripcion(menu.getDescripcion());
        menus.setOrden(menu.getOrden());
        return menuRepository.save(menus);
    }

    @Override
    public Menu save(Menu menu) {
        Menu menus = new Menu();
        menus.setMenuNombre(menu.getMenuNombre());
        menus.setDescripcion(menu.getDescripcion());
        menus.setOrden(menu.getOrden());
        return menuRepository.save(menus);
    }

    @Override
    public Menu existsByNombre(String nombre) {
        return menuRepository.existsByNombre(nombre);
    }
    

}
