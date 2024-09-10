/* global data, NaN, Document */
/*Cargar DOM antes de JS*/
document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_estado = urlParams.get('id_estado');
    municipioCargarDatosTabla(id_estado);
    tabs();
});
$("#home").click(function (e) {
    event.preventDefault();
    catalogosAdmin();
});

function tabs() {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=catalogoEstado()>Regresa a Estados</li>&nbsp;");
}

/*=============================================
 Funciones para regresar a los catálogos
 =============================================*/

function catalogosAdmin() {
    window.location.href = 'catalogosAdmin';
}
function catalogoEstado() {
    window.location.href = 'catalogoEstado';
}

/*=============================================
 Función visualización de tabla
 =============================================*/
function verTablaColonia(id_municipio) {
    let municipio = encodeURIComponent('id_municipio=' + id_municipio);
    window.location.href = 'catalogoColonia?' + municipio;
}

/***********************************************
 Carga de datos Municipio
 **************************************************/
function municipioCargarDatosTabla(id_estado) {
    event.preventDefault();
    $('#noteTable').DataTable().destroy();
    $tablaMunicipio = $('#noteTable').dataTable({
        "ajax": {
            //url: "catalogos/listarDatosMunicipio",
            url: "catalogos/buscarMunicipioEstado/" + id_estado,
            method: 'GET',
            dataSrc: ''
        },
        responsive: true,
        bProcessing: true,
        select: true,
        "language": {
            'processing': 'Procesando espera...',
            "sProcessing": "Procesando...",
            "sLengthMenu": "Mostrar _MENU_ Municipios",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": " ",
            "sInfo": " _START_ al _END_ Total: _TOTAL_",
            "sInfoEmpty": " ",
            "sInfoFiltered": "(filtrado de un total de _MAX_ municipios)",
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
            {data: "desc_municipio"},
            {data: "cat_estado.id_estado"},
            {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" onclick="funcionEditar(' + r.id_municipio + ')"><span class="fa fa-edit"></span>Editar</button>';
                    return he;
                }
            },
            {data: "", sTitle: "Cambiar Activo/inactivo", width: 150, visible: true, render: function (d, t, r) {
                    var he;
                    if (r.estatus === 1) {
                        he = '<button type="button" id="btndistrict"' + r.id_municipio + '  class="btn btn-outline-success" class="btn btn-outline-info"onclick="inhabilitar(' + r.id_municipio + ')"> ' + '<span class="fa fa-minus-circle"></span>Desactivar</button>';
                    } else {
                        he = '<button type="button" id="btndistrict"' + r.id_municipio + ' class="btn btn-outline-danger" onclick="activar(' + r.id_municipio + ')"> ' + '<span class="fa fa-minus-circle"></span>Activar</button>';
                    }
                    return he;
                }

            },
            {data: "", sTitle: "Colonias", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-info" onclick="verTablaColonia(' + r.id_municipio + ')"><span class="fa fa-search"></span>Colonias</button>';
                    return he;
                }
            }
        ]
    });
}

/*================================================
 AGREGAR Municipio
 ===================================================*/
$('#agregarMunicipioModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#desc_municipio_agregar").val("");
}); 

$("#agregarMunicipioModal").submit(function (e) {
    event.preventDefault();

    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);

    var id_estado = urlParams.get('id_estado');
    var desc_municipio = $("#desc_municipio_agregar").val();
    let desc_municipio_Mayus = desc_municipio.charAt(0).toUpperCase() + desc_municipio.slice(1);
    var estatus = 1;
    var datos = {"desc_municipio": desc_municipio_Mayus, "cat_estado": {"id_estado": id_estado}, "estatus": estatus};

    $.ajax({
        type: "POST",
        url: "catalogos/guardarMunicipio",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $("#agregarMunicipioModal").modal("hide");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#noteTable').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=============================================
 ACTIVAR O DESACTIVAR MUNICIPIO
 =============================================*/

function inhabilitar(id_municipio) {
     $('#modalStatus').on('hidden.bs.modal', function () {
        id_municipio = null;
    });	
    $("#modalStatus").modal("toggle");
    $("#botonDeshabilitar").click(function (e) {
        if (id_municipio !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estatusMunicipio/" + id_municipio + "/" + 0,
                data: "id_municipio=" + id_municipio, "estatus": 0,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatus").modal('hide');
                        toastr['warning']("El Municipio ha sido inhabilitada", "Aviso");
                        $('#noteTable').DataTable().ajax.reload();
                        id_municipio = null;
                    }
                }
            });
        }
    });
}
function activar(id_municipio) {
    $('#modalStatusActivar').on('hidden.bs.modal', function () {
        id_municipio = null;
    });	
    $("#modalStatusActivar").modal("toggle");
    $("#botonCambiar").click(function (e) {
        if (id_municipio !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estatusMunicipio/" + id_municipio + "/" + 1,
                data: "id_municipio=" + id_municipio, "estatus": 1,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusActivar").modal('hide');
                        toastr['success']("El municipio ha sido habilitado", "Aviso");
                        $('#noteTable').DataTable().ajax.reload();
                        id_municipio = null;
                    }
                }
            });
        }
    });
}

/*=============================================
 EDITAR MUNICIPIO
 =============================================*/
function funcionEditar(id_municipio) {
    $("#editarMunicipioModal").modal("toggle");
    document.getElementById("idMunicipio").value = id_municipio;

    $.ajax({
        type: "GET",
        url: "catalogos/buscarMunicipio/" + id_municipio,
        dataType: 'json',
        success: function (data) {
            $('#desc_municipio_edita').val(data.desc_municipio);
            $('#id_estado_edita').val(data.cat_estado.id_estado);
        },
        error: function (e) {
            alert(e);
        }
    });
}

function actualizar_Datos(id_municipio) {
    $("#editarMunicipioModal").submit(function (e) {
        event.preventDefault();
        var desc_municipio_edita = $("#desc_municipio_edita").val();
        let desc_municipio_edita_Mayus = desc_municipio_edita.charAt(0).toUpperCase() + desc_municipio_edita.slice(1);
        var id_estado_edita = $("#id_estado_edita").val();
        var datos = {"desc_municipio": desc_municipio_edita_Mayus, "cat_estado": {"id_estado": id_estado_edita}};

        if (id_municipio !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/actualizarMunicipio/" + id_municipio,
                data: JSON.stringify(datos),
                contentType: "application/json",
                success: function (data) {
                    $("#editarMunicipioModal").modal('hide');
                    id_municipio = null;
                    if (data.error === 0) {
                        toastr['success']("Municipio Actualizado");
                        $('#noteTable').DataTable().ajax.reload();
                    }
                    if (data.error !== 0) {
                        toastr['error'](data.data, "Aviso");
                    }
                },
                error: function (e) {
                    if (id_municipio !== null) {
                        toastr['error']("No se actualizo el Municipio", "Aviso");
                    }
                }
            });
        }
    });
}