/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.InformacionPlazasDTO;
import com.sirh.sirh.DTO.Registro_Solicitudes_FonacotDTO;
import com.sirh.sirh.entity.Registro_Solicitudes_Fonacot;
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
public interface Registro_Solicitudes_FonacotRepository extends JpaRepository<Registro_Solicitudes_Fonacot, Integer>{
    @Query(value = "SELECT * FROM registro_solicitudes_fonacot c WHERE c.trabajador_id = :trabajador_id", nativeQuery = true)
    List<Registro_Solicitudes_Fonacot> findOneSolicitud(@Param("trabajador_id") Integer trabajador_id);
 
@Query(value = "SELECT new com.sirh.sirh.DTO.Registro_Solicitudes_FonacotDTO(" +
        "persona.rfc as rfc, " +
        "persona.num_imss as num_imss, " +
        "persona.curp as curp, " +
        "catalogoGenero.desc_genero as desc_genero, " +
        //"persona.cat_genero.id_genero as genero_id, " +
        "persona.apellido_paterno as apellido_paterno, " +
        "persona.apellido_materno as apellido_materno, " +
        "persona.nombre as nombre, " +
        "trabajador.fecha_ingreso as fecha_ingreso, " +
        "catalogoPuesto.sueldo_mensual_neto as sueldo_mensual_neto, " +
        "catalogoPuesto.sueldo_mensual as sueldo_mensual, " +
        "catalogoExpediente.numero_expediente as numero_expediente, " +
        "trabajador.prestaciones_si_no as prestaciones_si_no, " +
        "catTipoNomina.id_tipo_nomina as tipo_nomina_id, " +
        "catalogoPuesto.sdi_inicial as sdi_inicial) " +
        "FROM Registro_Solicitudes_Fonacot rsf " +
        "JOIN Trabajador trabajador ON trabajador.id_trabajador = rsf.trabajador.id_trabajador " +
        "JOIN Persona persona ON persona.id_persona = trabajador.persona.id_persona " +
        "JOIN Trabajador_plaza trabajadorPlaza ON trabajadorPlaza.trabajador_id = trabajador.id_trabajador " +
        "JOIN Cat_Plaza catalogoPlaza ON catalogoPlaza.id_plaza = trabajadorPlaza.plaza_id " +
        "JOIN Cat_Puesto catalogoPuesto ON catalogoPuesto.id_puesto = catalogoPlaza.cat_puesto.id_puesto " +
        "JOIN Cat_Expediente catalogoExpediente ON catalogoExpediente.id_expediente = trabajador.cat_expediente.id_expediente " +
        "JOIN Cat_Tipo_Nomina catTipoNomina ON catTipoNomina.id_tipo_nomina = trabajadorPlaza.cat_Tipo_Nomina.id_tipo_nomina " +
        "JOIN Cat_Genero catalogoGenero ON catalogoGenero.id_genero = persona.cat_genero.id_genero"
        )
List<Registro_Solicitudes_FonacotDTO> documentoTexto();  
    
    
        
}
