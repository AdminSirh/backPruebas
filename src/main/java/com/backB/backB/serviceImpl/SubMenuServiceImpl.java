/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.serviceImpl;

import com.backB.backB.DTO.SubMenuDTO;
import com.backB.backB.entity.Submenu;
import com.backB.backB.repository.SubMenuRepository;
import com.backB.backB.service.SubMenuService;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jarellano22
 */
@Service
@Transactional
public class SubMenuServiceImpl implements SubMenuService {

    @Autowired
    private SubMenuRepository subMenuRepository;

    @Override
    public List<Submenu> findAllSubMenu(Integer menu_id) {
        return subMenuRepository.findAllSubMenu(menu_id);
    }

    @Override
    public List<Submenu> findAll(Integer menu_id) {
        return subMenuRepository.findAll(menu_id);
    }

    @Override
    public Submenu findOne(Integer id) {
        return subMenuRepository.findById(id).get();
    }

    @Override
    public Submenu activo(Integer id, Integer activo) {
        Submenu submenu = subMenuRepository.findById(id).get();
        submenu.setActivo(activo);
        return subMenuRepository.save(submenu);
    }

    @Override
    public Submenu actualizarSubmenu(Integer id, SubMenuDTO Submenu) {
        Submenu smenur = subMenuRepository.findById(id).get();
        Submenu smenus = smenur;
        smenus.setSubmenuNombre(Submenu.getSubmenuNombre());
        smenus.setDescripcion(Submenu.getDescripcion());
        return subMenuRepository.save(smenus);
    }

    @Override
    public Submenu save(Submenu menu) {
        return subMenuRepository.save(menu);
    }

    @Override
    public Submenu existsByNombre(String nombre) {
        return subMenuRepository.existsByNombre(nombre);
    }
}
