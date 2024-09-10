/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.ReportePersonaDTO;
import com.sirh.sirh.service.ReportePrestamoService;
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
 * @author oguerrero23
 */
@RestController
@RequestMapping("/reportPrestamo")
public class ReportePrestamoController {
    @Autowired(required = false)
    private ReportePrestamoService reportePrestamoService;  
    


    //*********************** PRESTAMO FONACOT*********************************
    
    @GetMapping(value = "/obtenPrestamoFonacot")
    public ResponseEntity<Resource> obtenPrestamoFonacot(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePrestamoService.obtenerHistoricoFonacot(params);
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
    
    //*********************** PRESTAMO PERSONAL*********************************
    
    @GetMapping(value = "/obtenFormatoPrestamoC")
    public ResponseEntity<Resource> obtenFormatoPrestamoC(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePrestamoService.obtenerFormatoPrestamoC(params);
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
    
    @GetMapping(value = "/obtenFormatoPrestamoF")
    public ResponseEntity<Resource> obtenFormatoPrestamoF(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePrestamoService.obtenerFormatoPrestamoF(params);
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
    
    @GetMapping(value = "/obtenFormatoPrestamoB")
    public ResponseEntity<Resource> obtenFormatoPrestamoB(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePrestamoService.obtenerFormatoPrestamoB(params);
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
    
    @GetMapping(value = "/obtenDepositoDiferentesBancos")
    public ResponseEntity<Resource> obtenDepositoDiferentesBancos(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePrestamoService.obtenerDepositoDiferentesBancos(params);
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
    
    @GetMapping(value = "/obtenDiferentesBancosExcel")
    public ResponseEntity<Resource> obtenDiferentesBancosExcel(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePrestamoService.obtenerDiferentesBancosExcel(params);
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
    
    @GetMapping(value = "/obtenDepositoBanorte")
    public ResponseEntity<Resource> obtenDepositoBanorte(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePrestamoService.obtenerDepositoBanorte(params);
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
    
    @GetMapping(value = "/obtenPrestamoPersonal")
    public ResponseEntity<Resource> obtenPrestamoPersonal(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePrestamoService.obtenerHistoricoBanorte(params);
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
