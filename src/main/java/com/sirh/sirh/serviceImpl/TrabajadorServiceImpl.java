package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.Listado_RA_10DTO;
import com.sirh.sirh.DTO.Semanas_Periodos_PagoDTO;
import com.sirh.sirh.DTO.TrabajadorDTO;
import com.sirh.sirh.DTO.Trabajador_FonacotDTO;
import com.sirh.sirh.DTO.Trabajador_Prestamo_PersonalDTO;
import com.sirh.sirh.DTO.Trabajador_SeparacionDTO;
import com.sirh.sirh.entity.Cat_Incidencias;
import com.sirh.sirh.entity.Cat_Plaza;
import com.sirh.sirh.entity.Cat_Puesto;
import com.sirh.sirh.entity.Cat_Tipo_Nomina;
import com.sirh.sirh.entity.Cuenta_Bancaria;
import com.sirh.sirh.entity.Historico_Trabajador_Plaza;
import com.sirh.sirh.entity.Periodos_Pago;
import com.sirh.sirh.entity.Tmp_Movimientos;
import com.sirh.sirh.entity.Trabajador;
import com.sirh.sirh.entity.Trabajador_plaza;
import com.sirh.sirh.entity.Validaciones;
import com.sirh.sirh.repository.Cuenta_BancariaRepository;
import com.sirh.sirh.repository.Historico_Trabajador_PlazaRepository;
import com.sirh.sirh.repository.IncidenciasRepository;
import com.sirh.sirh.repository.Pagos_1Repository;
import com.sirh.sirh.repository.Pagos_2Repository;
import com.sirh.sirh.repository.Pagos_3Repository;
import com.sirh.sirh.repository.Pagos_4Repository;
import com.sirh.sirh.repository.Pagos_5Repository;
import com.sirh.sirh.repository.Periodos_PagoRepository;
import com.sirh.sirh.repository.Tmp_MovimientosRepository;
import com.sirh.sirh.repository.TrabajadorRepository;
import com.sirh.sirh.repository.Trabajador_plazaRepository;
import com.sirh.sirh.repository.ValidacionesRepository;
import com.sirh.sirh.service.CatalogosService;
import com.sirh.sirh.service.TrabajadorService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nreyes22
 */
@Service
@Transactional
public class TrabajadorServiceImpl implements TrabajadorService {

    public static double suma_dias_contrato = 0;
    public static double suma_incidencias = 0;
    public static double suma_importe_aguinaldo = 0;

    @Autowired
    private Pagos_1Repository pagos_1Repository;

    @Autowired
    private Pagos_2Repository pagos_2Repository;

    @Autowired
    private Pagos_3Repository pagos_3Repository;

    @Autowired
    private Pagos_4Repository pagos_4Repository;

    @Autowired
    private Pagos_5Repository pagos_5Repository;

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private IncidenciasRepository incidenciasRepository;

    @Autowired
    private Cuenta_BancariaRepository cuentaRepository;

    @Autowired
    private Trabajador_plazaRepository trabajadorPlazaRepository;

    @Autowired
    private ValidacionesRepository validacionesPlazaRepository;

    @Autowired
    private Periodos_PagoRepository periodos_PagoRepository;

    @Autowired
    private Historico_Trabajador_PlazaRepository historico_Trabajador_PlazaRepository;

    @Autowired
    private Tmp_MovimientosRepository tmp_MovimientosRepository;

    @Autowired
    CatalogosService catalogosService;

    @Override
    public Double obtenSumaISRVarios88(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_1Repository.obtenSumaISRVarios88(primer_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Double obtenSumaISRTransportacion88(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_2Repository.obtenSumaISRTransportacion88(primer_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Double obtenSumaISRConfianza88(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_3Repository.obtenSumaISRConfianza88(primer_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Double obtenSumaISRJubilados88(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_4Repository.obtenSumaISRJubilados88(primer_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Double obtenSumaISREstructura88(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id) {
        return pagos_5Repository.obtenSumaISREstructura88(primer_periodo, incidencia_id, trabajador_id);
    }

    @Override
    public Trabajador save(Trabajador trabajador) {
        return trabajadorRepository.save(trabajador);
    }

    @Override
    public List<Trabajador> personasConExpediente() {
        return trabajadorRepository.personasConExpediente();
    }

    @Override
    public Trabajador findOne(Integer id_trabajador) {
        return trabajadorRepository.findById(id_trabajador).get();
    }

    @Override
    public Trabajador editarTrabajador(TrabajadorDTO trabajador, Integer id_persona) {
        Trabajador t = this.trabajadorRepository.findById(id_persona).get();
        Trabajador candidato = t;

        candidato.setComisionado_si_no(trabajador.getComisionado_si_no());
        candidato.setExtraccion_sindical_si_no(trabajador.getExtraccion_sindical_si_no());
        candidato.setFecha_antiguedad(trabajador.getFecha_antiguedad());
        candidato.setFecha_ingreso(trabajador.getFecha_ingreso());
        candidato.setPrestaciones_si_no(trabajador.getPrestaciones_si_no());

        return trabajadorRepository.save(candidato);
    }

    @Override
    public Trabajador buscarExp(Integer expediente_id) {
        return trabajadorRepository.buscarExp(expediente_id);
    }

    @Override
    public Integer ultimoIdTrabajador() {
        return trabajadorRepository.ultimoIdTrabajador();
    }

    @Override
    public Trabajador buscarNumExp(String numero_expediente) {
        return trabajadorRepository.buscarNumExp(numero_expediente);
    }

    //------ Cuenta Bancaria de Trabajador --------
    @Override
    public Cuenta_Bancaria saveCuentaBancaria(Cuenta_Bancaria cuenta_bancaria) {
        return cuentaRepository.save(cuenta_bancaria);
    }

    @Override
    public Cuenta_Bancaria findOneCuenta(Integer id) {
        return cuentaRepository.findById(id).get();
    }

    @Override
    public Cuenta_Bancaria findCuentaidTrabajador(Integer id) {
        return cuentaRepository.findCuentaidTrabajador(id);
    }
    //------ Trabajador Plaza --------

    @Override
    public Trabajador_plaza saveTrabajadorPlaza(Trabajador_plaza trabajador_plaza) {
        LocalDate datetime = LocalDate.now();
        trabajador_plaza.setFecha_captura(datetime);
        Trabajador tr = trabajadorRepository.findById(trabajador_plaza.getTrabajador_id()).get();
        trabajador_plaza.setFecha_inicial(tr.getFecha_antiguedad());
        return trabajadorPlazaRepository.save(trabajador_plaza);
    }

    @Override
    public Trabajador_plaza findPlazaTrabajador(Integer id) {
        return trabajadorPlazaRepository.findPlazaTrabajador(id);
    }

    @Override
    public List<Trabajador_plaza> findTrabajadoresByNominaAndNivel26(Integer tipo_nomina_id) {
        return trabajadorPlazaRepository.findTrabajadoresByNominaAndNivel26(tipo_nomina_id);
    }

    @Override
    public List<Trabajador_plaza> findTrabajadoresByNominaAndNivel27(Integer tipo_nomina_id) {
        return trabajadorPlazaRepository.findTrabajadoresByNominaAndNivel27(tipo_nomina_id);
    }

    @Override
    public Trabajador_FonacotDTO findTrabajadorFonacot(String numero_expediente) {
        return trabajadorPlazaRepository.findTrabajadorFonacot(numero_expediente);
    }

    @Override
    public List<Trabajador_Prestamo_PersonalDTO> listadoCreditoPersonal() {
        return trabajadorPlazaRepository.listadoCreditoPersonal();
    }

    @Override
    public List<Trabajador_SeparacionDTO> listadoSeparacion() {
        return trabajadorPlazaRepository.listadoSeparacion();
    }

    @Override
    public Integer findNominaTrabajadorByExpediente(String expediente) {
        return trabajadorPlazaRepository.findNominaTrabajadorByExpediente(expediente);
    }

    @Override
    public String findNombreNominaTrabajadorByExpediente(String expediente) {
        return trabajadorPlazaRepository.findNombreNominaTrabajadorByExpediente(expediente);
    }

    @Override
    public Trabajador_plaza editarPlazaTrabajador(Trabajador_plaza trabajador_plaza, Integer id) {
        Trabajador_plaza tp = this.trabajadorPlazaRepository.findPlazaTrabajador(id);
        Trabajador_plaza trabajador = tp;

        trabajador.setTrabajador_id(trabajador_plaza.getTrabajador_id());
        trabajador.setPlaza_id(trabajador_plaza.getPlaza_id());
        trabajador.setAreas_id(trabajador_plaza.getAreas_id());
        trabajador.setCat_Tipo_Nomina(trabajador_plaza.getCat_Tipo_Nomina());
        trabajador.setCat_tipo_contrato(trabajador_plaza.getCat_tipo_contrato());
        trabajador.setCat_Ubicacion(trabajador_plaza.getCat_Ubicacion());
        trabajador.setFecha_inicial(trabajador_plaza.getFecha_inicial());
        trabajador.setFecha_final(trabajador_plaza.getFecha_final());

        return trabajadorPlazaRepository.save(trabajador);
    }

    @Override
    public Cuenta_Bancaria editarCuentaTrabajador(Cuenta_Bancaria cuenta_bancaria, Integer id) {
        Cuenta_Bancaria cv = this.cuentaRepository.findCuentaidTrabajador(id);
        Cuenta_Bancaria cuenta = cv;

        cuenta.setBanco_id(cuenta_bancaria.getBanco_id());
        cuenta.setCuenta_bancaria(cuenta_bancaria.getCuenta_bancaria());

        return cuentaRepository.save(cuenta);
    }

    @Override
    public List<Trabajador_plaza> findTrabajadorArea(Integer areas_id) {
        return trabajadorPlazaRepository.findTrabajadorArea(areas_id);
    }

    //BUSCA EN TRABAJADOR_PLAZA POR MEDIO DE LA PLAZA_ID Y TRABAJADOR_ID PARA OBTENER LOS DÍAS DE CONTRATO DE ESTE TRABAJADOR
    @Override
    public void findByPlazaAndTrabajador(Integer plaza_id, Integer trabajador_id, Date fecha_final, String nomina) {
        Trabajador_plaza tp = trabajadorPlazaRepository.findByPlazaAndTrabajador(plaza_id, trabajador_id);
        Cat_Plaza plaza = catalogosService.findOnePlaza(plaza_id);
        int puesto = plaza.getCat_puesto().getId_puesto();
        Cat_Puesto cat_puesto = catalogosService.findByIdPuesto(puesto);
        double sueldo_mensual = cat_puesto.getSueldo_mensual();

        // TRABAJADOR CON CAMBIO DE PUESTO EN EL PERIODO, OBTENER LOS DÍAS PROPORCIONALES E IMPORTE DE AGUINALDO EN EL NUEVO PUESTO
        if (tp != null) {
            LocalDate fechaInicialLocalDate = tp.getFecha_inicial();
            LocalDate fechaFinalPeriodoLocalDate = new java.sql.Date(fecha_final.getTime()).toLocalDate();

            long diasTranscurridos = ChronoUnit.DAYS.between(fechaInicialLocalDate, fechaFinalPeriodoLocalDate);

            // Convertir LocalDate a Date
            Date fechaInicial = java.sql.Date.valueOf(fechaInicialLocalDate);

            Integer incidencias = incidenciasRepository.cuentaIncidenciasByTrabajadoryPeriodoVales(trabajador_id, fechaInicial, fecha_final);
            if (incidencias == null) {
                incidencias = 0;
            }

            long dias_ajustados = ajustaDias(fechaInicialLocalDate, fechaFinalPeriodoLocalDate, diasTranscurridos) + 1;
            if (dias_ajustados > 360) {
                dias_ajustados = 360;
            }

            double sueldo_diario = 0;
            if ("5".equals(nomina)) {
                double cantidad_adicional_mensual = cat_puesto.getCantidad_adicional_mensual();
                sueldo_diario = (cantidad_adicional_mensual + sueldo_mensual)/30;
            }

            double dias_aguinaldo = (40.0 / 360.0) * ((double) dias_ajustados - incidencias);
            sueldo_diario = sueldo_mensual/30;
            double importe_aguinaldo = dias_aguinaldo * sueldo_diario;

            suma_dias_contrato = suma_dias_contrato + dias_ajustados;
            suma_incidencias = suma_incidencias + incidencias;
            suma_importe_aguinaldo = suma_importe_aguinaldo + importe_aguinaldo;
        }
    }

    //BUSCA EN TRABAJADOR_PLAZA POR MEDIO DEL TRABAJADOR_ID PARA OBTENER LOS DÍAS DE CONTRATO DE ESTE TRABAJADOR
    @Override
    public void findPlazaTrabajadorByID(Integer trabajador_id, Date fecha_inicial_periodo, Date fecha_final_periodo, String nomina, String periodo) {
        Trabajador_plaza htp = trabajadorPlazaRepository.findPlazaTrabajador(trabajador_id);
        Cat_Plaza plaza = catalogosService.findOnePlaza(htp.getPlaza_id());
        int puesto = plaza.getCat_puesto().getId_puesto();
        Cat_Puesto cat_puesto = catalogosService.findByIdPuesto(puesto);
        double sueldo_mensual = cat_puesto.getSueldo_mensual();
        long diasTranscurridos = 0;
        if (htp != null) {
            // Convertir java.util.Date a java.time.LocalDate
            LocalDate fechaInicial = htp.getFecha_inicial();
            LocalDate fechaInicialPeriodo = fecha_inicial_periodo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaFinalPeriodo = fecha_final_periodo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Integer incidencias = 0;
            long dias_ajustados = 0;

            // Comparar si la fecha inicial es más antigua que el 1 de diciembre del año anterior
            if (fechaInicial.isBefore(fechaInicialPeriodo)) {
                // TRABAJADOR SIN CAMBIOS EN EL PERIODO Y EL AÑO COMPLETO DE AGUINALDO
                diasTranscurridos = ChronoUnit.DAYS.between(fechaInicialPeriodo, fechaFinalPeriodo);

                // Convertir LocalDate a Date para usar en el repositorio
                Date fechaInicialPeriodoDate = Date.from(fechaInicialPeriodo.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date fechaFinalPeriodoDate = Date.from(fechaFinalPeriodo.atStartOfDay(ZoneId.systemDefault()).toInstant());

                incidencias = incidenciasRepository.cuentaIncidenciasByTrabajadoryPeriodoVales(trabajador_id, fechaInicialPeriodoDate, fechaFinalPeriodoDate);
                if (incidencias == null) {
                    incidencias = 0;
                }
                dias_ajustados = ajustaDias(fechaInicialPeriodo, fechaFinalPeriodo, diasTranscurridos) + 1;
                System.out.println("Hola 1");
            } else {
                // TRABAJADOR NUEVO CON UNICO PUESTO Y MENOS DE UN AÑO EN ÉL
                diasTranscurridos = ChronoUnit.DAYS.between(fechaInicial, fechaFinalPeriodo);
                System.out.println("fechaInicial - > " + fechaInicial);
                System.out.println("fechaFinal - > " + fechaFinalPeriodo);
                // Convertir LocalDate a Date para usar en el repositorio
                Date fechaInicialDate = Date.from(fechaInicial.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date fechaFinalPeriodoDate = Date.from(fechaFinalPeriodo.atStartOfDay(ZoneId.systemDefault()).toInstant());

                incidencias = incidenciasRepository.cuentaIncidenciasByTrabajadoryPeriodoVales(trabajador_id, fechaInicialDate, fechaFinalPeriodoDate);
                if (incidencias == null) {
                    incidencias = 0;
                }
                dias_ajustados = ajustaDias(fechaInicial, fechaFinalPeriodo, diasTranscurridos) + 1;
                
            }

            if (dias_ajustados > 360) {
                dias_ajustados = 360;
            }
            double sueldo_diario = 0;
            if ("5".equals(nomina)) {
                double cantidad_adicional_mensual = cat_puesto.getCantidad_adicional_mensual();
                sueldo_diario = (cantidad_adicional_mensual + sueldo_mensual)/30;
            }
            double dias_aguinaldo = (40.0 / 360.0) * ((double) dias_ajustados - incidencias);
            sueldo_diario = sueldo_mensual/30;
            double importe_aguinaldo = dias_aguinaldo * sueldo_diario;
            //System.out.println("FECHA INICIAL --> " + fecha_inicial_periodo);
            //System.out.println("FECHA FINAL --> " + fecha_final_periodo);
            System.out.println("DIAS AJUSTADOS --> " + dias_ajustados);
            System.out.println("INCIDENCIAS --> " + incidencias);
            System.out.println("DIAS AGUINALDO --> " + dias_aguinaldo);
            System.out.println("SUELDO DIARIO --> " + sueldo_diario);
            System.out.println("IMPORTE AGUINALDO --> " + importe_aguinaldo);
            saveMovimientos243ConfianzaUnicoPuesto(nomina, periodo, trabajador_id, dias_ajustados, incidencias, importe_aguinaldo);
        }
    }

    public long ajustaDias(LocalDate fecha_inicial, LocalDate fecha_final, long diasTranscurridos) {
        int year = java.time.Year.now().getValue();
        YearMonth yearMonth = YearMonth.of(year, 2);
        int dias_febrero = yearMonth.lengthOfMonth();
        // ARREGLO DE MESES ESPECIALES QUE GENERAN RESTAR UN DÍA SI APARECEN EN EL PERIODO, EXCEPTO FEBRERO (2) QUE SE USA METER LOS DÍAS FALTANTES PARA QUE ACOMPLETEN 30 DÍAS
        int[] meses = {12, 1, 2, 3, 5, 7, 8, 10};
        List<Integer> mesesCon31Dias = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
        int mes_final = fecha_final.getMonthValue();
        // Convertir el arreglo a un conjunto para facilitar la búsqueda
        Set<Integer> mesesSet = new HashSet<>();
        for (int mes : meses) {
            mesesSet.add(mes);
        }

        // Lista para almacenar los meses encontrados
        List<Integer> mesesEncontrados = new ArrayList<>();

        // Verificar los meses entre las dos fechas
        LocalDate current = fecha_inicial.withDayOfMonth(1);
        LocalDate end = fecha_final.withDayOfMonth(1).plusMonths(1);

        while (current.isBefore(end)) {
            if (mesesSet.contains(current.getMonthValue())) {
                mesesEncontrados.add(current.getMonthValue());
            }
            current = current.plusMonths(1);
        }

        // Convertir la lista a un arreglo
        int[] mesesEncontradosArray = mesesEncontrados.stream().mapToInt(Integer::intValue).toArray();
        // CUANDO LA FECHA FINAL ES EL DÍA 31, SE OMITE ESE MES PARA NO RESTARLE EL DÍA A LOS DIAS TRANSCURRIDOS EN EL PERIODO
        if (mesesCon31Dias.contains(mes_final) && fecha_final.getDayOfMonth() != 31) {

            // Convertir el arreglo a una lista
            List<Integer> lista = new ArrayList<>(Arrays.asList(Arrays.stream(mesesEncontradosArray).boxed().toArray(Integer[]::new)));

            // Eliminar el elemento de la lista
            lista.removeIf(i -> i == mes_final);

            // Convertir la lista de nuevo a un arreglo
            mesesEncontradosArray = lista.stream().mapToInt(Integer::intValue).toArray();
        }
        int longitud = mesesEncontradosArray.length;
        // VERIFICAR SI FEBRERO ESTÁ EN EL ARREGLO DE MESES DEL PERIODO, PARA SUMAR O NO LOS DÍAS QUE FEBRERO TIENE MENOS
        boolean febreroEncontrado = false;
        for (int mes : mesesEncontradosArray) {
            if (mes == 2) {
                febreroEncontrado = true;
                break;
            }
        }
        // SI FEBRERO SE ENCUENTRA EN LA LISTA SE LE RESTA A LA LONGITUD LOS DÍAS FALTANTES DE FEBRERO
        if (febreroEncontrado) {
            // SI FEBRERO TIENE 28 DÍAS SE LE RESTAN A LA LONGITUD QUE SON LOS DÍAS PARA DESCONTAR A DIAS TRANSCURRIDOS, MENOS OTRO 1 POR QUE FEBRERO NO CUENTA COMO MES DESCONTABLE
            if (dias_febrero == 28) {
                diasTranscurridos -= (longitud - 3);

            } // SI FEBRERO TIENE 29 DÍAS SE LE RESTAN A LA LONGITUD QUE SON LOS DÍAS PARA DESCONTAR A DIAS TRANSCURRIDOS, MENOS OTRO 1 POR QUE FEBRERO NO CUENTA COMO MES DESCONTABLE
            else if (dias_febrero == 29) {
                diasTranscurridos -= (longitud - 2);
            }
        } // SI FEBRERO NO SE ENCUENTRA EN LA LISTA SOLO SE LE RESTA LA LONGITUD A LOS DÍAS TRANSCURRIDOS ENTRE LOS PERIODOS
        else {
            diasTranscurridos -= longitud;
        }
        return diasTranscurridos;
    }

    //------ Trabajador Validaciones --------
    @Override
    public Validaciones saveValidaciones(Validaciones validacion) {
        return validacionesPlazaRepository.save(validacion);

    }

    @Override
    public Validaciones findOneValidacion(Integer id) {
        return validacionesPlazaRepository.findById(id).get();
    }

    @Override
    public Validaciones findValidacionTrabajador(Integer id) {
        return validacionesPlazaRepository.findValidacionTrabajador(id);
    }

    @Override
    public Validaciones editarValidacion(Validaciones validacion, Integer id) {
        Validaciones v = this.validacionesPlazaRepository.findValidacionTrabajador(id);
        Validaciones val = v;

        val.setActivo_en_nomina_si_no(validacion.getActivo_en_nomina_si_no());
        val.setActivo_en_personal_si_no(validacion.getActivo_en_personal_si_no());
        val.setCon_contrato_si_no(validacion.getCon_contrato_si_no());
        val.setHabilita_si_no(validacion.getHabilita_si_no());
        val.setSancion_si_no(validacion.getSancion_si_no());

        return validacionesPlazaRepository.save(val);
    }
    //------ Periodos de Pago --------

    @Override
    public Integer findPeriodoPago(Integer id_nomina, Date fecha_inicio) {
        return periodos_PagoRepository.findPeriodoPago(id_nomina, fecha_inicio);
    }

    @Override
    public List<Periodos_Pago> findPeriodosFechas(Integer id_nomina) {
        return periodos_PagoRepository.findPeriodosFechas(id_nomina);
    }

    @Override
    public List<Periodos_Pago> findAllPeriodosPagoByNomina(Integer id_nomina) {
        return periodos_PagoRepository.findAllPeriodosPagoByNomina(id_nomina);
    }

    @Override
    public List<Periodos_Pago> findPeriodoFechaCorteAndTipoNomina(Integer id_nomina, Date fecha_corte) {
        return periodos_PagoRepository.findPeriodoFechaCorteAndTipoNomina(id_nomina, fecha_corte);
    }

    @Override
    public Periodos_Pago findOnePeriodo(Integer id_periodo_pago) {
        return periodos_PagoRepository.findOnePeriodo(id_periodo_pago);
    }

    @Override
    public Periodos_Pago findOnePeriodoPago(Integer periodoPago, Integer id_nomina) {
        return periodos_PagoRepository.findOnePeriodoPago(periodoPago, id_nomina);
    }

    @Override
    public List<Integer> findLapsoPeriodos(Integer idNomina, Date fechaInicio, Date fechaFin) {
        return periodos_PagoRepository.findLapsoPeriodos(idNomina, fechaInicio, fechaFin);
    }

    @Override
    public List<Semanas_Periodos_PagoDTO> obtenerFechasSemanalesConfianza() {
        List<Object[]> resultados = periodos_PagoRepository.obtenerFechasSemanalesConfianza();
        List<Semanas_Periodos_PagoDTO> dtos = new ArrayList<>();
        for (Object[] resultado : resultados) {
            try {
                // Convertir el valor de Object[] a java.sql.Timestamp
                java.sql.Timestamp timestampFechaInicial = (java.sql.Timestamp) resultado[0];
                java.sql.Timestamp timestampFechaFinal = (java.sql.Timestamp) resultado[1];

                // Convertir java.sql.Timestamp a LocalDate
                LocalDate fechaInicial = timestampFechaInicial.toLocalDateTime().toLocalDate();
                LocalDate fechaFinal = timestampFechaFinal.toLocalDateTime().toLocalDate();

                // Crear el DTO y añadirlo a la lista
                Semanas_Periodos_PagoDTO dto = new Semanas_Periodos_PagoDTO(fechaInicial, fechaFinal);
                dtos.add(dto);
            } catch (ClassCastException e) {
                // Manejo del error de conversión
                e.printStackTrace();
            }
        }
        return dtos;
    }

    @Override
    public List<Semanas_Periodos_PagoDTO> obtenerFechasSemanalesVarios() {
        List<Object[]> resultados = periodos_PagoRepository.obtenerFechasSemanalesVarios();
        List<Semanas_Periodos_PagoDTO> dtos = new ArrayList<>();
        for (Object[] resultado : resultados) {
            try {
                // Convertir el valor de Object[] a java.sql.Timestamp
                java.sql.Timestamp timestampFechaInicial = (java.sql.Timestamp) resultado[0];
                java.sql.Timestamp timestampFechaFinal = (java.sql.Timestamp) resultado[1];

                // Convertir java.sql.Timestamp a LocalDate
                LocalDate fechaInicial = timestampFechaInicial.toLocalDateTime().toLocalDate();
                LocalDate fechaFinal = timestampFechaFinal.toLocalDateTime().toLocalDate();

                Semanas_Periodos_PagoDTO dto = new Semanas_Periodos_PagoDTO(fechaInicial, fechaFinal);
                dtos.add(dto);
            } catch (ClassCastException e) {
                // Manejo del error de conversión
                e.printStackTrace();
            }
        }
        return dtos;
    }

    @Override
    public List<Semanas_Periodos_PagoDTO> obtenerFechasSemanalesTransportacion() {
        List<Object[]> resultados = periodos_PagoRepository.obtenerFechasSemanalesTransportacion();
        List<Semanas_Periodos_PagoDTO> dtos = new ArrayList<>();
        for (Object[] resultado : resultados) {
            try {
                // Convertir el valor de Object[] a java.sql.Timestamp
                java.sql.Timestamp timestampFechaInicial = (java.sql.Timestamp) resultado[0];
                java.sql.Timestamp timestampFechaFinal = (java.sql.Timestamp) resultado[1];

                // Convertir java.sql.Timestamp a LocalDate
                LocalDate fechaInicial = timestampFechaInicial.toLocalDateTime().toLocalDate();
                LocalDate fechaFinal = timestampFechaFinal.toLocalDateTime().toLocalDate();

                // Crear el DTO y añadirlo a la lista
                Semanas_Periodos_PagoDTO dto = new Semanas_Periodos_PagoDTO(fechaInicial, fechaFinal);
                dtos.add(dto);
            } catch (ClassCastException e) {
                // Manejo del error de conversión
                e.printStackTrace();
            }
        }
        return dtos;
    }

    @Override
    public List<Periodos_Pago> obtenerPeriodosGeneracionCercanos(Integer id_nomina) {
        return periodos_PagoRepository.obtenerPeriodosGeneracionCercanos(id_nomina);
    }

    @Override
    public List<Trabajador> personasConPlaza() {
        return trabajadorRepository.personasConPlaza();
    }

    @Override
    public List<Trabajador> filtradoTotalDeIncidenciasKardex(Integer tipo_nomina, Integer incidencia_id, Date fecha_inicio_periodo_pago, Date fecha_fin_periodo_pago) {
        return trabajadorRepository.filtradoTotalDeIncidenciasKardex(tipo_nomina, incidencia_id, fecha_inicio_periodo_pago, fecha_fin_periodo_pago);
    }

    @Override
    public List<Trabajador> filtradoSoloDeIncidenciasKardex(Integer tipo_nomina, Integer incidencia_id) {
        return trabajadorRepository.filtradoSoloDeIncidenciasKardex(tipo_nomina, incidencia_id);
    }

    @Override
    public List<Trabajador> filtradoPeriodosDeIncidenciasKardex(Integer tipo_nomina, Date fecha_inicio_periodo_pago, Date fecha_fin_periodo_pago) {
        return trabajadorRepository.filtradoPeriodosDeIncidenciasKardex(tipo_nomina, fecha_inicio_periodo_pago, fecha_fin_periodo_pago);
    }

    @Override
    public List<Trabajador> personasConPlazaTipoNominaEspecifica(Integer tipo_nomina) {
        return trabajadorRepository.personasConPlazaTipoNominaEspecifica(tipo_nomina);
    }
    //------ Buscar el trabajador en Historico trabajador plaza --------

    @Override
    public Historico_Trabajador_Plaza findHistoricoPlazaTrabajador(Integer id) {
        return historico_Trabajador_PlazaRepository.findHistoricoPlazaTrabajador(id);
    }

    @Override
    public Boolean esOperativoOAdministrativo(Integer trabajador_id) {
        return trabajadorPlazaRepository.esOperativoOAdministrativo(trabajador_id);
    }

    @Override
    public List<Listado_RA_10DTO> findByNominaAndArea(Integer id_area, Integer id_nomina) {
        return trabajadorPlazaRepository.findByNominaAndArea(id_area, id_nomina);
    }

    @Override
    public List<Trabajador> trabajadoresConExpedienteFiltroNominaOpcional(Integer tipo_nomina_id) {
        return trabajadorRepository.trabajadoresConExpedienteFiltroNominaOpcional(tipo_nomina_id);
    }

    // BUSCA LAS PLAZAS ANTIGUAS EN EL HISTORICO POR TRABAJADOR, OBTIENE SU IMPORTE Y LOS DIAS POR CONTRATO
    @Override
    public void findByPlazaAndTrabajadorHistorico(Integer plaza_id, Integer trabajador_id, Date fecha_inicial, String nomina) {
        Historico_Trabajador_Plaza htp = historico_Trabajador_PlazaRepository.findByPlazaAndTrabajadorHistorico(plaza_id, trabajador_id);
        Cat_Plaza plaza = catalogosService.findOnePlaza(plaza_id);
        int puesto = plaza.getCat_puesto().getId_puesto();
        Cat_Puesto cat_puesto = catalogosService.findByIdPuesto(puesto);
        double sueldo_mensual = cat_puesto.getSueldo_mensual();

        long diasTranscurridos = 0;
        if (htp != null) {
            // Convertir java.util.Date a java.time.LocalDate
            LocalDate fechaInicial = htp.getFecha_inicial();
            LocalDate fechaFinal = htp.getFecha_final();
            LocalDate fechaInicialPeriodo = fecha_inicial.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Integer incidencias = 0;
            long dias_ajustados = 0;

            // Comparar si la fecha inicial es más antigua que el 1 de diciembre del año anterior
            if (fechaInicial.isBefore(fechaInicialPeriodo)) {
                // TRABAJADOR CON UN CAMBIO DE PUESTO FUERA DEL PERIODO DE AGUINALDO
                diasTranscurridos = ChronoUnit.DAYS.between(fechaInicialPeriodo, fechaFinal);
                dias_ajustados = ajustaDias(fechaInicialPeriodo, fechaFinal, diasTranscurridos) + 1;

                // Convertir LocalDate a Date para usar en el repositorio
                Date fechaInicialPeriodoDate = Date.from(fechaInicialPeriodo.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date fechaFinalDate = Date.from(fechaFinal.atStartOfDay(ZoneId.systemDefault()).toInstant());

                incidencias = incidenciasRepository.cuentaIncidenciasByTrabajadoryPeriodoVales(trabajador_id, fechaInicialPeriodoDate, fechaFinalDate);
                if (incidencias == null) {
                    incidencias = 0;
                }
            } else {
                // TRABAJADOR CON UN CAMBIO DE PUESTO DENTRO DEL PERIODO DE AGUINALDO MAS DE UN CAMBIO EN HISTORICO
                diasTranscurridos = ChronoUnit.DAYS.between(fechaInicial, fechaFinal);
                dias_ajustados = ajustaDias(fechaInicial, fechaFinal, diasTranscurridos) + 1;

                // Convertir LocalDate a Date para usar en el repositorio
                Date fechaInicialDate = Date.from(fechaInicial.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date fechaFinalDate = Date.from(fechaFinal.atStartOfDay(ZoneId.systemDefault()).toInstant());

                incidencias = incidenciasRepository.cuentaIncidenciasByTrabajadoryPeriodoVales(trabajador_id, fechaInicialDate, fechaFinalDate);
                if (incidencias == null) {
                    incidencias = 0;
                }
            }
            if (dias_ajustados > 360) {
                dias_ajustados = 360;
            }
            double sueldo_diario = 0;
            if ("5".equals(nomina)) {
                double cantidad_adicional_mensual = cat_puesto.getCantidad_adicional_mensual();
                sueldo_diario = (cantidad_adicional_mensual + sueldo_mensual)/30;
            }
            double dias_aguinaldo = (40.0 / 360.0) * ((double) dias_ajustados - incidencias);
            sueldo_diario = sueldo_mensual/30;
            double importe_aguinaldo = dias_aguinaldo * sueldo_diario;
            suma_dias_contrato = suma_dias_contrato + dias_ajustados;
            suma_incidencias = suma_incidencias + incidencias;
            suma_importe_aguinaldo = suma_importe_aguinaldo + importe_aguinaldo;
        }
    }

    // ******************************** GRABAR DIAS DE CONTRATO (243 Cve Nomina - id_incidencia 106) CONFIANZA EN TMP MOVIMIENTOS********************
    @Override
    public Tmp_Movimientos saveMovimientos243Confianza(Tmp_Movimientos movimientos) {
        if (suma_dias_contrato > 360) {
            suma_dias_contrato = 360;
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
        m.setValor_1(suma_dias_contrato);
        m.setFecha_creacion(fecha);
        suma_dias_contrato = 0;
        return tmp_MovimientosRepository.save(m);
    }

    // ******************************** GRABAR DIAS DE INASISTENCIAS (244 Cve Nomina - id_incidencia 107) CONFIANZA EN TMP MOVIMIENTOS********************
    @Override
    public Tmp_Movimientos saveMovimientos244Confianza(Tmp_Movimientos movimientos) {
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
        m.setValor_1(suma_incidencias);
        m.setFecha_creacion(fecha);
        suma_incidencias = 0;
        return tmp_MovimientosRepository.save(m);
    }

    // ******************************** GRABAR IMPORTE DE AGUINALDO (12 Cve Nomina - id_Incidencia 48) CONFIANZA EN TMP MOVIMIENTOS********************
    @Override
    public Tmp_Movimientos saveMovimientos12Confianza(Tmp_Movimientos movimientos) {
        Tmp_Movimientos m = new Tmp_Movimientos();
        LocalDate fecha = LocalDate.now();
        Cat_Incidencias catPercepcion = new Cat_Incidencias();
        catPercepcion.setId_incidencia(48);
        m.setTrabajador(movimientos.getTrabajador());
        m.setCat_Incidencias(catPercepcion);
        m.setAnio_aplicacion(movimientos.getAnio_aplicacion());
        m.setPeriodo_generacion(movimientos.getPeriodo_generacion());
        m.setPeriodo_aplicacion(movimientos.getPeriodo_aplicacion());
        m.setCat_Tipo_Nomina(movimientos.getCat_Tipo_Nomina());
        m.setValor_1(suma_importe_aguinaldo);
        m.setFecha_creacion(fecha);
        suma_importe_aguinaldo = 0;
        return tmp_MovimientosRepository.save(m);
    }

    // ******************************** GRABAR IMPORTE AGUINALDO ANTERIOR (330 Cve Nomina - Id_Incidencia 168) EN TMP MOVIMIENTOS********************
    @Override
    public void saveMovimientos330Confianza(Tmp_Movimientos movimientos) {
        LocalDate currentDate = LocalDate.now();
        LocalDate fecha = LocalDate.now();
        int currentYear = currentDate.getYear();
        int periodo_88 = Integer.parseInt(currentYear + "88");;

        if (Integer.toString(movimientos.getPeriodo_aplicacion()).equals(currentYear + "89")) {
            Tmp_Movimientos m = new Tmp_Movimientos();
            Double incidencia_varios_88 = pagos_1Repository.obtenIncidenciaVarios(periodo_88, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_transportacion_88 = pagos_2Repository.obtenIncidenciaTransportacion(periodo_88, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_confianza_88 = pagos_3Repository.obtenIncidenciaConfianza(periodo_88, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_estructura_88 = pagos_5Repository.obtenIncidenciaEstructura(periodo_88, 48, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_jubilados_88 = pagos_4Repository.obtenIncidenciaJubilados(periodo_88, 48, movimientos.getTrabajador().getId_trabajador());

            double incidencia_12_varios_val = (incidencia_varios_88 == null) ? 0.0 : incidencia_varios_88;
            double incidencia_12_transportacion_val = (incidencia_transportacion_88 == null) ? 0.0 : incidencia_transportacion_88;
            double incidencia_12_confianza_val = (incidencia_confianza_88 == null) ? 0.0 : incidencia_confianza_88;
            double incidencia_12_estructura_val = (incidencia_estructura_88 == null) ? 0.0 : incidencia_estructura_88;
            double incidencia_12_jubilados_val = (incidencia_jubilados_88 == null) ? 0.0 : incidencia_jubilados_88;
            double aguinaldo_anterior_88 = incidencia_12_varios_val + incidencia_12_transportacion_val + incidencia_12_confianza_val + incidencia_12_estructura_val + incidencia_12_jubilados_val;

            Cat_Incidencias catPercepcion = new Cat_Incidencias();
            catPercepcion.setId_incidencia(168);
            m.setTrabajador(movimientos.getTrabajador());
            m.setCat_Incidencias(catPercepcion);
            m.setAnio_aplicacion(movimientos.getAnio_aplicacion());
            m.setPeriodo_generacion(movimientos.getPeriodo_generacion());
            m.setPeriodo_aplicacion(movimientos.getPeriodo_aplicacion());
            m.setCat_Tipo_Nomina(movimientos.getCat_Tipo_Nomina());
            m.setValor_1(aguinaldo_anterior_88);
            m.setFecha_creacion(fecha);
            tmp_MovimientosRepository.save(m);
        }

    }

    // ******************************** GRABAR IMPORTE DE DEVOLUCION ISR (339 Cve Nomina - Id_Incidencia 171) EN TMP MOVIMIENTOS********************
    @Override
    public void saveMovimientos339Confianza(Tmp_Movimientos movimientos) {
        LocalDate currentDate = LocalDate.now();
        Tmp_Movimientos m = new Tmp_Movimientos();
        LocalDate fecha = LocalDate.now();
        int currentYear = currentDate.getYear();
        int periodo_88 = Integer.parseInt(currentYear + "88");
        if (Integer.toString(movimientos.getPeriodo_aplicacion()).equals(currentYear + "89")) {

            Double incidencia_339_varios = obtenSumaISRVarios88(periodo_88, 345, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_339_transportacion = obtenSumaISRTransportacion88(periodo_88, 345, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_339_confianza = obtenSumaISRConfianza88(periodo_88, 345, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_339_estructura = obtenSumaISREstructura88(periodo_88, 345, movimientos.getTrabajador().getId_trabajador());
            Double incidencia_339_jubilados = obtenSumaISRJubilados88(periodo_88, 345, movimientos.getTrabajador().getId_trabajador());

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
            m.setPeriodo_aplicacion(movimientos.getPeriodo_aplicacion());
            m.setCat_Tipo_Nomina(movimientos.getCat_Tipo_Nomina());
            m.setValor_1(isr);
            m.setFecha_creacion(fecha);

            tmp_MovimientosRepository.save(m);
        }
    }

    // ******************************** GRABAR DIAS DE CONTRATO (243 Cve Nomina - id_incidencia 106) CONFIANZA EN TMP MOVIMIENTOS UNICO PUESTO********************
    public Tmp_Movimientos saveMovimientos243ConfianzaUnicoPuesto(String nomina, String periodo, Integer trabajador_id, long dias_ajustados, Integer incidencias, Double importe_aguinaldo) {
        if (dias_ajustados > 360) {
            dias_ajustados = 360;
        }
        Tmp_Movimientos m = new Tmp_Movimientos();
        LocalDate fecha = LocalDate.now();
        int ano = fecha.getYear();
        Cat_Incidencias catPercepcion = new Cat_Incidencias();
        catPercepcion.setId_incidencia(106);
        Trabajador tr = new Trabajador();
        tr.setId_trabajador(trabajador_id);
        Cat_Tipo_Nomina cn = new Cat_Tipo_Nomina();
        cn.setId_tipo_nomina(Integer.parseInt(nomina));
        m.setTrabajador(tr);
        m.setCat_Incidencias(catPercepcion);
        m.setAnio_aplicacion(ano);
        m.setPeriodo_generacion(Integer.parseInt(periodo));
        m.setPeriodo_aplicacion(Integer.parseInt(periodo));
        m.setCat_Tipo_Nomina(cn);
        m.setValor_1((double) dias_ajustados);
        m.setFecha_creacion(fecha);

        saveMovimientos244ConfianzaUnicoPuesto(nomina, periodo, trabajador_id, incidencias, importe_aguinaldo);
        return tmp_MovimientosRepository.save(m);

    }

    // ******************************** GRABAR DIAS DE INASISTENCIAS (244 Cve Nomina - id_incidencia 107) CONFIANZA EN TMP MOVIMIENTOS UNICO PUESTO********************
    public Tmp_Movimientos saveMovimientos244ConfianzaUnicoPuesto(String nomina, String periodo, Integer trabajador_id, Integer incidencias, Double importe_aguinaldo) {
        Tmp_Movimientos m = new Tmp_Movimientos();
        LocalDate fecha = LocalDate.now();
        int ano = fecha.getYear();
        Cat_Incidencias catPercepcion = new Cat_Incidencias();
        catPercepcion.setId_incidencia(107);
        Trabajador tr = new Trabajador();
        tr.setId_trabajador(trabajador_id);
        Cat_Tipo_Nomina cn = new Cat_Tipo_Nomina();
        cn.setId_tipo_nomina(Integer.parseInt(nomina));
        m.setTrabajador(tr);
        m.setCat_Incidencias(catPercepcion);
        m.setAnio_aplicacion(ano);
        m.setPeriodo_generacion(Integer.parseInt(periodo));
        m.setPeriodo_aplicacion(Integer.parseInt(periodo));
        m.setCat_Tipo_Nomina(cn);
        m.setValor_1((double) incidencias);
        m.setFecha_creacion(fecha);
        saveMovimientos12ConfianzaUnicoPuesto(nomina, periodo, trabajador_id, importe_aguinaldo);

        return tmp_MovimientosRepository.save(m);
    }

    // ******************************** GRABAR IMPORTE DE AGUINALDO (12 Cve Nomina - id_Incidencia 48) CONFIANZA EN TMP MOVIMIENTOS UNICO PUESTO********************
    public Tmp_Movimientos saveMovimientos12ConfianzaUnicoPuesto(String nomina, String periodo, Integer trabajador_id, Double importe_aguinaldo) {
        Tmp_Movimientos m = new Tmp_Movimientos();
        LocalDate fecha = LocalDate.now();
        int ano = fecha.getYear();
        Cat_Incidencias catPercepcion = new Cat_Incidencias();
        catPercepcion.setId_incidencia(48);
        Trabajador tr = new Trabajador();
        tr.setId_trabajador(trabajador_id);
        Cat_Tipo_Nomina cn = new Cat_Tipo_Nomina();
        cn.setId_tipo_nomina(Integer.parseInt(nomina));
        m.setTrabajador(tr);
        m.setCat_Incidencias(catPercepcion);
        m.setAnio_aplicacion(ano);
        m.setPeriodo_generacion(Integer.parseInt(periodo));
        m.setPeriodo_aplicacion(Integer.parseInt(periodo));
        m.setCat_Tipo_Nomina(cn);
        m.setValor_1(importe_aguinaldo);
        m.setFecha_creacion(fecha);
        return tmp_MovimientosRepository.save(m);
    }

}
