/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Periodos_Pago;
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
public interface Periodos_PagoRepository extends JpaRepository<Periodos_Pago, Integer> {

    @Query(value = "SELECT periodo FROM periodos_pago c WHERE :fecha_inicio BETWEEN date(c.fecha_inicial) AND date(c.fecha_final) AND c.id_nomina = :id_nomina AND c.pagado = 0 AND c.especial=0 AND c.dias_incluidos < 20", nativeQuery = true)
    Integer findPeriodoPago(@Param("id_nomina") Integer id_nomina, @Param("fecha_inicio") Date fecha_inicio);

    @Query(value = "SELECT * FROM periodos_pago c WHERE c.id_nomina = :id_nomina AND c.pagado = 0 AND c.especial = 0 AND c.aguinaldo = 0 order by c.fecha_inicial asc", nativeQuery = true)
    List<Periodos_Pago> findPeriodosFechas(@Param("id_nomina") Integer id_nomina);

    @Query(value = "SELECT * FROM periodos_pago c WHERE c.id_nomina = :id_nomina AND c.especial = 0 order by c.fecha_inicial asc", nativeQuery = true)
    List<Periodos_Pago> findAllPeriodosPagoByNomina(@Param("id_nomina") Integer id_nomina);

    @Query(value = "SELECT * FROM periodos_pago c WHERE c.id_periodo_pago = :id_periodo_pago", nativeQuery = true)
    Periodos_Pago findOnePeriodo(@Param("id_periodo_pago") Integer id_periodo_pago);

    @Query(value = "SELECT * FROM periodos_pago c WHERE :fecha_corte = date(c.fecha_corte) AND c.id_nomina = :id_nomina AND c.pagado = 0 and c.especial=0", nativeQuery = true)
    List<Periodos_Pago> findPeriodoFechaCorteAndTipoNomina(@Param("id_nomina") Integer id_nomina, @Param("fecha_corte") Date fecha_corte);

    @Query(value = "SELECT * FROM periodos_pago WHERE id_nomina =:id_nomina AND especial != 0 AND pagado = 0 AND aguinaldo = 0 AND dias_incluidos < 360", nativeQuery = true)
    List<Periodos_Pago> findPeriodosVales(@Param("id_nomina") Integer id_nomina);


    @Query(value = "SELECT * FROM periodos_pago WHERE id_nomina = :id_nomina AND aguinaldo = 1 AND pagado = 0", nativeQuery = true)
    List<Periodos_Pago> findPeriodosAguinaldo(@Param("id_nomina") Integer id_nomina);

//    @Query(value = "SELECT *\n"
//            + "FROM periodos_pago c\n"
//            + "WHERE c.periodo = :periodoPago\n"
//            //            + "  AND c.pagado = 0\n"
//            + "  AND c.especial = 0\n"
//            // + "  AND c.fecha_corte >= CURRENT_DATE\n"
//            + "  AND c.id_nomina = :id_nomina\n"
//            + "ORDER BY c.fecha_corte\n"
//            + "LIMIT 1;", nativeQuery = true)
    
    @Query(value = "SELECT * FROM periodos_pago c\n" +
                    "WHERE c.periodo = :periodoPago\n" +
                    "AND c.pagado = 0\n" +
                    "AND c.id_nomina = :id_nomina\n" +
                    "ORDER BY c.fecha_corte\n" +
                    "LIMIT 1;", nativeQuery = true)
    Periodos_Pago findOnePeriodoPago(@Param("periodoPago") Integer periodoPago, @Param("id_nomina") Integer id_nomina);

    @Query(value = "SELECT periodo\n"
            + "FROM public.periodos_pago\n"
            + "WHERE id_nomina = :idNomina\n"
            + "AND especial = 0\n"
            + "AND aguinaldo = 0\n"
            + "AND (date(fecha_inicial) <= :fechaFin AND date(fecha_final) >= :fechaInicio) \n"
            + "ORDER BY periodo ASC", nativeQuery = true)
    List<Integer> findLapsoPeriodos(@Param("idNomina") Integer idNomina, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    @Query(value = "WITH RECURSIVE semanas AS (\n"
            + "  SELECT\n"
            + "    generate_series(\n"
            + "      (SELECT MIN(fecha_inicial) FROM periodos_pago WHERE id_nomina = 3 AND EXTRACT(YEAR FROM fecha_inicial) = EXTRACT(YEAR FROM CURRENT_DATE)),\n"
            + "      (SELECT MAX(fecha_final) FROM periodos_pago WHERE id_nomina = 3 AND EXTRACT(YEAR FROM fecha_final) = EXTRACT(YEAR FROM CURRENT_DATE)),\n"
            + "      interval '1 day'\n"
            + "    ) AS fecha\n"
            + "    ORDER BY fecha ASC\n"
            + ")\n"
            + "SELECT \n"
            + "  fecha AS fecha_inicial_semana,\n"
            + "  fecha + (7 - EXTRACT(DOW FROM fecha)) * interval '1 day' AS fecha_final_semana\n" //Terminando el día domingo
            + "FROM semanas\n"
            + "WHERE\n"
            + "  EXTRACT(DOW FROM fecha) = 1\n" // Día lunes que comienza el periodo de confianza
            + "ORDER BY fecha_inicial_semana;", nativeQuery = true)
    List<Object[]> obtenerFechasSemanalesConfianza();

    @Query(value = "WITH RECURSIVE semanas AS (\n"
            + "  SELECT\n"
            + "    generate_series(\n"
            + "      (SELECT MIN(fecha_inicial) FROM periodos_pago WHERE id_nomina = 1 AND EXTRACT(YEAR FROM fecha_inicial) = EXTRACT(YEAR FROM CURRENT_DATE)),\n"
            + "      (SELECT MAX(fecha_final) FROM periodos_pago WHERE id_nomina = 1 AND EXTRACT(YEAR FROM fecha_final) = EXTRACT(YEAR FROM CURRENT_DATE)),\n"
            + "      interval '1 day'\n"
            + "    ) AS fecha\n"
            + "    ORDER BY fecha ASC\n"
            + ")\n"
            + "SELECT \n"
            + "  fecha AS fecha_inicial_semana,\n"
            + "  fecha + (9 - EXTRACT(DOW FROM fecha)) * interval '1 day' AS fecha_final_semana\n" //Terminando el día Martes
            + "FROM semanas\n"
            + "WHERE\n"
            + "  EXTRACT(DOW FROM fecha) = 3\n" // Día Mércoles que comienza el periodo de Varios
            + "ORDER BY fecha_inicial_semana;", nativeQuery = true)
    List<Object[]> obtenerFechasSemanalesVarios();

    @Query(value = "WITH RECURSIVE semanas AS (\n"
            + "  SELECT\n"
            + "    generate_series(\n"
            + "      (SELECT MIN(fecha_inicial) FROM periodos_pago WHERE id_nomina = 2 AND EXTRACT(YEAR FROM fecha_inicial) = EXTRACT(YEAR FROM CURRENT_DATE)),\n"
            + "      (SELECT MAX(fecha_final) FROM periodos_pago WHERE id_nomina = 2 AND EXTRACT(YEAR FROM fecha_final) = EXTRACT(YEAR FROM CURRENT_DATE)),\n"
            + "      interval '1 day'\n"
            + "    ) AS fecha\n"
            + "    ORDER BY fecha ASC\n"
            + ")\n"
            + "SELECT \n"
            + "  fecha AS fecha_inicial_semana,\n"
            + "  fecha + (6 - EXTRACT(DOW FROM fecha)) * interval '1 day' AS fecha_final_semana\n" //Terminando el día Sábado
            + "FROM semanas\n"
            + "WHERE\n"
            + "  EXTRACT(DOW FROM fecha) = 0\n" // Día Domingo que comienza el periodo de Transpórtación
            + "ORDER BY fecha_inicial_semana;", nativeQuery = true)
    List<Object[]> obtenerFechasSemanalesTransportacion();

    @Query(value = "SELECT *\n"
            + "FROM public.periodos_pago\n"
            + "WHERE (fecha_final <= CURRENT_DATE OR fecha_inicial <= CURRENT_DATE)\n"
            + "  AND id_nomina = :id_nomina\n"
            + "  AND especial = 0\n"
            + "ORDER BY fecha_final DESC\n"
            + "LIMIT 4;", nativeQuery = true)
    List<Periodos_Pago> obtenerPeriodosGeneracionCercanos(@Param("id_nomina") Integer id_nomina);

    @Query(value = "SELECT ctn.desc_nomina FROM periodos_pago pp JOIN catalogo_tipo_nomina ctn ON ctn.id_tipo_nomina = pp.id_nomina GROUP BY ctn.desc_nomina", nativeQuery = true)
    List<String> findNominasCargadas();

    @Query(value = "SELECT * FROM periodos_pago c WHERE c.id_nomina = :nomina_id AND c.especial = 0 AND c.aguinaldo = 0 \n"
            + "ORDER BY c.fecha_inicial ASC", nativeQuery = true)
    List<Periodos_Pago> listarAllPeriodos(@Param("nomina_id") Integer nomina_id);

    @Query(value = "SELECT DISTINCT ON (id_nomina)\n"
            + "Nomina.desc_nomina,\n"
            + "mes,\n"
            + "fecha_inicial,\n"
            + "fecha_final,\n"
            + "fecha_corte,\n"
            + "periodo\n"
            + "FROM periodos_pago AS Periodo\n"
            + "JOIN catalogo_tipo_nomina AS Nomina ON Periodo.id_nomina = Nomina.id_tipo_nomina\n"
            + "WHERE pagado = 0\n"
            + "AND aguinaldo = 0\n"
            + "AND especial = 0\n"
            //+ "AND CURRENT_DATE BETWEEN fecha_inicial AND fecha_final\n"
            + "AND EXTRACT(MONTH FROM fecha_corte) = EXTRACT(MONTH FROM CURRENT_DATE)\n"
            + "AND EXTRACT(YEAR FROM fecha_corte) = EXTRACT(YEAR FROM CURRENT_DATE)\n"
            + "ORDER BY id_nomina ASC; ",
            nativeQuery = true)
    List<Object[]> findPeriodosProximosSinPagar();

}
