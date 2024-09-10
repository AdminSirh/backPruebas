/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Dias_Vacaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oguerrero23
 */
@Repository
public interface Cat_Dias_VacacionesRepository extends JpaRepository<Cat_Dias_Vacaciones,Integer>{
    @Query(value = "SELECT dias FROM catalogo_dias_vacaciones sm WHERE sm.tipo_dias_vacaciones_id = :tipo and :antiguedad BETWEEN sm.anios_inicio and sm.anios_fin", nativeQuery = true)
    Integer diasDisponibles(@Param("tipo") Integer tipo_dias_vacaciones_id,@Param("antiguedad") Double antiguedad);
}
