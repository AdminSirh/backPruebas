/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.IncapacidadesDTO;
import com.sirh.sirh.DTO.IncapacidadesMovimientosDTO;
import com.sirh.sirh.DTO.IncidenciasCardexGeneralDTO;
import com.sirh.sirh.DTO.IncidenciasDTO;
import com.sirh.sirh.DTO.IncidenciasSuaDTO;
import com.sirh.sirh.DTO.Incidencias_AyudaDTO;
import com.sirh.sirh.DTO.Incidencias_Ayuda_DiasPermisoDTO;
import com.sirh.sirh.DTO.Incidencias_RA_TransportacionDTO;
import com.sirh.sirh.DTO.Incidencias_VacacionesDTO;
import com.sirh.sirh.DTO.ResultadoValidacionesIncapacidadesDTO;
import com.sirh.sirh.entity.Ayuda;
import com.sirh.sirh.entity.Ayuda_Dias_Permiso;
import com.sirh.sirh.entity.Historico_Incapacidades;
import com.sirh.sirh.entity.Incapacidades;
import com.sirh.sirh.entity.Incidencias;
import com.sirh.sirh.entity.Incidencias_Contrato_Dias;
import com.sirh.sirh.entity.Percepciones_Por_Nomina;
import com.sirh.sirh.entity.Relacion_Incapacidades_Nomina_Dias;
import com.sirh.sirh.entity.Vacaciones;
import com.sirh.sirh.repository.AyudaRepository;
import com.sirh.sirh.repository.Ayuda_Dias_PermisoRepository;
import com.sirh.sirh.repository.Historico_IncapacidadesRepository;
import com.sirh.sirh.repository.IncapacidadesRepository;
import com.sirh.sirh.repository.IncidenciasRepository;
import com.sirh.sirh.repository.Incidencias_Contrato_DiasRepository;
import com.sirh.sirh.repository.Percepciones_Por_NominaRepository;
import com.sirh.sirh.repository.Relacion_Incapacidades_Nomina_DiasRepository;
import com.sirh.sirh.repository.VacacionesRepository;
import com.sirh.sirh.service.IncidenciasService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akalvarez19
 */
@Service
@Transactional
public class IncidenciasServiceImpl implements IncidenciasService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IncidenciasRepository incidenciasRepository;

    @Autowired
    private Incidencias_Contrato_DiasRepository incidencias_Contrato_DiasRepository;

    @Autowired
    private VacacionesRepository vacacionesRepository;

    @Autowired
    private AyudaRepository ayudaRepository;

    @Autowired
    private Ayuda_Dias_PermisoRepository ayuda_dias_permisoRepository;

    @Autowired
    private IncapacidadesRepository incapacidadesRepository;

    @Autowired
    private Percepciones_Por_NominaRepository percepciones_Por_NominaRepository;

    @Autowired
    private Historico_IncapacidadesRepository historico_IncapacidadesRepository;

    @Autowired
    private Relacion_Incapacidades_Nomina_DiasRepository relacion_Incapacidades_Nomina_DiasRepository;

    // ******************* INICIDENCIAS ******************
    @Override
    public List<Integer> findIdIncidenciaImportacion(Integer nomina_id, Integer periodoPago, List<Integer> tipoIncidencia) {
        return incidenciasRepository.findIdIncidenciaImportacion(nomina_id, periodoPago, tipoIncidencia);
    }

    @Override
    public Incidencias saveIncidencias(IncidenciasDTO incidencias) {
        Incidencias incidenciasT = new Incidencias();

        incidenciasT.setTrabajador_id(incidencias.getTrabajador_id());
        incidenciasT.setCat_incidencias(incidencias.getCat_incidencias());
        incidenciasT.setFecha_inicio(incidencias.getFecha_inicio());
        incidenciasT.setFecha_fin(incidencias.getFecha_fin());
        incidenciasT.setNum_dias(incidencias.getNum_dias());
        incidenciasT.setObservaciones(incidencias.getObservaciones());
        incidenciasT.setFolio(incidencias.getFolio());
        incidenciasT.setBis(incidencias.getBis());
        incidenciasT.setEstatus(1);

        incidenciasT.setEstado_incidencia_id(1);
        return incidenciasRepository.save(incidenciasT);
    }

    @Override
    public List<Incidencias> findIncidencias(Integer trabajador_id) {
        return incidenciasRepository.findIncidencias(trabajador_id);
    }

    @Override
    public List<Incidencias> findIncidenciasFolio(String folio) {
        return incidenciasRepository.findIncidenciasFolio(folio);
    }

    @Override
    public void updateIncidenciasEstatusFolioRelacionado(String folio) {
        incidenciasRepository.updateIncidenciasEstatusFolioRelacionado(folio);
    }

    @Override
    public Incidencias editarIncidencias(IncidenciasDTO incidencias, Integer id_incidencias) {
        Incidencias d = this.incidenciasRepository.findById(id_incidencias).get();
        Incidencias datos = d;
        datos.setFecha_inicio(incidencias.getFecha_inicio());
        datos.setFecha_fin(incidencias.getFecha_fin());
        datos.setNum_dias(incidencias.getNum_dias());
        datos.setFolio(incidencias.getFolio());
        datos.setObservaciones(incidencias.getObservaciones());
        datos.setCat_incidencias(incidencias.getCat_incidencias());
        datos.setCorrida_vacaciones(incidencias.getCorrida_vacaciones());
        //datos.setCat_incidencias(incidencias.getCat_incidencias());
        return incidenciasRepository.save(datos);
    }

    @Override
    public Incidencias agregarCorrida(Incidencias incidencias, Integer id_incidencias) {
        Incidencias d = this.incidenciasRepository.findById(id_incidencias).get();
        Incidencias datos = d;
        datos.setCorrida_vacaciones(incidencias.getCorrida_vacaciones());
        return incidenciasRepository.save(datos);
    }

    @Override
    public Incidencias editarFechasIncidencias(IncidenciasDTO incidencias, Integer id_incidencias) {
        Incidencias d = this.incidenciasRepository.findById(id_incidencias).get();
        Incidencias datos = d;
        datos.setFecha_inicio(incidencias.getFecha_inicio());
        datos.setFecha_fin(incidencias.getFecha_fin());
        return incidenciasRepository.save(datos);
    }

    @Override
    public List<Incidencias> findIncidenciasTrabajador(Integer trabajador_id) {
        return incidenciasRepository.findIncidenciasTrabajador(trabajador_id);
    }

    @Override
    public List<Incidencias> findEspecificIncidenceWorker(Integer trabajador_id, Integer incidencia_id) {
        return incidenciasRepository.findEspecificIncidenceWorker(trabajador_id, incidencia_id);
    }

    @Override
    public Incidencias findOneIncidencias(Integer id) {
        return incidenciasRepository.findById(id).get();
    }

    @Override
    public Incidencias findIncidenciasID(Integer id_libro_dias) {
        return incidenciasRepository.findById(id_libro_dias).get();
    }

    @Override
    public Incidencias estatus(Integer id, Integer estatus) {
        Incidencias estado = incidenciasRepository.findById(id).get();
        estado.setEstatus(estatus);

        return incidenciasRepository.save(estado);
    }

    @Override
    public Integer cuentaIncidenciasByTrabajadoryPeriodoVales(Integer trabajador_id, Date fecha_inicial, Date fecha_fin) {
        Integer result = incidenciasRepository.cuentaIncidenciasByTrabajadoryPeriodoVales(trabajador_id, fecha_inicial, fecha_fin);
        if (result == null) {
            result = 0;
        }
        return result;
    }

    @Override
    public List<List<Object>> incidenciasFaltaEnPeriodo(Integer trabajador_id, Date fecha_inicial, Date fecha_fin) {
        return incidenciasRepository.incidenciasFaltaEnPeriodo(trabajador_id, fecha_inicial, fecha_fin);
    }

    @Override
    public Incidencias estadoActiv(Integer id, Integer estado_incidencia_id) {
        Incidencias estado_inc = incidenciasRepository.findById(id).get();
        estado_inc.setEstado_incidencia_id(estado_incidencia_id);
        return incidenciasRepository.save(estado_inc);
    }

    @Override
    public Incidencias estadoCancel(Integer id, Integer estado_incidencia_id) {
        Incidencias estado_inc = incidenciasRepository.findById(id).get();
        estado_inc.setEstado_incidencia_id(estado_incidencia_id);
        return incidenciasRepository.save(estado_inc);
    }

    @Override
    public Integer findLastIncidencia(Integer trabajador_id) {
        return incidenciasRepository.findLastIncidencia(trabajador_id);
    }

//SERVICIO DE INCIDENCIAS Y CONTRATO DEL TRABAJADOR//
    @Override
    public List<Incidencias_Contrato_Dias> findIncidenciaContrato(Integer contrato_id) {
        return incidencias_Contrato_DiasRepository.findIncidenciaContrato(contrato_id);
    }

    @Override
    public List<Percepciones_Por_Nomina> findAllPercepcionesPorNomina() {
        return percepciones_Por_NominaRepository.findAll().stream().map(percepciones_Por_Nomina -> modelMapper.map(percepciones_Por_Nomina, Percepciones_Por_Nomina.class)).collect(Collectors.toList());
    }

    @Override
    public List<Percepciones_Por_Nomina> findPercepcionesNomina(Integer id_nomina) {
        return percepciones_Por_NominaRepository.findPercepcionesNomina(id_nomina);
    }

    @Override
    public Integer findDiasInciencia(Integer contrato_id, Integer incidencias_id) {
        return incidencias_Contrato_DiasRepository.findDiasInciencia(contrato_id, incidencias_id);
    }

    @Override
    public Incidencias findSinContratoTrabajador(Integer id_trabajador) {
        return incidenciasRepository.findSinContratoTrabajador(id_trabajador);
    }

    //------ Trabajador Vacaciones --------
//    @Override
//    public List<Vacaciones> findAllVacaciones() {
//        return vacacionesRepository.findAll().stream().map(vacaciones -> modelMapper.map(vacaciones, Vacaciones.class)).collect(Collectors.toList());
//    }
    @Override
    public Vacaciones saveVacaciones(Vacaciones vacaciones) {
        Vacaciones vac = new Vacaciones();
        LocalDate datetime = LocalDate.now();
        vac.setTrabajador_id(vacaciones.getTrabajador_id());
        vac.setExpediente(vacaciones.getExpediente());
        vac.setDias_paseos(vacaciones.getDias_paseos());
        vac.setFecha_inicio(vacaciones.getFecha_inicio());
        vac.setFecha_fin(vacaciones.getFecha_fin());
        vac.setPeriodo_vacacional(vacaciones.getPeriodo_vacacional());
        vac.setAntiguedad(vacaciones.getAntiguedad());
        vac.setDias_totales(vacaciones.getDias_totales());
        vac.setEstado_vacaciones_id(6);
        vac.setDias_disponibles(vacaciones.getDias_disponibles());
        vac.setPeriodo_aplicacion(vacaciones.getPeriodo_aplicacion());
        vac.setMotivo_cancelacion(vacaciones.getMotivo_cancelacion());
        vac.setReferencia_cancelacion(vacaciones.getReferencia_cancelacion());
        vac.setFecha_inicio_anio_periodo_vacacional(vacaciones.getFecha_inicio_anio_periodo_vacacional());
        vac.setFecha_fin_anio_periodo_vacacional(vacaciones.getFecha_fin_anio_periodo_vacacional());
        vac.setDias_disponibles(vacaciones.getDias_disponibles());
        vac.setObservaciones(vacaciones.getObservaciones());
        vac.setPeriodo_aplicacion_fin(vacaciones.getPeriodo_aplicacion_fin());
        vac.setA_discontinua(vacaciones.getA_discontinua());
        vac.setDias_proporcionales(vacaciones.getDias_proporcionales());
        vac.setGenera_pago_si_no(vacaciones.getGenera_pago_si_no());
        vac.setIncidencias_id(vacaciones.getIncidencias_id());
        vac.setFecha_movimiento(datetime);

        return vacacionesRepository.save(vac);
    }

    @Override
    public Vacaciones editarVacaciones(Vacaciones vacaciones, Integer id_vacaciones) {
        Vacaciones v = this.vacacionesRepository.findById(id_vacaciones).get();
        Vacaciones vac = v;

        vac.setMotivo_cancelacion(vacaciones.getMotivo_cancelacion());
        vac.setReferencia_cancelacion(vacaciones.getReferencia_cancelacion());

        return vacacionesRepository.save(vac);
    }

    @Override
    public Integer buscaPeriodoVacacional(Double tipo_dias_vacaciones_id, Integer trabajador_id) {
        return vacacionesRepository.buscaPeriodoVacacional(tipo_dias_vacaciones_id, trabajador_id);
    }

    @Override
    public Vacaciones editarVacacionesTrabajador(Vacaciones vacaciones, Integer id_trabajador, Integer incidencias_id) {
        Vacaciones v = this.vacacionesRepository.findVacacionesTrabajador(id_trabajador, incidencias_id);
        Vacaciones vac = v;

        vac.setMotivo_cancelacion(vacaciones.getMotivo_cancelacion());
        vac.setReferencia_cancelacion(vacaciones.getReferencia_cancelacion());
        vac.setEstado_vacaciones_id(3);
        return vacacionesRepository.save(vac);
    }

    @Override
    public Date buscaUltimaFechaVacaciones(Integer trabajador_id) {
        return vacacionesRepository.buscaUltimaFechaVacaciones(trabajador_id);
    }

    //------ Trabajador Vacaciones-Incidencias --------
    public Integer insertarDTO(Incidencias_VacacionesDTO i_v_dto) {
        LocalDate datetime = LocalDate.now();
        Incidencias in = new Incidencias();
        in.setTrabajador_id(i_v_dto.getTrabajador_id());
        in.setCat_incidencias(i_v_dto.getCat_incidencias());
        in.setFecha_inicio(i_v_dto.getFecha_inicio());
        in.setFecha_fin(i_v_dto.getFecha_fin());
        in.setNum_dias(i_v_dto.getNum_dias());
        in.setObservaciones(i_v_dto.getObservaciones());
        in.setFolio(i_v_dto.getFolio());
        in.setBis(i_v_dto.getBis());
        in.setEstatus(1);
        in.setEstado_incidencia_id(1);

        incidenciasRepository.save(in);

        Vacaciones vac = new Vacaciones();
        vac.setTrabajador_id(i_v_dto.getTrabajador_id());
        vac.setExpediente(i_v_dto.getExpediente());
        vac.setDias_paseos(i_v_dto.getDias_paseos());
        vac.setFecha_inicio(i_v_dto.getFecha_inicio());
        vac.setFecha_fin(i_v_dto.getFecha_fin());
        vac.setPeriodo_vacacional(i_v_dto.getPeriodo_vacacional());
        vac.setAntiguedad(i_v_dto.getAntiguedad());
        vac.setDias_totales(i_v_dto.getDias_totales());
        vac.setEstado_vacaciones_id(6);
        vac.setDias_disponibles(i_v_dto.getDias_disponibles());
        vac.setPeriodo_aplicacion(i_v_dto.getPeriodo_aplicacion());
        vac.setMotivo_cancelacion(i_v_dto.getMotivo_cancelacion());
        vac.setReferencia_cancelacion(i_v_dto.getReferencia_cancelacion());
        vac.setFecha_inicio_anio_periodo_vacacional(i_v_dto.getFecha_inicio_anio_periodo_vacacional());
        vac.setFecha_fin_anio_periodo_vacacional(i_v_dto.getFecha_fin_anio_periodo_vacacional());
        vac.setDias_disponibles(i_v_dto.getDias_disponibles());
        vac.setObservaciones(i_v_dto.getObservaciones());
        vac.setPeriodo_aplicacion_fin(i_v_dto.getPeriodo_aplicacion_fin());
        vac.setA_discontinua(0);
        vac.setDias_proporcionales(i_v_dto.getDias_proporcionales());
        vac.setGenera_pago_si_no(i_v_dto.getGenera_pago_si_no());
        vac.setIncidencias_id(i_v_dto.getIncidencias_id());
        vac.setFecha_movimiento(datetime);
        vac.setIncidencias_id(in.getId_incidencia());

        vacacionesRepository.save(vac);

        return in.getId_incidencia();
    }

    //------ Trabajador Incidencias-Ayudas --------
    public Integer saveIncidencias_Ayuda_Dias_Permiso(Incidencias_Ayuda_DiasPermisoDTO i_a_dp_dto) {
        Incidencias in = new Incidencias();
        in.setTrabajador_id(i_a_dp_dto.getTrabajador_id());
        in.setCat_incidencias(i_a_dp_dto.getCat_incidencias());
        in.setFecha_inicio(i_a_dp_dto.getFecha_inicio());
        in.setFecha_fin(i_a_dp_dto.getFecha_fin());
        in.setNum_dias(i_a_dp_dto.getNum_dias());
        in.setObservaciones(i_a_dp_dto.getObservaciones());
        in.setFolio(i_a_dp_dto.getFolio());
        in.setBis(i_a_dp_dto.getBis());
        in.setEstatus(1);
        in.setEstado_incidencia_id(1);

        //Se guarda el id de la incidencia generada para asignárselo a la otra tabla de ayuda la cual será la llave foránea hacia incidencias
        Incidencias incidenciasGuardada = incidenciasRepository.save(in);
        Integer incidenciaIdGenerado = incidenciasGuardada.getId_incidencia();

        Ayuda_Dias_Permiso adp = new Ayuda_Dias_Permiso();
        adp.setDia_1(i_a_dp_dto.getDia_1());
        adp.setDia_2(i_a_dp_dto.getDia_2());
        adp.setDia_3(i_a_dp_dto.getDia_3());
        adp.setDia_4(i_a_dp_dto.getDia_4());
        adp.setDia_5(i_a_dp_dto.getDia_5());
        adp.setDia_6(i_a_dp_dto.getDia_6());

        Ayuda_Dias_Permiso diasPermisoGuardado = ayuda_dias_permisoRepository.save(adp);
        Integer diaPermisoIdGenerado = diasPermisoGuardado.getId_ayuda_dias();

        Ayuda a = new Ayuda();
        a.setFecha_emision(i_a_dp_dto.getFecha_emision());
        a.setFecha_recepcion(i_a_dp_dto.getFecha_recepcion());
        a.setNombre(i_a_dp_dto.getNombre());
        a.setApellido_paterno(i_a_dp_dto.getApellido_paterno());
        a.setApellido_materno(i_a_dp_dto.getApellido_materno());
        //Aquí se asigna la llave foránea hacia incidencias
        a.setIncidencia_id(incidenciaIdGenerado);
        a.setParentesco_id(i_a_dp_dto.getParentesco_id());
        a.setOrigen(i_a_dp_dto.getOrigen());
        a.setPeriodo_pago_id(i_a_dp_dto.getPeriodo_pago_id());
        a.setEstatus(1);
        a.setTrabajador_id(i_a_dp_dto.getTrabajador_id());
        a.setMonto(i_a_dp_dto.getMonto());
        //Esta es la llave foránea hacia la tabla de los días de permiso
        a.setAyuda_dias_id(diaPermisoIdGenerado);
        a.setFecha_evento(i_a_dp_dto.getFecha_evento());
        ayudaRepository.save(a);

        return incidenciasGuardada.getId_incidencia();
    }

    public Integer saveIncidencias_Ayuda(Incidencias_AyudaDTO i_a_dto) {
        Incidencias in = new Incidencias();
        in.setTrabajador_id(i_a_dto.getTrabajador_id());
        in.setCat_incidencias(i_a_dto.getCat_incidencias());
        in.setFecha_inicio(i_a_dto.getFecha_inicio());
        in.setFecha_fin(i_a_dto.getFecha_fin());
        in.setNum_dias(i_a_dto.getNum_dias());
        in.setObservaciones(i_a_dto.getObservaciones());
        in.setFolio(i_a_dto.getFolio());
        in.setBis(i_a_dto.getBis());
        in.setEstatus(1);
        in.setEstado_incidencia_id(1);

        //Se guarda el id de la incidencia generada para asignárselo a la otra tabla de ayuda la cual será la llave foránea hacia incidencias
        Incidencias incidenciasGuardada = incidenciasRepository.save(in);
        Integer incidenciaIdGenerado = incidenciasGuardada.getId_incidencia();

        Ayuda a = new Ayuda();
        a.setFecha_emision(i_a_dto.getFecha_emision());
        a.setFecha_recepcion(i_a_dto.getFecha_recepcion());
        a.setNombre(i_a_dto.getNombre());
        a.setApellido_paterno(i_a_dto.getApellido_paterno());
        a.setApellido_materno(i_a_dto.getApellido_materno());
        //Aquí se asigna la llave foránea hacia incidencias
        a.setIncidencia_id(incidenciaIdGenerado);
        a.setParentesco_id(i_a_dto.getParentesco_id());
        a.setOrigen(i_a_dto.getOrigen());
        a.setPeriodo_pago_id(i_a_dto.getPeriodo_pago_id());
        a.setEstatus(1);
        a.setTrabajador_id(i_a_dto.getTrabajador_id());
        a.setMonto(i_a_dto.getMonto());
        ayudaRepository.save(a);

        return incidenciasGuardada.getId_incidencia();

    }

    //------ INCAPACIDADES DEL TRABAJADOR --------
    @Override
    public List<Incapacidades> findIncapacidades(Integer expediente) {
        return incapacidadesRepository.findIncapacidades(expediente);
    }

    @Override
    public List<Incapacidades> findIncapacidadByFolio(String folio, Integer expediente) {
        return incapacidadesRepository.findIncapacidadByFolio(folio, expediente);
    }

    @Override
    public List<Incapacidades> findPeriodosLiberacion(Integer expediente, Date desde, Date hasta) {
        return incapacidadesRepository.findPeriodosLiberacion(expediente, desde, hasta);
    }

    @Override
    public List<IncapacidadesDTO> generarTXTIncapacidades(Date desde, Date hasta) {
        return incapacidadesRepository.generarTXTIncapacidades(desde, hasta);
    }

    @Override
    public List<IncapacidadesMovimientosDTO> generarTXTIncapacidadesMovimientos(Date desde, Date hasta) {
        return incapacidadesRepository.generarTXTIncapacidadesMovimientos(desde, hasta);
    }

    @Override
    public List<IncidenciasSuaDTO> generarTXTInasistenciasSUA(Date desde, Date hasta) {
        return incidenciasRepository.generarTXTInasistenciasSUA(desde, hasta);
    }

    @Override
    public List<Incidencias_RA_TransportacionDTO> obtenerIncidenciasRATransportacion(Date inicio_periodo_pago, Date fin_periodo_pago, Integer trabajador_id) {
        return incidenciasRepository.obtenerIncidenciasRATransportacion(inicio_periodo_pago, fin_periodo_pago, trabajador_id);
    }

    @Override
    public Incapacidades findIncapacidadesContinuas(Integer expediente, LocalDate fechaInicioIncapacidadContinua) {
        return incapacidadesRepository.findIncapacidadesContinuas(expediente, fechaInicioIncapacidadContinua);
    }

    @Override
    public Incapacidades findFechaFinIncapacidadInicial(Integer expediente, LocalDate fechaFinal) {
        return incapacidadesRepository.findFechaFinIncapacidadInicial(expediente, fechaFinal);
    }

    @Override
    public Incapacidades updateAutorizacionIncapacidades(Incapacidades incapacidades, Integer id_incapacidad) {
        Incapacidades i = this.incapacidadesRepository.findById(id_incapacidad).get();
        Incapacidades inc = i;
        LocalDateTime now = LocalDateTime.now();
        inc.setAutorizar(incapacidades.getAutorizar());
        inc.setFecha_autorizacion(now);
        return incapacidadesRepository.save(inc);
    }

    @Override
    public Incapacidades findOneIncapacidad(Integer id_incapacidad) {
        return incapacidadesRepository.findById(id_incapacidad).get();
    }

    @Override
    public List<Historico_Incapacidades> findIncapacidadesHistorico(Integer expediente) {
        return historico_IncapacidadesRepository.findIncapacidadesHistorico(expediente);
    }

    @Override
    public Historico_Incapacidades findOneIncapacidadHistorico(Integer id_incapacidad) {
        return historico_IncapacidadesRepository.findById(id_incapacidad).get();
    }

    @Override
    public Relacion_Incapacidades_Nomina_Dias findRelacionIncapacidades(Integer id_nomina,
            Integer motivo_incapacidad_id,
            Integer prestaciones_si_no,
            Integer tipo_incapacidad_id,
            Integer extemporanea_si_no) {
        return relacion_Incapacidades_Nomina_DiasRepository.findRelacionIncapacidades(id_nomina, motivo_incapacidad_id, prestaciones_si_no, tipo_incapacidad_id, extemporanea_si_no);
    }

    @Override
    public ResultadoValidacionesIncapacidadesDTO allValidacionesIncapacidades(Integer expediente,
            LocalDate fecha_inicio_incapacidad,
            LocalDate fecha_fin_incapacidad,
            LocalDate fecha_liberacion_incapacidad,
            Integer motivo_incapacidad_id,
            Integer tipo_incapacidad_id) {
        // ******************************* VALIDACION PARA INCAPACIDAD EXTEMPORÁNEA ****************************
        // Sumar tres días a la fecha de inicio de la incapacidad para calcular la fecha límite de extemporánea
        LocalDate fechaLimiteExtemporanea = fecha_inicio_incapacidad.plusDays(3);
        // Sumar un día a la fecha final para considerar incapacidades continuas
        LocalDate fechaInicioIncapacidadContinua = fecha_fin_incapacidad.plusDays(1);
        // Restar un día a la fecha inicial de la incapacidad para considerar incapacidades iniciales relacionadas
        LocalDate fechaFinIncapacidadInicial = fecha_inicio_incapacidad.minusDays(1);

        // Comparar las fechas para determinar si son extemporáneas
        boolean extemporanea = fecha_liberacion_incapacidad.isAfter(fechaLimiteExtemporanea);

        /* 
    -Para encontrar incapacidades por EG de tipo continuas que correspondan al inicio de la incapacidad continua
    a partir de una incapacidad por EG inicial
    -Para encontrar incapacidades por EG iniciales que correspondan a la fecha de inicio menos un día, la cual
    representará la incapacidad anterior a partir de una incapacidad por EG Continua
         */
        Incapacidades incapacidadesContinuas = null;
        Incapacidades incapacidadesIniciales = null;

        // ***************************** VALIDACION PARA INCAPACIDADES INICIALES CON UNA INCAPACIDAD CONTINUA ****************************
        // motivo_incapacidad_id corresponde al registro número 1 de la tabla catalogo_motivo_incapacidad, "Enfermedad General"
        // tipo_incapacidad_id corresponde al registro número 1 de la tabla catalogo_tipo_incapacidad "Inicial"
        if (motivo_incapacidad_id == 1 && tipo_incapacidad_id == 1) {
            incapacidadesContinuas = findIncapacidadesContinuas(expediente, fechaInicioIncapacidadContinua);
        } // ***************************** VALIDACION PARA INCAPACIDADES CONTINUAS CON UNA INCAPACIDAD INICIAL PREVIA ****************************
        // tipo_incapacidad_id corresponde al registro número 2 de la tabla catalogo_tipo_incapacidad "Continuo"
        else if (motivo_incapacidad_id == 1 && tipo_incapacidad_id == 2) {
            incapacidadesIniciales = findFechaFinIncapacidadInicial(expediente, fechaFinIncapacidadInicial);
        }

        // Set de los valores calculados a la entidad dto de validaciones 
        ResultadoValidacionesIncapacidadesDTO resultado = new ResultadoValidacionesIncapacidadesDTO();
        resultado.setExtemporanea(extemporanea);
        resultado.setExpediente(expediente);
        resultado.setFechaInicioIncapacidad(fecha_inicio_incapacidad);
        resultado.setFechaFinIncapacidad(fecha_fin_incapacidad);
        resultado.setFechaLiberacionIncapacidad(fecha_liberacion_incapacidad);
        resultado.setIncapacidadContinua(incapacidadesContinuas);
        resultado.setIncapacidadInicial(incapacidadesIniciales);

        return resultado;
    }

    //-------Importación de Incidencias en formato txt--------------
    @Override
    public List<IncidenciasCardexGeneralDTO> documentoTextoCardexIncidenciasConfianza(LocalDateTime fecha_inicial, LocalDateTime fecha_final) {
        return incidenciasRepository.documentoTextoCardexIncidenciasConfianza(fecha_inicial, fecha_final);
    }

    @Override
    public List<IncidenciasCardexGeneralDTO> documentoTextoCardexIncidenciasBase(LocalDateTime fecha_inicial, LocalDateTime fecha_final) {
        return incidenciasRepository.documentoTextoCardexIncidenciasBase(fecha_inicial, fecha_final);
    }

    @Override
    public List<IncidenciasCardexGeneralDTO> documentoTextoCardexIncidenciasTransportacion(LocalDateTime fecha_inicial, LocalDateTime fecha_final) {
        return incidenciasRepository.documentoTextoCardexIncidenciasTransportacion(fecha_inicial, fecha_final);
    }

    @Override
    public List<IncidenciasCardexGeneralDTO> conteoIncidenciasKardexExpediente(LocalDateTime fecha_inicial, LocalDateTime fecha_final, Integer idNomina, List<Integer> incidenciasFiltradas, String expediente) {
        return incidenciasRepository.conteoIncidenciasKardexExpediente(fecha_inicial, fecha_final, idNomina, incidenciasFiltradas, expediente);
    }

    @Override
    public List<IncidenciasCardexGeneralDTO> conteoIncidenciasKardexGeneral(LocalDateTime fecha_inicial, LocalDateTime fecha_final, Integer idNomina, List<Integer> incidenciasFiltradas) {
        return incidenciasRepository.conteoIncidenciasKardexGeneral(fecha_inicial, fecha_final, idNomina, incidenciasFiltradas);
    }

}
