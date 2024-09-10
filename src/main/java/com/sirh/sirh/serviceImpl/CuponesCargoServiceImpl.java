/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.entity.Cupones_Actualizacion_Cuentas;
import com.sirh.sirh.entity.Cupones_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Cupones_Registro_Pagos_Deudas;
import com.sirh.sirh.entity.Fechas_Corte_Descuentos;
import com.sirh.sirh.repository.Cupones_Actualizacion_CuentasRepository;
import com.sirh.sirh.repository.Cupones_Cuentas_Por_CobrarRepository;
import com.sirh.sirh.repository.Cupones_Registro_Pagos_DeudasRepository;
import com.sirh.sirh.repository.Fechas_Corte_DescuentosRepository;
import com.sirh.sirh.service.CuponesCargoService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author oguerrero23
 */
@Service
@Transactional
public class CuponesCargoServiceImpl implements CuponesCargoService {
    
    @Autowired
    Cupones_Actualizacion_CuentasRepository cupones_Actualizacion_CuentasRepository;    
    
    @Autowired
    Cupones_Cuentas_Por_CobrarRepository cupones_Cuentas_Por_CobrarRepository;
    
    @Autowired
    Cupones_Registro_Pagos_DeudasRepository cupones_Registro_Pagos_DeudasRepository;
    
    @Autowired
    Fechas_Corte_DescuentosRepository fechas_Corte_DescuentosRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    // *********** CUPONES CUENTAS POR COBRAR *********************************
    @Override
    public Cupones_Cuentas_Por_Cobrar saveCuenta(Cupones_Cuentas_Por_Cobrar cuenta) {
        Cupones_Cuentas_Por_Cobrar cuentas_Por_Cobrar = new Cupones_Cuentas_Por_Cobrar();
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
        
        return cupones_Cuentas_Por_CobrarRepository.save(cuentas_Por_Cobrar);
    }
    
    @Override
    public List<Cupones_Cuentas_Por_Cobrar> findOneCuentaCupones(Integer trabajador_id) {
        return cupones_Cuentas_Por_CobrarRepository.findOneCuentaCupones(trabajador_id);
    }
    
    @Override
    public Cupones_Cuentas_Por_Cobrar estatusCuenta(Integer trabajador_id) {
        Cupones_Cuentas_Por_Cobrar cuenta = cupones_Cuentas_Por_CobrarRepository.findCuentaEstatus(trabajador_id);
        cuenta.setEstatus(0);
        return cupones_Cuentas_Por_CobrarRepository.save(cuenta);
    }
    
    //******************CUPONES REGISTRO PAGO DEUDAS *******************
    
    @Override
    public Cupones_Registro_Pagos_Deudas saveRegistroPagosDeudas(Cupones_Registro_Pagos_Deudas registro) {
        Cupones_Registro_Pagos_Deudas reg = new Cupones_Registro_Pagos_Deudas();

        // Configurar los atributos del objeto reg basado en el objeto registro recibido
        reg.setCuentas_Por_Cobrar(registro.getCuentas_Por_Cobrar());
        reg.setEstatus(1);
        reg.setMonto_pago(registro.getMonto_pago()); 
        reg.setTrabajador_id(registro.getTrabajador_id());
        reg.setPeriodo_pago(registro.getPeriodo_pago()); 
        reg.setSaldo_deuda(registro.getSaldo_deuda()); 
        reg.setJustificacion(registro.getJustificacion());

        // Guardar el objeto reg en la base de datos
        return cupones_Registro_Pagos_DeudasRepository.save(reg);
    }
    
    @Override
    public Cupones_Cuentas_Por_Cobrar estatusCuentaSaldada(Integer id_cuenta) {
        LocalDate datetimeOne = LocalDate.now();
        Cupones_Cuentas_Por_Cobrar cuenta = cupones_Cuentas_Por_CobrarRepository.findById(id_cuenta).get();
        cuenta.setEstatus(4);
        cuenta.setFecha_cancelacion_cuenta(datetimeOne);
        return cupones_Cuentas_Por_CobrarRepository.save(cuenta);
    }
    
    //********************* CUPONES ACTUALIZACIÓN DE CUENTAS *******************

    @Override
    public List<Cupones_Actualizacion_Cuentas> findOneCuentaCuponesActual(Integer trabajador_id, Integer cupones_cuentas_cobrar_id) {
        return cupones_Actualizacion_CuentasRepository.findOneCuentaCuponesActual(trabajador_id, cupones_cuentas_cobrar_id);
    }
    
    @Override
    public List<Integer> recuperaCuentasActivasAuxilio() {
        return cupones_Cuentas_Por_CobrarRepository.recuperaCuentasActivasCupones();
    }
    
    @Override
    public List<Cupones_Cuentas_Por_Cobrar> recuperaCuentasNominaCupones(Integer nomina_id) {
        return cupones_Cuentas_Por_CobrarRepository.recuperaCuentasNominaCupones(nomina_id);
    }
    
    @Override
    public List saveActualizacionCuentasCupones(List cuenta, Integer periodos) {
         List<Cupones_Actualizacion_Cuentas> savedActualizaciones = new ArrayList<>();
        for (int i = 0; i < cuenta.size(); i++) {
            
            //Genera registros en Actualización de Cuentas
            Cupones_Cuentas_Por_Cobrar cuentaPorCobrar = (Cupones_Cuentas_Por_Cobrar) cuenta.get(i);
            Cupones_Actualizacion_Cuentas ac = new Cupones_Actualizacion_Cuentas();
            int contador = cupones_Actualizacion_CuentasRepository.contadorDescuentos(cuentaPorCobrar.getId_cupones_cuentas_cobrar());
            LocalDate datetime = LocalDate.now();
            ac.setAbono(cuentaPorCobrar.getMonto_pago());
            ac.setAbono_vac(cuentaPorCobrar.getSaldo_vacs());
            ac.setCupones_cuentas_cobrar_id(cuentaPorCobrar.getId_cupones_cuentas_cobrar());
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
            
            Cupones_Actualizacion_Cuentas savedAc = cupones_Actualizacion_CuentasRepository.save(ac);
            savedActualizaciones.add(savedAc);

            Cupones_Cuentas_Por_Cobrar cpc = cupones_Cuentas_Por_CobrarRepository.findById(cuentaPorCobrar.getId_cupones_cuentas_cobrar()).get();
            cpc.setSaldo_actual(cuentaPorCobrar.getSaldo_actual() - cuentaPorCobrar.getMonto_pago());
            cpc.setPeriodo_pago_actual(periodos);
            cpc.setFecha_movimiento(datetime);
            //Actualizar el periodo de pago actual con la consulta a periodos de pago
            if (savedAc.getSaldo_actual() <= 0.0)  {
                cpc.setEstatus(3);
            }

            cupones_Cuentas_Por_CobrarRepository.save(cpc);
            
            Fechas_Corte_Descuentos fcp = new Fechas_Corte_Descuentos();
            fcp.setFecha_corte(datetime);
            fcp.setIncidencia_id(73);
            fcp.setCat_Tipo_Nomina(cuentaPorCobrar.getCat_Tipo_Nomina());
            fechas_Corte_DescuentosRepository.save(fcp);
        }

        return savedActualizaciones;
    }
    
    //****************FECHAS DE CORTE O DESCUENTOS***************************************
    @Override
    public List<Fechas_Corte_Descuentos> findAllFechasCorte() {
        return fechas_Corte_DescuentosRepository.findAll().stream().map(fechas -> modelMapper.map(fechas, Fechas_Corte_Descuentos.class)).collect(Collectors.toList());
    }
}
