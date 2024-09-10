/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.Periodos_PagoDTO;
import com.sirh.sirh.entity.Cat_Tipo_Nomina;
import com.sirh.sirh.entity.Ciclos_Conceptos;
import com.sirh.sirh.entity.Periodos_Pago;
import com.sirh.sirh.entity.Semanas_Corte_Tiempo_Extra;
import com.sirh.sirh.repository.Ciclos_ConceptosRepository;
import com.sirh.sirh.repository.Periodos_PagoRepository;
import com.sirh.sirh.repository.Semanas_Corte_Tiempo_ExtraRepository;
import com.sirh.sirh.service.PeriodosPagoService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
 * @author oguerrero23
 */
@Service
@Transactional
public class PeriodosPagoServiceImpl implements PeriodosPagoService {

    @Autowired
    private Periodos_PagoRepository periodos_PagoRepository;

    @Autowired
    private Ciclos_ConceptosRepository ciclos_ConceptosRepository;

    @Autowired
    private Semanas_Corte_Tiempo_ExtraRepository semanas_Corte_Tiempo_ExtraRepository;

    @Autowired
    private ModelMapper modelMapper;

    // *************************************** PERIODOS DE PAGO ************************************* 
    @Override
    public List<Periodos_Pago> saveAllPeriodos(List<Periodos_PagoDTO> data) {
        List<Periodos_Pago> periodos = new ArrayList<>();
        for (Periodos_PagoDTO periodoDTO : data) {
            Periodos_Pago periodo = new Periodos_Pago();
            periodo.setMes(periodoDTO.getMes());
            periodo.setAnio(periodoDTO.getAnio());
            periodo.setPeriodo(periodoDTO.getPeriodo());
            periodo.setFecha_inicial(periodoDTO.getFecha_inicial());
            periodo.setFecha_final(periodoDTO.getFecha_final());
            periodo.setFecha_inicial_sueldo(periodoDTO.getFecha_inicial_sueldo());
            periodo.setFecha_final_sueldo(periodoDTO.getFecha_final_sueldo());
            periodo.setDias_incluidos(periodoDTO.getDias_incluidos());
            periodo.setFecha_limite(periodoDTO.getFecha_limite());
            periodo.setFecha_corte(periodoDTO.getFecha_corte());
            periodo.setFecha_entrega_caja(periodoDTO.getFecha_entrega_caja());
            periodo.setFecha_entrega_caja_anticipada(periodoDTO.getFecha_entrega_caja_anticipada());
            periodo.setFecha_pago(periodoDTO.getFecha_pago());
            periodo.setPagado(periodoDTO.getPagado());
            periodo.setId_nomina(periodoDTO.getId_nomina());
            periodo.setEstimulo_puntualidad(periodoDTO.getEstimulo_puntualidad());
            periodo.setEstimulo_apego_reglamento(periodoDTO.getEstimulo_apego_reglamento());
            periodo.setAyuda_transporte(periodoDTO.getAyuda_transporte());
            periodo.setQuinquenio_mensual(periodoDTO.getQuinquenio_mensual());
            periodo.setVales_despensa(periodoDTO.getVales_despensa());
            periodo.setAguinaldo(periodoDTO.getAguinaldo());
            periodo.setEspecial(periodoDTO.getEspecial());
            periodo.setFecha_movimiento(periodoDTO.getFecha_movimiento());
            periodo.setFecha_inicial_das(periodoDTO.getFecha_inicial_das());
            periodo.setFecha_final_das(periodoDTO.getFecha_final_das());
            periodo.setFecha_inicial_est_vac(periodoDTO.getFecha_inicial_est_vac());
            periodo.setFecha_final_est_vac(periodoDTO.getFecha_final_est_vac());
            periodo.setFecha_imss(periodoDTO.getFecha_imss());
            periodo.setPago_pens(periodoDTO.getPago_pens());
            periodos.add(periodo);
        }
        return periodos_PagoRepository.saveAll(periodos);
    }

    public void saveSemanasCorteFromExcel(MultipartFile file) throws IOException {
        if (semanas_Corte_Tiempo_ExtraRepository.existeSemanasCorteAnioActual()) {
            throw new RuntimeException("Ya existen semanas de corte para el año actual.");
        }
        List<Semanas_Corte_Tiempo_Extra> semanasCorteList = new ArrayList<>();
        try ( Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Saltar la fila de encabezado
                    continue;
                }
                Semanas_Corte_Tiempo_Extra semanasCorte = new Semanas_Corte_Tiempo_Extra();
                semanasCorte.setAnio((int) row.getCell(0).getNumericCellValue());
                semanasCorte.setMes((int) row.getCell(1).getNumericCellValue());
                semanasCorte.setSemana((int) row.getCell(2).getNumericCellValue());
                semanasCorte.setFecha_inicial(convertToDate(row.getCell(3)));
                semanasCorte.setFecha_final(convertToDate(row.getCell(4)));
                semanasCorte.setPeriodo_aplicacion((int) row.getCell(5).getNumericCellValue());

                semanasCorteList.add(semanasCorte);
            }
        }
        semanas_Corte_Tiempo_ExtraRepository.saveAll(semanasCorteList);
    }

    @Override
    public List<String> findNominasCargadas() {
        return periodos_PagoRepository.findNominasCargadas();
    }

    @Override
    public List<Periodos_Pago> listAllPeriodos(Integer nomina_id) {
        return periodos_PagoRepository.listarAllPeriodos(nomina_id);
    }

    // *************************************** CICLOS CONCEPTOS ************************************* 
    @Override
    public Ciclos_Conceptos saveCiclos(Ciclos_Conceptos ciclo) {
        Ciclos_Conceptos c = new Ciclos_Conceptos();
        c.setCat_Tipo_Nomina(ciclo.getCat_Tipo_Nomina());
        c.setAnio(ciclo.getAnio());
        c.setMes(ciclo.getMes());
        c.setSemana(ciclo.getSemana());
        c.setFecha_inicial(ciclo.getFecha_inicial());
        c.setFecha_final(ciclo.getFecha_final());
        c.setPeriodo_aplicacion(ciclo.getPeriodo_aplicacion());
        c.setEstatus(1);
        return ciclos_ConceptosRepository.save(c);
    }

    @Override
    public List<Ciclos_Conceptos> findAllCiclos() {
        return ciclos_ConceptosRepository.findAll().stream().map(ciclos -> modelMapper.map(ciclos, Ciclos_Conceptos.class)).collect(Collectors.toList());
    }

    @Override
    public Ciclos_Conceptos updateCiclos(Ciclos_Conceptos ciclo, Integer id_ciclo) {
        Ciclos_Conceptos c = this.ciclos_ConceptosRepository.findById(id_ciclo).get();
        c.setCat_Tipo_Nomina(ciclo.getCat_Tipo_Nomina());
        c.setAnio(ciclo.getAnio());
        c.setMes(ciclo.getMes());
        c.setSemana(ciclo.getSemana());
        c.setFecha_inicial(ciclo.getFecha_inicial());
        c.setFecha_final(ciclo.getFecha_final());
        c.setPeriodo_aplicacion(ciclo.getPeriodo_aplicacion());
        c.setEstatus(1);
        return ciclos_ConceptosRepository.save(c);
    }

    @Override
    public Ciclos_Conceptos findOneCiclo(Integer id_ciclo) {
        return ciclos_ConceptosRepository.findById(id_ciclo).get();
    }

    @Override
    public void importarCiclosExcel(MultipartFile archivoExcel) throws IOException, ParseException {
        XSSFWorkbook workbook = new XSSFWorkbook(archivoExcel.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        int lastRow = worksheet.getPhysicalNumberOfRows();
        for (int i = 1; i < lastRow; i++) {
            XSSFRow row = worksheet.getRow(i);
            if (row != null) {//Verificar si la fila no es nula
                Ciclos_Conceptos ciclos = new Ciclos_Conceptos();
                Cat_Tipo_Nomina nomina = new Cat_Tipo_Nomina();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String formattedDate = sdf.format(row.getCell(4).getDateCellValue());
                String formattedDateFin = sdf.format(row.getCell(5).getDateCellValue());

                LocalDate fecha_inicial = LocalDate.parse(formattedDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                LocalDate fecha_final = LocalDate.parse(formattedDateFin, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                nomina.setId_tipo_nomina((int) row.getCell(0).getNumericCellValue());
                ciclos.setCat_Tipo_Nomina(nomina);
                ciclos.setAnio((int) row.getCell(1).getNumericCellValue());
                ciclos.setMes((int) row.getCell(2).getNumericCellValue());
                ciclos.setSemana((int) row.getCell(3).getNumericCellValue());
                ciclos.setFecha_inicial(fecha_inicial);
                ciclos.setFecha_final(fecha_final);
                ciclos.setPeriodo_aplicacion((int) row.getCell(6).getNumericCellValue());
                ciclos.setEstatus(1);
                ciclos_ConceptosRepository.save(ciclos);
            }
        }
    }

    @Override
    public void deleteCiclos() {
        ciclos_ConceptosRepository.deleteCiclos();
    }

    //**************PERIODOS DE AGUINALDO **********************
    @Override
    public List<Periodos_Pago> findPeriodosAguinaldo(Integer id_nomina) {
        return periodos_PagoRepository.findPeriodosAguinaldo(id_nomina);
    }

    @Override
    public List<Object[]> findPeriodosProximosSinPagar() {
        return periodos_PagoRepository.findPeriodosProximosSinPagar();
    }

    //Función para convertir celdas en isntancias de local date }
    private LocalDate convertToDate(Cell cell) {
        if (DateUtil.isCellDateFormatted(cell)) {
            Date date = cell.getDateCellValue();
            return Instant.ofEpochMilli(date.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        return null;
    }

}
