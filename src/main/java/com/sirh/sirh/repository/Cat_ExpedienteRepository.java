/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Expediente;
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
public interface Cat_ExpedienteRepository extends JpaRepository<Cat_Expediente, Integer> {

    //@Query(value = "SELECT * FROM catalogo_expedientes e WHERE e.asignado = 0 and e.estatus=1", nativeQuery = true)
    //List<Cat_Expediente> findAllLibres();
    
    @Query(value = "SELECT * FROM catalogo_expedientes e WHERE e.asignado = 0 and e.estatus=1 order by numero_expediente desc", nativeQuery = true)
    List<Cat_Expediente> findAllLibres();

    // ******************* Verificar si ya existe el numero_expediente ingresado ********************************************
    @Query(value = "SELECT * FROM catalogo_expedientes e WHERE e.numero_expediente = :numero_expediente and e.estatus=1", nativeQuery = true)
    Cat_Expediente existsByNumExpediente(@Param("numero_expediente") String numero_expediente);
    
    @Query(value = "SELECT id_expediente FROM catalogo_expedientes e WHERE e.numero_expediente = :numero_expediente", nativeQuery = true)
    Integer idExpediente(@Param("numero_expediente") String numero_expediente);
    
    @Query(value = "SELECT * FROM catalogo_expedientes e ORDER BY e.id_expediente DESC LIMIT 1", nativeQuery = true)
    Cat_Expediente ultimoExpediente();
    
    @Query(value = "SELECT id_expediente from catalogo_expedientes e ORDER BY e.id_expediente DESC LIMIT 1", nativeQuery = true)
    Integer ultimoIdExpediente();
    
}
