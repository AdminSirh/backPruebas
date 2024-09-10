/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Pagos_Deducciones_1;
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
public interface Pagos_Deducciones_1Repository extends JpaRepository <Pagos_Deducciones_1, Integer>{
    
    @Query(value = "SELECT * FROM pagos_deducciones_1\n" +
                    "WHERE pago_1_id =:pago_id", nativeQuery = true)
    List<Pagos_Deducciones_1> findAllDeducciones1(@Param("pago_id") Integer pago_id);
    
}
