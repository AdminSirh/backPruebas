/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.entity.Trabajador_Firma;
import com.sirh.sirh.repository.Trabajador_FirmaRepository;
import com.sirh.sirh.service.FirmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author oguerrero23
 */
@Service
@Transactional
public class FirmaServiceImpl implements FirmaService{

    @Autowired
    Trabajador_FirmaRepository trabajador_FirmaRepository;

    @Override
    public Trabajador_Firma existsFirma(Integer trabajador_id) {
        return trabajador_FirmaRepository.existsFirma(trabajador_id);

    }  

    @Override
    public Trabajador_Firma updateStatus(Trabajador_Firma trabajador_firma, Integer id) {
        Trabajador_Firma tf = this.trabajador_FirmaRepository.existsFirma(id);
        tf.setEstatus(0);
        return trabajador_FirmaRepository.save(tf);
    }
}
