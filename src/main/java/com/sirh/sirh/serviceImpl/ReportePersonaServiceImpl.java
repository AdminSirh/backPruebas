package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.ReportePersonaDTO;
import com.sirh.sirh.service.ReportePersonaService;
import com.sirh.sirh.util.TipoReporteEnum;
import jasperCommons.JasperReportManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.transaction.annotation.Transactional;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author nreyes22
 */
@Service
@Transactional
public class ReportePersonaServiceImpl implements ReportePersonaService {

    @Autowired(required = false)
    private JasperReportManager reportManager = new JasperReportManager();

    @Autowired(required = false)
    private DataSource dataSource;

    String basePath = "src/main/resources/static/";

    File directorio = new File(basePath);

    public void rutas() {
        if (!(directorio.exists() && directorio.isDirectory())) {
            basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        }
    }

    @Override
    public ReportePersonaDTO obtenerReportePersona(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "reporteSIRH_FechaCaptura";

        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        params.put("fecha_captura", Date.valueOf((String) params.get("fecha_captura"))); // Cambia de String a formato fecha 

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, dataSource.getConnection());
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    /*=============================REPORTE MONITOR PLAZA ====================================== 
    
     * **********************************************************
     * ALFABETICAMENTE
     **********************************************************
     */
    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasAlfabetPuesto(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasPuestoAlfabet";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        params.put("id_puesto", Integer.valueOf((String) params.get("id_puesto")));
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
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasAlfabetTipoContrato(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasTiposContratoAlfabet";
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
        String tipoContratoIdsString = (String) params.get("tipo_contrato_id");

        //Se guarda en una colección de enteros realizando la separación por comas
        Collection<Integer> tipoContratoIds = Arrays.stream(tipoContratoIdsString.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        //Se mandan los parámetros al servidor ya con el tipo colection que requiere el reporte de Jasper
        params.put("tipo_contrato_id", tipoContratoIds);

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasAlfabetPuestoYTipoContrato(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasPuestoContratoAlfabet";
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

        //Se guarda la cadena de tipo de contratos proveniente del params.get
        String tipoContratoIdsString = (String) params.get("tipo_contrato_id");
        //Se guarda en una colección de enteros realizando la separación por comas
        Collection<Integer> tipoContratoIds = Arrays.stream(tipoContratoIdsString.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        //Se mandan los parámetros al servidor correspondientes para id_puesto
        params.put("id_puesto", Integer.valueOf((String) params.get("id_puesto")));
        //Se mandan los parámetros al servidor ya con el tipo colection que requiere el reporte de Jasper
        params.put("tipo_contrato_id", tipoContratoIds);

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasAlfabetPuestoYEstatus(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasPuestoEstatusAlfabet";
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

        params.put("id_puesto", Integer.valueOf((String) params.get("id_puesto")));
        params.put("estatus_plaza_id", Integer.valueOf((String) params.get("estatus_plaza_id")));

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasAlfabetPuestoTipoContratoYEstatus(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasPuestoEstatusTipoContratoAlfabet";
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

        params.put("id_puesto", Integer.valueOf((String) params.get("id_puesto")));
        //Se guarda la cadena de tipo de contratos proveniente del params.get
        String tipoContratoIdsString = (String) params.get("tipo_contrato_id");
        //Se guarda en una colección de enteros realizando la separación por comas
        Collection<Integer> tipoContratoIds = Arrays.stream(tipoContratoIdsString.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        //Se mandan los parámetros al servidor ya con el tipo colection que requiere el reporte de Jasper
        params.put("tipo_contrato_id", tipoContratoIds);
        params.put("estatus_plaza_id", Integer.valueOf((String) params.get("estatus_plaza_id")));

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    /**
     * *********************************************************
     * ORGANIGRAMA *********************************************************
     */
    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasOrganigramaPuesto(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasPuestoOrganigrama";
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

        params.put("id_puesto", Integer.valueOf((String) params.get("id_puesto")));

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasOrganigramaTipoContrato(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasTiposContratoOrganigrama";
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

        //Se guarda la cadena de tipo de contratos proveniente del params.get
        String tipoContratoIdsString = (String) params.get("tipo_contrato_id");
        //Se guarda en una colección de enteros realizando la separación por comas
        Collection<Integer> tipoContratoIds = Arrays.stream(tipoContratoIdsString.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        //Se mandan los parámetros al servidor ya con el tipo colection que requiere el reporte de Jasper
        params.put("tipo_contrato_id", tipoContratoIds);

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasOrganigramaPuestoYTipoContrato(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasPuestoContratoOrganigrama";
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

        //Se guarda la cadena de tipo de contratos proveniente del params.get
        String tipoContratoIdsString = (String) params.get("tipo_contrato_id");
        //Se guarda en una colección de enteros realizando la separación por comas
        Collection<Integer> tipoContratoIds = Arrays.stream(tipoContratoIdsString.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        //Se mandan los parámetros al servidor correspondientes para id_puesto
        params.put("id_puesto", Integer.valueOf((String) params.get("id_puesto")));
        //Se mandan los parámetros al servidor ya con el tipo colection que requiere el reporte de Jasper
        params.put("tipo_contrato_id", tipoContratoIds);

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasOrganigramaPuestoYEstatus(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasPuestoEstatusOrganigrama";
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

        params.put("id_puesto", Integer.valueOf((String) params.get("id_puesto")));
        params.put("estatus_plaza_id", Integer.valueOf((String) params.get("estatus_plaza_id")));

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasOrganigramaPuestoTipoContratoYEstatus(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasPuestoEstatusTipoContratoOrganigrama";
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

        params.put("id_puesto", Integer.valueOf((String) params.get("id_puesto")));
        //Se guarda la cadena de tipo de contratos proveniente del params.get
        String tipoContratoIdsString = (String) params.get("tipo_contrato_id");
        //Se guarda en una colección de enteros realizando la separación por comas
        Collection<Integer> tipoContratoIds = Arrays.stream(tipoContratoIdsString.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        //Se mandan los parámetros al servidor ya con el tipo colection que requiere el reporte de Jasper
        params.put("tipo_contrato_id", tipoContratoIds);
        params.put("estatus_plaza_id", Integer.valueOf((String) params.get("estatus_plaza_id")));

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    /**
     * **********************************************************
     * EXPEDIENTE **********************************************************
     */
    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasExpedientePuesto(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasPuestoExpediente";
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

        params.put("id_puesto", Integer.valueOf((String) params.get("id_puesto")));

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasExpedienteTipoContrato(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasTiposContratoExpediente";
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

        //Se guarda la cadena de tipo de contratos proveniente del params.get
        String tipoContratoIdsString = (String) params.get("tipo_contrato_id");
        //Se guarda en una colección de enteros realizando la separación por comas
        Collection<Integer> tipoContratoIds = Arrays.stream(tipoContratoIdsString.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        //Se mandan los parámetros al servidor ya con el tipo colection que requiere el reporte de Jasper
        params.put("tipo_contrato_id", tipoContratoIds);

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasExpedientePuestoYTipoContrato(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasPuestoContratoExpediente ";
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

        //Se guarda la cadena de tipo de contratos proveniente del params.get
        String tipoContratoIdsString = (String) params.get("tipo_contrato_id");
        //Se guarda en una colección de enteros realizando la separación por comas
        Collection<Integer> tipoContratoIds = Arrays.stream(tipoContratoIdsString.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        //Se mandan los parámetros al servidor correspondientes para id_puesto
        params.put("id_puesto", Integer.valueOf((String) params.get("id_puesto")));
        //Se mandan los parámetros al servidor ya con el tipo colection que requiere el reporte de Jasper
        params.put("tipo_contrato_id", tipoContratoIds);

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasExpedientePuestoYEstatus(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasPuestoEstatusExpediente";
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

        params.put("id_puesto", Integer.valueOf((String) params.get("id_puesto")));
        params.put("estatus_plaza_id", Integer.valueOf((String) params.get("estatus_plaza_id")));

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasExpedientePuestoTipoContratoYEstatus(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasPuestoEstatusTipoContratoExpediente";
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

        params.put("id_puesto", Integer.valueOf((String) params.get("id_puesto")));
        //Se guarda la cadena de tipo de contratos proveniente del params.get
        String tipoContratoIdsString = (String) params.get("tipo_contrato_id");
        //Se guarda en una colección de enteros realizando la separación por comas
        Collection<Integer> tipoContratoIds = Arrays.stream(tipoContratoIdsString.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        //Se mandan los parámetros al servidor ya con el tipo colection que requiere el reporte de Jasper
        params.put("tipo_contrato_id", tipoContratoIds);
        params.put("estatus_plaza_id", Integer.valueOf((String) params.get("estatus_plaza_id")));

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    /**
     * **********************************************************
     * CONSTANCIAS **********************************************************
     */
    @Override
    public ReportePersonaDTO obtenerConstanciaMensualIntegrado(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "constanciaSalarioMensualIntegrado";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        params.put("expediente", params.get("expediente"));
        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImgLogoGob", absolutePath);
        //Segunda imagen
        String relativePath2 = "imagenesReportes/ciudadInnovadora.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("urlImgCiudadInnovadora", absolutePath2);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerConstanciaSinSalario(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "constanciaSinSalario";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        params.put("expediente", params.get("expediente"));
        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImgLogoGob", absolutePath);
        //Segunda imagen
        String relativePath2 = "imagenesReportes/ciudadInnovadora.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("urlImgCiudadInnovadora", absolutePath2);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }

        return dto;
    }

    @Override
    public ReportePersonaDTO obtenerConstanciaFallecimiento(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "constanciaDefuncion";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        params.put("expediente", params.get("expediente"));
        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImgLogoGob", absolutePath);
        //Segunda imagen
        String relativePath2 = "imagenesReportes/ciudadInnovadora.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("urlImgCiudadInnovadora", absolutePath2);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }

        return dto;
    }

    /**
     * **********************************************************
     * AVISO MOVIMIENTO DE PERSONAL
     * **********************************************************
     */
    @Override
    public ReportePersonaDTO ObtenMovimientosPersonal(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "Aviso_de_movimiento_de_personal";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);

        params.put("parametro_base", Boolean.valueOf((String) params.get("parametro_base")));
        params.put("parametro_confianza", Boolean.valueOf((String) params.get("parametro_confianza")));
        params.put("parametro_estructura", Boolean.valueOf((String) params.get("parametro_estructura")));
        params.put("numero_expediente", String.valueOf((String) params.get("numero_expediente")));
        params.put("numero", String.valueOf((String) params.get("numero")));

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO ObtenMovimientosPersonalCambio(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "Aviso_de_movimiento_de_personal_cambio";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);

        params.put("parametro_base", Boolean.valueOf((String) params.get("parametro_base")));
        params.put("parametro_confianza", Boolean.valueOf((String) params.get("parametro_confianza")));
        params.put("parametro_estructura", Boolean.valueOf((String) params.get("parametro_estructura")));
        params.put("numero_expediente", String.valueOf((String) params.get("numero_expediente")));
        params.put("numero", String.valueOf((String) params.get("numero")));

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO reportePersonalFemeninoPorArea(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "PlantillaDelPersonalFemeninoPorArea";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        //Carga de imagen 1
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/cdmx.jpeg";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImage", absolutePath);
        params.put("Orden", params.get("Orden"));

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO reporteJubiladoActivo(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "plantillaPersonalJubiladoActivo";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/cdmx.jpeg";
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

    /**
     * **********************************************************
     * MOVIMIENTOS DE PERSONAL
     * **********************************************************
     */
    @Override
    public ReportePersonaDTO movimientoPersonalHistorico(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "historicoMovimientosRegistradosPlazaVacantes";

        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }

        return dto;
    }

    /*
    ******************************************************************************************
    REPORTE PLAZAS AUTORIZADAS x ID AREA
    *******************************************************************************************
     */
    @Override
    public ReportePersonaDTO obtenerMonitorPlazasAutorizadasAreas(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteMonitorPlazasAutorizadasAreas";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        params.put("areas_id", Integer.valueOf((String) params.get("areas_id")));
        //carga de imagen
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

    /*
    ******************************************************************************************
    REPORTE PENSIÓN ALIMENTICIA
    *******************************************************************************************
     */
    @Override
    public ReportePersonaDTO reportePensionAlimenticia(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "PensionesAlimenticias";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/cdmx.jpeg";
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

    /*
    ******************************************************************************************
    REPORTES PARA CONTROL DE ASISTENCIA
    *******************************************************************************************
     */
    @Override
    public ReportePersonaDTO controlAsistenciaPersonalBase(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ControlAsistenciaBase";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);
        //Parámettros extra
        String periodoStr = (String) params.get("periodo");
        String areaIdStr = (String) params.get("area_id");
        Integer periodo = Integer.parseInt(periodoStr);
        String fecha_inicio = (String) params.get("fecha_inicio");
        String fecha_fin = (String) params.get("fecha_fin");
        String expedienteStr = (String) params.get("expediente");
        String comentario = (String) params.get("comentario");
        //El valor nulo para area id se manejará como una selección para todas las áreas
        Integer areaId = null;
        if (areaIdStr != null && !areaIdStr.equalsIgnoreCase("null")) {
            areaId = Integer.parseInt(areaIdStr);
        }
        //El valor nulo se manejará como si una búsqueda sin expediente en específico
        Integer expediente = null;
        if (expedienteStr != null && !expedienteStr.equalsIgnoreCase("null")) {
            expediente = Integer.parseInt(expedienteStr);
        }
        params.put("periodo", periodo);
        params.put("area_id", areaId);
        params.put("fecha_inicio", fecha_inicio);
        params.put("fecha_fin", fecha_fin);
        params.put("expediente", expediente);
        params.put("comentario", comentario);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO resumenControlAsistenciaPersonalBase(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ComplementoCABase";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);
        //Parámettros extra
        String periodoStr = (String) params.get("periodo");
        Integer periodo = Integer.parseInt(periodoStr);
        //El valor nulo para area id se manejará como una selección para todas las áreas
        params.put("periodo", periodo);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO controlAsistenciaPersonalConfianza(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ControlAsistenciaConfianza";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);
        //Parámettros extra
        String periodoStr = (String) params.get("periodo");
        String areaIdStr = (String) params.get("area_id");
        String fecha_inicio = (String) params.get("fecha_inicio");
        String fecha_fin = (String) params.get("fecha_fin");
        String expedienteStr = (String) params.get("expediente");
        Integer periodo = Integer.parseInt(periodoStr);
        String comentario = (String) params.get("comentario");
        //El valor nulo para area id se manejará como una selección para todas las áreas
        Integer areaId = null;
        if (areaIdStr != null && !areaIdStr.equalsIgnoreCase("null")) {
            areaId = Integer.parseInt(areaIdStr);
        }
        //El valor nulo se manejará como si una búsqueda sin expediente en específico
        Integer expediente = null;
        if (expedienteStr != null && !expedienteStr.equalsIgnoreCase("null")) {
            expediente = Integer.parseInt(expedienteStr);
        }
        params.put("periodo", periodo);
        params.put("area_id", areaId);
        params.put("fecha_inicio", fecha_inicio);
        params.put("fecha_fin", fecha_fin);
        params.put("expediente", expediente);
        params.put("comentario", comentario);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    /*
    *******************************************************************************************
    REPORTES PARA AMONESTACIÓN Y SUSPENSIÓN
    *******************************************************************************************
     */
    @Override
    public ReportePersonaDTO suspension_amonestacion(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "Amonestaciones_Suspensiones";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("img", absolutePath);

        params.put("expediente", String.valueOf((String) params.get("expediente")));
        params.put("motivo", String.valueOf((String) params.get("motivo")));
        params.put("falta", String.valueOf((String) params.get("falta")));
        params.put("asunto", String.valueOf((String) params.get("asunto")));

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    /*
    *******************************************************************************************
    REPORTE INCAPACIDADES EXPEDIDAS IMSS
    *******************************************************************************************
     */
    @Override
    public ReportePersonaDTO incapacidadesExpedidasIMSS(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "IncapacidadesExpedidasIMSS";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/cdmx.jpeg";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);

        //Parámetros extra
        String periodoStr = (String) params.get("periodo");
        String expedienteStr = (String) params.get("expediente");
        String folioStr = (String) params.get("folio");
        String nominaIdStr = (String) params.get("id_nomina");
        String areaIdStr = (String) params.get("id_area");
        String motivoIncapacidadStr = (String) params.get("motivo_incapacidad");
        String fechaIicialStr = (String) params.get("fecha_inicial_liberacion");
        String fechaFinalMovimientoStr = (String) params.get("fecha_final_liberacion");

        //El valor nulo para expediente se manejará como una selección para todos los expedientes
        Integer periodo = null;
        if (periodoStr != null && !periodoStr.equalsIgnoreCase("null")) {
            periodo = Integer.parseInt(periodoStr);
        }
        //El valor nulo para expediente se manejará como una selección para todos los expedientes
        Integer expediente = null;
        if (expedienteStr != null && !expedienteStr.equalsIgnoreCase("null")) {
            expediente = Integer.parseInt(expedienteStr);
        }
        //El valor nulo para folio se manejará como una selección para todos los folios
        String folio = null;
        if (folioStr != null && !folioStr.equalsIgnoreCase("null")) {
            folio = folioStr;
        }
        //El valor nulo para folio se manejará como una selección para todos los folios
        Integer nomina = null;
        if (nominaIdStr != null && !nominaIdStr.equalsIgnoreCase("null")) {
            nomina = Integer.parseInt(nominaIdStr);
        }
        //El valor nulo para area id se manejará como una selección para todas las áreas
        Integer areaId = null;
        if (areaIdStr != null && !areaIdStr.equalsIgnoreCase("null")) {
            areaId = Integer.parseInt(areaIdStr);
        }
        //El valor nulo para folio se manejará como una selección para todos los folios
        Integer motivoIncapacidad = null;
        if (motivoIncapacidadStr != null && !motivoIncapacidadStr.equalsIgnoreCase("null")) {
            motivoIncapacidad = Integer.parseInt(motivoIncapacidadStr);
        }

        params.put("periodo", periodo);
        params.put("expediente", expediente);
        params.put("folio", folio);
        params.put("id_nomina", nomina);
        params.put("id_area", areaId);
        params.put("motivo_incapacidad", motivoIncapacidad);
        params.put("fecha_inicial_liberacion", fechaIicialStr);
        params.put("fecha_final_liberacion", fechaFinalMovimientoStr);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO incapacidadePeriodoPorMotivoIMSS(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "IncapacidadesPeriodoPorMotivoIncapacidad";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/Logo_CDMX.png";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);

        //Parámetros extra
        String periodoStr = (String) params.get("periodo");
        String expedienteStr = (String) params.get("expediente");
        String folioStr = (String) params.get("folio");
        String nominaIdStr = (String) params.get("id_nomina");
        String areaIdStr = (String) params.get("id_area");
        String motivoIncapacidadStr = (String) params.get("motivo_incapacidad");
        String fechaInicialStr = (String) params.get("fecha_inicial");
        String fechaFinalStr = (String) params.get("fecha_final");

        //El valor nulo para expediente se manejará como una selección para todos los expedientes
        Integer periodo = null;
        if (periodoStr != null && !periodoStr.equalsIgnoreCase("null")) {
            periodo = Integer.parseInt(periodoStr);
        }
        //El valor nulo para expediente se manejará como una selección para todos los expedientes
        Integer expediente = null;
        if (expedienteStr != null && !expedienteStr.equalsIgnoreCase("null")) {
            expediente = Integer.parseInt(expedienteStr);
        }
        //El valor nulo para folio se manejará como una selección para todos los folios
        String folio = null;
        if (folioStr != null && !folioStr.equalsIgnoreCase("null")) {
            folio = folioStr;
        }
        //El valor nulo para folio se manejará como una selección para todos los folios
        Integer nomina = null;
        if (nominaIdStr != null && !nominaIdStr.equalsIgnoreCase("null")) {
            nomina = Integer.parseInt(nominaIdStr);
        }
        //El valor nulo para area id se manejará como una selección para todas las áreas
        Integer areaId = null;
        if (areaIdStr != null && !areaIdStr.equalsIgnoreCase("null")) {
            areaId = Integer.parseInt(areaIdStr);
        }
        //El valor nulo para folio se manejará como una selección para todos los folios
        Integer motivoIncapacidad = null;
        if (motivoIncapacidadStr != null && !motivoIncapacidadStr.equalsIgnoreCase("null")) {
            motivoIncapacidad = Integer.parseInt(motivoIncapacidadStr);
        }

        params.put("periodo", periodo);
        params.put("expediente", expediente);
        params.put("folio", folio);
        params.put("id_nomina", nomina);
        params.put("id_area", areaId);
        params.put("motivo_incapacidad", motivoIncapacidad);
        params.put("fecha_inicial", fechaInicialStr);
        params.put("fecha_final", fechaFinalStr);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    /*
    *******************************************************************************************
    REPORTE INASISTENCIAS SUA IMSS
    *******************************************************************************************
     */
    @Override
    public ReportePersonaDTO inasistenciasSUAIMSS(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "RelacionInasistenciasSUA";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/cdmx.jpeg";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);
        //Parámettros extra
        String fecha_inicio = (String) params.get("fechaInicio");
        String fecha_fin = (String) params.get("fechaFin");
        params.put("fechaInicio", fecha_inicio);
        params.put("fechaFin", fecha_fin);

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    /*
    *******************************************************************************************
    REPORTE AYUDAS
    *******************************************************************************************
     */
    @Override
    public ReportePersonaDTO reporteAyudas(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteAyudas";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        //Carga de imagen
        //String basePath = "/var/lib/tomcat9/webapps/sirh/WEB-INF/classes/static/";
        //String basePath = "src/main/resources/static/";
        String relativePath = "imagenesReportes/cdmx.jpeg";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);
        //Parámetros extra
        String fecha_inicio = (String) params.get("fechaInicio");
        String fecha_fin = (String) params.get("fechaFin");
        params.put("fechaInicio", fecha_inicio);
        params.put("fechaFin", fecha_fin);
        //Parámetro opcional del estado de la incidencia
        if (params.get("estadoIncidenciaId") != null) {
            params.put("estadoIncidenciaId", Integer.valueOf((String) params.get("estadoIncidenciaId")));
        }

        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO reporteCapturaSemanalRA15(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReporteCapturaSemanalRA15PorPeriodo";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        String relativePath = "imagenesReportes/cdmx.jpeg";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);
        //Parámetros extra
        String periodoStr = (String) params.get("periodo_id");
        Integer periodo = Integer.parseInt(periodoStr);
        params.put("periodo_id", periodo);
        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    @Override
    public ReportePersonaDTO reporteSinTurnoEnElPeriodoRA15(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "Ra15TrabajadoresSinTurnoPeriodo";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);
        String relativePath = "imagenesReportes/cdmx.jpeg";
        File file = new File(basePath, relativePath);
        String absolutePath = file.getAbsolutePath();
        params.put("urlImg", absolutePath);
        //Parámetros extra
        String periodoStr = (String) params.get("periodo_id");
        Integer periodo = Integer.parseInt(periodoStr);
        params.put("periodo_id", periodo);
        try ( ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource))) {
            byte[] bs = stream.toByteArray();
            dto.setStream(new ByteArrayInputStream(bs));
            dto.setLength(bs.length);
        }
        return dto;
    }

    /*
    *******************************************************************************************
    REPORTE DEPOSITO DE PAGO PARA DIFERENTES BANCOS
    *******************************************************************************************
     */
    @Override
    public ReportePersonaDTO depositoPagoDiferentesBancos(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "DepositoDiferentesBancos";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        String relativePath2 = "imagenesReportes/Leyenda.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("img_ste", absolutePath1);
        params.put("leyenda", absolutePath2);
        params.put("periodo", Integer.valueOf((String) params.get("periodo")));
        params.put("nomina", (String) params.get("nomina"));
        params.put("tabla", (String) params.get("tabla"));
        params.put("fecha_inicial", (String) params.get("fecha_inicial"));
        params.put("fecha_final", (String) params.get("fecha_final"));
        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO depositoPagoDiferentesBancosEx(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "DepositoDiferentesBancosE";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        String relativePath2 = "imagenesReportes/Leyenda.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("img_ste", absolutePath1);
        params.put("leyenda", absolutePath2);
        params.put("periodo", Integer.valueOf((String) params.get("periodo")));
        params.put("nomina", (String) params.get("nomina"));
        params.put("tabla", (String) params.get("tabla"));
        params.put("fecha_inicial", (String) params.get("fecha_inicial"));
        params.put("fecha_final", (String) params.get("fecha_final"));
        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    /*
    *******************************************************************************************
    REPORTE DEPOSITO DE PAGO PARA DIFERENTES BANCOS PA VALES
    *******************************************************************************************
     */
    @Override
    public ReportePersonaDTO depositoPagoDiferentesBancosPAVales(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "DepositoDiferentesBancosPAVales";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        String relativePath2 = "imagenesReportes/Leyenda.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("img_ste", absolutePath1);
        params.put("leyenda", absolutePath2);
        params.put("periodo", Integer.valueOf((String) params.get("periodo")));
        params.put("nomina", (String) params.get("nomina"));
        params.put("tabla", (String) params.get("tabla"));
        params.put("fecha_inicial", (String) params.get("fecha_inicial"));
        params.put("fecha_final", (String) params.get("fecha_final"));
        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO depositoPagoDiferentesBancosPAValesEx(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "DepositoDiferentesBancosPAValesE";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        String relativePath2 = "imagenesReportes/Leyenda.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("img_ste", absolutePath1);
        params.put("leyenda", absolutePath2);
        params.put("periodo", Integer.valueOf((String) params.get("periodo")));
        params.put("nomina", (String) params.get("nomina"));
        params.put("tabla", (String) params.get("tabla"));
        params.put("fecha_inicial", (String) params.get("fecha_inicial"));
        params.put("fecha_final", (String) params.get("fecha_final"));
        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    /*
    *******************************************************************************************
    REPORTE DEPOSITO DE PAGO PARA DIFERENTES BANCOS PENSION ALIMENTICIA
    *******************************************************************************************
     */
    @Override
    public ReportePersonaDTO depositoPagoDiferentesBancosPension(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "DepositoDiferentesBancosPensiones";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        String relativePath2 = "imagenesReportes/Leyenda.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("img_ste", absolutePath1);
        params.put("leyenda", absolutePath2);
        params.put("periodo", Integer.valueOf((String) params.get("periodo")));
        params.put("nomina", (String) params.get("nomina"));
        params.put("tabla", (String) params.get("tabla"));
        params.put("fecha_inicial", (String) params.get("fecha_inicial"));
        params.put("fecha_final", (String) params.get("fecha_final"));
        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO depositoPagoDiferentesBancosPensionEx(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "DepositoDiferentesBancosPensionesE";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        String relativePath2 = "imagenesReportes/Leyenda.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("img_ste", absolutePath1);
        params.put("leyenda", absolutePath2);
        params.put("periodo", Integer.valueOf((String) params.get("periodo")));
        params.put("nomina", (String) params.get("nomina"));
        params.put("tabla", (String) params.get("tabla"));
        params.put("fecha_inicial", (String) params.get("fecha_inicial"));
        params.put("fecha_final", (String) params.get("fecha_final"));
        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    /*
    *******************************************************************************************
    REPORTE DEPOSITO DE PAGO PARA DIFERENTES BANCOS VALES
    *******************************************************************************************
     */
    @Override
    public ReportePersonaDTO depositoPagoDiferentesBancosVales(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "DepositoDiferentesBancosVales";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        String relativePath2 = "imagenesReportes/Leyenda.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("img_ste", absolutePath1);
        params.put("leyenda", absolutePath2);
        params.put("periodo", Integer.valueOf((String) params.get("periodo")));
        params.put("nomina", (String) params.get("nomina"));
        params.put("tabla", (String) params.get("tabla"));
        params.put("fecha_inicial", (String) params.get("fecha_inicial"));
        params.put("fecha_final", (String) params.get("fecha_final"));
        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    @Override
    public ReportePersonaDTO depositoPagoDiferentesBancosValesEx(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "DepositoDiferentesBancosValesE";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        String relativePath2 = "imagenesReportes/Leyenda.png";
        File file2 = new File(basePath, relativePath2);
        String absolutePath2 = file2.getAbsolutePath();
        params.put("img_ste", absolutePath1);
        params.put("leyenda", absolutePath2);
        params.put("periodo", Integer.valueOf((String) params.get("periodo")));
        params.put("nomina", (String) params.get("nomina"));
        params.put("tabla", (String) params.get("tabla"));
        params.put("fecha_inicial", (String) params.get("fecha_inicial"));
        params.put("fecha_final", (String) params.get("fecha_final"));
        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);

        return dto;
    }

    /*
    *******************************************************************************************
    REPORTE RECIBOS PARA PAGOS DE FINIQUITOS
    *******************************************************************************************
     */
    @Override
    public ReportePersonaDTO reciboFiniquito(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReciboFiniquitoLiq";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        params.put("urlImg", absolutePath1);
        //Parámetros a recibr desde el frontend
        params.put("trabajadorId", Integer.valueOf((String) params.get("trabajadorId")));
        params.put("fechaBaja", (String) params.get("fechaBaja"));
        params.put("aniosAntig", Integer.valueOf((String) params.get("aniosAntig")));
        params.put("mesesAntig", Integer.valueOf((String) params.get("mesesAntig")));
        params.put("diasAntig", Integer.valueOf((String) params.get("diasAntig")));
        params.put("salDiarioInteg", Double.valueOf((String) params.get("salDiarioInteg")));
        params.put("salMensInteg", Double.valueOf((String) params.get("salMensInteg")));
        params.put("periodoVacaciones", (String) params.get("periodoVacaciones"));
        params.put("periodoAguinaldo", (String) params.get("periodoAguinaldo"));
        params.put("diasPrimaAntig", Double.valueOf((String) params.get("diasPrimaAntig")));
        params.put("mesesIndemnizacion", Double.valueOf((String) params.get("mesesIndemnizacion")));
        params.put("diasVeinte", Double.valueOf((String) params.get("diasVeinte")));
        params.put("diasVacaciones", Double.valueOf((String) params.get("diasVacaciones")));
        params.put("diasPrimaVacacional", Double.valueOf((String) params.get("diasPrimaVacacional")));
        params.put("diasAguinaldo", Double.valueOf((String) params.get("diasAguinaldo")));
        //Importes
        params.put("importePrimaAntig", Double.valueOf((String) params.get("importePrimaAntig")));
        params.put("importeIndemnizacionLegal", Double.valueOf((String) params.get("importeIndemnizacionLegal")));
        params.put("importeDiasVeinte", Double.valueOf((String) params.get("importeDiasVeinte")));
        params.put("importeVacaciones", Double.valueOf((String) params.get("importeVacaciones")));
        params.put("importePrimaVacacional", Double.valueOf((String) params.get("importePrimaVacacional")));
        params.put("importeAguinaldo", Double.valueOf((String) params.get("importeAguinaldo")));
        params.put("isrFiniquito", Double.valueOf((String) params.get("isrFiniquito")));
        params.put("isrIndemnizacion", Double.valueOf((String) params.get("isrIndemnizacion")));
        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);
        return dto;
    }

    @Override
    public ReportePersonaDTO reciboFiniquitoPrestaciones(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "ReciboFiniquitoLiqPrestaciones";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        String relativePath1 = "imagenesReportes/LOGO_CDMX_STE.jpeg";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        params.put("urlImg", absolutePath1);
        //Parámetros a recibr desde el frontend
        params.put("trabajadorId", Integer.valueOf((String) params.get("trabajadorId")));
        params.put("fechaBaja", (String) params.get("fechaBaja"));
        params.put("aniosAntig", Integer.valueOf((String) params.get("aniosAntig")));
        params.put("mesesAntig", Integer.valueOf((String) params.get("mesesAntig")));
        params.put("diasAntig", Integer.valueOf((String) params.get("diasAntig")));
        params.put("salDiarioInteg", Double.valueOf((String) params.get("salDiarioInteg")));
        params.put("salMensInteg", Double.valueOf((String) params.get("salMensInteg")));
        params.put("periodoVacaciones", (String) params.get("periodoVacaciones"));
        params.put("periodoAguinaldo", (String) params.get("periodoAguinaldo"));
        params.put("diasPrimaAntig", Double.valueOf((String) params.get("diasPrimaAntig")));
        params.put("mesesIndemnizacion", Double.valueOf((String) params.get("mesesIndemnizacion")));
        params.put("diasVeinte", Double.valueOf((String) params.get("diasVeinte")));
        params.put("diasVacaciones", Double.valueOf((String) params.get("diasVacaciones")));
        params.put("diasPrimaVacacional", Double.valueOf((String) params.get("diasPrimaVacacional")));
        params.put("diasAguinaldo", Double.valueOf((String) params.get("diasAguinaldo")));
        //Importes
        params.put("importePrimaAntig", Double.valueOf((String) params.get("importePrimaAntig")));
        params.put("importeIndemnizacionLegal", Double.valueOf((String) params.get("importeIndemnizacionLegal")));
        params.put("importeDiasVeinte", Double.valueOf((String) params.get("importeDiasVeinte")));
        params.put("importeVacaciones", Double.valueOf((String) params.get("importeVacaciones")));
        params.put("importePrimaVacacional", Double.valueOf((String) params.get("importePrimaVacacional")));
        params.put("importeAguinaldo", Double.valueOf((String) params.get("importeAguinaldo")));
        params.put("isrFiniquito", Double.valueOf((String) params.get("isrFiniquito")));
        params.put("isrIndemnizacion", Double.valueOf((String) params.get("isrIndemnizacion")));
        //Campos adicionales para los trabajadores con prestaciones
        params.put("mesesCompAntigCct", Double.valueOf((String) params.get("mesesCompAntigCct")));
        params.put("importeMesesCompAntigCct", Double.valueOf((String) params.get("importeMesesCompAntigCct")));
        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);
        return dto;
    }

    @Override
    public ReportePersonaDTO reporteRetroJubilados(Map<String, Object> params) throws JRException, IOException, SQLException {
        String fileName = "retroJubilados";
        rutas();
        ReportePersonaDTO dto = new ReportePersonaDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName + extension);

        String relativePath1 = "imagenesReportes/Logo_CDMX.png";
        File file1 = new File(basePath, relativePath1);
        String absolutePath1 = file1.getAbsolutePath();
        params.put("urlImg", absolutePath1);
        //Parámetros a recibr desde el frontend
        params.put("anioParam", Integer.valueOf((String) params.get("anioParam")));
        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params, DataSourceUtils.getConnection(dataSource));
        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLength(bs.length);
        return dto;
    }

}
