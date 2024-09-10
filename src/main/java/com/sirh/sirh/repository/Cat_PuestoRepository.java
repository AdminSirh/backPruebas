package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Puesto;
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
public interface Cat_PuestoRepository extends JpaRepository<Cat_Puesto, Integer> {

    //************ BUSCAR EN LA TABLA PUESTO POR codigo_puesto **************
    @Query(value = "SELECT * FROM catalogo_puesto u WHERE u.codigo_puesto= :codigo_puesto", nativeQuery = true)
    Cat_Puesto findCodigoPuesto(@Param("codigo_puesto") Integer codigo_puesto);

    // ******************* Verificar si ya existe el codigo_puesto ingresado ********************************************
    @Query(value = "SELECT * FROM catalogo_puesto e WHERE e.codigo_puesto = :codigo_puesto and e.status=1", nativeQuery = true)
    Cat_Puesto existsByCodPuesto(@Param("codigo_puesto") Integer codigo_puesto);

    @Query(value = "SELECT * FROM catalogo_puesto AS pu WHERE pu.id_puesto= :id_puesto", nativeQuery = true)
    Cat_Puesto findByIdPuesto(@Param("id_puesto") Integer id_puesto);
}
