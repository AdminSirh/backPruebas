/* global data, NaN, Document */
/*Cargar DOM antes de JS*/
document.addEventListener('DOMContentLoaded', () => {
    $tablaCatalogoSangre;
    tabs();
});

/*===================================================
 Ejecutar Tabla
 ======================================================*/
$tablaCatalogoSangre = $('#noteTable').DataTable({
    "ajax": {
        url: 'catalogos/listarDatosSangre',
        method: 'GET',
        dataSrc: ''
    },
    responsive: true,
    bProcessing: true,
    select: true,
    "language": {
        "processing": "Procesando espera...",
        "sProcessing": "Procesando...",
        "sLengthMenu": "Mostrar _MENU_ licencias",
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
        {data: "tipo_sangre"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="cargaSangreForm(' + r.id_sangre + ')" > ' + '<span class="fa fa-edit"> </span> Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Cambiar Activo/inactivo", width: 150, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_sangre + '  class="btn btn-outline-success" class="btn btn-outline-info"onclick="inhabilitar(' + r.id_sangre + ')"> ' + '<span class="fa fa-minus-circle"></span>Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_sangre + ' class="btn btn-outline-danger" onclick="activar(' + r.id_sangre + ')"> ' + '<span class="fa fa-minus-circle"></span>Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*================================================
 AGREGAR Tipo de Sangre
 ===================================================*/
//Limpieza de agrega
$('#agregarSangreModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#tipo_sangre_agregar").val("");
}); 
$("#agregarSangreModal").submit(function (e) {
    event.preventDefault();
    var tipo_sangre = $("#tipo_sangre_agregar").val();
    var datos = {"tipo_sangre": tipo_sangre};

    $.ajax({
        type: "POST",
        url: "catalogos/guardarDatosSangre",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $("#agregarSangreModal").modal("hide");
            toastr['success'](data.data, "Aviso");
            $tablaCatalogoSangre.ajax.reload(null, false);

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
    $("#formGuardarSangre")[0].reset();
});

/*=============================================
 ACTIVAR O DESACTIVAR Tipo Sanguineo
 =============================================*/

function inhabilitar(id_sangre) {
    $('#modalStatus').on('hidden.bs.modal', function () {
        id_sangre = null;
    }); 
    $("#modalStatus").modal("toggle");
    $("#botonDeshabilitar").click(function (e) {
        if (id_sangre !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estadoSangre/" + id_sangre + "/" + 0,
                data: "id_sangre=" + id_sangre, "estatus": 0,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatus").modal('hide');
                        toastr['warning']("El Tipo Sanguineo ha sido inhabilitado", "Aviso");
                        // $tablaUsuarios.ajax.reload();

                        $('#noteTable').DataTable().ajax.reload();
                        id_sangre = null;
                    }
                }
            });
        }
    });
}

function activar(id_sangre) {
    $('#modalStatusActivar').on('hidden.bs.modal', function () {
        id_sangre = null;
    }); 
    $("#modalStatusActivar").modal("toggle");
    $("#botonCambiar").click(function (e) {
        if (id_sangre !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estadoSangre/" + id_sangre + "/" + 1,
                data: "id_sangre=" + id_sangre, "estatus": 1,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatusActivar").modal('hide');
                        toastr['success']("El Tipo Sanguineo ha sido habilitado", "Aviso");
                        // $tablaUsuarios.ajax.reload();
                        $('#noteTable').DataTable().ajax.reload();
                        id_sangre = null;
                    }
                }
            });
        }
    });
}

/*=============================================
 EDITAR Tipo Sanguineo
 =============================================*/
function cargaSangreForm(id_sangre) {
    $("#editarSangreModal").modal("toggle");
    document.getElementById("idSangre").value = id_sangre;
    $.ajax({
        type: "GET",
        url: "catalogos/buscarSangre/" + id_sangre,
        dataType: 'json',
        success: function (data) {
            $('#tipo_sangre_edita').val(data.tipo_sangre);
        },
        error: function (e) {
            alert(e);
        }
    });
}

function actualizar_Datos(id_sangre) {
    $('#editarSangreModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_sangre = null;
    }); 
    $("#editarSangreModal").submit(function (e) {
        event.preventDefault();
        var tipo_sangre_edita = $("#tipo_sangre_edita").val();
        var datos = {"tipo_sangre": tipo_sangre_edita};
        $.ajax({
            type: "POST",
            url: "catalogos/actualizarDatosSangre/" + id_sangre,
            data: JSON.stringify(datos),
            contentType: "application/json",
            success: function (data) {
                $("#editarSangreModal").modal('hide');
                if (data.error === 0) {
                    toastr['success'](data.data, "Aviso");
                    $tablaCatalogoSangre.ajax.reload(null, false);
                }
                if (data.error !== 0) {
                    toastr['error'](data.data, "Aviso");
                }
            },
            error: function (e) {
                toastr['error']("No se actualizo el tipo Sanguineo", "Aviso");
            }
        });
    });
}







