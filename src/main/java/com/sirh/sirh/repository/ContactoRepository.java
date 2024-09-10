/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author nreyes22
 */
public interface ContactoRepository extends JpaRepository<Contacto, Integer>{
    
     //********* BUSCAR EN TABLA DIRECCION POR PERSONA_ID *********
    @Query(value = "SELECT * FROM contacto u WHERE u.persona_id = :persona_id", nativeQuery = true)
    Contacto findContacto(@Param("persona_id") Integer persona_id);
}
