/* global $tablaExpediente, finalName, cadena3, slice, consoloe, $tablaHorarios */
document.addEventListener('DOMContentLoaded', () => {
    $tablaTrabajadores;
});


$tablaTrabajadores = $('#tabla_Trabajadores').dataTable({
    "ajax": {
        url: "trabajador/listadoTrabajadoresPlaza",
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
        {data: "persona.fecha_nacimiento"},
        {data: "persona.cat_genero.desc_genero"},
        {data: "persona.cat_estado_civil.desc_edo_civil"},
        {data: "persona.curp"},
        {data: "persona.fecha_captura", render: function (data, type, row) {
                var fechaCaptura = new Date(data);
                return fechaCaptura.toLocaleDateString();
            }},
        {data: "fecha_antiguedad", render: function (data, type, row) {
                var fechaCaptura = new Date(data);
                return fechaCaptura.toLocaleDateString();
            }},
        {data: "", sTitle: "Generar Credencial", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="administrarCredencial(' + r.id_trabajador + ')"><span class="fa fa-edit"></span>Credencial</button>';
                return he;
            }
        }]
});


function administrarCredencial(id_trabajador) {
    window.location.href = 'generaCredencial?id_trabajador=' + id_trabajador;
}

