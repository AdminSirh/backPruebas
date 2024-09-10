/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cupones_Actualizacion_Cuentas;
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
public interface Cupones_Actualizacion_CuentasRepository extends JpaRepository <Cupones_Actualizacion_Cuentas,Integer>{
    
    @Query(value = "SELECT * FROM cupones_actualizacion_cuentas c WHERE c.trabajador_id = :trabajador_id and c.cupones_cuentas_cobrar_id=:cupones_cuentas_cobrar_id and c.estatus=1 ", nativeQuery = true)
    List<Cupones_Actualizacion_Cuentas> findOneCuentaCuponesActual(@Param("trabajador_id") Integer trabajador_id, @Param("cupones_cuentas_cobrar_id") Integer cupones_cuentas_cobrar_id);
    
    @Query(value = "SELECT COUNT(*) FROM cupones_actualizacion_cuentas WHERE cupones_cuentas_cobrar_id =:cupones_cuentas_cobrar_id", nativeQuery = true)
    Integer contadorDescuentos(@Param("cupones_cuentas_cobrar_id") Integer cupones_cuentas_cobrar_id);
}
