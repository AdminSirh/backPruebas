/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.entity.Rol;
import com.sirh.sirh.repository.RolRepository;
import com.sirh.sirh.service.RolService;
import com.sirh.sirh.util.RolNombre;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jarellano22
 */
@Service
@Transactional
public class RolServiceImpl implements RolService {

    @Autowired
    RolRepository rolRepository;

    @Override
    public void save(Rol rol) {
        rolRepository.save(rol);
    }

    @Override
    public Optional<Rol> getByRolNombre(RolNombre name) {
        return rolRepository.findByName(name);
    }

    @Override
    public boolean existsByRolNombre(RolNombre name) {
        return rolRepository.existsByName(name);
    }

    @Override
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }
}
