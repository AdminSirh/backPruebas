package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Beneficiario;
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
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Integer> {

    //********* BUSCAR EN TABLA Beneficiario POR PERSONA_ID *********
    @Query(value = "SELECT * FROM beneficiario u WHERE u.trabajador_id = :trabajador_id and u.estatus = 1", nativeQuery = true)
    List<Beneficiario> findBeneficiario(@Param("trabajador_id") Integer trabajador_id);

    //************ BUSCAR EN LA TABLA BENEFICIARIO POR PERSONA_ID **************
    @Query(value = "SELECT * FROM beneficiario u WHERE u.trabajador_id = :trabajador_id and u.estatus = 1", nativeQuery = true)
    Beneficiario findBeneficiarioPersona(@Param("trabajador_id") Integer trabajador_id);

}