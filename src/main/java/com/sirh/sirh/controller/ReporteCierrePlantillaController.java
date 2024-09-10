/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.ReportePersonaDTO;
import com.sirh.sirh.service.ReporteCierrePlantillaService;
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
@RequestMapping("/reportCierrePlantilla")
public class ReporteCierrePlantillaController {

    @Autowired(required = false)
    private ReporteCierrePlantillaService reporteCierrePlantillaService;

    @GetMapping(value = "obtenerMovimientosPlazasMes")
    public ResponseEntity<Resource> obtenerMovimientosPlazasMes(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reporteCierrePlantillaService.obtenerMovimientosPlazasMes(params);
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

    @GetMapping(value = "obtenerResumenGeneroeIngresos")
    public ResponseEntity<Resource> obtenerResumenGeneroeIngresos(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reporteCierrePlantillaService.obtenerResumenGeneroeIngresos(params);
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

    @GetMapping(value = "reporteCierrePlantilla")
    public ResponseEntity<Resource> reporteCierrePlantilla(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reporteCierrePlantillaService.reporteCierrePlantilla(params);
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

    @GetMapping(value = "reporteCierrePlantillaSubI")
    public ResponseEntity<Resource> reporteCierrePlantillaSubI(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reporteCierrePlantillaService.reporteCierrePlantillaSubI(params);
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

    @GetMapping(value = "reporteCierrePlantillaSubII")
    public ResponseEntity<Resource> reporteCierrePlantillaSubII(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reporteCierrePlantillaService.reporteCierrePlantillaSubII(params);
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

    @GetMapping(value = "reporteCierrePlantillaSubIII")
    public ResponseEntity<Resource> reporteCierrePlantillaSubIII(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reporteCierrePlantillaService.reporteCierrePlantillaSubIII(params);
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

    @GetMapping(value = "reportePuestoGenero")
    public ResponseEntity<Resource> reportePuestoGenero(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reporteCierrePlantillaService.reportePuestoGenero(params);
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

    @GetMapping(value = "reporteGeneroRangoEdades")
    public ResponseEntity<Resource> reporteGeneroXRangoEdades(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reporteCierrePlantillaService.reporteGeneroXRangoEdades(params);
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

    @GetMapping(value = "reporteGeneroEdades")
    public ResponseEntity<Resource> reporteGeneroXEdades(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reporteCierrePlantillaService.reporteGeneroXEdades(params);
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
    
    @GetMapping(value = "reporteMesualDePLazasOcupadasPorNumeroExpediente")
    public ResponseEntity<Resource> reportePlazasOcupadasPorExpediente(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        
        ReportePersonaDTO dto = reporteCierrePlantillaService.reportePlazasOcupadasPorExpediente(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        
        if(params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }
        
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }
    
    @GetMapping(value = "reportePorGeneroYGradoMaximoDeEstudios")
    public ResponseEntity<Resource> reportePorGeneroYGradoDeEstudios(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reporteCierrePlantillaService.reportePorGeneroYGradoDeEstudios(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        
        if(params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else{
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline;  filename =\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }
    
     @GetMapping(value = "reporteMovimientosRegistradosPlantilla")
    public ResponseEntity<Resource> reporteMovimientosRegistradosPlantilla(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reporteCierrePlantillaService.reporteMovimientosRegistradosPlantilla(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        
        if(params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else{
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline;  filename =\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }
    
    
}
