/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_persona = urlParams.get('id_persona');
    personaCargaDatosForm(id_persona);
    tabs(id_persona);
});

function tabs(id_persona) {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li><a href='#tabs-1' class='btn btn-info'>1.- DATOS GENERALES</a></li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaContacto('" + id_persona + "')>2.- DATOS DE CONTACTO</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaDireccion('" + id_persona + "')>3.- DIRECCION</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaLicencia('" + id_persona + "')>4.- LICENCIA</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaDocumentos('" + id_persona + "')>5.- DOCUMENTOS</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaMedico('" + id_persona + "')>6.- DATOS MÉDICOS</li>&nbsp;"+
            "<li class='btn btn-primary' onclick=personaCarta('" + id_persona + "')>7.- CARTA DESIGNATARIA</li>&nbsp;");
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

function personaMedico(id_persona) {
    window.location.href = 'personaMedico?id_persona=' + id_persona;
}
function personaCarta(id_persona) {
    window.location.href = 'personaCarta?id_persona=' + id_persona;
}

function personaCargaDatosForm(id_persona) {
    $.ajax({
        type: "GET",
        url: "personas/buscarPersona/" + id_persona,
        dataType: 'json',
        success: function (data) {
            (data.data.nombre === null) ? $('#nombre_candidato_edita').val("") : $('#nombre_candidato_edita').val(data.data.nombre);
            (data.data.apellido_paterno === null) ? $('#apellido_paterno_edita').val("") : $('#apellido_paterno_edita').val(data.data.apellido_paterno);
            (data.data.apellido_materno === null) ? $('#apellido_materno_edita').val("") : $('#apellido_materno_edita').val(data.data.apellido_materno);

            (data.data.fecha_nacimiento === null) ? $('#fecha_nacimiento_edita').val("") : $('#fecha_nacimiento_edita').val(data.data.fecha_nacimiento);
            (data.data.cat_estado === null) ? estadoNacimiento(0) : estadoNacimiento(data.data.cat_estado.id_estado);
            (data.data.cat_nacionalidad === null) ? nacionalidad(0) : nacionalidad(data.data.cat_nacionalidad.id_nacionalidad);
            (data.data.cat_estudios === null) ? gradoEstudios(0) : gradoEstudios(data.data.cat_estudios.id_grado);

            (data.data.curp === null) ? $('#curp_edita').val("") : $('#curp_edita').val(data.data.curp);
            (data.data.rfc === null) ? $('#rfc_edita').val("") : $('#rfc_edita').val(data.data.rfc);
            (data.data.num_imss === null) ? $('#num_imss_edita').val("") : $('#num_imss_edita').val(data.data.num_imss);
            (data.data.cat_genero === null) ? genero(0) : genero(data.data.cat_genero.id_genero);

            (data.data.matricula_smn === null) ? $('#smn_edita').val("") : $('#smn_edita').val(data.data.matricula_smn);
            (data.data.cat_estado_civil === null) ? estadoCivil(0) : estadoCivil(data.data.cat_estado_civil.id_edo_civil);
            (data.data.fecha_matrimonio === null) ? $('#fecha_matrimonio_edita').val("") : $('#fecha_matrimonio_edita').val(data.data.fecha_matrimonio);

            (data.data.cat_cargo_admon_pub_si_no === null) ? admonPublica(0) : admonPublica(data.data.cat_cargo_admon_pub_si_no.id);
            (data.data.cat_inhabilitado_admon_pub_si_no === null) ? inhabilitacionPublica(0) : inhabilitacionPublica(data.data.cat_inhabilitado_admon_pub_si_no.id);
            (data.data.cat_credito_infonavit_si_no === null) ? infonavitPublica(0) : infonavitPublica(data.data.cat_credito_infonavit_si_no.id);
            (data.data.cat_hijos_si_no === null) ? hijos(0) : hijos(data.data.cat_hijos_si_no.id);
        },
        error: function (e) {
            alert(e);
        }
    });

}


/*=============================================
 COMBO Hijos SI NO
 =============================================*/

function hijos(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarSi_No",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbHijos').empty().append("<option value=''>* Tiene Hijos </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id === id) ? vselected = "selected" : vselected = " ";
                        $('#cmbHijos').append('<option value="' + data[x].id + '"' + vselected + '>' + data[x].descripcion + '</option>');
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
 COMBO Admon Publica SI NO
 =============================================*/

function admonPublica(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarSi_No",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbAdmonPublica').empty().append("<option value=''>* Cargo en Admon. Pública </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id === id) ? vselected = "selected" : vselected = "";
                        $('#cmbAdmonPublica').append('<option value="' + data[x].id + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }

            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Admon Publica : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 COMBO Inhabilitación Admon. Pública SI NO
 =============================================*/

function inhabilitacionPublica(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarSi_No",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbInhabilitadoAdmonPub').empty().append("<option value=''>* Inhabilitación Admon. Pública</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id === id) ? vselected = "selected" : vselected = "";
                        $('#cmbInhabilitadoAdmonPub').append('<option value="' + data[x].id + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Inhabilitacion A. Publica: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 COMBO Credito Infonavit  SI NO
 =============================================*/

function infonavitPublica(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarSi_No",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbCreditoInfonavit').empty().append("<option value=''>* Crédito INFONAVIT</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id === id) ? vselected = "selected" : vselected = "";
                        $('#cmbCreditoInfonavit').append('<option value="' + data[x].id + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Infonavit : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 COMBO ESTUDIOS
 =============================================*/
function gradoEstudios(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarEstudios",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbEstudios').empty().append("<option value=''>* Grado Máximo de Estudios</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_grado === id) ? vselected = "selected" : vselected = "";
                        $('#cmbEstudios').append('<option value="' + data[x].id_grado + '"' + vselected + '>' + data[x].desc_grado + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Grado de Estudios : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 COMBO ESTADOS LUGAR NACIMIENTO
 =============================================*/

function estadoNacimiento(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarEstado",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbEstadoNacimiento').empty().append("<option value=''>* Lugar de Nacimiento</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_estado === id) ? vselected = "selected" : vselected = "";
                        $('#cmbEstadoNacimiento').append('<option value="' + data[x].id_estado + '"' + vselected + '>' + data[x].desc_estado + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Estado Nacimiento : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


/*=============================================
 COMBO NACIONALIDAD
 =============================================*/
function nacionalidad(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarNacionalidad",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbNacionalidad').empty().append("<option value=''>* Nacionalidad</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_nacionalidad === id) ? vselected = "selected" : vselected = "";
                        $('#cmbNacionalidad').append('<option value="' + data[x].id_nacionalidad + '"' + vselected + '>' + data[x].desc_nacionalidad + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Nacionalidad : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 COMBO GENERO
 =============================================*/
function genero(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarGenero",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbGenero').empty().append("<option value=''>* Genero</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_genero === id) ? vselected = "selected" : vselected = "";
                        $('#cmbGenero').append('<option value="' + data[x].id_genero + '"' + vselected + '>' + data[x].desc_genero + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Genero : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 COMBO ESTADO CIVIL
 =============================================*/
function estadoCivil(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarEdoCivil",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbEstadoCivil').empty().append("<option value=''>* Estado Civil</option> ");
                var vselected = "";
                /*if (id === 2) {
                    $("#fecha_matrimonio_edita").attr('disabled', 'disabled');
                }*/
//                } else if (id === 1 || id === 3) {
//                    $('#fecha_matrimonio_edita').removeAttr('disabled');
//                    document.querySelector('#fecha_matrimonio_edita').required = true;
//    }
                if (data.length > 0) {

                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_edo_civil === id) ? vselected = "selected" : vselected = "";
                        $('#cmbEstadoCivil').append('<option value="' + data[x].id_edo_civil + '"' + vselected + '>' + data[x].desc_edo_civil + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Estado Civil : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//Activar o desactivar el combo Fecha Matrimonio dependiendo del Estado Civil que seleccione el usuario
/*function validarEdoCivil() {
    var id = document.getElementById('cmbEstadoCivil').value;
    if (id === "2") {
        $("#fecha_matrimonio_edita").attr('disabled', 'disabled');
    } else if (id === "1" || id === "3") {
        document.querySelector('#fecha_matrimonio_edita').required = true;
        $('#fecha_matrimonio_edita').removeAttr('disabled');

    }
}*/

$("#formGuardarPersona").submit(function (e) {
    event.preventDefault();
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_persona = urlParams.get('id_persona');

    var nombre = $("#nombre_candidato_edita").val();                        //1
    var apellido_paterno = $("#apellido_paterno_edita").val();              //2
    var apellido_materno = $("#apellido_materno_edita").val();              //3
    var fecha_nacimiento = $("#fecha_nacimiento_edita").val();              //4
    var estado_nacimiento_id = $("#cmbEstadoNacimiento").val();             //5
    var nacionalidad_id = $("#cmbNacionalidad").val();                      //6
    var grado_estudios_id = $("#cmbEstudios").val();                        //7
    var curp_edita = $("#curp_edita").val();                                //8
    var rfc_edita = $("#rfc_edita").val();                                  //9
    var num_imss_edita = $("#num_imss_edita").val();                        //10
    var genero_id = $("#cmbGenero").val();                                  //11
    var matricula_smn = $("#smn_edita").val();                              //|2
    var estado_civil_id = $("#cmbEstadoCivil").val();                       //13
    var fecha_matrimonio_edita = $("#fecha_matrimonio_edita").val();        //14
    var hijos_si_no = $("#cmbHijos").val();                                 //15
    var administracion_publica_si_no = $("#cmbAdmonPublica").val();         //16
    var inhabilitado_si_no = $("#cmbInhabilitadoAdmonPub").val();           //17
    var credito_infonavit_id = $("#cmbCreditoInfonavit").val();             //18


//    console.log(typeof (estado_civil_id));
//    if (estado_civil_id===2) {
//        console.log("estado_civil_id ");
//        $("#fecha_matrimonio_edita").attr('disabled','disabled'); 
   /* if (estado_civil_id === "1" || estado_civil_id === "3") {
        document.querySelector('#fecha_matrimonio_edita').required = true;
//        $('#fecha_matrimonio_edita').removeAttr('disabled');
        if ($.trim(fecha_matrimonio_edita) === "") {
            return false;
        }
    }*/


    if ($.trim(nombre) === "") {
        return false;
    }
    if ($.trim(apellido_paterno) === "") {
        return false;
    }
    if ($.trim(estado_civil_id) === "") {
        return false;
    }
    if ($.trim(fecha_nacimiento) === "") {
        return false;
    }
    if ($.trim(estado_nacimiento_id) === "") {
        return false;
    }
    if ($.trim(nacionalidad_id) === "") {
        return false;
    }
    if ($.trim(grado_estudios_id) === "") {
        return false;
    }
    if ($.trim(curp_edita) === "") {
        return false;
    }
    if ($.trim(rfc_edita) === "") {
        return false;
    }
    if ($.trim(num_imss_edita) === "") {
        return false;
    }
    if ($.trim(genero_id) === "") {
        return false;
    }
    /*if ($.trim(matricula_smn) === "") {
        return false;
    }*/
//   if ($.trim(fecha_matrimonio_edita) === "") {
//        return false;
//    }
    if ($.trim(hijos_si_no) === "") {
        return false;
    }
    if ($.trim(administracion_publica_si_no) === "") {
        return false;
    }
    if ($.trim(inhabilitado_si_no) === "") {
        return false;
    }
    if ($.trim(credito_infonavit_id) === "") {
        return false;
    }

    var datos =
            {"nombre": nombre, "apellido_paterno": apellido_paterno, "apellido_materno": apellido_materno, "fecha_nacimiento": fecha_nacimiento,
                "cat_estado_nacimiento": {"id_estado": estado_nacimiento_id}, "cat_nacionalidad": {"id_nacionalidad": nacionalidad_id}, "cat_estudios": {"id_grado": grado_estudios_id},
                "curp": curp_edita, "rfc": rfc_edita, "num_imss": num_imss_edita, "cat_genero": {"id_genero": genero_id}, "matricula_smn": matricula_smn, "cat_estado_civil": {"id_edo_civil": estado_civil_id},
                "fecha_matrimonio": fecha_matrimonio_edita, "hijos_si_no": {"id": hijos_si_no}, "cargo_admon_pub_si_no": {"id": administracion_publica_si_no},
                "inhabilitado_admon_pub_si_no": {"id": inhabilitado_si_no}, "credito_infonavit_si_no": {"id": credito_infonavit_id}};
    //console.log(id_persona);
    $.ajax({
        type: "POST",
        url: "personas/guardarPersonaGenerales/" + id_persona,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            toastr['success'](data.data, "Aviso");

        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso Error");
        }
    });
});

function verCandidatos() {
    window.location.href = 'candidatos';
}

function verTrabajadores() {
    window.location.href = 'trabajadoresAdmin';
}
