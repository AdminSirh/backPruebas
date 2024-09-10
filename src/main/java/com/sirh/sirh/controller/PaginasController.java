/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sirh.sirh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author jarellano22
 */
@Controller

public class PaginasController {

    //Pagina principal 
    @GetMapping(value = "/sirh")
    public String sirh() {
        return "sirh";
    }

    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    //PAGINAS Menu
    @GetMapping(value = "/catalogosAdmin")
    public String catalogos() {
        return "catalogosAdmin";
    }

    @GetMapping(value = "/usuariosAdmin")
    public String usuarios() {
        return "usuariosAdmin";
    }

    @GetMapping(value = "/menuAdmin")
    public String menuAdmin() {
        return "menuAdmin";
    }

    @GetMapping(value = "/candidatos")
    public String candidatos() {
        return "candidatos";
    }

    @GetMapping(value = "/personaDatosGenerales")
    public String personaDatosGenerales() {
        return "personaDatosGenerales";
    }

    @GetMapping(value = "/personaDatosGeneralesVer")
    public String personaDatosGeneralesVer() {
        return "personaDatosGeneralesVer";
    }

    @GetMapping(value = "/personaContacto")
    public String personaContacto() {
        return "personaContacto";
    }

    @GetMapping(value = "/personaContactoVer")
    public String personaContactoVer() {
        return "personaContactoVer";
    }

    @GetMapping(value = "/personaDireccion")
    public String personaDireccion() {
        return "personaDireccion";
    }

    @GetMapping(value = "/personaDireccionVer")
    public String personaDireccionVer() {
        return "personaDireccionVer";
    }

    @GetMapping(value = "/personaLicencia")
    public String personaLicencia() {
        return "personaLicencia";
    }

    @GetMapping(value = "/personaLicenciaVer")
    public String personaLicenciaVer() {
        return "personaLicenciaVer";
    }

    @GetMapping(value = "/personaDocumentos")
    public String personaDocumentos() {
        return "personaDocumentos";
    }

    @GetMapping(value = "/personaDocumentosVer")
    public String personaDocumentosVer() {
        return "personaDocumentosVer";
    }

    @GetMapping(value = "/personaBeneficiarios")
    public String personaBeneficiarios() {
        return "personaBeneficiarios";
    }

    @GetMapping(value = "/personaCarta")
    public String personaCarta() {
        return "personaCarta";
    }

    @GetMapping(value = "/personaCartaVer")
    public String personaCartaVer() {
        return "personaCartaVer";
    }

    @GetMapping(value = "/expedientesAdmin")
    public String expedientesAdmin() {
        return "expedientesAdmin";
    }

    @GetMapping(value = "/trabajadorDatosGenerales")
    public String datosTrabajador() {
        return "trabajadorDatosGenerales";
    }

    @GetMapping(value = "/trabajadorBancarios")
    public String trabajadorBancarios() {
        return "trabajadorBancarios";
    }

    @GetMapping(value = "/trabajadorJubilacion")
    public String trabajadorJubilacion() {
        return "trabajadorJubilacion";
    }

    @GetMapping(value = "/trabajadorPuesto")
    public String trabajadorPuesto() {
        return "trabajadorPuesto";
    }

    @GetMapping(value = "/subMenuAdmin")
    public String subMenu() {
        return "subMenuAdmin";
    }

    @GetMapping(value = "/trabajadoresAdmin")
    public String trabajadoresAdmin() {
        return "trabajadoresAdmin";
    }

    @GetMapping(value = "/personaMedico")
    public String personaMedico() {
        return "personaMedico";
    }

    @GetMapping(value = "/personaMedicoVer")
    public String personaMedicoVer() {
        return "personaMedicoVer";
    }

    @GetMapping(value = "/prenominaOperacion")
    public String prenominaOperacion() {
        return "prenominaOperacion";
    }

    @GetMapping(value = "/pagosOperacion")
    public String pagosOperacion() {
        return "pagosOperacion";
    }

    @GetMapping(value = "/horariosOperacion")
    public String horariosOperacion() {
        return "horariosOperacion";
    }

    @GetMapping(value = "/plazasOperacion")
    public String plazasOperacion() {
        return "plazasOperacion";
    }

    @GetMapping(value = "/ciclosOperacion")
    public String ciclosOperacion() {
        return "ciclosOperacion";
    }

    @GetMapping(value = "/periodosOperacion")
    public String periodosOperacion() {
        return "periodosOperacion";
    }

    @GetMapping(value = "/cuentasOperacion")
    public String cuentasOperacion() {
        return "cuentasOperacion";
    }

    @GetMapping(value = "/imssOperacion")
    public String imssOperacion() {
        return "imssOperacion";
    }

    @GetMapping(value = "/festivosOperacion")
    public String festivosOperacion() {
        return "festivosOperacion";
    }

    @GetMapping(value = "/deduccionesOperacion")
    public String deduccionesOperacion() {
        return "deduccionesOperacion";
    }

    @GetMapping(value = "/isrOperacion")
    public String isrOperacion() {
        return "isrOperacion";
    }

    @GetMapping(value = "/reportesfinanzasOperacion")
    public String reportesfinanzasOperacion() {
        return "reportesfinanzasOperacion";
    }

    @GetMapping(value = "/reportesOperacion")
    public String reportesOperacion() {
        return "reportesOperacion";
    }

    @GetMapping(value = "/reportesGenerales")
    public String reportesGenerales() {
        return "reportesGenerales";
    }

    @GetMapping(value = "/manualesOperacion")
    public String manualesOperacion() {
        return "manualesOperacion";
    }

    @GetMapping(value = "/guardaIncidencias")
    public String guardaIncidencias() {
        return "guardaIncidencias";
    }

    @GetMapping(value = "/calculoPreNomina")
    public String calculoPreNomina() {
        return "calculoPreNomina";
    }

    @GetMapping(value = "/ayudaAlumbramientoFallecimiento")
    public String ayudaAlumbramientoFallecimiento() {
        return "ayudaAlumbramientoFallecimiento";
    }

    @GetMapping(value = "/parametrosNomina")
    public String parametrosNomina() {
        return "parametrosNomina";
    }

    @GetMapping(value = "/incidenciasAutomaticasTrabajador")
    public String incidenciasAutomaticasTrabajador() {
        return "incidenciasAutomaticasTrabajador";
    }

    @GetMapping(value = "/calendarioAnual")
    public String calendarioAnual() {
        return "calendarioAnual";
    }

    @GetMapping(value = "/registroInasistencias")
    public String registroInasistencias() {
        return "registroInasistencias";
    }

    @GetMapping(value = "/guardaInasistencias")
    public String guardaInasistencias() {
        return "guardaInasistencias";
    }

    @GetMapping(value = "/creditoInfonavit")
    public String creditoInfonavit() {
        return "creditoInfonavit";
    }

    @GetMapping(value = "/capturaCreditoInfonavit")
    public String capturaCreditoInfonavit() {
        return "capturaCreditoInfonavit";
    }

    @GetMapping(value = "/pensionAlimenticia")
    public String pensionAlimenticia() {
        return "pensionAlimenticia";
    }

    @GetMapping(value = "/asignacionPension")
    public String asignacionPension() {
        return "asignacionPension";
    }

    @GetMapping(value = "/consultaPagos")
    public String consultaPagos() {
        return "consultaPagos";
    }

    @GetMapping(value = "/capturaPagos")
    public String capturaPagos() {
        return "capturaPagos";
    }

    @GetMapping(value = "/descuentosProgramados")
    public String descuentosProgramados() {
        return "descuentosProgramados";
    }

    @GetMapping(value = "/capturaDescuentosProgramados")
    public String capturaDescuentosProgramados() {
        return "capturaDescuentosProgramados";
    }

    @GetMapping(value = "/masCalculos")
    public String masCalculos() {
        return "masCalculos";
    }

    @GetMapping(value = "/tiempoExtra")
    public String tiempoExtra() {
        return "tiempoExtra";
    }

    @GetMapping(value = "/adminPuestos")
    public String adminPuestos() {
        return "adminPuestos";
    }

    @GetMapping(value = "/movimientosUsuario")
    public String movimientosUsuario() {
        return "movimientosUsuario";
    }

    @GetMapping(value = "/puestoDatosGenerales")
    public String puestoDatosGenerales() {
        return "puestoDatosGenerales";
    }

    @GetMapping(value = "/administrarPlazas")
    public String administrarPlazas() {
        return "administrarPlazas";
    }

    @GetMapping(value = "/trabajadorValidacion")
    public String trabajadorValidacion() {
        return "trabajadorValidacion";
    }

    @GetMapping(value = "/catalogoAreas")
    public String catalogoAreas() {
        return "catalogoAreas";
    }

    @GetMapping(value = "/trabajadorHorario")
    public String trabajadorHorario() {
        return "trabajadorHorario";
    }

    @GetMapping(value = "/incidenciasAdmin")
    public String incidenciasAdmin() {
        return "incidenciasAdmin";
    }

    @GetMapping(value = "/generaVacaciones")
    public String generaVacaciones() {
        return "generaVacaciones";
    }

    @GetMapping(value = "/trabajadorIncidencias")
    public String trabajadorIncidencias() {
        return "trabajadorIncidencias";
    }

    //PANTALLAS CATÁLOGOS
    @GetMapping(value = "/catalogoBancos")
    public String catalogoBancos() {
        return "catalogoBancos";
    }

    @GetMapping(value = "/catalogoCargo")
    public String catalogoCargo() {
        return "catalogoCargo";
    }

    @GetMapping(value = "/catalogoDepositos")
    public String catalogoDepositos() {
        return "catalogoDepositos";
    }

    @GetMapping(value = "/catalogoEdoCivil")
    public String catalogoEdoCivil() {
        return "catalogoEdoCivil";
    }

    @GetMapping(value = "/catalogoEstructura")
    public String catalogoEstructura() {
        return "catalogoEstructura";
    }

    @GetMapping(value = "/catalogoParentesco")
    public String catalogoParentesco() {
        return "catalogoParentesco";
    }

    @GetMapping(value = "/catalogoGenero")
    public String catalogoGenero() {
        return "catalogoGenero";
    }

    @GetMapping(value = "/catalogoEstudios")
    public String catalogoEstudios() {
        return "catalogoEstudios";
    }

    @GetMapping(value = "/catalogoDias")
    public String catalogoDias() {
        return "catalogoDias";
    }

    @GetMapping(value = "/catalogoEstado")
    public String catalogoEstado() {
        return "catalogoEstado";
    }

    @GetMapping(value = "/catalogoEdoVacaciones")
    public String catalogoEdoVacaciones() {
        return "catalogoEdoVacaciones";
    }

    @GetMapping(value = "/catalogoCreditoInfonavit")
    public String catalogoCreditoInfonavit() {
        return "catalogoCreditoInfonavit";
    }

    @GetMapping(value = "/catalogoHorario")
    public String catalogoHorario() {
        return "catalogoHorario";
    }

    @GetMapping(value = "/catalogoTipoTabulador")
    public String catalogoTipoTabulador() {
        return "catalogoTipoTabulador";
    }

    @GetMapping(value = "/catalogoTipoDocumento")
    public String catalogoTipoDocumento() {
        return "catalogoTipoDocumento";
    }

    @GetMapping(value = "/catalogoLicencia")
    public String catalogoLicencia() {
        return "catalogoLicencia";
    }

    @GetMapping(value = "/catalogoTipoSangre")
    public String catalogoTipoSangre() {
        return "catalogoTipoSangre";
    }

    @GetMapping(value = "/catalogoInhabilitado")
    public String catalogoInhabilitado() {
        return "catalogoInhabilitado";
    }

    @GetMapping(value = "/catalogoIncidencias")
    public String catalogoIncidencias() {
        return "catalogoIncidencias";
    }

    @GetMapping(value = "/catalogoTipoIncidencia")
    public String catalogoTipoIncidencia() {
        return "catalogoTipoIncidencia";
    }

    @GetMapping(value = "/catalogoNacionalidad")
    public String catalogoNacionalidad() {
        return "catalogoNacionalidad";
    }

    @GetMapping(value = "/catalogo_si_no")
    public String catalogo_si_no() {
        return "catalogo_si_no";
    }

    @GetMapping(value = "/catalogoDia_Festivos")
    public String catalogoDiasFestivos() {
        return "catalogoDia_Festivos";
    }

    @GetMapping(value = "/catalogoTipoContrato")
    public String catalogoTipoContrato() {
        return "catalogoTipoContrato";
    }

    @GetMapping(value = "/catalogoPlazaMovimientos")
    public String catalogoPlazaMovimientos() {
        return "catalogoPlazaMovimientos";
    }

    @GetMapping(value = "/catalogoTipoBeneficiario")
    public String catalogoTipoBeneficiario() {
        return "catalogoTipoBeneficiario";
    }

    @GetMapping(value = "/catalogoMunicipio")
    public String catalogoMunicipio() {
        return "catalogoMunicipio";
    }

    @GetMapping(value = "/catalogoColonia")
    public String catalogoColonia() {
        return "catalogoColonia";
    }

    @GetMapping(value = "/reportesPersonal")
    public String reportesPersonal() {
        return "reportesPersonal";
    }

    @GetMapping(value = "/catalogoUbicacion")
    public String catalogoUbicacion() {
        return "catalogoUbicacion";
    }

    @GetMapping(value = "/plazaAutorizada")
    public String plazaAutorizada() {
        return "plazaAutorizada";
    }

    @GetMapping(value = "/catalogoGrado")
    public String catalogoGrado() {
        return "catalogoGrado";
    }

    @GetMapping(value = "/cierrePlantilla")
    public String cierrePlantilla() {
        return "cierrePlantilla";
    }

    @GetMapping(value = "/reporteAvisoMovimientoPersonal")
    public String reporteAvisoMovimientoPersonal() {
        return "reporteAvisoMovimientoPersonal";
    }

    @GetMapping(value = "/constanciasPersonal")
    public String constanciasPersonal() {
        return "constanciasPersonal";
    }

    @GetMapping(value = "/reporteFemeninoArea")
    public String reporteFemeninoArea() {
        return "reporteFemeninoArea";
    }

    @GetMapping(value = "/personalJubilado")
    public String personalJubilado() {
        return "personalJubilado";
    }

    @GetMapping(value = "/movimientosPersonal")
    public String movimientosPersonal() {
        return "movimientosPersonal";
    }

    @GetMapping(value = "/todos")
    public String todos() {
        return "todos";
    }

    @GetMapping(value = "/descuentoFONACOT")
    public String descuentoFONACOT() {
        return "descuentoFONACOT";
    }

    @GetMapping(value = "/fonacotAdmin")
    public String fonacotAdmin() {
        return "fonacotAdmin";
    }

    @GetMapping(value = "/importacionDescuentos")
    public String importacionDescuentos() {
        return "importacionDescuentos";
    }

    @GetMapping(value = "/devolucionDescuento")
    public String devolucionDescuento() {
        return "devolucionDescuento";
    }

    @GetMapping(value = "/reportesPrestamos")
    public String reportesPrestamos() {
        return "reportesPrestamos";
    }

    @GetMapping(value = "/pAlimenticiaTabla")
    public String rpAlimenticiaTabla() {
        return "pAlimenticiaTabla";
    }

    @GetMapping(value = "/pAlimenticiaTabInactivas")
    public String pAlimenticiaTabInactivas() {
        return "pAlimenticiaTabInactivas";
    }

    @GetMapping(value = "/pAlimenticiaHistoricoEdiciones")
    public String pAlimenticiaHistoricoEdiciones() {
        return "pAlimenticiaHistoricoEdiciones";
    }

    @GetMapping(value = "/pensionAlimenticiaBeneficiarios")
    public String pensionAlimenticiaBeneficiarios() {
        return "pensionAlimenticiaBeneficiarios";
    }

    @GetMapping(value = "/controlAsistencia")
    public String controlAsistencia() {
        return "controlAsistencia";
    }

    @GetMapping(value = "/firmasAdmin")
    public String firmasAdmin() {
        return "firmasAdmin";
    }

    @GetMapping(value = "/generaCredencial")
    public String generaCredencial() {
        return "generaCredencial";
    }

    @GetMapping(value = "/prestamosPersonalesAdmin")
    public String prestamosPersonalesAdmin() {
        return "prestamosPersonalesAdmin";
    }

    @GetMapping(value = "/descuentoPersonal")
    public String descuentoPersonal() {
        return "descuentoPersonal";
    }

    @GetMapping(value = "/trabajadorIncapacidades")
    public String trabajadorIncapacidades() {
        return "trabajadorIncapacidades";
    }

    @GetMapping(value = "/creditoPersonalCuentaActiva")
    public String creditoPersonalCuentaActiva() {
        return "creditoPersonalCuentaActiva";
    }

    @GetMapping(value = "/creditoPersonalPagos")
    public String creditoPersonalPagos() {
        return "creditoPersonalPagos";
    }

    @GetMapping(value = "/fonacotCuentaActiva")
    public String fonacotCuentaActiva() {
        return "fonacotCuentaActiva";
    }

    @GetMapping(value = "/creditoFonacotPagos")
    public String creditoFonacotPagos() {
        return "creditoFonacotPagos";
    }

    @GetMapping(value = "/fondoAuxilioAdmin")
    public String fondoAuxilioAdmin() {
        return "fondoAuxilioAdmin";
    }

    @GetMapping(value = "/descuentoFondoAuxilio")
    public String descuentoFondoAuxilio() {
        return "descuentoFondoAuxilio";
    }

    @GetMapping(value = "/fondoAuxilioCuentaActiva")
    public String fondoAuxilioCuentaActiva() {
        return "fondoAuxilioCuentaActiva";
    }

    @GetMapping(value = "/fondoAuxilioPagos")
    public String fondoAuxilioPagos() {
        return "fondoAuxilioPagos";
    }

    @GetMapping(value = "/interfazArchivosSUA")
    public String interfazArchivosSUA() {
        return "interfazArchivosSUA";
    }

    @GetMapping(value = "/importacionIncidencias")
    public String importacionIncidencias() {
        return "importacionIncidencias";
    }

    @GetMapping(value = "/importacionIncidenciasKardex")
    public String importacionIncidenciasKardex() {
        return "importacionIncidenciasKardex";
    }

    @GetMapping(value = "/reporteAyudas")
    public String reporteAyudas() {
        return "reporteAyudas";
    }

    @GetMapping(value = "/cuponesCargoAdmin")
    public String cuponesCargoAdmin() {
        return "cuponesCargoAdmin";
    }

    @GetMapping(value = "/descuentoCuponesCargo")
    public String descuentoCuponesCargo() {
        return "descuentoCuponesCargo";
    }

    @GetMapping(value = "/cuponesCargoCuentaActiva")
    public String cuponesCargoCuentaActiva() {
        return "cuponesCargoCuentaActiva";
    }

    @GetMapping(value = "/cuponesCargoPagos")
    public String cuponesCargoPagos() {
        return "cuponesCargoPagos";
    }

    @GetMapping(value = "/tiempoExtraAdmin")
    public String tiempoExtraAdmin() {
        return "tiempoExtraAdmin";
    }

    @GetMapping(value = "/registroIncidenciasTransportacion")
    public String registroIncidenciasTransportacion() {
        return "registroIncidenciasTransportacion";
    }

    @GetMapping(value = "/capturaIncidenciasTransportacion")
    public String capturaIncidenciasTransportacion() {
        return "capturaIncidenciasTransportacion";
    }

    @GetMapping(value = "/adminListadoNombTransp")
    public String adminListadoNombTransp() {
        return "adminListadoNombTransp";
    }

    @GetMapping(value = "/adeudoViviendaAdmin")
    public String adeudoViviendaAdmin() {
        return "adeudoViviendaAdmin";
    }

    @GetMapping(value = "/descuentoAdeudoVivienda")
    public String descuentoAdeudoVivienda() {
        return "descuentoAdeudoVivienda";
    }

    @GetMapping(value = "/adeudoViviendaCuentaActiva")
    public String adeudoViviendaCuentaActiva() {
        return "adeudoViviendaCuentaActiva";
    }

    @GetMapping(value = "/adeudoViviendaPagos")
    public String adeudoViviendaPagos() {
        return "adeudoViviendaPagos";
    }

    @GetMapping(value = "/calculoSeparacionAdmin")
    public String calculoSeparacionAdmin() {
        return "calculoSeparacionAdmin";
    }

    @GetMapping(value = "/calculoSeparacion")
    public String calculoSeparacion() {
        return "calculoSeparacion";
    }

    @GetMapping(value = "/valesFinAnio")
    public String valesFinAnio() {
        return "valesFinAnio";
    }

    @GetMapping(value = "/actualizacionMontoVales")
    public String actualizacionMontoVales() {
        return "actualizacionMontoVales";
    }

    @GetMapping(value = "/consultaHistoricaTransportacion")
    public String consultaHistoricaTransportacion() {
        return "consultaHistoricaTransportacion";
    }

    @GetMapping(value = "/reportesModuloCapturaRA15")
    public String reportesModuloCapturaRA15() {
        return "reportesModuloCapturaRA15";
    }

    @GetMapping(value = "/historicoTrabajadorCapturaSemanal")
    public String historicoTrabajadorCapturaSemanal() {
        return "historicoTrabajadorCapturaSemanal";
    }

    @GetMapping(value = "/importarTurnosAutorizados")
    public String importarTurnosAutorizados() {
        return "importarTurnosAutorizados";
    }

    @GetMapping(value = "/libroTurnosAutorizadosRA15")
    public String libroTurnosAutorizadosRA15() {
        return "libroTurnosAutorizadosRA15";
    }

    @GetMapping(value = "/libroTurnosAutorizadosRA15Tren")
    public String libroTurnosAutorizadosRA15Tren() {
        return "libroTurnosAutorizadosRA15Tren";
    }

    @GetMapping(value = "/importarCiclos")
    public String importarCiclos() {
        return "importarCiclos";
    }

    @GetMapping(value = "/catalogoImpuestosNomina")
    public String catalogoImpuestosNomina() {
        return "catalogoImpuestosNomina";
    }

    @GetMapping(value = "/cargarImpuestos")
    public String cargarImpuestos() {
        return "cargarImpuestos";
    }

    @GetMapping(value = "/adminRa10")
    public String adminRa10() {
        return "adminRa10";
    }

    @GetMapping(value = "/guardaMovimientoProvisional")
    public String guardaMovimientoProvisional() {
        return "guardaMovimientoProvisional";
    }

    @GetMapping(value = "/consultarMovimientosProvisionales")
    public String consultarMovimientosProvisionales() {
        return "consultarMovimientosProvisionales";
    }

    @GetMapping(value = "/sdiAdmin")
    public String sdiAdmin() {
        return "sdiAdmin";
    }

    @GetMapping(value = "/detallePagosIncidencias")
    public String detallePagosIncidencias() {
        return "detallePagosIncidencias";
    }

    @GetMapping(value = "/detallePagosConceptos")
    public String detallePagosConceptos() {
        return "detallePagosConceptos";
    }

    @GetMapping(value = "/sdiTrabajdores")
    public String sdiTrabajdores() {
        return "sdiTrabajdores";
    }

    @GetMapping(value = "/sdiDetalleTrabajador")
    public String sdiDetalleTrabajador() {
        return "sdiDetalleTrabajador";
    }

    @GetMapping(value = "/aguinaldo")
    public String aguinaldo() {
        return "aguinaldo";
    }

    @GetMapping(value = "/conceptosNomina")
    public String conceptosNomina() {
        return "conceptosNomina";
    }

    @GetMapping(value = "/calculoRetroactivo")
    public String calculoRetroactivo() {
        return "calculoRetroactivo";
    }

    @GetMapping(value = "/semanansCorte")
    public String semanansCorte() {
        return "semanansCorte";
    }

}
