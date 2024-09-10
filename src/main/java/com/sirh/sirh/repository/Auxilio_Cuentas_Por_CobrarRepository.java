/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Auxilio_Cuentas_Por_Cobrar;
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
public interface Auxilio_Cuentas_Por_CobrarRepository extends JpaRepository<Auxilio_Cuentas_Por_Cobrar,Integer>{
    
    @Query(value = "SELECT * FROM auxilio_cuentas_por_cobrar c WHERE c.trabajador_id = :trabajador_id and c.estatus=1 order by c.fecha_movimiento desc limit 1", nativeQuery = true)
    List<Auxilio_Cuentas_Por_Cobrar> findOneCuentaAuxilio(@Param("trabajador_id") Integer trabajador_id);
    
    @Query(value = "SELECT * FROM auxilio_cuentas_por_cobrar c WHERE c.trabajador_id = :trabajador_id and c.estatus=1 order by c.fecha_movimiento desc limit 1", nativeQuery = true)
    Auxilio_Cuentas_Por_Cobrar findCuentaEstatus(@Param("trabajador_id") Integer trabajador_id);
    
    @Query(value = "SELECT DISTINCT nomina_id FROM auxilio_cuentas_por_cobrar WHERE estatus = 1 ", nativeQuery = true)
    List<Integer> recuperaCuentasActivasAuxilio();
    
    @Query(value = "SELECT * FROM auxilio_cuentas_por_cobrar WHERE estatus = 1 AND nomina_id =:nomina_id", nativeQuery = true)
    List<Auxilio_Cuentas_Por_Cobrar> recuperaCuentasNominaAuxilio(@Param("nomina_id") Integer nomina_id);
}
