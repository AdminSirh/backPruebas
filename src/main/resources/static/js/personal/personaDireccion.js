document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_persona = urlParams.get('id_persona');
    tabs(id_persona);
    cargaDatosDireccion(id_persona);
});

function tabs(id_persona) {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=personaDatosGenerales('" + id_persona + "')>1.-DATOS GENERALES</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaContacto('" + id_persona + "')>2.-DATOS DE CONTACTO</li>&nbsp;" +
            "<li><a href='#tabs-3' class='btn btn-info'>3.- DIRECCION</a></li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaLicencia('" + id_persona + "')>4.-LICENCIA</li>&nbsp;" +
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



/*=============================================
 Carga la direccion en form
 =============================================*/
function cargaDatosDireccion(id_persona) {
    $.ajax({
        type: "GET",
        url: "personas/buscarDireccion/" + id_persona,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }

            if (data.data !== null) {

                $('#id_Direccion').val(data.data.id_direccion);
                (data.data.calle === null) ? $('#calle_valor').val("") : $('#calle_valor').val(data.data.calle);
                (data.data.num_exterior === null) ? $('#num_exterior').val("") : $('#num_exterior').val(data.data.num_exterior);
                (data.data.num_interior === null) ? $('#num_interior').val("") : $('#num_interior').val(data.data.num_interior);
                //Si la colonia es diferente de null o cero se llama a la función colonia que recupera la información
                if (data.data.colonia_id !== 0 && data.data.colonia_id !== null) {
                    colonia(data.data.colonia_id);
                }
            }
            var cod_postal = $("#cod_postal").val();
            if (cod_postal === "") {
                const input = document.querySelector('input');
                const log = document.getElementById('#cod_postal');
                input.addEventListener('change', buscarCP);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos de dirección : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 Busqueda de colonias por CP
 =============================================*/
function buscarCP(cod_postal) {
    var cp = cod_postal.target.value;
    if (cp.length === 5) {

        $.ajax({
            type: "GET",
            url: "codigoPostal/buscarCP/" + cp,
            contentType: "application/json",
            success: function (data) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                }

                $('#alcaldia').empty().append("<option value=''>*Selecciona la Colonia</option> ");

                if (data.data.length > 1) {

                    $('#desc_estado').val(data.data[0].cat_municipio.cat_estado.desc_estado);
                    $('#desc_municipio').val(data.data[0].cat_municipio.desc_municipio);

                    for (let i = 0; i < data.data.length; i++) {
                        $('#alcaldia').append('<option value="' + data.data[i].id_colonia + '"' + '>' + data.data[i].desc_colonia + '</option>');
                    }
                } else if (data.data.length === 1) {
                    $('#desc_estado').val(data.data[0].cat_municipio.cat_estado.desc_estado);
                    $('#desc_municipio').val(data.data[0].cat_municipio.desc_municipio);
                    $('#alcaldia').append('<option value="' + data.data[0].id_colonia + '"' + '>' + data.data[0].desc_colonia + '</option>');

                } else {
                    toastr['error']("El Código Postal ingresado no fue encontrado", "Aviso");
                }
            },
            error: function (e) {
                toastr["warning"]("Error al encontrar el Código Postal ingresado : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    } else {
        toastr['error']("El Código Postal ingresado es incorrecto", "Aviso");
    }
}


/*=============================================
 Colonias 
 =============================================*/
function colonia(id_colonia) {
    $.ajax({
        type: "GET",
        url: "personas/buscarColonia/" + id_colonia,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            $('#desc_estado').val(data.data.cat_municipio.cat_estado.desc_estado);
            $('#desc_municipio').val(data.data.cat_municipio.desc_municipio);
            $('#alcaldia').append('<option value="' + data.data.id_colonia + '"' + '>' + data.data.desc_colonia + '</option>');
            $('#cod_postal').val(data.data.cod_postal);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar la información de la Colonia : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 Valida si direccion esta null y carga datos
 =============================================*/
$("#formDireccionPersona").submit(function (e) {
    event.preventDefault();
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_persona = urlParams.get('id_persona');

    var calle_valor = $("#calle_valor").val();
    var num_exterior = $("#num_exterior").val();
    var num_interior = $("#num_interior").val();
    var colonia_id = $("#alcaldia").val();
    var id_direccion = $("#id_Direccion").val();

    if ($.trim(calle_valor) === "") {
        return false;
    }
  
    if ($.trim(colonia_id) === "") {
        return false;
    }

    var datos = {"calle": calle_valor, "num_exterior": num_exterior, "num_interior": num_interior, "colonia_id": colonia_id, "persona_id": id_persona};

    var direccionURL = "";
    if (id_direccion === "" || id_direccion === null) {
        direccionURL = "personas/guardarDireccionPersona";
    } else if (id_direccion !== "") {
        direccionURL = "personas/editarPersonaDireccion/" + id_persona;
    }

    $.ajax({
        type: "POST",
        url: direccionURL,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            cargaDatosDireccion(id_persona);
            toastr['success'](data.data, "Aviso");
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

function verCandidatos() {
    window.location.href = 'candidatos';
}

function verTrabajadores() {
    window.location.href = 'trabajadoresAdmin';
}