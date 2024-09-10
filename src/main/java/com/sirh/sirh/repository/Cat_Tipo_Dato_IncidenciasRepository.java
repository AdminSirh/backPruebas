/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Tipo_Dato_Incidencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rrosero23
 */
@Repository
public interface Cat_Tipo_Dato_IncidenciasRepository extends JpaRepository<Cat_Tipo_Dato_Incidencias, Integer> {

}
