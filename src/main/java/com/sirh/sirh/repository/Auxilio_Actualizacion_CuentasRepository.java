/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Auxilio_Actualizacion_Cuentas;
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
public interface Auxilio_Actualizacion_CuentasRepository extends JpaRepository <Auxilio_Actualizacion_Cuentas,Integer>{
    @Query(value = "SELECT * FROM auxilio_actualizacion_cuentas c WHERE c.trabajador_id = :trabajador_id and c.auxilio_cuentas_cobrar_id=:auxilio_cuentas_cobrar_id and c.estatus=1 ", nativeQuery = true)
    List<Auxilio_Actualizacion_Cuentas> findOneCuentaAuxilioActual(@Param("trabajador_id") Integer trabajador_id, @Param("auxilio_cuentas_cobrar_id") Integer auxilio_cuentas_cobrar_id);

    @Query(value = "SELECT COUNT(*) FROM auxilio_actualizacion_cuentas WHERE auxilio_cuentas_cobrar_id =:auxilio_cuentas_cobrar_id", nativeQuery = true)
    Integer contadorDescuentos(@Param("auxilio_cuentas_cobrar_id") Integer auxilio_cuentas_cobrar_id);
}
