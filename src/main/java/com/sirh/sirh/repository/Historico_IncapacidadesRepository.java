/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Historico_Incapacidades;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rrosero23
 */
@Repository
public interface Historico_IncapacidadesRepository extends JpaRepository<Historico_Incapacidades, Integer> {
    //********* BUSCAR EN TABLA Incapacidades POR Expediente *********
    @Query(value = "SELECT * FROM historico_incapacidades hi WHERE hi.expediente = :expediente", nativeQuery = true)
    List<Historico_Incapacidades> findIncapacidadesHistorico(@Param("expediente") Integer expediente);

}
