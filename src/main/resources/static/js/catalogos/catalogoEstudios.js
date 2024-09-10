/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

//******************************************************************************
//                      CATÁLOGO ESTUDIOS            
//******************************************************************************
/*=================Carga de tabla Estudios============================ */
tabla_Estudios = $('#tabla_Estudios').dataTable({
    "ajax": {
        url: "catalogos/listarDatosEstudios",
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
        {data: "id_grado"},
        {data: "desc_grado"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarEstudioModal" onclick="EditarEstudio(' + r.id_grado + '); estudiosCargaDatosForm(' + r.id_grado + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_grado + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusEstudio(' + r.id_grado + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_grado + ' class="btn btn-outline-danger" onclick="estatusEstudio(' + r.id_grado + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*=================Añadir elementos a tabla Estudio============================ */
//Limpieza de agrega
$('#agregarEstudioModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#nombre_estudio_agrega").val("");
}); 

$("#agregarEstudioModal").submit(function (e) {
    event.preventDefault();
    var desc_grado = $("#nombre_estudio_agrega").val();

    //Validación de no envío de datos vacíos
    if ($.trim(desc_grado) === "") {
        $("#alertaAgregar").append(" <label class='text-danger'><small>El campo 'Nombre del Estudio' no puede estar vacío,ingresa los datos correctos</small></label>");
        return false;
    }

    var datos = {"desc_grado": desc_grado};
    $.ajax({
        type: "POST",
        url: "catalogos/GuardarEstudios",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarEstudioModal").modal('hide');
            //Limpieza de inputs y alerta al momento de agregar de manera correcta.
            $("#nombre_estudio_agrega").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_Estudios').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=================Edicion de Estudios============================ */
function EditarEstudio(id_grado) {
    $('#modificarEstudioModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_grado = null;
    }); 
    $("#modificarEstudioModal").submit(function (e) {
        event.preventDefault();
        var desc_estudio = $("#desc_estudio_edi").val();
        var datos = {"desc_grado": desc_estudio};
        var direccionURL = "";
        direccionURL = "catalogos/editarEstudios/" + id_grado;

        if (id_grado !== null) {
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
                $("#modificarEstudioModal").modal('hide');
                $('#tabla_Estudios').DataTable().ajax.reload();
                toastr['success'](data.data, "Se modificó con éxito");
                
                //Resest de variable para evitsr duplicados al agregar de manera continua
                id_grado = null;
            },
            error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    }
    });
}
//Función para trae el valor de la base de datos para edición sobre el mismo
function estudiosCargaDatosForm(id_grado) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarEstudio/" + id_grado,
        dataType: 'json',
        success: function (data) {
            (data.data.desc_grado === null) ? $('#desc_estudio_edi').val("") : $('#desc_estudio_edi').val(data.data.desc_grado);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusEstudio(id_grado, estatus) {
    if (estatus === 1) {
        $("#modalStatusEstudio").modal("toggle");
        inhabilitarEstudio(id_grado, 0);
    } else if (estatus === 0) {
        $("#modalStatusEstudioActiva").modal("toggle");
        activarEstudio(id_grado, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}
/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarEstudio(id_grado, estatus) {
    $('#modalStatusEstudio').on('hidden.bs.modal', function () {
        id_grado = null;
    }); 

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_grado !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusEstudios/" + id_grado + "/" + estatus,
                data: "id_grado=" + id_grado, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusEstudio").modal('hide');
                        toastr['warning']("El Estudio ha sido inhabilitado", "Aviso");
                        $('#tabla_Estudios').DataTable().ajax.reload();
                        id_grado = null;
                    }
                }
            });
        }
    });
}
/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarEstudio(id_grado, estatus) {
    $('#modalStatusEstudioActiva').on('hidden.bs.modal', function () {
        id_grado = null;
    }); 

    $("#botonActivarEstructura").click(function (e) {
        if (id_grado !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusEstudios/" + id_grado + "/" + estatus,
                data: "id_estructura=" + id_grado, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusEstudioActiva").modal('hide');
                        toastr['success']("El Estudio ha sido activado", "Aviso");
                        $('#tabla_Estudios').DataTable().ajax.reload();
                        id_grado = null;
                    }
                }
            });
        }
    });
}

