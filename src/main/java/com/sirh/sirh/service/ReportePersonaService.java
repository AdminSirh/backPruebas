package com.sirh.sirh.service;

import com.sirh.sirh.DTO.ReportePersonaDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author nreyes22
 */
public interface ReportePersonaService {

    ReportePersonaDTO obtenerReportePersona(Map<String, Object> params) throws JRException, IOException, SQLException;

    /**
     * **********************************************************
     * ALFABETICAMENTE *********************************************************
     */
    //Reporte de búsueda de puesto ordenado por orden alfabeitco con criterio Nombre del trabajador
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasAlfabetPuesto(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte de búsueda de Tipo de Contrato ordenado por orden alfabeitco con criterio Nombre del trabajador
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasAlfabetTipoContrato(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte de búsueda de Puesto con Tipo Contrato ordenado por orden alfabeitco con criterio Nombre del trabajador
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasAlfabetPuestoYTipoContrato(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte de búsueda de Puesto con Estatus de la plaza ordenado por orden alfabeitco con criterio Nombre del trabajador
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasAlfabetPuestoYEstatus(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte de búsueda de Puesto con Estatus de la plaza y Tipo de Contrato ordenado por orden alfabeitco con criterio Nombre del trabajador
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasAlfabetPuestoTipoContratoYEstatus(Map<String, Object> params) throws JRException, IOException, SQLException;

    /**
     * *********************************************************
     * ORGANIGRAMA *********************************************************
     */
    //Reporte de búsueda de puesto ordenado por Organigrama
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasOrganigramaPuesto(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte de búsueda de Tipo de Contrato ordenado por Organigrama
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasOrganigramaTipoContrato(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte de búsueda de Puesto con Tipo Contrato ordenado por Organigrama
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasOrganigramaPuestoYTipoContrato(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte de búsueda de Puesto con Estatus de la plaza ordenado por Organigrama
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasOrganigramaPuestoYEstatus(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte de búsueda de Puesto con Estatus de la plaza y Tipo de Contrato ordenado por Organigrama
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasOrganigramaPuestoTipoContratoYEstatus(Map<String, Object> params) throws JRException, IOException, SQLException;

    /**
     * **********************************************************
     * EXPEDIENTE **********************************************************
     */
    //Reporte de búsueda de puesto ordenado por Organigrama
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasExpedientePuesto(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte de búsueda de Tipo de Contrato ordenado por Organigrama
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasExpedienteTipoContrato(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte de búsueda de Puesto con Tipo Contrato ordenado por Organigrama
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasExpedientePuestoYTipoContrato(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte de búsueda de Puesto con Estatus de la plaza ordenado por Organigrama
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasExpedientePuestoYEstatus(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte de búsueda de Puesto con Estatus de la plaza y Tipo de Contrato ordenado por Organigrama
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasExpedientePuestoTipoContratoYEstatus(Map<String, Object> params) throws JRException, IOException, SQLException;

    /**
     * **********************************************************
     * CONSTANCIAS **********************************************************
     */
    //Constancia mensual integrado 
    ReportePersonaDTO obtenerConstanciaMensualIntegrado(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Constancia sin salario
    ReportePersonaDTO obtenerConstanciaSinSalario(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Constancia fallecimiento
    ReportePersonaDTO obtenerConstanciaFallecimiento(Map<String, Object> params) throws JRException, IOException, SQLException;

    /**
     * **********************************************************
     * AVISO MOVIMIENTO DE PERSONAL
     * **********************************************************
     */
    ReportePersonaDTO ObtenMovimientosPersonal(Map<String, Object> params) throws JRException, IOException, SQLException;

    ReportePersonaDTO ObtenMovimientosPersonalCambio(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte Personal Femenino por Area
    ReportePersonaDTO reportePersonalFemeninoPorArea(Map<String, Object> params) throws JRException, IOException, SQLException;

    /**
     * **********************************************************
     * PERSONAL JUBILADO ACTIVO
     * ************************************************************
     */
    ReportePersonaDTO reporteJubiladoActivo(Map<String, Object> params) throws JRException, IOException, SQLException;

    /**
     * **********************************************************
     * MOVIMIENTOS DE PERSONAL
     * **********************************************************
     */
    //Reporte histórico movimientos de personal
    ReportePersonaDTO movimientoPersonalHistorico(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    OBTENER PLAZAS x ID AREA
    *******************************************************************************************
     */
    ReportePersonaDTO obtenerMonitorPlazasAutorizadasAreas(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    REPORTE PENSIÓN ALIMENTICIA
    *******************************************************************************************
     */
    ReportePersonaDTO reportePensionAlimenticia(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    REPORTES PARA CONTROL DE ASISTENCIA
    *******************************************************************************************
     */
    ReportePersonaDTO controlAsistenciaPersonalBase(Map<String, Object> params) throws JRException, IOException, SQLException;

    ReportePersonaDTO controlAsistenciaPersonalConfianza(Map<String, Object> params) throws JRException, IOException, SQLException;

    ReportePersonaDTO resumenControlAsistenciaPersonalBase(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    REPORTES PARA AMONESTACIÓN Y SUSPENSIÓN
    *******************************************************************************************
     */
    ReportePersonaDTO suspension_amonestacion(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    REPORTE INCAPACIDADES EXPEDIDAS IMSSS
    *******************************************************************************************
     */
    ReportePersonaDTO incapacidadesExpedidasIMSS(Map<String, Object> params) throws JRException, IOException, SQLException;

    ReportePersonaDTO incapacidadePeriodoPorMotivoIMSS(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    REPORTE INASISTENCIAS SUA IMSS
    *******************************************************************************************
     */
    ReportePersonaDTO inasistenciasSUAIMSS(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    REPORTE AYUDAS
    *******************************************************************************************
     */
    ReportePersonaDTO reporteAyudas(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    REPORTE CONTROL DE ASISTENCIA RA15
    *******************************************************************************************
     */
    ReportePersonaDTO reporteCapturaSemanalRA15(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    REPORTE RELACION DE TRABAJADORES TRANSPORTACION SIN TURNO EN UN PERIODO
    *******************************************************************************************
     */
    ReportePersonaDTO reporteSinTurnoEnElPeriodoRA15(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    REPORTE DEPOSITO DE PAGO PARA DIFERENTES BANCOS
    *******************************************************************************************
     */
    ReportePersonaDTO depositoPagoDiferentesBancos(Map<String, Object> params) throws JRException, IOException, SQLException;

    ReportePersonaDTO depositoPagoDiferentesBancosEx(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    REPORTE DEPOSITO DE PAGO PARA DIFERENTES BANCOS (PA-VALES)
    *******************************************************************************************
     */
    ReportePersonaDTO depositoPagoDiferentesBancosPAVales(Map<String, Object> params) throws JRException, IOException, SQLException;

    ReportePersonaDTO depositoPagoDiferentesBancosPAValesEx(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    REPORTE DEPOSITO DE PAGO PARA DIFERENTES BANCOS (PENSION ALIMENTICIA)
    *******************************************************************************************
     */
    ReportePersonaDTO depositoPagoDiferentesBancosPension(Map<String, Object> params) throws JRException, IOException, SQLException;

    ReportePersonaDTO depositoPagoDiferentesBancosPensionEx(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    REPORTE DEPOSITO DE PAGO PARA DIFERENTES BANCOS (Vales Despensa)
    *******************************************************************************************
     */
    ReportePersonaDTO depositoPagoDiferentesBancosVales(Map<String, Object> params) throws JRException, IOException, SQLException;

    ReportePersonaDTO depositoPagoDiferentesBancosValesEx(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    REPORTE RECIBOS PARA PAGOS DE FINIQUITOS
    *******************************************************************************************
     */
    ReportePersonaDTO reciboFiniquito(Map<String, Object> params) throws JRException, IOException, SQLException;

    ReportePersonaDTO reciboFiniquitoPrestaciones(Map<String, Object> params) throws JRException, IOException, SQLException;

    /*
    *******************************************************************************************
    REPORTE RETROACTIVO PARA JUBILADOS
    *******************************************************************************************
     */
    ReportePersonaDTO reporteRetroJubilados(Map<String, Object> params) throws JRException, IOException, SQLException;

}
