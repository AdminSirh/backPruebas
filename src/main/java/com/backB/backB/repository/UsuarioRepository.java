/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.backB.backB.repository;

/**
 *
 * @author jarellano22
 */
import com.backB.backB.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jarellano22
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Tipo optional esta consulta es para verificar que el usuario exista y este activo
    @Query(value = "SELECT * FROM usuarios u WHERE u.username = :username and u.activo=1", nativeQuery = true)
    public Optional<Usuario> findByUsername(@Param("username") String username);

    // Tipo boolean esta consulta es para verificar que el usuario exista y este activo
    @Query(value = "SELECT * FROM usuarios u WHERE u.username = :username and u.activo=1", nativeQuery = true)
    Usuario existsByUsername(@Param("username") String username);

     // Tipo Usuario esta consulta es para verificar la session activa
    @Query(value = "SELECT * FROM usuarios u WHERE u.username = :username and u.activo=1", nativeQuery = true)
    Usuario findByUsuarioSession(@Param("username") String username);

    //Page<Usuario> findByUsername(Pageable pageable);
}
