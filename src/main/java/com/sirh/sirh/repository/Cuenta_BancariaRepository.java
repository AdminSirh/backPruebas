/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cuenta_Bancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oguerrero23
 */
@Repository
public interface Cuenta_BancariaRepository extends JpaRepository<Cuenta_Bancaria, Integer>{
    @Query(value = "SELECT * FROM cuenta_bancaria c WHERE c.trabajador_id = :trabajador_id", nativeQuery = true)
    Cuenta_Bancaria findCuentaidTrabajador(@Param("trabajador_id") Integer trabajador_id);
 
    
}
