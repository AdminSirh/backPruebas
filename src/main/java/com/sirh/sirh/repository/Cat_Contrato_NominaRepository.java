/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;
import com.sirh.sirh.entity.Cat_Areas;
import com.sirh.sirh.entity.Cat_Contrato_Nomina;
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
public interface Cat_Contrato_NominaRepository extends JpaRepository<Cat_Contrato_Nomina, Integer>{
    @Query(value = "SELECT * FROM catalogo_tipo_contrato_nomina c WHERE c.tipo_contrato_id = :tipo_contrato_id", nativeQuery = true)
    List <Cat_Contrato_Nomina> listaIdContrato(@Param("tipo_contrato_id") Integer tipo_contrato_id);
}
