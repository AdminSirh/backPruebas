/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Fonacot_Actualizacion_Cuentas;
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
public interface Fonacot_Actualizacion_CuentasRepository extends JpaRepository <Fonacot_Actualizacion_Cuentas,Integer>{
    @Query(value = "SELECT * FROM fonacot_actualizacion_cuentas c WHERE c.trabajador_id = :trabajador_id and c.cuenta_por_cobrar_id=:cuenta_por_cobrar_id and c.estatus=1 ", nativeQuery = true)
    List<Fonacot_Actualizacion_Cuentas> findOneCuentaActual(@Param("trabajador_id") Integer trabajador_id, @Param("cuenta_por_cobrar_id") Integer cuenta_por_cobrar_id);
    
    @Query(value = "SELECT COUNT(*) FROM fonacot_actualizacion_cuentas where cuenta_por_cobrar_id =:cuenta_por_cobrar_id", nativeQuery = true)
    Integer contadorDescuentosFonacot(@Param("cuenta_por_cobrar_id") Integer cuenta_por_cobrar_id);
}
