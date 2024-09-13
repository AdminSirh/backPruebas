/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.serviceImpl;

import com.backB.backB.entity.Usuario_Rol;
import com.backB.backB.repository.UsuarioRolRepository;
import com.backB.backB.service.UsuarioRolService;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jarellano22
 */
@Service
@Transactional
public class UsuarioRolServiceImpl implements UsuarioRolService {

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    @Override
    public Usuario_Rol save(Usuario_Rol usuario_rol) {
        return usuarioRolRepository.save(usuario_rol);
    }
    
      @Override
    public Usuario_Rol findOne(Integer usuario_id) {
        return usuarioRolRepository.findById(usuario_id).get();
    }

    @Override
    public void deleteById(Integer id_rol) {
       usuarioRolRepository.deleteById(id_rol);
    }

    @Override
    public Optional<Usuario_Rol> findByUsuarioId(Integer usuario_id) {
      return usuarioRolRepository.findById(usuario_id);
    }

    @Override
    public Usuario_Rol findByUsuario_Id(Integer usuario_id) {
       return usuarioRolRepository.findByUsuario_Id(usuario_id);
    }

    @Override
    public void delete_Rol(Integer usuario_id, Integer rol_id) {
     usuarioRolRepository.deleteRol(usuario_id, rol_id);
    }

}
