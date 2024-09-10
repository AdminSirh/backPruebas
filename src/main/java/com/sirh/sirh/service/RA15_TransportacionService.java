/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.CapturaSemanalRA15DTO;
import com.sirh.sirh.DTO.Captura_Semanal_Resumen_IncidenciasRA15DTO;
import com.sirh.sirh.DTO.OperadorTitularDTO;
import com.sirh.sirh.DTO.OperadorTrenDTO;
import com.sirh.sirh.DTO.Trabajador_RA15DTO;
import com.sirh.sirh.entity.Captura_Semanal_RA15;
import com.sirh.sirh.entity.Historico_Captura_Semanal_RA15;
import com.sirh.sirh.entity.Historico_Tmp_Libro_RA15;
import com.sirh.sirh.entity.Historico_Tmp_Libro_Tren_RA15;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author rroscero23
 */
public interface RA15_TransportacionService {

    public List<Captura_Semanal_RA15> findByIdTrabajadorAndIdPeriodo(Integer trabajadorId, Integer periodoId);

    public List<Captura_Semanal_RA15> findByIdTrabajadorExpedienteAndIdPeriodo(String numero_expediente, Integer periodoId);

    public Captura_Semanal_RA15 saveCapturaSemanal(Captura_Semanal_RA15 captura_Semanal_RA15);

    public Captura_Semanal_RA15 updateCapturaSemanal(Captura_Semanal_RA15 captura_Semanal_RA15, Integer id_ra);

    public List<CapturaSemanalRA15DTO> reporteCSVCapturaSemanal(Integer periodo_id);

    public Historico_Captura_Semanal_RA15 findHistoricoRA15(Integer trabajador_id, Integer periodo_id);

    public List<Trabajador_RA15DTO> trabajadoresRA15Periodo(Integer periodo_id);

    public List<Captura_Semanal_Resumen_IncidenciasRA15DTO> reporteCSVCapturaSemanalResumen(Integer periodo_id);

    public List<Historico_Tmp_Libro_RA15> listarAllDatosPlantas();

    public Historico_Tmp_Libro_RA15 saveNewPlantas(Historico_Tmp_Libro_RA15 historico_Tmp_Libro_RA15);

    void importarPlantasExcel(MultipartFile archivoExcel, String nombreHoja, Integer anio) throws IOException;

    public void updateEstatusRegistrosAnteriores(Integer anio, Integer expediente);

    public void updateEstatusRegistrosAnterioresTrenLigero(Integer anio, Integer expediente);

    public List<Historico_Tmp_Libro_RA15> findAllPlantasActivas();

    public List<Historico_Tmp_Libro_Tren_RA15> findAllPlantasActivasTrenLigero();

    public Historico_Tmp_Libro_RA15 findOnePlanta(Integer id_planta);

    public Historico_Tmp_Libro_RA15 updatePlanta(Historico_Tmp_Libro_RA15 plantaAutorizada, Integer id_planta);

    public void findRelevos();

    //public List<Historico_Tmp_Libro_RA15> findHorasTrabajadas(Integer expediente, Integer dia);
    public List<OperadorTitularDTO> findActiveOpTitular();

    public List<OperadorTrenDTO> findActiveOpTitularTren();

    public Boolean existsByAnioAndEstatusTmpHistorico(Integer anio);

    public Boolean existsByAnioAndEstatusTmpHistoricoTren(Integer anio);

    public void exportPlantasToExcel(HttpServletResponse response) throws IOException;

    public String findLineaByExpediete(Integer expedente);

}
