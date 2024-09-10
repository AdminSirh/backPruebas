/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.Captura_Semanal_Resumen_IncidenciasRA15DTO;
import com.sirh.sirh.entity.Captura_Semanal_Resumen_IncidenciasRA15;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author rroscero23
 */
public interface Captura_Semanal_Resumen_IncidenciasRA15Repository extends JpaRepository<Captura_Semanal_Resumen_IncidenciasRA15, Integer> {

    @Query(value = "SELECT new com.sirh.sirh.DTO.Captura_Semanal_Resumen_IncidenciasRA15DTO( \n"
            + "Expe.numero_expediente,\n"
            + "concat(Per.apellido_paterno, ' ', Per.apellido_materno, ' ', Per.nombre, ' ') AS nombre_completo,\n"
            + "Puesto.sueldo_hora,\n"
            + "Resumen.id_resumen,\n"
            + "Resumen.horas_turno,\n"
            + "Resumen.horas_doble,\n"
            + "Resumen.horas_triple,\n"
            + "Resumen.dias_laborados,\n"
            + "Resumen.total_faltas,\n"
            + "Resumen.total_paseos,\n"
            + "Resumen.prima_dominical,\n"
            + "Resumen.festivo,\n"
            + "Resumen.omisiones,\n"
            + "Resumen.dif_paseos,\n"
            + "Resumen.dif_prima,\n"
            + "Resumen.dif_tiempo_extra,\n"
            + "Resumen.dif_sueldo,\n"
            + "Resumen.dias_pago,\n"
            + "Resumen.dias_cve_27)\n"
            + "FROM \n"
            + "Captura_Semanal_RA15 AS DatosGenerales\n"
            + "JOIN Captura_Semanal_Resumen_IncidenciasRA15 AS Resumen ON DatosGenerales.captura_Semanal_Resumen_IncidenciasRA15.id_resumen = Resumen.id_resumen\n"
            + "JOIN Trabajador_plaza AS Plaza ON DatosGenerales.trabajador_id = Plaza.trabajador_id\n"
            + "JOIN Cat_Plaza AS CatPlaza ON Plaza.plaza_id = CatPlaza.id_plaza\n"
            + "JOIN Cat_Puesto AS Puesto ON CatPlaza.cat_puesto.id_puesto = Puesto.id_puesto\n"
            + "JOIN Trabajador AS Trab ON DatosGenerales.trabajador_id = Trab.id_trabajador\n"
            + "JOIN Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente\n"
            + "JOIN Persona AS Per ON Trab.persona.id_persona = Per.id_persona\n"
            + "WHERE DatosGenerales.periodo_aplicacion_id = :periodo_id")
    List<Captura_Semanal_Resumen_IncidenciasRA15DTO> reporteCSVCapturaSemanalResumen(@Param("periodo_id") Integer periodo_id);

}
