package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Validaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oguerrero23
 */
@Repository
public interface ValidacionesRepository extends JpaRepository<Validaciones, Integer> {
    @Query(value = "SELECT * FROM validaciones c WHERE c.trabajador_id = :trabajador_id", nativeQuery = true)
    Validaciones findValidacionTrabajador(@Param("trabajador_id") Integer trabajador_id);
}
