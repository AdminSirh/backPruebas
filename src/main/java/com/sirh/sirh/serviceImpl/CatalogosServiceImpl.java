/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.serviceImpl;

import com.sirh.sirh.DTO.Cat_AreasDTO;
import com.sirh.sirh.DTO.Cat_BancoDTO;
import com.sirh.sirh.DTO.Cat_BeneficiarioDTO;
import com.sirh.sirh.DTO.Cat_CargoDTO;
import com.sirh.sirh.DTO.Cat_ColoniaDTO;
import com.sirh.sirh.DTO.Cat_Credito_InfonavitDTO;
import com.sirh.sirh.DTO.Cat_DiasDTO;
import com.sirh.sirh.DTO.Cat_Edo_CivilDTO;
import com.sirh.sirh.DTO.Cat_EstadoDTO;
import com.sirh.sirh.DTO.Cat_Estado_IncidenciaDTO;
import com.sirh.sirh.DTO.Cat_Estado_VacacionesDTO;
import com.sirh.sirh.DTO.Cat_EstructuraDTO;
import com.sirh.sirh.DTO.Cat_EstudiosDTO;
import com.sirh.sirh.DTO.Cat_ExpedienteDTO;
import com.sirh.sirh.DTO.Cat_GeneroDTO;
import com.sirh.sirh.DTO.Cat_IncidenciasDTO;
import com.sirh.sirh.DTO.Cat_InhabilitadoDTO;
import com.sirh.sirh.DTO.Cat_LicenciaDTO;
import com.sirh.sirh.DTO.Cat_MunicipioDTO;
import com.sirh.sirh.DTO.Cat_NacionalidadDTO;
import com.sirh.sirh.DTO.Cat_NumExpedienteDTO;
import com.sirh.sirh.DTO.Cat_ParentescoDTO;
import com.sirh.sirh.DTO.Cat_Plaza_MovimientosDTO;
import com.sirh.sirh.DTO.Cat_PuestoDTO;
import com.sirh.sirh.DTO.Cat_SangreDTO;
import com.sirh.sirh.DTO.Cat_Si_NoDTO;
import com.sirh.sirh.DTO.Cat_TabuladorDTO;
import com.sirh.sirh.DTO.Cat_Tabulador_SueldoDTO;
import com.sirh.sirh.DTO.Cat_Tipo_AyudaDTO;
import com.sirh.sirh.DTO.Cat_Tipo_BeneficiarioDTO;
import com.sirh.sirh.DTO.Cat_Tipo_IncidenciaDTO;
import com.sirh.sirh.entity.Cat_Areas;
import com.sirh.sirh.entity.Cat_Banco;
import com.sirh.sirh.entity.Cat_Beneficiario;
import com.sirh.sirh.entity.Cat_Bimestres_Sdi;
import com.sirh.sirh.entity.Cat_Cargo;
import com.sirh.sirh.entity.Cat_Colonia;
import com.sirh.sirh.entity.Cat_Contrato_Nomina;
import com.sirh.sirh.entity.Cat_Credito_Infonavit;
import com.sirh.sirh.entity.Cat_Depositos;
import com.sirh.sirh.entity.Cat_Dias;
import com.sirh.sirh.entity.Cat_Dias_Festivos;
import com.sirh.sirh.entity.Cat_Edo_Civil;
import com.sirh.sirh.entity.Cat_Estado;
import com.sirh.sirh.entity.Cat_Estado_Incidencia;
import com.sirh.sirh.entity.Cat_Estado_Vacaciones;
import com.sirh.sirh.entity.Cat_Estructura;
import com.sirh.sirh.entity.Cat_Estudios;
import com.sirh.sirh.entity.Cat_Expediente;
import com.sirh.sirh.entity.Cat_Genero;
import com.sirh.sirh.entity.Cat_Grado;
import com.sirh.sirh.entity.Cat_Horario;
import com.sirh.sirh.entity.Cat_Impuesto_Concepto;
import com.sirh.sirh.entity.Cat_Incidencias;
import com.sirh.sirh.entity.Cat_Inhabilitado;
import com.sirh.sirh.entity.Cat_Licencia;
import com.sirh.sirh.entity.Cat_Motivo_Cancelacion;
import com.sirh.sirh.entity.Cat_Motivo_Incapacidad;
import com.sirh.sirh.entity.Cat_Motivos_RA10;
import com.sirh.sirh.entity.Cat_Municipio;
import com.sirh.sirh.entity.Cat_Nacionalidad;
import com.sirh.sirh.entity.Cat_Nomina_Ubicacion;
import com.sirh.sirh.entity.Cat_Tipo_Contrato;
import com.sirh.sirh.entity.Cat_Parentesco;
import com.sirh.sirh.entity.Cat_Periodo_Impuesto;
import com.sirh.sirh.entity.Cat_Plaza;
import com.sirh.sirh.entity.Cat_Plaza_Movimientos;
import com.sirh.sirh.entity.Cat_Puesto;
import com.sirh.sirh.entity.Cat_Reporte_Monitor;
import com.sirh.sirh.entity.Cat_Sangre;
import com.sirh.sirh.entity.Cat_Si_No;
import com.sirh.sirh.entity.Cat_Tabulador;
import com.sirh.sirh.entity.Cat_Tabulador_Sueldo;
import com.sirh.sirh.entity.Cat_Tipo_Ayuda;
import com.sirh.sirh.entity.Cat_Tipo_Beneficiario;
import com.sirh.sirh.entity.Cat_Tipo_Control_Incapacidad;
import com.sirh.sirh.entity.Cat_Tipo_Dato_Incidencias;
import com.sirh.sirh.entity.Cat_Tipo_Documento;
import com.sirh.sirh.entity.Cat_Tipo_Incapacidad;
import com.sirh.sirh.entity.Cat_Tipo_Incidencia;
import com.sirh.sirh.entity.Cat_Tipo_Mov_Idse_Sua;
import com.sirh.sirh.entity.Cat_Tipo_Nomina;
import com.sirh.sirh.entity.Cat_Tipo_Riesgo_Incapacidad;
import com.sirh.sirh.entity.Cat_Tipo_Secuelas_Incapacidad;
import com.sirh.sirh.entity.Cat_Ubicacion;
import com.sirh.sirh.entity.Historico_Credito_Infonavit;
import com.sirh.sirh.entity.Historico_Libro_Dias;
import com.sirh.sirh.entity.Historico_Puesto;
import com.sirh.sirh.entity.Historico_Trabajador_Plaza;
import com.sirh.sirh.repository.Cat_AreasRepository;
import com.sirh.sirh.repository.Cat_BancoRepository;
import com.sirh.sirh.repository.Cat_BeneficiarioRepository;
import com.sirh.sirh.repository.Cat_Bimestres_SdiRepository;
import com.sirh.sirh.repository.Cat_CargoRepository;
import com.sirh.sirh.repository.Cat_ColoniaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sirh.sirh.service.CatalogosService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import com.sirh.sirh.repository.Cat_CreditoInfonavitRepository;
import com.sirh.sirh.repository.Cat_DepositosRepository;
import com.sirh.sirh.repository.Cat_DiasRepository;
import com.sirh.sirh.repository.Cat_Edo_CivilRepository;
import com.sirh.sirh.repository.Cat_EstadoRepository;
import com.sirh.sirh.repository.Cat_Estado_VacacionesRepository;
import com.sirh.sirh.repository.Cat_EstructuraRepository;
import com.sirh.sirh.repository.Cat_EstudiosRepository;
import com.sirh.sirh.repository.Cat_ExpedienteRepository;
import com.sirh.sirh.repository.Cat_GeneroRepository;
import com.sirh.sirh.repository.Cat_InhabilitadoRepository;
import com.sirh.sirh.repository.Cat_LicenciaRepository;
import com.sirh.sirh.repository.Cat_NacionalidadRepository;
import com.sirh.sirh.repository.Cat_ParentescoRepository;
import com.sirh.sirh.repository.Cat_PuestoRepository;
import com.sirh.sirh.repository.Cat_PlazaRepository;
import com.sirh.sirh.repository.Cat_SangreRepository;
import com.sirh.sirh.repository.Cat_Si_NoRepository;
import com.sirh.sirh.repository.Cat_TabuladorRepository;
import com.sirh.sirh.repository.Cat_Tabulador_SueldoRepository;
import com.sirh.sirh.repository.Cat_Tipo_BeneficiarioRepository;
import java.time.LocalDateTime;
import com.sirh.sirh.repository.Cat_Contrato_NominaRepository;
import com.sirh.sirh.repository.Cat_Dias_FestivosRepository;
import com.sirh.sirh.repository.Cat_Dias_VacacionesRepository;
import com.sirh.sirh.repository.Cat_Estado_IncidenciaRepository;
import com.sirh.sirh.repository.Cat_GradoRepository;
import com.sirh.sirh.repository.Cat_HorarioRepository;
import com.sirh.sirh.repository.Cat_Impuesto_ConceptoRepository;
import com.sirh.sirh.repository.Cat_IncidenciasRepository;
import com.sirh.sirh.repository.Cat_Motivo_CancelacionRepository;
import com.sirh.sirh.repository.Cat_Motivo_IncapacidadRepository;
import com.sirh.sirh.repository.Cat_Motivos_RA10Repository;
import com.sirh.sirh.repository.Cat_MunicipioRepository;
import com.sirh.sirh.repository.Cat_Nomina_UbicacionRepository;
import com.sirh.sirh.repository.Cat_Periodo_ImpuestoRepository;
import com.sirh.sirh.repository.Cat_Plaza_MovimientosRepository;
import com.sirh.sirh.repository.Cat_Reporte_MonitorRepository;
import com.sirh.sirh.repository.Cat_Tipo_AyudaRepository;
import com.sirh.sirh.repository.Cat_Tipo_ContratoRepository;
import com.sirh.sirh.repository.Cat_Tipo_Control_IncapacidadRepository;
import com.sirh.sirh.repository.Cat_Tipo_Dato_IncidenciasRepository;
import com.sirh.sirh.repository.Cat_Tipo_DocumentoRepository;
import com.sirh.sirh.repository.Cat_Tipo_IncapacidadRepository;
import com.sirh.sirh.repository.Cat_Tipo_IncidenciaRepository;
import com.sirh.sirh.repository.Cat_Tipo_Mov_Idse_SuaRepository;
import com.sirh.sirh.repository.Cat_Tipo_NominaRepository;
import com.sirh.sirh.repository.Cat_Tipo_Riesgo_IncapacidadRepository;
import com.sirh.sirh.repository.Cat_Tipo_Secuelas_IncapacidadRepository;
import com.sirh.sirh.repository.Historico_Credito_InfonavitRepository;
import com.sirh.sirh.repository.Historico_PuestoRepository;
import com.sirh.sirh.repository.Historico_Trabajador_PlazaRepository;
import java.util.Date;
import com.sirh.sirh.repository.Cat_UbicacionRepository;
import com.sirh.sirh.repository.Historico_Libro_DiasRepository;
import java.time.LocalDate;

/**
 *
 * @author jarellano22
 */
@Service
@Transactional
public class CatalogosServiceImpl implements CatalogosService {

    @Autowired
    private Cat_AreasRepository cat_AreasRepository;

    @Autowired
    private Cat_Tipo_NominaRepository cat_Tipo_NominaRepository;

    @Autowired
    private Cat_Bimestres_SdiRepository cat_Bimestres_SdiRepository;

    @Autowired
    private Cat_ColoniaRepository cat_ColoniaRepository;

    @Autowired
    private Cat_CargoRepository cat_cargoRepository;

    @Autowired
    private Cat_CreditoInfonavitRepository cat_CreditoInfonavitRepository;

    @Autowired
    private Cat_Edo_CivilRepository cat_Edo_CivilRepository;

    @Autowired
    private Cat_EstadoRepository cat_EstadoRepository;

    @Autowired
    private Cat_Estado_VacacionesRepository cat_Estado_VacacionesRepository;

    @Autowired
    private Cat_EstructuraRepository cat_EstructuraRepository;

    @Autowired
    private Cat_EstudiosRepository cat_EstudiosRepository;

    @Autowired
    private Cat_GeneroRepository cat_GeneroRepository;

    @Autowired
    private Cat_InhabilitadoRepository cat_InhabilitadoRepository;

    @Autowired
    private Cat_LicenciaRepository cat_LicenciaRepository;

    @Autowired
    private Cat_Tipo_ContratoRepository cat_Tipo_ContratoRepository;

    @Autowired
    private Cat_ParentescoRepository cat_ParentescoRepository;

    @Autowired
    private Cat_SangreRepository cat_SangreRepository;

    @Autowired
    private Cat_BeneficiarioRepository cat_BeneficiarioRepository;

    @Autowired
    private Cat_Tabulador_SueldoRepository cat_Tabulador_SueldoRepository;

    @Autowired
    private Cat_TabuladorRepository cat_TabuladorRepository;

    @Autowired
    private Cat_NacionalidadRepository cat_NacionalidadRepository;

    @Autowired
    private Cat_Si_NoRepository cat_Si_NoRepository;

    @Autowired
    private Cat_ExpedienteRepository cat_ExpedienteRepository;

    @Autowired
    private Cat_Tipo_BeneficiarioRepository cat_Tipo_BeneficiarioRepository;

    @Autowired
    private Cat_BancoRepository cat_BancoRepository;

    @Autowired
    private Cat_PuestoRepository cat_PuestoRepository;

    @Autowired
    private Cat_PlazaRepository cat_PlazaRepository;

    @Autowired
    private Cat_DepositosRepository cat_DepositosRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Cat_DiasRepository cat_DiasRepository;

    @Autowired
    private Cat_UbicacionRepository cat_UbicacionRepository;

    @Autowired
    private Cat_Contrato_NominaRepository cat_Contrato_NominaRepository;

    @Autowired
    private Cat_Motivo_IncapacidadRepository cat_Motivo_IncapacidadRepository;

    @Autowired
    private Cat_Tipo_IncapacidadRepository cat_Tipo_IncapacidadRepository;

    @Autowired
    private Cat_Tipo_Riesgo_IncapacidadRepository cat_Tipo_Riesgo_IncapacidadRepository;

    @Autowired
    private Cat_Tipo_Secuelas_IncapacidadRepository cat_Tipo_Secuelas_IncapacidadRepository;

    @Autowired
    private Cat_Tipo_Control_IncapacidadRepository cat_Tipo_Control_IncapacidadRepository;

    @Autowired
    private Cat_Tipo_Mov_Idse_SuaRepository cat_Tipo_Mov_Idse_SuaRepository;

    @Autowired
    private Cat_Tipo_Dato_IncidenciasRepository cat_Tipo_Dato_IncidenciasRepository;

    @Autowired
    private Cat_Nomina_UbicacionRepository cat_Nomina_UbicacionRepository;

    @Autowired
    private Historico_PuestoRepository historico_PuestoRepository;

    @Autowired
    private Historico_Trabajador_PlazaRepository historico_Trabajador_PlazaRepository;

    @Autowired
    private Cat_Dias_VacacionesRepository cat_Dias_VacacionesRepository;

    @Autowired
    private Cat_IncidenciasRepository cat_incidenciasRepository;

    @Autowired
    private Cat_Tipo_IncidenciaRepository cat_tipo_incidenciaRepository;

    @Autowired
    private Cat_Estado_IncidenciaRepository cat_estado_incidenciaRepository;

    @Autowired
    private Cat_Motivo_CancelacionRepository cat_Motivo_CancelacionRepository;

    @Autowired
    private Cat_Dias_FestivosRepository cat_Dias_FestivosRepository;

    @Autowired
    private Cat_HorarioRepository cat_HorarioRepository;

    @Autowired
    private Cat_Plaza_MovimientosRepository cat_Plaza_MovimientosRepository;

    @Autowired
    private Cat_Tipo_DocumentoRepository cat_Tipo_DocumentoRepository;

    @Autowired
    private Cat_MunicipioRepository cat_MunicipioRepository;

    @Autowired
    private Cat_Tipo_AyudaRepository cat_tipo_ayudaRepository;

    @Autowired
    private Historico_Credito_InfonavitRepository historico_Credito_InfonavitRepository;

    @Autowired
    private Cat_Reporte_MonitorRepository cat_Reporte_MonitorRepository;

    @Autowired
    private Cat_GradoRepository cat_GradoRepository;

    @Autowired
    private Historico_Libro_DiasRepository historico_libro_diasRepository;

    @Autowired
    private Cat_Impuesto_ConceptoRepository cat_Impuesto_ConceptoRepository;

    @Autowired
    private Cat_Periodo_ImpuestoRepository cat_Periodo_ImpuestoRepository;

    @Autowired
    private Cat_Motivos_RA10Repository cat_Motivos_RA10Repository;

    /**
     * @return @Override public List<Cat_AreasDTO> findAll() { List<Cat_Areas>
     * cat_areas = catalogosRepository.findAll(); //Create dto
     * List<Cat_AreasDTO>
     * cat_AreasDTO = new ArrayList<>(); for (Cat_Areas areas : cat_areas) {
     * cat_AreasDTO.add(modelMapper.map(areas, Cat_AreasDTO.class)); } return
     * cat_AreasDTO; }
     */
    // ******************* CATALOGO AREAS ******************
    @Override
    public Cat_Areas editarAreas(Cat_Areas areas, Integer id_a) {
        Cat_Areas cat_a = this.cat_AreasRepository.findById(id_a).get();
        Cat_Areas cat = cat_a;
        LocalDate datetime = LocalDate.now();

        cat.setCodigo_area(areas.getCodigo_area());
        cat.setCodigo_personal(areas.getCodigo_personal());
        cat.setDesc_area(areas.getDesc_area());
        cat.setDeterminante(areas.getDeterminante());
        cat.setDoc_autorizacion(areas.getDoc_autorizacion());
        cat.setEstatus(1);
        cat.setFecha_movimiento(datetime);
        cat.setHw_area(areas.getHw_area());
        cat.setHw_bis(areas.getHw_bis());
        cat.setHw_status(areas.getHw_status());
        cat.setPresupuesto(areas.getPresupuesto());
        cat.setPrograma(areas.getPrograma());

        return cat_AreasRepository.save(cat);
    }

    @Override
    public Cat_Areas estatus(Integer id, Integer activo) {
        Cat_Areas estatus = cat_AreasRepository.findById(id).get();
        estatus.setEstatus(activo);

        return cat_AreasRepository.save(estatus);
    }

    @Override
    public List<Cat_Areas> listarAreas() {
        return cat_AreasRepository.findAll().stream().map(cat_areas -> modelMapper.map(cat_areas, Cat_Areas.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_AreasDTO> findAll() {
        return cat_AreasRepository.findAll().stream().map(cat_areas -> modelMapper.map(cat_areas, Cat_AreasDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Areas> findAllDatosAreas() { //Listar todos los datos
        return cat_AreasRepository.findAll().stream().map(cat_areas -> modelMapper.map(cat_areas, Cat_Areas.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Areas save(Cat_Areas areas) {

        Cat_Areas cat_areas = new Cat_Areas();
        LocalDate datetime = LocalDate.now();

        cat_areas.setEstatus(1);
        cat_areas.setCodigo_area(areas.getCodigo_area());
        cat_areas.setCodigo_personal(areas.getCodigo_personal());
        cat_areas.setDesc_area(areas.getDesc_area());
        cat_areas.setDeterminante(areas.getDeterminante());
        cat_areas.setDoc_autorizacion(areas.getDoc_autorizacion());
        cat_areas.setFecha_captura(datetime);
        cat_areas.setFecha_movimiento(datetime);
        cat_areas.setHw_area(areas.getHw_area());
        cat_areas.setHw_bis(areas.getHw_bis());
        cat_areas.setHw_status(areas.getHw_status());
        cat_areas.setPresupuesto(areas.getPresupuesto());
        cat_areas.setPrograma(areas.getPrograma());

        return cat_AreasRepository.save(cat_areas);
    }

    @Override
    public Cat_Areas findOne(Integer id) {
        return cat_AreasRepository.findById(id).get();
    }

    @Override
    public Cat_Areas update(Cat_Areas cat_areas, Integer id) {
        return cat_AreasRepository.save(cat_areas);
    }

    @Override
    public void delete(Integer id) {
        cat_AreasRepository.deleteById(id);
    }

    @Override
    public List<Cat_Bimestres_Sdi> findAllBimestresSdi() {
        return cat_Bimestres_SdiRepository.findAll().stream().map(cat_Bimestres_Sdi -> modelMapper.map(cat_Bimestres_Sdi, Cat_Bimestres_Sdi.class)).collect(Collectors.toList());
    }
    
    @Override
    public Cat_Bimestres_Sdi findByIdBimestre(Integer id_bimestre) {
        return cat_Bimestres_SdiRepository.findByIdBimestre(id_bimestre);
    }

    // ******************* CATALOGO CARGO ******************
    @Override
    public List<Cat_CargoDTO> findAllCargo() {
        return cat_cargoRepository.findAll().stream().map(cat_cargo -> modelMapper.map(cat_cargo, Cat_CargoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Cargo> findAllDatosCargo() { //Listar todos los datos
        return cat_cargoRepository.findAll().stream().map(cat_cargo -> modelMapper.map(cat_cargo, Cat_Cargo.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Cargo findOneCargo(Integer id) {
        return cat_cargoRepository.findById(id).get();
    }

    @Override
    public Cat_Cargo saveCargo(Cat_Cargo catCargo) {
        Cat_Cargo c = new Cat_Cargo();
        c.setCargo_admon_pub(catCargo.getCargo_admon_pub());
        c.setEstatus(1);
        return cat_cargoRepository.save(c);
    }

    @Override
    public Cat_Cargo updateCargo(Cat_Cargo cat_Cargo, Integer id_cargo) {
        Cat_Cargo c = cat_cargoRepository.findById(id_cargo).get();
        c.setCargo_admon_pub(cat_Cargo.getCargo_admon_pub());
        c.setEstatus(1);
        return cat_cargoRepository.save(c);
    }

    @Override
    public Cat_Cargo cambioEstatusCargo(Integer id_cargo, Integer activo) {
        Cat_Cargo stat = cat_cargoRepository.findById(id_cargo).get();
        stat.setEstatus(activo);
        return cat_cargoRepository.save(stat);
    }

    // ******************* CATALOGO COLONIA ******************
    @Override
    public List<Cat_ColoniaDTO> findAllColonia() {
        return cat_ColoniaRepository.findAll().stream().map(cat_colonia -> modelMapper.map(cat_colonia, Cat_ColoniaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Colonia> findAllDatosColonia() { //Listar todos los datos
        return cat_ColoniaRepository.findAll().stream().map(cat_colonia -> modelMapper.map(cat_colonia, Cat_Colonia.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Colonia saveColonia(Cat_Colonia colonia) {
        Cat_Colonia col = new Cat_Colonia();
        col.setCat_municipio(colonia.getCat_municipio());
        col.setCod_postal(colonia.getCod_postal());
        col.setDesc_colonia(colonia.getDesc_colonia());
        col.setTipo_asentamiento(colonia.getTipo_asentamiento());
        col.setEstatus(1);
        return cat_ColoniaRepository.save(col);
    }

    @Override
    public List<Cat_Colonia> findMunicipiosColonia(Integer id_municipio) {
        return cat_ColoniaRepository.findMunicipiosColonia(id_municipio);
    }

    @Override
    public Cat_Colonia updateCatColonia(Cat_ColoniaDTO colonia, Integer id_colonia) {
        Cat_Colonia c = this.cat_ColoniaRepository.findById(id_colonia).get();
        Cat_Colonia col = c;
        col.setDesc_colonia(colonia.getDesc_colonia());
        col.setCod_postal(colonia.getCod_postal());
        col.setTipo_asentamiento(colonia.getTipo_asentamiento());
        col.setCat_municipio(colonia.getCat_municipio());
        col.setEstatus(1);
        return cat_ColoniaRepository.save(col);
    }

    @Override
    public Cat_Colonia updateColonia(Cat_Colonia colonia, Integer id_colonia) {
        Cat_Colonia col = cat_ColoniaRepository.findById(id_colonia).get();
        col.setDesc_colonia(col.getDesc_colonia());

        return cat_ColoniaRepository.save(col);
    }

    @Override
    public Cat_Colonia estatusColonia(Integer id_colonia, Integer estatus) {
        Cat_Colonia colonia = cat_ColoniaRepository.findById(id_colonia).get();
        colonia.setEstatus(estatus);
        return cat_ColoniaRepository.save(colonia);
    }

    @Override
    public Cat_Colonia findOneColonia(Integer id_colonia) {
        return cat_ColoniaRepository.findById(id_colonia).get();
    }

    // ******************* CATALOGO CREDITO ******************
    @Override
    public List<Cat_Credito_InfonavitDTO> findAllCredito() {
        return cat_CreditoInfonavitRepository.findAll().stream().map(cat_credito -> modelMapper.map(cat_credito, Cat_Credito_InfonavitDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Credito_Infonavit> findAllDatosCredito() { //Listar todos los datos
        return cat_CreditoInfonavitRepository.findAll().stream().map(cat_credito -> modelMapper.map(cat_credito, Cat_Credito_Infonavit.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Credito_Infonavit findOneCredito(Integer id) {
        return cat_CreditoInfonavitRepository.findById(id).get();
    }

    @Override
    public Cat_Credito_Infonavit saveCredito(Cat_Credito_Infonavit cat_Credito_Infonavit) {
        Cat_Credito_Infonavit ci = new Cat_Credito_Infonavit();
        ci.setCredito_infonavit(cat_Credito_Infonavit.getCredito_infonavit());
        ci.setEstatus(1);
        return cat_CreditoInfonavitRepository.save(ci);
    }

    @Override
    public Cat_Credito_Infonavit updateCredito(Cat_Credito_Infonavit catCredito_infonavit, Integer id_credito) {
        Cat_Credito_Infonavit c = cat_CreditoInfonavitRepository.findById(id_credito).get();
        c.setCredito_infonavit(catCredito_infonavit.getCredito_infonavit());
        c.setEstatus(1);
        return cat_CreditoInfonavitRepository.save(c);
    }

    @Override
    public Cat_Credito_Infonavit cambioEstatusCredito(Integer id_credito, Integer stat) {
        Cat_Credito_Infonavit s = cat_CreditoInfonavitRepository.findById(id_credito).get();
        s.setEstatus(stat);
        return cat_CreditoInfonavitRepository.save(s);
    }

    // ******************* CATALOGO ESTADO CIVIL ******************
    @Override
    public List<Cat_Edo_CivilDTO> findAllEdoCivil() {
        return cat_Edo_CivilRepository.findAll().stream().map(cat_edoCivil -> modelMapper.map(cat_edoCivil, Cat_Edo_CivilDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Edo_Civil> findAllDatosEdoCivil() { //Listar todos los datos
        return cat_Edo_CivilRepository.findAll().stream().map(cat_edoCivil -> modelMapper.map(cat_edoCivil, Cat_Edo_Civil.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Edo_Civil findOneEdoCivil(Integer id) {
        return cat_Edo_CivilRepository.findById(id).get();
    }

    @Override
    public Cat_Edo_Civil saveEdoCivil(Cat_Edo_Civil cat_Edo_Civil) {
        Cat_Edo_Civil ec = new Cat_Edo_Civil();
        ec.setDesc_edo_civil(cat_Edo_Civil.getDesc_edo_civil());
        ec.setEstatus(1);
        return cat_Edo_CivilRepository.save(ec);
    }

    @Override
    public Cat_Edo_Civil updateEdoCivil(Cat_Edo_Civil EdoCivil, Integer id_edo_civil) {
        Cat_Edo_Civil EdoC = cat_Edo_CivilRepository.findById(id_edo_civil).get();
        EdoC.setDesc_edo_civil(EdoCivil.getDesc_edo_civil());
        EdoC.setEstatus(1);
        return cat_Edo_CivilRepository.save(EdoC);
    }

    @Override
    public Cat_Edo_Civil cambioEstatusEdoCivil(Integer id_edo_civil, Integer stat) {
        Cat_Edo_Civil s = cat_Edo_CivilRepository.findById(id_edo_civil).get();
        s.setEstatus(stat);
        return cat_Edo_CivilRepository.save(s);
    }

    // ******************* CATALOGO ESTADO ******************
    @Override
    public List<Cat_EstadoDTO> findAllEstado() {
        return cat_EstadoRepository.findAll().stream().map(cat_estado -> modelMapper.map(cat_estado, Cat_EstadoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Estado> findAllDatosEstado() { //Listar todos los datos
        return cat_EstadoRepository.findAll().stream().map(cat_estado -> modelMapper.map(cat_estado, Cat_Estado.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Estado saveEstado(Cat_Estado cat_Estado) {
        return cat_EstadoRepository.save(cat_Estado);
    }

    @Override
    public Cat_Estado findOneEstado(Integer id) {
        return cat_EstadoRepository.findById(id).get();
    }

    @Override
    public Cat_Estado updateEstado(Cat_Estado estado, Integer id_estado) {
        Cat_Estado e = cat_EstadoRepository.findById(id_estado).get();
        e.setDesc_estado(estado.getDesc_estado());
        return cat_EstadoRepository.save(e);
    }

    @Override
    public Cat_Estado cambioEstatusEstado(Integer id_estado, Integer stat) {
        Cat_Estado s = cat_EstadoRepository.findById(id_estado).get();
        s.setEstatus(stat);
        return cat_EstadoRepository.save(s);
    }

    // ******************* CATALOGO ESTADO VACACIONES******************
    @Override
    public List<Cat_Estado_VacacionesDTO> findAllEstadoVacaciones() {
        return cat_Estado_VacacionesRepository.findAll().stream().map(cat_estadoVacaciones -> modelMapper.map(cat_estadoVacaciones, Cat_Estado_VacacionesDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Estado_Vacaciones> findAllDatosEstadoVacaciones() { //Listar todos los datos
        return cat_Estado_VacacionesRepository.findAll().stream().map(cat_estadoVacaciones -> modelMapper.map(cat_estadoVacaciones, Cat_Estado_Vacaciones.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Estado_Vacaciones findOneEstadoVacaciones(Integer id) {
        return cat_Estado_VacacionesRepository.findById(id).get();
    }

    @Override
    public Cat_Estado_Vacaciones saveEstadoVacaciones(Cat_Estado_Vacaciones cat_Estado_Vacaciones) {
        Cat_Estado_Vacaciones edoVac = new Cat_Estado_Vacaciones();
        edoVac.setDesc_estado_vacaciones(cat_Estado_Vacaciones.getDesc_estado_vacaciones());
        edoVac.setEstatus(1);
        return cat_Estado_VacacionesRepository.save(edoVac);

    }

    @Override
    public Cat_Estado_Vacaciones updateEstadoVacaciones(Cat_Estado_Vacaciones estadoVacaciones, Integer id_estado_vacaciones) {
        Cat_Estado_Vacaciones e = cat_Estado_VacacionesRepository.findById(id_estado_vacaciones).get();
        e.setDesc_estado_vacaciones(estadoVacaciones.getDesc_estado_vacaciones());
        e.setEstatus(1);
        return cat_Estado_VacacionesRepository.save(e);
    }

    @Override
    public Cat_Estado_Vacaciones cambioEstatusEstadoVacaciones(Integer id_estado_vacaciones, Integer stat) {
        Cat_Estado_Vacaciones s = cat_Estado_VacacionesRepository.findById(id_estado_vacaciones).get();
        s.setEstatus(stat);
        return cat_Estado_VacacionesRepository.save(s);
    }

    // ******************* CATALOGO ESTRUCTURA ******************
    @Override
    public List<Cat_EstructuraDTO> findAllEstructura() {
        return cat_EstructuraRepository.findAll().stream().map(cat_Estructura -> modelMapper.map(cat_Estructura, Cat_EstructuraDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Estructura> findAllDatosEstructura() { //Listar todos los datos
        return cat_EstructuraRepository.findAll().stream().map(cat_Estructura -> modelMapper.map(cat_Estructura, Cat_Estructura.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Estructura findOneEstructura(Integer id) {
        return cat_EstructuraRepository.findById(id).get();
    }

    @Override
    public Cat_Estructura saveEstructura(Cat_Estructura cat_Estructura) {
        Cat_Estructura e = new Cat_Estructura();
        e.setDesc_estructura(cat_Estructura.getDesc_estructura());
        e.setEstatus(1);
        return cat_EstructuraRepository.save(e);
    }

    @Override
    public Cat_Estructura updateEstructura(Cat_Estructura estructura, Integer id_estructura) {
        Cat_Estructura e = cat_EstructuraRepository.findById(id_estructura).get();
        e.setDesc_estructura(estructura.getDesc_estructura());
        e.setEstatus(1);
        return cat_EstructuraRepository.save(e);
    }

    @Override
    public Cat_Estructura cambioEstatusEstructura(Integer id_estructura, Integer stat) {
        Cat_Estructura s = cat_EstructuraRepository.findById(id_estructura).get();
        s.setEstatus(stat);
        return cat_EstructuraRepository.save(s);
    }

    // ******************* CATALOGO ESTUDIOS ******************
    @Override
    public List<Cat_EstudiosDTO> findAllEstudios() {
        return cat_EstudiosRepository.findAll().stream().map(cat_Estudios -> modelMapper.map(cat_Estudios, Cat_EstudiosDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Estudios> findAllDatosEstudios() { //Listar todos los datos
        return cat_EstudiosRepository.findAll().stream().map(cat_Estudios -> modelMapper.map(cat_Estudios, Cat_Estudios.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Estudios findOneEstudio(Integer id) {
        return cat_EstudiosRepository.findById(id).get();
    }

    @Override
    public Cat_Estudios saveEstudios(Cat_Estudios cat_Estudios) {
        Cat_Estudios e = new Cat_Estudios();
        e.setDesc_grado(cat_Estudios.getDesc_grado());
        e.setEstatus(1);
        return cat_EstudiosRepository.save(e);
    }

    @Override
    public Cat_Estudios updateEstudios(Cat_Estudios estudios, Integer id_grado) {
        Cat_Estudios e = cat_EstudiosRepository.findById(id_grado).get();
        e.setDesc_grado(estudios.getDesc_grado());
        e.setEstatus(1);
        return cat_EstudiosRepository.save(e);
    }

    @Override
    public Cat_Estudios cambioEstatusEstudios(Integer id_grado, Integer estatus) {
        Cat_Estudios e = cat_EstudiosRepository.findById(id_grado).get();
        e.setEstatus(estatus);
        return cat_EstudiosRepository.save(e);
    }

    // ******************* CATALOGOS DE INCAPACIDAD ******************
    @Override
    public List<Cat_Motivo_Incapacidad> findAllMotivosIncapacidad() {
        return cat_Motivo_IncapacidadRepository.findAll().stream().map(cat_Motivo_Incapacidad -> modelMapper.map(cat_Motivo_Incapacidad, Cat_Motivo_Incapacidad.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Tipo_Incapacidad> findAllTiposIncapacidad() {
        return cat_Tipo_IncapacidadRepository.findAll().stream().map(cat_Tipo_Incapacidad -> modelMapper.map(cat_Tipo_Incapacidad, Cat_Tipo_Incapacidad.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Tipo_Riesgo_Incapacidad> findAllTipoRiesgoIncapacidad() {
        return cat_Tipo_Riesgo_IncapacidadRepository.findAll().stream().map(cat_Tipo_Riesgo_Incapacidad -> modelMapper.map(cat_Tipo_Riesgo_Incapacidad, Cat_Tipo_Riesgo_Incapacidad.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Tipo_Secuelas_Incapacidad> findAllTipoSecuelasIncapacidad() {
        return cat_Tipo_Secuelas_IncapacidadRepository.findAll().stream().map(cat_Tipo_Secuelas_Incapacidad -> modelMapper.map(cat_Tipo_Secuelas_Incapacidad, Cat_Tipo_Secuelas_Incapacidad.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Tipo_Control_Incapacidad> findAllTipoControlIncapacidad() {
        return cat_Tipo_Control_IncapacidadRepository.findAll().stream().map(cat_Tipo_Control_Incapacidad -> modelMapper.map(cat_Tipo_Control_Incapacidad, Cat_Tipo_Control_Incapacidad.class)).collect(Collectors.toList());
    }

    // ******************* CATALOGO GENERO ******************
    @Override
    public List<Cat_GeneroDTO> findAllGenero() { //cat_GeneroRepository
        return cat_GeneroRepository.findAll().stream().map(cat_GeneroDTO -> modelMapper.map(cat_GeneroDTO, Cat_GeneroDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Genero> findAllDatosGenero() { //Listar todos los datos
        return cat_GeneroRepository.findAll().stream().map(cat_GeneroDTO -> modelMapper.map(cat_GeneroDTO, Cat_Genero.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Genero findOneGenero(Integer id) {
        return cat_GeneroRepository.findById(id).get();
    }

    @Override
    public Cat_Genero saveGenero(Cat_Genero cat_Genero) {
        Cat_Genero g = new Cat_Genero();
        g.setDesc_genero(cat_Genero.getDesc_genero());
        g.setEstatus(1);
        return cat_GeneroRepository.save(g);
    }

    @Override
    public Cat_Genero updateGenero(Cat_Genero genero, Integer id_genero) {
        Cat_Genero g = cat_GeneroRepository.findById(id_genero).get();
        g.setDesc_genero(genero.getDesc_genero());
        g.setEstatus(1);
        return cat_GeneroRepository.save(g);
    }

    @Override
    public Cat_Genero cambioEstatusGenero(Integer id_genero, Integer activo) {
        Cat_Genero stat = cat_GeneroRepository.findById(id_genero).get();
        stat.setEstatus(activo);
        return cat_GeneroRepository.save(stat);
    }

    // ******************* CATALOGO INHABILITADO ******************
    @Override
    public List<Cat_InhabilitadoDTO> findAllInhabilitado() {
        return cat_InhabilitadoRepository.findAll().stream().map(cat_InhabilitadoDTO -> modelMapper.map(cat_InhabilitadoDTO, Cat_InhabilitadoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Inhabilitado> findAllDatosInhabilitado() { //Listar todos los datos
        return cat_InhabilitadoRepository.findAll().stream().map(cat_Inhabilitado -> modelMapper.map(cat_Inhabilitado, Cat_Inhabilitado.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Inhabilitado saveInhabilitdo(Cat_Inhabilitado cat_Inhabilitado) {
        return cat_InhabilitadoRepository.save(cat_Inhabilitado);
    }

    @Override
    public Cat_Inhabilitado saveInhabilitdo(Cat_InhabilitadoDTO cat_Inhabilitado) {
        Cat_Inhabilitado tipoInhabilitado = new Cat_Inhabilitado();
        tipoInhabilitado.setInhabilitado(cat_Inhabilitado.getInhabilitado());
        tipoInhabilitado.setEstatus(1);
        return cat_InhabilitadoRepository.save(tipoInhabilitado);
    }

    @Override
    public Cat_Inhabilitado updateInhabilitado(Integer id_inhabilitado, Cat_InhabilitadoDTO cat_Inhabilitado) {
        Cat_Inhabilitado inhabil = this.cat_InhabilitadoRepository.findById(id_inhabilitado).get();
        Cat_Inhabilitado tipoInhabilitado = inhabil;
        tipoInhabilitado.setInhabilitado(cat_Inhabilitado.getInhabilitado());
        tipoInhabilitado.setEstatus(1);
        return cat_InhabilitadoRepository.save(tipoInhabilitado);
    }

    @Override
    public Cat_Inhabilitado findOneInhabilitado(Integer id_inhabilitado) {
        return cat_InhabilitadoRepository.findById(id_inhabilitado).get();
    }

    @Override
    public Cat_Inhabilitado estatusInhabilitado(Integer id_inhabilitado, Integer estatus) {
        Cat_Inhabilitado inhabilitado = cat_InhabilitadoRepository.findById(id_inhabilitado).get();
        inhabilitado.setEstatus(estatus);
        return cat_InhabilitadoRepository.save(inhabilitado);
    }

    // ******************* CATALOGO LICENCIA ******************
    @Override
    public List<Cat_LicenciaDTO> findAllLicencia() {
        return cat_LicenciaRepository.findAll().stream().map(cat_LicenciaDTO -> modelMapper.map(cat_LicenciaDTO, Cat_LicenciaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Licencia> findAllDatosLicencia() { //Listar todos los datos
        return cat_LicenciaRepository.findAll().stream().map(cat_Licencia -> modelMapper.map(cat_Licencia, Cat_Licencia.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Licencia saveLicencia(Cat_Licencia cat_Licencia) {
        return cat_LicenciaRepository.save(cat_Licencia);
    }

    @Override
    public Cat_Licencia findOneLicencia(Integer id_licencia) {
        return cat_LicenciaRepository.findById(id_licencia).get();
    }

    @Override
    public Cat_Licencia updateLicencia(Integer id_licencia, Cat_LicenciaDTO licencia) {
        Cat_Licencia licen = this.cat_LicenciaRepository.findById(id_licencia).get();
        Cat_Licencia licencias = licen;
        licencias.setTipo_licencia(licencia.getTipo_licencia());
        licencias.setEstatus(1);
        return cat_LicenciaRepository.save(licencias);
    }

    @Override
    public Cat_Licencia estatusLicencia(Integer id_licencia, Integer estatus) {

        Cat_Licencia licencia = cat_LicenciaRepository.findById(id_licencia).get();
        licencia.setEstatus(estatus);

        return cat_LicenciaRepository.save(licencia);
    }

    @Override
    public void deleteLicencia(Integer id_licencia) {
        cat_LicenciaRepository.deleteById(id_licencia);
    }

    // ******************* CATALOGO CONTRATO ******************
    @Override
    public List<Cat_Tipo_Contrato> findAllContrato() {
        return cat_Tipo_ContratoRepository.findAll().stream().map(cat_tipo_contrato -> modelMapper.map(cat_tipo_contrato, Cat_Tipo_Contrato.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Tipo_Contrato> findAllDatosContrato() { //Listar todos los datos
        return cat_Tipo_ContratoRepository.findAll().stream().map(cat_tipo_contrato -> modelMapper.map(cat_tipo_contrato, Cat_Tipo_Contrato.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Tipo_Contrato saveContrato(Cat_Tipo_Contrato cat_tipo_contrato) {
        return cat_Tipo_ContratoRepository.save(cat_tipo_contrato);
    }

    // ******************* CATALOGO SANGRE ******************
    @Override
    public List<Cat_SangreDTO> findAllSangre() {
        return cat_SangreRepository.findAll().stream().map(cat_sangre -> modelMapper.map(cat_sangre, Cat_SangreDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Sangre> findAllDatosSangre() { //Listar todos los datos
        return cat_SangreRepository.findAll().stream().map(cat_sangre -> modelMapper.map(cat_sangre, Cat_Sangre.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Sangre saveSangre(Cat_Sangre cat_Sangre) {
        return cat_SangreRepository.save(cat_Sangre);
    }

    @Override
    public Cat_Sangre saveSangre(Cat_SangreDTO cat_Sangre) {
        Cat_Sangre tipoSangre = new Cat_Sangre();
        tipoSangre.setTipo_sangre(cat_Sangre.getTipo_sangre());
        tipoSangre.setEstatus(1);
        return cat_SangreRepository.save(tipoSangre);
    }

    @Override
    public Cat_Sangre updateSangre(Integer id_sangre, Cat_SangreDTO cat_Sangre) {
        Cat_Sangre bload = this.cat_SangreRepository.findById(id_sangre).get();
        Cat_Sangre sangre = bload;
        sangre.setTipo_sangre(cat_Sangre.getTipo_sangre());
        sangre.setEstatus(1);
        return cat_SangreRepository.save(sangre);
    }

    @Override
    public Cat_Sangre estatusSangre(Integer id_sangre, Integer estatus) {
        Cat_Sangre sangre = cat_SangreRepository.findById(id_sangre).get();
        sangre.setEstatus(estatus);

        return cat_SangreRepository.save(sangre);
    }

    @Override
    public Cat_Sangre findOneSangre(Integer id_sangre) {
        return cat_SangreRepository.findById(id_sangre).get();
    }

    // ******************* CATALOGO TABULADOR SUELDO ******************
    @Override
    public List<Cat_Tabulador_SueldoDTO> findAllTabulador_Sueldo() {
        return cat_Tabulador_SueldoRepository.findAll().stream().map(cat_TabuladorSueldo -> modelMapper.map(cat_TabuladorSueldo, Cat_Tabulador_SueldoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Tabulador_Sueldo> findAllDatosTabulador_Sueldo() { //Listar todos los datos
        return cat_Tabulador_SueldoRepository.findAll().stream().map(cat_TabuladorSueldo -> modelMapper.map(cat_TabuladorSueldo, Cat_Tabulador_Sueldo.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Tabulador_Sueldo saveTabulador_Sueldo(Cat_Tabulador_Sueldo cat_Tabulador_Sueldo) {
        return cat_Tabulador_SueldoRepository.save(cat_Tabulador_Sueldo);
    }

    // ******************* CATALOGO TIPO DE MOVIMIENTO PARA IDSE SUA ******************
    @Override
    public List<Cat_Tipo_Mov_Idse_Sua> findAllTipoMovImss() {
        return cat_Tipo_Mov_Idse_SuaRepository.findAll().stream().map(cat_Tipo_Mov_Idse_Sua -> modelMapper.map(cat_Tipo_Mov_Idse_Sua, Cat_Tipo_Mov_Idse_Sua.class)).collect(Collectors.toList());
    }

    // ******************* CATALOGO BENEFICIARIO ******************
    @Override
    public List<Cat_BeneficiarioDTO> findAllBeneficiario() {
        return cat_BeneficiarioRepository.findAll().stream().map(cat_Beneficiario -> modelMapper.map(cat_Beneficiario, Cat_BeneficiarioDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Beneficiario> findAllDatosBeneficiario() { //Listar todos los datos
        return cat_BeneficiarioRepository.findAll().stream().map(cat_Beneficiario -> modelMapper.map(cat_Beneficiario, Cat_Beneficiario.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Beneficiario saveBeneficiario(Cat_Beneficiario cat_Beneficiario) {
        return cat_BeneficiarioRepository.save(cat_Beneficiario);
    }

    // ******************* HISTORICO DE CREDITO INFONAVIT ******************
    @Override
    public Historico_Credito_Infonavit saveHistoricoCredito(Historico_Credito_Infonavit historico_Credito_Infonavit) {
        return historico_Credito_InfonavitRepository.save(historico_Credito_Infonavit);
    }

    // ******************* CATALOGO TABULADOR ******************
    @Override
    public List<Cat_TabuladorDTO> findAllTabulador() {
        return cat_TabuladorRepository.findAll().stream().map(cat_Tabulador -> modelMapper.map(cat_Tabulador, Cat_TabuladorDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Tabulador> findAllDatosTabulador() { //Listar todos los datos
        return cat_TabuladorRepository.findAll().stream().map(cat_Tabulador -> modelMapper.map(cat_Tabulador, Cat_Tabulador.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Tabulador findOneTabulador(Integer id) {
        return cat_TabuladorRepository.findById(id).get();
    }

    @Override
    public Cat_Tabulador saveTabulador(Cat_Tabulador cat_Tabulador) {
        Cat_Tabulador t = new Cat_Tabulador();
        t.setDesc_tipo_tabulador(cat_Tabulador.getDesc_tipo_tabulador());
        t.setClave_tipo_tabulador(cat_Tabulador.getClave_tipo_tabulador());
        t.setEstatus(1);
        return cat_TabuladorRepository.save(t);
    }

    @Override
    public Cat_Tabulador updateTabulador(Cat_Tabulador tabulador, Integer id_tipo_tabulador) {
        Cat_Tabulador t;
        t = cat_TabuladorRepository.findById(id_tipo_tabulador).get();
        t.setDesc_tipo_tabulador(tabulador.getDesc_tipo_tabulador());
        t.setClave_tipo_tabulador(tabulador.getClave_tipo_tabulador());
        t.setEstatus(1);
        return cat_TabuladorRepository.save(t);
    }

    @Override
    public Cat_Tabulador cambioEstatusTabulador(Integer id_tipo_tabulador, Integer stat) {
        Cat_Tabulador s = cat_TabuladorRepository.findById(id_tipo_tabulador).get();
        s.setEstatus(stat);
        return cat_TabuladorRepository.save(s);
    }

    //*******************************CATALOGO TIPO DE DATO PARA INCIDENCIAS
    @Override
    public List<Cat_Tipo_Dato_Incidencias> findAllCatTipoDatoIncidencias() { //Listar todos los datos
        return cat_Tipo_Dato_IncidenciasRepository.findAll().stream().map(cat_tipo_dato_incidencias -> modelMapper.map(cat_tipo_dato_incidencias, Cat_Tipo_Dato_Incidencias.class)).collect(Collectors.toList());
    }

    // ******************* CATALOGO PARENTESCO ******************
    @Override
    public List<Cat_ParentescoDTO> findAllParentesco() {
        return cat_ParentescoRepository.findAll().stream().map(cat_Parentesco -> modelMapper.map(cat_Parentesco, Cat_ParentescoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Parentesco> findAllDatosParentesco() { //Listar todos los datos
        return cat_ParentescoRepository.findAll().stream().map(cat_Parentesco -> modelMapper.map(cat_Parentesco, Cat_Parentesco.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Parentesco findOneParentesco(Integer id) {
        return cat_ParentescoRepository.findById(id).get();
    }

    @Override
    public Cat_Parentesco saveParentesco(Cat_Parentesco cat_Parentesco) {
        Cat_Parentesco p = new Cat_Parentesco();
        p.setDesc_parentesco(cat_Parentesco.getDesc_parentesco());
        p.setEstatus(1);
        return cat_ParentescoRepository.save(p);
    }

    @Override
    public Cat_Parentesco updateParentesco(Cat_Parentesco parentesco, Integer id_parentesco) {
        Cat_Parentesco p = cat_ParentescoRepository.findById(id_parentesco).get();
        p.setDesc_parentesco(parentesco.getDesc_parentesco());
        p.setEstatus(1);
        return cat_ParentescoRepository.save(p);
    }

    @Override
    public Cat_Parentesco cambioEstatusParentesco(Integer id_parentesco, Integer activo) {
        Cat_Parentesco stat = cat_ParentescoRepository.findById(id_parentesco).get();
        stat.setEstatus(activo);
        return cat_ParentescoRepository.save(stat);
    }

    // ******************* CATALOGO NACIONALIDAD ******************
    @Override
    public List<Cat_NacionalidadDTO> findAllNacionalidad() {
        return cat_NacionalidadRepository.findAll().stream().map(cat_Nacionalidad -> modelMapper.map(cat_Nacionalidad, Cat_NacionalidadDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Nacionalidad> findAllDatosNacionalidad() { //Listar todos los datos
        return cat_NacionalidadRepository.findAll().stream().map(cat_Nacionalidad -> modelMapper.map(cat_Nacionalidad, Cat_Nacionalidad.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Nacionalidad saveNacionalidad(Cat_Nacionalidad cat_Nacionalidad) {
        return cat_NacionalidadRepository.save(cat_Nacionalidad);
    }

    @Override
    public Cat_Nacionalidad saveNacionalidad(Cat_NacionalidadDTO cat_Nacionalidad) {
        Cat_Nacionalidad tipoNacionalidad = new Cat_Nacionalidad();
        tipoNacionalidad.setDesc_nacionalidad(cat_Nacionalidad.getDesc_nacionalidad());
        tipoNacionalidad.setEstatus(1);
        return cat_NacionalidadRepository.save(tipoNacionalidad);
    }

    @Override
    public Cat_Nacionalidad findOneNacionalidad(Integer id_nacionalidad) {
        return cat_NacionalidadRepository.findById(id_nacionalidad).get();
    }

    @Override
    public Cat_Nacionalidad updateNacionalidad(Integer id_nacionalidad, Cat_NacionalidadDTO cat_Nacionalidad) {
        Cat_Nacionalidad nacion = cat_NacionalidadRepository.findById(id_nacionalidad).get();
        Cat_Nacionalidad nacionalidad = nacion;
        nacionalidad.setDesc_nacionalidad(cat_Nacionalidad.getDesc_nacionalidad());
        nacionalidad.setEstatus(1);
        return cat_NacionalidadRepository.save(nacion);
    }

    @Override
    public Cat_Nacionalidad estatusNacionalidad(Integer id_nacionalidad, Integer estatus) {
        Cat_Nacionalidad nacionalidad = cat_NacionalidadRepository.findById(id_nacionalidad).get();
        nacionalidad.setEstatus(estatus);
        return cat_NacionalidadRepository.save(nacionalidad);
    }

    // ******************* CATALOGO SI NO ******************
    @Override
    public List<Cat_Si_NoDTO> findAllSiNo() {
        return cat_Si_NoRepository.findAll().stream().map(cat_Si_No -> modelMapper.map(cat_Si_No, Cat_Si_NoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Si_No> findAllDatosSiNo() { //Listar todos los datos
        return cat_Si_NoRepository.findAll().stream().map(cat_Si_No -> modelMapper.map(cat_Si_No, Cat_Si_No.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Si_No saveSiNo(Cat_Si_No cat_Si_No) {
        return cat_Si_NoRepository.save(cat_Si_No);
    }

    @Override
    public Cat_Si_No updateSiNo(Integer id, Cat_Si_NoDTO cat_Si_No) {
        Cat_Si_No desc = this.cat_Si_NoRepository.findById(id).get();
        Cat_Si_No descripcion = desc;
        descripcion.setDescripcion(cat_Si_No.getDescripcion());
        descripcion.setEstatus(1);
        return cat_Si_NoRepository.save(descripcion);
    }

    @Override
    public Cat_Si_No findOneSiNo(Integer id) {
        return cat_Si_NoRepository.findById(id).get();
    }

    @Override
    public Cat_Si_No estatusSiNo(Integer id, Integer estatus) {
        Cat_Si_No descripcion = cat_Si_NoRepository.findById(id).get();
        descripcion.setEstatus(estatus);
        return cat_Si_NoRepository.save(descripcion);
    }
//************************* CATALOGO BUSCAR CP ***************************

    @Override
    public List<Cat_Colonia> findByCod_Postal(String cod_postal) {
        return cat_ColoniaRepository.findByCod_Postal(cod_postal);
    }

//************** CATALOGO PARA EXPEDIENTES***************************
    @Override
    public Integer idExpediente(String numero_expediente) {
        return cat_ExpedienteRepository.idExpediente(numero_expediente);
    }

    @Override
    public List<Cat_ExpedienteDTO> findAllExpedientes() {
        return cat_ExpedienteRepository.findAllLibres().stream().map(cat_Expediente -> modelMapper.map(cat_Expediente, Cat_ExpedienteDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Expediente> findAllExp() {
        return cat_ExpedienteRepository.findAll();
    }

    @Override
    public Cat_Expediente activo(Integer id_expediente, Integer estatus) {
        Cat_Expediente exp = cat_ExpedienteRepository.findById(id_expediente).get();
        exp.setEstatus(estatus);

        return cat_ExpedienteRepository.save(exp);
    }

    @Override
    public Cat_Expediente saveExpediente(Cat_NumExpedienteDTO expediente) {
        Cat_Expediente exp = new Cat_Expediente();

        exp.setNumero_expediente(expediente.getNumero_expediente());
        exp.setAsignado(1);
        exp.setEstatus(1);
        LocalDate datetime = LocalDate.now();
        exp.setFecha_registro(datetime);
        return cat_ExpedienteRepository.save(exp);
    }

    @Override
    public Cat_Expediente existsByNumExpediente(String numero_expediente) {
        return cat_ExpedienteRepository.existsByNumExpediente(numero_expediente);
    }

    @Override
    public Cat_Expediente update(Integer id, Cat_NumExpedienteDTO expediente) {
        Cat_Expediente exp = this.cat_ExpedienteRepository.findById(id).get();
        Cat_Expediente expedientes = exp;

        expedientes.setAsignado(expediente.getAsignado());
        return cat_ExpedienteRepository.save(expedientes);
    }

    @Override
    public Cat_Expediente ultimoExpediente() {
        return cat_ExpedienteRepository.ultimoExpediente();
    }

    @Override
    public Integer ultimoIdExpediente() {
        return cat_ExpedienteRepository.ultimoIdExpediente();
    }

    @Override
    public List<Cat_Parentesco> findAllCatalogo_Parentesco() {
        return cat_ParentescoRepository.findAll().stream().map(Cat_Parentesco -> modelMapper.map(Cat_Parentesco, Cat_Parentesco.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Tipo_Beneficiario> findAllCatalogo_TipoBeneficiario() {
        return cat_Tipo_BeneficiarioRepository.findAll().stream().map(Cat_Tipo_Beneficiario -> modelMapper.map(Cat_Tipo_Beneficiario, Cat_Tipo_Beneficiario.class)).collect(Collectors.toList());
    }

    //************************* CATALOGO BUSCAR BANCOS ***************************
    @Override
    public List<Cat_BancoDTO> findAllBanco() {
        return cat_BancoRepository.findAll().stream().map(cat_banco -> modelMapper.map(cat_banco, Cat_BancoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Banco> findAllDatosBanco() { //Listar todos los datos
        return cat_BancoRepository.findAll().stream().map(cat_banco -> modelMapper.map(cat_banco, Cat_Banco.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Banco findOneBanco(Integer id) {
        return cat_BancoRepository.findById(id).get();
    }

    @Override
    public Cat_Banco saveBanco(Cat_Banco cat_Banco) {
        Cat_Banco banco = new Cat_Banco();
        banco.setNombre_banco(cat_Banco.getNombre_banco());
        banco.setComision(cat_Banco.getComision());
        banco.setEstatus(1);
        return cat_BancoRepository.save(banco);
    }

    @Override
    public Cat_Banco updateBanco(Cat_Banco banco, Integer id_banco) {
        Cat_Banco b = cat_BancoRepository.findById(id_banco).get();
        b.setNombre_banco(banco.getNombre_banco());
        b.setEstatus(banco.getEstatus());
        b.setComision(banco.getComision());
        b.setEstatus(1);
        return cat_BancoRepository.save(b);
    }

    @Override
    public Cat_Banco cambioEstatusBanco(Integer id_banco, Integer activo) {
        Cat_Banco stat = cat_BancoRepository.findById(id_banco).get();
        stat.setEstatus(activo);
        return cat_BancoRepository.save(stat);
    }

    @Override
    public Integer findMaxComision(Integer id_banco) {
        return cat_BancoRepository.findMaxComision(id_banco);
    }

    //************************* CATALOGO puestos ***************************
    @Override
    public List<Cat_Puesto> findAllPuesto() {
        return cat_PuestoRepository.findAll().stream().map(Cat_Puesto -> modelMapper.map(Cat_Puesto, Cat_Puesto.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Puesto savePuesto(Cat_Puesto puesto) {
        LocalDate datetime = LocalDate.now();
        puesto.setFecha_movimiento(datetime);
        puesto.setFecha_captura(datetime);
        return cat_PuestoRepository.save(puesto);
    }

    @Override
    public Cat_Puesto findCodigoPuesto(Integer codigo_puesto) {
        return cat_PuestoRepository.findCodigoPuesto(codigo_puesto);
    }

    @Override
    public Cat_Puesto editarCatPuesto(Cat_PuestoDTO puesto, Integer codigo_puesto) {
        Cat_Puesto cat_P = this.cat_PuestoRepository.findCodigoPuesto(codigo_puesto);
        Cat_Puesto pt = cat_P;

//      System.out.println("TIPOOO ... " + puesto.getDif_cantAdicMens());
        pt.setCodigo_puesto(puesto.getCodigo_puesto());
        pt.setNivel(puesto.getNivel());
        pt.setPuesto(puesto.getPuesto());
        pt.setSueldo_diario(puesto.getSueldo_diario());
        pt.setSueldo_diario_doble(puesto.getSueldo_diarioDoble());
        pt.setSueldo_hora(puesto.getSueldo_hora());
        pt.setSueldo_hora_doble(puesto.getSueldo_horaDoble());
        pt.setSueldo_hora_triple(puesto.getSueldo_horaTriple());
        pt.setSueldo_mensual(puesto.getSueldo_mensual());
        pt.setCantidad_adicional_mensual(puesto.getCantidad_adicionalMensual());
        pt.setNum_plazas_autorizadas(puesto.getNum_plazasAutorizadas());
        pt.setSueldo_quincenal_tabulado(puesto.getSueldo_quincenalTabulado());
        pt.setDif_sueldo_diario(puesto.getDif_sueldoDiario());
        pt.setDif_sueldo_hora(puesto.getDif_sueldoHora());
        pt.setDif_sueldo_mensual(puesto.getDif_sueldoMensual());
        pt.setDif_cant_adicmens(puesto.getDif_cantAdicMens()); //difCantAdMensual
        pt.setIsr_mens_prest(puesto.getIsr_mensPrest());
        pt.setIsr_mensual(puesto.getIsr_mensual());
        pt.setCuota_imss(puesto.getCuota_imss());
        pt.setSueldo_mensual_neto(puesto.getSueldo_mensualNeto());
        pt.setCodigo_rh(puesto.getCodigo_rh());
        pt.setSueldo_estructura(puesto.getSueldo_estructura());
        pt.setSueldoHora7(puesto.getSueldoHora7());
        LocalDate datetime = LocalDate.now();
        pt.setFecha_movimiento(datetime);
        return cat_PuestoRepository.save(pt);
    }

    @Override
    public Cat_Puesto existsByCodPuesto(Integer codigo_puesto) {
        return cat_PuestoRepository.existsByCodPuesto(codigo_puesto);
    }

    @Override
    public Cat_Puesto findByIdPuesto(Integer id_puesto) {
        return cat_PuestoRepository.findByIdPuesto(id_puesto);
    }
    //*********************** CATALOGO PLAZA ***************************

    @Override
    public List<Cat_Plaza> findAllPlaza() {
        return cat_PlazaRepository.findAll();
    }

    @Override
    public List<Cat_Plaza> findAllPlaza(Integer puesto_id) {
        return cat_PlazaRepository.findAllPlaza(puesto_id);
    }

    @Override
    public Integer countPlazas(Integer puesto_id) {
        return cat_PlazaRepository.countPlazas(puesto_id);
    }

    @Override
    public Integer countPlazasDisponibles(Integer puesto_id) {
        return cat_PlazaRepository.countPlazasDisponibles(puesto_id);
    }

    @Override
    public Integer countPlazasAsignadas(Integer puesto_id) {
        return cat_PlazaRepository.countPlazasAsignadas(puesto_id);
    }

    @Override
    public Cat_Plaza savePlaza(Cat_Plaza plaza) {
        Cat_Plaza cat_plaza = new Cat_Plaza();

        cat_plaza.setEstatus_plaza_id(3);
        cat_plaza.setNumero_plaza(plaza.getNumero_plaza());
        cat_plaza.setCat_puesto(plaza.getCat_puesto());
        return cat_PlazaRepository.save(cat_plaza);
    }

    @Override
    public Cat_Plaza editarCatPlaza(Cat_Plaza plaza, Integer id_plaza) {
        Cat_Plaza p = this.cat_PlazaRepository.findById(id_plaza).get();
        Cat_Plaza pl = p;

        pl.setEstatus_plaza_id(plaza.getEstatus_plaza_id());

        return cat_PlazaRepository.save(pl);
    }

    @Override
    public Cat_Plaza findOnePlaza(Integer id_plaza) {
        return cat_PlazaRepository.findById(id_plaza).get();
    }

    @Override
    public Cat_Puesto cambioEstatus(Integer id_puesto, Integer activo) {
        Cat_Puesto puesto = cat_PuestoRepository.findById(id_puesto).get();
        puesto.setStatus(activo);

        return cat_PuestoRepository.save(puesto);
    }

    @Override
    public Cat_Plaza cambioEstatusPlaza(Integer id_plaza, Integer activo) {
        Cat_Plaza plaza = cat_PlazaRepository.findById(id_plaza).get();
        plaza.setEstatus_plaza_id(activo);

        return cat_PlazaRepository.save(plaza);
    }

    @Override
    public Integer ultimoId() {
        return cat_PlazaRepository.ultimoId();
    }

    @Override
    public List<Cat_Plaza> findPlazasDisponibles(Integer puesto_id) {
        return cat_PlazaRepository.findPlazasDisponibles(puesto_id);
    }

    //*********************** CATALOGO CONTRATO NOMINA ***************************
    @Override
    public List<Cat_Contrato_Nomina> findAllContratoNomina() {
        return cat_Contrato_NominaRepository.findAll().stream().map(Cat_Contrato_Nomina -> modelMapper.map(Cat_Contrato_Nomina, Cat_Contrato_Nomina.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Contrato_Nomina> findIdContrato(Integer id_contrato) {
        return cat_Contrato_NominaRepository.listaIdContrato(id_contrato);
    }

    //*********************** CATALOGO NOMINA UBICACION ***************************
    @Override
    public List<Cat_Nomina_Ubicacion> findIdNomina(Integer id_nomina) {
        return cat_Nomina_UbicacionRepository.listaIdNomina(id_nomina);

    }

    // ******************* CATALOGO DIAS ******************
    @Override
    public List<Cat_DiasDTO> findAllDias() {
        return cat_DiasRepository.findAll().stream().map(cat_dias -> modelMapper.map(cat_dias, Cat_DiasDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Dias> findAllDatosDias() { //Listar todos los datos
        return cat_DiasRepository.findAll().stream().map(cat_dias -> modelMapper.map(cat_dias, Cat_Dias.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Dias saveDias(Cat_Dias cat_Dias) {
        return cat_DiasRepository.save(cat_Dias);
    }

    @Override
    public Cat_Dias findOneDia(Integer id) {
        return cat_DiasRepository.findById(id).get();
    }

    @Override
    public Cat_Dias editarDias(Cat_Dias dias, Integer id_dia) {
        Cat_Dias d = cat_DiasRepository.findById(id_dia).get();
        d.setDia(dias.getDia());
        d.setEstatus(1);
        return cat_DiasRepository.save(d);
    }

    @Override
    public Cat_Dias saveDia(Cat_Dias dia) {
        Cat_Dias d = new Cat_Dias();
        d.setDia(dia.getDia());
        d.setEstatus(1);
        return cat_DiasRepository.save(d);
    }

    @Override
    public Cat_Dias cambioEstatusDia(Integer id_dia, Integer stat) {
        Cat_Dias dias = cat_DiasRepository.findById(id_dia).get();
        dias.setEstatus(stat);
        return cat_DiasRepository.save(dias);
    }

    // ******************* HISTORICO DE PUESTOS ******************
    @Override
    public Historico_Puesto saveHistorico(Historico_Puesto historico) {
        Historico_Puesto hist = new Historico_Puesto();

        hist.setId_puesto(historico.getId_puesto());
        hist.setCodigo_puesto(historico.getCodigo_puesto());
        hist.setSueldo_mensual(historico.getSueldo_mensual());
        hist.setNivel(historico.getNivel());
        hist.setPuesto(historico.getPuesto());
        hist.setSueldo_diario(historico.getSueldo_diario());
        hist.setSueldo_diario_doble(historico.getSueldo_diario_doble());
        hist.setSueldo_hora(historico.getSueldo_hora());
        hist.setSueldo_hora_doble(historico.getSueldo_hora_doble());
        hist.setSueldo_hora_triple(historico.getSueldo_hora_triple());
        hist.setCantidad_adicional_mensual(historico.getCantidad_adicional_mensual());
        hist.setSueldo_mensual_neto(historico.getSueldo_mensual_neto());
        hist.setNum_plazas_autorizadas(historico.getNum_plazas_autorizadas());
        hist.setSueldo_quincenal_tabulado(historico.getSueldo_quincenal_tabulado());
        hist.setDif_sueldo_diario(historico.getDif_sueldo_diario());
        hist.setDif_sueldo_hora(historico.getDif_sueldo_hora());
        hist.setDif_sueldo_mensual(historico.getDif_sueldo_mensual());
        hist.setDif_cant_adicmens(historico.getDif_cant_adicmens());
        hist.setIsr_mens_prest(historico.getIsr_mens_prest());
        hist.setIsr_mensual(historico.getIsr_mensual());
        hist.setCuota_imss(historico.getCuota_imss());
        hist.setCodigo_rh(historico.getCodigo_rh());
        hist.setSueldo_estructura(historico.getSueldo_estructura());
        hist.setSueldoHora7(historico.getSueldoHora7());
        hist.setStatus(historico.getStatus());
        hist.setFecha_captura(historico.getFecha_captura());
        hist.setFecha_movimiento(historico.getFecha_movimiento());
        LocalDate fecha_hoy = LocalDate.now();
        hist.setFecha_registro(fecha_hoy);

        return historico_PuestoRepository.save(hist);
    }

    @Override
    public Historico_Trabajador_Plaza saveHistoricoTP(Historico_Trabajador_Plaza historico) {
        Historico_Trabajador_Plaza hist = new Historico_Trabajador_Plaza();

        hist.setTrabajador_plaza_id(historico.getTrabajador_plaza_id());
        hist.setPlaza_id(historico.getPlaza_id());
        hist.setTrabajador_id(historico.getTrabajador_id());
        hist.setAreas_id(historico.getAreas_id());
        hist.setTipo_contrato_id(historico.getTipo_contrato_id());
        hist.setTipo_nomina_id(historico.getTipo_nomina_id());
        hist.setUbicacion_id(historico.getUbicacion_id());
        hist.setPlaza_movimientos_id(historico.getPlaza_movimientos_id());
        hist.setObservaciones(historico.getObservaciones());
        hist.setFecha_inicial(historico.getFecha_inicial());
        hist.setFecha_final(historico.getFecha_final());
        LocalDate fecha_hoy = LocalDate.now();
        hist.setFecha_registro(fecha_hoy);

        return historico_Trabajador_PlazaRepository.save(hist);
    }

// ******************* CATALOGO DIAS VACACIONES ******************
    @Override
    public Integer diasDisponibles(Integer tipo_dias_vacaciones_id, Double antiguedad) {
        return cat_Dias_VacacionesRepository.diasDisponibles(tipo_dias_vacaciones_id, antiguedad);
    }

    // ******************* CATALOGO INICIDENCIAS ******************
    @Override
    public List<Cat_IncidenciasDTO> findAllIncidencias() {
        return cat_incidenciasRepository.findAll().stream().map(cat_incidencias -> modelMapper.map(cat_incidencias, Cat_IncidenciasDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Incidencias> findAllDatosIncidencias() {
        return cat_incidenciasRepository.findAll().stream().map(cat_incidencias -> modelMapper.map(cat_incidencias, Cat_Incidencias.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Incidencias> findIncID(Integer tipo_incidencia_id) {
        return cat_incidenciasRepository.findIncID(tipo_incidencia_id);
    }

    @Override
    public Cat_Incidencias saveCat(Cat_Incidencias cat_incidencias) {
        return cat_incidenciasRepository.save(cat_incidencias);
    }

    @Override
    public Cat_Incidencias findOneIncidencia(Integer id_incidencia) {
        return cat_incidenciasRepository.findById(id_incidencia).get();
    }

    @Override
    public List<Cat_Incidencias> findIncidencia(Integer tipo_Incidencia_id) {
        return cat_incidenciasRepository.findIncidencia(tipo_Incidencia_id);
    }

    @Override
    public List<Cat_Incidencias> findIncidenciasKardex() {
        return cat_incidenciasRepository.findIncidenciasKardex();
    }

    @Override
    public List<Cat_Incidencias> findAllDeducciones() {
        return cat_incidenciasRepository.findAllDeducciones();
    }

    @Override
    public Cat_Incidencias actualizarIncidencia(Integer id_incidencia, Cat_Incidencias incidencias) {
        Cat_Incidencias i = cat_incidenciasRepository.findById(id_incidencia).get();
        Cat_Incidencias j = i;
        j.setDescripcion(incidencias.getDescripcion());
        j.setCat_tipo_incidencia(incidencias.getCat_tipo_incidencia());
        j.setCoa_id(incidencias.getCoa_id());
        j.setInicial_descripcion(incidencias.getInicial_descripcion());
        j.setValor_binario(incidencias.getValor_binario());
        j.setInoperable(incidencias.getInoperable());
        j.setEstatus(1);
        j.setColor_kardex(incidencias.getColor_kardex());
        return cat_incidenciasRepository.save(j);
    }

    @Override
    public Cat_Incidencias saveIncidencia(Cat_Incidencias incidencias) {
        Cat_Incidencias incidencia = new Cat_Incidencias();
        incidencia.setDescripcion(incidencias.getDescripcion());
        incidencia.setCat_tipo_incidencia(incidencias.getCat_tipo_incidencia());
        incidencia.setCoa_id(incidencias.getCoa_id());
        incidencia.setInicial_descripcion(incidencias.getInicial_descripcion());
        incidencia.setValor_binario(incidencias.getValor_binario());
        incidencia.setInoperable(incidencias.getInoperable());
        incidencia.setEstatus(1);
        incidencia.setColor_kardex(incidencias.getColor_kardex());
        return cat_incidenciasRepository.save(incidencia);
    }

    @Override
    public Cat_Incidencias cambioEstatusIncidencias(Integer id_incidencia, Integer stat) {
        Cat_Incidencias incidencia = cat_incidenciasRepository.findById(id_incidencia).get();
        incidencia.setEstatus(stat);
        return cat_incidenciasRepository.save(incidencia);
    }

// ******************* CATALOGO TIPO INICIDENCIAS ******************
    @Override
    public List<Cat_Tipo_IncidenciaDTO> findAllTipo() {
        return cat_tipo_incidenciaRepository.findAll().stream().map(cat_tipo_incidencia -> modelMapper.map(cat_tipo_incidencia, Cat_Tipo_IncidenciaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Tipo_Incidencia> findDatosTipo() {
        return cat_tipo_incidenciaRepository.findAll().stream().map(cat_tipo_incidencia -> modelMapper.map(cat_tipo_incidencia, Cat_Tipo_Incidencia.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Tipo_Incidencia> findTipoID(Integer tipo_incidencia_id) {
        return cat_tipo_incidenciaRepository.findTipoID(tipo_incidencia_id);
    }

    @Override
    public Cat_Tipo_Incidencia saveTipo(Cat_Tipo_Incidencia cat_tipo_incidencia) {
        Cat_Tipo_Incidencia ctp = new Cat_Tipo_Incidencia();
        ctp.setDescripcion(cat_tipo_incidencia.getDescripcion());
        ctp.setEstatus(1);
        return cat_tipo_incidenciaRepository.save(ctp);
    }

    @Override
    public Cat_Tipo_Incidencia cambioEstatusTipoIncidencias(Integer id_tipo_incidencia, Integer stat) {
        Cat_Tipo_Incidencia Tipoincidencia = cat_tipo_incidenciaRepository.findById(id_tipo_incidencia).get();
        Tipoincidencia.setEstatus(stat);
        return cat_tipo_incidenciaRepository.save(Tipoincidencia);
    }

// ******************* CATALOGO ESTADO INICIDENCIAS ******************
    @Override
    public List<Cat_Estado_IncidenciaDTO> findEstadoInc() {
        return cat_estado_incidenciaRepository.findAll().stream().map(cat_estado_incidencia -> modelMapper.map(cat_estado_incidencia, Cat_Estado_IncidenciaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Estado_Incidencia> findDatosEstadoInc() {
        return cat_estado_incidenciaRepository.findAll().stream().map(cat_estado_incidencia -> modelMapper.map(cat_estado_incidencia, Cat_Estado_Incidencia.class)).collect(Collectors.toList());
    }
// ******************* CATALOGO MOTIVO CANCELACION ******************

    @Override
    public List<Cat_Motivo_Cancelacion> findAllMotivoCancelacion() {
        return cat_Motivo_CancelacionRepository.findAll().stream().map(cat_motivo_cancelacion -> modelMapper.map(cat_motivo_cancelacion, Cat_Motivo_Cancelacion.class)).collect(Collectors.toList());
    }
//*********************** CATALOGO DEPÓSITOS ***************************

    @Override
    public Cat_Depositos findOneDeposito(Integer id) {
        return cat_DepositosRepository.findById(id).get();
    }

    @Override
    public Cat_Depositos saveDeposito(Cat_Depositos depositos) {
        Cat_Depositos d = new Cat_Depositos();
        d.setDesc_deposito(depositos.getDesc_deposito());
        d.setEstatus(1);
        return cat_DepositosRepository.save(d);
    }

    @Override
    public List<Cat_Depositos> findAllDepositos() {
        return cat_DepositosRepository.findAll().stream().map(Cat_Depositos -> modelMapper.map(Cat_Depositos, Cat_Depositos.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Depositos editarDepositos(Cat_Depositos depo, Integer id_deposito) {
        Cat_Depositos d = cat_DepositosRepository.findById(id_deposito).get();
        d.setDesc_deposito(depo.getDesc_deposito());
        d.setEstatus(1);

        return cat_DepositosRepository.save(d);
    }

    @Override
    public Cat_Depositos cambioEstatusDeposito(Integer id_deposito, Integer stat) {
        Cat_Depositos d = cat_DepositosRepository.findById(id_deposito).get();
        d.setEstatus(stat);
        return cat_DepositosRepository.save(d);
    }
//************************* CATALOGO DIAS FESTIVOS ***************************

    @Override
    public Cat_Dias_Festivos findOneDiaFestivo(Integer id_festivos) {
        return cat_Dias_FestivosRepository.findById(id_festivos).get();
    }

    @Override
    public List<Cat_Dias_Festivos> findAllDatosDiasFestivos() { //Listar todos los datos
        return cat_Dias_FestivosRepository.findAll().stream().map(cat_Dias_Festivos -> modelMapper.map(cat_Dias_Festivos, Cat_Dias_Festivos.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Dias_Festivos saveDiaFestivo(Cat_Dias_Festivos diaFestivo) {
        Cat_Dias_Festivos df = new Cat_Dias_Festivos();
        df.setDescripcion(diaFestivo.getDescripcion());
        df.setEstatus(1);
        df.setCat_Tipo_Nomina(diaFestivo.getCat_Tipo_Nomina());
        df.setFecha(diaFestivo.getFecha());
        df.setContrato_colectivo(diaFestivo.getContrato_colectivo());
        return cat_Dias_FestivosRepository.save(df);
    }

    @Override
    public Cat_Dias_Festivos updateDiaFestivo(Cat_Dias_Festivos diaFestivo, Integer id_festivos) {
        Cat_Dias_Festivos df = cat_Dias_FestivosRepository.findById(id_festivos).get();
        df.setDescripcion(diaFestivo.getDescripcion());
        df.setEstatus(1);
        df.setCat_Tipo_Nomina(diaFestivo.getCat_Tipo_Nomina());
        df.setFecha(diaFestivo.getFecha());
        df.setContrato_colectivo(diaFestivo.getContrato_colectivo());
        return cat_Dias_FestivosRepository.save(df);
    }

    @Override
    public Cat_Dias_Festivos cambioEstatusFestivo(Integer id_festivos, Integer stat) {
        Cat_Dias_Festivos s = cat_Dias_FestivosRepository.findById(id_festivos).get();
        s.setEstatus(stat);
        return cat_Dias_FestivosRepository.save(s);
    }

    @Override
    public List<Cat_Dias_Festivos> findDiasNomina(Integer nomina_id) {
        return cat_Dias_FestivosRepository.findDiasNomina(nomina_id);
    }

    @Override
    public List<Cat_Dias_Festivos> findDiasFestivos(Integer nomina_id, Date fecha) {
        return cat_Dias_FestivosRepository.findDiasFestivos(nomina_id, fecha);
    }

//******************* CATALOGO TIPO NOMINA *************************
    @Override
    public List<Cat_Tipo_Nomina> findAllTipoNomina() {
        return cat_Tipo_NominaRepository.findAll().stream().map(cat_Tipo_Nomina -> modelMapper.map(cat_Tipo_Nomina, Cat_Tipo_Nomina.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Tipo_Nomina findByIdNomina(Integer id_tipo_nomina) {
        return cat_Tipo_NominaRepository.findByIdNomina(id_tipo_nomina);
    }
// ******************* CATALOGO HORARIO ******************

    @Override
    public Cat_Horario findOneHorario(Integer id) {
        return cat_HorarioRepository.findById(id).get();
    }

    @Override
    public List<Cat_Horario> findAllHorarios() { //Listar todos los datos
        return cat_HorarioRepository.findAll().stream().map(Cat_Horario -> modelMapper.map(Cat_Horario, Cat_Horario.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Horario saveHorario(Cat_Horario horario) {
        return cat_HorarioRepository.save(horario);
    }

    @Override
    public Cat_Horario editarHorarios(Cat_Horario horario, Integer id_horario) {
        Cat_Horario h = cat_HorarioRepository.findById(id_horario).get();
        h.setEstatus(horario.getEstatus());
        h.setCa(horario.getCa());
        h.setHoras_acumuladas(horario.getHoras_acumuladas());
        h.setLunes(horario.getLunes());
        h.setMartes(horario.getMartes());
        h.setMiercoles(horario.getMiercoles());
        h.setJueves(horario.getJueves());
        h.setViernes(horario.getViernes());
        h.setSabado(horario.getSabado());
        h.setDomingo(horario.getDomingo());
        return cat_HorarioRepository.save(h);
    }

    @Override
    public Cat_Horario cambioEstatusHorario(Integer id_horario, Integer stat) {
        Cat_Horario s = cat_HorarioRepository.findById(id_horario).get();
        s.setEstatus(stat);
        return cat_HorarioRepository.save(s);
    }
    // ******************* CATÁLOGO PLAZA MOVIMIENTOS ****************   

    @Override
    public List<Cat_Plaza_MovimientosDTO> findAllPlazaMovimientos() {
        return cat_Plaza_MovimientosRepository.findAll().stream().map(cat_plazaMovimientos -> modelMapper.map(cat_plazaMovimientos, Cat_Plaza_MovimientosDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Plaza_Movimientos> findAllDatosPlazaMovimientos() {
        return cat_Plaza_MovimientosRepository.findAll().stream().map(cat_plazaMovimientos -> modelMapper.map(cat_plazaMovimientos, Cat_Plaza_Movimientos.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Plaza_Movimientos savePlazaMovimientos(Cat_Plaza_Movimientos cat_Plaza_Movimientos) {
        Cat_Plaza_Movimientos pm = new Cat_Plaza_Movimientos();
        pm.setClave_movimiento(cat_Plaza_Movimientos.getClave_movimiento());
        pm.setDescripcion(cat_Plaza_Movimientos.getDescripcion());
        pm.setEstatus(1);
        return cat_Plaza_MovimientosRepository.save(pm);
    }

    @Override
    public Cat_Plaza_Movimientos updatePlazaMovimientos(Integer id_plaza_movimientos, Cat_Plaza_MovimientosDTO cat_Plaza_Movimientos) {
        Cat_Plaza_Movimientos move = this.cat_Plaza_MovimientosRepository.findById(id_plaza_movimientos).get();
        Cat_Plaza_Movimientos moves = move;
        moves.setDescripcion(cat_Plaza_Movimientos.getDescripcion());
        moves.setClave_movimiento(cat_Plaza_Movimientos.getClave_movimiento());
        moves.setEstatus(1);
        return cat_Plaza_MovimientosRepository.save(moves);
    }

    @Override
    public Cat_Plaza_Movimientos estatusPlazaMovimientos(Integer id_plaza_movimientos, Integer estatus) {
        Cat_Plaza_Movimientos pm = cat_Plaza_MovimientosRepository.findById(id_plaza_movimientos).get();
        pm.setEstatus(estatus);
        return cat_Plaza_MovimientosRepository.save(pm);
    }

    @Override
    public Cat_Plaza_Movimientos findOnePlazaMovimientos(Integer id_plaza_movimientos) {
        return cat_Plaza_MovimientosRepository.findById(id_plaza_movimientos).get();
    }
//******************* CATALOGO TIPO BENEFICIARIO *************************

    @Override
    public List<Cat_Tipo_BeneficiarioDTO> findAllTipoBeneficiario() {
        return cat_Tipo_BeneficiarioRepository.findAll().stream().map(cat_Tipo_Beneficiario -> modelMapper.map(cat_Tipo_Beneficiario, Cat_Tipo_BeneficiarioDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Tipo_Beneficiario> findAllDatosTipoBeneficiario() {
        return cat_Tipo_BeneficiarioRepository.findAll().stream().map(cat_Tipo_Beneficiario -> modelMapper.map(cat_Tipo_Beneficiario, Cat_Tipo_Beneficiario.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Tipo_Beneficiario saveTipoBeneficiario(Cat_Tipo_Beneficiario cat_Tipo_Beneficiario) {
        Cat_Tipo_Beneficiario b = new Cat_Tipo_Beneficiario();
        b.setDesc_tipo_beneficiario(cat_Tipo_Beneficiario.getDesc_tipo_beneficiario());
        b.setDesc_tipo_tabulador(cat_Tipo_Beneficiario.getDesc_tipo_tabulador());
        b.setEstatus(1);
        return cat_Tipo_BeneficiarioRepository.save(b);
    }

    @Override
    public Cat_Tipo_Beneficiario updateTipoBeneficiario(Integer id_tipo_beneficiario, Cat_Tipo_BeneficiarioDTO cat_Tipo_Beneficiario) {
        Cat_Tipo_Beneficiario ben = cat_Tipo_BeneficiarioRepository.findById(id_tipo_beneficiario).get();
        ben.setDesc_tipo_beneficiario(cat_Tipo_Beneficiario.getDesc_tipo_beneficiario());
        ben.setEstatus(1);
        return cat_Tipo_BeneficiarioRepository.save(ben);
    }

    @Override
    public Cat_Tipo_Beneficiario estatusTipoBeneficiario(Integer id_tipo_beneficiario, Integer estatus) {
        Cat_Tipo_Beneficiario ben = cat_Tipo_BeneficiarioRepository.findById(id_tipo_beneficiario).get();
        ben.setEstatus(estatus);
        return cat_Tipo_BeneficiarioRepository.save(ben);
    }

    @Override
    public Cat_Tipo_Beneficiario findOneTipoBeneficiario(Integer id_tipo_beneficiario) {
        return cat_Tipo_BeneficiarioRepository.findById(id_tipo_beneficiario).get();
    }
// ******************* CATALOGO TIPO DOCUMENTO ******************

    @Override
    public Cat_Tipo_Documento findOneDocumento(Integer id) {
        return cat_Tipo_DocumentoRepository.findById(id).get();
    }

    @Override
    public List<Cat_Tipo_Documento> findAllDatosDocumentos() { //Listar todos los datos
        return cat_Tipo_DocumentoRepository.findAll().stream().map(cat_Tipo_Documento -> modelMapper.map(cat_Tipo_Documento, Cat_Tipo_Documento.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Tipo_Documento saveDocumento(Cat_Tipo_Documento documento) {
        Cat_Tipo_Documento td = new Cat_Tipo_Documento();
        td.setDocumento(documento.getDocumento());
        td.setEstatus(1);
        return cat_Tipo_DocumentoRepository.save(td);
    }

    @Override
    public Cat_Tipo_Documento updateDocumentos(Cat_Tipo_Documento documento, Integer id_tipo_documento) {
        Cat_Tipo_Documento d = cat_Tipo_DocumentoRepository.findById(id_tipo_documento).get();
        d.setDocumento(documento.getDocumento());
        d.setEstatus(1);
        return cat_Tipo_DocumentoRepository.save(d);
    }

    @Override
    public Cat_Tipo_Documento cambioEstatusDocumentos(Integer id_tipo_documento, Integer stat) {
        Cat_Tipo_Documento d = cat_Tipo_DocumentoRepository.findById(id_tipo_documento).get();
        d.setEstatus(stat);
        return cat_Tipo_DocumentoRepository.save(d);
    }
//******************** CATALOGO MUNICIPIO ************************    

    @Override
    public List<Cat_MunicipioDTO> findAllMunicipio() {
        return cat_MunicipioRepository.findAll().stream().map(cat_municipio -> modelMapper.map(cat_municipio, Cat_MunicipioDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Municipio> findAllDatosMunicipio() {
        return cat_MunicipioRepository.findAll().stream().map(cat_municipio -> modelMapper.map(cat_municipio, Cat_Municipio.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Municipio> findEstadoMunicipio(Integer id_estado) {
        return cat_MunicipioRepository.findEstadoMunicipio(id_estado);
    }

    @Override
    public Cat_Municipio saveMunicipio(Cat_Municipio municipio) {
        Cat_Municipio mun = new Cat_Municipio();
        mun.setCat_estado(municipio.getCat_estado());
        mun.setDesc_municipio(municipio.getDesc_municipio());
        mun.setEstatus(1);

        return cat_MunicipioRepository.save(mun);
    }

    @Override
    public Cat_Municipio updateMunicipio(Cat_MunicipioDTO cat_Municipio, Integer id_municipio) {
        Cat_Municipio municipio = this.cat_MunicipioRepository.findById(id_municipio).get();
        municipio.setDesc_municipio(cat_Municipio.getDesc_municipio());
        municipio.setEstatus(1);
        return cat_MunicipioRepository.save(municipio);
    }

    @Override
    public Cat_Municipio findOneMunicipio(Integer id_municipio) {
        return cat_MunicipioRepository.findById(id_municipio).get();
    }

    @Override
    public Cat_Municipio estatusMunicipio(Integer id_municipio, Integer estatus) {
        Cat_Municipio municipio = cat_MunicipioRepository.findById(id_municipio).get();
        municipio.setEstatus(estatus);
        return cat_MunicipioRepository.save(municipio);
    }

    // ******************* CATALOGO TIPO CONTRATO ******************
    @Override
    public List<Cat_Tipo_Contrato> findAllDatosTipoContrato() { //Listar todos los datos
        return cat_Tipo_ContratoRepository.findAll().stream().map(cat_Tipo_Contrato -> modelMapper.map(cat_Tipo_Contrato, Cat_Tipo_Contrato.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Tipo_Contrato findOneTipoContrato(Integer id_tipo_contrato) {
        return cat_Tipo_ContratoRepository.findById(id_tipo_contrato).get();
    }

    @Override
    public Cat_Tipo_Contrato actualizarTipoContrato(Integer id_tipo_contrato, Cat_Tipo_Contrato tipo_Contrato) {
        Cat_Tipo_Contrato c = cat_Tipo_ContratoRepository.findById(id_tipo_contrato).get();
        c.setTipo_nomina(tipo_Contrato.getTipo_nomina());
        c.setDescripcion(tipo_Contrato.getDescripcion());
        c.setEstatus(1);
        return cat_Tipo_ContratoRepository.save(c);
    }

    @Override
    public Cat_Tipo_Contrato saveTipoContrato(Cat_Tipo_Contrato cat_Tipo_Contrato) {
        Cat_Tipo_Contrato tc = new Cat_Tipo_Contrato();
        tc.setDescripcion(cat_Tipo_Contrato.getDescripcion());
        tc.setTipo_nomina(cat_Tipo_Contrato.getTipo_nomina());
        tc.setEstatus(1);
        return cat_Tipo_ContratoRepository.save(tc);
    }

    @Override
    public Cat_Tipo_Contrato cambioEstatusTipoContrato(Integer id_tipo_contrato, Integer stat) {
        Cat_Tipo_Contrato c = cat_Tipo_ContratoRepository.findById(id_tipo_contrato).get();
        c.setEstatus(stat);
        return cat_Tipo_ContratoRepository.save(c);
    }

    // ******************* CATALOGO TIPO DE AYUDA ******************
    @Override
    public List<Cat_Tipo_AyudaDTO> findAllTipoAyuda() {
        return cat_DiasRepository.findAll().stream().map(cat_dias -> modelMapper.map(cat_dias, Cat_Tipo_AyudaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Tipo_Ayuda> findAllTipoDatosAyuda() { //Listar todos los datos
        return cat_tipo_ayudaRepository.findAll().stream().map(cat_tipo_ayuda -> modelMapper.map(cat_tipo_ayuda, Cat_Tipo_Ayuda.class)).collect(Collectors.toList());
    }

    @Override
    public List<Cat_Tipo_Ayuda> findAyudaID(Integer tipo_ayuda_id) {
        return cat_tipo_ayudaRepository.findAyudaID(tipo_ayuda_id);
    }
    //*********************** CATALOGOS UBICACION***************************

    @Override
    public List<Cat_Ubicacion> findAllUbicacion() {
        return cat_UbicacionRepository.findAll().stream().map(cat_ubicacion -> modelMapper.map(cat_ubicacion, Cat_Ubicacion.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Ubicacion saveUbicacion(Cat_Ubicacion cat_Ubicacion) {
        Cat_Ubicacion ubicacion = new Cat_Ubicacion();
        ubicacion.setDesc_ubicacion(cat_Ubicacion.getDesc_ubicacion());
        ubicacion.setClave(cat_Ubicacion.getClave());
        ubicacion.setEstatus(1);
        return cat_UbicacionRepository.save(ubicacion);
    }

    @Override
    public Cat_Ubicacion updateUbicacion(Cat_Ubicacion cat_Ubicacion, Integer id_ubicacion) {
        Cat_Ubicacion u = cat_UbicacionRepository.findById(id_ubicacion).get();
        u.setDesc_ubicacion(cat_Ubicacion.getDesc_ubicacion());
        u.setClave(cat_Ubicacion.getClave());

        return cat_UbicacionRepository.save(u);
    }

    @Override
    public Cat_Ubicacion findOneUbicacion(Integer id_ubicacion) {
        return cat_UbicacionRepository.findById(id_ubicacion).get();
    }

    @Override
    public Cat_Ubicacion cambioEstatusUbicacion(Integer id_ubicacion, Integer activo) {
        Cat_Ubicacion u = cat_UbicacionRepository.findById(id_ubicacion).get();
        u.setEstatus(activo);
        return cat_UbicacionRepository.save(u);
    }

    // ********** CATALOGO REPORTE MONITOR *******************
    @Override
    public List<Cat_Reporte_Monitor> findAllOrdenReporteMonitor() {
        return cat_Reporte_MonitorRepository.findAll().stream().map(Cat_Reporte_Monitor -> modelMapper.map(Cat_Reporte_Monitor, Cat_Reporte_Monitor.class)).collect(Collectors.toList());
    }

    // ********** CATALOGO GRADO *******************
    @Override
    public List<Cat_Grado> findAllDatosGrado() {
        return cat_GradoRepository.findAll().stream().map(cat_grado -> modelMapper.map(cat_grado, Cat_Grado.class)).collect(Collectors.toList());
    }

    @Override
    public Cat_Grado saveGrado(Cat_Grado cat_grado) {
        Cat_Grado grado = new Cat_Grado();
        grado.setDesc_grado(cat_grado.getDesc_grado());
        grado.setEstatus(1);
        return cat_GradoRepository.save(grado);
    }

    @Override
    public Cat_Grado findOneGrado(Integer id) {
        return cat_GradoRepository.findById(id).get();
    }

    @Override
    public Cat_Grado updateGrado(Cat_Grado cat_grado, Integer id_grado) {
        Cat_Grado g = cat_GradoRepository.findById(id_grado).get();
        g.setDesc_grado(cat_grado.getDesc_grado());

        return cat_GradoRepository.save(g);
    }

    @Override
    public Cat_Grado cambioEstatusGrado(Integer id_grado, Integer stat) {

        Cat_Grado g = cat_GradoRepository.findById(id_grado).get();
        g.setEstatus(stat);
        return cat_GradoRepository.save(g);
    }

    @Override
    public List<Cat_Tipo_Documento> findDocumentos() {
        return cat_Tipo_DocumentoRepository.findDocumentos();

    }

    @Override
    public Historico_Libro_Dias saveHistoricoLibroDias(Historico_Libro_Dias historico_libro_dias) {
//        Historico_Libro_Dias historico = new Historico_Libro_Dias();
//        historico.setEstatus(1);

        return historico_libro_diasRepository.save(historico_libro_dias);
    }

    @Override
    public List<Cat_Impuesto_Concepto> findAllCatImpuestoConcepto() {
        return cat_Impuesto_ConceptoRepository.findAll();
    }

    @Override
    public List<Cat_Periodo_Impuesto> findAllCatPeriodoImpuesto() {
        return cat_Periodo_ImpuestoRepository.findAll();
    }

    @Override
    public List<Cat_Motivos_RA10> findAllMotivosRa10() {
        return cat_Motivos_RA10Repository.findAll().stream().map(cat_Motivos_RA10 -> modelMapper.map(cat_Motivos_RA10, Cat_Motivos_RA10.class)).collect(Collectors.toList());
    }
}
