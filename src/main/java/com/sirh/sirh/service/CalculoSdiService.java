/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.SdiDTO;
import com.sirh.sirh.entity.Rela_Calculos_Sdi_Cve;
import com.sirh.sirh.entity.Tmp_Sdi_Acum_Bim;
import com.sirh.sirh.entity.Tmp_Sdi_Calculos_Bim;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *
 * @author rroscero23
 */
public interface CalculoSdiService {

    public Rela_Calculos_Sdi_Cve findRelacionCalculoIncidencia(String valorCampo);

    public Tmp_Sdi_Acum_Bim saveTmpAcumBim(Integer idTrabajador,
            Integer idBimestre,
            LocalDate fechaEgreso,
            Double totalCve1,
            Double totalCve3,
            Double totalCve4,
            Double totalCve5,
            Double totalCve6,
            Double totalCve7,
            Double totalCve9,
            Double totalCve10,
            Double totalCve11,
            Double totalCve12,
            Double totalCve14,
            Double totalCve15,
            Double totalCve16,
            Double totalCve17,
            Double totalCve18,
            Double totalCve19,
            Double totalCve21,
            Double totalCve22,
            Double totalCve23,
            Double totalCve24,
            Double totalCve26,
            Double totalCve27,
            Double totalCve28,
            Double totalCve30,
            Double totalCve31,
            Double totalCve32,
            Double totalCve33,
            Double totalCve36,
            Double totalCve39,
            Double totalCve41,
            Double totalCve42,
            Double totalCve44,
            Double totalCve45,
            Double totalCve46,
            Double totalCve207,
            Double totalCve217,
            Double totalCve222,
            Double totalCve231,
            Double totalCve237,
            Double totalCve246,
            Double totalCve247,
            Double totalCve248,
            Double totalCve249,
            Double totalCve1002,
            Double totalCve1008,
            Double totalInci21,
            Double totalInci27,
            Double totalInci101,
            Double totalCve1210,
            Double totalCve1211,
            Double totalCve1212,
            Double totalCve1213,
            Double totalInci1214,
            Double totalInci1218);

    public Tmp_Sdi_Calculos_Bim saveCalculosBimestre(Integer idTrabajador,
            Integer idBimestre,
            Double cve01,
            Double cve10,
            Double cve12,
            Double cve16,
            Double cve31,
            Double cve32,
            Double cve33,
            Double cve44,
            Double cve231,
            Double totalSalarioFijo,
            Double diasPagados,
            Double diasIncapacidad,
            Double diasVacaciones,
            Double estimuloVales,
            Double cve01Var,
            Double cve03Var,
            Double cve05Var,
            Double cve06Var,
            Double cve07Var,
            Double cve11Var,
            Double cve14Var,
            Double cve15Var,
            Double cve23Var,
            Double cve24Var,
            Double cve41Var,
            Double cve217Var,
            Double cve222Var,
            Double cve237Var,
            Double cve249Var,
            Double cve09Var,
            Double cve21Var,
            Double cve10Var,
            Double cve17Var,
            Double cve27Var,
            Double difFondoAhorro,
            Double difPrimaVacacional,
            Double importeEstimuloPuntualidad,
            Double importeEstimuloAsistencia,
            Double sdiBaseCotizacion,
            Double sdiVariable,
            Double diasIncapacidadVar,
            Double sdiMixto,
            Double salarioMinimoDiario,
            Double difSueldo,
            Double diasPrimaVacacionalAntiguedad,
            Double clausula45Cct);

    public Tmp_Sdi_Calculos_Bim saveCalculosBimestreFijos(
            Integer idTrabajador,
            Integer idBimestre,
            Double cve01,
            Double cve10,
            Double cve12,
            Double cve16,
            Double cve31,
            Double cve32,
            Double cve33,
            Double cve44,
            Double cve231,
            Double totalSalarioFijo,
            Double sdiMixto);

    public void calcularSdiPorNomina(Integer nomina_id, List<Integer> periodos_considerados, Integer idBimestre, SseEmitter emitter) throws IOException;

    ;

    public void calcularSdiNuevoIngreso(Integer idTrabajador, Integer idBimestre);

    public void exportarConceptosCalculados(HttpServletResponse response, Integer idNomina, Integer idBimestre) throws IOException;

    public void exportarCalculosObtenidosSdiVarios(HttpServletResponse response, List<Integer> periodos) throws IOException;

    public void exportarCalculosObtenidosSdiTransportacion(HttpServletResponse response, List<Integer> periodos) throws IOException;

    public void exportarCalculosObtenidosConfianza(HttpServletResponse response, List<Integer> periodos) throws IOException;

    public void exportarCalculosObtenidosSdiEstructura(HttpServletResponse response, List<Integer> periodos) throws IOException;

    public List<SdiDTO> findBimestresSdi(Integer idNomina, Integer idBimestre);

    public Boolean existeBimestreCalculado(Integer idBimestre);

    public Boolean existeBimestreCalculadoTrabajador(Integer idTrabajador, Integer idBimestre);

    public Boolean existeBimestreAcumulado(Integer idBimestre);

    public List<Tmp_Sdi_Calculos_Bim> findDetalleCalculoBimTrab(Integer idBimestre, Integer idTrabajador);

    public String generarTxSuaModif(Integer idBimestre, Integer idNomina);

    public String generarTxIdseModif(Integer idBimestre, Integer idNomina, Integer idTipoMovimiento);

    public void borrarAcumuladosBimestre(Integer idBimestre);

    public void borrarCalculosBimestre(Integer idBimestre);

}
