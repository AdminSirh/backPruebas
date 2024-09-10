/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.Pension_AlimenticiaDTO;
import com.sirh.sirh.entity.Pension_Alimenticia_B;
import com.sirh.sirh.entity.Pension_Alimenticia_OJ;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author rrosero23
 */
/**
 * *****************ORDEN JUDICIAL******************************
 */
public interface Pension_Alimenticia_OJRepository extends JpaRepository<Pension_Alimenticia_OJ, Integer> {

    @Query(value = "SELECT * FROM pension_a_orden_judicial WHERE trabajador_id =:trabajador_id AND estatus = true", nativeQuery = true)
    List<Pension_Alimenticia_OJ> findTrabajadorOrdenJudicial(@Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT COUNT(*) FROM pension_a_orden_judicial WHERE trabajador_id = :trabajador_id AND estatus = true;", nativeQuery = true)
    Integer cuentaPensionesTrabajador(@Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT * FROM pension_a_orden_judicial WHERE id_pension =:id_pension", nativeQuery = true)
    List<Pension_Alimenticia_OJ> findTrabajadorPensionA(@Param("id_pension") Integer id_pension);

    @Query(value = "SELECT * FROM pension_a_orden_judicial WHERE trabajador_id =:trabajador_id AND estatus = false", nativeQuery = true)
    List<Pension_Alimenticia_OJ> findTrabajadorOrdenJudicialHistoricoInactivo(@Param("trabajador_id") Integer trabajador_id);

//    @Query(value = "SELECT * FROM pension_a_orden_judicial WHERE estatus = true", nativeQuery = true)
//    List<Pension_Alimenticia_OJ> finAllPensionesActivas();
    @Query(value = "SELECT new com.sirh.sirh.DTO.Pension_AlimenticiaDTO( \n"
            + "OrdenJudicial.id_pension,\n"
            + "OrdenJudicial.oficio,\n"
            + "OrdenJudicial.Pension_Alimenticia_B.nombre,\n"
            + "OrdenJudicial.Pension_Alimenticia_B.apellido_paterno,\n"
            + "OrdenJudicial.Pension_Alimenticia_B.apellido_materno, \n"
            + "Trabajador.id_trabajador,\n"
            + "Trabajador.persona.nombre AS nombreT,\n"
            + "Trabajador.persona.apellido_paterno AS apellidoPatT,\n"
            + "Trabajador.persona.apellido_materno AS apellidoMatT )\n"
            + "FROM Pension_Alimenticia_OJ AS OrdenJudicial\n"
            + "JOIN Pension_Alimenticia_B AS Beneficiario ON OrdenJudicial.Pension_Alimenticia_B.id_beneficiario_pa = Beneficiario.id_beneficiario_pa\n"
            + "JOIN Trabajador AS Trabajador ON OrdenJudicial.trabajador_id = Trabajador.id_trabajador\n"
            + "JOIN Trabajador.persona AS Persona ON Trabajador.persona.id_persona = Persona.id_persona\n"
            + "WHERE OrdenJudicial.estatus = true")
    List<Pension_AlimenticiaDTO> finAllPensionesActivas();

    @Query(value = "SELECT Principal.porcentaje "
            + "FROM pension_a_orden_judicial AS Principal "
            + "JOIN pension_a_montos AS Montos ON Montos.id_montos = Principal.montos_id "
            + "WHERE Principal.trabajador_id = :trabajador_id "
            //Que sea una pensión activa
            + "AND Principal.estatus = true "
            //Que aplique a finiquito
            + "AND apl_finiq = true "
            //Que sea de tipo porcentaje
            + "AND Principal.porcentaje IS NOT NULL "
            //Ordenado por la fecha de recepción más antigua a la más nueva
            + "ORDER BY fecha_recepcion ASC "
            + "LIMIT 5;", nativeQuery = true)
    List<Double> findPorcentajePensionesActivas(@Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT Principal.cuota_fija "
            + "FROM pension_a_orden_judicial AS Principal "
            + "JOIN pension_a_montos AS Montos ON Montos.id_montos = Principal.montos_id "
            + "WHERE Principal.trabajador_id = :trabajador_id "
            //Que sea una pensión activa
            + "AND Principal.estatus = true "
            //Que aplique a finiquito
            + "AND apl_finiq = true "
            //Que sea de tipo cuota fija
            + "AND Principal.cuota_fija IS NOT NULL "
            //Ordenado por la fecha de recepción más antigua a la más nueva
            + "ORDER BY fecha_recepcion ASC "
            + "LIMIT 5;", nativeQuery = true)
    List<Double> findCuotaFijaPensionesActivas(@Param("trabajador_id") Integer trabajador_id);

}
