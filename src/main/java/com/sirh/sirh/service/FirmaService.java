/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.entity.Trabajador_Firma;

/**
 *
 * @author oguerrero23
 */
public interface FirmaService {
    public Trabajador_Firma existsFirma(Integer trabajador_id);
    
    public Trabajador_Firma updateStatus(Trabajador_Firma trabajador_firma, Integer id);
}
