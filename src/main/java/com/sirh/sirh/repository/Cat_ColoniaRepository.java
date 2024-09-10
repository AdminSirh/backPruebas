package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Colonia;
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
public interface Cat_ColoniaRepository extends JpaRepository<Cat_Colonia, Integer> {

    // ***************** BUSCAR MEDIANTE CÓDIGO POSTAL *******************
    @Query(value = "SELECT * FROM catalogo_colonia INNER JOIN catalogo_municipio ON catalogo_colonia.id_municipio= catalogo_municipio.id_municipio"
            + " INNER JOIN catalogo_estado ON catalogo_municipio.id_estado = catalogo_estado.id_estado WHERE cod_postal= :cod_postal", nativeQuery = true)
    List<Cat_Colonia> findByCod_Postal(@Param("cod_postal") String cod_postal);

    @Query(value = "SELECT * FROM catalogo_colonia cc WHERE cc.id_municipio = :id_municipio", nativeQuery = true)
    List<Cat_Colonia> findMunicipiosColonia(@Param("id_municipio") Integer id_municipio);
}
