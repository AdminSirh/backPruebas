document.addEventListener('DOMContentLoaded', () => {
    tipo_nomina();
    cmbIncidencias();
    // Add an event listener to the dropdown
    const tipoNominaSelect = $('#tipo_nomina');

    // Add an event listener to the dropdown
    tipoNominaSelect.on('change', function () {
        const selectedValue = $(this).val();
        loadTable(selectedValue);
        mostrarOcultarBoton();
    });

    // Valor seleccionado por defecto
    const id_inicial = '1';
    tipoNominaSelect.val(id_inicial);
    loadTable(id_inicial);

    // Manejar el cambio de selección en el select
    $("#selectInasistencias").on("change", function () {
        var selectedValues = $(this).val();
        // Verificar si "Todas" está seleccionada junto con otras opciones
        if (selectedValues && selectedValues.includes("all") && selectedValues.length > 1) {
            // Deseleccionar "Todas" si se ha seleccionado junto con otras opciones
            selectedValues = selectedValues.filter(value => value !== "all");
            $(this).val(selectedValues);
        }
    });
});

const cmbIncidencias = () => {
    $.ajax({
        type: "GET",
        url: "catalogos/listarIncidenciasKardex",
        dataType: 'json',
        success: function (data) {
            const selectInasistencias = $('#selectInasistencias');

            // Limpieza del Select
            selectInasistencias.empty();

            // Opción por defecto
            selectInasistencias.append('<option value="all" selected>Todas</option>');
            data.forEach(function (incidencia) {
                const optionText = `${incidencia.inicial_descripcion} - ${incidencia.descripcion}`;
                selectInasistencias.append(`<option value="${incidencia.id_incidencia}">${optionText}</option>`);
            });
        },
        error: function (e) {
            alert(e);
        }
    });
};

const tipo_nomina = (id) => {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                //Seleccion de todas las nominas id 6
                //$('#tipo_nomina').empty().append("<option>* </option>");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        if (data[x].id_tipo_nomina !== 1) {  // Excluye el elemento con ID 1
                            vselected = (data[x].id_tipo_nomina === id) ? "selected" : "";
                            $('#tipo_nomina').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                        }
                    }
                    //Se coloca el valor de la nómina de Varios para esta generación de CA, verificar generación de CA para otro tipo de nóminas
                    $('#tipo_nomina').val("1");
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};

const loadTable = (id_nomina) => {
    //Se coloca el nombre de la nómina dependiendo
    var nombreOpcion = $("#tipo_nomina option:selected").text();
    $("#nombreNominaActivaImportacion").text("Descargar Conteo de Inasistencia (" + nombreOpcion + ")");
    $("#nombreNominaActivaDescarga").text("Descargar Incidencias (" + nombreOpcion + ")");
    //Se destruye la instancia de la tabla si existe
    if ($('#tabla_Trabajadores')) {
        $('#tabla_Trabajadores').DataTable().destroy();
    }
    $tablaTrabajadores = $('#tabla_Trabajadores').dataTable({
        "ajax": {
            url: "trabajador/listadoTrabajadoresPlazaTipoNomina/" + id_nomina,
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
            {data: "", sTitle: "Kardex", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" onclick="GuardaInasistencias(' + r.id_trabajador + ')"><span class="fa fa-search"></span> Visualizar </button>';
                    return he;
                }
            }]
    });
};

function mostrarOcultarBoton() {
    var select = $("#tipo_nomina");
    var boton = $("#botonDescarga");
    var botonConteo = $("#botonConteo");

    if (select.val() === "3" || select.val() === "2" || select.val() === "1") {
        boton.show();
        botonConteo.show();
    } else {
        boton.hide();
        botonConteo.hide();
    }
}

const descargaTexto = () => {
    let fecha_inicial = $('#min-date').val();
    let fecha_final = $('#max-date').val();

    if (fecha_inicial === '' || fecha_final === '') {
        toastr.warning("Selecciona una fecha", "Aviso");
        return;
    }

    if (fecha_inicial > fecha_final) {
        toastr.warning("La fecha Inicial no debe superar a la Fecha Final", "Aviso");
        return;
    }
    ;

    if ($('#tipo_nomina').val() === '3') {
        let url = "incidencias/listarDocumentoTextoCardexConfianza/";
        ajaxTxt(url, fecha_inicial, fecha_final, 'InasistenciasConfianza');
    } else if ($('#tipo_nomina').val() === '1') {
        let url = "incidencias/listarDocumentoTextoCardexBase/";
        ajaxTxt(url, fecha_inicial, fecha_final, 'InasistenciasBase');
    } else {
        let url = "incidencias/listarDocumentoTextoCardexTransportacion/";
        ajaxTxt(url, fecha_inicial, fecha_final, 'InasistenciasTransportacion');
    }
};

const descargaTextoConteo = () => {
    let tipoNominaId = $('#tipo_nomina').val();
    let fecha_inicial = $('#fechaInicioConteo').val();
    let fecha_final = $('#fechaFinConteo').val();
    let expediente = $('#expediente').val();
    //Comprobación de fecha
    if (fecha_inicial === '' || fecha_final === '') {
        toastr.warning("Selecciona una fecha", "Aviso");
        return;
    }
    if (fecha_inicial > fecha_final) {
        toastr.warning("La fecha Inicial no debe superar a la Fecha Final", "Aviso");
        return;
    }
    var selectedValues = $("#selectInasistencias").val();

    if (selectedValues === null || selectedValues.length === 0) {
        toastr.warning("Seleccione al menos una opción de inasistencia", "Aviso");
        return;
        //Casos de seleccion especifica de incapacidades
    } else if (!selectedValues.includes("all")) {
        //Si se recibe el expediente
        if (expediente !== '') {
            const baseUrl = 'incidencias/listarDocumentoTextoCardexGeneralExpediente/' + fecha_inicial + '/' + fecha_final + '/' + tipoNominaId + '/' + expediente + '?';
            const queryString = "incidenciasFiltradas=" + selectedValues.join("&incidenciasFiltradas=");
            const url = baseUrl + queryString;
            ajaxTxtConteo(url, fecha_inicial, fecha_final);
            return;
        }
        const baseUrl = 'incidencias/listarDocumentoTextoCardexSinExpediente/' + fecha_inicial + '/' + fecha_final + '/' + tipoNominaId + '?';
        const queryString = "incidenciasFiltradas=" + selectedValues.join("&incidenciasFiltradas=");
        const url = baseUrl + queryString;
        ajaxTxtConteo(url, fecha_inicial, fecha_final);
        //Todas las incapacidades
    } else {
        //Se envian todos los id de las incidencias
        selectedValues = [1, 36, 37, 27, 16, 14, 31, 15, 26, 23, 24, 25, 2, 32, 35, 5, 21, 6, 3, 38, 4, 13, 19, 30, 22, 33, 29, 18, 34, 10, 11, 12, 28, 17, 9, 20, 7, 8];
        //Comprobar si se esta recibiendo el expediente
        if (expediente !== '') {
            const baseUrl = 'incidencias/listarDocumentoTextoCardexGeneralExpediente/' + fecha_inicial + '/' + fecha_final + '/' + tipoNominaId + '/' + expediente + '?';
            const queryString = "incidenciasFiltradas=" + selectedValues.join("&incidenciasFiltradas=");
            const url = baseUrl + queryString;
            ajaxTxtConteo(url, fecha_inicial, fecha_final);
            return;
        }
        const baseUrl = 'incidencias/listarDocumentoTextoCardexSinExpediente/' + fecha_inicial + '/' + fecha_final + '/' + tipoNominaId + '?';
        const queryString = "incidenciasFiltradas=" + selectedValues.join("&incidenciasFiltradas=");
        const url = baseUrl + queryString;
        ajaxTxtConteo(url, fecha_inicial, fecha_final);
    }
    ;
    return;
};

const ajaxTxtConteo = (url, fecha_inicial, fecha_final) => {
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                if (data == null) {
                    toastr.error("Error: No se encontraron incidencias para las fechas ingresadas");
                    return;
                }
                // Agregando datos
                const aggregatedData = {};

                data.forEach(item => {
                    const {numero_expediente, inicial_descripcion, cantidad_incidencias} = item;

                    if (!aggregatedData[numero_expediente]) {
                        aggregatedData[numero_expediente] = {};
                    }

                    if (!aggregatedData[numero_expediente][inicial_descripcion]) {
                        aggregatedData[numero_expediente][inicial_descripcion] = 0;
                    }

                    aggregatedData[numero_expediente][inicial_descripcion] += cantidad_incidencias;
                });
                // Generar contenido CSV
                let csv = fecha_inicial + " al " + fecha_final + "\n";
                csv += "Exp;" + obtenerEncabezados(aggregatedData) + "\n";

                for (const expediente in aggregatedData) {
                    const incidences = aggregatedData[expediente];
                    const row = [expediente];

                    const columns = obtenerEncabezados(aggregatedData);

                    columns.forEach(columnName => {
                        row.push(incidences[columnName] || 0);
                    });

                    csv += row.join(";") + "\n";
                }
                // Función para obtener encabezados únicos
                function obtenerEncabezados(data) {
                    const headers = new Set();

                    for (const expediente in data) {
                        for (const inicial in data[expediente]) {
                            headers.add(inicial);
                        }
                    }

                    return Array.from(headers).sort(); // Ordenar encabezados alfabéticamente si es necesario
                }
                // Crear un Blob que contiene los datos CSV
                const blob = new Blob([csv], {type: "text/plain;charset=utf-8"});

                // Crear un enlace de descarga y desencadenar la descarga
                const a = document.createElement("a");
                a.href = URL.createObjectURL(blob);
                a.download = "Inasistencias" + fecha_inicial + '_' + fecha_inicial + ".txt";
                a.click();
                toastr["success"]("Archivo generado con éxito", "Aviso", {progressBar: true, closeButton: true});
                $("#modalConteo").modal('hide');
                $('#fechaInicioConteo').val('');
                $('#fechaFinConteo').val('');
                $('#selectInasistencias').val('');
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar el área: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};

function ajaxTxt(url, fecha_inicial, fecha_final, nombreDescarga) {

    const meses = [
        'ene', 'feb', 'mar', 'abr', 'may', 'jun',
        'jul', 'ago', 'sep', 'oct', 'nov', 'dic'
    ];

// Función para obtener el nombre abreviado del mes
    function obtenerNombreMes(mesNumero) {
        return meses[mesNumero - 1];
    }
    // Convertir fecha inicial al nuevo formato
    let fecha_inicial_parts = fecha_inicial.split('-');
    let nueva_fecha_inicial = `${fecha_inicial_parts[2]}-${obtenerNombreMes(parseInt(fecha_inicial_parts[1]))}-${fecha_inicial_parts[0]}`;

// Convertir fecha final al nuevo formato
    let fecha_final_parts = fecha_final.split('-');
    let nueva_fecha_final = `${fecha_final_parts[2]}-${obtenerNombreMes(parseInt(fecha_final_parts[1]))}-${fecha_final_parts[0]}`;
    $.ajax({
        type: "GET",
        url: url + fecha_inicial + "/" + fecha_final,
        dataType: 'json',
        success: function (data) {
            if (data == null) {
                toastr.error("Error: No se encontraron incidencias para las fechas ingeresadas");
                return;
            }
            // Aggregating data
            const aggregatedData = {};

            data.forEach(item => {
                const {numero_expediente, inicial_descripcion, cantidad_incidencias} = item;

                if (!aggregatedData[numero_expediente]) {
                    aggregatedData[numero_expediente] = {};
                }

                if (!aggregatedData[numero_expediente][inicial_descripcion]) {
                    aggregatedData[numero_expediente][inicial_descripcion] = cantidad_incidencias;
                } else {
                    aggregatedData[numero_expediente][inicial_descripcion] += cantidad_incidencias;
                }
            });

            // Generate CSV content
            let csv = nueva_fecha_inicial + " al " + nueva_fecha_final + "\n" + "Exp;F;R.A;R.B;R.C;EG50;EG60;EG75;EG100;RT100;PRE100;EN60;EN100;ET25;ET50;EG0;RT0;PRE0;S\n";

            for (const expediente in aggregatedData) {
                const incidences = aggregatedData[expediente];
                const row = [expediente];

                const columns = [
                    "F", "R.A", "R.B", "R.C", "EG50", "EG60", "EG75",
                    "EG100", "RT100", "PRE100", "EN60", "EN100", "ET25",
                    "ET50", "EG0", "RT0", "PRE0", "S"
                ];

                columns.forEach(columnName => {
                    row.push(incidences[columnName] || 0);
                });

                csv += row.join(";") + "\n";
            }

            // Create a Blob containing the CSV data
            const blob = new Blob([csv], {type: "text/plain;charset=utf-8"});

            // Create a download link and trigger download
            const a = document.createElement("a");
            a.href = URL.createObjectURL(blob);
            a.download = nombreDescarga + nueva_fecha_inicial + "_" + nueva_fecha_final + ".txt";
            a.click();
            toastr["success"]("Archivo generado con éxito", "Aviso", {progressBar: true, closeButton: true});
            $("#txtModal").modal('hide');
            $('#min-date').val('');
            $('#max-date').val('');
        },
        error: function (e) {
            toastr.warning("Error: " + e);
        }
    });
}

//Limpieza del modal al cerrar
$('#modalConteo').on('hidden.bs.modal', function () {
    $('#expediente').val('');
    $('#selectInasistencias').val('all');
    $('#fechaInicioConteo').val('');
    $('#fechaFinConteo').val('');
    toastr.clear();
});

$('#modalConteo').on('shown.bs.modal', function () {
    toastr.info('Para Seleccionar varias inasistencias mantenga la tecla Control y seleccione cada una de ellas', 'Información', {
        timeOut: 0, // Establece el tiempo de espera en 0 (no se cerrará automáticamente)
        closeButton: true // Muestra el botón de cierre en el Toastr
    });

});

function GuardaInasistencias(id_trabajador) {
    window.location.href = 'guardaInasistencias?id_trabajador=' + id_trabajador;
}