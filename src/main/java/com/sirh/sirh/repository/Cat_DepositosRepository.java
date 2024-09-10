
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Depositos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nreyes22
 */

@Repository
public interface Cat_DepositosRepository extends JpaRepository<Cat_Depositos, Integer>{
    
}
