//******************************************************************************
//                        TABS PARAMETROS NÓMINA      
//******************************************************************************
document.addEventListener('DOMContentLoaded', () => {
    tabs();
    const urlParams = new URLSearchParams(window.location.search);
    const activeTab = urlParams.get('tab');

    if (activeTab) {
        // Remover la clase "btn-info" de todas las pestañas
        $('.nav-tabs li').removeClass('btn-info');

        // Agregar la clase "btn-info" a la pestaña activa
        $('#' + activeTab).addClass('btn-info');
    }
});

var tabsHtml = "<ul class='nav nav-tabs' role='tablist'>" +
        "<li class='btn btn-primary' id='tab-1' onclick=calendarioAnual()>Calendario Anual</li>&nbsp;" +
        "<li class='btn btn-primary' id='tab-2' onclick=parametrosNomina()>Propiedades por Nómina</li>&nbsp;" +
        "<li class='btn btn-primary' id='tab-3' onclick=semanasCorte()>Semanas de Corte de Tiempo Exrta</li>&nbsp;";

function tabs() {
    $('#myTab').html(tabsHtml);
}

function redireccionTab(tabId, url) {
    const redirectUrl = url + `?tab=${tabId}`;
    //Se mantiene en la pestaña actual si se da click sobre la misma
    if (getActiveTab() !== tabId) {
        window.location.href = redirectUrl;
    }
}

function getActiveTab() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('tab');
}

function calendarioAnual() {
    redireccionTab('tab-1', 'parametrosNomina');
}
function parametrosNomina() {
    redireccionTab('tab-2', 'conceptosNomina');
}
function semanasCorte() {
    redireccionTab('tab-3', 'semanansCorte');
}