/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Incidencias;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author akalvarez19
 */
public interface Cat_IncidenciasRepository extends JpaRepository<Cat_Incidencias, Integer> {

    //************ BUSCAR EN INCIDENDENCIA POR TIPO DE INCIDENCIA **************
    @Query(value = "SELECT * "
            + "FROM catalogo_incidencias  "
            + "WHERE tipo_incidencia_id = :tipo_incidencia_id and estatus = 1", nativeQuery = true)
    List<Cat_Incidencias> findIncID(@Param("tipo_incidencia_id") Integer tipo_incidencia_id);

    //********* BUSCAR EN TABLA Incidencias tipo_Incidencia_id  *********
    @Query(value = "SELECT * FROM catalogo_incidencias ci WHERE ci.tipo_Incidencia_id = :tipo_Incidencia_id", nativeQuery = true)
    List<Cat_Incidencias> findIncidencia(@Param("tipo_Incidencia_id") Integer tipo_Incidencia_id);

    @Query(value = "SELECT * FROM catalogo_incidencias WHERE estatus_kardex = 1 ORDER BY id_incidencia ASC", nativeQuery = true)
    List<Cat_Incidencias> findIncidenciasKardex();

    @Query(value = "SELECT * FROM catalogo_incidencias WHERE tipo_incidencia_id = 1  AND cve_nomina IS NOT NULL ORDER BY id_incidencia ASC", nativeQuery = true)
    List<Cat_Incidencias> findAllDeducciones();

}
