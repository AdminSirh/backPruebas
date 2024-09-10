/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Pagos_Deducciones_3;
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
public interface Pagos_Deducciones_3Repository extends JpaRepository<Pagos_Deducciones_3, Integer>{
    
    @Query(value = "SELECT * FROM pagos_deducciones_3\n" +
                    "WHERE pago_3_id =:pago_id", nativeQuery = true)
    List<Pagos_Deducciones_3> findAllDeducciones3(@Param("pago_id") Integer pago_id);
}
