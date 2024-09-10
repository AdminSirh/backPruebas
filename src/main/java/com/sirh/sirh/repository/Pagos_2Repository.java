/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Pagos_2;
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
public interface Pagos_2Repository extends JpaRepository <Pagos_2, Integer>{
    
    @Query(value = "SELECT c_e.numero_expediente, pe.nombre, pe.apellido_paterno, pe.apellido_materno, cpu.sueldo_diario, cpu.puesto, p_2.id_pago_2, tr.id_trabajador\n" +
                    "FROM pagos_2 p_2\n" +
                    "JOIN trabajador tr ON tr.id_trabajador = p_2.trabajador_id\n" +
                    "JOIN catalogo_expedientes c_e ON c_e.id_expediente = tr.expediente_id\n" +
                    "JOIN persona pe ON pe.id_persona = tr.persona_id\n" +
                    "JOIN trabajador_plaza tp ON tp.trabajador_id = p_2.trabajador_id\n" +
                    "JOIN catalogo_plaza cp ON cp.id_plaza = tp.plaza_id\n" +
                    "JOIN catalogo_puesto cpu ON cpu.id_puesto = cp.puesto_id\n" +
                    "WHERE p_2.periodo_aplicacion = :periodo_aplicacion", nativeQuery = true)
    List<Object> consultaPagosTransportacion(@Param("periodo_aplicacion") Integer periodo_aplicacion);
    
    @Query(value = "SELECT SUM(pp.valor) FROM pagos_2 pa\n" +
                   "JOIN pagos_percepciones_2 pp ON pa.id_pago_2 = pp.pago_2_id\n" +
                   "WHERE (pa.periodo_aplicacion = :primer_periodo OR pa.periodo_aplicacion = :segundo_periodo) " +
                   "AND pp.cat_incidencia_id = :incidencia_id AND pa.trabajador_id = :trabajador_id ", nativeQuery = true)
    Double obtenSumaIncidenciasTransportacion(@Param("primer_periodo") Integer primer_periodo, @Param("segundo_periodo") Integer segundo_periodo, @Param("incidencia_id") Integer incidencia_id, @Param("trabajador_id") Integer trabajador_id);
    
    @Query(value = "SELECT SUM(pp.valor) FROM pagos_2 pa\n" +
                   "JOIN pagos_percepciones_2 pp ON pa.id_pago_2 = pp.pago_2_id\n" +
                   "WHERE (pa.periodo_aplicacion = :primer_periodo) " +
                   "AND pp.cat_incidencia_id = :incidencia_id AND pa.trabajador_id = :trabajador_id ", nativeQuery = true)
    Double obtenIncidenciaTransportacion(@Param("primer_periodo") Integer primer_periodo, @Param("incidencia_id") Integer incidencia_id, @Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT SUM(pp.monto) FROM pagos_2 pa\n" +
                    "JOIN pagos_calculos_deduccion_2 pp ON pp.pago_2_id = pa.id_pago_2\n" +
                    "WHERE (pa.periodo_aplicacion = :primer_periodo OR pa.periodo_aplicacion = :segundo_periodo)\n" +
                    "AND pp.cat_incidencia_id = :incidencia_id AND pa.trabajador_id = :trabajador_id", nativeQuery = true)
    Double obtenSumaISRTransportacion(@Param("primer_periodo") Integer primer_periodo, @Param("segundo_periodo") Integer segundo_periodo, @Param("incidencia_id") Integer incidencia_id, @Param("trabajador_id") Integer trabajador_id);
    
    @Query(value = "SELECT SUM(pp.monto) FROM pagos_2 pa\n" +
                    "JOIN pagos_calculos_deduccion_2 pp ON pp.pago_2_id = pa.id_pago_2\n" +
                    "WHERE (pa.periodo_aplicacion = :primer_periodo)\n" +
                    "AND pp.cat_incidencia_id = :incidencia_id AND pa.trabajador_id = :trabajador_id", nativeQuery = true)
    Double obtenSumaISRTransportacion88(@Param("primer_periodo") Integer primer_periodo, @Param("incidencia_id") Integer incidencia_id, @Param("trabajador_id") Integer trabajador_id);
    
}
