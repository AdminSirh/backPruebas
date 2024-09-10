document.addEventListener('DOMContentLoaded', () => {
    cargaCmbTipoNomina();
    //Evento de escucha al cambio de la nómina 
    $('#cmbTipoNomina').on('change', function () {
        const valorSeleccionadoComboTipoNomina = $(this).val();
        generarTabla(valorSeleccionadoComboTipoNomina);
    });
});

const cargaCmbTipoNomina = (id) => {
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
                    for (var x = 0; x < data.length; x++) {
                        if (data[x].id_tipo_nomina !== 4 && data[x].id_tipo_nomina !== 5) {
                            vselected = (data[x].id_tipo_nomina === id) ? "selected" : "";
                            $('#cmbTipoNomina').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                        }
                    }
                    $('#cmbTipoNomina').val("1");
                    generarTabla($('#cmbTipoNomina').val());
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Tipo Nómina : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};

const generarTabla = (idTipoNomina) => {
    //Se destruye la instancia de la tabla en caso de que haya sido generada anteriormente
    if ($.fn.DataTable.isDataTable('#tabla_Trabajadores')) {
        $('#tabla_Trabajadores').DataTable().destroy();
    }
    $('#tabla_Trabajadores').dataTable({
        "ajax": {
            url: "trabajador/listadoTrabajadoresPlazaTipoNomina/" + idTipoNomina,
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

            {data: "cat_expediente.numero_expediente"},
            {data: "persona.nombre"},
            {data: "persona.apellido_paterno"},
            {data: "persona.apellido_materno"},
            {data: "", sTitle: "Administrar Incidencias", width: 200, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" onclick="administrarTiempoExtra(' + r.id_trabajador + ')"><span class="fa fa-edit"></span>Captura Tiempo Extra</button>';
                    return he;
                }
            }]
    });
};

const administrarTiempoExtra = (id_trabajador) => {
    window.location.href = 'tiempoExtra?id_trabajador=' + id_trabajador;
};