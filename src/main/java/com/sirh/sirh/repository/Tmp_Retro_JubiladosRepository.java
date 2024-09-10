/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Tmp_Retro_Jubilados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rroscero23
 */
@Repository
public interface Tmp_Retro_JubiladosRepository extends JpaRepository<Tmp_Retro_Jubilados, Integer> {

    @Query(value = "SELECT EXISTS (\n"
            + "    SELECT 1\n"
            + "    FROM tmp_retro_jubilados\n"
            + "    WHERE anio = EXTRACT(YEAR FROM CURRENT_DATE)\n"
            + ") AS existe_registro_anio_actual;", nativeQuery = true)
    Boolean seProcesoAnioActual();

}
