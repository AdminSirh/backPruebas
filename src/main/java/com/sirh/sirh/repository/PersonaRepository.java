/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Persona;
import java.util.List;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jarellano22
 */
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {

    // Verifica que el candidato no este registrado con anterioridad por CURP
    @Query(value = "SELECT * FROM persona p WHERE p.curp = :curp", nativeQuery = true)
    Persona existeCurp(@Param("curp") String curp);

    // ID Obtenemos el ultimo registro de persona
    @Query(value = "SELECT MAX(id_persona) FROM persona", nativeQuery = true)
    Integer ultimoIdPersona();

//    @Query(value = "SELECT * FROM persona where expediente_si_no=0", nativeQuery = true)
    @Query(value = "SELECT * FROM persona where expediente_si_no=2", nativeQuery = true)
    List<Persona> personasSinExpediente();
    
    @Query(value = "SELECT grado_estudios_id FROM persona e WHERE e.id_persona = :id_persona", nativeQuery = true)
    Integer idGrado(@Param ("id_persona")Integer id_persona);

}
