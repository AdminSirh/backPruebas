/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.entity.Monto_Vales;
import com.sirh.sirh.entity.Periodos_Pago;
import com.sirh.sirh.entity.Tmp_Movimientos;
import java.util.List;

/**
 *
 * @author oguerrero23
 */
public interface ValesService {
    
    public List<Monto_Vales> findAllMontos();
    
    public Monto_Vales saveMonto(Monto_Vales monto_vales);
    
    public Monto_Vales findOneMonto(Integer id_monto_vales);
    
    public Monto_Vales updateMonto(Monto_Vales monto_vales, Integer id_monto_vale);
    
    public Monto_Vales findMontosVales(Integer dias_trabajados);
    
    //******************* PERIODOS DE PAGO  ************************************
    public List<Periodos_Pago> findPeriodosVales(Integer id_nomina);
    
    //******************* TMP_MOVIMIENTOS (2) DIAS LABORADOS EN EL AÑO *********
    
    public Tmp_Movimientos saveMovimientos2(Tmp_Movimientos movimientos);
    
    //******************* TMP_MOVIMIENTOS (244) TOTAL DE INCIDENCIAS POR AÑO *********
    
    public Tmp_Movimientos saveMovimientos244(Tmp_Movimientos movimientos);
    
    //******************* TMP_MOVIMIENTOS (249) VALES DE FIN DE AÑO *********
    
    public Tmp_Movimientos saveMovimientos249(Tmp_Movimientos movimientos);
}
