/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.Registro_Solicitudes_FonacotDTO;
import com.sirh.sirh.entity.Fechas_Corte_Prestamos;
import com.sirh.sirh.entity.Fonacot_Actualizacion_Cuentas;
import com.sirh.sirh.entity.Fonacot_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Fonacot_Registro_Pagos_Deudas;
import com.sirh.sirh.entity.Registro_Solicitudes_Fonacot;
import java.util.List;

/**
 *
 * @author oguerrero23
 */
public interface CreditoFonacotService {
    // ********** FONACOT CUENTAS POR COBRAR *******************
    public Fonacot_Cuentas_Por_Cobrar saveCuenta(Fonacot_Cuentas_Por_Cobrar cuenta);
    
    public List<Fonacot_Cuentas_Por_Cobrar> findOneCuentaFonacot(Integer trabajador_id);
    
    public List<Integer> nominasCargadas();
    
    public void deleteMovimientos(Integer nomina_id);

//    public Fonacot_Cuentas_Por_Cobrar findCuentaFonacot(Integer trabajador_id);
    
    public Fonacot_Cuentas_Por_Cobrar estatusCuenta(Integer trabajador_id);
    
    public List<Fonacot_Cuentas_Por_Cobrar> findAllCuentas();
    // ********** SOLICITUDES FONACOT *******************
    public Registro_Solicitudes_Fonacot saveSolicitud(Registro_Solicitudes_Fonacot solicitud);
     
    public List<Registro_Solicitudes_Fonacot> findSolicitudes();
    
    public List<Registro_Solicitudes_Fonacot> findOneSolicitud(Integer trabajador_id);
    
    public List<Registro_Solicitudes_FonacotDTO> documentoTexto();

    //*****************FONACOT REGISTRO DE PAGOS DEUDAS ***********************************
    public Fonacot_Registro_Pagos_Deudas saveRegistroPagosDeudas(Fonacot_Registro_Pagos_Deudas registro);
    
    public Fonacot_Cuentas_Por_Cobrar estatusCuentaSaldada(Integer id_trabajador);
//    
//    public List<Registro_Pagos_Deudas> findRegistroPagosDeudas();
    
    //********************* FONACOT ACTUALIZACIÓN DE CUENTAS *******************
    public List<Integer> recuperaCuentasActivas();
    
    public List saveActualizacionCuentas(List cuenta, Integer periodos);
    
    public List<Fonacot_Cuentas_Por_Cobrar> recuperaCuentasNomina(Integer nomina_id);
    
    public List<Fonacot_Actualizacion_Cuentas> findOneCuentaActual(Integer trabajador_id, Integer cuenta_por_cobrar_id);
    
    public Integer contadorDescuentosFonacot(Integer cuenta_por_cobrar_id);
    
    //****************FECHAS DE CORTE O DESCUENTOS***************************************
    public List<Fechas_Corte_Prestamos> findAllFechasCorte(); //Listar todos los datos
}

