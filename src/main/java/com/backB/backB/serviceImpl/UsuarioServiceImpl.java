/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.serviceImpl;

import com.backB.backB.DTO.UsuarioDTO;
import com.backB.backB.DTO.UsuarioGuardarDTO;
import com.backB.backB.DTO.UsuarioPasswordDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backB.backB.entity.Usuario;
import com.backB.backB.repository.UsuarioRepository;
import com.backB.backB.service.UsuarioService;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author jarellano22
 */
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario save(UsuarioGuardarDTO usuario) {

        Usuario user = new Usuario();

        user.setNombre(usuario.getNombre());
        user.setAp_paterno(usuario.getAp_paterno());
        user.setAp_materno(usuario.getAp_materno());
        user.setUsername(usuario.getUsername());
        user.setPassword(usuario.getPassword());
        user.setActivo(1);
        user.setTrabajador_id(usuario.getTrabajador_id());
        user.setRoles(usuario.getRoles());

        return usuarioRepository.save(user);
    }

    @Override
    public Usuario findOne(Integer id) {
        return usuarioRepository.findById(id).get();
    }

    @Override
    public Usuario update(Integer id, UsuarioDTO usuario) {
        Usuario user = this.usuarioRepository.findById(id).get();
        Usuario usuarios = user;
        usuarios.setNombre(usuario.getNombre());
        usuarios.setAp_paterno(usuario.getAp_paterno());
        usuarios.setAp_materno(usuario.getAp_materno());
        usuarios.setUsername(usuario.getUsername());
        return usuarioRepository.save(usuarios);

    }

    @Override
    public Usuario activo(Integer id, Integer activo) {
        Usuario user = usuarioRepository.findById(id).get();
        user.setActivo(activo);

        return usuarioRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> getByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public boolean existsById(int id) {
        return usuarioRepository.existsById(id);
    }

    @Override
    public Usuario existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    @Override
    public Usuario updatePassword(Integer id, String password) {
        Usuario user = this.usuarioRepository.findById(id).get();
        Usuario usuarios = user;
        usuarios.setPassword(password);
        return usuarioRepository.save(usuarios);

    }

    @Override
    public Usuario findByUsuarioSession(String username) {
        return usuarioRepository.findByUsuarioSession(username);
    }

    @Override
    public Page<Usuario> findPage(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Usuario actualizaPassword(Integer id, UsuarioPasswordDTO usuario) {
        Usuario user = this.usuarioRepository.findById(id).get();
        Usuario usuarios = user;
        usuarios.setPassword(usuario.getPassword());
        return usuarioRepository.save(usuarios);
    }

}
