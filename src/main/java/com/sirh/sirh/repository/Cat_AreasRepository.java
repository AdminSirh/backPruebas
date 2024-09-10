/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.repository;

/**
 *
 * @author jarellano22
 */
import com.sirh.sirh.DTO.Cat_AreasDTO;
import com.sirh.sirh.entity.Cat_Areas;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jarellano22
 */
@Repository
public interface Cat_AreasRepository extends JpaRepository<Cat_Areas, Integer> {

    public List<Cat_AreasDTO> findByEstatus(Integer estatus_id);
}
