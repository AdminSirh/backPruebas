/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.IncapacidadesDTO;
import com.sirh.sirh.DTO.IncapacidadesMovimientosDTO;
import com.sirh.sirh.DTO.Incidencias_RA_TransportacionDTO;
import com.sirh.sirh.DTO.IncidenciasCardexGeneralDTO;
import com.sirh.sirh.DTO.IncidenciasDTO;
import com.sirh.sirh.DTO.IncidenciasSuaDTO;
import com.sirh.sirh.DTO.ResultadoValidacionesIncapacidadesDTO;
import com.sirh.sirh.entity.Historico_Incapacidades;
import com.sirh.sirh.entity.Incapacidades;
import com.sirh.sirh.entity.Incidencias;
import com.sirh.sirh.entity.Incidencias_Contrato_Dias;
import com.sirh.sirh.entity.Percepciones_Por_Nomina;
import com.sirh.sirh.entity.Relacion_Incapacidades_Nomina_Dias;
import com.sirh.sirh.entity.Vacaciones;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author akalvarez19
 */
public interface IncidenciasService {

    public List<Integer> findIdIncidenciaImportacion(Integer nomina_id, Integer periodoPago, List<Integer> tipoIncidencia);

    public Incidencias saveIncidencias(IncidenciasDTO incidencias);

    public List<Incidencias> findIncidencias(Integer trabajador_id);

    public List<Incidencias> findIncidenciasTrabajador(Integer trabajador_id);

    public List<Incidencias> findIncidenciasFolio(String folio);

    public void updateIncidenciasEstatusFolioRelacionado(String folio);

    public List<Incidencias> findEspecificIncidenceWorker(Integer trabajador_id, Integer incidencia_id);

    public Incidencias editarIncidencias(IncidenciasDTO incidencias, Integer id_incidencia);

    public Incidencias agregarCorrida(Incidencias incidencias, Integer id_incidencia);

    public Incidencias editarFechasIncidencias(IncidenciasDTO incidencias, Integer id_incidencia);

    public Incidencias findOneIncidencias(Integer id);

    public Incidencias findIncidenciasID(Integer id_incidencia);

    public Incidencias estatus(Integer id, Integer estatus);

    public Incidencias estadoActiv(Integer id, Integer estado_incidencia_id);

    public Incidencias estadoCancel(Integer id, Integer estado_incidencia_id);

    public Integer findLastIncidencia(Integer trabajador_id);

    public Integer cuentaIncidenciasByTrabajadoryPeriodoVales(Integer trabajador_id, Date fecha_inicial, Date fecha_fin);

    public List<List<Object>> incidenciasFaltaEnPeriodo(Integer trabajador_id, Date fecha_inicial, Date fecha_fin);

    //SERVICIO DE INCIDENCIAS Y CONTRATO DEL TRABAJADOR
    public List<Incidencias_Contrato_Dias> findIncidenciaContrato(Integer contrato_id);

    public List<Percepciones_Por_Nomina> findAllPercepcionesPorNomina();

    public List<Percepciones_Por_Nomina> findPercepcionesNomina(Integer id_nomina);

    public Incidencias findSinContratoTrabajador(Integer id_trabajador);

    public Integer findDiasInciencia(Integer contrato_id, Integer incidencias_id);
    //------ Vacaciones del Trabajador --------

    //public List<Vacaciones> findAllVacaciones();
    public Vacaciones saveVacaciones(Vacaciones vacaciones);

    public Vacaciones editarVacaciones(Vacaciones vacaciones, Integer id_vacaciones);

    public Integer buscaPeriodoVacacional(Double periodo_vacacional, Integer trabajador_id);

    public Vacaciones editarVacacionesTrabajador(Vacaciones vacaciones, Integer trabajador_id, Integer incidencias_id);

    public Date buscaUltimaFechaVacaciones(Integer trabajador_id);

    //------ Incapacidades del Trabajador --------
    public Incapacidades findOneIncapacidad(Integer id_incapacidad);

    public List<Incapacidades> findIncapacidades(Integer expediente);

    public List<Incapacidades> findIncapacidadByFolio(String folio, Integer expediente);

    public List<Incapacidades> findPeriodosLiberacion(Integer expediente, Date desde, Date hasta);

    public List<IncapacidadesDTO> generarTXTIncapacidades(Date desde, Date hasta);

    public List<IncapacidadesMovimientosDTO> generarTXTIncapacidadesMovimientos(Date desde, Date hasta);

    public List<IncidenciasSuaDTO> generarTXTInasistenciasSUA(Date desde, Date hasta);

    public List<Incidencias_RA_TransportacionDTO> obtenerIncidenciasRATransportacion(Date inicio_periodo_pago, Date fin_periodo_pago, Integer trabajador_id);

    public Incapacidades findIncapacidadesContinuas(Integer expediente, LocalDate fechaInicioIncapacidadContinua);

    public Incapacidades findFechaFinIncapacidadInicial(Integer expediente, LocalDate fechaFinal);

    public Incapacidades updateAutorizacionIncapacidades(Incapacidades incapacidades, Integer id_incapacidad);

    public Historico_Incapacidades findOneIncapacidadHistorico(Integer id_incapacidad);

    public List<Historico_Incapacidades> findIncapacidadesHistorico(Integer expediente);

    public Relacion_Incapacidades_Nomina_Dias findRelacionIncapacidades(Integer id_nomina,
            Integer motivo_incapacidad_id,
            Integer prestaciones_si_no,
            Integer tipo_incapacidad_id,
            Integer extemporanea_si_no);

    public ResultadoValidacionesIncapacidadesDTO allValidacionesIncapacidades(Integer expediente,
            LocalDate fecha_inicio_incapacidad,
            LocalDate fecha_fin_incapacidad,
            LocalDate fecha_liberacion_incapacidad,
            Integer motivo_incapacidad_id,
            Integer tipo_incapacidad_id);

    //-------Importación de Incidencias en formato txt--------------
    public List<IncidenciasCardexGeneralDTO> documentoTextoCardexIncidenciasConfianza(LocalDateTime fecha_inicial, LocalDateTime fecha_final);

    public List<IncidenciasCardexGeneralDTO> documentoTextoCardexIncidenciasBase(LocalDateTime fecha_inicial, LocalDateTime fecha_final);

    public List<IncidenciasCardexGeneralDTO> documentoTextoCardexIncidenciasTransportacion(LocalDateTime fecha_inicial, LocalDateTime fecha_final);

    public List<IncidenciasCardexGeneralDTO> conteoIncidenciasKardexExpediente(LocalDateTime fecha_inicial, LocalDateTime fecha_final, Integer idNomina, List<Integer> incidenciasFiltradas, String expediente);

    public List<IncidenciasCardexGeneralDTO> conteoIncidenciasKardexGeneral(LocalDateTime fecha_inicial, LocalDateTime fecha_final, Integer idNomina, List<Integer> incidenciasFiltradas);

}
