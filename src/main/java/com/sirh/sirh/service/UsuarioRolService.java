/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.entity.Usuario_Rol;
import java.util.Optional;

/**
 *
 * @author jarellano22
 */
public interface UsuarioRolService {

    public Usuario_Rol save(Usuario_Rol usuario_rol);

    public Usuario_Rol findOne(Integer usuario_id);

    public void deleteById(Integer id_rol);

    public Optional<Usuario_Rol> findByUsuarioId(Integer usuario_id);

    public Usuario_Rol findByUsuario_Id(Integer usuario_id);

    public void delete_Rol(Integer usuario_id, Integer rol_id);
}
