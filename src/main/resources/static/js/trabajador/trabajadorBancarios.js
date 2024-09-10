document.addEventListener('DOMContentLoaded', () => {
    funcionEditar();
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');
    tabs(id_trabajador);
    bancos();

});

function tabs(id_trabajador) {
    $('#myTabTrabajador').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=trabajadorDatosGenerales('" + id_trabajador + "')>1.- DATOS DEL TRABAJADOR</a></li>&nbsp;" +
            "<li><a href='#tabs-1' class='btn btn-info'>2.- DATOS BANCARIOS</a></li>&nbsp;" +
            "<li class='btn btn-primary' onclick=trabajadorPuesto('" + id_trabajador + "')>3.- PLAZA</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=verValidaciones('" + id_trabajador + "')>4.- VALIDACIÓN</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=verBeneficiarios('" + id_trabajador + "')>5.- BENEFICIARIOS - SEGURO DE VIDA</li>&nbsp;");
}
function trabajadorDatosGenerales(id_trabajador) {
    window.location.href = 'trabajadorDatosGenerales?id_trabajador=' + id_trabajador;
}

function trabajadorJubilacion(id_trabajador) {
    window.location.href = 'trabajadorJubilacion?id_trabajador=' + id_trabajador;
}

function trabajadorPuesto(id_trabajador) {
    window.location.href = 'trabajadorPuesto?id_trabajador=' + id_trabajador;
}

function verTrabajadores() {
    window.location.href = 'trabajadoresAdmin';
}

function verValidaciones(id_trabajador) {
    window.location.href = 'trabajadorValidacion?id_trabajador=' + id_trabajador;
}

function verBeneficiarios(id_trabajador) {
    window.location.href = 'personaBeneficiarios?id_trabajador=' + id_trabajador;
}

//==================== LISTAR COMBO DE BANCOS===========================
function bancos(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarBancos",
        dataType: 'json',
        success: function (data) {

            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#bancosCmb').empty().append("<option value=''>* Bancos </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_banco === id) ? vselected = "selected" : vselected = " ";
                        $('#bancosCmb').append('<option value="' + data[x].id_banco + '"' + vselected + '>' + data[x].nombre_banco + '</option>');

                    }

                }

            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar el Banco: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 GUARDAR CUENTA BANCARIA
 =============================================*/

$("#formGuardarBancarios").submit(function (e) {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_trabajador = urlParams.get('id_trabajador');
    var id_cuenta_bancaria = $("#id_cuenta_bancaria").val();
    event.preventDefault();

    var clabe = $("#clabeCuentaBancaria").val();
    var bancos = $("#bancosCmb").val();

    if ($.trim(clabe) === "" || clabe.length < 18) {
        toastr['warning']("La CLABE debe contener 18 dígitos", "Advertencia");
        return false;
    }
    if ($.trim(bancos) === "") {
        toastr['warning']("Debe seleccionar un banco", "Advertencia");
        return false;
    }
    if ($.trim(id_trabajador) === "") {
        toastr['warning']("No se encontró el identificador del trabajador", "Advertencia");
        return false;
    }
    var datos = {"cuenta_bancaria": clabe, "banco_id": bancos, "trabajador_id": id_trabajador};

    var url_accion = "";
    if (id_cuenta_bancaria === "" || id_cuenta_bancaria === null) {

        url_accion = "trabajador/guardarCuentaBancaria";

    } else if (id_cuenta_bancaria !== "" || id_cuenta_bancaria !== null) {

        url_accion = "trabajador/editarCuentaTrabajador/" + id_trabajador;
    }
    $.ajax({
        type: "POST",
        url: url_accion,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            funcionEditar(id_trabajador);
            toastr['success'](data.data, "Se guardó correctamente la Información");
            event.preventDefault();
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
});
/*=============================================
 BUSCAR LA CUENTA POR EL ID TRABAJADOR
 =============================================*/
function funcionEditar(id) {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id = urlParams.get('id_trabajador');

    $.ajax({
        type: "GET",
        url: "trabajador/buscarCuentaidTrabajador/" + id,
        dataType: 'json',
        success: function (data) {
            $('#id_cuenta_bancaria').val(data.data.id_cuenta_bancaria);
            $('#clabeCuentaBancaria').val(data.data.cuenta_bancaria);
            bancos(data.data.banco_id);
        },
        error: function (e) {
            alert(e);
        }
    });
}
/*=============================================
 VALIDACIÓN DE TIPO NUMBER Y LONGITUD
 =============================================*/

function validacion(object) {
    if (object.value.length > object.maxLength)
        object.value = object.value.slice(0, object.maxLength);

}