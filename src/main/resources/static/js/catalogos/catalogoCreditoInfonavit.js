/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
//Limpieza de agrega
$('#agregarCreditoModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#desc_Credito").val("");
}); 

//******************************************************************************
//                      CATÁLOGO CRÉDITO INFONAVIT            
//******************************************************************************

/*=================Carga de tabla Crédito Infonavit============================ */
tabla_Credito = $('#tabla_Credito').dataTable({
    "ajax": {
        url: "catalogos/listarDatosCreditoInfonavit",
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
        {data: "id_credito"},
        {data: "credito_infonavit"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarCreditoModal" onclick="editarCredito(' + r.id_credito + '); creditoCargaDatosForm(' + r.id_credito + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_credito + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusCredito(' + r.id_credito + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_credito + ' class="btn btn-outline-danger" onclick="estatusCredito(' + r.id_credito + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*=================Añadir elementos a tabla Crédito============================ */
$("#agregarCreditoModal").submit(function (e) {
    event.preventDefault();
    var credito_infonavit = $("#desc_Credito").val();
    var datos = {"credito_infonavit": credito_infonavit};
    $.ajax({
        type: "POST",
        url: "catalogos/GuardarCatCredito",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarCreditoModal").modal('hide');
            $("#desc_Credito").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_Credito').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=================Edición de Crédito============================ */
function editarCredito(id_credito) {
    $('#modificarCreditoModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_credito = null;
    }); 
    
    $("#modificarCreditoModal").submit(function (e) {
        event.preventDefault();
        var credito_infonavit = $("#desc_Credito_edi").val();
        var datos = {"credito_infonavit": credito_infonavit};
        var direccionURL = "";
        direccionURL = "catalogos/editarCredito/" + id_credito;
        
        if(id_credito!==null){
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
                $("#modificarCreditoModal").modal('hide');
                $('#tabla_Credito').DataTable().ajax.reload();
                toastr['success'](data.data, "Se modificó con éxito");
                //Reset de Variable para evitar duplicados al editar de manera continua.
                id_credito = null;
            },
            error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    }
    });
}
//Función para trae el valor de la base de datos para edición sobre el mismo
function creditoCargaDatosForm(id_credito) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarCredito/" + id_credito,
        dataType: 'json',
        success: function (data) {
            (data.data.credito_infonavit === null) ? $('#desc_Credito_edi').val("") : $('#desc_Credito_edi').val(data.data.credito_infonavit);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS DE CRÉDITO PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusCredito(id_credito, estatus) {
    if (estatus === 1) {
        $("#modalStatusCredito").modal("toggle");
        inhabilitarCredito(id_credito, 0);
    } else if (estatus === 0) {
        $("#modalStatusCreditoActiva").modal("toggle");
        activarCredito(id_credito, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}
/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarCredito(id_credito, estatus) {
   $('#modalStatusCredito').on('hidden.bs.modal', function () {
        id_credito = null;
    });	

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_credito !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusCredito/" + id_credito + "/" + estatus,
                data: "id_credito=" + id_credito, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusCredito").modal('hide');
                        toastr['warning']("Este Crédito ha sido inhabilitado", "Aviso");
                        $('#tabla_Credito').DataTable().ajax.reload();
                        id_credito = null;
                    }
                }
            });
        }
    });
}
/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarCredito(id_credito, estatus) {
    $('#modalStatusCreditoActiva').on('hidden.bs.modal', function () {
        id_credito = null;
    }); 

    $("#botonActivar").click(function (e) {
        if (id_credito !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusCredito/" + id_credito + "/" + estatus,
                data: "id_credito=" + id_credito, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusCreditoActiva").modal('hide');
                        toastr['success']("Este Crédito ha sido activado", "Aviso");
                        $('#tabla_Credito').DataTable().ajax.reload();
                        id_credito = null;
                    }
                }
            });
        }
    });
}
