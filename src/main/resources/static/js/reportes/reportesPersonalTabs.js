$(() => {
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

const tabs = () => {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' id='tab-1'  onclick=reportesPersonal()>Monitor de Plazas Autorizadas</li>&nbsp;" +
            "<li class='btn btn-primary' id='tab-2'  onclick=plazaAutorizada()>Plazas Autorizadas</li>&nbsp;" +
            "<li class='btn btn-primary' id='tab-3'  onclick=constanciasPersonal()>Constancias Personal</li>&nbsp;" +
            "<li class='btn btn-primary' id='tab-4'  onclick=avisoMovimientoPersonal()>Aviso Movimientos Personal</li>&nbsp;" +
            "<li class='btn btn-primary' id='tab-5' onclick=reporteFemeninoArea()>Personal Femenino</li>&nbsp;" +
            "<li class='btn btn-primary' id='tab-6' onclick=personalJubilado()>Personal Jubilado</li>&nbsp;" +
            "<li class='btn btn-primary' id='tab-7' onclick=movimientosPersonal()>Movimientos Personal</li>&nbsp;" +
            "<li class='btn btn-primary' id='tab-8' onclick=reporteAyudas()>Ayudas</li>&nbsp;");
};

const reportesPersonal = () => {
    window.location.href = 'reportesPersonal?tab=tab-1';
};

const plazaAutorizada = () => {
    window.location.href = 'plazaAutorizada?tab=tab-2';
};

const constanciasPersonal = () => {
    window.location.href = 'constanciasPersonal?tab=tab-3';
};

const avisoMovimientoPersonal = () => {
    window.location.href = 'reporteAvisoMovimientoPersonal?tab=tab-4';
};

const reporteFemeninoArea = () => {
    window.location.href = 'reporteFemeninoArea?tab=tab-5';
};

const personalJubilado = () => {
    window.location.href = 'personalJubilado?tab=tab-6';
};

const movimientosPersonal = () => {
    window.location.href = 'movimientosPersonal?tab=tab-7';
};

const reporteAyudas = () => {
    window.location.href = 'reporteAyudas?tab=tab-8';
};

