/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Licencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dguerrero18
 */
@Repository
public interface LicenciaRepository extends JpaRepository<Licencia, Integer> {
    
    @Query(value = "SELECT * FROM licencia l WHERE l.persona_id = :persona_id", nativeQuery = true)
    Licencia findLicencia(@Param("persona_id") Integer persona_id);
}