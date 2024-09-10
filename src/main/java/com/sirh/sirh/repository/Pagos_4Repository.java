/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.ConceptosRetroActJubDTO;
import com.sirh.sirh.entity.Pagos_4;
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
public interface Pagos_4Repository extends JpaRepository<Pagos_4, Integer> {

    @Query(value = "SELECT c_e.numero_expediente, pe.nombre, pe.apellido_paterno, pe.apellido_materno, cpu.sueldo_diario, cpu.puesto, p_4.id_pago_4, tr.id_trabajador\n"
            + "FROM pagos_4 p_4\n"
            + "JOIN trabajador tr ON tr.id_trabajador = p_4.trabajador_id\n"
            + "JOIN catalogo_expedientes c_e ON c_e.id_expediente = tr.expediente_id\n"
            + "JOIN persona pe ON pe.id_persona = tr.persona_id\n"
            + "JOIN trabajador_plaza tp ON tp.trabajador_id = p_4.trabajador_id\n"
            + "JOIN catalogo_plaza cp ON cp.id_plaza = tp.plaza_id\n"
            + "JOIN catalogo_puesto cpu ON cpu.id_puesto = cp.puesto_id\n"
            + "WHERE p_4.periodo_aplicacion = :periodo_aplicacion", nativeQuery = true)
    List<Object> consultaPagosJubilados(@Param("periodo_aplicacion") Integer periodo_aplicacion);

    @Query(value = "SELECT SUM(pp.valor) FROM pagos_4 pa\n"
            + "JOIN pagos_percepciones_4 pp ON pa.id_pago_4 = pp.pago_4_id\n"
            + "WHERE (pa.periodo_aplicacion = :primer_periodo OR pa.periodo_aplicacion = :segundo_periodo) "
            + "AND pp.cat_incidencia_id = :incidencia_id AND pa.trabajador_id = :trabajador_id ", nativeQuery = true)
    Double obtenSumaIncidenciasJubilados(@Param("primer_periodo") Integer primer_periodo, @Param("segundo_periodo") Integer segundo_periodo, @Param("incidencia_id") Integer incidencia_id, @Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT SUM(pp.valor) FROM pagos_4 pa\n"
            + "JOIN pagos_percepciones_4 pp ON pa.id_pago_4 = pp.pago_4_id\n"
            + "WHERE (pa.periodo_aplicacion = :primer_periodo) "
            + "AND pp.cat_incidencia_id = :incidencia_id AND pa.trabajador_id = :trabajador_id ", nativeQuery = true)
    Double obtenIncidenciaJubilados(@Param("primer_periodo") Integer primer_periodo, @Param("incidencia_id") Integer incidencia_id, @Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT SUM(pp.monto) FROM pagos_4 pa\n"
            + "JOIN pagos_calculos_deduccion_4 pp ON pp.pago_4_id = pa.id_pago_4\n"
            + "WHERE (pa.periodo_aplicacion = :primer_periodo OR pa.periodo_aplicacion = :segundo_periodo)\n"
            + "AND pp.cat_incidencia_id = :incidencia_id AND pa.trabajador_id = :trabajador_id", nativeQuery = true)
    Double obtenSumaISRJubilados(@Param("primer_periodo") Integer primer_periodo, @Param("segundo_periodo") Integer segundo_periodo, @Param("incidencia_id") Integer incidencia_id, @Param("trabajador_id") Integer trabajador_id);


    @Query(value = "SELECT new com.sirh.sirh.DTO.ConceptosRetroActJubDTO( \n"
            + "principalJub.trabajador.id_trabajador,\n"
            + "percepcionesJub.cat_Incidencias.id_incidencia,\n"
            + "principalJub.periodo_aplicacion,\n"
            + "percepcionesJub.valor,\n"
            + "puesto.sueldo_mensual,\n"
            + "puesto.id_puesto)\n"
            + "FROM Pagos_4 AS principalJub\n"
            + "JOIN Pagos_Percepciones_4 AS percepcionesJub  ON principalJub.id_pago_4 = percepcionesJub.pagos_4.id_pago_4\n"
            + "JOIN Cat_Puesto AS puesto  ON principalJub.cat_Puesto.id_puesto = puesto.id_puesto\n"
            + "WHERE principalJub.periodo_aplicacion BETWEEN :periodoInicial AND :periodoFinal\n"
            + "AND principalJub.trabajador.id_trabajador NOT IN (:trabajadoresDescartados)\n"
            + "AND (percepcionesJub.cat_Incidencias.id_incidencia IN (40, 63, 64)\n"
            + "OR (percepcionesJub.cat_Incidencias.id_incidencia = 112 AND percepcionesJub.periodo_generacion >= :periodoInicial))\n")
    List<ConceptosRetroActJubDTO> conceptosRetroactivoJubilados(@Param("periodoInicial") Integer periodoInicial,
            @Param("periodoFinal") Integer periodoFinal,
            List<Integer> trabajadoresDescartados);

    @Query(value = "SELECT new com.sirh.sirh.DTO.ConceptosRetroActJubDTO( \n"
            + "principalJub.trabajador.id_trabajador,\n"
            + "percepcionesCalcJub.cat_Incidencias.id_incidencia,\n"
            + "principalJub.periodo_aplicacion,\n"
            + "percepcionesCalcJub.monto,\n"
            + "puesto.sueldo_mensual,\n"
            + "puesto.id_puesto)\n"
            + "FROM Pagos_4 AS principalJub\n"
            + "JOIN Pagos_Calculos_Percepcion_4 AS percepcionesCalcJub ON principalJub.id_pago_4 = percepcionesCalcJub.pagos_4.id_pago_4\n"
            + "JOIN Cat_Puesto AS puesto ON principalJub.cat_Puesto.id_puesto = puesto.id_puesto\n"
            + "WHERE principalJub.periodo_aplicacion BETWEEN :periodoInicial AND :periodoFinal\n"
            + "AND principalJub.periodo_aplicacion = percepcionesCalcJub.periodo_generacion\n"
            //Incidencias correspondientes a calculos de percepciones, claves nómina: 1,2,29,251,30
            + "AND percepcionesCalcJub.cat_Incidencias.id_incidencia  IN (220, 40, 245, 286, 246)\n"
            + "AND principalJub.trabajador.id_trabajador NOT IN (:trabajadoresDescartados)"
    )
    List<ConceptosRetroActJubDTO> calculosRetroactivoJubilados(@Param("periodoInicial") Integer periodoInicial,
            @Param("periodoFinal") Integer periodoFinal,
            List<Integer> trabajadoresDescartados);

    
    @Query(value = "SELECT SUM(pp.monto) FROM pagos_4 pa\n" +
                    "JOIN pagos_calculos_deduccion_4 pp ON pp.pago_4_id = pa.id_pago_4\n" +
                    "WHERE (pa.periodo_aplicacion = :primer_periodo)\n" +
                    "AND pp.cat_incidencia_id = :incidencia_id AND pa.trabajador_id = :trabajador_id", nativeQuery = true)
    Double obtenSumaISRJubilados88(@Param("primer_periodo") Integer primer_periodo, @Param("incidencia_id") Integer incidencia_id, @Param("trabajador_id") Integer trabajador_id);
    

}
