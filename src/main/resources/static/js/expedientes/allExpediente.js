/* global $tablaExpediente, finalName, cadena3, slice */

document.addEventListener('DOMContentLoaded', () => {
    $tablaExpediente;
});

var expedientes = [];

$tablaExpediente = $('#noteTable').dataTable({
    "ajax": {
        url: "catalogos/listarDatosExpediente",
        method: 'GET',
        dataSrc: ''
    },
    responsive: true,
    bProcessing: true,
    select: true,
    "language": {
        'processing': 'Procesando espera...',
        "sProcessing": "Procesando...",
        "sLengthMenu": "Mostrar _MENU_ registros",
        "sZeroRecords": "No se encontraron resultados",
        "sEmptyTable": " ",
        "sInfo": " _START_ al _END_ Total: _TOTAL_",
        "sInfoEmpty": " ",
        "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
        "sInfoPostFix": "",
        //"searchPlaceholder": "Buscar",
        "search": "Buscar",
        "paginate": {
            "previous": 'Anterior',
            "next": 'Siguiente'
        },
        "paging": false,
        "bPaging": false,
        "scrollY": "300px",
        "sUrl": "",
        "sInfoThousands": ",",
        "sLoadingRecords": "Cargando...",
        "oAria": {
            "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
            "sSortDescending": ": Activar para ordenar la columna de manera descendente"
        }
    },
    columns: [
        {data: "id_expediente"},
        {data: "", sTitle: "Numero de expediente", width: 100, visible: true, render: function (d, t, r) {
                expedientes.push(Number(r.numero_expediente));
                var hi = '';
                if (r.asignado === 0) {
                    hi = '<button type="button" style="width: 150px" id="btndistrict"' + r.numero_expediente + '  class="btn btn-outline-danger" onclick="mostrarDatos(' + r.numero_expediente + ')"> ' + '<span class="fas fa-address-card"></span>' + r.numero_expediente + '</button>';
                } else {
                    hi = '<button type="button" style="width: 150px" id="btndistrict"' + r.numero_expediente + '  class="btn btn-outline-success" onclick="mostrarDatos(' + r.numero_expediente + ')"> ' + '<span class="fas fa-address-card"></span>' + r.numero_expediente + '</button>';

                }
                return hi;
            }
        },
        {data: "", sTitle: "Asignado", width: 100, visible: true, render: function (d, t, r) {
                var as = '';
                if (r.asignado === 1) {
                    as = 'Si';
                } else if (r.asignado === 0) {
                    as = 'No';
                }
                return as;
            }
        },

        {data: "", sTitle: "Estatus del No. Expediente", width: 100, visible: true, render: function (d, t, r) {
                expedientes.push(Number(r.numero_expediente));
                var he = '';

                if (r.estatus === 1 && r.asignado === 0) {
                    he = '<button type="button" style="width: 150px" id="btndistrict"' + r.id_expediente + '  class="btn btn-outline-success" onclick="estatusExpediente(' + r.id_expediente + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span>Desactivar</button>';
                } else if (r.estatus === 0) {
                    he = '<button type="button" style="width: 150px" id="btndistrict"' + r.id_expediente + ' class="btn btn-outline-danger" onclick="estatusExpediente(' + r.id_expediente + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span>Activar</button>';
                }
                return he;
            }
        },
        {data: "fecha_registro"}
    ]
});

//************************ CAMBIAR ESTATUS DEL EXPEDIENTE ***********************
function estatusExpediente(id_expediente, estatus) {
    if (estatus === 1) {
        $("#modalStatusDeshabilitar").modal("toggle");
        inhabilitarExp(id_expediente, 0);

    } else if (estatus === 0) {
        $("#modalStatusActivarExp").modal("toggle");
        activarExp(id_expediente, 1);
    }
}

//------------------------ Desactivar Expediente -----------------------------
function inhabilitarExp(id_expediente, estatus) {
    $("#btnDeshabilitarExpediente").click(function (e) {
        if (id_expediente !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estatusExpediente/" + id_expediente + "/" + estatus,
                data: "id_expediente=" + id_expediente, "estatus": estatus

            }).then(function () {
                $("#modalStatusDeshabilitar").modal('hide');
                toastr['success']("El expediente ha sido inhabilitado", "Aviso");
                id_expediente = null;
                $tablaExpediente.DataTable().ajax.reload();
            });
        }
    });
}

//---------------------------- Activar Expediente ------------------------------
function activarExp(id_expediente, estatus) {
    $("#botonActivar").click(function (e) {
        if (id_expediente !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estatusExpediente/" + id_expediente + "/" + estatus,
                data: "id_expediente=" + id_expediente, "estatus": estatus
            }).then(function () {
                $("#modalStatusActivarExp").modal('hide');
                toastr['success']("El expediente ha sido habilitado", "Aviso");
                id_expediente = null;
                $tablaExpediente.DataTable().ajax.reload();
            });
        }
    });
}

/*=============================================
 MOSTRAR DATOS DEL USUARIO
 =============================================*/
function getJustDate(date) {
    if (date) {
        let dateNew = date.split('T')[0];
        return dateNew;
    }
}
;

function formatString(str) {
    if (str) {
        let cadena = str.slice(1);
        let stringCadena = cadena.toLowerCase();
        let stringNew = str[0] + stringCadena;
        return stringNew;
    }
}
;

function nameFormat(str) {
    if (str) {
        let cadenas = str.split(" ");
        let name1 = cadenas.map(names => names[0].toUpperCase() + names.slice(1).toLowerCase());
        let name = name1.join(" ");
        return name;
    }
}
;

function mostrarDatos(numero_expediente) {
    $tablaExpediente.DataTable().ajax.reload();
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador_NumExpediente/" + numero_expediente,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información contacte a sistemas...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                if (data.data === null) {
                    toastr["warning"]("El expediente aún no ha sido asignado", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    $("#mostrarDatosModal").modal("toggle");
                    //$tablaExpediente.DataTable().ajax.reload();
                }
                let name = nameFormat(data.data.persona.nombre);
                let firstName = formatString(data.data.persona.apellido_paterno);
                let lastName = formatString(data.data.persona.apellido_materno);
                let finalName = name + " " + firstName + " " + lastName;
                let dateFecha_antiguedad = getJustDate(data.data.fecha_antiguedad);
                let dateFecha_ingreso = getJustDate(data.data.fecha_ingreso);
                let genero = formatString(data.data.persona.cat_genero.desc_genero);
                let edo_civil = formatString(data.data.persona.cat_estado_civil.desc_edo_civil);
                $('#nombre_edita').val(finalName);
                $('#username_edita').val(data.data.persona.curp);
                $('#fecha_nacimiento').val(data.data.persona.fecha_nacimiento);
                $('#genero').val(genero);
                $('#estado_civil').val(edo_civil);
                $('#curp').val(data.data.persona.curp);
                $('#fecha_captura').val(dateFecha_ingreso);
                $('#fecha_antiguedad').val(dateFecha_antiguedad);
            }
        }, error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
}

//***************************** GUARDAR EXPEDIENTE *****************************
$("#formGuardarExpediente").submit(function (e) {
    event.preventDefault();
    var numero_expediente = $("#numero_expediente").val();
    var asignado = $("#asignado").val();
    var estatus = $("#estatus").val();

    //Eliminar elementos repetidos
    let result = expedientes.filter((item, index) => {
        return expedientes.indexOf(item) === index;
    });

    // var contador = 0;
    // for (var i = 0; i < result.length; i++) { //Verificar que el número de expediente ingresado sea mayor a los anteriores
    // if (parseInt(numero_expediente) > parseInt(result[i])) {
    // contador += 1;
    //}
    //}

    //Validando dato
    if ($.trim(numero_expediente) === "") {
        toastr["warning"]("El número de expediente ingresado no es correcto o ya existe...", "Aviso", {progressBar: true, closeButton: true});
        return false;
    }

    var datos = {"numero_expediente": numero_expediente, "asignado": asignado, "estatus": estatus};

    $.ajax({
        type: "POST",
        url: "catalogos/guardarExpediente",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});

            } else {
                $("#agregarExpedienteModal").modal('hide');
                toastr['success'](data.data, "Aviso");
                $tablaExpediente.DataTable().ajax.reload();
                $("#formGuardarExpediente")[0].reset();
            }
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
});

/*=============================================
 LIMPIAR FORMULARIO
 =============================================*/
$("#btn_Cancelar").on("click", function (event) {
    $("div#alerta").removeClass("#invalid-feedback");
    $("div#alerta").html("");
    $("#formGuardarExpediente")[0].reset();
});