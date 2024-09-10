/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Fonacot_Cuentas_Por_Cobrar;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oguerrero23
 */
@Repository
public interface Fonacot_Cuentas_Por_CobrarRepository extends JpaRepository<Fonacot_Cuentas_Por_Cobrar, Integer>{
    
    @Query(value = "SELECT * FROM fonacot_cuentas_por_cobrar c WHERE c.trabajador_id = :trabajador_id and c.estatus=1 order by c.fecha_movimiento desc limit 1", nativeQuery = true)
    List<Fonacot_Cuentas_Por_Cobrar> findOneCuentaFonacot(@Param("trabajador_id") Integer trabajador_id);
    
    @Query(value = "SELECT DISTINCT nomina_id FROM fonacot_cuentas_por_cobrar WHERE estatus = 1 ORDER BY nomina_id ASC", nativeQuery = true)
    List<Integer> nominasCargadas();
    
    @Modifying
    @Query(value = "DELETE FROM fonacot_cuentas_por_cobrar WHERE nomina_id = :nomina_id AND estatus = 1", nativeQuery = true)
    void deleteMovimientos(@Param("nomina_id") Integer nomina_id);
    
    @Query(value = "SELECT * FROM fonacot_cuentas_por_cobrar c WHERE c.trabajador_id = :trabajador_id and c.estatus=1 order by c.fecha_movimiento desc limit 1", nativeQuery = true)
    Fonacot_Cuentas_Por_Cobrar findCuentaEstatus(@Param("trabajador_id") Integer trabajador_id);
    
    @Query(value = "SELECT DISTINCT nomina_id FROM fonacot_cuentas_por_cobrar WHERE estatus = 1 ", nativeQuery = true)
    List<Integer> recuperaCuentasActivas();
    
    @Query(value = "SELECT * FROM fonacot_cuentas_por_cobrar WHERE estatus = 1 AND nomina_id =:nomina_id", nativeQuery = true)
    List<Fonacot_Cuentas_Por_Cobrar> recuperaCuentasNomina(@Param("nomina_id") Integer nomina_id);
    
    @Query(value = "SELECT * FROM fonacot_cuentas_por_cobrar cpc WHERE cpc.estatus = 1", nativeQuery = true)
    List<Fonacot_Cuentas_Por_Cobrar> findAllCuentas();
    
    @Query(value = "SELECT * FROM fonacot_cuentas_por_cobrar WHERE estatus = 1 AND nomina_id =:nomina_id", nativeQuery = true)
    List<Fonacot_Cuentas_Por_Cobrar> recuperaCuentasNominaFonacot(@Param("nomina_id") Integer nomina_id);
}
