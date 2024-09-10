/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Registro_Plazas_Consolidado;
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
public interface Registro_Plazas_ConsolidadoRepository extends JpaRepository<Registro_Plazas_Consolidado, Integer> {

    @Query(value = "SELECT COUNT(rpc.puesto_id)\n"
            + "FROM registro_plazas_consolidado rpc, tmp_consolidado tc\n"
            + "WHERE rpc.TIPO = tc.TIPO\n"
            + "AND rpc.puesto_id = tc.puesto_id\n"
            + "AND rpc.NIVEL = tc.NIVEL\n"
            + "AND rpc.estatus = 1\n"
            + "AND rpc.plazas_autorizadas != tc.numero_plazas_autorizadas", nativeQuery = true)
    Long obtenerNumeroCambiosConsolidado();

    @Modifying
    @Transactional
    @Query(value = "UPDATE registro_plazas_consolidado "
            + "SET estatus = 2 "
            + "FROM registro_plazas_consolidado rpc, tmp_consolidado tc "
            + "WHERE registro_plazas_consolidado.tipo = tc.tipo "
            + "AND registro_plazas_consolidado.puesto_id = tc.puesto_id "
            + "AND registro_plazas_consolidado.nivel = tc.nivel "
            + "AND registro_plazas_consolidado.estatus = 1 "
            + "AND registro_plazas_consolidado.plazas_autorizadas != tc.numero_plazas_autorizadas",
            nativeQuery = true)
    void actualizarEstatusRegistroConsolidado();

}
