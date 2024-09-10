/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.ReportePersonaDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author rrosero23
 */
public interface ReporteCierrePlantillaService {

    //Reporte R1
    ReportePersonaDTO obtenerMovimientosPlazasMes(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte R3
    ReportePersonaDTO obtenerResumenGeneroeIngresos(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte R4
    ReportePersonaDTO reporteCierrePlantilla(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte R4 SubReporte I 
    ReportePersonaDTO reporteCierrePlantillaSubI(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte R4 SubReporte II
    ReportePersonaDTO reporteCierrePlantillaSubII(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte R4 SubReporte III
    ReportePersonaDTO reporteCierrePlantillaSubIII(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte R5 
    ReportePersonaDTO reportePuestoGenero(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte R6
    ReportePersonaDTO reporteGeneroXRangoEdades(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte R7
    ReportePersonaDTO reporteGeneroXEdades(Map<String, Object> params) throws JRException, IOException, SQLException;

    //Reporte Mensual de Plazas Ocupadas Por Expediente
    ReportePersonaDTO reportePlazasOcupadasPorExpediente(Map<String, Object> params) throws JRException, IOException, SQLException;
    
    //Reporte por Genero y Grado maximo de estudios
    ReportePersonaDTO reportePorGeneroYGradoDeEstudios(Map<String, Object> params) throws JRException, IOException, SQLException;
    
    //Reporte movimiento registrado plantilla
    ReportePersonaDTO reporteMovimientosRegistradosPlantilla(Map<String, Object> params) throws JRException, IOException, SQLException;
    
}
