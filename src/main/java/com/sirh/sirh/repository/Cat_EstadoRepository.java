
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nreyes22
 */

@Repository
public interface Cat_EstadoRepository extends JpaRepository<Cat_Estado, Integer>{
    
}
