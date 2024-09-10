/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.OperadorTitularDTO;
import com.sirh.sirh.DTO.RelevosDTO;
import com.sirh.sirh.entity.Historico_Tmp_Libro_RA15;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rroscero23
 */
@Repository
public interface Historico_Tmp_Libro_RA15Repository extends JpaRepository<Historico_Tmp_Libro_RA15, Integer> {

    @Modifying
    @Query(value = "UPDATE historico_tmp_libro_ra_15\n"
            + "SET estatus = 0 \n"
            + "WHERE anio < :anio\n"
            + "AND expediente = :expediente\n"
            + "AND estatus = 1", nativeQuery = true)
    void updateEstatusRegistrosAnteriores(@Param("anio") Integer anio, @Param("expediente") Integer expediente);

    @Query(value = "SELECT * FROM historico_tmp_libro_ra_15 WHERE estatus = 1\n"
            + "ORDER BY expediente ASC", nativeQuery = true)
    List<Historico_Tmp_Libro_RA15> findAllPlantasActivas();

    @Query("SELECT new com.sirh.sirh.DTO.RelevosDTO(r.id_planta, r.relevo_1, r.relevo_2, r.descanso_1, r.descanso_2) "
            + "FROM Historico_Tmp_Libro_RA15 r "
            + "GROUP BY r.id_planta, r.relevo_1, r.relevo_2, r.descanso_1, r.descanso_2 "
            + "ORDER BY r.relevo_1, r.relevo_2")
    List<RelevosDTO> findRelevos();

    @Query("SELECT new com.sirh.sirh.DTO.OperadorTitularDTO(\n"
            + "ra15.expediente, \n"
            + "ra15.descansos, \n"
            + "ra15.entra_semana, \n"
            + "ra15.sale_semana, \n"
            + "ra15.horas_trab_semana, \n"
            + "ra15.pago_semana, \n"
            + "ra15.entra_sabado, \n"
            + "ra15.sale_sabado, \n"
            + "ra15.horas_trab_sabado, \n"
            + "ra15.pago_sabado, \n"
            + "ra15.entra_domingo, \n"
            + "ra15.sale_domingo, \n"
            + "ra15.horas_trab_domingo, \n"
            + "ra15.pago_domingo) \n"
            + "FROM Historico_Tmp_Libro_RA15 AS ra15\n"
            + "WHERE estatus = 1")
    List<OperadorTitularDTO> findActiveOpTitular();

    @Query(value = "SELECT EXISTS (SELECT 1 FROM historico_tmp_libro_ra_15 WHERE anio = :anio AND estatus = 1)", nativeQuery = true)
    Boolean existsByAnioAndEstatusTmpHistorico(Integer anio);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM historico_tmp_libro_tren_ra_15 WHERE anio = :anio AND estatus = 1)", nativeQuery = true)
    Boolean existsByAnioAndEstatusTmpHistoricoTren(Integer anio);

    @Query(value = "SELECT * FROM historico_tmp_libro_ra_15 WHERE (relevo_1 = :expediente AND descanso_1 = :dia) OR (relevo_2 = :expediente AND descanso_2 = :dia)", nativeQuery = true)
    Historico_Tmp_Libro_RA15 findHorasTrabajadas(@Param("expediente") Integer expediente, @Param("dia") Integer dia);

    @Query(value = "SELECT \n"
            + "linea \n"
            + "FROM historico_tmp_libro_ra_15\n"
            + "WHERE estatus = 1\n"
            + "AND expediente = :expediente", nativeQuery = true)
    String findLineaByExpediete(Integer expediente);
}
