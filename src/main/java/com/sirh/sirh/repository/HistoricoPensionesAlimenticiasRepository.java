/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.HistoricoPensionesAlimenticias;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rroscero23
 */
@Repository
public interface HistoricoPensionesAlimenticiasRepository extends JpaRepository<HistoricoPensionesAlimenticias, Integer> {

    @Query(value = "SELECT * FROM historico_pensiones_alimenticias WHERE id_pension_historico =:id_pension_historico", nativeQuery = true)
    List<HistoricoPensionesAlimenticias> findHistoricoPensAlim(@Param("id_pension_historico") Integer id_pension_historico);

    @Query(value = "SELECT * FROM historico_pensiones_alimenticias WHERE trabajador_id =:id_trabajador AND (fecha_movimiento BETWEEN :desde AND :hasta)", nativeQuery = true)
    List<HistoricoPensionesAlimenticias> findTrabajadorPensionAHistorico(@Param("id_trabajador") Integer id_trabajador, @Param("desde") Date desde, @Param("hasta") Date hasta);

}
