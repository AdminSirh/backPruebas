package com.sirh.sirh.service;

import com.sirh.sirh.DTO.ReportePersonaDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author oguerrero23
 */
public interface ReportePrestamoService {
    //Reportes FONACOT
    ReportePersonaDTO obtenerHistoricoFonacot(Map<String, Object> params) throws JRException, IOException, SQLException;
    
    //Reportes Personales
    ReportePersonaDTO obtenerFormatoPrestamoC(Map<String, Object> params) throws JRException, IOException, SQLException;
    
    ReportePersonaDTO obtenerFormatoPrestamoF(Map<String, Object> params) throws JRException, IOException, SQLException;
    
    ReportePersonaDTO obtenerFormatoPrestamoB(Map<String, Object> params) throws JRException, IOException, SQLException;
    
    ReportePersonaDTO obtenerDepositoDiferentesBancos(Map<String, Object> params) throws JRException, IOException, SQLException;
    
    ReportePersonaDTO obtenerDiferentesBancosExcel(Map<String, Object> params) throws JRException, IOException, SQLException;
    
    ReportePersonaDTO obtenerDepositoBanorte(Map<String, Object> params) throws JRException, IOException, SQLException;
    
    ReportePersonaDTO obtenerHistoricoBanorte(Map<String, Object> params) throws JRException, IOException, SQLException;
    
}
