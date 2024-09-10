/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.Calculo_Dif_Cve19DTO;
import com.sirh.sirh.DTO.Listado_RA_10DTO;
import com.sirh.sirh.DTO.TrabajadorPagoRA10;
import com.sirh.sirh.DTO.Trabajador_SueldoDTO;
import com.sirh.sirh.entity.Captura_Semanal_RA10;
import com.sirh.sirh.entity.Trabajador_plaza;
import com.sirh.sirh.repository.Captura_Semanal_RA10Repository;
import com.sirh.sirh.repository.PlazaRepository;
import com.sirh.sirh.service.Captura_Semanal_RA10Service;
import com.sirh.sirh.service.TrabajadorService;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rroscero23
 */
@Service
@Transactional
public class Captura_Semanal_RA10ServiceImpl implements Captura_Semanal_RA10Service {

    @Autowired
    Captura_Semanal_RA10Repository captura_Semanal_RA10Repository;

    @Autowired
    TrabajadorService trabajadorService;

    @Autowired
    PlazaRepository plazaRepository;

    @Override
    public Captura_Semanal_RA10 saveRa10(Captura_Semanal_RA10 captura_Semanal_RA10) {
        Integer idTrabajadorAusente = captura_Semanal_RA10.getTrabajador_ausente_id();
        if (idTrabajadorAusente != null) {
            Trabajador_plaza trabPlaza = trabajadorService.findPlazaTrabajador(idTrabajadorAusente);
            Integer idTipoNomina = trabPlaza.getCat_Tipo_Nomina().getId_tipo_nomina();
            if (idTipoNomina != 1) {
                throw new RuntimeException("El tipo de nómina del suplente no es válido. Se esperaba un suplente de la nómina de Varios.");
            }
        }
        Captura_Semanal_RA10 ra10 = new Captura_Semanal_RA10();
        ra10.setFecha_inicial(captura_Semanal_RA10.getFecha_inicial());
        ra10.setFecha_final(captura_Semanal_RA10.getFecha_final());
        ra10.setPeriodo_generacion(captura_Semanal_RA10.getPeriodo_generacion());
        ra10.setPeriodo_aplicacion(captura_Semanal_RA10.getPeriodo_aplicacion());
        ra10.setMartes(captura_Semanal_RA10.getMartes());
        ra10.setMiercoles(captura_Semanal_RA10.getMiercoles());
        ra10.setJueves(captura_Semanal_RA10.getJueves());
        ra10.setViernes(captura_Semanal_RA10.getViernes());
        ra10.setSabado(captura_Semanal_RA10.getSabado());
        ra10.setDomingo(captura_Semanal_RA10.getDomingo());
        ra10.setLunes(captura_Semanal_RA10.getLunes());
        ra10.setAdministrativo(captura_Semanal_RA10.getAdministrativo());
        ra10.setOperativo(captura_Semanal_RA10.getOperativo());
        ra10.setTotal_semana(captura_Semanal_RA10.getTotal_semana());
        ra10.setComentario(captura_Semanal_RA10.getComentario());
        ra10.setTrabajador_id(captura_Semanal_RA10.getTrabajador_id());
        ra10.setTrabajador_ausente_id(captura_Semanal_RA10.getTrabajador_ausente_id());
        ra10.setCat_Puesto(captura_Semanal_RA10.getCat_Puesto());
        ra10.setMotivo_id(captura_Semanal_RA10.getMotivo_id());
        ra10.setEstatus(1);
        return captura_Semanal_RA10Repository.save(ra10);
    }

    @Override
    public Captura_Semanal_RA10 updateRa10(Captura_Semanal_RA10 captura_Semanal_RA10, Integer id_ra_10) {
        Captura_Semanal_RA10 ra10 = this.captura_Semanal_RA10Repository.findById(id_ra_10).get();
        ra10.setPeriodo_aplicacion(captura_Semanal_RA10.getPeriodo_aplicacion());
        ra10.setMiercoles(captura_Semanal_RA10.getMiercoles());
        ra10.setJueves(captura_Semanal_RA10.getJueves());
        ra10.setViernes(captura_Semanal_RA10.getViernes());
        ra10.setSabado(captura_Semanal_RA10.getSabado());
        ra10.setDomingo(captura_Semanal_RA10.getDomingo());
        ra10.setLunes(captura_Semanal_RA10.getLunes());
        ra10.setMartes(captura_Semanal_RA10.getMartes());
        ra10.setTotal_semana(captura_Semanal_RA10.getTotal_semana());
        ra10.setComentario(captura_Semanal_RA10.getComentario());
//        ra10.setMotivo_id(captura_Semanal_RA10.getMotivo_id());
        return captura_Semanal_RA10Repository.save(ra10);
    }

    @Override
    public List<Captura_Semanal_RA10> findCapturasTrabajador(Integer trabajador_id) {
        return captura_Semanal_RA10Repository.findCapturasTrabajador(trabajador_id);
    }

    @Override
    public Captura_Semanal_RA10 findOneCapturaSemanalRa10(Integer id_ra_10) {
        return captura_Semanal_RA10Repository.findById(id_ra_10).get();
    }

    @Override
    public Captura_Semanal_RA10 cambioEstatusCaptura(Integer id_ra_10, Integer estatus) {
        Captura_Semanal_RA10 csRa10 = captura_Semanal_RA10Repository.findById(id_ra_10).get();
        csRa10.setEstatus(estatus);
        return captura_Semanal_RA10Repository.save(csRa10);
    }

    @Override
    public List<Captura_Semanal_RA10> findCapturaSemanalByFechas(Date fechaInicial, Date fechaFinal, Integer trabajador_id) {
        return captura_Semanal_RA10Repository.findCapturaSemanalByFechas(fechaInicial, fechaFinal, trabajador_id);
    }

    @Override
    public List<TrabajadorPagoRA10> findListadoExcel(Integer periodo_generacion) {
        return captura_Semanal_RA10Repository.findListadoExcel(periodo_generacion);
    }

    @Override
    public List<Listado_RA_10DTO> findAreaAndPeriodoGen(Integer id_area, Integer periodo_generacion) {
        return captura_Semanal_RA10Repository.findAreaAndPeriodoGen(id_area, periodo_generacion);
    }

    @Override
    public void exportTrabajadoresConMontosRA10(HttpServletResponse response, Integer periodo_generacion) throws IOException {
        List<TrabajadorPagoRA10> montosPeriodo = findListadoExcel(periodo_generacion);
        if (montosPeriodo.isEmpty()) {
            throw new RuntimeException("La lista de montos para el periodo especificado está vacía.");
        }
        // Create a workbook and a sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Plantas");
        // Create headers
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Expediente", "Total Registrado"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
        // Write data to the sheet
        int rowNum = 1;
        for (TrabajadorPagoRA10 montos : montosPeriodo) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(montos.getExpediente());
            row.createCell(1).setCellValue(montos.getTotal_semana());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try ( OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    @Override
    public Boolean existeCapturaRa10PorIdTrabajador(Integer trabajador_id, Integer periodo_generacion) {
        return captura_Semanal_RA10Repository.existeCapturaRa10PorIdTrabajador(trabajador_id, periodo_generacion);
    }

    @Override
    public List<Captura_Semanal_RA10> buscarCapturaPeriodoTrabajador(Integer trabajador_id, Integer periodo_generacion) {
        return captura_Semanal_RA10Repository.buscarCapturaPeriodoTrabajador(trabajador_id, periodo_generacion);
    }

    @Override
    public Calculo_Dif_Cve19DTO calcularCve19DiferenciaTiempoExtra(Integer trabajador_id, Integer periodo_generacion,
            Double total_te_doble, Double total_te_paseos_doble,
            Double total_te_festivo_doble, Double horas_extras_laboradas_domingo) {
        // Se obtiene la información de la captura existente para RA10 (Si existe por periodo y id del trabajador)
        List<Captura_Semanal_RA10> capturaRA10 = captura_Semanal_RA10Repository.buscarCapturaPeriodoTrabajador(trabajador_id, periodo_generacion);
        Trabajador_SueldoDTO sueldoActual = plazaRepository.findSueldosTrabajador(trabajador_id);
        if (!capturaRA10.isEmpty()) {
            // Declaración de variables para utilizar posteriormente
            Double primaDominicalOrdinaria = 0.0;
            Double primaDominicalSuplencia = 0.0;
            Double totalSueldoOrdinario = 0.0;
            Double totalSueldoSuplencia = 0.0;
            Double diferenciaCve19 = 0.0;
            Double diferenciaPrimasDominicales = 0.0;
            Double totalHrsDoblesExtras = total_te_doble + total_te_paseos_doble + total_te_festivo_doble;

            // Si la implementación es correcta solo debe retornar una instancia de ra10 con estatus activo
            Captura_Semanal_RA10 instanciaCapturaRA10 = capturaRA10.get(0);
            // Se obtiene si el trabajador es operativo o administrativo
            Boolean operativo = instanciaCapturaRA10.getOperativo();
            Boolean administrativo = instanciaCapturaRA10.getAdministrativo();
            // Se obtienen los sueldos por hora 8 y 7 para asignarlos según corresponda (Admin 7 hrs, Operativo 8 hrs)
            Double sueldoSupl8hrs = instanciaCapturaRA10.getCat_Puesto().getSueldo_hora();
            Double sueldoSupl7hrs = instanciaCapturaRA10.getCat_Puesto().getSueldoHora7();
            // Se obtienen los sueldos por hora 8 y 7 del puesto actual del trabajador
            Double sueldoActual8hrs = sueldoActual.getSueldoHora8();
            Double sueldoActual7hrs = sueldoActual.getSueldoHora7();

            // Cálculos de la diferencia de tiempo extra CVE 19 para la RA10 según el tipo del trabajador
            //********************OPERATIVO************************
            if (operativo) {
                primaDominicalOrdinaria = (sueldoActual8hrs * 8) / 2;
                primaDominicalSuplencia = (sueldoSupl8hrs * 8) / 2;
                // Multiplicación de sueldos por hora por horas extras
                totalSueldoOrdinario = sueldoActual8hrs * totalHrsDoblesExtras;
                totalSueldoSuplencia = sueldoSupl8hrs * totalHrsDoblesExtras;
                // Cálculo del total de la diferencia
                diferenciaCve19 = 2 * (totalSueldoSuplencia - totalSueldoOrdinario);
                // Cálculo de la diferencia en primas dominicales si el trabajador tiene un registro de horas extras en domingo
                if (horas_extras_laboradas_domingo > 0) {
                    diferenciaPrimasDominicales = primaDominicalSuplencia - primaDominicalOrdinaria;
                }
                //********************ADMINISTRATIVO************************
            } else if (administrativo) {
                primaDominicalOrdinaria = (sueldoActual7hrs * 7) / 2;
                primaDominicalSuplencia = (sueldoSupl7hrs * 7) / 2;
                // Multiplicación de sueldos por hora por horas extras
                totalSueldoOrdinario = sueldoActual7hrs * totalHrsDoblesExtras;
                totalSueldoSuplencia = sueldoSupl7hrs * totalHrsDoblesExtras;
                // Calculo total de la diferencia
                diferenciaCve19 = 2 * (totalSueldoSuplencia - totalSueldoOrdinario);
                // Cálculo de la diferencia en primas dominicales si el trabajador tiene un registro de horas extras en domingo
                if (horas_extras_laboradas_domingo > 0) {
                    diferenciaPrimasDominicales = primaDominicalSuplencia - primaDominicalOrdinaria;
                }
            } else {
                throw new RuntimeException("No se puede determinar si el trabajador es operativo o administrativo.");
            }
            // Formateo de los valores a dos decimales
            DecimalFormat df = new DecimalFormat("#.##");
            diferenciaCve19 = Double.valueOf(df.format(diferenciaCve19));
            diferenciaPrimasDominicales = Double.valueOf(df.format(diferenciaPrimasDominicales));
            totalHrsDoblesExtras = Double.valueOf(df.format(totalHrsDoblesExtras));
            // Se asigna al objeto DTO y se retorna
            return setterGetterCalculo(trabajador_id, periodo_generacion, sueldoSupl7hrs,
                    sueldoActual7hrs, totalHrsDoblesExtras, diferenciaPrimasDominicales, diferenciaCve19);

        } else {
            // Si la instancia de RA10 está vacía significa que el trabajador no tiene captura en el periodo de generación recibido
            return null;
        }
    }

    public Calculo_Dif_Cve19DTO setterGetterCalculo(Integer idTrabajador, Integer periodoGeneracion, Double sueldoHoraSupl,
            Double sueldoHoraActual, Double totalHorasDobles, Double diferenciaPrimaDominical, Double diferenciaCve19) {
        Calculo_Dif_Cve19DTO difCve19 = new Calculo_Dif_Cve19DTO();
        difCve19.setId_trabajador(idTrabajador);
        difCve19.setPeriodo_generacion(periodoGeneracion);
        difCve19.setSueldo_hora_suplencia(sueldoHoraSupl);
        difCve19.setSueldo_hora_actual(sueldoHoraActual);
        difCve19.setTotal_horas_dobles(totalHorasDobles);
        difCve19.setDiferencia_prima_dominical(diferenciaPrimaDominical);
        difCve19.setDiferencia_pagar_tiempo_extra(diferenciaCve19);
        return difCve19;
    }
}
