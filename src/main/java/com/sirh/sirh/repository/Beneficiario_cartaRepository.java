package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Beneficiario_carta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dguerrero18
 */
@Repository
public interface Beneficiario_cartaRepository extends JpaRepository<Beneficiario_carta, Integer>{
     //********* BUSCAR EN TABLA Beneficiario_carta POR PERSONA_ID *********
    @Query(value = "SELECT * FROM beneficiario_carta u WHERE u.persona_id = :persona_id and u.estatus = 1 and tipo_beneficiario_carta_id =1", nativeQuery = true)
    List<Beneficiario_carta> findBeneficiarioCarta(@Param("persona_id") Integer persona_id);

    //************ BUSCAR EN LA TABLA BENEFICIARIO POR PERSONA_ID **************
    @Query(value = "SELECT * FROM beneficiario_carta u WHERE u.persona_id = :persona_id and u.estatus = 1 and tipo_beneficiario_carta_id =1", nativeQuery = true)
    Beneficiario_carta findBeneficiarioPersonaCarta(@Param("persona_id") Integer persona_id);
    
       @Query(value = "SELECT * FROM beneficiario_carta u WHERE u.persona_id = :persona_id and u.estatus = 1 and tipo_beneficiario_carta_id =2", nativeQuery = true)
    List<Beneficiario_carta> findBeneficiarioCartaSec(@Param("persona_id") Integer persona_id);

    //************ BUSCAR EN LA TABLA BENEFICIARIO POR PERSONA_ID **************
    @Query(value = "SELECT * FROM beneficiario_carta u WHERE u.persona_id = :persona_id and u.estatus = 1 and tipo_beneficiario_carta_id =2", nativeQuery = true)
    Beneficiario_carta findBeneficiarioPersonaCartaSec(@Param("persona_id") Integer persona_id);
    
}
