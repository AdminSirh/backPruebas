/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Tipo_Nomina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oguerrero23
 */
@Repository
public interface Cat_Tipo_NominaRepository extends JpaRepository<Cat_Tipo_Nomina, Integer>{
    @Query(value = "SELECT * FROM catalogo_tipo_nomina WHERE id_tipo_nomina =:id_tipo_nomina", nativeQuery = true)
    Cat_Tipo_Nomina findByIdNomina(@Param("id_tipo_nomina") Integer id_tipo_nomina);
   
}
