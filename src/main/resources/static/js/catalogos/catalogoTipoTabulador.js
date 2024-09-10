/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
//******************************************************************************
//                      CATÁLOGO TIPO TABULADOR         
//******************************************************************************

/*=================Carga de tabla Tipo de tabulador============================ */
tabla_Tipo_Tabulador = $('#tabla_Tipo_Tabulador').dataTable({
    "ajax": {
        url: "catalogos/listarTabuladores",
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
        {data: "id_tipo_tabulador"},
        {data: "desc_tipo_tabulador"},
        {data: "clave_tipo_tabulador"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarTabuladorModal" onclick="editarTabulador(' + r.id_tipo_tabulador + '); tabuladorCargaDatosForm(' + r.id_tipo_tabulador + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_tipo_tabulador + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusTabulador(' + r.id_tipo_tabulador + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_tipo_tabulador + ' class="btn btn-outline-danger" onclick="estatusTabulador(' + r.id_tipo_tabulador + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*=================Añadir elementos a tabla Tabulador============================ */
//Limpieza de agrega
$('#agregarTabuladorModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#desc_TipoTab").val("");
   $("#desc_CveTab").val("");
}); 
$("#agregarTabuladorModal").submit(function (e) {
    event.preventDefault();
    var desc_tipo_tabulador = $("#desc_TipoTab").val();
    var clave_tipo_tabulador = $("#desc_CveTab").val();

    //Validación de no envío de datos vacíos
    if ($.trim(desc_tipo_tabulador) === "") {
        $("#alertaAgregaTabulador").append(" <label class='text-danger'><small>El campo 'Descripción del Tipo de Tabulador' no puede estar vacío,ingresa los datos correctos</small></label>");
        return false;
    }
    if ($.trim(clave_tipo_tabulador) === "") {
        $("#alertaAgregaTabulador").append(" <label class='text-danger'><small>El campo 'Descripción de la Clave del Tipo de Tabulador' no puede estar vacío,ingresa los datos correctos</small></label>");
        return false;
    }
    var datos = {"desc_tipo_tabulador": desc_tipo_tabulador, "clave_tipo_tabulador": clave_tipo_tabulador};
    $.ajax({
        type: "POST",
        url: "catalogos/GuardarTabulador",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarTabuladorModal").modal('hide');
            $("#desc_TipoTab").val("");
            $("#desc_CveTab").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_Tipo_Tabulador').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo: " + e, " error", {progressBar: true, closeButton: true});
        }
    });

});

/*=================Edición de Tipo Tabulador============================ */
function editarTabulador(id_tipo_tabulador) {
    $('#modificarTabuladorModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_tipo_tabulador = null;
    }); 
    $("#modificarTabuladorModal").submit(function (e) {
        event.preventDefault();
        var desc_tipo_tabulador = $("#desc_TipoTab_edi").val();
        var clave_tipo_tabulador = $("#desc_CveTab_Edi").val();
        var datos = {"desc_tipo_tabulador": desc_tipo_tabulador, "clave_tipo_tabulador": clave_tipo_tabulador};
        var direccionURL = "";
        direccionURL = "catalogos/editarTabulador/" + id_tipo_tabulador;
        
        if (id_tipo_tabulador !== null) {
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
                $("#modificarTabuladorModal").modal('hide');
                $('#tabla_Tipo_Tabulador').DataTable().ajax.reload();
                toastr['success'](data.data, "Se modificó con éxito");
                //Reset de variable para evitar problemas al editar registros continuos
                id_tipo_tabulador = null;
            },
            error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
                }
        });
    }
    });
}
//Función para trae el valor de la base de datos para edición sobre el mismo
function tabuladorCargaDatosForm(id_tipo_tabulador) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarTabulador/" + id_tipo_tabulador,
        dataType: 'json',
        success: function (data) {
            (data.data.desc_tipo_tabulador === null) ? $('#desc_TipoTab_edi').val("") : $('#desc_TipoTab_edi').val(data.data.desc_tipo_tabulador);
            (data.data.clave_tipo_tabulador === null) ? $('#desc_CveTab_Edi').val("") : $('#desc_CveTab_Edi').val(data.data.clave_tipo_tabulador);

        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS DE TABULADOR PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusTabulador(id_tipo_tabulador, estatus) {
    if (estatus === 1) {
        $("#modalStatusTabulador").modal("toggle");
        inhabilitarTabulador(id_tipo_tabulador, 0);
    } else if (estatus === 0) {
        $("#modalStatusTabuladorActiva").modal("toggle");
        activarTabulador(id_tipo_tabulador, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}

/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarTabulador(id_tipo_tabulador, estatus) {
    $('#modalStatusTabulador').on('hidden.bs.modal', function () {
        id_tipo_tabulador = null;
    });

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_tipo_tabulador !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusTabulador/" + id_tipo_tabulador + "/" + estatus,
                data: "id_horario=" + id_tipo_tabulador, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusTabulador").modal('hide');
                        toastr['warning']("Este Tabulador ha sido inhabilitado", "Aviso");
                        $('#tabla_Tipo_Tabulador').DataTable().ajax.reload();
                        id_tipo_tabulador = null;
                    }
                }
            });
        }
    });
}

/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarTabulador(id_tipo_tabulador, estatus) {
    $('#modalStatusTabuladorActiva').on('hidden.bs.modal', function () {
        id_tipo_tabulador = null;
    });

    $("#botonActivar").click(function (e) {
        if (id_tipo_tabulador !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusTabulador/" + id_tipo_tabulador + "/" + estatus,
                data: "id_horario=" + id_tipo_tabulador, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusTabuladorActiva").modal('hide');
                        toastr['success']("Este Tabulador ha sido activado", "Aviso");
                        $('#tabla_Tipo_Tabulador').DataTable().ajax.reload();
                        id_tipo_tabulador = null;
                    }
                }
            });
        }
    });
}

