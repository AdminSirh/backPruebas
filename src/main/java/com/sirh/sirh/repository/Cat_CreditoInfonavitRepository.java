
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Credito_Infonavit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nreyes22
 */
@Repository
public interface Cat_CreditoInfonavitRepository extends JpaRepository<Cat_Credito_Infonavit, Integer>{
    
}
