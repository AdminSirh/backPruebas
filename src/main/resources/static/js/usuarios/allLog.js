document.addEventListener('DOMContentLoaded', () => {



});

$("#formLogUsuarios").submit(function (e) {
    event.preventDefault();
    $('#tabla_log').DataTable().destroy();
    var desde = $("#fecha_desde").val();
    var hasta = $("#fecha_hasta").val();


    if ($.trim(desde) === "") {
        return false;
    }
    if ($.trim(hasta) === "") {
        return false;
    }

    $('#tabla_log').dataTable({
        "ajax": {
            url: "adminLog/listarMovimientos/" + desde + "/" + hasta,
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
            {data: "user_id"},
            {data: "user_name"},
            {data: "log_id"},
            {data: "type"},
            {data: "operation"},
            {data: "remote_addr"},
            {data: "request_uri"},
            {data: "method"},
            {data: "params"},
            {data: "createdAt"}
            //  {data: "result_params"}
        ]

    }).draw();

});
