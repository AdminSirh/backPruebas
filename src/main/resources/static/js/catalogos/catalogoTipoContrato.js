/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

//******************************************************************************
//                      CATÁLOGO TIPO CONTRATO        
//******************************************************************************

/*=================Carga de tabla Tipo de Contrato============================ */
tabla_Tipo_Contrato = $('#tabla_Tipo_Contrato').dataTable({
    "ajax": {
        url: "catalogos/listarTipoContrato",
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
        {data: "id_tipo_contrato"},
        {data: "tipo_nomina"},
        {data: "descripcion"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarTipoContratoModal" onclick="editarTipoContrato(' + r.id_tipo_contrato + '); tipoContratoCargaDatosForm(' + r.id_tipo_contrato + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_tipo_contrato + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusTipoContrato(' + r.id_tipo_contrato + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_tipo_contrato + ' class="btn btn-outline-danger" onclick="estatusTipoContrato(' + r.id_tipo_contrato + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*=================Añadir elementos a tabla Tipo de Contrato============================ */
//Limpieza de agrega
$('#agregarTipoContratoModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#tipoNomina_agrega").val("");
   $("#descripcion_agrega").val("");
}); 
$("#agregarTipoContratoModal").submit(function (e) {
    event.preventDefault();
    var tipo_nomina = $("#tipoNomina_agrega").val();
    var descripcion = $("#descripcion_agrega").val();

    //Validación de no envío de datos vacíos
    if ($.trim(tipo_nomina) === "") {
        $("#alertaAgregar").append(" <label class='text-danger'><small>El campo 'Tipo Nómina' no puede estar vacío,ingresa los datos correctos</small></label>");
        return false;
    }
    if ($.trim(descripcion) === "") {
        $("#alertaAgregar").append(" <label class='text-danger'><small>El campo 'Descripción' no puede estar vacío,ingresa los datos correctos</small></label>");
        return false;
    }

    var datos = {"tipo_nomina": tipo_nomina, "descripcion": descripcion};
    $.ajax({
        type: "POST",
        url: "catalogos/guardarTipoContrato",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarTipoContratoModal").modal('hide');
            $("#tipoNomina_agrega").val("");
            $("#descripcion_agrega").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_Tipo_Contrato').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=================Edición de Tipo Contrato============================ */
function editarTipoContrato(id_tipo_contrato) {
    $('#modificarTipoContratoModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_tipo_contrato = null;
    }); 
    
    $("#modificarTipoContratoModal").submit(function (e) {
        event.preventDefault();
        var tipo_nomina = $("#tipo_nomina_edi").val();
        var descripcion = $("#desc_edi").val();
        var datos = {"tipo_nomina": tipo_nomina, "descripcion": descripcion};

        direccionURL = "";
        direccionURL = "catalogos/editarTipoContrato/" + id_tipo_contrato;
        
        if (id_tipo_contrato!== null) {
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
                $("#modificarTipoContratoModal").modal('hide');
                $('#tabla_Tipo_Contrato').DataTable().ajax.reload();
                toastr['success'](data.data, "Se modificó con éxito");
                id_tipo_contrato = null;
            },
            error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    }
    });
}

//Función para trae el valor de la base de datos para edición sobre el mismo
function tipoContratoCargaDatosForm(id_tipo_contrato) {
    $.ajax({
        type: "GET",
        url: "catalogos/buscarTipoContrato/" + id_tipo_contrato,
        dataType: 'json',
        success: function (data) {
            (data.data.tipo_nomina === null) ? $('#tipo_nomina_edi').val("") : $('#tipo_nomina_edi').val(data.data.tipo_nomina);
            (data.data.descripcion === null) ? $('#desc_edi').val("") : $('#desc_edi').val(data.data.descripcion);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS DE TIPO CONTRATO PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusTipoContrato(id_tipo_contrato, estatus) {
    if (estatus === 1) {
        $("#modalStatusTipoContrato").modal("toggle");
        inhabilitarTipoContrato(id_tipo_contrato, 0);
    } else if (estatus === 0) {
        $("#modalStatusTipoContratoActiva").modal("toggle");
        activarTipoContrato(id_tipo_contrato, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}

/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarTipoContrato(id_tipo_contrato, estatus) {
    $('#modalStatusTipoContrato').on('hidden.bs.modal', function () {
        id_tipo_contrato = null;
    }); 

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_tipo_contrato !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusTipoContrato/" + id_tipo_contrato + "/" + estatus,
                data: "id_tipo_contrato=" + id_tipo_contrato, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusTipoContrato").modal('hide');
                        toastr['warning']("Este Tipo de Contrato ha sido inhabilitado", "Aviso");
                        $('#tabla_Tipo_Contrato').DataTable().ajax.reload();
                        id_tipo_contrato = null;
                    }
                }
            });
        }
    });
}

/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarTipoContrato(id_tipo_contrato, estatus) {
    $('#modalStatusTipoContratoActiva').on('hidden.bs.modal', function () {
        id_tipo_contrato = null;
    }); 

    $("#botonActivar").click(function (e) {
        if (id_tipo_contrato !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusTipoContrato/" + id_tipo_contrato + "/" + estatus,
                data: "id_tipo_contrato=" + id_tipo_contrato, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusTipoContratoActiva").modal('hide');
                        toastr['success']("Este Tipo de Contrato ha sido activado", "Aviso");
                        $('#tabla_Tipo_Contrato').DataTable().ajax.reload();
                        id_tipo_contrato = null;
                    }
                }
            });
        }
    });
}
