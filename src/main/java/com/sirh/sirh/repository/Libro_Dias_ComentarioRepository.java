/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Libro_Dias_Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author rrosero23
 */
public interface Libro_Dias_ComentarioRepository extends JpaRepository<Libro_Dias_Comentario, Integer> {

    @Query(value = "SELECT * FROM libro_dias_comentario_general_trabajador\n"
            + "WHERE trabajador_id = :trabajador_id\n", nativeQuery = true)
    Libro_Dias_Comentario findComentarioGeneralTrabajador(@Param("trabajador_id") Integer trabajador_id);

}
