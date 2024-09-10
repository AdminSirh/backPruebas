package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Impuestos_Nomina;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nreyes22
 */
@Repository
public interface Cat_Impuestos_NominaRepository extends JpaRepository<Cat_Impuestos_Nomina, Integer> {

    //************ CONSULTA PARA CONTAR LOS IMPUESTOS MEDIANTE EL ESTATUS Y AÑO ***********
    @Query(value = "SELECT count(*)  FROM catalogo_impuestos_nomina WHERE estatus = 1  and anio=:anio and impuesto_concepto_id=1 AND  periodo= '1'", nativeQuery = true)
    Integer findImpuestosStatus(@Param("anio") Integer anio);

    //************ CONSULTA PARA LISTAR LOS REGISTROS ANUALES CON ESTATUS 1 Y CONCEPTO IGUAL A IMPUESTO ***********
    @Query(value = "SELECT * FROM catalogo_impuestos_nomina WHERE anio=:anio AND periodo='1' AND estatus=1 AND impuesto_concepto_id=1", nativeQuery = true)
    List<Cat_Impuestos_Nomina> listadoImpuestosAnuales(@Param("anio") Integer anio);

    @Query(value = "SELECT * FROM public.catalogo_impuestos_nomina\n"
            + "WHERE anio = :anio\n"
            + "AND impuesto_concepto_id = 1\n"
            + "AND periodo = '1'\n"
            + "ORDER BY limite_inferior ASC", nativeQuery = true)
    List<Cat_Impuestos_Nomina> findIsptAnual(@Param("anio") Integer anio);
}
