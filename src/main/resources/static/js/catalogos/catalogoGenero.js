/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
//******************************************************************************
//                      CATÁLOGO GÉNERO            
//******************************************************************************

/*=================Carga de tabla Género============================ */
tabla_Genero = $('#tabla_Genero').dataTable({
    "ajax": {
        url: "catalogos/listarDatosGenero",
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
        {data: "id_genero"},
        {data: "desc_genero"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarGeneroModal" onclick="EditarGenero(' + r.id_genero + '); generoCargaDatosForm(' + r.id_genero + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_genero + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusGenero(' + r.id_genero + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_genero + ' class="btn btn-outline-danger" onclick="estatusGenero(' + r.id_genero + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*=================Añadir elementos a tabla género============================ */
//Limpieza de agrega
$('#agregarGeneroModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#desc_Genero").val("");
}); 

$("#agregarGeneroModal").submit(function (e) {
    event.preventDefault();
    var desc_Genero = $("#desc_Genero").val();
    var datos = {"desc_genero": desc_Genero};
    $.ajax({
        type: "POST",
        url: "catalogos/GuardarGenero",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarGeneroModal").modal('hide');
            $("#desc_Genero").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_Genero').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=================Edicion de Género============================ */
function EditarGenero(id_genero) {
    $('#modificarGeneroModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_genero = null;
    }); 
    $("#modificarGeneroModal").submit(function (e) {
        event.preventDefault();
        var desc_genero = $("#desc_genero_edi").val();
        var datos = {"desc_genero": desc_genero};
        var direccionURL = "";
        direccionURL = "catalogos/editarGenero/" + id_genero;

        if (id_genero !== null) {
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
                $("#modificarGeneroModal").modal('hide');
                $('#tabla_Genero').DataTable().ajax.reload();
                toastr['success'](data.data, "Se modificó con éxito");
                id_genero = null;
            },
            error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    }
    });
}
//Función para trae el valor de la base de datos para edición sobre el mismo
function generoCargaDatosForm(id_genero) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarGenero/" + id_genero,
        dataType: 'json',
        success: function (data) {
            (data.data.desc_genero === null) ? $('#desc_genero_edi').val("") : $('#desc_genero_edi').val(data.data.desc_genero);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusGenero(id_genero, estatus) {
    if (estatus === 1) {
        $("#modalStatusGenero").modal("toggle");
        inhabilitarGenero(id_genero, 0);
    } else if (estatus === 0) {
        $("#modalStatusGeneroActiva").modal("toggle");
        activarGenero(id_genero, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}
/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarGenero(id_genero, estatus) {
    $('#modalStatusGenero').on('hidden.bs.modal', function () {
        id_genero = null;
    }); 

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_genero !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusGenero/" + id_genero + "/" + estatus,
                data: "id_genero=" + id_genero, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusGenero").modal('hide');
                        toastr['warning']("El Género ha sido inhabilitado", "Aviso");
                        $('#tabla_Genero').DataTable().ajax.reload();
                        id_genero = null;
                    }
                }
            });
        }
    });
}
/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarGenero(id_genero, estatus) {
    $('#modalStatusGeneroActiva').on('hidden.bs.modal', function () {
        id_genero = null;
    }); 

    $("#botonActivarEstructura").click(function (e) {
        if (id_genero !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusGenero/" + id_genero + "/" + estatus,
                data: "id_estructura=" + id_genero, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusGeneroActiva").modal('hide');
                        toastr['success']("El Género ha sido activado", "Aviso");
                        $('#tabla_Genero').DataTable().ajax.reload();
                        id_genero = null;
                    }
                }
            });
        }
    });
}
