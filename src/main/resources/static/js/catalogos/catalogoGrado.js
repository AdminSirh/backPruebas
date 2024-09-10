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
tabla_Grado = $('#tabla_Grado').dataTable({
    "ajax": {
        url: "catalogos/listarGrado",
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
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarGradoModal" onclick="editarGrado(' + r.id_grado + '); creditoCargaDatosForm(' + r.id_grado + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_grado + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusGrado(' + r.id_grado + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_grado + ' class="btn btn-outline-danger" onclick="estatusGrado(' + r.id_grado + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*=================Añadir elementos a tabla Crédito============================ */
$("#agregarGradoModal").submit(function (e) {
    event.preventDefault();
    var desc_grado = $("#desc_Grado").val();
    var datos = {"desc_grado": desc_grado};
    $.ajax({
        type: "POST",
        url: "catalogos/guardarGrado",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarGradoModal").modal('hide');
            $("#desc_Grado").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_Grado').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=================Edición de Crédito============================ */
function editarGrado(id_grado) {
    $('#modificarGradoModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_grado = null;
    }); 
    
    $("#modificarGradoModal").submit(function (e) {
        event.preventDefault();
        var desc_grado = $("#desc_grado_edi").val();
        var datos = {"desc_grado": desc_grado};
        var direccionURL = "";
        direccionURL = "catalogos/editarGrado/" + id_grado;
        
        if(id_grado!==null){
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
                $("#modificarGradoModal").modal('hide');
                $('#tabla_Grado').DataTable().ajax.reload();
                toastr['success'](data.data, "Se modificó con éxito");
                //Reset de Variable para evitar duplicados al editar de manera continua.
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
function creditoCargaDatosForm(id_grado) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarGrado/" + id_grado,
        dataType: 'json',
        success: function (data) {
            (data.data.desc_grado === null) ? $('#desc_grado_edi').val("") : $('#desc_grado_edi').val(data.data.desc_grado);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS DE CRÉDITO PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusGrado(id_grado, estatus) {
    if (estatus === 1) {
        $("#modalStatusGrado").modal("toggle");
        inhabilitarGrado(id_grado, 0);
    } else if (estatus === 0) {
        $("#modalStatusGradoActiva").modal("toggle");
        activarGrado(id_grado, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}
/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarGrado(id_grado, estatus) {
   $('#modalStatusGrado').on('hidden.bs.modal', function () {
        id_grado = null;
    });	

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_grado !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusGrado/" + id_grado + "/" + estatus,
                data: "id_grado=" + id_grado, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusGrado").modal('hide');
                        toastr['warning']("Este Grado ha sido inhabilitado", "Aviso");
                        $('#tabla_Grado').DataTable().ajax.reload();
                        id_grado = null;
                    }
                }
            });
        }
    });
}
/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarGrado(id_grado, estatus) {
    $('#modalStatusGradoActiva').on('hidden.bs.modal', function () {
        id_grado = null;
    }); 

    $("#botonActivar").click(function (e) {
        if (id_grado !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusGrado/" + id_grado + "/" + estatus,
                data: "id_grado=" + id_grado, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusGradoActiva").modal('hide');
                        toastr['success']("Este Grado ha sido activado", "Aviso");
                        $('#tabla_Grado').DataTable().ajax.reload();
                        id_grado = null;
                    }
                }
            });
        }
    });
}
