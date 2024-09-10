/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.entity.Cat_Incidencias;
import com.sirh.sirh.entity.Cat_Percepcion;
import com.sirh.sirh.entity.Monto_Vales;
import com.sirh.sirh.entity.Periodos_Pago;
import com.sirh.sirh.entity.Tmp_Movimientos;
import com.sirh.sirh.repository.Monto_ValesRepository;
import com.sirh.sirh.repository.Periodos_PagoRepository;
import com.sirh.sirh.repository.Tmp_MovimientosRepository;
import com.sirh.sirh.service.ValesService;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class ValesServiceImpl implements ValesService{
    
    @Autowired
    private Monto_ValesRepository monto_ValesRepository; 
    
    @Autowired
    private Periodos_PagoRepository periodos_PagoRepository; 
    
    @Autowired 
    private Tmp_MovimientosRepository tmp_MovimientosRepository; 
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public List<Monto_Vales> findAllMontos() {
        return monto_ValesRepository.findAll().stream().map(monto_vales -> modelMapper.map(monto_vales, Monto_Vales.class)).collect(Collectors.toList());
    }

    @Override
    public Monto_Vales saveMonto(Monto_Vales monto_vales) {

        Monto_Vales monto = new Monto_Vales();
        
        monto.setDia_inicio(monto_vales.getDia_inicio());
        monto.setDia_fin(monto_vales.getDia_fin());
        monto.setMonto_confianza(monto_vales.getMonto_confianza());
        monto.setMonto_base(monto_vales.getMonto_base());
        
        return monto_ValesRepository.save(monto);
    }
    
    @Override
    public Monto_Vales findMontosVales(Integer dias_trabajados) {
        return monto_ValesRepository.findMontosVales(dias_trabajados);
    }
    
    @Override
    public Monto_Vales findOneMonto(Integer id_monto_vales) {
        return monto_ValesRepository.findById(id_monto_vales).get();
    }
    
    @Override
    public Monto_Vales updateMonto(Monto_Vales monto_vales, Integer id_monto_vales) {
        Monto_Vales m = monto_ValesRepository.findById(id_monto_vales).get();
        m.setDia_inicio(monto_vales.getDia_inicio());
        m.setDia_fin(monto_vales.getDia_fin());
        m.setMonto_confianza(monto_vales.getMonto_confianza());
        m.setMonto_base(monto_vales.getMonto_base());
        return monto_ValesRepository.save(m);
    }
    
    //******************* PERIODOS DE PAGO  ************************************
    @Override
    public List<Periodos_Pago> findPeriodosVales(Integer id_nomina) {
        return periodos_PagoRepository.findPeriodosVales(id_nomina);
    }
    
    

    //******************* TMP_MOVIMIENTOS (2 Cve Nomina - id_Incidencia 40) DIAS LABORADOS EN EL AÑO *********
    @Override
    public Tmp_Movimientos saveMovimientos2(Tmp_Movimientos movimientos) {
        Tmp_Movimientos m = new Tmp_Movimientos();
        LocalDate fecha = LocalDate.now();
        Cat_Incidencias catPercepcion = new Cat_Incidencias();
        catPercepcion.setId_incidencia(40);
        m.setTrabajador(movimientos.getTrabajador());
        m.setCat_Incidencias(catPercepcion);
        m.setAnio_aplicacion(movimientos.getAnio_aplicacion());
        m.setPeriodo_generacion(movimientos.getPeriodo_generacion());
        m.setPeriodo_aplicacion(movimientos.getPeriodo_aplicacion());
        m.setCat_Tipo_Nomina(movimientos.getCat_Tipo_Nomina());
        m.setValor_1(movimientos.getValor_1());
        m.setFecha_creacion(fecha);
        return tmp_MovimientosRepository.save(m);
    }
    //******************* TMP_MOVIMIENTOS (244 Cve Nomina - id_Incidencia 107) TOTAL DE INCIDENCIAS POR AÑO *********
    @Override
    public Tmp_Movimientos saveMovimientos244(Tmp_Movimientos movimientos) {
        Tmp_Movimientos m = new Tmp_Movimientos();
        LocalDate fecha = LocalDate.now();
        Cat_Incidencias catPercepcion = new Cat_Incidencias();
        catPercepcion.setId_incidencia(107);
        m.setTrabajador(movimientos.getTrabajador());
        m.setCat_Incidencias(catPercepcion);
        m.setAnio_aplicacion(movimientos.getAnio_aplicacion());
        m.setPeriodo_generacion(movimientos.getPeriodo_generacion());
        m.setPeriodo_aplicacion(movimientos.getPeriodo_aplicacion());
        m.setCat_Tipo_Nomina(movimientos.getCat_Tipo_Nomina());
        m.setValor_1(movimientos.getValor_1());
        m.setFecha_creacion(fecha);
        return tmp_MovimientosRepository.save(m);
    }
    //******************* TMP_MOVIMIENTOS (249 Cve Nomina - id_Incidencia 110) VALES DE FIN DE AÑO *********
    @Override
    public Tmp_Movimientos saveMovimientos249(Tmp_Movimientos movimientos) {
        Tmp_Movimientos m = new Tmp_Movimientos();
        LocalDate fecha = LocalDate.now();
        Cat_Incidencias catPercepcion = new Cat_Incidencias();
        catPercepcion.setId_incidencia(110);
        m.setTrabajador(movimientos.getTrabajador());
        m.setCat_Incidencias(catPercepcion);
        m.setAnio_aplicacion(movimientos.getAnio_aplicacion());
        m.setPeriodo_generacion(movimientos.getPeriodo_generacion());
        m.setPeriodo_aplicacion(movimientos.getPeriodo_aplicacion());
        m.setCat_Tipo_Nomina(movimientos.getCat_Tipo_Nomina());
        m.setValor_1(movimientos.getValor_1());
        m.setFecha_creacion(fecha);
        
        return tmp_MovimientosRepository.save(m);
    }
}
