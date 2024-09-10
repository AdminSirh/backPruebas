/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import com.sirh.sirh.DTO.Cat_AreasDTO;
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
import com.sirh.sirh.DTO.Cat_Tipo_AyudaDTO;
import com.sirh.sirh.DTO.Cat_Tipo_BeneficiarioDTO;
import com.sirh.sirh.DTO.Cat_Tipo_IncidenciaDTO;
import com.sirh.sirh.config.SystemControllerLog;
import com.sirh.sirh.entity.Cat_Areas;
import com.sirh.sirh.entity.Cat_Banco;
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
import com.sirh.sirh.service.CatalogosService;
import com.sirh.sirh.util.Response;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sirh.sirh.exception.Exceptions;
import sirh.sirh.exception.OutputEntity;

/**
 *
 * @author jarellano22
 */
@RestController
@RequestMapping("catalogos")
public class CatalogosController {

    @Autowired
    CatalogosService catalogosService;

    //******************* CATALOGO AREAS ************************************
    @GetMapping(value = "/estatusArea/{id}/{activo}")
    public ResponseEntity<Cat_Areas> estatusArea(@RequestBody @PathVariable Integer id, @PathVariable Integer activo) {
        try {
            return new ResponseEntity<>(catalogosService.estatus(id, activo), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarAreas")
    public ResponseEntity<Cat_AreasDTO> listarAreas() {
        try {
            List<Cat_AreasDTO> result = catalogosService.findAll();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosAreas")
    public ResponseEntity<Cat_Areas> listarDatosAreas() {
        try {
            List<Cat_Areas> result = catalogosService.findAllDatosAreas();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarArea")
    public ResponseEntity<Cat_Areas> guardarUsuarios(@RequestBody Cat_Areas catArea) {
        try {
            return new ResponseEntity<>(catalogosService.save(catArea), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarAreas/{id_area}")
    public ResponseEntity<OutputEntity<String>> editarAreas(@RequestBody Cat_Areas areas, @PathVariable Integer id_area) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.editarAreas(areas, id_area);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Busca por id y Lista a todos los datos del CATALOGO_Area, cuando id_area_superior sea igual al id solicitado 
    @GetMapping(value = "/listarAllAreas")
    public ResponseEntity<Cat_Areas> listarAllAreas() {
        try {
            List<Cat_Areas> result = catalogosService.listarAreas();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarArea/{id_area}")
    public ResponseEntity<OutputEntity<Cat_Areas>> buscarArea(@PathVariable Integer id_area) {
        OutputEntity<Cat_Areas> out = new OutputEntity<>();
        try {
            Cat_Areas result = catalogosService.findOne(id_area);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/listarCargo")
    public ResponseEntity<Cat_CargoDTO> listarCat_Cargo() {
        try {
            List<Cat_CargoDTO> result = catalogosService.findAllCargo();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO BIMESTRES ************************************
    @GetMapping(value = "/listarBimestres")
    public ResponseEntity<Cat_Bimestres_Sdi> listarBimestres() {
        try {
            List<Cat_Bimestres_Sdi> result = catalogosService.findAllBimestresSdi();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/encuentraUnBimestre/{id_bimestres}")
    public ResponseEntity<OutputEntity<Cat_Bimestres_Sdi>> findOneBimestre(@PathVariable("id_bimestres") Integer id_bimestres) {
        OutputEntity<Cat_Bimestres_Sdi> out = new OutputEntity<>();
        try {
            Cat_Bimestres_Sdi result = catalogosService.findByIdBimestre(id_bimestres);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //listar todos los datos
    @GetMapping(value = "/listarDatosCargo")
    public ResponseEntity<Cat_Cargo> listarDatosCat_Cargo() {
        try {
            List<Cat_Cargo> result = catalogosService.findAllDatosCargo();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/guardarCargo")
    public ResponseEntity<Cat_Cargo> guardarCatalogoCargo(@RequestBody Cat_Cargo catCargo) {
        try {
            return new ResponseEntity<>(catalogosService.saveCargo(catCargo), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Buscar cargo por ID 
    @GetMapping(value = "/buscarCargo/{id}")
    public ResponseEntity<OutputEntity<Cat_Cargo>> findOneCargo(@PathVariable Integer id) {
        OutputEntity<Cat_Cargo> out = new OutputEntity<>();
        try {
            Cat_Cargo result = catalogosService.findOneCargo(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarCargo/{id_cargo}")
    public ResponseEntity<OutputEntity<String>> updateCargo(@RequestBody Cat_Cargo catCargo, @PathVariable("id_cargo") Integer id_cargo) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateCargo(catCargo, id_cargo);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de cargo modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @SystemControllerLog(operation = "cambioEstatusCargo", type = "Cambiar el estatus del cargo") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusCargo/{id_cargo}/{estatus}")
    public ResponseEntity<Cat_Cargo> cambioEstatusCargo(@RequestBody @PathVariable Integer id_cargo, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusCargo(id_cargo, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO COLONIA  ************************************
    @GetMapping(value = "/listarColonias")
    public ResponseEntity<Cat_ColoniaDTO> listarColonia() {
        try {
            List<Cat_ColoniaDTO> result = catalogosService.findAllColonia();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosColonias")
    public ResponseEntity<Cat_Colonia> listarDatosColonia() {
        try {
            List<Cat_Colonia> result = catalogosService.findAllDatosColonia();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //listar todos los datos
    @GetMapping(value = "/findAllDatosColonia")
    public ResponseEntity<Cat_Colonia> findAllDatosColonia() {
        try {
            List<Cat_Colonia> result = catalogosService.findAllDatosColonia();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/guardarCatColonia")
    public ResponseEntity<OutputEntity<Cat_Colonia>> saveColonia(@RequestBody Cat_Colonia colonia) {
        OutputEntity<Cat_Colonia> out = new OutputEntity<>();
        try {
            catalogosService.saveColonia(colonia);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), null);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping("/actualizarCatColonia/{id_colonia}")
    public ResponseEntity<OutputEntity<String>> updateCatColonia(@RequestBody Cat_ColoniaDTO colonia, @PathVariable("id_colonia") Integer id_colonia) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateCatColonia(colonia, id_colonia);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos Colonia actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping(value = "/editarColonia/{id_colonia}")
    public ResponseEntity<OutputEntity<String>> updateColonia(@RequestBody Cat_Colonia colonia, @PathVariable("id_colonia") Integer id_colonia) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateColonia(colonia, id_colonia);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de colonia modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping("/estatusColonia/{id_colonia}/{estatus}")
    public ResponseEntity<Cat_Colonia> estatusColonia(@RequestBody @PathVariable("id_colonia") Integer id_colonia, @PathVariable("estatus") Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.estatusColonia(id_colonia, estatus), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscarColonia/{id_colonia}")
    public ResponseEntity<OutputEntity<Cat_Colonia>> findOneColonia(@PathVariable("id_colonia") Integer id_colonia) {
        OutputEntity<Cat_Colonia> out = new OutputEntity<>();
        try {
            Cat_Colonia result = catalogosService.findOneColonia(id_colonia);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscarMunicipiosColonia/{id_municipio}")
    public ResponseEntity<OutputEntity<List<Cat_Colonia>>> findMunicipioColonia(@PathVariable("id_municipio") Integer id_municipio) {
        try {
            List<Cat_Colonia> result = catalogosService.findMunicipiosColonia(id_municipio);
            return new ResponseEntity(result, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ************************************************************************************************************************
     * HISTORICO CREDITO INFONAVIT
     * ************************************************************************************************************************
     */
    @PostMapping("/guardaHistoricoCreditoInfonavit")
    public ResponseEntity<Historico_Credito_Infonavit> guardaHistoricoCredito(@RequestBody Historico_Credito_Infonavit historico_Credito_Infonavit) {
        try {
            return new ResponseEntity<>(catalogosService.saveHistoricoCredito(historico_Credito_Infonavit), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO CREDITO  ************************************
    @GetMapping(value = "/listarCreditoInfonavit")
    public ResponseEntity<Cat_Credito_InfonavitDTO> listarCat_Credito() {
        try {
            List<Cat_Credito_InfonavitDTO> result = catalogosService.findAllCredito();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosCreditoInfonavit")
    public ResponseEntity<Cat_Credito_Infonavit> listarDatosCat_Credito() {
        try {
            List<Cat_Credito_Infonavit> result = catalogosService.findAllDatosCredito();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarCatCredito")
    public ResponseEntity<Cat_Credito_Infonavit> guardarCatalogoCredito(@RequestBody Cat_Credito_Infonavit cat_Credito_Infonavit) {
        try {
            return new ResponseEntity<>(catalogosService.saveCredito(cat_Credito_Infonavit), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Enconrar un crédito por ID
    @GetMapping(value = "/buscarCredito/{id}")
    public ResponseEntity<OutputEntity<Cat_Credito_Infonavit>> findOneCredito(@PathVariable Integer id) {
        OutputEntity<Cat_Credito_Infonavit> out = new OutputEntity<>();
        try {
            Cat_Credito_Infonavit result = catalogosService.findOneCredito(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarCredito/{id_credito}")
    public ResponseEntity<OutputEntity<String>> updateCredito(@RequestBody Cat_Credito_Infonavit catCredito_infonavit, @PathVariable("id_credito") Integer id_credito) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateCredito(catCredito_infonavit, id_credito);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de crédito modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @SystemControllerLog(operation = "cambioEstatusCredito", type = "Cambiar el estatus del credito") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusCredito/{id_credito}/{estatus}")
    public ResponseEntity<Cat_Credito_Infonavit> cambioEstatusCredito(@RequestBody @PathVariable Integer id_credito, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusCredito(id_credito, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO ESTADO CIVIL  ************************************
    @GetMapping(value = "/listarEdoCivil")
    public ResponseEntity<Cat_Edo_CivilDTO> listarCat_Edo_Civil() {
        try {
            List<Cat_Edo_CivilDTO> result = catalogosService.findAllEdoCivil();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosEdoCivil")
    public ResponseEntity<Cat_Edo_CivilDTO> listarDatosCat_Edo_Civil() {
        try {
            List<Cat_Edo_Civil> result = catalogosService.findAllDatosEdoCivil();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarEdoCivil")
    public ResponseEntity<Cat_Edo_Civil> guardarCatalogoEdoCivil(@RequestBody Cat_Edo_Civil cat_Edo_Civil) {
        try {
            return new ResponseEntity<>(catalogosService.saveEdoCivil(cat_Edo_Civil), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Buscar EdoCivil por ID 
    @GetMapping(value = "/buscarEdoCivil/{id}")
    public ResponseEntity<OutputEntity<Cat_Edo_Civil>> findOneEdoCivil(@PathVariable Integer id) {
        OutputEntity<Cat_Edo_Civil> out = new OutputEntity<>();
        try {
            Cat_Edo_Civil result = catalogosService.findOneEdoCivil(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarEdoCivil/{id_edo_civil}")
    public ResponseEntity<OutputEntity<String>> updateEdoCivil(@RequestBody Cat_Edo_Civil EdoCivil, @PathVariable("id_edo_civil") Integer id_edo_civil) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateEdoCivil(EdoCivil, id_edo_civil);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de Estado civil modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @SystemControllerLog(operation = "cambioEstatusEdoCivil", type = "Cambiar el estatus del estado civil") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusEdoCivil/{id_edo_civil}/{estatus}")
    public ResponseEntity<Cat_Edo_Civil> cambioEstatusEdoCivil(@RequestBody @PathVariable Integer id_edo_civil, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusEdoCivil(id_edo_civil, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO ESTADO  ************************************
    @GetMapping(value = "/listarEstado")
    public ResponseEntity<Cat_EstadoDTO> listarCat_Estado() {
        try {
            List<Cat_EstadoDTO> result = catalogosService.findAllEstado();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar Todos Los Datos
    @GetMapping(value = "/listarDatosEstado")
    public ResponseEntity<Cat_Estado> listarDatosCat_Estado() {
        try {
            List<Cat_Estado> result = catalogosService.findAllDatosEstado();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarEstado")
    public ResponseEntity<Cat_Estado> guardarCatalogoEstado(@RequestBody Cat_Estado cat_Estado) {
        try {
            return new ResponseEntity<>(catalogosService.saveEstado(cat_Estado), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Buscar Estado por ID 
    @GetMapping(value = "/buscarEstado/{id}")
    public ResponseEntity<OutputEntity<Cat_Estado>> findOneEstado(@PathVariable Integer id) {
        OutputEntity<Cat_Estado> out = new OutputEntity<>();
        try {
            Cat_Estado result = catalogosService.findOneEstado(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualizar los datos de la estado
    @PostMapping(value = "/editarEstado/{id_estado}")
    public ResponseEntity<OutputEntity<String>> updateEstado(@RequestBody Cat_Estado estado, @PathVariable("id_estado") Integer id_estado) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateEstado(estado, id_estado);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de Estado modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Cambiar el estatus de el estado
    @SystemControllerLog(operation = "cambioEstatusEstado", type = "Cambiar el estatus del estado") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusEstado/{id_estado}/{estatus}")
    public ResponseEntity<Cat_Estado> cambioEstatusEstado(@RequestBody @PathVariable Integer id_estado, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusEstado(id_estado, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO ESTADO VACACIONES ************************************
    @GetMapping(value = "/listarEstadoVacaciones")
    public ResponseEntity<Cat_Estado_VacacionesDTO> listarCat_EstadoVacacciones() {
        try {
            List<Cat_Estado_VacacionesDTO> result = catalogosService.findAllEstadoVacaciones();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar Todos Los Datos 
    @GetMapping(value = "/listarDatosEstadoVacaciones")
    public ResponseEntity<Cat_Estado_Vacaciones> listarDatosCat_EstadoVacacciones() {
        try {
            List<Cat_Estado_Vacaciones> result = catalogosService.findAllDatosEstadoVacaciones();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarEstadoVacaciones")
    public ResponseEntity<Cat_Estado_Vacaciones> guardarCatalogoEstadoVacaciones(@RequestBody Cat_Estado_Vacaciones cat_Estado_Vacaciones) {
        try {
            return new ResponseEntity<>(catalogosService.saveEstadoVacaciones(cat_Estado_Vacaciones), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarEstadoVacaciones/{id}")
    public ResponseEntity<OutputEntity<Cat_Estado_Vacaciones>> findOneEstadoVacaciones(@PathVariable Integer id) {
        OutputEntity<Cat_Estado_Vacaciones> out = new OutputEntity<>();
        try {
            Cat_Estado_Vacaciones result = catalogosService.findOneEstadoVacaciones(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarEstadoVacaciones/{id_estado_vacaciones}")
    public ResponseEntity<OutputEntity<String>> updateEstadoVacaciones(@RequestBody Cat_Estado_Vacaciones estadoVacaciones, @PathVariable("id_estado_vacaciones") Integer id_estado_vacaciones) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateEstadoVacaciones(estadoVacaciones, id_estado_vacaciones);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos del Estado de Vacaciones modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @SystemControllerLog(operation = "cambioEstatusEstadoVacaciones", type = "Cambiar el estatus de estado vacaciones") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusEstadoVacaciones/{id_estado_vacaciones}/{estatus}")
    public ResponseEntity<Cat_Estado_Vacaciones> cambioEstatusEstadoVacaciones(@RequestBody @PathVariable Integer id_estado_vacaciones, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusEstadoVacaciones(id_estado_vacaciones, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO ESTRUCTURA ************************************
    @GetMapping(value = "/listarEstructura")
    public ResponseEntity<Cat_EstructuraDTO> listarCat_Estructura() {
        try {
            List<Cat_EstructuraDTO> result = catalogosService.findAllEstructura();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar Todos Los Datos
    @GetMapping(value = "/listarDatosEstructura")
    public ResponseEntity<Cat_Estructura> listarDatosCat_Estructura() {
        try {
            List<Cat_Estructura> result = catalogosService.findAllDatosEstructura();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarEstructura")
    public ResponseEntity<Cat_Estructura> guardarCatalogoEstructura(@RequestBody Cat_Estructura cat_Estructura) {
        try {
            return new ResponseEntity<>(catalogosService.saveEstructura(cat_Estructura), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Buscar Estructura por ID 
    @GetMapping(value = "/buscarEstructura/{id}")
    public ResponseEntity<OutputEntity<Cat_Estructura>> findOneEstructura(@PathVariable Integer id) {
        OutputEntity<Cat_Estructura> out = new OutputEntity<>();
        try {
            Cat_Estructura result = catalogosService.findOneEstructura(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualizar los datos de la estructura
    @PostMapping(value = "/editarEstructura/{id_estructura}")
    public ResponseEntity<OutputEntity<String>> updateEstructura(@RequestBody Cat_Estructura estructura, @PathVariable("id_estructura") Integer id_estructura) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateEstructura(estructura, id_estructura);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de estructura modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Cambiar el estatus de la estructura
    @SystemControllerLog(operation = "cambioEstatusEstructura", type = "Cambiar el estatus de la estructura") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusEstructura/{id_estructura}/{estatus}")
    public ResponseEntity<Cat_Estructura> cambioEstatusEstructura(@RequestBody @PathVariable Integer id_estructura, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusEstructura(id_estructura, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO ESTUDIOS ************************************
    @GetMapping(value = "/listarEstudios")
    public ResponseEntity<Cat_EstudiosDTO> listarCat_Estudios() {
        try {
            List<Cat_EstudiosDTO> result = catalogosService.findAllEstudios();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosEstudios")
    public ResponseEntity<Cat_Estudios> listarDatosCat_Estudios() {
        try {
            List<Cat_Estudios> result = catalogosService.findAllDatosEstudios();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarEstudios")
    public ResponseEntity<Cat_Estudios> guardarCatalogoEstudios(@RequestBody Cat_Estudios cat_Estudios) {
        try {
            return new ResponseEntity<>(catalogosService.saveEstudios(cat_Estudios), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarEstudios/{id_grado}")
    public ResponseEntity<OutputEntity<String>> updateEstudios(@RequestBody Cat_Estudios estudios, @PathVariable("id_grado") Integer id_grado) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateEstudios(estudios, id_grado);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de estudio modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    //Buscar estudios por ID 

    @GetMapping(value = "/buscarEstudio/{id}")
    public ResponseEntity<OutputEntity<Cat_Estudios>> findOneEstudio(@PathVariable Integer id) {
        OutputEntity<Cat_Estudios> out = new OutputEntity<>();
        try {
            Cat_Estudios result = catalogosService.findOneEstudio(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SystemControllerLog(operation = "cambioEstatusEstudios", type = "Cambiar el estatus del estudio") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusEstudios/{id_grado}/{estatus}")
    public ResponseEntity<Cat_Estudios> cambioEstatusEstudios(@RequestBody @PathVariable Integer id_grado, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusEstudios(id_grado, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGOS DE INCAPACIDAD ************************************
    @GetMapping(value = "/listarMotivosIncapacidad")
    public ResponseEntity<Cat_Motivo_Incapacidad> listarMotivosIncapacidad() {
        try {
            List<Cat_Motivo_Incapacidad> result = catalogosService.findAllMotivosIncapacidad();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarTiposIncapacidad")
    public ResponseEntity<Cat_Tipo_Incapacidad> listarTiposIncapacidad() {
        try {
            List<Cat_Tipo_Incapacidad> result = catalogosService.findAllTiposIncapacidad();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarTipoRiesgoIncapacidad")
    public ResponseEntity<Cat_Tipo_Riesgo_Incapacidad> listarTipoRiesgoIncapacidad() {
        try {
            List<Cat_Tipo_Riesgo_Incapacidad> result = catalogosService.findAllTipoRiesgoIncapacidad();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarTipoSecuelasIncapacidad")
    public ResponseEntity<Cat_Tipo_Secuelas_Incapacidad> listarTipoSecuelasIncapacidad() {
        try {
            List<Cat_Tipo_Secuelas_Incapacidad> result = catalogosService.findAllTipoSecuelasIncapacidad();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarTipoControlIncapacidad")
    public ResponseEntity<Cat_Tipo_Control_Incapacidad> listarTipoControlIncapacidad() {
        try {
            List<Cat_Tipo_Control_Incapacidad> result = catalogosService.findAllTipoControlIncapacidad();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO GENERO ************************************
    @GetMapping(value = "/listarGenero")
    public ResponseEntity<Cat_GeneroDTO> listarCat_Genero() {
        try {
            List<Cat_GeneroDTO> result = catalogosService.findAllGenero();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarDatosGenero")
    public ResponseEntity<Cat_Genero> listarDatosCat_Genero() {
        try {
            List<Cat_Genero> result = catalogosService.findAllDatosGenero();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarGenero")
    public ResponseEntity<Cat_Genero> guardarCatalogoGenero(@RequestBody Cat_Genero cat_Genero) {
        try {
            return new ResponseEntity<>(catalogosService.saveGenero(cat_Genero), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarGenero/{id}")
    public ResponseEntity<OutputEntity<Cat_Genero>> findOneGenero(@PathVariable Integer id) {
        OutputEntity<Cat_Genero> out = new OutputEntity<>();
        try {
            Cat_Genero result = catalogosService.findOneGenero(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarGenero/{id_genero}")
    public ResponseEntity<OutputEntity<String>> updateGenero(@RequestBody Cat_Genero genero, @PathVariable("id_genero") Integer id_genero) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateGenero(genero, id_genero);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de género modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @SystemControllerLog(operation = "cambioEstatusGenero", type = "Cambiar el estatus del género") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusGenero/{id_genero}/{estatus}")
    public ResponseEntity<Cat_Genero> cambioEstatusGenero(@RequestBody @PathVariable Integer id_genero, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusGenero(id_genero, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO DIAS  ************************************
    @GetMapping(value = "/listarDias")
    public ResponseEntity<Cat_DiasDTO> listarDias() {
        try {
            List<Cat_DiasDTO> result = catalogosService.findAllDias();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosDias")
    public ResponseEntity<Cat_Dias> listarDatosDias() {
        try {
            List<Cat_Dias> result = catalogosService.findAllDatosDias();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarDias")
    public ResponseEntity<Cat_Dias> guardarSangre(@RequestBody Cat_Dias cat_Dias) {
        try {
            return new ResponseEntity<>(catalogosService.saveDias(cat_Dias), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarDia/{id}")
    public ResponseEntity<OutputEntity<Cat_Dias>> findOneDia(@PathVariable Integer id) {
        OutputEntity<Cat_Dias> out = new OutputEntity<>();
        try {
            Cat_Dias result = catalogosService.findOneDia(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualizar los datos del Día
    @PostMapping(value = "/editarDia/{id_dia}")
    public ResponseEntity<OutputEntity<String>> editarDias(@RequestBody Cat_Dias dias, @PathVariable("id_dia") Integer id_dia) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.editarDias(dias, id_dia);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de Día modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Guardar nuevo día 
    @PostMapping("/GuardarDia")
    public ResponseEntity<Cat_Dias> saveDia(@RequestBody Cat_Dias dias) {
        try {
            return new ResponseEntity<>(catalogosService.saveDia(dias), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Edición de estatus de día
    @SystemControllerLog(operation = "cambioEstatusDia", type = "Cambiar el estatus del día") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusDia/{id_dia}/{estatus}")
    public ResponseEntity<Cat_Dias> cambioEstatusDia(@RequestBody @PathVariable Integer id_dia, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusDia(id_dia, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO PARENTESCO  ************************************
    @GetMapping(value = "/listarTiposDatosIncidencias")
    public ResponseEntity<Cat_Tipo_Dato_Incidencias> listarTipoDatosIncidencias() {
        try {
            List<Cat_Tipo_Dato_Incidencias> result = catalogosService.findAllCatTipoDatoIncidencias();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO PARENTESCO  ************************************
    @GetMapping(value = "/listarParentesco")
    public ResponseEntity<Cat_ParentescoDTO> listarParentesco() {
        try {
            List<Cat_ParentescoDTO> result = catalogosService.findAllParentesco();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosParentesco")
    public ResponseEntity<Cat_Parentesco> listarDatosParentesco() {
        try {
            List<Cat_Parentesco> result = catalogosService.findAllDatosParentesco();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarParentesco")
    public ResponseEntity<Cat_Parentesco> guardarParentesco(@RequestBody Cat_Parentesco cat_Parentesco) {
        try {
            return new ResponseEntity<>(catalogosService.saveParentesco(cat_Parentesco), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarParentesco/{id_parentesco}")
    public ResponseEntity<OutputEntity<String>> updateParentesco(@RequestBody Cat_Parentesco parentesco, @PathVariable("id_parentesco") Integer id_parentesco) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateParentesco(parentesco, id_parentesco);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de parentesco modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @SystemControllerLog(operation = "cambioEstatusParentesco", type = "Cambiar el estatus del parentesco") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusParentesco/{id_parentesco}/{estatus}")
    public ResponseEntity<Cat_Parentesco> cambioEstatusParentesco(@RequestBody @PathVariable Integer id_parentesco, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusParentesco(id_parentesco, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Buscar parentesco por ID 
    @GetMapping(value = "/buscarParentesco/{id}")
    public ResponseEntity<OutputEntity<Cat_Parentesco>> findOneParentesco(@PathVariable Integer id) {
        OutputEntity<Cat_Parentesco> out = new OutputEntity<>();
        try {
            Cat_Parentesco result = catalogosService.findOneParentesco(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO INHABILITADO ************************************
    @GetMapping(value = "/listarInhabilitado")
    public ResponseEntity<Cat_InhabilitadoDTO> listarCat_Inhabilitado() {
        try {
            List<Cat_InhabilitadoDTO> result = catalogosService.findAllInhabilitado();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosInhabilitado")
    public ResponseEntity<Cat_Inhabilitado> listarDatosCat_Inhabilitado() {
        try {
            List<Cat_Inhabilitado> result = catalogosService.findAllDatosInhabilitado();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarInhabilitado")
    public ResponseEntity<Cat_Inhabilitado> guardarCatalogoInhabilitado(@RequestBody Cat_Inhabilitado cat_Inhabilitado) {
        try {
            return new ResponseEntity<>(catalogosService.saveInhabilitdo(cat_Inhabilitado), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Guardar datos e inicializar el estatus con 1
    @PostMapping(value = "/guardarInhabilitado")
    public ResponseEntity<OutputEntity<String>> saveInhabilitdo(@RequestBody Cat_InhabilitadoDTO cat_Inhabilitado) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.saveInhabilitdo(cat_Inhabilitado);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Inhabilitado Creado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Actualizar datos de Catatalogo Inhabilitado
    @PostMapping("/actualizarInhabilitado/{id_inhabilitado}")
    public ResponseEntity<OutputEntity<String>> updateInhabilitado(@RequestBody Cat_InhabilitadoDTO cat_Inhabilitado, @PathVariable Integer id_inhabilitado) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateInhabilitado(id_inhabilitado, cat_Inhabilitado);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Inhabilitado Actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Buscar Inhabilitado por ID
    @GetMapping("/buscarInhabilitado/{id_inhabilitado}")
    public ResponseEntity<Cat_Inhabilitado> findOneInhabilitado(@PathVariable Integer id_inhabilitado) {
        try {
            Cat_Inhabilitado result = catalogosService.findOneInhabilitado(id_inhabilitado);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Estado de Inhabilitado activo o inactivo**//
    //1 Activo - 0 Inactivo
    @GetMapping(value = "/estadoInhabilitado/{id_inhabilitado}/{estatus}")
    public ResponseEntity<Cat_Inhabilitado> estatusInhabilitado(@RequestBody @PathVariable Integer id_inhabilitado, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.estatusInhabilitado(id_inhabilitado, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO LICENCIA ************************************
    @GetMapping(value = "/listarLicencia")
    public ResponseEntity<Cat_LicenciaDTO> listarCat_Licencia() {
        try {
            List<Cat_LicenciaDTO> result = catalogosService.findAllLicencia();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosLicencia")
    public ResponseEntity<Cat_Licencia> listarDatosCat_Licencia() {
        try {
            List<Cat_Licencia> result = catalogosService.findAllDatosLicencia();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarLicencia")
    public ResponseEntity<Cat_Licencia> guardarCatalogoLicencia(@RequestBody Cat_Licencia cat_Licencia) {
        try {
            return new ResponseEntity<>(catalogosService.saveLicencia(cat_Licencia), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscarLicencia/{id_licencia}")
    public ResponseEntity<Cat_Licencia> findOneLicencia(@PathVariable Integer id_licencia) {
        try {
            Cat_Licencia result = catalogosService.findOneLicencia(id_licencia);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/actualizarLicencia/{id_licencia}")
    public ResponseEntity<OutputEntity<String>> updateLicencia(@RequestBody Cat_LicenciaDTO licencia, @PathVariable Integer id_licencia) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateLicencia(id_licencia, licencia);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Usuario Actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping("/borrarLicencia/{id_licencia}")
    public ResponseEntity<OutputEntity<Integer>> deleteLicencia(@PathVariable("id_licencia") Integer id_licencia) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            catalogosService.deleteLicencia(id_licencia);
            out.success(Response.DELETED.getCode(), Response.DELETED.getKey(), null);
            return ResponseEntity.ok(out);
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //1 Activo - 0 Inactivo
    @GetMapping(value = "/estadoLicencia/{id_licencia}/{estatus}")
    public ResponseEntity<Cat_Licencia> estatusLicencia(@RequestBody @PathVariable Integer id_licencia, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.estatusLicencia(id_licencia, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO Nacionalidad ************************************
    @GetMapping(value = "/listarNacionalidad")
    public ResponseEntity<Cat_NacionalidadDTO> listarCat_Nacionalidad() {
        try {
            List<Cat_NacionalidadDTO> result = catalogosService.findAllNacionalidad();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosNacionalidad")
    public ResponseEntity<Cat_Nacionalidad> listarDatosCat_Nacionalidad() {
        try {
            List<Cat_Nacionalidad> result = catalogosService.findAllDatosNacionalidad();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarNacionalidad")
    public ResponseEntity<Cat_Nacionalidad> guardarCatalogoNacionalidad(@RequestBody Cat_Nacionalidad cat_Nacionalidad) {
        try {
            return new ResponseEntity<>(catalogosService.saveNacionalidad(cat_Nacionalidad), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Guardar datos e inicializar el estatus con 1
    @PostMapping(value = "/guardarNacionalidad")
    public ResponseEntity<OutputEntity<String>> saveNacionalidad(@RequestBody Cat_NacionalidadDTO cat_Nacionalidad) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.saveNacionalidad(cat_Nacionalidad);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Nacionalidad Creada");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Buscar Nacionalidad por ID
    @GetMapping("/buscarNacionalidad/{id_nacionalidad}")
    public ResponseEntity<Cat_Nacionalidad> findOneNacionalidad(@PathVariable Integer id_nacionalidad) {
        try {
            Cat_Nacionalidad result = catalogosService.findOneNacionalidad(id_nacionalidad);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualizar Nacionalidad
    @PostMapping(value = "/actualizarNacionalidad/{id_nacionalidad}")
    public ResponseEntity<OutputEntity<String>> updateNacionalidad(@RequestBody Cat_NacionalidadDTO cat_Nacionalidad, @PathVariable Integer id_nacionalidad) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateNacionalidad(id_nacionalidad, cat_Nacionalidad);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Nacionalidad Actualizada");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping(value = "/estadoNacionalidad/{id_nacionalidad}/{estatus}")
    public ResponseEntity<OutputEntity<Cat_Nacionalidad>> estatusNacionalidad(@RequestBody @PathVariable Integer id_nacionalidad, @PathVariable Integer estatus) {
        OutputEntity<Cat_Nacionalidad> out = new OutputEntity<>();
        try {
            Cat_Nacionalidad result = catalogosService.estatusNacionalidad(id_nacionalidad, estatus);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //******************* CATALOGO Nacionalidad ************************************
    @GetMapping(value = "/listarSi_No")
    public ResponseEntity<Cat_Si_NoDTO> listarCat_Si_No() {
        try {
            List<Cat_Si_NoDTO> result = catalogosService.findAllSiNo();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosSi_No")
    public ResponseEntity<Cat_Si_No> listarDatosSi_No() {
        try {
            List<Cat_Si_No> result = catalogosService.findAllDatosSiNo();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/guardarSiNo")
    public ResponseEntity<Cat_Si_No> guardarCatalogoSi_No(@RequestBody Cat_Si_No cat_Si_No) {
        try {
            return new ResponseEntity<>(catalogosService.saveSiNo(cat_Si_No), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualizar Si No
    @PostMapping(value = "/actualizarSiNo/{id}")
    public ResponseEntity<OutputEntity<String>> updateSiNo(@RequestBody Cat_Si_NoDTO cat_Si_No, @PathVariable Integer id) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateSiNo(id, cat_Si_No);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "SiNo actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping(value = "/buscarSiNo/{id}")
    public ResponseEntity<Cat_Si_No> findOneSiNo(@PathVariable Integer id) {
        try {
            Cat_Si_No result = catalogosService.findOneSiNo(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/estadoSiNo/{id}/{estatus}")
    public ResponseEntity<OutputEntity<Cat_Si_No>> estatusSiNo(@RequestBody @PathVariable Integer id, @PathVariable Integer estatus) {
        OutputEntity<Cat_Si_No> out = new OutputEntity<>();
        try {
            Cat_Si_No result = catalogosService.estatusSiNo(id, estatus);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    // **************** CATALOGO EXPEDIENTE *****************************
    @GetMapping(value = "/buscarIdExpediente/{expediente}")
    public ResponseEntity<OutputEntity<Integer>> buscarIdExpediente(@RequestBody @PathVariable("expediente") String numero_expediente) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Integer result = catalogosService.idExpediente(numero_expediente);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
//            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarExpediente")
    public ResponseEntity<Cat_ExpedienteDTO> listarCat_Expediente() {
        try {
            List<Cat_ExpedienteDTO> result = catalogosService.findAllExpedientes();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ------------------------- Listar todos los datos de la tabla expediente ------------------------------ 
    @GetMapping(value = "/listarDatosExpediente")
    public ResponseEntity<Cat_Expediente> listarExpedientes() {
        try {
            List<Cat_Expediente> result = catalogosService.findAllExp();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ------------------------- Cambiar el Status del expediente ------------------------------ 
    @GetMapping(value = "/estatusExpediente/{id_expediente}/{estatus}")
    public ResponseEntity<Cat_Expediente> estatusExpediente(@RequestBody @PathVariable Integer id_expediente, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.activo(id_expediente, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ------------------------- Guardar un nuevo expediente ------------------------------ 
    @PostMapping(value = "/guardarExpediente")
    public ResponseEntity<OutputEntity<String>> guardarNum_Expediente(@RequestBody Cat_NumExpedienteDTO expediente) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            if (catalogosService.existsByNumExpediente(expediente.getNumero_expediente()) != null) {
                throw new Exceptions(Response.EXPEDIENTEEXISTE.getKey(), Response.EXPEDIENTEEXISTE.getCode());
            }
            catalogosService.saveExpediente(expediente);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Expediente Guardado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exceptions e) {
            out.failed(e.getCode(), e.getMessages(), null);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
    //---------------- ACTUALIZAR VALOR DE ASIGNADO EXPEDIENTE -----------------

    @PostMapping(value = "/actualizarExpediente/{id}")
    public ResponseEntity<OutputEntity<String>> actualizarExp(@RequestBody Cat_NumExpedienteDTO expediente, @PathVariable Integer id) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.update(id, expediente);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Expediente Actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping("/ultimoExpediente")
    public ResponseEntity<Cat_Expediente> ultimoExpediente() {
        try {
            Cat_Expediente result = catalogosService.ultimoExpediente();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ultimoIdExpediente")
    public ResponseEntity<Integer> ultimoIdExpediente() {
        try {
            Integer result = catalogosService.ultimoIdExpediente();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO SANGRE  ************************************
    @GetMapping(value = "/listarSangre")
    public ResponseEntity<Cat_SangreDTO> listarSangre() {
        try {
            List<Cat_SangreDTO> result = catalogosService.findAllSangre();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosSangre")
    public ResponseEntity<Cat_Sangre> listarDatosSangre() {
        try {
            List<Cat_Sangre> result = catalogosService.findAllDatosSangre();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarSangre")
    public ResponseEntity<Cat_Sangre> guardarSangre(@RequestBody Cat_Sangre cat_Sangre) {
        try {
            return new ResponseEntity<>(catalogosService.saveSangre(cat_Sangre), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Guardar datos de Catalogo Sangre
    @PostMapping(value = "/guardarDatosSangre")
    public ResponseEntity<OutputEntity<String>> saveSangre(@RequestBody Cat_SangreDTO cat_Sangre) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.saveSangre(cat_Sangre);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Datos guardados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Actualizar datos de Catalogo Sangre
    @PostMapping(value = "/actualizarDatosSangre/{id_sangre}")
    public ResponseEntity<OutputEntity<String>> updateSangre(@RequestBody Cat_SangreDTO cat_Sangre, @PathVariable Integer id_sangre) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateSangre(id_sangre, cat_Sangre);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de Sangre Actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Estado de Sangre ACTIVO o INACTIVO
    @GetMapping(value = "/estadoSangre/{id_sangre}/{estatus}")
    public ResponseEntity<Cat_Sangre> estatusSangre(@RequestBody @PathVariable Integer id_sangre, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.estatusSangre(id_sangre, estatus), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Buscar Sangre por ID

    @GetMapping("/buscarSangre/{id_sangre}")
    public ResponseEntity<Cat_Sangre> findOneSangre(@PathVariable Integer id_sangre) {
        try {
            Cat_Sangre result = catalogosService.findOneSangre(id_sangre);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos del Catalogo Parentesco
    @GetMapping(value = "/listarDatosCat_Parentesco")
    public ResponseEntity<Cat_Parentesco> listarDatosCatParentesco() {
        try {
            List<Cat_Parentesco> result = catalogosService.findAllCatalogo_Parentesco();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos del Catalogo Tipo Beneficiario
    @GetMapping(value = "/listarDatosCat_Tipo_Beneficiario")
    public ResponseEntity<Cat_Tipo_Beneficiario> listarDatosCat_Tipo_Beneficiario() {
        try {
            List<Cat_Tipo_Beneficiario> result = catalogosService.findAllCatalogo_TipoBeneficiario();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR.. " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO BANCOS  ************************************
    @GetMapping(value = "/buscarBanco/{id}")
    public ResponseEntity<OutputEntity<Cat_Banco>> findOneBanco(@PathVariable Integer id) {
        OutputEntity<Cat_Banco> out = new OutputEntity<>();
        try {
            Cat_Banco result = catalogosService.findOneBanco(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Guardar nuevo banco
    @PostMapping("/GuardarBanco")
    public ResponseEntity<Cat_Banco> saveBanco(@RequestBody Cat_Banco banco) {
        try {
            return new ResponseEntity<>(catalogosService.saveBanco(banco), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar para combo de banco
    @GetMapping(value = "/listarBancos")
    public ResponseEntity<Cat_Banco> findAllDatosBanco() {
        try {
            List<Cat_Banco> result = catalogosService.findAllDatosBanco();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualizar los datos del banco
    @PostMapping(value = "/editarBanco/{id_banco}")
    public ResponseEntity<OutputEntity<String>> updateBanco(@RequestBody Cat_Banco banco, @PathVariable("id_banco") Integer id_banco) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            Integer result = catalogosService.findMaxComision(id_banco);
            Double max_com = 1.0 * result;
            if (banco.getComision() < max_com) {
                catalogosService.updateBanco(banco, id_banco);
                out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de banco modificados con éxito");
            }
            return new ResponseEntity<>(out, out.getCode());

        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @SystemControllerLog(operation = "cambioEstatusBanco", type = "Cambiar el estatus del banco") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusBanco/{id_banco}/{estatus}")
    public ResponseEntity<Cat_Banco> cambioEstatusBanco(@RequestBody @PathVariable Integer id_banco, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusBanco(id_banco, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping(value = "/comisionMax/{id_banco}")
//    public ResponseEntity<OutputEntity<Integer>> comisionMax(@PathVariable("id_banco") Integer id_banco) {
//        OutputEntity<Integer> out = new OutputEntity<>();
//        try {
//            Integer result = catalogosService.findMaxComision(id_banco);
//            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
//            return new ResponseEntity<>(out, out.getCode());
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    //******************************************************** CATALOGO PUESTO ************************************************************
    //------------------------ Listar Datos de los Puestos ----------------------------
    @SystemControllerLog(operation = "listarDatosPuesto", type = "Listó los datos de los puestos disponibles") //Log de quien ejecuta el metodo
    @GetMapping(value = "/listarDatosCatPuesto")
    public ResponseEntity<Cat_Puesto> listarDatosCatPuesto() {
        try {
            List<Cat_Puesto> result = catalogosService.findAllPuesto();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("ERROR.. " +  e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //---------------- Guardar los Datos Puestos ------------
    @SystemControllerLog(operation = "guardarDatosPuesto", type = "Guardo los datos de un puesto") //Log de quien ejecuta el metodo
    @PostMapping(value = "/guardarPuesto")
    public ResponseEntity<OutputEntity<Cat_Puesto>> guardarPuesto(@RequestBody Cat_Puesto puesto) {
        OutputEntity<Cat_Puesto> out = new OutputEntity<>();
        try {
            if (catalogosService.existsByCodPuesto(puesto.getCodigo_puesto()) != null) {
//                System.out.println("qqqqqqqqqqqqq");
                throw new Exceptions(Response.CODPUESTOEXISTE.getKey(), Response.CODPUESTOEXISTE.getCode());
            }
//            System.out.println("qqqqqqqqqqqqq");
            catalogosService.savePuesto(puesto);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), null);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exceptions e) {
            Cat_Puesto result = catalogosService.existsByCodPuesto(puesto.getCodigo_puesto());
            out.failed(e.getCode(), e.getMessages(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }
 
    @GetMapping(value = "/encontrarPuesto/{id_puesto}")
    public ResponseEntity<Cat_Puesto> encontrarIdPuesto(@PathVariable Integer id_puesto) {
        try {
            Cat_Puesto result = catalogosService.findByIdPuesto(id_puesto);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //-------------------- Listar los Datos de Puestos ------------------------
    @SystemControllerLog(operation = "listarPuestos", type = "Listó los datos de los puestos disponibles") //Log de quien ejecuta el metodo
    @GetMapping(value = "/listarPuestos")
    public ResponseEntity<Cat_Puesto> listarPuestos() {
        try {
            List<Cat_Puesto> result = catalogosService.findAllPuesto();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {

            System.out.println(e);
            System.out.println("ee " + e);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
    }

    //------------------------------------ Listar datos de Contrato --------------------------------
    @SystemControllerLog(operation = "listarContrato", type = "Listó los datos de Contrato") //Log de quien ejecuta el metodo
    @GetMapping(value = "/listarContrato")
    public ResponseEntity<Cat_Tipo_Contrato> listarContrato() {
        try {
            List<Cat_Tipo_Contrato> result = catalogosService.findAllContrato();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //------------------------------------ Buscar un puesto mediante su código ------------------------------------
    @SystemControllerLog(operation = "buscarCodPuesto", type = "Se buscó información de un puesto mediante su código") //Log de quien ejecuta el metodo
    @GetMapping(value = "/buscarCodPuesto/{cod_puesto}")
    public ResponseEntity<OutputEntity<Cat_Puesto>> buscarCodigoPuesto(@PathVariable Integer cod_puesto) {
        OutputEntity<Cat_Puesto> out = new OutputEntity<>();
        try {
            Cat_Puesto result = catalogosService.findCodigoPuesto(cod_puesto);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //-------------------------------- Editar los datos de un puesto ------------------------------------------------
    @SystemControllerLog(operation = "editarPuesto", type = "Editó los datos de un puesto") //Log de quien ejecuta el metodo
    @PostMapping(value = "/editarPuesto/{codigo_puesto}")
    public ResponseEntity<OutputEntity<String>> editarPuesto(@RequestBody Cat_PuestoDTO puesto, @PathVariable Integer codigo_puesto) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.editarCatPuesto(puesto, codigo_puesto);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
//            System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    /**
     * ************************************************************************************************************************
     * CATALOGO PLAZA
     * ************************************************************************************************************************
     */
    @SystemControllerLog(operation = "listarTodasPlazas", type = "Listó tpdas las plazas de la Tabla") //Log de quien ejecuta el metodo
    @GetMapping(value = "/listarTodasPlazas")
    public ResponseEntity<Cat_Plaza> listarTodasPlazas() {
        try {
            List<Cat_Plaza> result = catalogosService.findAllPlaza();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //-------------------------------- Listar las Plazas de un puesto --------------------------------
    @SystemControllerLog(operation = "listarPlazas", type = "Listó los datos de las plazas disponibles") //Log de quien ejecuta el metodo
    @GetMapping(value = "/listarPlazas/{puesto_id}")
    public ResponseEntity<Cat_Plaza> listarPlazas(@RequestBody @PathVariable("puesto_id") Integer puesto_id) {
        try {
            List<Cat_Plaza> result = catalogosService.findAllPlaza(puesto_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ------------- Retorna el número de plazas que tiene registradas un puesto --------------------------
    @SystemControllerLog(operation = "contarPlazas", type = "Contar las plazas disponibles") //Log de quien ejecuta el metodo
    @GetMapping(value = "/contarPlazas/{puesto_id}")
    public ResponseEntity<OutputEntity<Integer>> contarPlazas(@RequestBody @PathVariable("puesto_id") Integer puesto_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Integer result = catalogosService.countPlazas(puesto_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ------------- Retorna el número de plazas disponibles que tiene registradas un puesto --------------------------
    @GetMapping(value = "/contarPlazasDisponibles/{puesto_id}")
    public ResponseEntity<OutputEntity<Integer>> contarPlazasDisponibles(@RequestBody @PathVariable("puesto_id") Integer puesto_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Integer result = catalogosService.countPlazasDisponibles(puesto_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ------------- Retorna el número de plazas Asignadas que tiene registradas un puesto --------------------------
    @GetMapping(value = "/contarPlazasAsignadas/{puesto_id}")
    public ResponseEntity<OutputEntity<Integer>> contarPlazasAsignadas(@RequestBody @PathVariable("puesto_id") Integer puesto_id) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Integer result = catalogosService.countPlazasAsignadas(puesto_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
//            System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ----------------------- Guarda una nueva plaza --------------------------
    @SystemControllerLog(operation = "guardarPlaza", type = "Guardó los datos de una plaza") //Log de quien ejecuta el metodo
    @PostMapping(value = "/guardarPlaza")
    public ResponseEntity<OutputEntity<Cat_Plaza>> guardarPlaza(@RequestBody Cat_Plaza plaza) {
        OutputEntity<Cat_Plaza> out = new OutputEntity<>();
        try {
            catalogosService.savePlaza(plaza);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), null);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //---------------------- Edita los datos de una plaza -----------------------
    @SystemControllerLog(operation = "editarPlaza", type = "Editó los datos de una plaza") //Log de quien ejecuta el metodo
    @PostMapping(value = "/editarPlaza/{id_plaza}")
    public ResponseEntity<OutputEntity<String>> editarPlaza(@RequestBody Cat_Plaza plaza, @PathVariable Integer id_plaza) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.editarCatPlaza(plaza, id_plaza);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos actualizados");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    // ----------------------- Buscar los datos de una plaza mediante su id -----------------------
    @SystemControllerLog(operation = "buscarPlaza", type = "Buscar los datos de una plaza") //Log de quien ejecuta el metodo
    @GetMapping(value = "/buscarPlaza/{plaza_id}")
    public ResponseEntity<OutputEntity<Cat_Plaza>> buscarPlaza(@PathVariable("plaza_id") Integer plaza_id) {
        OutputEntity<Cat_Plaza> out = new OutputEntity<>();
        try {
            Cat_Plaza result = catalogosService.findOnePlaza(plaza_id);

            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ----------------------- Cambiar el estatus de un Puesto -----------------------
    @SystemControllerLog(operation = "cambiarEstatus", type = "Cambiar el estatus de un puesto") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambiarEstatus/{id_puesto}/{estatus}")
    public ResponseEntity<Cat_Puesto> cambiarEstatus(@RequestBody @PathVariable Integer id_puesto, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatus(id_puesto, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ----------------------- Cambiar el estatus de una Plaza -----------------------
    @SystemControllerLog(operation = "cambiarEstatusPlaza", type = "Cambiar el estatus de una plaza") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambiarEstatusPlaza/{id_plaza}/{estatus}")
    public ResponseEntity<Cat_Plaza> cambiarEstatusPlaza(@RequestBody @PathVariable Integer id_plaza, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusPlaza(id_plaza, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/ultimoRegistro")//Devuelve el ultimo id de la tabla
    public ResponseEntity<OutputEntity<Integer>> ultimoRegistro() {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Integer result = catalogosService.ultimoId();
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarPlazasDisponibles/{puesto_id}")
    public ResponseEntity<Cat_Plaza> buscarPlazasDisponibles(@RequestBody @PathVariable("puesto_id") Integer puesto_id) {
        try {
            List<Cat_Plaza> result = catalogosService.findPlazasDisponibles(puesto_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    
    /**
     * ************************************************************************************************************************
     * CATALOGO CONTRATO NOMINA
     * ************************************************************************************************************************
     */
    @GetMapping(value = "/listarContratoNomina")
    public ResponseEntity<Cat_Contrato_Nomina> listarContratoNomina() {
        try {
            List<Cat_Contrato_Nomina> result = catalogosService.findAllContratoNomina();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarIdContrato/{id_contrato}")
    public ResponseEntity<Cat_Contrato_Nomina> listarIdContrato(@RequestBody @PathVariable("id_contrato") Integer id_contrato) {
        try {
            List<Cat_Contrato_Nomina> result = catalogosService.findIdContrato(id_contrato);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ************************************************************************************************************************
     * CATALOGO NOMINA UBICACION
     * ************************************************************************************************************************
     */
    @GetMapping(value = "/listarIdNomina/{id_nomina}")
    public ResponseEntity<Cat_Nomina_Ubicacion> listarIdNomina(@RequestBody @PathVariable("id_nomina") Integer id_nomina) {
        try {
            List<Cat_Nomina_Ubicacion> result = catalogosService.findIdNomina(id_nomina);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ************************************************************************************************************************
     * HISTORICO PUESTO
     * ************************************************************************************************************************
     */
    @PostMapping("/guardaHistoricoPuesto")
    public ResponseEntity<Historico_Puesto> guardaHistoricoPuesto(@RequestBody Historico_Puesto historico) {
        try {
            return new ResponseEntity<>(catalogosService.saveHistorico(historico), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ************************************************************************************************************************
     * HISTORICO TRABAJADOR PLAZA
     * ************************************************************************************************************************
     */
    @PostMapping("/guardaHistoricoTrabajadorPlaza")
    public ResponseEntity<Historico_Trabajador_Plaza> guardaHistoricoTrabajadorPlaza(@RequestBody Historico_Trabajador_Plaza historico) {
        try {
            return new ResponseEntity<>(catalogosService.saveHistoricoTP(historico), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ************************************************************************************************************************
     * CATALOGO DIAS VACACIONES
     * ************************************************************************************************************************
     */
    @GetMapping(value = "/listarDiasDisponibles/{tipo_dias_vacaciones_id}/{antiguedad}")
    public ResponseEntity<OutputEntity<Integer>> listarDiasDisponibles(@RequestBody @PathVariable("tipo_dias_vacaciones_id") Integer tipo_dias_vacaciones_id, @PathVariable("antiguedad") Double antiguedad) {
        OutputEntity<Integer> out = new OutputEntity<>();
        try {
            Integer result = catalogosService.diasDisponibles(tipo_dias_vacaciones_id, antiguedad);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * ************************************************************************************************************************
     * CATALOGO INCIDENCIAS
     * ************************************************************************************************************************
     */
    @GetMapping(value = "/listarCatIncidencias")
    public ResponseEntity<Cat_IncidenciasDTO> listarCatIncidencias() {
        try {
            List<Cat_IncidenciasDTO> result = catalogosService.findAllIncidencias();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosIncidencias")
    public ResponseEntity<Cat_Incidencias> listarDatosIncidencias() {
        try {
            List<Cat_Incidencias> result = catalogosService.findAllDatosIncidencias();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarIncidencia/{tipo_incidencia_id}")
    public ResponseEntity<OutputEntity<List<Cat_Incidencias>>> buscarTipoID(@PathVariable("tipo_incidencia_id") Integer tipo_incidencia_id) {
        OutputEntity<List<Cat_Incidencias>> out = new OutputEntity<>();
        try {
            List<Cat_Incidencias> result = catalogosService.findIncID(tipo_incidencia_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarCatIncidencias")
    public ResponseEntity<Cat_Incidencias> guardarCatInciderncias(@RequestBody Cat_Incidencias cat_incidencias) {
        try {
            return new ResponseEntity<>(catalogosService.saveCat(cat_incidencias), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/encuentraUnElementoCatIncidencias/{id_incidencia}")
    public ResponseEntity<OutputEntity<Cat_Incidencias>> encuentraCatIncidencia(@PathVariable("id_incidencia") Integer id_incidencia) {
        OutputEntity<Cat_Incidencias> out = new OutputEntity<>();
        try {
            Cat_Incidencias result = catalogosService.findOneIncidencia(id_incidencia);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/guardarIncidencia")
    public ResponseEntity<OutputEntity<String>> saveIncidencia(@RequestBody Cat_Incidencias incidencias) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.saveIncidencia(incidencias);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), "Nacionalidad Creada");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping(value = "/actualizarIncidencia/{id_incidencia}")
    public ResponseEntity<OutputEntity<String>> actualizarIncidencia(@PathVariable Integer id_incidencia, @RequestBody Cat_Incidencias incidencias) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.actualizarIncidencia(id_incidencia, incidencias);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Incidencia Actualizada");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarUnaIncidencia/{tipo_Incidencia_id}")
    public ResponseEntity<OutputEntity<List<Cat_Incidencias>>> findIncidencia(@PathVariable("tipo_Incidencia_id") Integer tipo_Incidencia_id) {
        //OutputEntity<List<Cat_Incidencias>> out = new OutputEntity<>();
        try {
            List<Cat_Incidencias> result = catalogosService.findIncidencia(tipo_Incidencia_id);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarIncidenciasKardex")
    public ResponseEntity<OutputEntity<List<Cat_Incidencias>>> encuentraIncidenciasKardex() {
        //OutputEntity<List<Cat_Incidencias>> out = new OutputEntity<>();
        try {
            List<Cat_Incidencias> result = catalogosService.findIncidenciasKardex();
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarDeducciones")
    public ResponseEntity<OutputEntity<List<Cat_Incidencias>>> listaIncidenciasDeducciones() {
        //OutputEntity<List<Cat_Incidencias>> out = new OutputEntity<>();
        try {
            List<Cat_Incidencias> result = catalogosService.findAllDeducciones();
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SystemControllerLog(operation = "cambioEstatusIncidencias", type = "Cambiar el estatus de la incidencia") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusIncidencias/{id_incidencia}/{estatus}")
    public ResponseEntity<Cat_Incidencias> cambioEstatusIncidencias(@RequestBody @PathVariable Integer id_incidencia, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusIncidencias(id_incidencia, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO TIPO INCIDENCIAS  ************************************
    @GetMapping(value = "/listarTipoIncidencias")
    public ResponseEntity<Cat_Tipo_IncidenciaDTO> listarTipoIncidencias() {
        try {
            List<Cat_Tipo_IncidenciaDTO> result = catalogosService.findAllTipo();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosTipoIncidencias")
    public ResponseEntity<Cat_Tipo_Incidencia> listarDatosTipoIncidencias() {
        try {
            List<Cat_Tipo_Incidencia> result = catalogosService.findDatosTipo();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarTipoIncidencia/{tipo_incidencia_id}")
    public ResponseEntity<OutputEntity<List<Cat_Tipo_Incidencia>>> buscarTipoIncID(@PathVariable("tipo_incidencia_id") Integer tipo_incidencia_id) {
        OutputEntity<List<Cat_Tipo_Incidencia>> out = new OutputEntity<>();
        try {
            List<Cat_Tipo_Incidencia> result = catalogosService.findTipoID(tipo_incidencia_id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/guardarTipoIncidencia")
    public ResponseEntity<Cat_Tipo_Incidencia> guardarTipoIncidencias(@RequestBody Cat_Tipo_Incidencia cat_tipo_incidencia) {
        try {
            return new ResponseEntity<>(catalogosService.saveTipo(cat_tipo_incidencia), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/cambioEstatusTipoIncidencia/{id_tipo_incidencia}/{estatus}")
    public ResponseEntity<Cat_Tipo_Incidencia> cambioEstatusTipoIncidencias(@RequestBody @PathVariable Integer id_tipo_incidencia, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusTipoIncidencias(id_tipo_incidencia, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO ESTADO INCIDENCIAS  ************************************
    @GetMapping(value = "/listarEstadoIncidencias")
    public ResponseEntity<Cat_Estado_IncidenciaDTO> listarEstadoIncidencias() {
        try {
            List<Cat_Estado_IncidenciaDTO> result = catalogosService.findEstadoInc();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosEstadoIncidencias")
    public ResponseEntity<Cat_Estado_Incidencia> listarDatosEstadoIncidencias() {
        try {
            List<Cat_Estado_Incidencia> result = catalogosService.findDatosEstadoInc();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//******************* CATALOGO MOTIVO CANCELACION  ************************************

    @GetMapping(value = "/listarMotivoCancelacion")
    public ResponseEntity<Cat_Motivo_Cancelacion> listarMotivoCancelacion() {
        try {
            List<Cat_Motivo_Cancelacion> result = catalogosService.findAllMotivoCancelacion();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//******************* CATALOGO DEPOSITOS  ************************************
    @SystemControllerLog(operation = "guardarDeposito", type = "Guardó los datos de una depósito") //Log de quien ejecuta el metodo
    @PostMapping(value = "/guardarDeposito")
    public ResponseEntity<OutputEntity<Cat_Depositos>> saveDeposito(@RequestBody Cat_Depositos deposito) {
        OutputEntity<Cat_Depositos> out = new OutputEntity<>();
        try {
            catalogosService.saveDeposito(deposito);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), null);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Buscar depósito por ID 
    @GetMapping(value = "/buscarDeposito/{id}")
    public ResponseEntity<OutputEntity<Cat_Depositos>> findOneDeposito(@PathVariable Integer id) {
        OutputEntity<Cat_Depositos> out = new OutputEntity<>();
        try {
            Cat_Depositos result = catalogosService.findOneDeposito(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarDeposito/{id_deposito}")
    public ResponseEntity<OutputEntity<String>> editarDepositos(@RequestBody Cat_Depositos depo, @PathVariable("id_deposito") Integer id_deposito) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.editarDepositos(depo, id_deposito);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de depósito modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @SystemControllerLog(operation = "cambioEstatusDeposito", type = "Cambiar el estatus del deposito") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusDeposito/{id_deposito}/{estatus}")
    public ResponseEntity<Cat_Depositos> cambioEstatusDeposito(@RequestBody @PathVariable Integer id_deposito, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusDeposito(id_deposito, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

// ----------------------- Listar los datos del catalogo Depositos -------------------------------
    @SystemControllerLog(operation = "listarDepositos", type = "Listó los datos del catalogo deposito") //Log de quien ejecuta el metodo
    @GetMapping(value = "/listarDepositos")
    public ResponseEntity<Cat_Depositos> listarDepositos() {
        try {
            List<Cat_Depositos> result = catalogosService.findAllDepositos();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//******************* CATALOGO DÍAS FESTIVOS  ************************************

    @GetMapping(value = "/buscarDiaFestivo/{id_festivos}")
    public ResponseEntity<OutputEntity<Cat_Dias_Festivos>> findOneDiaFestivo(@PathVariable Integer id_festivos) {
        OutputEntity<Cat_Dias_Festivos> out = new OutputEntity<>();
        try {
            Cat_Dias_Festivos result = catalogosService.findOneDiaFestivo(id_festivos);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarDiaFestivo")
    public ResponseEntity<Cat_Dias_Festivos> saveDiaFestivo(@RequestBody Cat_Dias_Festivos diaFestivo) {
        try {
            return new ResponseEntity<>(catalogosService.saveDiaFestivo(diaFestivo), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarDiasFestivos")
    public ResponseEntity<Cat_Dias_Festivos> findAllDatosDiasFestivos() {
        try {
            List<Cat_Dias_Festivos> result = catalogosService.findAllDatosDiasFestivos();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarDiaFestivo/{id_festivos}")
    public ResponseEntity<OutputEntity<String>> updateDiaFestivo(@RequestBody Cat_Dias_Festivos diaFestivo, @PathVariable("id_festivos") Integer id_festivos) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateDiaFestivo(diaFestivo, id_festivos);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de dia festivo modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @SystemControllerLog(operation = "cambioEstatusDiaFestivo", type = "Cambiar el estatus del banco") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusDiaFestivo/{id_festivos}/{estatus}")
    public ResponseEntity<Cat_Dias_Festivos> cambioEstatusFestivo(@RequestBody @PathVariable Integer id_festivos, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusFestivo(id_festivos, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarDiasNomina/{nomina_id}")
    public ResponseEntity<Cat_Dias_Festivos> listarDiasNomina(@PathVariable("nomina_id") Integer nomina_id) {
        try {
            List<Cat_Dias_Festivos> result = catalogosService.findDiasNomina(nomina_id);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/encontrarDiasFestivos/{nomina_id}/{fecha}")
    public ResponseEntity<Cat_Dias_Festivos> encontrarDiasFestivos(@PathVariable("nomina_id") Integer nomina_id, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        try {
            List<Cat_Dias_Festivos> result = catalogosService.findDiasFestivos(nomina_id, fecha);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//******************* CATALOGO HORARIO ************************************
    //Buscar genero por ID 
    @GetMapping(value = "/buscarHorario/{id}")
    public ResponseEntity<OutputEntity<Cat_Horario>> findOneHorario(@PathVariable Integer id) {
        OutputEntity<Cat_Horario> out = new OutputEntity<>();
        try {
            Cat_Horario result = catalogosService.findOneHorario(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarHorario")
    public ResponseEntity<Cat_Horario> findAllHorarios() {
        try {
            List<Cat_Horario> result = catalogosService.findAllHorarios();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarHorario")
    public ResponseEntity<Cat_Horario> saveHorario(@RequestBody Cat_Horario horario) {
        try {
            return new ResponseEntity<>(catalogosService.saveHorario(horario), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualizar los datos del horario
    @PostMapping(value = "/editarHorario/{id_horario}")
    public ResponseEntity<OutputEntity<String>> editarHorarios(@RequestBody Cat_Horario horario, @PathVariable("id_horario") Integer id_horario) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.editarHorarios(horario, id_horario);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de horario modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @SystemControllerLog(operation = "cambioEstatusHorario", type = "Cambiar el estatus del horario") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusHorario/{id_horario}/{estatus}")
    public ResponseEntity<Cat_Horario> cambioEstatusHorario(@RequestBody @PathVariable Integer id_horario, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusHorario(id_horario, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* HISTÓRICO LIBRO DÍAS ************************************
    @PostMapping("/GuardarHistoricoLibroDias")
    public ResponseEntity<Historico_Libro_Dias> GuardarHistoricoLibroDias(@RequestBody Historico_Libro_Dias historico_libro_dias) {
        try {
            return new ResponseEntity<>(catalogosService.saveHistoricoLibroDias(historico_libro_dias), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("error " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//****************** CATALOGO PLAZA MOVIMIENTOS ***************************
    @GetMapping("/listarPlazaMovimientos")
    public ResponseEntity<Cat_Plaza_MovimientosDTO> findAllPlazaMovimientos() {
        try {
            List<Cat_Plaza_MovimientosDTO> result = catalogosService.findAllPlazaMovimientos();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping("/listarDatosPlazaMovimientos")
    public ResponseEntity<Cat_Plaza_Movimientos> findAllDatosPlazaMovimientos() {
        try {
            List<Cat_Plaza_Movimientos> result = catalogosService.findAllDatosPlazaMovimientos();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/guardarPlazaMovimientos")
    public ResponseEntity<Cat_Plaza_Movimientos> savePlazaMovimientos(@RequestBody Cat_Plaza_Movimientos cat_Plaza_Movimientos) {
        try {
            return new ResponseEntity<>(catalogosService.savePlazaMovimientos(cat_Plaza_Movimientos), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //Actualizar datos de Catalogo Plaza Movimientos
    @PostMapping("/actualizarPlazaMovimientos/{id_plaza_movimientos}")
    public ResponseEntity<OutputEntity<String>> updatePlazaMovimientos(@RequestBody Cat_Plaza_MovimientosDTO cat_Plaza_Movimientos, @PathVariable Integer id_plaza_movimientos) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updatePlazaMovimientos(id_plaza_movimientos, cat_Plaza_Movimientos);
            out.success((Response.UPDATE.getCode()), Response.UPDATE.getKey(), "Movimiento Actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Estatus Plaza Movimientos
    @GetMapping("/estatusPlazaMovimientos/{id_plaza_movimientos}/{estatus}")
    public ResponseEntity<Cat_Plaza_Movimientos> estatusPlazaMovimientos(@PathVariable Integer id_plaza_movimientos, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.estatusPlazaMovimientos(id_plaza_movimientos, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscarPlazaMovimientos/{id_plaza_movimientos}")
    public ResponseEntity<Cat_Plaza_Movimientos> findOnePlazaMovimientos(@PathVariable Integer id_plaza_movimientos) {
        try {
            return new ResponseEntity<>(catalogosService.findOnePlazaMovimientos(id_plaza_movimientos), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // **************** CATÁLOGO TIPO BENEFICIARIO *************************    

    @GetMapping("/listarTipoBeneficiario")
    public ResponseEntity<Cat_Tipo_BeneficiarioDTO> findAllTipoBeneficiario() {
        try {
            List<Cat_Tipo_BeneficiarioDTO> result = catalogosService.findAllTipoBeneficiario();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping("/listarDatosTipoBeneficiario")
    public ResponseEntity<Cat_Tipo_Beneficiario> findAllDatosTipoBeneficiario() {
        try {
            List<Cat_Tipo_Beneficiario> result = catalogosService.findAllDatosTipoBeneficiario();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/guardarTipoBeneficiario")
    public ResponseEntity<Cat_Tipo_Beneficiario> saveTipoBeneficiario(@RequestBody Cat_Tipo_Beneficiario cat_Tipo_Beneficiario) {
        try {
            return new ResponseEntity<>(catalogosService.saveTipoBeneficiario(cat_Tipo_Beneficiario), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Actualizar datos de Catálogo de Tipo Beneficiario
    @PostMapping("/actualizarTipoBeneficiario/{id_tipo_beneficiario}")
    public ResponseEntity<OutputEntity<String>> updateTipoBeneficiario(@RequestBody Cat_Tipo_BeneficiarioDTO cat_Tipo_Beneficiario, @PathVariable Integer id_tipo_beneficiario) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateTipoBeneficiario(id_tipo_beneficiario, cat_Tipo_Beneficiario);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Tipo Beneficiario Actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Buscar Tipo Beneficiario por ID
    @GetMapping("/buscarTipoBeneficiario/{id_tipo_beneficiario}")
    public ResponseEntity<Cat_Tipo_Beneficiario> findOneTipoBeneficiario(@PathVariable Integer id_tipo_beneficiario) {
        try {
            Cat_Tipo_Beneficiario result = catalogosService.findOneTipoBeneficiario(id_tipo_beneficiario);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Estatus de Tipo Beneficiario
    @GetMapping("/estatusTipoBeneficiario/{id_tipo_beneficiario}/{estatus}")
    public ResponseEntity<Cat_Tipo_Beneficiario> estatusTipoBeneficiario(@RequestBody @PathVariable Integer id_tipo_beneficiario, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.estatusTipoBeneficiario(id_tipo_beneficiario, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

// **************** CATALOGO TIPO DOCUMENTOS *****************************
    @GetMapping(value = "/buscarDocumento/{id}")
    public ResponseEntity<OutputEntity<Cat_Tipo_Documento>> findOneDocumento(@PathVariable Integer id) {
        OutputEntity<Cat_Tipo_Documento> out = new OutputEntity<>();
        try {
            Cat_Tipo_Documento result = catalogosService.findOneDocumento(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarDocumeto")
    public ResponseEntity<Cat_Tipo_Documento> saveDocumento(@RequestBody Cat_Tipo_Documento documento) {
        try {
            return new ResponseEntity<>(catalogosService.saveDocumento(documento), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarDocumentos")
    public ResponseEntity<Cat_Tipo_Documento> findAllDatosDocumentos() {
        try {
            List<Cat_Tipo_Documento> result = catalogosService.findAllDatosDocumentos();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarDocumento/{id_tipo_documento}")
    public ResponseEntity<OutputEntity<String>> updateDocumentos(@RequestBody Cat_Tipo_Documento documento, @PathVariable("id_tipo_documento") Integer id_tipo_documento) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateDocumentos(documento, id_tipo_documento);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de documento modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @SystemControllerLog(operation = "cambioEstatusDocumentos", type = "Cambiar el estatus del documento") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusDocumentos/{id_tipo_documento}/{estatus}")
    public ResponseEntity<Cat_Tipo_Documento> cambioEstatusDocumentos(@RequestBody @PathVariable Integer id_tipo_documento, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusDocumentos(id_tipo_documento, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //******************* CATALOGO TABULADOR ************************************   
    //Buscar Tabulador por ID 

    @GetMapping(value = "/buscarTabulador/{id}")
    public ResponseEntity<OutputEntity<Cat_Tabulador>> findOneTabulador(@PathVariable Integer id) {
        OutputEntity<Cat_Tabulador> out = new OutputEntity<>();
        try {
            Cat_Tabulador result = catalogosService.findOneTabulador(id);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/GuardarTabulador")
    public ResponseEntity<Cat_Tabulador> saveTabulador(@RequestBody Cat_Tabulador tabulador) {
        try {
            return new ResponseEntity<>(catalogosService.saveTabulador(tabulador), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarTabuladores")
    public ResponseEntity<Cat_Tabulador> findAllDatosTabulador() {
        try {
            List<Cat_Tabulador> result = catalogosService.findAllDatosTabulador();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarTabulador/{id_tipo_tabulador}")
    public ResponseEntity<OutputEntity<String>> updateTabulador(@RequestBody Cat_Tabulador tabulador, @PathVariable("id_tipo_tabulador") Integer id_tipo_tabulador) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateTabulador(tabulador, id_tipo_tabulador);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de tabulador modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @SystemControllerLog(operation = "cambioEstatusTabulador", type = "Cambiar el estatus del banco") //Log de quien ejecuta el metodo
    @PostMapping(value = "/cambioEstatusTabulador/{id_tipo_tabulador}/{estatus}")
    public ResponseEntity<Cat_Tabulador> cambioEstatusTabulador(@RequestBody @PathVariable Integer id_tipo_tabulador, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusTabulador(id_tipo_tabulador, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //******************* CATÁLOGO TIPO DE MOVIMIENTO PARA IDSE SUA ***************************  

    @GetMapping(value = "/listarMotivosImss")
    public ResponseEntity<Cat_Tipo_Mov_Idse_Sua> findAllTipoMovImss() {
        try {
            List<Cat_Tipo_Mov_Idse_Sua> result = catalogosService.findAllTipoMovImss();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //******************* CATÁLOGO MUNICIPIO ***************************  

    @GetMapping("/litarMunicipio")
    public ResponseEntity<Cat_MunicipioDTO> findAllMunicipio() {
        try {
            List<Cat_MunicipioDTO> result = catalogosService.findAllMunicipio();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarDatosMunicipio")
    public ResponseEntity<Cat_Municipio> findAllDatosMunicipio() {
        try {
            List<Cat_Municipio> result = catalogosService.findAllDatosMunicipio();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscarMunicipioEstado/{id_estado}")
    public ResponseEntity<OutputEntity<List<Cat_Municipio>>> findEstadoMunicipio(@PathVariable("id_estado") Integer id_estado) {
        //OutputEntity<List<Cat_Municipio>> out = new OutputEntity<>();
        try {
            List<Cat_Municipio> result = catalogosService.findEstadoMunicipio(id_estado);
            //out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity(result, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/guardarMunicipio")
    public ResponseEntity<Cat_Municipio> saveMunicipio(@RequestBody Cat_Municipio cat_Municipio) {
        try {
            return new ResponseEntity<>(catalogosService.saveMunicipio(cat_Municipio), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    //Actualizar datos de Catálogo Municipio
    @PostMapping("/actualizarMunicipio/{id_municipio}")
    public ResponseEntity<OutputEntity<String>> updateMunicipio(@RequestBody Cat_MunicipioDTO cat_Municipio, @PathVariable Integer id_municipio) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateMunicipio(cat_Municipio, id_municipio);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Municipio Actualizado");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    //Buscar Municipio por ID
    @GetMapping("/buscarMunicipio/{id_municipio}")
    public ResponseEntity<Cat_Municipio> findOneMunicipio(@PathVariable Integer id_municipio) {
        try {
            Cat_Municipio result = catalogosService.findOneMunicipio(id_municipio);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Estatu de Municipio
    @GetMapping("/estatusMunicipio/{id_municipio}/{estatus}")
    public ResponseEntity<Cat_Municipio> estatusMunicipio(@RequestBody @PathVariable Integer id_municipio, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.estatusMunicipio(id_municipio, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
// **************** CATALOGO TIPO CONTRATO *****************************

    @GetMapping(value = "/listarTipoContrato")
    public ResponseEntity<Cat_Tipo_Contrato> findAllDatosTipoContrato() {
        try {
            List<Cat_Tipo_Contrato> result = catalogosService.findAllDatosTipoContrato();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarTipoContrato/{id_tipo_contrato}")
    public ResponseEntity<OutputEntity<Cat_Tipo_Contrato>> findOneTipoContrato(@PathVariable Integer id_tipo_contrato) {
        OutputEntity<Cat_Tipo_Contrato> out = new OutputEntity<>();
        try {
            Cat_Tipo_Contrato result = catalogosService.findOneTipoContrato(id_tipo_contrato);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarTipoContrato/{id_tipo_contrato}")
    public ResponseEntity<OutputEntity<String>> actualizarTipoContrato(@PathVariable("id_tipo_contrato") Integer id_tipo_contrato, @RequestBody Cat_Tipo_Contrato tipo_Contrato) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.actualizarTipoContrato(id_tipo_contrato, tipo_Contrato);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de Tipo Contrato modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {

            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping(value = "/guardarTipoContrato")
    public ResponseEntity<OutputEntity<Cat_Tipo_Contrato>> saveTipoContrato(@RequestBody Cat_Tipo_Contrato cat_Tipo_Contrato) {
        OutputEntity<Cat_Tipo_Contrato> out = new OutputEntity<>();
        try {
            catalogosService.saveTipoContrato(cat_Tipo_Contrato);
            out.success(Response.CREATED.getCode(), Response.CREATED.getKey(), null);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/cambioEstatusTipoContrato/{id_tipo_contrato}/{estatus}")
    public ResponseEntity<Cat_Tipo_Contrato> cambioEstatusTipoContrato(@RequestBody @PathVariable Integer id_tipo_contrato, @PathVariable Integer estatus) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusTipoContrato(id_tipo_contrato, estatus), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO TIPO AYUDA  ************************************
    @GetMapping(value = "/listarCatTipoAyuda")
    public ResponseEntity<Cat_Tipo_AyudaDTO> listarCatTipoAyuda() {
        try {
            List<Cat_Tipo_AyudaDTO> result = catalogosService.findAllTipoAyuda();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Listar todos los datos
    @GetMapping(value = "/listarDatosTipoAyuda")
    public ResponseEntity<Cat_Tipo_Ayuda> listarDatosTipoAyuda() {
        try {
            List<Cat_Tipo_AyudaDTO> result = catalogosService.findAllTipoAyuda();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarTipoAyuda/{id_tipo_ayuda}")
    public ResponseEntity<OutputEntity<List<Cat_Tipo_Ayuda>>> buscarAyudaID(@PathVariable("id_tipo_ayuda") Integer id_tipo_ayuda) {
        OutputEntity<List<Cat_Tipo_Ayuda>> out = new OutputEntity<>();
        try {
            List<Cat_Tipo_Ayuda> result = catalogosService.findAyudaID(id_tipo_ayuda);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            //System.out.println("e " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //******************* CATALOGO UBICACION  ************************************

    @GetMapping(value = "/listarUbicacion")
    public ResponseEntity<Cat_Ubicacion> listarUbicacion() {
        try {
            List<Cat_Ubicacion> result = catalogosService.findAllUbicacion();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/guardarUbicacion")
    public ResponseEntity<Cat_Ubicacion> saveUbicacion(@RequestBody Cat_Ubicacion cat_Ubicacion) {
        try {
            return new ResponseEntity<>(catalogosService.saveUbicacion(cat_Ubicacion), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/editarUbicacion/{id_ubicacion}")
    public ResponseEntity<OutputEntity<String>> updateUbicacion(@RequestBody Cat_Ubicacion cat_Ubicacion, @PathVariable("id_ubicacion") Integer id_ubicacion) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateUbicacion(cat_Ubicacion, id_ubicacion);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de tabulador modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @GetMapping(value = "/buscarUbicacion/{id_ubicacion}")
    public ResponseEntity<OutputEntity<Cat_Ubicacion>> findOneUbicacion(@PathVariable Integer id_ubicacion) {
        OutputEntity<Cat_Ubicacion> out = new OutputEntity<>();
        try {
            Cat_Ubicacion result = catalogosService.findOneUbicacion(id_ubicacion);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/cambioEstatusUbicacion/{id_ubicacion}/{activo}")
    public ResponseEntity<Cat_Ubicacion> cambioEstatusUbicacion(@RequestBody @PathVariable Integer id_ubicacion, @PathVariable Integer activo) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusUbicacion(id_ubicacion, activo), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO REPORTE MONITOR  ************************************
    @GetMapping(value = "/listarDatosOrdenReporteMonitor")
    public ResponseEntity<Cat_Reporte_Monitor> listarDatosOrdenReporteMonitor() {
        try {
            List<Cat_Reporte_Monitor> result = catalogosService.findAllOrdenReporteMonitor();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //******************* CATALOGO GRADO  ************************************
    @GetMapping(value = "/listarGrado")
    public ResponseEntity<Cat_Grado> listarGrado() {
        try {
            List<Cat_Grado> result = catalogosService.findAllDatosGrado();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/guardarGrado")
    public ResponseEntity<Cat_Grado> saveGrado(@RequestBody Cat_Grado cat_grado) {
        try {
            return new ResponseEntity<>(catalogosService.saveGrado(cat_grado), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/buscarGrado/{id_grado}")
    public ResponseEntity<OutputEntity<Cat_Grado>> findOneGrado(@PathVariable Integer id_grado) {
        OutputEntity<Cat_Grado> out = new OutputEntity<>();
        try {
            Cat_Grado result = catalogosService.findOneGrado(id_grado);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/editarGrado/{id_grado}")
    public ResponseEntity<OutputEntity<String>> updateGrado(@RequestBody Cat_Grado cat_grado, @PathVariable("id_grado") Integer id_grado) {
        OutputEntity<String> out = new OutputEntity<>();
        try {
            catalogosService.updateGrado(cat_grado, id_grado);
            out.success(Response.UPDATE.getCode(), Response.UPDATE.getKey(), "Datos de tabulador modificados con éxito");
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            out.error();
            return new ResponseEntity<>(out, out.getCode());
        }
    }

    @PostMapping(value = "/cambioEstatusGrado/{id_grado}/{activo}")
    public ResponseEntity<Cat_Grado> cambioEstatusGrado(@RequestBody @PathVariable Integer id_grado, @PathVariable Integer activo) {
        try {
            return new ResponseEntity<>(catalogosService.cambioEstatusGrado(id_grado, activo), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // **************** CATÁLOGO TIPO NOMINAS *************************   
    @GetMapping("/listarTipoNominas")
    public ResponseEntity<Cat_Tipo_Nomina> findAllTipoNomina() {
        try {
            List<Cat_Tipo_Nomina> result = catalogosService.findAllTipoNomina();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/buscarNomina/{id_tipo_nomina}")
    public ResponseEntity<OutputEntity<Cat_Tipo_Nomina>> buscarNomina(@PathVariable Integer id_tipo_nomina) {
        OutputEntity<Cat_Tipo_Nomina> out = new OutputEntity<>();
        try {
            Cat_Tipo_Nomina result = catalogosService.findByIdNomina(id_tipo_nomina);
            out.success(Response.OK.getCode(), Response.OK.getKey(), result);
            return new ResponseEntity<>(out, out.getCode());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarDocumentosac")
    public ResponseEntity<Cat_Tipo_Documento> listarDocumentosac() {
        try {
            List<Cat_Tipo_Documento> result = catalogosService.findDocumentos();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //*************************************************************************
    //                          CATALOGO IMPUESTO CONCEPTO
    //*************************************************************************
    @GetMapping(value = "/listarImpuestoConcepto")
    public ResponseEntity<Cat_Impuesto_Concepto> listarImpuestoConcepto() {
        try {
            List<Cat_Impuesto_Concepto> result = catalogosService.findAllCatImpuestoConcepto();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //*************************************************************************
    //                          CATALOGO PERIODO IMPUESTO
    //*************************************************************************
    @GetMapping(value = "/listarPeriodoImpuesto")
    public ResponseEntity<Cat_Periodo_Impuesto> listarPeriodoImpuesto() {
        try {
            List<Cat_Periodo_Impuesto> result = catalogosService.findAllCatPeriodoImpuesto();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("eee " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/listarMotivosRa10")
    public ResponseEntity<Cat_Motivos_RA10> listarMotivosRa10() {
        try {
            List<Cat_Motivos_RA10> result = catalogosService.findAllMotivosRa10();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
