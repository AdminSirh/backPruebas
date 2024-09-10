/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.entity.Auxilio_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Cupones_Actualizacion_Cuentas;
import com.sirh.sirh.entity.Cupones_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Cupones_Registro_Pagos_Deudas;
import com.sirh.sirh.entity.Fechas_Corte_Descuentos;
import com.sirh.sirh.entity.Periodos_Pago;
import com.sirh.sirh.service.CuponesCargoService;
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
@RequestMapping("cuponesCargo")
public class CuponesCargoController {
    @Autowired
    CuponesCargoService cuponesCargoService;
    
    @Autowired
    TrabajadorServiceImpl trabajadorServiceImpl;
    
    // **********CUPONES CUENTAS POR COBRAR *******************
    @PostMapping(value = "/guardarDescuentoCupones")
    public ResponseEntity<OutputEntity<String>> guardarDescuentoCupones(@RequestBody Cupones_Cuentas_Por_Cobrar cuenta) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            cuponesCargoService.saveCuenta(cuenta);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Los datos del descuento han sido guardado con Exito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    
    //Busca por id y Lista a todos los datos del Trabajador en Cuentas por Cobrar 
    @GetMapping(value = "/listarCuentaPorTrabajador/{id_trabajador}")
    public ResponseEntity<Cupones_Cuentas_Por_Cobrar> listarCuentaPorTrabajador(@RequestBody @PathVariable("id_trabajador") Integer id_trabajador) {
        try {
            List<Cupones_Cuentas_Por_Cobrar> result = cuponesCargoService.findOneCuentaCupones(id_trabajador);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //Eliminar Cuenta (Cambiar estatus = 0)
    @PostMapping("/estatusCuentaPorCobrar/{trabajador_id}")
    public ResponseEntity<Cupones_Cuentas_Por_Cobrar> estatusCuenta(@PathVariable Integer trabajador_id) {
        try {
            Cupones_Cuentas_Por_Cobrar cuenta = cuponesCargoService.estatusCuenta(trabajador_id);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // ************************ REGISTRO PAGO DEUDAS **********************************
    @PostMapping(value = "/guardarRegistroPagosDeudas")
    public ResponseEntity<OutputEntity<String>> guardarRegistroPagoDeuda(@RequestBody Cupones_Registro_Pagos_Deudas registro) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            cuponesCargoService.saveRegistroPagosDeudas(registro);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Los datos del registro han sido guardados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println(e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    
    //Saldar Cuenta (Cambiar estatus = 4)
    @PostMapping("/estatusCuentaSaldada/{id_cuenta}")
    public ResponseEntity<Cupones_Cuentas_Por_Cobrar> estatusCuentaSaldada(@PathVariable Integer id_cuenta) {
        try {
            Cupones_Cuentas_Por_Cobrar cuenta = cuponesCargoService.estatusCuentaSaldada(id_cuenta);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // ***************************** CUPONES ACTUALIZACION DE CUENTAS *************************
    
    @GetMapping(value = "/listarCuentasCuponesActuales/{id_trabajador}/{cupones_cuentas_cobrar_id}")
    public ResponseEntity<Cupones_Actualizacion_Cuentas> listarCuentasCuponesActuales(@RequestBody @PathVariable("id_trabajador") Integer id_trabajador, @PathVariable("cupones_cuentas_cobrar_id") Integer cupones_cuentas_cobrar_id) {
        try {
            List<Cupones_Actualizacion_Cuentas> result = cuponesCargoService.findOneCuentaCuponesActual(id_trabajador, cupones_cuentas_cobrar_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarCuentasActivasCupones")
    public ResponseEntity<Cupones_Cuentas_Por_Cobrar> listarCuentasActivasCupones() {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime hoy = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0, 0);
            List<Integer> result = cuponesCargoService.recuperaCuentasActivasAuxilio();
            for (int i = 0; i < result.size(); i++) {
                int id_nomina = result.get(i);
                List<Periodos_Pago> pp = trabajadorServiceImpl.findPeriodosFechas(id_nomina);
                for (int j = 0; j < pp.size(); j++) {
                    Periodos_Pago periodos = pp.get(j);
                    System.out.println(periodos.getFecha_corte() + " periodos.getFecha_corte()");
                    if (hoy.equals(periodos.getFecha_corte())) {
                        List<Cupones_Cuentas_Por_Cobrar> result_filtrado = cuponesCargoService.recuperaCuentasNominaCupones(id_nomina);
                        cuponesCargoService.saveActualizacionCuentasCupones(result_filtrado,periodos.getPeriodo());
                        
                    }
                }
            }
            
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //****************FECHAS DE CORTE O DESCUENTOS***************************************
    //Listar todos los datos
    @GetMapping(value = "/listarFechasCorteDescuentos")
    public ResponseEntity<Fechas_Corte_Descuentos> listarFechasCortePrestamos() {
        try {
            List<Fechas_Corte_Descuentos> result = cuponesCargoService.findAllFechasCorte();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
}
