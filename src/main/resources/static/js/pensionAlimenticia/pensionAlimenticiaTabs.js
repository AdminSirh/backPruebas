document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const activeTab = urlParams.get('tab');
    tabs();
    tabAdmin();
    modificarClaseTabs(activeTab);
});

function modificarClaseTabs(activeTab) {
    if (activeTab) {
        // Remover la clase "btn-info" de todas las pestañas
        $('.nav-tabs li').removeClass('btn-info');

        // Agregar la clase "btn-info" a la pestaña activa
        $('#' + activeTab).addClass('btn-info');
    }

}

function tabs() {
    $('#myTab').html(
            "<li class='btn btn-secondary' onclick=pensionAlimentaria()>Ver Trabajadores</li></ul><hr>" +
            "<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' id='tab-2'  onclick=verPension()> 1.- VISUALIZACIÓN Y MODIFICACIÓN DE CRÉDITOS ACTIVOS</li>&nbsp;" +
            "<li class='btn btn-primary' id='tab-3'  onclick=verPensionHistorico()>2.- PENSIONES ALIMENTICIAS INACTIVAS</li>&nbsp;" +
            "<li class='btn btn-primary' id='tab-4'  onclick=pAlimenticiaHistoricoEdiciones()>3.- HISTÓRICO DE EDICIONES A PENSIONES ALIMENTICIAS</li>&nbsp;");
}

function tabAdmin() {
    $('#myTabAdmin').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary btn-info' id='tab-1'  onclick=pensionAlimentariaHome()>Trabajadores</li>&nbsp;" +
            "<li class='btn btn-primary' id='tab-2'  onclick=verBeneficiarios()>Beneficiarios</li>&nbsp;");
}

//Mantiene el valor del parámetro id trabajador y resalta las pestañas
function redireccionTab(tabId, paramId, url) {
    const urlParams = new URLSearchParams(window.location.search);
    const paramValue = urlParams.get(paramId);
    const redirectUrl = url + `?tab=${tabId}&${paramId}=${paramValue}`;
    //Se mantiene en la pestaña actual si se da click sobre la misma
    if (getActiveTab() !== tabId) {
        window.location.href = redirectUrl;
    }
}

//Comprobación de tab Actual
function getActiveTab() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('tab');
}

function asignacionPensionHome() {
    redireccionTab('tab-1', 'id_trabajador', 'asignacionPension');
}

function verPension() {
    redireccionTab('tab-2', 'id_trabajador', 'pAlimenticiaTabla');
}

function verPensionHistorico() {
    redireccionTab('tab-3', 'id_trabajador', 'pAlimenticiaTabInactivas');
}

function pAlimenticiaHistoricoEdiciones() {
    redireccionTab('tab-4', 'id_trabajador', 'pAlimenticiaHistoricoEdiciones');
}

function pensionAlimentariaHome() {
    window.location.href = 'pensionAlimenticia';
}

function pensionAlimentaria() {
    window.location.href = 'pensionAlimenticia';
}

function verBeneficiarios() {
    window.location.href = 'pensionAlimenticiaBeneficiarios';
}