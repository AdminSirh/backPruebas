/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.SDITransportacionDTO;
import com.sirh.sirh.entity.Pagos_Calculos_Percepcion_2;
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
public interface Pagos_Calculos_Percepcion_2Repository extends JpaRepository<Pagos_Calculos_Percepcion_2, Integer> {

    @Query(value = "SELECT * FROM pagos_calculos_percepcion_2 pcp\n"
            + "WHERE pcp.pago_2_id =:pago_id", nativeQuery = true)
    List<Pagos_Calculos_Percepcion_2> findAllCalculosPercepcion2(@Param("pago_id") Integer pago_id);

    @Query(value = "SELECT * FROM sdi_conceptos_transportacion(cast(string_to_array(cast(:periodos_bimestre as varchar) , ',') as integer[]))", nativeQuery = true)
    List<Object[]> conceptos_sdi_transportacion(@Param("periodos_bimestre") String periodos_bimestre);

}
