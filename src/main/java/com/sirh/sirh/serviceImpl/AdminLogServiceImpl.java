/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.AdminLogDTO;
import com.sirh.sirh.entity.AdminLog;
import com.sirh.sirh.repository.AdminLogRepository;
import com.sirh.sirh.service.AdminLogService;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jarellano22
 */
@Service
@Transactional
public class AdminLogServiceImpl implements AdminLogService {

    @Autowired
    AdminLogRepository adminLogRepository;

    @Override
    public List<AdminLog> findAllB(Date desde, Date hasta) {
        return adminLogRepository.findAllB(desde, hasta);
    }

    @Override
    public List<AdminLogDTO> searchOperationByDate(String operacion, Date fechaInicio, Date fechaFin) {
        return adminLogRepository.searchOperationByDate(operacion, fechaInicio, fechaFin);
    }
}
