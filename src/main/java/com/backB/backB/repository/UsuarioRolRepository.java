/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.backB.backB.repository;

/**
 *
 * @author jarellano22
 */
import com.backB.backB.entity.Usuario_Rol;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface UsuarioRolRepository extends JpaRepository<Usuario_Rol, Integer> {

    @Query(value = "SELECT * FROM usuarios_roles u WHERE u.usuario_id = :usuario_id ", nativeQuery = true)
    Usuario_Rol findByUsuario_Id(@Param("usuario_id") Integer usuario_id);

    //************************** ELIMINAR ROL DE USUARIO ************************
    @Modifying
    @Query(value = "DELETE FROM usuarios_roles u WHERE u.usuario_id = :usuario_id and u.rol_id= :rol_id", nativeQuery = true)
    void deleteRol(@Param("usuario_id") Integer usuario_id, Integer rol_id);
}
