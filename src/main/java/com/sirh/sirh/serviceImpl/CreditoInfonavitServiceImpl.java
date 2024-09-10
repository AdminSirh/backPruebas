/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.entity.Cat_Estatus_Credito;
import com.sirh.sirh.entity.Cat_Motivo_Baja;
import com.sirh.sirh.entity.CreditoInfonavit;
import com.sirh.sirh.entity.Historico_Credito_Infonavit;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sirh.sirh.service.CreditoInfonavitService;
import com.sirh.sirh.repository.CreditoInfonavitRepository;
import com.sirh.sirh.repository.Historico_Credito_InfonavitRepository;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author rrosero23
 */
@Service
@Transactional
public class CreditoInfonavitServiceImpl implements CreditoInfonavitService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CreditoInfonavitRepository creditoInfonavitRepository;
    
    @Autowired
    private Historico_Credito_InfonavitRepository historico_Credito_InfonavitRepository;

    @Override
    public CreditoInfonavit findOneCreditoInfonavit(Integer id_credito_infonavit) {
        return creditoInfonavitRepository.findById(id_credito_infonavit).get();
    }

    @Override
    public List<CreditoInfonavit> findAllDatosCreditoInfonavit() {
        return creditoInfonavitRepository.findAll().stream().map(creditoInfonavit -> modelMapper.map(creditoInfonavit, CreditoInfonavit.class)).collect(Collectors.toList());
    }

    @Override
    public CreditoInfonavit saveCreditoInfonavit(CreditoInfonavit creditoInfonavit) {
        return creditoInfonavitRepository.save(creditoInfonavit);
    }

    @Override
    public CreditoInfonavit updateCreditoInfonavit(CreditoInfonavit creditoInfonavit, Integer id) {
        CreditoInfonavit cI = this.creditoInfonavitRepository.findById(id).get();
        cI.setCat_tipo_credito(creditoInfonavit.getCat_tipo_credito());
        cI.setTrabajador(creditoInfonavit.getTrabajador());
        cI.setNumero_de_credito(creditoInfonavit.getNumero_de_credito());
        cI.setDescripcion_tipo_credito(creditoInfonavit.getDescripcion_tipo_credito());
        cI.setFecha_de_recepcion(creditoInfonavit.getFecha_de_recepcion());
        cI.setFecha_de_aplicacion(creditoInfonavit.getFecha_de_aplicacion());
        //cI.setCat_Estatus_Credito(creditoInfonavit.getCat_Estatus_Credito());
        cI.setFecha_movimiento(creditoInfonavit.getFecha_movimiento());
        //cI.setCat_Motivo_Baja(creditoInfonavit.getCat_Motivo_Baja());
        //cI.setFecha_evento(creditoInfonavit.getFecha_evento());
        return creditoInfonavitRepository.save(cI);
    }

    @Override
    public List<CreditoInfonavit> findCreditoInfonavitTrabajador(Integer trabajador_id) {
        return creditoInfonavitRepository.findCreditoInfonavitTrabajador(trabajador_id);
    }
    
    @Override
    public List<Historico_Credito_Infonavit> findCreditoInfonavitHistoricoTrabajador(Integer trabajador_id) {
        return historico_Credito_InfonavitRepository.findCreditoInfonavitHistoricoTrabajador(trabajador_id);
    }

    @Override
    public void updateMotivoBajaId(Integer idCreditoInfonavit, Integer motivoBajaId) {
        Optional<CreditoInfonavit> creditoInfonavitOptional = creditoInfonavitRepository.findById(idCreditoInfonavit);
        if (creditoInfonavitOptional.isPresent()) {
            CreditoInfonavit creditoInfonavit = creditoInfonavitOptional.get();
            Cat_Motivo_Baja motivoBaja = new Cat_Motivo_Baja();
            motivoBaja.setId_motivo_baja(motivoBajaId);
            creditoInfonavit.setCat_Motivo_Baja(motivoBaja);
            creditoInfonavitRepository.save(creditoInfonavit);
        } else {
            throw new EntityNotFoundException("CreditoInfonavit with id " + idCreditoInfonavit + " not found");
        }
    }
    
     @Override
    public void updateEstatusCredito(Integer idCreditoInfonavit, Integer estatusCreditoId) {
        Optional<CreditoInfonavit> creditoInfonavitOptional = creditoInfonavitRepository.findById(idCreditoInfonavit);
        if (creditoInfonavitOptional.isPresent()) {
            CreditoInfonavit creditoInfonavit = creditoInfonavitOptional.get();
            Cat_Estatus_Credito estatusCredito = new Cat_Estatus_Credito();
            estatusCredito.setId_estatus_credito(estatusCreditoId);
            creditoInfonavit.setCat_Estatus_Credito(estatusCredito);
            creditoInfonavitRepository.save(creditoInfonavit);
        } else {
            throw new EntityNotFoundException("CreditoInfonavit with id " + idCreditoInfonavit + " not found");
        }
    }
    
    @Override
    public CreditoInfonavit updateFechaEventoAndEstatusCredito(CreditoInfonavit creditoInfonavit, Integer id) {
        CreditoInfonavit cI = this.creditoInfonavitRepository.findById(id).get();
        //Agregar Fecha Evento
        cI.setFecha_evento(creditoInfonavit.getFecha_evento());
        //Modificar el estatus del crédito
        cI.setCat_Estatus_Credito(creditoInfonavit.getCat_Estatus_Credito());
        return creditoInfonavitRepository.save(cI);
    }

}
