document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    bloqueaBotonGuardar(trabajador_id);
});

function listarPagos(trabajador_id){
    var id_cuenta_por_cobrar = $('#id_cuenta_por_cobrar').val();
    $tabla_Pagos = $('#tabla_Pagos').DataTable({
        
    "ajax": {
        url: 'cuponesCargo/listarCuentasCuponesActuales/'+trabajador_id + "/" + id_cuenta_por_cobrar,
        method: 'GET',
        dataSrc: ''
    },
    responsive: true,
    bProcessing: true,
    select: true,
    "language": {
        "processing": "Procesando espera...",
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
        {data: "trabajador.cat_expediente.numero_expediente"},
        {data: "abono"},
        {data: "periodo"},
        {data: "saldo_actual"}
        ]
    });
}

function bloqueaBotonGuardar(id_trabajador){
    $.ajax({
        type: "GET",
        url: 'cuponesCargo/listarCuentaPorTrabajador/'+id_trabajador,
        dataType: 'json',
        success: function (data) {
            
            if (data !== undefined) {
                if (data[0].id_cupones_cuentas_cobrar !== null && data[0].estatus === 1) {
                    $('#id_cuenta_por_cobrar').val(data[0].id_cupones_cuentas_cobrar);
                    listarPagos(id_trabajador);
                    
                }
            }
        },
        error: function (e) {
            alert(e);
        }
    });
}


function cuponesCargoAdmin() {
    window.location.href = 'cuponesCargoAdmin';
}