document.addEventListener('DOMContentLoaded', () => {
    cargaCmbTipoNomina();
    vincularEventosInputs();
});

function vincularEventosInputs() {
    //Evento de escucha al cambio en el combo de la nómina
    $('#cmbTipoNomina').on('change', function () {
        const valorSeleccionaoNomina = $(this).val();
        cargaTablaPropiedades(valorSeleccionaoNomina);
    });
    //Evento de escucha al cierre del modal de edición
    $('#editar_propiedad_modal').on('hidden.bs.modal', function () {
        $('#form_modificar_propiedad')[0].reset();
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    });
}

function cargaTablaPropiedades(idNomina) {
    //Se destruye la instancia de la tabla en caso de que haya sido generada anteriormente
    if ($.fn.DataTable.isDataTable('#tabla_propiedades')) {
        $('#tabla_propiedades').DataTable().destroy();
    }
    $tabla_propiedades = $('#tabla_propiedades').dataTable({
        "ajax": {
            url: "propiedadesNomina/encontrarPropiedadesNomina/" + idNomina,
            method: 'GET',
            dataSrc: ''
        },
        responsive: true,
        bProcessing: true,
        select: true,
        //Añadí este autowidth para evitar que se incrmente el ancho de la tabla cada que se vuelve a renderizar la tabla
        "autoWidth": false,
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
        "columns": [
            {
                "data": "cat_Incidencias.propiedad",
                "render": function (data, type, row) {
                    return data ? data : '';
                }
            },
            {
                "data": "valor",
                "render": function (data, type, row) {
                    return data ? data : '';
                }
            },
            {
                "data": "cat_Incidencias.cat_tipo_dato_incidencias.descripcion",
                "render": function (data, type, row) {
                    return data ? data : '';
                }
            },
            {
                "data": "fecha_movimiento",
                "render": function (data, type, row) {
                    return data ? data : '';
                }
            },
            {
                "data": null,
                "sTitle": "Editar",
                "width": 100,
                "visible": true,
                "render": function (data, type, row) {
                    return '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#editar_propiedad_modal" onclick="editarPropiedad(' + row.id_propiedad + ') ;propiedadCargaDatosForm(' + row.id_propiedad + ')"><span class="fa fa-edit"></span>Editar</button>';
                }
            }
        ]
    });
}

function cargaCmbTipoNomina(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                var vselected = "";
                if (data.length > 0) {
                    $('#cmbTipoNomina').empty(); // Limpiar opciones anteriores
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_tipo_nomina === id) ? "selected" : "";
                        $('#cmbTipoNomina').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                    }
                    cargaTablaPropiedades($('#cmbTipoNomina').val());
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Tipo Nómina : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

function editarPropiedad(id) {
    //Remoción de mensajes de validación y reset de id recibido
    $('#editar_propiedad_modal').on('hidden.bs.modal', function () {
        id = null;
    });

    $("#form_modificar_propiedad").off('submit').on('submit', function (e) {
        e.preventDefault();
        let valor = $("#valor_propiedad").val();
        if ($.trim(valor) === "") {
            return false;
        }
        let datos = {"valor": valor};
        $.ajax({
            type: "POST",
            url: "propiedadesNomina/editarPropiedadNomina/" + id,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(datos),
            success: function (data) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información para modificar", "Aviso", {progressBar: true, closeButton: true});
                }
                $("#editar_propiedad_modal").modal('hide');
                toastr['success']("Se modificó con éxito", "Aviso");
                $('#tabla_propiedades').DataTable().ajax.reload();
                //Reset de Variable para evitar duplicados al editar de manera continua.
                id = null;
            },
            error: function (e) {
                toastr["error"]("Error al guardar los datos : " + e, "Error", {progressBar: true, closeButton: true});
            }
        });
    });
}

function propiedadCargaDatosForm(id) {
    $.ajax({
        type: "GET",
        url: "propiedadesNomina/buscarPropiedad/" + id,
        dataType: 'json',
        success: function (data) {
            (data.data.cat_Incidencias === null) ? $('#descripcion_propiedad').val("") : $('#descripcion_propiedad').val(data.data.cat_Incidencias.propiedad);
            (data.data.valor === null) ? $('#valor_propiedad').val("") : $('#valor_propiedad').val(data.data.valor);
        },
        error: function (e) {
            toastr["error"]("Error al recuperar la propiedad : " + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}