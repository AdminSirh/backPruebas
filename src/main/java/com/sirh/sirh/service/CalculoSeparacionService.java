/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.FiniquitoDTO;
import java.time.LocalDate;

/**
 *
 * @author rroscero23
 */
public interface CalculoSeparacionService {

    public FiniquitoDTO calculaFiniquitoTrabajador(Integer idTrabajador, Boolean finiquito, Boolean jubilacion,
            Boolean pagoMarcha, Boolean fRenunciaVol,
            Boolean fRecisionContrato, Boolean fRemocionCargo,
            Double anios, Double meses, Double dias, LocalDate fechaIngreso, LocalDate fechaBaja);

    public Double calcularIsrAnual(Double total, Integer idNomina, Double aniosServicio, Boolean esIndemnizacion);

}
