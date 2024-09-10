/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.entity.Cupones_Actualizacion_Cuentas;
import com.sirh.sirh.entity.Cupones_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Cupones_Registro_Pagos_Deudas;
import com.sirh.sirh.entity.Fechas_Corte_Descuentos;
import java.util.List;

/**
 *
 * @author oguerrero23
 */
public interface CuponesCargoService {
    //********************* CUPONES CUENTAS POR COBRAR *******************
    public Cupones_Cuentas_Por_Cobrar saveCuenta(Cupones_Cuentas_Por_Cobrar cuenta);
    
    public List<Cupones_Cuentas_Por_Cobrar> findOneCuentaCupones(Integer trabajador_id);
    
    public Cupones_Cuentas_Por_Cobrar estatusCuenta(Integer trabajador_id);
    
    //*****************CUPONES REGISTRO DE PAGOS DEUDAS ***********************************
    public Cupones_Registro_Pagos_Deudas saveRegistroPagosDeudas(Cupones_Registro_Pagos_Deudas registro);
    
    public Cupones_Cuentas_Por_Cobrar estatusCuentaSaldada(Integer id_trabajador);
    
    //********************* CUPONES ACTUALIZACIÓN DE CUENTAS *******************
    public List<Cupones_Actualizacion_Cuentas> findOneCuentaCuponesActual(Integer trabajador_id, Integer cupones_cuentas_cobrar_id);
    
    public List<Integer> recuperaCuentasActivasAuxilio();
    
    public List<Cupones_Cuentas_Por_Cobrar> recuperaCuentasNominaCupones(Integer nomina_id);
    
    public List saveActualizacionCuentasCupones(List cuenta, Integer periodos);
    
    //****************FECHAS DE CORTE O DESCUENTOS***************************************
    public List<Fechas_Corte_Descuentos> findAllFechasCorte(); //Listar todos los datos
}
