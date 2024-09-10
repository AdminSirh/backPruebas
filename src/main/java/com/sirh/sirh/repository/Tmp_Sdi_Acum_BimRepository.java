/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Tmp_Sdi_Acum_Bim;
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
public interface Tmp_Sdi_Acum_BimRepository extends JpaRepository<Tmp_Sdi_Acum_Bim, Integer> {

    @Query(value = "SELECT EXISTS (\n"
            + "    SELECT 1\n"
            + "    FROM tmp_sdi_acumulados_bimestre\n"
            + "    WHERE bimestre_id = :idBimestre\n"
            + ")", nativeQuery = true)
    Boolean existeBimestreAcumulado(@Param("idBimestre") Integer idBimestre);

    @Modifying
    @Query(value = "CALL delete_acum_sdi_bimester(:bimesterId)", nativeQuery = true)
    void callDeleteAcumSdiBimester(@Param("bimesterId") int bimesterId);

}
