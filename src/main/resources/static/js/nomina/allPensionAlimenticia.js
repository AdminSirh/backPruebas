
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
        {data: "persona.fecha_captura"},
        {data: "fecha_antiguedad"},
        {data: "", sTitle: "Pensión Alimenticia", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="asignacionPension(' + r.id_trabajador + ')"><span class="fa fa-edit"></span>Asignación de Pensión</button>';
                return he;
            }
        }]
});


function asignacionPension(id_trabajador) {
    window.location.href = 'asignacionPension?id_trabajador=' + id_trabajador;
}

 
