document.addEventListener('DOMContentLoaded', () => {
    cmbBimestres();
    cmbTipoNomina();
});

function cmbBimestres(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarBimestres",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información de bimestres...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbBimestre').empty().append("<option value=''>* Selecciona</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_bimestre === id) ? "selected" : "";
                        $('#cmbBimestre').append('<option value="' + data[x].id_bimestre + '"' + vselected + '>' + data[x].bimestre + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Bimestre: " + e.responseText, "Error", {progressBar: true, closeButton: true});
        }
    });
}

function cmbTipoNomina(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                //Seleccion de las nominas con ID 1 y 3
                $('#cmbNomina').empty().append('<option value="">*Selecciona una nómina</option>');
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        if (data[x].id_tipo_nomina !== 4) {
                            var vselected = (data[x].id_tipo_nomina === id) ? "selected" : "";
                            $('#cmbNomina').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                        }
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

function homeSdi() {
    window.location.href = 'sdiAdmin';
}

$("#cmbNomina").change(function () {
    generarTablaTrabajadoresSdi();
});

$("#cmbBimestre").change(function () {
    generarTablaTrabajadoresSdi();
});

function generarTablaTrabajadoresSdi() {
    let idNomina = $('#cmbNomina').val();
    let idBimestre = $('#cmbBimestre').val();
    //Nos aseguramos que el valor de la nómina siempre esté presente
    if (!idNomina) {
        toastr["warning"]("El tipo de nómina es obligatorio ", " Advertencia", {progressBar: true, closeButton: true});
        return;
    }
    //Se contruye la url con el id de la nómina
    let url = 'calculoSdi/findTrabajadoresNominaBimestreSdi?idNomina=' + idNomina;
    if (idBimestre) {
        url += '&idBimestre=' + idBimestre;
    }
    //Se destruye la instancia de la tabla si ya fue creada
    if ($.fn.DataTable.isDataTable('#tabla_listado_sdi')) {
        $('#tabla_listado_sdi').DataTable().destroy();
    }
    tablaTrabajadoresSdi = $('#tabla_listado_sdi').dataTable({
        "ajax": {
            url: url,
            method: 'GET',
            dataSrc: ''
        },
        responsive: true,
        bProcessing: true,
        select: true,
        //Añadí este autowidth para evitar que se incrmente el ancho de la tabla cada que se vuelve a dibujar la tabla
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
            {data: "numero_expediente"},
            {data: "nombre"},
            {data: "apellido_paterno"},
            {data: "apellido_materno"},
            {data: "desc_bimestre"},
            {data: "", sTitle: "Consultar S.D.I", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    var he = '<button type="button" class="btn btn-outline-primary" onclick="verInformacionBimestreTrabajador(' + r.id_trabajador + ', ' + r.id_bimestre + ')"><span class="fa fa-edit"></span> S.D.I. Trabajador </button>';
                    return he;
                }
            }]
    });
}

function verInformacionBimestreTrabajador(idTrabajador, idBimestre) {
    const param1 = encodeURIComponent(idTrabajador);
    const param2 = encodeURIComponent(idBimestre);
    const url = `sdiDetalleTrabajador?id_trabajador=${param1}&id_bimestre=${param2}`;
    window.location.href = url;
}

/**
 * Genera el excel con los cálculos realizados en el backend provenientes de la tabla 
 * tmp_sdi_calculos_bimestre según la nómina y el bimestre seleccionado
 */
function descargarCalculos() {
    let idNomina = $('#cmbNomina').val();
    let idBimestre = $('#cmbBimestre').val();
    if (!idNomina || !idBimestre) {
        toastr["warning"]("Debe seleccionar una nómina o bimestre", "Advertencia", {progressBar: true, closeButton: true});
        return;
    }
    $.ajax({
        url: "calculoSdi/exportarConceptosCalculados/" + idNomina + "/" + idBimestre,
        type: "GET",
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, textStatus, xhr) {
            var filename = "";
            var disposition = xhr.getResponseHeader('Content-Disposition');
            if (disposition && disposition.indexOf('attachment') !== -1) {
                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                var matches = filenameRegex.exec(disposition);
                if (matches != null && matches[1])
                    filename = matches[1].replace(/['"]/g, '');
            }
            var url = window.URL.createObjectURL(data);
            var a = document.createElement('a');
            a.href = url;
            //Se obtiene el nombre que se genera en el servidor
            a.download = filename || 'calculados.xlsx';
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
            toastr["success"]("Se generó correctamente el archivo excel", "Aviso", {progressBar: true, closeButton: true});
        },
        error: function (xhr, status, error) {
            toastr["error"]("Ocurrió un error " + error, "Error", {progressBar: true, closeButton: true});
        }
    });
}