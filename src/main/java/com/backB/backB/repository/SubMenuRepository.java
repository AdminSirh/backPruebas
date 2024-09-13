/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.backB.backB.repository;

import com.backB.backB.entity.Submenu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jarellano22
 */
@Repository
public interface SubMenuRepository extends JpaRepository<Submenu, Integer> {

    @Query(value = "SELECT * FROM submenu sm WHERE sm.menu_id = :menu_id and sm.activo=1", nativeQuery = true)
    List<Submenu> findAllSubMenu(@Param("menu_id") Integer menu_id);

    @Query(value = "SELECT * FROM submenu sm WHERE sm.menu_id = :menu_id", nativeQuery = true)
    List<Submenu> findAll(@Param("menu_id") Integer menu_id);

    @Query(value = "SELECT * FROM submenu sm WHERE sm.nombre = :nombre", nativeQuery = true)
    Submenu existsByNombre(@Param("nombre") String nombre);
}
