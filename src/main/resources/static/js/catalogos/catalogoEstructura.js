/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
//******************************************************************************
//                      CATÁLOGO ESTRUCTURAS            
//******************************************************************************

/*=================Carga de tabla Estructuras============================ */
tabla_Estructuras = $('#tabla_Estructuras').dataTable({
    "ajax": {
        url: "catalogos/listarDatosEstructura",
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
        {data: "id_estructura"},
        {data: "desc_estructura"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarEstructuraModal" onclick="EditarEstructura(' + r.id_estructura + '); estructuraCargaDatosForm(' + r.id_estructura + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_estructura + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusEstructura(' + r.id_estructura + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_estructura + ' class="btn btn-outline-danger" onclick="estatusEstructura(' + r.id_estructura + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*=================Añadir elementos a tabla Estructura============================ */
//Limpieza de agrega
$('#agregarEstructuraModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#desc_Estructura").val("");
}); 

$("#agregarEstructuraModal").submit(function (e) {
    event.preventDefault();
    var desc_estructura = $("#desc_Estructura").val();
    var datos = {"desc_estructura": desc_estructura};

    $.ajax({
        type: "POST",
        url: "catalogos/GuardarEstructura",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarEstructuraModal").modal('hide');
            $("#desc_Estructura").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_Estructuras').DataTable().ajax.reload();
        },
        error: function (e) {

            toastr["warning"]("Error al agregar elemento al catálogo : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=================Edicion de Estructura============================ */
function EditarEstructura(id_estructura) {
    $('#modificarEstructuraModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_estructura = null;
    }); 
    $("#modificarEstructuraModal").submit(function (e) {
        event.preventDefault();
        var desc_estructura = $("#desc_estructura_edi").val();
        var datos = {"desc_estructura": desc_estructura};
        var direccionURL = "";
        direccionURL = "catalogos/editarEstructura/" + id_estructura;

        if (id_estructura !== null) {
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
                $("#modificarEstructuraModal").modal('hide');
                $('#tabla_Estructuras').DataTable().ajax.reload();
                toastr['success'](data.data, "Se modificó con éxito");

                //Reset de variable
                id_estructura = null;
            },
            error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    }
    });
}

//Función para trae el valor de la base de datos para edición sobre el mismo
function estructuraCargaDatosForm(id_estructura) {
    $.ajax({
        type: "GET",
        url: "catalogos/buscarEstructura/" + id_estructura,
        dataType: 'json',
        success: function (data) {
            (data.data.desc_estructura === null) ? $('#desc_estructura_edi').val("") : $('#desc_estructura_edi').val(data.data.desc_estructura);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS DE LA ESTRUCTURA PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusEstructura(id_estructura, estatus) {
    if (estatus === 1) {
        $("#modalStatusEstructura").modal("toggle");
        inhabilitarEstructura(id_estructura, 0);
    } else if (estatus === 0) {
        $("#modalStatusEstructuraActiva").modal("toggle");
        activarEstructura(id_estructura, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}

/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarEstructura(id_estructura, estatus) {
    $('#modalStatusEstructura').on('hidden.bs.modal', function () {
        id_estructura = null;
    }); 

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_estructura !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusEstructura/" + id_estructura + "/" + estatus,
                data: "id_estructura=" + id_estructura, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusEstructura").modal('hide');
                        toastr['warning']("La Estructura ha sido inhabilitada", "Aviso");
                        $('#tabla_Estructuras').DataTable().ajax.reload();
                        id_estructura = null;
                    }
                }
            });
        }
    });
}
/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarEstructura(id_estructura, estatus) {
    $('#modalStatusEstructuraActiva').on('hidden.bs.modal', function () {
        id_estructura = null;
    }); 

    $("#botonActivarEstructura").click(function (e) {
        if (id_estructura !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusEstructura/" + id_estructura + "/" + estatus,
                data: "id_estructura=" + id_estructura, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusEstructuraActiva").modal('hide');
                        toastr['success']("La Estructura ha sido activada", "Aviso");
                        $('#tabla_Estructuras').DataTable().ajax.reload();
                        id_estructura = null;
                    }
                }
            });
        }
    });
}

