/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Bimestres_Sdi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author rroscero23
 */
public interface Cat_Bimestres_SdiRepository extends JpaRepository<Cat_Bimestres_Sdi, Integer> {

    @Query(value = "SELECT * FROM catalogo_bimestres_sdi\n"
            + "WHERE id_bimestre = :id_bimestre",
            nativeQuery = true)
    Cat_Bimestres_Sdi findByIdBimestre(@Param("id_bimestre") Integer id_bimestre);

}
