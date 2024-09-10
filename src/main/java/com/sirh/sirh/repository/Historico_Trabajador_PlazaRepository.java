/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Historico_Trabajador_Plaza;
import com.sirh.sirh.entity.Trabajador_plaza;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oguerrero23
 */
@Repository
public interface Historico_Trabajador_PlazaRepository extends JpaRepository<Historico_Trabajador_Plaza, Integer>{
    @Query(value = "SELECT * FROM historico_trabajador_plaza c WHERE c.trabajador_id = :trabajador_id limit 1", nativeQuery = true)
    Historico_Trabajador_Plaza findHistoricoPlazaTrabajador(@Param("trabajador_id") Integer trabajador_id);
    
    @Query(value = "SELECT * FROM historico_trabajador_plaza WHERE plaza_id = :plaza_id AND trabajador_id = :trabajador_id AND plaza_movimientos_id IN (17,18,19,20,23,24,102)\n" +
                    "ORDER BY fecha_final\n" +
                    "LIMIT 1;", nativeQuery = true)
    Historico_Trabajador_Plaza findByPlazaAndTrabajadorHistorico(@Param("plaza_id") Integer plaza_id, @Param("trabajador_id") Integer trabajador_id);

}
