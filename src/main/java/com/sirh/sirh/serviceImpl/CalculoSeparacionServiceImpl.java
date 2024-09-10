/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.FiniquitoDTO;
import com.sirh.sirh.DTO.IncidenciasCardexGeneralDTO;
import com.sirh.sirh.entity.Cat_Tipo_Nomina;
import com.sirh.sirh.entity.Trabajador;
import com.sirh.sirh.DTO.Trabajador_SueldoDTO;
import com.sirh.sirh.entity.Cat_Impuestos_Nomina;
import com.sirh.sirh.entity.Trabajador_plaza;
import com.sirh.sirh.repository.Cat_Impuestos_NominaRepository;
import com.sirh.sirh.repository.IncidenciasRepository;
import com.sirh.sirh.repository.Pagos_Percepciones_1Repository;
import com.sirh.sirh.repository.Pagos_Percepciones_2Repository;
import com.sirh.sirh.repository.Pagos_Percepciones_3Repository;
import com.sirh.sirh.repository.Pagos_Percepciones_4Repository;
import com.sirh.sirh.repository.Pagos_Percepciones_5Repository;
import com.sirh.sirh.repository.Pension_Alimenticia_OJRepository;
import com.sirh.sirh.repository.PlazaRepository;
import com.sirh.sirh.repository.Propiedades_NominaRepository;
import com.sirh.sirh.repository.TrabajadorRepository;
import com.sirh.sirh.repository.Trabajador_plazaRepository;
import com.sirh.sirh.repository.VacacionesRepository;
import com.sirh.sirh.service.CalculoSeparacionService;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rroscero23
 */
@Service
@Transactional
public class CalculoSeparacionServiceImpl implements CalculoSeparacionService {

    @Autowired
    PlazaRepository plazaRepository;

    @Autowired
    Pension_Alimenticia_OJRepository pension_Alimenticia_OJRepository;

    @Autowired
    Trabajador_plazaRepository trabajador_plazaRepository;

    @Autowired
    TrabajadorRepository trabajadorRepository;

    @Autowired
    Cat_Impuestos_NominaRepository cat_Impuestos_NominaRepository;

    @Autowired
    Propiedades_NominaRepository propiedades_NominaRepository;

    @Autowired
    VacacionesRepository vacacionesRepository;

    @Autowired
    Pagos_Percepciones_1Repository pagos_Percepciones_1Repository;

    @Autowired
    Pagos_Percepciones_2Repository pagos_Percepciones_2Repository;

    @Autowired
    Pagos_Percepciones_3Repository pagos_Percepciones_3Repository;

    @Autowired
    Pagos_Percepciones_4Repository pagos_Percepciones_4Repository;

    @Autowired
    Pagos_Percepciones_5Repository pagos_Percepciones_5Repository;

    @Autowired
    IncidenciasRepository incidenciasRepository;

    @Override
    public FiniquitoDTO calculaFiniquitoTrabajador(Integer idTrabajador, Boolean finiquito, Boolean jubilacion,
            Boolean pagoMarcha, Boolean fRenunciaVol,
            Boolean fRecisionContrato, Boolean fRemocionCargo,
            Double anios, Double meses, Double dias, LocalDate fechaIngreso, LocalDate fechaBaja) {

        //Consultas generales a Repositorios
        Trabajador_plaza infoPlaza = trabajador_plazaRepository.findPlazaTrabajador(idTrabajador);
        Trabajador infoTrabajador = trabajadorRepository.findById(idTrabajador).get();
        Cat_Tipo_Nomina infoNomina = infoPlaza.getCat_Tipo_Nomina();
        Integer idNomina = infoNomina.getId_tipo_nomina();
        Integer prestacionesSiNo = infoTrabajador.getPrestaciones_si_no();

        // Cálculo de indemnización por despido
        Double mesesIndemnizacion = calcularMesesIndemnizacion();
        Double diasIndemnizacionVeinte = calcularVeinteDiasPorAnio(anios);
        Double diasPrimaAntiguedad = calcularDiasPrimaPorAntiguedad(anios, meses);

        // Cálculo de días de vacaciones y aguinaldo
        Double diasVacaciones = calculaDiasVacacionesPrimaVacacional(idTrabajador, idNomina, fechaIngreso, fechaBaja);
        String periodoVacaciones = generaPeriodosVacaciones(fechaBaja);
        Double diasContratoAguinaldo = obtenDiasContratoAguinaldo(idTrabajador, idNomina, fechaIngreso, fechaBaja);
        Double diasAguinaldo = calculaDiasAguinaldo(idTrabajador, idNomina, prestacionesSiNo, diasContratoAguinaldo, fechaIngreso, fechaBaja);
        Integer periodoAguinaldo = generaPeriodoAguinaldo(fechaBaja);
        // Cálculo de integración salarial y otros valores
        Double totalDiarioIntegracionSalarial = calculaIntegracionSalarial(idTrabajador, idNomina);
        Double salarioIntegradoMensual = totalDiarioIntegracionSalarial * 30;

        // Obtención de sueldo tabulado
        Map<String, Double> valores = recuperarSueldoTabuladoTrabajador(idTrabajador);
        Double sueldoDiarioTabulado = valores.get("sueldoDiarioTabulado");
        Double sueldoMensualTabulado = valores.get("sueldoMensual");

        // Cálculo de compensación por antigüedad
        Map<String, Double> valoresCompAntiguedad = calculaMesesCompensacionAntiguedad(idTrabajador, anios, meses, salarioIntegradoMensual);
        Double mesesCompAntiguedad = valoresCompAntiguedad.get("mesesCompensacion");

        // Cálculo de importes unitarios
        Double importeUnitarioPrimaAntiguedad = calculaImportePrimaAntiguedad(idNomina, idTrabajador, totalDiarioIntegracionSalarial);
        Double importeUnitarioPrimaVacacional = calculaImportePrimaVacacional(idTrabajador, idNomina, sueldoDiarioTabulado);

        // Cálculo de totales por percepciones
        Double totalIndemnizacion = calcularProducto(mesesIndemnizacion, salarioIntegradoMensual);
        Double totalVeinteDiasAnio = calcularProducto(diasIndemnizacionVeinte, totalDiarioIntegracionSalarial);
        Double totalPrimaAntiguedad = calcularProducto(diasPrimaAntiguedad, importeUnitarioPrimaAntiguedad);
        Double totalCompAntig = valoresCompAntiguedad.get("compensacionMonetaria");
        Double totalFiniqIndemnizacion = calcularTotalIndemnizacion(totalIndemnizacion, totalVeinteDiasAnio, totalPrimaAntiguedad, totalCompAntig);
        Double totalDiasVacaciones = calcularProducto(diasVacaciones, sueldoDiarioTabulado);
        Double totalPrimaVacacional = calcularProducto(diasVacaciones, importeUnitarioPrimaVacacional);
        Double totalAguinaldo = calcularProducto(diasAguinaldo, sueldoDiarioTabulado);

        // Cálculo de ISR para indemnización y finiquito
        Double isrTotalIndemnizacion = calcularIsrAnual(totalFiniqIndemnizacion, idNomina, anios, true);
        Double totalFiniquitos = calcularTotalFiniquito(totalDiasVacaciones, totalPrimaVacacional, totalAguinaldo);
        Double isrTotalFiniquito = calcularIsrAnual(totalFiniquitos, idNomina, anios, false);
        //Búsqueda de pensiones alimenticias activas que apliquen a finiquito (Porcentaje)
        List<Double> porcentajesPensiones = pension_Alimenticia_OJRepository.findPorcentajePensionesActivas(idTrabajador);
        //Búsqueda de pensiones alimenticias activas que apliquen a finiquito (Cuota fija)
        List<Double> cuotaFijaPensiones = pension_Alimenticia_OJRepository.findCuotaFijaPensionesActivas(idTrabajador);
        // Cálculo de importes totales por deducciones
        Double totalDeducciones = calcularTotalDeducciones(isrTotalIndemnizacion, isrTotalFiniquito);
        // Creación y asignación de valores a FiniquitoDTO
        FiniquitoDTO finiquitoDTO = new FiniquitoDTO();
        finiquitoDTO.setMesesIndemnizacion(mesesIndemnizacion);
        finiquitoDTO.setVeinteDiasAnio(diasIndemnizacionVeinte);
        finiquitoDTO.setDiasPrimaAntiguedad(diasPrimaAntiguedad);
        finiquitoDTO.setMesesCompensacionAntiguedad(mesesCompAntiguedad);
        finiquitoDTO.setDiasVacaciones(diasVacaciones);
        finiquitoDTO.setDiasPrimaVacacional(diasVacaciones);
        finiquitoDTO.setDiasAguinaldo(diasAguinaldo);
        finiquitoDTO.setPeriodoVacacionalStr(periodoVacaciones);
        finiquitoDTO.setPeriodoAguinaldoAnio(periodoAguinaldo);
        finiquitoDTO.setSueldoDiarioTabulado(sueldoDiarioTabulado);
        finiquitoDTO.setSueldoMensualTabulado(sueldoMensualTabulado);
        finiquitoDTO.setSalarioDiarioIntegrado(totalDiarioIntegracionSalarial);
        finiquitoDTO.setImporteUnitarioPrimaAntiguedad(importeUnitarioPrimaAntiguedad);
        finiquitoDTO.setImporteUnitarioPrimaVacacional(importeUnitarioPrimaVacacional);
        finiquitoDTO.setTotalMesesIndemnizacion(totalIndemnizacion);
        finiquitoDTO.setTotalVeinteDiasAnio(totalVeinteDiasAnio);
        finiquitoDTO.setTotalDiasPrimaAntiguedad(totalPrimaAntiguedad);
        finiquitoDTO.setTotalMesesCompAntiguedad(totalCompAntig);
        finiquitoDTO.setTotalDiasVacaciones(totalDiasVacaciones);
        finiquitoDTO.setTotaDiasPrimaVacacional(totalPrimaVacacional);
        finiquitoDTO.setTotalDiasAguinaldo(totalAguinaldo);
        //PENSIONES ALIMENTICIAS ENCONTRADAS PORCENTAJES
        finiquitoDTO.setPension1(porcentajesPensiones.size() > 0 ? porcentajesPensiones.get(0) : null);
        finiquitoDTO.setPension2(porcentajesPensiones.size() > 1 ? porcentajesPensiones.get(1) : null);
        finiquitoDTO.setPension3(porcentajesPensiones.size() > 2 ? porcentajesPensiones.get(2) : null);
        finiquitoDTO.setPension4(porcentajesPensiones.size() > 3 ? porcentajesPensiones.get(3) : null);
        finiquitoDTO.setPension5(porcentajesPensiones.size() > 4 ? porcentajesPensiones.get(4) : null);
        //PENSIONES ALIMENTICIAS ENCONTRADAS CUOTA FIJA
        finiquitoDTO.setPension1Fija(cuotaFijaPensiones.size() > 0 ? cuotaFijaPensiones.get(0) : null);
        finiquitoDTO.setPension2Fija(cuotaFijaPensiones.size() > 1 ? cuotaFijaPensiones.get(1) : null);
        finiquitoDTO.setPension3Fija(cuotaFijaPensiones.size() > 2 ? cuotaFijaPensiones.get(2) : null);
        finiquitoDTO.setPension4Fija(cuotaFijaPensiones.size() > 3 ? cuotaFijaPensiones.get(3) : null);
        finiquitoDTO.setPension5Fija(cuotaFijaPensiones.size() > 4 ? cuotaFijaPensiones.get(4) : null);
        finiquitoDTO.setIsrIndemnizacion(isrTotalIndemnizacion);
        finiquitoDTO.setIsrIndemnizacion(isrTotalIndemnizacion);
        finiquitoDTO.setIsrFiniquito(isrTotalFiniquito);
        finiquitoDTO.setTotalDeducciones(totalDeducciones);
        finiquitoDTO.setSalarioMensualIntegrado(salarioIntegradoMensual);

        return finiquitoDTO;
    }

    //************************FUNCIONES DE CÁLCULOS POR SEPARACION************************
    /*Artículo 50. LFT-
    III. Además de las indemnizaciones a que se refieren las fracciones anteriores, en el importe de
    tres meses de salario y el pago de los salarios vencidos e intereses
    La indeminzacion constitucional solo implica el pago de tres meses de salario integrado, salarios caídos
    y demás prestaciones que hubiere devengado o que le otorgue expresamente la ley o los contraros de trabajo*/
    private Double calcularMesesIndemnizacion() {
        //Corresponden tres meses
        return 3.0;
    }

    /*Artículo 50.- Las indemnizaciones a que se refiere el artículo anterior consistirán:
    I. Si la relación de trabajo fuere por tiempo determinado menor de un año, en una cantidad igual
    al importe de los salarios de la mitad del tiempo de servicios prestados; si excediera de un año,
    en una cantidad igual al importe de los salarios de seis meses por el primer año  y de veinte días
    por cada uno de los años siguientes en que hubiese prestado sus servicios;*/
    private Double calcularVeinteDiasPorAnio(Double anios) {
        //Cantidad de días por años de antiguedad
        return anios * 20;
    }

    /*Artículo 162. LFT- Los trabajadores de planta tienen derecho a una prima de antigüedad, de conformidad
        con las normas siguientes:
        I. La prima de antigüedad consistirá en el importe de doce días de salario, por cada año de
           servicio...
    El restante de meses se sumará a la cantidad de días que le correspondan por antiguedad*/
    private Double calcularDiasPrimaPorAntiguedad(Double anios, Double meses) {
        //El número de años por doce días mas el número de meses remanentes
        return (anios * 12) + meses;
    }

    /*Solo aplicable a los trabajadores con prestaciones
    CAPITULO X DEL CCT
    De las antiguedades
    Cláusula 132. El servicio dará a sus trabajadores una compensación por antiguedad que adquieran en sus labores
    cuando dejen de pertenecer a él, de acuerdo con las condiciones que en seguida se expresan:
    A)Los que hayan servido sin interrupción de 2 a 6 años, el equivalente a medio mes de salario por cada año;
    compuesto por cuota tabulada, vales de despensa, prima vacacional y aguinaldo que por cuota diaria perciban.
    B)A los que hayan servido sin interrupción por más de 6 años se les pagará por los primeros 6 años, la compensación
    anterior, y por los restantes, el equivalente a un mes por año, compuesto por cuota tabulada, vales de despensa,
    prima vacacional y aguinaldo que por cuota diaria perciban.
    
    Cláusula 135. El tiempo de servicios continuos se computarán por años completos contándose a partir de la fecha del último
    ingreso del trabajador al Servicio. Para los trabajadores que tengan menos de dieciséis años de servicio toda fracción
    mayor a 7 meses se computará como un año completo y aquellos que tengan más de dieciséis años de servicios, toda 
    fracción mayor de cinco meses se computará como año completo*/
    private Map<String, Double> calculaMesesCompensacionAntiguedad(Integer idTrabajador, Double anios, Double meses, Double salarioIntegradoMensual) {
        //Se verifica si el trabajador cuenta con prestaciones de CCT 
        Trabajador infoTrabajador = trabajadorRepository.findById(idTrabajador).get();
        Integer prestacionesSiNo = infoTrabajador.getPrestaciones_si_no();
        //Si el trabajador no tiene prestaciones entonces se procede a retornar sin el cálculo
        if (prestacionesSiNo == 2) {
            // Si no tiene prestaciones, retornar un mapa vacío con ceros
            return crearMapaCompensacionAnt(0.0, 0.0);
        }
        Double aniosAjustados = anios;
        // Ajuste de años según los meses y los años de antigüedad cláusula 135
        if ((anios < 16.0 && meses > 6.0) || (anios >= 16.0 && meses > 4.0)) {
            aniosAjustados += 1;
        }
        Double totalMesesCompensacion = 0.0;
        //Las comparaciones y conidciones se realizan con los años reales
        if (anios >= 2.0) {
            if (anios <= 6.0) {
                totalMesesCompensacion = aniosAjustados * 0.5;
            } else {
                totalMesesCompensacion = 3.0; // 6 años * 0.5 (primeros 6 años)
                totalMesesCompensacion += (aniosAjustados - 6.0); // Añadir años adicionales
            }
        }
        // Calcular la compensación monetaria total
        Double compensacionTotal = totalMesesCompensacion * salarioIntegradoMensual;
        //Se retorna el mapa de valores para devolver de esta misma función el total en meses y el total en términos monetarios
        Map<String, Double> datos = crearMapaCompensacionAnt(totalMesesCompensacion, compensacionTotal);
        return datos;
    }

    //Se crea un mapa que retorna los valores correspondientes a la compensación por antiguedad
    private Map<String, Double> crearMapaCompensacionAnt(Double mesesComp, Double importeMesesComp) {
        Map<String, Double> mapaVacio = new HashMap<>();
        mapaVacio.put("mesesCompensacion", mesesComp);
        mapaVacio.put("compensacionMonetaria", importeMesesComp);
        return mapaVacio;
    }

    /*Se calculan los días de vacaciones correspondientes al trabajador junto al periodo considerado*/
    private double calculaDiasVacacionesPrimaVacacional(Integer idTrabajador, Integer idNomina, LocalDate fechaIngreso, LocalDate fechaBaja) {
        // Consultar los días disponibles pagados en el último periodo registrado del trabajador, o retornar 0 si no existen
        Integer diasRestantesDisponibles = Optional.ofNullable(vacacionesRepository.buscarDiasVacDisponibles(idTrabajador)).orElse(0);
        // Si no tiene registro de vacaciones en la tabla de vacaciones, buscar las vacaciones correspondientes por contrato
        if (diasRestantesDisponibles == 0) {
            // Consultar la información del trabajador
            Trabajador infoTrabajador = trabajadorRepository.findById(idTrabajador).orElseThrow(() -> new NoSuchElementException("Trabajador no encontrado"));
            Integer prestacionesSiNo = infoTrabajador.getPrestaciones_si_no();
            long diferenciaDiasFechIngresoFechActual = calculaDiferenciaDias(fechaIngreso, LocalDate.now());
            // Calcular días de vacaciones correspondientes
            diasRestantesDisponibles = calcularDiasVacacionesCorrespondientes(idNomina, prestacionesSiNo, diferenciaDiasFechIngresoFechActual);
        }
        // Obtener el año actual
        int yearActual = Year.now().getValue();
        // Ajustar la fecha de ingreso al año actual y al año anterior
        LocalDate fechaIngresoAnioActual = fechaIngreso.withYear(yearActual);
        LocalDate fechaIngresoAnioAnterior = fechaIngresoAnioActual.minusYears(1);
        // Calcular la diferencia de días entre las fechas ajustadas y la fecha de baja
        long diferenciaDiasAniosActuales = ChronoUnit.DAYS.between(fechaIngresoAnioActual, fechaBaja.plusDays(1));
        long diferenciaDiasAniosDiferentes = ChronoUnit.DAYS.between(fechaIngresoAnioAnterior, fechaBaja.plusDays(1));
        // Determinar los días de diferencia según las condiciones
        long diasDiferencia = (fechaIngreso.getMonthValue() < fechaBaja.getMonthValue()
                || (fechaIngreso.getMonthValue() == fechaBaja.getMonthValue() && fechaIngreso.getDayOfMonth() <= fechaBaja.getDayOfMonth()))
                ? diferenciaDiasAniosActuales
                : diferenciaDiasAniosDiferentes;
        // Limitar los días de diferencia a un máximo de un año
        diasDiferencia = Math.min(diasDiferencia, 365);
        // Calcular los días de vacaciones correspondientes
        return diasRestantesDisponibles * ((double) diasDiferencia / 365);
    }

    //Genera la cadena para los periodos de las vacaciones
    private String generaPeriodosVacaciones(LocalDate fechaBaja) {
        Integer anio = fechaBaja.getYear();
        Integer anioMasUno = anio + 1;
        return anio + "/" + anioMasUno;
    }

    //Obtiene el periodo de aguinaldo calculado
    private Integer generaPeriodoAguinaldo(LocalDate fechaBaja) {
        Integer anio = fechaBaja.getYear();
        return anio;
    }

    /*Se calculan los días de aguinaldo junto al periodo considerado*/
    private Double obtenDiasContratoAguinaldo(Integer idTrabajador, Integer idNomina, LocalDate fechaIngreso, LocalDate fechaBaja) {
        // Obtener el año de la fecha de baja
        Integer anio = fechaBaja.getYear();
        Double diasdeContratoPagoAguinaldo = 0.0;
        // Calcular los días de aguinaldo según el tipo de nómina
        switch (idNomina) {
            case 1:
                diasdeContratoPagoAguinaldo = pagos_Percepciones_1Repository.findDiasContratoAguinaldo1(anio, idTrabajador);
                break;
            case 2:
                diasdeContratoPagoAguinaldo = pagos_Percepciones_2Repository.findDiasContratoAguinaldo2(anio, idTrabajador);
                break;
            case 3:
                diasdeContratoPagoAguinaldo = pagos_Percepciones_3Repository.findDiasContratoAguinaldo3(anio, idTrabajador);
                break;
            case 4:
                diasdeContratoPagoAguinaldo = pagos_Percepciones_4Repository.findDiasContratoAguinaldo4(anio, idTrabajador);
                break;
            case 5:
                diasdeContratoPagoAguinaldo = pagos_Percepciones_5Repository.findDiasContratoAguinaldo5(anio, idTrabajador);
                break;
            default:
                return 0.0;
        }
        // Verificar si los días de contrato son nulos y retornar cero en tal caso
        return diasdeContratoPagoAguinaldo != null ? diasdeContratoPagoAguinaldo : 0.0;
    }

    private Double calculaDiasAguinaldo(Integer idTrabajador, Integer idNomina, Integer prestacionesSiNo, Double diasContratoAguinaldo, LocalDate fechaIngreso, LocalDate fechaBaja) {
        //Se consulta la propiedad por nómina para obtener los días de aguinaldo y días del año
        Double propiedadDiasAguinaldo = propiedades_NominaRepository.findDiasAguinaldo(idNomina);
        Double propiedadDiasAnio = propiedades_NominaRepository.findDiasAnio(idNomina);
        //Se valida si se recibe un valor de dias de contrato para aguinaldo
        if (diasContratoAguinaldo == null || diasContratoAguinaldo <= 0) {
            return 0.0;
        }
        //Variable final a retornar
        Double diasAguinaldoCalculado = 0.0;
        //Se obtienen los años de las dos fechas
        int anioIngreso = fechaIngreso.getYear();
        int anioBaja = fechaBaja.getYear();
        //Variable para detectar la diferencia de días entre la fecha calculada y la fecha de pagos de anticipos
        long diferenciaDiasPago = 0;
        // Ajustar la fecha de ingreso al año actual y al año anterior
        LocalDate fechaCalculo = LocalDate.now();
        //Fecha de pagos para los anticipos
        LocalDate fechaPagoAnticipo = LocalDate.now();
        LocalDate fechaPagoAnticipo1 = LocalDate.of(anioBaja, 4, 30);
        LocalDate fechaPagoAnticipo2 = LocalDate.of(anioBaja, 8, 31);
        LocalDate fechaPagoAnticipo3 = LocalDate.of(anioBaja, 12, 31);
        //Se define la fecha que se utilizará para el cálculo según los años de la fecha de ingreso y fecha de baja
        if (anioIngreso < anioBaja) {
            fechaCalculo = LocalDate.of(anioBaja, 1, 1);
        } else if (anioIngreso == anioBaja) {
            fechaCalculo = fechaIngreso;
        }
        //Se calcula la diferencia entre días de la fecha de cálculo y la fecha de liquidacion
        long diferenciaDias = ChronoUnit.DAYS.between(fechaCalculo, fechaBaja);
        /*Asignación de anticipos 1,2,3 para el aguinaldo
        Anticipo 1*/
        if (diasContratoAguinaldo >= 1 && diasContratoAguinaldo <= 120) {
            diferenciaDiasPago = ChronoUnit.DAYS.between(fechaCalculo, fechaPagoAnticipo1);
            fechaPagoAnticipo = fechaPagoAnticipo1;
            //Anticipo 2
        } else if (diasContratoAguinaldo >= 121 && diasContratoAguinaldo <= 240) {
            diferenciaDiasPago = ChronoUnit.DAYS.between(fechaCalculo, fechaPagoAnticipo2);
            fechaPagoAnticipo = fechaPagoAnticipo2;
            //Anticipo 3
        } else if (diasContratoAguinaldo >= 241 && diasContratoAguinaldo <= 366) {
            diferenciaDiasPago = ChronoUnit.DAYS.between(fechaCalculo, fechaPagoAnticipo3);
            fechaPagoAnticipo = fechaPagoAnticipo3;
        }
        //Ajuste de diferencia dias y dias pago si se cumple la condicion
        if (diferenciaDias < diferenciaDiasPago) {
            diferenciaDias = (diferenciaDiasPago - diferenciaDias) * -1;
            diferenciaDiasPago = 0;
        }
        //Se obtienen las faltas del repositorio en el lapso de fechas indicado
        List<IncidenciasCardexGeneralDTO> faltas = incidenciasRepository.conteoIncidenciasKardexTrabajador(fechaPagoAnticipo.plusDays(1), fechaBaja, idTrabajador);
        // Variable para acumular la cantidad total de incidencias
        int totalFaltas = faltas.stream().mapToInt(dto -> dto.getCantidad_incidencias()).sum();
        // Calcular los días de aguinaldo según el tipo de nómina
        switch (idNomina) {
            //Nómina de confianza
            case 3:
                diasAguinaldoCalculado = (prestacionesSiNo == 1) ? (propiedadDiasAguinaldo * (diferenciaDias - (diferenciaDiasPago + (double) totalFaltas))) / propiedadDiasAnio : ((propiedadDiasAguinaldo - 20) * (diferenciaDias - (diferenciaDiasPago + (double) totalFaltas))) / propiedadDiasAnio;
            //Nómina de funcionarios
            case 5:
                return diasAguinaldoCalculado = (propiedadDiasAguinaldo * 2) * (diferenciaDias - (diferenciaDiasPago + (double) totalFaltas)) / propiedadDiasAnio;
            //Resto de las nóminas
            default:
                return diasAguinaldoCalculado = (propiedadDiasAguinaldo * (diferenciaDias - (diferenciaDiasPago + (double) totalFaltas))) / propiedadDiasAnio;
        }
    }

    /*Calcula el total de las percepciones obtenidas por el trabajador antes e impuestos (ISR)*/
    private void calculaTotalPercepcionesAntesDeImpuesto() {

    }

    /*Se me comentó que este cálculo de isr se realiza sobre los conceptos de:
    -Total vacaciones
    -Total prima vacacional
    -Total de aguinaldo
    Artículo 95 Impuesto anual de pagos por separación.
    Cuando se obtengan ingresos por concepto de primas de antigüedad, retiro e indemnizaciones u otros pagos, 
    por separación, se calculará el impuesto anual, conforme a las siguientes reglas:
    I.Del total de percepciones por este concepto, se separará una cantidad igual a la del último
    sueldo mensual ordinario, la cual se sumará a los demás ingresos por los que se deba pagar el impuesto en el año
    de calendario de que se trate y se calculará, en los términos de este Título, el impuesto correspondiente a 
    dichos ingresos. Cuando el total de las percepciones sean inferiores al último sueldo mensual ordinario,
    éstas se sumarán en su totalidad a los demás ingresos por los que se deba pagar el impuesto y no se aplicará
    la fracción II de este artículo.
    II.Al total de percepciones por este concepto se restará una cantidad igual a la del último sueldo mensual
    ordinario y al resultado se le aplicará la tasa que correspondió al impuesto que señala la fracción anterior.
    El impuesto que resulte se sumará al calculado conforme a la fracción que antecede.
    La tasa a que se refiere la fracción II que antecede se calculará dividiendo el impuesto señalado en la fracción I 
    anterior entre la cantidad a la cual se le aplicó la tarifa del artículo 152 de esta Ley; el cociente así obtenido
    se multiplica por cien y el producto se expresa en por ciento.*/
 /*Artículo 93 Ingresos exentos.
    No se pagará el impuesto sobre la renta por la obtención de los siguientes ingresos:
    XIII.Los que obtengan las personas que han estado sujetas a una relación laboral en el momento de su
    separación, por concepto de primas de antigüedad, retiro e indemnizaciones u otros pagos, así como los obtenidos
    con cargo a la subcuenta del seguro de retiro o a la subcuenta de retiro, cesantía en edad avanzada y vejez,
    previstas en la Ley del Seguro Social y los que obtengan los trabajadores al servicio del Estado con cargo a la
    cuenta individual del sistema de ahorro para el retiro, prevista en la Ley del Instituto de Seguridad y Servicios 
    Sociales de los Trabajadores del Estado, y los que obtengan por concepto del beneficio previsto en la Ley de Pensión
    Universal, hasta por el equivalente a noventa veces el salario mínimo general del área geográfica del contribuyente
    por cada año de servicio o de contribución en el caso de la subcuenta del seguro de retiro, de la subcuenta de retiro,
    cesantía en edad avanzada y vejez o de la cuenta individual del sistema de ahorro para el retiro. Los años de servicio
    serán los que se hubieran considerado para el cálculo de los conceptos mencionados. Toda fracción de más de seis meses
    se considerará un año completo. Por el excedente se pagará el impuesto en los términos de este Título.*/
    @Override
    public Double calcularIsrAnual(Double total, Integer idNomina, Double aniosServicio, Boolean flagIndemnizacion) {
        // Obtener el valor de la UMA si se trata de una indemnización para considerar parte exenta
        Double totalTemp = flagIndemnizacion
                ? Math.max(total - (propiedades_NominaRepository.findSalMinimo(idNomina) * 90 * aniosServicio), 0)
                : total;
        Integer year = LocalDate.now().getYear();
        List<Cat_Impuestos_Nomina> isrAnual = cat_Impuestos_NominaRepository.findIsptAnual(year);
        Boolean flagRangoEncontrado = false;
        for (Cat_Impuestos_Nomina impuesto : isrAnual) {
            Double limiteInferior = impuesto.getLimite_inferior();
            Double limiteSuperior = impuesto.getLimite_superior();
            if (totalTemp >= limiteInferior && totalTemp <= limiteSuperior) {
                flagRangoEncontrado = true;
                Double pocentajeSobreExcedente = impuesto.getPorcentaje_excedente_limite_inferior() / 100.0;
                Double cuotaFijaDinero = impuesto.getCuota_fija_dinero();
                Double diferenciaLimiteInferior = totalTemp - limiteInferior;
                Double porcentajeSobreDiferenciaLimiteInferior = pocentajeSobreExcedente * diferenciaLimiteInferior;
                Double isrCalculado = cuotaFijaDinero + porcentajeSobreDiferenciaLimiteInferior;
                return isrCalculado;
            }
        }
        if (!flagRangoEncontrado) {
            throw new RuntimeException("Error al calcular I.S.R. del trabajador");
        }
        return null;
    }

    /*Calcular total de deducciones*/
    private Double calcularTotalDeducciones(Double isrIndemnizacion, Double isrFiniquito) {
        return isrIndemnizacion + isrFiniquito;
    }

    /*Calcula el total final a percibir por el trabajador*/
    private void calcularTotalPercibirTrabajador() {

    }

    /*Artículo 485 y 486 de la LFT
    Artículo 485.- La cantidad que se tome como base para el pago de las indemnizaciones no podrá ser inferior al salario mínimo.
    Artículo 486.- Para determinar las indemnizaciones a que se refiere este título, si el salario que percibe el trabajador
    excede del doble del salario mínimo del área geográfica de aplicación a que corresponda el lugar de prestación
    del trabajo, se considerará esa cantidad como salario máximo. Si el trabajo se presta en lugares de diferentes
    áreas geográficas de aplicación, el salario máximo será el doble del promedio de los salarios mínimos respectivos.
    Me comentó Cynthia Romero, de nómina, que la prima de antiguedad se calcula diferente debido al aumento de salario mínimo,
    del puesto de nivel 27 hacia arriba se topa a dos salarios mínimos, si es de nivel 27 hacia abajo es con un salario integrado */
    private Double calculaImportePrimaAntiguedad(Integer idNomina, Integer idTrabajador, Double sueldoDiarioIntegrado) {
        Trabajador_SueldoDTO infoPuesto = plazaRepository.findSueldosTrabajador(idTrabajador);
        Integer nivelPuesto = infoPuesto.getNivel();
        if (nivelPuesto >= 27) {
            //Se busca con el método del repositorio el salario mínimo para finiquitos de la nómina del trabajador
            Double salarioMinVigente = propiedades_NominaRepository.findSalMinFiniquito(idNomina);
            return salarioMinVigente * 2;
        } else {
            return sueldoDiarioIntegrado;
        }
    }

    private Double calculaImportePrimaVacacional(Integer idTrabajador, Integer idNomina, Double sueldoDiarioTabulado) {
        //Consultas a repositorios para obtener nómina y prestaciones del trabajador así como la prima vacacional de la nómina con prestaciones
        Trabajador infoTrabajador = trabajadorRepository.findById(idTrabajador).get();
        Double proporcionPrimaVacacional = propiedades_NominaRepository.findProporcionPrimaVacacional(idNomina);
        Integer prestacionesSiNo = infoTrabajador.getPrestaciones_si_no();
        if ((idNomina == 3 && prestacionesSiNo == 1) || idNomina == 1 || idNomina == 2) {
            return sueldoDiarioTabulado * proporcionPrimaVacacional;
        } else {
            return sueldoDiarioTabulado * 0.25;
        }
    }

    //Funcion con mapa de valores para no repetir la misma consulta en otras funciones
    private Map<String, Double> recuperarSueldoTabuladoTrabajador(Integer idTrabajador) {
        Trabajador_SueldoDTO infoSueldo = plazaRepository.findSueldosTrabajador(idTrabajador);
        Map<String, Double> datos = new HashMap<>();
        datos.put("sueldoDiarioTabulado", infoSueldo.getSueldo_diario());
        datos.put("sueldoMensual", infoSueldo.getSueldoMensual());
        return datos;
    }

    private Double calculaIntegracionSalarial(Integer idTrabajador, Integer idNomina) {
        //Consultas a repositorios para saber qué información calcular
        Trabajador infoTrabajador = trabajadorRepository.findById(idTrabajador).get();
        Trabajador_SueldoDTO infoSueldo = plazaRepository.findSueldosTrabajador(idTrabajador);
        Integer prestacionesSiNo = infoTrabajador.getPrestaciones_si_no();
        LocalDate fechaIngresoTrabajador = infoTrabajador.getFecha_ingreso();
        Double sueldoMensual = infoSueldo.getSueldoMensual();
        LocalDate fechaIngreso = fechaIngresoTrabajador;
        LocalDate fechaActual = LocalDate.now();
        Long diferenciaDiasFechIngresoFechActual = calculaDiferenciaDias(fechaIngreso, fechaActual);
        //Calcula los días de vacaciones correspondientes según la antiguedad y su tabulador de días acorde a la nómina y prestaciones
        Integer diasVacacionesCorrespondientes = calcularDiasVacacionesCorrespondientes(idNomina, prestacionesSiNo, diferenciaDiasFechIngresoFechActual);
        //Conceptos generales para las nóminas
        Double salarioBase = calculaSalarioBase(sueldoMensual);
        Double valesDespensa = calculaValesDespensaVarios();
        //Cálculos específicos para cada nómina
        switch (idNomina) {
            //  ***********VARIOS***********
            case 1:
                Double primaVacacional = calculaPrimaVacacionalVarios(salarioBase, diasVacacionesCorrespondientes);
                Double aguinaldo = calculaAguinaldoVarios(salarioBase);
                Double totalIntegracionSalarialVarios = calculaTotalIntegracionSalarial(salarioBase, valesDespensa, primaVacacional, aguinaldo);
                return totalIntegracionSalarialVarios;
            //  ***********TRANSPORTACIÓN***********
            case 2:
                //Para transportacion
                //PENDIENTE OBTENER LAS HORAS DE CORRIDA PARA NO DEJAR EL 8.0 DE MANERA FIJA
                Double salarioPrimaVacacionalTransportacion = calculaSalarioPrimaVacacionalTransportacion(salarioBase, 8.0);
                Double primaVacacionalTransportacion = calcularPrimaVacacionalTransportacion(salarioPrimaVacacionalTransportacion, diasVacacionesCorrespondientes);
                Double aguinaldoTransportacion = calculaAguinaldoTransportacion(salarioBase);
                Double totalIntegracionSalarialTransp = calculaTotalIntegracionSalarialTransportacion(salarioPrimaVacacionalTransportacion, valesDespensa, primaVacacionalTransportacion, aguinaldoTransportacion);
                return totalIntegracionSalarialTransp;
            //  ***********CONFIANZA Y FUNCIONARIOS***********
            case 3:
            case 5:
                // Para confianza y estructura
                Double primaVacacionalCfzaFuncionarios = calculaPrimaVacacionalCfzaFuncionarios(salarioBase, diasVacacionesCorrespondientes);
                Double aguinaldoCfzaEstructura = calculaAguinaldoCfzaEstructura(salarioBase);
                Double totalIntegracionSalarialCfzaEstructura = calculaTotalIntegracionSalarial(salarioBase, 0.0, primaVacacionalCfzaFuncionarios, aguinaldoCfzaEstructura);
                return totalIntegracionSalarialCfzaEstructura;
            default:
                throw new RuntimeException("Nómina inválida");
        }
    }

    //Dependiendo de la nómina o prestaciones se toma un tabulador u otro
    private Integer calcularDiasVacacionesCorrespondientes(Integer idNomina, Integer prestacionesSiNo, Long diferenciaDiasFechIngresoFechActual) {
        if ((idNomina == 3 && prestacionesSiNo == 2) || idNomina == 5) {
            return tabuladorDiasVacacionesEstructuraCfzaSinPrestaciones(diferenciaDiasFechIngresoFechActual);
        } else {
            return tabuladorDiasVacacionesVariosTranspCfzaPrestaciones(diferenciaDiasFechIngresoFechActual);
        }
    }

    /*TABULADORES DE DÍAS DE VACACIONES SEGÚN ANTIGUEDAD Y NÓMINA O PRESTACIONES DEL TRABAJADOR*/
    private Integer tabuladorDiasVacacionesVariosTranspCfzaPrestaciones(Long diferenciaEnDias) {
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

    private Integer tabuladorDiasVacacionesEstructuraCfzaSinPrestaciones(Long diferenciaEnDias) {
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

    private Long calculaDiferenciaDias(LocalDate fechaIngreso, LocalDate fechaCorteSdi) {
        // Calcula la diferencia en días entre dos LocalDate
        long diferenciaEnDias = ChronoUnit.DAYS.between(fechaIngreso, fechaCorteSdi);
        return diferenciaEnDias;
    }

    private Double calculaSalarioBase(Double salarioMensual) {
        return (salarioMensual / 30);
    }
    //CÁLCULOS ESPECIFICOS PARA LAS NÓMINAS: VARIOS

    private Double calculaValesDespensaVarios() {
        //Incidencia 362 del catálogo de incidencias que corresponde a la cantidad mensual de vales
        Double cantidadMensualVales = propiedades_NominaRepository.findPropiedadNomina(362, 1);
        Double propiedadDiasAnio = propiedades_NominaRepository.findDiasAnio(1);
        return ((cantidadMensualVales * 13) / propiedadDiasAnio);
    }

    private Double calculaPrimaVacacionalVarios(Double salarioBase, Integer diasVacacionesCorrespondientes) {
        Double propiedadDiasAnio = propiedades_NominaRepository.findDiasAnio(1);
        return ((diasVacacionesCorrespondientes * 1.20 / propiedadDiasAnio) * salarioBase);
    }

    private Double calculaAguinaldoVarios(Double salarioBase) {
        return 0.1644 * salarioBase;
    }

    private Double calculaTotalIntegracionSalarial(Double salarioBase, Double valesDespensa, Double primaVacacional, Double aguinaldo) {
        return salarioBase + valesDespensa + primaVacacional + aguinaldo;
    }

    //TRANSPORTACION
    private Double calculaSalarioPrimaVacacionalTransportacion(Double salarioBase, Double corrida) {
        return (corrida / 8) * salarioBase;
    }

    private Double calcularPrimaVacacionalTransportacion(Double salarioPrimaVacacional, Integer diasVacaciones) {
        Double propiedadDiasAnio = propiedades_NominaRepository.findDiasAnio(2);
        return (diasVacaciones / propiedadDiasAnio) * salarioPrimaVacacional;
    }

    private Double calculaAguinaldoTransportacion(Double salarioBase) {
        Double propiedadDiasAnio = propiedades_NominaRepository.findDiasAnio(2);
        return (60.0 / propiedadDiasAnio) * salarioBase;
    }

    private Double calculaTotalIntegracionSalarialTransportacion(Double salarioPrimaVacacional, Double valesDespensa, Double primaVacacional, Double aguinaldo) {
        return salarioPrimaVacacional + valesDespensa + primaVacacional + aguinaldo;
    }

    //CONFIANZA Y FUNCIONARIOS
    private Double calculaPrimaVacacionalCfzaFuncionarios(Double salarioBase, Integer diasVacaciones) {
        return (diasVacaciones * 0.25 / 365.0) * salarioBase;
    }

    private Double calculaAguinaldoCfzaEstructura(Double salarioBase) {
        return (40.0 / 360.0) * salarioBase;
    }

    //*************************CÁLCULO DE IMPORTES TOTALES********************
    private Double calcularProducto(Double cantidad, Double factor) {
        return cantidad * factor;
    }

    private Double calcularTotalIndemnizacion(Double totalIndemnizacion, Double totalVeinteDiasAnio,
            Double totalPrimaAntiguedad, Double totalCompensAntig) {
        if (totalIndemnizacion == null || totalVeinteDiasAnio == null
                || totalPrimaAntiguedad == null || totalCompensAntig == null) {
            throw new IllegalArgumentException("Ninguno de los valores de entrada puede ser nulo.");
        }
        return totalIndemnizacion + totalVeinteDiasAnio + totalPrimaAntiguedad + totalCompensAntig;
    }

    private Double calcularTotalFiniquito(Double importeVacaciones, Double importePrimaVacacional, Double importeAguinaldo) {
        if (importeVacaciones == null || importePrimaVacacional == null
                || importeAguinaldo == null) {
            throw new IllegalArgumentException("Ninguno de los valores de entrada puede ser nulo.");
        }
        return importeVacaciones + importePrimaVacacional + importeAguinaldo;
    }

}
