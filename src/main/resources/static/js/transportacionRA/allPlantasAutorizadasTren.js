document.addEventListener('DOMContentLoaded', () => {
    $tabla_plantas_tren;
});

//Retorno al plantas autorizadas operadores y patio
const tomaPlantasAutorizadas = () => {
    window.location.href = 'libroTurnosAutorizadosRA15?tab=tab-6&tab=tab-7';
};

$tabla_plantas_tren = $('#tabla_plantas_tren').DataTable({
    "ajax": {
        url: "ra15/listarPlantasActivasTren",
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
        {data: "descansos"},
        {data: "cat_Dias.dia"},
        {data: "pago"}
    ]
});