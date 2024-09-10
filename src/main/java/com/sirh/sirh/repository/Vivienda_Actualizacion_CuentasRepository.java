/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Vivienda_Actualizacion_Cuentas;
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
public interface Vivienda_Actualizacion_CuentasRepository extends JpaRepository<Vivienda_Actualizacion_Cuentas,Integer>{
    @Query(value = "SELECT * FROM vivienda_actualizacion_cuentas c WHERE c.trabajador_id = :trabajador_id and c.vivienda_cuentas_cobrar_id=:vivienda_cuentas_cobrar_id and c.estatus=1 ", nativeQuery = true)
    List<Vivienda_Actualizacion_Cuentas> findOneCuentaViviendaActual(@Param("trabajador_id") Integer trabajador_id, @Param("vivienda_cuentas_cobrar_id") Integer avivienda_cuentas_cobrar_id);
    
    @Query(value = "SELECT COUNT(*) FROM vivienda_actualizacion_cuentas WHERE vivienda_cuentas_cobrar_id =:vivienda_cuentas_cobrar_id", nativeQuery = true)
    Integer contadorDescuentos(@Param("vivienda_cuentas_cobrar_id") Integer vivienda_cuentas_cobrar_id);
}
