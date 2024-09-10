
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Edo_Civil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cat_Edo_CivilRepository extends JpaRepository<Cat_Edo_Civil, Integer>{
    
}
