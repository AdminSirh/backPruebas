document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_persona = urlParams.get('id_persona');
    cargaDatosBeneficiario(id_persona);
    cargaDatosBeneficiarioSec(id_persona);
    tabs(id_persona);
  ;

});
function tabs(id_persona) {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=personaDatosGenerales('" + id_persona + "')>1.-DATOS GENERALES</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaContacto('" + id_persona + "')>2.-DATOS DE CONTACTO</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaDireccion('" + id_persona + "')>3.-DIRECCION</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaLicencia('" + id_persona + "')>4.-LICENCIA</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaDocumentos('" + id_persona + "')>5.-DOCUMENTOS</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaMedico('" + id_persona + "')>6.-DATOS MEDICOS</li>&nbsp;"+
            "<li class='btn btn-primary' onclick=personaCarta('" + id_persona + "')>7.-CARTA DESIGNATARIA</li>&nbsp;");

}
 
function personaDatosGenerales(id_persona) {
    window.location.href = 'personaDatosGeneralesVer?id_persona=' + id_persona;
}
function personaContacto(id_persona) {
    window.location.href = 'personaContactoVer?id_persona=' + id_persona;
}
function personaDireccion(id_persona) {
    window.location.href = 'personaDireccionVer?id_persona=' + id_persona;
}
function personaLicencia(id_persona) {
    window.location.href = 'personaLicenciaVer?id_persona=' + id_persona;
}
function personaDocumentos(id_persona) {
    window.location.href = 'personaDocumentosVer?id_persona=' + id_persona;
}
function personaMedico(id_persona) {
    window.location.href = 'personaMedicoVer?id_persona=' + id_persona;
}
function personaCarta(id_persona) {
    window.location.href = 'personaCartaVer?id_persona=' + id_persona;
}