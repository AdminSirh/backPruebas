/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Registro_Plantilla_Mensual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rroscero23
 */
@Repository
public interface Registro_Plantilla_MensualRepository extends JpaRepository<Registro_Plantilla_Mensual, Integer> {

    @Query(value = "SELECT \n"
            + " CASE \n"
            + "     WHEN EXISTS (\n"
            //Consulta si existe un solo registro en la tabla registro_plantilla_mensual para el mes y año actual
            + "            SELECT 1 \n"
            + "            FROM registro_plantilla_mensual rpm \n"
            + "            WHERE rpm.anio = :anio AND rpm.mes = :mes\n"
            + "        ) \n"
            + "        AND EXISTS (\n"
            //Consulta si existe un solo registro en la tabla tmp_consolidado para el mes y año actual
            + "            SELECT 1 \n"
            + "            FROM registro_plazas_consolidado rpc \n"
            + "            WHERE rpc.anio = :anio AND rpc.mes = :mes\n"
            + "            AND rpc.estatus = 1\n"
            + "        ) \n"
            + "        THEN true \n"
            + "        ELSE false \n"
            + "    END AS existe_en_ambas_tablas; ",
            nativeQuery = true)
    Boolean boolEjecutoCierreMensual(@Param("anio") Integer anio, @Param("mes") Integer mes);
}
