/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Personal_Actualizacion_Cuentas;
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
public interface Personal_Actualizacion_CuentasRepository extends JpaRepository <Personal_Actualizacion_Cuentas,Integer>{
    @Query(value = "SELECT * FROM personal_actualizacion_cuentas c WHERE c.trabajador_id = :trabajador_id and c.personal_cuentas_por_cobrar_id=:personal_cuentas_por_cobrar_id and c.estatus=1 ", nativeQuery = true)
    List<Personal_Actualizacion_Cuentas> findOneCuentaPersonalActual(@Param("trabajador_id") Integer trabajador_id, @Param("personal_cuentas_por_cobrar_id") Integer personal_cuentas_por_cobrar_id);

    @Query(value = "SELECT COUNT(*) FROM personal_actualizacion_cuentas where personal_cuentas_por_cobrar_id =:personal_cuentas_por_cobrar_id", nativeQuery = true)
    Integer contadorDescuentos(@Param("personal_cuentas_por_cobrar_id") Integer personal_cuentas_por_cobrar_id);
}
