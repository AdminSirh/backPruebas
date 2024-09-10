package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.Trabajador_Prestamo_PersonalDTO;
import com.sirh.sirh.entity.Trabajador;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nreyes22
 */
@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {

    @Query(value = "SELECT * FROM trabajador INNER JOIN persona ON trabajador.persona_id= persona.id_persona\n"
            + "INNER JOIN catalogo_expedientes ON trabajador.expediente_id =catalogo_expedientes.id_expediente ", nativeQuery = true)
    List<Trabajador> personasConExpediente();

    // Buscamos al trabajador por expediente
    @Query(value = "SELECT * FROM trabajador p WHERE p.expediente_id = :expediente_id", nativeQuery = true)
    Trabajador buscarExp(@Param("expediente_id") Integer expediente_id);

    // ID Obtenemos el ultimo registro de trabajador
    @Query(value = "SELECT MAX(id_trabajador) FROM trabajador", nativeQuery = true)
    Integer ultimoIdTrabajador();

    @Query(value = "SELECT p.id_persona, c.id_expediente, c.numero_expediente, c.asignado, c.estatus, c.fecha_registro, t.persona_id, t.id_trabajador,"
            + "t.expediente_id, t.extraccion_sindical_si_no, t.prestaciones_si_no, t.comisionado_si_no, t.fecha_antiguedad,"
            + "t.fecha_ingreso "
            + "FROM trabajador t "
            + "INNER JOIN persona p on t.persona_id = p.id_persona "
            + "INNER JOIN catalogo_expedientes c on t.expediente_id = c.id_expediente "
            + "WHERE c.numero_expediente = :numero_expediente", nativeQuery = true)
    Trabajador buscarNumExp(@Param("numero_expediente") String numero_expediente);

    @Query(value = "SELECT * FROM trabajador\n"
            + "JOIN persona ON trabajador.persona_id= persona.id_persona\n"
            + "JOIN trabajador_plaza ON trabajador.id_trabajador = trabajador_id\n"
            + "JOIN catalogo_expedientes ON trabajador.expediente_id = catalogo_expedientes.id_expediente\n", nativeQuery = true)
    List<Trabajador> personasConPlaza();

    @Query(value = "SELECT DISTINCT trabajador.*\n"
            + "FROM trabajador\n"
            + "JOIN persona ON trabajador.persona_id = persona.id_persona\n"
            + "JOIN trabajador_plaza ON trabajador.id_trabajador = trabajador_plaza.trabajador_id\n"
            + "JOIN catalogo_expedientes ON trabajador.expediente_id = catalogo_expedientes.id_expediente\n"
            + "LEFT JOIN incidencias ON trabajador_plaza.trabajador_id = incidencias.trabajador_id\n"
            //Aquí va la nómina a filtrar
            + "WHERE trabajador_plaza.tipo_nomina_id = :tipo_nomina\n"
            //Aquí va la incidencia a filtrar
            + "AND incidencias.incidencia_id =  :incidencia_id\n"
            //Aquí van las fechas de inicio y de término correspondientes a los periodos de pago
            + "AND (Date(incidencias.fecha_inicio) >= :fecha_inicio_periodo_pago)\n"
            + "AND (Date(incidencias.fecha_fin) <= :fecha_fin_periodo_pago);", nativeQuery = true)
    List<Trabajador> filtradoTotalDeIncidenciasKardex(@Param("tipo_nomina") Integer tipo_nomina,
            @Param("incidencia_id") Integer incidencia_id,
            @Param("fecha_inicio_periodo_pago") Date fecha_inicio_periodo_pago,
            @Param("fecha_fin_periodo_pago") Date fecha_fin_periodo_pago);

    @Query(value = "SELECT DISTINCT trabajador.*\n"
            + "FROM trabajador\n"
            + "JOIN persona ON trabajador.persona_id = persona.id_persona\n"
            + "JOIN trabajador_plaza ON trabajador.id_trabajador = trabajador_plaza.trabajador_id\n"
            + "JOIN catalogo_expedientes ON trabajador.expediente_id = catalogo_expedientes.id_expediente\n"
            + "LEFT JOIN incidencias ON trabajador_plaza.trabajador_id = incidencias.trabajador_id\n"
            //Aquí va la nómina a filtrar
            + "WHERE trabajador_plaza.tipo_nomina_id = :tipo_nomina\n"
            //Aquí va la incidencia a filtrar
            + "AND incidencias.incidencia_id =  :incidencia_id \n", nativeQuery = true)
    List<Trabajador> filtradoSoloDeIncidenciasKardex(@Param("tipo_nomina") Integer tipo_nomina,
            @Param("incidencia_id") Integer incidencia_id);

    @Query(value = "SELECT DISTINCT trabajador.*\n"
            + "FROM trabajador\n"
            + "JOIN persona ON trabajador.persona_id = persona.id_persona\n"
            + "JOIN trabajador_plaza ON trabajador.id_trabajador = trabajador_plaza.trabajador_id\n"
            + "JOIN catalogo_expedientes ON trabajador.expediente_id = catalogo_expedientes.id_expediente\n"
            + "LEFT JOIN incidencias ON trabajador_plaza.trabajador_id = incidencias.trabajador_id\n"
            //Aquí va la nómina a filtrar
            + "WHERE trabajador_plaza.tipo_nomina_id = :tipo_nomina\n"
            //Aquí van las fechas de inicio y de término correspondientes a los periodos de pago
            + "AND (Date(incidencias.fecha_inicio) >= :fecha_inicio_periodo_pago)\n"
            + "AND (Date(incidencias.fecha_fin) <= :fecha_fin_periodo_pago);", nativeQuery = true)
    List<Trabajador> filtradoPeriodosDeIncidenciasKardex(@Param("tipo_nomina") Integer tipo_nomina,
            @Param("fecha_inicio_periodo_pago") Date fecha_inicio_periodo_pago,
            @Param("fecha_fin_periodo_pago") Date fecha_fin_periodo_pago);

    @Query(value = "SELECT * \n"
            + "FROM trabajador\n"
            + "JOIN persona ON trabajador.persona_id= persona.id_persona\n"
            + "JOIN trabajador_plaza ON trabajador.id_trabajador = trabajador_id\n"
            + "JOIN catalogo_expedientes ON trabajador.expediente_id = catalogo_expedientes.id_expediente\n"
            + "WHERE tipo_nomina_id = :tipo_nomina\n"
            + "ORDER BY catalogo_expedientes.numero_expediente",
            nativeQuery = true)
    List<Trabajador> personasConPlazaTipoNominaEspecifica(@Param("tipo_nomina") Integer tipo_nomina);

    @Query(value = "SELECT \n"
            + "Trab.id_trabajador \n"
            + "FROM \n"
            + "trabajador AS Trab\n"
            + "JOIN catalogo_expedientes AS Expe ON Trab.expediente_id = Expe.id_expediente\n"
            + "WHERE Expe.numero_expediente IN (:listaExpedientes)",
            nativeQuery = true)
    List<Integer> obtenerIdTrabajadorConExpediente(@Param("listaExpedientes") List<String> listaExpedientes);

    @Query(value = "SELECT *\n"
            + "FROM trabajador AS Trab\n"
            + "INNER JOIN persona AS Per ON Trab.persona_id = Per.id_persona\n"
            + "INNER JOIN catalogo_expedientes AS Expe ON Trab.expediente_id = Expe.id_expediente\n"
            + "LEFT JOIN trabajador_plaza AS Plaza ON Trab.id_trabajador = Plaza.trabajador_id\n"
            + "WHERE (Plaza.tipo_nomina_id = :tipo_nomina_id OR :tipo_nomina_id IS NULL)", nativeQuery = true)
    List<Trabajador> trabajadoresConExpedienteFiltroNominaOpcional(@Param("tipo_nomina_id") Integer tipo_nomina_id);

}
