/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.ConceptosRetroActJubDTO;
import java.util.List;

/**
 *
 * @author rroscero23
 */
public interface CalculoRetroactivoService {

    List<ConceptosRetroActJubDTO> conceptosRetroactivoJubilados(Integer periodoInicial, Integer periodoFinal, List<Integer> trabajadoresDescartados);

    List<ConceptosRetroActJubDTO> calculosRetroactivoJubilados(Integer periodoInicial, Integer periodoFinal, List<Integer> trabajadoresDescartados);

    public void calculaRetroactivoJubilados(List<String> expedientesDescartados, Integer periodoInicio, Integer periodoFin, Double porcentajeIncrPension,
            Double porcentajeIncrAyudaTransp, Double porcentajeIncrVales, Double porcentajeIncrPensAtrasada);

    public void insertarEnTmpMovimientos();
}
