package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.Listado_RA_10DTO;
import com.sirh.sirh.DTO.PlantillaMensualDTO;
import com.sirh.sirh.DTO.Semanas_Periodos_PagoDTO;
import com.sirh.sirh.DTO.TrabajadorDTO;
import com.sirh.sirh.DTO.Trabajador_FonacotDTO;
import com.sirh.sirh.DTO.Trabajador_Prestamo_PersonalDTO;
import com.sirh.sirh.DTO.Trabajador_SeparacionDTO;
import com.sirh.sirh.config.SystemControllerLog;
import com.sirh.sirh.entity.Cuenta_Bancaria;
import com.sirh.sirh.entity.Historico_Trabajador_Plaza;
import com.sirh.sirh.entity.Periodos_Pago;
import com.sirh.sirh.entity.Registro_Plantilla_Mensual;
import com.sirh.sirh.entity.Tmp_Movimientos;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sirh.sirh.exception.OutputEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.sirh.sirh.entity.Trabajador;
import com.sirh.sirh.entity.Trabajador_plaza;
import com.sirh.sirh.entity.Validaciones;
import com.sirh.sirh.service.TrabajadorService;
import com.sirh.sirh.serviceImpl.TrabajadorServiceImpl;
import com.sirh.sirh.util.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author nreyes22
 */
@RestController
@RequestMapping("trabajador")
public class TrabajadorController {

    @Autowired
    TrabajadorService trabajadorService;

    @Autowired
    TrabajadorServiceImpl trabajadorServiceImpl;

    //************************** GUARDAR TRABAJADOR ****************************
    @PostMapping(value = "/guardarTrabajador")
    public ResponseEntity<OutputEntity<String>> guardarPersonaGenerales(@RequestBody Trabajador trabajador) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            trabajadorService.save(trabajador);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Los datos del trabajador han sido guardados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    // ******************** LISTAR TRABAJADORES *****************************
    @GetMapping(value = "/listado")
    public ResponseEntity<List<Trabajador>> listarTrabajadores() {
        try {
            List<Trabajador> trabajador = trabajadorService.personasConExpediente();
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ********************* BUSCAR TRABAJADOR POR MEDIO DE SU ID ***************
    @GetMapping(value = "/buscarTrabajador/{id_trabajador}")
    public ResponseEntity<OutputEntity<Trabajador>> buscarTrabajador(@PathVariable Integer id_trabajador) {
        OutputEntity<Trabajador> out = new OutputEntity<>();
        try {

            Trabajador result = trabajadorService.findOne(id_trabajador);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** EDITAR DATOS DEL TRABAJADOR  ************************
    @SystemControllerLog(operation = "editarTrabajador", type = "Edito los generales del trabajador") //Log de quien ejecuta el metodo
    @PostMapping(value = "/editarTrabajador/{id_trabajador}")
    public ResponseEntity<OutputEntity<String>> editarPersonaDireccion(@RequestBody TrabajadorDTO persona, @PathVariable Integer id_trabajador) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            trabajadorService.editarTrabajador(persona, id_trabajador);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos del trabajador actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** BUSCAR DATOS DEL TRABAJADOR POR MEDIO DE ID_EXPEDIENTE ************************
    @GetMapping(value = "/buscarTrabajador_IdExpediente/{id_expediente}")
    public ResponseEntity<OutputEntity<Trabajador>> buscarTrabajadorExp(@PathVariable Integer id_expediente) {
        OutputEntity<Trabajador> out = new OutputEntity<>();
        try {

            Trabajador result = trabajadorService.buscarExp(id_expediente);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Buscar ultimo Id registrado en trabajador
    @GetMapping(value = "/ultimoIdTrabajador")
    public ResponseEntity<OutputEntity<Integer>> ultimoIdTrabajador() {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Integer result = trabajadorService.ultimoIdTrabajador();
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** BUSCAR DATOS DEL TRABAJADOR POR NUMERO DE EXPEDIENTE ************************
    @GetMapping(value = "/buscarTrabajador_NumExpediente/{numero_expediente}")
    public ResponseEntity<OutputEntity<Trabajador>> buscarTrabajadorNoExp(@PathVariable String numero_expediente) {
        OutputEntity<Trabajador> out = new OutputEntity<>();
        try {

            Trabajador result = trabajadorService.buscarNumExp(numero_expediente);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************** CUENTA BANCARIA ************************
    @PostMapping(value = "/guardarCuentaBancaria")
    public ResponseEntity<Cuenta_Bancaria> guardarCuentaBancaria(@RequestBody Cuenta_Bancaria cuenta_bancaria) {
        try {
            return new ResponseEntity<>(trabajadorService.saveCuentaBancaria(cuenta_bancaria), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarCuenta/{id_cuenta_bancaria}")
    public ResponseEntity<OutputEntity<Cuenta_Bancaria>> buscarCuentaBancaria(@PathVariable Integer id_cuenta_bancaria) {
        OutputEntity<Cuenta_Bancaria> out = new OutputEntity<>();
        try {
            Cuenta_Bancaria result = trabajadorService.findOneCuenta(id_cuenta_bancaria);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarCuentaidTrabajador/{id_trabajador}")
    public ResponseEntity<OutputEntity<Cuenta_Bancaria>> buscarCuentaidTrabajador(@PathVariable Integer id_trabajador) {
        OutputEntity<Cuenta_Bancaria> out = new OutputEntity<>();
        try {
            Cuenta_Bancaria result = trabajadorService.findCuentaidTrabajador(id_trabajador);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SystemControllerLog(operation = "editarCuentaTrabajador", type = "Editó la cuenta")
    @PostMapping(value = "/editarCuentaTrabajador/{id_trabajador}")
    public ResponseEntity<OutputEntity<String>> editarCuentaTrabajador(@RequestBody Cuenta_Bancaria cuenta_bancaria, @PathVariable Integer id_trabajador) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            trabajadorService.editarCuentaTrabajador(cuenta_bancaria, id_trabajador);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de validación actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** PLAZA DEL TRABAJADOR ************************
    @PostMapping(value = "/guardarTrabajadorPlaza")
    public ResponseEntity<Trabajador_plaza> guardarTrabajadorPlaza(@RequestBody Trabajador_plaza trabajador_plaza) {
        try {
            return new ResponseEntity<>(trabajadorService.saveTrabajadorPlaza(trabajador_plaza), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarPlazaTrabajador/{id_trabajador}")
    public ResponseEntity<OutputEntity<Trabajador_plaza>> buscarPlazaTrabajador(@PathVariable Integer id_trabajador) {
        OutputEntity<Trabajador_plaza> out = new OutputEntity<>();
        try {
            Trabajador_plaza result = trabajadorService.findPlazaTrabajador(id_trabajador);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/obtenPlazaTrabajador/{plaza_id}/{trabajador_id}/{fecha_final}/{nomina}")
    public ResponseEntity<OutputEntity<Void>> obtenPlazaTrabajador(@PathVariable Integer plaza_id, @PathVariable Integer trabajador_id, @PathVariable Date fecha_final, @PathVariable String nomina) {
        OutputEntity<Void> out = new OutputEntity<>();
        try {
            trabajadorService.findByPlazaAndTrabajador(plaza_id, trabajador_id, fecha_final, nomina);
            out.success(Response.OK.getCode(), Response.OK.getKey(), null);
            return new ResponseEntity<>(out, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(out, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @SystemControllerLog(operation = "editarPlazaTrabajador", type = "Editó la plaza")
    @PostMapping(value = "/editarPlazaTrabajador/{id_trabajador}")
    public ResponseEntity<OutputEntity<String>> editarPlazaTrabajador(@RequestBody Trabajador_plaza trabajador_plaza, @PathVariable Integer id_trabajador) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            trabajadorService.editarPlazaTrabajador(trabajador_plaza, id_trabajador);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de plaza actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping(value = "/buscarTrabajadorAreas/{areas_id}")
    public ResponseEntity<Trabajador_plaza> buscarTrabajadorAreas(@PathVariable Integer areas_id) {
        try {
            List<Trabajador_plaza> result = trabajadorService.findTrabajadorArea(areas_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarTrabajadorNominaNivel26/{tipo_nomina_id}")
    public ResponseEntity<Trabajador_plaza> buscarTrabajadorNominaNivel26(@PathVariable Integer tipo_nomina_id) {
        try {
            List<Trabajador_plaza> result = trabajadorService.findTrabajadoresByNominaAndNivel26(tipo_nomina_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarTrabajadorNominaNivel27/{tipo_nomina_id}")
    public ResponseEntity<Trabajador_plaza> buscarTrabajadorNominaNivel27(@PathVariable Integer tipo_nomina_id) {
        try {
            List<Trabajador_plaza> result = trabajadorService.findTrabajadoresByNominaAndNivel27(tipo_nomina_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarTrabajadorFonacot/{num_imss}")
    public ResponseEntity<OutputEntity<Trabajador_FonacotDTO>> buscarTrabajadorFonacot(@PathVariable String num_imss) {
        OutputEntity<Trabajador_FonacotDTO> out = new OutputEntity<>();
        try {
            Trabajador_FonacotDTO result = trabajadorService.findTrabajadorFonacot(num_imss);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarCandidatosCreditoPersonal")
    public ResponseEntity<List<Trabajador_Prestamo_PersonalDTO>> listarCandidatosCreditoPersonal() {
        try {
            List<Trabajador_Prestamo_PersonalDTO> trabajadores = trabajadorService.listadoCreditoPersonal();
            return new ResponseEntity<>(trabajadores, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarTrabajadoresSeparacion")
    public ResponseEntity<List<Trabajador_SeparacionDTO>> listarTrabajadoresSeparacion() {
        try {
            List<Trabajador_SeparacionDTO> trabajadores = trabajadorService.listadoSeparacion();
            return new ResponseEntity<>(trabajadores, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscarNominaPorExpediente/{expediente}")
    public ResponseEntity<Integer> getNominaByExpediente(@PathVariable String expediente) {
        Integer tipoNomina = trabajadorService.findNominaTrabajadorByExpediente(expediente);

        if (tipoNomina != null) {
            return ResponseEntity.ok(tipoNomina);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/buscarNombreNominaPorExpediente/{expediente}")
    public ResponseEntity<String> getNombreNominaByExpediente(@PathVariable String expediente) {
        String tipoNomina = trabajadorService.findNombreNominaTrabajadorByExpediente(expediente);

        if (tipoNomina != null) {
            return ResponseEntity.ok(tipoNomina);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    //******************** VALIDACIÓN DEL TRABAJADOR ************************
    @PostMapping(value = "/guardarValidacion")
    public ResponseEntity<Validaciones> guardarValidacion(@RequestBody Validaciones validacion) {
        try {
            return new ResponseEntity<>(trabajadorService.saveValidaciones(validacion), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarValidacion/{id_validaciones}")
    public ResponseEntity<OutputEntity<Validaciones>> buscarValidacion(@PathVariable Integer id_validaciones) {
        OutputEntity<Validaciones> out = new OutputEntity<>();
        try {
            Validaciones result = trabajadorService.findOneValidacion(id_validaciones);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarValidacionTrabajador/{id_trabajador}")
    public ResponseEntity<OutputEntity<Validaciones>> buscarValidacionTrabajador(@PathVariable Integer id_trabajador) {
        OutputEntity<Validaciones> out = new OutputEntity<>();
        try {
            Validaciones result = trabajadorService.findValidacionTrabajador(id_trabajador);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SystemControllerLog(operation = "editarValidacion", type = "Editó la validación")
    @PostMapping(value = "/editarValidacion/{id_trabajador}")
    public ResponseEntity<OutputEntity<String>> editarValidacion(@RequestBody Validaciones validacion, @PathVariable Integer id_trabajador) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            trabajadorService.editarValidacion(validacion, id_trabajador);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de validación actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************** PERIODOS DE PAGO ************************
    @GetMapping(value = "/buscaPeriodoPago/{id_nomina}/{fecha_inicial}")
    public ResponseEntity<OutputEntity<Integer>> buscaPeriodoPago(@RequestBody @PathVariable("id_nomina") Integer id_nomina, @PathVariable("fecha_inicial") String fecha_inicial) throws ParseException {
        OutputEntity<Integer> out = new OutputEntity<>();
        Date fechaInicial = new SimpleDateFormat("yyyy-MM-dd").parse(fecha_inicial);
        try {
            Integer result = trabajadorService.findPeriodoPago(id_nomina, fechaInicial);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/findPeriodoFechaCorteAndTipoNomina/{id_nomina}/{fecha_corte}")
    public ResponseEntity<List<Periodos_Pago>> findPeriodoFechaCorteAndTipoNomina(@RequestBody @PathVariable("id_nomina") Integer id_nomina, @PathVariable("fecha_corte") String fecha_inicial) throws ParseException {
        Date fechaInicial = new SimpleDateFormat("yyyy-MM-dd").parse(fecha_inicial);
        try {
            List<Periodos_Pago> periodo = trabajadorService.findPeriodoFechaCorteAndTipoNomina(id_nomina, fechaInicial);
            return new ResponseEntity<>(periodo, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscaPeriodosFechas/{id_nomina}")
    public ResponseEntity<List<Periodos_Pago>> buscaPeriodosFechas(@PathVariable("id_nomina") Integer id_nomina) {
        try {
            List<Periodos_Pago> periodo = trabajadorService.findPeriodosFechas(id_nomina);
            return new ResponseEntity<>(periodo, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarTodosLosPeriodosPorNomina/{id_nomina}")
    public ResponseEntity<List<Periodos_Pago>> buscarTodosLosPeriodosPorNomina(@PathVariable("id_nomina") Integer id_nomina) {
        try {
            List<Periodos_Pago> periodo = trabajadorService.findAllPeriodosPagoByNomina(id_nomina);
            return new ResponseEntity<>(periodo, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarPeriodoPagoId/{id_periodo_pago}")
    public ResponseEntity<OutputEntity<Periodos_Pago>> buscarPeriodoPagoId(@PathVariable Integer id_periodo_pago) {
        OutputEntity<Periodos_Pago> out = new OutputEntity<>();
        try {
            Periodos_Pago result = trabajadorService.findOnePeriodo(id_periodo_pago);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarPeriodoPago/{periodoPago}/{id_nomina}")
    public ResponseEntity<OutputEntity<Periodos_Pago>> buscarPeriodoPago(@PathVariable Integer periodoPago, @PathVariable Integer id_nomina) {
        OutputEntity<Periodos_Pago> out = new OutputEntity<>();
        try {
            Periodos_Pago result = trabajadorService.findOnePeriodoPago(periodoPago, id_nomina);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscaLapsoPeriodos/{idNomina}/{fechaInicial}/{fechaFinal}")
    public ResponseEntity<OutputEntity<List<Integer>>> findLapsoPeriodos(
            @PathVariable("idNomina") Integer idNomina,
            @PathVariable("fechaInicial") String fechaInicial,
            @PathVariable("fechaFinal") String fechaFinal
    ) throws ParseException {
        OutputEntity<List<Integer>> out = new OutputEntity<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicialFormateada = dateFormat.parse(fechaInicial);
        Date fechaFinalFormateada = dateFormat.parse(fechaFinal);

        try {
            List<Integer> result = trabajadorService.findLapsoPeriodos(idNomina, fechaInicialFormateada, fechaFinalFormateada);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/obtenerFechasSemanalesConfianza")
    public ResponseEntity<List<Semanas_Periodos_PagoDTO>> fechasSemanalesConfianza() {
        try {
            List<Semanas_Periodos_PagoDTO> semanas = trabajadorService.obtenerFechasSemanalesConfianza();
            return new ResponseEntity<>(semanas, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/obtenerFechasSemanalesVarios")
    public ResponseEntity<List<Semanas_Periodos_PagoDTO>> fechasSemanalesVarios() {
        try {
            List<Semanas_Periodos_PagoDTO> semanas = trabajadorService.obtenerFechasSemanalesVarios();
            return new ResponseEntity<>(semanas, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/obtenerFechasSemanalesTransportacion")
    public ResponseEntity<List<Semanas_Periodos_PagoDTO>> fechasSemanalesTransportacion() {
        try {
            List<Semanas_Periodos_PagoDTO> semanas = trabajadorService.obtenerFechasSemanalesTransportacion();
            return new ResponseEntity<>(semanas, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarPeriodosGeneracionCercanos/{id_nomina}")
    public ResponseEntity<List<Periodos_Pago>> buscarPeriodosGeneracionCercanos(@PathVariable("id_nomina") Integer id_nomina) {
        try {
            List<Periodos_Pago> periodo = trabajadorService.obtenerPeriodosGeneracionCercanos(id_nomina);
            return new ResponseEntity<>(periodo, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ******************** LISTAR TRABAJADORES CON EXPEDIENTE *****************************
    @GetMapping(value = "/listadoTrabajadoresPlaza")
    public ResponseEntity<List<Trabajador>> listarTrabajadoresPlaza() {
        try {
            List<Trabajador> trabajador = trabajadorService.personasConPlaza();
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ******************** LISTAR TRABAJADORES CON TODOS LOS FILTROS *****************************
    @GetMapping(value = "/filtradoGeneralTrabajadoresKardex/{tipo_nomina}/{incidencia_id}/{fecha_inicio_periodo_pago}/{fecha_fin_periodo_pago}")
    public ResponseEntity<List<Trabajador>> filtradoTotalDeIncidenciasKardex(@PathVariable Integer tipo_nomina,
            @PathVariable Integer incidencia_id,
            @PathVariable String fecha_inicio_periodo_pago,
            @PathVariable String fecha_fin_periodo_pago) throws ParseException {
        Date fecha_inicio = null;
        Date fecha_fin = null;
        if (!fecha_inicio_periodo_pago.equalsIgnoreCase("null")) {
            fecha_inicio = new SimpleDateFormat("yyyy-MM-dd").parse(fecha_inicio_periodo_pago);
        }
        if (!fecha_fin_periodo_pago.equalsIgnoreCase("null")) {
            fecha_fin = new SimpleDateFormat("yyyy-MM-dd").parse(fecha_fin_periodo_pago);
        }
        try {
            List<Trabajador> trabajador = trabajadorService.filtradoTotalDeIncidenciasKardex(tipo_nomina, incidencia_id, fecha_inicio, fecha_fin);
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ******************** LISTAR TRABAJADORES CON FILTRO INCIDENCIA *****************************
    @GetMapping(value = "/filtradoIncidenciasTrabajadoresKardex/{tipo_nomina}/{incidencia_id}")
    public ResponseEntity<List<Trabajador>> filtradoSoloDeIncidenciasKardex(@PathVariable Integer tipo_nomina,
            @PathVariable Integer incidencia_id) {
        try {
            List<Trabajador> trabajador = trabajadorService.filtradoSoloDeIncidenciasKardex(tipo_nomina, incidencia_id);
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ******************** LISTAR TRABAJADORES CON FILTRO PERIODOS *****************************
    @GetMapping(value = "/filtradoPeriodosTrabajadoresIncidenciasKardex/{tipo_nomina}/{fecha_inicio_periodo_pago}/{fecha_fin_periodo_pago}")
    public ResponseEntity<List<Trabajador>> filtradoPeriodosDeIncidenciasKardex(@PathVariable Integer tipo_nomina,
            @PathVariable String fecha_inicio_periodo_pago,
            @PathVariable String fecha_fin_periodo_pago) throws ParseException {
        Date fecha_inicio = null;
        Date fecha_fin = null;
        if (!fecha_inicio_periodo_pago.equalsIgnoreCase("null")) {
            fecha_inicio = new SimpleDateFormat("yyyy-MM-dd").parse(fecha_inicio_periodo_pago);
        }
        if (!fecha_fin_periodo_pago.equalsIgnoreCase("null")) {
            fecha_fin = new SimpleDateFormat("yyyy-MM-dd").parse(fecha_fin_periodo_pago);
        }
        try {
            List<Trabajador> trabajador = trabajadorService.filtradoPeriodosDeIncidenciasKardex(tipo_nomina, fecha_inicio, fecha_fin);
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ******************** LISTAR TRABAJADORES CON EXPEDIENTE Y PLAZA ESPECÍFICO*****************************
    @GetMapping(value = "/listadoTrabajadoresPlazaTipoNomina/{tipo_nomina}")
    public ResponseEntity<List<Trabajador>> personasConPlazaTipoNominaEspecifica(@PathVariable Integer tipo_nomina) {
        try {
            List<Trabajador> trabajador = trabajadorService.personasConPlazaTipoNominaEspecifica(tipo_nomina);
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ******************** BUSCAR TRABAJADOR EN EL HISTORICO DE SU PLAZA *****************************
    @GetMapping(value = "/buscarHistoricoPlazaTrabajador/{id_trabajador}")
    public ResponseEntity<OutputEntity<Historico_Trabajador_Plaza>> buscarHistoricoPlazaTrabajador(@PathVariable Integer id_trabajador) {
        OutputEntity<Historico_Trabajador_Plaza> out = new OutputEntity<>();
        try {
            Historico_Trabajador_Plaza result = trabajadorService.findHistoricoPlazaTrabajador(id_trabajador);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/esOperativo/{trabajador_id}")
    public ResponseEntity<Boolean> esOperativoOAdministrativo(@PathVariable Integer trabajador_id) {
        Boolean tipoNomina = trabajadorService.esOperativoOAdministrativo(trabajador_id);
        return ResponseEntity.ok(tipoNomina);
    }

    @GetMapping(value = "/bucarTrabajadoresPorNominayArea/{id_area}/{id_nomina}")
    public ResponseEntity<List<Listado_RA_10DTO>> findByNominaAndArea(@PathVariable Integer id_area,
            @PathVariable Integer id_nomina) {
        try {
            List<Listado_RA_10DTO> trabajador = trabajadorService.findByNominaAndArea(id_area, id_nomina);
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarTrabajadoresNominaOpcional")
    public ResponseEntity<List<Trabajador>> trabajadoresConExpedienteFiltroNominaOpcional(@RequestParam(required = false) Integer tipo_nomina_id) {
        try {
            List<Trabajador> trabajadores = trabajadorService.trabajadoresConExpedienteFiltroNominaOpcional(tipo_nomina_id);
            return new ResponseEntity<>(trabajadores, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/obtenPlazaTrabajadorHistorico/{plaza_id}/{trabajador_id}/{fecha_inicial}/{nomina}")
    public ResponseEntity<OutputEntity<Double>> obtenPlazaTrabajadorHistorico(@PathVariable Integer plaza_id, @PathVariable Integer trabajador_id, @PathVariable Date fecha_inicial, @PathVariable String nomina) {
        OutputEntity<Double> out = new OutputEntity<>();
        try {
            trabajadorService.findByPlazaAndTrabajadorHistorico(plaza_id, trabajador_id, fecha_inicial, nomina);
            out.success(Response.OK.getCode(), Response.OK.getKey(), null);
            return new ResponseEntity<>(out, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(out, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/obtenPlazaTrabajadoPorId/{trabajador_id}/{fecha_inicial}/{fecha_final}/{nomina}/{periodo}")
    public ResponseEntity<OutputEntity<Void>> obtenPlazaTrabajadoPorId(@PathVariable Integer trabajador_id, @PathVariable Date fecha_inicial, @PathVariable Date fecha_final, @PathVariable String nomina, @PathVariable String periodo) {
        OutputEntity<Void> out = new OutputEntity<>();
        try {
            trabajadorService.findPlazaTrabajadorByID(trabajador_id, fecha_inicial, fecha_final, nomina, periodo);
            out.success(Response.OK.getCode(), Response.OK.getKey(), null);
            return new ResponseEntity<>(out, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(out, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // ******************************** GRABAR DIAS DE CONTRATO (243) CONFIANZA EN TMP MOVIMIENTOS********************
    @PostMapping("/guardarMovimientos243Confianza")
    public ResponseEntity<Tmp_Movimientos> saveMovimientos243Confianza(@RequestBody Tmp_Movimientos movimientos) {
        try {
            return new ResponseEntity<>(trabajadorService.saveMovimientos243Confianza(movimientos), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ******************************** GRABAR DIAS DE INASISTENCIAS (244) CONFIANZA EN TMP MOVIMIENTOS********************
    @PostMapping("/guardarMovimientos244Confianza")
    public ResponseEntity<Tmp_Movimientos> saveMovimientos244Confianza(@RequestBody Tmp_Movimientos movimientos) {
        try {
            return new ResponseEntity<>(trabajadorService.saveMovimientos244Confianza(movimientos), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ******************************** GRABAR IMPORTE DE AGUINALDO (12) CONFIANZA EN TMP MOVIMIENTOS********************
    @PostMapping("/guardarMovimientos12Confianza")
    public ResponseEntity<Tmp_Movimientos> saveMovimientos12Confianza(@RequestBody Tmp_Movimientos movimientos) {
        try {
            return new ResponseEntity<>(trabajadorService.saveMovimientos12Confianza(movimientos), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ******************************** GRABAR IMPORTE AGUINALDO ANTERIOR (330) EN TMP MOVIMIENTOS********************
    @PostMapping("/guardarMovimientos330Confianza")
    public ResponseEntity<Void> saveMovimientos330Confianza(@RequestBody Tmp_Movimientos movimientos) {
        try {
            trabajadorService.saveMovimientos330Confianza(movimientos);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ******************************** GRABAR IMPORTE AGUINALDO ANTERIOR (339) EN TMP MOVIMIENTOS********************
    @PostMapping("/guardarMovimientos339Confianza")
    public ResponseEntity<Void> saveMovimientos339Confianza(@RequestBody Tmp_Movimientos movimientos) {
        try {
            trabajadorService.saveMovimientos339Confianza(movimientos);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
