/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Monto_Vales;
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
public interface Monto_ValesRepository extends JpaRepository <Monto_Vales,Integer>{
    @Query(value = "SELECT * FROM monto_vales WHERE :dias_trabajados BETWEEN dia_inicio AND dia_fin", nativeQuery = true)
    Monto_Vales findMontosVales(@Param("dias_trabajados") Integer dias_trabajados);
}
