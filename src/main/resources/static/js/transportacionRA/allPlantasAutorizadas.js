document.addEventListener('DOMContentLoaded', () => {
    $tabla_plantas;
});

$tabla_plantas = $('#tabla_plantas').dataTable({
    "ajax": {
        url: "ra15/listarPlantasActivas",
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
        {data: "expediente"},
        {data: "relevo_1"},
        {data: "relevo_2"},
        {data: "descansos"},
        {data: "corrida"},
        {data: "entra_semana"},
        {data: "sale_semana"},
        {data: "horas_trab_semana"},
        {data: "pago_semana"},
        {data: "entra_sabado"},
        {data: "sale_sabado"},
        {data: "horas_trab_sabado"},
        {data: "pago_sabado"},
        {data: "corrida_sabado"},
        {data: "entra_domingo"},
        {data: "sale_domingo"},
        {data: "horas_trab_domingo"},
        {data: "pago_domingo"},
        {data: "corrida_domingo"}
//        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
//                var he;
//                he = '<button type="button" class="btn btn-outline-primary" onclick="editarRegistroPlanta(' + r.id_planta + ')" > ' + '<span class="fa fa-edit"> </span> Editar</button>';
//                return he;
//            }}
        //{data: "linea"},
        //{data: "deposito"}
    ]
});

function editarRegistroPlanta(idPlanta) {
    console.log(idPlanta);
}