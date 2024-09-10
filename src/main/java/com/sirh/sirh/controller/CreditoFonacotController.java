/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.Registro_Solicitudes_FonacotDTO;
import com.sirh.sirh.entity.Fechas_Corte_Prestamos;
import com.sirh.sirh.entity.Fonacot_Actualizacion_Cuentas;
import com.sirh.sirh.entity.Fonacot_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Periodos_Pago;
import com.sirh.sirh.entity.Fonacot_Registro_Pagos_Deudas;
import com.sirh.sirh.entity.Registro_Solicitudes_Fonacot;
import com.sirh.sirh.service.CreditoFonacotService;
import com.sirh.sirh.serviceImpl.TrabajadorServiceImpl;
import com.sirh.sirh.util.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author oguerrero23
 */
@RestController
@RequestMapping("creditoFonacot")
public class CreditoFonacotController {
    @Autowired
    CreditoFonacotService creditoFonacotService;
    
    @Autowired
    TrabajadorServiceImpl trabajadorServiceImpl; 
    // ********** CUENTAS POR COBRAR *******************
    //************************** GUARDAR DESCUENTOS ****************************
    @PostMapping(value = "/guardarDescuentoFonacot")
    public ResponseEntity<OutputEntity<String>> guardarDescuentoFonacot(@RequestBody Fonacot_Cuentas_Por_Cobrar cuenta) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            creditoFonacotService.saveCuenta(cuenta);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Los datos del descuento han sido guardado con Exito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    
     //Busca por id y Lista a todos los datos del Trabajador en Cuentas por Cobrar 
    @GetMapping(value = "/listarCuentaPorTrabajador/{id_trabajador}")
    public ResponseEntity<Fonacot_Cuentas_Por_Cobrar> listarCuentaPorTrabajador(@RequestBody @PathVariable("id_trabajador") Integer id_trabajador) {
        try {
            List<Fonacot_Cuentas_Por_Cobrar> result = creditoFonacotService.findOneCuentaFonacot(id_trabajador);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //BUSCAR NOMINAS CARGADAS DESDE EL EXCEL A LA BASE
    @GetMapping(value = "/nominasCargadas")
    public ResponseEntity<OutputEntity<Integer>> nominasCargadas() {
        try {
            List<Integer> result = creditoFonacotService.nominasCargadas();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //ELIMINAR NOMINAS CARGADAS EN CUENTAS POR COBRAR POR ID NOMINA
    @PostMapping(value = "/eliminarDescuentoFonacot/{id_nomina}")
    public ResponseEntity<Void> eliminarDescuentoFonacot(@PathVariable("id_nomina") Integer id_nomina) {
        try {
            creditoFonacotService.deleteMovimientos(id_nomina);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    //Busca id_trabajador y en especifico la cuenta que pertenece a FONACOT
//    @GetMapping(value = "cuentaFonacot/{trabajador_id}")
//    public ResponseEntity<Cuentas_Por_Cobrar> cuentaFonacot(@RequestBody @PathVariable("trabajador_id") Integer trabajador_id) {
//        try {
//            Fonacot_Cuentas_Por_Cobrar result = creditoFonacotService.findCuentaFonacot(trabajador_id);
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
    //Eliminar Cuenta (Cambiar estatus = 0)
    @PostMapping("/estatusCuentaPorCobrar/{trabajador_id}")
    public ResponseEntity<Fonacot_Cuentas_Por_Cobrar> estatusCuenta(@PathVariable Integer trabajador_id) {
        try {
            Fonacot_Cuentas_Por_Cobrar cuenta = creditoFonacotService.estatusCuenta(trabajador_id);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarCuentasFonacot")
    public ResponseEntity<Fonacot_Cuentas_Por_Cobrar> listarCuentasFonacot() {
        try {
            List<Fonacot_Cuentas_Por_Cobrar> result = creditoFonacotService.findAllCuentas();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // ********** SOLICITUDES FONACOT *******************
    @PostMapping(value = "/guardarRegistroFonacot")
    public ResponseEntity<OutputEntity<String>> guardarRegistroFonacot(@RequestBody Registro_Solicitudes_Fonacot registro) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            creditoFonacotService.saveSolicitud(registro);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Los datos del registro han sido realizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    
    @GetMapping(value = "/listarSolicitudesFonacot")
    public ResponseEntity<Registro_Solicitudes_Fonacot> listarSolicitudesFonacot() {
        try {
            List<Registro_Solicitudes_Fonacot> result = creditoFonacotService.findSolicitudes();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarSolicitudPorTrabajador/{id_trabajador}")
    public ResponseEntity<Registro_Solicitudes_Fonacot> listarSolicitudPorTrabajador(@RequestBody @PathVariable("id_trabajador") Integer id_trabajador) {
        try {
            List<Registro_Solicitudes_Fonacot> result = creditoFonacotService.findOneSolicitud(id_trabajador);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarDocumentoTexto")
    public ResponseEntity<Registro_Solicitudes_Fonacot> listarDocumentoTexto() {
        try {
            List<Registro_Solicitudes_FonacotDTO> result = creditoFonacotService.documentoTexto();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // ********** REGISTRO PAGO DEUDAS *******************
    @PostMapping(value = "/guardarRegistroPagosDeudas")
    public ResponseEntity<OutputEntity<String>> guardarRegistroPagoDeuda(@RequestBody Fonacot_Registro_Pagos_Deudas registro) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            creditoFonacotService.saveRegistroPagosDeudas(registro);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Los datos del registro han sido guardados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    
    //Saldar Cuenta (Cambiar estatus = 4)
    @PostMapping("/estatusCuentaSaldada/{id_cuenta}")
    public ResponseEntity<Fonacot_Cuentas_Por_Cobrar> estatusCuentaSaldada(@PathVariable Integer id_cuenta) {
        try {
            Fonacot_Cuentas_Por_Cobrar cuenta = creditoFonacotService.estatusCuentaSaldada(id_cuenta);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // ********** ACTUALIZACION DE CUENTAS *******************
    @GetMapping(value = "/listarCuentasActivas")
    public ResponseEntity<Fonacot_Cuentas_Por_Cobrar> listarCuentasActivas() {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime hoy = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0, 0);
            List<Integer> result = creditoFonacotService.recuperaCuentasActivas();
            for (int i = 0; i < result.size(); i++) {
                int id_nomina = result.get(i);
                List<Periodos_Pago> pp = trabajadorServiceImpl.findPeriodosFechas(id_nomina);
                for (int j = 0; j < pp.size(); j++) {
                    Periodos_Pago periodos = pp.get(j);
                    if (hoy.equals(periodos.getFecha_corte())) {
                        List<Fonacot_Cuentas_Por_Cobrar> result_filtrado = creditoFonacotService.recuperaCuentasNomina(id_nomina);
                        creditoFonacotService.saveActualizacionCuentas(result_filtrado,periodos.getPeriodo());
                    }
                }
            }
            
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarCuentasActuales/{id_trabajador}/{cuenta_por_cobrar_id}")
    public ResponseEntity<Fonacot_Actualizacion_Cuentas> listarCuentasActuales(@RequestBody @PathVariable("id_trabajador") Integer id_trabajador, @PathVariable("cuenta_por_cobrar_id") Integer cuenta_por_cobrar_id) {
        try {
            List<Fonacot_Actualizacion_Cuentas> result = creditoFonacotService.findOneCuentaActual(id_trabajador, cuenta_por_cobrar_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/contadorDescuentosFonacot/{cuenta_por_cobrar_id}")
    public ResponseEntity<Integer> contadorDescuentosFonacot(@PathVariable Integer cuenta_por_cobrar_id) {
        try {
            Integer cuenta = creditoFonacotService.contadorDescuentosFonacot(cuenta_por_cobrar_id);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //****************FECHAS DE CORTE O DESCUENTOS***************************************
    //Listar todos los datos
    @GetMapping(value = "/listarFechasCortePrestamos")
    public ResponseEntity<Fechas_Corte_Prestamos> listarFechasCortePrestamos() {
        try {
            List<Fechas_Corte_Prestamos> result = creditoFonacotService.findAllFechasCorte();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
