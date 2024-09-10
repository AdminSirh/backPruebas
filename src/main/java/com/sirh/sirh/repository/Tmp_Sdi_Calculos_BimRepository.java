/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.Calculos_SdiDTO;
import com.sirh.sirh.DTO.SdiDTO;
import com.sirh.sirh.DTO.SdiSua_DTO;
import com.sirh.sirh.DTO.Sdi_IdseDTO;
import com.sirh.sirh.entity.Tmp_Sdi_Calculos_Bim;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rroscero23
 */
@Repository
public interface Tmp_Sdi_Calculos_BimRepository extends JpaRepository<Tmp_Sdi_Calculos_Bim, Integer> {

    @Query(value = "SELECT new com.sirh.sirh.DTO.SdiDTO( \n"
            + "Trab.id_trabajador,\n"
            + "CalculoBim.cat_Bimestres_Sdi.id_bimestre,\n"
            + "CalculoBim.cat_Bimestres_Sdi.bimestre,\n"
            + "Expe.numero_expediente,\n"
            + "Per.nombre,\n"
            + "Per.apellido_paterno,\n"
            + "Per.apellido_materno)\n"
            + "FROM Tmp_Sdi_Calculos_Bim AS CalculoBim\n"
            + "JOIN Trabajador_plaza AS TrabPl ON CalculoBim.trabajador_id = TrabPl.trabajador_id\n"
            + "JOIN Trabajador AS Trab ON CalculoBim.trabajador_id = Trab.id_trabajador\n"
            + "JOIN Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente\n"
            + "JOIN Persona AS Per ON Trab.persona.id_persona = Per.id_persona\n"
            + "WHERE TrabPl.cat_Tipo_Nomina.id_tipo_nomina= :idNomina\n"
            + "AND (:idBimestre IS NULL OR CalculoBim.cat_Bimestres_Sdi.id_bimestre = :idBimestre)"
            + "ORDER BY id_sdi_calculo ASC ")
    List<SdiDTO> findBimestresSdi(@Param("idNomina") Integer idNomina, @Param("idBimestre") Integer idBimestre);

    @Query(value = "SELECT EXISTS (\n"
            + "    SELECT 1\n"
            + "    FROM tmp_sdi_calculos_bimestre\n"
            + "    WHERE bimestre_id = :idBimestre\n"
            + ")", nativeQuery = true)
    Boolean existeBimestreCalculado(@Param("idBimestre") Integer idBimestre);

    @Query(value = "SELECT EXISTS (\n"
            + "    SELECT 1\n"
            + "    FROM tmp_sdi_calculos_bimestre\n"
            + "    WHERE trabajador_id = :idTrabajador\n"
            + "    AND bimestre_id = :idBimestre\n"
            + ")", nativeQuery = true)
    Boolean existeBimestreCalculadoTrabajador(@Param("idTrabajador") Integer idTrabajador, @Param("idBimestre") Integer idBimestre);

    @Query(value = "SELECT * FROM tmp_sdi_calculos_bimestre\n"
            + "WHERE bimestre_id = :idBimestre\n"
            + "AND trabajador_id = :idTrabajador", nativeQuery = true)
    List<Tmp_Sdi_Calculos_Bim> findDetalleCalculoBimTrab(@Param("idBimestre") Integer idBimestre, @Param("idTrabajador") Integer idTrabajador);

    @Query(value = "SELECT new com.sirh.sirh.DTO.SdiSua_DTO( \n"
            + "Per.num_imss,\n"
            + "ConceptosVariables.valor_32 AS salario_mixto)\n"
            + "FROM Tmp_Sdi_Calculos_Bim AS CalculosGeneral\n"
            + "JOIN Tmp_Sdi_Variable_Calculos_Bim AS ConceptosVariables ON CalculosGeneral.tmp_Sdi_Variable_Calculos_Bim.id_sdi_variable = ConceptosVariables.id_sdi_variable\n"
            + "JOIN Trabajador AS Trab ON CalculosGeneral.trabajador_id = Trab.id_trabajador\n"
            + "JOIN Persona AS Per ON Trab.persona.id_persona = Per.id_persona\n"
            + "JOIN Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente\n"
            + "JOIN Trabajador_plaza AS TrabPlaza ON Trab.id_trabajador = TrabPlaza.trabajador_id\n"
            + "WHERE CalculosGeneral.cat_Bimestres_Sdi.id_bimestre = :idBimestre\n"
            + "AND TrabPlaza.cat_Tipo_Nomina.id_tipo_nomina = :idNomina\n"
            + "ORDER BY Expe.numero_expediente ASC \n")
    List<SdiSua_DTO> consultaTxtSuaModifSalario(@Param("idBimestre") Integer idBimestre,
            @Param("idNomina") Integer idNomina);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Sdi_IdseDTO( \n"
            + "Per.num_imss,\n"
            + "Per.apellido_paterno,\n"
            + "Per.apellido_materno,\n"
            + "Per.nombre,\n"
            + "ConceptosVariables.valor_32 AS sal_base_cot,\n"
            + "Expe.numero_expediente,\n"
            + "Per.rfc)\n"
            + "FROM Tmp_Sdi_Calculos_Bim AS CalculoGeneral\n"
            + "JOIN Tmp_Sdi_Variable_Calculos_Bim AS ConceptosVariables ON CalculoGeneral.tmp_Sdi_Variable_Calculos_Bim.id_sdi_variable = ConceptosVariables.id_sdi_variable\n"
            + "JOIN Trabajador_plaza AS TrabPlaz ON CalculoGeneral.trabajador_id = TrabPlaz.trabajador_id\n"
            + "JOIN Trabajador AS Trab ON TrabPlaz.trabajador_id = Trab.id_trabajador\n"
            + "JOIN Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente\n"
            + "JOIN Persona AS Per ON Trab.persona.id_persona = Per.id_persona\n"
            + "WHERE CalculoGeneral.cat_Bimestres_Sdi.id_bimestre = :idBimestre\n"
            + "AND TrabPlaz.cat_Tipo_Nomina.id_tipo_nomina = :idNomina\n"
            + "ORDER BY Expe.numero_expediente ASC \n")
    List<Sdi_IdseDTO> consultaTxtIdseModifSalario(@Param("idBimestre") Integer idBimestre,
            @Param("idNomina") Integer idNomina);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Calculos_SdiDTO( \n"
            + "Expe.numero_expediente,\n"
            + "Per.apellido_paterno,\n"
            + "Per.apellido_materno,\n"
            + "Per.nombre,\n"
            + "calculoFijo.fijo_01 AS sueldoDiarioTabulado,\n"
            + "calculoFijo.fijo_02 AS estimuloVacCalculado,\n"
            + "calculoFijo.fijo_03 AS importeAguinCalculado,\n"
            + "calculoFijo.fijo_04 AS quinquenioMensCalculado,\n"
            + "calculoFijo.fijo_05 AS ayudaEscolarCalculado,\n"
            + "calculoFijo.fijo_06 AS ayudaReyesCalculado,\n"
            + "calculoFijo.fijo_07 AS ayudaTranspCalculado,\n"
            + "calculoFijo.fijo_08 AS estim10MayoCalculado,\n"
            + "calculoFijo.fijo_09 AS fondoAhorro7Calculado,\n"
            + "calculoFijo.fijo_10 AS sdiFijoCalculado,\n"
            + "calculoVariable.valor_34 AS difSueldoFormula,\n"
            + "calculoVariable.valor_25 AS difFondoAhorroFormula,\n"
            + "calculoVariable.valor_26 AS difEstimVacFormula)\n"
            + "FROM Tmp_Sdi_Calculos_Bim AS calculoPrincipal\n"
            + "JOIN Trabajador AS Trab ON Trab.id_trabajador = calculoPrincipal.trabajador_id\n"
            + "JOIN Trabajador_plaza AS Plaza ON Trab.id_trabajador = Plaza.trabajador_id\n"
            + "JOIN Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente\n"
            + "JOIN Persona AS Per ON Per.id_persona = Trab.persona.id_persona\n"
            + "JOIN Tmp_Sdi_Fijo_Calculos_Bim AS calculoFijo ON calculoPrincipal.tmp_Sdi_Fijo_Calculos_Bim.id_sdi_fijo = calculoFijo.id_sdi_fijo \n"
            + "JOIN Tmp_Sdi_Variable_Calculos_Bim AS calculoVariable ON calculoPrincipal.tmp_Sdi_Variable_Calculos_Bim.id_sdi_variable = calculoVariable.id_sdi_variable\n"
            + "WHERE Plaza.cat_Tipo_Nomina.id_tipo_nomina = :idNomina\n"
            + "AND calculoPrincipal.cat_Bimestres_Sdi.id_bimestre = :idBimestre \n"
            + "ORDER BY Expe.numero_expediente \n")
    List<Calculos_SdiDTO> consultaConceptosCalculadosNominaBim(@Param("idNomina") Integer idNomina,
            @Param("idBimestre") Integer idBimestre);

    @Modifying
    @Query(value = "CALL delete_calculation_sdi_bimester(:bimesterId)", nativeQuery = true)
    void callDeleteCalculationSdiBimester(@Param("bimesterId") int bimesterId);
}
