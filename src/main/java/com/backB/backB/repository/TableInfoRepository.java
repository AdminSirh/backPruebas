/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.backB.backB.repository;

import com.backB.backB.entity.TableInfo;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author rroscero23
 */
@Repository
public interface TableInfoRepository extends JpaRepository<TableInfo, String> {

    @Query(value = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_name LIKE 'catalogo%' ORDER BY table_name ASC", nativeQuery = true)
    List<String> findTablasCatalogos();
}
