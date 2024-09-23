/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.backB.backB.controller;

import com.backB.backB.service.TableInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rroscero23
 */
@RestController
@RequestMapping("informacionTablas")
public class TableInfoController {

    @Autowired
    private TableInfoService tableInfoService;

    @GetMapping("/listarTablasCatalogos")
    public List<String> getTablesStartingWithCat() {
        return tableInfoService.findTablasCatalogos();
    }
}
