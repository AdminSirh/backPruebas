/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Contrato_Nomina;
import com.sirh.sirh.entity.Cat_Nomina_Ubicacion;
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
public interface Cat_Nomina_UbicacionRepository extends JpaRepository<Cat_Nomina_Ubicacion, Integer>{
    @Query(value = "SELECT * FROM catalogo_tipo_nomina_ubicacion c WHERE c.tipo_nomina_id = :tipo_nomina_id", nativeQuery = true)
    List <Cat_Nomina_Ubicacion> listaIdNomina(@Param("tipo_nomina_id") Integer tipo_nomina_id);
}
