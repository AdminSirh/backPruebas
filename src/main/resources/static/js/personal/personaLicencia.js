/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
/*window.onload = function () {
 const valores = window.location.search;
 const urlParams = new URLSearchParams(valores);
 var id_persona = urlParams.get('id_persona');
 $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
 "<li class='btn btn-primary' onclick=personaDatosGenerales('" + id_persona + "')>1.-DATOS GENERALES</li>&nbsp;" +
 "<li class='btn btn-primary' onclick=personaContacto('" + id_persona + "')>2.-DATOS DE CONTACTO</li>&nbsp;" +
 "<li class='btn btn-primary' onclick=personaDireccion('" + id_persona + "')>3.-DIRECCION</li>&nbsp;" +
 "<li><a href='#tabs-4' class='btn btn-primary'>4.-LICENCIA</a></li>&nbsp;" +
 "<li class='btn btn-primary' onclick=personaDocumentos('" + id_persona + "')>5.-DOCUMENTOS</li>&nbsp;" +
 "<li class='btn btn-primary' onclick=personaBeneficiarios('" + id_persona + "')>6.-BENEFICIARIOS</li>&nbsp;" +
 "<li class='btn btn-primary' onclick=personaMedico('" + id_persona + "')>7.-DATOS MEDICOS</li>&nbsp;");
 cargaDatosLicencia(id_persona);
 };*/
document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_persona = urlParams.get('id_persona');
    tabs(id_persona);
    cargaDatosLicencia(id_persona);
    tipoLicencia();
});
function tabs(id_persona) {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=personaDatosGenerales('" + id_persona + "')>1.-DATOS GENERALES</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaContacto('" + id_persona + "')>2.-DATOS DE CONTACTO</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaDireccion('" + id_persona + "')>3.- DIRECCION</li>&nbsp;" +
            "<li><a href='#tabs-4' class='btn btn-info'>4.- LICENCIA</a></li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaDocumentos('" + id_persona + "')>5.-DOCUMENTOS</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaMedico('" + id_persona + "')>6.-DATOS MEDICOS</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaCarta('" + id_persona + "')>7.- CARTA DESIGNATARIA</li>&nbsp;");
}

function personaDatosGenerales(id_persona) {
    window.location.href = 'personaDatosGenerales?id_persona=' + id_persona;
}
function personaContacto(id_persona) {
    window.location.href = 'personaContacto?id_persona=' + id_persona;
}
function personaDireccion(id_persona) {
    window.location.href = 'personaDireccion?id_persona=' + id_persona;
}
function personaDocumentos(id_persona) {
    window.location.href = 'personaDocumentos?id_persona=' + id_persona;
}

function personaMedico(id_persona) {
    window.location.href = 'personaMedico?id_persona=' + id_persona;
}
function personaCarta(id_persona) {
    window.location.href = 'personaCarta?id_persona=' + id_persona;
}


function verCandidatos() {
    window.location.href = 'candidatos';
}


/*=============================================
 Carga la datos de Licencia en el form
 =============================================*/
function cargaDatosLicencia(id_persona) {
    $.ajax({
        type: "GET",
        url: "personas/buscarLicencia/" + id_persona,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }

            if (data.data !== null) {

                $('#id_licencia').val(data.data.id_licencia);
                (data.data.num_licencia === null) ? $('#num_licencia').val("") : $('#num_licencia').val(data.data.num_licencia);
                (data.data.inicio_vig_licencia === null) ? $('#fecha_inicio').val("") : $('#fecha_inicio').val(data.data.inicio_vig_licencia);
                (data.data.fin_vig_licencia === null) ? $('#fecha_fin').val("") : $('#fecha_fin').val(data.data.fin_vig_licencia);
                tipoLicencia(data.data.tipo_licencia_id);
            }
            /* var tipo_licencia = $("#tipo_licencia").val();
             if (tipo_licencia === "") {
             //const input = document.querySelector('input');
             const log = document.getElementById('#tipo_licencia');
             // input.addEventListener('change', buscarCP);
             }*/
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos de Licencia : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

$("#formLicenciaPersona").submit(function (e) {
    event.preventDefault();
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var persona_id = urlParams.get('id_persona');
    var tipo_licencia_id = $("#tipo_licencia").val();
    var num_licencia = $("#num_licencia").val();
    var inicio_vig_licencia = $("#fecha_inicio").val();
    var fin_vig_licencia = $("#fecha_fin").val();
    var id_licencia = $("#id_licencia").val();


    if ($.trim(tipo_licencia_id) === "") {
        return false;
    }
    if ($.trim(num_licencia) === "") {
        return false;
    }
    if ($.trim(inicio_vig_licencia) === "") {
        return false;
    }
    if ($.trim(fin_vig_licencia) === "") {
        return false;
    }
    if ($.trim(persona_id) === "") {
        return false;
    }

    var datos = {"tipo_licencia_id": tipo_licencia_id, "num_licencia": num_licencia, "inicio_vig_licencia": inicio_vig_licencia, "fin_vig_licencia": fin_vig_licencia,
        "persona_id": persona_id};
    var licenciaURL = "";
    if (id_licencia === "" || id_licencia === null) {
        licenciaURL = "personas/guardarLicenciaPersona";
    } else if (id_licencia !== "") {
        licenciaURL = "personas/editarPersonaLicencia/" + persona_id;
    }

    $.ajax({
        type: "POST",
        url: licenciaURL,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            cargaDatosLicencia(persona_id);
            toastr['success'](data.data, "Aviso");
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});


function tipoLicencia(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarDatosLicencia",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#tipo_licencia').empty().append("<option value=''>* Tipo de Licencia</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_licencia === id) ? vselected = "selected" : vselected = "";
                        $('#tipo_licencia').append('<option value="' + data[x].id_licencia + '"' + vselected + '>' + data[x].tipo_licencia + '</option>');
                        
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select de Tipo de Licencia : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
function verCandidatos() {
    window.location.href = 'candidatos';
}

function verTrabajadores() {
    window.location.href = 'trabajadoresAdmin';
}