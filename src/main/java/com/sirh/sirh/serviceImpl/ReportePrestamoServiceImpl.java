/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.ReportePersonaDTO;
import com.sirh.sirh.service.ReportePrestamoService;
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
 * @author oguerrero23
 */
@Service
@Transactional
public class ReportePrestamoServiceImpl implements ReportePrestamoService{
    @Autowired(required = false)
    private JasperReportManager reportManager = new JasperReportManager();

    @Autowired(required = false)
    private DataSource dataSource;
    String  basePath = "src/main/resources/static/";

    File directorio = new File(basePath);
    
    public void rutas() {
        if (!(directorio.exists() && directorio.isDirectory())) {
            basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        }
    }
    
    //*********************** PRESTAMO FONACOT*********************************
    @Override
    public ReportePersonaDTO obtenerHistoricoFonacot(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "PrestamosFonacotExpediente";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        // Carga de las imágenes
        String relativePath1 = "imagenesReportes/cdmx.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        params.put("Img", absolutePath1);

        String relativePath2 = "imagenesReportes/Leyenda.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("imgNomina", absolutePath2);

        params.put("expediente", (String) params.get("expediente"));

        // Convertir "periodo" a Integer
        String periodoStr = (String) params.get("periodo");
        if (periodoStr != null) {
            Integer periodoInt = Integer.parseInt(periodoStr);
            params.put("periodo", periodoInt);
        }

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }
    
    //*********************** PRESTAMO PERSONAL*********************************

    @Override
    public ReportePersonaDTO obtenerFormatoPrestamoC(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "SolicitudPrestamoPersonalC";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        // Carga de las imágenes
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath1 = "src/main/resources/static/";
        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        params.put("img", absolutePath1);

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerFormatoPrestamoF(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "SolicitudPrestamoPersonalF2";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        // Carga de las imágenes
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath1 = "src/main/resources/static/";
        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        params.put("img", absolutePath1);

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerFormatoPrestamoB(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "SolicitudPrestamoPersonalB";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        // Carga de las imágenes
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath1 = "src/main/resources/static/";
        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        params.put("Img", absolutePath1);

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerDepositoDiferentesBancos(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "DiferentesBancosPrestamoPersonal";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        // Carga de las imágenes
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath1 = "src/main/resources/static/";
        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        params.put("cdmx", absolutePath1);

        String relativePath2 = "imagenesReportes/Leyenda.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("leyenda", absolutePath2);

        params.put("nomina", (String) params.get("nomina"));

        // Convertir "periodo" a Integer
        String periodoStr = (String) params.get("periodo");
        if (periodoStr != null) {
            Integer periodoInt = Integer.parseInt(periodoStr);
            params.put("periodo", periodoInt);
        }
        // Convertir "id_nomina" a Integer
        String id_nominaStr = (String) params.get("id_nomina");
        if (id_nominaStr != null) {
            Integer id_nominaInt = Integer.parseInt(id_nominaStr);
            params.put("id_nomina", id_nominaInt);
        }

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerDiferentesBancosExcel(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "DiferentesBancosExcel";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        // Carga de las imágenes
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath1 = "src/main/resources/static/";
        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        params.put("cdmx", absolutePath1);

        String relativePath2 = "imagenesReportes/Leyenda.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("leyenda", absolutePath2);

        params.put("nomina", (String) params.get("nomina"));

        // Convertir "periodo" a Integer
        String periodoStr = (String) params.get("periodo");
        if (periodoStr != null) {
            Integer periodoInt = Integer.parseInt(periodoStr);
            params.put("periodo", periodoInt);
        }
        // Convertir "id_nomina" a Integer
        String id_nominaStr = (String) params.get("id_nomina");
        if (id_nominaStr != null) {
            Integer id_nominaInt = Integer.parseInt(id_nominaStr);
            params.put("id_nomina", id_nominaInt);
        }

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerDepositoBanorte(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "DepositoBanortePrestamosPersonales";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        // Carga de las imágenes
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath1 = "src/main/resources/static/";
        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        params.put("cdmx", absolutePath1);

        String relativePath2 = "imagenesReportes/Leyenda.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("leyenda", absolutePath2);

        params.put("nomina", (String) params.get("nomina"));

        // Convertir "periodo" a Integer
        String periodoStr = (String) params.get("periodo");
        if (periodoStr != null) {
            Integer periodoInt = Integer.parseInt(periodoStr);
            params.put("periodo", periodoInt);
        }
        // Convertir "id_nomina" a Integer
        String id_nominaStr = (String) params.get("id_nomina");
        if (id_nominaStr != null) {
            Integer id_nominaInt = Integer.parseInt(id_nominaStr);
            params.put("id_nomina", id_nominaInt);
        }

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }
    
    @Override
    public ReportePersonaDTO obtenerHistoricoBanorte(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "PrestamosPersonalesExpediente";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        // Carga de las imágenes
        String relativePath1 = "imagenesReportes/cdmx.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        params.put("Img", absolutePath1);

        String relativePath2 = "imagenesReportes/Leyenda.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("imgNomina", absolutePath2);

        params.put("expediente", (String) params.get("expediente"));

        // Convertir "periodo" a Integer
        String periodoStr = (String) params.get("periodo");
        if (periodoStr != null) {
            Integer periodoInt = Integer.parseInt(periodoStr);
            params.put("periodo", periodoInt);
        }

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }
    
    
}
