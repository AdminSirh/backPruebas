/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Trabajador_Firma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author oguerrero23
 */
@Repository
public interface Trabajador_FirmaRepository extends JpaRepository<Trabajador_Firma, Integer>{
    @Query(value = "SELECT * FROM trabajador_firma tf WHERE tf.trabajador_id = :trabajador_id and tf.estatus=1", nativeQuery = true)
    Trabajador_Firma existsFirma(@Param("trabajador_id") Integer trabajador_id);
    
    
}
