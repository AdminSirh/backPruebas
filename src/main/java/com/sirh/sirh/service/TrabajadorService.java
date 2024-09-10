package com.sirh.sirh.service;

import com.sirh.sirh.DTO.Listado_RA_10DTO;
import com.sirh.sirh.DTO.PlantillaMensualDTO;
import com.sirh.sirh.DTO.Semanas_Periodos_PagoDTO;
import com.sirh.sirh.DTO.TrabajadorDTO;
import com.sirh.sirh.DTO.Trabajador_FonacotDTO;
import com.sirh.sirh.DTO.Trabajador_Prestamo_PersonalDTO;
import com.sirh.sirh.DTO.Trabajador_SeparacionDTO;
import com.sirh.sirh.entity.Cuenta_Bancaria;
import com.sirh.sirh.entity.Historico_Trabajador_Plaza;
import com.sirh.sirh.entity.Periodos_Pago;
import com.sirh.sirh.entity.Registro_Plantilla_Mensual;
import com.sirh.sirh.entity.Tmp_Movimientos;
import com.sirh.sirh.entity.Trabajador;
import com.sirh.sirh.entity.Trabajador_plaza;
import com.sirh.sirh.entity.Validaciones;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nreyes22
 */
public interface TrabajadorService {

    //------ Guardar nuevo Trabajador --------
    public Trabajador save(Trabajador persona);

    //Listar Trabajadores con plaza
    public List<Trabajador> personasConPlaza();

    //Función de filtrado total para las incidencias que se muestran en kárdex
    public List<Trabajador> filtradoTotalDeIncidenciasKardex(Integer tipo_nomina, Integer incidencia_id, Date fecha_inicio_periodo_pago, Date fecha_fin_periodo_pago);

    //Función de filtrado solo para las incidencias que se muestran en kárdex
    public List<Trabajador> filtradoSoloDeIncidenciasKardex(Integer tipo_nomina, Integer incidencia_id);

    //Función de filtrado periodos de pago para las incidencias que se muestran en kárdex
    public List<Trabajador> filtradoPeriodosDeIncidenciasKardex(Integer tipo_nomina, Date fecha_inicio_periodo_pago, Date fecha_fin_periodo_pago);

    //Listing especific payroll 
    public List<Trabajador> personasConPlazaTipoNominaEspecifica(Integer tipo_nomina);

    //Listar Trabajadores
    public List<Trabajador> personasConExpediente();

    public Trabajador findOne(Integer id_trabajador);

    public Trabajador editarTrabajador(TrabajadorDTO trabajador, Integer id_persona);

    Trabajador buscarExp(Integer expediente_id);

    public Integer ultimoIdTrabajador();

    Trabajador buscarNumExp(String numero_expediente);

    public Double obtenSumaISRVarios88(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id);

    public Double obtenSumaISRTransportacion88(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id);

    public Double obtenSumaISRConfianza88(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id);

    public Double obtenSumaISRJubilados88(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id);

    public Double obtenSumaISREstructura88(Integer primer_periodo, Integer incidencia_id, Integer trabajador_id);

    //------ Cuenta Bancaria de Trabajador --------
    public Cuenta_Bancaria saveCuentaBancaria(Cuenta_Bancaria cuenta_bancaria);

    public Cuenta_Bancaria findOneCuenta(Integer id);

    public Cuenta_Bancaria findCuentaidTrabajador(Integer id);

    //------ Plaza del Trabajador --------
    public Trabajador_plaza saveTrabajadorPlaza(Trabajador_plaza trabajador_plaza);
    //Buscar Plaza por el id del trabajador

    public Trabajador_plaza findPlazaTrabajador(Integer id);

    public Trabajador_plaza editarPlazaTrabajador(Trabajador_plaza trabajador_plaza, Integer id);

    public List<Trabajador_plaza> findTrabajadorArea(Integer areas_id);

    public Trabajador_FonacotDTO findTrabajadorFonacot(String numero_expediente);

    public List<Trabajador_plaza> findTrabajadoresByNominaAndNivel26(Integer tipo_nomina_id);

    public List<Trabajador_plaza> findTrabajadoresByNominaAndNivel27(Integer tipo_nomina_id);

    public void findByPlazaAndTrabajador(Integer plaza_id, Integer trabajador_id, Date fecha_final, String nomina);

    public void findPlazaTrabajadorByID(Integer id, Date fecha_inicial, Date fecha_final, String nomina, String periodo);

    //Editar CUENTA por el id del trabajador
    public Cuenta_Bancaria editarCuentaTrabajador(Cuenta_Bancaria cuenta_bancaria, Integer id);

    //------ Validaciones del Trabajador --------
    public Validaciones saveValidaciones(Validaciones validacion);

    public Validaciones findOneValidacion(Integer id);

    public Validaciones findValidacionTrabajador(Integer id);
    //Editar Validacion por el id del trabajador

    public Validaciones editarValidacion(Validaciones validacion, Integer id);

    //------ Periodos de Pago --------
    public Periodos_Pago findOnePeriodo(Integer id_periodo_pago);

    public Integer findPeriodoPago(Integer id_nomina, Date fecha_inicio);

    public List<Periodos_Pago> findPeriodosFechas(Integer id_nomina);

    public List<Periodos_Pago> findAllPeriodosPagoByNomina(Integer id_nomina);

    public List<Periodos_Pago> findPeriodoFechaCorteAndTipoNomina(Integer id_nomina, Date fecha_corte);

    public Periodos_Pago findOnePeriodoPago(Integer periodoPago, Integer id_nomina);

    public List<Integer> findLapsoPeriodos(Integer idNomina, Date fechaInicio, Date fechaFin);

    public List<Semanas_Periodos_PagoDTO> obtenerFechasSemanalesConfianza();

    public List<Semanas_Periodos_PagoDTO> obtenerFechasSemanalesVarios();

    public List<Semanas_Periodos_PagoDTO> obtenerFechasSemanalesTransportacion();

    public List<Periodos_Pago> obtenerPeriodosGeneracionCercanos(Integer id_nomina);

    //------ Buscar el trabajador en Historico trabajador plaza --------
    public Historico_Trabajador_Plaza findHistoricoPlazaTrabajador(Integer id);

    public List<Trabajador_Prestamo_PersonalDTO> listadoCreditoPersonal();

    public List<Trabajador_SeparacionDTO> listadoSeparacion();

    public Integer findNominaTrabajadorByExpediente(String expediente);

    public String findNombreNominaTrabajadorByExpediente(String expediente);

    public Boolean esOperativoOAdministrativo(Integer trabajador_id);

    public List<Listado_RA_10DTO> findByNominaAndArea(Integer id_area, Integer id_nomina);

    public List<Trabajador> trabajadoresConExpedienteFiltroNominaOpcional(Integer tipo_nomina_id);

    public void findByPlazaAndTrabajadorHistorico(Integer plaza_id, Integer trabajador_id, Date fecha_inicial, String nomina);

    // ******************************** GRABAR DIAS DE CONTRATO (243) CONFIANZA EN TMP MOVIMIENTOS********************
    public Tmp_Movimientos saveMovimientos243Confianza(Tmp_Movimientos movimientos);

    // ******************************** GRABAR DIAS DE INASISTENCIAS (244) CONFIANZA EN TMP MOVIMIENTOS********************
    public Tmp_Movimientos saveMovimientos244Confianza(Tmp_Movimientos movimientos);

    // ******************************** GRABAR IMPORTE DE AGUINALDO (12) CONFIANZA EN TMP MOVIMIENTOS********************
    public Tmp_Movimientos saveMovimientos12Confianza(Tmp_Movimientos movimientos);

    // ******************************** GRABAR IMPORTE AGUINALDO ANTERIOR (330) EN TMP MOVIMIENTOS********************
    public void saveMovimientos330Confianza(Tmp_Movimientos movimientos);

    // ******************************** GRABAR IMPORTE DE DEVOLUCION ISR (339) EN TMP MOVIMIENTOS********************
    public void saveMovimientos339Confianza(Tmp_Movimientos movimientos);
}
