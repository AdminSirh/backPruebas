/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.entity.Fechas_Corte_Prestamos;
import com.sirh.sirh.entity.Fonacot_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Periodos_Pago;
import com.sirh.sirh.entity.Personal_Actualizacion_Cuentas;
import com.sirh.sirh.entity.Personal_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Personal_Registro_Pagos_Deudas;
import com.sirh.sirh.service.CreditoPersonalService;
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
@RequestMapping("creditoPersonal")
public class CreditoPersonalController {
    @Autowired
    CreditoPersonalService creditoPersonalService;
    
    @Autowired
    TrabajadorServiceImpl trabajadorServiceImpl;
    
    // **********PERSONAL CUENTAS POR COBRAR *******************
    @PostMapping(value = "/guardarDescuentoPersonal")
    public ResponseEntity<OutputEntity<String>> guardarDescuentoPersonal(@RequestBody Personal_Cuentas_Por_Cobrar cuenta) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            creditoPersonalService.saveCuenta(cuenta);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Los datos del descuento han sido guardado con Exito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    
     //Busca por id y Lista a todos los datos del Trabajador en Cuentas por Cobrar 
    @GetMapping(value = "/listarCuentaPorTrabajadorPersonal/{id_trabajador}")
    public ResponseEntity<Personal_Cuentas_Por_Cobrar> listarCuentaPorTrabajadorPersonal(@RequestBody @PathVariable("id_trabajador") Integer id_trabajador) {
        try {
            List<Personal_Cuentas_Por_Cobrar> result = creditoPersonalService.findOneCuentaPersonal(id_trabajador);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //Eliminar Cuenta Personal(Cambiar estatus = 0)
    @PostMapping("/estatusPersonalCuentaPorCobrar/{trabajador_id}")
    public ResponseEntity<List<Personal_Cuentas_Por_Cobrar>> estatusCuentaPersonal(@PathVariable Integer trabajador_id) {
        try {
            List<Personal_Cuentas_Por_Cobrar> cuenta = creditoPersonalService.estatusCuentaPersonal(trabajador_id);
            if (cuenta.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/estatusDepositoPrestamos/{nomina_id}/{periodo_pago}")
    public ResponseEntity<Personal_Cuentas_Por_Cobrar> estatusDepositoDiferentesBancos(@RequestBody @PathVariable("nomina_id") Integer nomina_id, @PathVariable("periodo_pago") Integer periodo_pago) {
        try {
            List<Personal_Cuentas_Por_Cobrar> result = creditoPersonalService.depositosReporte(nomina_id, periodo_pago);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // ********** PERSONAL ACTUALIZACION DE CUENTAS *******************
    @GetMapping(value = "/listarCuentasActivasPersonal")
    public ResponseEntity<Personal_Cuentas_Por_Cobrar> listarCuentasActivasPersonal() {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime hoy = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0, 0);
            List<Integer> result = creditoPersonalService.recuperaCuentasActivasPersonal();
            for (int i = 0; i < result.size(); i++) {
                int id_nomina = result.get(i);
                List<Periodos_Pago> pp = trabajadorServiceImpl.findPeriodosFechas(id_nomina);
                for (int j = 0; j < pp.size(); j++) {
                    Periodos_Pago periodos = pp.get(j);
                    if (hoy.equals(periodos.getFecha_corte())) {
                        List<Personal_Cuentas_Por_Cobrar> result_filtrado = creditoPersonalService.recuperaCuentasNominaPersonal(id_nomina);
                        creditoPersonalService.saveActualizacionCuentasPersonal(result_filtrado,periodos.getPeriodo());
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
    
    @GetMapping(value = "/listarCuentasPersonalesActuales/{id_trabajador}/{personal_cuentas_por_cobrar_id}")
    public ResponseEntity<Personal_Actualizacion_Cuentas> listarCuentasPersonalesActuales(@RequestBody @PathVariable("id_trabajador") Integer id_trabajador, @PathVariable("personal_cuentas_por_cobrar_id") Integer personal_cuentas_por_cobrar_id) {
        try {
            List<Personal_Actualizacion_Cuentas> result = creditoPersonalService.findOneCuentaPersonalActual(id_trabajador, personal_cuentas_por_cobrar_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // ********** PERSONAL REGISTRO PAGO DEUDAS *******************
    @PostMapping(value = "/guardarRegistroPagosDeudasPersonal")
    public ResponseEntity<OutputEntity<String>> guardarRegistroPagosDeudasPersonal(@RequestBody Personal_Registro_Pagos_Deudas registro) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            creditoPersonalService.saveRegistroPagosDeudasPersonal(registro);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Los datos del registro han sido guardados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            System.out.println(e);
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    //Saldar Cuenta (Cambiar estatus = 4)
    @PostMapping("/estatusCuentaSaldada/{id_cuenta}")
    public ResponseEntity<Personal_Cuentas_Por_Cobrar> estatusCuentaSaldada(@PathVariable Integer id_cuenta) {
        try {
            Personal_Cuentas_Por_Cobrar cuenta = creditoPersonalService.estatusCuentaPersonalSaldada(id_cuenta);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/contadorDescuentos/{personal_cuentas_por_cobrar_id}")
    public ResponseEntity<Integer> contadorDescuentos(@PathVariable Integer personal_cuentas_por_cobrar_id) {
        try {
            Integer cuenta = creditoPersonalService.contadorDescuentos(personal_cuentas_por_cobrar_id);
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
            List<Fechas_Corte_Prestamos> result = creditoPersonalService.findAllFechasCorte();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
