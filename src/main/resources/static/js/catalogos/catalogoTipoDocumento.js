/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

//******************************************************************************
//                      CATÁLOGO TIPO DOCUMENTO         
//******************************************************************************
/*=================Carga de tabla Tipo de Documento============================ */
tabla_Tipo_Documento = $('#tabla_Tipo_Documento').dataTable({
    "ajax": {
        url: "catalogos/listarDocumentos",
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
        {data: "id_tipo_documento"},
        {data: "documento"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarDocumentoModal" onclick="editarDocumento(' + r.id_tipo_documento + '); documentoCargaDatosForm(' + r.id_tipo_documento + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_tipo_documento + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusDocumento(' + r.id_tipo_documento + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_tipo_documento + ' class="btn btn-outline-danger" onclick="estatusDocumento(' + r.id_tipo_documento + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});
/*=================Añadir elementos a tabla Documentos============================ */
//Limpieza de agrega
$('#agregarDocumentoModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#desc_TipoDoc").val("");
}); 

$("#agregarDocumentoModal").submit(function (e) {
    event.preventDefault();
    var documento = $("#desc_TipoDoc").val();
    var datos = {"documento": documento};
    $.ajax({
        type: "POST",
        url: "catalogos/GuardarDocumeto",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarDocumentoModal").modal('hide');
            $("#desc_TipoDoc").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_Tipo_Documento').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo: " + e, " error", {progressBar: true, closeButton: true});
        }
    });

});

/*=================Edición de Documentos============================ */
function editarDocumento(id_tipo_documento) {
    $('#modificarDocumentoModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_tipo_documento = null;
    }); 
    $("#modificarDocumentoModal").submit(function (e) {
        event.preventDefault();
        var documento = $("#desc_TipoDoc_edi").val();
        var datos = {"documento": documento};
        var direccionURL = "";
        direccionURL = "catalogos/editarDocumento/" + id_tipo_documento;

        if (id_tipo_documento !== null) {
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
                    $("#modificarDocumentoModal").modal('hide');
                    $('#tabla_Tipo_Documento').DataTable().ajax.reload();
                    toastr['success'](data.data, "Se modificó con éxito");

                    //Reset de Variable para evitar duplicados al editar de manera continua.
                    id_tipo_documento = null;
                },
                error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
                }
            });
        }
    });
}

//Función para trae el valor de la base de datos para edición sobre el mismo
function documentoCargaDatosForm(id_tipo_documento) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarDocumento/" + id_tipo_documento,
        dataType: 'json',
        success: function (data) {
            (data.data.documento === null) ? $('#desc_TipoDoc_edi').val("") : $('#desc_TipoDoc_edi').val(data.data.documento);

        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS DE TIPO DOCUMENTO PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusDocumento(id_tipo_documento, estatus) {
    if (estatus === 1) {
        $("#modalStatusDocumento").modal("toggle");
        inhabilitarDocumento(id_tipo_documento, 0);
    } else if (estatus === 0) {
        $("#modalStatusDocumentoActiva").modal("toggle");
        activarDocumento(id_tipo_documento, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}

/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarDocumento(id_tipo_documento, estatus) {
    $('#modalStatusDocumento').on('hidden.bs.modal', function () {
        id_tipo_documento = null;
    }); 

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_tipo_documento !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusDocumentos/" + id_tipo_documento + "/" + estatus,
                data: "id_horario=" + id_tipo_documento, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusDocumento").modal('hide');
                        toastr['warning']("Este Documento ha sido inhabilitado", "Aviso");
                        $('#tabla_Tipo_Documento').DataTable().ajax.reload();
                        id_tipo_documento = null;
                    }
                }
            });
        }
    });
}
/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarDocumento(id_tipo_documento, estatus) {
    $('#modalStatusDocumentoActiva').on('hidden.bs.modal', function () {
        id_tipo_documento = null;
    }); 

    $("#botonActivar").click(function (e) {
        if (id_tipo_documento !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusDocumentos/" + id_tipo_documento + "/" + estatus,
                data: "id_horario=" + id_tipo_documento, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusDocumentoActiva").modal('hide');
                        toastr['success']("Este Documento ha sido activado", "Aviso");
                        $('#tabla_Tipo_Documento').DataTable().ajax.reload();
                        id_tipo_documento = null;
                    }
                }
            });
        }
    });
}
