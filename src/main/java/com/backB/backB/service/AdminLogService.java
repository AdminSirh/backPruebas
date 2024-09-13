/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.backB.backB.service;

import com.backB.backB.DTO.AdminLogDTO;
import com.backB.backB.entity.AdminLog;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jarellano22
 */
public interface AdminLogService {

    /*LISTAR Movimientos del Usuario*/
    public List<AdminLog> findAllB(Date desde, Date hasta);

    public List<AdminLogDTO> searchOperationByDate(String operacion, Date fechaInicio, Date fechaFin);
}
