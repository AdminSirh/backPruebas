
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Estado_Vacaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nreyes22
 */

@Repository 
public interface Cat_Estado_VacacionesRepository extends JpaRepository<Cat_Estado_Vacaciones, Integer>{
    
}
