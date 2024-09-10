document.addEventListener('DOMContentLoaded', () => {
    tabla();
    comparaFechasCorte();
});

//LISTA A LOS TRABAJADORES CON NOMINA PARA PODER APLICAR DESCUENTOS
function tabla() {
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
            {data: "", sTitle: "Fondo Auxilio", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" onclick="capturaDescuento(' + r.id_trabajador + ')"><span class="fa fa-edit"></span>Captura Descuento</button>';
                    return he;
                }
            }]
    });
}
//COMPARA EN LAS TABLA FECHAS DE CORTE PARA SABER SI YA SE REALIZARON LOS DESCUENTOS ESE DÍA
function comparaFechasCorte() {
    var hoy = new Date();
    var hoy_formato = formatearFecha(hoy);
    var fechas_corte = [];
    $.ajax({
        type: "GET",
        url: "fondoAuxilio/listarFechasCorteDescuentos",
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
//DEVUELVE LA FECHA EN FORMATO YY-MM-DD
function formatearFecha(fecha) {
    var date = new Date(fecha);

    var year = date.getFullYear();
    var month = (date.getMonth() + 1).toString().padStart(2, '0'); // Agrega ceros a la izquierda si es necesario
    var day = date.getDate().toString().padStart(2, '0'); // Agrega ceros a la izquierda si es necesario

    return year + '-' + month + '-' + day; // Formato: AAAA-MM-DD
}
//REALIZA LOS DESCUENTOS EN LA TABLA CUENTAS POR COBRAR Y ACTUALIZACION DE CUENTAS
function descuentos() {
    $.ajax({
        type: "GET",
        url: "fondoAuxilio/listarCuentasActivasAuxilio",
        dataType: 'json',
        success: function (data) {
            comparaFechasCorte();
            toastr['success']("Descuento realizado");
            $("#confirmacionModal").modal('hide');
        },
        error: function (e) {
            alert(e);
        }
    });
}

//MENSAJE DE CONFIRMACION PARA GENERAR LOS DESCUENTOS DE FONDO DE AUXILIO
function confirmacion() {
    $("#confirmacionModal").modal();
}


function capturaDescuento(id_trabajador) {
    window.location.href = 'descuentoFondoAuxilio?id_trabajador=' + id_trabajador;
}


