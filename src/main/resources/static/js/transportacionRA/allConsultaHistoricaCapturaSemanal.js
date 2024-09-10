const nominaTransportacion = 2;
document.addEventListener('DOMContentLoaded', () => {
    $tablaTrabajadores;
});
$tablaTrabajadores = $('#tablaTrabajadores').dataTable({
    "ajax": {
        url: "trabajador/listadoTrabajadoresPlazaTipoNomina/" + nominaTransportacion,
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
        {data: "", sTitle: "Histórico", width: 200, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="redireccionAHistorico(' + r.id_trabajador + ')"><span class="fa fa-history"></span> Consultar Histórico</button>';
                return he;
            }
        }]
});

function redireccionAHistorico(id_trabajador) {
    window.location.href = 'historicoTrabajadorCapturaSemanal?id_trabajador=' + id_trabajador;
}