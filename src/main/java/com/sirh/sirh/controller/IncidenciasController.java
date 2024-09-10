/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

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
import com.sirh.sirh.config.SystemControllerLog;
import com.sirh.sirh.entity.Historico_Incapacidades;
import com.sirh.sirh.entity.Incapacidades;
import com.sirh.sirh.entity.Incidencias;
import com.sirh.sirh.entity.Incidencias_Contrato_Dias;
import com.sirh.sirh.entity.Percepciones_Por_Nomina;
import com.sirh.sirh.entity.Relacion_Incapacidades_Nomina_Dias;
import com.sirh.sirh.entity.Trabajador;
import com.sirh.sirh.entity.Trabajador_plaza;
import com.sirh.sirh.entity.Vacaciones;
import com.sirh.sirh.service.CatalogosService;
import com.sirh.sirh.service.IncidenciasService;
import com.sirh.sirh.service.TrabajadorService;
import com.sirh.sirh.serviceImpl.IncidenciasServiceImpl;
import com.sirh.sirh.serviceImpl.TrabajadorServiceImpl;
import com.sirh.sirh.util.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author akalvarez19
 */
@RestController
@RequestMapping("incidencias")
public class IncidenciasController {

    @Autowired
    IncidenciasService incidenciasService;

    @Autowired
    IncidenciasServiceImpl incidenciasServiceImpl;

    @Autowired
    TrabajadorService trabajadorService;

    @Autowired
    TrabajadorServiceImpl trabajadorServiceImpl;

    @Autowired
    CatalogosService catalogosService;

    //******************* SERVICIOS INCIDENCIAS  ************************************
    @GetMapping(value = "/buscarIncidenciasImportacion/{nomina_id}/{periodoPago}/{tipoIncidencia}")
    public ResponseEntity<List<Integer>> buscarIncidenciasImportacion(@PathVariable("nomina_id") Integer nomina_id, @PathVariable("periodoPago") Integer periodoPago, @PathVariable("tipoIncidencia") List<Integer> tipoIncidencia) {
        try {
            List<Integer> result = incidenciasService.findIdIncidenciaImportacion(nomina_id, periodoPago, tipoIncidencia);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** GUARDAR INCIDENCIA************************
    @SystemControllerLog(operation = "guardarIncidenciaTrabajador", type = "Generó una incidencia para kardex")
    @PostMapping(value = "/guardarIncidencia")
    public ResponseEntity<OutputEntity<String>> guardariIncidenciaTrabajador(@RequestBody IncidenciasDTO incidencias) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            Incidencias incidenciaGuardada = incidenciasService.saveIncidencias(incidencias);
            Integer idGenerado = incidenciaGuardada.getId_incidencia();
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de incidencia guardados con éxito");
            out.setData(String.valueOf(idGenerado));
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** EDITAR INCIDENCIA DEL TRABAJADOR ************************
    @SystemControllerLog(operation = "editarIncidenciasTrabajador", type = "Editó una incidencia")
    @PostMapping(value = "/editarIncidencia/{id_incidencia}")
    public ResponseEntity<OutputEntity<String>> editarIncidenciasTrabajador(@RequestBody IncidenciasDTO incidencias, @PathVariable Integer id_incidencia) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            incidenciasService.editarIncidencias(incidencias, id_incidencia);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de incidencia modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** EDITAR CORRIDA DEL TRABAJADOR ************************
    @PostMapping(value = "/agregarCorrida/{id_incidencia}")
    public ResponseEntity<OutputEntity<String>> agregarCorrida(@RequestBody Incidencias incidencias, @PathVariable Integer id_incidencia) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            incidenciasService.agregarCorrida(incidencias, id_incidencia);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de incidencia modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** EDITAR SOLO FECHAS DE LA INCIDENCIA DEL TRABAJADOR ************************
    @PostMapping(value = "/editarFechasIncidencias/{id_incidencia}")
    public ResponseEntity<OutputEntity<String>> editarFechasIncidencias(@RequestBody IncidenciasDTO incidencias, @PathVariable Integer id_incidencia) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            incidenciasService.editarFechasIncidencias(incidencias, id_incidencia);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de incidencia modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** BUSCAR INCIDENCIAS DEL TRABAJADOR ************************
    //Buscar contacto por Id_Persona 
    @GetMapping(value = "/buscarIncidencias/{trabajador_id}")
    public ResponseEntity<List<Incidencias>> buscarIncidenciasTrabajador(@PathVariable("trabajador_id") Integer trabajador_id) {
        try {
            List<Incidencias> result = incidenciasService.findIncidencias(trabajador_id);
            return new ResponseEntity<>(result, HttpStatus.OK);
            //out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            //return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarSinContrato/{id_trabajador}")
    public ResponseEntity<OutputEntity<Incidencias>> buscarSinContrato(@PathVariable("id_trabajador") Integer id_trabajador) {
        OutputEntity<Incidencias> out = new OutputEntity<>();
        try {
            Incidencias result = incidenciasService.findSinContratoTrabajador(id_trabajador);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarIncidenciasAutorizadas/{trabajador_id}")
    public ResponseEntity<List<Incidencias>> buscarIncidenciasTrabajadorAutorizadas(@PathVariable("trabajador_id") Integer trabajador_id) {
        try {
            List<Incidencias> result = incidenciasService.findIncidenciasTrabajador(trabajador_id);
            return new ResponseEntity<>(result, HttpStatus.OK);
            //out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            //return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarFolioIncidencia/{folio}")
    public ResponseEntity<List<Incidencias>> buscarFolioIncidencias(@PathVariable("folio") String folio) {
        try {
            List<Incidencias> result = incidenciasService.findIncidenciasFolio(folio);
            return new ResponseEntity<>(result, HttpStatus.OK);
            //out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            //return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarIncidenciasPeriodoVales/{trabajador_id}/{fecha_inicial}/{fecha_fin}")
    public ResponseEntity<Integer> buscarIncidenciasPeriodoVales(@PathVariable("trabajador_id") Integer trabajador_id, @PathVariable("fecha_inicial") Date fecha_inicial, @PathVariable("fecha_fin") Date fecha_fin) {

        try {
            Integer result = incidenciasService.cuentaIncidenciasByTrabajadoryPeriodoVales(trabajador_id, fecha_inicial, fecha_fin);
            if (result == null) {
                result = 0;
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarFaltasPeriodo/{trabajador_id}/{fecha_inicial}/{fecha_fin}")
    public ResponseEntity<Object> buscarFaltasPeriodo(@PathVariable("trabajador_id") Integer trabajador_id, @PathVariable("fecha_inicial") Date fecha_inicial, @PathVariable("fecha_fin") Date fecha_fin) {
        try {
            List<List<Object>> result = incidenciasService.incidenciasFaltaEnPeriodo(trabajador_id, fecha_inicial, fecha_fin);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizarEstadoIncidenciasFolio/{folio}")
    public ResponseEntity<String> actualizarIncidenciasRelacionadasFolio(@PathVariable("folio") String folio) {
        try {
            incidenciasService.updateIncidenciasEstatusFolioRelacionado(folio);
            return ResponseEntity.ok("Incidencias relacionadas actualizadas correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar incidencias: " + e.getMessage());
        }
    }

    //******************** SEARCH ESPECIFIC INCIDENCE FOR THE WORKER ************************
    @GetMapping(value = "buscarIncidenciaEspecificaTrabajador/{trabajador_id}/{incidencia_id}")
    public ResponseEntity<List<Incidencias>> findEspecificIncidenceWorker(@PathVariable("trabajador_id") Integer trabajador_id, @PathVariable("incidencia_id") Integer incidencia_id) {
        try {
            List<Incidencias> result = incidenciasService.findEspecificIncidenceWorker(trabajador_id, incidencia_id);
            return new ResponseEntity<>(result, HttpStatus.OK);
            //out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            //return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** BUSCAR INCIDENCIAS DEL TRABAJADOR ************************
    //Buscar contacto por Id_Persona 
    @GetMapping(value = "/buscarIncidenciaId/{id_incidencia}")
    public ResponseEntity<OutputEntity<Incidencias>> findLibro_H(@PathVariable("id_incidencia") Integer id_incidencia) {
        OutputEntity<Incidencias> out = new OutputEntity<>();
        try {
            Incidencias result = incidenciasService.findIncidenciasID(id_incidencia);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** DESACTIVAR INCIDENCIA ************************
    @PostMapping(value = "/estatusIncidencia/{id}/{estatus}")
    public ResponseEntity<Incidencias> eliminarIncidencia(@PathVariable("id") Integer id, @PathVariable("estatus") Integer estatus) {
        try {
            return new ResponseEntity<>(incidenciasService.estatus(id, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** ACTIVAR ESTADO INCIDENCIA ************************
    @PostMapping(value = "/activarIncidencia/{id}/{estado_incidencia_id}")
    public ResponseEntity<Incidencias> activarIncidencia(@PathVariable("id") Integer id, @PathVariable("estado_incidencia_id") Integer estado_incidencia_id) {
        try {
            return new ResponseEntity<>(incidenciasService.estadoActiv(id, estado_incidencia_id), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** CANCELAR ESTADO INCIDENCIA ************************
    @PostMapping(value = "/cancelarIncidencia/{id}/{estado_incidencia_id}")
    public ResponseEntity<Incidencias> cancelarIncidencia(@PathVariable("id") Integer id, @PathVariable("estado_incidencia_id") Integer estado_incidencia_id) {
        try {
            return new ResponseEntity<>(incidenciasService.estadoCancel(id, estado_incidencia_id), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarUltimaIncidencia/{id_trabajador}")
    public ResponseEntity<OutputEntity<Integer>> buscarUltimaIncidencia(@RequestBody @PathVariable("id_trabajador") Integer id_trabajador) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Integer result = incidenciasService.findLastIncidencia(id_trabajador);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //SERVICIO DE INCIDENCIAS Y CONTRATO DEL TRABAJADOR
    @GetMapping(value = "/buscarIncidenciaContrato/{contrato_id}")
    public ResponseEntity<List<Incidencias_Contrato_Dias>> buscarIncidenciasContrato(@PathVariable("contrato_id") Integer contrato_id) {
        try {
            List<Incidencias_Contrato_Dias> result = incidenciasService.findIncidenciaContrato(contrato_id);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarPercepcionesNominas")
    public ResponseEntity<Percepciones_Por_Nomina> listarPercepcionesPorNomina() {
        try {
            List<Percepciones_Por_Nomina> result = incidenciasService.findAllPercepcionesPorNomina();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscarPercepcionesPorNomina/{id_nomina}")
    public ResponseEntity<OutputEntity<List<Percepciones_Por_Nomina>>> findPercepcionesNomina(@PathVariable("id_nomina") Integer id_nomina) {
        try {
            List<Percepciones_Por_Nomina> result = incidenciasService.findPercepcionesNomina(id_nomina);
            return new ResponseEntity(result, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //SERVICIO DE INCIDENCIAS Y CONTRATO DEL TRABAJADOR
    @GetMapping(value = "/buscarDiasIncidencia/{contrato_id}/{incidencias_id}")
    public ResponseEntity<OutputEntity<Integer>> buscarDiasIncidencia(@PathVariable("contrato_id") Integer contrato_id, @PathVariable("incidencias_id") Integer incidencias_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Integer result = incidenciasService.findDiasInciencia(contrato_id, incidencias_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //******************** VACACIONES DEL TRABAJADOR ************************
//    @GetMapping(value = "/listarVacaciones")
//    public ResponseEntity<Vacaciones> listarVacaciones() {
//        try {
//            List<Vacaciones> result = incidenciasService.findAllVacaciones();
//            if (result.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity(result, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping(value = "/guardarVacaciones")
    public ResponseEntity<Vacaciones> guardarVacaciones(@RequestBody Vacaciones vacaciones) {
        try {
            return new ResponseEntity<>(incidenciasService.saveVacaciones(vacaciones), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarVacaciones/{id_vacaciones}")
    public ResponseEntity<OutputEntity<String>> editarVacaciones(@RequestBody Vacaciones vacaciones, @PathVariable Integer id_vacaciones) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            incidenciasService.editarVacaciones(vacaciones, id_vacaciones);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de vacaciones actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //OBTIENE LOS DIAS DISPONIBLES DE VACACIONES BUSCANDO POR EL ULTIMO REGISTRO DEL PERIODO VACACIONAL/
    @GetMapping(value = "/buscaPeriodoVacacional/{periodo_vacacional}/{trabajador_id}")
    public ResponseEntity<OutputEntity<Integer>> buscaPeriodoVacacional(@RequestBody @PathVariable("periodo_vacacional") Double periodo_vacacional, @PathVariable("trabajador_id") Integer trabajador_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Integer result = incidenciasService.buscaPeriodoVacacional(periodo_vacacional, trabajador_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarVacacionesTrabajador/{id_trabajador}/{incidencias_id}")
    public ResponseEntity<OutputEntity<String>> editarVacacionesTrabajador(@RequestBody Vacaciones vacaciones, @PathVariable Integer id_trabajador, @PathVariable Integer incidencias_id) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            incidenciasService.editarVacacionesTrabajador(vacaciones, id_trabajador, incidencias_id);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de vacaciones actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @SystemControllerLog(operation = "guardarIncidenciaTrabajadorVacaciones", type = "Generó una incidencia de vacaciones para kardex")
    @PostMapping(value = "/guardarIncidenciasVacaciones")
    public ResponseEntity<OutputEntity<String>> guardarIncidenciaVacaciones(@RequestBody Incidencias_VacacionesDTO i_v_dto) {
        OutputEntity<String> out = new OutputEntity<>();
        int id_trabajador = i_v_dto.getTrabajador_id();
        Trabajador_plaza tra_p = trabajadorService.findPlazaTrabajador(id_trabajador);
        int id_contrato = tra_p.getCat_tipo_contrato().getId_tipo_contrato();
        Trabajador trabajador = trabajadorService.findOne(id_trabajador);
        int prestaciones = trabajador.getPrestaciones_si_no();
        LocalDate antiguedad_n = trabajador.getFecha_antiguedad();
        LocalDate fecha_actual = LocalDate.now();
        Period diferencia = Period.between(antiguedad_n, fecha_actual);
        int anios = diferencia.getYears();
        int meses = diferencia.getMonths();
        //int dias = diferencia.getDays();

        Integer incidenciaId = null;

        if ((id_contrato == 2 && prestaciones == 2) || (id_contrato == 3 && prestaciones == 2)) {
            int dias_correspondientes = 10;
            int dias_disponibles = dias_correspondientes - i_v_dto.getNum_dias();
            if (dias_disponibles > 0) {
                incidenciaId = incidenciasServiceImpl.insertarDTO(i_v_dto);
                out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Vacaciones Registradas");
                out.setData(String.valueOf(incidenciaId));
            } else {
                out.error();
            }
        } else if (id_contrato == 1 || (id_contrato == 2 && prestaciones == 1) || id_contrato == 4 || id_contrato == 5 || id_contrato == 6 || id_contrato == 7) {
            if (meses < 6) {
                Integer dias_correspondientes = catalogosService.diasDisponibles(1, (anios * 1.0));
                int dias_disponibles = dias_correspondientes - i_v_dto.getNum_dias();
                if (dias_disponibles > 0) {
                    incidenciaId = incidenciasServiceImpl.insertarDTO(i_v_dto);
                    out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Vacaciones Registradas");
                    out.setData(String.valueOf(incidenciaId));
                } else {
                    out.error();
                }

            } else {
                out.error();
            }

        } else if (id_contrato == 3 && prestaciones == 1) {
            Integer dias_correspondientes = catalogosService.diasDisponibles(2, (anios * 1.0));
            int dias_disponibles = dias_correspondientes - i_v_dto.getNum_dias();
            if (dias_disponibles > 0) {
                incidenciaId = incidenciasServiceImpl.insertarDTO(i_v_dto);
                out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Vacaciones Registradas");
                out.setData(String.valueOf(incidenciaId));
            } else {
                out.error();
            }

        }

        return new ResponseEntity<>(out, out.getCode());
    }

    @PostMapping(value = "/guardarIncidenciasAyudaDiaPermiso")
    public ResponseEntity<OutputEntity<String>> guardarTodasTablasIncidenciasAyuda(@RequestBody Incidencias_Ayuda_DiasPermisoDTO i_a_dp_dto) {
        OutputEntity<String> out = new OutputEntity<>();

        try {
            Integer idIncidenciaGenerado = incidenciasServiceImpl.saveIncidencias_Ayuda_Dias_Permiso(i_a_dp_dto);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de incidencia guardados con éxito");
            out.setData(String.valueOf(idIncidenciaGenerado));
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping(value = "/guardarIncidenciasAyuda")
    public ResponseEntity<OutputEntity<String>> guardarIncidenciasAyuda(@RequestBody Incidencias_AyudaDTO i_a_dto) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            Integer idIncidenciaGenerado = incidenciasServiceImpl.saveIncidencias_Ayuda(i_a_dto);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de incidencia guardados con éxito");
            out.setData(String.valueOf(idIncidenciaGenerado));
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping(value = "/buscaUltimaFechaVacaciones/{trabajador_id}")
    public ResponseEntity<OutputEntity<Date>> buscaUltimaFechaVacaciones(@PathVariable("trabajador_id") Integer trabajador_id) {
        OutputEntity<Date> out = new OutputEntity<>();
        try {
            Date result = incidenciasService.buscaUltimaFechaVacaciones(trabajador_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //------ INCAPACIDADES DEL TRABAJADOR --------
    @GetMapping(value = "/buscarIncapacidades/{expediente}")
    public ResponseEntity<List<Incapacidades>> buscarIncapacidades(@PathVariable("expediente") Integer expediente) {
        try {
            List<Incapacidades> result = incidenciasService.findIncapacidades(expediente);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarIncapacidadesFolio/{folio}/{expediente}")
    public ResponseEntity<List<Incapacidades>> encontrarFolioIncapacidades(@PathVariable String folio, @PathVariable Integer expediente) throws ParseException {
        try {
            List<Incapacidades> incapacidades = incidenciasService.findIncapacidadByFolio(folio, expediente);
            return new ResponseEntity<>(incapacidades, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/encontrarPeriodosLiberacion/{expediente}/{desde}/{hasta}")
    public ResponseEntity<List<Incapacidades>> encontrarPeriodosLiberacion(@PathVariable Integer expediente, @PathVariable String desde, @PathVariable String hasta) throws ParseException {

        Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(desde);
        Date fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);
        try {
            List<Incapacidades> incapacidades = incidenciasService.findPeriodosLiberacion(expediente, fechaInicio, fechaFin);
            return new ResponseEntity<>(incapacidades, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/generarTXTIncapacidadesSUA/{desde}/{hasta}")
    public ResponseEntity<List<IncapacidadesDTO>> generarTXTIncapacidades(@PathVariable String desde, @PathVariable String hasta) throws ParseException {

        Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(desde);
        Date fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);
        try {
            List<IncapacidadesDTO> incapacidades = incidenciasService.generarTXTIncapacidades(fechaInicio, fechaFin);
            return new ResponseEntity<>(incapacidades, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/generarTXTIncapacidadesMovimientosSUA/{desde}/{hasta}")
    public ResponseEntity<List<IncapacidadesMovimientosDTO>> generarTXTIncapacidadesMovimientos(@PathVariable String desde, @PathVariable String hasta) throws ParseException {

        Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(desde);
        Date fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);
        try {
            List<IncapacidadesMovimientosDTO> incapacidades = incidenciasService.generarTXTIncapacidadesMovimientos(fechaInicio, fechaFin);
            return new ResponseEntity<>(incapacidades, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/generarInasistenciasSUA/{desde}/{hasta}")
    public ResponseEntity<List<IncidenciasSuaDTO>> generarTXTInasistenciasSUA(@PathVariable String desde, @PathVariable String hasta) throws ParseException {

        Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(desde);
        Date fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);
        try {
            List<IncidenciasSuaDTO> incapacidades = incidenciasService.generarTXTInasistenciasSUA(fechaInicio, fechaFin);
            return new ResponseEntity<>(incapacidades, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarIncidenciasTrabajadorTransportacion/{inicio_periodo_pago}/{fin_periodo_pago}/{trabajador_id}")
    public ResponseEntity<List<Incidencias_RA_TransportacionDTO>> obtenerIncidenciasRATransportacion(@PathVariable String inicio_periodo_pago, @PathVariable String fin_periodo_pago, @PathVariable Integer trabajador_id) throws ParseException {

        Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(inicio_periodo_pago);
        Date fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(fin_periodo_pago);
        try {
            List<Incidencias_RA_TransportacionDTO> incidenciasKardex = incidenciasService.obtenerIncidenciasRATransportacion(fechaInicio, fechaFin, trabajador_id);
            return new ResponseEntity<>(incidenciasKardex, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarIncapacidadesContinuas/{expediente}/{fechaInicioIncapacidadContinua}")
    public ResponseEntity<OutputEntity<Incapacidades>> encontrarIncapacidadesContinuas(
            @PathVariable Integer expediente,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicioIncapacidadContinua) {

        OutputEntity<Incapacidades> out = new OutputEntity<>();
        try {
            Incapacidades result = incidenciasService.findIncapacidadesContinuas(expediente, fechaInicioIncapacidadContinua);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarFechaFinIncapacidadInicial/{expediente}/{fechaFinal}")
    public ResponseEntity<OutputEntity<Incapacidades>> encontrarFechaFinIncapacidadesInicial(
            @PathVariable Integer expediente,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFinal) {

        OutputEntity<Incapacidades> out = new OutputEntity<>();
        try {
            Incapacidades result = incidenciasService.findFechaFinIncapacidadInicial(expediente, fechaFinal);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/autorizarIncapacidad/{id_incapacidad}")
    public ResponseEntity<OutputEntity<String>> autorizarIncapacidad(@RequestBody Incapacidades incapacidades, @PathVariable Integer id_incapacidad) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            incidenciasService.updateAutorizacionIncapacidades(incapacidades, id_incapacidad);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de Incapacidad actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping(value = "/buscarIdIncapacidad/{id_incapacidad}")
    public ResponseEntity<OutputEntity<Incapacidades>> findOneIncapacidad(@PathVariable("id_incapacidad") Integer id_incapacidad) {
        OutputEntity<Incapacidades> out = new OutputEntity<>();
        try {
            Incapacidades result = incidenciasService.findOneIncapacidad(id_incapacidad);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarIncapacidadesHistorico/{expediente}")
    public ResponseEntity<List<Historico_Incapacidades>> buscarIncapacidadesHistoricos(@PathVariable("expediente") Integer expediente) {
        try {
            List<Historico_Incapacidades> result = incidenciasService.findIncapacidadesHistorico(expediente);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarIdIncapacidadHistorico/{id_incapacidad}")
    public ResponseEntity<OutputEntity<Historico_Incapacidades>> findOneIncapacidadHistorico(@PathVariable("id_incapacidad") Integer id_incapacidad) {
        OutputEntity<Historico_Incapacidades> out = new OutputEntity<>();
        try {
            Historico_Incapacidades result = incidenciasService.findOneIncapacidadHistorico(id_incapacidad);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DOCUMENTOS TXT PARA IMPORTACIÓN DE INCIDENCIAS
    @GetMapping(value = "/listarDocumentoTextoCardexConfianza/{fecha_inicio}/{fecha_fin}")
    public ResponseEntity<IncidenciasCardexGeneralDTO> documentoTextoCardexIncidenciasConfianza(@PathVariable("fecha_inicio") String fecha_inicio, @PathVariable("fecha_fin") String fecha_fin) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime fechaInicio = LocalDate.parse(fecha_inicio, formatter).atStartOfDay();
            LocalDateTime fechaFin = LocalDate.parse(fecha_fin, formatter).atStartOfDay();

            List<IncidenciasCardexGeneralDTO> result = incidenciasService.documentoTextoCardexIncidenciasConfianza(fechaInicio, fechaFin);

            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity(result, HttpStatus.OK);
        } catch (DateTimeParseException e) {
            // Handle parsing exception
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarRelacionIncapacidadesNomina")
    public ResponseEntity<Relacion_Incapacidades_Nomina_Dias> findRelacionIncapacidades(
            @RequestParam("id_nomina") Integer id_nomina,
            @RequestParam("motivo_incapacidad_id") Integer motivo_incapacidad_id,
            @RequestParam(value = "prestaciones_si_no", required = false) Integer prestaciones_si_no,
            @RequestParam(value = "tipo_incapacidad_id", required = false) Integer tipo_incapacidad_id,
            @RequestParam("extemporanea_si_no") Integer extemporanea_si_no) {
        try {
            Relacion_Incapacidades_Nomina_Dias result = incidenciasService.findRelacionIncapacidades(id_nomina,
                    motivo_incapacidad_id,
                    prestaciones_si_no,
                    tipo_incapacidad_id,
                    extemporanea_si_no);
            if (result == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/validarIncapacidad")
    public ResponseEntity<ResultadoValidacionesIncapacidadesDTO> allValidacionesIncapacidades(
            @RequestParam("expediente") Integer expediente,
            @RequestParam("fecha_inicio_incapacidad") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha_inicio_incapacidad,
            @RequestParam("fecha_fin_incapacidad") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha_fin_incapacidad,
            @RequestParam("fecha_liberacion_incapacidad") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha_liberacion_incapacidad,
            @RequestParam("motivo_incapacidad_id") Integer motivo_incapacidad_id,
            @RequestParam(value = "tipo_incapacidad_id", required = false) Integer tipo_incapacidad_id) {

        try {
            ResultadoValidacionesIncapacidadesDTO result = incidenciasService.allValidacionesIncapacidades(
                    expediente,
                    fecha_inicio_incapacidad,
                    fecha_fin_incapacidad,
                    fecha_liberacion_incapacidad,
                    motivo_incapacidad_id,
                    tipo_incapacidad_id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarDocumentoTextoCardexBase/{fecha_inicio}/{fecha_fin}")
    public ResponseEntity<IncidenciasCardexGeneralDTO> documentoTextoCardexIncidenciasBase(@PathVariable("fecha_inicio") String fecha_inicio, @PathVariable("fecha_fin") String fecha_fin) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime fechaInicio = LocalDate.parse(fecha_inicio, formatter).atStartOfDay();
            LocalDateTime fechaFin = LocalDate.parse(fecha_fin, formatter).atStartOfDay();

            List<IncidenciasCardexGeneralDTO> result = incidenciasService.documentoTextoCardexIncidenciasBase(fechaInicio, fechaFin);

            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity(result, HttpStatus.OK);
        } catch (DateTimeParseException e) {
            // Handle parsing exception
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarDocumentoTextoCardexTransportacion/{fecha_inicio}/{fecha_fin}")
    public ResponseEntity<IncidenciasCardexGeneralDTO> documentoTextoCardexIncidenciasTransportacion(@PathVariable("fecha_inicio") String fecha_inicio, @PathVariable("fecha_fin") String fecha_fin) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime fechaInicio = LocalDate.parse(fecha_inicio, formatter).atStartOfDay();
            LocalDateTime fechaFin = LocalDate.parse(fecha_fin, formatter).atStartOfDay();

            List<IncidenciasCardexGeneralDTO> result = incidenciasService.documentoTextoCardexIncidenciasTransportacion(fechaInicio, fechaFin);

            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity(result, HttpStatus.OK);
        } catch (DateTimeParseException e) {
            // Handle parsing exception
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarDocumentoTextoCardexGeneralExpediente/{fecha_inicio}/{fecha_fin}/{idNomina}/{expediente}")
    public ResponseEntity<IncidenciasCardexGeneralDTO> documentoTextoCardexIncidenciasGeneralExpediente(@PathVariable("fecha_inicio") String fecha_inicio,
            @PathVariable("fecha_fin") String fecha_fin,
            @PathVariable("idNomina") Integer idNomina,
            @RequestParam List<Integer> incidenciasFiltradas,
            @PathVariable("expediente") String expediente
    ) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime fechaInicio = LocalDate.parse(fecha_inicio, formatter).atStartOfDay();
            LocalDateTime fechaFin = LocalDate.parse(fecha_fin, formatter).atStartOfDay();

            List<IncidenciasCardexGeneralDTO> result = incidenciasService.conteoIncidenciasKardexExpediente(fechaInicio, fechaFin, idNomina, incidenciasFiltradas, expediente);

            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity(result, HttpStatus.OK);
        } catch (DateTimeParseException e) {
            // Handle parsing exception
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarDocumentoTextoCardexSinExpediente/{fecha_inicio}/{fecha_fin}/{idNomina}")
    public ResponseEntity<IncidenciasCardexGeneralDTO> documentoTextoCardexIncidenciaSinExpediente(@PathVariable("fecha_inicio") String fecha_inicio,
            @PathVariable("fecha_fin") String fecha_fin,
            @PathVariable("idNomina") Integer idNomina,
            @RequestParam List<Integer> incidenciasFiltradas
    ) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime fechaInicio = LocalDate.parse(fecha_inicio, formatter).atStartOfDay();
            LocalDateTime fechaFin = LocalDate.parse(fecha_fin, formatter).atStartOfDay();

            List<IncidenciasCardexGeneralDTO> result = incidenciasService.conteoIncidenciasKardexGeneral(fechaInicio, fechaFin, idNomina, incidenciasFiltradas);

            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity(result, HttpStatus.OK);
        } catch (DateTimeParseException e) {
            // Handle parsing exception
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
