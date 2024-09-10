/* global data, NaN, Document */
/*Cargar DOM antes de JS*/
document.addEventListener('DOMContentLoaded', () => {
    $tablaCatalogoTipoBeneficiario;
    tabs();
});

/*===================================================
 Ejecutar Tabla
 ======================================================*/
$tablaCatalogoTipoBeneficiario = $('#noteTable').DataTable({
    "ajax": {
        url: 'catalogos/listarDatosTipoBeneficiario',
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
        {data: "desc_tipo_beneficiario"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="funcionEditar(' + r.id_tipo_beneficiario + ')" > ' + '<span class="fa fa-edit"> </span> Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Cambiar Activo/inactivo", width: 150, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_tipo_beneficiario + '  class="btn btn-outline-success" class="btn btn-outline-info"onclick="inhabilitar(' + r.id_tipo_beneficiario + ')"> ' + '<span class="fa fa-minus-circle"></span>Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_tipo_beneficiario + ' class="btn btn-outline-danger" onclick="activar(' + r.id_tipo_beneficiario + ')"> ' + '<span class="fa fa-minus-circle"></span>Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*================================================
 AGREGAR TIPO BENEFICIARIO
 ===================================================*/
//Limpieza de agrega
$('#agregarTipoBeneficiarioModal').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    $("#desc_tipo_beneficiario_agregar").val("");
});
$("#agregarTipoBeneficiarioModal").submit(function (e) {
    event.preventDefault();
    var desc_tipo_beneficiario = $("#desc_tipo_beneficiario_agregar").val();

    var datos = {"desc_tipo_beneficiario": desc_tipo_beneficiario};
    $.ajax({
        type: "POST",
        url: "catalogos/guardarTipoBeneficiario",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $("#agregarTipoBeneficiarioModal").modal("hide");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $tablaCatalogoTipoBeneficiario.ajax.reload(null, false);

        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=============================================
 ACTIVAR O DESACTIVAR TIPO BENEFICIARIO
 =============================================*/

function inhabilitar(id_tipo_beneficiario) {
    $('#modalStatus').on('hidden.bs.modal', function () {
        id_tipo_beneficiario = null;
    });
    $("#modalStatus").modal("toggle");
    $("#botonDeshabilitar").click(function (e) {
        if (id_tipo_beneficiario !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estatusTipoBeneficiario/" + id_tipo_beneficiario + "/" + 0,
                data: "id_tipo_beneficiario=" + id_tipo_beneficiario, "estatus": 0,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatus").modal('hide');
                        toastr['warning']("El Tipo de Beneficiario ha sido inhabilitado", "Aviso");
                        // $tablaUsuarios.ajax.reload();

                        $('#noteTable').DataTable().ajax.reload();
                        id_tipo_beneficiario = null;
                    }
                }
            });
        }
    });
}

function activar(id_tipo_beneficiario) {
    $('#modalStatusActivar').on('hidden.bs.modal', function () {
        id_tipo_beneficiario = null;
    });
    $("#modalStatusActivar").modal("toggle");
    $("#botonCambiar").click(function (e) {
        if (id_tipo_beneficiario !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estatusTipoBeneficiario/" + id_tipo_beneficiario + "/" + 1,
                data: "id_tipo_beneficiario=" + id_tipo_beneficiario, "estatus": 1,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusActivar").modal('hide');
                        toastr['success']("El Tipo de Beneficiario ha sido habilitado", "Aviso");
                        $('#noteTable').DataTable().ajax.reload();
                        id_tipo_beneficiario = null;
                    }
                }
            });
        }
    });
}

/*=============================================
 EDITAR TIPO BENEFICIARIO
 =============================================*/
function funcionEditar(id_tipo_beneficiario) {
    $("#editarTipoBeneficiarioModal").modal("toggle");
    document.getElementById("idTipoBeneficiario").value = id_tipo_beneficiario;
    $.ajax({
        type: "GET",
        url: "catalogos/buscarTipoBeneficiario/" + id_tipo_beneficiario,
        dataType: 'json',
        success: function (data) {
            $('#desc_tipo_beneficiario_edita').val(data.desc_tipo_beneficiario);
        },
        error: function (e) {
            alert(e);
        }
    });
}

function actualizar_Datos(id_tipo_beneficiario) {
    $('#editarTipoBeneficiarioModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
        //Invalida el id para evitar inconsistencias
        id_tipo_beneficiario = null;
    });
    $("#editarTipoBeneficiarioModal").submit(function (e) {
        event.preventDefault();
        var desc_tipo_beneficiario_edita = $("#desc_tipo_beneficiario_edita").val();

        var datos = {"desc_tipo_beneficiario": desc_tipo_beneficiario_edita};
        if (id_tipo_beneficiario !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/actualizarTipoBeneficiario/" + id_tipo_beneficiario,
                data: JSON.stringify(datos),
                contentType: "application/json",
                success: function (data) {
                    $("#editarTipoBeneficiarioModal").modal('hide');
                    if (data.error === 0) {
                        toastr['success'](data.data, "Aviso");
                        $('#noteTable').DataTable().ajax.reload();
                    }
                },
                error: function (e) {
                    toastr['error']("No se actualizo el tipo de beneficiario", "Aviso");
                }
            });
        }
    });
}

