/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Tmp_Consolidado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rroscero23
 */
@Repository
public interface Tmp_ConsolidadoRepository extends JpaRepository<Tmp_Consolidado, Integer> {

    @Query(value = "SELECT COUNT(tc.puesto_id)\n"
            + "FROM tmp_consolidado tc\n"
            + "WHERE tc.puesto_id NOT IN (\n"
            + "    SELECT puesto_id\n"
            + "    FROM registro_plazas_consolidado\n"
            + "    WHERE estatus = 1\n"
            + ")", nativeQuery = true)
    Long contarPuestosNoConsolidados();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO registro_plazas_consolidado (anio, mes, tipo, puesto_id, nivel, plazas_autorizadas, fecha_movimiento, estatus)\n"
            + "SELECT tc.anio, tc.mes, tc.tipo, tc.puesto_id, tc.nivel, tc.numero_plazas_autorizadas, tc.fecha_movimiento, tc.estatus\n"
            + "FROM tmp_consolidado tc\n"
            + "WHERE tc.puesto_id NOT IN (\n"
            + "    SELECT puesto_id\n"
            + "    FROM registro_plazas_consolidado\n"
            + "    WHERE estatus = 1\n"
            + ")",
            nativeQuery = true)
    void adicionaCategoriasNuevaCreacionoModificadas();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tmp_consolidado;", nativeQuery = true)
    void eliminaRegistrosTemporales();

}
