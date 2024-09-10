/* global data, NaN, Document */
/*Cargar DOM antes de JS*/
document.addEventListener('DOMContentLoaded', () => {
    $tablaCatalogoInhabilitado;
    tabs();
});
/*===================================================
 Ejecutar Tabla
 ======================================================*/
$tablaCatalogoInhabilitado = $('#noteTable').DataTable({
    "ajax": {
        url: 'catalogos/listarDatosInhabilitado',
        method: 'GET',
        dataSrc: ''
    },
    responsive: true,
    bProcessing: true,
    select: true,
    "language": {
        "processing": "Procesando espera...",
        "sProcessing": "Procesando...",
        "sLengthMenu": "Mostrar _MENU_ inhabilitado",
        "sZeroRecords": "No se encontraron resultados",
        "sEmptyTable": " ",
        "sInfo": "_START_ al _END_ Total: _TOTAL_",
        "sInfoEmpty": " ",
        "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
        "sInfoPostFix": "",
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
        {data: "inhabilitado"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="funcionEditar(' + r.id_inhabilitado + ')" > ' + '<span class="fa fa-edit"> </span> Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Cambiar Activo/inactivo", width: 150, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_inhabilitado + '  class="btn btn-outline-success" class="btn btn-outline-info"onclick="inhabilitar(' + r.id_inhabilitado + ')"> ' + '<span class="fa fa-minus-circle"></span>Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_inhabilitado + ' class="btn btn-outline-danger" onclick="activar(' + r.id_inhabilitado + ')"> ' + '<span class="fa fa-minus-circle"></span>Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*================================================
 AGREGAR INHABILITADO
 ===================================================*/
//Limpieza de agrega
$('#agregarInhabilitadoModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#inhabilitado_agregar").val("");
}); 
$("#agregarInhabilitadoModal").submit(function (e) {
    event.preventDefault();
    var inhabilitado = $("#inhabilitado_agregar").val();
    var datos = {"inhabilitado": inhabilitado};

    $.ajax({
        type: "POST",
        url: "catalogos/guardarInhabilitado",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $("#agregarInhabilitadoModal").modal("hide");
            toastr['success'](data.data, "Aviso");
            $tablaCatalogoInhabilitado.ajax.reload();

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
    $("#formGuardarInhabilitado")[0].reset();
});

/*=============================================
 ACTIVAR O DESACTIVAR INHABILITADO
 =============================================*/

function inhabilitar(id_inhabilitado) {
    event.preventDefault();
    $('#modalStatus').on('hidden.bs.modal', function () {
        id_inhabilitado = null;
    }); 
    $("#modalStatus").modal("toggle");
    $("#botonDeshabilitar").click(function (e) {
        if (id_inhabilitado !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estadoInhabilitado/" + id_inhabilitado + "/" + 0,
                data: "id_inhabilitado=" + id_inhabilitado, "estatus": 0,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatus").modal('hide');
                        toastr['warning']("Catálogo ha sido inhabilitado", "Aviso");
                        // $tablaUsuarios.ajax.reload();

                        $('#noteTable').DataTable().ajax.reload();
                        id_inhabilitado = null;
                    }
                }
            });
        }
    });
}

function activar(id_inhabilitado) {
    $('#modalStatusActivar').on('hidden.bs.modal', function () {
        id_inhabilitado = null;
    }); 
    $("#modalStatusActivar").modal("toggle");
    $("#botonCambiar").click(function (e) {
        if (id_inhabilitado !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estadoInhabilitado/" + id_inhabilitado + "/" + 1,
                data: "id_inhabilitado=" + id_inhabilitado, "estatus": 1,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatusActivar").modal('hide');
                        toastr['success']("Catálogo ha sido habilitado", "Aviso");
                        // $tablaUsuarios.ajax.reload();
                        $('#noteTable').DataTable().ajax.reload();
                        id_inhabilitado = null;
                    }
                }
            });
        }
    });
}

/*=============================================
 EDITAR INHABILITADO
 =============================================*/
function funcionEditar(id_inhabilitado) {
    $("#editarInhabilitadoModal").modal("toggle");
    document.getElementById("idInhabilitado").value = id_inhabilitado;
    $.ajax({
        type: "GET",
        url: "catalogos/buscarInhabilitado/" + id_inhabilitado,
        dataType: 'json',
        success: function (data) {
            $('#inhabilitado_edita').val(data.inhabilitado);
        },
        error: function (e) {
            alert(e);
        }
    });
}

function actualizar_Datos(id_inhabilitado) {
    $("#editarInhabilitadoModal").submit(function (e) {
    event.preventDefault();
    var inhabilitado_edita = $("#inhabilitado_edita").val();
    var datos = {"inhabilitado": inhabilitado_edita};

    if (id_inhabilitado !== null) {
        $.ajax({
            type: "POST",
            url: "catalogos/actualizarInhabilitado/" + id_inhabilitado,
            data: JSON.stringify(datos),
            contentType: "application/json",
            success: function (data) {
                $("#editarInhabilitadoModal").modal('hide');
                $tablaCatalogoInhabilitado.ajax.reload();
                if (data.error === 0) {
                    toastr['success'](data.data, "Aviso");
                    id_inhabilitado = null;
                }
            },
            error: function (e) {
                toastr['error']("No se actualizo Inhabilitado. Verificar longitud de los caracteres (10)", "Aviso");
            }
        });
    }
    });
}



