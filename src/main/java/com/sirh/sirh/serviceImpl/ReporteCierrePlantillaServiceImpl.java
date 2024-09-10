/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.ReportePersonaDTO;
import com.sirh.sirh.service.ReporteCierrePlantillaService;
import com.sirh.sirh.util.TipoReporteEnum;
import jasperCommons.JasperReportManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.transaction.annotation.Transactional;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author rrosero23
 */
@Service
@Transactional
public class ReporteCierrePlantillaServiceImpl implements ReporteCierrePlantillaService {

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
    @Override
    public ReportePersonaDTO obtenerMovimientosPlazasMes(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMovimientosMesR1";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        params.put("mes", Integer.valueOf((String) params.get("mes")));
        //Ordenamiento del reporte
        params.put("orden", params.get("orden").toString());

        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_Dependencia.png";
        
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerResumenGeneroeIngresos(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "EstadisticaGeneroYEdad_xNivelDeIngresosR3";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        params.put("mes", Integer.valueOf((String) params.get("mes")));

        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO reporteCierrePlantilla(Map<String, Object> params) throws JRException, IOException, SQLException {
        rutas();
        String fileName = "ReportePlantillaPersonal(cierreMensual)R4";
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        params.put("mes", Integer.valueOf((String) params.get("mes")));

        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath);
        params.put("urlImg", absolutePath);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO reporteCierrePlantillaSubI(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "Sub_I_R4";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        params.put("mes", Integer.valueOf((String) params.get("mes")));

        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO reporteCierrePlantillaSubII(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "Sub_II_R4";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        params.put("mes", Integer.valueOf((String) params.get("mes")));

        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO reporteCierrePlantillaSubIII(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "Sub_III_R4";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        params.put("mes", Integer.valueOf((String) params.get("mes")));

        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO reportePuestoGenero(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReportePuestoGeneroR5";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        params.put("mes", Integer.valueOf((String) params.get("mes")));

        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath);
        params.put("urlImg", absolutePath);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO reporteGeneroXRangoEdades(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "EstadisticaGeneroXRangoEdadesR6";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        params.put("mes", Integer.valueOf((String) params.get("mes")));

        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO reporteGeneroXEdades(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "EstadisticaGeneroXEdadesR7";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        params.put("mes", Integer.valueOf((String) params.get("mes")));

        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }
    
    @Override
    public ReportePersonaDTO reportePlazasOcupadasPorExpediente(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "reporteMensualDePlazasOcupadasPorExpediente";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        String numero_expediente = (String) params.get("numero_expediente");
        params.put("numero_expediente", numero_expediente);
        params.put("fecha_inicio", java.sql.Date.valueOf((String) params.get("fecha_inicio")));
        params.put("fecha_fin", java.sql.Date.valueOf((String) params.get("fecha_fin")));

        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_Dependencia.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO reportePorGeneroYGradoDeEstudios(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "reportePorGeneroYGradoMaximoDeEstudios";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_Dependencia.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO reporteMovimientosRegistradosPlantilla(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "MovimientosRegistradosEnPlantilla";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        String mesString = (String) params.get("mes");
        Integer mes = Integer.parseInt(mesString);
        params.put("mes", mes);
        String yearString = (String) params.get("year");
        Integer year = Integer.parseInt(yearString);
        params.put("year", year);
        
        //carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_Dependencia.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);
        
        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))){
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

}