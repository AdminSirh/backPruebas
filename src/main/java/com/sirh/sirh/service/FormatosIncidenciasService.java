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
public interface FormatosIncidenciasService {

    //Autorización de Vacaciones
    ReportePersonaDTO autorizacionVacaciones(Map<String, Object> params) throws JRException, IOException, SQLException;

}
