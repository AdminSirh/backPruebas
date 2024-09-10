/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Tmp_Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author oguerrero23
 */
@Repository
public interface Tmp_MovimientosRepository extends JpaRepository<Tmp_Movimientos, Integer> {

    //Se llama a la función en la DB que inserta la información de la tabla temp_retro_jubilados a tmp_movimientos
    @Modifying
    @Transactional
    @Query(value = "CALL insertar_dias_lab_retro_jubilados_tmp_mov(:anio)", nativeQuery = true)
    void adicionarIncidencia2RetroactivoJubilados(@Param("anio") Integer anio);

    @Modifying
    @Transactional
    @Query(value = "CALL insertar_pens_jub_retro_jubilados_tmp_mov(:anio)", nativeQuery = true)
    void adicionarIncidencia29RetroactivoJubilados(@Param("anio") Integer anio);

    @Modifying
    @Transactional
    @Query(value = "CALL insertar_vales_despensa_retro_jubilados_tmp_mov(:anio)", nativeQuery = true)
    void adicionarIncidencia30RetroactivoJubilados(@Param("anio") Integer anio);

    @Modifying
    @Transactional
    @Query(value = "CALL insertar_ayuda_transp_retro_jubilados_tmp_mov(:anio)", nativeQuery = true)
    void adicionarIncidencia33RetroactivoJubilados(@Param("anio") Integer anio);

    @Modifying
    @Transactional
    @Query(value = "CALL insertar_pens_jub_atrasada_retro_jubilados_tmp_mov(:anio)", nativeQuery = true)
    void adicionarIncidencia251RetroactivoJubilados(@Param("anio") Integer anio);

    @Query(value = "SELECT EXISTS (\n"
            + "   SELECT 1\n"
            + "FROM tmp_movimientos\n"
            + "WHERE nomina_id = 4\n"
            //Se considera el año actual para el periodo 92, es decir 202X92
            + "AND periodo_aplicacion = (EXTRACT(YEAR FROM CURRENT_DATE) * 100 + 92)\n"
            + "AND periodo_generacion = (EXTRACT(YEAR FROM CURRENT_DATE) * 100 + 92)\n"
            + ") AS existe_movimiento_retro_jubilados;",
            nativeQuery = true)
    Boolean existe_movimiento_retro_jubilados();

}
