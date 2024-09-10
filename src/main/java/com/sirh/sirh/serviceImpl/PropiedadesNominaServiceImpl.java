/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.entity.Propiedades_Nomina;
import com.sirh.sirh.repository.Propiedades_NominaRepository;
import com.sirh.sirh.service.PropiedadesNominaService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rroscero23
 */
@Service
@Transactional
public class PropiedadesNominaServiceImpl implements PropiedadesNominaService {

    @Autowired
    private Propiedades_NominaRepository propiedades_NominaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Propiedades_Nomina> findAllPropiedadesNomina() {
        return propiedades_NominaRepository.findAll().stream().map(propiedades_Nomina -> modelMapper.map(propiedades_Nomina, Propiedades_Nomina.class)).collect(Collectors.toList());
    }

    @Override
    public List<Propiedades_Nomina> findPropiedadesNomina(Integer nomina_id) {
        return propiedades_NominaRepository.findPropiedadesNomina(nomina_id);
    }

    @Override
    public Propiedades_Nomina updatePropiedadNomina(Propiedades_Nomina propiedadesNomina, Integer id_propiedad) {
        Propiedades_Nomina pN = propiedades_NominaRepository.findById(id_propiedad).get();
        LocalDate fechaMovimiento = LocalDate.now();
        pN.setValor(propiedadesNomina.getValor());
        pN.setFecha_movimiento(fechaMovimiento);
        return propiedades_NominaRepository.save(pN);
    }

    @Override
    public Propiedades_Nomina findOnePropiedad(Integer id_propiedad) {
        return propiedades_NominaRepository.findById(id_propiedad).get();
    }

    @Override
    public Double findSalMinFiniquito(Integer id_nomina) {
        return propiedades_NominaRepository.findSalMinFiniquito(id_nomina);
    }

    @Override
    public Double findSalMinimo(Integer id_nomina) {
        return propiedades_NominaRepository.findSalMinimo(id_nomina);
    }

}
