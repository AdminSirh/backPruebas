/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Tipo_Ayuda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author akalvarez19
 */

public interface Cat_Tipo_AyudaRepository extends JpaRepository<Cat_Tipo_Ayuda, Integer>{
    
    @Query(value = "SELECT * " 
        +"FROM catalogo_tipo_ayuda  "
        + "WHERE id_tipo_ayuda = :id_tipo_ayuda and estatus = 1" , nativeQuery = true)
    List<Cat_Tipo_Ayuda> findAyudaID(@Param("id_tipo_ayuda") Integer id_tipo_ayuda);
 
}
