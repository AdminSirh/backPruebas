/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Pagos_Percepciones_4;
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
public interface Pagos_Percepciones_4Repository extends JpaRepository<Pagos_Percepciones_4, Integer> {

    @Query(value = "SELECT * FROM pagos_percepciones_4\n"
            + "WHERE pago_4_id =:pago_id", nativeQuery = true)
    List<Pagos_Percepciones_4> findAllPercepciones4(@Param("pago_id") Integer pago_id);

    @Query(value = "SELECT \n"
            //Suma los totales de días con contrato para el trabajador
            + "SUM(pagosPercepcion.valor) \n"
            + "FROM \n"
            + "pagos_percepciones_4 AS pagosPercepcion\n"
            + "JOIN \n"
            + "pagos_4 AS pagoGeneral ON pagosPercepcion.pago_4_id = pagoGeneral.id_pago_4\n"
            + "WHERE \n"
            + "pagoGeneral.anio_aplicacion = :anio\n"
            + "AND \n"
            + "pagoGeneral.trabajador_id = :trabajador_id\n"
            + "AND \n"
            //Incidencia con id 106 con clave de mómina 243 que corresponde al concepto de catalogo_incidencias 'Días con contrato para aguinaldo'
            + "pagosPercepcion.cat_incidencia_id = 106", nativeQuery = true)
    Double findDiasContratoAguinaldo4(@Param("anio") Integer anio, @Param("trabajador_id") Integer trabajador_id);
}
