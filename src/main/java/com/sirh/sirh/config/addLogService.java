/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.config;

import com.sirh.sirh.entity.AdminLog;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jarellano22
 */
@Service
@Transactional
public class addLogService implements LogService {

    @Autowired
    private addLogRepository AddLogRepository;

    @Override
    public AdminLog save(AdminLog adminlog) {
        return AddLogRepository.save(adminlog);
    }
}
