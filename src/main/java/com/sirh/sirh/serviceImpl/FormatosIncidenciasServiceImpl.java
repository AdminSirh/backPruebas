/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.ReportePersonaDTO;
import com.sirh.sirh.service.FormatosIncidenciasService;
import com.sirh.sirh.util.TipoReporteEnum;
import jasperCommons.JasperReportManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rrosero23
 */
@Service
@Transactional
public class FormatosIncidenciasServiceImpl implements FormatosIncidenciasService {

    @Autowired(required = false)
    private JasperReportManager reportManager = new JasperReportManager();

    @Autowired(required = false)
    private DataSource dataSource;

    @Override
    public ReportePersonaDTO autorizacionVacaciones(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "AutorizacionVacaciones";
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        //Parametros necesarios para generar el reporte
        params.put("incidencias_id", Integer.valueOf((String) params.get("incidencias_id")));
        //Carga de imágenes
        String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImgLogoGob", absolutePath);
        //Segunda imagen
        String relativePath2 = "imagenesReportes/Logo_Dependencia.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("urlImgDependencia", absolutePath2);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

}
