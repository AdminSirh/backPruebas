/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.ReportePersonaDTO;
import com.sirh.sirh.service.FormatosIncidenciasService;
import com.sirh.sirh.util.TipoReporteEnum;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rrosero23
 */
@RestController
@RequestMapping("/formatosIncidencias")
public class FormatosIncidenciasController {

    @Autowired(required = false)
    private FormatosIncidenciasService formatosIncidenciasService;

    @GetMapping(value = "autorizacionVacaciones")
    public ResponseEntity<Resource> autorizacionVacaciones(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = formatosIncidenciasService.autorizacionVacaciones(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;

        if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }

        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
                .contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }

}
