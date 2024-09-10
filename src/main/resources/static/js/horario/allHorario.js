/* global $tablaExpediente, finalName, cadena3, slice, consoloe, $tablaHorarios */
document.addEventListener('DOMContentLoaded', () => {
    //$tablaTrabajadores;
    tipo_nomina();
    const tipoNominaSelect = $('#tipo_nomina');
    const id_inicial = '1';
    tipoNominaSelect.val(id_inicial);
    loadTable(id_inicial);

    tipoNominaSelect.on('change', function () {
        const selectedValue = $(this).val();
        loadTable(selectedValue);
    });
});

const loadTable = (id_nomina) => {
    if ($('#tabla_Trabajadores')) {
        $('#tabla_Trabajadores').DataTable().destroy();
    }
    $tablaTrabajadores = $('#tabla_Trabajadores').dataTable({
        "ajax": {
            url: "trabajador/listadoTrabajadoresPlazaTipoNomina/" + id_nomina,
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
            {data: "persona.curp"},
            {data: "persona.fecha_captura", render: function (data, type, row) {
                    var fechaCaptura = new Date(data);
                    return fechaCaptura.toLocaleDateString();
                }},
            {data: "", sTitle: "Horario", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" onclick="Horario(' + r.id_trabajador + ')"><span class="fa fa-edit"></span>Horarios</button>';
                    return he;
                }
            }]
    });

};

const tipo_nomina = (id) => {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                //Seleccion de todas las nominas id 6
                //$('#tipo_nomina').empty().append("<option>* </option>");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        if (data[x].id_tipo_nomina !== 1) {  // Excluye el elemento con ID 1
                            vselected = (data[x].id_tipo_nomina === id) ? "selected" : "";
                            $('#tipo_nomina').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                        }
                    }
                    $('#tipo_nomina').val("1");
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};



function Horario(id_trabajador) {
    window.location.href = 'trabajadorHorario?id_trabajador=' + id_trabajador;
}

//--------Limpiar los datos de un formulario---------
/*$("#btn_CancelarHor").on("click", function (event) {
 $("#FormGuardarHorario")[0].reset();
 });*/


