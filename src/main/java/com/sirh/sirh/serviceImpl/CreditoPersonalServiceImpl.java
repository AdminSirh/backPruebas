/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.entity.Fechas_Corte_Prestamos;
import com.sirh.sirh.entity.Fonacot_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Personal_Actualizacion_Cuentas;
import com.sirh.sirh.entity.Personal_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Personal_Registro_Pagos_Deudas;
import com.sirh.sirh.repository.Fechas_Corte_PrestamosRepository;
import com.sirh.sirh.repository.Personal_Actualizacion_CuentasRepository;
import com.sirh.sirh.service.CreditoPersonalService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sirh.sirh.repository.Personal_Cuentas_Por_CobrarRepository;
import com.sirh.sirh.repository.Personal_Registro_Pagos_DeudasRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

/**
 *
 * @author oguerrero23
 */
@Service
@Transactional
public class CreditoPersonalServiceImpl implements CreditoPersonalService{

@Autowired
Personal_Cuentas_Por_CobrarRepository cuentas_Por_CobrarRepository;

@Autowired
Personal_Actualizacion_CuentasRepository personal_Actualizacion_CuentasRepository; 

@Autowired
Personal_Registro_Pagos_DeudasRepository personal_Registro_Pagos_DeudasRepository;

@Autowired
Fechas_Corte_PrestamosRepository fechas_Corte_PrestamosRepository;
    
@Autowired
private ModelMapper modelMapper;
// **********PERSONAL CUENTAS POR COBRAR *****************************
    @Override
    public Personal_Cuentas_Por_Cobrar saveCuenta(Personal_Cuentas_Por_Cobrar cuenta) {
        Personal_Cuentas_Por_Cobrar cuentas_Por_Cobrar = new Personal_Cuentas_Por_Cobrar();
        LocalDate datetime = LocalDate.now();
        LocalDate fechaPorDefecto = LocalDate.of(1900, 1, 1);
        cuentas_Por_Cobrar.setTrabajador(cuenta.getTrabajador());
        cuentas_Por_Cobrar.setCat_Tipo_Nomina(cuenta.getCat_Tipo_Nomina());
        cuentas_Por_Cobrar.setSaldo_inicial(cuenta.getSaldo_inicial());
        cuentas_Por_Cobrar.setSaldo_actual(cuenta.getSaldo_actual());
        cuentas_Por_Cobrar.setMonto_pago(cuenta.getMonto_pago());
        cuentas_Por_Cobrar.setNumero_periodos_pago(cuenta.getNumero_periodos_pago());
        cuentas_Por_Cobrar.setPeriodo_pago_actual(cuenta.getPeriodo_pago_actual());
        cuentas_Por_Cobrar.setCriterio_pago("3");
        cuentas_Por_Cobrar.setRemanente_ultimo_pago(cuenta.getRemanente_ultimo_pago());
        cuentas_Por_Cobrar.setFecha_inicio(datetime);
        cuentas_Por_Cobrar.setFecha_fin(fechaPorDefecto);
        cuentas_Por_Cobrar.setPeriodo_inicial(cuenta.getPeriodo_inicial());
        cuentas_Por_Cobrar.setDestino(0);
        cuentas_Por_Cobrar.setReferencia("");
        cuentas_Por_Cobrar.setRemanente_ultimo_pago_pr(cuenta.getRemanente_ultimo_pago_pr());
        cuentas_Por_Cobrar.setEstatus(1);
        cuentas_Por_Cobrar.setFecha_movimiento(datetime);
        cuentas_Por_Cobrar.setFecha_cancelacion_cuenta(cuenta.getFecha_cancelacion_cuenta());
        cuentas_Por_Cobrar.setDevolucion(cuenta.getDevolucion());
        cuentas_Por_Cobrar.setMonto_criterio_pago(cuenta.getMonto_criterio_pago());
        cuentas_Por_Cobrar.setNumero_deuda(1);
        cuentas_Por_Cobrar.setRetencion_norm(0.0);
        cuentas_Por_Cobrar.setRetencion_vac(0.0);
        cuentas_Por_Cobrar.setSaldo_vacs(cuenta.getSaldo_vacs());
        cuentas_Por_Cobrar.setSaldo_actual_pr(cuenta.getSaldo_actual_pr());
        cuentas_Por_Cobrar.setDeposito(0);
        
        return cuentas_Por_CobrarRepository.save(cuentas_Por_Cobrar);
    }

    @Override
    public List<Personal_Cuentas_Por_Cobrar> findOneCuentaPersonal(Integer trabajador_id) {
        return cuentas_Por_CobrarRepository.findOneCuentaPersonal(trabajador_id);
    }

    @Override
    public List<Personal_Cuentas_Por_Cobrar> estatusCuentaPersonal(Integer trabajador_id) {
        List<Personal_Cuentas_Por_Cobrar> cuenta = cuentas_Por_CobrarRepository.findCuentaPersonalEstatus(trabajador_id);
        for (int i = 0; i < cuenta.size(); i++) {
            Personal_Cuentas_Por_Cobrar cuentaPorCobrar = (Personal_Cuentas_Por_Cobrar) cuenta.get(i);
            cuentaPorCobrar.setEstatus(0);
            cuentas_Por_CobrarRepository.save(cuentaPorCobrar);
        }
        
        return cuenta;
    }
    
    @Override
    public List<Personal_Cuentas_Por_Cobrar> depositosReporte(Integer nomina_id, Integer periodo_pago) {
        List<Personal_Cuentas_Por_Cobrar> cuenta = cuentas_Por_CobrarRepository.depositosReporte(nomina_id, periodo_pago);
        for (int i = 0; i < cuenta.size(); i++) {
            Personal_Cuentas_Por_Cobrar cuentaPorCobrar = (Personal_Cuentas_Por_Cobrar) cuenta.get(i);
            cuentaPorCobrar.setDeposito(1);
            cuentas_Por_CobrarRepository.save(cuentaPorCobrar);
        }
        
        return cuenta;
    }

    //********************* PERSONAL ACTUALIZACIÓN DE CUENTAS *******************

    @Override
    public List<Personal_Actualizacion_Cuentas> findOneCuentaPersonalActual(Integer trabajador_id, Integer personal_cuentas_por_cobrar_id) {
        return personal_Actualizacion_CuentasRepository.findOneCuentaPersonalActual(trabajador_id, personal_cuentas_por_cobrar_id);
    }

    @Override
    public List<Integer> recuperaCuentasActivasPersonal() {
        return cuentas_Por_CobrarRepository.recuperaCuentasActivasPersonal();
    }

    @Override
    public List<Personal_Cuentas_Por_Cobrar> recuperaCuentasNominaPersonal(Integer nomina_id) {
        return cuentas_Por_CobrarRepository.recuperaCuentasNominaPersonal(nomina_id);
    }
    
    

    @Override
    public Integer contadorDescuentos(Integer personal_cuentas_por_cobrar_id) {
        return personal_Actualizacion_CuentasRepository.contadorDescuentos(personal_cuentas_por_cobrar_id);
    }
    
    @Override
    public List saveActualizacionCuentasPersonal(List cuenta, Integer periodos) {
         List<Personal_Actualizacion_Cuentas> savedActualizaciones = new ArrayList<>();
        for (int i = 0; i < cuenta.size(); i++) {
            
            //Genera registros en Actualización de Cuentas
            Personal_Cuentas_Por_Cobrar cuentaPorCobrar = (Personal_Cuentas_Por_Cobrar) cuenta.get(i);
            Personal_Actualizacion_Cuentas ac = new Personal_Actualizacion_Cuentas();
            int contador = personal_Actualizacion_CuentasRepository.contadorDescuentos(cuentaPorCobrar.getId_personal_cuentas_cobrar());
            LocalDate datetime = LocalDate.now();
            ac.setAbono(cuentaPorCobrar.getMonto_pago());
            ac.setAbono_vac(cuentaPorCobrar.getSaldo_vacs());
            ac.setPersonal_cuentas_por_cobrar_id(cuentaPorCobrar.getId_personal_cuentas_cobrar());
            ac.setSaldo_anterior(cuentaPorCobrar.getSaldo_actual());
            ac.setEstatus(1);
            if (contador == cuentaPorCobrar.getNumero_periodos_pago()-1) {
                if (ac.getSaldo_anterior() != ac.getAbono()) {
                    ac.setAbono(ac.getSaldo_anterior());
                    cuentaPorCobrar.setMonto_pago(cuentaPorCobrar.getSaldo_actual());
                }
                ac.setEstatus(3);
            }
            ac.setSaldo_actual(ac.getSaldo_anterior()-ac.getAbono());
            ac.setTrabajador(cuentaPorCobrar.getTrabajador());
            ac.setCat_Tipo_Nomina(cuentaPorCobrar.getCat_Tipo_Nomina());
            ac.setFecha_movimiento(datetime);
            ac.setPeriodo(cuentaPorCobrar.getPeriodo_pago_actual());
            
            Personal_Actualizacion_Cuentas savedAc = personal_Actualizacion_CuentasRepository.save(ac);
            savedActualizaciones.add(savedAc);

            Personal_Cuentas_Por_Cobrar cpc = cuentas_Por_CobrarRepository.findById(cuentaPorCobrar.getId_personal_cuentas_cobrar()).get();
            cpc.setSaldo_actual(cuentaPorCobrar.getSaldo_actual() - cuentaPorCobrar.getMonto_pago());
            cpc.setPeriodo_pago_actual(periodos);
            cpc.setFecha_movimiento(datetime);
            //Actualizar el periodo de pago actual con la consulta a periodos de pago
            if (savedAc.getSaldo_actual() <= 0.0)  {
                cpc.setEstatus(3);
            }

            cuentas_Por_CobrarRepository.save(cpc);
            
            Fechas_Corte_Prestamos fcp = new Fechas_Corte_Prestamos();
            fcp.setFecha_corte(datetime);
            fcp.setIncidencia_id(60);
            fcp.setCat_Tipo_Nomina(cuentaPorCobrar.getCat_Tipo_Nomina());
            fechas_Corte_PrestamosRepository.save(fcp);
        }

        return savedActualizaciones;
    }

    //     **********PERSONAL REGISTRO PAGO DEUDAS *******************
    @Override
    public Personal_Registro_Pagos_Deudas saveRegistroPagosDeudasPersonal(Personal_Registro_Pagos_Deudas registro) {
        
        Personal_Registro_Pagos_Deudas reg = new Personal_Registro_Pagos_Deudas();

        // Configurar los atributos del objeto reg basado en el objeto registro recibido
        reg.setCuentas_Por_Cobrar(registro.getCuentas_Por_Cobrar());
        reg.setEstatus(1);
        reg.setMonto_pago(registro.getMonto_pago()); 
        reg.setTrabajador_id(registro.getTrabajador_id());
        reg.setPeriodo_pago(registro.getPeriodo_pago()); 
        reg.setSaldo_deuda(registro.getSaldo_deuda()); 
        reg.setJustificacion(registro.getJustificacion());

        // Guardar el objeto reg en la base de datos
        return personal_Registro_Pagos_DeudasRepository.save(reg);
    }
    
    @Override
    public Personal_Cuentas_Por_Cobrar estatusCuentaPersonalSaldada(Integer id_cuenta) {
        LocalDate datetimeOne = LocalDate.now();
        Personal_Cuentas_Por_Cobrar cuenta = cuentas_Por_CobrarRepository.findById(id_cuenta).get();
        cuenta.setEstatus(4);
        cuenta.setFecha_cancelacion_cuenta(datetimeOne);
        return cuentas_Por_CobrarRepository.save(cuenta);
    }

    //****************FECHAS DE CORTE O DESCUENTOS***************************************
    @Override
    public List<Fechas_Corte_Prestamos> findAllFechasCorte() {
        return fechas_Corte_PrestamosRepository.findAll().stream().map(fechas -> modelMapper.map(fechas, Fechas_Corte_Prestamos.class)).collect(Collectors.toList());
    }
    
}
