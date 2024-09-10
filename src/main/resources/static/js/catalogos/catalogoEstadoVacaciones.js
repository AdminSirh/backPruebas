/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
//******************************************************************************
//                      CATÁLOGO ESTADO VACACIONES            
//******************************************************************************

/*=================Carga de tabla Estado Vacaciones============================ */
tabla_Estado_Vacaciones = $('#tabla_Estado_Vacaciones').dataTable({
    "ajax": {
        url: "catalogos/listarDatosEstadoVacaciones",
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
        {data: "id_estado_vacaciones"},
        {data: "desc_estado_vacaciones"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarEdoVacModal" onclick="editarEdoVac(' + r.id_estado_vacaciones + '); edoVacCargaDatosForm(' + r.id_estado_vacaciones + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_estado_vacaciones + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusEdoVac(' + r.id_estado_vacaciones + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_estado_vacaciones + ' class="btn btn-outline-danger" onclick="estatusEdoVac(' + r.id_estado_vacaciones + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*=================Añadir elementos a tabla Estado Vacaciones============================ */
//Limpieza de agrega
$('#agregarEdo_Vac_Modal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#desc_Edo_Vac").val("");
}); 
$("#agregarEdo_Vac_Modal").submit(function (e) {
    event.preventDefault();
    var desc_estado_vacaciones = $("#desc_Edo_Vac").val();
    var datos = {"desc_estado_vacaciones": desc_estado_vacaciones};
    $.ajax({
        type: "POST",
        url: "catalogos/GuardarEstadoVacaciones",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarEdo_Vac_Modal").modal('hide');
            $("#desc_Edo_Vac").val("");
            toastr['success'](data.data, "Se ha agregado correctamente el elemento");
            $('#tabla_Estado_Vacaciones').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=================Edicion de Estado Vacaciones============================ */
function editarEdoVac(id_estado_vacaciones) {
    $('#modificarEdoVacModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_estado_vacaciones = null;
    }); 
    $("#modificarEdoVacModal").submit(function (e) {
        event.preventDefault();
        var desc_estado_vacaciones = $("#desc_EdoVac_edi").val();
        var datos = {"desc_estado_vacaciones": desc_estado_vacaciones};
        var direccionURL = "";
        direccionURL = "catalogos/editarEstadoVacaciones/" + id_estado_vacaciones;

        if (id_estado_vacaciones !== null) {
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
                $("#modificarEdoVacModal").modal('hide');
                $('#tabla_Estado_Vacaciones').DataTable().ajax.reload();
                toastr['success'](data.data, "Se modificó con éxito");
                id_estado_vacaciones = null;
            },
            error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    }
    });
}
//Función para trae el valor de la base de datos para edición sobre el mismo
function edoVacCargaDatosForm(id_estado_vacaciones) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarEstadoVacaciones/" + id_estado_vacaciones,
        dataType: 'json',
        success: function (data) {
            (data.data.desc_estado_vacaciones === null) ? $('#desc_EdoVac_edi').val("") : $('#desc_EdoVac_edi').val(data.data.desc_estado_vacaciones);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS DE EDOVAC PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusEdoVac(id_estado_vacaciones, estatus) {
    if (estatus === 1) {
        $("#modalStatusEdoVac").modal("toggle");
        inhabilitarEdoVac(id_estado_vacaciones, 0);
    } else if (estatus === 0) {
        $("#modalStatusEdoVacActiva").modal("toggle");
        activarEdoVac(id_estado_vacaciones, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}

/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarEdoVac(id_estado_vacaciones, estatus) {
    $('#modalStatusEdoVac').on('hidden.bs.modal', function () {
        id_estado_vacaciones = null;
    }); 

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_estado_vacaciones !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusEstadoVacaciones/" + id_estado_vacaciones + "/" + estatus,
                data: "id_estado_vacaciones=" + id_estado_vacaciones, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusEdoVac").modal('hide');
                        toastr['warning']("Este Estado Vacaciones ha sido inhabilitado", "Aviso");
                        $('#tabla_Estado_Vacaciones').DataTable().ajax.reload();
                        id_estado_vacaciones = null;
                    }
                }
            });
        }
    });
}
/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarEdoVac(id_estado_vacaciones, estatus) {
    $('#modalStatusEdoVacActiva').on('hidden.bs.modal', function () {
        id_estado_vacaciones = null;
    }); 

    $("#botonActivar").click(function (e) {
        if (id_estado_vacaciones !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusEstadoVacaciones/" + id_estado_vacaciones + "/" + estatus,
                data: "id_estructura=" + id_estado_vacaciones, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusEdoVacActiva").modal('hide');
                        toastr['success']("Este Estado Vacaciones ha sido activado", "Aviso");
                        $('#tabla_Estado_Vacaciones').DataTable().ajax.reload();
                        id_estado_vacaciones = null;
                    }
                }
            });
        }
    });
}

