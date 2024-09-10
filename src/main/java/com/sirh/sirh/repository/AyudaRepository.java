/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.AyudaDTO;
import com.sirh.sirh.DTO.AyudaDatosDTO;
import com.sirh.sirh.entity.Ayuda;
import java.util.List;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author akalvarez19
 */
public interface AyudaRepository extends JpaRepository<Ayuda, Integer> {

    @Query(value = "SELECT a.id_ayuda, a.tipo_ayuda_id, fecha_recepcion, a.fecha_emision, a.folio, a. parentesco, a.nombre, "
            + "a.apellido_paterno, a.apellido_materno, a.periodo_pago_id, a.estatus, a.incidencia_id "
            + "FROM ayuda a "
            + "INNER JOIN incidencias i on a.incidencia_id = i.id_incidencia "
            + "WHERE a.id_ayuda =:id_ayuda a.estatus = 1", nativeQuery = true)
    List<Ayuda> findAyudaID(@Param("id_ayuda") Integer id_ayuda);

    @Query(value = "SELECT a.id_ayuda, a.tipo_ayuda_id, fecha_recepcion, a.fecha_emision, a.folio, a. parentesco, a.nombre, "
            + "a.apellido_paterno, a.apellido_materno, a.periodo_pago_id, a.estatus, a.incidencia_id, i.trabajador_id "
            + "FROM ayuda a "
            + "INNER JOIN incidencias i on a.incidencia_id = i.id_incidencia "
            + "WHERE i.trabajador_id =:trabajador_id and a.estatus = 1", nativeQuery = true)
    List<Ayuda> findAyudaIDT(@Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT new com.sirh.sirh.DTO.AyudaDTO( "
            + "Ayuda.id_ayuda, "
            + "Ayuda_Dias_Permiso.id_ayuda_dias, "
            + "Ayuda.fecha_recepcion, "
            + "Ayuda.fecha_emision, "
            + "Ayuda.parentesco_id, "
            + "Ayuda.nombre, "
            + "Ayuda.apellido_paterno, "
            + "Ayuda.apellido_materno, "
            + "Ayuda.origen, "
            + "Ayuda.fecha_evento, "
            + "Ayuda_Dias_Permiso.dia_1, "
            + "Ayuda_Dias_Permiso.dia_2, "
            + "Ayuda_Dias_Permiso.dia_3, "
            + "Ayuda_Dias_Permiso.dia_4, "
            + "Ayuda_Dias_Permiso.dia_5, "
            + "Ayuda_Dias_Permiso.dia_6) "
            + "FROM Ayuda Ayuda "
            + "JOIN Incidencias Incidencias ON Ayuda.incidencia_id = Incidencias.id_incidencia "
            + "LEFT JOIN Ayuda_Dias_Permiso Ayuda_Dias_Permiso ON Ayuda.ayuda_dias_id = Ayuda_Dias_Permiso.id_ayuda_dias "
            + "WHERE Incidencias.id_incidencia = :id_incidencia")
    List<AyudaDTO> findAyudaIfExists(@Param("id_incidencia") Integer id_incidencia);

    @Query(value = "SELECT new com.sirh.sirh.DTO.AyudaDatosDTO( \n"
            + "Expediente.numero_expediente,\n"
            + "Persona.nombre AS nombre_trabajador,\n"
            + "Persona.apellido_paterno AS apellido_paterno_trabajador,\n"
            + "Persona.apellido_materno AS apellido_materno_trabajador,\n"
            + "Ayuda.fecha_emision,\n"
            + "Inci.fecha_inicio,\n"
            + "Inci.fecha_fin,\n"
            + "Inci.num_dias,\n"
            + "Ayuda.monto,\n"
            + "Ayuda.periodo_pago_id,\n"
            + "Ayuda.nombre AS nombre_ayuda,\n"
            + "Ayuda.apellido_paterno AS apellido_paterno_ayuda,\n"
            + "Ayuda.apellido_materno AS apellido_materno_ayuda,\n"
            + "Parentesco.desc_parentesco,\n"
            + "CatInci.inicial_descripcion,\n"
            + "Inci.folio,\n"
            + "Ayuda.fecha_recepcion,\n"
            + "Nomina.desc_nomina)\n"
            + "FROM Ayuda AS Ayuda\n"
            + "JOIN Incidencias AS Inci ON Inci.id_incidencia = Ayuda.incidencia_id\n"
            + "JOIN Cat_Parentesco AS Parentesco ON Ayuda.parentesco_id = Parentesco.id_parentesco\n"
            + "JOIN Cat_Incidencias AS CatInci ON Inci.cat_incidencias.id_incidencia = CatInci.id_incidencia\n"
            + "JOIN Trabajador_plaza AS TrabPlaza ON Ayuda.trabajador_id = TrabPlaza.trabajador_id \n"
            + "JOIN Cat_Tipo_Nomina AS Nomina ON Nomina.id_tipo_nomina = TrabPlaza.cat_Tipo_Nomina.id_tipo_nomina\n"
            + "JOIN Trabajador AS Trab ON TrabPlaza.trabajador_id = Trab.id_trabajador\n"
            + "JOIN Cat_Expediente AS Expediente ON Trab.cat_expediente.id_expediente = Expediente.id_expediente\n"
            + "JOIN Persona AS Persona ON Trab.persona.id_persona = Persona.id_persona\n"
            //Parámetro opcional de filtrado, si se envía se filtra, si no, no se realiza el filtrado
            + "WHERE (:estadoIncidenciaParametro IS NULL OR Inci.estado_incidencia_id = :estadoIncidenciaParametro) \n"
            + "AND (CAST(:fechaInicioParametro AS timestamp) IS NULL OR date(Inci.fecha_inicio) BETWEEN date(:fechaInicioParametro) AND date(:fechaFinParametro))")
    List<AyudaDatosDTO> findAllAyudas(
            @Param("estadoIncidenciaParametro") Integer estadoIncidenciaParametro,
            @Param("fechaInicioParametro") Date fechaInicioParametro,
            @Param("fechaFinParametro") Date fechaFinParametro);

}
