/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

/**
 *
 * @author rroscero23
 */
public interface CierrePlantillaService {

    public void ejecutarCierreMensual();
    
    public void generaRegistroPlazasConsolidado();

    public void ejecutarTmpRegistroConsolidado();

    public Boolean boolEjecutoCierreMensual(Integer anio, Integer mes);

}
