/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Dias_Festivos;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rrosero23
 */
@Repository
public interface Cat_Dias_FestivosRepository extends JpaRepository<Cat_Dias_Festivos, Integer> {

    @Query(value = "SELECT * FROM catalogo_dias_festivos WHERE nomina_id =:nomina_id", nativeQuery = true)
    List<Cat_Dias_Festivos> findDiasNomina(@Param("nomina_id") Integer nomina_id);

    @Query(value = "SELECT * FROM catalogo_dias_festivos WHERE nomina_id =:nomina_id AND date(fecha) = :fecha", nativeQuery = true)
    List<Cat_Dias_Festivos> findDiasFestivos(@Param("nomina_id") Integer nomina_id, @Param("fecha") Date fecha);
    

}
