/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.SDIVariosDTO;
import com.sirh.sirh.entity.Cat_Incidencias;
import com.sirh.sirh.entity.Cat_Percepcion;
import com.sirh.sirh.entity.Pagos_3;
import com.sirh.sirh.entity.Pagos_5;
import com.sirh.sirh.entity.Pagos_Calculos_Deduccion_1;
import com.sirh.sirh.entity.Pagos_Calculos_Deduccion_2;
import com.sirh.sirh.entity.Pagos_Calculos_Deduccion_3;
import com.sirh.sirh.entity.Pagos_Calculos_Deduccion_4;
import com.sirh.sirh.entity.Pagos_Calculos_Deduccion_5;
import com.sirh.sirh.entity.Pagos_Calculos_Percepcion_1;
import com.sirh.sirh.entity.Pagos_Calculos_Percepcion_2;
import com.sirh.sirh.entity.Pagos_Calculos_Percepcion_3;
import com.sirh.sirh.entity.Pagos_Calculos_Percepcion_4;
import com.sirh.sirh.entity.Pagos_Calculos_Percepcion_5;
import com.sirh.sirh.entity.Pagos_Deducciones_1;
import com.sirh.sirh.entity.Pagos_Deducciones_2;
import com.sirh.sirh.entity.Pagos_Deducciones_3;
import com.sirh.sirh.entity.Pagos_Deducciones_4;
import com.sirh.sirh.entity.Pagos_Deducciones_5;
import com.sirh.sirh.entity.Pagos_Percepciones_1;
import com.sirh.sirh.entity.Pagos_Percepciones_2;
import com.sirh.sirh.entity.Pagos_Percepciones_3;
import com.sirh.sirh.entity.Pagos_Percepciones_4;
import com.sirh.sirh.entity.Pagos_Percepciones_5;
import com.sirh.sirh.entity.Tmp_Movimientos;
import com.sirh.sirh.entity.Trabajador;
import com.sirh.sirh.repository.Pagos_1Repository;
import com.sirh.sirh.repository.Pagos_2Repository;
import com.sirh.sirh.repository.Pagos_3Repository;
import com.sirh.sirh.repository.Pagos_4Repository;
import com.sirh.sirh.repository.Pagos_5Repository;
import com.sirh.sirh.repository.Pagos_Calculos_Deduccion_1Repository;
import com.sirh.sirh.repository.Pagos_Calculos_Deduccion_2Repository;
import com.sirh.sirh.repository.Pagos_Calculos_Deduccion_3Repository;
import com.sirh.sirh.repository.Pagos_Calculos_Deduccion_4Repository;
import com.sirh.sirh.repository.Pagos_Calculos_Deduccion_5Repository;
import com.sirh.sirh.repository.Pagos_Calculos_Percepcion_1Repository;
import com.sirh.sirh.repository.Pagos_Calculos_Percepcion_2Repository;
import com.sirh.sirh.repository.Pagos_Calculos_Percepcion_3Repository;
import com.sirh.sirh.repository.Pagos_Calculos_Percepcion_4Repository;
import com.sirh.sirh.repository.Pagos_Calculos_Percepcion_5Repository;
import com.sirh.sirh.repository.Pagos_Deducciones_1Repository;
import com.sirh.sirh.repository.Pagos_Deducciones_2Repository;
import com.sirh.sirh.repository.Pagos_Deducciones_3Repository;
import com.sirh.sirh.repository.Pagos_Deducciones_4Repository;
import com.sirh.sirh.repository.Pagos_Deducciones_5Repository;
import com.sirh.sirh.repository.Pagos_Percepciones_1Repository;
import com.sirh.sirh.repository.Pagos_Percepciones_2Repository;
import com.sirh.sirh.repository.Pagos_Percepciones_3Repository;
import com.sirh.sirh.repository.Pagos_Percepciones_4Repository;
import com.sirh.sirh.repository.Pagos_Percepciones_5Repository;
import com.sirh.sirh.repository.Tmp_MovimientosRepository;
import com.sirh.sirh.service.PagosService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author oguerrero23
 */
@Service
@Transactional
public class PagosServiceImpl implements PagosService {

    @Autowired
    private Pagos_1Repository pagos_1Repository;

    @Autowired
    private Pagos_Calculos_Percepcion_1Repository pagos_Calculos_Percepcion_1Repository;

    @Autowired
    private Pagos_Calculos_Deduccion_1Repository pagos_Calculos_Deduccion_1Repository;

    @Autowired
    private Pagos_Percepciones_1Repository pagos_Percepciones_1Repository;

    @Autowired
    private Pagos_Deducciones_1Repository pagos_Deducciones_1Repository;

    @Autowired
    private Pagos_2Repository pagos_2Repository;

    @Autowired
    private Pagos_Calculos_Percepcion_2Repository pagos_Calculos_Percepcion_2Repository;

    @Autowired
    private Pagos_Calculos_Deduccion_2Repository pagos_Calculos_Deduccion_2Repository;

    @Autowired
    private Pagos_Percepciones_2Repository pagos_Percepciones_2Repository;

    @Autowired
    private Pagos_Deducciones_2Repository pagos_Deducciones_2Repository;

    @Autowired
    private Pagos_3Repository pagos_3Repository;

    @Autowired
    private Pagos_Calculos_Percepcion_3Repository pagos_Calculos_Percepcion_3Repository;

    @Autowired
    private Pagos_Calculos_Deduccion_3Repository pagos_Calculos_Deduccion_3Repository;

    @Autowired
    private Pagos_Percepciones_3Repository pagos_Percepciones_3Repository;

    @Autowired
    private Pagos_Deducciones_3Repository pagos_Deducciones_3Repository;

    @Autowired
    private Pagos_4Repository pagos_4Repository;

    @Autowired
    private Pagos_Calculos_Percepcion_4Repository pagos_Calculos_Percepcion_4Repository;

    @Autowired
    private Pagos_Calculos_Deduccion_4Repository pagos_Calculos_Deduccion_4Repository;

    @Autowired
    private Pagos_Percepciones_4Repository pagos_Percepciones_4Repository;

    @Autowired
    private Pagos_Deducciones_4Repository pagos_Deducciones_4Repository;

    @Autowired
    private Pagos_5Repository pagos_5Repository;

    @Autowired
    private Pagos_Calculos_Percepcion_5Repository pagos_Calculos_Percepcion_5Repository;

    @Autowired
    private Pagos_Calculos_Deduccion_5Repository pagos_Calculos_Deduccion_5Repository;

    @Autowired
    private Pagos_Percepciones_5Repository pagos_Percepciones_5Repository;

    @Autowired
    private Pagos_Deducciones_5Repository pagos_Deducciones_5Repository;

    @Autowired
    private Tmp_MovimientosRepository tmp_MovimientosRepository;

    // *************** PAGOS DE NOMINA DE VARIOS *********************************
    @Override
    public List<Object> consultaPagosVarios(Integer periodo_aplicacion) {
        return pagos_1Repository.consultaPagosVarios(periodo_aplicacion);
    }

    @Override
    public Double obtenSumaIncidenciasVarios(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_1Repository.obtenSumaIncidenciasVarios(primer_periodo, segundo_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Double obtenSumaISRVarios(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_1Repository.obtenSumaISRVarios(primer_periodo, segundo_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Double obtenIncidenciaVarios(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_1Repository.obtenIncidenciaVarios(primer_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public List<Object> findAllCalculosPercepcionDeduccion1(Integer pago_id) {
        List<Object> resultados = new ArrayList<>();

        // Obtener resultados de la primera tabla
        List<Pagos_Calculos_Percepcion_1> percepciones = pagos_Calculos_Percepcion_1Repository.findAllCalculosPercepcion1(pago_id);

        // Agregar los resultados de la primera tabla a la lista combinada
        resultados.addAll(percepciones);

        // Obtener resultados de la segunda tabla
        List<Pagos_Calculos_Deduccion_1> deducciones = pagos_Calculos_Deduccion_1Repository.findAllCalculosDeduccion1(pago_id);

        // Agregar los resultados de la segunda tabla a la lista combinada
        resultados.addAll(deducciones);
        return resultados;
    }

    @Override
    public List<Object> findAllPercepcionDeduccion1(Integer pago_id) {
        List<Object> resultados = new ArrayList<>();

        // Obtener resultados de la primera tabla
        List<Pagos_Percepciones_1> percepciones = pagos_Percepciones_1Repository.findAllPercepciones1(pago_id);

        // Agregar los resultados de la primera tabla a la lista combinada
        resultados.addAll(percepciones);

        // Obtener resultados de la segunda tabla
        List<Pagos_Deducciones_1> deducciones = pagos_Deducciones_1Repository.findAllDeducciones1(pago_id);

        // Agregar los resultados de la segunda tabla a la lista combinada
        resultados.addAll(deducciones);

        return resultados;
    }

    // *************** PAGOS DE NOMINA DE TRANSPORTACION *************************
    @Override
    public List<Object> consultaPagosTransportacion(Integer periodo_aplicacion) {
        return pagos_2Repository.consultaPagosTransportacion(periodo_aplicacion);
    }

    @Override
    public Double obtenSumaIncidenciasTransportacion(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_2Repository.obtenSumaIncidenciasTransportacion(primer_periodo, segundo_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Double obtenSumaISRTransportacion(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_2Repository.obtenSumaISRTransportacion(primer_periodo, segundo_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Double obtenIncidenciaTransportacion(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_2Repository.obtenIncidenciaTransportacion(primer_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public List<Object> findAllCalculosPercepcionDeduccion2(Integer pago_id) {
        List<Object> resultados = new ArrayList<>();

        // Obtener resultados de la primera tabla
        List<Pagos_Calculos_Percepcion_2> percepciones = pagos_Calculos_Percepcion_2Repository.findAllCalculosPercepcion2(pago_id);

        // Agregar los resultados de la primera tabla a la lista combinada
        resultados.addAll(percepciones);

        // Obtener resultados de la segunda tabla
        List<Pagos_Calculos_Deduccion_2> deducciones = pagos_Calculos_Deduccion_2Repository.findAllCalculosDeduccion2(pago_id);

        // Agregar los resultados de la segunda tabla a la lista combinada
        resultados.addAll(deducciones);

        return resultados;
    }

    @Override
    public List<Object> findAllPercepcionDeduccion2(Integer pago_id) {
        List<Object> resultados = new ArrayList<>();

        // Obtener resultados de la primera tabla
        List<Pagos_Percepciones_2> percepciones = pagos_Percepciones_2Repository.findAllPercepciones2(pago_id);

        // Agregar los resultados de la primera tabla a la lista combinada
        resultados.addAll(percepciones);

        // Obtener resultados de la segunda tabla
        List<Pagos_Deducciones_2> deducciones = pagos_Deducciones_2Repository.findAllDeducciones2(pago_id);

        // Agregar los resultados de la segunda tabla a la lista combinada
        resultados.addAll(deducciones);

        return resultados;
    }

    // *************** PAGOS DE NOMINA DE CONFIANZA ******************************
    @Override
    public List<Object> consultaPagosConfianza(Integer periodo_aplicacion) {
        return pagos_3Repository.consultaPagosConfianza(periodo_aplicacion);
    }

    @Override
    public Double obtenSumaIncidenciasConfianza(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_3Repository.obtenSumaIncidenciasConfianza(primer_periodo, segundo_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Double obtenSumaISRConfianza(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_3Repository.obtenSumaISRConfianza(primer_periodo, segundo_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Double obtenIncidenciaConfianza(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_3Repository.obtenIncidenciaConfianza(primer_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public List<Object> findAllCalculosPercepcionDeduccion3(Integer pago_id) {
        List<Object> resultados = new ArrayList<>();

        // Obtener resultados de la primera tabla
        List<Pagos_Calculos_Percepcion_3> percepciones = pagos_Calculos_Percepcion_3Repository.findAllCalculosPercepcion3(pago_id);

        // Agregar los resultados de la primera tabla a la lista combinada
        resultados.addAll(percepciones);

        // Obtener resultados de la segunda tabla
        List<Pagos_Calculos_Deduccion_3> deducciones = pagos_Calculos_Deduccion_3Repository.findAllCalculosDeduccion3(pago_id);

        // Agregar los resultados de la segunda tabla a la lista combinada
        resultados.addAll(deducciones);

        return resultados;
    }

    @Override
    public List<Object> findAllPercepcionDeduccion3(Integer pago_id) {
        List<Object> resultados = new ArrayList<>();

        // Obtener resultados de la primera tabla
        List<Pagos_Percepciones_3> percepciones = pagos_Percepciones_3Repository.findAllPercepciones3(pago_id);

        // Agregar los resultados de la primera tabla a la lista combinada
        resultados.addAll(percepciones);

        // Obtener resultados de la segunda tabla
        List<Pagos_Deducciones_3> deducciones = pagos_Deducciones_3Repository.findAllDeducciones3(pago_id);

        // Agregar los resultados de la segunda tabla a la lista combinada
        resultados.addAll(deducciones);

        return resultados;
    }

    @Override
    public List<Pagos_3> obtenPuestosDiferentesConfianza(Date fecha_diciembre, Date fecha_noviembre, Integer trabajador_id) {
        return pagos_3Repository.obtenPuestosDiferentes(fecha_diciembre, fecha_noviembre, trabajador_id);
    }

    // *************** PAGOS DE NOMINA DE ESTRUCTURA ******************************
    @Override
    public List<Object> consultaPagosEstructura(Integer periodo_aplicacion) {
        return pagos_5Repository.consultaPagosEstructura(periodo_aplicacion);
    }

    @Override
    public Double obtenSumaIncidenciasEstructura(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_5Repository.obtenSumaIncidenciasEstructura(primer_periodo, segundo_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Double obtenSumaISREstructura(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_5Repository.obtenSumaISREstructura(primer_periodo, segundo_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Double obtenIncidenciaEstructura(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_5Repository.obtenIncidenciaEstructura(primer_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public List<Object> findAllCalculosPercepcionDeduccion5(Integer pago_id) {
        List<Object> resultados = new ArrayList<>();

        // Obtener resultados de la primera tabla
        List<Pagos_Calculos_Percepcion_5> percepciones = pagos_Calculos_Percepcion_5Repository.findAllCalculosPercepcion5(pago_id);

        // Agregar los resultados de la primera tabla a la lista combinada
        resultados.addAll(percepciones);

        // Obtener resultados de la segunda tabla
        List<Pagos_Calculos_Deduccion_5> deducciones = pagos_Calculos_Deduccion_5Repository.findAllCalculosDeduccion5(pago_id);

        // Agregar los resultados de la segunda tabla a la lista combinada
        resultados.addAll(deducciones);

        return resultados;
    }

    @Override
    public List<Object> findAllPercepcionDeduccion5(Integer pago_id) {
        List<Object> resultados = new ArrayList<>();

        // Obtener resultados de la primera tabla
        List<Pagos_Percepciones_5> percepciones = pagos_Percepciones_5Repository.findAllPercepciones5(pago_id);

        // Agregar los resultados de la primera tabla a la lista combinada
        resultados.addAll(percepciones);

        // Obtener resultados de la segunda tabla
        List<Pagos_Deducciones_5> deducciones = pagos_Deducciones_5Repository.findAllDeducciones5(pago_id);

        // Agregar los resultados de la segunda tabla a la lista combinada
        resultados.addAll(deducciones);

        return resultados;
    }

    @Override
    public List<Pagos_5> obtenPuestosDiferentesEstructura(Date fecha_diciembre, Date fecha_noviembre, Integer trabajador_id) {
        return pagos_5Repository.obtenPuestosDiferentes(fecha_diciembre, fecha_noviembre, trabajador_id);
    }

    // *************** PAGOS DE NOMINA DE JUBILADOS ******************************
    @Override
    public List<Object> consultaPagosJubilados(Integer periodo_aplicacion) {
        return pagos_4Repository.consultaPagosJubilados(periodo_aplicacion);
    }

    @Override
    public Double obtenSumaIncidenciasJubilados(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_4Repository.obtenSumaIncidenciasJubilados(primer_periodo, segundo_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Double obtenSumaISRJubilados(Integer primer_periodo, Integer segundo_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_4Repository.obtenSumaISRJubilados(primer_periodo, segundo_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Double obtenIncidenciaJubilados(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_4Repository.obtenIncidenciaJubilados(primer_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public List<Object> findAllCalculosPercepcionDeduccion4(Integer pago_id) {
        List<Object> resultados = new ArrayList<>();

        // Obtener resultados de la primera tabla
        List<Pagos_Calculos_Percepcion_4> percepciones = pagos_Calculos_Percepcion_4Repository.findAllCalculosPercepcion4(pago_id);

        // Agregar los resultados de la primera tabla a la lista combinada
        resultados.addAll(percepciones);

        // Obtener resultados de la segunda tabla
        List<Pagos_Calculos_Deduccion_4> deducciones = pagos_Calculos_Deduccion_4Repository.findAllCalculosDeduccion4(pago_id);

        // Agregar los resultados de la segunda tabla a la lista combinada
        resultados.addAll(deducciones);

        return resultados;
    }

    @Override
    public List<Object> findAllPercepcionDeduccion4(Integer pago_id) {
        List<Object> resultados = new ArrayList<>();

        // Obtener resultados de la primera tabla
        List<Pagos_Percepciones_4> percepciones = pagos_Percepciones_4Repository.findAllPercepciones4(pago_id);

        // Agregar los resultados de la primera tabla a la lista combinada
        resultados.addAll(percepciones);

        // Obtener resultados de la segunda tabla
        List<Pagos_Deducciones_4> deducciones = pagos_Deducciones_4Repository.findAllDeducciones4(pago_id);

        // Agregar los resultados de la segunda tabla a la lista combinada
        resultados.addAll(deducciones);

        return resultados;
    }

    // ******************************** GRABAR INASISTENCIAS (244 Cve Nomina - Id_Incidencia 107) EN TMP MOVIMIENTOS********************
    @Override
    public Tmp_Movimientos saveMovimientos244(Tmp_Movimientos movimientos) {
        Tmp_Movimientos m = new Tmp_Movimientos();
        LocalDate fecha = LocalDate.now();
        Cat_Incidencias catPercepcion = new Cat_Incidencias();
        catPercepcion.setId_incidencia(107);
        m.setTrabajador(movimientos.getTrabajador());
        m.setCat_Incidencias(catPercepcion);
        m.setAnio_aplicacion(movimientos.getAnio_aplicacion());
        m.setPeriodo_generacion(movimientos.getPeriodo_generacion());
        m.setPeriodo_aplicacion(movimientos.getPeriodo_aplicacion());
        m.setCat_Tipo_Nomina(movimientos.getCat_Tipo_Nomina());
        m.setValor_1(movimientos.getValor_1());
        m.setFecha_creacion(fecha);
        saveMovimientos245(movimientos);
        return tmp_MovimientosRepository.save(m);
    }

    // ******************************** GRABAR DIAS CONTRATO (243 Cve Nomina - Id_Incidencia 106) EN TMP MOVIMIENTOS********************
    @Override
    public Tmp_Movimientos saveMovimientos243(Tmp_Movimientos movimientos, Integer anios, Date fecha_antiguedad, Date fecha_final) {
        double dias_contrato = 0;
        int dias_adicionales = 0;
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(fecha_antiguedad);
        int mes_antiguedad = calendar.get(Calendar.MONTH);
        calendar.setTime(fecha_final);
        int mes_final = calendar.get(Calendar.MONTH);
        // Calcular la diferencia en milisegundos
        long diferencia_milisegundos = fecha_final.getTime() - fecha_antiguedad.getTime();

        // Convertir la diferencia en milisegundos a días
        long diferencia_dias = TimeUnit.MILLISECONDS.toDays(diferencia_milisegundos);
        if (anios > 0) {
            dias_contrato = 120;
        } else if (anios == 0 && mes_antiguedad < (mes_final - 3)) {
            dias_contrato = 120;
        } else {
            Map<Integer, Integer> diasAdicionalesMap = new HashMap<>();
            if (mes_final == 4) {
                diasAdicionalesMap.put(1, 2);
                diasAdicionalesMap.put(2, 1);
                diasAdicionalesMap.put(3, 1);
                diasAdicionalesMap.put(4, 2);
                diasAdicionalesMap.put(5, 2);
            } else if (mes_final == 8) {
                diasAdicionalesMap.put(1, 5);
                diasAdicionalesMap.put(2, 4);
                diasAdicionalesMap.put(3, 4);
                diasAdicionalesMap.put(4, 3);
                diasAdicionalesMap.put(5, 3);
                diasAdicionalesMap.put(6, 2);
                diasAdicionalesMap.put(7, 2);
                diasAdicionalesMap.put(8, 1);
            } else {
                diasAdicionalesMap.put(1, 5);
                diasAdicionalesMap.put(2, 4);
                diasAdicionalesMap.put(3, 6);
                diasAdicionalesMap.put(4, 5);
                diasAdicionalesMap.put(5, 5);
                diasAdicionalesMap.put(6, 4);
                diasAdicionalesMap.put(7, 4);
                diasAdicionalesMap.put(8, 3);
                diasAdicionalesMap.put(9, 2);
                diasAdicionalesMap.put(10, 2);
                diasAdicionalesMap.put(11, 1);
                diasAdicionalesMap.put(12, 1);
            }

            dias_adicionales = diasAdicionalesMap.getOrDefault(mes_antiguedad, 0);
            dias_contrato = (diferencia_dias + 1) + dias_adicionales;
        }

        Tmp_Movimientos m = new Tmp_Movimientos();
        LocalDate fecha = LocalDate.now();
        Cat_Incidencias catPercepcion = new Cat_Incidencias();
        catPercepcion.setId_incidencia(106);
        m.setTrabajador(movimientos.getTrabajador());
        m.setCat_Incidencias(catPercepcion);
        m.setAnio_aplicacion(movimientos.getAnio_aplicacion());
        m.setPeriodo_generacion(movimientos.getPeriodo_generacion());
        m.setPeriodo_aplicacion(movimientos.getPeriodo_aplicacion());
        m.setCat_Tipo_Nomina(movimientos.getCat_Tipo_Nomina());
        m.setValor_1(dias_contrato);
        m.setFecha_creacion(fecha);
        return tmp_MovimientosRepository.save(m);
    }

    // ******************************** GRABAR DIAS AGUINALDO ANTERIOR (245 Cve Nomina - Id_incidencia 108) EN TMP MOVIMIENTOS********************
    public void saveMovimientos245(Tmp_Movimientos movimientos) {
        LocalDate currentDate = LocalDate.now();
        Tmp_Movimientos m = new Tmp_Movimientos();
        LocalDate fecha = LocalDate.now();
        int currentYear = currentDate.getYear();
        int periodo_85 = Integer.parseInt(currentYear + "85");
        int periodo_86 = Integer.parseInt(currentYear + "86");
        if (Integer.toString(movimientos.getPeriodo_aplicacion()).equals(currentYear + "86") || Integer.toString(movimientos.getPeriodo_aplicacion()).equals(currentYear + "87")) {

            Double incidencia_243_varios = obtenSumaIncidenciasVarios(periodo_85, periodo_86, 106, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_244_varios = obtenSumaIncidenciasVarios(periodo_85, periodo_86, 107, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_243_transportacion = obtenSumaIncidenciasTransportacion(periodo_85, periodo_86, 106, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_244_transportacion = obtenSumaIncidenciasTransportacion(periodo_85, periodo_86, 107, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_243_confianza = obtenSumaIncidenciasConfianza(periodo_85, periodo_86, 106, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_244_confianza = obtenSumaIncidenciasConfianza(periodo_85, periodo_86, 107, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_243_estructura = obtenSumaIncidenciasEstructura(periodo_85, periodo_86, 106, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_244_estructura = obtenSumaIncidenciasEstructura(periodo_85, periodo_86, 107, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_243_jubilados = obtenSumaIncidenciasJubilados(periodo_85, periodo_86, 106, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_244_jubilados = obtenSumaIncidenciasJubilados(periodo_85, periodo_86, 107, movimientos.getTrabajador().getId_trabajador());

            double incidencia_243_varios_val = (incidencia_243_varios == null) ? 0.0 : incidencia_243_varios;
            double incidencia_244_varios_val = (incidencia_244_varios == null) ? 0.0 : incidencia_244_varios;
            double incidencia_243_transportacion_val = (incidencia_243_transportacion == null) ? 0.0 : incidencia_243_transportacion;
            double incidencia_244_transportacion_val = (incidencia_244_transportacion == null) ? 0.0 : incidencia_244_transportacion;
            double incidencia_243_confianza_val = (incidencia_243_confianza == null) ? 0.0 : incidencia_243_confianza;
            double incidencia_244_confianza_val = (incidencia_244_confianza == null) ? 0.0 : incidencia_244_confianza;
            double incidencia_243_estructura_val = (incidencia_243_estructura == null) ? 0.0 : incidencia_243_estructura;
            double incidencia_244_estructura_val = (incidencia_244_estructura == null) ? 0.0 : incidencia_244_estructura;
            double incidencia_243_jubilados_val = (incidencia_243_jubilados == null) ? 0.0 : incidencia_243_jubilados;
            double incidencia_244_jubilados_val = (incidencia_244_jubilados == null) ? 0.0 : incidencia_244_jubilados;
            double suma_243 = incidencia_243_varios_val + incidencia_243_transportacion_val + incidencia_243_confianza_val + incidencia_243_estructura_val + incidencia_243_jubilados_val;
            double suma_244 = incidencia_244_varios_val + incidencia_244_transportacion_val + incidencia_244_confianza_val + incidencia_244_estructura_val + incidencia_244_jubilados_val;

            double dias_pagados = (20 / 120.0) * (suma_243 - suma_244);
            Cat_Incidencias catPercepcion = new Cat_Incidencias();
            catPercepcion.setId_incidencia(108);
            m.setTrabajador(movimientos.getTrabajador());
            m.setCat_Incidencias(catPercepcion);
            m.setAnio_aplicacion(movimientos.getAnio_aplicacion());
            m.setPeriodo_generacion(movimientos.getPeriodo_generacion());
            m.setPeriodo_aplicacion(movimientos.getPeriodo_aplicacion());
            m.setCat_Tipo_Nomina(movimientos.getCat_Tipo_Nomina());
            m.setValor_1(dias_pagados);
            m.setFecha_creacion(fecha);
            saveMovimientos330And332(movimientos);
            tmp_MovimientosRepository.save(m);
        }

    }

    // ******************************** GRABAR IMPORTE AGUINALDO ANTERIOR (330 Cve Nomina - Id_Incidencia 168, 332 Cve Nomina - Id_Incidencia 170) EN TMP MOVIMIENTOS********************
    public void saveMovimientos330And332(Tmp_Movimientos movimientos) {
        LocalDate currentDate = LocalDate.now();

        LocalDate fecha = LocalDate.now();
        int currentYear = currentDate.getYear();
        int periodo_85 = Integer.parseInt(currentYear + "85");
        int periodo_86 = Integer.parseInt(currentYear + "86");

        if (Integer.toString(movimientos.getPeriodo_aplicacion()).equals(currentYear + "86")) {
            Tmp_Movimientos m = new Tmp_Movimientos();
            Double incidencia_varios_85 = obtenIncidenciaVarios(periodo_85, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_transportacion_85 = obtenIncidenciaTransportacion(periodo_85, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_confianza_85 = obtenIncidenciaConfianza(periodo_85, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_estructura_85 = obtenIncidenciaEstructura(periodo_85, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_jubilados_85 = obtenIncidenciaJubilados(periodo_85, 48, movimientos.getTrabajador().getId_trabajador());

            double incidencia_12_varios_val = (incidencia_varios_85 == null) ? 0.0 : incidencia_varios_85;
            double incidencia_12_transportacion_val = (incidencia_transportacion_85 == null) ? 0.0 : incidencia_transportacion_85;
            double incidencia_12_confianza_val = (incidencia_confianza_85 == null) ? 0.0 : incidencia_confianza_85;
            double incidencia_12_estructura_val = (incidencia_estructura_85 == null) ? 0.0 : incidencia_estructura_85;
            double incidencia_12_jubilados_val = (incidencia_jubilados_85 == null) ? 0.0 : incidencia_jubilados_85;
            double aguinaldo_anterior_85 = incidencia_12_varios_val + incidencia_12_transportacion_val + incidencia_12_confianza_val + incidencia_12_estructura_val + incidencia_12_jubilados_val;

            Cat_Incidencias catPercepcion = new Cat_Incidencias();
            catPercepcion.setId_incidencia(168);
            m.setTrabajador(movimientos.getTrabajador());
            m.setCat_Incidencias(catPercepcion);
            m.setAnio_aplicacion(movimientos.getAnio_aplicacion());
            m.setPeriodo_generacion(movimientos.getPeriodo_generacion());
            m.setPeriodo_aplicacion(movimientos.getPeriodo_aplicacion());
            m.setCat_Tipo_Nomina(movimientos.getCat_Tipo_Nomina());
            m.setValor_1(aguinaldo_anterior_85);
            m.setFecha_creacion(fecha);
            tmp_MovimientosRepository.save(m);
            saveMovimientos339(movimientos);
        }
        if (Integer.toString(movimientos.getPeriodo_aplicacion()).equals(currentYear + "87")) {
            Tmp_Movimientos m = new Tmp_Movimientos();
            Tmp_Movimientos m1 = new Tmp_Movimientos();
            Double incidencia_varios_85 = obtenIncidenciaVarios(periodo_85, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_transportacion_85 = obtenIncidenciaTransportacion(periodo_85, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_confianza_85 = obtenIncidenciaConfianza(periodo_85, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_estructura_85 = obtenIncidenciaEstructura(periodo_85, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_jubilados_85 = obtenIncidenciaJubilados(periodo_85, 48, movimientos.getTrabajador().getId_trabajador());

            Double incidencia_varios_86 = obtenIncidenciaVarios(periodo_86, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_transportacion_86 = obtenIncidenciaTransportacion(periodo_86, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_confianza_86 = obtenIncidenciaConfianza(periodo_86, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_estructura_86 = obtenIncidenciaEstructura(periodo_86, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_jubilados_86 = obtenIncidenciaJubilados(periodo_86, 48, movimientos.getTrabajador().getId_trabajador());

            double incidencia_varios_85_val = (incidencia_varios_85 == null) ? 0.0 : incidencia_varios_85;
            double incidencia_transportacion_85_val = (incidencia_transportacion_85 == null) ? 0.0 : incidencia_transportacion_85;
            double incidencia_confianza_85_val = (incidencia_confianza_85 == null) ? 0.0 : incidencia_confianza_85;
            double incidencia_estructura_85_val = (incidencia_estructura_85 == null) ? 0.0 : incidencia_estructura_85;
            double incidencia_jubilados_85_val = (incidencia_jubilados_85 == null) ? 0.0 : incidencia_jubilados_85;
            double aguinaldo_anterior_85 = incidencia_varios_85_val + incidencia_transportacion_85_val + incidencia_confianza_85_val + incidencia_estructura_85_val + incidencia_jubilados_85_val;

            double incidencia_varios_86_val = (incidencia_varios_86 == null) ? 0.0 : incidencia_varios_86;
            double incidencia_transportacion_86_val = (incidencia_transportacion_86 == null) ? 0.0 : incidencia_transportacion_86;
            double incidencia_confianza_86_val = (incidencia_confianza_86 == null) ? 0.0 : incidencia_confianza_86;
            double incidencia_estructura_86_val = (incidencia_estructura_86 == null) ? 0.0 : incidencia_estructura_86;
            double incidencia_jubilados_86_val = (incidencia_jubilados_86 == null) ? 0.0 : incidencia_jubilados_86;
            double aguinaldo_anterior_86 = incidencia_varios_86_val + incidencia_transportacion_86_val + incidencia_confianza_86_val + incidencia_estructura_86_val + incidencia_jubilados_86_val;

            Cat_Incidencias catPercepcion = new Cat_Incidencias();
            catPercepcion.setId_incidencia(168);
            m.setTrabajador(movimientos.getTrabajador());
            m.setCat_Incidencias(catPercepcion);
            m.setAnio_aplicacion(movimientos.getAnio_aplicacion());
            m.setPeriodo_generacion(movimientos.getPeriodo_generacion());
            m.setPeriodo_aplicacion(movimientos.getPeriodo_generacion());
            m.setCat_Tipo_Nomina(movimientos.getCat_Tipo_Nomina());
            m.setValor_1(aguinaldo_anterior_85);
            m.setFecha_creacion(fecha);
            tmp_MovimientosRepository.save(m);

            Cat_Incidencias percepcion = new Cat_Incidencias();
            percepcion.setId_incidencia(170);
            m1.setTrabajador(movimientos.getTrabajador());
            m1.setCat_Incidencias(percepcion);
            m1.setAnio_aplicacion(movimientos.getAnio_aplicacion());
            m1.setPeriodo_generacion(movimientos.getPeriodo_generacion());
            m1.setPeriodo_aplicacion(movimientos.getPeriodo_generacion());
            m1.setCat_Tipo_Nomina(movimientos.getCat_Tipo_Nomina());
            m1.setValor_1(aguinaldo_anterior_86);
            m1.setFecha_creacion(fecha);
            tmp_MovimientosRepository.save(m1);
            saveMovimientos339(movimientos);
        }

    }

    // ******************************** GRABAR IMPORTE DE DEVOLUCION ISR (339 Cve Nomina - Id_incidencia 171) EN TMP MOVIMIENTOS********************
    public void saveMovimientos339(Tmp_Movimientos movimientos) {
        LocalDate currentDate = LocalDate.now();
        Tmp_Movimientos m = new Tmp_Movimientos();
        LocalDate fecha = LocalDate.now();
        int currentYear = currentDate.getYear();
        int periodo_85 = Integer.parseInt(currentYear + "85");
        int periodo_86 = Integer.parseInt(currentYear + "86");
        if (Integer.toString(movimientos.getPeriodo_aplicacion()).equals(currentYear + "87")) {

            Double incidencia_339_varios = obtenSumaISRVarios(periodo_85, periodo_86, 345, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_339_transportacion = obtenSumaISRTransportacion(periodo_85, periodo_86, 345, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_339_confianza = obtenSumaISRConfianza(periodo_85, periodo_86, 345, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_339_estructura = obtenSumaISREstructura(periodo_85, periodo_86, 345, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_339_jubilados = obtenSumaISRJubilados(periodo_85, periodo_86, 345, movimientos.getTrabajador().getId_trabajador());

            double incidencia_339_varios_val = (incidencia_339_varios == null) ? 0.0 : incidencia_339_varios;
            double incidencia_339_transportacion_val = (incidencia_339_transportacion == null) ? 0.0 : incidencia_339_transportacion;
            double incidencia_339_confianza_val = (incidencia_339_confianza == null) ? 0.0 : incidencia_339_confianza;
            double incidencia_339_estructura_val = (incidencia_339_estructura == null) ? 0.0 : incidencia_339_estructura;
            double incidencia_339_jubilados_val = (incidencia_339_jubilados == null) ? 0.0 : incidencia_339_jubilados;
            double isr = incidencia_339_varios_val + incidencia_339_transportacion_val + incidencia_339_confianza_val + incidencia_339_estructura_val + incidencia_339_jubilados_val;

            Cat_Incidencias catPercepcion = new Cat_Incidencias();
            catPercepcion.setId_incidencia(171);
            m.setTrabajador(movimientos.getTrabajador());
            m.setCat_Incidencias(catPercepcion);
            m.setAnio_aplicacion(movimientos.getAnio_aplicacion());
            m.setPeriodo_generacion(movimientos.getPeriodo_generacion());
            m.setPeriodo_aplicacion(movimientos.getPeriodo_generacion());
            m.setCat_Tipo_Nomina(movimientos.getCat_Tipo_Nomina());
            m.setValor_1(isr);
            m.setFecha_creacion(fecha);
            tmp_MovimientosRepository.save(m);
        }

    }

}
