/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.InformacionPlazasDTO;
import com.sirh.sirh.DTO.Trabajador_PuestoDTO;
import com.sirh.sirh.DTO.Trabajador_SueldoDTO;
import com.sirh.sirh.DTO.Trabajador_plazaDTO;
import com.sirh.sirh.entity.Trabajador_plaza;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rrosero23
 */
public interface PlazaService {

    public List<Trabajador_plazaDTO> findByPuestoAndEstatus(Integer puestoId, Integer estatusPlazaId);

    public List<Trabajador_PuestoDTO> findPlazaTrabajadorByIdTrabajador(Integer trabajador_id);

    public List<Trabajador_plazaDTO> findByPuesto(Integer puestoId);
    
    public List<Trabajador_plazaDTO> findTiposDeContrato(List<Integer> tipoContratoIds);

    public List<Trabajador_plazaDTO> findByTipoContratoIdAndPuestoId(List<Integer> tipoContratoIds, List<Integer> puestoIds);

    public List<Trabajador_plazaDTO> findByPuestoAndEstatusAndTipoDeContrato(Integer puestoId, Integer estatusPlazaId, List<Integer> tipoContratoId);

    public List<InformacionPlazasDTO> findByNumPlaza(Integer numero_plaza);

    public List<InformacionPlazasDTO> findByEstatusPlaza(List<Integer> estatus_plaza_id);

    public List<InformacionPlazasDTO> findByAreaPlaza(Integer id_area);

    public List<InformacionPlazasDTO> findByAreaAndEstatusPlaza(Integer id_area, List<Integer> estatus_plaza_id);

    public Trabajador_SueldoDTO findSueldosTrabajador(Integer id_trabajador);
}
