/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.Listado_RA_10DTO;
import com.sirh.sirh.DTO.TrabajadorPagoRA10;
import com.sirh.sirh.entity.Captura_Semanal_RA10;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rroscero23
 */
@Repository
public interface Captura_Semanal_RA10Repository extends JpaRepository<Captura_Semanal_RA10, Integer> {

    @Query(value = "SELECT * FROM captura_semanal_ra_10\n"
            + "WHERE trabajador_id = :trabajador_id", nativeQuery = true)
    List<Captura_Semanal_RA10> findCapturasTrabajador(@Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT * FROM public.captura_semanal_ra_10 "
            + "WHERE (fecha_inicial >= COALESCE(:fechaInicial, fecha_inicial)) "
            + "AND (fecha_final <= COALESCE(:fechaFinal, fecha_final)) "
            + "AND trabajador_id = :trabajador_id "
            + "ORDER BY periodo_generacion ASC", nativeQuery = true)
    List<Captura_Semanal_RA10> findCapturaSemanalByFechas(
            @Param("fechaInicial") Date fechaInicial,
            @Param("fechaFinal") Date fechaFinal,
            @Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT new com.sirh.sirh.DTO.TrabajadorPagoRA10( \n"
            + "Expe.numero_expediente AS expediente,\n"
            + "RA10.total_semana AS total_semana) \n"
            + "FROM \n"
            + "Captura_Semanal_RA10 AS RA10\n"
            + "JOIN Trabajador AS Trab ON RA10.trabajador_id = Trab.id_trabajador\n"
            + "JOIN Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente\n"
            + "WHERE \n"
            + "RA10.periodo_generacion = :periodo_generacion\n"
            + "AND \n"
            + "(RA10.total_semana IS NOT NULL AND RA10.total_semana != 0) \n"
            + "ORDER BY Expe.numero_expediente \n")
    List<TrabajadorPagoRA10> findListadoExcel(@Param("periodo_generacion") Integer periodo_generacion);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Listado_RA_10DTO( \n"
            + "Trab.id_trabajador,\n"
            + "Expe.numero_expediente,\n"
            + "Per.nombre,\n"
            + "Per.apellido_paterno,\n"
            + "Per.apellido_materno)\n"
            + "FROM \n"
            + "Captura_Semanal_RA10 AS RA10\n"
            + "JOIN \n"
            + "Trabajador AS Trab ON RA10.trabajador_id = Trab.id_trabajador\n"
            + "JOIN \n"
            + "Trabajador_plaza AS TrabPl ON Trab.id_trabajador = TrabPl.trabajador_id\n"
            + "JOIN \n"
            + "Cat_Areas AS Area ON TrabPl.areas_id = Area.idArea\n"
            + "JOIN \n"
            + "Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente\n"
            + "JOIN \n"
            + "Persona AS Per ON Trab.persona.id_persona = Per.id_persona\n"
            + "WHERE \n"
            + "(Area.idArea IS NULL OR Area.idArea = :id_area OR :id_area IS NULL)\n"
            + "AND (RA10.periodo_generacion IS NULL OR RA10.periodo_generacion = :periodo_generacion)\n"
            + "GROUP BY \n"
            + "Trab.id_trabajador,\n"
            + "Expe.numero_expediente,\n"
            + "Per.nombre,\n"
            + "Per.apellido_paterno,\n"
            + "Per.apellido_materno")
    List<Listado_RA_10DTO> findAreaAndPeriodoGen(
            @Param("id_area") Integer id_area,
            @Param("periodo_generacion") Integer periodo_generacion);

    @Query(value = "SELECT EXISTS (\n"
            + "SELECT 1\n"
            + "FROM public.captura_semanal_ra_10\n"
            + "WHERE periodo_generacion = :periodo_generacion\n"
            + "AND trabajador_id = :trabajador_id\n"
            + "AND estatus = 1\n"
            + ") AS existe_registro;", nativeQuery = true)
    Boolean existeCapturaRa10PorIdTrabajador(@Param("trabajador_id") Integer trabajador_id,
            @Param("periodo_generacion") Integer periodo_generacion);

    @Query(value = "SELECT * FROM public.captura_semanal_ra_10\n"
            + "WHERE periodo_generacion = :periodo_generacion\n"
            + "AND trabajador_id = :trabajador_id\n"
            + "AND estatus = 1", nativeQuery = true)
    List<Captura_Semanal_RA10> buscarCapturaPeriodoTrabajador(@Param("trabajador_id") Integer trabajador_id,
            @Param("periodo_generacion") Integer periodo_generacion);

}
