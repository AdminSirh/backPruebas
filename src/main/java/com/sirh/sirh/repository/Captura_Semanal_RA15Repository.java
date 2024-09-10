/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.CapturaSemanalRA15DTO;
import com.sirh.sirh.DTO.Trabajador_RA15DTO;
import com.sirh.sirh.entity.Captura_Semanal_RA15;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author rroscero23
 */
public interface Captura_Semanal_RA15Repository extends JpaRepository<Captura_Semanal_RA15, Integer> {

    @Query(value = "SELECT * FROM captura_semanal_ra_15\n"
            + "WHERE trabajador_id = :trabajador_id\n"
            + "AND periodo_aplicacion_id = :periodo_id", nativeQuery = true)
    List<Captura_Semanal_RA15> findByIdTrabajadorAndIdPeriodo(@Param("trabajador_id") Integer trabajadorId, @Param("periodo_id") Integer periodoId);

    @Query(value = "SELECT *\n"
            + "FROM captura_semanal_ra_15 AS RA15\n"
            + "JOIN trabajador AS Trab ON RA15.trabajador_id = Trab.id_trabajador\n"
            + "JOIN catalogo_expedientes AS Expediente on  Trab.expediente_id = Expediente.id_expediente\n"
            + "WHERE Expediente.numero_expediente = :numero_expediente\n"
            + "AND periodo_aplicacion_id = :periodo_id\n"
            + "LIMIT 1", nativeQuery = true)
    List<Captura_Semanal_RA15> findByIdTrabajadorExpedienteAndIdPeriodo(@Param("numero_expediente") String numero_expediente, @Param("periodo_id") Integer periodoId);

    @Query(value = "SELECT  new com.sirh.sirh.DTO.CapturaSemanalRA15DTO(\n"
            + "Expediente.numero_expediente,\n"
            + "CONCAT(Persona.apellido_materno, ' ', Persona.apellido_paterno, ' ', Persona.nombre) AS nombre_completo,\n"
            + "Puesto.puesto,\n"
            + "SINO.descripcion AS comisionado,\n"
            + "Puesto.sueldo_hora,\n"
            + "Totales.dias_laborados,\n"
            + "Totales.dias_pago,\n"
            + "Totales.dias_cve_27,\n"
            + "Totales.horas_turno,\n"
            + "Totales.dif_sueldo,\n"
            + "Totales.prima_dominical,\n"
            + "Totales.total_paseos,\n"
            + "Totales.horas_doble,\n"
            + "Totales.horas_triple,\n"
            + "Totales.festivo,\n"
            + "Totales.omisiones,\n"
            + "Totales.total_faltas, \n"
            + "JornadaNormal.domingo AS domingo_jn,\n"
            + "JornadaNormal.lunes AS lunes_jn,\n"
            + "JornadaNormal.martes AS martes_jn,\n"
            + "JornadaNormal.miercoles AS miercoles_jn,\n"
            + "JornadaNormal.jueves AS jueves_jn,\n"
            + "JornadaNormal.viernes AS viernes_jn,\n"
            + "JornadaNormal.sabado AS sabado_jn, \n"
            + "ExcedenteJornada.domingo AS domingo_ej, \n"
            + "ExcedenteJornada.lunes AS lunes_ej, \n"
            + "ExcedenteJornada.martes AS martes_ej, \n"
            + "ExcedenteJornada.miercoles AS miercoles_ej, \n"
            + "ExcedenteJornada.jueves AS jueves_ej, \n"
            + "ExcedenteJornada.viernes AS viernes_ej, \n"
            + "ExcedenteJornada.sabado AS sabado_ej, \n"
            + "TiempoExtra.domingo AS domingo_te, \n"
            + "TiempoExtra.lunes AS lunes_te, \n"
            + "TiempoExtra.martes AS martes_te, \n"
            + "TiempoExtra.miercoles AS miercoles_te, \n"
            + "TiempoExtra.jueves AS jueves_te, \n"
            + "TiempoExtra.viernes AS viernes_te, \n"
            + "TiempoExtra.sabado AS sabado_te, \n"
            + "PaseosLaborados.domingo AS domingo_pl, \n"
            + "PaseosLaborados.lunes AS lunes_pl, \n"
            + "PaseosLaborados.martes AS martes_pl, \n"
            + "PaseosLaborados.miercoles AS miercoles_pl, \n"
            + "PaseosLaborados.jueves AS jueves_pl, \n"
            + "PaseosLaborados.viernes AS viernes_pl, \n"
            + "PaseosLaborados.sabado AS sabado_pl, \n"
            + "Incidencias.domingo AS domingo_inci, \n"
            + "Incidencias.lunes AS lunes_inci, \n"
            + "Incidencias.martes AS martes_inci, \n"
            + "Incidencias.miercoles AS miercoles_inci, \n"
            + "Incidencias.jueves AS jueves_inci, \n"
            + "Incidencias.viernes AS viernes_inci, \n"
            + "Incidencias.sabado AS sabado_inci, \n"
            + "Suplencias.domingo AS domingo_sup, \n"
            + "Suplencias.lunes AS lunes_sup, \n"
            + "Suplencias.martes AS martes_sup, \n"
            + "Suplencias.miercoles AS miercoles_sup, \n"
            + "Suplencias.jueves AS jueves_sup, \n"
            + "Suplencias.viernes AS viernes_sup, \n"
            + "Suplencias.sabado AS sabado_sup) \n"
            + "FROM \n"
            + "Captura_Semanal_RA15 AS CapturaSemanal\n"
            + "JOIN Captura_Semanal_Resumen_IncidenciasRA15 AS Totales ON CapturaSemanal.captura_Semanal_Resumen_IncidenciasRA15.id_resumen = Totales.id_resumen\n"
            + "JOIN Captura_Semanal_Jornada_Normal_RA15 AS JornadaNormal ON CapturaSemanal.captura_Semanal_Jornada_Normal_RA15.id_jornada_normal = JornadaNormal.id_jornada_normal\n"
            + "JOIN Captura_Semanal_Excedente_Jornada_RA15 AS ExcedenteJornada ON CapturaSemanal.captura_Semanal_Excedente_Jornada_RA15.id_excedente_jornada = ExcedenteJornada.id_excedente_jornada\n"
            + "JOIN Captura_Semanal_Tiempo_Extra_RA15 AS TiempoExtra ON CapturaSemanal.captura_Semanal_Tiempo_Extra_RA15.id_tiempo_extra = TiempoExtra.id_tiempo_extra\n"
            + "JOIN Captura_Semanal_Paseos_Laborados_RA15 AS PaseosLaborados ON CapturaSemanal.captura_Semanal_Paseos_Laborados_RA15.id_paseos_laborados = PaseosLaborados.id_paseos_laborados\n"
            + "JOIN Captura_Semanal_Inasistencias_RA15 AS Incidencias ON CapturaSemanal.captura_Semanal_Inasistencias_RA15.id_inasistencia = Incidencias.id_inasistencia\n"
            + "JOIN Captura_Semanal_Suplencias_RA15 AS Suplencias ON CapturaSemanal.captura_Semanal_Suplencias_RA15.id_suplencia = Suplencias.id_suplencia\n"
            + "JOIN Trabajador AS Trabajador ON CapturaSemanal.trabajador_id = Trabajador.id_trabajador\n"
            + "JOIN Persona AS Persona ON Trabajador.persona.id_persona = Persona.id_persona\n"
            + "JOIN Cat_Expediente AS Expediente ON Trabajador.cat_expediente.id_expediente = Expediente.id_expediente\n"
            + "JOIN Trabajador_plaza AS Plaza ON CapturaSemanal.trabajador_id = Plaza.trabajador_id\n"
            + "JOIN Cat_Si_No AS SINO ON Trabajador.comisionado_si_no = SINO.id\n"
            + "JOIN Cat_Plaza AS CPlaza ON Plaza.plaza_id = CPlaza.id_plaza\n"
            + "JOIN Cat_Puesto AS Puesto ON CPlaza.cat_puesto.id_puesto = Puesto.id_puesto \n"
            + "WHERE CapturaSemanal.periodo_aplicacion_id = :periodo_id \n"
            + "ORDER BY Expediente.numero_expediente")
    List<CapturaSemanalRA15DTO> reporteCSVCapturaSemanal(@Param("periodo_id") Integer periodo_id);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Trabajador_RA15DTO( \n"
            + "Trab.id_trabajador,\n"
            + "Per.nombre,\n"
            + "Per.apellido_paterno,\n"
            + "Per.apellido_materno,\n"
            + "Expe.numero_expediente,\n"
            + "JornadaNormal.lunes,\n"
            + "JornadaNormal.martes,\n"
            + "JornadaNormal.miercoles,\n"
            + "JornadaNormal.jueves,\n"
            + "JornadaNormal.viernes,\n"
            + "JornadaNormal.sabado,\n"
            + "JornadaNormal.domingo, \n"
            + "MAX(CASE WHEN Dias.dia_descanso = 1 AND Dias.cat_dias.id_dia = 2 THEN 1 ELSE 0 END) AS lunes_descanso,\n"
            + "MAX(CASE WHEN Dias.dia_descanso = 1 AND Dias.cat_dias.id_dia = 3 THEN 1 ELSE 0 END) AS martes_descanso,\n"
            + "MAX(CASE WHEN Dias.dia_descanso = 1 AND Dias.cat_dias.id_dia = 4 THEN 1 ELSE 0 END) AS miercoles_descanso,\n"
            + "MAX(CASE WHEN Dias.dia_descanso = 1 AND Dias.cat_dias.id_dia = 5 THEN 1 ELSE 0 END) AS jueves_descanso,\n"
            + "MAX(CASE WHEN Dias.dia_descanso = 1 AND Dias.cat_dias.id_dia = 6 THEN 1 ELSE 0 END) AS viernes_descanso,\n"
            + "MAX(CASE WHEN Dias.dia_descanso = 1 AND Dias.cat_dias.id_dia = 7 THEN 1 ELSE 0 END) AS sabado_descanso,\n"
            + "MAX(CASE WHEN Dias.dia_descanso = 1 AND Dias.cat_dias.id_dia= 1 THEN 1 ELSE 0 END) AS domingo_descanso) \n"
            + "FROM Trabajador AS Trab\n"
            + "JOIN Persona AS Per ON Trab.persona.id_persona = Per.id_persona\n"
            + "JOIN Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente\n"
            + "JOIN Trabajador_plaza AS Plaza ON Trab.id_trabajador = Plaza.trabajador_id\n"
            + "LEFT JOIN Captura_Semanal_RA15 AS CapturaSemanal ON Trab.id_trabajador = CapturaSemanal.trabajador_id AND CapturaSemanal.periodo_aplicacion_id = :periodo_id \n"
            + "LEFT JOIN Captura_Semanal_Jornada_Normal_RA15 AS JornadaNormal ON CapturaSemanal.captura_Semanal_Jornada_Normal_RA15.id_jornada_normal = JornadaNormal.id_jornada_normal\n"
            + "LEFT JOIN Libro_Dias AS Dias ON Dias.trabajador_id = Trab.id_trabajador\n"
            + "WHERE Plaza.cat_Tipo_Nomina.id_tipo_nomina = 2 \n"
            + "GROUP BY Trab.id_trabajador, Per.nombre, Per.apellido_paterno, Per.apellido_materno, Expe.numero_expediente, \n"
            + "JornadaNormal.lunes, JornadaNormal.martes, JornadaNormal.miercoles, JornadaNormal.jueves, JornadaNormal.viernes, JornadaNormal.sabado, JornadaNormal.domingo \n"
            + "ORDER BY Expe.numero_expediente")
    List<Trabajador_RA15DTO> trabajadoresRA15Periodo(@Param("periodo_id") Integer periodo_id);

}
