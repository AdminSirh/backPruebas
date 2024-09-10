/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Vacaciones;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oguerrero23
 */
@Repository
public interface VacacionesRepository extends JpaRepository<Vacaciones, Integer> {

    //OBTENER LOS DIAS DISPONIBLES DE VACACIONES BUSCANDO POR EL ULTIMO REGISTRO DEL PERIODO VACACIONAL
    @Query(value = "SELECT dias_disponibles FROM vacaciones WHERE id_vacaciones = (SELECT Max(id_vacaciones) FROM vacaciones WHERE periodo_vacacional = :periodo_vacacional and trabajador_id = :trabajador_id and motivo_cancelacion is null)", nativeQuery = true)
    Integer buscaPeriodoVacacional(@Param("periodo_vacacional") Double periodo_vacacional, @Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT * FROM vacaciones c WHERE c.trabajador_id = :trabajador_id and c.incidencias_id = :incidencias_id", nativeQuery = true)
    Vacaciones findVacacionesTrabajador(@Param("trabajador_id") Integer trabajador_id, @Param("incidencias_id") Integer incidencias_id);

    @Query(value = "SELECT fecha_fin FROM vacaciones WHERE trabajador_id = :trabajador_id ORDER BY fecha_fin DESC LIMIT 1", nativeQuery = true)
    Date buscaUltimaFechaVacaciones(@Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT \n"
            + "dias_disponibles\n"
            + "FROM vacaciones\n"
            + "WHERE trabajador_id = :trabajador_id\n"
            //Su estado debe ser PAGADA en la tabla de catalogo_estado_vacaciones 
            + "AND estado_vacaciones_id = 7\n"
            //Ordenado por el último periodo de aplicación encontrado
            + "ORDER BY periodo_aplicacion DESC\n"
            //Limitando a un solo resultado
            + "LIMIT 1;", nativeQuery = true)
    Integer buscarDiasVacDisponibles(@Param("trabajador_id") Integer trabajador_id);

}
