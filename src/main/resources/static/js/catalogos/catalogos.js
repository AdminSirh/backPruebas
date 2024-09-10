//******************************************************************************
//                        TABS PARA CATÁLOGOS      
//******************************************************************************
$(() => {
    tabs();
    tabs2();
    tabs3();
    const urlParams = new URLSearchParams(window.location.search);
    const activeTab = urlParams.get('tab');

    if (activeTab) {
        // Remover la clase "btn-info" de todas las pestañas
        $('.nav-tabs li').removeClass('btn-info');

        // Agregar la clase "btn-info" a la pestaña activa
        $('#' + activeTab).addClass('btn-info');
    }
});

//Regresa a lista de catálogos cuando se da click sobre el Heading "Administración de catálogos"
$("#home").click(function (e) {
    event.preventDefault();
    catalogosAdmin();
});

/*=============================================
 Despliegue de Tabs en cada HTML relacionado. Se utilizan dos función tabs para que se separen las mismas al tener la pantalla completa
 =============================================*/
// Guarda el HTML a insertar en una variable
var tabsHtml = "<ul class='nav nav-tabs' role='tablist'>" +
        "<li class='btn btn-primary' id='tab-1' onclick=catalogoBancos()>Bancos</li>&nbsp;" + //1
        "<li class='btn btn-primary' id='tab-2' onclick=catalogoCargo()>Cargo</li>&nbsp;" + //2
        "<li class='btn btn-primary' id='tab-3' onclick=catalogoCreditoInfonavit()>Crédito Infonavit</li>&nbsp;" + //3
        "<li class='btn btn-primary' id='tab-4' onclick=catalogoDepositos()>Depósitos</li>&nbsp;" + // 4
        "<li class='btn btn-primary' id='tab-5' onclick=catalogoDias()>Días</li>&nbsp;" + //5 
        "<li class='btn btn-primary' id='tab-6' onclick=catalogoDiasFestivos()>Días Festivos</li>&nbsp;" + //6
        "<li class='btn btn-primary' id='tab-7' onclick=catalogoEdoCivil()>Estado Civil</li>&nbsp;" + //7
        "<li class='btn btn-primary' id='tab-8' onclick=catalogoEstado()>Estado/Municipios/Colonias</li>&nbsp;" + //8
        "<li class='btn btn-primary' id='tab-9' onclick=catalogoEstadoVacaciones()>Estado Vacaciones</li>&nbsp;" + //9
        "<li class='btn btn-primary' id='tab-10' onclick=catalogoEstructura()>Estructura</li>&nbsp;" + //10
        "<li class='btn btn-primary' id='tab-11' onclick=catalogoEstudios()>Estudios</li>&nbsp;" + //11
        "<li class='btn btn-primary' id='tab-12' onclick=catalogoGenero()>Género</li>&nbsp;" + //12
        "<li class='btn btn-primary' id='tab-13' onclick=catalogoInhabilitado()>Inhabilitado</li>&nbsp;" + //13
        "<li class='btn btn-primary' id='tab-14' onclick=catalogoLicencia()>Licencias</li>&nbsp;</ul>"; //14

var tabs2Html = "<ul class='nav nav-tabs' role='tablist'>" +
        "<li class='btn btn-primary' id='tab-15' onclick=catalogoNacionalidad()>Nacionalidad</li>&nbsp;" + //15
        "<li class='btn btn-primary' id='tab-16' onclick=catalogoPlazaMovimientos()>Plaza Movimientos</li>&nbsp;" + //16
        "<li class='btn btn-primary' id='tab-17' onclick=catalogoParentesco() >Parentesco</li>&nbsp" + //17
        "<li class='btn btn-primary' id='tab-18' onclick=catalogo_si_no()>Si, No</li>&nbsp;" + //18
        "<li class='btn btn-primary' id='tab-19' onclick=catalogoTipoBeneficiario()>Tipo Beneficiario</li>&nbsp;" + //19
        "<li class='btn btn-primary' id='tab-20' onclick=catalogoTipoContrato()>Tipo Contrato</li>&nbsp;" + //20
        "<li class='btn btn-primary' id='tab-21' onclick=catalogoTipoDocumento()>Tipo Documento</li>&nbsp;" + //21 
        "<li class='btn btn-primary' id='tab-22' onclick=catalogoTipoIncidencia()>Tipo Incidencias</li>&nbsp;" + //22
        "<li class='btn btn-primary' id='tab-23' onclick=catalogoTipoSangre()>Tipo de Sangre</li>&nbsp;" + //23
        "<li class='btn btn-primary' id='tab-24' onclick=catalogoTipoTabulador()>Tipo Tabulador</li>&nbsp;" + //24
        "<li class='btn btn-primary' id='tab-25' onclick=catalogoAreas()>Áreas</li>&nbsp;" +
        "<li class='btn btn-primary' id='tab-26' onclick=catalogoUbicacion()>Ubicación</li>&nbsp;"; //26


var tabs3Html = "<ul class='nav nav-tabs' role='tablist'>" +
        "<li class='btn btn-primary' id='tab-27' onclick=impuestos()>Impuestos</li>&nbsp;"; 

function tabs() {
    $('#myTab').html(tabsHtml);
}

function tabs2() {
    $('#myTab2').html(tabs2Html);
}

function tabs3() {
    $('#myTab3').html(tabs3Html);
}

const redireccionTab = (tabId, url) => {
    const redirectUrl = url + `?tab=${tabId}`;
    //Se mantiene en la pestaña actual si se da click sobre la misma
    if (getActiveTab() !== tabId) {
        window.location.href = redirectUrl;
    }
};

const getActiveTab = () => {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('tab');
};

/*=============================================
 Funciones para apertura de otros catálogos 
 =============================================*/
function catalogoBancos() {
     redireccionTab('tab-1', 'catalogoBancos');
}
function catalogoCargo() {
    redireccionTab('tab-2', 'catalogoCargo');
}
function catalogoCreditoInfonavit() {
    redireccionTab('tab-3', 'catalogoCreditoInfonavit');
}
function catalogoDepositos() {
    redireccionTab('tab-4', 'catalogoDepositos');
}
function catalogoDias() {
    redireccionTab('tab-5', 'catalogoDias');
}
function catalogoDiasFestivos() {
    redireccionTab('tab-6', 'catalogoDia_Festivos');
}
function catalogoEdoCivil() {
    redireccionTab('tab-7', 'catalogoEdoCivil');
}
function catalogoEstado() {
    redireccionTab('tab-8', 'catalogoEstado');
}
function catalogoEstadoVacaciones() {
    redireccionTab('tab-9', 'catalogoEdoVacaciones');
}
function catalogoEstructura() {
    redireccionTab('tab-10', 'catalogoEstructura');
}
function catalogoEstudios() {
    redireccionTab('tab-11', 'catalogoEstudios');
}
function catalogoGenero() {
    redireccionTab('tab-12', 'catalogoGenero');
}
function catalogoInhabilitado() {
    redireccionTab('tab-13', 'catalogoInhabilitado');
}
function catalogoLicencia() {
    redireccionTab('tab-14', 'catalogoLicencia');
}
function catalogoNacionalidad() {
    redireccionTab('tab-15', 'catalogoNacionalidad');
}
function catalogoPlazaMovimientos() {
    redireccionTab('tab-16', 'catalogoPlazaMovimientos');
}
function catalogoParentesco() {
    redireccionTab('tab-17', 'catalogoParentesco');
}
function catalogo_si_no() {
    redireccionTab('tab-18', 'catalogo_si_no');
}
function catalogoTipoBeneficiario() {
    redireccionTab('tab-19', 'catalogoTipoBeneficiario');
}
function catalogoTipoContrato() {
    redireccionTab('tab-20', 'catalogoTipoContrato');
}
function catalogoTipoDocumento() {
    redireccionTab('tab-21', 'catalogoTipoDocumento');
}
function catalogoTipoIncidencia() {
    redireccionTab('tab-22', 'catalogoTipoIncidencia');
}
function catalogoTipoSangre() {
    redireccionTab('tab-23', 'catalogoTipoSangre');
}
function catalogoTipoTabulador() {
    redireccionTab('tab-24', 'catalogoTipoTabulador');
}
function catalogoAreas() {
    redireccionTab('tab-25', 'catalogoAreas');
}
function catalogoUbicacion() {
    redireccionTab('tab-26', 'catalogoUbicacion');
}
function catalogosAdmin() {
    window.location.href = 'catalogosAdmin';
}
function catalogoIncidencias() {
    window.location.href = 'catalogoIncidencias';
}


function impuestos() {
    redireccionTab('tab-27', 'catalogoImpuestosNomina');
}