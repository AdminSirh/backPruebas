/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.Listado_RA_10DTO;
import com.sirh.sirh.DTO.PlantillaMensualDTO;
import com.sirh.sirh.DTO.Trabajador_FonacotDTO;
import com.sirh.sirh.DTO.Trabajador_Prestamo_PersonalDTO;
import com.sirh.sirh.DTO.Trabajador_SeparacionDTO;
import com.sirh.sirh.entity.Trabajador_plaza;
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
public interface Trabajador_plazaRepository extends JpaRepository<Trabajador_plaza, Integer> {

    @Query(value = "SELECT * FROM trabajador_plaza c WHERE c.trabajador_id = :trabajador_id", nativeQuery = true)
    Trabajador_plaza findPlazaTrabajador(@Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT * FROM trabajador_plaza WHERE areas_id = :areas_id", nativeQuery = true)
    List<Trabajador_plaza> findTrabajadorArea(@Param("areas_id") Integer areas_id);

    @Query(value = "SELECT * FROM trabajador_plaza t_p \n"
            + "JOIN catalogo_plaza c_p ON c_p.id_plaza = t_p.plaza_id\n"
            + "JOIN catalogo_puesto c_pu ON c_pu.id_puesto = c_p.puesto_id\n"
            + "WHERE t_p.tipo_nomina_id =:tipo_nomina_id AND c_pu.nivel <=26", nativeQuery = true)
    List<Trabajador_plaza> findTrabajadoresByNominaAndNivel26(@Param("tipo_nomina_id") Integer tipo_nomina_id);

    @Query(value = "SELECT * FROM trabajador_plaza t_p \n"
            + "JOIN catalogo_plaza c_p ON c_p.id_plaza = t_p.plaza_id\n"
            + "JOIN catalogo_puesto c_pu ON c_pu.id_puesto = c_p.puesto_id\n"
            + "WHERE t_p.tipo_nomina_id =:tipo_nomina_id AND c_pu.nivel =27", nativeQuery = true)
    List<Trabajador_plaza> findTrabajadoresByNominaAndNivel27(@Param("tipo_nomina_id") Integer tipo_nomina_id);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Trabajador_FonacotDTO(\n"
            + "ce.numero_expediente as expedienteDTO,\n"
            + "pe.nombre as nombreDTO,\n"
            + "pe.apellido_paterno as apellido_paternoDTO,\n"
            + "pe.apellido_materno as apellido_maternoDTO,\n"
            + "cn.id_tipo_nomina as id_tipo_nominaDTO)\n"
            + "FROM Trabajador_plaza tp\n"
            + "JOIN Trabajador tr ON tr.id_trabajador = tp.trabajador_id \n"
            + "JOIN Persona pe ON pe.id_persona = tr.persona.id_persona \n"
            + "JOIN Cat_Tipo_Nomina cn ON cn.id_tipo_nomina = tp.cat_Tipo_Nomina.id_tipo_nomina\n"
            + "JOIN Cat_Expediente ce ON ce.id_expediente = tr.cat_expediente.id_expediente \n"
            + "WHERE pe.num_imss = :num_imss")
    Trabajador_FonacotDTO findTrabajadorFonacot(@Param("num_imss") String num_imss);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Trabajador_Prestamo_PersonalDTO(\n"
            + "pe.apellido_paterno as apellido_paternoDTO,\n"
            + "pe.apellido_materno as apellido_maternoDTO,\n"
            + "pe.nombre as nombreDTO,\n"
            + "pe.curp as curpDTO,\n"
            + "ce.numero_expediente as numero_expedienteDTO,\n"
            + "c_g.desc_genero as desc_generoDTO, \n"
            + "c_e_c.desc_edo_civil as desc_edo_civilDTO, \n"
            + "tr.fecha_ingreso as fecha_ingresoDTO, \n"
            + "c_a.desc_area as desc_areaDTO, \n"
            + "c_a.codigo_area as codigo_areaDTO, \n"
            + "tr.id_trabajador as id_trabajadorDTO)\n"
            + "FROM Trabajador_plaza tp \n"
            + "JOIN Trabajador tr ON tr.id_trabajador = tp.trabajador_id \n"
            + "JOIN Persona pe ON pe.id_persona = tr.persona.id_persona \n"
            + "JOIN Cat_Expediente ce ON ce.id_expediente = tr.cat_expediente.id_expediente \n"
            + "JOIN Cat_Genero c_g ON c_g.id_genero = pe.cat_genero.id_genero \n"
            + "JOIN Cat_Edo_Civil c_e_c ON c_e_c.id_edo_civil = pe.cat_estado_civil.id_edo_civil \n"
            + "JOIN Cat_Areas c_a ON c_a.idArea = tp.areas_id")
    List<Trabajador_Prestamo_PersonalDTO> listadoCreditoPersonal();

    //Se deuvelve la nómina el trabajador al hacer la búsqueda por expediente
    @Query(value = "SELECT \n"
            + "tipo_nomina_id\n"
            + "FROM trabajador_plaza\n"
            + "JOIN trabajador ON trabajador.id_trabajador = trabajador_id\n"
            + "JOIN catalogo_expedientes ON catalogo_expedientes.id_expediente = trabajador.expediente_id\n"
            + "WHERE catalogo_expedientes.numero_expediente = :expediente", nativeQuery = true)
    Integer findNominaTrabajadorByExpediente(@Param("expediente") String expediente);

    @Query(value = "SELECT \n"
            + "catalogo_tipo_nomina.desc_nomina\n"
            + "FROM trabajador_plaza\n"
            + "JOIN trabajador ON trabajador.id_trabajador = trabajador_id\n"
            + "JOIN catalogo_expedientes ON catalogo_expedientes.id_expediente = trabajador.expediente_id\n"
            + "JOIN catalogo_tipo_nomina ON catalogo_tipo_nomina.id_tipo_nomina = trabajador_plaza.tipo_nomina_id\n"
            + "WHERE catalogo_expedientes.numero_expediente = :expediente", nativeQuery = true)
    String findNombreNominaTrabajadorByExpediente(@Param("expediente") String expediente);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Trabajador_SeparacionDTO(\n"
            + "pe.apellido_paterno as apellido_paternoDTO,\n"
            + "pe.apellido_materno as apellido_maternoDTO,\n"
            + "pe.nombre as nombreDTO,\n"
            + "pe.rfc as rfcDTO,\n"
            + "pe.num_imss as imssDTO, \n"
            + "pe.fecha_nacimiento as fecha_nacimientoDTO, \n"
            + "ce.numero_expediente as numero_expedienteDTO,\n"
            + "tr.fecha_ingreso as fecha_ingresoDTO, \n"
            + "c_a.desc_area as desc_areaDTO, \n"
            + "c_t_n.desc_nomina as nominaDTO, \n"
            + "c_pu.puesto as puestoDTO, \n"
            + "tr.id_trabajador as id_trabajadorDTO)\n"
            + "FROM Trabajador_plaza tp \n"
            + "JOIN Trabajador tr ON tr.id_trabajador = tp.trabajador_id \n"
            + "JOIN Persona pe ON pe.id_persona = tr.persona.id_persona \n"
            + "JOIN Cat_Tipo_Nomina c_t_n ON c_t_n.id_tipo_nomina = tp.cat_Tipo_Nomina.id_tipo_nomina \n"
            + "JOIN Cat_Expediente ce ON ce.id_expediente = tr.cat_expediente.id_expediente \n"
            + "JOIN Cat_Plaza c_p ON c_p.id_plaza = tp.plaza_id \n"
            + "JOIN Cat_Puesto c_pu ON c_pu.id_puesto = c_p.cat_puesto.id_puesto \n"
            + "JOIN Cat_Areas c_a ON c_a.idArea = tp.areas_id")
    List<Trabajador_SeparacionDTO> listadoSeparacion();

    @Query(value = "SELECT \n"
            + "CASE \n"
            + "WHEN ubicacion_id IN (1, 2, 3, 6) THEN true\n"
            + "WHEN ubicacion_id = 5 THEN false\n"
            + "ELSE null\n"
            + "END AS esOperativo\n"
            + "FROM public.trabajador_plaza\n"
            + "WHERE trabajador_id = :trabajador_id \n",
            nativeQuery = true)
    Boolean esOperativoOAdministrativo(@Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Listado_RA_10DTO(\n"
            + "Trab.id_trabajador,\n"
            + "Expe.numero_expediente,\n"
            + "Per.nombre,\n"
            + "Per.apellido_paterno,\n"
            + "Per.apellido_materno)\n"
            + "FROM Trabajador_plaza AS TP\n"
            + "JOIN Trabajador AS Trab ON Trab.id_trabajador =  TP.trabajador_id\n"
            + "JOIN Persona AS Per ON Trab.persona.id_persona = Per.id_persona\n"
            + "JOIN Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente\n"
            + "WHERE TP.areas_id = :id_area\n"
            + "AND TP.cat_Tipo_Nomina.id_tipo_nomina = :id_nomina")
    List<Listado_RA_10DTO> findByNominaAndArea(Integer id_area, Integer id_nomina);

    @Query(value = "SELECT  new com.sirh.sirh.DTO.PlantillaMensualDTO( \n"
            + "YEAR(current_date()), \n"
            + "MONTH(current_date()), \n"
            + "TrabPlaza.trabajador_id,\n"
            + "Plaza.numero_plaza,\n"
            + "Puesto.base_confianza AS tipo,\n"
            + "Puesto.nivel,\n"
            + "Puesto.codigo_puesto,\n"
            + "Puesto.puesto,\n"
            + "Puesto.sueldo_mensual_neto,\n"
            + "CAST(Area.codigo_area AS string),\n"
            + "Puesto.id_puesto AS puesto_id,\n"
            + "Area.idArea,\n"
            + "TrabPlaza.cat_tipo_contrato.id_tipo_contrato AS tipo_contrato_id,\n"
            + "TrabPlaza.cat_Tipo_Nomina.id_tipo_nomina AS tipo_nomina_id,\n"
            + "1 AS prestaciones,\n"
            + "Puesto.sueldo_mensual AS sueldo_mensual_bruto) "
            + "FROM \n"
            + "Trabajador_plaza AS TrabPlaza\n"
            + "JOIN Cat_Plaza AS Plaza ON TrabPlaza.plaza_id = Plaza.id_plaza\n"
            + "JOIN Trabajador AS Trab ON TrabPlaza.trabajador_id = Trab.id_trabajador\n"
            + "JOIN Cat_Expediente AS Expe ON Trab.cat_expediente.id_expediente = Expe.id_expediente\n"
            + "JOIN Cat_Puesto AS Puesto ON Plaza.cat_puesto.id_puesto = Puesto.id_puesto\n"
            + "JOIN Cat_Areas AS Area ON TrabPlaza.areas_id = Area.idArea\n"
            + "WHERE Plaza.estatus_plaza_id = 1\n"
            + "AND TrabPlaza.cat_Tipo_Nomina.id_tipo_nomina IN (1,2,3,5)\n"
            + "ORDER BY Expe.numero_expediente")
    List<PlantillaMensualDTO> cierrePlantillaMensual();

    @Query(value = "SELECT * FROM trabajador_plaza WHERE plaza_id = :plaza_id AND trabajador_id = :trabajador_id", nativeQuery = true)
    Trabajador_plaza findByPlazaAndTrabajador(Integer plaza_id, Integer trabajador_id);

}
