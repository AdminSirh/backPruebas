$(document).ready(function () {
    cmbTipoContrato();
});

//******************************************************************************
//                      CATÁLOGO DIAS FESTIVOS         
//******************************************************************************
const cmbTipoContrato = (id) => {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbTipoNomina').empty().append("<option value=''>* </option> ");
                $('#cmbTipoNominaEdita').empty().append("<option value=''>* </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_tipo_nomina === id) ? vselected = "selected" : vselected = "----";
                        $('#cmbTipoNomina').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                        $('#cmbTipoNominaEdita').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};
/*=================Carga de tabla Días Festivos============================ */
tabla_DiasFestivos = $('#tabla_DiasFestivos').dataTable({
    "ajax": {
        url: "catalogos/listarDiasFestivos",
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
        {data: "id_festivos"},
        {data: "descripcion"},
        {data: "fecha"},
        {data: "cat_Tipo_Nomina.desc_nomina"},
        {data: "", sTitle: "Contrato Colectivo", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.contrato_colectivo === 1) {
                    he = '<i class="fa fa-check" aria-hidden="true"></i>';
                } else if (r.contrato_colectivo === 0) {
                    he = '<i class="fa fa-times" aria-hidden="true"></i>';
                } else {
                    he = '<label>';
                }
                return he;
            }
        },
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarDiaFestModal" onclick="editarDiaFestivo(' + r.id_festivos + '); diaFestivoCargaDatosForm(' + r.id_festivos + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_festivos + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusDiaFestivo(' + r.id_festivos + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_festivos + ' class="btn btn-outline-danger" onclick="estatusDiaFestivo(' + r.id_festivos + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*=================Añadir elementos a tabla Días Festivos============================ */
//Limpieza de agrega
$('#agregarDiaFestivoModal').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    $("#desc_DiaFest").val("");
});

$("#agregarDiaFestivoModal").submit(function (e) {
    event.preventDefault();
    var tipoNomina = $("#cmbTipoNomina").val();
    var descripcion = $("#desc_DiaFest").val();
    var fechaFestivo = $("#fechaFestivo").val();
    var chkContratoColectivo = $("#chkContratoColectivo").is(":checked") ? $("#chkContratoColectivo").val() : 0;

    var datos = {"cat_Tipo_Nomina":
                {"id_tipo_nomina": tipoNomina},
        "descripcion": descripcion,
        "fecha": fechaFestivo,
        "contrato_colectivo": chkContratoColectivo};

    $.ajax({
        type: "POST",
        url: "catalogos/GuardarDiaFestivo",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarDiaFestivoModal").modal('hide');
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_DiasFestivos').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

$('#modificarDiaFestModal').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation was-validated')[0].classList.remove("was-validated");
    //Invalida el id para evitar inconsistencias
    id_festivos = null;
});

/*=================Edición de Días Festivos============================ */
function editarDiaFestivo(id_festivos) {
    $("#modificarDiaFestModal").submit(function (e) {
        event.preventDefault();
        var descripcion = $("#desc_DiaFest_Edi").val();
        var tipoNomina = $("#cmbTipoNominaEdita").val();
        var fechaFestivo = $("#fechaFestivoEdita").val();
        var chkContratoColectivo = $("#chkContratoColectivoEdita").is(":checked") ? $("#chkContratoColectivoEdita").val() : 0;
        var datos = {"cat_Tipo_Nomina":
                    {"id_tipo_nomina": tipoNomina},
            "descripcion": descripcion,
            "fecha": fechaFestivo,
            "contrato_colectivo": chkContratoColectivo};
        var direccionURL = "";
        direccionURL = "catalogos/editarDiaFestivo/" + id_festivos;

        if (id_festivos !== null) {
            $.ajax({
                type: "POST",
                url: direccionURL,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(datos),
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información para modificar", "Aviso", {progressBar: true, closeButton: true});
                    }
                    $("#modificarDiaFestModal").modal('hide');
                    $('#tabla_DiasFestivos').DataTable().ajax.reload();
                    toastr['success'](data.data, "Se modificó con éxito");
                    id_festivos = null;
                },
                error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
                }
            });
        }
    });
}

//Función para trae el valor de la base de datos para edición sobre el mismo
function diaFestivoCargaDatosForm(id_festivos) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarDiaFestivo/" + id_festivos,
        dataType: 'json',
        success: function (data) {
            (data.data.descripcion === null) ? $('#desc_DiaFest_Edi').val("") : $('#desc_DiaFest_Edi').val(data.data.descripcion);
            $('#chkContratoColectivoEdita').prop('checked', data.data.contrato_colectivo === 1 ? true : false);
            $('#cmbTipoNominaEdita').val(data.data.cat_Tipo_Nomina.id_tipo_nomina);
            $('#fechaFestivoEdita').val(data.data.fecha);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusDiaFestivo(id_festivos, estatus) {
    if (estatus === 1) {
        $("#modalStatusDiaFestivo").modal("toggle");
        inhabilitarDiaFestivo(id_festivos, 0);
    } else if (estatus === 0) {
        $("#modalStatusDiaFestivoActiva").modal("toggle");
        activarDiaFestivo(id_festivos, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}
/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarDiaFestivo(id_festivos, estatus) {
    $('#modalStatusDiaFestivo').on('hidden.bs.modal', function () {
        id_festivos = null;
    });

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_festivos !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusDiaFestivo/" + id_festivos + "/" + estatus,
                data: "id_horario=" + id_festivos, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusDiaFestivo").modal('hide');
                        toastr['warning']("Este Día Festivo ha sido inhabilitado", "Aviso");
                        $('#tabla_DiasFestivos').DataTable().ajax.reload();
                        id_festivos = null;
                    }
                }
            });
        }
    });
}

/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarDiaFestivo(id_festivos, estatus) {
    $('#modalStatusDiaFestivoActiva').on('hidden.bs.modal', function () {
        id_festivos = null;
    });

    $("#botonActivar").click(function (e) {
        if (id_festivos !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusDiaFestivo/" + id_festivos + "/" + estatus,
                data: "id_festivos=" + id_festivos, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusDiaFestivoActiva").modal('hide');
                        toastr['success']("Este Día Festivo ha sido activado", "Aviso");
                        $('#tabla_DiasFestivos').DataTable().ajax.reload();
                        id_festivos = null;
                    }
                }
            });
        }
    });
}
