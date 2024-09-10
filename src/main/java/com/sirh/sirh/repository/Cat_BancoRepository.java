/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author jarellano22
 */
public interface Cat_BancoRepository extends JpaRepository<Cat_Banco, Integer> {
    @Query(value = "SELECT max_comision FROM catalogo_bancos c WHERE c.id_banco = :id_banco", nativeQuery = true)
    Integer findMaxComision(@Param("id_banco") Integer id_banco);
    
}
