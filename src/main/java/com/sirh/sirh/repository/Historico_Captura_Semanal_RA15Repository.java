/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Historico_Captura_Semanal_RA15;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rroscero23
 */
@Repository
public interface Historico_Captura_Semanal_RA15Repository extends JpaRepository<Historico_Captura_Semanal_RA15, Integer> {

    @Query(value = "SELECT * FROM historico_captura_semanal_ra15 WHERE trabajador_id = :trabajador_id AND periodo_aplicacion_id = :periodo_id", nativeQuery = true)
    Historico_Captura_Semanal_RA15 findHistoricoRA15(@Param("trabajador_id") Integer trabajador_id, @Param("periodo_id") Integer periodo_id);

}
