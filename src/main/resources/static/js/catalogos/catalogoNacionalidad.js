/* global data, NaN, Document */
/*Cargar DOM antes de JS*/
document.addEventListener('DOMContentLoaded', () => {
    $tablaCatalogoNacionalidad;
    tabs();
});

/*===================================================
 Ejecutar Tabla
 ======================================================*/
$tablaCatalogoNacionalidad = $('#noteTable').DataTable({
    "ajax": {
        url: 'catalogos/listarDatosNacionalidad',
        method: 'GET',
        dataSrc: ''
    },
    responsive: true,
    bProcessing: true,
    select: true,
    "language": {
        "processing": "Procesando espera...",
        "sProcessing": "Procesando...",
        "sLengthMenu": "Mostrar _MENU_ nacionalidad",
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
        {data: "desc_nacionalidad"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="nacionalidadCargaDatosForm(' + r.id_nacionalidad + ')" > ' + '<span class="fa fa-edit"> </span> Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Cambiar Activo/inactivo", width: 150, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_nacionalidad + '  class="btn btn-outline-success" class="btn btn-outline-info"onclick="inhabilitar(' + r.id_nacionalidad + ')"> ' + '<span class="fa fa-minus-circle"></span>Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_nacionalidad + ' class="btn btn-outline-danger" onclick="activar(' + r.id_nacionalidad + ')"> ' + '<span class="fa fa-minus-circle"></span>Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*================================================
 AGREGAR NACIONALIDADES
 ===================================================*/
//Limpieza de agrega
$('#agregarNacionalidadModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#desc_nacionalidad_agregar").val("");
}); 

$("#agregarNacionalidadModal").submit(function (e) {
    event.preventDefault();
    var desc_nacionalidad = $("#desc_nacionalidad_agregar").val();
    var datos = {"desc_nacionalidad": desc_nacionalidad};

    $.ajax({
        type: "POST",
        url: "catalogos/guardarNacionalidad",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $("#agregarNacionalidadModal").modal("hide");
            $("#desc_nacionalidad_agregar").val("");
            toastr['success'](data.data, "Aviso");
            $tablaCatalogoNacionalidad.ajax.reload(null, false);

        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
});

/*=============================================
 ACTIVAR O DESACTIVAR LICENCIAS
 =============================================*/

function inhabilitar(id_nacionalidad) {
    $('#modalStatus').on('hidden.bs.modal', function () {
        id_nacionalidad = null;
    }); 
    $("#modalStatus").modal("toggle");
    $("#botonDeshabilitar").click(function (e) {
        if (id_nacionalidad !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estadoNacionalidad/" + id_nacionalidad + "/" + 0,
                data: "id_nacionalidad=" + id_nacionalidad, "estatus": 0,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatus").modal('hide');
                        toastr['warning']("La Nacionalidad ha sido inhabilitada", "Aviso");
                        // $tablaUsuarios.ajax.reload();

                        $('#noteTable').DataTable().ajax.reload();
                        id_nacionalidad = null;
                    }
                }
            });
        }
    });
}

function activar(id_nacionalidad) {
    $('#modalStatusActivar').on('hidden.bs.modal', function () {
        id_nacionalidad = null;
    }); 
    $("#modalStatusActivar").modal("toggle");
    $("#botonCambiar").click(function (e) {
        if (id_nacionalidad !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estadoNacionalidad/" + id_nacionalidad + "/" + 1,
                data: "id_nacionalidad=" + id_nacionalidad, "estatus": 1,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatusActivar").modal('hide');
                        toastr['success']("La Nacionalidad ha sido habilitada", "Aviso");
                        // $tablaUsuarios.ajax.reload();
                        $('#noteTable').DataTable().ajax.reload();
                        id_nacionalidad = null;
                    }
                }
            });
        }
    });
}

/*=============================================
 EDITAR LICENCIAS
 =============================================*/
function nacionalidadCargaDatosForm(id_nacionalidad) {
    $("#editarNacionalidadModal").modal("toggle");
    document.getElementById("idNacionalidad").value = id_nacionalidad;
    $.ajax({
        type: "GET",
        url: "catalogos/buscarNacionalidad/" + id_nacionalidad,
        dataType: 'json',
        success: function (data) {
            $('#desc_nacionalidad_edita').val(data.desc_nacionalidad);
        },
        error: function (e) {
            alert(e);
        }
    });
}

function actualizar_Datos(id_nacionalidad) {
    $('#editarNacionalidadModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_nacionalidad = null;
    }); 
    $("#editarNacionalidadModal").submit(function (e) {
        event.preventDefault();
        var desc_nacionalidad_edita = $("#desc_nacionalidad_edita").val();
        var datos = {"desc_nacionalidad": desc_nacionalidad_edita};
        if (id_nacionalidad !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/actualizarNacionalidad/" + id_nacionalidad,
                data: JSON.stringify(datos),
                contentType: "application/json",
                success: function (data) {
                    $("#editarNacionalidadModal").modal('hide');
                    if (data.error === 0) {
                        toastr['success'](data.data, "Aviso");
                        $tablaCatalogoNacionalidad.ajax.reload(null, false);
                    }
                    if (data.error !== 0) {
                        toastr['error'](data.data, "Aviso");
                    }
                },
                error: function (e) {
                    toastr['error']("No se actualizo la Nacionalidad", "Aviso");
                }
            });
    }
    });
}


