/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.InformacionPlazasDTO;
import com.sirh.sirh.DTO.Trabajador_PuestoDTO;
import com.sirh.sirh.DTO.Trabajador_SueldoDTO;
import com.sirh.sirh.DTO.Trabajador_plazaDTO;
import com.sirh.sirh.entity.Trabajador_plaza;
import com.sirh.sirh.repository.PlazaRepository;
import com.sirh.sirh.service.PlazaService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rrosero23
 */
@Service
@Transactional
public class PlazaServiceImpl implements PlazaService {

    @Autowired
    PlazaRepository plazaRepository;

    @Override
    public List<Trabajador_plazaDTO> findByPuestoAndEstatus(Integer puestoId, Integer estatusPlazaId) {
        return plazaRepository.findByPuestoAndEstatus(puestoId, estatusPlazaId);
    }

    @Override
    public List<Trabajador_PuestoDTO> findPlazaTrabajadorByIdTrabajador(Integer trabajador_id) {
        return plazaRepository.findPlazaTrabajadorByIdTrabajador(trabajador_id);
    }

    @Override
    public List<Trabajador_plazaDTO> findByPuesto(Integer puestoId) {
        return plazaRepository.findByPuesto(puestoId);
    }

    @Override
    public List<Trabajador_plazaDTO> findTiposDeContrato(List<Integer> tipoContratoIds) {
        return plazaRepository.findTiposDeContrato(tipoContratoIds);
    }

    @Override
    public List<Trabajador_plazaDTO> findByTipoContratoIdAndPuestoId(List<Integer> tipoContratoIds, List<Integer> puestoIds) {
        return plazaRepository.findByTipoContratoIdAndPuestoId(tipoContratoIds, puestoIds);
    }

    @Override
    public List<Trabajador_plazaDTO> findByPuestoAndEstatusAndTipoDeContrato(Integer puestoId, Integer estatusPlazaId, List<Integer> tipoContratoId) {
        return plazaRepository.findByPuestoAndEstatusAndTipoDeContrato(puestoId, estatusPlazaId, tipoContratoId);
    }

    @Override
    public List<InformacionPlazasDTO> findByNumPlaza(Integer numero_plaza) {
        return plazaRepository.findByNumPlaza(numero_plaza);
    }

    @Override
    public List<InformacionPlazasDTO> findByEstatusPlaza(List<Integer> estatus_plaza_id) {
        return plazaRepository.findByEstatusPlaza(estatus_plaza_id);
    }

    @Override
    public List<InformacionPlazasDTO> findByAreaPlaza(Integer id_area) {
        return plazaRepository.findByAreaPlaza(id_area);
    }

    @Override
    public List<InformacionPlazasDTO> findByAreaAndEstatusPlaza(Integer id_area, List<Integer> estatus_plaza_id) {
        return plazaRepository.findByAreaAndEstatusPlaza(id_area, estatus_plaza_id);
    }

    @Override
    public Trabajador_SueldoDTO findSueldosTrabajador(Integer id_trabajador) {
        return plazaRepository.findSueldosTrabajador(id_trabajador);
    }

 
} 
