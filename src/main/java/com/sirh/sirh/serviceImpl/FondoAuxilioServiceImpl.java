/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.entity.Auxilio_Actualizacion_Cuentas;
import com.sirh.sirh.entity.Auxilio_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Auxilio_Registro_Pagos_Deudas;
import com.sirh.sirh.entity.Fechas_Corte_Descuentos;
import com.sirh.sirh.repository.Auxilio_Actualizacion_CuentasRepository;
import com.sirh.sirh.repository.Auxilio_Cuentas_Por_CobrarRepository;
import com.sirh.sirh.repository.Auxilio_Registro_Pagos_DeudasRepository;
import com.sirh.sirh.repository.Fechas_Corte_DescuentosRepository;
import com.sirh.sirh.service.FondoAuxilioService;
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
public class FondoAuxilioServiceImpl implements FondoAuxilioService{
    @Autowired
    Auxilio_Cuentas_Por_CobrarRepository auxilio_Cuentas_Por_CobrarRepository;
    
    @Autowired
    Auxilio_Registro_Pagos_DeudasRepository auxilio_Registro_Pagos_DeudasRepository;
    
    @Autowired
    Auxilio_Actualizacion_CuentasRepository auxilio_Actualizacion_CuentasRepository;
    
    @Autowired
    Fechas_Corte_DescuentosRepository fechas_Corte_DescuentosRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    // *********** AUXILIO CUENTAS POR COBRAR *********************************
    @Override
    public Auxilio_Cuentas_Por_Cobrar saveCuenta(Auxilio_Cuentas_Por_Cobrar cuenta) {
        Auxilio_Cuentas_Por_Cobrar cuentas_Por_Cobrar = new Auxilio_Cuentas_Por_Cobrar();
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
        
        return auxilio_Cuentas_Por_CobrarRepository.save(cuentas_Por_Cobrar);
    }

    @Override
    public List<Auxilio_Cuentas_Por_Cobrar> findOneCuentaAuxilio(Integer trabajador_id) {
        return auxilio_Cuentas_Por_CobrarRepository.findOneCuentaAuxilio(trabajador_id);
    }
    
    @Override
    public Auxilio_Cuentas_Por_Cobrar estatusCuenta(Integer trabajador_id) {
        Auxilio_Cuentas_Por_Cobrar cuenta = auxilio_Cuentas_Por_CobrarRepository.findCuentaEstatus(trabajador_id);
        cuenta.setEstatus(0);
        return auxilio_Cuentas_Por_CobrarRepository.save(cuenta);
    }
    
    //     **********AUXILIO REGISTRO PAGO DEUDAS *******************
    
    @Override
    public Auxilio_Registro_Pagos_Deudas saveRegistroPagosDeudas(Auxilio_Registro_Pagos_Deudas registro) {
        
        Auxilio_Registro_Pagos_Deudas reg = new Auxilio_Registro_Pagos_Deudas();

        // Configurar los atributos del objeto reg basado en el objeto registro recibido
        reg.setCuentas_Por_Cobrar(registro.getCuentas_Por_Cobrar());
        reg.setEstatus(1);
        reg.setMonto_pago(registro.getMonto_pago()); 
        reg.setTrabajador_id(registro.getTrabajador_id());
        reg.setPeriodo_pago(registro.getPeriodo_pago()); 
        reg.setSaldo_deuda(registro.getSaldo_deuda()); 
        reg.setJustificacion(registro.getJustificacion());

        // Guardar el objeto reg en la base de datos
        return auxilio_Registro_Pagos_DeudasRepository.save(reg);
    }
    
    @Override
    public Auxilio_Cuentas_Por_Cobrar estatusCuentaSaldada(Integer id_cuenta) {
        LocalDate datetimeOne = LocalDate.now();
        Auxilio_Cuentas_Por_Cobrar cuenta = auxilio_Cuentas_Por_CobrarRepository.findById(id_cuenta).get();
        cuenta.setEstatus(4);
        cuenta.setFecha_cancelacion_cuenta(datetimeOne);
        return auxilio_Cuentas_Por_CobrarRepository.save(cuenta);
    }
    
    //********************* AUXILIO ACTUALIZACIÓN DE CUENTAS *******************

    @Override
    public List<Auxilio_Actualizacion_Cuentas> findOneCuentaAuxilioActual(Integer trabajador_id, Integer auxilio_cuentas_cobrar_id) {
        return auxilio_Actualizacion_CuentasRepository.findOneCuentaAuxilioActual(trabajador_id, auxilio_cuentas_cobrar_id);
    }
    
    @Override
    public List<Integer> recuperaCuentasActivasAuxilio() {
        return auxilio_Cuentas_Por_CobrarRepository.recuperaCuentasActivasAuxilio();
    }
    
    @Override
    public List<Auxilio_Cuentas_Por_Cobrar> recuperaCuentasNominaAuxilio(Integer nomina_id) {
        return auxilio_Cuentas_Por_CobrarRepository.recuperaCuentasNominaAuxilio(nomina_id);
    }
    
    @Override
    public List saveActualizacionCuentasAuxilio(List cuenta, Integer periodos) {
         List<Auxilio_Actualizacion_Cuentas> savedActualizaciones = new ArrayList<>();
        for (int i = 0; i < cuenta.size(); i++) {
            
            //Genera registros en Actualización de Cuentas
            Auxilio_Cuentas_Por_Cobrar cuentaPorCobrar = (Auxilio_Cuentas_Por_Cobrar) cuenta.get(i);
            Auxilio_Actualizacion_Cuentas ac = new Auxilio_Actualizacion_Cuentas();
            int contador = auxilio_Actualizacion_CuentasRepository.contadorDescuentos(cuentaPorCobrar.getId_auxilio_cuentas_cobrar());
            LocalDate datetime = LocalDate.now();
            ac.setAbono(cuentaPorCobrar.getMonto_pago());
            ac.setAbono_vac(cuentaPorCobrar.getSaldo_vacs());
            ac.setAuxilio_cuentas_cobrar_id(cuentaPorCobrar.getId_auxilio_cuentas_cobrar());
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
            
            Auxilio_Actualizacion_Cuentas savedAc = auxilio_Actualizacion_CuentasRepository.save(ac);
            savedActualizaciones.add(savedAc);

            Auxilio_Cuentas_Por_Cobrar cpc = auxilio_Cuentas_Por_CobrarRepository.findById(cuentaPorCobrar.getId_auxilio_cuentas_cobrar()).get();
            cpc.setSaldo_actual(cuentaPorCobrar.getSaldo_actual() - cuentaPorCobrar.getMonto_pago());
            cpc.setPeriodo_pago_actual(periodos);
            cpc.setFecha_movimiento(datetime);
            //Actualizar el periodo de pago actual con la consulta a periodos de pago
            if (savedAc.getSaldo_actual() <= 0.0)  {
                cpc.setEstatus(3);
            }

            auxilio_Cuentas_Por_CobrarRepository.save(cpc);
            
            Fechas_Corte_Descuentos fcp = new Fechas_Corte_Descuentos();
            fcp.setFecha_corte(datetime);
            fcp.setIncidencia_id(61);
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
