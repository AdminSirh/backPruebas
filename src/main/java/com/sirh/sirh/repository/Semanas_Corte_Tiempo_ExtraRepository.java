/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Semanas_Corte_Tiempo_Extra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author rroscero23
 */
public interface Semanas_Corte_Tiempo_ExtraRepository extends JpaRepository<Semanas_Corte_Tiempo_Extra, Integer> {

    @Query(value = "SELECT EXISTS ("
            + "    SELECT 1 "
            + "    FROM semanas_corte_tiempo_extra "
            + "    WHERE anio = EXTRACT(YEAR FROM CURRENT_DATE)"
            + ")", nativeQuery = true)
    Boolean existeSemanasCorteAnioActual();

}
