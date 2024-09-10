document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    listarCuenta(trabajador_id);
    
});

function listarCuenta(trabajador_id){
    buscaExpediente(trabajador_id);
    $tablaCuentas = $('#tabla_Cuentas').DataTable({
    "ajax": {
        url: 'creditoPersonal/listarCuentaPorTrabajadorPersonal/'+trabajador_id,
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
        {data: "",
                render: function (data, type, row) {
                    var expediente = $('#expediente').val();
                    return expediente;
                }
        },
        {data: "fecha_movimiento"},
        {data: "saldo_inicial"},
        {data: "saldo_actual"},
        {data: "monto_pago"},
        {data: "numero_periodos_pago"},
        {data: "periodo_pago_actual"},
        {data: "remanente_ultimo_pago"}
        
        ]
    });
}

// BUSCA EL EXPEDIENTE DEL TRABAJADOR PARA LISTARLO EN LA TABLA
function buscaExpediente(trabajador_id){
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + trabajador_id,
        dataType: 'json',
        success: function (data) {
            $('#nombre').val(data.data.persona.nombre + " " + data.data.persona.apellido_paterno + " " +data.data.persona.apellido_materno);
            $('#expediente').val(data.data.cat_expediente.numero_expediente);
            
        },
        error: function (e) {
            alert(e);
        }
    });   
}


function prestamosPersonalesAdmin() {
    window.location.href = 'prestamosPersonalesAdmin';
}