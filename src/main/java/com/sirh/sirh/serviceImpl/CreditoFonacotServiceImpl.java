package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.Registro_Solicitudes_FonacotDTO;
import com.sirh.sirh.entity.Fechas_Corte_Prestamos;
import com.sirh.sirh.entity.Fonacot_Actualizacion_Cuentas;
import com.sirh.sirh.entity.Fonacot_Cuentas_Por_Cobrar;
import com.sirh.sirh.entity.Fonacot_Registro_Pagos_Deudas;
import com.sirh.sirh.entity.Registro_Solicitudes_Fonacot;
import com.sirh.sirh.repository.Fechas_Corte_PrestamosRepository;
import com.sirh.sirh.repository.Registro_Solicitudes_FonacotRepository;
import com.sirh.sirh.service.CreditoFonacotService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sirh.sirh.repository.Fonacot_Cuentas_Por_CobrarRepository;
import com.sirh.sirh.repository.Fonacot_Actualizacion_CuentasRepository;
import com.sirh.sirh.repository.Fonacot_Registro_Pagos_DeudasRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author oguerrero23
 */
@Service
@Transactional
public class CreditoFonacotServiceImpl implements CreditoFonacotService {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    Fonacot_Cuentas_Por_CobrarRepository cuentas_Por_CobrarRepository;
    
    @Autowired
    Registro_Solicitudes_FonacotRepository registro_Solicitudes_FonacotRepository;
    
    @Autowired
    Fonacot_Registro_Pagos_DeudasRepository registro_Pagos_DeudasRepository;
    
    @Autowired
    Fonacot_Actualizacion_CuentasRepository actualizacion_CuentasRepository;
    
    @Autowired
    Fechas_Corte_PrestamosRepository fechas_Corte_PrestamosRepository;
    
    // ********** CUENTAS POR COBRAR *******************
    @Override
    public Fonacot_Cuentas_Por_Cobrar saveCuenta(Fonacot_Cuentas_Por_Cobrar cuenta) {
        Fonacot_Cuentas_Por_Cobrar cuentas_Por_Cobrar = new Fonacot_Cuentas_Por_Cobrar();
        LocalDate date = LocalDate.now();
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
        cuentas_Por_Cobrar.setFecha_inicio(date);
        cuentas_Por_Cobrar.setFecha_fin(fechaPorDefecto);
        cuentas_Por_Cobrar.setPeriodo_inicial(cuenta.getPeriodo_inicial());
        cuentas_Por_Cobrar.setDestino(0);
        cuentas_Por_Cobrar.setReferencia("");
        cuentas_Por_Cobrar.setRemanente_ultimo_pago_pr(cuenta.getRemanente_ultimo_pago_pr());
        cuentas_Por_Cobrar.setEstatus(1);
        cuentas_Por_Cobrar.setFecha_movimiento(date);
        cuentas_Por_Cobrar.setFecha_cancelacion_cuenta(cuenta.getFecha_cancelacion_cuenta());
        cuentas_Por_Cobrar.setDevolucion(cuenta.getDevolucion());
        cuentas_Por_Cobrar.setMonto_criterio_pago(cuenta.getMonto_criterio_pago());
        cuentas_Por_Cobrar.setNumero_deuda(1);
        cuentas_Por_Cobrar.setRetencion_nom(0.0);
        cuentas_Por_Cobrar.setRetencion_vac(0.0);
        cuentas_Por_Cobrar.setSaldo_vacs(cuenta.getSaldo_vacs());
        cuentas_Por_Cobrar.setSaldo_actual_pr(cuenta.getSaldo_actual_pr());
        cuentas_Por_Cobrar.setSolicitado_fonacot(cuenta.getSolicitado_fonacot());
        
        return cuentas_Por_CobrarRepository.save(cuentas_Por_Cobrar);
    }

    @Override
    public List<Fonacot_Cuentas_Por_Cobrar> findOneCuentaFonacot(Integer trabajador_id) {
        return cuentas_Por_CobrarRepository.findOneCuentaFonacot(trabajador_id);
    }
    
    @Override
    public List<Integer> nominasCargadas() {
        return cuentas_Por_CobrarRepository.nominasCargadas();
    }
    
    @Override
    public void deleteMovimientos(Integer nomina_id) {
        cuentas_Por_CobrarRepository.deleteMovimientos(nomina_id);
    }

//     @Override
//    public Fonacot_Cuentas_Por_Cobrar findCuentaFonacot(Integer trabajador_id) {
//        return cuentas_Por_CobrarRepository.findCuentaFonacot(trabajador_id);
//    }
//
    @Override
    public Fonacot_Cuentas_Por_Cobrar estatusCuenta(Integer trabajador_id) {
        Fonacot_Cuentas_Por_Cobrar cuenta = cuentas_Por_CobrarRepository.findCuentaEstatus(trabajador_id);
        cuenta.setEstatus(0);
        return cuentas_Por_CobrarRepository.save(cuenta);
    }
    
    @Override
    public List<Fonacot_Cuentas_Por_Cobrar> findAllCuentas() {
        return cuentas_Por_CobrarRepository.findAllCuentas();
    }
    // ********** SOLICITUDES FONACOT *******************
    @Override
    public Registro_Solicitudes_Fonacot saveSolicitud(Registro_Solicitudes_Fonacot solicitud) {
        Registro_Solicitudes_Fonacot rg = new Registro_Solicitudes_Fonacot();
        LocalDate datetime = LocalDate.now();
        
        rg.setTrabajador(solicitud.getTrabajador());
        rg.setEstatus(1);
        rg.setFecha_captura(datetime);
        rg.setFecha_movimiento(datetime);
        
        return registro_Solicitudes_FonacotRepository.save(rg);
    }

    @Override
    public List<Registro_Solicitudes_Fonacot> findSolicitudes() {
        return registro_Solicitudes_FonacotRepository.findAll().stream().map(solicitudes -> modelMapper.map(solicitudes, Registro_Solicitudes_Fonacot.class)).collect(Collectors.toList());
    }

    @Override
    public List<Registro_Solicitudes_Fonacot> findOneSolicitud(Integer trabajador_id) {
        return registro_Solicitudes_FonacotRepository.findOneSolicitud(trabajador_id);
    }

    @Override
    public List<Registro_Solicitudes_FonacotDTO> documentoTexto() {
        return registro_Solicitudes_FonacotRepository.documentoTexto();
    }
//     **********FONACOT REGISTRO PAGO DEUDAS *******************
    @Override
    public Fonacot_Registro_Pagos_Deudas saveRegistroPagosDeudas(Fonacot_Registro_Pagos_Deudas registro) {
        
        Fonacot_Registro_Pagos_Deudas reg = new Fonacot_Registro_Pagos_Deudas();

        // Configurar los atributos del objeto reg basado en el objeto registro recibido
        reg.setCuentas_Por_Cobrar(registro.getCuentas_Por_Cobrar());
        reg.setEstatus(1);
        reg.setMonto_pago(registro.getMonto_pago()); 
        reg.setTrabajador_id(registro.getTrabajador_id());
        reg.setPeriodo_pago(registro.getPeriodo_pago()); 
        reg.setSaldo_deuda(registro.getSaldo_deuda()); 
        reg.setJustificacion(registro.getJustificacion());

        // Guardar el objeto reg en la base de datos
        return registro_Pagos_DeudasRepository.save(reg);
    }
//    
//    @Override
//    public List<Registro_Pagos_Deudas> findRegistroPagosDeudas(){
//        return registro_Pagos_DeudasRepository.findAll().stream().map(solicitudes -> modelMapper.map(solicitudes, Fonacot_Registro_Pagos_Deudas.class)).collect(Collectors.toList());
//    }

    @Override
    public Fonacot_Cuentas_Por_Cobrar estatusCuentaSaldada(Integer id_cuenta) {
        LocalDate datetime = LocalDate.now();
        Fonacot_Cuentas_Por_Cobrar cuenta = cuentas_Por_CobrarRepository.findById(id_cuenta).get();
        cuenta.setEstatus(4);
        cuenta.setFecha_cancelacion_cuenta(datetime);
        return cuentas_Por_CobrarRepository.save(cuenta);
    }
    
    //********************* ACTUALIZACIÓN DE CUENTAS *******************
    @Override
    public List<Integer> recuperaCuentasActivas() {
        return cuentas_Por_CobrarRepository.recuperaCuentasActivas();
    }
    
    @Override
    public Integer contadorDescuentosFonacot(Integer cuenta_por_cobrar_id) {
        return actualizacion_CuentasRepository.contadorDescuentosFonacot(cuenta_por_cobrar_id);
    }
    
    @Override
    public List saveActualizacionCuentas(List cuenta, Integer periodos) {
         List<Fonacot_Actualizacion_Cuentas> savedActualizaciones = new ArrayList<>();
        for (int i = 0; i < cuenta.size(); i++) {
            //Genera registros en Actualización de Cuentas
            Fonacot_Cuentas_Por_Cobrar cuentaPorCobrar = (Fonacot_Cuentas_Por_Cobrar) cuenta.get(i);
            Fonacot_Actualizacion_Cuentas ac = new Fonacot_Actualizacion_Cuentas();
            int contador = actualizacion_CuentasRepository.contadorDescuentosFonacot(cuentaPorCobrar.getId_cuentas_cobrar());
            LocalDate datetime = LocalDate.now();
            ac.setAbono(cuentaPorCobrar.getMonto_pago());
            ac.setAbono_vac(cuentaPorCobrar.getSaldo_vacs());
            ac.setCuenta_por_cobrar_id(cuentaPorCobrar.getId_cuentas_cobrar());
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
            ac.setPeriodo(cuentaPorCobrar.getPeriodo_pago_actual());
            ac.setFecha_movimiento(datetime);
            Fonacot_Actualizacion_Cuentas savedAc = actualizacion_CuentasRepository.save(ac);
            savedActualizaciones.add(savedAc);

            Fonacot_Cuentas_Por_Cobrar cpc = cuentas_Por_CobrarRepository.findById(cuentaPorCobrar.getId_cuentas_cobrar()).get();
            
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
            fcp.setIncidencia_id(354);
            fcp.setCat_Tipo_Nomina(cuentaPorCobrar.getCat_Tipo_Nomina());
            fechas_Corte_PrestamosRepository.save(fcp);
        }

        return savedActualizaciones;
    }

    @Override
    public List<Fonacot_Cuentas_Por_Cobrar> recuperaCuentasNomina(Integer nomina_id) {
        return cuentas_Por_CobrarRepository.recuperaCuentasNomina(nomina_id);
    }

    @Override
    public List<Fonacot_Actualizacion_Cuentas> findOneCuentaActual(Integer trabajador_id, Integer cuenta_por_cobrar_id) {
        return actualizacion_CuentasRepository.findOneCuentaActual(trabajador_id, cuenta_por_cobrar_id);
    }
    
    //****************FECHAS DE CORTE O DESCUENTOS***************************************
    @Override
    public List<Fechas_Corte_Prestamos> findAllFechasCorte() {
        return fechas_Corte_PrestamosRepository.findAll().stream().map(fechas -> modelMapper.map(fechas, Fechas_Corte_Prestamos.class)).collect(Collectors.toList());
    }

}
