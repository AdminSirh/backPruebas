/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.backB.backB.repository;

import com.backB.backB.DTO.AdminLogDTO;
import com.backB.backB.entity.AdminLog;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jarellano22
 */
@Repository
public interface AdminLogRepository extends JpaRepository<AdminLog, Long> {
    // Verifica que el candidato no este registrado con anterioridad por CURP

    @Query(value = "SELECT * FROM admin_log al WHERE date(al.operate_date) BETWEEN :desde AND :hasta", nativeQuery = true)
    List<AdminLog> findAllB(@Param("desde") Date desde, @Param("hasta") Date hasta);

    //List<AdminLog> findAllB(Date desde, Date hasta);
    //Selecciona una operación en especifico realizada por un usuario en cierto periodo de fechas
    @Query(value = "SELECT new com.backB.backB.DTO.AdminLogDTO( \n"
            + "LogAdministrador.result_params AS id_incidencia_generada,\n"
            + "RolesDescripcion.name AS creators_role,\n"
            + "LogAdministrador.user_name) \n"
            + "FROM AdminLog AS LogAdministrador\n"
            + "JOIN Usuario_Rol AS RelacionUsuarioRoles ON LogAdministrador.user_id = RelacionUsuarioRoles.usuario_id\n"
            + "JOIN Rol AS RolesDescripcion ON RelacionUsuarioRoles.rol_id = RolesDescripcion.id\n"
            + "WHERE LogAdministrador.operation = :operacion\n"
            + "AND operate_date >= :fechaInicio\n"
            + "AND operate_date <= :fechaFin")
    List<AdminLogDTO> searchOperationByDate(@Param("operacion") String operacion,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin);

}
