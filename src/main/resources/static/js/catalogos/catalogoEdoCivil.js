/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

//******************************************************************************
//                      CATÁLOGO ESTADO CIVIL            
//******************************************************************************

/*=================Carga de tabla Estado Civil============================ */
tabla_EdoCivil = $('#tabla_EdoCivil').dataTable({
    "ajax": {
        url: "catalogos/listarDatosEdoCivil",
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
        {data: "id_edo_civil"},
        {data: "desc_edo_civil"},
        //{data: "estatus"}, //No forma parte del objeto que se está cargando
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarEdoCivilModal" onclick="EditarEdoCivil(' + r.id_edo_civil + ');edoCivilCargaDatosForm(' + r.id_edo_civil + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_edo_civil + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusEdoCivil(' + r.id_edo_civil + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_edo_civil + ' class="btn btn-outline-danger" onclick="estatusEdoCivil(' + r.id_edo_civil + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*=================Añadir elementos a tabla estado civil============================ */
//Limpieza de agrega
$('#agregarEdoCivilModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#desc_EdoCivil").val("");
}); 

$("#agregarEdoCivilModal").submit(function (e) {
    event.preventDefault();
    var desc_edo_civil = $("#desc_EdoCivil").val();
    var datos = {"desc_edo_civil": desc_edo_civil};
    $.ajax({
        type: "POST",
        url: "catalogos/GuardarEdoCivil",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarEdoCivilModal").modal('hide');
            $("#desc_EdoCivil").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_EdoCivil').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=================Edicion de Estado Civil============================ */
function EditarEdoCivil(id_edo_civil) {
    $('#modificarEdoCivilModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
        //Invalida el id para evitar inconsistencias
        id_edo_civil = null;
    });
    
    $("#modificarEdoCivilModal").submit(function (e) {
        event.preventDefault();
        var desc_edo_civil = $("#desc_edoCivil_edi").val();
        var datos = {"desc_edo_civil": desc_edo_civil};
        var direccionURL = "";
        direccionURL = "catalogos/editarEdoCivil/" + id_edo_civil;
        
        if (id_edo_civil !== null) {
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
                $("#modificarEdoCivilModal").modal('hide');
                $('#tabla_EdoCivil').DataTable().ajax.reload();
                toastr['success'](data.data, "Se modificó con éxito");
                //Reset de variable para permitir edición contínua sin problemas
                id_edo_civil = null;
            },
            error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    }
    });
}

//Función para trae el valor de la base de datos para edición sobre el mismo
function edoCivilCargaDatosForm(id_edo_civil) {
    $.ajax({
        type: "GET",
        url: "catalogos/buscarEdoCivil/" + id_edo_civil,
        dataType: 'json',
        success: function (data) {
            (data.data.desc_edo_civil === null) ? $('#desc_edoCivil_edi').val("") : $('#desc_edoCivil_edi').val(data.data.desc_edo_civil);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS DEL ESTADO CIVIL PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusEdoCivil(id_edo_civil, estatus) {

    if (estatus === 1) {
        $("#modalStatusEdoCivil").modal("toggle");
        inhabilitarEdoCivil(id_edo_civil, 0);
    } else if (estatus === 0) {
        $("#modalStatusEdoCivilActiva").modal("toggle");
        activarEdoCivil(id_edo_civil, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}

/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarEdoCivil(id_edo_civil, estatus) {
    $('#modalStatusEdoCivil').on('hidden.bs.modal', function () {
        id_edo_civil = null;
    });
    
    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_edo_civil !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusEdoCivil/" + id_edo_civil + "/" + estatus,
                data: "id_edo_civil=" + id_edo_civil, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusEdoCivil").modal('hide');
                        toastr['warning']("El Estado Civil ha sido inhabilitado", "Aviso");
                        $('#tabla_EdoCivil').DataTable().ajax.reload();
                        id_edo_civil = null;
                    }
                }
            });
        }
    });
}

/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarEdoCivil(id_edo_civil, estatus) {
    $('#modalStatusEdoCivilActiva').on('hidden.bs.modal', function () {
        id_edo_civil = null;
    }); 

    $("#botonActivarDepo").click(function (e) {
        if (id_edo_civil !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusEdoCivil/" + id_edo_civil + "/" + estatus,
                data: "id_edo_civil=" + id_edo_civil, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusEdoCivilActiva").modal('hide');
                        toastr['success']("El Estado Civil ha sido activado", "Aviso");
                        $('#tabla_EdoCivil').DataTable().ajax.reload();
                        id_edo_civil = null;
                    }
                }
            });
        }
    });
}

