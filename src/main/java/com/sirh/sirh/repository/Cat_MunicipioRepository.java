package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Municipio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Cat_MunicipioRepository extends JpaRepository<Cat_Municipio, Integer>{
    
//    @Query(value = "SELECT * FROM catalogo_municipio cm WHERE cm.id_estado = :id_estado and cm.activo=1", nativeQuery = true)
//    List<Cat_Municipio> findAllMunicipio(@Param("id_estado") Integer id_estado);
//    
    @Query(value = "SELECT * FROM catalogo_municipio cm WHERE cm.id_estado = :id_estado", nativeQuery = true)
    List<Cat_Municipio> findEstadoMunicipio(@Param("id_estado") Integer id_estado);
    
}
