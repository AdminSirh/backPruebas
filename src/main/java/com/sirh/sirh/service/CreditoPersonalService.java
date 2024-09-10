/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.entity.Fechas_Corte_Prestamos;
import com.sirh.sirh.entity.Fonacot_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Personal_Actualizacion_Cuentas;
import com.sirh.sirh.entity.Personal_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Personal_Registro_Pagos_Deudas;
import java.util.List;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author oguerrero23
 */
public interface CreditoPersonalService {
    
    //********************* PERSONAL CUENTAS POR COBRAR *******************
    public Personal_Cuentas_Por_Cobrar saveCuenta(Personal_Cuentas_Por_Cobrar cuenta);
    
    public List<Personal_Cuentas_Por_Cobrar> findOneCuentaPersonal(Integer trabajador_id);
    
    public List<Personal_Cuentas_Por_Cobrar> estatusCuentaPersonal(Integer trabajador_id);
    
    public List<Personal_Cuentas_Por_Cobrar> depositosReporte(Integer nomina_id, Integer periodo_pago);
    
    //********************* PERSONAL ACTUALIZACIÓN DE CUENTAS *******************
    public List<Integer> recuperaCuentasActivasPersonal();
    
    public List<Personal_Actualizacion_Cuentas> findOneCuentaPersonalActual( Integer trabajador_id, Integer personal_cuentas_por_cobrar_id);
    
    public List<Personal_Cuentas_Por_Cobrar> recuperaCuentasNominaPersonal(Integer nomina_id);
    
    public List saveActualizacionCuentasPersonal(List cuenta, Integer periodos);
    
    public Integer contadorDescuentos(Integer personal_cuentas_por_cobrar_id);
    //*****************PERSONAL REGISTRO DE PAGOS DEUDAS ***********************************
    public Personal_Registro_Pagos_Deudas saveRegistroPagosDeudasPersonal(Personal_Registro_Pagos_Deudas registro);
    
    public Personal_Cuentas_Por_Cobrar estatusCuentaPersonalSaldada(Integer id_cuenta);
    
    //****************FECHAS DE CORTE O DESCUENTOS***************************************
    public List<Fechas_Corte_Prestamos> findAllFechasCorte(); //Listar todos los datos
    
}
