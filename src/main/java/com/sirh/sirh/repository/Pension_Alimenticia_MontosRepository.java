/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Pension_Alimenticia_Montos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author rrosero23
 */
public interface Pension_Alimenticia_MontosRepository extends JpaRepository<Pension_Alimenticia_Montos, Integer> {

    @Query(value = "SELECT * FROM pension_a_montos WHERE trabajador_id =:trabajador_id", nativeQuery = true)
    List<Pension_Alimenticia_Montos> findTrabajadorMontos(@Param("trabajador_id") Integer trabajador_id);
    
}
