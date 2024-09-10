/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Relacion_Incapacidades_Nomina_Dias;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rroscero23
 */
@Repository
public interface Relacion_Incapacidades_Nomina_DiasRepository extends JpaRepository<Relacion_Incapacidades_Nomina_Dias, Integer> {

    @Query(value = "SELECT *\n"
            + "FROM relacion_incapacidades_nomina_dias\n"
            + "WHERE nomina_id = :id_nomina\n"
            + "AND motivo_incapacidad_id = :motivo_incapacidad_id\n"
            + "AND extemporanea_si_no = :extemporanea_si_no\n"
            //Parámetros opcionales
            + "AND (prestaciones_si_no = :prestaciones_si_no OR :prestaciones_si_no IS NULL)\n"
            + "AND (tipo_incapacidad_id = :tipo_incapacidad_id OR :tipo_incapacidad_id IS NULL)", nativeQuery = true)
    Relacion_Incapacidades_Nomina_Dias findRelacionIncapacidades(@Param("id_nomina") Integer id_nomina,
            @Param("motivo_incapacidad_id") Integer motivo_incapacidad_id,
            @Param("prestaciones_si_no") Integer prestaciones_si_no,
            @Param("tipo_incapacidad_id") Integer tipo_incapacidad_id,
            @Param("extemporanea_si_no") Integer extemporanea_si_no);
}
