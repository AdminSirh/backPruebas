$(document).ready(function () {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var idTrabajador = urlParams.get('id_trabajador');
    tablaDatosTrabajador();
    relacionarEventos(idTrabajador);
});

function relacionarEventos(idTrabajador) {
    // Delegación de eventos para detectar clic en el botón "Siguiente"
    $(document).on('click', '#tabla_datos_trabajador_next', function () {
        redirigirSegunBoton('siguiente', idTrabajador);
    });
    // Delegación de eventos para detectar clic en el botón "Anterior"
    $(document).on('click', '#tabla_datos_trabajador_previous', function () {
        redirigirSegunBoton('anterior', idTrabajador);
    });
}

function tablaDatosTrabajador() {
    tabla = $('#tabla_datos_trabajador').DataTable({
        "ajax": {
            url: "trabajador/listadoTrabajadoresPlazaTipoNomina/" + nominaTransportacion, //Se obtiene la constante del otro js
            method: 'GET',
            dataSrc: ''
        },
        responsive: false,
        bProcessing: true,
        select: true,
        //Ocultar búsqueda
        searching: false,
        info: false,
        //Ocultar mostrar x registros
        bLengthChange: false,
        lengthMenu: [1],
        //No mostrar números entre paginación
        pagingType: "simple",
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
                "previous": 'Trabajador Anterior',
                "next": 'Trabajador Siguiente'
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
        dom: 'p',
        columns: [
            {data: "cat_expediente.numero_expediente"},
            {
                data: function (row) {
                    return row.persona.nombre + ' ' + row.persona.apellido_paterno + ' ' + row.persona.apellido_materno;
                },
                title: "Nombre Completo"
            }
        ]
    });
}
;

// Redirigir al siguiente o anterior registro según el botón presionado
function redirigirSegunBoton(direccion, idTrabajador) {
    var idTrabajadorNumero = parseInt(idTrabajador);
    var tabla = $('#tabla_datos_trabajador').DataTable();
    var filaSeleccionada;

    // Recorrer todas las filas
    for (var i = 0; i < tabla.rows().data().length; i++) {
        var datosFila = tabla.row(i).data();

        // Si coinciden la fila con el id del trabajador de la url entonces se asigna el valor de la fila seleccionada como el actual
        if (datosFila.id_trabajador === idTrabajadorNumero) {
            filaSeleccionada = tabla.row(i);
            break;
        }
    }

    var idRegistro = filaSeleccionada.data().id_trabajador;
    var nuevaURL;

    if (direccion === 'siguiente') {
        var siguienteFila = tabla.row(filaSeleccionada.index() + 1).data();
        if (siguienteFila && siguienteFila.id_trabajador !== idTrabajadorNumero) {
            idRegistro = siguienteFila.id_trabajador;
            nuevaURL = '/capturaIncidenciasTransportacion?id_trabajador=' + idRegistro;
        } else {
            toastr.info('No hay más registros siguientes disponibles.', 'Información');
        }
    } else if (direccion === 'anterior') {
        var anteriorFila = tabla.row(filaSeleccionada.index() - 1).data();
        if (anteriorFila && anteriorFila.id_trabajador !== idTrabajadorNumero) {
            idRegistro = anteriorFila.id_trabajador;
            nuevaURL = '/capturaIncidenciasTransportacion?id_trabajador=' + idRegistro;
        } else {
            toastr.info('No hay más registros siguientes anteriores disponibles.', 'Información');
        }
    }

    if (nuevaURL) {
        window.location.href = nuevaURL;
    }
}

