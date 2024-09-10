
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Inhabilitado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nreyes22
 */
@Repository
public interface Cat_InhabilitadoRepository extends JpaRepository<Cat_Inhabilitado, Integer>{
    
}
