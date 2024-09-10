document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_persona = urlParams.get('id_persona');
    cargaDatosMedicos(id_persona);
    sangre();
    tabs(id_persona);
    punto();
    enfermedades();
    alergias();

});
function tabs(id_persona) {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=personaDatosGenerales('" + id_persona + "')>1.-DATOS GENERALES</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaContacto('" + id_persona + "')>2.-DATOS DE CONTACTO</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaDireccion('" + id_persona + "')>3.-DIRECCION</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaLicencia('" + id_persona + "')>4.-LICENCIA</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaDocumentos('" + id_persona + "')>5.-DOCUMENTOS</li>&nbsp;" +
            "<li><a href='#tabs-7' class='btn btn-info'>6.-DATOS MEDICOS </a></li>&nbsp;" +
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
function personaLicencia(id_persona) {
    window.location.href = 'personaLicencia?id_persona=' + id_persona;
}
function personaDocumentos(id_persona) {
    window.location.href = 'personaDocumentos?id_persona=' + id_persona;
}
function personaCarta(id_persona) {
    window.location.href = 'personaCarta?id_persona=' + id_persona;
}
function punto() {
    $("#altura_edita").on("input", function () {
        var v = $(this).val();
        if (v.toString().length > 2)
        {
            if (v.indexOf(".") > 0)
                v = v.slice(0, v.indexOf(".")) + v.slice(v.indexOf(".") + 1);
            $(this).val(v.slice(0, -2) + "." + v.slice(-2));
        }

    });
}

function cargaDatosMedicos(id_persona) {
    $.ajax({
        type: "GET",
        url: "personas/buscarMedico/" + id_persona,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            if (data.data !== null) {
                $('#id_medico').val(data.data.medico_id);
                (data.data.altura === null) ? $('#altura_edita').val("") : $('#altura_edita').val(data.data.altura);
                (data.data.peso === null) ? $('#peso_edita').val("") : $('#peso_edita').val(data.data.peso);
                (data.data.enfermedades === null) ? $('#enfermedades_edita').val("") : $('#enfermedades_edita').val(data.data.enfermedades);
                (data.data.alergias === null) ? $('#alergias_edita').val("") : $('#alergias_edita').val(data.data.alergias);
                (data.data.medico_cabecera === null) ? $('#medico_cabecera_edita').val("") : $('#medico_cabecera_edita').val(data.data.medico_cabecera);
                (data.data.telefono_medico === null) ? $('#telefono_medico_edita').val("") : $('#telefono_medico_edita').val(data.data.telefono_medico);
                //(data.data.contacto_caso_accidente === null) ? $('#contacto_caso_accidente_edita').val("") : $('#contacto_caso_accidente_edita').val(data.data.contacto_caso_accidente);
                (data.data.clinica === null) ? $('#clinica_edita').val("") : $('#clinica_edita').val(data.data.clinica);
               // (data.data.telefono_contacto_caso_accidente === null) ? $('#telefono_contacto_caso_accidente_edita').val("") : $('#telefono_contacto_caso_accidente_edita').val(data.data.telefono_contacto_caso_accidente);
                sangre(data.data.tipo_sangre_id);
                enfermedades(data.data.enfermedades_si_no);
                alergias(data.data.alergias_si_no);

                if (data.data.enfermedades_si_no === 1) {
                    $('#enfermedades_edita').removeAttr('disabled');
                }
                if (data.data.alergias_si_no === 1) {
                    $('#alergias_edita').removeAttr('disabled');
                }

            } else {
                sangre(0);
                enfermedades(0);
                alergias(0);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos medicos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });

}

/*=============================================
 COMBO SANGRE
 =============================================*/
function sangre(id) {
    // console.log("-->" + id);
    $.ajax({
        type: "GET",
        url: "catalogos/listarSangre",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {

                $('#cmbSangre').empty().append("<option value=''>* Tipo de Sangre</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_sangre === id) ? vselected = "selected" : vselected = "----";
                        $('#cmbSangre').append('<option value="' + data[x].id_sangre + '"' + vselected + '>' + data[x].tipo_sangre + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Tipo de Sangre : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


/*=============================================
 Activar o desactiva combo enfermedades si no edita
 =============================================*/
function validarEnfermedades() {
    var id = document.getElementById('cmbEnfermedades').value;
    if (id === "2") {
        $("#enfermedades_edita").attr('disabled', 'disabled');
    } else if (id === "1") {
        document.querySelector('#enfermedades_edita').required = true;
        $('#enfermedades_edita').removeAttr('disabled');

    }
}

/*=============================================
 Activar o desactiva combo enfermedades si no edita
 =============================================*/
function validarAlergias() {
    var id = document.getElementById('cmbAlergias').value;
    if (id === "2") {
        $("#alergias_edita").attr('disabled', 'disabled');
    } else if (id === "1") {
        document.querySelector('#alergias_edita').required = true;
        $('#alergias_edita').removeAttr('disabled');

    }
}



/*=============================================
 Guardar datos medicos del candidato
 =============================================*/
$("#formGuardarMedicoPersona").submit(function (e) {
    event.preventDefault();
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var persona_id = urlParams.get('id_persona');
    var altura = $("#altura_edita").val();
    var peso = $("#peso_edita").val();
    var tipo_sangre_id = $("#cmbSangre").val();
    var enfermedades = $("#enfermedades_edita").val();
    var alergias = $("#alergias_edita").val();
    var medico_cabecera = $("#medico_cabecera_edita").val();
    var telefono_medico = $("#telefono_medico_edita").val();
    //var contacto_caso_accidente = $("#contacto_caso_accidente_edita").val();
    var clinica = $("#clinica_edita").val();
    var id_medico = $('#id_medico').val();
    var cmbEnfermedades_si_no = $('#cmbEnfermedades').val();
    var cmbAlergias_si_no = $('#cmbAlergias').val();
    //var telefono_contacto_caso_accidente = $("#telefono_contacto_caso_accidente_edita").val();


    if ($.trim(altura) === "") {
        return false;
    }
    if ($.trim(peso) === "") {
        return false;
    }
    if ($.trim(tipo_sangre_id) === "") {
        return false;
    }
    if ($.trim(cmbEnfermedades_si_no) === "") {
        return false;
    }
    if ($.trim(cmbAlergias_si_no) === "") {
        return false;
    }
    if (cmbEnfermedades_si_no === "1") {
        document.querySelector('#enfermedades_edita').required = true;
        if ($.trim(enfermedades) === "") {
            return false;
        }
    }
    if (cmbAlergias_si_no === "1") {
        document.querySelector('#alergias_edita').required = true;
        if ($.trim(alergias) === "") {
            return false;
        }
    }
    if ($.trim(medico_cabecera) === "") {
        return false;
    }
    if ($.trim(telefono_medico) === "") {
        return false;
    }
    if ($.trim(clinica) === "") {
        return false;
    }
    if ($.trim(persona_id) === "") {
        return false;
    }

    var datos =
            {"altura": altura, "peso": peso, "tipo_sangre_id": tipo_sangre_id,
                "enfermedades": enfermedades, "alergias": alergias, "medico_cabecera": medico_cabecera, "telefono_medico": telefono_medico,
                "clinica": clinica, "persona_id": persona_id, "enfermedades_si_no": cmbEnfermedades_si_no, "alergias_si_no": cmbAlergias_si_no};
    var url_accion = "";
    if (id_medico === "" || id_medico === null) {
        url_accion = "personas/guardarMedicoPersona";

    } else if (id_medico !== "" || id_medico !== null) {
        url_accion = "personas/editarMedicoPersona/" + persona_id;
    }

    $.ajax({
        type: "POST",
        url: url_accion,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),
        success: function (data) {

            toastr['success'](data.data, "Aviso");
            cargaDatosMedicos(persona_id);
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso Error al guardar datos");
        }
    });
});

/*=============================================
 COMBO SANGRE
 =============================================*/
function sangre(id) {
    // console.log("-->" + id);
    $.ajax({
        type: "GET",
        url: "catalogos/listarSangre",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {

                $('#cmbSangre').empty().append("<option value=''>* Tipo de Sangre</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_sangre === id) ? vselected = "selected" : vselected = "----";
                        $('#cmbSangre').append('<option value="' + data[x].id_sangre + '"' + vselected + '>' + data[x].tipo_sangre + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Tipo de Sangre : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 COMBO Enfermedades SI NO
 =============================================*/

function enfermedades(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarSi_No",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbEnfermedades').empty().append("<option value=''>* ¿Padece alguna enfermedad? </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id === id) ? vselected = "selected" : vselected = " ";
                        $('#cmbEnfermedades').append('<option value="' + data[x].id + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Hijos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


/*=============================================
 COMBO Alergias SI NO
 =============================================*/

function alergias(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarSi_No",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbAlergias').empty().append("<option value=''>* ¿Padece alguna alergia? </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id === id) ? vselected = "selected" : vselected = " ";
                        $('#cmbAlergias').append('<option value="' + data[x].id + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Hijos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

function verCandidatos() {
    window.location.href = 'candidatos';
}

function verTrabajadores() {
    window.location.href = 'trabajadoresAdmin';
}