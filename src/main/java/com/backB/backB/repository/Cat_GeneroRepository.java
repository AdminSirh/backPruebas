
package com.backB.backB.repository;

import com.backB.backB.entity.Cat_Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nreyes22
 */
@Repository
public interface Cat_GeneroRepository extends JpaRepository<Cat_Genero, Integer>{
    
}
