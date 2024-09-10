/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Propiedades_Nomina;
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
public interface Propiedades_NominaRepository extends JpaRepository<Propiedades_Nomina, Integer> {

    @Query(value = "SELECT * \n"
            + "FROM propiedades_por_nomina\n"
            + "WHERE nomina_id = :nomina_id", nativeQuery = true)
    List<Propiedades_Nomina> findPropiedadesNomina(@Param("nomina_id") Integer nomina_id);

    @Query(value = "SELECT valor \n"
            + "FROM propiedades_por_nomina \n"
            + "WHERE incidencia_id = 217 \n"
            + "AND nomina_id = :id_nomina", nativeQuery = true)
    Double findSalMinFiniquito(@Param("id_nomina") Integer id_nomina);

    @Query(value = "SELECT valor \n"
            + "FROM propiedades_por_nomina \n"
            + "WHERE incidencia_id = 194 \n"
            + "AND nomina_id = :id_nomina", nativeQuery = true)
    Double findProporcionPrimaVacacional(@Param("id_nomina") Integer id_nomina);

    @Query(value = "SELECT valor \n"
            + "FROM propiedades_por_nomina \n"
            + "WHERE incidencia_id = 186 \n"
            + "AND nomina_id = :id_nomina", nativeQuery = true)
    Double findSalMinimo(@Param("id_nomina") Integer id_nomina);

    @Query(value = "SELECT valor \n"
            + "FROM propiedades_por_nomina \n"
            + "WHERE incidencia_id = 196 \n"
            + "AND nomina_id = :id_nomina", nativeQuery = true)
    Double findDiasAguinaldo(@Param("id_nomina") Integer id_nomina);

    @Query(value = "SELECT valor \n"
            + "FROM propiedades_por_nomina \n"
            + "WHERE incidencia_id = 187 \n"
            + "AND nomina_id = :id_nomina", nativeQuery = true)
    Double findDiasAnio(@Param("id_nomina") Integer id_nomina);

    @Query(value = "SELECT valor \n"
            + "FROM propiedades_por_nomina \n"
            + "WHERE incidencia_id = :id_incidencia \n"
            + "AND nomina_id = :id_nomina", nativeQuery = true)
    Double findPropiedadNomina(@Param("id_incidencia") Integer id_incidencia, @Param("id_nomina") Integer id_nomina);

}
