/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.Incidencias_RA_TransportacionDTO;
import com.sirh.sirh.DTO.IncidenciasCardexGeneralDTO;
import com.sirh.sirh.DTO.IncidenciasSuaDTO;
import com.sirh.sirh.entity.Incidencias;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author akalvarez19
 */
public interface IncidenciasRepository extends JpaRepository<Incidencias, Integer> {

    //************ENCONTRAR ID INCIDENCIA DE PERIODOS, TIPOS DE NÓMINA Y TIPOS DE INCIDENCIA***************
    @Query(value = "SELECT \n"
            + "Inci.id_incidencia,\n"
            + "Inci.fecha_inicio,\n"
            + "Inci.fecha_fin\n"
            + "FROM\n"
            + "incidencias AS Inci\n"
            + "JOIN\n"
            + "trabajador_plaza AS TraP ON TraP.trabajador_id = Inci.trabajador_id\n"
            + "JOIN\n"
            + "periodos_pago AS PerP ON Inci.fecha_inicio BETWEEN PerP.fecha_inicial AND PerP.fecha_final\n"
            + "WHERE\n"
            + "PerP.id_nomina = TraP.tipo_nomina_id\n"
            + "AND PerP.id_nomina = :nomina_id\n"
            + "AND PerP.periodo = :periodoPago\n"
            + "AND Inci.incidencia_id IN (:tipoIncidencia)", nativeQuery = true)
    List<Integer> findIdIncidenciaImportacion(@Param("nomina_id") Integer nomina_id, @Param("periodoPago") Integer periodoPago, @Param("tipoIncidencia") List<Integer> tipoIncidencia);

    //************ BUSCAR EN LA TABLA INCIDENCIAS POR TRABAJADOR_ID **************
    @Query(value = "SELECT * "
            + "FROM incidencias i "
            + "INNER JOIN catalogo_incidencias c on i.incidencia_id = c.id_incidencia "
            + "INNER JOIN catalogo_tipo_incidencia t on c.tipo_incidencia_id = t.id_tipo_incidencia "
            + "INNER JOIN catalogo_estado_incidencia e on i.estado_incidencia_id = e.id_estado_incidencia "
            + "WHERE i.trabajador_id =:trabajador_id and  i.estatus =1 ", nativeQuery = true)
    List<Incidencias> findIncidencias(@Param("trabajador_id") Integer trabajador_id);

    //************ BUSCAR EN LA TABLA INCIDENCIAS POR TRABAJADOR_ID ACTIVAS **************
    @Query(value = "SELECT * "
            + "FROM incidencias i "
            + "INNER JOIN catalogo_incidencias c on i.incidencia_id = c.id_incidencia "
            + "INNER JOIN catalogo_tipo_incidencia t on c.tipo_incidencia_id = t.id_tipo_incidencia "
            + "INNER JOIN catalogo_estado_incidencia e on i.estado_incidencia_id = e.id_estado_incidencia "
            + "WHERE i.trabajador_id =:trabajador_id and  i.estatus =1 and  estado_incidencia_id = 1 "
            + "AND c.estatus_kardex = 1 "
            //Edición realizada para no mostrar la fecha de inicio y fecha fin de los elementos de la tabla ayuda a excepción del permiso retribuido
            + "AND NOT EXISTS (\n"
            + "    SELECT 1\n"
            + "    FROM ayuda a\n"
            + "    WHERE a.incidencia_id = i.id_incidencia AND i.incidencia_id <> 33 \n"
            + ")", nativeQuery = true)
    List<Incidencias> findIncidenciasTrabajador(@Param("trabajador_id") Integer trabajador_id);

    //************ BUSCAR EN LA TABLA INCIDENCIAS POR FOLIO (Incapacidades) **************
    @Query(value = "SELECT * \n"
            + "FROM incidencias\n"
            + "WHERE folio = :folio \n"
            + "AND estatus = 1", nativeQuery = true)
    List<Incidencias> findIncidenciasFolio(@Param("folio") String folio);

    //Cuenta las Incidencias por trabajador en el periodo de pago de los vales de Fin de año
    @Query(value = "SELECT SUM(num_dias) FROM incidencias  WHERE trabajador_id =:trabajador_id AND incidencia_id IN (2, 6 ,9, 13, 14, 15, 16, 17, 18, 19, 22, 23, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35) AND fecha_inicio >=:fecha_inicial AND fecha_fin<:fecha_fin", nativeQuery = true)
    Integer cuentaIncidenciasByTrabajadoryPeriodoVales(@Param("trabajador_id") Integer trabajador_id, @Param("fecha_inicial") Date fecha_inicial, @Param("fecha_fin") Date fecha_fin);

    //Actualizar en estado de las incidencias co mismo folio (Incidencias relacionadas ayuda)
    @Modifying
    @Query(value = "UPDATE Incidencias SET estatus = 0 WHERE folio = :folio AND estatus = 1 AND (incidencia_id = 3 OR incidencia_id = 4)", nativeQuery = false)
    void updateIncidenciasEstatusFolioRelacionado(@Param("folio") String folio);

    //Agrupa las incidencias de falta por numero de dias 
    @Query(value = "SELECT c_i.descripcion, SUM(i.num_dias) AS total_dias, i.fecha_inicio  FROM incidencias i JOIN catalogo_incidencias c_i ON c_i.id_incidencia = i.incidencia_id WHERE i.trabajador_id = :trabajador_id \n"
            + "AND i.incidencia_id IN (2, 9, 13, 14, 15, 16, 17, 18, 19, 22, 23, 27, 28, 29, 30, 31, 32, 34, 35) \n"
            + "AND i.fecha_inicio >= :fecha_inicial AND i.fecha_fin < :fecha_fin \n"
            + "GROUP BY c_i.descripcion, i.fecha_inicio ", nativeQuery = true)
    List<List<Object>> incidenciasFaltaEnPeriodo(@Param("trabajador_id") Integer trabajador_id, @Param("fecha_inicial") Date fecha_inicial, @Param("fecha_fin") Date fecha_fin);

//************ BUSCAR EN LA TABLA INCIDENCIAS POR ID **************
    @Query(value = "SELECT * "
            + "FROM incidencias i "
            + "INNER JOIN catalogo_incidencias c on i.incidencia_id = c.id_incidencia "
            + "INNER JOIN catalogo_tipo_incidencia t on c.tipo_incidencia_id = t.id_tipo_incidencia "
            + "INNER JOIN catalogo_estado_incidencia e on i.estado_incidencia_id = e.id_estado_incidencia "
            + "WHERE i.id_incidencia =:id_incidencia and i.estatus = 1", nativeQuery = true)
    Incidencias findIncidenciasID(@Param("id_incidencia") Integer id_incidencia);

    @Query(value = "SELECT MAX(id_incidencia) FROM incidencias u WHERE u.trabajador_id = :trabajador_id", nativeQuery = true)
    Integer findLastIncidencia(@Param("trabajador_id") Integer trabajador_id);

    //************ BUSCAR EN LA TABLA INCIDENCIAS POR TRABAJADOR_ID Y TIPO INCIDENCIA **************
    @Query(value = "SELECT * \n"
            + " FROM incidencias i \n"
            + " INNER JOIN catalogo_incidencias c on i.incidencia_id = c.id_incidencia \n"
            + "INNER JOIN catalogo_tipo_incidencia t on c.tipo_incidencia_id = t.id_tipo_incidencia \n"
            + "INNER JOIN catalogo_estado_incidencia e on i.estado_incidencia_id = e.id_estado_incidencia \n"
            + "WHERE i.trabajador_id =:trabajador_id \n"
            + "AND  i.estatus =1 \n"
            + "AND i.incidencia_id = :incidencia_id\n"
            + "AND i.estado_incidencia_id =1 \n"
            + "AND c.estatus_kardex = 1"
            + "AND NOT EXISTS (\n"
            + "    SELECT 1\n"
            + "    FROM ayuda a\n"
            //Edición realizada para no mostrar la fecha de inicio y fecha fin de los elementos de la tabla ayuda a excepción del permiso retribuido
            + "    WHERE a.incidencia_id = i.id_incidencia AND i.incidencia_id <> 33 \n"
            + ");", nativeQuery = true)
    List<Incidencias> findEspecificIncidenceWorker(@Param("trabajador_id") Integer trabajador_id, @Param("incidencia_id") Integer incidencia_id);

    //************ GENERA DESCARGA INCIDENICAS ESPECIFICAS EN UN LAPSO DE FECHAS (TXT) **************
    @Query(value
            = "SELECT new com.sirh.sirh.DTO.IncidenciasCardexGeneralDTO("
            + "CataInci.inicial_descripcion, "
            + "Trab.id_trabajador, "
            + "Expe.numero_expediente, "
            + "EXTRACT(DAY FROM (Inci.fecha_fin - Inci.fecha_inicio)) + 1 AS cantidad_incidencias) "
            + "FROM Trabajador_plaza AS Plaza "
            + "JOIN Trabajador AS Trab ON Plaza.trabajador_id = Trab.id_trabajador "
            + "JOIN Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente "
            + "LEFT JOIN Incidencias AS Inci ON Inci.trabajador_id = Trab.id_trabajador "
            + "AND FUNCTION('DATE', Inci.fecha_inicio) >= :fecha_inicial AND FUNCTION('DATE', Inci.fecha_fin) <= :fecha_final "
            //--Faltas, R.A, R.B, R.C, EG50, EG60, EG75, EG100, RT100, PRE100, EN60, EN100, ET25, ET50, EG0, RT0, PRE0, S
            + "AND Inci.cat_incidencias.id_incidencia IN (2,10,11,12,14,31,15,16,17,18,23,26,24,25,27,28,29,9) "
            + "LEFT JOIN Cat_Incidencias AS CataInci ON Inci.cat_incidencias.id_incidencia = CataInci.id_incidencia "
            + "WHERE Plaza.cat_Tipo_Nomina.id_tipo_nomina = 3 "
            + "GROUP BY CataInci.inicial_descripcion, Trab.id_trabajador, Expe.numero_expediente, Inci.fecha_inicio, Inci.fecha_fin"
    )
    List<IncidenciasCardexGeneralDTO> documentoTextoCardexIncidenciasConfianza(@Param("fecha_inicial") LocalDateTime fecha_inicial,
            @Param("fecha_final") LocalDateTime fecha_final);

    @Query(value
            = "SELECT new com.sirh.sirh.DTO.IncidenciasCardexGeneralDTO("
            + "CataInci.inicial_descripcion, "
            + "Trab.id_trabajador, "
            + "Expe.numero_expediente, "
            + "EXTRACT(DAY FROM (Inci.fecha_fin - Inci.fecha_inicio)) + 1 AS cantidad_incidencias) "
            + "FROM Trabajador_plaza AS Plaza "
            + "JOIN Trabajador AS Trab ON Plaza.trabajador_id = Trab.id_trabajador "
            + "JOIN Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente "
            + "LEFT JOIN Incidencias AS Inci ON Inci.trabajador_id = Trab.id_trabajador "
            + "AND FUNCTION('DATE', Inci.fecha_inicio) >= :fecha_inicial AND FUNCTION('DATE', Inci.fecha_fin) <= :fecha_final "
            //--Faltas, R.A, R.B, R.C, EG50, EG60, EG75, EG100, RT100, PRE100, EN60, EN100, ET25, ET50, EG0, RT0, PRE0, S
            + "AND Inci.cat_incidencias.id_incidencia IN (2,10,11,12,14,31,15,16,17,18,23,26,24,25,27,28,29,9) "
            + "LEFT JOIN Cat_Incidencias AS CataInci ON Inci.cat_incidencias.id_incidencia = CataInci.id_incidencia "
            + "WHERE Plaza.cat_Tipo_Nomina.id_tipo_nomina = 1 "
            + "GROUP BY CataInci.inicial_descripcion, Trab.id_trabajador, Expe.numero_expediente, Inci.fecha_inicio, Inci.fecha_fin"
    )
    List<IncidenciasCardexGeneralDTO> documentoTextoCardexIncidenciasBase(@Param("fecha_inicial") LocalDateTime fecha_inicial,
            @Param("fecha_final") LocalDateTime fecha_final);

    @Query(value
            = "SELECT new com.sirh.sirh.DTO.IncidenciasCardexGeneralDTO("
            + "CataInci.inicial_descripcion, "
            + "Trab.id_trabajador, "
            + "Expe.numero_expediente, "
            + "EXTRACT(DAY FROM (Inci.fecha_fin - Inci.fecha_inicio)) + 1 AS cantidad_incidencias) "
            + "FROM Trabajador_plaza AS Plaza "
            + "JOIN Trabajador AS Trab ON Plaza.trabajador_id = Trab.id_trabajador "
            + "JOIN Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente "
            + "LEFT JOIN Incidencias AS Inci ON Inci.trabajador_id = Trab.id_trabajador "
            + "AND FUNCTION('DATE', Inci.fecha_inicio) >= :fecha_inicial AND FUNCTION('DATE', Inci.fecha_fin) <= :fecha_final "
            //--Faltas, R.A, R.B, R.C, EG50, EG60, EG75, EG100, RT100, PRE100, EN60, EN100, ET25, ET50, EG0, RT0, PRE0, S
            + "AND Inci.cat_incidencias.id_incidencia IN (2,10,11,12,14,31,15,16,17,18,23,26,24,25,27,28,29,9) "
            + "LEFT JOIN Cat_Incidencias AS CataInci ON Inci.cat_incidencias.id_incidencia = CataInci.id_incidencia "
            + "WHERE Plaza.cat_Tipo_Nomina.id_tipo_nomina = 2 "
            + "GROUP BY CataInci.inicial_descripcion, Trab.id_trabajador, Expe.numero_expediente, Inci.fecha_inicio, Inci.fecha_fin"
    )
    List<IncidenciasCardexGeneralDTO> documentoTextoCardexIncidenciasTransportacion(@Param("fecha_inicial") LocalDateTime fecha_inicial,
            @Param("fecha_final") LocalDateTime fecha_final);

    @Query(value
            = "SELECT new com.sirh.sirh.DTO.IncidenciasCardexGeneralDTO("
            + "CataInci.inicial_descripcion, "
            + "Trab.id_trabajador, "
            + "Expe.numero_expediente, "
            + "EXTRACT(DAY FROM (Inci.fecha_fin - Inci.fecha_inicio)) + 1 AS cantidad_incidencias) "
            + "FROM Trabajador_plaza AS Plaza "
            + "JOIN Trabajador AS Trab ON Plaza.trabajador_id = Trab.id_trabajador "
            + "JOIN Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente "
            + "INNER JOIN Incidencias AS Inci ON Inci.trabajador_id = Trab.id_trabajador "
            + "AND FUNCTION('DATE', Inci.fecha_inicio) >= :fecha_inicial AND FUNCTION('DATE', Inci.fecha_fin) <= :fecha_final "
            //--Faltas, R.A, R.B, R.C, EG50, EG60, EG75, EG100, RT100, PRE100, EN60, EN100, ET25, ET50, EG0, RT0, PRE0, S
            + "AND Inci.cat_incidencias.id_incidencia IN (:incidenciasFiltradas)"
            + "LEFT JOIN Cat_Incidencias AS CataInci ON Inci.cat_incidencias.id_incidencia = CataInci.id_incidencia "
            + "WHERE Plaza.cat_Tipo_Nomina.id_tipo_nomina = :idNomina "
            + "AND (:expediente IS NULL OR Expe.numero_expediente = :expediente OR :expediente = '') "
            + "GROUP BY CataInci.inicial_descripcion, Trab.id_trabajador, Expe.numero_expediente, Inci.fecha_inicio, Inci.fecha_fin"
    )
    List<IncidenciasCardexGeneralDTO> conteoIncidenciasKardexExpediente(@Param("fecha_inicial") LocalDateTime fecha_inicial,
            @Param("fecha_final") LocalDateTime fecha_final,
            @Param("idNomina") Integer idNomina,
            @Param("incidenciasFiltradas") List<Integer> incidenciasFiltradas,
            @Param("expediente") String expediente);

    @Query(value
            = "SELECT new com.sirh.sirh.DTO.IncidenciasCardexGeneralDTO(\n"
            + "  ci.inicial_descripcion,\n"
            + "  t.id_trabajador,\n"
            + "  ce.numero_expediente,\n"
            + "  EXTRACT(DAY FROM (i.fecha_fin - i.fecha_inicio)) + 1 AS cantidad_incidencias\n"
            + ")\n"
            + "FROM Incidencias AS i\n"
            + "JOIN Trabajador AS t ON i.trabajador_id = t.id_trabajador\n"
            + "JOIN Cat_Expediente AS ce ON t.cat_expediente.id_expediente = ce.id_expediente\n"
            + "JOIN Cat_Incidencias AS ci ON i.cat_incidencias.id_incidencia = ci.id_incidencia\n"
            + "JOIN Trabajador_plaza AS tp ON t.id_trabajador = tp.trabajador_id\n"
            + "WHERE tp.cat_Tipo_Nomina.id_tipo_nomina = :idNomina\n"
            + "AND i.cat_incidencias.id_incidencia IN (:incidenciasFiltradas)\n"
            + "AND FUNCTION('DATE', i.fecha_inicio) >= :fecha_inicial AND FUNCTION('DATE', i.fecha_fin) <= :fecha_final \n"
            + "GROUP BY\n"
            + "  ci.inicial_descripcion,\n"
            + "  t.id_trabajador,\n"
            + "  ce.numero_expediente,\n"
            + "  i.fecha_inicio,\n"
            + "  i.fecha_fin"
    )
    List<IncidenciasCardexGeneralDTO> conteoIncidenciasKardexGeneral(@Param("fecha_inicial") LocalDateTime fecha_inicial,
            @Param("fecha_final") LocalDateTime fecha_final,
            @Param("idNomina") Integer idNomina,
            @Param("incidenciasFiltradas") List<Integer> incidenciasFiltradas);

    @Query(value
            = "SELECT new com.sirh.sirh.DTO.IncidenciasCardexGeneralDTO("
            + "CataInci.inicial_descripcion, "
            + "Trab.id_trabajador, "
            + "Expe.numero_expediente, "
            + "EXTRACT(DAY FROM (Inci.fecha_fin - Inci.fecha_inicio)) + 1 AS cantidad_incidencias) "
            + "FROM Trabajador_plaza AS Plaza "
            + "JOIN Trabajador AS Trab ON Plaza.trabajador_id = Trab.id_trabajador "
            + "JOIN Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente "
            + "INNER JOIN Incidencias AS Inci ON Inci.trabajador_id = Trab.id_trabajador "
            + "AND FUNCTION('DATE', Inci.fecha_inicio) >= :fecha_inicial AND FUNCTION('DATE', Inci.fecha_fin) <= :fecha_final "
            //--Faltas, R.A, R.B, R.C, EG50, EG60, EG75, EG100, RT100, PRE100, EN60, EN100, ET25, ET50, EG0, RT0, PRE0, S
            + "AND Inci.cat_incidencias.id_incidencia IN (1, 36, 37, 27, 16, 14, 31, 15, 26, 23, 24, 25, 2, 32, 35, 5, 21, 6, 3, 38, 4, 13, 19, 30, 22, 33, 29, 18, 34, 10, 11, 12, 28, 17, 9, 20, 7, 8)"
            + "LEFT JOIN Cat_Incidencias AS CataInci ON Inci.cat_incidencias.id_incidencia = CataInci.id_incidencia "
            + "WHERE Plaza.trabajador_id = :trabajador_id "
            + "GROUP BY CataInci.inicial_descripcion, Trab.id_trabajador, Expe.numero_expediente, Inci.fecha_inicio, Inci.fecha_fin"
    )
    List<IncidenciasCardexGeneralDTO> conteoIncidenciasKardexTrabajador(@Param("fecha_inicial") LocalDate fecha_inicial,
            @Param("fecha_final") LocalDate fecha_final,
            @Param("trabajador_id") Integer trabajador_id);

    @Query(value
            = "SELECT new com.sirh.sirh.DTO.IncidenciasSuaDTO(\n"
            + "Per.num_imss,\n"
            + "Inci.fecha_inicio )\n"
            + "FROM Incidencias AS Inci\n"
            + "JOIN Trabajador AS Trab ON Inci.trabajador_id = id_trabajador\n"
            + "JOIN Persona AS Per ON Trab.persona.id_persona = id_persona\n"
            + "WHERE DATE(Inci.fecha_inicio) >= :desde AND DATE(Inci.fecha_fin) <= :hasta\n"
            //Incapacidades activas
            + "AND estatus = 1\n"
            //Incapacidades Autorizadas
            + "AND estado_incidencia_id = 1\n"
            //Que correspondan a faltas 
            + "AND incidencia_id = 2"
    )
    List<IncidenciasSuaDTO> generarTXTInasistenciasSUA(@Param("desde") Date desde, @Param("hasta") Date hasta);

    @Query(value
            = "SELECT new com.sirh.sirh.DTO.Incidencias_RA_TransportacionDTO(\n"
            + "Cata.inicial_descripcion,\n"
            + "Cata.descripcion,\n"
            + "Inci.fecha_inicio,\n"
            + "Inci.fecha_fin,\n"
            + "Inci.num_dias,\n"
            + "Inci.observaciones )\n"
            + "FROM Incidencias AS Inci\n"
            + "JOIN Cat_Incidencias AS Cata ON Inci.cat_incidencias.id_incidencia = Cata.id_incidencia\n"
            + "WHERE trabajador_id = :trabajador_id\n"
            + "AND ("
            + "(DATE(Inci.fecha_inicio) BETWEEN :inicio_periodo_pago AND :fin_periodo_pago) OR "
            + "(DATE(Inci.fecha_fin) BETWEEN :inicio_periodo_pago AND :fin_periodo_pago) OR "
            + "(:inicio_periodo_pago BETWEEN DATE(Inci.fecha_inicio) AND DATE(Inci.fecha_fin)) OR "
            + "(:fin_periodo_pago BETWEEN DATE(Inci.fecha_inicio) AND DATE(Inci.fecha_fin))"
            + ") "
            //Que sean incidencias activas
            + "AND  Inci.estatus = 1\n"
            //Que sean incidencias autorizadas
            + "AND Inci.estado_incidencia_id = 1\n"
            //Que sean incicencias que se muestran en el kardex
            + "AND Cata.estatus_kardex = 1\n"
            + "ORDER BY fecha_inicio DESC"
    )
    List<Incidencias_RA_TransportacionDTO> obtenerIncidenciasRATransportacion(@Param("inicio_periodo_pago") Date inicio_periodo_pago, @Param("fin_periodo_pago") Date fin_periodo_pago, @Param("trabajador_id") Integer trabajador_id);

    //********************* ENCONTRAR A LA INCIDENCIA SIN CONTRATO POR TRABAJADOR*****************************
    @Query(value = "SELECT * FROM incidencias WHERE trabajador_id =:trabajador_id AND incidencia_id = 20", nativeQuery = true)
    Incidencias findSinContratoTrabajador(@Param("trabajador_id") Integer trabajador_id);
}
