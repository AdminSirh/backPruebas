document.addEventListener('DOMContentLoaded', () => {
   
});

/*=================Carga de tabla ubicación============================ */
tabla_Ubicacion = $('#tabla_Ubicacion').dataTable({
    "ajax": {
        url: "catalogos/listarUbicacion",
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
        {data: "id_ubicacion"},
        {data: "desc_ubicacion"},
        {data: "clave"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarUbicacionModal" onclick="EditarUbicacion(' + r.id_ubicacion + ') ;recuperaUbicacion(' + r.id_ubicacion + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_ubicacion + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusUbicacion(' + r.id_ubicacion + ',' + r.estatus + ');"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_ubicacion + ' class="btn btn-outline-danger" onclick="estatusUbicacion(' + r.id_ubicacion + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});



//=============Limpieza de formulario Agrega===================
$('#agregarUbicacionModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#desc_ubicacion").val("");
   $("#clave").val("");
}); 

$("#agregarUbicacionModal").submit(function (e) {
    event.preventDefault();
    var desc_ubicacion = $("#desc_ubicacion").val();
    var clave = $("#clave").val();

    var datos = {"desc_ubicacion":  desc_ubicacion, "clave":clave};
    $.ajax({
        type: "POST",
        url: "catalogos/guardarUbicacion",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarUbicacionModal").modal('hide');
            $("#desc_ubicacion").val("");
            $("#clave").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_Ubicacion').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=================Edición de Ubicacion============================ */
function EditarUbicacion(id_ubicacion) {
    $('#modificarUbicacionModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validació
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
        id_ubicacion = null;
    });
    
    $("#modificarUbicacionModal").submit(function (e) {
        event.preventDefault();
        var desc_ubicacion_edita = $("#desc_ubicacion_edita").val();
        var clave_edita = $("#clave_edita").val();

        var datos = {"desc_ubicacion":  desc_ubicacion_edita, "clave":clave_edita};
        
        if (id_ubicacion !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/editarUbicacion/" + id_ubicacion,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(datos),
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información para modificar", "Aviso", {progressBar: true, closeButton: true});
                    }
                    $("#modificarUbicacionModal").modal('hide');
                    $('#tabla_Ubicacion').DataTable().ajax.reload();
                    toastr['success']("Se modificó con éxito");

                    id_ubicacion = null;
                },
                error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
                }
            });
        }
    });
}

function recuperaUbicacion(id_ubicacion) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarUbicacion/" + id_ubicacion,
        dataType: 'json',
        success: function (data) {
            (data.data.desc_ubicacion === null) ? $('#desc_ubicacion_edita').val("") : $('#desc_ubicacion_edita').val(data.data.desc_ubicacion);
            (data.data.clave === null) ? $('#clave_edita').val("") : $('#clave_edita').val(data.data.clave);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusUbicacion(id_ubicacion, estatus) {
    if (estatus === 1) {
        $("#modalStatusUbicacionDesactiva").modal("toggle");
        inhabilitarUbicacion(id_ubicacion, 0);
    } else if (estatus === 0) {
        $("#modalStatusUbicacionActiva").modal("toggle");
        activarUbicacion(id_ubicacion, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}
/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarUbicacion(id_ubicacion, estatus) {
    $('#modalStatusUbicacionDesactiva').on('hidden.bs.modal', function () {
        id_ubicacion = null;
    });
    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_ubicacion !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusUbicacion/" + id_ubicacion + "/" + estatus,
                data: "id_ubicacion=" + id_ubicacion, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusUbicacionDesactiva").modal('hide');
                        toastr['warning']("Esta Ubicación ha sido inhabilitada", "Aviso");
                        $('#tabla_Ubicacion').DataTable().ajax.reload();
                        id_ubicacion = null;
                    }
                }
            });
        }
    });
}

/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarUbicacion(id_ubicacion, estatus) {
    $('#modalStatusUbicacionActiva').on('hidden.bs.modal', function () {
        id_ubicacion = null;
    }); 
    $("#botonActivarEstatus").click(function (e) {
        if (id_ubicacion !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusUbicacion/" + id_ubicacion + "/" + estatus,
                data: "id_ubicacion=" + id_ubicacion, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusUbicacionActiva").modal('hide');
                        toastr['success']("Esta Ubicación  ha sido activada", "Aviso");
                        $('#tabla_Ubicacion').DataTable().ajax.reload();
                        id_ubicacion = null;
                    }
                }
            });
        }
    });
}
