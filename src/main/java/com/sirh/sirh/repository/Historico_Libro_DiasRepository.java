
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Historico_Libro_Dias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nreyes22
 */
@Repository
public interface Historico_Libro_DiasRepository extends JpaRepository<Historico_Libro_Dias, Integer>{
    
}
