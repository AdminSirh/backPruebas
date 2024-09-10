/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author akalvarez19
 */
public interface MedicoRepository extends JpaRepository<Medico, Integer>{
    
    //********* BUSCAR EN TABlA MEDICO POR PERSONA_ID *********
    @Query(value = "SELECT * FROM medico m WHERE m.persona_id = :persona_id", nativeQuery = true)
    Medico findMedico(@Param("persona_id") Integer persona_id);
}

