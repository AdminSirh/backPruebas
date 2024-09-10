package com.sirh.sirh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sirh.sirh.entity.Cat_Plaza_Movimientos;

@Repository
public interface Cat_Plaza_MovimientosRepository extends JpaRepository<Cat_Plaza_Movimientos, Integer>{
    
}
