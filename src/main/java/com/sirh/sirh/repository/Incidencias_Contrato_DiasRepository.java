/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Incidencias_Contrato_Dias;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oguerrero23
 */
@Repository
public interface Incidencias_Contrato_DiasRepository extends JpaRepository <Incidencias_Contrato_Dias, Integer>{
    
    @Query(value = "SELECT * FROM incidencias_contrato_dias c WHERE c.contrato_id = :contrato_id", nativeQuery = true)
    List<Incidencias_Contrato_Dias> findIncidenciaContrato(@Param("contrato_id") Integer contrato_id);
    
    @Query(value = "SELECT dias FROM incidencias_contrato_dias c WHERE c.contrato_id = :contrato_id and c.incidencias_id = :incidencias_id", nativeQuery = true)
    Integer findDiasInciencia(@Param("contrato_id") Integer contrato_id , @Param("incidencias_id") Integer incidencias_id);
    
}
