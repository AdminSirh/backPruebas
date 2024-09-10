/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;
import com.sirh.sirh.entity.Pagos_3;
import com.sirh.sirh.entity.Pagos_5;
import com.sirh.sirh.entity.Tmp_Movimientos;
import com.sirh.sirh.service.PagosService;
import java.util.Date;
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

/**
 *
 * @author oguerrero23
 */
@RestController
@RequestMapping("pagos")
public class PagosController {
    @Autowired
    PagosService pagosService;
    
    // *************** PAGOS DE NOMINA DE VARIOS *********************************
    
    @GetMapping(value = "/listarPagosVarios/{periodo_aplicacion}")
    public ResponseEntity<Object> listarPagosVarios(@PathVariable Integer periodo_aplicacion) {
        try {
            List<Object> result = pagosService.consultaPagosVarios(periodo_aplicacion);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarCalculosPercepcionDeduccion1/{pago_id}")
    public ResponseEntity<Object> listarCalculosPercepcionDeduccion1(@PathVariable Integer pago_id) {
        try {
            List<Object> result = pagosService.findAllCalculosPercepcionDeduccion1(pago_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarPercepcionDeduccion1/{pago_id}")
    public ResponseEntity<Object> listarPercepcionDeduccion1(@PathVariable Integer pago_id) {
        try {
            List<Object> result = pagosService.findAllPercepcionDeduccion1(pago_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // *************** PAGOS DE NOMINA DE TRANSPORTACION *************************
    
    @GetMapping(value = "/listarPagosTransportacion/{periodo_aplicacion}")
    public ResponseEntity<Object> listarPagosTransportacion(@PathVariable Integer periodo_aplicacion) {
        try {
            List<Object> result = pagosService.consultaPagosTransportacion(periodo_aplicacion);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarCalculosPercepcionDeduccion2/{pago_id}")
    public ResponseEntity<Object> listarCalculosPercepcionDeduccion2(@PathVariable Integer pago_id) {
        try {
            List<Object> result = pagosService.findAllCalculosPercepcionDeduccion2(pago_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarPercepcionDeduccion2/{pago_id}")
    public ResponseEntity<Object> listarPercepcionDeduccion2(@PathVariable Integer pago_id) {
        try {
            List<Object> result = pagosService.findAllPercepcionDeduccion2(pago_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // *************** PAGOS DE NOMINA DE CONFIANZA ******************************
    
    @GetMapping(value = "/listarPagosConfianza/{periodo_aplicacion}")
    public ResponseEntity<Object> listarPagosConfianza(@PathVariable Integer periodo_aplicacion) {
        try {
            List<Object> result = pagosService.consultaPagosConfianza(periodo_aplicacion);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarCalculosPercepcionDeduccion3/{pago_id}")
    public ResponseEntity<Object> listarCalculosPercepcionDeduccion3(@PathVariable Integer pago_id) {
        try {
            List<Object> result = pagosService.findAllCalculosPercepcionDeduccion3(pago_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarPercepcionDeduccion3/{pago_id}")
    public ResponseEntity<Object> listarPercepcionDeduccion3(@PathVariable Integer pago_id) {
        try {
            List<Object> result = pagosService.findAllPercepcionDeduccion3(pago_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarDiferentesPuestosConfianza/{fecha_diciembre}/{fecha_noviembre}/{trabajador_id}")
    public ResponseEntity<Pagos_3> listarDiferentesPuestosConfianza(@PathVariable Date fecha_diciembre, @PathVariable Date fecha_noviembre, @PathVariable Integer trabajador_id) {
        try {
            List<Pagos_3> result = pagosService.obtenPuestosDiferentesConfianza(fecha_diciembre, fecha_noviembre, trabajador_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // *************** PAGOS DE NOMINA DE ESTRUCTURA ******************************
    
    @GetMapping(value = "/listarPagosEstructura/{periodo_aplicacion}")
    public ResponseEntity<Object> listarPagosEstructura(@PathVariable Integer periodo_aplicacion) {
        try {
            List<Object> result = pagosService.consultaPagosEstructura(periodo_aplicacion);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarCalculosPercepcionDeduccion5/{pago_id}")
    public ResponseEntity<Object> listarCalculosPercepcionDeduccion5(@PathVariable Integer pago_id) {
        try {
            List<Object> result = pagosService.findAllCalculosPercepcionDeduccion5(pago_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarPercepcionDeduccion5/{pago_id}")
    public ResponseEntity<Object> listarPercepcionDeduccion5(@PathVariable Integer pago_id) {
        try {
            List<Object> result = pagosService.findAllPercepcionDeduccion5(pago_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarDiferentesPuestosEstructura/{fecha_diciembre}/{fecha_noviembre}/{trabajador_id}")
    public ResponseEntity<Pagos_5> listarDiferentesPuestosEstructura(@PathVariable Date fecha_diciembre, @PathVariable Date fecha_noviembre, @PathVariable Integer trabajador_id) {
        try {
            List<Pagos_5> result = pagosService.obtenPuestosDiferentesEstructura(fecha_diciembre, fecha_noviembre, trabajador_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // *************** PAGOS DE NOMINA DE JUBILADOS ******************************
    
    @GetMapping(value = "/listarPagosJubilados/{periodo_aplicacion}")
    public ResponseEntity<Object> listarPagosJubilados(@PathVariable Integer periodo_aplicacion) {
        try {
            List<Object> result = pagosService.consultaPagosJubilados(periodo_aplicacion);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarCalculosPercepcionDeduccion4/{pago_id}")
    public ResponseEntity<Object> listarCalculosPercepcionDeduccion4(@PathVariable Integer pago_id) {
        try {
            List<Object> result = pagosService.findAllCalculosPercepcionDeduccion4(pago_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarPercepcionDeduccion4/{pago_id}")
    public ResponseEntity<Object> listarPercepcionDeduccion4(@PathVariable Integer pago_id) {
        try {
            List<Object> result = pagosService.findAllPercepcionDeduccion4(pago_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // ******************************** GRABAR INASISTENCIAS (244) EN TMP MOVIMIENTOS********************
    @PostMapping("/guardarMovimientos244")
    public ResponseEntity<Tmp_Movimientos> saveMovimientos244(@RequestBody Tmp_Movimientos movimientos) {
        try {
            return new ResponseEntity<>(pagosService.saveMovimientos244(movimientos), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // ******************************** GRABAR DIAS CONTRATO (243) EN TMP MOVIMIENTOS********************
    @PostMapping("/guardarMovimientos243/{anios}/{fecha_antiguedad}/{fecha_final}")
    public ResponseEntity<Tmp_Movimientos> saveMovimientos243(@RequestBody Tmp_Movimientos movimientos,@PathVariable Integer anios, @PathVariable Date fecha_antiguedad, @PathVariable Date fecha_final) {
        try {
            return new ResponseEntity<>(pagosService.saveMovimientos243(movimientos, anios, fecha_antiguedad, fecha_final), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
