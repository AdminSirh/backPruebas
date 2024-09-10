
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nreyes22
 */
@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer>{
    
    //********* BUSCAR EN TABLA DIRECCION POR PERSONA_ID *********
    @Query(value = "SELECT * FROM direccion u WHERE u.persona_id = :persona_id", nativeQuery = true)
    Direccion findDireccion(@Param("persona_id") Integer persona_id);
}
