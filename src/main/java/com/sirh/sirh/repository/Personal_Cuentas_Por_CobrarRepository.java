/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Personal_Cuentas_Por_Cobrar;
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
public interface Personal_Cuentas_Por_CobrarRepository extends JpaRepository<Personal_Cuentas_Por_Cobrar, Integer>{
    @Query(value = "SELECT * FROM personal_cuentas_por_cobrar c WHERE c.trabajador_id = :trabajador_id and c.estatus=1 and c.saldo_inicial != 6.96 order by c.fecha_movimiento desc limit 1", nativeQuery = true)
    List<Personal_Cuentas_Por_Cobrar> findOneCuentaPersonal(@Param("trabajador_id") Integer trabajador_id);
    
    @Query(value = "SELECT * FROM personal_cuentas_por_cobrar c WHERE c.trabajador_id = :trabajador_id and c.estatus=1", nativeQuery = true)
    List<Personal_Cuentas_Por_Cobrar> findCuentaPersonalEstatus(@Param("trabajador_id") Integer trabajador_id);
    
    @Query(value = "SELECT DISTINCT nomina_id FROM personal_cuentas_por_cobrar WHERE estatus = 1 ", nativeQuery = true)
    List<Integer> recuperaCuentasActivasPersonal();
    
    @Query(value = "SELECT * FROM personal_cuentas_por_cobrar WHERE estatus = 1 AND nomina_id =:nomina_id", nativeQuery = true)
    List<Personal_Cuentas_Por_Cobrar> recuperaCuentasNominaPersonal(@Param("nomina_id") Integer nomina_id);
    
    @Query(value = "SELECT * FROM personal_cuentas_por_cobrar WHERE nomina_id= :nomina_id and periodo_pago_actual>= :periodo_pago and estatus=1 and deposito=0", nativeQuery = true)
    List<Personal_Cuentas_Por_Cobrar> depositosReporte(@Param("nomina_id") Integer nomina_id, @Param("periodo_pago") Integer periodo_pago);
    
}
