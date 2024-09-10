/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Pagos_Calculos_Percepcion_4;
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
public interface Pagos_Calculos_Percepcion_4Repository extends JpaRepository <Pagos_Calculos_Percepcion_4,Integer>{
    @Query(value = "SELECT * FROM pagos_calculos_percepcion_4 pcp\n" +
                    "WHERE pcp.pago_4_id =:pago_id", nativeQuery = true)
    List<Pagos_Calculos_Percepcion_4> findAllCalculosPercepcion4(@Param("pago_id") Integer pago_id);
}
