package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.Periodos_PagoDTO;
import com.sirh.sirh.entity.Ciclos_Conceptos;
import com.sirh.sirh.entity.Periodos_Pago;
import com.sirh.sirh.service.PeriodosPagoService;
import com.sirh.sirh.util.Response;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author oguerrero23
 */
@RestController
@RequestMapping("periodosPago")
public class PeriodosPagoController {

    @Autowired
    PeriodosPagoService periodosPagoService;

    // *************************************** PERIODOS DE PAGO ***************************************
    @PostMapping(value = "/guardaPeriodos")
    public ResponseEntity<OutputEntity< List<Periodos_Pago>>> guardarPeriodos(@RequestBody List<Periodos_PagoDTO> periodos) {
        OutputEntity<List<Periodos_Pago>> out = new OutputEntity<>();
        try {
            System.out.println(periodos);
            List<Periodos_Pago> iterable = periodosPagoService.saveAllPeriodos(periodos);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), iterable);
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping("/cargaSemanasCorteTiempoExtra")
    public ResponseEntity<String> saveSemanasCorteTiempoExtra(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Por favor, seleccione un archivo para cargar.", HttpStatus.BAD_REQUEST);
        }

        try {
            periodosPagoService.saveSemanasCorteFromExcel(file);
            return new ResponseEntity<>("Archivo cargado y datos guardados correctamente.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al procesar el archivo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarPeriodosCargados")
    public ResponseEntity<String> listarPeriodosCargados() {
        try {
            List<String> result = periodosPagoService.findNominasCargadas();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarAllPeriodos/{nomina_id}")
    public ResponseEntity<Periodos_Pago> listarAllPeriodos(@RequestBody @PathVariable("nomina_id") Integer nomina_id) {
        try {
            List<Periodos_Pago> result = periodosPagoService.listAllPeriodos(nomina_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // *************************************** CICLOS CONCEPTOS *************************************
    @PostMapping(value = "/guardarCiclo")
    public ResponseEntity<OutputEntity<Ciclos_Conceptos>> saveCiclo(@RequestBody Ciclos_Conceptos ciclos) {
        OutputEntity<Ciclos_Conceptos> out = new OutputEntity<>();
        try {
            periodosPagoService.saveCiclos(ciclos);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), null);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping(value = "/listarCiclos")
    public ResponseEntity<Ciclos_Conceptos> listarCiclos() {
        try {
            List<Ciclos_Conceptos> result = periodosPagoService.findAllCiclos();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarCiclo/{id_ciclo}")
    public ResponseEntity<OutputEntity<String>> editarCiclo(@RequestBody Ciclos_Conceptos ciclo, @PathVariable Integer id_ciclo) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            periodosPagoService.updateCiclos(ciclo, id_ciclo);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping(value = "/buscarCiclo/{id_ciclo}")
    public ResponseEntity<OutputEntity<Ciclos_Conceptos>> buscarCiclo(@PathVariable Integer id_ciclo) {
        OutputEntity<Ciclos_Conceptos> out = new OutputEntity<>();
        try {
            Ciclos_Conceptos result = periodosPagoService.findOneCiclo(id_ciclo);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/importarCiclos")
    public ResponseEntity<Void> importarCiclos(@RequestParam("archivo") MultipartFile archivoExcel) throws ParseException {
        try {
            periodosPagoService.importarCiclosExcel(archivoExcel);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //ELIMINAR NOMINAS CARGADAS EN CUENTAS POR COBRAR POR ID NOMINA
    @PostMapping(value = "/eliminarCiclos")
    public ResponseEntity<Void> eliminarCiclos() {
        try {
            periodosPagoService.deleteCiclos();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //**************PERIODOS DE AGUINALDO **********************
    @GetMapping(value = "/listarPeriodosAguinaldo/{nomina_id}")
    public ResponseEntity<Periodos_Pago> listarPeriodosAguinaldo(@RequestBody @PathVariable("nomina_id") Integer nomina_id) {
        try {
            List<Periodos_Pago> result = periodosPagoService.findPeriodosAguinaldo(nomina_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/recordatorioPeriodosSinPagar")
    public ResponseEntity<Object[]> findPeriodosProximosSinPagar() {
        try {
            List<Object[]> result = periodosPagoService.findPeriodosProximosSinPagar();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
