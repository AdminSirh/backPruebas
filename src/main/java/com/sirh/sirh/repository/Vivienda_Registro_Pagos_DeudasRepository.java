/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Vivienda_Registro_Pagos_Deudas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oguerrero23
 */
@Repository
public interface Vivienda_Registro_Pagos_DeudasRepository extends JpaRepository <Vivienda_Registro_Pagos_Deudas,Integer>{
    
}
