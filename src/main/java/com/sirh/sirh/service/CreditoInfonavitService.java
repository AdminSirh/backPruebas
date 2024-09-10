/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.entity.CreditoInfonavit;
import com.sirh.sirh.entity.Historico_Credito_Infonavit;
import java.util.List;

/**
 *
 * @author rrosero23
 */
public interface CreditoInfonavitService {

    public CreditoInfonavit findOneCreditoInfonavit(Integer id_credito_infonavit);

    public List<CreditoInfonavit> findAllDatosCreditoInfonavit(); //Listar todos los datos

    public CreditoInfonavit saveCreditoInfonavit(CreditoInfonavit creditoInfonavit);

    public CreditoInfonavit updateCreditoInfonavit(CreditoInfonavit creditoInfonavit, Integer id);

    public List<CreditoInfonavit> findCreditoInfonavitTrabajador(Integer trabajador_id);
    
    public List<Historico_Credito_Infonavit> findCreditoInfonavitHistoricoTrabajador(Integer trabajador_id);

    public void updateMotivoBajaId(Integer idCreditoInfonavit, Integer motivoBajaId);
    
    public void updateEstatusCredito(Integer idCreditoInfonavit, Integer estatusCreditoId);
    
    public CreditoInfonavit updateFechaEventoAndEstatusCredito (CreditoInfonavit creditoInfonavit, Integer id);
    

}
