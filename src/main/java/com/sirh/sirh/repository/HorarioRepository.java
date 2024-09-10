/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Horario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author akalvarez19
 */
public interface HorarioRepository extends JpaRepository<Horario, Integer>{
    
    @Query(value = "SELECT * FROM libro WHERE trabajador_id =:trabajador_id", nativeQuery = true)
    List<Horario> findHorario(@Param("trabajador_id") Integer trabajador_id);
    
    //************ BUSCAR EN LA TABLA BENEFICIARIO POR TRABAJADOR_ID **************
    @Query(value = "SELECT * FROM libro WHERE trabajador_id =:trabajador_id", nativeQuery = true)
    Horario findHorarioTrabajador(@Param("trabajador_id") Integer trabajador_id);
    
    /*@Query(value = "SELECT * FROM horario WHERE id_horario =:id_horario", nativeQuery = true)
    Horario editarHorario(@Param("id_horario") Integer id_horario);*/
}
