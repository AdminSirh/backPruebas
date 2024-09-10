/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.Pension_AlimenticiaDTO;
import com.sirh.sirh.entity.HistoricoPensionesAlimenticias;
import com.sirh.sirh.entity.Pension_Alimenticia_B;
import com.sirh.sirh.entity.Pension_Alimenticia_Montos;
import com.sirh.sirh.entity.Pension_Alimenticia_OJ;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rrosero23
 */
public interface Pension_AlimenticiaService {

    public Pension_Alimenticia_OJ savePensionAlimenticia(Pension_Alimenticia_OJ pension_Alimenticia_OJ);

    /**
     * *****************ORDEN JUDICIAL******************************
     */
    public List<Pension_Alimenticia_OJ> findAllPensiones();

    public List<Pension_AlimenticiaDTO> finAllPensionesActivas();

    public Integer cuentaPensionesTrabajador(Integer trabajador_id);

    public List<Pension_Alimenticia_OJ> findTrabajadorOrdenJudicial(Integer trabajador_id);

    public Pension_Alimenticia_OJ actualizarEstatus(Integer idPension);

    public Pension_Alimenticia_OJ updatePensionAlimenticia(Pension_Alimenticia_OJ pension_Alimenticia_OJ, Integer id_pension);

    public List<Pension_Alimenticia_OJ> findTrabajadorPensionA(Integer id_pension);

    List<HistoricoPensionesAlimenticias> findHistoricoPensAlim(Integer id_pension_historico);

    List<HistoricoPensionesAlimenticias> findTrabajadorPensionAHistorico(Integer id_trabajador, Date desde, Date hasta);

    public List<Pension_Alimenticia_OJ> findTrabajadorOrdenJudicialHistoricoInactivo(Integer trabajador_id);

    /**
     * *****************BENEFICARIO******************************
     */
    public List<Pension_Alimenticia_B> findTrabajadorBeneficiario(Integer trabajador_id);

    /**
     * *****************MONTOS******************************
     */
    public List<Pension_Alimenticia_Montos> findTrabajadorMontos(Integer trabajador_id);

}
