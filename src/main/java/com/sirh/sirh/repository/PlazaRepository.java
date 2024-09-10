/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.InformacionPlazasDTO;
import com.sirh.sirh.DTO.Trabajador_PuestoDTO;
import com.sirh.sirh.DTO.Trabajador_SueldoDTO;
import com.sirh.sirh.DTO.Trabajador_plazaDTO;
import com.sirh.sirh.entity.Trabajador_plaza;
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
public interface PlazaRepository extends JpaRepository<Trabajador_plaza, Integer> {

    @Query(value = "SELECT new com.sirh.sirh.DTO.Trabajador_plazaDTO( \n"
            + "  trabajador.cat_expediente.id_expediente as expedienteId, \n"
            + "  cTipoContrato.id_tipo_contrato as idTipoContrato,\n"
            + "  cTipoContrato.tipo_nomina as tipoNomina, \n"
            + "  cPlaza.numero_plaza as numeroPlaza, \n"
            + "  cPuesto.nivel as nivelDto,\n"
            + "  cPuesto.id_puesto as idPuesto,\n"
            + "  cPuesto.puesto as puestoDto, \n"
            + "  persona.nombre as nombreDto, \n"
            + "  persona.apellido_paterno as apellidoPaterno, \n"
            + "  persona.apellido_materno as apellidoMaterno,\n"
            + "  cExpedientes.numero_expediente as numeroExpediente,\n"
            + "  cEstatusPlaza.id_estatus_plaza as idEstatusPlaza,\n"
            + "  cEstatusPlaza.descripcion as descripcionDto )\n"
            + "FROM \n"
            + "  Trabajador_plaza  trabajadorPlaza\n"
            + "  JOIN Trabajador trabajador ON trabajador.id_trabajador = trabajadorPlaza.trabajador_id\n"
            + "  JOIN Cat_Plaza cPlaza ON cPlaza.id_plaza = trabajadorPlaza.plaza_id\n"
            + "  JOIN Persona persona ON trabajador.persona.id_persona = id_persona\n"
            + "  JOIN Cat_Tipo_Contrato cTipoContrato ON trabajadorPlaza.cat_tipo_contrato.id_tipo_contrato = cTipoContrato.id_tipo_contrato\n"
            + "  JOIN Cat_Puesto cPuesto ON cPlaza.cat_puesto.id_puesto  = id_puesto\n"
            + "  JOIN Cat_Estatus_Plaza cEstatusPlaza ON cPlaza.estatus_plaza_id = cEstatusPlaza.id_estatus_plaza\n"
            + "  JOIN Cat_Expediente cExpedientes ON trabajador.cat_expediente.id_expediente = id_expediente\n"
            + "  WHERE cPuesto.id_puesto = :puesto_id"
            + "  AND cEstatusPlaza.id_estatus_plaza = :estatus_plaza_id")
    List<Trabajador_plazaDTO> findByPuestoAndEstatus(@Param("puesto_id") Integer puestoId, @Param("estatus_plaza_id") Integer estatusPlazaId);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Trabajador_PuestoDTO( \n"
            + "  trabajador.cat_expediente.id_expediente as expedienteId, \n"
            + "  cTipoContrato.id_tipo_contrato as idTipoContrato,\n"
            + "  cTipoNomina.id_tipo_nomina as idTipoNomina, \n"
            + "  cTipoNomina.desc_nomina as tipoNomina, \n"
            + "  cPlaza.numero_plaza as numeroPlaza, \n"
            + "  cPuesto.nivel as nivelDto,\n"
            + "  cPuesto.id_puesto as idPuesto,\n"
            + "  cPuesto.puesto as puestoDto, \n"
            + "  cPuesto.sueldo_diario as sueldoDiarioDto, \n"
            + "  cAreas.desc_area as descAreaDto, \n"
            + "  persona.nombre as nombreDto, \n"
            + "  persona.apellido_paterno as apellidoPaterno, \n"
            + "  persona.apellido_materno as apellidoMaterno,\n"
            + "  cExpedientes.numero_expediente as numeroExpediente,\n"
            + "  cEstatusPlaza.id_estatus_plaza as idEstatusPlaza,\n"
            + "  cEstatusPlaza.descripcion as descripcionDto )\n"
            + "FROM \n"
            + "  Trabajador_plaza  trabajadorPlaza\n"
            + "  JOIN Trabajador trabajador ON trabajador.id_trabajador = trabajadorPlaza.trabajador_id\n"
            + "  JOIN Cat_Plaza cPlaza ON cPlaza.id_plaza = trabajadorPlaza.plaza_id\n"
            + "  JOIN Persona persona ON trabajador.persona.id_persona = id_persona\n"
            + "  JOIN Cat_Tipo_Contrato cTipoContrato ON trabajadorPlaza.cat_tipo_contrato.id_tipo_contrato = cTipoContrato.id_tipo_contrato\n"
            + "  JOIN Cat_Tipo_Nomina cTipoNomina ON trabajadorPlaza.cat_Tipo_Nomina.id_tipo_nomina = cTipoNomina.id_tipo_nomina\n"
            + "  JOIN Cat_Puesto cPuesto ON cPlaza.cat_puesto.id_puesto  = id_puesto\n"
            + "  JOIN Cat_Estatus_Plaza cEstatusPlaza ON cPlaza.estatus_plaza_id = cEstatusPlaza.id_estatus_plaza\n"
            + "  JOIN Cat_Expediente cExpedientes ON trabajador.cat_expediente.id_expediente = id_expediente\n"
            + "  JOIN Cat_Areas cAreas ON trabajadorPlaza.areas_id = id_area\n"
            + "  WHERE trabajadorPlaza.trabajador_id = :trabajador_id")
    List<Trabajador_PuestoDTO> findPlazaTrabajadorByIdTrabajador(@Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Trabajador_plazaDTO( \n"
            + "  trabajador.cat_expediente.id_expediente as expedienteId, \n"
            + "  cTipoContrato.id_tipo_contrato as idTipoContrato,\n"
            + "  cTipoContrato.tipo_nomina as tipoNomina, \n"
            + "  cPlaza.numero_plaza as numeroPlaza, \n"
            + "  cPuesto.nivel as nivelDto,\n"
            + "  cPuesto.id_puesto as idPuesto,\n"
            + "  cPuesto.puesto as puestoDto, \n"
            + "  persona.nombre as nombreDto, \n"
            + "  persona.apellido_paterno as apellidoPaterno, \n"
            + "  persona.apellido_materno as apellidoMaterno,\n"
            + "  cExpedientes.numero_expediente as numeroExpediente,\n"
            + "  cEstatusPlaza.id_estatus_plaza as idEstatusPlaza,\n"
            + "  cEstatusPlaza.descripcion as descripcionDto )\n"
            + "FROM \n"
            + "  Trabajador_plaza  trabajadorPlaza\n"
            + "  JOIN Trabajador trabajador ON trabajador.id_trabajador = trabajadorPlaza.trabajador_id\n"
            + "  JOIN Cat_Plaza cPlaza ON cPlaza.id_plaza = trabajadorPlaza.plaza_id\n"
            + "  JOIN Persona persona ON trabajador.persona.id_persona = id_persona\n"
            + "  JOIN Cat_Tipo_Contrato cTipoContrato ON trabajadorPlaza.cat_tipo_contrato.id_tipo_contrato = cTipoContrato.id_tipo_contrato\n"
            + "  JOIN Cat_Puesto cPuesto ON cPlaza.cat_puesto.id_puesto  = id_puesto\n"
            + "  JOIN Cat_Estatus_Plaza cEstatusPlaza ON cPlaza.estatus_plaza_id = cEstatusPlaza.id_estatus_plaza\n"
            + "  JOIN Cat_Expediente cExpedientes ON trabajador.cat_expediente.id_expediente = id_expediente\n"
            + "  WHERE cPuesto.id_puesto = :puesto_id")
    List<Trabajador_plazaDTO> findByPuesto(@Param("puesto_id") Integer puestoId);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Trabajador_plazaDTO( \n"
            + "  trabajador.cat_expediente.id_expediente as expedienteId, \n"
            + "  cTipoContrato.id_tipo_contrato as idTipoContrato,\n"
            + "  cTipoContrato.tipo_nomina as tipoNomina, \n"
            + "  cPlaza.numero_plaza as numeroPlaza, \n"
            + "  cPuesto.nivel as nivelDto,\n"
            + "  cPuesto.id_puesto as idPuesto,\n"
            + "  cPuesto.puesto as puestoDto, \n"
            + "  persona.nombre as nombreDto, \n"
            + "  persona.apellido_paterno as apellidoPaterno, \n"
            + "  persona.apellido_materno as apellidoMaterno,\n"
            + "  cExpedientes.numero_expediente as numeroExpediente,\n"
            + "  cEstatusPlaza.id_estatus_plaza as idEstatusPlaza,\n"
            + "  cEstatusPlaza.descripcion as descripcionDto )\n"
            + "FROM \n"
            + "  Trabajador_plaza  trabajadorPlaza\n"
            + "  JOIN Trabajador trabajador ON trabajador.id_trabajador = trabajadorPlaza.trabajador_id\n"
            + "  JOIN Cat_Plaza cPlaza ON cPlaza.id_plaza = trabajadorPlaza.plaza_id\n"
            + "  JOIN Persona persona ON trabajador.persona.id_persona = id_persona\n"
            + "  JOIN Cat_Tipo_Contrato cTipoContrato ON trabajadorPlaza.cat_tipo_contrato.id_tipo_contrato = cTipoContrato.id_tipo_contrato\n"
            + "  JOIN Cat_Puesto cPuesto ON cPlaza.cat_puesto.id_puesto  = id_puesto\n"
            + "  JOIN Cat_Estatus_Plaza cEstatusPlaza ON cPlaza.estatus_plaza_id = cEstatusPlaza.id_estatus_plaza\n"
            + "  JOIN Cat_Expediente cExpedientes ON trabajador.cat_expediente.id_expediente = id_expediente\n"
            + "  WHERE trabajadorPlaza.cat_tipo_contrato.id_tipo_contrato IN (:tipo_contrato_ids)")
    List<Trabajador_plazaDTO> findTiposDeContrato(@Param("tipo_contrato_ids") List<Integer> tipoContratoIds);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Trabajador_plazaDTO( \n"
            + "  trabajador.cat_expediente.id_expediente as expedienteId, \n"
            + "  cTipoContrato.id_tipo_contrato as idTipoContrato,\n"
            + "  cTipoContrato.tipo_nomina as tipoNomina, \n"
            + "  cPlaza.numero_plaza as numeroPlaza, \n"
            + "  cPuesto.nivel as nivelDto,\n"
            + "  cPuesto.id_puesto as idPuesto,\n"
            + "  cPuesto.puesto as puestoDto, \n"
            + "  persona.nombre as nombreDto, \n"
            + "  persona.apellido_paterno as apellidoPaterno, \n"
            + "  persona.apellido_materno as apellidoMaterno,\n"
            + "  cExpedientes.numero_expediente as numeroExpediente,\n"
            + "  cEstatusPlaza.id_estatus_plaza as idEstatusPlaza,\n"
            + "  cEstatusPlaza.descripcion as descripcionDto )\n"
            + "FROM \n"
            + "  Trabajador_plaza  trabajadorPlaza\n"
            + "  JOIN Trabajador trabajador ON trabajador.id_trabajador = trabajadorPlaza.trabajador_id\n"
            + "  JOIN Cat_Plaza cPlaza ON cPlaza.id_plaza = trabajadorPlaza.plaza_id\n"
            + "  JOIN Persona persona ON trabajador.persona.id_persona = id_persona\n"
            + "  JOIN Cat_Tipo_Contrato cTipoContrato ON trabajadorPlaza.cat_tipo_contrato.id_tipo_contrato = cTipoContrato.id_tipo_contrato\n"
            + "  JOIN Cat_Puesto cPuesto ON cPlaza.cat_puesto.id_puesto  = id_puesto\n"
            + "  JOIN Cat_Estatus_Plaza cEstatusPlaza ON cPlaza.estatus_plaza_id = cEstatusPlaza.id_estatus_plaza\n"
            + "  JOIN Cat_Expediente cExpedientes ON trabajador.cat_expediente.id_expediente = id_expediente\n"
            + "  WHERE cPuesto.id_puesto = :puesto_ids"
            + "  AND trabajadorPlaza.cat_tipo_contrato.id_tipo_contrato IN (:tipo_contrato_ids)")
    List<Trabajador_plazaDTO> findByTipoContratoIdAndPuestoId(@Param("tipo_contrato_ids") List<Integer> tipoContratoIds, @Param("puesto_ids") List<Integer> puesto_ids);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Trabajador_plazaDTO( \n"
            + "  trabajador.cat_expediente.id_expediente as expedienteId, \n"
            + "  cTipoContrato.id_tipo_contrato as idTipoContrato,\n"
            + "  cTipoContrato.tipo_nomina as tipoNomina, \n"
            + "  cPlaza.numero_plaza as numeroPlaza, \n"
            + "  cPuesto.nivel as nivelDto,\n"
            + "  cPuesto.id_puesto as idPuesto,\n"
            + "  cPuesto.puesto as puestoDto, \n"
            + "  persona.nombre as nombreDto, \n"
            + "  persona.apellido_paterno as apellidoPaterno, \n"
            + "  persona.apellido_materno as apellidoMaterno,\n"
            + "  cExpedientes.numero_expediente as numeroExpediente,\n"
            + "  cEstatusPlaza.id_estatus_plaza as idEstatusPlaza,\n"
            + "  cEstatusPlaza.descripcion as descripcionDto )\n"
            + "FROM \n"
            + "  Trabajador_plaza  trabajadorPlaza\n"
            + "  JOIN Trabajador trabajador ON trabajador.id_trabajador = trabajadorPlaza.trabajador_id\n"
            + "  JOIN Cat_Plaza cPlaza ON cPlaza.id_plaza = trabajadorPlaza.plaza_id\n"
            + "  JOIN Persona persona ON trabajador.persona.id_persona = id_persona\n"
            + "  JOIN Cat_Tipo_Contrato cTipoContrato ON trabajadorPlaza.cat_tipo_contrato.id_tipo_contrato = cTipoContrato.id_tipo_contrato\n"
            + "  JOIN Cat_Puesto cPuesto ON cPlaza.cat_puesto.id_puesto  = id_puesto\n"
            + "  JOIN Cat_Estatus_Plaza cEstatusPlaza ON cPlaza.estatus_plaza_id = cEstatusPlaza.id_estatus_plaza\n"
            + "  JOIN Cat_Expediente cExpedientes ON trabajador.cat_expediente.id_expediente = id_expediente\n"
            + "  WHERE cPuesto.id_puesto = :puesto_id"
            + "  AND cEstatusPlaza.id_estatus_plaza = :estatus_plaza_id"
            + "  AND trabajadorPlaza.cat_tipo_contrato.id_tipo_contrato IN (:tipo_contrato_ids)")
    List<Trabajador_plazaDTO> findByPuestoAndEstatusAndTipoDeContrato(@Param("puesto_id") Integer puestoId, @Param("estatus_plaza_id") Integer estatusPlazaId, @Param("tipo_contrato_ids") List<Integer> tipoContratoIds);

    @Query(value = "SELECT new com.sirh.sirh.DTO.InformacionPlazasDTO("
            + "cPlaza.numero_plaza as numero_plaza,"
            + "TrabajadorPlaza.areas_id as id_area,"
            + "cPuesto.puesto as puesto,"
            + "cPlaza.estatus_plaza_id as estatus_plaza_id,"
            + "persona.nombre as nombre,"
            + "persona.apellido_materno as apellido_materno,"
            + "persona.apellido_paterno as apellido_paterno,"
            + "cExpedientes.numero_expediente as numero_expediente) "
            + "FROM Trabajador_plaza TrabajadorPlaza \n"
            + "JOIN Trabajador trabajador ON TrabajadorPlaza.trabajador_id = trabajador.id_trabajador \n"
            + "JOIN Persona persona ON trabajador.persona.id_persona = id_persona\n"
            + "JOIN Cat_Plaza cPlaza ON cPlaza.id_plaza = TrabajadorPlaza.plaza_id\n"
            + "JOIN Cat_Puesto cPuesto ON cPlaza.cat_puesto.id_puesto  = id_puesto\n"
            + "JOIN Cat_Expediente cExpedientes ON trabajador.cat_expediente.id_expediente = id_expediente\n"
            + "WHERE cPlaza.numero_plaza = :numero_plaza")
    List<InformacionPlazasDTO> findByNumPlaza(@Param("numero_plaza") Integer numero_plaza);

    @Query(value = "SELECT new com.sirh.sirh.DTO.InformacionPlazasDTO("
            + "cPlaza.numero_plaza as numero_plaza,"
            + "TrabajadorPlaza.areas_id as id_area,"
            + "cPuesto.puesto as puesto,"
            + "cPlaza.estatus_plaza_id as estatus_plaza_id,"
            + "persona.nombre as nombre,"
            + "persona.apellido_materno as apellido_materno,"
            + "persona.apellido_paterno as apellido_paterno,"
            + "cExpedientes.numero_expediente as numero_expediente) "
            + "FROM Trabajador_plaza TrabajadorPlaza \n"
            + "JOIN Trabajador trabajador ON TrabajadorPlaza.trabajador_id = trabajador.id_trabajador \n"
            + "JOIN Persona persona ON trabajador.persona.id_persona = id_persona\n"
            + "JOIN Cat_Plaza cPlaza ON cPlaza.id_plaza = TrabajadorPlaza.plaza_id\n"
            + "JOIN Cat_Puesto cPuesto ON cPlaza.cat_puesto.id_puesto  = id_puesto\n"
            + "JOIN Cat_Expediente cExpedientes ON trabajador.cat_expediente.id_expediente = id_expediente\n"
            + "WHERE cPlaza.estatus_plaza_id IN (:estatus_plaza_id)")
    List<InformacionPlazasDTO> findByEstatusPlaza(@Param("estatus_plaza_id") List<Integer> estatus_plaza_id);

    @Query(value = "SELECT new com.sirh.sirh.DTO.InformacionPlazasDTO("
            + "cPlaza.numero_plaza as numero_plaza,"
            + "TrabajadorPlaza.areas_id as id_area,"
            + "cPuesto.puesto as puesto,"
            + "cPlaza.estatus_plaza_id as estatus_plaza_id,"
            + "persona.nombre as nombre,"
            + "persona.apellido_materno as apellido_materno,"
            + "persona.apellido_paterno as apellido_paterno,"
            + "cExpedientes.numero_expediente as numero_expediente) "
            + "FROM Trabajador_plaza TrabajadorPlaza \n"
            + "JOIN Trabajador trabajador ON TrabajadorPlaza.trabajador_id = trabajador.id_trabajador \n"
            + "JOIN Persona persona ON trabajador.persona.id_persona = id_persona\n"
            + "JOIN Cat_Plaza cPlaza ON cPlaza.id_plaza = TrabajadorPlaza.plaza_id\n"
            + "JOIN Cat_Puesto cPuesto ON cPlaza.cat_puesto.id_puesto  = id_puesto\n"
            + "JOIN Cat_Expediente cExpedientes ON trabajador.cat_expediente.id_expediente = id_expediente\n"
            + "WHERE TrabajadorPlaza.areas_id = :id_area")
    List<InformacionPlazasDTO> findByAreaPlaza(@Param("id_area") Integer id_area);

    @Query(value = "SELECT new com.sirh.sirh.DTO.InformacionPlazasDTO("
            + "cPlaza.numero_plaza as numero_plaza,"
            + "TrabajadorPlaza.areas_id as id_area,"
            + "cPuesto.puesto as puesto,"
            + "cPlaza.estatus_plaza_id as estatus_plaza_id,"
            + "persona.nombre as nombre,"
            + "persona.apellido_materno as apellido_materno,"
            + "persona.apellido_paterno as apellido_paterno,"
            + "cExpedientes.numero_expediente as numero_expediente) "
            + "FROM Trabajador_plaza TrabajadorPlaza \n"
            + "JOIN Trabajador trabajador ON TrabajadorPlaza.trabajador_id = trabajador.id_trabajador \n"
            + "JOIN Persona persona ON trabajador.persona.id_persona = id_persona\n"
            + "JOIN Cat_Plaza cPlaza ON cPlaza.id_plaza = TrabajadorPlaza.plaza_id\n"
            + "JOIN Cat_Puesto cPuesto ON cPlaza.cat_puesto.id_puesto  = id_puesto\n"
            + "JOIN Cat_Expediente cExpedientes ON trabajador.cat_expediente.id_expediente = id_expediente\n"
            + "WHERE TrabajadorPlaza.areas_id = :id_area\n"
            + "AND cPlaza.estatus_plaza_id IN (:estatus_plaza_id)")
    List<InformacionPlazasDTO> findByAreaAndEstatusPlaza(@Param("id_area") Integer id_area, @Param("estatus_plaza_id") List<Integer> estatus_plaza_id);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Trabajador_SueldoDTO("
            + "CPU.id_puesto AS idPuesto,\n"
            + "CPU.puesto AS puesto,\n"
            + "CPU.nivel AS nivel,\n"
            + "CPU.sueldo_diario AS sueldo_diario,\n"
            + "CPU.sueldo_mensual AS sueldoMensual,\n"
            + "CPU.sueldo_hora AS sueldoHora8,\n"
            + "CPU.sueldoHora7 AS sueldoHora7)\n"
            + "FROM Trabajador_plaza AS TP\n"
            + "JOIN Cat_Plaza AS CP ON TP.plaza_id = CP.id_plaza\n"
            + "JOIN Cat_Puesto AS CPU ON CP.cat_puesto.id_puesto = CPU.id_puesto\n"
            + "WHERE TP.trabajador_id = :id_trabajador")
    Trabajador_SueldoDTO findSueldosTrabajador(@Param("id_trabajador") Integer id_trabajador);
   
}
