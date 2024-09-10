/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

import com.sirh.sirh.entity.Cat_Tipo_Documento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jarellano22
 */
@Repository
public interface Cat_Tipo_DocumentoRepository extends JpaRepository<Cat_Tipo_Documento, Integer>{
      @Query(value = "SELECT * FROM catalogo_tipo_documento d WHERE d.estatus=1", nativeQuery = true)
     List<Cat_Tipo_Documento> findDocumentos();
}
