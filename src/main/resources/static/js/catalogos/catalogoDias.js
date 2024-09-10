/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
//******************************************************************************
//                      CATÁLOGO DÍAS            
//******************************************************************************
/*=================Carga de tabla Días============================ */
tabla_Dias = $('#tabla_Dias').dataTable({
    "ajax": {
        url: "catalogos/listarDias",
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
        {data: "id_dia"},
        {data: "dia"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarDiaModal" onclick="editarDia(' + r.id_dia + '); diaCargaDatosForm(' + r.id_dia + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_dia + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusDia(' + r.id_dia + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_dia + ' class="btn btn-outline-danger" onclick="estatusDia(' + r.id_dia + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});
/*=================Edicion de Días============================ */
function editarDia(id_dia) {
    $('#modificarDiaModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_dia = null;
    }); 
    $("#modificarDiaModal").submit(function (e) {
        event.preventDefault();
        var dia = $("#desc_dia_edi").val();
        var datos = {"dia": dia};
        var direccionURL = "";
        direccionURL = "catalogos/editarDia/" + id_dia;

        if (id_dia !== null) {
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
                    $("#modificarDiaModal").modal('hide');
                    $('#tabla_Dias').DataTable().ajax.reload();
                    toastr['success'](data.data, "Se modificó con éxito");
                    //Reset de variable para evitar eroresc al realizar modificaciones continuas
                    id_dia = null;
                },
                error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});

                }
            });
        }
    });
}
//Función para trae el valor de la base de datos para edición sobre el mismo
function diaCargaDatosForm(id_dia) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarDia/" + id_dia,
        dataType: 'json',
        success: function (data) {
            (data.data.dia === null) ? $('#desc_dia_edi').val("") : $('#desc_dia_edi').val(data.data.dia);
        },
        error: function (e) {
            alert(e);
        }
    });
}
/*=================VERIFICACION DE ESTATUS DEL DÍA PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusDia(id_dia, estatus) {
    if (estatus === 1) {
        $("#modalStatusDia").modal("toggle");
        inhabilitarDia(id_dia, 0);
    } else if (estatus === 0) {
        $("#modalStatusDiaActiva").modal("toggle");
        activarDia(id_dia, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}

/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarDia(id_dia, estatus) {
    $('#modalStatusDia').on('hidden.bs.modal', function () {
        id_dia = null;
    });	

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_dia !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusDia/" + id_dia + "/" + estatus,
                data: "id_dia=" + id_dia, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusDia").modal('hide');
                        toastr['warning']("El Día ha sido inhabilitado", "Aviso");
                        $('#tabla_Dias').DataTable().ajax.reload();
                        id_dia = null;
                    }
                }
            });
        }
    });
}
/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarDia(id_dia, estatus) {
    $('#modalStatusDiaActiva').on('hidden.bs.modal', function () {
        id_dia = null;
    }); 

    $("#botonActivarEstructura").click(function (e) {
        if (id_dia !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusDia/" + id_dia + "/" + estatus,
                data: "id_estructura=" + id_dia, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusDiaActiva").modal('hide');
                        toastr['success']("El Día ha sido activado", "Aviso");
                        $('#tabla_Dias').DataTable().ajax.reload();
                        id_dia = null;
                    }
                }
            });
        }
    });
}