
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Licencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nreyes22
 */
@Repository
public interface Cat_LicenciaRepository extends JpaRepository<Cat_Licencia, Integer>{
    
}
