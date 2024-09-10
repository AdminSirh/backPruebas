/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.entity.Submenu_Rol;
import com.sirh.sirh.repository.SubMenuRolRepository;
import com.sirh.sirh.service.SubMenuRolService;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dguerrero18
 */
@Service
@Transactional
public class SubMenuRolServiceImpl implements SubMenuRolService {

    @Autowired
    private SubMenuRolRepository subMenuRolRepository;

    @Override
    public Submenu_Rol save(Submenu_Rol submenu_rol) {
        return subMenuRolRepository.save(submenu_rol);
    }

    @Override
    public Submenu_Rol findOne(Integer submenu_id) {
        return subMenuRolRepository.findById(submenu_id).get();
    }

    @Override
    public Optional<Submenu_Rol> findBySubmenuId(Integer submenu_id) {
        return subMenuRolRepository.findById(submenu_id);
    }

    @Override
    public void deleteById(Integer id_rol) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Submenu_Rol findBySubMenuRoles(Integer submenu_id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete_Rol(Integer submenu_id, Integer rol_id) {
        subMenuRolRepository.deleteRol(submenu_id, rol_id);
    }

    @Override
    public Submenu_Rol findBySubMenuid(Integer submenu_id) {
        return subMenuRolRepository.findById(submenu_id).get();
    }

}
