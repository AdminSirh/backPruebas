/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.serviceImpl;

import com.backB.backB.repository.TableInfoRepository;
import com.backB.backB.service.TableInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rroscero23
 */
@Service
public class TableInfoServiceImpl implements TableInfoService {

    @Autowired
    private TableInfoRepository tableInfoRepository;

    @Override
    public List<String> findTablasCatalogos() {
        return tableInfoRepository.findTablasCatalogos();
    }
}
