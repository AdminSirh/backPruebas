/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Vivienda_Cuentas_Por_Cobrar;
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
public interface Vivienda_Cuentas_Por_CobrarRepository extends JpaRepository<Vivienda_Cuentas_Por_Cobrar, Integer>{
    @Query(value = "SELECT * FROM vivienda_cuentas_por_cobrar c WHERE c.trabajador_id = :trabajador_id and c.estatus=1 order by c.fecha_movimiento desc limit 1", nativeQuery = true)
    List<Vivienda_Cuentas_Por_Cobrar> findOneCuentaVivienda(@Param("trabajador_id") Integer trabajador_id);
    
    @Query(value = "SELECT * FROM vivienda_cuentas_por_cobrar c WHERE c.trabajador_id = :trabajador_id and c.estatus=1 order by c.fecha_movimiento desc limit 1", nativeQuery = true)
    Vivienda_Cuentas_Por_Cobrar findCuentaEstatus(@Param("trabajador_id") Integer trabajador_id);
    
    @Query(value = "SELECT DISTINCT nomina_id FROM vivienda_cuentas_por_cobrar WHERE estatus = 1 ", nativeQuery = true)
    List<Integer> recuperaCuentasActivasVivienda();
    
    @Query(value = "SELECT * FROM vivienda_cuentas_por_cobrar WHERE estatus = 1 AND nomina_id =:nomina_id", nativeQuery = true)
    List<Vivienda_Cuentas_Por_Cobrar> recuperaCuentasNominaVivienda(@Param("nomina_id") Integer nomina_id);
}
