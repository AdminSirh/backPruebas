/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.OperadorTrenDTO;
import com.sirh.sirh.entity.Historico_Tmp_Libro_Tren_RA15;
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
public interface Historico_Tmp_Libro_Tren_RA15Repository extends JpaRepository<Historico_Tmp_Libro_Tren_RA15, Integer> {

    @Modifying
    @Query(value = "UPDATE historico_tmp_libro_tren_ra_15\n"
            + "SET estatus = 0 \n"
            + "WHERE anio < :anio\n"
            + "AND expediente = :expediente\n"
            + "AND estatus = 1", nativeQuery = true)
    void updateEstatusRegistrosAnterioresTrenLigero(@Param("anio") Integer anio, @Param("expediente") Integer expediente);

    @Query(value = "SELECT * FROM historico_tmp_libro_tren_ra_15 WHERE estatus = 1", nativeQuery = true)
    List<Historico_Tmp_Libro_Tren_RA15> findAllPlantasActivasTrenLigero();

    @Query(value
            = "SELECT new com.sirh.sirh.DTO.OperadorTrenDTO(\n"
            + "expediente, \n"
            + "descansos, \n"
            + "SUM(CASE WHEN dia_id = 1 THEN pago ELSE 0 END) AS pagoDomingo, \n"
            + "SUM(CASE WHEN dia_id = 2 THEN pago ELSE 0 END) AS pagoLunes, \n"
            + "SUM(CASE WHEN dia_id = 3 THEN pago ELSE 0 END) AS pagoMartes, \n"
            + "SUM(CASE WHEN dia_id = 4 THEN pago ELSE 0 END) AS pagoMiercoles, \n"
            + "SUM(CASE WHEN dia_id = 5 THEN pago ELSE 0 END) AS pagoJueves, \n"
            + "SUM(CASE WHEN dia_id = 6 THEN pago ELSE 0 END) AS pagoViernes, \n"
            + "SUM(CASE WHEN dia_id = 7 THEN pago ELSE 0 END) AS pagoSabado ) \n"
            + "FROM Historico_Tmp_Libro_Tren_RA15 \n"
            + "WHERE estatus = 1 \n"
            + "GROUP BY expediente, descansos \n"
            + "ORDER BY expediente ASC")
    List<OperadorTrenDTO> findActiveOpTitularTren();

}
