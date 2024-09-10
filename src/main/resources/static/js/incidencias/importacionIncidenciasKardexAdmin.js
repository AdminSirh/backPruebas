document.addEventListener('DOMContentLoaded', () => {
    //*****************Mensajes al ingresar a la pantalla*************
    //Información para habilitar la carga del archivo
    toastr.info('Primero debe seleccionar el tipo nómina y el periodo de generación, posteriormente, la incidencia que desea importar', 'Información', {
        progressBar: true, closeButton: true, timeOut: 20000
    });
    //*************Llamada a Funciones**************
    validaBotones();
    cmbTipoNomina();
    cmbIncidencias();
    //************Constantes**************
    const cmbTipoNominaSelect = $("#cmbTipoNomina");
    const cmbIncidenciaSelect = $("#cmbIncidencia");
    //************Eventos de escucha**********
    //Al cambiar la nómina se validan los botones seleccionados 
    cmbTipoNominaSelect.change(() => {
        validaBotones();
        habilitarBotonElimina();
        //Se limpia la tabla de registros y se deshabilita el guardado
        let table = $('#tabla_importaciones').DataTable();
        $("#guarda").prop("disabled", true);
        table.clear().draw();
    });
    //Habilitar y deshabilitar el periodo de generación si no se tiene valor seleccionado para la nómina
    cmbTipoNominaSelect.change(function () {
        var selectedValue = $(this).val();
        if (selectedValue === "") {
            $('#periodo_generacion').prop('disabled', true);
        } else {
            $('#periodo_generacion').prop('disabled', false);
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

        // Establecer el texto en el label
        $("#descripcionIncidencia").text(textoSeleccionado);

        if (textoSeleccionado === "* Tipo de Incidencia") {
            $("#descripcionIncidencia").text("SIN INCIDENCIA SELECCIONADA");
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
const generaTablaPreProcesado = async (csvContent, nombreArchivoCargado) => {
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

    // Obtén la primera columna del CSV la cual debe contener todos los expedientes de los trabajadores ()
    let firstColumn = csvData.map(row => row[0]);
    //********************************VALIDACIÓN NÓMINAS TRABAJADORES*******************************
    let cmbTipoNomina = $('#cmbTipoNomina').val();
    try {
        const relacionNominaExpediente = await obtenerNominasTrabajadores(firstColumn, cmbTipoNomina);
        let elementosNoCorrespondientes = relacionNominaExpediente.filter((elemento) => {
            return elemento.nomina !== parseInt(cmbTipoNomina);
        });

        if (elementosNoCorrespondientes.length > 0) {
            $("#modalExpedientesDiferentesNominas").modal("show");
            toastr["warning"]("Se encontraron expedientes de diferentes nóminas, se canceló la importación", "Aviso", {progressBar: true, closeButton: true});
            generarTxtExpedientesDiferentesNominas(nombreArchivoCargado, elementosNoCorrespondientes);
            //Se detiene la ejecución de la funcion si hay expedientes de otras nóminas diferentes a las seleccionadas en el combo
            return;
        }
        // Hacer algo con elementosNoCorrespondientes aquí
    } catch (error) {
        console.error("Error al obtener nóminas de trabajadores", error);
    }
    //********************************VALIDACIÓN EXPEDIENTES DUPLICADOS*******************************
    let expedientesDuplicados = obtenerExpedientesDuplicados(firstColumn);
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

    //***********************************VALIDACIÓN EXPEDIENTES NO ENCONTRADOS***********************
    let expedientesDesconocidos;
    obtenerExpedientesDesconocidosOGeneraTabla(firstColumn, csvContent, table, periodoGeneracion)
            .then((expedientes) => {
                expedientesDesconocidos = expedientes;
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
                    const valorSeleccionadoCmbIncidencias = $("#cmbIncidencia").val();
//***************************************GUARDADO DE INCIDENCIAS DEL CSV ACORDE AL TIPO DE INCIDENCIA SELECCIONADO**********************************
                    switch (valorSeleccionadoCmbIncidencias) {
//            case '1':
//                // Acciones cuando se selecciona "Amonestaciones"
//                break;
                        case '2':
                            // Acciones cuando se selecciona "Faltas"
                            const incidenciaIdFaltas = {
                                //Falta
                                "F": 2,
                                //Retardo A
                                "0.5": 10,
                                //Retardo B
                                "0.7": 11,
                                //Retardo C
                                "0.1": 12,
                                //Amonestación
                                "A": 1,
                                //Suspensión
                                "S": 9
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdFaltas);
                            break;
                        case '3':
                            // Acciones cuando se selecciona "Permiso por Alumbramiento"
                            const incidenciaIdPermisoAlumbramiento = {
                                "P.A": 3
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdPermisoAlumbramiento);
                            break;
                        case '4':
                            // Acciones cuando se selecciona "Permiso por Fallecimiento"
                            const incidenciaIdPermisoFallecimiento = {
                                "P.F": 4
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdPermisoFallecimiento);
                            break;
                        case '5':
                            // Acciones cuando se selecciona "Notas Laudatorias"
                            const incidenciaIdNotasLaudatorias = {
                                "J": 5
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdNotasLaudatorias);
                            break;
                        case '6':
                            // Acciones cuando se selecciona "Permiso Ordinario"
                            const incidenciaIdPermisoOrdinario = {
                                "P": 6
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdPermisoOrdinario);
                            break;
                        case '7':
                            // Acciones cuando se selecciona "Vacaciones"
                            const incidenciaIdVacaciones = {
                                "V": 7
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdVacaciones);
                            break;
                        case '8':
                            // Acciones cuando se selecciona "Vacaciones Trabajadas"
                            const incidenciaIdVacacionesTrabajadas = {
                                "V.T": 8
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdVacacionesTrabajadas);
                            break;
//            case '9':
//                // Acciones cuando se selecciona "Suspensiones"
//                break;
//            case '10':
//                // Acciones cuando se selecciona "Retardo A"
//                break;
//            case '11':
//                // Acciones cuando se selecciona "Retardo B"
//                break;
//            case '12':
//                // Acciones cuando se selecciona "Retardo C"
//                break;
                        case '13':
                            // Acciones cuando se selecciona "Permiso s/goce sueldo"
                            const incidenciaIdPermisoSinGoceSueldo = {
                                "P.S": 13
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdPermisoSinGoceSueldo);
                            break;
                        case '14':
                            // Acciones cuando se selecciona "Incapacidad por EG al 50"
                            const incidenciaIdIncapacidadEg50 = {
                                "EG50": 14
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdIncapacidadEg50);
                            break;
                        case '15':
                            // Acciones cuando se selecciona "Incapacidad por EG al 75"
                            const incidenciaIdIncapacidadEg75 = {
                                "EG75": 15
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdIncapacidadEg75);
                            break;
                        case '16':
                            // Acciones cuando se selecciona "Incapacidad por EG al 100"
                            const incidenciaIdIncapacidadEg100 = {
                                "EG100": 16
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdIncapacidadEg100);
                            break;
                        case '17':
                            // Acciones cuando se selecciona "Incapacidad por RT al 100"
                            const incidenciaIdIncapacidadRt100 = {
                                "RT100": 17
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdIncapacidadRt100);
                            break;
                        case '18':
                            // Acciones cuando se selecciona "Incapacidad por PRE al 100"
                            const incidenciaIdIncapacidadPre100 = {
                                "PRE100": 18
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdIncapacidadPre100);
                            break;
                        case '19':
                            // Acciones cuando se selecciona "Permiso Sindical"
                            const incidenciaIdPermisoSindical = {
                                "PSINDICAL": 19
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdPermisoSindical);
                            break;
                        case '20':
                            // Acciones cuando se selecciona "Sin Contrato"
                            const incidenciaIdSinContrato = {
                                "S.C": 20
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdSinContrato);
                            break;
                        case '21':
                            // Acciones cuando se selecciona "Orden de Trabajo"
                            const incidenciaIdOrdenTrabajo = {
                                "O.T": 21
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdOrdenTrabajo);
                            break;
                        case '22':
                            // Acciones cuando se selecciona "Incapacidad por POS al 100"
                            const incidenciaIdPos100 = {
                                "POS100": 22
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdPos100);
                            break;
                        case '23':
                            // Acciones cuando se selecciona "Incapacidad por EN al 60"
                            const incidenciaIdEn60 = {
                                "EN60": 23
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdEn60);
                            break;
                        case '24':
                            // Acciones cuando se selecciona "Incapacidad Compensación ET al 25"
                            const incidenciaIdEt25 = {
                                "ET25": 24
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdEt25);
                            break;
                        case '25':
                            // Acciones cuando se selecciona "Incapacidad Compensación ET al 50"
                            const incidenciaIdEt50 = {
                                "ET50": 25
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdEt50);
                            break;
                        case '26':
                            // Acciones cuando se selecciona "Incapacidad por EN al 100"
                            const incidenciaIdEn100 = {
                                "EN100": 26
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdEn100);
                            break;
                        case '27':
                            // Acciones cuando se selecciona "Incapacidad por EG al 0"
                            const incidenciaIdEg0 = {
                                "EG0": 27
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdEg0);
                            break;
                        case '28':
                            // Acciones cuando se selecciona "Compensación por RT al 0"
                            const incidenciaIdRt0 = {
                                "RT0": 28
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdRt0);
                            break;
                        case '29':
                            // Acciones cuando se selecciona "Compensación por PRE al 0"
                            const incidenciaIdPre0 = {
                                "PRE0": 29
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdPre0);
                            break;
                        case '30':
                            // Acciones cuando se selecciona "Compensación por POS al 0"
                            const incidenciaIdPos0 = {
                                "POS0": 30
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdPos0);
                            break;
                        case '31':
                            // Acciones cuando se selecciona "Compensación por EG al 60"
                            const incidenciaIdCompensacionEg60 = {
                                "EG60": 31
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdCompensacionEg60);
                            break;
                        case '32':
                            // Acciones cuando se selecciona "Falta en Tarjeta/Turno"
                            const incidenciaIdFaltaTarjetaTurno = {
                                "FT": 32
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdFaltaTarjetaTurno);
                            break;
                        case '33':
                            // Acciones cuando se selecciona "Permiso Retribuido"
                            const incidenciaIdPermisoRetribuido = {
                                "PRCF": 33
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdPermisoRetribuido);
                            break;
                        case '34':
                            // Acciones cuando se selecciona "Incapacidad por PRP al 100"
                            const incidenciaIdIncapacidadPrp100 = {
                                "PRP100": 34
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdIncapacidadPrp100);
                            break;
                        case '35':
                            // Acciones cuando se selecciona "Interrupción de Embarazo"
                            const incidenciaIdInterrupcionEmbarazo = {
                                "I.E": 35
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdInterrupcionEmbarazo);
                            break;
                        case '36':
                            // Acciones cuando se selecciona "Adopción"
                            const incidenciaIdAdopcion = {
                                "A.D": 36
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdAdopcion);
                            break;
                        case '37':
                            // Acciones cuando se selecciona "Diagnóstico de Cáncer"
                            const incidenciaIdDiagnosticoCancer = {
                                "D.C": 37
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdDiagnosticoCancer);
                            break;
                        case '38':
                            // Acciones cuando se selecciona "Cumpleaños"
                            const incidenciaIdPermisoCumpleanios = {
                                "P.C": 38
                            };
                            guardaIncidenciasCSV(csvContent, incidenciaIdPermisoCumpleanios);
                            break;
                        default:
                            // Acciones por defecto si no coincide con ningún caso
                            break;
                    }
                }
                ;
            })
            .catch((error) => {
                toastr["error"]("Hubo un error al obtener expedientes desconocidos: " + error, "Error", {progressBar: true, closeButton: true});
            });
};

const obtenerNominasTrabajadores = async(firstColumn, cmbTipoNomina) => {
    var enteroCmbTipoNomina = parseInt(cmbTipoNomina);
    return Promise.all(firstColumn.map(async (expediente) => {
        return new Promise((resolve, reject) => {
            $.ajax({
                type: "GET",
                url: "trabajador/buscarNominaPorExpediente/" + expediente,
                dataType: 'json',
                success: function (data, status, xhr) {
                    if (data !== null && data !== undefined) {
                        const nomina = data;
                        const objetoTrabajador = {
                            expediente: expediente,
                            nomina: nomina
                        };
                        resolve(objetoTrabajador);
                    } else {
                        //Se le coloca la nómina cuando no se encuentra el expediente ya que la validación de expedientes desconocidos ocurre después de esta función
                        resolve({expediente: expediente, nomina: enteroCmbTipoNomina});
                    }
                },
                error: function (e) {
                    console.error("Error fetching data for expediente", expediente, e);
                    resolve({expediente: expediente, nomina: null});
                }
            });
        });
    }));
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

const obtenerExpedientesDesconocidosOGeneraTabla = (firstColumn, csvContent, table, periodoGeneracion) => {
    // Hice la promesa para no colocar respuestas de ajax asíncronas y anidadas
    return new Promise((resolve, reject) => {
        const expedientesDesconocidos = new Set();
        const requests = [];
        firstColumn.forEach((expediente) => {
            requests.push(
                    new Promise((innerResolve, innerReject) => {
                        $.ajax({
                            type: "GET",
                            url: "trabajador/buscarTrabajador_NumExpediente/" + expediente,
                            dataType: 'json',
                            success: function (data) {
                                if (data.data === null || data.data === undefined) {
                                    expedientesDesconocidos.add(expediente);
                                } else {
                                    let expedienteNumero = data.data.cat_expediente.numero_expediente;
                                    let nombreCompleto = data.data.persona.nombre + ' ' + data.data.persona.apellido_paterno + ' ' + data.data.persona.apellido_materno;

                                    // Parsea la fila actual del CSV y cuenta columnas no vacías
                                    const csvColumns = csvContent.split('\n')[firstColumn.indexOf(expediente)].split(',');
                                    const conteoColumnasNoVacias = csvColumns.slice(1).filter(col => col.trim() !== '').length;

                                    // Usa el conteo de columnas no vacías como "monto"
                                    let monto = conteoColumnasNoVacias;

                                    $("#guarda").prop("disabled", false);
                                    table.row.add([
                                        expedienteNumero,
                                        nombreCompleto,
                                        monto,
                                        periodoGeneracion,
                                        //periodoAplicacion,
                                        ''
                                                //0
                                    ]).draw().node();
                                }
                                innerResolve();
                            },
                            error: function (e) {
                                toastr["error"](e, "Error", {progressBar: true, closeButton: true});
                                innerResolve();
                            }
                        });
                    })
                    );
        });
        Promise.all(requests)
                .then(() => {
                    resolve(expedientesDesconocidos);
                })
                .catch((error) => {
                    reject(error);
                });
    });
};



//Se guardan las incidencias ya que fueron procesadas, validadas y son mostradas en la tabla, aquí se valida por último las iniciales que no se reconozcan
const guardaIncidenciasCSV = (csvContent, inicialesId) => {
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
                    //**************************CARGA DE INCIDENCIAS************************************
                    let fechaInicio = new Date(data.data.fecha_inicial);
                    let fechaFin = new Date(data.data.fecha_final);
                    let diferenciaEnMilisegundos = fechaFin - fechaInicio;
                    //Más uno contando el primer día
                    let diferenciaEnDias = diferenciaEnMilisegundos / (1000 * 60 * 60 * 24) + 1;
                    //Con las fechas iniciales y finales se realiza la lectura del csv
                    let lines = csvContent.split('\n');
                    let inicialesDesconocidas = [];
                    lines.forEach(function (line) {
                        let columns = line.split(',');
                        // La primera columna es el expediente del trabajador
                        let expediente = columns[0];
                        //******************************Validación de fechas correctas para el periodo***************************
                        if (columns.length - 1 > diferenciaEnDias) {
                            let table = $('#tabla_importaciones').DataTable();
                            //Se resetea la barra de progreso
                            resetProgress();
                            //Se elimina la tabla ya que no se puede hacer carga de archivos erróneos
                            $("#guarda").prop("disabled", true);
                            table.clear().draw();
                            toastr["error"](`Se excedieron los días del periodo en el archivo CSV, verificar contenido`, "Error", {progressBar: true, closeButton: true});
                            return;
                        }
                        // Las columnas 2 en adelante representan las incidencias
                        for (let i = 1; i < columns.length; i++) { // Empezamos desde la columna 1
                            // Elimina espacios en blanco para obtener registros de la última columna de manera correcta
                            let incidencia = columns[i].trim();
                            if (incidencia) {
                                // Verifica si la inicial es conocida en el contexto de la incidencia que se genera (Switch)
                                if (inicialesId.hasOwnProperty(incidencia)) {
                                    let idIncidencia = inicialesId[incidencia];
                                    let fecha = new Date(fechaInicio);
                                    fecha.setDate(fecha.getDate() + i - 1); // Incrementa la fecha en i - 1 días
                                    ajaxSaveIncidencia(expediente, fecha, idIncidencia);
                                    //console.log(`Expediente ${expediente}: ${incidencia} el ${fecha.toISOString()}`);
                                } else {
                                    // La inicial es desconocida, la agregamos a inicialesDesconocidas
                                    if (!inicialesDesconocidas[incidencia]) {
                                        inicialesDesconocidas[incidencia] = [];
                                    }
                                    inicialesDesconocidas[incidencia].push(expediente);
                                }
                            }
                        }
                    });
                    /********************************VALIDACIÓN PARA INICIALES DE LAS INCIDENCIAS DESCONOCIDAS****************
                     Si el objeto se mapeo con iniciales desconocidas se muestra el mensaje al usuario y se le da la opción de descargar el txt con las iniciales que no fueron reconocidas y los expedientes relacionados*/
                    if (Object.keys(inicialesDesconocidas).length > 0) {
                        // Muestra el modal y el mensaje de error
                        $("#modalInicialesDesconocidas").modal("show");
                        const unknownInicialesMessage = Object.keys(inicialesDesconocidas).map(initial => {
                            return `${inicialesDesconocidas[initial].join(', ')}: ${initial}`;
                        }).join(', ');
                        generarTxtInicialesDesconocidas(inicialesDesconocidas);
                        toastr["error"](`Se omitieron los siguientes registros debido a iniciales desconocidas encontradas: ${unknownInicialesMessage}`, "Error", {progressBar: true, closeButton: true});
                        return;
                    }
                }
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar periodos de pago : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    });
};

const ajaxSaveIncidencia = async (expediente, fecha, idIncidencia) => {
    try {
        //Se obtiene el id del trabajador mediante el expediente
        const id_trabajador = await obtenerIdTrabajador(expediente);
        //Se obtienen las incidencias del trabajador para verificar que no se repitan los registros
        const incidenciasTrabajador = await obtenerIncidenciasTrabajador(id_trabajador);
        let fechaInicio = fecha;
        fechaInicio.setHours(fechaInicio.getHours() - 6);
        const estado_incidencia_id = 1;
        const folio = 'N/A';
        const bis = 'N/A';
        const num_dias = 1;
        const datos = {
            "num_dias": num_dias,
            "cat_estado_incidencia": {"id_estado_incidencia": estado_incidencia_id},
            "cat_incidencias": {"id_incidencia": idIncidencia},
            "fecha_inicio": fechaInicio,
            "fecha_fin": fechaInicio,
            "folio": folio,
            "bis": bis,
            "observaciones": 'Cargado masivamente desde CSV de importación',
            "trabajador_id": id_trabajador
        };
        //********************Se verifica que no existan registros para ese mismo día*************************
        const incidenciasDuplicadas = incidenciasTrabajador.filter(incidencia => sonIguales(incidencia, datos));
        if (incidenciasDuplicadas.length > 0) {
            let table = $('#tabla_importaciones').DataTable();
            //Se resetea la barra de progreso
            resetProgress();
            //Se elimina la tabla ya que no se puede hacer carga de archivos erróneos
            $("#guarda").prop("disabled", true);
            table.clear().draw();
            toastr['error']("Ya existen incidencias del mismo tipo y el mismo día para el/los trabajadores correspondientes", "Error");
            juntaDuplicados(incidenciasDuplicadas);
            return;
        }

        //Se actualiza la Incapacidad correspondiente al generar la incidencia
        await $.ajax({
            type: "POST",
            url: "incidencias/guardarIncidencia",
            data: JSON.stringify(datos),
            contentType: "application/json"
        });
        toastr['success']("Incidencia Generada correctamente", "Éxito");
        indicaProcesadoTabla(expediente);
    } catch (e) {
        toastr["warning"]("Error al guardar los datos : " + e, "Error", {progressBar: true, closeButton: true});
    }
};

function sonIguales(incidencia, datos) {
    const fechaInicioIncidencia = new Date(incidencia.fecha_inicio).getTime();
    const fechaInicioDatos = new Date(datos.fecha_inicio).getTime();

    const fechaFinIncidencia = new Date(incidencia.fecha_fin).getTime();
    const fechaFinDatos = new Date(datos.fecha_fin).getTime();
    return (
            incidencia.trabajador_id === datos.trabajador_id &&
            fechaInicioIncidencia === fechaInicioDatos &&
            fechaFinIncidencia === fechaFinDatos &&
            incidencia.cat_incidencias.id_incidencia === datos.cat_incidencias.id_incidencia
            );
}

const obtenerIdTrabajador = async (expediente) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "GET",
            url: "trabajador/buscarTrabajador_NumExpediente/" + expediente,
            dataType: 'json',
            success: (data) => {
                resolve(data.data.id_trabajador);
            },
            error: (e) => {
                reject(e);
            }
        });
    });
};

const obtenerIncidenciasTrabajador = async (id_trabajador) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "GET",
            url: "incidencias/buscarIncidencias/" + id_trabajador,
            dataType: 'json',
            success: (data) => {
                resolve(data);
            },
            error: (e) => {
                reject(e);
            }
        });
    });
};

//Se marcan con un si los registros que fueron procesado exitosamente
const indicaProcesadoTabla = (expediente) => {
    // Busca todas las filas en la tabla
    $('#tabla_importaciones tbody tr').each(function () {
        // Obtiene el valor de la celda EXPEDIENTE en la fila actual
        var expedienteCelda = $(this).find('td:eq(0)').text();
        // Obtiene el valor de la celda en la columna PROCESADO (columna 5)
        var procesadoCelda = $(this).find('td:eq(5)');

        if (expedienteCelda === expediente) {
            // Se indica que el registro correspondiente al expediente fue procesado
            procesadoCelda.text('Sí');
        } else if (procesadoCelda.text() === '') {
            // Marca como "No" en rojo si la celda está vacía
            procesadoCelda.text('No');
        }
    });
};

//Se eliminan todas las incidencias del periodo de aplicación seleccionado para la nómina seleccionada
const eliminarIncidenciasSeleccionadas = () => {
    $("#eliminarIncidenciasModal").submit(function (event) {
        event.preventDefault();
        let estatus = 0;
        let incidenciasFaltas = [1, 2, 9, 10, 11, 12];
        let incidenciaSeleccionada = $('#cmbIncidencia').val();
        let idTipoNomina = $('#cmbTipoNomina').val();
        let periodoGeneracion = $('#periodo_generacion').val();
        let url;

        if (incidenciaSeleccionada === '2') {
            url = "incidencias/buscarIncidenciasImportacion/" + idTipoNomina + "/" + periodoGeneracion + "/" + incidenciasFaltas;
        } else {
            url = "incidencias/buscarIncidenciasImportacion/" + idTipoNomina + "/" + periodoGeneracion + "/" + incidenciaSeleccionada;
        }
        //Se obtienen las incidencias con los parámetros seleccionados (Nómina, periodo y tipos de incidencias)
        $.ajax({
            type: "GET",
            url: url,
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
                                    toastr['error']("Las incidencias seleccionadas han sido eliminadas para la nómina y periodos seleccionados", "Aviso");
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
};

const arregloUnico = [];
//Esta función se encarga de generar el arreglo con todos los elementos duplicados proveniente de la base de datos
const juntaDuplicados = (registros) => {
    arregloUnico.push(...registros);
    //Se llama a la función que genera el txt con los registros duplicados detectados
    generarTxtRegistrosDuplicados(arregloUnico);
};

//Bandera de ejecución única para registros que resultan duplicados
let flagTxtDuplicados = false;
//Se genera el txt si el usuario lo desea para mostrarle los datos de los registros duplicados encontrados
const generarTxtRegistrosDuplicados = (arregloUnicoDuplicados) => {
    if (flagTxtDuplicados === false) {
        $("#modalRegistrosDuplicados").modal("show");
        $("#modalRegistrosDuplicados").submit(function (event) {
            event.preventDefault();
            var fecha = new Date();
            var options = {year: 'numeric', month: 'long', day: 'numeric', weekday: 'long'};
            var linea1 = new Intl.DateTimeFormat('es-ES', options).format(fecha);

            // Preparar un array de líneas para el archivo TXT
            var lineas = [linea1];
            // Iterar sobre los datos y agregarlos al array de líneas
            arregloUnicoDuplicados.forEach(function (registro) {
                const linea = `ID Trabajador: ${registro.trabajador_id}, ID Incidencia: ${registro.id_incidencia}, Fecha inicio:  ${registro.fecha_inicio}, Fecha Fin:  ${registro.fecha_fin},  Descripción: ${registro.cat_incidencias.descripcion}`;
                lineas.push(linea);
            });
            // Unir las líneas en un solo contenido de texto
            var contenido = lineas.join('\n');
            var nombreTxt = "registrosDuplicados.txt"; // Nombre del archivo TXT
            var blob = new Blob([contenido], {type: "text/plain"});

            // Crear un enlace y simular un clic para descargar el archivo
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement("a");
            a.href = url;
            a.download = nombreTxt;
            a.style.display = 'none';
            document.body.appendChild(a);
            a.click();

            // Limpiar y revocar el objeto URL después de la descarga
            window.URL.revokeObjectURL(url);
            $("#modalRegistrosDuplicados").modal("hide");

        });
        //Se marca la bandera de ejecución para evitar múltiples ejecuciones y por ende varias descargas
        flagTxtDuplicados = true;
    }
};

const generarTxtInicialesDesconocidas = (inicialesDesconocidas) => {
    $("#modalInicialesDesconocidas").submit(function (event) {
        event.preventDefault();
        var fecha = new Date();
        var options = {year: 'numeric', month: 'long', day: 'numeric', weekday: 'long'};
        var linea1 = new Intl.DateTimeFormat('es-ES', options).format(fecha);

        // Preparar un array de líneas para el archivo TXT
        var lineas = [linea1];
        // Iterar sobre las iniciales desconocidas y agregarlas al array de líneas
        for (const inicial in inicialesDesconocidas) {
            if (inicialesDesconocidas.hasOwnProperty(inicial)) {
                const expedientes = inicialesDesconocidas[inicial];
                const linea = `${expedientes.join(', ')}: ${inicial}`;
                lineas.push(linea);
            }
        }
        // Unir las líneas en un solo contenido de texto
        var contenido = lineas.join('\n');
        var nombreTxt = "inicialesDesconocidas.txt";
        var blob = new Blob([contenido], {type: "text/plain"});
        var url = window.URL.createObjectURL(blob);
        var a = document.createElement("a");
        a.href = url;
        a.download = nombreTxt;
        a.click();
        window.URL.revokeObjectURL(url);
        $("#modalInicialesDesconocidas").modal("hide");
    });
};

const generarTxtExpedientesDiferentesNominas = (nombreArchivoCargado, elementosNoCorrespondientes) => {
    resetProgress();
    $("#modalExpedientesDiferentesNominas").submit(function (event) {
        event.preventDefault();
        var fecha = new Date();
        var options = {year: 'numeric', month: 'long', day: 'numeric', weekday: 'long'};
        var linea1 = new Intl.DateTimeFormat('es-ES', options).format(fecha);
        var linea2 = "Expedientes de diferentes nóminas encontrados en el archivo: " + nombreArchivoCargado;

        // Convierte cada objeto en una cadena legible
        var expedientesTexto = elementosNoCorrespondientes.map(item => `Expediente: ${item.expediente}, Nómina: ${item.nomina}`).join(', ');

        var contenido = linea1 + "\n" + linea2 + "\n" + expedientesTexto;
        var nombreTxt = "errorNominas.txt";
        var blob = new Blob([contenido], {type: "text/plain"});
        var url = window.URL.createObjectURL(blob);
        var a = document.createElement("a");
        a.href = url;
        a.download = nombreTxt;
        a.click();
        window.URL.revokeObjectURL(url);
        $("#modalExpedientesDiferentesNominas").modal("hide");
    });
};


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

//Función para llenar las percepciones y todas las deducciones de las nóminas
const cmbIncidencias = () => {
    const selectIncidencias = $('#cmbIncidencia');
    selectIncidencias.empty().append('<option value="">Cargando...</option');
    $.ajax({
        type: "GET",
        url: "catalogos/listarIncidenciasKardex",
        dataType: 'json',
        success: function (data) {
            selectIncidencias.empty();
            if (data.length === 0) {
                selectIncidencias.append('<option value="">No hay datos disponibles</option>');
            } else {
                // Filtrar y ordenar las incidencias por id_incidencia
                const filteredData = data.filter(incidencia => ![1, 3, 4, 7, 8, 9, 10, 11, 12, 14, 15, 16, , 17, 18, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 33, 34].includes(incidencia.id_incidencia));
                /*Se filtran las incidencias anteriores ya que la importación de faltas puede englobar los Retardos A,B,C, Amonestaciones, Suspensiones y las propias faltas, además 
                 * se filtran todas las incapacidades y compensaciones puesto que su carga se realiza desde el módulo de autorización y la compensación se agrega de manera individual para 
                 * casos específicos */
                filteredData.sort((a, b) => a.id_incidencia - b.id_incidencia);
                // Opción por defecto
                selectIncidencias.append('<option value="">* Tipo de Incidencia</option');

                filteredData.forEach(function (incidencia) {
                    let optionText = `(${incidencia.id_incidencia}) - ${incidencia.descripcion} - ${incidencia.inicial_descripcion}`;
                    if (incidencia.id_incidencia === 2) {
                        // Se agrega la información para el id 2 que corresponde a faltas
                        optionText += ' (Retardos A (0.5), B (0.7), C (0.1), Amonestaciones (A) y Suspensiones (S))';
                    }
                    selectIncidencias.append(`<option value=${incidencia.id_incidencia}>${optionText}</option>`);
                });
            }
            toastr.success("Se cargaron las Incidencias del Kárdex");
        },
        error: function (e) {
            toastr.error(e);
        }
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