/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.entity.Fechas_Corte_Descuentos;
import com.sirh.sirh.entity.Periodos_Pago;
import com.sirh.sirh.entity.Vivienda_Actualizacion_Cuentas;
import com.sirh.sirh.entity.Vivienda_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Vivienda_Registro_Pagos_Deudas;
import com.sirh.sirh.service.AbonoViviendaService;
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
@RequestMapping("abonoVivienda")
public class AbonoViviendaController {
    @Autowired
    AbonoViviendaService abonoViviendaService;
    
    @Autowired
    TrabajadorServiceImpl trabajadorServiceImpl;
    
    // **********VIVIENDA CUENTAS POR COBRAR *******************
    @PostMapping(value = "/guardarDescuentoVivienda")
    public ResponseEntity<OutputEntity<String>> guardarDescuentoVivienda(@RequestBody Vivienda_Cuentas_Por_Cobrar cuenta) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            abonoViviendaService.saveCuenta(cuenta);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Los datos del descuento han sido guardado con Exito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    
    //Busca por id y Lista a todos los datos del Trabajador en Cuentas por Cobrar 
    @GetMapping(value = "/listarCuentaPorTrabajador/{id_trabajador}")
    public ResponseEntity<Vivienda_Cuentas_Por_Cobrar> listarCuentaPorTrabajador(@RequestBody @PathVariable("id_trabajador") Integer id_trabajador) {
        try {
            List<Vivienda_Cuentas_Por_Cobrar> result = abonoViviendaService.findOneCuentaVivienda(id_trabajador);
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
    public ResponseEntity<Vivienda_Cuentas_Por_Cobrar> estatusCuenta(@PathVariable Integer trabajador_id) {
        try {
            Vivienda_Cuentas_Por_Cobrar cuenta = abonoViviendaService.estatusCuenta(trabajador_id);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // ********** REGISTRO PAGO DEUDAS *******************
    @PostMapping(value = "/guardarRegistroPagosDeudas")
    public ResponseEntity<OutputEntity<String>> guardarRegistroPagoDeuda(@RequestBody Vivienda_Registro_Pagos_Deudas registro) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            abonoViviendaService.saveRegistroPagosDeudas(registro);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Los datos del registro han sido guardados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    
    //Saldar Cuenta (Cambiar estatus = 4)
    @PostMapping("/estatusCuentaSaldada/{id_cuenta}")
    public ResponseEntity<Vivienda_Cuentas_Por_Cobrar> estatusCuentaSaldada(@PathVariable Integer id_cuenta) {
        try {
            Vivienda_Cuentas_Por_Cobrar cuenta = abonoViviendaService.estatusCuentaSaldada(id_cuenta);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // ********** VIVIENDA ACTUALIZACION DE CUENTAS *******************
    @GetMapping(value = "/listarCuentasViviendaActuales/{id_trabajador}/{vivienda_cuentas_cobrar_id}")
    public ResponseEntity<Vivienda_Actualizacion_Cuentas> listarCuentasViviendaActuales(@RequestBody @PathVariable("id_trabajador") Integer id_trabajador, @PathVariable("vivienda_cuentas_cobrar_id") Integer vivienda_cuentas_cobrar_id) {
        try {
            List<Vivienda_Actualizacion_Cuentas> result = abonoViviendaService.findOneCuentaViviendaActual(id_trabajador, vivienda_cuentas_cobrar_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarCuentasActivasVivienda")
    public ResponseEntity<Vivienda_Cuentas_Por_Cobrar> listarCuentasActivasVivienda() {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime hoy = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0, 0);
            List<Integer> result = abonoViviendaService.recuperaCuentasActivasVivienda();
            for (int i = 0; i < result.size(); i++) {
                int id_nomina = result.get(i);
                List<Periodos_Pago> pp = trabajadorServiceImpl.findPeriodosFechas(id_nomina);
                for (int j = 0; j < pp.size(); j++) {
                    Periodos_Pago periodos = pp.get(j);
                    if (hoy.equals(periodos.getFecha_corte())) {
                        List<Vivienda_Cuentas_Por_Cobrar> result_filtrado = abonoViviendaService.recuperaCuentasNominaVivienda(id_nomina);
                        abonoViviendaService.saveActualizacionCuentasVivienda(result_filtrado,periodos.getPeriodo());
                        
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
            List<Fechas_Corte_Descuentos> result = abonoViviendaService.findAllFechasCorte();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
