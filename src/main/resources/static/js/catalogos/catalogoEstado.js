/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', () => {
});
$("#home").click(function (e) {
    event.preventDefault();
    catalogosAdmin();
});

function catalogosAdmin() {
    window.location.href = 'catalogosAdmin';
}

//******************************************************************************
//                      CATÁLOGO ESTADO            
//******************************************************************************
/*=================Carga de tabla Estados============================ */
tabla_Estados = $('#tabla_Estados').dataTable({
    "ajax": {
        url: "catalogos/listarDatosEstado",
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
        {data: "id_estado"},
        {data: "desc_estado"},
        //{data: "estatus"}, //No forma parte del objeto que se está cargando
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarEstadoModal" onclick="editarEstado(' + r.id_estado + '); estadoCargaDatosForm(' + r.id_estado + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_estado + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusEstado(' + r.id_estado + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_estado + ' class="btn btn-outline-danger" onclick="estatusEstado(' + r.id_estado + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        },
        {data: "", sTitle: "Municipios", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-info" onclick="verTablaMunicipio(' + r.id_estado + ')"><span class="fa fa-search"></span>Municipios</button>';
                return he;
            }
        }]
});

function verTablaMunicipio(id_estado) {
    let estado = encodeURIComponent('id_estado=' + id_estado);
    window.location.href = 'catalogoMunicipio?' + estado;
}

/*=================Edicion de Días============================ */
function editarEstado(id_estado) {
    $('#modificarEstadoModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_estado = null;
    }); 
    
    $("#modificarEstadoModal").submit(function (e) {
        event.preventDefault();
        var desc_estado = $("#desc_estado_edi").val();
        let desc_Estado_MayusculaInicial = desc_estado.charAt(0).toUpperCase() + desc_estado.slice(1);
        var datos = {"desc_estado": desc_Estado_MayusculaInicial};
        var direccionURL = "";
        direccionURL = "catalogos/editarEstado/" + id_estado;

        if (id_estado !== null) {
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
                $("#modificarEstadoModal").modal('hide');
                $('#tabla_Estados').DataTable().ajax.reload();
                toastr['success'](data.data, "Se modificó con éxito");

                //Reset de variable para evitar problemas al realizar ediciones continuas
                id_estado = null;
            },
            error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
                
            }
        });
    }
    });
}

//Función para trae el valor de la base de datos para edición sobre el mismo
function estadoCargaDatosForm(id_estado) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarEstado/" + id_estado,
        dataType: 'json',
        success: function (data) {
            (data.data.desc_estado === null) ? $('#desc_estado_edi').val("") : $('#desc_estado_edi').val(data.data.desc_estado);
        },
        error: function (e) {
            alert(e);
        }
    });
}
/*=================VERIFICACION DE ESTATUS DEL ESTADO PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusEstado(id_estado, estatus) {
    if (estatus === 1) {
        $("#modalStatusEstado").modal("toggle");
        inhabilitarEstado(id_estado, 0);
    } else if (estatus === 0) {
        $("#modalStatusEstadoActiva").modal("toggle");
        activarEstado(id_estado, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}

/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarEstado(id_estado, estatus) {
    $('#modalStatusEstado').on('hidden.bs.modal', function () {
        id_estado = null;
    });	

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_estado !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusEstado/" + id_estado + "/" + estatus,
                data: "id_dia=" + id_estado, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusEstado").modal('hide');
                        toastr['warning']("El Estado ha sido inhabilitado", "Aviso");
                        $('#tabla_Estados').DataTable().ajax.reload();
                        id_estado = null;
                    }
                }
            });
        }
    });
}
/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarEstado(id_estado, estatus) {
    $('#modalStatusEstadoActiva').on('hidden.bs.modal', function () {
        id_estado = null;
    }); 

    $("#botonActivar").click(function (e) {
        if (id_estado !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusEstado/" + id_estado + "/" + estatus,
                data: "id_estado=" + id_estado, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusEstadoActiva").modal('hide');
                        toastr['success']("El Estado ha sido activado", "Aviso");
                        $('#tabla_Estados').DataTable().ajax.reload();
                        id_estado = null;
                    }
                }
            });
        }
    });
}
