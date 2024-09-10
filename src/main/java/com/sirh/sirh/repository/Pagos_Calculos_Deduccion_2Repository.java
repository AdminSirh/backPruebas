/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Pagos_Calculos_Deduccion_1;
import com.sirh.sirh.entity.Pagos_Calculos_Deduccion_2;
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
public interface Pagos_Calculos_Deduccion_2Repository extends JpaRepository <Pagos_Calculos_Deduccion_2, Integer>{
    @Query(value = "SELECT * FROM pagos_calculos_deduccion_2 pcd\n" +
                    "WHERE pcd.pago_2_id =:pago_id", nativeQuery = true)
    List<Pagos_Calculos_Deduccion_2> findAllCalculosDeduccion2(@Param("pago_id") Integer pago_id);
}
