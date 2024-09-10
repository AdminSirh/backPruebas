/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sirh.sirh.service;

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
import com.sirh.sirh.entity.Cat_Tipo_Nomina;
import com.sirh.sirh.entity.Cat_Tipo_Riesgo_Incapacidad;
import com.sirh.sirh.entity.Cat_Tipo_Secuelas_Incapacidad;
import com.sirh.sirh.entity.Cat_Ubicacion;
import com.sirh.sirh.entity.Historico_Credito_Infonavit;
import com.sirh.sirh.entity.Historico_Libro_Dias;
import com.sirh.sirh.entity.Historico_Puesto;
import com.sirh.sirh.entity.Historico_Trabajador_Plaza;
import com.sirh.sirh.entity.Cat_Periodo_Impuesto;
import com.sirh.sirh.entity.Cat_Tipo_Mov_Idse_Sua;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jarellano22
 */
public interface CatalogosService {

    // ********** CATALOGO AREAS *******************
    public Cat_Areas estatus(Integer id, Integer activo);

    public List<Cat_AreasDTO> findAll();

    public List<Cat_Areas> findAllDatosAreas(); //Listar todos los datos

    public Cat_Areas save(Cat_Areas cat_Areas);

    public Cat_Areas findOne(Integer id);

    public Cat_Areas update(Cat_Areas cat_Areas, Integer id);

    public void delete(Integer id);

    public List<Cat_Areas> listarAreas();

    public Cat_Areas editarAreas(Cat_Areas areas, Integer id_a);

    public List<Cat_Bimestres_Sdi> findAllBimestresSdi();

    public Cat_Bimestres_Sdi findByIdBimestre(Integer id_bimestre);

    // ********** CATALOGO CARGO *******************
    public List<Cat_CargoDTO> findAllCargo();

    public List<Cat_Cargo> findAllDatosCargo(); //Listar todos los datos

    public Cat_Cargo saveCargo(Cat_Cargo catCargo);
    
    public Cat_Cargo findOneCargo(Integer id);

    public Cat_Cargo updateCargo(Cat_Cargo catCargo, Integer id_cargo);

    public Cat_Cargo cambioEstatusCargo(Integer id_cargo, Integer activo);

    // ********** CATALOGO COLONIA *******************
    public List<Cat_ColoniaDTO> findAllColonia();

    public List<Cat_Colonia> findAllDatosColonia(); //Listar todos los datos

    public List<Cat_Colonia> findMunicipiosColonia(Integer id_municipio);

    public Cat_Colonia saveColonia(Cat_Colonia colonia);

    public Cat_Colonia updateCatColonia(Cat_ColoniaDTO colonia, Integer id_colonia);

    public Cat_Colonia updateColonia(Cat_Colonia colonia, Integer id_colonia);

    public Cat_Colonia estatusColonia(Integer id_colonia, Integer estatus);

    public Cat_Colonia findOneColonia(Integer id_colonia);

    // ********** CATALOGO CREDITO   *******************
    public List<Cat_Credito_InfonavitDTO> findAllCredito();

    public List<Cat_Credito_Infonavit> findAllDatosCredito(); //Listar todos los datos

    public Cat_Credito_Infonavit saveCredito(Cat_Credito_Infonavit cat_Credito_Infonavit);
    
    public Cat_Credito_Infonavit findOneCredito(Integer id);

    public Cat_Credito_Infonavit updateCredito(Cat_Credito_Infonavit catCredito_infonavit, Integer id_credito);

    public Cat_Credito_Infonavit cambioEstatusCredito(Integer id_credito, Integer stat);

    // ********** CATALOGO EDO. CIVIL   *******************
    public List<Cat_Edo_CivilDTO> findAllEdoCivil();

    public List<Cat_Edo_Civil> findAllDatosEdoCivil(); //Listar todos los datos

    public Cat_Edo_Civil saveEdoCivil(Cat_Edo_Civil cat_Edo_Civil);

    public Cat_Edo_Civil findOneEdoCivil(Integer id);

    public Cat_Edo_Civil updateEdoCivil(Cat_Edo_Civil EdoCivil, Integer id_edo_civil);

    public Cat_Edo_Civil cambioEstatusEdoCivil(Integer id_edo_civil, Integer stat);

    // ********** CATALOGO ESTADO   *******************
    public List<Cat_EstadoDTO> findAllEstado();

    public List<Cat_Estado> findAllDatosEstado(); //Listar todos los datos

    public Cat_Estado saveEstado(Cat_Estado cat_Estado);
    
    public Cat_Estado findOneEstado(Integer id);

    public Cat_Estado updateEstado(Cat_Estado estado, Integer id_estado);

    public Cat_Estado cambioEstatusEstado(Integer id_estado, Integer stat);

    // ********** CATALOGO ESTADO VACACIONES   *******************
    public List<Cat_Estado_VacacionesDTO> findAllEstadoVacaciones();

    public List<Cat_Estado_Vacaciones> findAllDatosEstadoVacaciones(); //Listar todos los datos

    public Cat_Estado_Vacaciones saveEstadoVacaciones(Cat_Estado_Vacaciones cat_Estado_Vacaciones);
    
    public Cat_Estado_Vacaciones findOneEstadoVacaciones(Integer id);

    public Cat_Estado_Vacaciones updateEstadoVacaciones(Cat_Estado_Vacaciones estadoVacaciones, Integer id_estado_vacaciones);

    public Cat_Estado_Vacaciones cambioEstatusEstadoVacaciones(Integer id_estado_vacaciones, Integer stat);

    // ********** CATALOGO ESTRUCTURA   *******************
    public List<Cat_EstructuraDTO> findAllEstructura();

    public List<Cat_Estructura> findAllDatosEstructura(); //Listar todos los datos

    public Cat_Estructura saveEstructura(Cat_Estructura cat_Estructura);
    
    public Cat_Estructura findOneEstructura(Integer id);

    public Cat_Estructura updateEstructura(Cat_Estructura estructura, Integer id_estructura);

    public Cat_Estructura cambioEstatusEstructura(Integer id_estructura, Integer stat);

    // ********** CATALOGO ESTUDIOS   *******************
    public List<Cat_EstudiosDTO> findAllEstudios();

    public List<Cat_Estudios> findAllDatosEstudios(); //Listar todos los datos

    public Cat_Estudios saveEstudios(Cat_Estudios cat_Estudios);
    
    public Cat_Estudios findOneEstudio(Integer id);

    public Cat_Estudios updateEstudios(Cat_Estudios estudios, Integer id_grado);

    public Cat_Estudios cambioEstatusEstudios(Integer id_grado, Integer estatus);

    // ********** CATALOGO GENERO   *******************
    public List<Cat_GeneroDTO> findAllGenero();

    public List<Cat_Genero> findAllDatosGenero(); //Listar todos los datos

    public Cat_Genero saveGenero(Cat_Genero cat_Genero);
    
    public Cat_Genero findOneGenero(Integer id);

    public Cat_Genero updateGenero(Cat_Genero genero, Integer id_genero);

    public Cat_Genero cambioEstatusGenero(Integer id_genero, Integer activo);

    // ********** CATALOGO INHABILITADO   *******************
    public List<Cat_InhabilitadoDTO> findAllInhabilitado();

    public List<Cat_Inhabilitado> findAllDatosInhabilitado(); //Listar todos los datos

    public Cat_Inhabilitado saveInhabilitdo(Cat_Inhabilitado cat_Inhabilitado);

    public Cat_Inhabilitado saveInhabilitdo(Cat_InhabilitadoDTO cat_Inhabilitado);

    public Cat_Inhabilitado updateInhabilitado(Integer id_inhabilitado, Cat_InhabilitadoDTO inhabilitado);

    public Cat_Inhabilitado findOneInhabilitado(Integer id_inhabilitado);

    public Cat_Inhabilitado estatusInhabilitado(Integer id_inhabilitado, Integer estatus);

    // ********** CATALOGO LICENCIA   *******************
    public List<Cat_LicenciaDTO> findAllLicencia();

    public List<Cat_Licencia> findAllDatosLicencia(); //Listar todos los datos

    public Cat_Licencia saveLicencia(Cat_Licencia cat_Licencia);

    public Cat_Licencia findOneLicencia(Integer id_licencia);

    public Cat_Licencia updateLicencia(Integer id_licencia, Cat_LicenciaDTO licencia);

    public Cat_Licencia estatusLicencia(Integer id_licencia, Integer estatus);

    public void deleteLicencia(Integer id_licencia);

    // ********** CATALOGO CONTRATO *******************
    public List<Cat_Tipo_Contrato> findAllContrato();

    public List<Cat_Tipo_Contrato> findAllDatosContrato(); //Listar todos los datos

    public Cat_Tipo_Contrato saveContrato(Cat_Tipo_Contrato cat_tipo_contrato);

    // ********** CATALOGO PARENTESCO *******************
    public List<Cat_ParentescoDTO> findAllParentesco();

    public List<Cat_Parentesco> findAllDatosParentesco(); //Listar todos los datos

    public Cat_Parentesco saveParentesco(Cat_Parentesco cat_Parentesco);
    
    public Cat_Parentesco findOneParentesco(Integer id);

    public Cat_Parentesco updateParentesco(Cat_Parentesco parentesco, Integer id_parentesco);

    public Cat_Parentesco cambioEstatusParentesco(Integer id_parentesco, Integer activo);
    
    // ********** CATALOGO SANGRE *******************
    public List<Cat_SangreDTO> findAllSangre();

    public List<Cat_Sangre> findAllDatosSangre(); //Listar todos los datos

    public Cat_Sangre saveSangre(Cat_Sangre cat_Sangre);

    public Cat_Sangre saveSangre(Cat_SangreDTO cat_Sangre);

    public Cat_Sangre updateSangre(Integer id_sangre, Cat_SangreDTO cat_Sangre);

    public Cat_Sangre estatusSangre(Integer id_sangre, Integer estatus);

    public Cat_Sangre findOneSangre(Integer id_sangre);

    // ********** CATALOGO TABULADOR SUELDO *******************
    public List<Cat_Tabulador_SueldoDTO> findAllTabulador_Sueldo();

    public List<Cat_Tabulador_Sueldo> findAllDatosTabulador_Sueldo(); //Listar todos los datos

    public Cat_Tabulador_Sueldo saveTabulador_Sueldo(Cat_Tabulador_Sueldo cat_Tabulador_Sueldo);

    // ********** CATALOGO TIPO DE MOVIMIENTO PARA IDSE SUA *******************
    public List<Cat_Tipo_Mov_Idse_Sua> findAllTipoMovImss();

    // ********** CATALOGO BENEFICIARIO *******************
    public List<Cat_BeneficiarioDTO> findAllBeneficiario();

    public List<Cat_Beneficiario> findAllDatosBeneficiario(); //Listar todos los datos

    public Cat_Beneficiario saveBeneficiario(Cat_Beneficiario cat_Beneficiario);

    // ********** CATALOGO TABULADOR *******************
    public List<Cat_TabuladorDTO> findAllTabulador();

    public List<Cat_Tabulador> findAllDatosTabulador(); //Listar todos los datos

    public Cat_Tabulador saveTabulador(Cat_Tabulador cat_Tabulador);
    
    public Cat_Tabulador findOneTabulador(Integer id);

    public Cat_Tabulador updateTabulador(Cat_Tabulador tabulador, Integer id_tipo_tabulador);

    public Cat_Tabulador cambioEstatusTabulador(Integer id_tipo_tabulador, Integer stat);

    // ********** CATALOGO TIPO DE DATO INCIDENCIAS *******************
    public List<Cat_Tipo_Dato_Incidencias> findAllCatTipoDatoIncidencias();

    // ********** CATALOGO NACIONALIDAD *******************
    public List<Cat_NacionalidadDTO> findAllNacionalidad();

    public List<Cat_Nacionalidad> findAllDatosNacionalidad(); //Listar todos los datos

    public Cat_Nacionalidad saveNacionalidad(Cat_Nacionalidad cat_Nacionalidad);

    public Cat_Nacionalidad saveNacionalidad(Cat_NacionalidadDTO cat_Nacionalidad);

    public Cat_Nacionalidad updateNacionalidad(Integer id_nacionalidad, Cat_NacionalidadDTO cat_Nacionalidad);

    public Cat_Nacionalidad estatusNacionalidad(Integer id_nacionalidad, Integer estatus);

    public Cat_Nacionalidad findOneNacionalidad(Integer id_nacionalidad);

    // ********** CATALOGOS DE INCAPACIDAD *******************
    public List<Cat_Motivo_Incapacidad> findAllMotivosIncapacidad();
  
    public List<Cat_Tipo_Incapacidad> findAllTiposIncapacidad();

    public List<Cat_Tipo_Riesgo_Incapacidad> findAllTipoRiesgoIncapacidad();

    public List<Cat_Tipo_Secuelas_Incapacidad> findAllTipoSecuelasIncapacidad();

    public List<Cat_Tipo_Control_Incapacidad> findAllTipoControlIncapacidad();

    // ********** CATALOGO SINO ****************************************
    public List<Cat_Si_NoDTO> findAllSiNo();

    public List<Cat_Si_No> findAllDatosSiNo(); //Listar todos los datos

    public Cat_Si_No saveSiNo(Cat_Si_No cat_Si_No);

    public Cat_Si_No updateSiNo(Integer id, Cat_Si_NoDTO cat_Si_No);

    public Cat_Si_No estatusSiNo(Integer id, Integer estatus);

    public Cat_Si_No findOneSiNo(Integer id);

    //************** CATALOGO DIRECCION  *******************************
    public List<Cat_Colonia> findByCod_Postal(String cod_postal);

    //************** CATALOGO PARA EXPEDIENTES  ************************
    public List<Cat_ExpedienteDTO> findAllExpedientes();

    public List<Cat_Expediente> findAllExp(); //Listar todo

    public Cat_Expediente activo(Integer id_expediente, Integer estatus);

    public Cat_Expediente existsByNumExpediente(String numero_expediente);

    public Cat_Expediente saveExpediente(Cat_NumExpedienteDTO expediente);
    
    public Integer idExpediente(String numero_expediente);
    
    public Cat_Expediente ultimoExpediente();
    
    public Integer ultimoIdExpediente();

    //************** UPDATE ASIGNADO EXPEDIENTE ***************
    public Cat_Expediente update(Integer id, Cat_NumExpedienteDTO usuario);

    //*********************** CATALOGOS BENEFIIARIOS ***************************
    public List<Cat_Parentesco> findAllCatalogo_Parentesco();

    public List<Cat_Tipo_Beneficiario> findAllCatalogo_TipoBeneficiario();

    //*********************** CATALOGOS Bancos***************************
    public List<Cat_BancoDTO> findAllBanco();

    public List<Cat_Banco> findAllDatosBanco(); //Listar todos los datos

    public Cat_Banco saveBanco(Cat_Banco cat_Banco);
    
    public Cat_Banco findOneBanco(Integer id);

    public Cat_Banco updateBanco(Cat_Banco banco, Integer id_banco);

    public Cat_Banco cambioEstatusBanco(Integer id_banco, Integer activo);
    
    public Integer findMaxComision(Integer id_banco);

    //*********************** CATALOGO PUESTO ***************************
    public List<Cat_Puesto> findAllPuesto();

    public Cat_Puesto savePuesto(Cat_Puesto puesto);

    public Cat_Puesto findCodigoPuesto(Integer codigo_puesto);

    public Cat_Puesto editarCatPuesto(Cat_PuestoDTO puesto, Integer codigo_puesto);

    Cat_Puesto existsByCodPuesto(Integer codigo_puesto);

    public Cat_Puesto findByIdPuesto(Integer id_puesto);

    public Cat_Puesto cambioEstatus(Integer id_puesto, Integer activo);

    //*********************** CATALOGO PLAZA ***************************
    public List<Cat_Plaza> findAllPlaza();

    List<Cat_Plaza> findAllPlaza(Integer puesto_id);

    Integer countPlazas(Integer puesto_id);

    Integer countPlazasDisponibles(Integer puesto_id);
    
    Integer countPlazasAsignadas(Integer puesto_id);

    public Cat_Plaza savePlaza(Cat_Plaza plaza);

    public Cat_Plaza editarCatPlaza(Cat_Plaza plaza, Integer id_puesto);

    public Cat_Plaza findOnePlaza(Integer id_plaza);

    public Cat_Plaza cambioEstatusPlaza(Integer id_plaza, Integer activo);

    Integer ultimoId();
    
    List<Cat_Plaza> findPlazasDisponibles(Integer puesto_id);
    //*********************** CATALOGO Depositos ***************************
    public List<Cat_Depositos> findAllDepositos();
    
    public Cat_Depositos findOneDeposito(Integer id);

    public Cat_Depositos editarDepositos(Cat_Depositos depo, Integer id_deposito);

    public Cat_Depositos saveDeposito(Cat_Depositos deposito);

    public Cat_Depositos cambioEstatusDeposito(Integer id_deposito, Integer stat);

    //*********************** CATALOGO CONTRATO NOMINA ***************************
   public List<Cat_Contrato_Nomina> findAllContratoNomina();
   
   public List<Cat_Contrato_Nomina> findIdContrato(Integer id_contrato);
    
   //*********************** CATALOGO NOMINA UBICACION***************************
   
   public List<Cat_Nomina_Ubicacion> findIdNomina(Integer id_nomina);
 
     // ********** CATALOGO DIAS *******************
    public List<Cat_DiasDTO> findAllDias();

    public List<Cat_Dias> findAllDatosDias(); //Listar todos los datos

    public Cat_Dias saveDias(Cat_Dias cat_Dias);
    
    public Cat_Dias findOneDia(Integer id);
    
    public Cat_Dias editarDias(Cat_Dias dias, Integer id_dia);

    public Cat_Dias saveDia(Cat_Dias dia);

    public Cat_Dias cambioEstatusDia(Integer id_dia, Integer stat);
    
    // ********** HISTORICO DE PUESTOS *******************
    public Historico_Puesto saveHistorico(Historico_Puesto historico);
    
    //************ CATALOGO TIPO NOMINA ***********************************
    public List<Cat_Tipo_Nomina> findAllTipoNomina();
    
    public Cat_Tipo_Nomina findByIdNomina(Integer id_tipo_nomina);
    
    // ********** HISTORICO DE PUESTOS *******************
    public Historico_Trabajador_Plaza saveHistoricoTP(Historico_Trabajador_Plaza historico);
    
    // ********** CATALOGO DIAS VACACIONES *******************
    
    Integer diasDisponibles(Integer tipo_dias_vacaciones_id, Double antiguedad);
    
    // ********** CATALOGO INCIDENCIAS *******************
    
    public List<Cat_IncidenciasDTO> findAllIncidencias();

    public List<Cat_Incidencias> findAllDatosIncidencias(); //Listar todos los datos
    
    public List<Cat_Incidencias> findIncID(Integer tipo_incidencia_id);

    public Cat_Incidencias saveCat(Cat_Incidencias cat_incidencias);

    public List<Cat_Incidencias> findIncidencia(Integer tipo_Incidencia_id);

    public List<Cat_Incidencias> findIncidenciasKardex();
    
    public List<Cat_Incidencias> findAllDeducciones();

    public Cat_Incidencias findOneIncidencia(Integer id_incidencia);

    public Cat_Incidencias actualizarIncidencia(Integer id_incidencia, Cat_Incidencias incidencias);

    public Cat_Incidencias saveIncidencia(Cat_Incidencias incidencias);

    public Cat_Incidencias cambioEstatusIncidencias(Integer id_incidencia, Integer stat);

    // ********** CATALOGO TIPO INCIDENCIAS *******************

    public List<Cat_Tipo_IncidenciaDTO> findAllTipo();

    public List<Cat_Tipo_Incidencia> findDatosTipo(); //Listar todos los datos
    
    public List<Cat_Tipo_Incidencia> findTipoID(Integer tipo_incidencia_id);

    public Cat_Tipo_Incidencia saveTipo(Cat_Tipo_Incidencia cat_tipo_incidencia);
    
    public Cat_Tipo_Incidencia cambioEstatusTipoIncidencias(Integer id_incidencia, Integer stat);
    
    // ********** CATALOGO ESTADO INCIDENCIAS *******************
    public List<Cat_Estado_IncidenciaDTO> findEstadoInc();

    public List<Cat_Estado_Incidencia> findDatosEstadoInc(); //Listar todos los datos
    
    // ********** CATALOGO MOTIVO CANCELACION *******************
    
    public List<Cat_Motivo_Cancelacion> findAllMotivoCancelacion();

    // ********** CATALOGO DIAS FESTIVOS   *******************
    public Cat_Dias_Festivos findOneDiaFestivo(Integer id_festivos);

    public List<Cat_Dias_Festivos> findAllDatosDiasFestivos(); //Listar todos los datos

    public Cat_Dias_Festivos saveDiaFestivo(Cat_Dias_Festivos diaFestivo);

    public Cat_Dias_Festivos updateDiaFestivo(Cat_Dias_Festivos diaFestivo, Integer id_festivos);

    public Cat_Dias_Festivos cambioEstatusFestivo(Integer id_festivos, Integer stat);
    
    public List<Cat_Dias_Festivos> findDiasNomina(Integer nomina_id);
    
    public List<Cat_Dias_Festivos> findDiasFestivos(Integer nomina_id, Date fecha);
    
    //*********************** CATALOGO HORARIO ***************************
    public Cat_Horario findOneHorario(Integer id);

    public List<Cat_Horario> findAllHorarios();

    public Cat_Horario editarHorarios(Cat_Horario horario, Integer id_horario);

    public Cat_Horario saveHorario(Cat_Horario horario);

    public Cat_Horario cambioEstatusHorario(Integer id_horario, Integer stat);

    // *********** CATÁLOGO PLAZA MOVIMIENTOS *************************
    public List<Cat_Plaza_MovimientosDTO> findAllPlazaMovimientos();

    public List<Cat_Plaza_Movimientos> findAllDatosPlazaMovimientos(); //Listar todos los datos

    public Cat_Plaza_Movimientos savePlazaMovimientos(Cat_Plaza_Movimientos cat_Plaza_Movimientos);

    public Cat_Plaza_Movimientos updatePlazaMovimientos(Integer id_plaza_movimientos, Cat_Plaza_MovimientosDTO cat_Plaza_Movimientos);

    public Cat_Plaza_Movimientos findOnePlazaMovimientos(Integer id_plaza_movimientos);

    public Cat_Plaza_Movimientos estatusPlazaMovimientos(Integer id_plaza_movimientos, Integer estatus);

    //************ CATALOGO TIPO_BENDEFICIARIO ***********************************
    public List<Cat_Tipo_BeneficiarioDTO> findAllTipoBeneficiario();

    public List<Cat_Tipo_Beneficiario> findAllDatosTipoBeneficiario();

    public Cat_Tipo_Beneficiario saveTipoBeneficiario(Cat_Tipo_Beneficiario cat_Tipo_Beneficiario);

    public Cat_Tipo_Beneficiario updateTipoBeneficiario(Integer id_tipo_beneficiario, Cat_Tipo_BeneficiarioDTO cat_Tipo_Beneficiario);

    public Cat_Tipo_Beneficiario estatusTipoBeneficiario(Integer id_tipo_beneficiario, Integer estatus);

    public Cat_Tipo_Beneficiario findOneTipoBeneficiario(Integer id_tipo_beneficiario);


    // ********** CATALOGO TIPO DOCUEMNTO *******************
    public Cat_Tipo_Documento findOneDocumento(Integer id);

    public List<Cat_Tipo_Documento> findAllDatosDocumentos(); //Listar todos los datos

    public Cat_Tipo_Documento saveDocumento(Cat_Tipo_Documento documento);

    public Cat_Tipo_Documento updateDocumentos(Cat_Tipo_Documento documento, Integer id_tipo_documento);

    public Cat_Tipo_Documento cambioEstatusDocumentos(Integer id_tipo_documento, Integer stat);
    
    public List<Cat_Tipo_Documento> findDocumentos();

    //*********** CATALOGO MUNICIPIO ******************
    public List<Cat_MunicipioDTO> findAllMunicipio(); //Listar datos con estatus=1

    public List<Cat_Municipio> findAllDatosMunicipio(); //Listar todos los datos 

    public List<Cat_Municipio> findEstadoMunicipio(Integer id_estado);

    public Cat_Municipio saveMunicipio(Cat_Municipio municipio);

    public Cat_Municipio updateMunicipio(Cat_MunicipioDTO cat_Municipio, Integer id_municipio);

    public Cat_Municipio estatusMunicipio(Integer id_municipio, Integer activo);

    public Cat_Municipio findOneMunicipio(Integer id_municipio);

    // ********** CATALOGO TIPO CONTRATO *******************
    public List<Cat_Tipo_Contrato> findAllDatosTipoContrato();

    public Cat_Tipo_Contrato findOneTipoContrato(Integer id_tipo_contrato);

    public Cat_Tipo_Contrato actualizarTipoContrato(Integer id_tipo_contrato, Cat_Tipo_Contrato tipo_Contrato);

    public Cat_Tipo_Contrato saveTipoContrato(Cat_Tipo_Contrato cat_Tipo_Contrato);

    public Cat_Tipo_Contrato cambioEstatusTipoContrato(Integer id_tipo_contrato, Integer stat);
   
   //*********************** CATALOGO TIPO DE AYUDA***************************
    public List<Cat_Tipo_AyudaDTO> findAllTipoAyuda();

    public List<Cat_Tipo_Ayuda> findAllTipoDatosAyuda(); //Listar todos los datos

    public List<Cat_Tipo_Ayuda> findAyudaID(Integer tipo_ayuda_id);

    // ********** HISTORICO DE CREDITO INFONAVIT *******************
    public Historico_Credito_Infonavit saveHistoricoCredito(Historico_Credito_Infonavit historico_Credito_Infonavit);
    
     //*********************** CATALOGOS UBICACION***************************
    public List<Cat_Ubicacion> findAllUbicacion();

    public Cat_Ubicacion saveUbicacion(Cat_Ubicacion cat_Ubicacion);
    
    public Cat_Ubicacion findOneUbicacion(Integer id_ubicacion);

    public Cat_Ubicacion updateUbicacion(Cat_Ubicacion cat_Ubicacion, Integer id_ubicacion);

    public Cat_Ubicacion cambioEstatusUbicacion(Integer id_ubicacion, Integer activo);

    
    // ********** CATALOGO REPORTE MONITOR *******************
    
    public List<Cat_Reporte_Monitor> findAllOrdenReporteMonitor();
    // ********** CATALOGO GRADO *******************
    
    public List<Cat_Grado> findAllDatosGrado(); //Listar todos los datos

    public Cat_Grado saveGrado(Cat_Grado cat_grado);
    
    public Cat_Grado findOneGrado(Integer id);

    public Cat_Grado updateGrado(Cat_Grado cat_grado, Integer id_grado);

    public Cat_Grado cambioEstatusGrado(Integer id_grado, Integer stat);
    
    // ********** HISTÓRICO LIBRO DÍAS *******************
    public Historico_Libro_Dias saveHistoricoLibroDias(Historico_Libro_Dias historico_libro_dias);
    
    
    // ********** CATALOGO IMPUESTO CONCEPTO*******************
    public List<Cat_Impuesto_Concepto> findAllCatImpuestoConcepto();
    
    // ********** CATALOGO PERIODO IMPUESTO *******************
    public List<Cat_Periodo_Impuesto> findAllCatPeriodoImpuesto();
    
    public List<Cat_Motivos_RA10> findAllMotivosRa10();
    
}
