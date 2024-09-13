/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.backB.backB.service;

import com.backB.backB.entity.Rol;
import java.util.Optional;
import com.backB.backB.util.RolNombre;
import java.util.List;

/**
 *
 * @author jarellano22
 */
public interface RolService {

    public void save(Rol rol);

    public Optional<Rol> getByRolNombre(RolNombre name);

    public boolean existsByRolNombre(RolNombre name);

    /*                  LISTAR ROLES                    */
    public List<Rol> findAll();

}
