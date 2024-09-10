/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.AdminLogDTO;
import com.sirh.sirh.entity.AdminLog;
import com.sirh.sirh.service.AdminLogService;
import com.sirh.sirh.util.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author jarellano22
 */
@RestController
@RequestMapping("adminLog")
public class AdminLogController {

    @Autowired
    AdminLogService adminLogService;

    //-------------Buscar los log usuarios----------------------------
    @GetMapping(value = "/listarMovimientos/{desde}/{hasta}")
    public ResponseEntity<List<AdminLog>> listarMovimientos(@PathVariable String desde, @PathVariable String hasta) throws ParseException {
        // OutputEntity<List<AdminLog>> out = new OutputEntity<>();

        Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(desde);
        Date fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);

        try {
            List<AdminLog> result = adminLogService.findAllB(fechaInicio, fechaFin);
            //  out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity(result, HttpStatus.OK);

            // out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), result);
            //return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarOperacionPorFecha/{operacion}/{desde}/{hasta}")
    public ResponseEntity<List<AdminLogDTO>> searchOperationByDate(@PathVariable String operacion, @PathVariable String desde, @PathVariable String hasta) throws ParseException {

        Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(desde);
        Date fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(hasta);

        try {
            List<AdminLogDTO> result = adminLogService.searchOperationByDate(operacion, fechaInicio, fechaFin);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
