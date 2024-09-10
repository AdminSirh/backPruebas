/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.DTO.Trabajador_DepositoDTO;
import com.sirh.sirh.entity.Libro_Dias;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author akalvarez19
 */
public interface Libro_DiasRepository extends JpaRepository<Libro_Dias, Integer> {

    //************ BUSCAR EN LA TABLA LIBRO DIAS POR TRABAJADOR_ID **************
    @Query(value = "SELECT * "
            + "FROM libro_dias d "
            + "INNER JOIN catalogo_dias c on d.dia_id = c.id_dia "
            + "INNER JOIN trabajador t on d.trabajador_id = t.id_trabajador "
            + "WHERE d.trabajador_id =:trabajador_id and d.estatus =1", nativeQuery = true)
    List<Libro_Dias> findLibro_Dias(@Param("trabajador_id") Integer trabajador_id);

    //************ BUSCAR EN LA TABLA LIBRO DIAS POR TRABAJADOR_ID **************
    @Query(value = "SELECT d.id_libro_dias, d.comentario, d.dia_id, d.horario_entrada, d.horario_salida, d.horas_trabajadas, "
            + "d.horas_pago, d.fecha_captura, d.fecha_movimiento, d.estatus, d.trabajador_id, c.dia "
            + "FROM libro_dias d "
            + "INNER JOIN catalogo_dias c on d.dia_ida = c.id_dia "
            + "INNER JOIN trabajador t on d.trabajador_id = t.id_trabajador "
            + "WHERE d.trabajador_id =:trabajador_id and d.estatus =1", nativeQuery = true)
    Libro_Dias findLibro_DiasTrabajador(@Param("trabajador_id") Integer trabajador_id);

    //************ BUSCAR EN LA TABLA LIBRO DIAS POR ID **************
    @Query(value = "SELECT d.id_libro_dias, d.comentario, d.dia_id, d.horario_entrada, d.horario_salida, d.horas_trabajadas, "
            + "d.horas_pago, d.fecha_captura, d.fecha_movimiento, d.estatus, d.trabajador_id, c.dia "
            + "FROM libro_dias d "
            + "INNER JOIN catalogo_dias c on d.dia_id = c.id_dia "
            + "INNER JOIN trabajador t on d.trabajador_id = t.id_trabajador "
            + "WHERE d.id_libro_dias =:id_libro_dias and d.estatus =1", nativeQuery = true)
    Libro_Dias findLibroH(@Param("id_libro_dias") Integer id_libro_dias);

    @Query(value = "SELECT * FROM libro_dias WHERE trabajador_id =:trabajador_id and estatus =1", nativeQuery = true)
    List<Libro_Dias> findDiasTrabajador(@Param("trabajador_id") Integer trabajador_id);

    @Query(value = "SELECT * FROM libro_dias \n"
            + "WHERE trabajador_id = :trabajador_id\n"
            + "AND dia_descanso = 1 \n"
            + "AND estatus = 1", nativeQuery = true)
    List<Libro_Dias> findDiasDescansosTrabajador(@Param("trabajador_id") Integer trabajador_id);
    

//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE libro_dias SET comentario = :comentario WHERE trabajador_id = :trabajador_id", nativeQuery = true)
//    void actualizarComentario(@Param("trabajador_id") Integer trabajador_id, @Param("comentario") Integer comentario);

    @Query(value = "SELECT new com.sirh.sirh.DTO.Trabajador_DepositoDTO( \n"
            + "Expe.numero_expediente,\n"
            + "Per.nombre,\n"
            + "Per.apellido_paterno,\n"
            + "Per.apellido_materno )\n"
            + "FROM Trabajador AS Trab\n"
            + "JOIN Trabajador_plaza AS Plaza ON Trab.id_trabajador = Plaza.trabajador_id\n"
            + "JOIN Persona AS Per ON Trab.persona.id_persona = id_persona\n"
            + "JOIN Cat_Expediente AS Expe ON Expe.id_expediente = Trab.cat_expediente.id_expediente\n"
            + "LEFT JOIN Libro_Dias AS Horario ON Trab.id_trabajador = Horario.trabajador_id\n"
            + "    AND Horario.cat_Depositos.id_deposito IS NOT NULL\n"
            + "WHERE Plaza.cat_Tipo_Nomina.id_tipo_nomina = 1\n"
            + "    AND Horario.cat_Depositos.id_deposito IS NULL\n"
            + "GROUP BY Trab.id_trabajador, Per.nombre, Per.apellido_materno, Per.apellido_paterno, Expe.numero_expediente"
    )
    List<Trabajador_DepositoDTO> findTrabajadoresBaseSinDepositoAsignado();

    @Query(value = "SELECT * FROM libro_dias \n"
            + "WHERE expediente = :expediente\n"
            + "AND estatus = 1\n", nativeQuery = true)
    List<Libro_Dias> findExpedienteLibroDias(@Param("expediente") Integer expediente);
    
    @Query(value = "SELECT * FROM libro_dias \n" +
                    "WHERE expediente = :expediente AND dia_id = :dia \n" +
                    "AND estatus = 1", nativeQuery = true)
    Libro_Dias findExpedienteDia(@Param("expediente") Integer expediente, @Param("dia") Integer dia);

}
