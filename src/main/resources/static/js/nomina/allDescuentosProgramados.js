
document.addEventListener('DOMContentLoaded', () => {
    extraccionSindical();
    prestaciones_Datos();
    comisionado_Datos();
    $tablaTrabajadores;
});


$tablaTrabajadores = $('#tabla_Trabajadores').dataTable({
    "ajax": {
        url: "trabajador/listado",
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
        "searchPlaceholder": "Buscar",
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
        {data: "", sTitle: "Importacion de incidencias", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="GuardaDescuentos(' + r.id_trabajador + ')"><span class="fa fa-edit"></span>Descuentos</button>';
                return he;
            }
        }]
});


function GuardaDescuentos(id_trabajador) {
    window.location.href = 'capturaDescuentosProgramados?id_trabajador=' + id_trabajador;
}


