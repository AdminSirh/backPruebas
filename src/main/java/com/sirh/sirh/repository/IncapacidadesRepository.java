/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.IncapacidadesDTO;
import com.sirh.sirh.DTO.IncapacidadesMovimientosDTO;
import com.sirh.sirh.entity.Incapacidades;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
public interface IncapacidadesRepository extends JpaRepository<Incapacidades, Integer> {

    //********* BUSCAR EN TABLA Incapacidades POR Expediente *********
    @Query(value = "SELECT * FROM Incapacidades u WHERE u.expediente = :expediente AND status = 1", nativeQuery = true)
    List<Incapacidades> findIncapacidades(@Param("expediente") Integer expediente);

    @Query(value = "SELECT * FROM Incapacidades WHERE folio = :folio AND status = 1 AND expediente= :expediente", nativeQuery = true)
    List<Incapacidades> findIncapacidadByFolio(@Param("folio") String folio, @Param("expediente") Integer expediente);

    // ******** Se buscan las incapacidades continuas para relacionarla con su incapacidad Inicial *********
    @Query(value = "SELECT *\n"
            + "FROM Incapacidades\n"
            + "WHERE date(fecha_inicial) = :fechaInicioIncapacidadContinua\n"
            + "AND tipo_incapacidad_id = 2\n" //Continua
            + "AND motivo_incapacidad_id = 1" //Enfermedad general
            + "AND expediente = :expediente", nativeQuery = true)
    Incapacidades findIncapacidadesContinuas(@Param("expediente") Integer expediente, @Param("fechaInicioIncapacidadContinua") LocalDate fechaInicioIncapacidadContinua);

    // ******** Se encuentra la incapacidad inicial a partir de la fecha de fin de la misma *********
    @Query(value = "SELECT *\n"
            + "FROM Incapacidades\n"
            + "WHERE date(fecha_final) = :fechaFinal\n"
            + "AND (tipo_incapacidad_id = 1 OR tipo_incapacidad_id = 2)\n" //Inicial
            + "AND motivo_incapacidad_id = 1" //Enfermedad general
            + "AND expediente = :expediente", nativeQuery = true)
    Incapacidades findFechaFinIncapacidadInicial(@Param("expediente") Integer expediente, @Param("fechaFinal") LocalDate fechaFinal);

    //********************Encontrar incapacidades por lapsos de fechas de liberación************************
    @Query(value = "SELECT * FROM Incapacidades WHERE expediente =:expediente AND (fecha_movimiento BETWEEN :desde AND :hasta)", nativeQuery = true)
    List<Incapacidades> findPeriodosLiberacion(@Param("expediente") Integer expediente, @Param("desde") Date desde, @Param("hasta") Date hasta);

    //********************Generar txt para módulo de incapacidades del SUA************************
    @Query(value = "SELECT new com.sirh.sirh.DTO.IncapacidadesDTO("
            + "P.num_imss, "
            + "I.fecha_inicial,\n"
            + "I.folio,\n"
            + "I.dias_incapacidad )\n"
            + "FROM Incapacidades AS I "
            + "JOIN Cat_Expediente AS CE ON CAST(I.expediente AS string) = CE.numero_expediente "
            + "JOIN Trabajador AS T ON T.cat_expediente.id_expediente = CE.id_expediente "
            + "JOIN T.persona AS P ON T.persona.id_persona = id_persona "
            + "WHERE DATE(I.fecha_inicial) >= :desde AND DATE(I.fecha_inicial) <= :hasta AND I.autorizar = 1 "
            + "ORDER BY I.fecha_inicial ASC")
    List<IncapacidadesDTO> generarTXTIncapacidades(@Param("desde") Date desde, @Param("hasta") Date hasta);

    //********************Generar txt para módulo de incapacidades del SUA (Para movimientos)************************
    @Query(value = "SELECT new com.sirh.sirh.DTO.IncapacidadesMovimientosDTO("
            + "P.num_imss, "
            + "I.fecha_inicial,\n"
            + "I.folio,\n"
            + "I.dias_incapacidad,\n"
            + "I.porcentaje_cobro_imss,\n"
            + "I.cat_Motivo_Incapacidad ,\n"
            + "I.cat_Tipo_Riesgo_Incapacidad ,\n"
            + "I.cat_Tipo_Secuelas_Incapacidad,\n"
            + "I.cat_Tipo_Control_Incapacidad,\n"
            + "I.fecha_final)\n"
            + "FROM Incapacidades AS I "
            + "JOIN Cat_Expediente AS CE ON CAST(I.expediente AS string) = CE.numero_expediente "
            + "JOIN Trabajador AS T ON T.cat_expediente.id_expediente = CE.id_expediente "
            + "JOIN T.persona AS P ON T.persona.id_persona = id_persona "
            + "WHERE DATE(I.fecha_inicial) >= :desde AND DATE(I.fecha_inicial) <= :hasta AND I.autorizar = 1 "
            + "ORDER BY I.fecha_inicial ASC")
    List<IncapacidadesMovimientosDTO> generarTXTIncapacidadesMovimientos(@Param("desde") Date desde, @Param("hasta") Date hasta);

}
