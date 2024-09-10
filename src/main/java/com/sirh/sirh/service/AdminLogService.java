/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

import com.sirh.sirh.DTO.AdminLogDTO;
import com.sirh.sirh.entity.AdminLog;
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
