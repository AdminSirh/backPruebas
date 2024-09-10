document.addEventListener('DOMContentLoaded', () => {
    //*****************Mensajes al ingresar a la pantalla*************
    //Información para habilitar la carga del archivo
    toastr.info('Primero debe seleccionar el tipo nómina y los periodos de generación y aplicación, posteriormente, la incidencia que desea importar', 'Información', {
        progressBar: true, closeButton: true, timeOut: 10000  //(10000ms = 10 segundos)
    });
    //*************Llamada a Funciones**************
    validaBotones();
    cmbTipoNomina();
    //************Constantes**************
    const cmbTipoNominaSelect = $("#cmbTipoNomina");
    const cmbIncidenciaSelect = $("#cmbIncidencia");
    //************Eventos de escucha**********
    //Al cambiar la nómina se validan los botones seleccionados 
    cmbTipoNominaSelect.change(() => {
        let idNomina = cmbTipoNominaSelect.val();
        validaBotones();
        habilitarBotonElimina();
        cmbIncidencias(idNomina);
        //Se limpia la tabla de registros y se deshabilita el guardado
        let table = $('#tabla_importaciones').DataTable();
        $("#guarda").prop("disabled", true);
        table.clear().draw();
    });

    //Habilitar y deshabilitar los otros inputs si no se tiene selecionada una nómina
    cmbTipoNominaSelect.change(function () {
        var selectedValue = $(this).val();
        if (selectedValue === "") {
            $('#cmbIncidencia').prop('disabled', true);
            $('#periodo_generacion').prop('disabled', true);
            $('#periodo_aplicacion').prop('disabled', true);
        } else {
            $('#cmbIncidencia').prop('disabled', false);
            $('#periodo_generacion').prop('disabled', false);
            $('#periodo_aplicacion').prop('disabled', false);
        }
        ;
    });

    //Al cambiar la incidencia se debe habilitar la eliminación e movimientos siempre y cuando la nómina esté seleccionada
    cmbIncidenciaSelect.change(() => {
        validaBotones();
        habilitarBotonElimina();
        //El texto que selecciona el usuario
        let textoSeleccionado = cmbIncidenciaSelect.find(":selected").text();
        //Se limpia la tabla de registros y se deshabilita el guardado
        let table = $('#tabla_importaciones').DataTable();

        let partes = textoSeleccionado.split(" - ");

        if (partes.length === 2) {
            let incidencia = partes[0];
            let descripcion = partes[1];

            // Establecer el texto en el label
            $("#descripcionIncidencia").text(incidencia + " " + descripcion);
        }
        $("#guarda").prop("disabled", true);
        table.clear().draw();
    });
    //Prueba de la carga del contenido file
    $('#file').on("change", function (event) {
        //Se resetea el progreso de la carga por si ya se había enviado anteriormente algún otro archivo
        let inputFile = $('#file').prop('files')[0];
        let nombreArchivoCargado = inputFile.name;
        const file = event.target.files[0];
        if (file) {
            if (!file.name.endsWith(".csv")) {
                toastr.error("Sólo se permite la carga de archivos CSV");
                //Se elimina el archivo cargado
                $("#file").val("");
                return;
            }
            const reader = new FileReader();
            //Evento on progress rastrea el progreso de carga de archivo
            reader.onprogress = function (e) {
                if (e.lengthComputable) {
                    const percentLoaded = (e.loaded / e.total) * 100;
                    //Se pasa a la barra el porcentaje cargado
                    $("#progress").val(percentLoaded);
                    //Muestra el porcentaje en el label correspondiente
                    $("#progress-label").text(percentLoaded.toFixed(1) + "%");
                }
            };
            //Operación de lectura del archivo se completó de manera exitosa 
            reader.onload = function (e) {
                const csvContent = e.target.result;
                //Se llama a la función que genera los datos que se provesarán
                generaTablaPreProcesado(csvContent, nombreArchivoCargado);
            };
            // Manejo de errores de lectura del archivo
            reader.onerror = function (e) {
                console.error("Error al leer el archivo.", e);
            };
            reader.readAsText(file);
            $("#progress-container").show();
        }
    });
    //*****************Declaración de funciones*********************
    //Manejo del botón de eliminación que debe tener una nómina seleccionada e incidencia para proceder a la eliminación
    const habilitarBotonElimina = () => {
        let inputFile = $("#file");
        let btnElimina = $("#elimina");
        const tipoNominaValue = $("#cmbIncidencia").val();
        const cmbTipoNominaValue = $("#cmbTipoNomina").val();
        if (tipoNominaValue !== "" && tipoNominaValue !== null && cmbTipoNominaValue !== "" && cmbTipoNominaValue !== null) {
            btnElimina.prop("disabled", false);
            inputFile.prop("disabled", false);
        } else {
            btnElimina.prop("disabled", true);
            inputFile.prop("disabled", true);
        }
    };
    //Función para llenar las percepciones y todas las deducciones de las nóminas
    const cmbIncidencias = (idNomina) => {
        const selectIncidencias = $('#cmbIncidencia');
        selectIncidencias.empty().append('<option value="">Cargando...</option>');

        if ($.trim(idNomina) === "") {
            selectIncidencias.empty();
            selectIncidencias.append('<option value="">* Tipo de Incidencia</option>');
            return false;
        }

        $.ajax({
            type: "GET",
            url: "incidencias/buscarPercepcionesPorNomina/" + idNomina,
            dataType: 'json',
            success: function (data) {
                selectIncidencias.empty();
                if (data.length === 0) {
                    selectIncidencias.append('<option value="">No hay datos disponibles</option>');
                } else {
                    // Ordenar las incidencias
                    data.sort((a, b) => a.cat_Incidencias.cve_nomina - b.cat_Incidencias.cve_nomina);
                    // Opción por defecto
                    selectIncidencias.append('<option value="">* Tipo de Incidencia</option>');

                    data.forEach(function (incidencia) {
                        const optionText = `(${incidencia.cat_Incidencias.cve_nomina}) - ${incidencia.cat_Incidencias.descripcion}`;
                        selectIncidencias.append(`<option value=${incidencia.id_percepcion_nomina}>${optionText}</option>`);
                    });
                }
                toastr.success("Se cargaron las percepciones de la nómina seleccionada");
                agregarDeducciones(selectIncidencias);
            },
            error: function (e) {
                toastr.error(e);
            }
        });
    };

    const agregarDeducciones = (selectIncidencias) => {
        $.ajax({
            type: "GET",
            url: "catalogos/listarDeducciones",
            dataType: 'json',
            success: function (data) {
                data.forEach(function (deduccion) {
                    data.sort((a, b) => a.cve_nomina - b.cve_nomina);
                    const optionText = `(${deduccion.cve_nomina}) - ${deduccion.descripcion}`;
                    selectIncidencias.append(`<option value=${deduccion.id_incidencia}>${optionText}</option>`);
                    toastr.success("Se cargaron las deducciones adicionales");
                });
            },
            error: function (e) {
                toastr.error(e);
            }
        });
    };

    //**************Elemento de configuración de la tabla (Idioma)********************
    //Cambiar el idioma de la tabla a español
    $('#tabla_importaciones').DataTable({
        "language": {
            'processing': 'Procesando espera...',
            "sProcessing": "Procesando...",
            "sLengthMenu": "Mostrar _MENU_ registros",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": " ",
            "sInfo": " _START_ al _END_ Total Trabajadores: _TOTAL_",
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
        }
    });
});

//Restaura el estado de la barra de progreso (Reset)
const resetProgress = () => {
    $("#file").val("");
    $("#progress").val(0);
    $("#progress-label").text("0%");
    $("#progress-container").hide();
};

const incidenciasAdmin = () => {
    window.location.href = 'incidenciasAdmin';
};
//Esta función muestra los datos en la tabla de pre procesamiento de los datos
const generaTablaPreProcesado = (csvContent, nombreArchivoCargado) => {
    let table = $('#tabla_importaciones').DataTable();
    //Se obtienen los periodos de generación y aplicación para colocarlos en la tabla
    let periodoAplicacion = $('#periodo_aplicacion').val();
    let periodoGeneracion = $('#periodo_generacion').val();
    let lines = csvContent.split('\n'); // Dividir el contenido en líneas
    let csvData = [];
    //Se veriica que no se generen filas vacías
    for (let i = 0; i < lines.length; i++) {
        let row = lines[i].split(',');
        if (row.join('').trim() !== '') {
            csvData.push(row);
        }
    }
    // Trae los encabezados del CSV, generar solo en los CSV con encabezados
    // let headerRow = csvData[0];

    // Obtén la primera columna del CSV ()
    let firstColumn = csvData.map(row => row[0]);
    //Obtener expedientes 
    expedientesDuplicados = obtenerExpedientesDuplicados(firstColumn);
    if (expedientesDuplicados.length > 0) {
        $("#modalExpedientesDuplicados").modal("show");
        //Se resetea la barra de progreso
        resetProgress();
        toastr["warning"]("Se encontraron expedientes duplicados", "Aviso", {progressBar: true, closeButton: true});
        //Se elimina la tabla ya que no se puede hacer carga de archivos erróneos
        $("#guarda").prop("disabled", true);
        table.clear().draw();
        generarTxtExpedientesDuplicados(nombreArchivoCargado, expedientesDuplicados);
        return;
    }
    //Ciclo para obtener los datos necesarios del trabajador y colocarlos en la tabla u obtner expedientes desconocidos
    expedientesDesconocidos = obtenerExpedientesDesconocidosOGeneraTabla(firstColumn, table, periodoGeneracion, periodoAplicacion);
    //Si los expedientes desconocidos es mayor a cero entonces se realiza el manejo para los mismos
    if (expedientesDesconocidos.size > 0) {
        $("#modalExpedientesDesconocidos").modal("show");
        //Se resetea la barra de progreso
        resetProgress();
        toastr["warning"]("Se encontraron expedientes desconocidos", "Aviso", {progressBar: true, closeButton: true});
        //Se elimina la tabla ya que no se puede hacer carga de archivos erróneos
        $("#guarda").prop("disabled", true);
        table.clear().draw();
        generarTxtExpedientesDesconocidos(nombreArchivoCargado, expedientesDesconocidos);
    }
    //Si no existieron expedientes desconocidos se mantiene la tabla generada en la función y se indica al usuario que no hubo problemas con los expedientes
    else {
        toastr["success"]("Se importaron las incidencias de manera correcta", "Éxito", {progressBar: true, closeButton: true});
        guardaIncidencias(csvContent);
    }
};

const obtenerExpedientesDuplicados = (firstColumn) => {
    const duplicados = [];
    const conteo = {};
    for (const elemento of firstColumn) {
        if (conteo[elemento]) {
            conteo[elemento]++;
        } else {
            conteo[elemento] = 1;
        }
    }
    for (const elemento in conteo) {
        if (conteo[elemento] > 1) {
            duplicados.push(elemento);
        }
    }

    return duplicados;
};

const obtenerExpedientesDesconocidosOGeneraTabla = (firstColumn, table, periodoGeneracion, periodoAplicacion) => {
    const expedientesDesconocidos = new Set();
    firstColumn.forEach((expediente) => {
        $.ajax({
            type: "GET",
            url: "trabajador/buscarTrabajador_NumExpediente/" + expediente,
            dataType: 'json',
            async: false,
            success: function (data) {
                //Se verifican los expedientes desconocidos, si encuentra uno no genera la tabla
                if (data.data === null || data.data === undefined) {
                    expedientesDesconocidos.add(expediente);
                    return;
                }
                let expediente = data.data.cat_expediente.numero_expediente;
                let nombreCompleto = data.data.persona.nombre + ' ' + data.data.persona.apellido_paterno + ' ' + data.data.persona.apellido_materno;
                let monto = 1;
                //Se habilita el botón de guardado
                $("#guarda").prop("disabled", false);
                //Se rellena la tabla con los registros encontrados si no se encontraron expedientes desconocidos
                table.row.add([
                    expediente,
                    nombreCompleto,
                    monto,
                    periodoGeneracion,
                    periodoAplicacion,
                    '',
                    0
                ]).draw().node();
            },
            error: function (e) {
                toastr["error"](e, "Error", {progressBar: true, closeButton: true});
            }
        });
    });
    //Se devuelven los expedientes que no fueron encontrados
    return expedientesDesconocidos;
};

//Se guardan las incidencias ya que fueron procesadas, validadas y son mostradas en la tabla
const guardaIncidencias = (csvContent) => {
    //Se espera a que el usuario de click en guardar movimientos
    $("#guarda").click(function (event) {
        event.preventDefault();
        //Comienza el manejo específico del CSV
        let periodoGeneracion = $('#periodo_generacion').val();
        let periodoAplicacion = $('#periodo_aplicacion').val();
        let cmbTipoNomina = $('#cmbTipoNomina').val();
        $.ajax({
            type: "GET",
            url: "trabajador/buscarPeriodoPago/" + periodoGeneracion + "/" + cmbTipoNomina,
            dataType: 'json',
            success: function (data) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    //**************************CARGA DE FALTAS************************************
                    let fechaInicio = data.data.fecha_inicial;
                    let fechaFin = data.data.fecha_final;
                    //Con las fechas iniciales y finales se realiza la lectura del csv
                    let lines = csvContent.split('\n');
                    lines.forEach(function (line) {
                        let columns = line.split(',');
                        // La primera columna es el expediente del trabajador
                        let expediente = columns[0];
                        // Las columnas 2 a 16 representan las incidencias
                        for (let i = 0; i < columns.length; i++) {
                            //Elmina espacios en blancos para obtener registros de la última columna de manera correcta
                            let incidencia = columns[i].trim();
                            let fecha = new Date(fechaInicio);
                            fecha.setDate(fecha.getDate() + i - 1); // Incrementa la fecha en i - 1 días
                            // Procesa la incidencia
                            let idFalta = 2;
                            let idRetardoA = 10;
                            let idRetardoB = 11;
                            let idRetardoC = 12;
                            let idAmonestacion = 1;
                            let idSuspension = 9;
                            if (incidencia === "F") {
                                ajaxSaveIncidencia(expediente, fecha, idFalta);
                                //console.log(`Expediente ${expediente}: Falta el ${fecha.toISOString()}`);
                            } else if (incidencia === "0.5") {
                                //console.log(`Expediente ${expediente}: Retardo A el ${fecha.toISOString()}`);
                                ajaxSaveIncidencia(expediente, fecha, idRetardoA);
                            } else if (incidencia === "0.7") {
                                //console.log(`Expediente ${expediente}: Retardo B el ${fecha.toISOString()}`);
                                ajaxSaveIncidencia(expediente, fecha, idRetardoB);
                            } else if (incidencia === "0.1") {
                                //console.log(`Expediente ${expediente}: Retardo C el ${fecha.toISOString()}`);
                                ajaxSaveIncidencia(expediente, fecha, idRetardoC);
                            } else if (incidencia === "A") {
                                //console.log(`Expediente ${expediente}: Amonestación el ${fecha.toISOString()}`);
                                ajaxSaveIncidencia(expediente, fecha, idAmonestacion);
                            } else if (incidencia === "S") {
                                //console.log(`Expediente ${expediente}: Suspensión el ${fecha.toISOString()}`);
                                ajaxSaveIncidencia(expediente, fecha, idSuspension);
                            }
                        }
                    });
                }
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar periodos de pago : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    });
};

const ajaxSaveIncidencia = (expediente, fecha, idIncidencia) => {
    let fechaInicio = fecha;
    let fechaFin = fechaInicio;
    //Conversión de expedientes a id trabajador
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador_NumExpediente/" + expediente,
        dataType: 'json',
        success: function (data) {
            let id_trabajador = data.data.id_trabajador;
            let estado_incidencia_id = 1;
            let folio = 'N/A';
            let bis = 'N/A';
            let num_dias = 1;
            datos = {"num_dias": num_dias,
                "cat_estado_incidencia": {"id_estado_incidencia": estado_incidencia_id},
                "cat_incidencias": {"id_incidencia": idIncidencia},
                "fecha_inicio": fechaInicio,
                "fecha_fin": fechaFin,
                "folio": folio,
                "bis": bis,
                "observaciones": 'N/A',
                "trabajador_id": id_trabajador};
            //Se actualiza la Incapacidad correspondiente al generar la incidencia
            $.ajax({
                type: "POST",
                url: "incidencias/guardarIncidencia",
                data: JSON.stringify(datos),
                contentType: "application/json",
                success: function (data) {
                    toastr['success'](data.data, "Incidencia Generada correctamente");
                },
                error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
                }
            });
        },
        error: function (e) {
            toastr["error"](e, "Error", {progressBar: true, closeButton: true});
        }
    });
};

function eliminarIncidenciasSeleccionadas() {
    $("#eliminarIncidenciasModal").submit(function (event) {
        event.preventDefault();
        let estatus = 0;
        let incidenciasFaltas = [1, 2, 9, 10, 11, 12];
        let idTipoNomina = $('#cmbTipoNomina').val();
        let periodoGeneracion = $('#periodo_generacion').val();
        //Se obtienen las incidencias con los parámetros seleccionados (Nómina, periodo y tipos de incidencias)
        $.ajax({
            type: "GET",
            url: "incidencias/buscarIncidenciasImportacion/" + idTipoNomina + "/" + periodoGeneracion + "/" + incidenciasFaltas,
            contentType: "application/json",
            success: function (data) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    //Se desautorizan las incidencias encontradas en lo parámetros de búsqueda (Encontradas);
                    let idIncidenciasEncontradas = data;
                    idIncidenciasEncontradas.forEach(function (id_incidencia) {
                        $.ajax({
                            type: "POST",
                            url: "incidencias/estatusIncidencia/" + id_incidencia + "/" + estatus,
                            contentType: "application/json",
                            success: function (data) {
                                if ($.isEmptyObject(data)) {
                                    toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
                                } else {
                                    toastr['error']("La incidencia ha sido eliminada", "Aviso");
                                    $("#eliminarIncidenciasModal").modal('hide');
                                }
                            },
                            error: function (e) {
                                toastr["error"](e, "Error", {progressBar: true, closeButton: true});
                            }
                        });
                    });
                }
            },
            error: function (e) {
                toastr["error"](e, "Error", {progressBar: true, closeButton: true});
            }
        });
    });
}

const generarTxtExpedientesDuplicados = (nombreArchivoCargado, expedientesDuplicados) => {
    $("#modalExpedientesDuplicados").submit(function (event) {
        event.preventDefault();
        var fecha = new Date();
        var options = {year: 'numeric', month: 'long', day: 'numeric', weekday: 'long'};
        var linea1 = new Intl.DateTimeFormat('es-ES', options).format(fecha);
        var linea2 = "Expedientes duplicados encontrados en el archivo: " + nombreArchivoCargado;
        // Se hace la conversión del set en un arreglo y se separa por comas
        var expedientesArray = Array.from(expedientesDuplicados);
        var expedientesTexto = expedientesArray.join(', ');

        var contenido = linea1 + "\n" + linea2 + "\n" + expedientesTexto;
        var nombreTxt = "errorExpedientes.txt";
        var blob = new Blob([contenido], {type: "text/plain"});
        var url = window.URL.createObjectURL(blob);
        var a = document.createElement("a");
        a.href = url;
        a.download = nombreTxt;
        a.click();
        window.URL.revokeObjectURL(url);
        $("#modalExpedientesDuplicados").modal("hide");
    });
};

const generarTxtExpedientesDesconocidos = (nombreArchivoCargado, expedientesDesconocidos) => {
    $("#modalExpedientesDesconocidos").submit(function (event) {
        event.preventDefault();
        var fecha = new Date();
        var options = {year: 'numeric', month: 'long', day: 'numeric', weekday: 'long'};
        var linea1 = new Intl.DateTimeFormat('es-ES', options).format(fecha);
        var linea2 = "Expedientes incorrectos encontrados en el archivo: " + nombreArchivoCargado;
        // Se hace la conversión del set en un arreglo y se separa por comas
        var expedientesArray = Array.from(expedientesDesconocidos);
        var expedientesTexto = expedientesArray.join(', ');

        var contenido = linea1 + "\n" + linea2 + "\n" + expedientesTexto;
        var nombreTxt = "errorExpedientes.txt";
        var blob = new Blob([contenido], {type: "text/plain"});
        var url = window.URL.createObjectURL(blob);
        var a = document.createElement("a");
        a.href = url;
        a.download = nombreTxt;
        a.click();
        window.URL.revokeObjectURL(url);
        $("#modalExpedientesDesconocidos").modal("hide");
    });
};


//Esta funcion inhabilita los botones de guardar, eliminar y cargar archivo cuando el select nomina no está seleccionado
const validaBotones = () => {
    $("#file").val("");
    //Se hace reset al progreso y se oculta la barra de carga
    $("#progress-label").text("0%");
    $("#progress-container").hide();
    //Se verifica si el combo de la nómina no tiene valores para deshabilitar los botones o en su defecto, habilitar la carga del archivo
    if ($("#cmbTipoNomina").val() === "" || $("#cmbTipoNomina").val() === null) {
        $("#file, #guarda, #elimina").prop("disabled", true);
        //Se limpian los select de los periodos
        $("#periodo_generacion, #periodo_aplicacion").empty();
    } else {
        $("#file").prop("disabled", false);
    }
};

//Se listan los tipos de nómina provenientes de la base de datos dentro del combo
const cmbTipoNomina = (id) => {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbTipoNomina').empty().append("<option value=''>* Tipo de Nómina </option> ");
                if (data.length > 0) {
                    //Combo sin la estructura
                    for (var x = 0; x < data.length; x++) {
                        var vselected = (data[x].id_tipo_nomina === id) ? "selected" : "";
                        $('#cmbTipoNomina').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};

//Función para obtener los periodos de pago dependiendo de la nomina seleccionada
const obtenPeriodos = () => {
    var id_nomina = $("#cmbTipoNomina").val();
    if (id_nomina === null || id_nomina === "") {
    } else {
        $.ajax({
            type: "GET",
            url: "trabajador/buscaPeriodosFechas/" + id_nomina,
            dataType: 'json',
            success: function (data) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    var vselected = "";
                    var compara_periodo = comparaFechas(data, vselected);
                    if (data.length > 0) {
                        for (var x = 0; x < data.length; x++) {
                            var n_periodo = data[x].periodo;
                            var fecha_inicial_formato = new Date(data[x].fecha_inicial);
                            var dias_inicial = fecha_inicial_formato.getDate();
                            var meses_inicial = fecha_inicial_formato.getMonth() + 1;
                            var anios_inicial = fecha_inicial_formato.getFullYear();

                            var fecha_final_formato = new Date(data[x].fecha_final);
                            var dias_final = fecha_final_formato.getDate();
                            var meses_final = fecha_final_formato.getMonth() + 1;
                            var anios_final = fecha_final_formato.getFullYear();
                            if (compara_periodo !== data[x].periodo) {

                                $('#periodo_generacion').append('<option value="' + data[x].periodo + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
                                        '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>');

                                $('#periodo_aplicacion').append('<option value="' + data[x].periodo + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
                                        '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>');
                            }
                        }
                    }
                }
            },
            error: function (e) {
                toastr["error"](e, "Error", {progressBar: true, closeButton: true});
            }
        });
    }
};

// Asigna los periodos
const comparaFechas = (data, vselected) => {
    var hoy = new Date();
    hoy.setHours(0, 0, 0, 0);
    for (var i = 0; i < data.length; i++) {
        var n_periodo = data[i].periodo;
        //Fechas iniciales
        var fecha_inicial_formato = new Date(data[i].fecha_inicial);
        var dias_inicial = fecha_inicial_formato.getDate();
        var meses_inicial = fecha_inicial_formato.getMonth() + 1;
        var anios_inicial = fecha_inicial_formato.getFullYear();
        //Fechas finales
        var fecha_final_formato = new Date(data[i].fecha_final);
        var dias_final = fecha_final_formato.getDate();
        var meses_final = fecha_final_formato.getMonth() + 1;
        var anios_final = fecha_final_formato.getFullYear();
        fecha_inicial_formato.setHours(0, 0, 0, 0);
        fecha_final_formato.setHours(0, 0, 0, 0);
        var fecha_corte_formato = new Date(data[i].fecha_corte);
        fecha_corte_formato.setHours(0, 0, 0, 0);
        if (hoy <= fecha_corte_formato) {
            $('#periodo_generacion').empty().append('<option value="' + data[i].periodo + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
                    '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>');
            $('#periodo_aplicacion').empty().append('<option value="' + data[i].periodo + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
                    '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>');
            return data[i].periodo;
        } else if (hoy > fecha_corte_formato) {
            var contador = i + 1;
            n_periodo = data[contador].periodo;
            fecha_inicial_formato = new Date(data[contador].fecha_inicial);
            fecha_final_formato = new Date(data[contador].fecha_final);
            $('#periodo_generacion').empty().append('<option value="' + data[contador].periodo + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + fecha_inicial_formato.getDate() + '/' + (fecha_inicial_formato.getMonth() + 1) + '/' + fecha_inicial_formato.getFullYear() +
                    '--' + fecha_final_formato.getDate() + '/' + (fecha_final_formato.getMonth() + 1) + '/' + fecha_final_formato.getFullYear() + '</option>');
            $('#periodo_aplicacion').empty().append('<option value="' + data[contador].periodo + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + fecha_inicial_formato.getDate() + '/' + (fecha_inicial_formato.getMonth() + 1) + '/' + fecha_inicial_formato.getFullYear() +
                    '--' + fecha_final_formato.getDate() + '/' + (fecha_final_formato.getMonth() + 1) + '/' + fecha_final_formato.getFullYear() + '</option>');
            return data[contador].periodo;
        }
    }
};