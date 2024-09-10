/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.Calculo_Dif_Cve19DTO;
import com.sirh.sirh.DTO.Listado_RA_10DTO;
import com.sirh.sirh.DTO.TrabajadorPagoRA10;
import com.sirh.sirh.entity.Captura_Semanal_RA10;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rroscero23
 */
public interface Captura_Semanal_RA10Service {

    public Captura_Semanal_RA10 saveRa10(Captura_Semanal_RA10 captura_Semanal_RA10);

    public Captura_Semanal_RA10 updateRa10(Captura_Semanal_RA10 captura_Semanal_RA10, Integer id_ra_10);

    public List<Captura_Semanal_RA10> findCapturasTrabajador(Integer trabajador_id);

    public Captura_Semanal_RA10 findOneCapturaSemanalRa10(Integer id_ra_10);

    public Captura_Semanal_RA10 cambioEstatusCaptura(Integer id_ra_10, Integer estatus);

    public List<Captura_Semanal_RA10> findCapturaSemanalByFechas(Date fechaInicial, Date fechaFinal, Integer trabajador_id);

    public List<TrabajadorPagoRA10> findListadoExcel(Integer periodo_generacion);

    public List<Listado_RA_10DTO> findAreaAndPeriodoGen(Integer id_area, Integer periodo_generacion);

    public void exportTrabajadoresConMontosRA10(HttpServletResponse response, Integer periodo_generacion) throws IOException;

    public Boolean existeCapturaRa10PorIdTrabajador(Integer trabajador_id, Integer periodo_generacion);

    public List<Captura_Semanal_RA10> buscarCapturaPeriodoTrabajador(Integer trabajador_id, Integer periodo_generacion);

    public Calculo_Dif_Cve19DTO calcularCve19DiferenciaTiempoExtra(Integer trabajador_id, Integer periodo_generacion,
            Double total_te_doble, Double total_te_paseos_doble,
            Double total_te_festivo_doble, Double horas_extras_laboradas_domingo);
}
