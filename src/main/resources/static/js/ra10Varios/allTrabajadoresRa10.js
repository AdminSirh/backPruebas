document.addEventListener('DOMContentLoaded', () => {
    let url = "trabajador/listadoTrabajadoresPlazaTipoNomina/1";
    //Llamada a funciones
    cmbAreas();
    cargaPeriodoGeneracion();
    generarTablaFiltrado(url);
});

// Evento change para cmbArea
$('#cmbArea').on('change', function () {
    validarSeleccion();
});
// Evento change para cmbPGeneracion
$('#cmbPGeneracion').on('change', function () {
    validarSeleccion();
});
//Valida y genera la url a consultar dependiendo de los valores ingresados por el usuario
function validarSeleccion() {
    const defaultCmbAreaValue = "todasAreas";
    const defaultCmbPGeneracionValue = "todosPeriodos";
    let id_area = $('#cmbArea').val();
    let periodo_gen = $('#cmbPGeneracion').val();
    switch (true) {
        case id_area !== defaultCmbAreaValue && periodo_gen === defaultCmbPGeneracionValue:
            url = "trabajador/bucarTrabajadoresPorNominayArea/" + id_area + "/1";
            generarTablaFiltrado(url);
            //console.log("El valor de cmbArea es diferente al valor por defecto, pero cmbPGeneracion es el valor por defecto.");
            break;
        case id_area === defaultCmbAreaValue && periodo_gen !== defaultCmbPGeneracionValue:
            url = "ra10/encontrarAreaYPeriodoGen?periodo_generacion=" + periodo_gen;
            generarTablaFiltrado(url);
            //console.log("El valor de cmbPGeneracion es diferente al valor por defecto, pero cmbArea es el valor por defecto.");
            break;
        case id_area !== defaultCmbAreaValue && periodo_gen !== defaultCmbPGeneracionValue:
            url = "ra10/encontrarAreaYPeriodoGen?id_area=" + id_area + "&periodo_generacion=" + periodo_gen;
            generarTablaFiltrado(url);
            //console.log("Ambos valores son diferentes a los valores por defecto.");
            break;
        default:
            url = "trabajador/listadoTrabajadoresPlazaTipoNomina/1";
            generarTablaFiltrado(url);
            //console.log("Ambos valores son iguales a los valores por defecto.");
            break;
    }
}
//Genera la tabla y recibe la url acorde a la selección del usuario
function generarTablaFiltrado(url) {
    //Se destruye la instancia de la tabla si ya fue creada
    if ($.fn.DataTable.isDataTable('#tabla_trabajadores_base')) {
        $('#tabla_trabajadores_base').DataTable().destroy();
    }
    ;
    tabla_trabajadores_base = $('#tabla_trabajadores_base').dataTable({
        "ajax": {
            url: url,
            method: 'GET',
            dataSrc: ''
        },
        responsive: true,
        bProcessing: true,
        select: true,
        //Añadí este autowidth para evitar que se incrmente el ancho de la tabla cada que se vuelve a dibujar la tabla
        "autoWidth": false,
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
            {data: (url === "trabajador/listadoTrabajadoresPlazaTipoNomina/1" ? "cat_expediente.numero_expediente" : "numero_expediente")},
            {data: (url === "trabajador/listadoTrabajadoresPlazaTipoNomina/1" ? "persona.nombre" : "nombre")},
            {data: (url === "trabajador/listadoTrabajadoresPlazaTipoNomina/1" ? "persona.apellido_paterno" : "apellido_paterno")},
            {data: (url === "trabajador/listadoTrabajadoresPlazaTipoNomina/1" ? "persona.apellido_materno" : "apellido_materno")},
            {data: "", sTitle: "Movimiento provisional", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" onclick="guardarMovimientoProvisional(' + r.id_trabajador + ')"><span class="fa fa-edit"></span> Asignar </button>';
                    return he;
                }
            }]
    });
}
//Coloca en el select de las áreas las mismas provenientes del catalogo de áreas
function cmbAreas(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarAreas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbArea').empty().append('<option value="todasAreas">*Todas las Áreas</option>');
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].idArea === id) ? vselected = "selected" : vselected = "----";
                        $('#cmbArea').append('<option value="' + data[x].idArea + '"' + vselected + '>' + data[x].desc_area + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//Carga los periodos de generación y los coloca en el select 
function cargaPeriodoGeneracion() {
    $.ajax({
        type: "GET",
        url: "trabajador/buscaPeriodosFechas/1",
        dataType: 'json',
        success: function (data) {
            let opciones = '<option value="todosPeriodos" selected>*Todos los periodos</option>';
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                return;
            }

            const hoy = new Date();
            hoy.setHours(0, 0, 0, 0);
            let periodoSeleccionado = null; // Declarar la variable aquí

            for (const periodo of data) {
                const fechaInicial = new Date(periodo.fecha_inicial);
                const fechaFinal = new Date(periodo.fecha_final);
                const fechaCorte = new Date(periodo.fecha_corte);
                fechaInicial.setHours(0, 0, 0, 0);
                fechaFinal.setHours(0, 0, 0, 0);
                fechaCorte.setHours(0, 0, 0, 0);

                if (hoy <= fechaCorte) {
                    opciones += `<option value="${periodo.periodo}">${crearOpcion(periodo.periodo, fechaInicial, fechaFinal)}</option>`;
                    periodoSeleccionado = periodo.periodo; // Asignar valor aquí
                    break;
                } else if (hoy > fechaCorte) {
                    const siguientePeriodo = data[data.indexOf(periodo) + 1];
                    if (siguientePeriodo) {
                        opciones += `<option value="${siguientePeriodo.periodo}">${crearOpcion(siguientePeriodo.periodo, fechaInicial, fechaFinal)}</option>`;
                        periodoSeleccionado = siguientePeriodo.periodo; // Asignar valor aquí
                        break;
                    }
                }
            }

            data.forEach(periodo => {
                if (periodo.periodo !== periodoSeleccionado) {
                    const fechaInicial = new Date(periodo.fecha_inicial);
                    const fechaFinal = new Date(periodo.fecha_final);
                    opciones += `<option value="${periodo.periodo}">${crearOpcion(periodo.periodo, fechaInicial, fechaFinal)}</option>`;
                }
            });

            $('#cmbPGeneracion').html(opciones);
        },
        error: function (e) {
            toastr["error"](e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Crea la opción del select de los periodos de generación formateado
function crearOpcion(periodo, fechaInicial, fechaFinal) {
    const periodoStr = String(periodo); // Convertir a string
    const diasInicial = fechaInicial.getDate();
    const mesesInicial = fechaInicial.getMonth() + 1;
    const aniosInicial = fechaInicial.getFullYear();
    const diasFinal = fechaFinal.getDate();
    const mesesFinal = fechaFinal.getMonth() + 1;
    const aniosFinal = fechaFinal.getFullYear();
    return 'P. ' + periodoStr.substr(4, 5) + " : " +
            diasInicial + '/' + mesesInicial + '/' + aniosInicial +
            '--' + diasFinal + '/' + mesesFinal + '/' + aniosFinal;
}
//Redirecciona al usuario a la pantalla de guardado para el trabajador que seleccione
function guardarMovimientoProvisional(id_trabajador) {
    const encodedId = encodeURIComponent(id_trabajador);
    window.location.href = 'guardaMovimientoProvisional?id_trabajador=' + encodedId;
}
//Genera el excel en el back con la información de los trabajadores con los montos correspondientes al periodo de generación seleccionado
function generarExcelDiferenciaSueldoOrdinario() {
    let periodoGeneracion = $('#cmbPGeneracion').val();
    //Valida si fue seleccionado un periodo
    if (periodoGeneracion === 'todosPeriodos') {
        toastr["warning"]("Debe seleccionar un periodo de generación para generar el archivo", "Advertencia", {progressBar: true, closeButton: true});
        return;
    }
    $.ajax({
        url: "ra10/exportTrabajadoresConMontosRA10?periodo_generacion=" + periodoGeneracion,
        type: "GET",
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, textStatus, xhr) {
            var filename = "";
            var disposition = xhr.getResponseHeader('Content-Disposition');
            if (disposition && disposition.indexOf('attachment') !== -1) {
                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                var matches = filenameRegex.exec(disposition);
                if (matches != null && matches[1])
                    filename = matches[1].replace(/['"]/g, '');
            }
            var url = window.URL.createObjectURL(data);
            var a = document.createElement('a');
            a.href = url;
            //Se obtiene el nombre que se genera en el servidor
            a.download = filename || 'diferenciaSueldoOrdinario.xlsx';
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
            toastr["success"]("Se generó correctamente el archivo excel", "Aviso", {progressBar: true, closeButton: true});
        },
        error: function (xhr, status, error) {
            const errorMessage = xhr.responseText || "Ocurrió un error al generar el excel, no se encontró información";
            toastr['error'](errorMessage, "Error");
        }
    });
}