/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Historico_Credito_Infonavit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author rrosero23
 */
public interface Historico_Credito_InfonavitRepository extends JpaRepository <Historico_Credito_Infonavit, Integer> {
    
    @Query(value = "SELECT * FROM historico_credito_infonavit hci WHERE hci.trabajador_id = :trabajador_id", nativeQuery = true)
    List<Historico_Credito_Infonavit> findCreditoInfonavitHistoricoTrabajador(@Param("trabajador_id") Integer trabajador_id);
    
}
