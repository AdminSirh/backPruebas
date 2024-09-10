/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.Pension_AlimenticiaDTO;
import com.sirh.sirh.entity.HistoricoPensionesAlimenticias;
import com.sirh.sirh.entity.Pension_Alimenticia_B;
import com.sirh.sirh.entity.Pension_Alimenticia_Montos;
import com.sirh.sirh.entity.Pension_Alimenticia_OJ;
import com.sirh.sirh.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sirh.sirh.exception.OutputEntity;
import com.sirh.sirh.service.Pension_AlimenticiaService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author rrosero23
 */
@RestController
@RequestMapping("pensionAlimenticia")
public class PensionAlimenticiaController {

    @Autowired
    Pension_AlimenticiaService pension_AlimenticiaService;

    @PostMapping("/guardaPensionAlimenticia")
    public ResponseEntity<Pension_Alimenticia_OJ> savePensionAlimenticia(@RequestBody Pension_Alimenticia_OJ pension_Alimenticia_OJ) {
        try {
            pension_AlimenticiaService.savePensionAlimenticia(pension_Alimenticia_OJ);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * *****************ORDEN JUDICIAL
     *
     ******************************
     * @param trabajador_id
     * @return
     */
    @GetMapping(value = "/listarPensionAlimenticia")
    public ResponseEntity<Pension_Alimenticia_OJ> listarPensionesAlimenticias() {
        try {
            List<Pension_Alimenticia_OJ> result = pension_AlimenticiaService.findAllPensiones();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarPensionAlimenticiaActivas")
    public ResponseEntity<Pension_AlimenticiaDTO> finAllPensionesActivas() {
        try {
            List<Pension_AlimenticiaDTO> result = pension_AlimenticiaService.finAllPensionesActivas();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/cuentaPensionesTrabajador/{trabajador_id}")
    public ResponseEntity<OutputEntity<Integer>> cuentaPensionesTrabajador(@RequestBody @PathVariable("trabajador_id") Integer trabajador_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Integer result = pension_AlimenticiaService.cuentaPensionesTrabajador(trabajador_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscartrabajadorOrdenJudicial/{trabajador_id}")
    public ResponseEntity<List<Pension_Alimenticia_OJ>> findTrabajadorOrdenJudicial(@PathVariable Integer trabajador_id) {
        try {
            List<Pension_Alimenticia_OJ> trabajador = pension_AlimenticiaService.findTrabajadorOrdenJudicial(trabajador_id);
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/updateEstatusPension/{idPension}")
    public ResponseEntity<Pension_Alimenticia_OJ> actualizarEstatus(@RequestBody @PathVariable Integer idPension) {
        try {
            return new ResponseEntity<>(pension_AlimenticiaService.actualizarEstatus(idPension), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizarPensionAlimenticia/{id_pension}")
    public ResponseEntity<OutputEntity<String>> updatePensionAlimenticia(@RequestBody Pension_Alimenticia_OJ pension_Alimenticia_OJ, @PathVariable("id_pension") Integer id_pension) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            pension_AlimenticiaService.updatePensionAlimenticia(pension_Alimenticia_OJ, id_pension);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos Pension Alimenticia actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }

    }

    @GetMapping(value = "/buscartrabajadorPensionA/{id_pension}")
    public ResponseEntity<List<Pension_Alimenticia_OJ>> findTrabajadorPensionA(@PathVariable Integer id_pension) {
        try {
            List<Pension_Alimenticia_OJ> trabajador = pension_AlimenticiaService.findTrabajadorPensionA(id_pension);
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarPensionAHistorico/{id_pension_historico}")
    public ResponseEntity<List<HistoricoPensionesAlimenticias>> findPensionAHistorico(@PathVariable Integer id_pension_historico) {
        try {
            List<HistoricoPensionesAlimenticias> trabajador = pension_AlimenticiaService.findHistoricoPensAlim(id_pension_historico);
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarTrabajadorPensionAHistorico/{id_trabajador}/{desde}/{hasta}")
    public ResponseEntity<List<HistoricoPensionesAlimenticias>> findTrabajadorPensionAHistorico(@PathVariable Integer id_trabajador, @PathVariable String desde, @PathVariable String hasta) throws ParseException {

        Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(desde);
        Date fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);
        try {
            List<HistoricoPensionesAlimenticias> trabajador = pension_AlimenticiaService.findTrabajadorPensionAHistorico(id_trabajador, fechaInicio, fechaFin);
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscartrabajadorOrdenJudicialInactiva/{trabajador_id}")
    public ResponseEntity<List<Pension_Alimenticia_OJ>> findTrabajadorOrdenJudicialHistoricoInactivo(@PathVariable Integer trabajador_id) {
        try {
            List<Pension_Alimenticia_OJ> trabajador = pension_AlimenticiaService.findTrabajadorOrdenJudicialHistoricoInactivo(trabajador_id);
            return new ResponseEntity<>(trabajador, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * *****************BENEFICARIO
     *
     ******************************
     * @param trabajador_id
     * @return
     */
    @GetMapping(value = "/buscartrabajadorBeneficiario/{trabajador_id}")
    public ResponseEntity<List<Pension_Alimenticia_B>> findTrabajadorBeneficiario(@PathVariable Integer trabajador_id) {
        try {
            List<Pension_Alimenticia_B> benbeficiario = pension_AlimenticiaService.findTrabajadorBeneficiario(trabajador_id);
            return new ResponseEntity<>(benbeficiario, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * *****************MONTOS
     *
     ******************************
     * @param trabajador_id
     * @return
     */
    @GetMapping(value = "/buscartrabajadorMontos/{trabajador_id}")
    public ResponseEntity<List<Pension_Alimenticia_Montos>> findTrabajadorMontos(@PathVariable Integer trabajador_id) {
        try {
            List<Pension_Alimenticia_Montos> montos = pension_AlimenticiaService.findTrabajadorMontos(trabajador_id);
            return new ResponseEntity<>(montos, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
