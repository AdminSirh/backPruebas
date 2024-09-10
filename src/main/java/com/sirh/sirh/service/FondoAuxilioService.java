/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.entity.Auxilio_Actualizacion_Cuentas;
import com.sirh.sirh.entity.Auxilio_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Auxilio_Registro_Pagos_Deudas;
import com.sirh.sirh.entity.Fechas_Corte_Descuentos;
import java.util.List;

/**
 *
 * @author oguerrero23
 */
public interface FondoAuxilioService {
    //********************* AUXILIO CUENTAS POR COBRAR *******************
    public Auxilio_Cuentas_Por_Cobrar saveCuenta(Auxilio_Cuentas_Por_Cobrar cuenta);
    
    public List<Auxilio_Cuentas_Por_Cobrar> findOneCuentaAuxilio(Integer trabajador_id);
    
    public Auxilio_Cuentas_Por_Cobrar estatusCuenta(Integer trabajador_id);
    
    //*****************AUXILIO REGISTRO DE PAGOS DEUDAS ***********************************
    public Auxilio_Registro_Pagos_Deudas saveRegistroPagosDeudas(Auxilio_Registro_Pagos_Deudas registro);
    
    public Auxilio_Cuentas_Por_Cobrar estatusCuentaSaldada(Integer id_trabajador);
    
    //********************* AUXILIO ACTUALIZACIÓN DE CUENTAS *******************
    public List<Auxilio_Actualizacion_Cuentas> findOneCuentaAuxilioActual( Integer trabajador_id, Integer auxilio_cuentas_cobrar_id);
    
    public List<Integer> recuperaCuentasActivasAuxilio();
    
    public List<Auxilio_Cuentas_Por_Cobrar> recuperaCuentasNominaAuxilio(Integer nomina_id);
    
    public List saveActualizacionCuentasAuxilio(List cuenta, Integer periodos);
    
    //****************FECHAS DE CORTE O DESCUENTOS***************************************
    public List<Fechas_Corte_Descuentos> findAllFechasCorte(); //Listar todos los datos
}
