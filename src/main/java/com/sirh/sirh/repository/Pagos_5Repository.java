/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Pagos_3;
import com.sirh.sirh.entity.Pagos_5;
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
public interface Pagos_5Repository extends JpaRepository <Pagos_5,Integer>{
    
    @Query(value = "SELECT c_e.numero_expediente, pe.nombre, pe.apellido_paterno, pe.apellido_materno, cpu.sueldo_diario, cpu.puesto, p_5.id_pago_5, tr.id_trabajador\n" +
                    "FROM pagos_5 p_5\n" +
                    "JOIN trabajador tr ON tr.id_trabajador = p_5.trabajador_id\n" +
                    "JOIN catalogo_expedientes c_e ON c_e.id_expediente = tr.expediente_id\n" +
                    "JOIN persona pe ON pe.id_persona = tr.persona_id\n" +
                    "JOIN trabajador_plaza tp ON tp.trabajador_id = p_5.trabajador_id\n" +
                    "JOIN catalogo_plaza cp ON cp.id_plaza = tp.plaza_id\n" +
                    "JOIN catalogo_puesto cpu ON cpu.id_puesto = cp.puesto_id\n" +
                    "WHERE p_5.periodo_aplicacion = :periodo_aplicacion", nativeQuery = true)
    List<Object> consultaPagosEstructura(@Param("periodo_aplicacion") Integer periodo_aplicacion);
    
    @Query(value = "SELECT SUM(pp.valor) FROM pagos_5 pa\n" +
                   "JOIN pagos_percepciones_5 pp ON pa.id_pago_5 = pp.pago_5_id\n" +
                   "WHERE (pa.periodo_aplicacion = :primer_periodo OR pa.periodo_aplicacion = :segundo_periodo) " +
                   "AND pp.cat_incidencia_id = :incidencia_id AND pa.trabajador_id = :trabajador_id ", nativeQuery = true)
    Double obtenSumaIncidenciasEstructura(@Param("primer_periodo") Integer primer_periodo, @Param("segundo_periodo") Integer segundo_periodo, @Param("incidencia_id") Integer incidencia_id, @Param("trabajador_id") Integer trabajador_id);
    
    @Query(value = "SELECT SUM(pp.valor) FROM pagos_5 pa\n" +
                   "JOIN pagos_percepciones_5 pp ON pa.id_pago_5 = pp.pago_5_id\n" +
                   "WHERE (pa.periodo_aplicacion = :primer_periodo) " +
                   "AND pp.cat_incidencia_id = :incidencia_id AND pa.trabajador_id = :trabajador_id ", nativeQuery = true)
    Double obtenIncidenciaEstructura(@Param("primer_periodo") Integer primer_periodo, @Param("incidencia_id") Integer incidencia_id, @Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT SUM(pp.monto) FROM pagos_5 pa\n" +
                    "JOIN pagos_calculos_deduccion_5 pp ON pp.pago_5_id = pa.id_pago_5\n" +
                    "WHERE (pa.periodo_aplicacion = :primer_periodo OR pa.periodo_aplicacion = :segundo_periodo)\n" +
                    "AND pp.cat_incidencia_id = :incidencia_id AND pa.trabajador_id = :trabajador_id", nativeQuery = true)
    Double obtenSumaISREstructura(@Param("primer_periodo") Integer primer_periodo, @Param("segundo_periodo") Integer segundo_periodo, @Param("incidencia_id") Integer incidencia_id, @Param("trabajador_id") Integer trabajador_id);
    
    @Query(value = "SELECT SUM(pp.monto) FROM pagos_5 pa\n" +
                    "JOIN pagos_calculos_deduccion_5 pp ON pp.pago_5_id = pa.id_pago_5\n" +
                    "WHERE (pa.periodo_aplicacion = :primer_periodo)\n" +
                    "AND pp.cat_incidencia_id = :incidencia_id AND pa.trabajador_id = :trabajador_id", nativeQuery = true)
    Double obtenSumaISREstructura88(@Param("primer_periodo") Integer primer_periodo, @Param("incidencia_id") Integer incidencia_id, @Param("trabajador_id") Integer trabajador_id);
    
    @Query(value = "SELECT * FROM pagos_5 WHERE fecha_registro >= :fecha_diciembre AND fecha_registro <= :fecha_noviembre AND trabajador_id = :trabajador_id\n" +
                   "AND (\n" +
                   "SELECT COUNT(DISTINCT puesto_id) FROM pagos_5 WHERE fecha_registro >= :fecha_diciembre AND fecha_registro <= :fecha_noviembre AND trabajador_id = :trabajador_id\n" +
                    ") > 1", nativeQuery = true)
    List<Pagos_5> obtenPuestosDiferentes(@Param("fecha_diciembre") Date fecha_diciembre, @Param("fecha_noviembre") Date fecha_noviembre, @Param("trabajador_id") Integer trabajador_id);
}
