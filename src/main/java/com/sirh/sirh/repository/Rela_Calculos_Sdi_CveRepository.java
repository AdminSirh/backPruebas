/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Rela_Calculos_Sdi_Cve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rroscero23
 */
@Repository
public interface Rela_Calculos_Sdi_CveRepository extends JpaRepository<Rela_Calculos_Sdi_Cve, Integer> {

    @Query(value = "SELECT *\n"
            + "FROM relacion_calculos_sdi_claves\n"
            + "WHERE valor_campo = :valorCampo", nativeQuery = true)
    Rela_Calculos_Sdi_Cve findRelacionCalculoIncidencia(@Param("valorCampo") String valorCampo);

}
