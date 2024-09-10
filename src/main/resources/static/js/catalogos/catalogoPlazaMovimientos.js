/* global data, NaN, Document */
/*Cargar DOM antes de JS*/
document.addEventListener('DOMContentLoaded', () => {
    $tablaCatalogoPlazaMovimientos;

});

/*===================================================
 Ejecutar Tabla
 ======================================================*/
$tablaCatalogoPlazaMovimientos = $('#noteTable').DataTable({
    "ajax": {
        url: 'catalogos/listarDatosPlazaMovimientos',
        method: 'GET',
        dataSrc: ''
    },
    responsive: true,
    bProcessing: true,
    select: true,
    "language": {
        "processing": "Procesando espera...",
        "sProcessing": "Procesando...",
        "sLengthMenu": "Mostrar _MENU_ Movimientos",
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
        {data: "clave_movimiento"},
        {data: "descripcion"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="funcionEditar(' + r.id_plaza_movimientos + ')" > ' + '<span class="fa fa-edit"> </span> Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Cambiar Activo/inactivo", width: 150, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_plaza_movimientos + '  class="btn btn-outline-success" class="btn btn-outline-info"onclick="inhabilitar(' + r.id_plaza_movimientos + ')"> ' + '<span class="fa fa-minus-circle"></span>Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_plaza_movimientos + ' class="btn btn-outline-danger" onclick="activar(' + r.id_plaza_movimientos + ')"> ' + '<span class="fa fa-minus-circle"></span>Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*================================================
 AGREGAR Plaza Movimiento
 ===================================================*/
//Limpieza de agrega
$('#agregarPlazaMovimientosModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#descripcion_agregar").val("");
   $("#clave_movimiento_agregar").val("");
}); 
$("#agregarPlazaMovimientosModal").submit(function (e) {
    event.preventDefault();
    var descripcion = $("#descripcion_agregar").val();
    var clave_movimiento = $("#clave_movimiento_agregar").val();
    var datos = {"descripcion": descripcion, "clave_movimiento": clave_movimiento};
    $.ajax({
        type: "POST",
        url: "catalogos/guardarPlazaMovimientos",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $("#agregarPlazaMovimientosModal").modal("hide");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $tablaCatalogoPlazaMovimientos.ajax.reload(null, false);

        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=============================================
 ACTIVAR O DESACTIVAR Plaza Movimientos
 =============================================*/

function inhabilitar(id_plaza_movimientos) {
    $('#modalStatus').on('hidden.bs.modal', function () {
        id_plaza_movimientos = null;
    }); 
    $("#modalStatus").modal("toggle");
    $("#botonDeshabilitar").click(function (e) {
        if (id_plaza_movimientos !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estatusPlazaMovimientos/" + id_plaza_movimientos + "/" + 0,
                data: "id_plaza_movimientos=" + id_plaza_movimientos, "estatus": 0,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatus").modal('hide');
                        toastr['warning']("El movimiento ha sido inhabilitado", "Aviso");
                        // $tablaUsuarios.ajax.reload();

                        $('#noteTable').DataTable().ajax.reload();
                        id_plaza_movimientos = null;
                    }
                }
            });
        }
    });
}

function activar(id_plaza_movimientos) {
    $('#modalStatusActivar').on('hidden.bs.modal', function () {
        id_plaza_movimientos = null;
    }); 
    $("#modalStatusActivar").modal("toggle");
    $("#botonCambiar").click(function (e) {
        if (id_plaza_movimientos !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estatusPlazaMovimientos/" + id_plaza_movimientos + "/" + 1,
                data: "id_plaza_movimientos=" + id_plaza_movimientos, "estatus": 1,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatusActivar").modal('hide');
                        toastr['success']("El movimiento ha sido habilitado", "Aviso");
                        // $tablaUsuarios.ajax.reload();
                        $('#noteTable').DataTable().ajax.reload();
                        id_plaza_movimientos = null;
                    }
                }
            });
        }
    });
}
/*=============================================
 EDITAR PLAZA MOVIMIENTOS
 =============================================*/
function funcionEditar(id_plaza_movimientos) {
    $("#editarPlazaMovimientosModal").modal("toggle");
    document.getElementById("idPlazaMovimientos").value = id_plaza_movimientos;
    $.ajax({
        type: "GET",
        url: "catalogos/buscarPlazaMovimientos/" + id_plaza_movimientos,
        dataType: 'json',
        success: function (data) {
            $('#descripcion_edita').val(data.descripcion);
            $('#clave_movimiento_edita').val(data.clave_movimiento);
        },
        error: function (e) {
            alert(e);
        }
    });
}

function actualizar_Datos(id_plaza_movimientos) {
    $('#editarPlazaMovimientosModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_plaza_movimientos = null;
    }); 
    $("#editarPlazaMovimientosModal").submit(function (e) {
        event.preventDefault();
        var descripcion_edita = $("#descripcion_edita").val();
        var clave_movimiento_edita = $("#clave_movimiento_edita").val();

        var datos = {"clave_movimiento": clave_movimiento_edita, "descripcion": descripcion_edita};
        $.ajax({
            type: "POST",
            url: "catalogos/actualizarPlazaMovimientos/" + id_plaza_movimientos,
            data: JSON.stringify(datos),
            contentType: "application/json",
            success: function (data) {
                $("#editarPlazaMovimientosModal").modal('hide');
                if (data.error === 0) {
                    toastr['success'](data.data, "Aviso");
                    $tablaCatalogoPlazaMovimientos.ajax.reload(null, false);
                }
                if (data.error !== 0) {
                    toastr['error'](data.data, "Aviso");
                }
            },
            error: function (e) {
                toastr['error']("No se actualizo el Movimientos", "Aviso");
            }
        });
    });
}

