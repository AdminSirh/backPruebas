/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.CreditoInfonavit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rrosero23
 */
@Repository
public interface CreditoInfonavitRepository extends JpaRepository<CreditoInfonavit, Integer> {
    
    //Encuentra los créditos activos para este trabajador
    @Query(value = "SELECT * FROM credito_infonavit ci WHERE ci.trabajador_id = :trabajador_id AND ci.estatus_credito_id = 2", nativeQuery = true)
    List<CreditoInfonavit> findCreditoInfonavitTrabajador(@Param("trabajador_id") Integer trabajador_id);

    //Edición únicamente de los Catálogos necesarios para estatus y Motivo de baja
    @Modifying
    @Query(value = "UPDATE credito_infonavit SET motivo_baja_id = :motivo_baja_id WHERE id_credito_infonavit = :id_credito_infonavit", nativeQuery = true)
    void updateMotivoBajaId(@Param("id_credito_infonavit") Integer idCreditoInfonavit, @Param("motivo_baja_id") Integer motivoBajaId);

    @Modifying
    @Query(value = "UPDATE credito_infonavit SET estatus_credito_id = :estatus_credito_id WHERE id_credito_infonavit = :id_credito_infonavit", nativeQuery = true)
    void updateEstatusCredito(@Param("id_credito_infonavit") Integer idCreditoInfonavit, @Param("estatus_credito_id") Integer estatusCreditoId);

}
