/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.Calculos_SdiDTO;
import com.sirh.sirh.DTO.SDIConfianzaDTO;
import com.sirh.sirh.DTO.SDIEstructuraDTO;
import com.sirh.sirh.DTO.SDITransportacionDTO;
import com.sirh.sirh.DTO.SDIVariosDTO;
import com.sirh.sirh.DTO.SdiDTO;
import com.sirh.sirh.DTO.SdiSua_DTO;
import com.sirh.sirh.DTO.Sdi_IdseDTO;
import com.sirh.sirh.DTO.Trabajador_SueldoDTO;
import com.sirh.sirh.entity.Cat_Bimestres_Sdi;
import com.sirh.sirh.entity.Cat_Genero;
import com.sirh.sirh.entity.Cat_Incidencias;
import com.sirh.sirh.entity.Persona;
import com.sirh.sirh.entity.Rela_Calculos_Sdi_Cve;
import com.sirh.sirh.entity.Tmp_Sdi_Acum_Bim;
import com.sirh.sirh.entity.Tmp_Sdi_Acum_Bimestre_Cves;
import com.sirh.sirh.entity.Tmp_Sdi_Calculos_Bim;
import com.sirh.sirh.entity.Tmp_Sdi_Fijo_Calculos_Bim;
import com.sirh.sirh.entity.Tmp_Sdi_Variable_Calculos_Bim;
import com.sirh.sirh.entity.Trabajador;
import com.sirh.sirh.entity.Cat_Si_No;
import com.sirh.sirh.entity.Cat_Tipo_Mov_Idse_Sua;
import com.sirh.sirh.entity.Cat_Tipo_Nomina;
import com.sirh.sirh.entity.Trabajador_plaza;
import com.sirh.sirh.repository.Cat_Bimestres_SdiRepository;
import com.sirh.sirh.repository.Cat_IncidenciasRepository;
import com.sirh.sirh.repository.Cat_Tipo_Mov_Idse_SuaRepository;
import com.sirh.sirh.repository.Pagos_Calculos_Percepcion_1Repository;
import com.sirh.sirh.repository.Pagos_Calculos_Percepcion_2Repository;
import com.sirh.sirh.repository.Pagos_Calculos_Percepcion_3Repository;
import com.sirh.sirh.repository.Pagos_Calculos_Percepcion_5Repository;
import com.sirh.sirh.repository.PlazaRepository;
import com.sirh.sirh.repository.Rela_Calculos_Sdi_CveRepository;
import com.sirh.sirh.repository.Tmp_Sdi_Acum_BimRepository;
import com.sirh.sirh.repository.Tmp_Sdi_Acum_Bimestre_CvesRepository;
import com.sirh.sirh.repository.Tmp_Sdi_Calculos_BimRepository;
import com.sirh.sirh.repository.Tmp_Sdi_Variable_Calculos_BimRepository;
import com.sirh.sirh.repository.Tmp_Sdi_Fijo_Calculos_BimRepository;
import com.sirh.sirh.repository.TrabajadorRepository;
import com.sirh.sirh.repository.Trabajador_plazaRepository;
import com.sirh.sirh.service.CalculoSdiService;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *
 * @author rroscero23
 */
@Service
@Transactional
public class CalculoSdiServiceImpl implements CalculoSdiService {

    @Autowired
    private Rela_Calculos_Sdi_CveRepository rela_Calculos_Sdi_CveRepository;

    @Autowired
    private Tmp_Sdi_Acum_BimRepository tmp_Sdi_Acum_BimRepository;

    @Autowired
    private Tmp_Sdi_Acum_Bimestre_CvesRepository tmp_Sdi_Acum_Bimestre_CvesRepository;

    @Autowired
    private Tmp_Sdi_Calculos_BimRepository tmp_Sdi_Calculos_BimRepository;

    @Autowired
    private Tmp_Sdi_Fijo_Calculos_BimRepository tmp_Sdi_Fijo_Calculos_BimRepository;

    @Autowired
    private Tmp_Sdi_Variable_Calculos_BimRepository tmp_Sdi_Variable_Calculos_BimRepository;

    @Autowired
    private Pagos_Calculos_Percepcion_1Repository pagos_Calculos_Percepcion_1Repository;

    @Autowired
    private Pagos_Calculos_Percepcion_2Repository pagos_Calculos_Percepcion_2Repository;

    @Autowired
    private Pagos_Calculos_Percepcion_3Repository pagos_Calculos_Percepcion_3Repository;

    @Autowired
    private Pagos_Calculos_Percepcion_5Repository pagos_Calculos_Percepcion_5Repository;

    @Autowired
    private Cat_Bimestres_SdiRepository cat_Bimestres_SdiRepository;

    @Autowired
    private Cat_Tipo_Mov_Idse_SuaRepository cat_Tipo_Mov_Idse_SuaRepository;

    @Autowired
    private PlazaRepository plazaRepository;

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private Trabajador_plazaRepository trabajadorPlazaRepository;

    @Autowired
    private Cat_IncidenciasRepository cat_IncidenciasRepository;

    @Override
    public Rela_Calculos_Sdi_Cve findRelacionCalculoIncidencia(String valorCampo) {
        return rela_Calculos_Sdi_CveRepository.findRelacionCalculoIncidencia(valorCampo);
    }

    @Override
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
            Double totalInci1210,
            Double totalInci1211,
            Double totalInci1212,
            Double totalInci1213,
            Double totalInci1214,
            Double totalInci1218) {
        //Se crea la instancia del bimestre
        Tmp_Sdi_Acum_Bim nuevoTmpAcumBim = new Tmp_Sdi_Acum_Bim();
        Cat_Bimestres_Sdi catBimestre = new Cat_Bimestres_Sdi();
        catBimestre.setId_bimestre(idBimestre);

        nuevoTmpAcumBim.setCat_Bimestres_Sdi(catBimestre);
        nuevoTmpAcumBim.setTrabajador_id(idTrabajador);
        nuevoTmpAcumBim.setFecha_egreso(fechaEgreso);
        nuevoTmpAcumBim.setPago_id_nomina(1);
        nuevoTmpAcumBim.setPago_id_puesto(1);
        nuevoTmpAcumBim.setPago_sueldo(1.0);
        nuevoTmpAcumBim.setId_cambios(1);
        //Se generan las claves correspondientes
        Tmp_Sdi_Acum_Bimestre_Cves nuevasClaves = new Tmp_Sdi_Acum_Bimestre_Cves();
        nuevasClaves.setClave_nomina_01(totalCve1);
        nuevasClaves.setClave_nomina_03(totalCve3);
        nuevasClaves.setClave_nomina_04(totalCve4);
        nuevasClaves.setClave_nomina_05(totalCve5);
        nuevasClaves.setClave_nomina_06(totalCve6);
        nuevasClaves.setClave_nomina_07(totalCve7);
        nuevasClaves.setClave_nomina_09(totalCve9);
        nuevasClaves.setClave_nomina_10(totalCve10);
        nuevasClaves.setClave_nomina_11(totalCve11);
        nuevasClaves.setClave_nomina_12(totalCve12);
        nuevasClaves.setClave_nomina_14(totalCve14);
        nuevasClaves.setClave_nomina_15(totalCve15);
        nuevasClaves.setClave_nomina_16(totalCve16);
        nuevasClaves.setClave_nomina_17(totalCve17);
        nuevasClaves.setClave_nomina_18(totalCve18);
        nuevasClaves.setClave_nomina_19(totalCve19);
        nuevasClaves.setClave_nomina_21(totalCve21);
        nuevasClaves.setClave_nomina_22(totalCve22);
        nuevasClaves.setClave_nomina_23(totalCve23);
        nuevasClaves.setClave_nomina_24(totalCve24);
        nuevasClaves.setClave_nomina_26(totalCve26);
        nuevasClaves.setClave_nomina_27(totalCve27);
        nuevasClaves.setClave_nomina_28(totalCve28);
        nuevasClaves.setClave_nomina_30(totalCve30);
        nuevasClaves.setClave_nomina_31(totalCve31);
        nuevasClaves.setClave_nomina_32(totalCve32);
        nuevasClaves.setClave_nomina_33(totalCve33);
        nuevasClaves.setClave_nomina_36(totalCve36);
        nuevasClaves.setClave_nomina_39(totalCve39);
        nuevasClaves.setClave_nomina_41(totalCve41);
        nuevasClaves.setClave_nomina_42(totalCve42);
        nuevasClaves.setClave_nomina_44(totalCve44);
        nuevasClaves.setClave_nomina_45(totalCve45);
        nuevasClaves.setClave_nomina_46(totalCve46);
        nuevasClaves.setClave_nomina_207(totalCve207);
        nuevasClaves.setClave_nomina_217(totalCve217);
        nuevasClaves.setClave_nomina_222(totalCve222);
        nuevasClaves.setClave_nomina_231(totalCve231);
        nuevasClaves.setClave_nomina_237(totalCve237);
        nuevasClaves.setClave_nomina_246(totalCve246);
        nuevasClaves.setClave_nomina_247(totalCve247);
        nuevasClaves.setClave_nomina_248(totalCve248);
        nuevasClaves.setClave_nomina_249(totalCve249);
        nuevasClaves.setInc_02(totalCve1002);
        nuevasClaves.setInc_08(totalCve1008);
        nuevasClaves.setInc_21(totalInci21);
        nuevasClaves.setInc_27(totalInci27);
        nuevasClaves.setInc_101(totalInci101);
        nuevasClaves.setInc_210(totalInci1210);
        nuevasClaves.setInc_211(totalInci1211);
        nuevasClaves.setInc_212(totalInci1212);
        nuevasClaves.setInc_213(totalInci1213);
        nuevasClaves.setInc_214(totalInci1214);
        nuevasClaves.setInc_218(totalInci1218);
        // Guarda la instancia de Tmp_Sdi_Acum_Bimestre_Cves en la base de datos
        nuevasClaves = tmp_Sdi_Acum_Bimestre_CvesRepository.save(nuevasClaves);
        //Se asigna el id recién generado a la tabla principal
        nuevoTmpAcumBim.setTmp_Sdi_Acum_Bimestre_Cves(nuevasClaves);
        //Se guarda la instancia de tmpSdiAcumBim en la base de datos con las claves foráneas generadas
        return tmp_Sdi_Acum_BimRepository.save(nuevoTmpAcumBim);
    }

    @Override
    public Tmp_Sdi_Calculos_Bim saveCalculosBimestre(
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
            Double clausula45Cct) {
        Tmp_Sdi_Calculos_Bim calculosBimestre = new Tmp_Sdi_Calculos_Bim();
        //Se crea una instancia de la clave correspondiente al id del bimestre a generar
        Cat_Bimestres_Sdi catBimestre = new Cat_Bimestres_Sdi();
        catBimestre.setId_bimestre(idBimestre);
        calculosBimestre.setCat_Bimestres_Sdi(catBimestre);
        calculosBimestre.setTrabajador_id(idTrabajador);
        calculosBimestre.setDias_prima_vac_antig(1);
        calculosBimestre.setAnios_antig(1);
        calculosBimestre.setVales_pag(1.0);
        calculosBimestre.setQuinquenio_pag(1.0);
        calculosBimestre.setIncentivo(1.0);
        calculosBimestre.setFecha_corte(LocalDate.now());
        calculosBimestre.setFecha_egreso(LocalDate.now());
        calculosBimestre.setCambio_nivel(0);
        //Se crea la instancia con los conceptos correspondientes al salario fijo
        Tmp_Sdi_Fijo_Calculos_Bim salarioFijo = new Tmp_Sdi_Fijo_Calculos_Bim();
        salarioFijo.setFijo_01(cve01);
        salarioFijo.setFijo_02(cve10);
        salarioFijo.setFijo_03(cve12);
        salarioFijo.setFijo_04(cve16);
        salarioFijo.setFijo_05(cve31);
        salarioFijo.setFijo_06(cve32);
        salarioFijo.setFijo_07(cve33);
        salarioFijo.setFijo_08(cve44);
        salarioFijo.setFijo_09(cve231);
        salarioFijo.setFijo_10(totalSalarioFijo);
        //Guarda la instancia de Tmp_Sdi_Fijo_Calculos_Bim en su propia tabla
        salarioFijo = tmp_Sdi_Fijo_Calculos_BimRepository.save(salarioFijo);
        //Pasa el id generado a la tabla principal
        calculosBimestre.setTmp_Sdi_Fijo_Calculos_Bim(salarioFijo);
        //Se crea la instancia con los conceptos correspondientes a salario variable
        Tmp_Sdi_Variable_Calculos_Bim salarioVariable = new Tmp_Sdi_Variable_Calculos_Bim();
        salarioVariable.setValor_01(diasPagados);
        salarioVariable.setValor_02(diasIncapacidad);
        salarioVariable.setValor_03(diasVacaciones);
        salarioVariable.setValor_04(estimuloVales);
        salarioVariable.setValor_05(cve01Var);
        salarioVariable.setValor_06(cve03Var);
        salarioVariable.setValor_07(cve05Var);
        salarioVariable.setValor_08(cve06Var);
        salarioVariable.setValor_09(cve07Var);
        salarioVariable.setValor_10(cve11Var);
        salarioVariable.setValor_11(cve14Var);
        salarioVariable.setValor_12(cve15Var);
        salarioVariable.setValor_13(cve23Var);
        salarioVariable.setValor_14(cve24Var);
        salarioVariable.setValor_15(cve41Var);
        salarioVariable.setValor_16(cve217Var);
        salarioVariable.setValor_17(cve222Var);
        salarioVariable.setValor_18(cve237Var);
        salarioVariable.setValor_19(cve249Var);
        salarioVariable.setValor_20(cve09Var);
        salarioVariable.setValor_21(cve21Var);
        salarioVariable.setValor_22(cve10Var);
        salarioVariable.setValor_23(cve17Var);
        salarioVariable.setValor_24(cve27Var);
        salarioVariable.setValor_25(difFondoAhorro);
        salarioVariable.setValor_26(difPrimaVacacional);
        salarioVariable.setValor_27(importeEstimuloPuntualidad);
        salarioVariable.setValor_28(importeEstimuloAsistencia);
        salarioVariable.setValor_29(sdiBaseCotizacion);
        salarioVariable.setValor_30(sdiVariable);
        salarioVariable.setValor_31(diasIncapacidadVar);
        salarioVariable.setValor_32(sdiMixto);
        salarioVariable.setValor_33(salarioMinimoDiario);
        salarioVariable.setValor_34(difSueldo);
        salarioVariable.setValor_35(diasPrimaVacacionalAntiguedad);
        salarioVariable.setValor_36(clausula45Cct);
        //Conceptos por validar
        salarioVariable.setValor_37(0.0);
        salarioVariable.setValor_38(0.0);
        salarioVariable.setValor_39(0.0);
        salarioVariable.setValor_40(0.0);
        //Se guarda la instancia en su propia tabla 
        salarioVariable = tmp_Sdi_Variable_Calculos_BimRepository.save(salarioVariable);
        //Se asigna el id generado en la tabla principal 
        calculosBimestre.setTmp_Sdi_Variable_Calculos_Bim(salarioVariable);
        return tmp_Sdi_Calculos_BimRepository.save(calculosBimestre);
    }

    //Guarda solo los conceptos fijos para los trabajadores de nuevo ingreso, se genera el id de la tabla variable por si se requieren modificaciones
    @Override
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
            Double sdiMixto) {
        Tmp_Sdi_Calculos_Bim calculosBimestre = new Tmp_Sdi_Calculos_Bim();
        //Se crea una instancia de la clave correspondiente al id del bimestre a generar
        Cat_Bimestres_Sdi catBimestre = new Cat_Bimestres_Sdi();
        catBimestre.setId_bimestre(idBimestre);
        calculosBimestre.setCat_Bimestres_Sdi(catBimestre);
        calculosBimestre.setTrabajador_id(idTrabajador);
        calculosBimestre.setDias_prima_vac_antig(1);
        calculosBimestre.setAnios_antig(1);
        calculosBimestre.setVales_pag(1.0);
        calculosBimestre.setQuinquenio_pag(1.0);
        calculosBimestre.setIncentivo(1.0);
        calculosBimestre.setFecha_corte(LocalDate.now());
        calculosBimestre.setFecha_egreso(LocalDate.now());
        calculosBimestre.setCambio_nivel(0);
        //Se crea la instancia con los conceptos correspondientes al salario fijo
        Tmp_Sdi_Fijo_Calculos_Bim salarioFijo = new Tmp_Sdi_Fijo_Calculos_Bim();
        salarioFijo.setFijo_01(cve01);
        salarioFijo.setFijo_02(cve10);
        salarioFijo.setFijo_03(cve12);
        salarioFijo.setFijo_04(cve16);
        salarioFijo.setFijo_05(cve31);
        salarioFijo.setFijo_06(cve32);
        salarioFijo.setFijo_07(cve33);
        salarioFijo.setFijo_08(cve44);
        salarioFijo.setFijo_09(cve231);
        salarioFijo.setFijo_10(totalSalarioFijo);
        //Guarda la instancia de Tmp_Sdi_Fijo_Calculos_Bim en su propia tabla
        salarioFijo = tmp_Sdi_Fijo_Calculos_BimRepository.save(salarioFijo);
        //Pasa el id generado a la tabla principal
        calculosBimestre.setTmp_Sdi_Fijo_Calculos_Bim(salarioFijo);
        //Se crea la instancia con los conceptos correspondientes a salario variable, no se asignan valores ya que solo noa interesa guardar el salario fijo
        Tmp_Sdi_Variable_Calculos_Bim salarioVariable = new Tmp_Sdi_Variable_Calculos_Bim();
        //Se guarda el salario mixto sin considerar los conceptos variables
        salarioVariable.setValor_32(sdiMixto);
        //Se guarda la instancia vacía en su propia tabla 
        salarioVariable = tmp_Sdi_Variable_Calculos_BimRepository.save(salarioVariable);
        //Se asigna el id generado en la tabla principal 
        calculosBimestre.setTmp_Sdi_Variable_Calculos_Bim(salarioVariable);
        return tmp_Sdi_Calculos_BimRepository.save(calculosBimestre);
    }

    //Se genera el cálculo de sdi para los trabajadores de las nóminas basándonos en la información obtenida de las tablas de pago
    @Override
    public void calcularSdiPorNomina(Integer nomina_id, List<Integer> periodos_considerados,
            Integer idBimestre, SseEmitter emitter) throws IOException {
        try {
            switch (nomina_id) {
                //Varios
                case 1:
                    generarSdiVariosBase(periodos_considerados, idBimestre, emitter);
                    break;
                //Transportacion
                case 2:
                    generarSdiTransportacion(periodos_considerados, idBimestre, emitter);
                    break;
                //Confianza
                case 3:
                    generarSdiConfianza(periodos_considerados, idBimestre, emitter);
                    break;
                //Estructura
                case 5:
                    generarSdiEstructura(periodos_considerados, idBimestre, emitter);
                    break;
                default:
                    throw new RuntimeException("Nómina inválida");
            }
        } catch (IOException e) {
            // Manejo de la excepción, por ejemplo, enviar un error al cliente o registrar el error
            emitter.completeWithError(e);
        }
    }

    //Se genera el cálculo para un trabajador de nuevo ingreso aplicando solo el salario fijo, ya que el trabajador no cuenta con información en tablas de pago
    @Override
    public void calcularSdiNuevoIngreso(Integer idTrabajador, Integer idBimestre) {
        try {
            // Obtener información del sueldo del trabajador
            final Trabajador_SueldoDTO infoSueldo = plazaRepository.findSueldosTrabajador(idTrabajador);
            // Obtener información de la plaza y nómina del trabajador
            final Trabajador_plaza infoPlaza = trabajadorPlazaRepository.findPlazaTrabajador(idTrabajador);
            //Obtener informacion del tipo de la nómina
            final Cat_Tipo_Nomina infoNomina = infoPlaza.getCat_Tipo_Nomina();
            final Integer idNomina = infoNomina.getId_tipo_nomina();
            // Obtener datos del trabajador
            final Trabajador datosTrab = trabajadorRepository.findById(idTrabajador)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trabajador no encontrado con id " + idTrabajador));
            final LocalDate fechaIngresoTrabajador = datosTrab.getFecha_ingreso();
            // Sólo relevante para la nómina de confianza
            final Integer prestacionesSiNo = datosTrab.getPrestaciones_si_no();
            // Obtener información personal del trabajador
            final Persona persona = datosTrab.getPersona();
            if (persona == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la información de la persona asociada al trabajador con id " + idTrabajador);
            }
            final Cat_Si_No siNoHijo = persona.getCat_hijos_si_no();
            final Cat_Genero genero = persona.getCat_genero();
            final String sexo = genero.getDesc_genero();
            final Boolean mujer = "Mujer".equalsIgnoreCase(sexo);
            final String hijoSiNo = siNoHijo.getDescripcion();
            final Boolean hijos = "Si".equalsIgnoreCase(hijoSiNo);
            // Obtener información del bimestre
            final Cat_Bimestres_Sdi informacionBimestre = cat_Bimestres_SdiRepository.findByIdBimestre(idBimestre);
            if (informacionBimestre == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Información del bimestre no encontrada para el id " + idBimestre);
            }
            final LocalDate fechaCorteBimestre = informacionBimestre.getFecha_fin();
            final Long diferenciaDiasEntreFechaIngresoyFechaCorte = calculaDiferenciaDias(fechaIngresoTrabajador, fechaCorteBimestre);
            // Sueldo Diario Tabulado
            final Double sueldoDiarioTabulado = infoSueldo.getSueldo_diario();
            // Inicializar variables
            Double diasAguinaldoTrabajador = calcularDiasAguinaldo(idNomina, prestacionesSiNo);
            Integer diasVacaciones = calcularDiasVacacionesInicial(diferenciaDiasEntreFechaIngresoyFechaCorte, idNomina, prestacionesSiNo);
            Double primaVacacionalDiaria = calcularPrimaVacacionalDiariaInicial(sueldoDiarioTabulado, diasVacaciones, idNomina, prestacionesSiNo);
            Double ayudaReyes = calculaAyudaReyesInicial(idNomina, prestacionesSiNo);
            Double ayudaMadres = calculaAyudaMadresInicial(hijos, mujer, idNomina, prestacionesSiNo);
            Double ayudaUtiles = calculaAyudaUtilesInicial(idNomina, prestacionesSiNo);
            Double ayudaTransporte = calcularAyudaTransporteInicial(idNomina, prestacionesSiNo);
            Double fondoAhorro7 = calcularFondoAhorroAl7PorcientoInicial(sueldoDiarioTabulado, idNomina, prestacionesSiNo);
            Double aguinaldoDiario = calcularAguinaldoDiario(sueldoDiarioTabulado, diasAguinaldoTrabajador);
            // Calcular el total del salario fijo
            Double totalSalarioFijo = calculaSalarioFijoTotal(
                    sueldoDiarioTabulado,
                    primaVacacionalDiaria,
                    aguinaldoDiario,
                    0.0, // No tendrán quinquenio si son de nuevo ingreso, independiente de sus prestaciones
                    ayudaReyes,
                    ayudaMadres,
                    ayudaUtiles,
                    ayudaTransporte,
                    fondoAhorro7
            );
            //Guarda los cálculos realizados, verificar si seguardan también los acumulados
            saveCalculosBimestreFijos(
                    idTrabajador,
                    idBimestre,
                    sueldoDiarioTabulado, //Clave 1 sueldo ordinario
                    primaVacacionalDiaria, //Estímulo vacaciones
                    aguinaldoDiario, //Importe de aguinaldo
                    0.0, //Quinquenio, como es nuevo ingreso se guarda en ceros independiente de sus prestaciones o nómina
                    ayudaUtiles, //Clave 31 Ayuda escolar
                    ayudaReyes, //Clave 32 Ayuda de reyes
                    ayudaTransporte, //Ckave 33 ayuda de transporte
                    ayudaMadres, //Clave 44 Estímulo 10 de Mayo
                    fondoAhorro7, //Clave 231 Fondo de ahorro grav emp. 7%
                    totalSalarioFijo, //Salario fijo
                    totalSalarioFijo); //SDI Mixto, se compone solo por el fijo sin considerar variables

        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al calcular el SDI de nuevo ingreso", e);
        }
    }

    @Override
    public List<SdiDTO> findBimestresSdi(Integer idNomina, Integer idBimestre) {
        return tmp_Sdi_Calculos_BimRepository.findBimestresSdi(idNomina, idBimestre);
    }

    @Override
    public Boolean existeBimestreCalculado(Integer idBimestre) {
        return tmp_Sdi_Calculos_BimRepository.existeBimestreCalculado(idBimestre);
    }

    @Override
    public Boolean existeBimestreCalculadoTrabajador(Integer idTrabajador, Integer idBimestre) {
        return tmp_Sdi_Calculos_BimRepository.existeBimestreCalculadoTrabajador(idTrabajador, idBimestre);
    }

    @Override
    public Boolean existeBimestreAcumulado(Integer idBimestre) {
        return tmp_Sdi_Acum_BimRepository.existeBimestreAcumulado(idBimestre);
    }

    @Override
    public List<Tmp_Sdi_Calculos_Bim> findDetalleCalculoBimTrab(Integer idBimestre, Integer idTrabajador) {
        return tmp_Sdi_Calculos_BimRepository.findDetalleCalculoBimTrab(idBimestre, idTrabajador);
    }

    @Override
    public String generarTxSuaModif(Integer idBimestre, Integer idNomina) {
        List<SdiSua_DTO> results = tmp_Sdi_Calculos_BimRepository.consultaTxtSuaModifSalario(idBimestre, idNomina);
        StringBuilder sb = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");
        for (SdiSua_DTO dto : results) {
            double salarioMixto = dto.getSalario_mixto() * 100;
            String salarioMixtoFormatted = decimalFormat.format(salarioMixto);
            // Concatenar los caracteres hasta la fecha del movimiento
            String firstPart = "01080836107" + dto.getNum_imss() + "07" + "01052024";
            // Insertar 8 espacios despues de la fecha
            String secondPart = String.format("%8s", "");
            // Se combinan todas las partes de la cadena
            sb.append(firstPart).append(secondPart).append(salarioMixtoFormatted).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String generarTxIdseModif(Integer idBimestre, Integer idNomina, Integer idTipoMovimiento) {
        List<Sdi_IdseDTO> results = tmp_Sdi_Calculos_BimRepository.consultaTxtIdseModifSalario(idBimestre, idNomina);
        Cat_Tipo_Mov_Idse_Sua tipoMov = cat_Tipo_Mov_Idse_SuaRepository.findById(idTipoMovimiento).get();
        String cveMovimiento = tipoMov.getClave();
        StringBuilder sb = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        for (Sdi_IdseDTO dto : results) {
            double salBaseCot = dto.getSal_base_cot() * 100;
            // Formatear cada componente según su longitud específica
            String cadenaFija = String.format(
                    "%-11s%-11s%-27s%-27s%-27s%-6s%-6s%-16s%-23s%-14s",
                    "01080836107", //Registro patronal
                    dto.getNum_imss(),
                    dto.getApellido_paterno() != null ? dto.getApellido_paterno().toUpperCase() : "", // Apellido Paterno (27 caracteres)
                    dto.getApellido_materno() != null ? dto.getApellido_materno().toUpperCase() : "", // Apellido Materno (27 caracteres)
                    dto.getNombre() != null ? dto.getNombre().toUpperCase() : "", // Nombre (27 caracteres)
                    decimalFormat.format(salBaseCot), // Salario mixto formateado (10 caracteres)
                    "",//Filler de6 espacios
                    "10001052024", // Fecha del movimiento (8 caracteres) con tipo trab, tipo salario, semana reducida
                    cveMovimiento + "00407" + dto.getNumero_expediente(), // Tipo de movimiento más la guía
                    dto.getRfc() + "9"
            );
            // Agregar la cadena formateada al StringBuilder
            sb.append(cadenaFija).append("\n");
        }
        return sb.toString();
    }

    @Transactional
    @Override
    public void borrarAcumuladosBimestre(Integer bimesterId) {
        try {
            tmp_Sdi_Acum_BimRepository.callDeleteAcumSdiBimester(bimesterId);
        } catch (Exception e) {
            throw new RuntimeException("Error al ejecutar el procedimiento almacenado", e);
        }
    }

    @Transactional
    @Override
    public void borrarCalculosBimestre(Integer bimesterId) {
        try {
            tmp_Sdi_Calculos_BimRepository.callDeleteCalculationSdiBimester(bimesterId);
        } catch (Exception e) {
            throw new RuntimeException("Error al ejecutar el procedimiento almacenado", e);
        }
    }

    //*****************************SDI VARIOS******************************************
    private void generarSdiVariosBase(List<Integer> periodos, Integer idBimestre, SseEmitter emitter) throws IOException {
        //Se obtiene la infomación del bimestre, aquí podríamos obtener la fecha de inicio, fin o la descripcion 
        Cat_Bimestres_Sdi informacionBimestre = cat_Bimestres_SdiRepository.findByIdBimestre(idBimestre);
        //Se obtiene el listado de las percepciones de los trabajadores según los periodos ingresados
        List<SDIVariosDTO> datosSdiVarios = obtenDatosVarios(periodos);
        //Se valida para no generar cálculos si el registro está vacío
        if (datosSdiVarios.isEmpty()) {
            throw new RuntimeException("No se encontró información de pago para los periodos de varios ingresados");
        }
        //Reportar el progreso al frontend
        int totalTrabajadores = datosSdiVarios.size();
        AtomicInteger progreso = new AtomicInteger(0);
        //Recorre y hace los cálculos para cada trabajador encontrado con sus totales según los peridos ingresados
        for (int i = 0; i < totalTrabajadores; i++) {
            SDIVariosDTO sdiVarios = datosSdiVarios.get(i);
            //Se determina si el trabjador tiene hijos o no
            String hijosSiNo = sdiVarios.getHijos_si_no();
            String genero = sdiVarios.getDesc_genero();
            Boolean hijos = null;
            Boolean mujer = (genero != null && genero.equalsIgnoreCase("Mujer"));
            if (hijosSiNo == null) {
                hijos = false;
            } else if (hijosSiNo.equals("No")) {
                hijos = false;
            } else if (hijosSiNo.equals("Si")) {
                hijos = true;
            }
            // Datos proporcionados para la prueba con el expediente 14137
            Double numeroDiasLaboradosCve1002 = sdiVarios.getTotal_cve_1002(); //32.20
            Double numeroDiasVacacionesCve1008 = sdiVarios.getTotal_cve_1008(); // 34.07
            Double sueldoDiarioTabulado = sdiVarios.getSueldo_diario(); //268.47
            Double sueldoOrdinarioCve1 = sdiVarios.getTotal_cve_1(); //8859.84
            Double diferenciaSueldoOrdinarioCve3 = sdiVarios.getTotal_cve_3();
            Double estimuloVacacionesCve10 = sdiVarios.getTotal_cve_10(); //15141.71
            Double estimuloPuntualidadCve17 = sdiVarios.getTotal_cve_17(); //1610.82
            Double diasAguinaldoEsteTrabajador = 60.0;
            Double primaDominicalCve11 = sdiVarios.getTotal_cve_11(); //0.0
            Double paseosLaboradosCve15 = sdiVarios.getTotal_cve_15(); //0.0
            Double estimuloApegoReglamentoCve23 = sdiVarios.getTotal_cve_23(); //1610.82
            Double bonoProductividadCalidadCve24 = sdiVarios.getTotal_cve_24(); //664.48
            Double ayudaAlumbramientoPaternidadCve41 = sdiVarios.getTotal_cve_41(); // 0.0
            Double ayudaFallecimientoCve217 = sdiVarios.getTotal_cve_217(); // 0.0
            Double horasTrabDiasFestivoCve222 = sdiVarios.getTotal_cve_222();
            LocalDate fechaIngresoTrabajador = sdiVarios.getFecha_ingreso();
            // Cálculos
            Double diasCotizados = calcularDiasDevengados(numeroDiasLaboradosCve1002, numeroDiasVacacionesCve1008, 1);
            Double difSueldoFormula = calcularDiferenciaSueldoPorFormula(sueldoOrdinarioCve1, sueldoDiarioTabulado, numeroDiasLaboradosCve1002);
            Double difFondoAhorroFormula = calcularDiferenciaFondoAhorroPorFormulaVariosCfza(0.0, difSueldoFormula);
            Double diferenciaPorFormulaEstimuloVacacionesVarios = calcularDiferenciaPorFormulaEstimuloVacaciones(estimuloVacacionesCve10, sueldoDiarioTabulado, numeroDiasVacacionesCve1008);

            Double importeBimestralQuinquenio = null;
            Integer diasVacacionesSinPrestaciones = null;
            Double totalSalarioFijo, totalSalarioVariable, salarioVariableDiario, subtotalSalarioBaseCotizacion, calculoEstimuloPuntualidad, sdi;

            LocalDate fechaIngreso = fechaIngresoTrabajador;
            LocalDate fechaCorteBimestre = informacionBimestre.getFecha_fin();
            Long diferenciaDiasEntreFechaIngresoyFechaCorte = calculaDiferenciaDias(fechaIngreso, fechaCorteBimestre);
            diasVacacionesSinPrestaciones = calcularDiasVacacionesVariosTranspCfzaSinPrestaciones(diferenciaDiasEntreFechaIngresoyFechaCorte);
            Double importeMensualQuinquenio = calcularQuinquenioMensual(diferenciaDiasEntreFechaIngresoyFechaCorte);
            importeBimestralQuinquenio = importeMensualQuinquenio * 2;

            Double primaVacacionalDiariaCfzaTranspVarios = calcularPrimaVacacionalDiariaCfzaTranspVarios(sueldoDiarioTabulado, diasVacacionesSinPrestaciones);
            Double aguinaldoDiario = calcularAguinaldoDiario(sueldoDiarioTabulado, diasAguinaldoEsteTrabajador);
            Double quinquenioDiario = calcularQuinquenioDiario(importeBimestralQuinquenio);
            Double ayudaReyes = calculaAyudaReyes();
            Double ayudaMadres = calculaAyudaMadres(hijos, mujer);
            Double ayudaUtiles = calculaAyudaUtiles();
            Double ayudaTransporte = calcularAyudaTransporte();
            Double fondoAhorro7 = calcularFondoAhorroAl7Porciento(sueldoDiarioTabulado);
            //Totales
            totalSalarioFijo = calculaSalarioFijoTotal(sueldoDiarioTabulado, primaVacacionalDiariaCfzaTranspVarios, aguinaldoDiario, quinquenioDiario, ayudaReyes, ayudaMadres, ayudaUtiles, ayudaTransporte, fondoAhorro7);
            totalSalarioVariable = calcularSalarioVariableTotalVarios(difSueldoFormula, difFondoAhorroFormula, diferenciaSueldoOrdinarioCve3, diferenciaPorFormulaEstimuloVacacionesVarios, primaDominicalCve11, paseosLaboradosCve15, estimuloApegoReglamentoCve23, bonoProductividadCalidadCve24, ayudaAlumbramientoPaternidadCve41, ayudaFallecimientoCve217, horasTrabDiasFestivoCve222);
            //Subtotales
            salarioVariableDiario = calcularSalarioVariableDiario(totalSalarioVariable, diasCotizados);
            subtotalSalarioBaseCotizacion = calcularSubtotalSbc(totalSalarioFijo, salarioVariableDiario);
            calculoEstimuloPuntualidad = calcularEstimuloPuntualidad(diasCotizados, estimuloPuntualidadCve17, subtotalSalarioBaseCotizacion);
            //Cálculo final del S.D.I
            sdi = subtotalSalarioBaseCotizacion + calculoEstimuloPuntualidad;
            // Imprime resultados y guarda los datos en la tabla Tmp_Sdi_Acum_Bim para todos los trabajadores de la nómina
            saveTmpAcumBim(sdiVarios.getId_trabajador(),
                    idBimestre, /*id del bimestre*/
                    LocalDate.now(), /*Fecha egreso*/
                    sdiVarios.getTotal_cve_1(),
                    sdiVarios.getTotal_cve_3(),
                    sdiVarios.getTotal_cve_4(),
                    0.0, /*Clave 5*/
                    0.0, /*Clave 6*/
                    0.0, /*Clave 7*/
                    sdiVarios.getTotal_cve_9(),
                    sdiVarios.getTotal_cve_10(),
                    sdiVarios.getTotal_cve_11(),
                    0.0, /*Clave 12*/
                    0.0, /*Clave 14*/
                    sdiVarios.getTotal_cve_15(),
                    sdiVarios.getTotal_cve_16(),
                    sdiVarios.getTotal_cve_17(),
                    sdiVarios.getTotal_cve_18(),
                    0.0, /*Clave 19*/
                    0.0, /*Clave 21*/
                    0.0, /*Clave 22*/
                    sdiVarios.getTotal_cve_23(),
                    sdiVarios.getTotal_cve_24(),
                    0.0, /*Clave 26*/
                    0.0, /*Clave 27*/
                    0.0, /*Clave 28*/
                    sdiVarios.getTotal_cve_30(),
                    0.0, /*Clave 31*/
                    0.0, /*Clave 32*/
                    sdiVarios.getTotal_cve_33(),
                    0.0, /*Clave 36*/
                    0.0, /*Clave 39*/
                    sdiVarios.getTotal_cve_41(),
                    sdiVarios.getTotal_cve_42(),
                    0.0, /*Clave 44*/
                    0.0, /*Clave 45*/
                    0.0, /*Clave 46*/
                    sdiVarios.getTotal_cve_207(),
                    sdiVarios.getTotal_cve_217(),
                    sdiVarios.getTotal_cve_222(),
                    sdiVarios.getTotal_cve_231(),
                    0.0, /*Clave 237*/
                    0.0, /*Clave 246*/
                    0.0, /*Clave 247*/
                    0.0, /*Clave 248*/
                    0.0, /*Clave 249*/
                    numeroDiasLaboradosCve1002,
                    0.0, /*Clave Inci 21*/
                    0.0, /*Clave Inci 27*/
                    0.0, /*Clave Inci 101*/
                    sdiVarios.getTotal_cve_1008(),
                    sdiVarios.getTotal_cve_1210(),
                    sdiVarios.getTotal_cve_1211(),
                    sdiVarios.getTotal_cve_1212(),
                    sdiVarios.getTotal_cve_1213(),
                    0.0, /*Clave Inci 1214*/
                    0.0 /*Clave Inci 1218*/);
            //Guarda los cálculos realizados
            saveCalculosBimestre(sdiVarios.getId_trabajador(),
                    idBimestre,
                    /*Desglose del cálculo fijo*/
                    sueldoDiarioTabulado,
                    primaVacacionalDiariaCfzaTranspVarios,
                    aguinaldoDiario,
                    quinquenioDiario,
                    ayudaUtiles,
                    ayudaReyes,
                    ayudaTransporte,
                    ayudaMadres,
                    fondoAhorro7,
                    totalSalarioFijo,
                    /*Desglose del cálculo variable*/
                    numeroDiasLaboradosCve1002,
                    0.0, //Dias incapacidad
                    0.0, //Dias vacaciones
                    0.0, //Estimulo vales
                    sueldoOrdinarioCve1,
                    diferenciaSueldoOrdinarioCve3,
                    0.0, //Numero de horas extras triples
                    0.0, //Permiso retribuido
                    0.0, //Descansos laborados para confianza
                    primaDominicalCve11,
                    0.0, //Diferencia de jornada para transportación solo
                    paseosLaboradosCve15,
                    estimuloApegoReglamentoCve23,
                    bonoProductividadCalidadCve24,
                    ayudaAlumbramientoPaternidadCve41,
                    ayudaFallecimientoCve217,
                    horasTrabDiasFestivoCve222,
                    0.0,//Día adicional
                    0.0, //Vales de fin de año 
                    0.0, //Vacaciones
                    0.0, //Salario de vacaciones
                    estimuloVacacionesCve10,
                    estimuloPuntualidadCve17,
                    0.0, //Estímulo puntualidad transportación
                    difFondoAhorroFormula,
                    diferenciaPorFormulaEstimuloVacacionesVarios,
                    calculoEstimuloPuntualidad, // Importe de estímulo puntualidad
                    0.0, //importe estímulo asistencia
                    totalSalarioVariable, //SDI base cotización
                    salarioVariableDiario, //SDI Variable
                    0.0, //Dias incapacidad variable
                    sdi, //sdimixto
                    0.0, //Salario mínimo diario
                    difSueldoFormula, //diferencia de sueldo
                    new Double(diasVacacionesSinPrestaciones),
                    0.0 //Clausula 77 Cct
            );
            int progresoActual = (i + 1) * 100 / totalTrabajadores;
            progreso.set(progresoActual);
            emitter.send(progresoActual);
//            System.out.println("El total del salario fijo del trabajador calculado es " + totalSalarioFijo);
//            System.out.println("El total del salario variable del trabajador calculado es " + totalSalarioVariable);
//            System.out.println("El salario variable diario es " + salarioVariableDiario);
//            System.out.println("El subtotal del salario base de cotización es " + subtotalSalarioBaseCotizacion);
//            System.out.println("El adicional de puntualidad solo se sumará si es positivo " + calculoEstimuloPuntualidad);
//            System.out.println("El valor final calculado para el sdi del trabajador es " + sdi);
        }
        emitter.complete();
    }

    //*****************************SDI TRANSPORTACION******************************************
    private void generarSdiTransportacion(List<Integer> periodos, Integer idBimestre, SseEmitter emitter) throws IOException {
        //Se obtiene la infomación del bimestre, aquí podríamos obtener la fecha de inicio, fin o la descripcion 
        Cat_Bimestres_Sdi informacionBimestre = cat_Bimestres_SdiRepository.findByIdBimestre(idBimestre);
        // Llama al método obtenDatosTransportacion para recuperar los datos
        List<SDITransportacionDTO> datosSdiTransportacion = obtenDatosTrasnportacion(periodos);
        //Se valida para no generar cálculos si el registro está vacío
        if (datosSdiTransportacion.isEmpty()) {
            throw new RuntimeException("No se encontró información de pago para los periodos de transportación ingresados");
        }
        //Reportar el progreso al frontend
        int totalTrabajadores = datosSdiTransportacion.size();
        AtomicInteger progreso = new AtomicInteger(0);
        //La prueba de cálculo se realizará con el expediente 16258
        //Recorre y hace los cálculos para cada trabajador encontrado con sus totales según los peridos ingresados
        for (int i = 0; i < totalTrabajadores; i++) {
            SDITransportacionDTO sdiTransportacion = datosSdiTransportacion.get(i);
            //Se determina si el trabjador tiene hijos o no
            Boolean hijos = "Si".equals(sdiTransportacion.getHijos_si_no());
            String genero = sdiTransportacion.getDesc_genero();
            //Se manda true si se trata de una mujer
            Boolean mujer = (genero != null && genero.equalsIgnoreCase("Mujer"));
            //Resto de variables para cálculo
            Double sueldoOrdinarioCve1 = sdiTransportacion.getTotal_cve_1();//11929.92;
            Double sueldoDiarioTabulado = sdiTransportacion.getSueldo_diario();//277.43;
            Double numDiasLaboradosCve1002 = sdiTransportacion.getTotal_cve_1002();//40.60;
            Double diferenciaSueldoOrdinarioCve3 = sdiTransportacion.getTotal_cve_3();//2343.06;
            Double estimuloVacacionesCve10 = sdiTransportacion.getTotal_cve_10();//8396.57;
            Double numDiasVacacionesCve1008 = sdiTransportacion.getTotal_cve_1008();//16.0;
            Double primaDominicalCve11 = sdiTransportacion.getTotal_cve_11();//0.0;
            Double difJornadaCve14 = sdiTransportacion.getTotal_cve_14();//3565.10;
            Double paseosLaboradosCve15 = sdiTransportacion.getTotal_cve_15();//0.0;
            Double estimuloApegoReglamentoCve23 = sdiTransportacion.getTotal_cve_23();//1664.58;
            Double bonoProdCalidadCve24 = sdiTransportacion.getTotal_cve_24();//1337.86;
            Double alumbramientoPaternidadCve41 = sdiTransportacion.getTotal_cve_41();//0.0;
            Double pagoClausula77CctCve45 = sdiTransportacion.getTotal_cve_45();//0.0;
            Double ayudaFallecimientoCve217 = sdiTransportacion.getTotal_cve_217();//0.0;
            Double horasTrabDiasFestivoCve222 = sdiTransportacion.getTotal_cve_222();//1756.48;
            Double diasAguinaldoEsteTrabajador = 60.0;
            Double estimuloPuntualidadCve17 = sdiTransportacion.getTotal_cve_17();//1664.58;
            Double estimuloPuntualidadTranspCve27 = sdiTransportacion.getTotal_cve_27();//1034.42;
            LocalDate fechaIngresoTrabajador = sdiTransportacion.getFecha_ingreso();

            //Calculadas 
            Double diasCotizados = calcularDiasDevengados(numDiasLaboradosCve1002, numDiasVacacionesCve1008, 2);
            Double difSueldoOrdinarioFormula = calcularDiferenciaSueldoPorFormula(sueldoOrdinarioCve1, sueldoDiarioTabulado, numDiasLaboradosCve1002);
            Double difFondoAhorroFormula = calcularDiferenciaFondoAhorroPorFormulaTransportacion(difSueldoOrdinarioFormula, diferenciaSueldoOrdinarioCve3, difJornadaCve14, pagoClausula77CctCve45);
            Double difEstimuloVacaciones = calcularDiferenciaPorFormulaEstimuloVacaciones(estimuloVacacionesCve10, sueldoDiarioTabulado, numDiasVacacionesCve1008);
            Double importeBimestralQuinquenio = null;
            Integer diasVacacionesSinPrestaciones = null;
            Double totalSalarioFijo, totalSalarioVariable, salarioVariableDiario, subtotalSalarioBaseCotizacion, calculoEstimuloPuntualidad, sdi;

            LocalDate fechaIngreso = fechaIngresoTrabajador;
            LocalDate fechaCorteBimestre = informacionBimestre.getFecha_fin();
            Long diferenciaDiasEntreFechaIngresoyFechaCorte = calculaDiferenciaDias(fechaIngreso, fechaCorteBimestre);
            diasVacacionesSinPrestaciones = calcularDiasVacacionesVariosTranspCfzaSinPrestaciones(diferenciaDiasEntreFechaIngresoyFechaCorte);
            Double importeMensualQuinquenio = calcularQuinquenioMensual(diferenciaDiasEntreFechaIngresoyFechaCorte);
            importeBimestralQuinquenio = importeMensualQuinquenio * 2;

            Double primaVacacionalDiariaTransp = calcularPrimaVacacionalDiariaCfzaTranspVarios(sueldoDiarioTabulado, diasVacacionesSinPrestaciones);
            Double aguinaldoDiario = calcularAguinaldoDiario(sueldoDiarioTabulado, diasAguinaldoEsteTrabajador);
            Double quinquenioDiario = calcularQuinquenioDiario(importeBimestralQuinquenio);
            Double ayudaReyes = calculaAyudaReyes();
            Double ayudaMadres = calculaAyudaMadres(hijos, mujer);
            Double ayudaUtiles = calculaAyudaUtiles();
            Double ayudaTransporte = calcularAyudaTransporte();
            Double fondoAhorro7 = calcularFondoAhorroAl7Porciento(sueldoDiarioTabulado);
            totalSalarioFijo = calculaSalarioFijoTotal(sueldoDiarioTabulado, primaVacacionalDiariaTransp, aguinaldoDiario, quinquenioDiario, ayudaReyes, ayudaMadres, ayudaUtiles, ayudaTransporte, fondoAhorro7);
            totalSalarioVariable = calcularSalarioVariableTotalTransportacion(difSueldoOrdinarioFormula, difFondoAhorroFormula, diferenciaSueldoOrdinarioCve3, difEstimuloVacaciones, primaDominicalCve11, difJornadaCve14, paseosLaboradosCve15, estimuloApegoReglamentoCve23, bonoProdCalidadCve24, alumbramientoPaternidadCve41, pagoClausula77CctCve45, ayudaFallecimientoCve217, horasTrabDiasFestivoCve222);
            salarioVariableDiario = calcularSalarioVariableDiario(totalSalarioVariable, diasCotizados);
            subtotalSalarioBaseCotizacion = calcularSubtotalSbc(totalSalarioFijo, salarioVariableDiario);
            calculoEstimuloPuntualidad = calcularEstimuloPuntualidadTransportacion(diasCotizados, estimuloPuntualidadCve17, estimuloPuntualidadTranspCve27, subtotalSalarioBaseCotizacion);
            sdi = subtotalSalarioBaseCotizacion + calculoEstimuloPuntualidad;
            // Imprime resultados y guarda los datos en la tabla Tmp_Sdi_Acum_Bim para todos los trabajadores de la nómina
            saveTmpAcumBim(sdiTransportacion.getId_trabajador(),
                    idBimestre, /*id del bimestre*/
                    LocalDate.now(), /*Fecha egreso*/
                    sdiTransportacion.getTotal_cve_1(),
                    sdiTransportacion.getTotal_cve_3(),
                    sdiTransportacion.getTotal_cve_4(),
                    0.0, /*Clave 5*/
                    0.0, /*Clave 6*/
                    0.0, /*Clave 7*/
                    sdiTransportacion.getTotal_cve_9(),
                    sdiTransportacion.getTotal_cve_10(),
                    sdiTransportacion.getTotal_cve_11(),
                    sdiTransportacion.getTotal_cve_12(), /*Clave 12*/
                    sdiTransportacion.getTotal_cve_14(), /*Clave 14*/
                    sdiTransportacion.getTotal_cve_15(),
                    sdiTransportacion.getTotal_cve_16(),
                    sdiTransportacion.getTotal_cve_17(),
                    sdiTransportacion.getTotal_cve_18(),
                    sdiTransportacion.getTotal_cve_19(), /*Clave 19*/
                    0.0, /*Clave 21*/
                    0.0, /*Clave 22*/
                    sdiTransportacion.getTotal_cve_23(),
                    sdiTransportacion.getTotal_cve_24(),
                    sdiTransportacion.getTotal_cve_26(), /*Clave 26*/
                    sdiTransportacion.getTotal_cve_27(), /*Clave 27*/
                    0.0, /*Clave 28*/
                    sdiTransportacion.getTotal_cve_30(),
                    0.0, /*Clave 31*/
                    sdiTransportacion.getTotal_cve_32(), /*Clave 32*/
                    sdiTransportacion.getTotal_cve_33(),
                    0.0, /*Clave 36*/
                    sdiTransportacion.getTotal_cve_39(), /*Clave 39*/
                    sdiTransportacion.getTotal_cve_41(),
                    sdiTransportacion.getTotal_cve_42(),
                    0.0, /*Clave 44*/
                    sdiTransportacion.getTotal_cve_45(), /*Clave 45*/
                    0.0, /*Clave 46*/
                    sdiTransportacion.getTotal_cve_207(),
                    sdiTransportacion.getTotal_cve_217(),
                    sdiTransportacion.getTotal_cve_222(),
                    sdiTransportacion.getTotal_cve_231(),
                    0.0, /*Clave 237*/
                    0.0, /*Clave 246*/
                    0.0, /*Clave 247*/
                    0.0, /*Clave 248*/
                    0.0, /*Clave 249*/
                    sdiTransportacion.getTotal_cve_1002(),
                    0.0, /*Clave Inci 21*/
                    0.0, /*Clave Inci 27*/
                    0.0, /*Clave Inci 101*/
                    sdiTransportacion.getTotal_cve_1008(),
                    sdiTransportacion.getTotal_cve_1210(),
                    sdiTransportacion.getTotal_cve_1211(),
                    sdiTransportacion.getTotal_cve_1212(),
                    sdiTransportacion.getTotal_cve_1213(),
                    0.0, /*Clave Inci 1214*/
                    0.0 /*Clave Inci 1218*/);

            //Guarda los cálculos realizados
            saveCalculosBimestre(sdiTransportacion.getId_trabajador(),
                    idBimestre,
                    /*Desglose del cálculo fijo*/
                    sueldoDiarioTabulado,
                    primaVacacionalDiariaTransp,
                    aguinaldoDiario,
                    quinquenioDiario,
                    ayudaUtiles,
                    ayudaReyes,
                    ayudaTransporte,
                    ayudaMadres,
                    fondoAhorro7,
                    totalSalarioFijo,
                    /*Desglose del cálculo variable*/
                    numDiasLaboradosCve1002,
                    0.0, //Dias incapacidad
                    0.0, //Dias vacaciones
                    sdiTransportacion.getTotal_cve_30(), //Estimulo vales
                    sueldoOrdinarioCve1,
                    diferenciaSueldoOrdinarioCve3,
                    0.0, //Numero de horas extras triples
                    0.0, //Permiso retribuido
                    0.0, //Descansos laborados para confianza
                    primaDominicalCve11,
                    difJornadaCve14,
                    paseosLaboradosCve15,
                    estimuloApegoReglamentoCve23,
                    bonoProdCalidadCve24,
                    alumbramientoPaternidadCve41,
                    ayudaFallecimientoCve217,
                    horasTrabDiasFestivoCve222,
                    0.0,//Día adicional
                    0.0, //Vales de fin de año 
                    0.0, //Vacaciones
                    0.0, //Salario de vacaciones
                    estimuloVacacionesCve10,
                    estimuloPuntualidadCve17,
                    estimuloPuntualidadTranspCve27, //Estímulo puntualidad transportación
                    difFondoAhorroFormula,
                    difEstimuloVacaciones,
                    calculoEstimuloPuntualidad, // Importe de estímulo puntualidad
                    0.0, //importe estímulo asistencia
                    totalSalarioVariable, //Sdi base cotización
                    salarioVariableDiario, //SDI Variable
                    0.0, //Dias incapacidad variable
                    sdi, //sdimixto
                    0.0, //Salario mínimo diario
                    difSueldoOrdinarioFormula, //diferencia de sueldo
                    new Double(diasVacacionesSinPrestaciones),
                    pagoClausula77CctCve45 //Clausula 77 cct
            );
            int progresoActual = (i + 1) * 100 / totalTrabajadores;
            progreso.set(progresoActual);
            emitter.send(progresoActual);
//            System.out.println("El total del salario fijo del trabajador calculado es " + totalSalarioFijo);
//            System.out.println("El total del salario variable del trabajador calculado es " + totalSalarioVariable);
//            System.out.println("El salario variable diario es " + salarioVariableDiario);
//            System.out.println("El subtotal del salario base de cotización es " + subtotalSalarioBaseCotizacion);
//            System.out.println("El adicional de puntualidad solo se sumará si es positivo " + calculoEstimuloPuntualidad);
//            System.out.println("El valor final calculado para el sdi del trabajador es " + sdi);
        }
        emitter.complete();
    }

    //*****************************SDI CONFIANZA******************************************
    private void generarSdiConfianza(List<Integer> periodos, Integer idBimestre, SseEmitter emitter) throws IOException {
        //Se obtiene la infomación del bimestre, aquí podríamos obtener la fecha de inicio, fin o la descripcion 
        Cat_Bimestres_Sdi informacionBimestre = cat_Bimestres_SdiRepository.findByIdBimestre(idBimestre);
        //Se buscan los conceptos totales de los trabajadores según los bimestres ingresados
        List<SDIConfianzaDTO> datosSdiConfianza = obtenDatosConfianza(periodos);
        //Se valida para no generar cálculos si el registro está vacío
        if (datosSdiConfianza.isEmpty()) {
            throw new RuntimeException("No se encontró información de pago para los periodos de confianza ingresados");
        }
        //Reportar el progreso al frontend
        int totalTrabajadores = datosSdiConfianza.size();
        AtomicInteger progreso = new AtomicInteger(0);
        //Recorre y hace los cálculos para cada trabajador encontrado con sus totales según los peridos ingresados
        for (int i = 0; i < totalTrabajadores; i++) {
            SDIConfianzaDTO sdiConfianza = datosSdiConfianza.get(i);
            Boolean prestacionesSiNo = sdiConfianza.getPrestaciones_si_no() == 1;
            Boolean hijos = sdiConfianza.getHijos_si_no() == 1;
            String genero = sdiConfianza.getDesc_genero();
            Boolean mujer = (genero != null && genero.equalsIgnoreCase("Mujer"));
            //Resto de variables para cálculo
            Double sueldoOrdinarioCve1 = sdiConfianza.getTotal_cve_1();//17406.40;
            Double sueldoDiarioTabulado = sdiConfianza.getSueldo_diario();//395.60;
            Double numDiasLaborados = sdiConfianza.getTotal_cve_2();//44.0;
            Double descansosLaboradosCfzaCve7 = sdiConfianza.getTotal_cve_7();//0.0;
            Double descansosPaseosCve1101 = sdiConfianza.getTotal_cve_1101();//0.0;
            Double estimuloVacacionesCve10 = sdiConfianza.getTotal_cve_10();//0.0;
            Double primaDominicalCve11 = sdiConfianza.getTotal_cve_11();//0.0;
            Double estimuloApegoReglamentoCve23 = sdiConfianza.getTotal_cve_23();//0.0;
            Double bonoProductividadCalidadCve24 = sdiConfianza.getTotal_cve_24();//0.0;
            Double horasTrabDiaFestivoCve222 = sdiConfianza.getTotal_cve_222();//0.0;
            Double estimuloPuntualidadCve17 = sdiConfianza.getTotal_cve_17();//0.0;
            Double diasPermisoRetribuidoCve1002 = sdiConfianza.getTotal_cve_1002();//0.0;
            Double diasSalarioVacacionesCve1021 = sdiConfianza.getTotal_cve_1021();//16.0;
            LocalDate fechaIngresoTrabajador = sdiConfianza.getFecha_ingreso();
            Double diasCotizados = calcularDiasDevengadosConfianza(numDiasLaborados, diasPermisoRetribuidoCve1002, diasSalarioVacacionesCve1021);
            Double difSueldoOrdinarioFormula = calcularDiferenciaSueldoPorFormula(sueldoOrdinarioCve1, sueldoDiarioTabulado, numDiasLaborados);
            Double importeBimestralQuinquenio = null;
            Integer diasVacacionesSinPrestaciones = null;
            Integer diasVacacionesPrestaciones = null;

            LocalDate fechaIngreso = fechaIngresoTrabajador;
            LocalDate fechaCorteBimestre = informacionBimestre.getFecha_fin();
            Long diferenciaDiasEntreFechaIngresoyFechaCorte = calculaDiferenciaDias(fechaIngreso, fechaCorteBimestre);
            //Días de vacaciones sin prestaciones
            diasVacacionesSinPrestaciones = calcularDiasVacacionesVariosTranspCfzaSinPrestaciones(diferenciaDiasEntreFechaIngresoyFechaCorte);
            //Días de vacaciones con prestaciones
            diasVacacionesPrestaciones = calcularDiasVacacionesEstructuraCfzaPrestaciones(diferenciaDiasEntreFechaIngresoyFechaCorte);
            Double importeMensualQuinquenio = calcularQuinquenioMensual(diferenciaDiasEntreFechaIngresoyFechaCorte);
            importeBimestralQuinquenio = importeMensualQuinquenio * 2;

            Double diferenciaPorFormulaEstimuloVacacionesCfza;
            Double primaVacacionalDiaria = null;
            Double aguinaldoDiario = null;
            Double quinquenioDiario = null;
            Double ayudaReyes = null;
            Double ayudaMadres = null;
            Double ayudaUtiles = null;
            Double ayudaTransporte = null;
            Double fondoAhorro7 = null;
            Integer diasVacacionesCalculo = null;
            //*****************SE VERIFICAN LOS CALCULOS ACORDE A LAS PRESTACIONES DEL TRABAJADOR******************
            if (prestacionesSiNo) {
                Double diasAguinaldoEsteTrabajador = 60.0;
                diferenciaPorFormulaEstimuloVacacionesCfza = calcularDiferenciaPorFormulaEstimuloVacacionesCfzaPrestaciones(sueldoDiarioTabulado, descansosPaseosCve1101);
                primaVacacionalDiaria = calcularPrimaVacacionalDiariaCfzaTranspVarios(sueldoDiarioTabulado, diasVacacionesSinPrestaciones);
                aguinaldoDiario = calcularAguinaldoDiario(sueldoDiarioTabulado, diasAguinaldoEsteTrabajador);
                quinquenioDiario = calcularQuinquenioDiario(importeBimestralQuinquenio);
                ayudaReyes = calculaAyudaReyes();
                ayudaMadres = calculaAyudaMadres(hijos, mujer);
                ayudaUtiles = calculaAyudaUtiles();
                ayudaTransporte = calcularAyudaTransporte();
                fondoAhorro7 = calcularFondoAhorroAl7Porciento(sueldoDiarioTabulado);
                //Para el guardado en calculos
                diasVacacionesCalculo = diasVacacionesPrestaciones;
            } else {
                Double diasAguinaldoEsteTrabajador = 40.0;
                diferenciaPorFormulaEstimuloVacacionesCfza = calcularDiferenciaPorFormulaEstimuloVacacionesCfzaSinPrestaciones(estimuloVacacionesCve10, sueldoDiarioTabulado, diasVacacionesPrestaciones);
                primaVacacionalDiaria = calcularPrimaVacacionalEstructuraCfzaSinPrestaciones(sueldoDiarioTabulado, diasVacacionesPrestaciones);
                aguinaldoDiario = calcularAguinaldoDiario(sueldoDiarioTabulado, diasAguinaldoEsteTrabajador);
                //Para el guardado en cálculos
                diasVacacionesCalculo = diasVacacionesSinPrestaciones;
                //Los trabajadores sin prestaciones no reciben las siguientes ayudas
                quinquenioDiario = 0.0;
                ayudaReyes = 0.0;
                ayudaMadres = 0.0;
                ayudaUtiles = 0.0;
                ayudaTransporte = 0.0;
                fondoAhorro7 = 0.0;
            }

            Double totalSalarioFijo, totalSalarioVariable, salarioVariableDiario, subtotalSalarioBaseCotizacion, calculoEstimuloPuntualidad, sdi;
            totalSalarioFijo = calculaSalarioFijoTotal(sueldoDiarioTabulado, primaVacacionalDiaria, aguinaldoDiario, quinquenioDiario, ayudaReyes, ayudaMadres, ayudaUtiles, ayudaTransporte, fondoAhorro7);
            totalSalarioVariable = calcularSalarioVariableTotalConfianza(difSueldoOrdinarioFormula, descansosLaboradosCfzaCve7, diferenciaPorFormulaEstimuloVacacionesCfza, primaDominicalCve11, estimuloApegoReglamentoCve23, bonoProductividadCalidadCve24, horasTrabDiaFestivoCve222);
            salarioVariableDiario = calcularSalarioVariableDiario(totalSalarioVariable, diasCotizados);
            subtotalSalarioBaseCotizacion = calcularSubtotalSbc(totalSalarioFijo, salarioVariableDiario);
            calculoEstimuloPuntualidad = calcularEstimuloPuntualidad(diasCotizados, estimuloPuntualidadCve17, subtotalSalarioBaseCotizacion);
            sdi = subtotalSalarioBaseCotizacion + calculoEstimuloPuntualidad;
            // Imprime resultados y guarda los datos en la tabla Tmp_Sdi_Acum_Bim para todos los trabajadores de la nómina
            saveTmpAcumBim(sdiConfianza.getId_trabajador(),
                    idBimestre, /*id del bimestre*/
                    LocalDate.now(), /*Fecha egreso*/
                    sdiConfianza.getTotal_cve_1(),
                    0.0, /*Clave 3*/
                    sdiConfianza.getTotal_cve_4(),
                    0.0, /*Clave 5*/
                    sdiConfianza.getTotal_cve_6(), /*Clave 6*/
                    sdiConfianza.getTotal_cve_7(), /*Clave 7*/
                    0.0, /*Clave 9*/
                    sdiConfianza.getTotal_cve_10(),
                    sdiConfianza.getTotal_cve_11(),
                    0.0, /*Clave 12*/
                    0.0, /*Clave 14*/
                    0.0, /*Clave 15*/
                    sdiConfianza.getTotal_cve_16(),
                    sdiConfianza.getTotal_cve_17(),
                    sdiConfianza.getTotal_cve_18(),
                    0.0, /*Clave 19*/
                    sdiConfianza.getTotal_cve_21(), /*Clave 21*/
                    0.0, /*Clave 22*/
                    sdiConfianza.getTotal_cve_23(),
                    sdiConfianza.getTotal_cve_24(),
                    sdiConfianza.getTotal_cve_26(), /*Clave 26*/
                    0.0, /*Clave 27*/
                    0.0, /*Clave 28*/
                    sdiConfianza.getTotal_cve_30(),
                    0.0, /*Clave 31*/
                    sdiConfianza.getTotal_cve_32(), /*Clave 32*/
                    sdiConfianza.getTotal_cve_33(),
                    0.0, /*Clave 36*/
                    0.0, /*Clave 39*/
                    0.0, /*Clave 41*/
                    0.0, /*Clave 42*/
                    0.0, /*Clave 44*/
                    0.0, /*Clave 45*/
                    0.0, /*Clave 46*/
                    sdiConfianza.getTotal_cve_207(),
                    0.0, /*Clave 217*/
                    sdiConfianza.getTotal_cve_222(),
                    sdiConfianza.getTotal_cve_231(),
                    0.0, /*Clave 237*/
                    0.0, /*Clave 246*/
                    0.0, /*Clave 247*/
                    0.0, /*Clave 248*/
                    0.0, /*Clave 249*/
                    sdiConfianza.getTotal_cve_1002(),
                    sdiConfianza.getTotal_cve_1021(), /*Clave Inci 21*/
                    0.0, /*Clave Inci 27*/
                    sdiConfianza.getTotal_cve_1101(), /*Clave Inci 101*/
                    0.0, /*Clave 1008*/
                    sdiConfianza.getTotal_cve_1210(),
                    sdiConfianza.getTotal_cve_1211(),
                    sdiConfianza.getTotal_cve_1212(),
                    sdiConfianza.getTotal_cve_1213(),
                    sdiConfianza.getTotal_cve_1214(), /*Clave Inci 1214*/
                    0.0 /*Clave Inci 1218*/);

            //Guarda los cálculos realizados
            saveCalculosBimestre(sdiConfianza.getId_trabajador(),
                    idBimestre,
                    /*Desglose del cálculo fijo*/
                    sueldoDiarioTabulado,
                    primaVacacionalDiaria,
                    aguinaldoDiario,
                    quinquenioDiario,
                    ayudaUtiles,
                    ayudaReyes,
                    ayudaTransporte,
                    ayudaMadres,
                    fondoAhorro7,
                    totalSalarioFijo,
                    /*Desglose del cálculo variable*/
                    diasCotizados,
                    0.0, //Dias incapacidad
                    0.0, //Dias vacaciones
                    0.0, //Estimulo vales
                    sueldoOrdinarioCve1,
                    0.0, //Diferencia sueldo ordinario clave 3
                    0.0, //Numero de horas extras triples
                    0.0, //Permiso retribuido
                    descansosLaboradosCfzaCve7, //Descansos laborados para confianza
                    primaDominicalCve11,
                    0.0, //Diferencia de jornada clave 14 solo transportación
                    0.0, //Clave 15 paseos laborados
                    estimuloApegoReglamentoCve23,
                    bonoProductividadCalidadCve24,
                    0.0, //Alumbramiento paternidad clave 41
                    0.0, //Clave 217 ayuda fallecimiento
                    horasTrabDiaFestivoCve222,
                    0.0,//Día adicional
                    0.0, //Vales de fin de año 
                    0.0, //Vacaciones
                    0.0, //Salario de vacaciones
                    estimuloVacacionesCve10,
                    estimuloPuntualidadCve17,
                    0.0, //Estímulo puntualidad transportación clave 27
                    0.0, //Diferencia de fondo de ahorro por fórmula
                    diferenciaPorFormulaEstimuloVacacionesCfza,
                    calculoEstimuloPuntualidad, // Importe de estímulo puntualidad
                    0.0, //importe estímulo asistencia
                    totalSalarioVariable, //Sdi base cotización
                    salarioVariableDiario, //Sdi variable
                    0.0, //Dias incapacidad variable
                    sdi, //sdimixto
                    0.0, //Salario mínimo diario
                    difSueldoOrdinarioFormula, //diferencia de sueldo
                    new Double(diasVacacionesCalculo),
                    0.0 //Clausula 77 cct
            );
            int progresoActual = (i + 1) * 100 / totalTrabajadores;
            progreso.set(progresoActual);
            emitter.send(progresoActual);
//            System.out.println("El total del salario fijo del trabajador calculado es " + totalSalarioFijo);
//            System.out.println("El total del salario variable del trabajador calculado es " + totalSalarioVariable);
//            System.out.println("El salario variable diario es " + salarioVariableDiario);
//            System.out.println("El subtotal del salario base de cotización es " + subtotalSalarioBaseCotizacion);
//            System.out.println("El adicional de puntualidad solo se sumará si es positivo " + calculoEstimuloPuntualidad);
//            System.out.println("El valor final calculado para el sdi del trabajador es " + sdi);
        }
        emitter.complete();
    }

    //*****************************SDI ESTRUCTURA******************************************
    private void generarSdiEstructura(List<Integer> periodos, Integer idBimestre, SseEmitter emitter) throws IOException {
        //Se obtiene la infomación del bimestre, aquí podríamos obtener la fecha de inicio, fin o la descripcion 
        Cat_Bimestres_Sdi informacionBimestre = cat_Bimestres_SdiRepository.findByIdBimestre(idBimestre);
        //Se obtienen los acumulados por bimestre según los periodos ingresados
        List<SDIEstructuraDTO> datosSdiEstructura = obtenDatosEstructura(periodos);
        //Se valida para no generar cálculos si el registro está vacío
        if (datosSdiEstructura.isEmpty()) {
            throw new RuntimeException("No se encontró información de pago para los periodos de funcionarios ingresados");
        }
        //Reportar el progreso al frontend
        int totalTrabajadores = datosSdiEstructura.size();
        AtomicInteger progreso = new AtomicInteger(0);
        //Recorre y hace los cálculos para cada trabajador encontrado con sus totales según los peridos ingresados
        for (int i = 0; i < totalTrabajadores; i++) {
            SDIEstructuraDTO sdiEstructura = datosSdiEstructura.get(i);
            //Prueba con expediente 16840
            Double sueldoDiarioTabulado = sdiEstructura.getSueldo_diario();//3177.57;
            Double diasAguinaldoFuncionarios = 40.0;
            LocalDate fechaIngresoTrabajador = sdiEstructura.getFecha_ingreso();
            Integer diasVacacionesPrestaciones = null;
            Double totalSalarioFijo, sdi;

            LocalDate fechaIngreso = fechaIngresoTrabajador;
            LocalDate fechaCorteBimestre = informacionBimestre.getFecha_fin();
            Long diferenciaDiasEntreFechaIngresoyFechaCorte = calculaDiferenciaDias(fechaIngreso, fechaCorteBimestre);
            diasVacacionesPrestaciones = calcularDiasVacacionesEstructuraCfzaPrestaciones(diferenciaDiasEntreFechaIngresoyFechaCorte);

            Double primaVacacionalEstructura = calcularPrimaVacacionalEstructuraCfzaSinPrestaciones(sueldoDiarioTabulado, diasVacacionesPrestaciones);
            Double aguinaldoDiario = calcularAguinaldoDiario(sueldoDiarioTabulado, diasAguinaldoFuncionarios);
            totalSalarioFijo = calculaSalarioFijoTotalEstructura(sueldoDiarioTabulado, primaVacacionalEstructura, aguinaldoDiario);
            sdi = validaUma(totalSalarioFijo);
            saveTmpAcumBim(sdiEstructura.getId_trabajador(),
                    idBimestre, /*id del bimestre*/
                    LocalDate.now(), /*Fecha egreso*/
                    sdiEstructura.getTotal_cve_1(),
                    0.0, /*Clave 3*/
                    0.0, /*Clave 4*/
                    0.0, /*Clave 5*/
                    0.0, /*Clave 6*/
                    0.0, /*Clave 7*/
                    0.0, /*Clave 9*/
                    sdiEstructura.getTotal_cve_10(),
                    0.0,/*Clave 11*/
                    0.0, /*Clave 12*/
                    0.0, /*Clave 14*/
                    0.0, /*Clave 15*/
                    0.0, /*Clave 16*/
                    0.0, /*Clave 17*/
                    sdiEstructura.getTotal_cve_18(),
                    0.0, /*Clave 19*/
                    sdiEstructura.getTotal_cve_21(), /*Clave 21*/
                    0.0, /*Clave 22*/
                    0.0, /*Clave 23*/
                    0.0, /*Clave 24*/
                    0.0, /*Clave 26*/
                    0.0, /*Clave 27*/
                    sdiEstructura.getTotal_cve_28(), /*Clave 28*/
                    0.0, /*Clave 30*/
                    0.0, /*Clave 31*/
                    0.0, /*Clave 32*/
                    0.0, /*Clave 33*/
                    0.0, /*Clave 36*/
                    0.0, /*Clave 39*/
                    0.0, /*Clave 41*/
                    0.0, /*Clave 42*/
                    0.0, /*Clave 44*/
                    0.0, /*Clave 45*/
                    0.0, /*Clave 46*/
                    0.0, /*Clave 207*/
                    0.0, /*Clave 217*/
                    0.0, /*Clave 222*/
                    0.0, /*Clave 231*/
                    0.0, /*Clave 237*/
                    0.0, /*Clave 246*/
                    0.0, /*Clave 247*/
                    0.0, /*Clave 248*/
                    0.0, /*Clave 249*/
                    sdiEstructura.getTotal_cve_1002(),
                    sdiEstructura.getTotal_cve_1021(), /*Clave Inci 21*/
                    0.0, /*Clave Inci 27*/
                    0.0, /*Clave Inci 101*/
                    0.0, /*Clave 1008*/
                    sdiEstructura.getTotal_cve_1210(),
                    sdiEstructura.getTotal_cve_1211(),
                    sdiEstructura.getTotal_cve_1212(),
                    0.0, /*Clave 213*/
                    0.0, /*Clave Inci 1214*/
                    0.0 /*Clave Inci 1218*/);
            saveCalculosBimestre(sdiEstructura.getId_trabajador(),
                    idBimestre,
                    /*Desglose del cálculo fijo*/
                    sueldoDiarioTabulado,
                    primaVacacionalEstructura,
                    aguinaldoDiario,
                    0.0, //Quinquenio
                    0.0, //Utiles
                    0.0, //Reyes
                    0.0, //Transporte
                    0.0, //Madres
                    0.0, //Fondo ahorro
                    totalSalarioFijo,
                    /*Desglose del cálculo variable*/
                    sdiEstructura.getTotal_cve_1002(),
                    0.0, //Dias incapacidad
                    0.0, //Dias vacaciones
                    0.0, //Estimulo vales
                    sdiEstructura.getTotal_cve_1(),
                    0.0, //Diferencia sueldo ordinario clave 3
                    0.0, //Numero de horas extras triples
                    0.0, //Permiso retribuido
                    0.0, //Descansos laborados para confianza
                    0.0, //Prima dominical
                    0.0, //Diferencia de jornada clave 14 solo transportación
                    0.0, //Clave 15 paseos laborados
                    0.0, //Apego al reglamento
                    0.0, //Bono productividad
                    0.0, //Alumbramiento paternidad clave 41
                    0.0, //Clave 217 ayuda fallecimiento
                    0.0, //Horas trabajadas en día festivo
                    0.0,//Día adicional
                    0.0, //Vales de fin de año 
                    0.0, //Vacaciones
                    0.0, //Salario de vacaciones
                    0.0, //Estimulo vacaciones clave 10
                    0.0, //Estimulo puntualidad clave 17
                    0.0, //Estímulo puntualidad transportación clave 27
                    0.0, //Diferencia de fondo de ahorro por fórmula
                    0.0, //Diferencia pro gormula vacaciones
                    0.0, // Importe de estímulo puntualidad
                    0.0, //importe estímulo asistencia
                    0.0, //Sdi base cotización
                    0.0, //Sdi variable
                    0.0, //Dias incapacidad variable
                    totalSalarioFijo, //sdimixto
                    0.0, //Salario mínimo diario
                    0.0, //diferencia de sueldo
                    new Double(diasVacacionesPrestaciones),
                    0.0
            );
            int progresoActual = (i + 1) * 100 / totalTrabajadores;
            progreso.set(progresoActual);
            emitter.send(progresoActual);
        }
        emitter.complete();
    }

    @Override
    public void exportarConceptosCalculados(HttpServletResponse response, Integer idNomina, Integer idBimestre) throws IOException {
        List<Calculos_SdiDTO> conceptosCalculados = tmp_Sdi_Calculos_BimRepository.consultaConceptosCalculadosNominaBim(idNomina, idBimestre);

        // Se valida para no generar cálculos si el registro está vacío
        if (conceptosCalculados.isEmpty()) {
            throw new RuntimeException("No se encontró información");
        }

        // Crear un workbook y una hoja
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Conceptos_Calculados_SDI");

        // Crear estilo de celda con fondo gris claro
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Negritas a los encabezados
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // Crear encabezados
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Numero Expediente",
            "Apellido Paterno",
            "Apellido Materno",
            "Nombre",
            "Sueldo Diario Tabulado",
            "Prima Vacacional Diaria",
            "Aguinaldo Diario",
            "Quinquenio Diario",
            "Ayuda Reyes",
            "Ayuda Madres",
            "Ayuda Útiles",
            "Ayuda Transporte",
            "Fondo de Ahorro 7%",
            "S.D.I. Fijo Calculado",
            "Diferencia de Sueldo Ordinario por Fórmula",
            "Diferencia de Fondo de Ahorro por Fórmula",
            "Diferencia por Fórmula Estímulo de Vacaciones",};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Escribir datos en la hoja
        int rowNum = 1;
        for (Calculos_SdiDTO calculos : conceptosCalculados) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(calculos.getNumero_expediente());
            row.createCell(1).setCellValue(calculos.getApellido_paterno());
            row.createCell(2).setCellValue(calculos.getApellido_materno());
            row.createCell(3).setCellValue(calculos.getNombre());
            row.createCell(4).setCellValue(redondear(calculos.getSueldoDiarioTabulado()));
            row.createCell(5).setCellValue(redondear(calculos.getEstimuloVacCalculado()));
            row.createCell(6).setCellValue(redondear(calculos.getImporteAguinCalculado()));
            row.createCell(7).setCellValue(redondear(calculos.getQuinquenioMensCalculado()));
            row.createCell(8).setCellValue(redondear(calculos.getAyudaReyesCalculado()));
            row.createCell(9).setCellValue(redondear(calculos.getEstim10MayoCalculado()));
            row.createCell(10).setCellValue(redondear(calculos.getAyudaEscolarCalculado()));
            row.createCell(11).setCellValue(redondear(calculos.getAyudaTranspCalculado()));
            row.createCell(12).setCellValue(redondear(calculos.getFondoAhorro7Calculado()));
            row.createCell(13).setCellValue(redondear(calculos.getSdiFijoCalculado()));
            row.createCell(14).setCellValue(redondear(calculos.getDifSueldoFormula()));
            row.createCell(15).setCellValue(redondear(calculos.getDifFondoAhorroFormula()));
            row.createCell(16).setCellValue(redondear(calculos.getDifEstimVacFormula()));
        }

        // Ajustar automáticamente el tamaño de las columnas
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Escribir el contenido del workbook en el output stream de HttpServletResponse
        try ( OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    private double redondear(Double valor) {
        if (valor == null) {
            return 0.0;
        }
        BigDecimal bd = BigDecimal.valueOf(valor);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void exportarCalculosObtenidosSdiVarios(HttpServletResponse response, List<Integer> periodos) throws IOException {
        List<SDIVariosDTO> datosSdiVarios = obtenDatosVarios(periodos);
        //Se valida para no generar cálculos si el registro está vacío
        if (datosSdiVarios.isEmpty()) {
            throw new RuntimeException("No se encontró información");
        }
        // Create a workbook and a sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Personal_Acumulados_SDI");
        // Crear estilo de celda con fondo gris claro
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //Negritas a los encabezados
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        // Create headers
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Expediente",
            "Apellido Paterno",
            "Apellido Materno",
            "Nombre", "NSS",
            "Dígito Vertificador",
            "RFC",
            "Género",
            "Hijos",
            "Sueldo Diario Tabulado",
            "Nomina",
            "Fecha Ingreso",
            "Sueldo Ordinario Clave 1",
            "Diferencia de sueldo ordinario Clave 3",
            "Horas extra dobles Clave 4",
            "Vacaciones Clave 9",
            "Estímulo de vacaciones Clave 10",
            "Prima Dominical Clave 11",
            "Paseos laborados Clave 15",
            "Quinquenio mensual Clave 16",
            "Estímulo por puntualidad Clave 17",
            "Subsidio I.M.S.S Clave 18",
            "Estímulo por apego al reglamento Clave 23",
            "Bono de productividad y calidad Clave 24",
            "Subsidio para el empleo Clave 26",
            "Ayuda canasta básica de alim. Vales de desp. Clave 30",
            "Vales despensa pensión Claves 76,326,327,328,329",
            "Ayuda de Transporte Clave 33",
            "Alumbramiento o paternidad Clave 41",
            "Devolucion abono INFONAVIT Clave 42",
            "Fondo de ahorro empresa 13% Clave 207",
            "Ayuda por fallecimiento Clave 217",
            "Horas trabajadas en día festivo Clave 222",
            "Fondo de Ahorro gra.emp. 7% Clave 231",
            "Numero de días laborados Clave 1002",
            "Número de días de vacaciones Clave 1008",
            "Descansos o paseos Clave 1101",
            "Días de Incapacidad por EG al 50% Clave 1210",
            "Días de Incapacidad por EG al 75% Clave 1211",
            "Días de incapacidad por EG al 100% Clave 1212",
            "Días de incapacidad por RT al 100% Clave 1213",
            "Días de Incapacidad por EG al 60% Clave 1220"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Aplicar estilo de celda al encabezado
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Aquí se formatea la fecha
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));

        // Write data to the sheet
        int rowNum = 1;
        for (SDIVariosDTO datoSdi : datosSdiVarios) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(datoSdi.getExpediente());
            row.createCell(1).setCellValue(datoSdi.getApellido_paterno());
            row.createCell(2).setCellValue(datoSdi.getApellido_materno());
            row.createCell(3).setCellValue(datoSdi.getNombre());
            row.createCell(4).setCellValue(datoSdi.getNum_imss_primeros_10());
            row.createCell(5).setCellValue(datoSdi.getNum_imss_digito_verificador());
            row.createCell(6).setCellValue(datoSdi.getRfc());
            row.createCell(7).setCellValue(datoSdi.getDesc_genero());
            row.createCell(8).setCellValue(datoSdi.getHijos_si_no());
            row.createCell(9).setCellValue(datoSdi.getSueldo_diario());
            row.createCell(10).setCellValue(datoSdi.getDesc_nomina());
            // Se crea así ya que apache poi no soporta datos de tipo local date time
            Cell fechaIngresoCell = row.createCell(11);
            if (datoSdi.getFecha_ingreso() != null) {
                fechaIngresoCell.setCellValue(java.sql.Date.valueOf(datoSdi.getFecha_ingreso()));
                fechaIngresoCell.setCellStyle(dateCellStyle);
            }
            row.createCell(12).setCellValue(datoSdi.getTotal_cve_1());
            row.createCell(13).setCellValue(datoSdi.getTotal_cve_3());
            row.createCell(14).setCellValue(datoSdi.getTotal_cve_4());
            row.createCell(15).setCellValue(datoSdi.getTotal_cve_9());
            row.createCell(16).setCellValue(datoSdi.getTotal_cve_10());
            row.createCell(17).setCellValue(datoSdi.getTotal_cve_11());
            row.createCell(18).setCellValue(datoSdi.getTotal_cve_15());
            row.createCell(19).setCellValue(datoSdi.getTotal_cve_16());
            row.createCell(20).setCellValue(datoSdi.getTotal_cve_17());
            row.createCell(21).setCellValue(datoSdi.getTotal_cve_18());
            row.createCell(22).setCellValue(datoSdi.getTotal_cve_23());
            row.createCell(23).setCellValue(datoSdi.getTotal_cve_24());
            row.createCell(24).setCellValue(datoSdi.getTotal_cve_26());
            row.createCell(25).setCellValue(datoSdi.getTotal_cve_30());
            row.createCell(26).setCellValue(datoSdi.getVales_despensa_pension_alim() != null ? datoSdi.getVales_despensa_pension_alim() : 0.0);
            row.createCell(27).setCellValue(datoSdi.getTotal_cve_33());
            row.createCell(28).setCellValue(datoSdi.getTotal_cve_41());
            row.createCell(29).setCellValue(datoSdi.getTotal_cve_42());
            row.createCell(30).setCellValue(datoSdi.getTotal_cve_207());
            row.createCell(31).setCellValue(datoSdi.getTotal_cve_217());
            row.createCell(32).setCellValue(datoSdi.getTotal_cve_222());
            row.createCell(33).setCellValue(datoSdi.getTotal_cve_231());
            row.createCell(34).setCellValue(datoSdi.getTotal_cve_1002());
            row.createCell(35).setCellValue(datoSdi.getTotal_cve_1008());
            row.createCell(36).setCellValue(datoSdi.getTotal_cve_1101());
            row.createCell(37).setCellValue(datoSdi.getTotal_cve_1210());
            row.createCell(38).setCellValue(datoSdi.getTotal_cve_1211());
            row.createCell(39).setCellValue(datoSdi.getTotal_cve_1212());
            row.createCell(40).setCellValue(datoSdi.getTotal_cve_1213());
            row.createCell(41).setCellValue(datoSdi.getTotal_cve_1220());
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
    public void exportarCalculosObtenidosSdiTransportacion(HttpServletResponse response, List<Integer> periodos) throws IOException {
        List<SDITransportacionDTO> datosSdiTransportacion = obtenDatosTrasnportacion(periodos);
        //Se valida para no generar cálculos si el registro está vacío
        if (datosSdiTransportacion.isEmpty()) {
            throw new RuntimeException("No se encontró información");
        }
        // Create a workbook and a sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Personal_Acumulados_SDI");
        // Crear estilo de celda con fondo gris claro
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //Negritas a los encabezados
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        // Create headers
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Expediente",
            "Apellido Paterno",
            "Apellido Materno",
            "Nombre",
            "NSS",
            "Dígito Vertificador",
            "RFC",
            "Género",
            "Hijos",
            "Sueldo Diario Tabulado",
            "Nomina",
            "Fecha Ingreso",
            "Sueldo Ordinario Clave 1",
            "Diferencia de sueldo ordinario Clave 3",
            "Horas extra dobles Clave 4",
            "Vacaciones Clave 9",
            "Estímulo de vacaciones Clave 10",
            "Prima Dominical Clave 11",
            "Importe de Aguinaldo 12",
            "Diferenca de Jornada Clave 14",
            "Paseos laborados Clave 15",
            "Quinquenio mensual Clave 16",
            "Estímulo por puntualidad Clave 17",
            "Subsidio I.M.S.S Clave 18",
            "Diferencia de tiempo extra Clave 19",
            "Estímulo por apego al reglamento Clave 23",
            "Bono de productividad y calidad Clave 24",
            "Subsidio para el empleo Clave 26",
            "Estímulo por puntualidad (Transportación) Clave 27",
            "Ayuda canasta básica de alim. Vales de desp. Clave 30",
            "Vales despensa pensión Claves 76,326,327,328,329",
            "Ayuda de Reyes Clave 32",
            "Ayuda de Transporte Clave 33",
            "Devolución descuento indebido Clave 39",
            "Alumbramiento o paternidad Clave 41",
            "Pago Cláusula 77 C.C.T. Clave 45",
            "Fondo de ahorro empresa 13% Clave 207",
            "Ayuda por fallecimiento Clave 217",
            "Horas trabajadas en día festivo Clave 222",
            "Fondo de Ahorro gra.emp. 7% Clave 231",
            "Numero de días laborados Clave 1002",
            "Número de días de vacaciones Clave 1008",
            "Descansos o paseos Clave 1101",
            "Días de Incapacidad por EG al 50% Clave 1210",
            "Días de Incapacidad por EG al 75% Clave 1211",
            "Días de incapacidad por EG al 100% Clave 1212",
            "Días de incapacidad por RT al 100% Clave 1213",
            "Días de Incapacidad por EG al 60% Clave 1220"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Aplicar estilo de celda al encabezado
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Aquí se formatea la fecha
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));

        // Write data to the sheet
        int rowNum = 1;
        for (SDITransportacionDTO datoSdi : datosSdiTransportacion) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(datoSdi.getExpediente());
            row.createCell(1).setCellValue(datoSdi.getApellido_paterno());
            row.createCell(2).setCellValue(datoSdi.getApellido_materno());
            row.createCell(3).setCellValue(datoSdi.getNombre());
            row.createCell(4).setCellValue(datoSdi.getNum_imss_primeros_10());
            row.createCell(5).setCellValue(datoSdi.getNum_imss_digito_verificador());
            row.createCell(6).setCellValue(datoSdi.getRfc());
            row.createCell(7).setCellValue(datoSdi.getDesc_genero());
            row.createCell(8).setCellValue(datoSdi.getHijos_si_no());
            row.createCell(9).setCellValue(datoSdi.getSueldo_diario());
            row.createCell(10).setCellValue(datoSdi.getDesc_nomina());
            // Se crea así ya que apache poi no soporta datos de tipo local date time
            Cell fechaIngresoCell = row.createCell(11);
            if (datoSdi.getFecha_ingreso() != null) {
                fechaIngresoCell.setCellValue(java.sql.Date.valueOf(datoSdi.getFecha_ingreso()));
                fechaIngresoCell.setCellStyle(dateCellStyle);
            }
            row.createCell(12).setCellValue(datoSdi.getTotal_cve_1());
            row.createCell(13).setCellValue(datoSdi.getTotal_cve_3());
            row.createCell(14).setCellValue(datoSdi.getTotal_cve_4());
            row.createCell(15).setCellValue(datoSdi.getTotal_cve_9());
            row.createCell(16).setCellValue(datoSdi.getTotal_cve_10());
            row.createCell(17).setCellValue(datoSdi.getTotal_cve_11());
            row.createCell(18).setCellValue(datoSdi.getTotal_cve_12());
            row.createCell(19).setCellValue(datoSdi.getTotal_cve_14());
            row.createCell(20).setCellValue(datoSdi.getTotal_cve_15());
            row.createCell(21).setCellValue(datoSdi.getTotal_cve_16());
            row.createCell(22).setCellValue(datoSdi.getTotal_cve_17());
            row.createCell(23).setCellValue(datoSdi.getTotal_cve_18());
            row.createCell(24).setCellValue(datoSdi.getTotal_cve_19());
            row.createCell(25).setCellValue(datoSdi.getTotal_cve_23());
            row.createCell(26).setCellValue(datoSdi.getTotal_cve_24());
            row.createCell(27).setCellValue(datoSdi.getTotal_cve_26());
            row.createCell(28).setCellValue(datoSdi.getTotal_cve_27());
            row.createCell(29).setCellValue(datoSdi.getTotal_cve_30());
            row.createCell(30).setCellValue(datoSdi.getVales_despensa_pension_alim() != null ? datoSdi.getVales_despensa_pension_alim() : 0.0);
            row.createCell(31).setCellValue(datoSdi.getTotal_cve_32());
            row.createCell(32).setCellValue(datoSdi.getTotal_cve_33());
            row.createCell(33).setCellValue(datoSdi.getTotal_cve_39());
            row.createCell(34).setCellValue(datoSdi.getTotal_cve_41());
            row.createCell(35).setCellValue(datoSdi.getTotal_cve_45());
            row.createCell(36).setCellValue(datoSdi.getTotal_cve_207());
            row.createCell(37).setCellValue(datoSdi.getTotal_cve_217());
            row.createCell(38).setCellValue(datoSdi.getTotal_cve_222());
            row.createCell(39).setCellValue(datoSdi.getTotal_cve_231());
            row.createCell(40).setCellValue(datoSdi.getTotal_cve_1002());
            row.createCell(41).setCellValue(datoSdi.getTotal_cve_1008());
            row.createCell(42).setCellValue(datoSdi.getTotal_cve_1101());
            row.createCell(43).setCellValue(datoSdi.getTotal_cve_1210());
            row.createCell(44).setCellValue(datoSdi.getTotal_cve_1211());
            row.createCell(45).setCellValue(datoSdi.getTotal_cve_1212());
            row.createCell(46).setCellValue(datoSdi.getTotal_cve_1213());
            row.createCell(47).setCellValue(datoSdi.getTotal_cve_1220());
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
    public void exportarCalculosObtenidosConfianza(HttpServletResponse response, List<Integer> periodos) throws IOException {
        List<SDIConfianzaDTO> datosSdiConfianza = obtenDatosConfianza(periodos);
        //Se valida para no generar cálculos si el registro está vacío
        if (datosSdiConfianza.isEmpty()) {
            throw new RuntimeException("No se encontró información");
        }
        // Create a workbook and a sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Personal_Acumulados_SDI");
        // Crear estilo de celda con fondo gris claro
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //Negritas a los encabezados
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        // Create headers
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Expediente",
            "Apellido Paterno",
            "Apellido Materno",
            "Nombre",
            "NSS",
            "Dígito Vertificador",
            "RFC",
            "Género",
            "Hijos",
            "Sueldo Diario Tabulado",
            "Nomina",
            "Fecha Ingreso",
            "Sueldo Ordinario Clave 1",
            "Horas Extra Dobles Clave 4",
            "Permiso Retribuido Clave 6",
            "Descansos Laborados (Confianza) Clave 7",
            "Estímulo de Vacaciones Clave 10",
            "Prima Dominical Clave 11",
            "Quinquenio Mensual Clave 16",
            "Estímulo por Puntualidad Clave 17",
            "Sumsidio I.M.S.S Clave 18",
            "Salario de Vacaciones Clave 21",
            "Estímulo por apego al reglamento Clave 23",
            "Bono de Productividad y Calidad Clave 24",
            "Subsidio para el Empleo Clave 26",
            "Ayuda Canasta Básica de Alim. Vales de Desp. Clave 30",
            "Vales despensa pensión Claves 76,326,327,328,329",
            "Ayuda de Reyes Clave 32",
            "Ayuda de Transporte Clave 33",
            "Fondo de Ahorro Empresa 13% Clave 207",
            "Horas Trabajadas en Día Festivo Clave 222",
            "Fondo de Ahorro grav.emp. 7% Clave 231",
            "Número de Días Laborados",
            "Permiso Retribuído Clave 1002",
            "Días de Salario de Vacaciones Clave 1021",
            "Descansos o Paseos Clave 1101",
            "Días de Incapacidad por EG al 50% Clave 1210",
            "Días de Incapacidad por EG al 75% Clave 1211",
            "Días de Incapacidad por EG al 100% Clave 1212",
            "Días de Incapacidad por RT al 100% Clave 1213",
            "Días de Incapacidad por Pr-P al 100% Clave 1214",
            "Días de Incapacidad por EG al 60% Clave 1220"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Aplicar estilo de celda al encabezado
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        // Aquí se formatea la fecha
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));

        // Write data to the sheet
        int rowNum = 1;
        for (SDIConfianzaDTO datoSdi : datosSdiConfianza) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(datoSdi.getExpediente());
            row.createCell(1).setCellValue(datoSdi.getApellido_paterno());
            row.createCell(2).setCellValue(datoSdi.getApellido_materno());
            row.createCell(3).setCellValue(datoSdi.getNombre());
            row.createCell(4).setCellValue(datoSdi.getNum_imss_primeros_10());
            row.createCell(5).setCellValue(datoSdi.getNum_imss_digito_verificador());
            row.createCell(6).setCellValue(datoSdi.getRfc());
            row.createCell(7).setCellValue(datoSdi.getDesc_genero());
            row.createCell(8).setCellValue(datoSdi.getHijos_si_no());
            row.createCell(9).setCellValue(datoSdi.getSueldo_diario());
            row.createCell(10).setCellValue(datoSdi.getDesc_nomina());
            // Se crea así ya que apache poi no soporta datos de tipo local date time
            Cell fechaIngresoCell = row.createCell(11);
            if (datoSdi.getFecha_ingreso() != null) {
                fechaIngresoCell.setCellValue(java.sql.Date.valueOf(datoSdi.getFecha_ingreso()));
                fechaIngresoCell.setCellStyle(dateCellStyle);
            }
            row.createCell(12).setCellValue(datoSdi.getTotal_cve_1());
            row.createCell(13).setCellValue(datoSdi.getTotal_cve_4());
            row.createCell(14).setCellValue(datoSdi.getTotal_cve_6());
            row.createCell(15).setCellValue(datoSdi.getTotal_cve_7());
            row.createCell(16).setCellValue(datoSdi.getTotal_cve_10());
            row.createCell(17).setCellValue(datoSdi.getTotal_cve_11());
            row.createCell(18).setCellValue(datoSdi.getTotal_cve_16());
            row.createCell(19).setCellValue(datoSdi.getTotal_cve_17());
            row.createCell(20).setCellValue(datoSdi.getTotal_cve_18());
            row.createCell(21).setCellValue(datoSdi.getTotal_cve_21());
            row.createCell(22).setCellValue(datoSdi.getTotal_cve_23());
            row.createCell(23).setCellValue(datoSdi.getTotal_cve_24());
            row.createCell(24).setCellValue(datoSdi.getTotal_cve_26());
            row.createCell(25).setCellValue(datoSdi.getTotal_cve_30());
            row.createCell(26).setCellValue(datoSdi.getVales_despensa_pension_alim() != null ? datoSdi.getVales_despensa_pension_alim() : 0.0);
            row.createCell(27).setCellValue(datoSdi.getTotal_cve_32());
            row.createCell(28).setCellValue(datoSdi.getTotal_cve_33());
            row.createCell(29).setCellValue(datoSdi.getTotal_cve_207());
            row.createCell(30).setCellValue(datoSdi.getTotal_cve_222());
            row.createCell(31).setCellValue(datoSdi.getTotal_cve_231());
            row.createCell(32).setCellValue(datoSdi.getTotal_cve_2());
            row.createCell(33).setCellValue(datoSdi.getTotal_cve_1002());
            row.createCell(34).setCellValue(datoSdi.getTotal_cve_1021());
            row.createCell(35).setCellValue(datoSdi.getTotal_cve_1101());
            row.createCell(36).setCellValue(datoSdi.getTotal_cve_1210());
            row.createCell(37).setCellValue(datoSdi.getTotal_cve_1211());
            row.createCell(38).setCellValue(datoSdi.getTotal_cve_1212());
            row.createCell(39).setCellValue(datoSdi.getTotal_cve_1213());
            row.createCell(40).setCellValue(datoSdi.getTotal_cve_1214());
            row.createCell(41).setCellValue(datoSdi.getTotal_cve_1220());
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
    public void exportarCalculosObtenidosSdiEstructura(HttpServletResponse response, List<Integer> periodos) throws IOException {
        List<SDIEstructuraDTO> datosSdiEstructura = obtenDatosEstructura(periodos);
        //Se valida para no generar cálculos si el registro está vacío
        if (datosSdiEstructura.isEmpty()) {
            throw new RuntimeException("No se encontró información");
        }
        // Create a workbook and a sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Personal_Acumulados_SDI");
        // Crear estilo de celda con fondo gris claro
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //Negritas a los encabezados
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        // Create headers
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Expediente",
            "Apellido Paterno",
            "Apellido Materno",
            "Nombre", "NSS",
            "Dígito Vertificador",
            "RFC",
            "Género",
            "Hijos",
            "Sueldo Diario Tabulado",
            "Nomina",
            "Fecha Ingreso",
            "Sueldo Ordinario Clave 1",
            "Estímulo de Vacaciones Clave 10",
            "Subsidio I.M.S.S. Clave 18",
            "Salario de Vacaciones Clave 21",
            "Cantidad adicional;reconocimiento y estímulo Clave 28",
            "Número de días laborados Clave 1002",
            "Días de salario de vacaciones Clave 1021",
            "Días de Incapacidad por EG al 50% Clave 1210",
            "Días de Incapacidad por EG al 75% Clave 1211",
            "Días de Incapacidad por EG al 100% Clave 1212"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Aplicar estilo de celda al encabezado
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }
        // Aquí se formatea la fecha
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));

        // Write data to the sheet
        int rowNum = 1;
        for (SDIEstructuraDTO datoSdi : datosSdiEstructura) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(datoSdi.getExpediente());
            row.createCell(1).setCellValue(datoSdi.getApellido_paterno());
            row.createCell(2).setCellValue(datoSdi.getApellido_materno());
            row.createCell(3).setCellValue(datoSdi.getNombre());
            row.createCell(4).setCellValue(datoSdi.getNum_imss_primeros_10());
            row.createCell(5).setCellValue(datoSdi.getNum_imss_digito_verificador());
            row.createCell(6).setCellValue(datoSdi.getRfc());
            row.createCell(7).setCellValue(datoSdi.getDesc_genero());
            row.createCell(8).setCellValue(datoSdi.getHijos_si_no());
            row.createCell(9).setCellValue(datoSdi.getSueldo_diario());
            row.createCell(10).setCellValue(datoSdi.getDesc_nomina());
            // Se crea así ya que apache poi no soporta datos de tipo local 
            Cell fechaIngresoCell = row.createCell(11);
            if (datoSdi.getFecha_ingreso() != null) {
                fechaIngresoCell.setCellValue(java.sql.Date.valueOf(datoSdi.getFecha_ingreso()));
                fechaIngresoCell.setCellStyle(dateCellStyle);
            }
            row.createCell(12).setCellValue(datoSdi.getTotal_cve_1());
            row.createCell(13).setCellValue(datoSdi.getTotal_cve_10());
            row.createCell(14).setCellValue(datoSdi.getTotal_cve_18());
            row.createCell(15).setCellValue(datoSdi.getTotal_cve_21());
            row.createCell(16).setCellValue(datoSdi.getTotal_cve_28());
            row.createCell(17).setCellValue(datoSdi.getTotal_cve_1002());
            row.createCell(18).setCellValue(datoSdi.getTotal_cve_1021());
            row.createCell(19).setCellValue(datoSdi.getTotal_cve_1210());
            row.createCell(20).setCellValue(datoSdi.getTotal_cve_1211());
            row.createCell(21).setCellValue(datoSdi.getTotal_cve_1212());
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

    //************************CALCULOS SDI INICIAL TRABAJADOR NUEVO INGRESO***********************
    private Double calcularDiasAguinaldo(Integer idNomina, Integer prestacionesSiNo) {
        switch (idNomina) {
            case 1:
                return 60.0;
            case 2:
                return 60.0;
            case 3:
                return prestacionesSiNo == 1 ? 60.0 : 40.0;
            case 5:
                return 40.0;
            default:
                throw new IllegalArgumentException("Nómina inválida");
        }
    }

    //A partir de la nómina, prestaciones se calculan los días de vacaciones con las funciones ya definidas
    private Integer calcularDiasVacacionesInicial(Long diferenciaDias, Integer idNomina, Integer prestacionesSiNo) {
        if (idNomina == 1 || idNomina == 2) {
            return calcularDiasVacacionesVariosTranspCfzaSinPrestaciones(diferenciaDias);
        } else if (idNomina == 3 && prestacionesSiNo == 1) {
            return calcularDiasVacacionesVariosTranspCfzaSinPrestaciones(diferenciaDias);
        } else {
            return calcularDiasVacacionesEstructuraCfzaPrestaciones(diferenciaDias);
        }
    }

    //A pertir de la nómina y sus prestaciones se define la prima vacacional en las funciones ya definidas
    private Double calcularPrimaVacacionalDiariaInicial(Double sueldoDiario, Integer diasVacaciones, Integer idNomina, Integer prestacionesSiNo) {
        if (idNomina == 1 || idNomina == 2 || (idNomina == 3 && prestacionesSiNo == 1)) {
            return calcularPrimaVacacionalDiariaCfzaTranspVarios(sueldoDiario, diasVacaciones);
        } else if ((idNomina == 3 && prestacionesSiNo == 2) || idNomina == 5) {
            return calcularPrimaVacacionalEstructuraCfzaSinPrestaciones(sueldoDiario, diasVacaciones);
        }
        return null;
    }

    //Devuelve cero o la ayuda de reyes según la nómina y prestación del trabjador
    private Double calculaAyudaReyesInicial(Integer idNomina, Integer prestacionesSiNo) {
        if (idNomina == 1 || idNomina == 2 || (idNomina == 3 && prestacionesSiNo == 1)) {
            return calculaAyudaReyes();
        }
        return 0.0;
    }

    //Devuelve cero o la ayuda de madres según la nómina o prestaciones del trabajador
    private Double calculaAyudaMadresInicial(Boolean hijos, Boolean mujer, Integer idNomina, Integer prestacionesSiNo) {
        if (idNomina == 1 || idNomina == 2 || (idNomina == 3 && prestacionesSiNo == 1)) {
            return calculaAyudaMadres(hijos, mujer);
        }
        return 0.0;
    }

    //Devuelve cero o la ayuda de útiles correspondientes según 
    private Double calculaAyudaUtilesInicial(Integer idNomina, Integer prestacionesSiNo) {
        if (idNomina == 1 || idNomina == 2 || (idNomina == 3 && prestacionesSiNo == 1)) {
            return calculaAyudaUtiles();
        }
        return 0.0;
    }

    //Devuelve cero o la ayuda de transporte según la nómina o prestaciones del trabajador
    private Double calcularAyudaTransporteInicial(Integer idNomina, Integer prestacionesSiNo) {
        if (idNomina == 1 || idNomina == 2 || (idNomina == 3 && prestacionesSiNo == 1)) {
            return calcularAyudaTransporte();
        }
        return 0.0;
    }

    //Devueelve el cálculo de fondo de ahorro al 7% o cero según la nómina y prestaciones del trabajador
    private Double calcularFondoAhorroAl7PorcientoInicial(Double sueldoDiario, Integer idNomina, Integer prestacionesSiNo) {
        if (idNomina == 1 || idNomina == 2 || (idNomina == 3 && prestacionesSiNo == 1)) {
            return calcularFondoAhorroAl7Porciento(sueldoDiario);
        }
        return 0.0;
    }

    //****************************CÁLCULOS DE SDI***************************

    /*CALCULO DE DIFERENCIA DE SUELDO ORDINARIO POR FÓRMULA
    Este se conforma por la diferencia positiva del sueldo ordinario bimestral menos el sueldo diario tabulado por los
    días laborados*/
    private Double calcularDiferenciaSueldoPorFormula(Double sueldoOrdinarioBimestre, Double sueldoDiarioTabulado, Double numeroDiasLaborados) {
        // Calcula el sueldo total esperado por los días laborados
        Double sueldoTotalEsperado = sueldoDiarioTabulado * numeroDiasLaborados;
        // Calcula la diferencia de sueldo
        Double diferenciaSueldoFormula = (sueldoOrdinarioBimestre > sueldoTotalEsperado) ? sueldoOrdinarioBimestre - sueldoTotalEsperado : 0.0;
        // Redondea la diferencia de sueldo al decimal más cercano
        return diferenciaSueldoFormula;
    }

    /*CALCULO DE DIAS COTIZADOS
    Este calculo varía según la nómina, los días devengados dependerán de los siguientes criterios y topes:
    -Dias de salario devengado = resulta de la suma del número de días laborados más el número de días pagados
    por vacaciones
    -CFZA: Tope de 60 días por bimestre
    -TRANSPORTACIÓN: Con un tope acuerdo a las semanas del bimestre, si es de 8 semanas el tope máximo es de 56 días,
    si es de 9 semanas el tope máximo es de 63 días
    -VARIOS:  Con un tope acuerdo a las semanas del bimestre, si es de 8 semanas el tope máximo es de 56 días,
    si es de 9 semanas el tope máximo es de 63 días
    -ESTRUCTURA/FUNCIONARIOS: Con un tope de hasta 60 días por bimeste*/
    private Double calcularDiasDevengados(Double diasLaborados, Double numeroDiasVacaciones, Integer idNomina) {
        Double diasDevengados;
        diasDevengados = diasLaborados + numeroDiasVacaciones;
        switch (idNomina) {
            case 1: // VARIOS
            case 2: // TRANSPORTACIÓN
                diasDevengados = Math.min(diasDevengados, 63.0); // Tope máximo de 63 días
                break;
            case 3: // CFZA (Confianza)
            case 4: // ESTRUCTURA/FUNCIONARIOS
                diasDevengados = Math.min(diasDevengados, 60.0); // Tope máximo de 60 días
                break;
            default:
                throw new RuntimeException("El tipo de nómina proporcionado no es válido: " + idNomina);
        }
        return diasDevengados;
    }

    private Double calcularDiasDevengadosConfianza(Double diasLaborados, Double permisoRetribuido, Double diasSalarioVacaciones) {
        Double diasDevengados;
        diasDevengados = diasLaborados + permisoRetribuido + diasSalarioVacaciones;
        if (diasDevengados > 60.0) {
            diasDevengados = 60.0;
        }
        return diasDevengados;
    }

    /*CALCULO DE DIFERENCIA DE FONDO DE AHORRO (TRANSPORTACION)
    Solo aplica para la nómina de transportación, se determina al aplicar la suma de la diferencia de 
    sueldo ordinario por fórmula, más el concepto tres (Diferencia de sueldo) más el concepto 14 (Diferencia de Jornada),
    más el concepto 45 (Clave 77 CCT),  por el 7% y el resultado se suma al 100%
    como importe variable*/
    private Double calcularDiferenciaFondoAhorroPorFormulaTransportacion(Double difSueldoOrdFormula, Double cve3, Double cve14, Double cve45) {
        Double diferenciaFaFormula;
        diferenciaFaFormula = (difSueldoOrdFormula + cve3 + cve14 + cve45) * 0.07;
        return diferenciaFaFormula;
    }

    /*CALCULO DE DIFERENCIA DE FONDO DE AHORRO (VARIOS Y CONFIANZA)
    Se determina al aplicar el 7% al concepto 3 (Diferencia de sueldo) mas la diferencia de sueldo ordinario por fórmula*/
    private Double calcularDiferenciaFondoAhorroPorFormulaVariosCfza(Double cve3, Double difSueldoFormula) {
        Double diferenciaFaFormula;
        diferenciaFaFormula = (cve3 + difSueldoFormula) * 0.07;
        return diferenciaFaFormula;
    }

    /*CALCULO DE DIFERENCIA POR FORMULA ESTIMULO VACACIONES (Varios, Trasnportación)
    Es la diferencia positiva de la prima vacacional pagada en el bimestre menos el sueldo diario tabulado por los días
    a que tiene derecho el empleado de acuerdo a su antiguedad por el 120%, y el resultado se suma como importe variable*/
    private Double calcularDiferenciaPorFormulaEstimuloVacaciones(Double cve10, Double sueldoDiarioTabulado,
            Double cve1008) {
        Double diferenciaEstimuloVacaciones;
        if (cve10 > (sueldoDiarioTabulado * cve1008 * 1.2)) {
            diferenciaEstimuloVacaciones = cve10 - (sueldoDiarioTabulado * cve1008 * 1.2);
        } else {
            diferenciaEstimuloVacaciones = 0.0;
        }
        return diferenciaEstimuloVacaciones;
    }

    /*CALCULO DE DIFERENCIA POR FORMULA ESTIMULO VACACIONES (Confianza) 120%*/
    private Double calcularDiferenciaPorFormulaEstimuloVacacionesCfzaPrestaciones(Double sueldoDiarioTabulado, Double cve1101) {
        Double diferenciaEstimuloVacaciones;
        diferenciaEstimuloVacaciones = sueldoDiarioTabulado * cve1101 * 1.2;
        return diferenciaEstimuloVacaciones;
    }

    /*CALCULO DE DIFERENCIA POR FORMULA ESTIMULO VACACIONES (Confianza) 25%%*/
    private Double calcularDiferenciaPorFormulaEstimuloVacacionesCfzaSinPrestaciones(Double estimuloVacacionesCve10,
            Double sueldoDiarioTabulado,
            Integer diasVacacionesCalculados) {
        Double diferenciaEstimuloVacaciones;
        if (estimuloVacacionesCve10 > (sueldoDiarioTabulado * diasVacacionesCalculados * 0.25)) {
            diferenciaEstimuloVacaciones = sueldoDiarioTabulado * diasVacacionesCalculados * 0.25;
        } else {
            diferenciaEstimuloVacaciones = 0.0;
        }
        return diferenciaEstimuloVacaciones;
    }

    /*PRIMA VACACIONAL DIARIA (Confianza, Transportación, Varios)
    Aplicable a los trabajadores con prestaciones en confianza
    (Sueldo diario tabulado por los días de vacaciones a los que tengan derecho según su antiguedad x120%)/365
     Verrificar si se consideran años bisiestos*/
    private Double calcularPrimaVacacionalDiariaCfzaTranspVarios(Double sueldoDiarioTabulado, Integer diasCalculados) {
        Double primaVacacionalDiaria = (sueldoDiarioTabulado * diasCalculados * 1.2) / 365;
        return primaVacacionalDiaria;
    }

    /*PRIMA VACACIONAL DIARIA (Funcionarios)
    (Sueldo diario tabulado por los días de vacaciones a los que tengan derecho según su antiguedad x25%)/365
     Verrificar si se consideran años bisiestos*/
    private Double calcularPrimaVacacionalEstructuraCfzaSinPrestaciones(Double sueldoDiarioTabulado, Integer diasCalculados) {
        Double primaVacacionalDiaria = (sueldoDiarioTabulado * diasCalculados * 0.25) / 365;
        return primaVacacionalDiaria;
    }

    /*AGUINALDO DIARIO
    (SUELDO DIARIO TABULADO X 40)/365  Verificar si siempre son 40, existen casos de 60
    En la nómina de confianza a partir del expediente 20821 en adelante se rigen en base a las prestaciones
    fijas del personal de estructura, no aplica en todos los casos... revisar
     */
    private Double calcularAguinaldoDiario(Double sueldoDiarioTabulado, Double diasAguinaldo) {
        Double aguinaldoDiario;
        aguinaldoDiario = (sueldoDiarioTabulado * diasAguinaldo) / 365;
        return aguinaldoDiario;
    }

    private Long calculaDiferenciaDias(LocalDate fechaIngreso, LocalDate fechaCorteSdi) {
        // Calcula la diferencia en días entre dos LocalDateTime
        long diferenciaEnDias = ChronoUnit.DAYS.between(fechaIngreso, fechaCorteSdi);
        return diferenciaEnDias;
    }

    /*QUINQUENIO MENSUAL CALCULADO
    (Quinquenio bimestral al que tengan derecho según su antiguedad)* 60
    CONTRATO COLECTIVO DE TRABAJO 			
    21 ENERO 2007 / 20 ENERO 2008			
    CLAUSULA 132A QUINQUENIO			
     */
    private Double calcularQuinquenioMensual(Long diferenciaEnDias) {
        Double importeMensualQuinquenio;
        //60 AÑOS 	59 AÑOS 364 DIAS 
        if (diferenciaEnDias >= 21912) {
            importeMensualQuinquenio = 1380.0;
            //55 AÑOS 	59 AÑOS 364 DIAS 
        } else if (diferenciaEnDias >= 20086) {
            importeMensualQuinquenio = 1265.0;
            //50 AÑOS 	54 AÑOS 364 DIAS 
        } else if (diferenciaEnDias >= 18260) {
            importeMensualQuinquenio = 1150.0;
            //45 AÑOS	49 AÑOS 364 DIAS 
        } else if (diferenciaEnDias >= 16434) {
            importeMensualQuinquenio = 1035.0;
            //40 AÑOS 	44 AÑOS 364 DIAS 
        } else if (diferenciaEnDias >= 14608) {
            importeMensualQuinquenio = 920.0;
            //35 AÑOS 	39 AÑOS 364 DIAS 
        } else if (diferenciaEnDias >= 12782) {
            importeMensualQuinquenio = 805.0;
            //30 AÑOS 	34 AÑOS 364 DIAS 
        } else if (diferenciaEnDias >= 10956) {
            importeMensualQuinquenio = 690.0;
            //25 AÑOS 	29 AÑOS 364 DIAS 
        } else if (diferenciaEnDias >= 9130) {
            importeMensualQuinquenio = 575.0;
            //20 AÑOS 	24 AÑOS 364 DIAS 
        } else if (diferenciaEnDias >= 7304) {
            importeMensualQuinquenio = 460.0;
            //15 AÑOS 	19 AÑOS 364 DIAS 
        } else if (diferenciaEnDias >= 5478) {
            importeMensualQuinquenio = 345.0;
            //10 AÑOS	14 AÑOS 364 DIAS 
        } else if (diferenciaEnDias >= 3652) {
            importeMensualQuinquenio = 230.0;
            //5 AÑOS 	9 AÑOS 364 DIAS
        } else if (diferenciaEnDias >= 1826) {
            importeMensualQuinquenio = 115.0;
            //No llegan a los 5 años
        } else if (diferenciaEnDias < 1826) {
            importeMensualQuinquenio = 0.0;
        } else {
            throw new RuntimeException("Error al calcular quinquenio trabajador");
        }
        return importeMensualQuinquenio;
    }


    /*QUINQUENIO DIARIO
    (Quinquenio bimestral al que tengan derecho según su antiguedad)* 60
     */
    private Double calcularQuinquenioDiario(Double quinquenioMensualCalculado) {
        Double quinquenioDiario = quinquenioMensualCalculado / 60;
        return quinquenioDiario;
    }

    private Integer calcularDiasVacacionesVariosTranspCfzaSinPrestaciones(Long diferenciaEnDias) {
        Integer diasVacacionesCorrespondientes;
        // 17886 ó MAS 		46 a 50
        if (diferenciaEnDias >= 16429) {
            diasVacacionesCorrespondientes = 38;
            //41 a 45
        } else if (diferenciaEnDias >= 14604) {
            diasVacacionesCorrespondientes = 36;
            //16061 A 17885		36 a 40
        } else if (diferenciaEnDias >= 12779) {
            diasVacacionesCorrespondientes = 34;
            //31 a 35
        } else if (diferenciaEnDias >= 10954) {
            diasVacacionesCorrespondientes = 32;
            //26 a 30
        } else if (diferenciaEnDias >= 9129) {
            diasVacacionesCorrespondientes = 30;
            //19 a 25
        } else if (diferenciaEnDias >= 6939) {
            diasVacacionesCorrespondientes = 28;
            //14236 A 16060 16 A 18
        } else if (diferenciaEnDias >= 5479) {
            diasVacacionesCorrespondientes = 26;
            //12411 A 14235 11 A 15
        } else if (diferenciaEnDias >= 3654) {
            diasVacacionesCorrespondientes = 24;
            //6936 A 12410 6 A 10
        } else if (diferenciaEnDias >= 1828) {
            diasVacacionesCorrespondientes = 22;
            //4016 A 6935 5
        } else if (diferenciaEnDias >= 1463) {
            diasVacacionesCorrespondientes = 20;
            //3286 A 4015 4
        } else if (diferenciaEnDias >= 1097) {
            diasVacacionesCorrespondientes = 18;
            //1096 A 3285 3
        } else if (diferenciaEnDias >= 732) {
            diasVacacionesCorrespondientes = 16;
            //366 A 1095 2
        } else if (diferenciaEnDias >= 366) {
            diasVacacionesCorrespondientes = 14;
            //01 A 365 1
        } else if (diferenciaEnDias >= 1) {
            diasVacacionesCorrespondientes = 12;
        } else {
            throw new RuntimeException("Error al calcular vacaciones del trabajador");
        }
        return diasVacacionesCorrespondientes;
    }

    private Integer calcularDiasVacacionesEstructuraCfzaPrestaciones(Long diferenciaEnDias) {
        Integer diasVacacionesCorrespondientes;
        // 17886 ó MAS 		46 a 50
        if (diferenciaEnDias >= 16429) {
            diasVacacionesCorrespondientes = 38;
            //41 a 45
        } else if (diferenciaEnDias >= 14604) {
            diasVacacionesCorrespondientes = 36;
            //16061 A 17885		36 a 40
        } else if (diferenciaEnDias >= 12779) {
            diasVacacionesCorrespondientes = 34;
            //31 a 35
        } else if (diferenciaEnDias >= 10954) {
            diasVacacionesCorrespondientes = 32;
            //26 a 30
        } else if (diferenciaEnDias >= 9129) {
            diasVacacionesCorrespondientes = 30;
            //19 a 25
        } else if (diferenciaEnDias >= 6939) {
            diasVacacionesCorrespondientes = 28;
            //14236 A 16060 16 A 18
        } else if (diferenciaEnDias >= 5479) {
            diasVacacionesCorrespondientes = 26;
            //12411 A 14235 11 A 15
        } else if (diferenciaEnDias >= 3654) {
            diasVacacionesCorrespondientes = 24;
            //6936 A 12410 6 A 10
        } else if (diferenciaEnDias >= 1828) {
            diasVacacionesCorrespondientes = 22;
            //4016 A 6935 5
        } else if (diferenciaEnDias >= 1463) {
            diasVacacionesCorrespondientes = 20;
            //3286 A 4015 4
        } else if (diferenciaEnDias >= 1097) {
            diasVacacionesCorrespondientes = 20;
            //1096 A 3285 3
        } else if (diferenciaEnDias >= 732) {
            diasVacacionesCorrespondientes = 20;
            //366 A 1095 2
        } else if (diferenciaEnDias >= 366) {
            diasVacacionesCorrespondientes = 20;
            //01 A 365 1
        } else if (diferenciaEnDias >= 1) {
            diasVacacionesCorrespondientes = 20;
        } else {
            throw new RuntimeException("Error al calcular vacaciones del trabajador");
        }
        return diasVacacionesCorrespondientes;
    }


    /*AYUDA DE REYES
    (150/365) Verificar valor ya que varía, será 178.85?
    La incidencia de ayuda de reyes tiene en el catalogo de incidencias el id 66
     */
    private Double calculaAyudaReyes() {
        Double valorIncidencia = recuperarValorIncidencia(66);
        return valorIncidencia / 365;
    }

    /*AYUDA MADRES
    Ayuda 10 de mayo a:
    MADRES TRABAJADORAS: (500/465)
    RESTO DE PERSONAL : (150/365)
    Verificar ambos valores ya que varían
    La incidencia con id 77 corresponde al Estímulo de 10 de mayo
     */
    private Double calculaAyudaMadres(Boolean tieneHijos, Boolean esMujer) {
        Double valorIncidencia = recuperarValorIncidencia(77);
        Double ayudaMadres;
        //Verificar si la persona tiene hijos y es mujer
        if (Boolean.TRUE.equals(tieneHijos) && Boolean.TRUE.equals(esMujer)) {
            ayudaMadres = valorIncidencia / 365.0;
            //Resto del personal
        } else {
            ayudaMadres = 200.75 / 365.0;
        }
        return ayudaMadres;
    }

    /*AYUDA UTILES
    (150/365) Verificar valor
    La incidencia con id 65 corresponde a ayuda escolar
     */
    private Double calculaAyudaUtiles() {
        Double valorIncidencia = recuperarValorIncidencia(65);
        return (valorIncidencia / 365.0);
    }

    /*AYUDA DE TRANSPORTE
    (283*12)/365, Verificar valor
    La incidencia con id 67 corresponde a la ayuda de transporte
     */
    private Double calcularAyudaTransporte() {
        Double valorIncidencia = recuperarValorIncidencia(67);
        return ((valorIncidencia * 12.0) / 365) * 100.0 / 100.0;
    }

    /*FONDO DE AHORRO AL 7%
    Sueldo diario tabulado * 7%
     */
    private Double calcularFondoAhorroAl7Porciento(Double sueldoDiarioTabulado) {
        return (sueldoDiarioTabulado * 0.07);
    }

    /*Hace una consulta a la base de datos para obtener los valores de las incidencias*/
    private Double recuperarValorIncidencia(Integer id) {
        Cat_Incidencias inc = cat_IncidenciasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Incidencia con ID " + id + " no encontrada"));
        return inc.getValor_variable();
    }

    /*SALARIO FIJO NÓMINA VARIOS Y TRANSPORTACIÓN
    Salario diario tabular + Prima Vacacional diaria + Aguinaldo Diario + Quinquenio Diario + Ayuda Reyes
    + Ayuda madres + Ayuda utiles + Ayuda transporte + F Ahorro al 7%
     */
    private Double calculaSalarioFijoTotal(Double sueldoDiarioTabulado,
            Double primaVacacionalDiaria,
            Double aguinaldoDiario,
            Double quinquenioDiario,
            Double ayudaReyes,
            Double ayudaMadres,
            Double ayudaUtiles,
            Double ayudaTransporte,
            Double fAhorro7
    ) {
        double total = sueldoDiarioTabulado + primaVacacionalDiaria + aguinaldoDiario + quinquenioDiario
                + ayudaReyes + ayudaMadres + ayudaUtiles + ayudaTransporte + fAhorro7;
        return Math.round(total * 100.0) / 100.0;
    }

    /*SALARIO FIJO ESTRUCTURA
    Salario diario tabular + Prima Vacacional diaria + Aguinaldo Diario
     */
    private Double calculaSalarioFijoTotalEstructura(Double sueldoDiarioTabulado,
            Double primaVacacionalDiaria,
            Double aguinaldoDiario
    ) {
        double total = sueldoDiarioTabulado + primaVacacionalDiaria + aguinaldoDiario;
        return Math.round(total * 100.0) / 100.0;
    }

    /*VALIDACION UNIDAD DE MEDIDA DE ACTUALIZACIÓN
     */
    private Double validaUma(Double salarioFijo) {
        final Double MAXIMO_PERMITIDO = 108.57 * 25;
        Double salarioValidado = salarioFijo > MAXIMO_PERMITIDO ? MAXIMO_PERMITIDO : salarioFijo;
        return salarioValidado;
    }

    /*SALARIO VARIABLE NÓMINA DE VARIOS
        Diferencia sueldo ordinario por fórmula + Diferencia de fondo de ahorro por fórmula + Diferencia de sueldo ordinario cve3
    + Diferencia por fórmula estímulo de vacaciones + Prima dominical + Paseos laborados + Alumbramiento o patermidad
    + Ayuda por fallecimiento  + Horas trabajadas en días festivos
     */
    private Double calcularSalarioVariableTotalVarios(Double difSueldoOrdinarioFormula,
            Double diferenciaFondoAhorroFormula,
            Double diferenciaSueldoOrdinario,
            Double diferenciaFormulaEstimuloVacaciones,
            Double primaDominical,
            Double paseosLaborados,
            Double estimuloApegoReglamento,
            Double bonoProductividadCalidad,
            Double ayudaAlumbramientoPaternidad,
            Double ayudaFallecimiento,
            Double horasTrabajadasDiaFestivo
    ) {
        return Math.round((difSueldoOrdinarioFormula + diferenciaFondoAhorroFormula + diferenciaSueldoOrdinario
                + diferenciaFormulaEstimuloVacaciones
                + primaDominical + paseosLaborados + estimuloApegoReglamento + bonoProductividadCalidad
                + ayudaAlumbramientoPaternidad + ayudaFallecimiento + horasTrabajadasDiaFestivo) * 100.0) / 100.0;
    }

    //Se compone por el total del salario variable dividido entre los días cotizados o devengados
    private Double calcularSalarioVariableDiario(Double totalSalarioVariable, Double diasDevengados) {
        return Math.round((totalSalarioVariable / diasDevengados) * 100.0) / 100.0;
    }

    //Se compone por la suma del total del salario fijo más el salario variable diario
    private Double calcularSubtotalSbc(Double salarioFijo, Double salarioVariableDiario) {
        return salarioFijo + salarioVariableDiario;
    }

    //Solo se suma si es mayor a cero la diferencia del estímulo de puntualidad
    private Double calcularEstimuloPuntualidad(Double diasDevengados, Double estimuloPuntualidad, Double salBaseCot) {
        Double subtotal = estimuloPuntualidad / diasDevengados;
        Double total = subtotal - (salBaseCot / 10);
        return total > 0 ? total : 0;
    }

    //Solo se suma si es mayor a cero la diferencia del estímulo de puntualidad
    private Double calcularEstimuloPuntualidadTransportacion(Double diasDevengados,
            Double estimuloPuntualidadCve17,
            Double estimuloPuntualidadTranspCve27,
            Double salBaseCot) {
        Double subtotal = (estimuloPuntualidadCve17 + estimuloPuntualidadTranspCve27) / diasDevengados;
        Double total = subtotal - (salBaseCot / 10);
        return total > 0 ? total : 0;
    }

    /*SALARIO VARIABLE NÓMINA DE TRANSPORTACION
        Diferencia sueldo ordinario por fórmula + Diferencia de fondo de ahorro por fórmula + Diferencia de sueldo ordinario cve3
    + Diferencia por fórmula estímulo de vacaciones + Prima dominical + Diferencia de Jornada Cve14 + Paseos laborados + Alumbramiento o patermidad
    + Pago Clausula 77 C.C.T Cve 45 + Ayuda por fallecimiento  + Horas trabajadas en días festivos
     */
    private Double calcularSalarioVariableTotalTransportacion(Double difSueldoOrdinarioFormula,
            Double diferenciaFondoAhorroFormula,
            Double diferenciaSueldoOrdinario,
            Double diferenciaFormulaEstimuloVacaciones,
            Double primaDominical,
            Double diferenciaJornada,
            Double paseosLaborados,
            Double estimuloApegoReglamento,
            Double bonoProductividadCalidad,
            Double ayudaAlumbramientoPaternidad,
            Double clausula77,
            Double ayudaFallecimiento,
            Double horasTrabajadasDiaFestivo
    ) {
        return Math.round((difSueldoOrdinarioFormula + diferenciaFondoAhorroFormula + diferenciaSueldoOrdinario
                + diferenciaFormulaEstimuloVacaciones
                + primaDominical + diferenciaJornada + paseosLaborados + estimuloApegoReglamento + bonoProductividadCalidad
                + ayudaAlumbramientoPaternidad + clausula77 + ayudaFallecimiento + horasTrabajadasDiaFestivo) * 100.0) / 100.0;
    }

    /*SALARIO VARIABLE NÓMINA DE CONFIANZA
    Diferencia sueldo ordinario por fórmula + descansos laborados confianza (cve7) + diferencia estímulo vacaciones
    prima dominical (cve11) + estímulo por apego al reglamento (cve23) + bono productividad y calidad (cve24) 
    + horas trabajadas en día festivo (cve222)
     */
    private Double calcularSalarioVariableTotalConfianza(Double difSueldoOrdinarioFormula,
            Double descansosLaboradosCfza,
            Double diferenciaEstimuloVacaciones,
            Double primaDominical,
            Double estimuloApegoReglamento,
            Double bonoProductividadCalidad,
            Double horasTrabajadasDiaFestivo
    ) {
        return Math.round((difSueldoOrdinarioFormula
                + descansosLaboradosCfza
                + diferenciaEstimuloVacaciones
                + primaDominical
                + estimuloApegoReglamento
                + bonoProductividadCalidad
                + horasTrabajadasDiaFestivo) * 100.0) / 100.0;
    }

    //*******************CONSULTA A REPOSITORIOS*****************************
    public List<SDIVariosDTO> obtenDatosVarios(List<Integer> periodosBimestre) {
        // Convierte la lista de enteros a una cadena en formato separado por comas
        String periodosArrayStr = periodosBimestre.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        List<Object[]> resultados = pagos_Calculos_Percepcion_1Repository.conceptos_sdi_varios(periodosArrayStr);
        return resultados.stream()
                .map(this::mapToSdiVariosDto)
                .collect(Collectors.toList());
    }

    public List<SDITransportacionDTO> obtenDatosTrasnportacion(List<Integer> periodosBimestre) {
        // Convierte la lista de enteros a una cadena en formato separado por comas
        String periodosArrayStr = periodosBimestre.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        List<Object[]> resultados = pagos_Calculos_Percepcion_2Repository.conceptos_sdi_transportacion(periodosArrayStr);
        return resultados.stream()
                .map(this::mapToSdiTransportacionDto)
                .collect(Collectors.toList());
    }

    public List<SDIConfianzaDTO> obtenDatosConfianza(List<Integer> periodosBimestre) {
        // Convierte la lista de enteros a una cadena en formato separado por comas
        String periodosArrayStr = periodosBimestre.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        List<Object[]> resultados = pagos_Calculos_Percepcion_3Repository.conceptos_sdi_confianza(periodosArrayStr);
        return resultados.stream()
                .map(this::mapToSdiConfianzaDto)
                .collect(Collectors.toList());
    }

    public List<SDIEstructuraDTO> obtenDatosEstructura(List<Integer> periodosBimestre) {
        // Convierte la lista de enteros a una cadena en formato separado por comas
        String periodosArrayStr = periodosBimestre.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        List<Object[]> resultados = pagos_Calculos_Percepcion_5Repository.conceptos_sdi_estructura(periodosArrayStr);
        return resultados.stream()
                .map(this::mapToSdiEstructuraDto)
                .collect(Collectors.toList());
    }

    //**************************MAPEO OBJETOS A DTO**************************
    private SDIVariosDTO mapToSdiVariosDto(Object[] result) {
        return new SDIVariosDTO(
                (Integer) result[0], // id_trabajador
                (String) result[1], // expediente
                (String) result[2], // nombre
                (String) result[3], // apellido_paterno
                (String) result[4], // apellido_materno
                (String) result[5], // num_imss_primeros_10
                (String) result[6], // num_imss_digito_verificador
                (String) result[7], // rfc
                (String) result[8], // desc_genero
                (String) result[9], // hijos_si_no
                (Double) result[10], // sueldo_diario
                (String) result[11], // desc_nomina
                convertToLocalDate(result[12]), // fecha_ingreso
                (Double) result[13], // total_cve_1
                (Double) result[14], // total_cve_3
                (Double) result[15], // total_cve_4
                (Double) result[16], // total_cve_9
                (Double) result[17], // total_cve_10
                (Double) result[18], // total_cve_11
                (Double) result[19], // total_cve_15
                (Double) result[20], // total_cve_16
                (Double) result[21], // total_cve_17
                (Double) result[22], // total_cve_18
                (Double) result[23], // total_cve_23
                (Double) result[24], // total_cve_24
                (Double) result[25], // total_cve_26
                (Double) result[26], // total_cve_30
                (Double) result[27], // vales_despensa_pension_alim
                (Double) result[28], // total_cve_33
                (Double) result[29], // total_cve_41
                (Double) result[30], // total_cve_42
                (Double) result[31], // total_cve_207
                (Double) result[32], // total_cve_217
                (Double) result[33], // total_cve_222
                (Double) result[34], // total_cve_231
                (Double) result[35], // total_cve_1002
                (Double) result[36], // total_cve_1008
                (Double) result[37], // total_cve_1101
                (Double) result[38], // total_cve_1210
                (Double) result[39], // total_cve_1211
                (Double) result[40], // total_cve_1212
                (Double) result[41], // total_cve_1213
                (Double) result[42] // total_cve_1220
        );
    }

    private SDITransportacionDTO mapToSdiTransportacionDto(Object[] result) {
        return new SDITransportacionDTO(
                (Integer) result[0], // id_trabajador
                (String) result[1], // expediente
                (String) result[2], // nombre
                (String) result[3], // apellido_paterno
                (String) result[4], // apellido_materno
                (String) result[5], // num_imss_primeros_10
                (String) result[6], // num_imss_digito_verificador
                (String) result[7], // rfc
                (String) result[8], // desc_genero
                (String) result[9], // hijos_si_no
                (Double) result[10], // sueldo_diario
                (String) result[11], // desc_nomina
                convertToLocalDate(result[12]), // fecha_ingreso
                (Double) result[13], // total_cve_1
                (Double) result[14], // total_cve_3
                (Double) result[15], // total_cve_4
                (Double) result[16], // total_cve_9
                (Double) result[17], // total_cve_10
                (Double) result[18], // total_cve_11
                (Double) result[19], // total_cve_12
                (Double) result[20], // total_cve_14
                (Double) result[21], // total_cve_15
                (Double) result[22], // total_cve_16
                (Double) result[23], // total_cve_17
                (Double) result[24], // total_cve_18
                (Double) result[25], // total_cve_19
                (Double) result[26], // total_cve_23
                (Double) result[27], // total_cve_24
                (Double) result[28], // total_cve_26
                (Double) result[29], // total_cve_27
                (Double) result[30], // total_cve_30
                (Double) result[31], // vales_despensa_pension_alim
                (Double) result[32], // total_cve_32
                (Double) result[33], // total_cve_33
                (Double) result[34], // total_cve_39
                (Double) result[35], // total_cve_41
                (Double) result[36], // total_cve_42
                (Double) result[37], // total_cve_45
                (Double) result[38], // total_cve_207
                (Double) result[39], // total_cve_217
                (Double) result[40], // total_cve_222
                (Double) result[41], // total_cve_231
                (Double) result[42], // total_cve_1002
                (Double) result[43], // total_cve_1008
                (Double) result[44], // total_cve_1101
                (Double) result[45], // total_cve_1210
                (Double) result[46], // total_cve_1211
                (Double) result[47], // total_cve_1212
                (Double) result[48], // total_cve_1213
                (Double) result[49] // total_cve_1220
        );
    }

    private SDIConfianzaDTO mapToSdiConfianzaDto(Object[] result) {
        return new SDIConfianzaDTO(
                (Integer) result[0], // id_trabajador
                (String) result[1], // expediente
                (String) result[2], // nombre
                (String) result[3], // apellido_paterno
                (String) result[4], // apellido_materno
                (String) result[5], // num_imss_primeros_10
                (String) result[6], // num_imss_digito_verificador
                (String) result[7], // rfc
                (String) result[8], // desc_genero
                (Integer) result[9], // hijos_si_no
                (Integer) result[10], // prestaciones_si_no
                (Double) result[11], // sueldo_diario
                (String) result[12], // desc_nomina
                convertToLocalDate(result[13]), // fecha_ingreso
                (Double) result[14], // total_cve_1
                (Double) result[15], // total_cve_2
                (Double) result[16], // total_cve_4
                (Double) result[17], // total_cve_6
                (Double) result[18], // total_cve_7
                (Double) result[19], // total_cve_10
                (Double) result[20], // total_cve_11
                (Double) result[21], // total_cve_16
                (Double) result[22], // total_cve_17
                (Double) result[23], // total_cve_18
                (Double) result[24], // total_cve_21
                (Double) result[25], // total_cve_23
                (Double) result[26], // total_cve_24
                (Double) result[27], // total_cve_26
                (Double) result[28], // total_cve_30
                (Double) result[29], // vales_despensa_pension_alim
                (Double) result[30], // total_cve_32
                (Double) result[31], // total_cve_33
                (Double) result[32], // total_cve_207
                (Double) result[33], // total_cve_222
                (Double) result[34], // total_cve_231
                (Double) result[34], // total_cve_1002
                (Double) result[36], // total_cve_1021
                (Double) result[37], // total_cve_1101
                (Double) result[38], // total_cve_1210
                (Double) result[39], // total_cve_1211
                (Double) result[40], // total_cve_1212
                (Double) result[41], // total_cve_1213
                (Double) result[42], // total_cve_1214
                (Double) result[43] // total_cve_1220
        );
    }

    private SDIEstructuraDTO mapToSdiEstructuraDto(Object[] result) {
        return new SDIEstructuraDTO(
                (Integer) result[0], // id_trabajador
                (String) result[1], // expediente
                (String) result[2], // nombre
                (String) result[3], // apellido_paterno
                (String) result[4], // apellido_materno
                (String) result[5], // num_imss_primeros_10
                (String) result[6], // num_imss_digito_verificador
                (String) result[7], // rfc
                (String) result[8], // desc_genero
                (String) result[9], // hijos_si_no
                (Double) result[10], // sueldo_diario
                (String) result[11], // desc_nomina
                convertToLocalDate(result[12]), // fecha_ingreso
                (Double) result[13], // total_cve_1
                (Double) result[14], // total_cve_10
                (Double) result[15], // total_cve_18
                (Double) result[16], // total_cve_21
                (Double) result[17], // total_cve_28
                (Double) result[18], // total_cve_1002
                (Double) result[19], // total_cve_1021
                (Double) result[20], // total_cve_1210
                (Double) result[21], // total_cve_1211
                (Double) result[22] // total_cve_1212
        );
    }

    private LocalDate convertToLocalDate(Object timestamp) {
        if (timestamp instanceof Timestamp) {
            return ((Timestamp) timestamp).toLocalDateTime().toLocalDate();
        }
        return null;
    }

}
