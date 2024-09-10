/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Tiempo_Extra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rroscero23
 */
@Repository
public interface Tiempo_ExtraRepository extends JpaRepository<Tiempo_Extra, Integer> {

    @Query(value = "SELECT * \n"
            + "FROM tiempo_extra\n"
            + "WHERE trabajador_id = :trabajadorId\n"
            + "AND periodo_aplicacion_id= :periodoApl\n"
            + "AND periodo_generacion_id = :periodoGen\n"
            + "AND semana_incidencias = :semanaIncidencias\n"
            + "AND estatus = 1", nativeQuery = true)

    Tiempo_Extra findTiempoExtra(
            @Param("trabajadorId") Integer trabajadorId,
            @Param("periodoApl") Integer periodoApl,
            @Param("periodoGen") Integer periodoGen,
            @Param("semanaIncidencias") String semanaIncidencias);

}
