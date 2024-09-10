/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Tipo_Incidencia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author akalvarez19
 */
public interface Cat_Tipo_IncidenciaRepository extends JpaRepository<Cat_Tipo_Incidencia, Integer>  {
    
    @Query(value = "SELECT * " 
        +"FROM catalogo_tipo_incidencia  "
        + "WHERE id_tipo_incidencia = :tipo_incidencia_id and estatus = 1" , nativeQuery = true)
    List<Cat_Tipo_Incidencia> findTipoID(@Param("tipo_incidencia_id") Integer tipo_incidencia_id);
 
}
    

