/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

//******************************************************************************
//                      CATÁLOGO DEPÓSITOS            
//******************************************************************************

/*=================Carga de tabla Depósitos============================ */
tabla_Depositos = $('#tabla_Depositos').dataTable({
    "ajax": {
        url: "catalogos/listarDepositos",
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
        {data: "id_deposito"},
        {data: "desc_deposito"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarDepoModal" onclick="EditarDeposito(' + r.id_deposito + ');depositosCargaDatosForm(' + r.id_deposito + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_deposito + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusDepo(' + r.id_deposito + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_deposito + ' class="btn btn-outline-danger" onclick="estatusDepo(' + r.id_deposito + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*=================Añadir elementos a tabla Depósitos============================ */
//Limpieza de agrega
$('#agregarDepositoModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#desc_deposito").val("");
}); 

$("#agregarDepositoModal").submit(function (e) {
    event.preventDefault();
    var desc_deposito = $("#desc_deposito").val();
    var datos = {"desc_deposito": desc_deposito};
    $.ajax({
        type: "POST",
        url: "catalogos/guardarDeposito",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarDepositoModal").modal('hide');
            $("#desc_deposito").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_Depositos').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=================Edicion de Depósitos============================ */
function EditarDeposito(id_deposito) {
    $('#modificarDepoModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_deposito = null;
    }); 
    $("#modificarDepoModal").submit(function (e) {
        event.preventDefault();
        var desc_deposito = $("#desc_deposito_edi").val();
        var datos = {"desc_deposito": desc_deposito};
        var direccionURL = "";
        direccionURL = "catalogos/editarDeposito/" + id_deposito;

        if (id_deposito !== null) {
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
                    $("#modificarDepoModal").modal('hide');
                    $('#tabla_Depositos').DataTable().ajax.reload();
                    toastr['success'](data.data, "Se modificó con éxito");
                    id_deposito = null;
                },
                error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
                }
            });
        }
    });
}

//Función para trae el valor de la base de datos para edición sobre el mismo
function depositosCargaDatosForm(id_deposito) {
    $.ajax({
        type: "GET",
        url: "catalogos/buscarDeposito/" + id_deposito,
        dataType: 'json',
        success: function (data) {
            (data.data.desc_deposito === null) ? $('#desc_deposito_edi').val("") : $('#desc_deposito_edi').val(data.data.desc_deposito);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS DEL DEPOSITO PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusDepo(id_deposito, estatus) {

    if (estatus === 1) {
        $("#modalStatusDepo").modal("toggle");
        inhabilitarDepo(id_deposito, 0);

    } else if (estatus === 0) {
        $("#modalStatusDepoActiva").modal("toggle");
        activarDepo(id_deposito, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}

/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarDepo(id_deposito, estatus) {
    $('#modalStatusDepo').on('hidden.bs.modal', function () {
        id_deposito = null;
    });	

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_deposito !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusDeposito/" + id_deposito + "/" + estatus,
                data: "id_deposito=" + id_deposito, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusDepo").modal('hide');
                        toastr['warning']("El Depósito ha sido inhabilitado", "Aviso");
                        $('#tabla_Depositos').DataTable().ajax.reload();
                        id_deposito = null;
                    }
                }
            });
        }
    });
}

/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarDepo(id_deposito, estatus) {
    $('#modalStatusDepoActiva').on('hidden.bs.modal', function () {
        id_deposito = null;
    }); 

    $("#botonActivarDepo").click(function (e) {
        if (id_deposito !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusDeposito/" + id_deposito + "/" + estatus,
                data: "id_cargo=" + id_deposito, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusDepoActiva").modal('hide');
                        toastr['success']("El Depósito ha sido activado", "Aviso");
                        $('#tabla_Depositos').DataTable().ajax.reload();
                        id_deposito = null;
                    }
                }
            });
        }
    });
}
