//******************************************************************************
//                        TABS PARA RA 15      
//******************************************************************************
document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const activeTabs = urlParams.getAll('tab'); // Obtener todos los valores de 'tab' en la URL
    tabs();
    tabsCapturaSemanal();
    tabsTurnosAutorizados();
    modificarClaseTab(activeTabs);
});

/*=============================================
 Despliegue de Tabs en cada HTML relacionado.
 =============================================*/
const tabsHtmlGeneral = "<ul class='nav nav-tabs' role='tablist'>" +
        "<li class='btn btn-primary' id='tab-1' onclick=capturaSemanal()>Captura de Turnos</li>&nbsp;" +
        "<li class='btn btn-primary' id='tab-2' onclick=consultaHistorica()>Consulta Histórica</li>&nbsp;" +
        "<li class='btn btn-primary' id='tab-3' onclick=importarTurnosAutorizados()>Importar Turnos Autorizados</li>&nbsp;" +
        "<li class='btn btn-primary' id='tab-6' onclick=libroTurnosAutorizados()>Libro Turnos Autorizados </li>&nbsp;";

const tabHtmlCapturaSemanal =
        "<ul class='nav nav-tabs' role='tablist'>" +
        "<li class='btn btn-primary' id='tab-4' onclick=nuevoPeriodoRa()>Nuevo Periodo RA15</li>&nbsp;" +
        "<li class='btn btn-primary' id='tab-5' onclick=reportesModuloCapturaRA15()>Reportes</li>&nbsp;";

const tabHtmlLibroTurnosAutorizados =
        "<ul class='nav nav-tabs' role='tablist'>" +
        "<li class='btn btn-primary' id='tab-7' onclick=libroTurnosAutorizados()>Operadores y General Patio</li>&nbsp;" +
        "<li class='btn btn-primary' id='tab-8' onclick=libroTurnosAutorizadosTren()>Tren Ligero</li>&nbsp;" +
        "<li class='btn btn-primary' id='tab-8' onclick=generarExcelPlantas()><i class='fa fa-table' aria-hidden='true'></i> Generar Archivo Excel</li>&nbsp;";

function modificarClaseTab(activeTabs) {

    if (activeTabs.length > 0) {
        // Remover la clase "btn-info" de todas las pestañas
        $('.nav-tabs li').removeClass('btn-info');

        // Agregar la clase "btn-info" a las pestañas activas
        activeTabs.forEach(tabId => {
            $('#' + tabId).addClass('btn-info');
        });
    }
}

function tabs() {
    $('#myTab').html(tabsHtmlGeneral);
}

function tabsCapturaSemanal() {
    $('#myTabCS').html(tabHtmlCapturaSemanal);
}

function tabsTurnosAutorizados() {
    $('#myTabTA').html(tabHtmlLibroTurnosAutorizados);
}

function redireccionTab(tabId, url) {
    let redirectUrl;
    if (url === 'libroTurnosAutorizadosRA15') {
        redirectUrl = url + `?tab=${tabId}&tab=tab-7`;
    } else {
        redirectUrl = url + `?tab=${tabId}`;
    }
    //Se mantiene en la pestaña actual si se da click sobre la misma
    if (getActiveTab() !== tabId) {
        window.location.href = redirectUrl;
    }
}

function getActiveTab() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('tab');
}
/*=============================================
 Funciones para apertura de módulos
 =============================================*/
function capturaSemanal() {
    redireccionTab('tab-1', 'adminListadoNombTransp');
}

function consultaHistorica() {
    redireccionTab('tab-2', 'consultaHistoricaTransportacion');
}

function reportesModuloCapturaRA15() {
    redireccionTab('tab-5', 'reportesModuloCapturaRA15');
}

function importarTurnosAutorizados() {
    redireccionTab('tab-3', 'importarTurnosAutorizados');
}

function libroTurnosAutorizados() {
    redireccionTab('tab-6', 'libroTurnosAutorizadosRA15');
}

function libroTurnosAutorizadosTren() {
    redireccionTab('tab-8', 'libroTurnosAutorizadosRA15Tren');
}