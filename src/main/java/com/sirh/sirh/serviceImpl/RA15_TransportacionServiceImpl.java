/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.CapturaSemanalRA15DTO;
import com.sirh.sirh.DTO.Captura_Semanal_Resumen_IncidenciasRA15DTO;
import com.sirh.sirh.DTO.OperadorTitularDTO;
import com.sirh.sirh.DTO.OperadorTrenDTO;
import com.sirh.sirh.DTO.Trabajador_RA15DTO;
import com.sirh.sirh.entity.Captura_Semanal_Abonos_Descuentos_RA15;
import com.sirh.sirh.entity.Captura_Semanal_Excedente_Jornada_RA15;
import com.sirh.sirh.entity.Captura_Semanal_Inasistencias_RA15;
import com.sirh.sirh.entity.Captura_Semanal_Jornada_Normal_RA15;
import com.sirh.sirh.entity.Captura_Semanal_Paseos_Laborados_RA15;
import com.sirh.sirh.entity.Captura_Semanal_RA15;
import com.sirh.sirh.entity.Captura_Semanal_Resumen_IncidenciasRA15;
import com.sirh.sirh.entity.Captura_Semanal_Suplencias_RA15;
import com.sirh.sirh.entity.Captura_Semanal_Tiempo_Extra_RA15;
import com.sirh.sirh.entity.Cat_Dias;
import com.sirh.sirh.entity.Historico_Captura_Semanal_RA15;
import com.sirh.sirh.entity.Historico_Tmp_Libro_RA15;
import com.sirh.sirh.entity.Historico_Tmp_Libro_Tren_RA15;
import com.sirh.sirh.repository.Captura_Semanal_Abonos_Descuentos_RA15Repository;
import com.sirh.sirh.repository.Captura_Semanal_Excedente_Jornada_RA15Repository;
import com.sirh.sirh.repository.Captura_Semanal_Inasistencias_RA15Repository;
import com.sirh.sirh.repository.Captura_Semanal_Jornada_Normal_RA15Repository;
import com.sirh.sirh.repository.Captura_Semanal_Paseos_Laborados_RA15Repository;
import com.sirh.sirh.repository.Captura_Semanal_RA15Repository;
import com.sirh.sirh.repository.Captura_Semanal_Resumen_IncidenciasRA15Repository;
import com.sirh.sirh.repository.Captura_Semanal_Suplencias_RA15Repository;
import com.sirh.sirh.repository.Captura_Semanal_Tiempo_Extra_RA15Repository;
import com.sirh.sirh.repository.Cat_DiasRepository;
import com.sirh.sirh.repository.Historico_Captura_Semanal_RA15Repository;
import com.sirh.sirh.repository.Historico_Tmp_Libro_RA15Repository;
import com.sirh.sirh.repository.Historico_Tmp_Libro_Tren_RA15Repository;
import com.sirh.sirh.service.RA15_TransportacionService;
import java.io.IOException;
import com.sirh.sirh.DTO.RelevosDTO;
import com.sirh.sirh.entity.Libro_Dias;
import com.sirh.sirh.repository.Libro_DiasRepository;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author rroscero23
 */
@Service
@Transactional
public class RA15_TransportacionServiceImpl implements RA15_TransportacionService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Captura_Semanal_RA15Repository captura_Semanal_RA15Repository;

    @Autowired
    private Captura_Semanal_Abonos_Descuentos_RA15Repository captura_Semanal_Abonos_Descuentos_RA15Repository;

    @Autowired
    private Captura_Semanal_Excedente_Jornada_RA15Repository captura_Semanal_Excedente_Jornada_RA15Repository;

    @Autowired
    private Captura_Semanal_Inasistencias_RA15Repository Captura_Semanal_Inasistencias_RA15Repository;

    @Autowired
    private Captura_Semanal_Jornada_Normal_RA15Repository captura_Semanal_Jornada_Normal_RA15Repository;

    @Autowired
    private Captura_Semanal_Suplencias_RA15Repository captura_Semanal_Suplencias_RA15Repository;

    @Autowired
    private Captura_Semanal_Tiempo_Extra_RA15Repository captura_Semanal_Tiempo_Extra_RA15Repository;

    @Autowired
    private Captura_Semanal_Paseos_Laborados_RA15Repository captura_Semanal_Paseos_Laborados_RA15Repository;

    @Autowired
    private Captura_Semanal_Resumen_IncidenciasRA15Repository captura_Semanal_Resumen_IncidenciasRA15Repository;

    @Autowired
    private Historico_Captura_Semanal_RA15Repository historico_Captura_Semanal_RA15Repository;

    @Autowired
    private Historico_Tmp_Libro_RA15Repository historico_Tmp_Libro_RA15Repository;

    @Autowired
    private Historico_Tmp_Libro_Tren_RA15Repository historico_Tmp_Libro_Tren_RA15Repository;

    @Autowired
    private Cat_DiasRepository cat_DiasRepository;

    @Autowired
    private Libro_DiasRepository libro_diasRepository;

    @Override
    public List<Captura_Semanal_RA15> findByIdTrabajadorAndIdPeriodo(Integer trabajadorId, Integer periodoId) {
        return captura_Semanal_RA15Repository.findByIdTrabajadorAndIdPeriodo(trabajadorId, periodoId);
    }

    @Override
    public List<Captura_Semanal_RA15> findByIdTrabajadorExpedienteAndIdPeriodo(String numero_expediente, Integer periodoId) {
        return captura_Semanal_RA15Repository.findByIdTrabajadorExpedienteAndIdPeriodo(numero_expediente, periodoId);
    }

    //Este servicio guarda al mismo tiempo en las tablas relacionadas y guarda el id que las relaciona en la tabla principal (Captura_Semanal_RA15)
    @Override
    public Captura_Semanal_RA15 saveCapturaSemanal(Captura_Semanal_RA15 captura_Semanal_RA15) {
        //Campos de la captura semanal en general
        Captura_Semanal_RA15 nuevaCapturaSemanal = new Captura_Semanal_RA15();
        nuevaCapturaSemanal.setTrabajador_id(captura_Semanal_RA15.getTrabajador_id());
        nuevaCapturaSemanal.setTipo_id(captura_Semanal_RA15.getTipo_id());
        nuevaCapturaSemanal.setHoras_planta(captura_Semanal_RA15.getHoras_planta());
        nuevaCapturaSemanal.setRuta(captura_Semanal_RA15.getRuta());
        nuevaCapturaSemanal.setEstatus(1);
        nuevaCapturaSemanal.setEstado(captura_Semanal_RA15.getEstado());
        nuevaCapturaSemanal.setA1(captura_Semanal_RA15.getA1());
        nuevaCapturaSemanal.setA2(captura_Semanal_RA15.getA2());
        nuevaCapturaSemanal.setA3(captura_Semanal_RA15.getA3());
        nuevaCapturaSemanal.setA4(captura_Semanal_RA15.getA4());
        nuevaCapturaSemanal.setA5(captura_Semanal_RA15.getA5());
        nuevaCapturaSemanal.setA6(captura_Semanal_RA15.getA6());
        nuevaCapturaSemanal.setA7(captura_Semanal_RA15.getA7());
        nuevaCapturaSemanal.setA8(captura_Semanal_RA15.getA8());
        nuevaCapturaSemanal.setA9(captura_Semanal_RA15.getA9());
        nuevaCapturaSemanal.setA10(captura_Semanal_RA15.getA10());
        nuevaCapturaSemanal.setA11(captura_Semanal_RA15.getA11());
        nuevaCapturaSemanal.setA12(captura_Semanal_RA15.getA12());
        nuevaCapturaSemanal.setA13(captura_Semanal_RA15.getA13());
        nuevaCapturaSemanal.setA14(captura_Semanal_RA15.getA14());
        nuevaCapturaSemanal.setA15(captura_Semanal_RA15.getA15());
        nuevaCapturaSemanal.setA16(captura_Semanal_RA15.getA16());
        nuevaCapturaSemanal.setA17(captura_Semanal_RA15.getA17());
        nuevaCapturaSemanal.setA18(captura_Semanal_RA15.getA18());
        nuevaCapturaSemanal.setA19(captura_Semanal_RA15.getA19());
        nuevaCapturaSemanal.setA20(captura_Semanal_RA15.getA20());
        nuevaCapturaSemanal.setA21(captura_Semanal_RA15.getA21());
        nuevaCapturaSemanal.setA22(captura_Semanal_RA15.getA22());
        nuevaCapturaSemanal.setA23(captura_Semanal_RA15.getA23());
        nuevaCapturaSemanal.setA24(captura_Semanal_RA15.getA24());
        nuevaCapturaSemanal.setA25(captura_Semanal_RA15.getA25());
        nuevaCapturaSemanal.setA26(captura_Semanal_RA15.getA26());
        nuevaCapturaSemanal.setA27(captura_Semanal_RA15.getA27());
        nuevaCapturaSemanal.setA28(captura_Semanal_RA15.getA28());
        nuevaCapturaSemanal.setCaptura(captura_Semanal_RA15.getCaptura());
        nuevaCapturaSemanal.setFecha_inicial_captura(captura_Semanal_RA15.getFecha_inicial_captura());
        nuevaCapturaSemanal.setFecha_final_captura(captura_Semanal_RA15.getFecha_final_captura());
        nuevaCapturaSemanal.setArea(captura_Semanal_RA15.getArea());
        nuevaCapturaSemanal.setActivo_ra15(captura_Semanal_RA15.getActivo_ra15());
        nuevaCapturaSemanal.setProp_desc_1(captura_Semanal_RA15.getProp_desc_1());
        nuevaCapturaSemanal.setProp_desc_2(captura_Semanal_RA15.getProp_desc_2());
        nuevaCapturaSemanal.setTotal_hrs_descanso(captura_Semanal_RA15.getTotal_hrs_descanso());
        nuevaCapturaSemanal.setCod_puesto_suplencia(captura_Semanal_RA15.getCod_puesto_suplencia());
        nuevaCapturaSemanal.setAnio_planta(captura_Semanal_RA15.getAnio_planta());
        nuevaCapturaSemanal.setFecha_vigencia_planta(captura_Semanal_RA15.getFecha_vigencia_planta());
        nuevaCapturaSemanal.setRuta_id(captura_Semanal_RA15.getRuta_id());
        nuevaCapturaSemanal.setTurno_id(captura_Semanal_RA15.getTurno_id());
        nuevaCapturaSemanal.setPeriodo_aplicacion_id(captura_Semanal_RA15.getPeriodo_aplicacion_id());

        //***************************ABONOS Y DESCUENTOS**************************
        Captura_Semanal_Abonos_Descuentos_RA15 abonosDescuentos = new Captura_Semanal_Abonos_Descuentos_RA15();
        abonosDescuentos.setAbono_jornada(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_jornada());
        abonosDescuentos.setDescuento_jornada(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_jornada());
        abonosDescuentos.setAbono_doble(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_doble());
        abonosDescuentos.setDescuento_doble(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_doble());
        abonosDescuentos.setAbono_triple(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_triple());
        abonosDescuentos.setDescuento_triple(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_triple());
        abonosDescuentos.setAbono_descanso(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_descanso());
        abonosDescuentos.setDescuento_descanso(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_descanso());
        abonosDescuentos.setAbono_prima(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_prima());
        abonosDescuentos.setDescuento_prima(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_prima());
        abonosDescuentos.setAbono_festivo(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_festivo());
        abonosDescuentos.setDescuento_festivo(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_festivo());
        abonosDescuentos.setAbono_dias_lab(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_dias_lab());
        abonosDescuentos.setDescuento_dias_lab(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_dias_lab());
        abonosDescuentos.setAbono_dif_suplencia(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_dif_suplencia());
        abonosDescuentos.setDescuento_dif_suplencia(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_dif_suplencia());
        abonosDescuentos.setObservaciones(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getObservaciones());
        abonosDescuentos.setDescuento_omisiones(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_omisiones());
        abonosDescuentos.setObservaciones_omisiones(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getObservaciones_omisiones());

        // Guarda la instancia de captura_Semanal_Abonos_Descuentos_RA15 en la base de datos
        abonosDescuentos = captura_Semanal_Abonos_Descuentos_RA15Repository.save(abonosDescuentos);

        // Asigna el ID recién generado a la relación en la tabla principal
        nuevaCapturaSemanal.setCaptura_Semanal_Abonos_Descuentos_RA15(abonosDescuentos);

        //**************************JORNADA NORMAL***********************************
        Captura_Semanal_Jornada_Normal_RA15 jornadaNormal = new Captura_Semanal_Jornada_Normal_RA15();
        jornadaNormal.setDomingo(captura_Semanal_RA15.getCaptura_Semanal_Jornada_Normal_RA15().getDomingo());
        jornadaNormal.setLunes(captura_Semanal_RA15.getCaptura_Semanal_Jornada_Normal_RA15().getLunes());
        jornadaNormal.setMartes(captura_Semanal_RA15.getCaptura_Semanal_Jornada_Normal_RA15().getMartes());
        jornadaNormal.setMiercoles(captura_Semanal_RA15.getCaptura_Semanal_Jornada_Normal_RA15().getMiercoles());
        jornadaNormal.setJueves(captura_Semanal_RA15.getCaptura_Semanal_Jornada_Normal_RA15().getJueves());
        jornadaNormal.setViernes(captura_Semanal_RA15.getCaptura_Semanal_Jornada_Normal_RA15().getViernes());
        jornadaNormal.setSabado(captura_Semanal_RA15.getCaptura_Semanal_Jornada_Normal_RA15().getSabado());
        //Guarda la instancia de la jornada normal en la base de datos
        jornadaNormal = captura_Semanal_Jornada_Normal_RA15Repository.save(jornadaNormal);
        // Asigna el ID recién generado a la relación en la tabla principal
        nuevaCapturaSemanal.setCaptura_Semanal_Jornada_Normal_RA15(jornadaNormal);
        //**********************************TIEMPO EXTRA*****************************
        Captura_Semanal_Tiempo_Extra_RA15 tiempoExtra = new Captura_Semanal_Tiempo_Extra_RA15();
        tiempoExtra.setDomingo(captura_Semanal_RA15.getCaptura_Semanal_Tiempo_Extra_RA15().getDomingo());
        tiempoExtra.setLunes(captura_Semanal_RA15.getCaptura_Semanal_Tiempo_Extra_RA15().getLunes());
        tiempoExtra.setMartes(captura_Semanal_RA15.getCaptura_Semanal_Tiempo_Extra_RA15().getMartes());
        tiempoExtra.setMiercoles(captura_Semanal_RA15.getCaptura_Semanal_Tiempo_Extra_RA15().getMiercoles());
        tiempoExtra.setJueves(captura_Semanal_RA15.getCaptura_Semanal_Tiempo_Extra_RA15().getJueves());
        tiempoExtra.setViernes(captura_Semanal_RA15.getCaptura_Semanal_Tiempo_Extra_RA15().getViernes());
        tiempoExtra.setSabado(captura_Semanal_RA15.getCaptura_Semanal_Tiempo_Extra_RA15().getSabado());
        tiempoExtra = captura_Semanal_Tiempo_Extra_RA15Repository.save(tiempoExtra);
        // Asigna el ID recién generado a la relación en la tabla principal
        nuevaCapturaSemanal.setCaptura_Semanal_Tiempo_Extra_RA15(tiempoExtra);

        //*****************************PASEOS LABORADOS****************************** 
        Captura_Semanal_Paseos_Laborados_RA15 paseosLaborados = new Captura_Semanal_Paseos_Laborados_RA15();
        paseosLaborados.setDomingo(captura_Semanal_RA15.getCaptura_Semanal_Paseos_Laborados_RA15().getDomingo());
        paseosLaborados.setLunes(captura_Semanal_RA15.getCaptura_Semanal_Paseos_Laborados_RA15().getLunes());
        paseosLaborados.setMartes(captura_Semanal_RA15.getCaptura_Semanal_Paseos_Laborados_RA15().getMartes());
        paseosLaborados.setMiercoles(captura_Semanal_RA15.getCaptura_Semanal_Paseos_Laborados_RA15().getMiercoles());
        paseosLaborados.setJueves(captura_Semanal_RA15.getCaptura_Semanal_Paseos_Laborados_RA15().getJueves());
        paseosLaborados.setViernes(captura_Semanal_RA15.getCaptura_Semanal_Paseos_Laborados_RA15().getViernes());
        paseosLaborados.setSabado(captura_Semanal_RA15.getCaptura_Semanal_Paseos_Laborados_RA15().getSabado());
        paseosLaborados = captura_Semanal_Paseos_Laborados_RA15Repository.save(paseosLaborados);
        // Asigna el ID recién generado a la relación en la tabla principal
        nuevaCapturaSemanal.setCaptura_Semanal_Paseos_Laborados_RA15(paseosLaborados);

        //********************************EXCEDENTES JORNADA***************************
        Captura_Semanal_Excedente_Jornada_RA15 excedenteJornada = new Captura_Semanal_Excedente_Jornada_RA15();
        excedenteJornada.setDomingo(captura_Semanal_RA15.getCaptura_Semanal_Excedente_Jornada_RA15().getDomingo());
        excedenteJornada.setLunes(captura_Semanal_RA15.getCaptura_Semanal_Excedente_Jornada_RA15().getLunes());
        excedenteJornada.setMartes(captura_Semanal_RA15.getCaptura_Semanal_Excedente_Jornada_RA15().getMartes());
        excedenteJornada.setMiercoles(captura_Semanal_RA15.getCaptura_Semanal_Excedente_Jornada_RA15().getMiercoles());
        excedenteJornada.setJueves(captura_Semanal_RA15.getCaptura_Semanal_Excedente_Jornada_RA15().getJueves());
        excedenteJornada.setViernes(captura_Semanal_RA15.getCaptura_Semanal_Excedente_Jornada_RA15().getViernes());
        excedenteJornada.setSabado(captura_Semanal_RA15.getCaptura_Semanal_Excedente_Jornada_RA15().getSabado());
        excedenteJornada = captura_Semanal_Excedente_Jornada_RA15Repository.save(excedenteJornada);
        // Asigna el ID recién generado a la relación en la tabla principal
        nuevaCapturaSemanal.setCaptura_Semanal_Excedente_Jornada_RA15(excedenteJornada);
        //*************************************SUPLENCIAS******************************
        Captura_Semanal_Suplencias_RA15 suplencias = new Captura_Semanal_Suplencias_RA15();
        suplencias.setDomingo(captura_Semanal_RA15.getCaptura_Semanal_Suplencias_RA15().getDomingo());
        suplencias.setLunes(captura_Semanal_RA15.getCaptura_Semanal_Suplencias_RA15().getLunes());
        suplencias.setMartes(captura_Semanal_RA15.getCaptura_Semanal_Suplencias_RA15().getMartes());
        suplencias.setMiercoles(captura_Semanal_RA15.getCaptura_Semanal_Suplencias_RA15().getMiercoles());
        suplencias.setJueves(captura_Semanal_RA15.getCaptura_Semanal_Suplencias_RA15().getJueves());
        suplencias.setViernes(captura_Semanal_RA15.getCaptura_Semanal_Suplencias_RA15().getViernes());
        suplencias.setSabado(captura_Semanal_RA15.getCaptura_Semanal_Suplencias_RA15().getSabado());
        suplencias = captura_Semanal_Suplencias_RA15Repository.save(suplencias);
        // Asigna el ID recién generado a la relación en la tabla principal
        nuevaCapturaSemanal.setCaptura_Semanal_Suplencias_RA15(suplencias);

        //*********************************INASISTENCIAS********************************************
        Captura_Semanal_Inasistencias_RA15 inasistencias = new Captura_Semanal_Inasistencias_RA15();
        inasistencias.setDomingo(captura_Semanal_RA15.getCaptura_Semanal_Inasistencias_RA15().getDomingo());
        inasistencias.setLunes(captura_Semanal_RA15.getCaptura_Semanal_Inasistencias_RA15().getLunes());
        inasistencias.setMartes(captura_Semanal_RA15.getCaptura_Semanal_Inasistencias_RA15().getMartes());
        inasistencias.setMiercoles(captura_Semanal_RA15.getCaptura_Semanal_Inasistencias_RA15().getMiercoles());
        inasistencias.setJueves(captura_Semanal_RA15.getCaptura_Semanal_Inasistencias_RA15().getJueves());
        inasistencias.setViernes(captura_Semanal_RA15.getCaptura_Semanal_Inasistencias_RA15().getViernes());
        inasistencias.setSabado(captura_Semanal_RA15.getCaptura_Semanal_Inasistencias_RA15().getSabado());
        inasistencias = Captura_Semanal_Inasistencias_RA15Repository.save(inasistencias);
        // Asigna el ID recién generado a la relación en la tabla principal
        nuevaCapturaSemanal.setCaptura_Semanal_Inasistencias_RA15(inasistencias);

        //*****************************RESUMEN DE INCIDENCIAS / TOTALES ****************************** 
        Captura_Semanal_Resumen_IncidenciasRA15 resumenIncidencias = new Captura_Semanal_Resumen_IncidenciasRA15();
        resumenIncidencias.setHoras_turno(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getHoras_turno());
        resumenIncidencias.setHoras_doble(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getHoras_doble());
        resumenIncidencias.setHoras_triple(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getHoras_triple());
        resumenIncidencias.setDias_laborados(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getDias_laborados());
        resumenIncidencias.setTotal_faltas(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getTotal_faltas());
        resumenIncidencias.setTotal_paseos(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getTotal_paseos());
        resumenIncidencias.setPrima_dominical(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getPrima_dominical());
        resumenIncidencias.setFestivo(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getFestivo());
        resumenIncidencias.setOmisiones(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getOmisiones());
        resumenIncidencias.setDif_paseos(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getDif_paseos());
        resumenIncidencias.setDif_prima(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getDif_prima());
        resumenIncidencias.setDif_tiempo_extra(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getDif_tiempo_extra());
        resumenIncidencias.setDif_sueldo(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getDif_sueldo());
        resumenIncidencias.setDias_pago(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getDias_pago());
        resumenIncidencias.setDias_cve_27(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getDias_cve_27());
        resumenIncidencias = captura_Semanal_Resumen_IncidenciasRA15Repository.save(resumenIncidencias);

        nuevaCapturaSemanal.setCaptura_Semanal_Resumen_IncidenciasRA15(resumenIncidencias);

        //Se guarda la instancia de captura semanal en la base de datos con las claves foráneas generadas
        return captura_Semanal_RA15Repository.save(nuevaCapturaSemanal);

    }

    //Actualiza todos los campos de la tabla principal y tabla relacionada
    @Override
    public Captura_Semanal_RA15 updateCapturaSemanal(Captura_Semanal_RA15 captura_Semanal_RA15, Integer id_ra) {
        Captura_Semanal_RA15 cs15 = captura_Semanal_RA15Repository.findById(id_ra).get();
        Captura_Semanal_RA15 cs = cs15;
        cs.setTrabajador_id(captura_Semanal_RA15.getTrabajador_id());
        cs.setTipo_id(captura_Semanal_RA15.getTipo_id());
        cs.setHoras_planta(captura_Semanal_RA15.getHoras_planta());
        cs.setRuta(captura_Semanal_RA15.getRuta());
        cs.setEstatus(1);
        cs.setEstado(captura_Semanal_RA15.getEstado());
        cs.setA1(captura_Semanal_RA15.getA1());
        cs.setA2(captura_Semanal_RA15.getA2());
        cs.setA3(captura_Semanal_RA15.getA3());
        cs.setA4(captura_Semanal_RA15.getA4());
        cs.setA5(captura_Semanal_RA15.getA5());
        cs.setA6(captura_Semanal_RA15.getA6());
        cs.setA7(captura_Semanal_RA15.getA7());
        cs.setA8(captura_Semanal_RA15.getA8());
        cs.setA9(captura_Semanal_RA15.getA9());
        cs.setA10(captura_Semanal_RA15.getA10());
        cs.setA11(captura_Semanal_RA15.getA11());
        cs.setA12(captura_Semanal_RA15.getA12());
        cs.setA13(captura_Semanal_RA15.getA13());
        cs.setA14(captura_Semanal_RA15.getA14());
        cs.setA15(captura_Semanal_RA15.getA15());
        cs.setA16(captura_Semanal_RA15.getA16());
        cs.setA17(captura_Semanal_RA15.getA17());
        cs.setA18(captura_Semanal_RA15.getA18());
        cs.setA19(captura_Semanal_RA15.getA19());
        cs.setA20(captura_Semanal_RA15.getA20());
        cs.setA21(captura_Semanal_RA15.getA21());
        cs.setA22(captura_Semanal_RA15.getA22());
        cs.setA23(captura_Semanal_RA15.getA23());
        cs.setA24(captura_Semanal_RA15.getA24());
        cs.setA25(captura_Semanal_RA15.getA25());
        cs.setA26(captura_Semanal_RA15.getA26());
        cs.setA27(captura_Semanal_RA15.getA27());
        cs.setA28(captura_Semanal_RA15.getA28());
        cs.setCaptura(captura_Semanal_RA15.getCaptura());
        cs.setFecha_inicial_captura(captura_Semanal_RA15.getFecha_inicial_captura());
        cs.setFecha_final_captura(captura_Semanal_RA15.getFecha_final_captura());
        cs.setArea(captura_Semanal_RA15.getArea());
        cs.setActivo_ra15(captura_Semanal_RA15.getActivo_ra15());
        cs.setProp_desc_1(captura_Semanal_RA15.getProp_desc_1());
        cs.setProp_desc_2(captura_Semanal_RA15.getProp_desc_2());
        cs.setTotal_hrs_descanso(captura_Semanal_RA15.getTotal_hrs_descanso());
        cs.setCod_puesto_suplencia(captura_Semanal_RA15.getCod_puesto_suplencia());
        cs.setAnio_planta(captura_Semanal_RA15.getAnio_planta());
        cs.setFecha_vigencia_planta(captura_Semanal_RA15.getFecha_vigencia_planta());
        cs.setRuta_id(captura_Semanal_RA15.getRuta_id());
        cs.setTurno_id(captura_Semanal_RA15.getTurno_id());
        cs.setPeriodo_aplicacion_id(captura_Semanal_RA15.getPeriodo_aplicacion_id());
        //***************************ABONOS Y DESCUENTOS**************************
        Captura_Semanal_Abonos_Descuentos_RA15 abonosDescuentos = cs.getCaptura_Semanal_Abonos_Descuentos_RA15();
        abonosDescuentos.setAbono_jornada(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_jornada());
        abonosDescuentos.setDescuento_jornada(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_jornada());
        abonosDescuentos.setAbono_doble(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_doble());
        abonosDescuentos.setDescuento_doble(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_doble());
        abonosDescuentos.setAbono_triple(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_triple());
        abonosDescuentos.setDescuento_triple(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_triple());
        abonosDescuentos.setAbono_descanso(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_descanso());
        abonosDescuentos.setDescuento_descanso(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_descanso());
        abonosDescuentos.setAbono_prima(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_prima());
        abonosDescuentos.setDescuento_prima(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_prima());
        abonosDescuentos.setAbono_festivo(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_festivo());
        abonosDescuentos.setDescuento_festivo(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_festivo());
        abonosDescuentos.setAbono_dias_lab(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_dias_lab());
        abonosDescuentos.setDescuento_dias_lab(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_dias_lab());
        abonosDescuentos.setAbono_dif_suplencia(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getAbono_dif_suplencia());
        abonosDescuentos.setDescuento_dif_suplencia(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_dif_suplencia());
        abonosDescuentos.setObservaciones(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getObservaciones());
        abonosDescuentos.setDescuento_omisiones(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getDescuento_omisiones());
        abonosDescuentos.setObservaciones_omisiones(captura_Semanal_RA15.getCaptura_Semanal_Abonos_Descuentos_RA15().getObservaciones_omisiones());
        //**************************JORNADA NORMAL***********************************
        Captura_Semanal_Jornada_Normal_RA15 jornadaNormal = cs.getCaptura_Semanal_Jornada_Normal_RA15();
        jornadaNormal.setDomingo(captura_Semanal_RA15.getCaptura_Semanal_Jornada_Normal_RA15().getDomingo());
        jornadaNormal.setLunes(captura_Semanal_RA15.getCaptura_Semanal_Jornada_Normal_RA15().getLunes());
        jornadaNormal.setMartes(captura_Semanal_RA15.getCaptura_Semanal_Jornada_Normal_RA15().getMartes());
        jornadaNormal.setMiercoles(captura_Semanal_RA15.getCaptura_Semanal_Jornada_Normal_RA15().getMiercoles());
        jornadaNormal.setJueves(captura_Semanal_RA15.getCaptura_Semanal_Jornada_Normal_RA15().getJueves());
        jornadaNormal.setViernes(captura_Semanal_RA15.getCaptura_Semanal_Jornada_Normal_RA15().getViernes());
        jornadaNormal.setSabado(captura_Semanal_RA15.getCaptura_Semanal_Jornada_Normal_RA15().getSabado());
        //**********************************TIEMPO EXTRA*****************************
        Captura_Semanal_Tiempo_Extra_RA15 tiempoExtra = cs.getCaptura_Semanal_Tiempo_Extra_RA15();
        tiempoExtra.setDomingo(captura_Semanal_RA15.getCaptura_Semanal_Tiempo_Extra_RA15().getDomingo());
        tiempoExtra.setLunes(captura_Semanal_RA15.getCaptura_Semanal_Tiempo_Extra_RA15().getLunes());
        tiempoExtra.setMartes(captura_Semanal_RA15.getCaptura_Semanal_Tiempo_Extra_RA15().getMartes());
        tiempoExtra.setMiercoles(captura_Semanal_RA15.getCaptura_Semanal_Tiempo_Extra_RA15().getMiercoles());
        tiempoExtra.setJueves(captura_Semanal_RA15.getCaptura_Semanal_Tiempo_Extra_RA15().getJueves());
        tiempoExtra.setViernes(captura_Semanal_RA15.getCaptura_Semanal_Tiempo_Extra_RA15().getViernes());
        tiempoExtra.setSabado(captura_Semanal_RA15.getCaptura_Semanal_Tiempo_Extra_RA15().getSabado());
        //*****************************PASEOS LABORADOS****************************** 
        Captura_Semanal_Paseos_Laborados_RA15 paseosLaborados = cs.getCaptura_Semanal_Paseos_Laborados_RA15();
        paseosLaborados.setDomingo(captura_Semanal_RA15.getCaptura_Semanal_Paseos_Laborados_RA15().getDomingo());
        paseosLaborados.setLunes(captura_Semanal_RA15.getCaptura_Semanal_Paseos_Laborados_RA15().getLunes());
        paseosLaborados.setMartes(captura_Semanal_RA15.getCaptura_Semanal_Paseos_Laborados_RA15().getMartes());
        paseosLaborados.setMiercoles(captura_Semanal_RA15.getCaptura_Semanal_Paseos_Laborados_RA15().getMiercoles());
        paseosLaborados.setJueves(captura_Semanal_RA15.getCaptura_Semanal_Paseos_Laborados_RA15().getJueves());
        paseosLaborados.setViernes(captura_Semanal_RA15.getCaptura_Semanal_Paseos_Laborados_RA15().getViernes());
        paseosLaborados.setSabado(captura_Semanal_RA15.getCaptura_Semanal_Paseos_Laborados_RA15().getSabado());
        //********************************EXCEDENTES JORNADA***************************
        Captura_Semanal_Excedente_Jornada_RA15 excedenteJornada = cs.getCaptura_Semanal_Excedente_Jornada_RA15();
        excedenteJornada.setDomingo(captura_Semanal_RA15.getCaptura_Semanal_Excedente_Jornada_RA15().getDomingo());
        excedenteJornada.setLunes(captura_Semanal_RA15.getCaptura_Semanal_Excedente_Jornada_RA15().getLunes());
        excedenteJornada.setMartes(captura_Semanal_RA15.getCaptura_Semanal_Excedente_Jornada_RA15().getMartes());
        excedenteJornada.setMiercoles(captura_Semanal_RA15.getCaptura_Semanal_Excedente_Jornada_RA15().getMiercoles());
        excedenteJornada.setJueves(captura_Semanal_RA15.getCaptura_Semanal_Excedente_Jornada_RA15().getJueves());
        excedenteJornada.setViernes(captura_Semanal_RA15.getCaptura_Semanal_Excedente_Jornada_RA15().getViernes());
        excedenteJornada.setSabado(captura_Semanal_RA15.getCaptura_Semanal_Excedente_Jornada_RA15().getSabado());
        //*************************************SUPLENCIAS******************************
        Captura_Semanal_Suplencias_RA15 suplencias = cs.getCaptura_Semanal_Suplencias_RA15();
        suplencias.setDomingo(captura_Semanal_RA15.getCaptura_Semanal_Suplencias_RA15().getDomingo());
        suplencias.setLunes(captura_Semanal_RA15.getCaptura_Semanal_Suplencias_RA15().getLunes());
        suplencias.setMartes(captura_Semanal_RA15.getCaptura_Semanal_Suplencias_RA15().getMartes());
        suplencias.setMiercoles(captura_Semanal_RA15.getCaptura_Semanal_Suplencias_RA15().getMiercoles());
        suplencias.setJueves(captura_Semanal_RA15.getCaptura_Semanal_Suplencias_RA15().getJueves());
        suplencias.setViernes(captura_Semanal_RA15.getCaptura_Semanal_Suplencias_RA15().getViernes());
        suplencias.setSabado(captura_Semanal_RA15.getCaptura_Semanal_Suplencias_RA15().getSabado());
        //*********************************INASISTENCIAS********************************************
        Captura_Semanal_Inasistencias_RA15 inasistencias = cs.getCaptura_Semanal_Inasistencias_RA15();
        inasistencias.setDomingo(captura_Semanal_RA15.getCaptura_Semanal_Inasistencias_RA15().getDomingo());
        inasistencias.setLunes(captura_Semanal_RA15.getCaptura_Semanal_Inasistencias_RA15().getLunes());
        inasistencias.setMartes(captura_Semanal_RA15.getCaptura_Semanal_Inasistencias_RA15().getMartes());
        inasistencias.setMiercoles(captura_Semanal_RA15.getCaptura_Semanal_Inasistencias_RA15().getMiercoles());
        inasistencias.setJueves(captura_Semanal_RA15.getCaptura_Semanal_Inasistencias_RA15().getJueves());
        inasistencias.setViernes(captura_Semanal_RA15.getCaptura_Semanal_Inasistencias_RA15().getViernes());
        inasistencias.setSabado(captura_Semanal_RA15.getCaptura_Semanal_Inasistencias_RA15().getSabado());

        //*****************************RESUMEN DE INCIDENCIAS / TOTALES ****************************** 
        Captura_Semanal_Resumen_IncidenciasRA15 resumenIncidencias = cs.getCaptura_Semanal_Resumen_IncidenciasRA15();
        resumenIncidencias.setHoras_turno(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getHoras_turno());
        resumenIncidencias.setHoras_doble(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getHoras_doble());
        resumenIncidencias.setHoras_triple(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getHoras_triple());
        resumenIncidencias.setDias_laborados(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getDias_laborados());
        resumenIncidencias.setTotal_faltas(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getTotal_faltas());
        resumenIncidencias.setTotal_paseos(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getTotal_paseos());
        resumenIncidencias.setPrima_dominical(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getPrima_dominical());
        resumenIncidencias.setFestivo(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getFestivo());
        resumenIncidencias.setOmisiones(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getOmisiones());
        resumenIncidencias.setDif_paseos(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getDif_paseos());
        resumenIncidencias.setDif_prima(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getDif_prima());
        resumenIncidencias.setDif_tiempo_extra(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getDif_tiempo_extra());
        resumenIncidencias.setDif_sueldo(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getDif_sueldo());
        resumenIncidencias.setDias_pago(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getDias_pago());
        resumenIncidencias.setDias_cve_27(captura_Semanal_RA15.getCaptura_Semanal_Resumen_IncidenciasRA15().getDias_cve_27());
        return captura_Semanal_RA15Repository.save(cs);
    }

    @Override
    public List<CapturaSemanalRA15DTO> reporteCSVCapturaSemanal(Integer periodo_id) {
        return captura_Semanal_RA15Repository.reporteCSVCapturaSemanal(periodo_id);
    }

    @Override
    public Historico_Captura_Semanal_RA15 findHistoricoRA15(Integer trabajador_id, Integer periodo_id) {
        return historico_Captura_Semanal_RA15Repository.findHistoricoRA15(trabajador_id, periodo_id);
    }

    @Override
    public List<Trabajador_RA15DTO> trabajadoresRA15Periodo(Integer periodo_id) {
        return captura_Semanal_RA15Repository.trabajadoresRA15Periodo(periodo_id);
    }

    @Override
    public List<Captura_Semanal_Resumen_IncidenciasRA15DTO> reporteCSVCapturaSemanalResumen(Integer periodo_id) {
        return captura_Semanal_Resumen_IncidenciasRA15Repository.reporteCSVCapturaSemanalResumen(periodo_id);
    }

    @Override
    public List<Historico_Tmp_Libro_RA15> listarAllDatosPlantas() {
        return historico_Tmp_Libro_RA15Repository.findAll().stream().map(historico_Tmp_Libro_RA15 -> modelMapper.map(historico_Tmp_Libro_RA15, Historico_Tmp_Libro_RA15.class)).collect(Collectors.toList());
    }

    @Override
    public Historico_Tmp_Libro_RA15 saveNewPlantas(Historico_Tmp_Libro_RA15 historico_Tmp_Libro_RA15) {
        return historico_Tmp_Libro_RA15Repository.save(historico_Tmp_Libro_RA15);
    }

    @Override
    public void importarPlantasExcel(MultipartFile archivoExcel, String nombreHoja, Integer anio) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(archivoExcel.getInputStream());
        XSSFSheet worksheet = workbook.getSheet(nombreHoja);
        int lastRow = worksheet.getPhysicalNumberOfRows();
        // Obtener el nombre de la hoja seleccionada sin espacios y en minúsculas para el switch
        String hojaSeleccionada = nombreHoja.trim().toLowerCase();
        // Utilizar un switch para manejar diferentes casos según el nombre de la hoja seleccionada
        switch (hojaSeleccionada) {
            case "operadores":
                //Busca el campo "NOMBRE" de la fila de encabezados para determinar donde comenzar a contar las filas con datos
                Integer nombreIndex = buscarIndiceNombre(worksheet, false);
                procesarOperadoresRA15(nombreIndex, lastRow, worksheet, anio);
                crearOActualizarTitular();
                findRelevos();
                break;
            case "tren ligero":
                //Busca el campo "NOMBRE" de la fila de encabezados para determinar donde comenzar a contar las filas con datos
                Integer nombreIndexTren = buscarIndiceNombre(worksheet, true);
                procesarTrenLigeroRA15(nombreIndexTren, lastRow, worksheet, anio);
                crearOActualizarTitularTrenLigero();
                break;
            case "general patio":
                //Busca el campo "NOMBRE" de la fila de encabezados para determinar donde comenzar a contar las filas con datos
                Integer nombreIndexPatio = buscarIndiceNombre(worksheet, false);
                procesarGeneralPatioRA15(nombreIndexPatio, lastRow, worksheet, anio);
                crearOActualizarTitular();
                findRelevos();
                break;
            default:
                //Si la hoja no coincide con ninguna del switch case
                throw new RuntimeException("El nombre de la hoja no es válido: " + nombreHoja);
        }
    }

    @Override
    public void updateEstatusRegistrosAnteriores(Integer anio, Integer expediente) {
        historico_Tmp_Libro_RA15Repository.updateEstatusRegistrosAnteriores(anio, expediente);
    }

    @Override
    public void updateEstatusRegistrosAnterioresTrenLigero(Integer anio, Integer expediente) {
        historico_Tmp_Libro_Tren_RA15Repository.updateEstatusRegistrosAnterioresTrenLigero(anio, expediente);
    }

    @Override
    public List<Historico_Tmp_Libro_RA15> findAllPlantasActivas() {
        return historico_Tmp_Libro_RA15Repository.findAllPlantasActivas();
    }

    @Override
    public List<Historico_Tmp_Libro_Tren_RA15> findAllPlantasActivasTrenLigero() {
        return historico_Tmp_Libro_Tren_RA15Repository.findAllPlantasActivasTrenLigero();
    }

    @Override
    public Historico_Tmp_Libro_RA15 findOnePlanta(Integer id_planta) {
        return historico_Tmp_Libro_RA15Repository.findById(id_planta).get();
    }

    @Override
    public Historico_Tmp_Libro_RA15 updatePlanta(Historico_Tmp_Libro_RA15 planta, Integer id_planta) {
        Historico_Tmp_Libro_RA15 pl = historico_Tmp_Libro_RA15Repository.findById(id_planta).get();
        pl.setRelevo_1(planta.getRelevo_1());
        pl.setRelevo_2(planta.getRelevo_2());
        //Modificar los días de descanso según el campo ingresado por el usuario
        pl.setDescansos(planta.getDescansos());
        pl.setCorrida(planta.getCorrida());
        pl.setEntra_semana(planta.getEntra_semana());
        pl.setSale_semana(planta.getSale_semana());
        pl.setHoras_trab_semana(planta.getHoras_trab_semana());
        pl.setPago_semana(planta.getPago_semana());
        pl.setEntra_sabado(planta.getPago_sabado());
        pl.setSale_sabado(planta.getSale_sabado());
        pl.setHoras_trab_sabado(planta.getHoras_trab_sabado());
        pl.setPago_sabado(planta.getPago_sabado());
        pl.setCorrida_sabado(planta.getCorrida_sabado());
        pl.setEntra_domingo(planta.getEntra_domingo());
        pl.setSale_domingo(planta.getSale_domingo());
        pl.setHoras_trab_domingo(planta.getHoras_trab_domingo());
        pl.setPago_domingo(planta.getPago_domingo());
        pl.setCorrida_domingo(pl.getCorrida_domingo());
        pl.setDeposito(planta.getDeposito());
        pl.setLinea(planta.getLinea());
        return historico_Tmp_Libro_RA15Repository.save(pl);
    }

    @Override
    public List<OperadorTitularDTO> findActiveOpTitular() {
        return historico_Tmp_Libro_RA15Repository.findActiveOpTitular();
    }

    @Override
    public List<OperadorTrenDTO> findActiveOpTitularTren() {
        return historico_Tmp_Libro_Tren_RA15Repository.findActiveOpTitularTren();
    }

    @Override
    public Boolean existsByAnioAndEstatusTmpHistorico(Integer anio) {
        return historico_Tmp_Libro_RA15Repository.existsByAnioAndEstatusTmpHistorico(anio);
    }

    @Override
    public Boolean existsByAnioAndEstatusTmpHistoricoTren(Integer anio) {
        return historico_Tmp_Libro_RA15Repository.existsByAnioAndEstatusTmpHistoricoTren(anio);
    }

    @Override
    public void exportPlantasToExcel(HttpServletResponse response) throws IOException {
        List<Historico_Tmp_Libro_RA15> listOfActivePlantas = findAllPlantasActivas();
        // Create a workbook and a sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Plantas");
        // Create headers
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Expediente", "Pago Semanal", "Pago Sábado", "Pago Domingo", "Dias de Descanso", "Total de horas", "Línea", "Depósito"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
        // Write data to the sheet
        int rowNum = 1;
        for (Historico_Tmp_Libro_RA15 planta : listOfActivePlantas) {
            // Verificar si el expediente es cero, en cuyo caso no se crea la fila
            if (planta.getExpediente() != 0) {
                Row row = sheet.createRow(rowNum++);
                String totalHoras = calculateTotalJournalHours(planta.getExpediente(), planta.getPago_semana(),
                        planta.getPago_sabado(), planta.getPago_domingo(),
                        asignarDiasDescansoNumero(planta.getDescansos()));
                row.createCell(0).setCellValue(planta.getExpediente());
                row.createCell(1).setCellValue(planta.getPago_semana());
                row.createCell(2).setCellValue(planta.getPago_sabado() != null ? String.valueOf(planta.getPago_sabado()) : "");
                row.createCell(3).setCellValue(planta.getPago_domingo() != null ? String.valueOf(planta.getPago_domingo()) : "");
                row.createCell(4).setCellValue(planta.getDescansos());
                row.createCell(5).setCellValue(totalHoras);
                row.createCell(6).setCellValue(planta.getLinea());
                row.createCell(7).setCellValue(planta.getDeposito());
            }
        }
        // Auto size columns
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        // Write the workbook content to the HttpServletResponse output stream
        try ( OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        }
        workbook.close();
    }
    
    @Override
    public String findLineaByExpediete(Integer expediente) {
        return historico_Tmp_Libro_RA15Repository.findLineaByExpediete(expediente);
    }

    //******************************FUNCIONES PARA LA IMPORTACIÓN DE DATOS MEDIANTE EXCEL Y CÁLCULO TOTALES DE HORA*****************************
    private String calculateTotalJournalHours(Integer expediente, Double pagoSemanal, Double pagoSabado, Double pagoDomingo, int[] descansos) {
        Double[] totalDeHoras = new Double[7];
        int descanso1 = descansos[0];
        int descanso2 = descansos[1];
        //Se recorre la semana de domingo a sábado
        for (int diaSemana = 1; diaSemana <= 7; diaSemana++) {
            /*Si el dia de la semana corresponde a un día de descanso del trabajador entonces se agregan las 8 horas 
            correspondientes a sus descansos y se procede a hacer el cálculo */
            if (diaSemana == descanso1 || diaSemana == descanso2) {
                totalDeHoras[diaSemana - 1] = 8.0;
                continue;
            }
            switch (diaSemana) {
                case 1: // Domingo
                    totalDeHoras[diaSemana - 1] = pagoDomingo;
                    break;
                case 7: // Sábado
                    totalDeHoras[diaSemana - 1] = pagoSabado;
                    break;
                default:
                    totalDeHoras[diaSemana - 1] = pagoSemanal;
                    break;
            }
        }
        Double horasTotales = sumarHorasArray(totalDeHoras);
        String ajusteReglaNegocio = adjustTotalHours(horasTotales);
        return ajusteReglaNegocio;
    }

    private String adjustTotalHours(Double totalHours) {
        // Si se recibe un valor nulo
        if (totalHours == null) {
            return null;
        }
        // Obtener las horas y los minutos del total de la jornada
        int horasDelTotalDeLaJornada = totalHours.intValue();
        int minutosDelTotalDeLaJornada = (int) ((totalHours - horasDelTotalDeLaJornada) * 100);
        // Ajustar los minutos según el criterio
        if (minutosDelTotalDeLaJornada >= 0 && minutosDelTotalDeLaJornada <= 2) {
            minutosDelTotalDeLaJornada = 0;
        } else if (minutosDelTotalDeLaJornada >= 3 && minutosDelTotalDeLaJornada <= 24) {
            minutosDelTotalDeLaJornada = 3;
        } else if (minutosDelTotalDeLaJornada >= 25 && minutosDelTotalDeLaJornada <= 39) {
            minutosDelTotalDeLaJornada = 5;
        } else if (minutosDelTotalDeLaJornada >= 40 && minutosDelTotalDeLaJornada <= 54) {
            minutosDelTotalDeLaJornada = 7;
        } else if (minutosDelTotalDeLaJornada >= 55) {
            horasDelTotalDeLaJornada += 1;
            minutosDelTotalDeLaJornada = 0;
        } else {
            System.err.println("No entró a ninguna condición el cálculo de proporciones de tiempo total");
            return null;
        }
        // Devolver el total de horas ajustado como una cadena
        return horasDelTotalDeLaJornada + "." + minutosDelTotalDeLaJornada;
    }

    private Double sumarHorasArray(Double[] horasArray) {
        // Inicializar minutos totales
        double sumaMinutos = 0.0;

        // Recorrer todas las horas en el array
        for (Double hora : horasArray) {
            if (hora != null) {
                // Separar las horas y los minutos
                int horas = hora.intValue();
                double minutos = (hora - horas) * 100; // Convertir los minutos a formato decimal

                // Convertir las horas a minutos y sumar
                sumaMinutos += horas * 60 + minutos;
            }
        }

        // Convertir la suma total de minutos a horas y minutos
        int horasTotales = (int) (sumaMinutos / 60);
        double minutosRestantes = sumaMinutos % 60;

        // Calcular la representación decimal
        double representacionDecimal = horasTotales + minutosRestantes / 100;
        // Retornar la suma total de horas
        return representacionDecimal;
    }

    private String getCellValueAsString(XSSFCell cell) {
        return cell != null ? cell.toString() : "";
    }

    private double convertirHorasADecimal(XSSFCell cell) {
        if (cell == null) {
            return 0.0;
        }
        double excelTimestamp = cell.getNumericCellValue();
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond((long) ((excelTimestamp - 25569) * 86400), 0, ZoneOffset.UTC);
        int hours = dateTime.getHour();
        int minutes = dateTime.getMinute();
        double decimalTime = hours + (double) minutes / 100;
        decimalTime = Double.parseDouble(String.format("%.2f", decimalTime));
        return decimalTime;
    }

    private void asignarDiasDescanso(String descansos, Historico_Tmp_Libro_RA15 tablaPlantas) {
        if (descansos.contains("DS")) {
            tablaPlantas.setDescanso_1(1); // Domingo
            tablaPlantas.setDescanso_2(7); // Sábado
        }
        if (descansos.contains("DL")) {
            tablaPlantas.setDescanso_1(1); // Domingo
            tablaPlantas.setDescanso_2(2); // Lunes
        }
        if (descansos.contains("LM")) {
            tablaPlantas.setDescanso_1(2); // Lunes
            tablaPlantas.setDescanso_2(3); // Martes
        }
        if (descansos.contains("MM")) {
            tablaPlantas.setDescanso_1(3); // Martes
            tablaPlantas.setDescanso_2(4); // Miércoles
        }
        if (descansos.contains("MJ")) {
            tablaPlantas.setDescanso_1(4); // Miércoles
            tablaPlantas.setDescanso_2(5); // Jueves
        }
        if (descansos.contains("JV")) {
            tablaPlantas.setDescanso_1(5); // Jueves
            tablaPlantas.setDescanso_2(6); // Viernes
        }
        if (descansos.contains("VS")) {
            tablaPlantas.setDescanso_1(6); // Viernes
            tablaPlantas.setDescanso_2(7); // Sabado
        }
    }

    private int[] asignarDiasDescansoNumero(String descansos) {
        int[] diasDescanso = new int[2];
        diasDescanso[0] = 0;
        diasDescanso[1] = 0;

        if (descansos.contains("DS")) {
            diasDescanso[0] = 1; // Domingo
            diasDescanso[1] = 7; // Sábado
        }
        if (descansos.contains("DL")) {
            diasDescanso[0] = 1; // Domingo
            diasDescanso[1] = 2; // Lunes
        }
        if (descansos.contains("LM")) {
            diasDescanso[0] = 2; // Lunes
            diasDescanso[1] = 3; // Martes
        }
        if (descansos.contains("MM")) {
            diasDescanso[0] = 3; // Martes
            diasDescanso[1] = 4; // Miércoles
        }
        if (descansos.contains("MJ")) {
            diasDescanso[0] = 4; // Miércoles
            diasDescanso[1] = 5; // Jueves
        }
        if (descansos.contains("JV")) {
            diasDescanso[0] = 5; // Jueves
            diasDescanso[1] = 6; // Viernes
        }
        if (descansos.contains("VS")) {
            diasDescanso[0] = 6; // Viernes
            diasDescanso[1] = 7; // Sabado
        }
        return diasDescanso;
    }

    private Integer buscarIndiceNombre(XSSFSheet worksheet, Boolean isTrenLigero) {
        int startColumn = 0;
        //Si la columna es para la carga de tren ligero entonces comienza en la columna B que es la que contiene los nombres
        if (isTrenLigero) {
            startColumn = 2;
        }
        int lastRow = worksheet.getPhysicalNumberOfRows();
        Integer nombreIndex = -1;
        for (int i = 0; i < lastRow; i++) {
            XSSFRow headerRow = worksheet.getRow(i);
            if (headerRow != null) {
                XSSFCell cell = headerRow.getCell(startColumn);
                if (cell != null && cell.getCellTypeEnum() == CellType.STRING && "NOMBRE".equals(cell.getStringCellValue().trim())) {
                    nombreIndex = i + 1;
                    break;
                }
            }
        }
        // Verificar si la celda está combinada
        if (nombreIndex != -1) {
            for (CellRangeAddress mergedRegion : worksheet.getMergedRegions()) {
                if (mergedRegion.isInRange(nombreIndex, 0)) {
                    nombreIndex = mergedRegion.getLastRow() + 1;
                    break;
                }
            }
        }
        return nombreIndex;
    }

    private void procesarOperadoresRA15(Integer nombreIndex, Integer lastRow, XSSFSheet worksheet, Integer anio) {
        //Recorre toda la fila horizontalmente y pasa a la siguiente fila
        for (int i = nombreIndex; i < lastRow - 1; i++) {
            XSSFRow row = worksheet.getRow(i);
            if (row != null) {//Verificar si la fila no es nula
                Historico_Tmp_Libro_RA15 tablaPlantas = new Historico_Tmp_Libro_RA15();
                //Se cambia el estatus de los registros menores al año recibido y trabajador
                updateEstatusRegistrosAnteriores(anio, (int) row.getCell(1).getNumericCellValue());
                //Se le coloca el año recibido por parámetro
                tablaPlantas.setAnio(anio);
                tablaPlantas.setExpediente((int) row.getCell(1).getNumericCellValue());
                tablaPlantas.setRelevo_1((int) row.getCell(2).getNumericCellValue());
                tablaPlantas.setRelevo_2((int) row.getCell(3).getNumericCellValue());
                tablaPlantas.setDescansos(row.getCell(5).getStringCellValue());
                //Se indican los días de descanso de manera numérica para el operador titular de la corrida
                String descansos = row.getCell(5).getStringCellValue();
                asignarDiasDescanso(descansos, tablaPlantas);
                String corridaCompleta = String.valueOf((int) row.getCell(6).getNumericCellValue()) + getCellValueAsString(row.getCell(7));
                tablaPlantas.setCorrida(corridaCompleta);
                tablaPlantas.setPago_semana(convertirHorasADecimal(row.getCell(13)));
                tablaPlantas.setCorrida_sabado(String.valueOf((int) row.getCell(14).getNumericCellValue()) + getCellValueAsString(row.getCell(15)));
                tablaPlantas.setPago_sabado(convertirHorasADecimal(row.getCell(21)));
                tablaPlantas.setCorrida_domingo(String.valueOf((int) row.getCell(22).getNumericCellValue()) + getCellValueAsString(row.getCell(23)));
                tablaPlantas.setPago_domingo(convertirHorasADecimal(row.getCell(29)));
                //Solo para los nuevos registros creados
                tablaPlantas.setEstatus(1);
                tablaPlantas.setLinea(getCellValueAsString(row.getCell(30)));
                tablaPlantas.setDeposito(getCellValueAsString(row.getCell(31)));
                tablaPlantas.setUnidad(getCellValueAsString(row.getCell(4)));
                tablaPlantas.setEntra_semana(convertirHorasADecimal(row.getCell(8)));
                tablaPlantas.setSale_semana(convertirHorasADecimal(row.getCell(9)));
                tablaPlantas.setHoras_trab_semana(convertirHorasADecimal(row.getCell(12)));
                tablaPlantas.setEntra_sabado(convertirHorasADecimal(row.getCell(16)));
                tablaPlantas.setSale_sabado(convertirHorasADecimal(row.getCell(17)));
                tablaPlantas.setHoras_trab_sabado(convertirHorasADecimal(row.getCell(20)));
                tablaPlantas.setEntra_domingo(convertirHorasADecimal(row.getCell(24)));
                tablaPlantas.setSale_domingo(convertirHorasADecimal(row.getCell(25)));
                tablaPlantas.setHoras_trab_domingo(convertirHorasADecimal(row.getCell(28)));
                historico_Tmp_Libro_RA15Repository.save(tablaPlantas);
            }
        }
    }

    private void procesarTrenLigeroRA15(Integer nombreIndex, Integer lastRow, XSSFSheet worksheet, Integer anio) {
        //Recorre toda la fila horizontalmente y pasa a la siguiente fila
        for (int i = nombreIndex; i < lastRow; i++) {
            XSSFRow row = worksheet.getRow(i);
            if (row != null) { // Verificar si la fila no es nula
                Integer expediente = (int) row.getCell(3).getNumericCellValue(); // Obtener el expediente
                String descansos = row.getCell(4).getStringCellValue(); // Obtener los días de descanso
                // Iterar sobre los días de la semana
                for (int diaSemana = 1; diaSemana <= 7; diaSemana++) {
                    // Crear un nuevo registro para el día de la semana actual
                    Historico_Tmp_Libro_Tren_RA15 registroDia = new Historico_Tmp_Libro_Tren_RA15();
                    //Se actualizan los registros de años anteriores para cada trabajador y dia
                    updateEstatusRegistrosAnterioresTrenLigero(anio, expediente);
                    registroDia.setAnio(anio);
                    registroDia.setExpediente(expediente);
                    registroDia.setDescansos(descansos);
                    registroDia.setEstatus(1);
                    Cat_Dias catDias = cat_DiasRepository.findById(diaSemana).orElse(null);
                    if (catDias != null) {
                        registroDia.setCat_Dias(catDias);
                        // Establecer el pago correspondiente al día de la semana actual
                        switch (diaSemana) {
                            case 1: // Domingo
                                registroDia.setPago(convertirHorasADecimal(row.getCell(8)));
                                break;
                            case 2: // Lunes
                                registroDia.setPago(convertirHorasADecimal(row.getCell(12)));
                                break;
                            case 3: // Martes
                                registroDia.setPago(convertirHorasADecimal(row.getCell(16)));
                                break;
                            case 4: // Miércoles
                                registroDia.setPago(convertirHorasADecimal(row.getCell(20)));
                                break;
                            case 5: // Jueves
                                registroDia.setPago(convertirHorasADecimal(row.getCell(24)));
                                break;
                            case 6: // Viernes
                                registroDia.setPago(convertirHorasADecimal(row.getCell(28)));
                                break;
                            case 7: // Sábado
                                registroDia.setPago(convertirHorasADecimal(row.getCell(32)));
                                break;
                        }
                        // Guardar el registro en la base de datos para cada día de la semana
                        historico_Tmp_Libro_Tren_RA15Repository.save(registroDia);
                    }
                }
            }
        }
    }

    private void procesarGeneralPatioRA15(Integer nombreIndex, Integer lastRow, XSSFSheet worksheet, Integer anio) {
        for (int i = nombreIndex; i < lastRow; i++) {
            XSSFRow row = worksheet.getRow(i);
            if (row != null) { // Verificar si la fila no es nula
                Historico_Tmp_Libro_RA15 tablaPlantas = new Historico_Tmp_Libro_RA15();
                //Se cambia el estatus de los registros menores al año recibido y trabajador
                updateEstatusRegistrosAnteriores(anio, (int) row.getCell(1).getNumericCellValue());
                //Se le coloca el año recibido por parámetro
                tablaPlantas.setAnio(anio);
                tablaPlantas.setExpediente((int) row.getCell(1).getNumericCellValue());
                tablaPlantas.setRelevo_1((int) row.getCell(2).getNumericCellValue());
                tablaPlantas.setRelevo_2((int) row.getCell(3).getNumericCellValue());
                tablaPlantas.setDescansos(row.getCell(4).getStringCellValue());
                //Se indican los días de descanso de manera numérica 
                String descansos = row.getCell(4).getStringCellValue();
                asignarDiasDescanso(descansos, tablaPlantas);
                tablaPlantas.setPago_semana(convertirHorasADecimal(row.getCell(9)));
                //Solo para los nuevos registros creados
                tablaPlantas.setEstatus(1);
                tablaPlantas.setDeposito(getCellValueAsString(row.getCell(11)));
                tablaPlantas.setEntra_semana(convertirHorasADecimal(row.getCell(6)));
                tablaPlantas.setSale_semana(convertirHorasADecimal(row.getCell(7)));
                tablaPlantas.setHoras_trab_semana(convertirHorasADecimal(row.getCell(8)));
                historico_Tmp_Libro_RA15Repository.save(tablaPlantas);
            }
        }
    }

    //Define si se crea o se actualiza el registro en libro días a partir de la tabla historico_tmp_libro_ra_15 (Operadores y patio)
    private void crearOActualizarTitular() {
        List<OperadorTitularDTO> datosTitular = historico_Tmp_Libro_RA15Repository.findActiveOpTitular();
        //Por cata titular activo se realiza: 
        for (int i = 0; i < datosTitular.size(); i++) {
            OperadorTitularDTO operadorTitularDTO = datosTitular.get(i);
            //Variables para la actualización o inserción de registros
            Integer expediente = operadorTitularDTO.getExpediente();
            String descansos = operadorTitularDTO.getDescansos();
            // Obtener valores de descanso del día que corresponde a descanso
            int[] diasDescanso = asignarDiasDescansoNumero(descansos);
            int descanso1 = diasDescanso[0];
            int descanso2 = diasDescanso[1];
            List<Libro_Dias> resultados = libro_diasRepository.findExpedienteLibroDias(expediente);
            //Si no existe el expediente entonces se crea un nuevo registro
            if (resultados.isEmpty()) {
                crearNuevoRegistroLibroDias(operadorTitularDTO, descanso1, descanso2);
                //Si existe el expediente entonces se procede a hacer la actualización por cada día
            } else {
                actualizarRegistroLibroDias(resultados, operadorTitularDTO, descanso1, descanso2);
            }
        }
    }

    //Define si se crea o actualiza el registro en libro días a partir de la tabla historico_tmp_libro_tren_ra_15 (Operadores de tren ligero)
    public void crearOActualizarTitularTrenLigero() {
        List<OperadorTrenDTO> datosOperadorTren = historico_Tmp_Libro_Tren_RA15Repository.findActiveOpTitularTren();
        for (int i = 0; i < datosOperadorTren.size(); i++) {
            OperadorTrenDTO operadorTrenDTO = datosOperadorTren.get(i);
            //Variables para la actualización o inserción de registros
            Integer expediente = operadorTrenDTO.getExpediente();
            String descansos = operadorTrenDTO.getDescansos();
            // Obtener valores de descanso del día que corresponde a descanso
            int[] diasDescanso = asignarDiasDescansoNumero(descansos);
            int descanso1 = diasDescanso[0];
            int descanso2 = diasDescanso[1];
            List<Libro_Dias> resultados = libro_diasRepository.findExpedienteLibroDias(expediente);
            //Si no existe el expediente entonces se crea un nuevo registro
            if (resultados.isEmpty()) {
                crearNuevoRegistroLibroDiasTren(operadorTrenDTO, descanso1, descanso2);
                //Si existe el expediente entonces se procede a hacer la actualización por cada día
            } else {
                actualizarRegistroLibroDiasTren(resultados, operadorTrenDTO, descanso1, descanso2);
            }
        }
    }

    //Actualiza los datos o hace setter y getter de los nuevos registros
    private void actualizarHorarioLibroDias(Libro_Dias libroD, OperadorTitularDTO operadorTitularDTO, int dia) {
        switch (dia) {
            case 1: // Domingo
                libroD.setHorario_entrada(operadorTitularDTO.getEntra_domingo() != null
                        ? formatearDecimalHoraString(operadorTitularDTO.getEntra_domingo())
                        : libroD.getHorario_entrada());
                libroD.setHorario_salida(operadorTitularDTO.getSalida_domingo() != null
                        ? formatearDecimalHoraString(operadorTitularDTO.getSalida_domingo())
                        : libroD.getHorario_salida());
                libroD.setHoras_trabajadas(operadorTitularDTO.getHoras_trab_domingo() != null
                        ? operadorTitularDTO.getHoras_trab_domingo()
                        : libroD.getHoras_trabajadas());
                libroD.setHoras_pago(operadorTitularDTO.getPago_domingo() != null
                        ? operadorTitularDTO.getPago_domingo()
                        : libroD.getHoras_pago());
                break;
            case 7: // Sábado
                libroD.setHorario_entrada(operadorTitularDTO.getEntrada_sabado() != null
                        ? formatearDecimalHoraString(operadorTitularDTO.getEntrada_sabado())
                        : libroD.getHorario_entrada());
                libroD.setHorario_salida(operadorTitularDTO.getSalida_sabado() != null
                        ? formatearDecimalHoraString(operadorTitularDTO.getSalida_sabado())
                        : libroD.getHorario_salida());
                libroD.setHoras_trabajadas(operadorTitularDTO.getHoras_trab_sabado() != null
                        ? operadorTitularDTO.getHoras_trab_sabado()
                        : libroD.getHoras_trabajadas());
                libroD.setHoras_pago(operadorTitularDTO.getPago_sabado() != null
                        ? operadorTitularDTO.getPago_sabado()
                        : libroD.getHoras_pago());
                break;
            default: // Resto de días
                libroD.setHorario_entrada(formatearDecimalHoraString(operadorTitularDTO.getEntrada_semana()));
                libroD.setHorario_salida(formatearDecimalHoraString(operadorTitularDTO.getSalida_semana()));
                libroD.setHoras_trabajadas(operadorTitularDTO.getHoras_trab_semana());
                libroD.setHoras_pago(operadorTitularDTO.getPago_semana());
                break;
        }
    }

    //*******************CREACION/ACTUALIZACION EN LIBRO DIAS DE REGISTROS OPERADORES Y GENERAL PATIO********************
    //Crea los registros nuevos de los expedientes que no se encuentren en libro días
    private void crearNuevoRegistroLibroDias(OperadorTitularDTO operadorTitularDTO, int descanso1, int descanso2) {
        if (operadorTitularDTO.getExpediente() == 0) {
            return;
        }
        for (int i = 1; i <= 7; i++) {
            Libro_Dias libroD = new Libro_Dias();
            libroD.setExpediente(operadorTitularDTO.getExpediente());
            libroD.setFecha_captura(LocalDateTime.now());
            Cat_Dias cat_dia = new Cat_Dias();
            cat_dia.setId_dia(i);
            libroD.setCat_dias(cat_dia);
            libroD.setEstatus(1);
            if (i == descanso1 || i == descanso2) {
                libroD.setDia_descanso(1);
                libro_diasRepository.save(libroD);
                continue;
            } else {
                libroD.setDia_descanso(0);
            }
            actualizarHorarioLibroDias(libroD, operadorTitularDTO, i);
            libro_diasRepository.save(libroD);
        }
    }

    //Realiza la actualización del registro existente en libro días
    private void actualizarRegistroLibroDias(List<Libro_Dias> resultados, OperadorTitularDTO operadorTitularDTO, int descanso1, int descanso2) {
        for (Libro_Dias libroDias : resultados) {
            Integer idRegistroDia = libroDias.getId_libro_dias();
            Cat_Dias diaDeLaSemana = libroDias.getCat_dias();
            Integer dia = diaDeLaSemana.getId_dia();
            Libro_Dias d = libro_diasRepository.findById(idRegistroDia).get();
            d.setEstatus(1);
            if (dia == descanso1 || dia == descanso2) {
                d.setDia_descanso(1);
                libro_diasRepository.save(d);
                continue;
            } else {
                d.setDia_descanso(0);
            }
            actualizarHorarioLibroDias(d, operadorTitularDTO, dia);
            libro_diasRepository.save(d);
        }
    }

    //***************************************CREACION/ACTUALIZACION EN LIBRO DIAS DE REGISTROS OPERADORES TREN********************
    public void crearNuevoRegistroLibroDiasTren(OperadorTrenDTO operadorTrenDTO, int descanso1, int descanso2) {
        if (operadorTrenDTO.getExpediente() == 0) {
            return;
        }
        LocalDateTime fechaCaptura = LocalDateTime.now();
        //Genera un registro nuevo por cada día de la semana
        for (int i = 1; i <= 7; i++) {
            Libro_Dias libroD = new Libro_Dias();
            libroD.setExpediente(operadorTrenDTO.getExpediente());
            libroD.setFecha_captura(fechaCaptura);
            Cat_Dias cat_dia = new Cat_Dias();
            cat_dia.setId_dia(i);
            libroD.setCat_dias(cat_dia);
            libroD.setEstatus(1);
            if (i == descanso1 || i == descanso2) {
                libroD.setDia_descanso(1);
                libro_diasRepository.save(libroD);
                //Salta a la siguiente iteración del ciclo
                continue;
            } else {
                libroD.setDia_descanso(0);
            }
            asignarHorasPago(libroD, operadorTrenDTO, i);
            libro_diasRepository.save(libroD);
        }
    }

    public void actualizarRegistroLibroDiasTren(List<Libro_Dias> resultados, OperadorTrenDTO operadorTrenDTO, int descanso1, int descanso2) {
        LocalDateTime fechaMovimiento = LocalDateTime.now();
        for (Libro_Dias libroDias : resultados) {
            Integer idRegistroDia = libroDias.getId_libro_dias();
            Cat_Dias diaDeLaSemana = libroDias.getCat_dias();
            Integer dia = diaDeLaSemana.getId_dia();
            Libro_Dias d = libro_diasRepository.findById(idRegistroDia).get();
            d.setEstatus(1);
            d.setFecha_movimiento(fechaMovimiento);
            if (dia == descanso1 || dia == descanso2) {
                d.setDia_descanso(1);
                libro_diasRepository.save(d);
                continue;
            } else {
                d.setDia_descanso(0);
            }
            asignarHorasPago(d, operadorTrenDTO, dia);
            libro_diasRepository.save(d);
        }
    }

    //Solo se asignan las horas de pago en el caso de los operadores de tren ligero
    private void asignarHorasPago(Libro_Dias libroD, OperadorTrenDTO operadorTrenDTO, int dia) {
        switch (dia) {
            case 1:
                libroD.setHoras_pago(operadorTrenDTO.getPagoDomingo());
                break;
            case 2:
                libroD.setHoras_pago(operadorTrenDTO.getPagoLunes());
                break;
            case 3:
                libroD.setHoras_pago(operadorTrenDTO.getPagoMartes());
                break;
            case 4:
                libroD.setHoras_pago(operadorTrenDTO.getPagoMiercoles());
                break;
            case 5:
                libroD.setHoras_pago(operadorTrenDTO.getPagoJueves());
                break;
            case 6:
                libroD.setHoras_pago(operadorTrenDTO.getPagoViernes());
                break;
            case 7:
                libroD.setHoras_pago(operadorTrenDTO.getPagoSabado());
                break;
            default:
                break;
        }
    }

    //Formatea a partir del doble recibodo al formato hora y minutos
    public String formatearDecimalHoraString(double horaDecimal) {
        DecimalFormat formato = new DecimalFormat("00.00");
        String horaFormateada = formato.format(horaDecimal);
        int horas = Integer.parseInt(horaFormateada.split("\\.")[0]);
        int minutos = Integer.parseInt(horaFormateada.split("\\.")[1]);
        return String.format("%02d:%02d", horas, minutos);
    }

    @Override
    public void findRelevos() {
        //Obtener los relevos con dias de descanso en dos listas
        List<RelevosDTO> relevos = historico_Tmp_Libro_RA15Repository.findRelevos();

        //Declarar la lista que guardará todos en parejas
        List<List<Integer>> relevos_dias = new ArrayList<>();
        // Declarar la lista de los dias de la semana
        List<Integer> dias_semana = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        // Ciclo para unificar los relevos 1 y descandos 1 con los relevos 2 y descansos 2
        for (int i = 0; i < relevos.size(); i++) {
            RelevosDTO relevosDTO = relevos.get(i);
            //Agregar en forma de parejas el relevo 1 y descanso 1. Ej: [(10888,1)]
            if (relevosDTO.getRelevo_1() != 0) {
                List<Integer> pareja = new ArrayList<>();
                pareja.add(relevosDTO.getRelevo_1());
                pareja.add(relevosDTO.getDescanso_1());
                relevos_dias.add(pareja);
            }
            //Agregar en forma de parejas el relevo 2 y descanso 2. Ej: [(10663,7)]
            if (relevosDTO.getRelevo_2() != 0) {
                List<Integer> pareja = new ArrayList<>();
                pareja.add(relevosDTO.getRelevo_2());
                pareja.add(relevosDTO.getDescanso_2());
                relevos_dias.add(pareja);
            }
        }
        // Definir un Comparator personalizado
        // Ordenamiento de la Lista de Menor a Mayor
        Comparator<List<Integer>> comparator = new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> lista1, List<Integer> lista2) {
                // Comparar por el primer elemento de cada lista
                return Integer.compare(lista1.get(0), lista2.get(0));
            }
        };
        // Ordenar la lista de listas utilizando el Comparator
        Collections.sort(relevos_dias, comparator);
        // Lista temporal de dias que se están guardando por expediente
        List<Integer> dias_temporal = new ArrayList<>();
        // Lista temporal de dias diferentes entre dias_temporal y los dias de la semana para obtener los de descanso
        List<Integer> diferentes = new ArrayList<>();
        int dia;
        int contador_expedientes = 0;
        // Ciclo para recorrer la lista ordenada e identificar el expediente actual y el siguiente
        for (int i = 0; i < relevos_dias.size(); i++) {

            Libro_Dias libroD = libro_diasRepository.findExpedienteDia(relevos_dias.get(i).get(0), relevos_dias.get(i).get(1));
            if (relevos_dias.size() - 1 != i) {
                // Condicion para saber si sigue siendo el mismo expediente o ya cambió
                if (Objects.equals(relevos_dias.get(i).get(0), relevos_dias.get(i + 1).get(0))) {
                    dia = relevos_dias.get(i).get(1);
                    // Si la lista dias_semana contiene el dia del registro actual se agrega a la lista dias_temporal
                    if (dias_semana.contains(dia)) {
                        dias_temporal.add(dia);
                        // Linea para hacer el SAVE en la tabla libro días sobre el registro en la posición i
                        parametrosTrabajados(dia, 0, relevos_dias.get(i).get(0));
                        contador_expedientes += 1;

                    }
                    // Si el expediente es diferente se realiza un corte de los días_temporales
                } else {
                    dia = relevos_dias.get(i).get(1);
                    // Si la lista dias_semana contiene el dia del registro actual se agrega a la lista dias_temporal
                    if (dias_semana.contains(dia)) {
                        dias_temporal.add(dia);
                        // Linea para hacer el SAVE en la tabla libro días sobre el registro en la posición i
                        parametrosTrabajados(dia, 0, relevos_dias.get(i).get(0));
                    }
                    if (contador_expedientes == 0) {
                        dia = relevos_dias.get(i).get(1);
                        parametrosTrabajados(dia, 0, relevos_dias.get(i).get(0));
                    }

                    //Comparar si en los días_semana se encuentra algún elemento de los días_temporales y guardarlos en la lista diferentes
                    for (Integer elemento : dias_semana) {
                        if (!dias_temporal.contains(elemento)) {
                            diferentes.add(elemento);
                        }
                    }
                    //Comparar si en los días_temporales se encuentra algún elemento de los días_semana y guardarlos en la lista diferentes
                    for (Integer elemento : dias_temporal) {
                        if (!dias_semana.contains(elemento)) {
                            diferentes.add(elemento);
                        }
                    }
                    // Recorrer los días diferentes que serían los que descansa el trabajador
                    for (Integer diferente : diferentes) {
                        if (libroD == null) {
                            guardaHorarios(diferente, 1, relevos_dias.get(i).get(0), "0.0", "0.0", 0.0, 0.0);
                        } else {
                            editarHorarios(diferente, 1, relevos_dias.get(i).get(0), "0.0", "0.0", 0.0, 0.0);
                        }
                    }
                    // Limpiar las listas temporales 
                    dias_temporal.clear();
                    diferentes.clear();
                    contador_expedientes = 0;
                }
            } else {
                dia = relevos_dias.get(i).get(1);
                // Si la lista dias_semana contiene el dia del registro actual se agrega a la lista dias_temporal
                if (dias_semana.contains(dia)) {
                    dias_temporal.add(dia);
                    // Linea para hacer el SAVE en la tabla libro días sobre el registro en la posición i
                    parametrosTrabajados(dia, 0, relevos_dias.get(i).get(0));
                }
                //Comparar si en los días_semana se encuentra algún elemento de los días_temporales y guardarlos en la lista diferentes
                for (Integer elemento : dias_semana) {
                    if (!dias_temporal.contains(elemento)) {
                        diferentes.add(elemento);
                    }
                }
                //Comparar si en los días_temporales se encuentra algún elemento de los días_semana y guardarlos en la lista diferentes
                for (Integer elemento : dias_temporal) {
                    if (!dias_semana.contains(elemento)) {
                        diferentes.add(elemento);
                    }
                }
                // Recorrer los días diferentes que serían los que descansa el trabajador
                for (Integer diferente : diferentes) {
                    if (libroD == null) {
                        guardaHorarios(diferente, 1, relevos_dias.get(i).get(0), "0.0", "0.0", 0.0, 0.0);
                    } else {
                        editarHorarios(diferente, 1, relevos_dias.get(i).get(0), "0.0", "0.0", 0.0, 0.0);
                    }
                }
            }
        }
    }

    // Metodo utilizado para los dias trabajados, obtener el objeto de un registro especifico de la tabla "Historico_Tmp_Libro_RA15", clasificar y obtener los parametros dependiendo el día ingresado
    public void parametrosTrabajados(int dia, int estatus, int expediente) {
        //Lista de los dias naturales de la semana
        List<Integer> dias_naturales = Arrays.asList(2, 3, 4, 5, 6);
        //Obtener el objeto de la consulta que devuelve un registro especifico
        Historico_Tmp_Libro_RA15 a = historico_Tmp_Libro_RA15Repository.findHorasTrabajadas(expediente, dia);
        Libro_Dias libroD = libro_diasRepository.findExpedienteDia(expediente, dia);

        if (a != null) {
            // Si el dia ingresado está en la lista de los dias naturales entrará a la condición
            if (dias_naturales.contains(dia)) {
                // Si el campo viene con valor nulo, cambiar el valor a 0.0 si no dejar el valor como está
                a.setEntra_semana(a.getEntra_semana() == null ? 0.0 : a.getEntra_semana());
                a.setSale_semana(a.getSale_semana() == null ? 0.0 : a.getSale_semana());
                a.setHoras_trab_semana(a.getHoras_trab_semana() == null ? 0.0 : a.getHoras_trab_semana());
                a.setPago_semana(a.getPago_semana() == null ? 0.0 : a.getPago_semana());
                // Invocar la función que va a guardar el registro que está iterando en este momento
                if (libroD == null) {
                    guardaHorarios(dia, estatus, expediente, a.getEntra_semana().toString(), a.getSale_semana().toString(), a.getHoras_trab_semana(), a.getPago_semana());
                } else {
                    editarHorarios(dia, estatus, expediente, a.getEntra_semana().toString(), a.getSale_semana().toString(), a.getHoras_trab_semana(), a.getPago_semana());
                }

            } else if (7 == dia) { // Si el día es sabado, obtener los valores correspondientes a ese día
                // Si el campo viene con valor nulo, cambiar el valor a 0.0 si no dejar el valor como está
                a.setEntra_sabado(a.getEntra_sabado() == null ? 0.0 : a.getEntra_sabado());
                a.setSale_sabado(a.getSale_sabado() == null ? 0.0 : a.getSale_sabado());
                a.setHoras_trab_sabado(a.getHoras_trab_sabado() == null ? 0.0 : a.getHoras_trab_sabado());
                a.setPago_sabado(a.getPago_sabado() == null ? 0.0 : a.getPago_sabado());
                // Invocar la función que va a guardar el registro que está iterando en este momento

                if (libroD == null) {
                    guardaHorarios(dia, estatus, expediente, a.getEntra_sabado().toString(), a.getSale_sabado().toString(), a.getHoras_trab_sabado(), a.getPago_sabado());
                } else {
                    editarHorarios(dia, estatus, expediente, a.getEntra_sabado().toString(), a.getSale_sabado().toString(), a.getHoras_trab_sabado(), a.getPago_sabado());
                }
            } else if (1 == dia) { // Si el día es domingo, obtener los valores correspondientes a ese día
                // Si el campo viene con valor nulo, cambiar el valor a 0.0 si no dejar el valor como está
                a.setEntra_domingo(a.getEntra_domingo() == null ? 0.0 : a.getEntra_domingo());
                a.setSale_domingo(a.getSale_domingo() == null ? 0.0 : a.getSale_domingo());
                a.setHoras_trab_domingo(a.getHoras_trab_domingo() == null ? 0.0 : a.getHoras_trab_domingo());
                a.setPago_domingo(a.getPago_domingo() == null ? 0.0 : a.getPago_domingo());
                // Invocar la función que va a guardar el registro que está iterando en este momento
                if (libroD == null) {
                    guardaHorarios(dia, estatus, expediente, a.getEntra_domingo().toString(), a.getSale_domingo().toString(), a.getHoras_trab_domingo(), a.getPago_domingo());
                } else {
                    editarHorarios(dia, estatus, expediente, a.getEntra_domingo().toString(), a.getSale_domingo().toString(), a.getHoras_trab_domingo(), a.getPago_domingo());
                }

            }
        }
    }

    // Realizar el insert en la tabla Libro Dias con los parametros recolectados anteriormente
    public void guardaHorarios(int dia, int estatus, int expediente, String entrada, String salida, Double horas_trabajadas, Double pago) {
        // Instancia de libro Dias para guardar en la tabla
        Libro_Dias libroD = new Libro_Dias();
        // Instancia de Catalogo Dias para darle valor al id del día en turno
        Cat_Dias cat_dias = new Cat_Dias();
        // Asignar el día en turno a la instancia de Catalogo días
        cat_dias.setId_dia(dia);
        libroD.setCat_dias(cat_dias);
        libroD.setEstatus(1);
        libroD.setDia_descanso(estatus);
        LocalDateTime datetime = LocalDateTime.now();
        libroD.setFecha_captura(datetime);
        libroD.setFecha_movimiento(datetime);
        libroD.setExpediente(expediente);
        libroD.setHorario_entrada(entrada);
        libroD.setHorario_salida(salida);
        libroD.setHoras_trabajadas(horas_trabajadas);
        libroD.setHoras_pago(pago);
        libro_diasRepository.save(libroD);
    }

    // Realizar el update en la tabla Libro Dias con los parametros recolectados anteriormente
    public void editarHorarios(int dia, int estatus, int expediente, String entrada, String salida, Double horas_trabajadas, Double pago) {
        Libro_Dias libroD = libro_diasRepository.findExpedienteDia(expediente, dia);
        // Instancia de Catalogo Dias para darle valor al id del día en turno
        Cat_Dias cat_dias = new Cat_Dias();
        // Asignar el día en turno a la instancia de Catalogo días
        cat_dias.setId_dia(dia);
        libroD.setCat_dias(cat_dias);
        libroD.setEstatus(1);
        libroD.setDia_descanso(estatus);
        LocalDateTime datetime = LocalDateTime.now();
        libroD.setFecha_captura(datetime);
        libroD.setFecha_movimiento(datetime);
        libroD.setExpediente(expediente);
        libroD.setHorario_entrada(entrada);
        libroD.setHorario_salida(salida);
        libroD.setHoras_trabajadas(horas_trabajadas);
        libroD.setHoras_pago(pago);
        libro_diasRepository.save(libroD);
    }

}
