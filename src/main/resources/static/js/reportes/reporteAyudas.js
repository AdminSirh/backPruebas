let urlAyudas = "ayuda/listarAyudas";
let fechaActual = new Date();

document.addEventListener('DOMContentLoaded', () => {
    //Se selecciona todos la cual es la opción inicial del filtrado
    var radioBtnTodos = $('#radioBtnTodos');
    radioBtnTodos.prop('checked', true);
    generarTablaAyudas(urlAyudas);

    //Escucha a la selección de los botones de radio
    $('input[name="opcion"]').change(function () {
        //Se coloca el valor por defecto para el tiempo al cambiar el checkbox
        $("#cmbPeriodosTiempo").val("todos");
        let valorSeleccionadoTiempo = $("#cmbPeriodosTiempo").val();
        let valorSeleccionadoEstatus = $('input[name="opcion"]:checked').val();
        manejoInputsFiltradoUsuario(valorSeleccionadoEstatus, valorSeleccionadoTiempo, fechaActual);
    });
    //Escucha al combo de selección del tiempo
    $("#cmbPeriodosTiempo").change(function () {
        let valorSeleccionadoEstatus = $('input[name="opcion"]:checked').val();
        let opcionSeleccionadaPeriodoTiempo = $(this).val();
        manejoInputsFiltradoUsuario(valorSeleccionadoEstatus, opcionSeleccionadaPeriodoTiempo, fechaActual);
    });

});

const manejoInputsFiltradoUsuario = (valorSeleccionadoEstatus, opcionSeleccionadaPeriodoTiempo, fechaActual) => {
    // Función para obtener el inicio de la semana
    const getInicioSemana = (fecha) => {
        var inicioSemana = new Date(fecha);
        inicioSemana.setDate(fecha.getDate() - fecha.getDay());
        return inicioSemana;
    };

    // Función para obtener el final de la semana
    const getFinSemana = (fecha) => {
        var finSemana = new Date(fecha);
        finSemana.setDate(fecha.getDate() - fecha.getDay() + 6);
        return finSemana;
    };

    // Función para obtener el inicio del mes
    const getInicioMes = (fecha) => {
        var inicioMes = new Date(fecha.getFullYear(), fecha.getMonth(), 1);
        return inicioMes;
    };

    // Función para obtener el final del mes
    const getFinMes = (fecha) => {
        var finMes = new Date(fecha.getFullYear(), fecha.getMonth() + 1, 0);
        return finMes;
    };

    const formateaFecha = (fecha) => {
        return fecha.getFullYear() + '-' + (fecha.getMonth() + 1).toString().padStart(2, '0') + '-' + fecha.getDate().toString().padStart(2, '0');
    };

    let urlReporte;
    // *********************ESTATUS TODOS************************************
    if (valorSeleccionadoEstatus === 'todos' && opcionSeleccionadaPeriodoTiempo === 'todos') {
        urlAyudas = "ayuda/listarAyudas";
        urlReporte = "report/reporteAyudas?tipo=PDF";
        generarTablaAyudas(urlAyudas);
        // TODOS / SEMANA ACTUAL
    } else if (valorSeleccionadoEstatus === 'todos' && opcionSeleccionadaPeriodoTiempo === 'semanaActual') {
        var inicioSemana = formateaFecha(getInicioSemana(fechaActual));
        var finSemana = formateaFecha(getFinSemana(fechaActual));
        urlAyudas = "ayuda/listarAyudas?fechaFinParametro=" + finSemana + "&fechaInicioParametro=" + inicioSemana;
        urlReporte = "report/reporteAyudas?tipo=PDF&fechaInicio=" + inicioSemana + "&fechaFin=" + finSemana;
        generarTablaAyudas(urlAyudas);
        //TODOS / MES ACUAL
    } else if (valorSeleccionadoEstatus === 'todos' && opcionSeleccionadaPeriodoTiempo === 'mesActual') {
        var inicioMes = formateaFecha(getInicioMes(fechaActual));
        var finMes = formateaFecha(getFinMes(fechaActual));
        urlAyudas = "ayuda/listarAyudas?fechaFinParametro=" + finMes + "&fechaInicioParametro=" + inicioMes;
        urlReporte = "report/reporteAyudas?tipo=PDF&fechaInicio=" + inicioMes + "&fechaFin=" + finMes;
        generarTablaAyudas(urlAyudas);
        //TODOS / MES ANTERIOR
    } else if (valorSeleccionadoEstatus === 'todos' && opcionSeleccionadaPeriodoTiempo === 'mesAnterior') {
        let fechaActualCopia = new Date(fechaActual);
        fechaActualCopia.setMonth(fechaActualCopia.getMonth() - 1);
        var inicioMesAnterior = formateaFecha(getInicioMes(fechaActualCopia));
        var finMesAnterior = formateaFecha(getFinMes(fechaActualCopia));
        urlAyudas = "ayuda/listarAyudas?fechaFinParametro=" + finMesAnterior + "&fechaInicioParametro=" + inicioMesAnterior;
        urlReporte = "report/reporteAyudas?tipo=PDF&fechaInicio=" + inicioMesAnterior + "&fechaFin=" + finMesAnterior;
        generarTablaAyudas(urlAyudas);
        // *********************ESTATUS TRÁMITE************************************
        //TRÁMITE / TODOS
    } else if (valorSeleccionadoEstatus === 'tramite' && opcionSeleccionadaPeriodoTiempo === 'todos') {
        urlAyudas = "ayuda/listarAyudas?estadoIncidenciaParametro=2";
        generarTablaAyudas(urlAyudas);
        urlReporte = "report/reporteAyudas?tipo=PDF&estadoIncidenciaId=2";
        //TRÁMITE / SEMANA ACTUAL
    } else if (valorSeleccionadoEstatus === 'tramite' && opcionSeleccionadaPeriodoTiempo === 'semanaActual') {
        var inicioSemana = formateaFecha(getInicioSemana(fechaActual));
        var finSemana = formateaFecha(getFinSemana(fechaActual));
        urlAyudas = "ayuda/listarAyudas?estadoIncidenciaParametro=2&fechaFinParametro=" + finSemana + "&fechaInicioParametro=" + inicioSemana;
        urlReporte = "report/reporteAyudas?tipo=PDF&estadoIncidenciaId=2&fechaInicio=" + inicioSemana + "&fechaFin=" + finSemana;
        generarTablaAyudas(urlAyudas);
        // TRÁMITE / MES ACTUAL
    } else if (valorSeleccionadoEstatus === 'tramite' && opcionSeleccionadaPeriodoTiempo === 'mesActual') {
        var inicioMes = formateaFecha(getInicioMes(fechaActual));
        var finMes = formateaFecha(getFinMes(fechaActual));
        urlAyudas = "ayuda/listarAyudas?estadoIncidenciaParametro=2&fechaFinParametro=" + finMes + "&fechaInicioParametro=" + inicioMes;
        urlReporte = "report/reporteAyudas?tipo=PDF&estadoIncidenciaId=2&fechaInicio=" + inicioMes + "&fechaFin=" + finMes;
        generarTablaAyudas(urlAyudas);
        // TRÁMITE / MES ANTERIOR
    } else if (valorSeleccionadoEstatus === 'tramite' && opcionSeleccionadaPeriodoTiempo === 'mesAnterior') {
        let fechaActualCopia = new Date(fechaActual);
        fechaActualCopia.setMonth(fechaActualCopia.getMonth() - 1);
        var inicioMesAnterior = formateaFecha(getInicioMes(fechaActualCopia));
        var finMesAnterior = formateaFecha(getFinMes(fechaActualCopia));
        urlAyudas = "ayuda/listarAyudas?estadoIncidenciaParametro=2&fechaFinParametro=" + finMesAnterior + "&fechaInicioParametro=" + inicioMesAnterior;
        urlReporte = "report/reporteAyudas?tipo=PDF&estadoIncidenciaId=2&fechaInicio=" + inicioMesAnterior + "&fechaFin=" + finMesAnterior;
        generarTablaAyudas(urlAyudas);
    }
    // *********************ESTATUS AUTORIZADO************************************
    //AUTORIZADO / TODOS
    else if (valorSeleccionadoEstatus === 'autorizadas' && opcionSeleccionadaPeriodoTiempo === 'todos') {
        urlAyudas = "ayuda/listarAyudas?estadoIncidenciaParametro=1";
        generarTablaAyudas(urlAyudas);
        urlReporte = "report/reporteAyudas?tipo=PDF&estadoIncidenciaId=1";
        //AUTORIZADO / SEMANA ACTUAL
    } else if (valorSeleccionadoEstatus === 'autorizadas' && opcionSeleccionadaPeriodoTiempo === 'semanaActual') {
        var inicioSemana = formateaFecha(getInicioSemana(fechaActual));
        var finSemana = formateaFecha(getFinSemana(fechaActual));
        urlAyudas = "ayuda/listarAyudas?estadoIncidenciaParametro=1&fechaFinParametro=" + finSemana + "&fechaInicioParametro=" + inicioSemana;
        urlReporte = "report/reporteAyudas?tipo=PDF&estadoIncidenciaId=1&fechaInicio=" + inicioSemana + "&fechaFin=" + finSemana;
        generarTablaAyudas(urlAyudas);
        // AUTORIZADO / MES ACTUAL
    } else if (valorSeleccionadoEstatus === 'autorizadas' && opcionSeleccionadaPeriodoTiempo === 'mesActual') {
        var inicioMes = formateaFecha(getInicioMes(fechaActual));
        var finMes = formateaFecha(getFinMes(fechaActual));
        urlAyudas = "ayuda/listarAyudas?estadoIncidenciaParametro=1&fechaFinParametro=" + finMes + "&fechaInicioParametro=" + inicioMes;
        urlReporte = "report/reporteAyudas?tipo=PDF&estadoIncidenciaId=1&fechaInicio=" + inicioMes + "&fechaFin=" + finMes;
        generarTablaAyudas(urlAyudas);
        //AUTORIZADO / MES ANTERIOR
    } else if (valorSeleccionadoEstatus === 'autorizadas' && opcionSeleccionadaPeriodoTiempo === 'mesAnterior') {
        let fechaActualCopia = new Date(fechaActual);
        fechaActualCopia.setMonth(fechaActualCopia.getMonth() - 1);
        var inicioMesAnterior = formateaFecha(getInicioMes(fechaActualCopia));
        var finMesAnterior = formateaFecha(getFinMes(fechaActualCopia));
        urlAyudas = "ayuda/listarAyudas?estadoIncidenciaParametro=1&fechaFinParametro=" + finMesAnterior + "&fechaInicioParametro=" + inicioMesAnterior;
        urlReporte = "report/reporteAyudas?tipo=PDF&estadoIncidenciaId=1&fechaInicio=" + inicioMesAnterior + "&fechaFin=" + finMesAnterior;
        generarTablaAyudas(urlAyudas);
    }
    return urlReporte;
};

const generarTablaAyudas = (url) => {
    //Se destruye la instancia de la tabla en caso de que haya sido generada anteriormente
    if ($.fn.DataTable.isDataTable('#tablaAyudas')) {
        $('#tablaAyudas').DataTable().destroy();
    }

    $tablaAyudas = $('#tablaAyudas').dataTable({
        "ajax": {
            url: url,
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

            {data: "numero_expediente"},
            {
                data: null,
                render: function (row) {
                    return row.apellido_paterno_trabajador + ' ' + row.apellido_materno_trabajador + ' ' + row.nombre_trabajador;
                }
            },
            {data: "fecha_emision"},
            {data: "fecha_inicio"},
            {data: "fecha_fin"},
            {data: "num_dias"},
            {data: "monto"},
            {data: "periodo_pago_id"},
            {
                data: null,
                render: function (row) {
                    return row.apellido_paterno_ayuda + ' ' + row.apellido_materno_ayuda + ' ' + row.nombre_ayuda;
                }
            },
            {data: "desc_parentesco"},
            {data: "inicial_descripcion"},
            {data: "folio"},
            {data: "fecha_recepcion"},
            {data: "desc_nomina"}
        ]
    });
};

function visualizarReporteAyudas() {
    let opcionRadioButton = $('input[name="opcion"]:checked').val();
    let periodoTiempo = $('#cmbPeriodosTiempo').val();
    let generaReporte = true;
    let urlReporte = manejoInputsFiltradoUsuario(opcionRadioButton, periodoTiempo, fechaActual, generaReporte);
    $.ajax({
        type: 'GET',
        url: urlReporte,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, textStatus, xhr) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#reportesModal").modal("show");
                var contentType = xhr.getResponseHeader("Content-Type");
                var filename = xhr.getResponseHeader("Content-disposition");
                filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                var url = window.URL.createObjectURL(data);
                var frame = $('#reportes_frame');
                var url = url;
                frame.attr('src', url).show();
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar informacion de documentos", {progressBar: true, closeButton: true});
        }
    });
}

function descargarReporteAyudas() {
    let opcionRadioButton = $('input[name="opcion"]:checked').val();
    let periodoTiempo = $('#cmbPeriodosTiempo').val();
    let generaReporte = true;
    let urlReporte = manejoInputsFiltradoUsuario(opcionRadioButton, periodoTiempo, fechaActual, generaReporte);
    $.ajax({
        type: 'GET',
        contentType: 'application/pdf',
        url: urlReporte,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, textStatus, jqXHR) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            toastr["success"]("Reporte Generado con éxito", "Aviso", {progressBar: true, closeButton: true});
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(data);
            link.download = "ayudas.pdf";
            link.click();
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
        }
    });
}