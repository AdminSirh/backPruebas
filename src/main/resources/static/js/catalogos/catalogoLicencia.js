/* global data, NaN, Document */
/*Cargar DOM antes de JS*/
document.addEventListener('DOMContentLoaded', () => {
    $tablaCatalogoLicencias;
    tabs();
});

/*===================================================
 Ejecutar Tabla
 ======================================================*/
$tablaCatalogoLicencias = $('#noteTable').DataTable({
    "ajax": {
        url: 'catalogos/listarDatosLicencia',
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
        {data: "tipo_licencia"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="funcionEditar(' + r.id_licencia + ')" > ' + '<span class="fa fa-edit"> </span> Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Cambiar Activo/inactivo", width: 150, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_licencia + '  class="btn btn-outline-success" class="btn btn-outline-info"onclick="inhabilitar(' + r.id_licencia + ')"> ' + '<span class="fa fa-minus-circle"></span>Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_licencia + ' class="btn btn-outline-danger" onclick="activar(' + r.id_licencia + ')"> ' + '<span class="fa fa-minus-circle"></span>Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*================================================
 AGREGAR LICENCIAS
 ===================================================*/
//Limpieza de agrega
$('#agregarLicenciaModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#tipo_licencia_agregar").val("");
}); 

$("#agregarLicenciaModal").submit(function (e) {
    event.preventDefault();
    var tipo_licencia = $("#tipo_licencia_agregar").val();

    if ($.trim(tipo_licencia) === "") {
        return false;
    }

    var datos = {"tipo_licencia": tipo_licencia};

    $.ajax({
        type: "POST",
        url: "catalogos/GuardarLicencia",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $("#agregarLicenciaModal").modal("hide");
            $("#tipo_licencia_agregar").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $tablaCatalogoLicencias.ajax.reload(null, false);
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=============================================
 ACTIVAR O DESACTIVAR LICENCIAS
 =============================================*/

function inhabilitar(id_licencia) {
    $('#modalStatus').on('hidden.bs.modal', function () {
        id_licencia = null;
    }); 
    $("#modalStatus").modal("toggle");
    $("#botonDeshabilitar").click(function (e) {
        if (id_licencia !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estadoLicencia/" + id_licencia + "/" + 0,
                data: "id_licencia=" + id_licencia, "estatus": 0,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatus").modal('hide');
                        toastr['warning']("La Licencia ha sido inhabilitada", "Aviso");

                        $('#noteTable').DataTable().ajax.reload();
                        id_licencia = null;
                    }
                }
            });
        }
    });
}

function activar(id_licencia) {
    $('#modalStatusActivar').on('hidden.bs.modal', function () {
        id_licencia = null;
    }); 
    $("#modalStatusActivar").modal("toggle");
    $("#botonCambiar").click(function (e) {
        if (id_licencia !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estadoLicencia/" + id_licencia + "/" + 1,
                data: "id_licencia=" + id_licencia, "estatus": 1,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatusActivar").modal('hide');
                        toastr['success']("La Licencia ha sido habilitada", "Aviso");
                        // $tablaUsuarios.ajax.reload();
                        $('#noteTable').DataTable().ajax.reload();
                        id_licencia = null;
                    }
                }
            });
        }
    });
}

/*=============================================
 EDITAR LICENCIAS
 =============================================*/
function funcionEditar(id_licencia) {
    $("#editarLicenciaModal").modal("toggle");
    document.getElementById("idLicencia").value = id_licencia;
    $.ajax({
        type: "GET",
        url: "catalogos/buscarLicencia/" + id_licencia,
        dataType: 'json',
        success: function (data) {
            $('#tipo_licencia_edita').val(data.tipo_licencia);
        },
        error: function (e) {
            alert(e);
        }
    });
}

function actualizar_Datos(id_licencia) {
    $('#editarLicenciaModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_licencia = null;
    }); 
    $("#editarLicenciaModal").submit(function (e) {
        event.preventDefault();
        var tipo_licencia_edita = $("#tipo_licencia_edita").val();
        var datos = {"tipo_licencia": tipo_licencia_edita};
        if (id_licencia !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/actualizarLicencia/" + id_licencia,
                data: JSON.stringify(datos),
                contentType: "application/json",
                success: function (data) {
                    $("#editarLicenciaModal").modal('hide');
                    if (data.error === 0) {
                        toastr['success']("Licencia actualizada");
                        $tablaCatalogoLicencias.ajax.reload(null, false);
                    }
                    if (data.error !== 0) {
                        toastr['error'](data.data, "Aviso");
                    }
                },
                error: function (e) {
                    toastr['error']("No se actualizó la Licencia", "Aviso");
                }
            });
    }
    });
}