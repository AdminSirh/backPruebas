/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.controller;

/**
 *
 * @author jarellano22
 */
import com.backB.backB.DTO.UsuarioDTO;
import com.backB.backB.DTO.UsuarioGuardarDTO;
import com.backB.backB.DTO.UsuarioPasswordDTO;
import com.backB.backB.entity.Rol;
import com.backB.backB.entity.Usuario;
import com.backB.backB.entity.Usuario_Rol;
import com.backB.backB.service.RolService;
import com.backB.backB.service.UsuarioRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.backB.backB.service.UsuarioService;
import com.backB.backB.util.Response;
import com.backB.backB.util.RolNombre;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import backB.backB.exception.Exceptions;
import backB.backB.exception.OutputEntity;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    UsuarioRolService usuarioRolService;

    @Autowired
    PasswordEncoder passwordEncoder;

    //Busca a todos los usuarios activos y no activos
    //s@SystemControllerLog(operation = "listarUsuarios", type = "obtuvo lista de todos los  usuarios") //Log de quien ejecuta el metodo
    @GetMapping(value = "/listarUsuarios")
    public ResponseEntity<Usuario> listarUsuarios() {
        try {
            List<Usuario> result = usuarioService.findAll();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Guardar datos basicos del Usuario con rol basico 
    //Role User id 1 
    //@SystemControllerLog(operation = "guardarUsuario", type = "guarda el usuario nuevo") //Log de quien ejecuta el metodo
    @PostMapping(value = "/guardarUsuario")
    public ResponseEntity<OutputEntity<String>> guardarUsuarios(@RequestBody UsuarioGuardarDTO usuario) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            if (usuarioService.existsByUsername(usuario.getUsername()) != null) {
                throw new Exceptions(Response.USERNAMEEXISTE.getKey(), Response.USERNAMEEXISTE.getCode());
            }
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).get();
            Set<Rol> roles = new HashSet<>();
            roles.add(rolUser);
            usuario.setRoles(roles);
            usuarioService.save(usuario);

            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Usuario Guardado");
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exceptions e) {
            out.failed(e.getCode(), e.getMessages(), null);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Buscar usuario por ID
    @GetMapping(value = "/buscarUsuario/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Integer id) {
        try {
            Usuario result = usuarioService.findOne(id);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualizar datos de usuario
    //@SystemControllerLog(operation = "actualizarUsuario", type = "actualizo los datos del usuario")
    @PostMapping(value = "/actualizarUsuario/{id}")
    public ResponseEntity<OutputEntity<String>> actualizarUsuario(@RequestBody UsuarioDTO usuario, @PathVariable Integer id) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            usuarioService.update(id, usuario);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Usuario Actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Desactivar Usuario o activar usuario 
    //0 Inactivo 1 Activo
    //@SystemControllerLog(operation = "eliminarUsuario", type = "desactivo al usuario")
    @PostMapping(value = "/eliminarUsuario/{id}/{activo}")
    public ResponseEntity<Usuario> eliminarUsuario(@RequestBody @PathVariable Integer id, @PathVariable Integer activo) {
        try {
            return new ResponseEntity<>(usuarioService.activo(id, activo), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Asignar roles a usuario Buscar ID de Usuario y ID del Catalogo de Roles
    @PostMapping(value = "/asignarRoles")
    public ResponseEntity<Usuario_Rol> asigarRolesUsuario(@RequestBody Usuario_Rol usuario_rol) {
        try {
            return new ResponseEntity<>(usuarioRolService.save(usuario_rol), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //*****************************Paginacion Nayeli***************************************************************************
    //@SystemControllerLog(operation = "paginacion", type = "obtuvo lista de todos los  usuarios con paginacion")
    @GetMapping(value = "/paginacion")
    public ResponseEntity<Page<Usuario>> PaginacionUsuarios(Pageable pageable) {
        try {
            Page<Usuario> page = usuarioService.findPage(pageable);
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //*****************************Estado del usuario activo o inactivo Nayeli***************************************************   

    @PostMapping(value = "/estadoUsuario/{id}/{estatus}")
    public ResponseEntity<Usuario> cambioEstatusGenero(@RequestBody @PathVariable Integer id, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(usuarioService.activo(id, estatus), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //********************************* Modificar Password *******************************************************
    @PostMapping(value = "/actualizarPassword/{id}")
    public ResponseEntity<OutputEntity<String>> actualizarPassword(@RequestBody UsuarioPasswordDTO usuario, @PathVariable Integer id) {
        OutputEntity<String> out = new OutputEntity<>();
        try {

            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioService.actualizaPassword(id, usuario);
            out.success(Response.UPDATE.getCode(), Response.PASSWORDUPDATE.getKey(), "Contraseña Actualizada");

            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //************************ ROLES************************************************
    //************************ GUARDAR ROL NAYELI **********************************
    @PostMapping(value = "/guardarUsuarioRol")
    public ResponseEntity<OutputEntity<String>> guardarRolUsuario(@RequestBody Usuario_Rol usuarioRol) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            usuarioRolService.save(usuarioRol);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Rol Asignado");
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            out.error();
            System.out.println("usuario rol " + usuarioRol.getUsuario_id() + e);
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //************************ BORRAR ROL NAYELI**************************************
    @PostMapping(value = "/eliminarRol/{usuario_id}/{rol_id}")
    public ResponseEntity<OutputEntity<Integer>> eliminarRol(@RequestBody @PathVariable("usuario_id") Integer usuario_id, @PathVariable("rol_id") Integer rol_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            usuarioRolService.delete_Rol(usuario_id, rol_id);
            out.success(Response.DELETED.getCode(), Response.DELETED.getKey(), null);
            return ResponseEntity.ok(out);
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //********************************* Buscar Usuario Rol Nayeli ***********************
    @GetMapping(value = "/buscarUsuarioRol/{id}")
    public ResponseEntity<OutputEntity<Set>> buscarUsuarioRol(@PathVariable Integer id) {
        OutputEntity<Set> out = new OutputEntity<>();
        try {
            Usuario result = usuarioService.findOne(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result.getRoles());
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Actualizar Contraseña del Usuario
    //@SystemControllerLog(operation = "cambiaContrasena", type = "cambio la contraseña")
    //**********************    ACTUALIZAR PASSWORD DE LA SESIÓN INICIADA ***************************************
    @PostMapping(value = "/cambiaContrasena/{password}")
    public ResponseEntity<Usuario> updatepassword(Authentication auth, @RequestBody @PathVariable String password) {
        try {
            String Username = auth.getName();
            Usuario usuario = usuarioService.findByUsuarioSession(Username);
            return new ResponseEntity<>(usuarioService.updatePassword(usuario.getId(), passwordEncoder.encode(password)), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //**************** VERIFICAR PASSWORD DE SESIÓN *******************************
    @GetMapping(value = "/verificarPassword/{password}")
    public ResponseEntity<OutputEntity<Boolean>> validarPasswordUser(Authentication auth, @RequestBody @PathVariable String password) {
        OutputEntity<Boolean> out = new OutputEntity<>();
        try {
            String Username = auth.getName();
            Usuario usuario = usuarioService.findByUsuarioSession(Username);
            //System.out.println("Contraseña Ingresada Por el USUARIO--> " + Username);
            //System.out.println("Contraseña Ingresada Por el USUARIO " + password);
            //System.out.println("COMPARACIÓN DE CONTRASEÑAS " +passwordEncoder.matches(password, usuario.getPassword()));
            out.success(Response.OK.getCode(), Response.OK.getKey(), passwordEncoder.matches(password, usuario.getPassword()));
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
