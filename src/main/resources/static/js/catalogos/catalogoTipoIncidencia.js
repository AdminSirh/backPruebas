/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

//******************************************************************************
//                      CATÁLOGO TIPO INCIDENCIAS      
//******************************************************************************

/*=================Carga de tabla Tipo Incidencias============================ */
tabla_TipoIncidencias = $('#tabla_TipoIncidencias').dataTable({
    "ajax": {
        url: "catalogos/listarTipoIncidencias",
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
        {data: "id_tipo_incidencia"},
        {data: "descripcion"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarTipoIncidencia" onclick="editarTipoIncidencia(' + r.id_tipo_incidencia + '); tipoIncidenciaCargaDatosForm(' + r.id_tipo_incidencia + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_tipo_incidencia + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusTipoIncidencia(' + r.id_tipo_incidencia + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_tipo_incidencia + ' class="btn btn-outline-danger" onclick="estatusTipoIncidencia(' + r.id_tipo_incidencia + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        },
        {data: "", sTitle: "Sub Menu", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                he = '<a  class="btn btn-outline-info" data-toggle="modal" data-target="#sub_TipoIncidenciasModal" onclick="verTablaIncidencia(' + r.id_tipo_incidencia + ')" ><span class="fa fa-search"></span>Administrar</a></td>';
                return he;                             //onclick="vermenu(' + num + ')"

            }
        }
    ]
});


/*=================Añadir elementos a tabla Tipo de Incidencia============================ */
//Limpieza de agrega
$('#agregarTipoIncidenciaModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#desc_TipoIncidencia").val("");
}); 
$("#agregarTipoIncidenciaModal").submit(function (e) {
    event.preventDefault();
    var descripcion = $("#desc_TipoIncidencia").val();
    var datos = {"descripcion": descripcion};
    
    $.ajax({
        type: "POST",
        url: "catalogos/guardarTipoIncidencia",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarTipoIncidenciaModal").modal('hide');
            $("#desc_TipoIncidencia").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_TipoIncidencias').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=================Edición de Tipo de Incidencias============================ */
function editarTipoIncidencia(id_tipo_incidencia) {
    $('#modificarTipoIncidencia').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_tipo_incidencia = null;
    }); 
    $("#modificarTipoIncidencia").submit(function (e) {
        event.preventDefault();
        var descripcion = $("#desc_TipoIncidencia_Edi").val();
        var datos = {"descripcion": descripcion};
        var direccionURL = "";
        direccionURL = "/catalogos/editarTipoIncidencia/" + id_tipo_incidencia;

        if (id_tipo_incidencia !== null) {
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
                $("#modificarTipoIncidencia").modal('hide');
                $('#tabla_TipoIncidencias').DataTable().ajax.reload();
                toastr['success'](data.data, "Se modificó con éxito");
                id_tipo_incidencia = null;
            },
            error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    }
    });
}

//Función para trae el valor de la base de datos para edición sobre el mismo
function tipoIncidenciaCargaDatosForm(id_tipo_incidencia) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarTipoIncidencia/" + id_tipo_incidencia,
        dataType: 'json',
        success: function (data) {
            (data.data[0].descripcion === null) ? $('#desc_TipoIncidencia_Edi').val("") : $('#desc_TipoIncidencia_Edi').val(data.data[0].descripcion);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS DE TIPO IINCIDENCIA PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusTipoIncidencia(id_tipo_incidencia, estatus) {
    if (estatus === 1) {
        $("#modalStatusTipoIncidencia").modal("toggle");
        inhabilitarTipoIncidencia(id_tipo_incidencia, 0);
    } else if (estatus === 0) {
        $("#modalStatusTipoIncidenciaActiva").modal("toggle");
        activarTipoIncidencia(id_tipo_incidencia, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}
/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarTipoIncidencia(id_tipo_incidencia, estatus) {
    $('#modalStatusTipoIncidencia').on('hidden.bs.modal', function () {
        id_tipo_incidencia = null;
    }); 
    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_tipo_incidencia !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusTipoIncidencia/" + id_tipo_incidencia + "/" + estatus,
                data: "id_tipo_incidencia=" + id_tipo_incidencia, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusTipoIncidencia").modal('hide');
                        toastr['warning']("Este Tipo de Incidencia ha sido inhabilitado", "Aviso");
                        $('#tabla_TipoIncidencias').DataTable().ajax.reload();
                        id_tipo_incidencia = null;
                    }
                }
            });
        }
    });
}

/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarTipoIncidencia(id_tipo_incidencia, estatus) {
    $('#modalStatusTipoIncidenciaActiva').on('hidden.bs.modal', function () {
        id_tipo_incidencia = null;
    }); 

    $("#botonActivar").click(function (e) {
        if (id_tipo_incidencia !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusTipoIncidencia/" + id_tipo_incidencia + "/" + estatus,
                data: "id_tipo_incidencia=" + id_tipo_incidencia, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusTipoIncidenciaActiva").modal('hide');
                        toastr['success']("Este Tipo de Incidencia ha sido activado", "Aviso");
                        $('#tabla_TipoIncidencias').DataTable().ajax.reload();
                        id_tipo_incidencia = null;
                    }
                }
            });
        }
    });
}

/*=================FUNCIÓN MOSTRAR TABLA INCIDENCIA============================ */
//Nos envia a la pagina de Catalogo Incidencias para editar esa información donde id_tipo_incidencia === tipo_incidencia_id

function verTablaIncidencia(id_tipo_incidencia) {
    let nombre = encodeURIComponent('id_tipo_incidencia=' + id_tipo_incidencia);
    id = id_tipo_incidencia;
    window.location.href = 'catalogoIncidencias?' + nombre;
}
