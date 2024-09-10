/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.entity.Fechas_Corte_Descuentos;
import com.sirh.sirh.entity.Vivienda_Actualizacion_Cuentas;
import com.sirh.sirh.entity.Vivienda_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Vivienda_Registro_Pagos_Deudas;
import java.util.List;

/**
 *
 * @author oguerrero23
 */
public interface AbonoViviendaService {
    //********************* VIVIENDA CUENTAS POR COBRAR *******************
    public Vivienda_Cuentas_Por_Cobrar saveCuenta(Vivienda_Cuentas_Por_Cobrar cuenta);
    
    public List<Vivienda_Cuentas_Por_Cobrar> findOneCuentaVivienda(Integer trabajador_id);
    
    public Vivienda_Cuentas_Por_Cobrar estatusCuenta(Integer trabajador_id);
    
    //*****************VIVIENDA REGISTRO DE PAGOS DEUDAS ***********************************
    public Vivienda_Registro_Pagos_Deudas saveRegistroPagosDeudas(Vivienda_Registro_Pagos_Deudas registro);
    
    public Vivienda_Cuentas_Por_Cobrar estatusCuentaSaldada(Integer id_trabajador);
    
    //********************* VIVIENDA ACTUALIZACIÓN DE CUENTAS *******************
    public List<Vivienda_Actualizacion_Cuentas> findOneCuentaViviendaActual( Integer trabajador_id, Integer vivienda_cuentas_cobrar_id);
    
    public List<Integer> recuperaCuentasActivasVivienda();
    
    public List<Vivienda_Cuentas_Por_Cobrar> recuperaCuentasNominaVivienda(Integer nomina_id);
    
    public List saveActualizacionCuentasVivienda(List cuenta, Integer periodos);
    
    //****************FECHAS DE CORTE O DESCUENTOS***************************************
    public List<Fechas_Corte_Descuentos> findAllFechasCorte(); //Listar todos los datos
}
