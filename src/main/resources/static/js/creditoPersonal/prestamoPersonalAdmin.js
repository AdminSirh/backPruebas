document.addEventListener('DOMContentLoaded', () => {
    tabla();
    comparaFechasCorte();
});

function tabla() {
    $tablaTrabajadores = $('#tabla_Trabajadores').dataTable({

        "ajax": {
            url: "trabajador/listarCandidatosCreditoPersonal",
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

            {data: "numero_expedienteDTO"},
            {data: "apellido_paternoDTO"},
            {data: "apellido_maternoDTO"},
            {data: "nombreDTO"},
            {data: "curpDTO"},
            {data: "desc_generoDTO"},
            {data: "desc_edo_civilDTO"},
            {data: "fecha_ingresoDTO", render: function (data, type, row) {
                    var fechaCaptura = new Date(data);
                    return fechaCaptura.toLocaleDateString();
                }},
            {data: "desc_areaDTO"},
            {data: "codigo_areaDTO"},
            {data: "", sTitle: "Préstamo", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" onclick="capturaCredito(' + r.id_trabajadorDTO + ')"><span class="fa fa-edit"></span>Captura Crédito</button>';
                    return he;
                }
            }]
    });
}

function comparaFechasCorte() {
    var hoy = new Date();
    var hoy_formato = formatearFecha(hoy);
    var fechas_corte = [];
    $.ajax({
        type: "GET",
        url: "creditoPersonal/listarFechasCortePrestamos",
        dataType: 'json',
        success: function (data) {
            if (data !== undefined) {
                for (var i = 0; i < data.length; i++) {
                    var fechaCorteFormateada = formatearFecha(data[i].fecha_corte);
                    fechas_corte.push(fechaCorteFormateada);
                }
                if (fechas_corte.includes(hoy_formato)) {
                    const boton_descuetos = document.getElementById('boton_descuentos');
                    boton_descuetos.disabled = true;
                }
            } else {
                const boton_descuetos = document.getElementById('boton_descuentos');
                boton_descuetos.disabled = false;
            }


        },
        error: function (e) {
            alert(e);
        }
    });
}

function formatearFecha(fecha) {
    var date = new Date(fecha);

    var year = date.getFullYear();
    var month = (date.getMonth() + 1).toString().padStart(2, '0'); // Agrega ceros a la izquierda si es necesario
    var day = date.getDate().toString().padStart(2, '0'); // Agrega ceros a la izquierda si es necesario

    return year + '-' + month + '-' + day; // Formato: AAAA-MM-DD
}

//MENSAJE DE CONFIRMACION PARA GENERAR LOS DESCUENTOS DE FONDO DE AUXILIO
function confirmacion() {
    $("#confirmacionModal").modal();
}

function descuentaPrestamos() {
    $.ajax({
        type: "GET",
        url: "creditoPersonal/listarCuentasActivasPersonal",
        dataType: 'json',
        success: function (data) {
            comparaFechasCorte();
            toastr['success']("Descuento realizado");
            $("#confirmacionModal").modal("hide");
        },
        error: function (e) {
            alert(e);
        }
    });
}


function capturaCredito(id_trabajador) {
    window.location.href = 'descuentoPersonal?id_trabajador=' + id_trabajador;
}







