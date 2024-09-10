/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Percepciones_Por_Nomina;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author rrosero23
 */
@Repository
public interface Percepciones_Por_NominaRepository extends JpaRepository<Percepciones_Por_Nomina, Integer> {

    @Query(value = "SELECT \n"
            + "*\n"
            + "FROM percepciones_por_nomina\n"
            + "WHERE tipo_nomina_id = :id_nomina \n", nativeQuery = true)
    List<Percepciones_Por_Nomina> findPercepcionesNomina(@Param("id_nomina") Integer id_nomina);

}
