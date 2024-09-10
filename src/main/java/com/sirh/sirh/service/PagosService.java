/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.SDIVariosDTO;
import com.sirh.sirh.entity.Pagos_3;
import com.sirh.sirh.entity.Pagos_5;
import com.sirh.sirh.entity.Tmp_Movimientos;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author oguerrero23
 */
public interface PagosService {

    // *************** PAGOS DE NOMINA DE VARIOS *************************
    public List<Object> consultaPagosVarios(Integer periodo_aplicacion);

    public List<Object> findAllCalculosPercepcionDeduccion1(Integer pago_id);

    public List<Object> findAllPercepcionDeduccion1(Integer pago_id);

    public Double obtenSumaIncidenciasVarios(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id);

    public Double obtenSumaISRVarios(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id);

    public Double obtenIncidenciaVarios(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id);

    // *************** PAGOS DE NOMINA DE TRANSPORTACION *************************
    public List<Object> consultaPagosTransportacion(Integer periodo_aplicacion);

    public List<Object> findAllCalculosPercepcionDeduccion2(Integer pago_id);

    public List<Object> findAllPercepcionDeduccion2(Integer pago_id);

    public Double obtenSumaIncidenciasTransportacion(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id);

    public Double obtenSumaISRTransportacion(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id);

    public Double obtenIncidenciaTransportacion(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id);

    // *************** PAGOS DE NOMINA DE CONFIANZA *************************
    public List<Object> consultaPagosConfianza(Integer periodo_aplicacion);

    public List<Object> findAllCalculosPercepcionDeduccion3(Integer pago_id);

    public List<Object> findAllPercepcionDeduccion3(Integer pago_id);

    public Double obtenSumaIncidenciasConfianza(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id);

    public Double obtenSumaISRConfianza(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id);

    public Double obtenIncidenciaConfianza(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id);

    public List<Pagos_3> obtenPuestosDiferentesConfianza(Date fecha_diciembre, Date fecha_noviembre, Integer trabajador_id);

    // *************** PAGOS DE NOMINA DE ESTRUCTURA *************************
    public List<Object> consultaPagosEstructura(Integer periodo_aplicacion);

    public List<Object> findAllCalculosPercepcionDeduccion5(Integer pago_id);

    public List<Object> findAllPercepcionDeduccion5(Integer pago_id);

    public Double obtenSumaIncidenciasEstructura(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id);

    public Double obtenSumaISREstructura(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id);

    public Double obtenIncidenciaEstructura(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id);

    public List<Pagos_5> obtenPuestosDiferentesEstructura(Date fecha_diciembre, Date fecha_noviembre, Integer trabajador_id);

    // *************** PAGOS DE NOMINA DE JUBILADOS *************************
    public List<Object> consultaPagosJubilados(Integer periodo_aplicacion);

    public List<Object> findAllCalculosPercepcionDeduccion4(Integer pago_id);

    public List<Object> findAllPercepcionDeduccion4(Integer pago_id);

    public Double obtenSumaIncidenciasJubilados(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id);

    public Double obtenSumaISRJubilados(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id);

    public Double obtenIncidenciaJubilados(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id);

    // ******************************** GRABAR INASISTENCIAS (244) EN TMP MOVIMIENTOS********************
    public Tmp_Movimientos saveMovimientos244(Tmp_Movimientos movimientos);

    // ******************************** GRABAR DIAS CONTRATO (243) ********************
    public Tmp_Movimientos saveMovimientos243(Tmp_Movimientos movimientos, Integer anios, Date fecha_antiguedad, Date fecha_final);

}
