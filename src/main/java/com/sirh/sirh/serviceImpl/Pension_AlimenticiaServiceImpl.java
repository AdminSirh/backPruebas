/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.Pension_AlimenticiaDTO;
import com.sirh.sirh.entity.HistoricoPensionesAlimenticias;
import com.sirh.sirh.entity.Pension_Alimenticia_B;
import com.sirh.sirh.entity.Pension_Alimenticia_Montos;
import com.sirh.sirh.entity.Pension_Alimenticia_OJ;
import com.sirh.sirh.repository.HistoricoPensionesAlimenticiasRepository;
import com.sirh.sirh.repository.Pension_Alimenticia_BRepository;
import com.sirh.sirh.repository.Pension_Alimenticia_MontosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sirh.sirh.service.Pension_AlimenticiaService;
import com.sirh.sirh.repository.Pension_Alimenticia_OJRepository;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

/**
 *
 * @author rrosero23
 */
@Service
@Transactional
public class Pension_AlimenticiaServiceImpl implements Pension_AlimenticiaService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Pension_Alimenticia_OJRepository pension_AlimenticiaRepository;

    @Autowired
    private Pension_Alimenticia_BRepository pension_Alimenticia_BRepository;

    @Autowired
    private Pension_Alimenticia_MontosRepository pension_Alimenticia_MontosRepository;

    @Autowired
    private HistoricoPensionesAlimenticiasRepository historicoPensionesAlimenticiasRepository;

    //Guarda en las tablas relacionadas de la pensión alimenticia, la principal, orden judicial, beneficiarios y montos
    @Override
    public Pension_Alimenticia_OJ savePensionAlimenticia(Pension_Alimenticia_OJ pension_Alimenticia_OJ) {
        // Pensión alimenticia orden judicial que es la tabla principal
        Pension_Alimenticia_OJ tablaPrincipal = new Pension_Alimenticia_OJ();
        tablaPrincipal.setTrabajador_id(pension_Alimenticia_OJ.getTrabajador_id());
        tablaPrincipal.setPorcentaje(pension_Alimenticia_OJ.getPorcentaje());
        tablaPrincipal.setModalidad(pension_Alimenticia_OJ.getModalidad());
        tablaPrincipal.setJuzgado(pension_Alimenticia_OJ.getJuzgado());
        tablaPrincipal.setFecha_recepcion(pension_Alimenticia_OJ.getFecha_recepcion());
        tablaPrincipal.setCuota_fija(pension_Alimenticia_OJ.getCuota_fija());
        tablaPrincipal.setExpediente_caso(pension_Alimenticia_OJ.getExpediente_caso());
        tablaPrincipal.setReferencia(pension_Alimenticia_OJ.getReferencia());
        tablaPrincipal.setOficio(pension_Alimenticia_OJ.getOficio());
        tablaPrincipal.setPeriodo_aplicacion(pension_Alimenticia_OJ.getPeriodo_aplicacion());
        tablaPrincipal.setEstatus(Boolean.TRUE);
        // ***************************DATOS DEL BENEFICIARIO**************************
        Pension_Alimenticia_B tablaBeneficiarios = new Pension_Alimenticia_B();
        tablaBeneficiarios.setNombre(pension_Alimenticia_OJ.getPension_Alimenticia_B().getNombre());
        tablaBeneficiarios.setApellido_paterno(pension_Alimenticia_OJ.getPension_Alimenticia_B().getApellido_paterno());
        tablaBeneficiarios.setApellido_materno(pension_Alimenticia_OJ.getPension_Alimenticia_B().getApellido_materno());
        tablaBeneficiarios.setRfc(pension_Alimenticia_OJ.getPension_Alimenticia_B().getRfc());
        tablaBeneficiarios.setCalle(pension_Alimenticia_OJ.getPension_Alimenticia_B().getCalle());
        tablaBeneficiarios.setNumero_oficial(pension_Alimenticia_OJ.getPension_Alimenticia_B().getNumero_oficial());
        tablaBeneficiarios.setCat_Colonia(pension_Alimenticia_OJ.getPension_Alimenticia_B().getCat_Colonia());
        tablaBeneficiarios.setCat_Banco(pension_Alimenticia_OJ.getPension_Alimenticia_B().getCat_Banco());
        tablaBeneficiarios.setCuentabeneficiario(pension_Alimenticia_OJ.getPension_Alimenticia_B().getCuentabeneficiario());
        // Guarda la instancia de beneficiarios en la base de datos
        tablaBeneficiarios = pension_Alimenticia_BRepository.save(tablaBeneficiarios);
        // Asigna el ID recién generado a la relación en la tabla principal
        tablaPrincipal.setPension_Alimenticia_B(tablaBeneficiarios);
        // **************************DATOS DE LOS MONTOS***********************************
        Pension_Alimenticia_Montos tablaMontos = new Pension_Alimenticia_Montos();
        tablaMontos.setId_fdo_ahorro(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getId_fdo_ahorro());
        tablaMontos.setFdo_trabajador(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getFdo_trabajador());
        tablaMontos.setFdo_empresa(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getFdo_empresa());
        tablaMontos.setFdo_interes(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getFdo_interes());
        tablaMontos.setDescuento(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getDescuento());
        tablaMontos.setApl_nomina(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getApl_nomina());
        tablaMontos.setApl_finiq(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getApl_finiq());
        tablaMontos.setVales_fin_anio(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getVales_fin_anio());
        tablaMontos.setId_deposito_bancario(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getId_deposito_bancario());
        tablaMontos.setPorcentaje_descuent(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getPorcentaje_descuent());
        tablaMontos.setPeriodo_gen(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getPeriodo_gen());
        tablaMontos.setAnualidad(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getAnualidad());
        tablaMontos.setPago_descuento(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getPago_descuento());
        // Guarda la instancia de montos en la base de datos
        tablaMontos = pension_Alimenticia_MontosRepository.save(tablaMontos);
        // Asigna el ID recién generado a la relación en la tabla principal
        tablaPrincipal.setPension_Alimenticia_Montos(tablaMontos);
        // Se guarda la instancia de orden judicial en la base de datos con las claves foráneas generadas
        return pension_AlimenticiaRepository.save(tablaPrincipal);
    }

    @Override
    public List<Pension_Alimenticia_OJ> findAllPensiones() {
        return pension_AlimenticiaRepository.findAll().stream().map(pension_Alimenticia_OJ -> modelMapper.map(pension_Alimenticia_OJ, Pension_Alimenticia_OJ.class)).collect(Collectors.toList());
    }

//    @Override
//    public List<Pension_Alimenticia_OJ> finAllPensionesActivas() {
//        return pension_AlimenticiaRepository.finAllPensionesActivas();
//    }
    @Override
    public List<Pension_AlimenticiaDTO> finAllPensionesActivas() {
        return pension_AlimenticiaRepository.finAllPensionesActivas();
    }

    @Override
    public Integer cuentaPensionesTrabajador(Integer trabajador_id) {
        return pension_AlimenticiaRepository.cuentaPensionesTrabajador(trabajador_id);
    }

    @Override
    public List<Pension_Alimenticia_OJ> findTrabajadorOrdenJudicial(Integer trabajador_id) {
        return pension_AlimenticiaRepository.findTrabajadorOrdenJudicial(trabajador_id);
    }

    @Override
    public Pension_Alimenticia_OJ actualizarEstatus(Integer idPension) {
        Pension_Alimenticia_OJ pa = pension_AlimenticiaRepository.findById(idPension).get();
        pa.setEstatus(false);
        pa.setFecha_baja_pension(LocalDate.now());
        return pension_AlimenticiaRepository.save(pa);
    }

    @Override
    public Pension_Alimenticia_OJ updatePensionAlimenticia(Pension_Alimenticia_OJ pension_Alimenticia_OJ, Integer id_pension) {
        Pension_Alimenticia_OJ p = pension_AlimenticiaRepository.findById(id_pension).get();
        Pension_Alimenticia_OJ pa = p;
        pa.setTrabajador_id(pension_Alimenticia_OJ.getTrabajador_id());
        pa.setPorcentaje(pension_Alimenticia_OJ.getPorcentaje());
        pa.setModalidad(pension_Alimenticia_OJ.getModalidad());
        pa.setJuzgado(pension_Alimenticia_OJ.getJuzgado());
        pa.setFecha_recepcion(pension_Alimenticia_OJ.getFecha_recepcion());
        pa.setFecha_movimiento(LocalDate.now());
        pa.setCuota_fija(pension_Alimenticia_OJ.getCuota_fija());
        pa.setExpediente_caso(pension_Alimenticia_OJ.getExpediente_caso());
        pa.setReferencia(pension_Alimenticia_OJ.getReferencia());
        pa.setOficio(pension_Alimenticia_OJ.getOficio());
        pa.setPeriodo_aplicacion(pension_Alimenticia_OJ.getPeriodo_aplicacion());
        pa.setEstatus(Boolean.TRUE);

        //Setter y Getter Beneficiario
        Pension_Alimenticia_B pensionAlimenticiaB = pa.getPension_Alimenticia_B();
        pensionAlimenticiaB.setNombre(pension_Alimenticia_OJ.getPension_Alimenticia_B().getNombre());
        pensionAlimenticiaB.setApellido_paterno(pension_Alimenticia_OJ.getPension_Alimenticia_B().getApellido_paterno());
        pensionAlimenticiaB.setApellido_materno(pension_Alimenticia_OJ.getPension_Alimenticia_B().getApellido_materno());
        pensionAlimenticiaB.setRfc(pension_Alimenticia_OJ.getPension_Alimenticia_B().getRfc());
        pensionAlimenticiaB.setCalle(pension_Alimenticia_OJ.getPension_Alimenticia_B().getCalle());
        pensionAlimenticiaB.setNumero_oficial(pension_Alimenticia_OJ.getPension_Alimenticia_B().getNumero_oficial());
        pensionAlimenticiaB.setCat_Colonia(pension_Alimenticia_OJ.getPension_Alimenticia_B().getCat_Colonia());
        pensionAlimenticiaB.setCat_Banco(pension_Alimenticia_OJ.getPension_Alimenticia_B().getCat_Banco());
        pensionAlimenticiaB.setCuentabeneficiario(pension_Alimenticia_OJ.getPension_Alimenticia_B().getCuentabeneficiario());

        //Setter y Getter Montos
        Pension_Alimenticia_Montos pension_Alimenticia_Montos = pa.getPension_Alimenticia_Montos();
        pension_Alimenticia_Montos.setId_fdo_ahorro(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getId_fdo_ahorro());
        pension_Alimenticia_Montos.setFdo_trabajador(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getFdo_trabajador());
        pension_Alimenticia_Montos.setFdo_empresa(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getFdo_empresa());
        pension_Alimenticia_Montos.setFdo_interes(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getFdo_interes());
        pension_Alimenticia_Montos.setDescuento(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getDescuento());
        pension_Alimenticia_Montos.setApl_nomina(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getApl_nomina());
        pension_Alimenticia_Montos.setApl_finiq(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getApl_finiq());
        pension_Alimenticia_Montos.setVales_fin_anio(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getVales_fin_anio());
        pension_Alimenticia_Montos.setId_deposito_bancario(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getId_deposito_bancario());
        pension_Alimenticia_Montos.setAnualidad(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getAnualidad());
        pension_Alimenticia_Montos.setPago_descuento(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getPago_descuento());
        pension_Alimenticia_Montos.setPorcentaje_descuent(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getPorcentaje_descuent());
        pension_Alimenticia_Montos.setPeriodo_gen(pension_Alimenticia_OJ.getPension_Alimenticia_Montos().getPeriodo_gen());
        return pension_AlimenticiaRepository.save(pa);
    }

    @Override
    public List<Pension_Alimenticia_OJ> findTrabajadorPensionA(Integer id_pension) {
        return pension_AlimenticiaRepository.findTrabajadorPensionA(id_pension);
    }

    @Override
    public List<HistoricoPensionesAlimenticias> findHistoricoPensAlim(Integer id_pension_historico) {
        return historicoPensionesAlimenticiasRepository.findHistoricoPensAlim(id_pension_historico);
    }

    @Override
    public List<HistoricoPensionesAlimenticias> findTrabajadorPensionAHistorico(Integer id_trabajador, Date desde, Date hasta) {
        return historicoPensionesAlimenticiasRepository.findTrabajadorPensionAHistorico(id_trabajador, desde, hasta);
    }

    @Override
    public List<Pension_Alimenticia_OJ> findTrabajadorOrdenJudicialHistoricoInactivo(Integer trabajador_id) {
        return pension_AlimenticiaRepository.findTrabajadorOrdenJudicialHistoricoInactivo(trabajador_id);
    }

    /**
     * *****************BENEFICARIO
     *
     ******************************
     * @param trabajador_id
     * @return
     */
    @Override
    public List<Pension_Alimenticia_B> findTrabajadorBeneficiario(Integer trabajador_id) {
        return pension_Alimenticia_BRepository.findTrabajadorBeneficiario(trabajador_id);
    }

    /**
     * *****************MONTOS
     *
     ******************************
     * @param trabajador_id
     * @return
     */
    @Override
    public List<Pension_Alimenticia_Montos> findTrabajadorMontos(Integer trabajador_id) {
        return pension_Alimenticia_MontosRepository.findTrabajadorMontos(trabajador_id);
    }

}
