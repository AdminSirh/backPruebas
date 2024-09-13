/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.backB.backB.repository;

import com.backB.backB.entity.Submenu_Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jarellano22
 */
@Repository
public interface SubMenuRolRepository extends JpaRepository<Submenu_Rol, Integer> {

    //@Query(value = "SELECT * FROM submenu_roles", nativeQuery = true)
    //List<Submenu_Rol> findByMenu();
    //@Query("SELECT u FROM Submenu_Rol where u.rol_id=:rol_id")
    @Query(value = "SELECT * FROM submenu_roles smr join submenu ON submenu.id=smr.submenu_id WHERE smr.rol_id = :rol_id and submenu.activo=1 order by submenu.orden ASC", nativeQuery = true)
    List<Submenu_Rol> findBySubMenuRoles(@Param("rol_id") Integer rol_id);

    @Query(value = "SELECT * FROM submenu_roles smr  WHERE smr.submenu_id = :submenu_id", nativeQuery = true)
    Submenu_Rol findBySubMenuid(@Param("submenu_id") Integer submenu_id);

    @Modifying
    @Query(value = "DELETE FROM submenu_roles smr WHERE smr.submenu_id = :submenu_id and smr.rol_id = :rol_id", nativeQuery = true)
    void deleteRol(@Param("submenu_id") Integer submenu_id, @Param("rol_id") Integer rol_id);

}
