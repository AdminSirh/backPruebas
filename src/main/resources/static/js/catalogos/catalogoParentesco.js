/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

//******************************************************************************
//                      CATÁLOGO PARENTESCO            
//******************************************************************************

/*=============================================
 LIMPIAR FORMULARIO
 =============================================*/
$("#btn_Cancelar").on("click", function (event) {
    $("div#alerta").removeClass("#invalid-feedback");
    $("div#alerta").html("");
    $("#formGuardarPlazaMovimientos").reset();
    $("#alerta").resetForm();
});

/*=================Carga de tabla parentesco============================ */
tabla_Parentesco = $('#tabla_Parentesco').dataTable({
    "ajax": {
        url: "catalogos/listarDatosCat_Parentesco",
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
        {data: "id_parentesco"},
        {data: "desc_parentesco"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarParentescoModal" onclick="EditarParentesco(' + r.id_parentesco + ');parentescoCargaDatosForm(' + r.id_parentesco + ') "  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_parentesco + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusParentesco(' + r.id_parentesco + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_parentesco + ' class="btn btn-outline-danger" onclick="estatusParentesco(' + r.id_parentesco + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});
/*=================VERIFICACION DE ESTATUS DEL PARENTESCO PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusParentesco(id_parentesco, estatus) {
    if (estatus === 1) {
        $("#modalStatusParentesco").modal("toggle");
        inhabilitarParentesco(id_parentesco, 0);

    } else if (estatus === 0) {
        $("#modalStatusParentescoActivar").modal("toggle");
        activarParentesco(id_parentesco, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}
/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarParentesco(id_parentesco, estatus) {
    $('#modalStatusParentesco').on('hidden.bs.modal', function () {
        id_parentesco = null;
    });	

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_parentesco !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusParentesco/" + id_parentesco + "/" + estatus,
                data: "id_parentesco=" + id_parentesco, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusParentesco").modal('hide');
                        toastr['warning']("El Parentesco ha sido inhabilitado", "Aviso");
                        $('#tabla_Parentesco').DataTable().ajax.reload();
                        id_parentesco = null;
                    }
                }
            });
        }
    });
}
/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarParentesco(id_parentesco, estatus) {
    $('#modalStatusParentescoActivar').on('hidden.bs.modal', function () {
        id_parentesco = null;
    }); 

    $("#botonActivarPuesto").click(function (e) {
        if (id_parentesco !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusParentesco/" + id_parentesco + "/" + estatus,
                data: "id_parentesco=" + id_parentesco, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusParentescoActivar").modal('hide');
                        toastr['success']("El Parentesco ha sido activado", "Aviso");
                        $('#tabla_Parentesco').DataTable().ajax.reload();
                        id_parentesco = null;
                    }
                }
            });
        }
    });
}
/*=================Añadir elementos a tabla parentesco============================ */
//Limpieza de agrega
$('#agregarParentescoModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#desc_parentesco").val("");
}); 

$("#agregarParentescoModal").submit(function (e) {
    event.preventDefault();
    var desc_parentesco = $("#desc_parentesco").val();
    var datos = {"desc_parentesco": desc_parentesco};
    $.ajax({
        type: "POST",
        url: "catalogos/GuardarParentesco",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarParentescoModal").modal('hide');
            $("#desc_parentesco").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_Parentesco').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=================Edicion de parentesco============================ */
function EditarParentesco(id_parentesco) {
    $('#modificarParentescoModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_parentesco = null;
    }); 
    $("#modificarParentescoModal").submit(function (e) {
        event.preventDefault();
        var desc_parentesco = $("#desc_parentesco_edi").val();
        var datos = {"desc_parentesco": desc_parentesco};
        var direccionURL = "";
        direccionURL = "catalogos/editarParentesco/" + id_parentesco;
        
        if (id_parentesco !== null) {
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
                $("#modificarParentescoModal").modal('hide');
                $('#tabla_Parentesco').DataTable().ajax.reload();
                toastr['success'](data.data, "Se modificó con éxito");

                id_parentesco = null;
            },
            error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
                }
        });
    }
    });
}
//Función para trae el valor de la base de datos para edición sobre el mismo
function parentescoCargaDatosForm(id_parentesco) {
    $.ajax({
        type: "GET",
        url: "catalogos/buscarParentesco/" + id_parentesco,
        dataType: 'json',
        success: function (data) {
            (data.data.desc_parentesco === null) ? $('#desc_parentesco_edi').val("") : $('#desc_parentesco_edi').val(data.data.desc_parentesco);
        },
        error: function (e) {
            alert(e);
        }
    });
}


