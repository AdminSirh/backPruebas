/* global data, NaN, Document */
/*Cargar DOM antes de JS*/
document.addEventListener('DOMContentLoaded', () => {
    $tablaCatalogoSiNo;
    tabs();
});

/*===================================================
 Ejecutar Tabla
 ======================================================*/
$tablaCatalogoSiNo = $('#tabla_Si_No').DataTable({
    "ajax": {
        url: 'catalogos/listarDatosSi_No',
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
        {data: "descripcion"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="funcionEditar(' + r.id + ')" > ' + '<span class="fa fa-edit"> </span> Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Cambiar Activo/inactivo", width: 150, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id + '  class="btn btn-outline-success" class="btn btn-outline-info"onclick="inhabilitar(' + r.id + ')"> ' + '<span class="fa fa-minus-circle"></span>Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id + ' class="btn btn-outline-danger" onclick="activar(' + r.id + ')"> ' + '<span class="fa fa-minus-circle"></span>Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*================================================
 AGREGAR SI_NO
 ===================================================*/
//Limpieza de agrega
$('#agregarSiNoModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#descripcion_agregar").val("");
}); 

$("#agregarSiNoModal").submit(function (e) {
    event.preventDefault();
    var descripcion = $("#descripcion_agregar").val();
    var datos = {"descripcion": descripcion};
    $.ajax({
        type: "POST",
        url: "catalogos/guardarSiNo",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $("#agregarSiNoModal").modal("hide");
            $("#descripcion_agregar").val("");
            toastr['success'](data.data, "Aviso");
            $tablaCatalogoSiNo.ajax.reload(null, false);
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
});

/*=============================================
 ACTIVAR O DESACTIVAR SI_NO
 =============================================*/
function inhabilitar(id) {
    $('#modalStatus').on('hidden.bs.modal', function () {
        id = null;
    }); 
    $("#modalStatus").modal("toggle");
    $("#botonDeshabilitar").click(function (e) {
        if (id !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estadoSiNo/" + id + "/" + 0,
                data: "id=" + id, "estatus": 0,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatus").modal('hide');
                        toastr['warning']("El dato ha sido inhabilitado", "Aviso");
                        // $tablaUsuarios.ajax.reload();

                        $('#tabla_Si_No').DataTable().ajax.reload();
                        id = null;
                    }
                }
            });
        }
    });
}

function activar(id) {
    $('#modalStatusActivar').on('hidden.bs.modal', function () {
        id = null;
    }); 
    $("#modalStatusActivar").modal("toggle");
    $("#botonCambiar").click(function (e) {
        if (id !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estadoSiNo/" + id + "/" + 1,
                data: "id=" + id, "estatus": 1,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatusActivar").modal('hide');
                        toastr['success']("El dato ha sido habilitado", "Aviso");
                        // $tablaUsuarios.ajax.reload();
                        $('#tabla_Si_No').DataTable().ajax.reload();
                        id = null;
                    }
                }
            });
        }
    });
}

/*=============================================
 EDITAR SI_NO
 =============================================*/
function funcionEditar(id) {
    $("#editarSiNoModal").modal("toggle");
    document.getElementById("idDato").value = id;
    $.ajax({
        type: "GET",
        url: "catalogos/buscarSiNo/" + id,
        dataType: 'json',
        success: function (data) {
            $('#descripcion_edita').val(data.descripcion);
        },
        error: function (e) {
            alert(e);
        }
    });
}

function actualizar_Datos(id) {
    $('#editarSiNoModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id = null;
    }); 
    $("#editarSiNoModal").submit(function (e) {
        event.preventDefault();
        var descripcion_edita = $("#descripcion_edita").val();
        var datos = {"descripcion": descripcion_edita};
        $.ajax({
            type: "POST",
            url: "catalogos/actualizarSiNo/" + id,
            data: JSON.stringify(datos),
            contentType: "application/json",
            success: function (data) {
                $("#editarSiNoModal").modal('hide');
                if (data.error === 0) {
                    toastr['success'](data.data, "Aviso");
                    $tablaCatalogoSiNo.ajax.reload(null, false);
                };
            },
            error: function (e) {
                toastr['error']("No se actualizo el dato", "Aviso");
            }
        });
    });
}



