package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.ReportePersonaDTO;
import com.sirh.sirh.service.ReportePersonaService;
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
 * @author nreyes22
 */
@RestController
@RequestMapping("/report")
public class ReportePersonaController {

    @Autowired(required = false)
    private ReportePersonaService reportePersonaService;

    @GetMapping(value = "persona/descarga")
    public ResponseEntity<Resource> descarga(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerReportePersona(params);
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

    /*=============================REPORTE MONITOR PLAZA ====================================== 
    
     * **********************************************************
     * ALFABETICAMENTE
     **********************************************************
     */
    @GetMapping(value = "monitorPlazasAutorizadas/descargaPuestoAlfabet")
    public ResponseEntity<Resource> descargaPuestoAlfabet(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasAlfabetPuesto(params);
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

    @GetMapping(value = "monitorPlazasAutorizadas/descargaTipoContratoAlfabet")
    public ResponseEntity<Resource> descargaTipoContratoAlfabet(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasAlfabetTipoContrato(params);
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

    @GetMapping(value = "monitorPlazasAutorizadas/descargaPuestoTipoContratoAlfabet")
    public ResponseEntity<Resource> descargaPuestoTipoContratoAlfabet(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasAlfabetPuestoYTipoContrato(params);
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

    @GetMapping(value = "monitorPlazasAutorizadas/descargaPuestoEstatusAlfabet")
    public ResponseEntity<Resource> descargaPuestoEstatusAlfabet(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasAlfabetPuestoYEstatus(params);
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

    @GetMapping(value = "monitorPlazasAutorizadas/descargaPuestoTipoContratoEstatusAlfabet")
    public ResponseEntity<Resource> descargaPuestoTipoContratoEstatusAlfabet(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasAlfabetPuestoTipoContratoYEstatus(params);
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

    /**
     * *********************************************************
     * ORGANIGRAMA *********************************************************
     */
    @GetMapping(value = "monitorPlazasAutorizadas/descargaPuestoOrganigrama")
    public ResponseEntity<Resource> descargaPuestoOrganigrama(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasOrganigramaPuesto(params);
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

    @GetMapping(value = "monitorPlazasAutorizadas/descargaTipoContratoOrganigrama")
    public ResponseEntity<Resource> descargaTipoContratoOrganigrama(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasOrganigramaTipoContrato(params);
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

    @GetMapping(value = "monitorPlazasAutorizadas/descargaPuestoTipoContratoOrganigrama")
    public ResponseEntity<Resource> descargaPuestoTipoContratoOrganigrama(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasOrganigramaPuestoYTipoContrato(params);
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

    @GetMapping(value = "monitorPlazasAutorizadas/descargaPuestoEstatusOrganigrama")
    public ResponseEntity<Resource> descargaPuestoEstatusOrganigrama(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasOrganigramaPuestoYEstatus(params);
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

    @GetMapping(value = "monitorPlazasAutorizadas/descargaPuestoTipoContratoEstatusOrganigrama")
    public ResponseEntity<Resource> descargaPuestoTipoContratoEstatusOrganigrama(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasOrganigramaPuestoTipoContratoYEstatus(params);
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

    /**
     * **********************************************************
     * EXPEDIENTE **********************************************************
     */
    @GetMapping(value = "monitorPlazasAutorizadas/descargaPuestoExpediente")
    public ResponseEntity<Resource> descargaPuestoExpediente(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasExpedientePuesto(params);
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

    @GetMapping(value = "monitorPlazasAutorizadas/descargaTipoContratoExpediente")
    public ResponseEntity<Resource> descargaTipoContratoExpediente(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasExpedienteTipoContrato(params);
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

    @GetMapping(value = "monitorPlazasAutorizadas/descargaPuestoTipoContratoExpediente")
    public ResponseEntity<Resource> descargaPuestoTipoContratoExpediente(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasExpedientePuestoYTipoContrato(params);
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

    @GetMapping(value = "monitorPlazasAutorizadas/descargaPuestoEstatusExpediente")
    public ResponseEntity<Resource> descargaPuestoEstatusExpediente(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasExpedientePuestoYEstatus(params);
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

    @GetMapping(value = "monitorPlazasAutorizadas/descargaPuestoTipoContratoEstatusExpediente")
    public ResponseEntity<Resource> descargaPuestoTipoContratoEstatusExpediente(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasExpedientePuestoTipoContratoYEstatus(params);
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

    /* **********************************************************
     * CONSTANCIAS
     **********************************************************
     */
    @GetMapping(value = "constancias/obtenerConstanciaMensualIntegrado")
    public ResponseEntity<Resource> obtenerConstanciaMensualIntegrado(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerConstanciaMensualIntegrado(params);
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

    @GetMapping(value = "constancias/obtenerConstanciaSinSalario")
    public ResponseEntity<Resource> obtenerConstanciaSinSalario(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerConstanciaSinSalario(params);
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

    @GetMapping(value = "constancias/obtenerConstanciaFallecimiento")
    public ResponseEntity<Resource> obtenerConstanciaFallecimiento(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerConstanciaFallecimiento(params);
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

    /**
     * **********************************************************
     * AVISO MOVIMIENTO DE PERSONAL
     * **********************************************************
     */
    @GetMapping(value = "aviso/obtenAvisoMovimientoPersonal")
    public ResponseEntity<Resource> obtenAvisoMovimientoPersonal(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.ObtenMovimientosPersonal(params);
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

    @GetMapping(value = "aviso/obtenAvisoMovimientoPersonalCambio")
    public ResponseEntity<Resource> obtenAvisoMovimientoPersonalCambio(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.ObtenMovimientosPersonalCambio(params);
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

    @GetMapping(value = "reportePersonalFemeninoPorArea")
    public ResponseEntity<Resource> reportePersonalFemeninoPorArea(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePersonaService.reportePersonalFemeninoPorArea(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;

        if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline;  filename =\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }

    @GetMapping(value = "jubilados/personalJubiladoActivo")
    public ResponseEntity<Resource> personalJubiladoActivo(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePersonaService.reporteJubiladoActivo(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;

        if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }

    /**
     * **********************************************************
     * MOVIMIENTOS DE PERSONAL
     * **********************************************************
     */
    @GetMapping(value = "movimientos/movimientoPersonalHistorico")
    public ResponseEntity<Resource> movimientoPersonalHistorico(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.movimientoPersonalHistorico(params);
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

    /*
    *******************************************************************************************************
    REPORTE PLAZA x ID AREA
    *******************************************************************************************************
     */
    @GetMapping("monitorPlazasAutorizadasAreas/descargaPlazaPorAreas")
    public ResponseEntity<Resource> descargaPlazaAreas(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.obtenerMonitorPlazasAutorizadasAreas(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }

    /*
    *******************************************************************************************************
    REPORTE PENSION ALIMENTICIA
    *******************************************************************************************************
     */
    @GetMapping("allPensionesAlimenticias")
    public ResponseEntity<Resource> reportePensionAlimenticia(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.reportePensionAlimenticia(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }

    /*
    *******************************************************************************************************
    REPORTE GENERACIÓN DE CONTROL DE ASISTENCIA
    *******************************************************************************************************
     */
    @GetMapping("controlAsistenciaPersonalBase")
    public ResponseEntity<Resource> controlAsistenciaPersonalBase(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.controlAsistenciaPersonalBase(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }

    @GetMapping("complementoControlAsistenciaPersonalBase")
    public ResponseEntity<Resource> resumenControlAsistenciaPersonalBase(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.resumenControlAsistenciaPersonalBase(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }

    @GetMapping("controlAsistenciaPersonalConfianza")
    public ResponseEntity<Resource> controlAsistenciaPersonalConfianza(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.controlAsistenciaPersonalConfianza(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }

    /*
    *******************************************************************************************
    REPORTES PARA AMONESTACIÓN Y SUSPENSIÓN
    *******************************************************************************************
     */
    @GetMapping(value = "registraSuspensionAmonestacion")
    public ResponseEntity<Resource> registraSuspensionAmonestacion(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.suspension_amonestacion(params);
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

    /*
    *******************************************************************************************
    REPORTE INCAPACIDADES EXPEDIDAS IMSSS
    *******************************************************************************************
     */
    @GetMapping("incapacidadesExpedidasIMSS")
    public ResponseEntity<Resource> incapacidadesExpedidasIMSS(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.incapacidadesExpedidasIMSS(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }

    @GetMapping("incapacidadePeriodoPorMotivoIMSS")
    public ResponseEntity<Resource> incapacidadePeriodoPorMotivoIMSS(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.incapacidadePeriodoPorMotivoIMSS(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }

    @GetMapping("inasistenciasSUAIMSS")
    public ResponseEntity<Resource> inasistenciasSUAIMSS(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.inasistenciasSUAIMSS(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }

    @GetMapping("reporteAyudas")
    public ResponseEntity<Resource> reporteAyudas(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.reporteAyudas(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }

    @GetMapping("reporteCapturaSemanalRA15")
    public ResponseEntity<Resource> reporteCapturaSemanalRA15(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.reporteCapturaSemanalRA15(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }

    @GetMapping("reporteSinTurnoEnElPeriodoRA15")
    public ResponseEntity<Resource> reporteSinTurnoEnElPeriodoRA15(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {

        ReportePersonaDTO dto = reportePersonaService.reporteSinTurnoEnElPeriodoRA15(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            mediaType = MediaType.APPLICATION_PDF;
        }
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"").contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }

    /*
    *******************************************************************************************
    REPORTE DEPOSITO DE PAGO PARA DIFERENTES BANCOS
    *******************************************************************************************
     */
    @GetMapping(value = "/listadoDepositosPagoDiferentesBancos")
    public ResponseEntity<Resource> listadoDepositosPagoDiferentesBancos(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePersonaService.depositoPagoDiferentesBancos(params);
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

    @GetMapping(value = "/listadoDepositosPagoDiferentesBancosE")
    public ResponseEntity<Resource> listadoDepositosPagoDiferentesBancosE(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePersonaService.depositoPagoDiferentesBancosEx(params);
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

    /*
    *******************************************************************************************
    REPORTE DEPOSITO DE PAGO PARA DIFERENTES BANCOS PENSION ALIMENTICIA VALES
    *******************************************************************************************
     */
    @GetMapping(value = "/listadoDepositosPagoDiferentesBancosPaVales")
    public ResponseEntity<Resource> listadoDepositosPagoDiferentesBancosPaVales(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePersonaService.depositoPagoDiferentesBancosPAVales(params);
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

    // Exactamente el mismo reporte de listadoDepositosPagoDiferentesBancosPaVales Pero con las dimensiones y el formato para archivo de Excel
    @GetMapping(value = "/listadoDepositosPagoDiferentesBancosPaValesE")
    public ResponseEntity<Resource> listadoDepositosPagoDiferentesBancosPaValesE(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePersonaService.depositoPagoDiferentesBancosPAValesEx(params);
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

    /*
    *******************************************************************************************
    REPORTE DEPOSITO DE PAGO PARA DIFERENTES BANCOS PENSION ALIMENTICIA 
    *******************************************************************************************
     */
    @GetMapping(value = "/listadoDepositosPagoDiferentesBancosPension")
    public ResponseEntity<Resource> listadoDepositosPagoDiferentesBancosPension(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePersonaService.depositoPagoDiferentesBancosPension(params);
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

    @GetMapping(value = "/listadoDepositosPagoDiferentesBancosPensionE")
    public ResponseEntity<Resource> listadoDepositosPagoDiferentesBancosPensionE(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePersonaService.depositoPagoDiferentesBancosPensionEx(params);
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

    /*
    *******************************************************************************************
    REPORTE DEPOSITO DE PAGO PARA DIFERENTES BANCOS VALES
    *******************************************************************************************
     */
    @GetMapping(value = "/listadoDepositosPagoDiferentesBancosVales")
    public ResponseEntity<Resource> listadoDepositosPagoDiferentesBancosVales(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePersonaService.depositoPagoDiferentesBancosVales(params);
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

    // Exactamente el mismo reporte de listadoDepositosPagoDiferentesBancosVales Pero con las dimensiones y el formato para archivo de Excel
    @GetMapping(value = "/listadoDepositosPagoDiferentesBancosValesE")
    public ResponseEntity<Resource> listadoDepositosPagoDiferentesBancosValesE(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePersonaService.depositoPagoDiferentesBancosValesEx(params);
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

    /*
    *******************************************************************************************
    REPORTE RECIBOS PARA PAGOS DE FINIQUITOS
    *******************************************************************************************
     */
    @GetMapping(value = "/reciboFiniquito")
    public ResponseEntity<Resource> reciboFiniquito(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePersonaService.reciboFiniquito(params);
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

    @GetMapping(value = "/reciboFiniquitoPrestaciones")
    public ResponseEntity<Resource> reciboFiniquitoPrestaciones(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePersonaService.reciboFiniquitoPrestaciones(params);
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

    @GetMapping(value = "/reporteRetroJubilados")
    public ResponseEntity<Resource> reporteRetroJubilados(@RequestParam Map<String, Object> params) throws JRException, IOException, SQLException {
        ReportePersonaDTO dto = reportePersonaService.reporteRetroJubilados(params);
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
