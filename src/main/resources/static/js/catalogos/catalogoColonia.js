/* global data, NaN, Document */
/*Cargar DOM antes de JS*/
document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_municipio = urlParams.get('id_municipio');
    coloniaCargarDatosTabla(id_municipio);
    tabs();
});

//Regresa a lista de catálogos cuando se da click sobre el Heading "Administración de catálogos"
$("#home").click(function (e) {
    event.preventDefault();
    catalogosAdmin();
});

function tabs() {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=catalogoEstado()>Regresa a Estados</li>&nbsp;");
}
function catalogosAdmin() {
    window.location.href = 'catalogosAdmin';
}
function catalogoEstado() {
    window.location.href = 'catalogoEstado';
}

/*===================================================
 Ejecutar Tabla
 ======================================================*/
function coloniaCargarDatosTabla(id_municipio) {
    event.preventDefault();
    $('#noteTable').DataTable().destroy();
    tablaCatalogoColonia = $('#noteTable').DataTable({
        "ajax": {
            url: "catalogos/buscarMunicipiosColonia/" + id_municipio,
            method: 'GET',
            dataSrc: ''
        },
        responsive: true,
        bProcessing: true,
        select: true,
        "language": {
            "processing": "Procesando espera...",
            "sProcessing": "Procesando...",
            "sLengthMenu": "Mostrar _MENU_ colonias",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": " ",
            "sInfo": "_START_ al _END_ Total: _TOTAL_",
            "sInfoEmpty": " ",
            "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
            "sInfoPostFix": "",
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
            {data: "cat_municipio.id_municipio"},
            {data: "desc_colonia"},
            {data: "cod_postal"},
            {data: "tipo_asentamiento"},
            {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" onclick="funcionEditar(' + r.id_colonia + ')" > ' + '<span class="fa fa-edit"> </span> Editar</button>';
                    return he;
                }
            },
            {data: "", sTitle: "Cambiar Activo/inactivo", width: 150, visible: true, render: function (d, t, r) {
                    var he;
                    if (r.estatus === 1) {
                        he = '<button type="button" id="btndistrict"' + r.id_colonia + '  class="btn btn-outline-success" class="btn btn-outline-info"onclick="inhabilitar(' + r.id_colonia + ')"> ' + '<span class="fa fa-minus-circle"></span>Desactivar</button>';
                    } else {
                        he = '<button type="button" id="btndistrict"' + r.id_colonia + ' class="btn btn-outline-danger" onclick="activar(' + r.id_colonia + ')"> ' + '<span class="fa fa-minus-circle"></span>Activar</button>';
                    }
                    return he;
                }

            }
        ]
    });
}
/*================================================
 AGREGAR COLONIAS
 ===================================================*/
//Limpieza de agrega
$('#agregarColoniaModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#desc_colonia_agregar").val("");
   $("#cod_postal_agregar").val("");
   $("#tipo_asentamiento_agregar").val("");
}); 

$("#agregarColoniaModal").submit(function (e) {
    event.preventDefault();

    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_municipio = urlParams.get('id_municipio');

    var desc_colonia = $("#desc_colonia_agregar").val();
    var cod_postal = $("#cod_postal_agregar").val();
    var tipo_asentamiento = $("#tipo_asentamiento_agregar").val();
    
    
    var datos = {"cat_municipio": {"id_municipio": id_municipio}, "desc_colonia": desc_colonia, "cod_postal": cod_postal, "tipo_asentamiento": tipo_asentamiento};
    
    $.ajax({
        type: "POST",
        url: "catalogos/guardarCatColonia",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $("#agregarColoniaModal").modal("hide");
            toastr['success']("Se ha agregado corretcamente el elemento");
            tablaCatalogoColonia.ajax.reload(null, false);

        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=============================================
 LIMPIAR FORMULARIO
 =============================================*/
$("#btn_Cancelar").on("click", function (event) {
    id_colonia=null;
});

/*=============================================
 ACTIVAR O DESACTIVAR COLONIAS
 =============================================*/

function inhabilitar(id_colonia) {
    $('#modalStatus').on('hidden.bs.modal', function () {
        id_colonia = null;
    }); 
    $("#modalStatus").modal("toggle");
    $("#botonDeshabilitar").click(function (e) {
        if (id_colonia !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/estatusColonia/" + id_colonia + "/" + 0,
                data: "id_colonia=" + id_colonia, "estatus": 0,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatus").modal('hide');
                        toastr['warning']("La Colonia ha sido inhabilitada", "Aviso");
                        // $tablaUsuarios.ajax.reload();

                        $('#noteTable').DataTable().ajax.reload();
                        id_colonia = null;
                    }
                }
            });
        }
    });
}

function activar(id_colonia) {
    $('#modalStatusActivar').on('hidden.bs.modal', function () {
        id_colonia = null;
    }); 
    $("#modalStatusActivar").modal("toggle");
    $("#botonCambiar").click(function (e) {
        if (id_colonia !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/estatusColonia/" + id_colonia + "/" + 1,
                data: "id_colonia=" + id_colonia, "estatus": 1,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatusActivar").modal('hide');
                        toastr['success']("La Colonia ha sido habilitada", "Aviso");
                        $('#noteTable').DataTable().ajax.reload();
                        id_colonia = null;
                    }
                }
            });
        }
    });
}

/*=============================================
 EDITAR COLONIA
 =============================================*/
function funcionEditar(id_colonia) {
    event.preventDefault();
    $("#editarColoniaModal").modal("toggle");
    document.getElementById("idColonia").value = id_colonia;
    $.ajax({
        type: "GET",
        url: "catalogos/buscarColonia/" + id_colonia,
        dataType: 'json',
        success: function (data) {
            $('#desc_colonia_edita').val(data.data.desc_colonia);
            $('#cod_postal_edita').val(data.data.cod_postal);
            $('#tipo_asentamiento_edita').val(data.data.tipo_asentamiento);
            $('#id_municipio_edita').val(data.data.cat_municipio.id_municipio);
        },
        error: function (e) {
            alert(e);
        }
    });
}

function actualizar_Datos(id_colonia) {
    
    $('#editarColoniaModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_colonia = null;
    });
    
    $("#editarColoniaModal").submit(function (e) {
        event.preventDefault();
        const valores = window.location.search;
        const d = decodeURIComponent(valores);
        const urlParams = new URLSearchParams(d);
        var id_municipio = urlParams.get('id_municipio');

        var desc_colonia_edita = $("#desc_colonia_edita").val();
        var cod_postal_edita = $("#cod_postal_edita").val();
        var tipo_asentamiento_edita = $("#tipo_asentamiento_edita").val();
        var datos = {"cat_municipio": {"id_municipio": id_municipio}, "desc_colonia": desc_colonia_edita, "cod_postal": cod_postal_edita, "tipo_asentamiento": tipo_asentamiento_edita};
        if (id_colonia !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/actualizarCatColonia/" + id_colonia,
                data: JSON.stringify(datos),
                contentType: "application/json",
                success: function (data) {
                    $("#editarColoniaModal").modal('hide');
                    if (data.error === 0) {
                        toastr['success']("Colonia Azualizada");
                        $('#noteTable').DataTable().ajax.reload();
                        id_colonia = null;

                    }
                    if (data.error !== 0) {
                        toastr['error'](data.data, "Aviso");
                    }
                },
                error: function (e) {
                    toastr['error']("No se actualizo la Colonia", "Aviso");
                }
            });
    }
    });
}


