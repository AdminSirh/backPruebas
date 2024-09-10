/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

$('#colorPicker').val("#808080");
var id_tipo_incidencia;

document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_tipo_incidencia = urlParams.get('id_tipo_incidencia');
    incidenciasCargaDatosTabla(id_tipo_incidencia);
    tabs();
});

document.addEventListener('DOMContentLoaded', () => {
    tabs();
});
function tabs() {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li id=Tab1 class='btn btn-primary' onclick=catalogoTipoIncidencia()>Tipo de Incidencias</li>&nbsp;");
}
/*=============================================
 Función para regresar a los catálogos
 =============================================*/

function catalogoTipoIncidencia() {
    window.location.href = 'catalogoTipoIncidencia';
}

//******************************************************************************
//                      CATÁLOGO INCIDENCIAS       
//******************************************************************************

/*=================Carga de tabla Incidencias============================ */

function incidenciasCargaDatosTabla(id_tipo_incidencia) {

    tabla_Incidencias = $('#tabla_Incidencias').dataTable({
        "ajax": {
            //url: "catalogos/listarDatosIncidencias",
            url: "catalogos/buscarUnaIncidencia/" + id_tipo_incidencia,
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
            {data: "id_incidencia"},
            {data: "descripcion"},
            {data: "cat_tipo_incidencia.id_tipo_incidencia"},
            {data: "coa_id"},
            {data: "inicial_descripcion"},
            {data: "valor_binario"},
            {data: "inoperable"},
            {data: "estatus_kardex", sTitle: "Kardex", render: function (d, t, r) {
                    if (r.estatus_kardex === 1) {
                        he = '<button type="button" class="btn btn-outline-primary" disabled><span class="fa fa-check"></span></button>';
                        return he;
                    } else {

                        he = '<button type="button" class="btn btn-outline-danger" disabled><span class="fa fa-times"></span></button>';
                        return he;
                    }
                }},
            {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarIncidenciaModal" onclick="editarIncidencia(' + r.id_incidencia + '); incidenciaCargaDatosForm(' + r.id_incidencia + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                    return he;
                }
            },
            {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                    var he;
                    if (r.estatus === 1) {
                        he = '<button type="button" id="btndistrict"' + r.id_incidencia + '  class="btn btn-outline-info"class="btn btn-outline-info" onclick="estatusIncidencia(' + r.id_incidencia + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                    } else {
                        he = '<button type="button" id="btndistrict"' + r.id_incidencia + ' class="btn btn-outline-danger" onclick="estatusIncidencia(' + r.id_incidencia + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                    }
                    return he;
                }
            }
        ]
    });

}



/*=================Añadir elementos a tabla Incidencias============================ */

const btnAgregaIncidencia = document.querySelector("#btn_Agrega_Incidencias");

//Tipo de incidencia se coloca en input de manera automática para evitar errores al agregar
btnAgregaIncidencia.addEventListener("click", function (evento) {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_tipo_incidencia = urlParams.get('id_tipo_incidencia');
    $('#tipoIncidencia').val(id_tipo_incidencia);
});

//Limpieza de agrega
$('#agregarIncidenciaModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#descripcion").val("");
   $("#tipoIncidencia").val("");
   $("#coaID").val("");
   $("#descInicial").val("");
   $("#valorBinario").val("");
   $("#inoperable").val("");
}); 

$("#agregarIncidenciaModal").submit(function (e) {
    event.preventDefault();
    var descripcion = $('#descripcion').val();
    var tipo_incidencia_id = $('#tipoIncidencia').val();
    var coa_id = $('#coaID').val();
    var inicial_descripcion = $('#descInicial').val();
    var valor_binario = $('#valorBinario').val();
    var inoperable = $('#inoperable').val();
    var hexKardex = $('#colorPicker').val();

    var datos = {"descripcion": descripcion, "cat_tipo_incidencia": {"id_tipo_incidencia": tipo_incidencia_id}, "coa_id": coa_id, "inicial_descripcion": inicial_descripcion, "valor_binario": valor_binario, "inoperable": inoperable, "color_kardex": hexKardex};
    $.ajax({
        type: "POST",
        url: "catalogos/guardarIncidencia",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarIncidenciaModal").modal('hide');
            $('#tabla_Incidencias').DataTable().ajax.reload();
            toastr['success']("Se ha agregado corretcamente el elemento");
            $('#tabla_Incidencias').DataTable().ajax.reload();
            $("#descripcion").val("");
            $("#tipoIncidencia").val("");
            $('#coaID').val("");
            $("#descInicial").val("");
            $("#valorBinario").val("");
            $("#inoperable").val("");
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=================Edición de Incidencias============================ */
function editarIncidencia(id_incidencia) {
    $('#modificarIncidenciaModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        $('#FormModificarIncidencia').removeClass('was-validated');
        //Invalida el id para evitar inconsistencias
        id_incidencia = null;
    });
    $("#modificarIncidenciaModal").submit(function (e) {
        event.preventDefault();
        var descripcion = $('#descripcion_edi').val();
        var tipo_incidencia_id = $('#tipoIncidencia_edi').val();
        var coa_id = $('#coaID_edi').val();
        var inicial_descripcion = $('#descInicial_edi').val();
        var valor_binario = $('#valorBinario_edi').val();
        var inoperable = $('#inoperable_edi').val();
        var hexKardex = $('#colorPickerEdi').val();
        var datos = {"descripcion": descripcion, "cat_tipo_incidencia":{"id_tipo_incidencia": tipo_incidencia_id}, "coa_id": coa_id, "inicial_descripcion": inicial_descripcion, "valor_binario": valor_binario, "inoperable": inoperable, "color_kardex":hexKardex};
        var direccionURL = "";
        direccionURL = "catalogos/actualizarIncidencia/" + id_incidencia;

        if (id_incidencia !== null){
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
                $("#modificarIncidenciaModal").modal('hide');
                $('#tabla_Incidencias').DataTable().ajax.reload();
                toastr['success'](data.data, "Se modificó con éxito");
                //Reseteo de variable para evitar inconsistencias al momento de editar campos contunuos
                id_incidencia = null;
            },
            error: function (e) {
                toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    }
    });
}
//Función para trae el valor de la base de datos para edición sobre el mismo
function incidenciaCargaDatosForm(id_incidencia) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/encuentraUnElementoCatIncidencias/" + id_incidencia,
        dataType: 'json',
        success: function (data) {
            (data.data.id_tipo_incidencia === null) ? $('#descripcion_edi').val("") : $('#descripcion_edi').val(data.data.descripcion);
            (data.data.cat_tipo_incidencia.descripcion === null) ? $('#tipoIncidencia_edi').val("") : $('#tipoIncidencia_edi').val(data.data.cat_tipo_incidencia.id_tipo_incidencia);
            (data.data.coa_id === null) ? $('#coaID_edi').val("") : $('#coaID_edi').val(data.data.coa_id);
            (data.data.inicial_descripcion === null) ? $('#descInicial_edi').val("") : $('#descInicial_edi').val(data.data.inicial_descripcion);
            (data.data.valor_binario === null) ? $('#valorBinario_edi').val("") : $('#valorBinario_edi').val(data.data.valor_binario);
            (data.data.inoperable === null) ? $('#inoperable_edi').val("") : $('#inoperable_edi').val(data.data.inoperable);
            (data.data.color_kardex === null) ? $('#colorPickerEdi').val("#808080") : $('#colorPickerEdi').val(data.data.color_kardex);
            //Se deshabilita la selección de color si la incidencia no se refleja en kardex
            if (data.data.estatus_kardex === 0) {
                $('#colorPickerEdi').prop('disabled', true);
                toastr['warning']("Esta incidencia no se muestra en kardex, se deshabilitó el color", "Aviso");
            } else {
                $('#colorPickerEdi').prop('disabled', false);
            }
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS DE INCIDENCIA PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusIncidencia(id_incidencia, estatus) {
    if (estatus === 1) {
        $("#modalStatusIncidencia").modal("toggle");
        inhabilitarIncidencia(id_incidencia, 0);
    } else if (estatus === 0) {
        $("#modalStatusIncidenciaActiva").modal("toggle");
        activarIncidencia(id_incidencia, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}

/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarIncidencia(id_incidencia, estatus) {
    event.preventDefault();
    $('#modalStatusIncidencia').on('hidden.bs.modal', function () {
        id_incidencia = null;
    }); 

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_incidencia !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusIncidencias/" + id_incidencia + "/" + estatus,
                data: "id_incidencia=" + id_incidencia, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusIncidencia").modal('hide');
                        toastr['warning']("Este Incidencia ha sido inhabilitado", "Aviso");
                        $('#tabla_Incidencias').DataTable().ajax.reload();
                        id_incidencia = null;
                    }
                }
            });
        }
    });
}
/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarIncidencia(id_incidencia, estatus) {
    event.preventDefault();
    $('#modalStatusIncidenciaActiva').on('hidden.bs.modal', function () {
        id_incidencia = null;
    }); 

    $("#botonActivar").click(function (e) {
        if (id_incidencia !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusIncidencias/" + id_incidencia + "/" + estatus,
                data: "id_incidencia=" + id_incidencia, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusIncidenciaActiva").modal('hide');
                        toastr['success']("Esta Incidencia ha sido activada", "Aviso");
                        $('#tabla_Incidencias').DataTable().ajax.reload();
                        id_incidencia = null;
                    }
                }
            });
        }
    });
}

