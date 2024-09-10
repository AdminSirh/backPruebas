/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Documentacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jarellano22
 */
@Repository
public interface DocumentacionRepository extends JpaRepository<Documentacion, Integer> {

    @Query(value = "SELECT * FROM documentacion d WHERE d.persona_id = :persona_id", nativeQuery = true)
    List<Documentacion> findAllDocumentos(@Param("persona_id") Integer persona_id);

    //************************** ELIMINAR DOCUMENTO ************************
    @Modifying
    @Query(value = "DELETE FROM documentacion d WHERE d.id_documentacion = :id_documentacion", nativeQuery = true)
    void deleteDocumento(@Param("id_documentacion") Integer id_documentacion);
}
