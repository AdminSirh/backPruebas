//VARIABLES GLOBALES 
//Para no realizar filtrados repetidos
let fechaInicialAnterior = null;
let fechaFinalAnterior = null;
let inputFolioAnterior = null;
let trabajador_id;
//Carga del DOM
document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    trabajador_id = urlParams.get('id_trabajador');
    rellenaCamposDatosGeneralesTrabajador(trabajador_id);
    vincularEventosInputs();
});

function vincularEventosInputs() {
    var checkboxFechas = $('#filtradoFechas');
    var checkboxFolio = $('#filtradoFolio');
    var inputFolio = $('#inputFolio');
    var fechaInicial = $('#fechaInicialLiberacion');
    var fechaFinal = $('#fechaFinalLiberacion');
    var botonFiltrar = $('#btnFiltrar');
    //Motivo de la incapacidad
    llenarComboBox("catalogos/listarMotivosIncapacidad", "#cmbMotivoIncapacidad", "descripcion_incapacidad", "id_motivo_incapacidad");
    //Tipo de incapacidad
    llenarComboBox("catalogos/listarTiposIncapacidad", "#cmbTipoIncapacidad", "descripcion_tipo_incapacidad", "id_tipo_incapacidad");
    //Tipo de riesgo de la incapacidad
    llenarComboBox("catalogos/listarTipoRiesgoIncapacidad", "#cmbTipoRiesgo", "descripcion_riesgo", "id_tipo_riesgo");
    //Tipo secuelas de la incapacidad
    llenarComboBox("catalogos/listarTipoSecuelasIncapacidad", "#cmbSecuelas", "descripcion", "id_tipo_secuela");
    //Tipo control de la incapacidad
    llenarComboBox("catalogos/listarTipoControlIncapacidad", "#cmbControlIncapacidad", "descripcion", "id_tipo_control");

    // Agregar eventos de entrada a los campos de fecha y búsqueda por folio del filtrado
    $('#fechaInicialLiberacion, #fechaFinalLiberacion, #inputFolio').on('input', actualizarEstadoBotonFiltrar);
    //Función de filtrado
    checkboxFechas.change(function () {
        if (checkboxFechas.is(':checked')) {
            fechaInicial.prop('disabled', false);
            fechaFinal.prop('disabled', false);
            checkboxFolio.prop('checked', false);
            inputFolio.prop('disabled', true);
            resetCampos(fechaInicial, fechaFinal, inputFolio, botonFiltrar);
        } else {
            fechaInicial.prop('disabled', true);
            fechaFinal.prop('disabled', true);
            resetCampos(fechaInicial, fechaFinal, inputFolio, botonFiltrar);
        }
    });

    checkboxFolio.change(function () {
        if (checkboxFolio.is(':checked')) {
            inputFolio.prop('disabled', false);
            checkboxFechas.prop('checked', false);
            fechaInicial.prop('disabled', true);
            fechaFinal.prop('disabled', true);
            resetCampos();
        } else {
            inputFolio.prop('disabled', true);
            resetCampos(fechaInicial, fechaFinal, inputFolio, botonFiltrar);
        }
    });

}
//Reset de los campos de filtrado
function resetCampos(fechaInicial, fechaFinal, inputFolio, botonFiltrar) {
    if (fechaInicial)
        fechaInicial.val('');
    if (fechaFinal)
        fechaFinal.val('');
    if (inputFolio)
        inputFolio.val('');
    if (botonFiltrar)
        botonFiltrar.prop('disabled', true);
}

function redireccionTabIncidencias() {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    window.location.href = 'trabajadorIncidencias?id_trabajador=' + trabajador_id;
}

//Redirección al kárdex
function verKardexTrabajador() {
    window.location.href = 'guardaInasistencias?id_trabajador=' + trabajador_id;
}
//Home incidencias
function adminIncidencias() {
    window.location.href = 'incidenciasAdmin';
}
//Carga los campos del trabajador que son mostrados en la página
function rellenaCamposDatosGeneralesTrabajador(trabajador_id) {
    $.ajax({
        type: "GET",
        url: `trabajador/buscarTrabajador/${trabajador_id}`,
        dataType: 'json',
        success: successHandler,
        error: errorHandler
    });
}

function successHandler(data) {
    const expediente = data.data.cat_expediente;
    const persona = data.data.persona;
    $('#campo_expediente').val(expediente.numero_expediente);
    $('#campo_nombre').val(`${persona.nombre} ${persona.apellido_paterno} ${persona.apellido_materno}`);
    $('#campo_nss').val(persona.num_imss);
    $('#prestaciones').val(data.data.prestaciones_si_no);
    const prestacionesSiNo = data.data.prestaciones_si_no === 1 ? 'Sí' : 'No';
    $('#campo_prestaciones_si_no').val(prestacionesSiNo);
    buscarNomina(trabajador_id);
}

function errorHandler(e) {
    toastr['error']("Ocurrió un error al obtener información del trabajador " + e, "Aviso");
}
//Se obtiene la nómina a la que pertenece el trabajador
function buscarNomina(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPlazaTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            $('#id_nomina').val(data.data.cat_Tipo_Nomina.id_tipo_nomina);
            $('#campo_nomina').val(data.data.cat_Tipo_Nomina.desc_nomina);
            if (data.data.cat_Tipo_Nomina.desc_nomina !== "Confianza") {
                eliminarCampoPrestaciones();
            }
        },
        error: function (e) {
            alert(e);
        }
    });
}
function eliminarCampoPrestaciones() {
    $('#campo_prestaciones_si_no').closest('.col').remove();
    $('#campo_prestaciones_si_no').prev('label').remove();
    $('#campo_prestaciones_si_no').remove();
}
;
//Rellena los diferentes listados para el combo box de el detalle de la incapacidad
function llenarComboBox(url, idCampo, descripcionCampo, idSeleccionar, id) {
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $(idCampo).empty().append('<option value="">*Sin opción seleccionada</option>');
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        var vselected = (data[x].cve_original === id) ? "selected" : "";
                        $(idCampo).append('<option value="' + data[x][idSeleccionar] + '"' + vselected + '>' + data[x][descripcionCampo] + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar datos del select: " + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
// Función para habilitar/deshabilitar el botón "Filtrar" el
function actualizarEstadoBotonFiltrar() {
    var fechaInicial = $('#fechaInicialLiberacion').val();
    var fechaFinal = $('#fechaFinalLiberacion').val();
    var inputFolio = $('#inputFolio').val();
    if (fechaInicial && fechaFinal) {
        $('#btnFiltrar').prop('disabled', false);
    } else if (inputFolio) {
        $('#btnFiltrar').prop('disabled', false);
    } else {
        toastr["info"]("Selecciona la fecha inicial y la fecha final o realiza la búsqueda por el folio de la incapacidad para habilitar la búsqueda", "Información", {progressBar: true, closeButton: true});
        $('#btnFiltrar').prop('disabled', true);
    }
}

function filtrar() {
    const fechaInicial = $('#fechaInicialLiberacion').val();
    const fechaFinal = $('#fechaFinalLiberacion').val();
    const inputFolio = $('#inputFolio').val().trim();

    const mostrarAviso = (mensaje) => {
        toastr["warning"](mensaje, "Aviso", {progressBar: true, closeButton: true});
    };

    if (fechaInicial === fechaInicialAnterior && fechaFinal === fechaFinalAnterior) {
        mostrarAviso("Se está mostrando el filtrado actual");
        return;
    }

    if (inputFolio === inputFolioAnterior) {
        mostrarAviso("Se está mostrando el filtrado actual");
        return;
    }

    if (inputFolio) {
        generarDataTableConFiltros(null, null, inputFolio);
        inputFolioAnterior = inputFolio;
    } else {
        fechaInicialAnterior = fechaInicial;
        fechaFinalAnterior = fechaFinal;
        generarDataTableConFiltros(fechaInicial, fechaFinal);
    }
}
//Carga la tabla de las incapacidades acorde a los criterios de búsqueda
function generarDataTableConFiltros(fechaInicial, fechaFinal, folio) {
    const expedienteTrabajador = $('#campo_expediente').val();
    let url;
    if (fechaInicial && fechaFinal) {
        url = `incidencias/encontrarPeriodosLiberacion/${encodeURIComponent(expedienteTrabajador)}/${fechaInicial}/${fechaFinal}`;
    } else {
        url = `incidencias/buscarIncapacidadesFolio/${folio}/${encodeURIComponent(expedienteTrabajador)}`;
    }

    // Obtener la referencia a la tabla existente, si existe
    const tablaIncapacidades = $('#tabla_incapacidades').DataTable();

    // Destruir la tabla existente si está inicializada
    if ($.fn.DataTable.isDataTable('#tabla_incapacidades')) {
        tablaIncapacidades.destroy();
    }

    $('#tabla_incapacidades').DataTable({
        pageLength: 100,
        ajax: {
            url: url,
            method: 'GET',
            dataSrc: ''
        },
        responsive: true,
        bProcessing: true,
        select: true,
        language: {
            'processing': 'Procesando espera...',
            'sProcessing': 'Procesando...',
            'sLengthMenu': 'Mostrar _MENU_ registros',
            'sZeroRecords': 'No se encontraron resultados',
            'sEmptyTable': ' ',
            'sInfo': ' _START_ al _END_ Total: _TOTAL_',
            'sInfoEmpty': ' ',
            'sInfoFiltered': '(filtrado de un total de _MAX_ registros)',
            'sInfoPostFix': '',
            'search': 'Buscar',
            'paginate': {
                'previous': 'Anterior',
                'next': 'Siguiente'
            },
            'paging': false,
            'bPaging': false,
            'scrollY': '300px',
            'sUrl': '',
            'sInfoThousands': ',',
            'sLoadingRecords': 'Cargando...',
            'oAria': {
                'sSortAscending': ': Activar para ordenar la columna de manera ascendente',
                'sSortDescending': ': Activar para ordenar la columna de manera descendente'
            }
        },
        columns: [
            {data: 'fecha_movimiento', render: renderFecha},
            {data: 'fecha_inicial', render: renderFecha},
            {data: 'fecha_final', render: renderFecha},
            {data: 'folio'},
            {'data': 'cat_Motivo_Incapacidad.descripcion_incapacidad'},
            {'data': 'cat_Tipo_Incapacidad.descripcion_tipo_incapacidad'},
            {data: '', sTitle: 'AUTORIZADO/CANCELADO', width: 150, visible: true, render: renderBoton}
        ]
    });
}
//Se formatea la fecha al formato deseado en el datatable
function renderFecha(data, type, full, meta) {
    if (type === 'display' || type === 'filter') {
        // Formatear la fecha para mostrarla en "dd/mm/yyyy"
        const date = new Date(data);
        const day = date.getDate();
        const month = date.getMonth() + 1;
        const year = date.getFullYear();
        return (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + year;
    }
    return data;
}
//Se renderizan los botones correspondientes
function renderBoton(d, t, r) {
    let he;
    // Se reciben en cero para incapacidades canceladas o nulo para incapacidades recién recibidas de Servicio Médico
    if (r.autorizar === 0 || r.autorizar === null) {
        he = `<button type="button" id="btndistrict${r.id_incapacidad}" class="btn btn-outline-success" onclick="obtenerInformacionIncapacidadParaModal(${r.id_incapacidad});"> <span class="fa fa-minus-circle"></span> Autorizar</button>`;
    } else {
        if (r.pagada !== 1) {
            he = `<button type="button" id="btndistrict${r.id_incapacidad}" class="btn btn-outline-danger" onclick="obtenerInformacionIncapacidadParaModal(${r.id_incapacidad})"> <span class="fa fa-minus-circle"></span> Rechazar</button>`;
        } else {
            he = `<button type="button" id="btndistrict${r.id_incapacidad}" class="btn btn-outline-danger" disabled> <span class="fa fa-minus-circle"></span> Rechazar</button>`;
        }
    }
    return he;
}

function obtenerInformacionIncapacidadParaModal(id_incapacidad) {
    $("#incapacidadIMSS").modal("toggle");
    $.ajax({
        type: "GET",
        url: "incidencias/buscarIdIncapacidad/" + id_incapacidad,
        dataType: 'json',
        success: function (data) {
            const statusLabel = $('#statusLabel');
            const chkGeneraIncidencia = $('#chkGeneraIncidencia');
            if (data.data.autorizar === 1) {
                statusLabel.html('<i class="fas fa-check"></i> AUTORIZADO EN NÓMINA');
                statusLabel[0].style.color = 'green'; // Establecer el color a verde
                $('#btnBajaIncapacidad').show();
                $('label[for="chkGeneraIncidencia"]').hide();
                chkGeneraIncidencia.hide();
                $('#btnGeneraIncidenciaIncapacidad').hide();
            } else {
                statusLabel.html('<i class="fas fa-times"></i> NO AUTORIZADO EN NÓMINA');
                statusLabel[0].style.color = 'red'; // Establecer el color a rojo
                $('#bajaIncapacidad').hide();
                $('label[for="chkGeneraIncidencia"]').show();
                chkGeneraIncidencia.show();
                $('#btnGeneraIncidenciaIncapacidad').show();
                $('#btnBajaIncapacidad').hide();
            }
            //Campos para operaciones
            $('#id_incapacidad').val(data.data.id_incapacidad);
            $('#lblFolio').text(data.data.folio);
            $('#lblExpediente').text(data.data.expediente);
            $('#fecha_liberacion').val(data.data.fecha_movimiento);
            //Campos de la incapacidad
            $('#lblNombreEmpleado').text($('#campo_nombre').val());
            $('#lblNSS').text($('#campo_nss').val());
            // Establecer la fecha inicial
            const fechaInicialISO8601 = data.data.fecha_inicial;
            const fechaInicialParte = fechaInicialISO8601.split('T')[0];
            $('#fechaInicialIncapacidad').val(fechaInicialParte);
            // Asignar la fecha sin el offset al input
            $('#fechaInicialIncapacidadHidden').val(data.data.fecha_inicial);

            // Establecer la fecha final
            const fechaFinalISO8601 = data.data.fecha_final;
            const fechaFinalParte = fechaFinalISO8601.split('T')[0];
            $('#fechaFinalIncapacidad').val(fechaFinalParte);
            // Asignar la fecha sin el offset al input
            $('#fechaFinalIncapacidadHidden').val(data.data.fecha_final);
            $('#lblDiasIncapacidad').text(data.data.dias_incapacidad);
            $('#cmbMotivoIncapacidad').val(data.data.cat_Motivo_Incapacidad.id_motivo_incapacidad);
            $('#cmbTipoIncapacidad').val(data.data.cat_Tipo_Incapacidad.id_tipo_incapacidad);

            $('#lblDiasCotizables').text(data.data.dias_cotizables);
            $('#lblSalarioDiarioIntegrado').text('$ ' + data.data.salario_diario_integrado);
            $('#lblSubtotal').text('$ ' + data.data.subtotal);
            $('#porcentajeCobro').text('% ' + data.data.porcentaje_cobro_imss);
            $('#lblImporteACobrar').text('$ ' + data.data.importe_cobrar_imss);
            $('#cmbTipoRiesgo').val(data.data.cat_Tipo_Riesgo_Incapacidad ? data.data.cat_Tipo_Riesgo_Incapacidad.id_tipo_riesgo : 0);
            $('#cmbSecuelas').val(data.data.cat_Tipo_Secuelas_Incapacidad ? data.data.cat_Tipo_Secuelas_Incapacidad.id_tipo_secuela : 0);
            $('#cmbControlIncapacidad').val(data.data.cat_Tipo_Control_Incapacidad ? data.data.cat_Tipo_Control_Incapacidad.id_tipo_control : 0);
            mostrarUsuarioInformacionValidacionesIncapacidad();
        },
        error: function (e) {
            alert(e);
        }
    });

    //Habilita el boton para autorizar incapacidad cuando se da click a generar incidencia
    $('#chkGeneraIncidencia').on('click', function () {
        // Habilita o deshabilita el botón según el estado del checkbox
        if ($(this).is(':checked')) {
            $('#btnGeneraIncidenciaIncapacidad').prop('disabled', false);
        } else {
            $('#btnGeneraIncidenciaIncapacidad').prop('disabled', true);
        }
    });
}
//Solo para mostrar al usuario los mensajes con las validaciones correspondientes
function mostrarUsuarioInformacionValidacionesIncapacidad() {
    //Motivo de la incapacidad proveniente de la tabla catalogo_motivo_incapacidad
    let cmbMotivoIncapacidad = $('#cmbMotivoIncapacidad').val();
    let expedienteTrabajadorActual = $('#campo_expediente').val();
    //Proveniente de la tabla catalogo_motivo_incapacidad, solo en la enfermedad general se consideran los tipos
    let tipoIncapacidad = (cmbMotivoIncapacidad !== '1') ? '' : $('#cmbTipoIncapacidad').val();
    let fechaInicioIncapacidad = formatDate($('#fechaInicialIncapacidadHidden').val());
    let fechaFinIncapacidad = formatDate($('#fechaFinalIncapacidadHidden').val());
    let fechaLiberacionIncapacidad = formatDate($('#fecha_liberacion').val());
    $.ajax({
        type: "GET",
        url: "incidencias/validarIncapacidad?expediente=" + expedienteTrabajadorActual +
                "&fecha_fin_incapacidad=" + fechaFinIncapacidad +
                "&fecha_inicio_incapacidad=" + fechaInicioIncapacidad +
                "&fecha_liberacion_incapacidad=" + fechaLiberacionIncapacidad +
                "&motivo_incapacidad_id=" + cmbMotivoIncapacidad +
                "&tipo_incapacidad_id=" + tipoIncapacidad,
        dataType: 'json',
        success: function (dataValidacion) {
            let fechaLibValidacion = new Date(dataValidacion.fechaLiberacionIncapacidad);
            const dia = fechaLibValidacion.getDate().toString().padStart(2, '0');
            const mes = (fechaLibValidacion.getMonth() + 1).toString().padStart(2, '0');
            const año = fechaLibValidacion.getFullYear().toString().slice(2);
            const fechaFormateada = `${dia}/${mes}/${año}`;
            //Indica si la incapacidad es extemporánea (validado en el backend)
            dataValidacion.extemporanea !== false && toastr.warning('Incapacidad extemporánea, la fecha de liberación fue el ' + fechaFormateada, 'Advertencia', {timeOut: 10000, closeButton: true});
            //Verifica si existe una incapacidad continua a partir de una inicial
            dataValidacion.incapacidadContinua !== null && toastr.info('Existe una incapacidad continua relacionada. Folio: ' + dataValidacion.incapacidadContinua.folio, "Información", {timeOut: 10000, closeButton: true});
            //Indica si existe una incapacidad inicial a partir de una continua
            dataValidacion.incapacidadInicial !== null && toastr.info('Existe una incapacidad inicial relacionada, para autorizarlas conjuntamente autoriza la incapacidad inicial con folio: ' + dataValidacion.incapacidadInicial.folio, "Información", {timeOut: 10000, closeButton: true});
        },
        error: function (e) {
            toastr["error"]("Error al recuperar validaciones: " + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Aquí inicia el proceso para generar la incidencia al autorizarla desde el modal
function validarIncapacidad() {
    //Valores para validaciones y búsqueda de información
    let idNomina = $('#id_nomina').val();
    //Motivo de la incapacidad proveniente de la tabla catalogo_motivo_incapacidad
    let cmbMotivoIncapacidad = $('#cmbMotivoIncapacidad').val();
    let expedienteTrabajadorActual = $('#campo_expediente').val();
    //La nómina de varios y transportacion no tienen en cuenta las prestaciones ya que todos los trabajadores cuentan con ellas
    let prestacionesSiNo = idNomina === '1' || idNomina === '2' ? '' : $('#prestaciones').val();
    //Proveniente de la tabla catalogo_motivo_incapacidad, solo en la enfermedad general se consideran los tipos
    let tipoIncapacidad = (cmbMotivoIncapacidad !== '1') ? '' : $('#cmbTipoIncapacidad').val();
    let fechaInicioIncapacidad = formatDate($('#fechaInicialIncapacidadHidden').val());
    let fechaFinIncapacidad = formatDate($('#fechaFinalIncapacidadHidden').val());
    let fechaLiberacionIncapacidad = formatDate($('#fecha_liberacion').val());

    $.ajax({
        type: "GET",
        url: "incidencias/validarIncapacidad?expediente=" + expedienteTrabajadorActual +
                "&fecha_fin_incapacidad=" + fechaFinIncapacidad +
                "&fecha_inicio_incapacidad=" + fechaInicioIncapacidad +
                "&fecha_liberacion_incapacidad=" + fechaLiberacionIncapacidad +
                "&motivo_incapacidad_id=" + cmbMotivoIncapacidad +
                "&tipo_incapacidad_id=" + tipoIncapacidad,
        dataType: 'json',
        success: function (dataValidacion) {
            let extemporaneaSiNo = dataValidacion.extemporanea ? 1 : 2;
            buscarRelacionIncapacidadesIncidenciasNominaDias(fechaInicioIncapacidad, fechaFinIncapacidad, idNomina,
                    cmbMotivoIncapacidad, prestacionesSiNo, tipoIncapacidad, extemporaneaSiNo, dataValidacion);
        },
        error: function (e) {
            toastr["error"]("Error al recuperar validaciones: " + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

function buscarRelacionIncapacidadesIncidenciasNominaDias(fechaInicioIncapacidad, fechaFinIncapacidad,
        idNomina, cmbMotivoIncapacidad, prestacionesSiNo, tipoIncapacidad, extemporaneaSiNo, dataValidacion) {
    /*Se pasan los parámetros correspondientes, los únicos parámetros opcionales son tipoIncapacidad y prestacionesSiNo
     que pueden ser recibidos con valores nulos
     PrestacionesSiNo solo aplica para la nómina de confianza, mientras que el tipo de incapacidad solo es relevante 
     cuando el motivo de la incapacidad es enfermedad general*/
    $.ajax({
        type: "GET",
        url: "incidencias/buscarRelacionIncapacidadesNomina?id_nomina=" + idNomina +
                "&motivo_incapacidad_id=" + cmbMotivoIncapacidad +
                "&prestaciones_si_no=" + prestacionesSiNo +
                "&tipo_incapacidad_id=" + tipoIncapacidad +
                "&extemporanea_si_no=" + extemporaneaSiNo,
        dataType: 'json',
        success: function (data) {
            /* Se mandan los parámetros obtenidos a la función que generará las incidencias correspondientes en kardex,
             utilizando una cadena vacía como valor por defecto en caso de que alguna variable sea nula */
            ordenarDatosParaGenerarIncidencia(fechaInicioIncapacidad, fechaFinIncapacidad,
                    data.cat_IncidenciasPrimerGrupo.id_incidencia ?? '', data.dias_primer_grupo,
                    data.cat_IncidenciasSegundoGrupo?.id_incidencia ?? '', data.dias_segundo_grupo,
                    data.cat_IncidenciasTercerGrupo?.id_incidencia ?? '', data.dias_tercer_grupo,
                    data.cat_IncidenciasCuartoGrupo?.id_incidencia ?? '', data.dias_cuarto_grupo, dataValidacion);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar relación de incapacidades: " + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

function ordenarDatosParaGenerarIncidencia(fechaInicio, fechaFin, id1, dias1, id2, dias2, id3, dias3, id4, dias4, dataValidacion) {
    const fechaInicial = new Date(fechaInicio);
    let fechaFinal = new Date(fechaFin);
    //Para pasarlos a la función de ordenamiento
    const parametrosRelacion = {
        id1: id1,
        dias1: dias1,
        id2: id2,
        dias2: dias2,
        id3: id3,
        dias3: dias3,
        id4: id4,
        dias4: dias4
    };
    //Si se encuentran incapacidades continuas o iniciales relacionadas se despliega el modal de confirnmación de fusión de incapacidades
    if (dataValidacion.incapacidadContinua !== null /*|| dataValidacion.incapacidadInicial !== null*/) {
        $('#modalAlertaFusionIncapacidades').modal('show');
        //Maneja la confirmación del usuario
        $('#modalAlertaFusionIncapacidades #formAvisoAlertaDeContinuidadIncapacidades button.btn-primary').off('click').on('click', function () {
            handleModalConfirmacion(dataValidacion, fechaInicial, parametrosRelacion);
        });
        //Maneja la cancelación para no fusionar incapacidades
        $('#modalAlertaFusionIncapacidades #cancelarFusion').off('click').on('click', function () {
            handleModalCancelacion(fechaInicial, fechaFinal, parametrosRelacion);
        });
    } else {
        ejecutarOrdenamientoIncidenciasDias(fechaInicial, fechaFinal, parametrosRelacion);
    }
}
//Ordena los grupos de fechas de incidencias y llama a la función que hace el guardado de las incidencias en su tabla
function ejecutarOrdenamientoIncidenciasDias(inicio, fin, parametrosRelacion, folioRelacionado) {
    const gruposIncidencias = [];
    // Inicializamos la fecha del grupo con la fecha de inicio
    let fechaGrupo = new Date(inicio);
    // Iteramos sobre los parámetros de relación para agrupar las incidencias por fecha
    for (let i = 1; i <= 4; i++) {
        const id = parametrosRelacion[`id${i}`];
        const dias = parametrosRelacion[`dias${i}`];
        // Verificamos si hay un ID definido
        if (id) {
            let fechaInicioGrupo = new Date(fechaGrupo);
            let fechaFinGrupo;
            //Si tien días de incidencias
            if (dias) {
                fechaGrupo.setDate(fechaGrupo.getDate() + dias);
                fechaFinGrupo = fechaGrupo > fin ? new Date(fin) : new Date(fechaGrupo);
                fechaGrupo.setDate(fechaGrupo.getDate() + 1);
                //Si no se trata de una continua, se extiende a la fecha final
            } else {
                fechaFinGrupo = new Date(fin);
            }
            gruposIncidencias.push({
                fechaInicio: fechaInicioGrupo,
                fechaFin: fechaFinGrupo,
                incidencia: {id, dias}
            });
            // Salimos del bucle si la fecha de fin del grupo supera la fecha final recibida
            if (fechaFinGrupo.getTime() >= fin.getTime()) {
                break;
            }
        }
    }
    // Usando forEach para iterar sobre cada elemento del grupo incidencias que contendrá las fechas inicio y fin con el id de la incidencia a generar
    gruposIncidencias.forEach(function (grupo) {
        guardarIncidencias(grupo.fechaInicio, grupo.fechaFin, grupo.incidencia.id, folioRelacionado);
    });
}
//Maneja la confirmación del usuario estableciendo la fecha final de la incapacidad a la fecha final de la incapacidad relacionada
function handleModalConfirmacion(dataValidacion, fechaInicial, parametrosRelacion) {
    const fechaFinIncapacidadRelacionada = dataValidacion.incapacidadContinua !== null ? new Date(dataValidacion.incapacidadContinua.fecha_final) : new Date(dataValidacion.incapacidadInicial.fecha_final);
    fechaFinal = fechaFinIncapacidadRelacionada;
    $('#modalAlertaFusionIncapacidades').modal('hide');
    //Se autoriza desde aquí la incapacidad relacionada
    ajaxEstatusIncapacidad(dataValidacion.incapacidadContinua.id_incapacidad);
    ejecutarOrdenamientoIncidenciasDias(fechaInicial, fechaFinal, parametrosRelacion, dataValidacion.incapacidadContinua.folio);
}
//Maneja la cancelación de la fusión
function handleModalCancelacion(fechaInicial, fechaFinal, parametrosRelacion) {
    $('#modalAlertaFusionIncapacidades').modal('hide');
    ejecutarOrdenamientoIncidenciasDias(fechaInicial, fechaFinal, parametrosRelacion);
}

//Guarda las incidencias en el kárdex (tabla de incidencias)
function guardarIncidencias(fechaInicio, fechaFin, idIncidencia, folioRelacionado) {
    let folioIncapacidad = $('#lblFolio').text();
    if (folioRelacionado) {
        folioIncapacidad += ', ' + folioRelacionado;
    }
    let estado_incidencia_id = 1;
    let bis = 'N/A';
    let diffInMilliseconds = fechaFin - fechaInicio;
    let num_dias = Math.floor(diffInMilliseconds / (1000 * 60 * 60 * 24)) + 1;
    datos = {"num_dias": num_dias,
        "cat_estado_incidencia": {"id_estado_incidencia": estado_incidencia_id},
        "cat_incidencias": {"id_incidencia": idIncidencia},
        "fecha_inicio": fechaInicio,
        "fecha_fin": fechaFin,
        "folio": folioIncapacidad,
        "bis": bis,
        "observaciones": generarObservacion(idIncidencia, fechaInicio, fechaFin),
        "trabajador_id": trabajador_id};
    //Se actualiza la Incapacidad correspondiente al generar la incidencia
    $.ajax({
        type: "POST",
        url: "incidencias/guardarIncidencia",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            toastr['success']("Incidencias generadas correctamente", "Éxito");
            //Se agina automáticamente la fecha_autorización cuando se llame a este servicio y se autoriza en la tabla de incapacidades
            ajaxEstatusIncapacidad();
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//Se genera la observacion con la inicial de la incidencia, los periodos que abarca la incapacidad
function generarObservacion(idCatalogoincidencia, fechaInicio, fechaFin) {
    let idNomina = $('#id_nomina').val();
    let result;
    let fechaInicialFormateada = convertirFechaAnioMesDia(fechaInicio);
    let fechaFinalFormateada = convertirFechaAnioMesDia(fechaFin);
    // Primera solicitud AJAX para obtener la descripción inicial
    $.ajax({
        type: "GET",
        url: "catalogos/encuentraUnElementoCatIncidencias/" + idCatalogoincidencia,
        dataType: 'json',
        async: false,
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                // Segunda solicitud AJAX para obtener los periodos
                $.ajax({
                    type: "GET",
                    url: "trabajador/buscaLapsoPeriodos/" + idNomina + "/" + fechaInicialFormateada + "/" + fechaFinalFormateada,
                    dataType: 'json',
                    async: false,
                    success: function (periodosData) {
                        if ($.isEmptyObject(periodosData)) {
                            toastr["warning"]("No se encontraron periodos...", "Aviso", {progressBar: true, closeButton: true});
                        } else {
                            const descripcionInicial = data.data.inicial_descripcion;
                            const periodos = periodosData.data;
                            result = descripcionInicial + " " + periodos.join(', ');
                        }
                    },
                    error: function (e) {
                        toastr["warning"]("Error al obtener periodos: " + e, "Error", {progressBar: true, closeButton: true});
                    }
                });
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar comentario : " + e, "Error", {progressBar: true, closeButton: true});
        }
    });
    return result;
}

//Se marca como autorizada/ no autorizada en la tabla de incapacidades y se asigna la fecha de autorizacion
function ajaxEstatusIncapacidad(id_incapacidad_relacionada) {
    let url;
    let id_incapacidad = $('#id_incapacidad').val();
    var autorizar = 1;
    var datos = {"autorizar": autorizar};
    //Se verifica si se recibe como parámetro el id de la incidencia relacionada
    if (id_incapacidad_relacionada) {
        url = "incidencias/autorizarIncapacidad/" + id_incapacidad_relacionada;
    } else {
        url = "incidencias/autorizarIncapacidad/" + id_incapacidad;
    }
    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#incapacidadIMSS").modal("hide");
            if (data.error === 0) {
                toastr.options.preventDuplicates = true;
                toastr['success']("Se actualizó el estatus de la incapacidad exitosamente", "Aviso");
                $('#tabla_incapacidades').DataTable().ajax.reload();
                //Reset de identificador de incapacidad
                id_incapacidad = null;
            }
            if (data.error !== 0) {
                toastr['error'](data.data, "Aviso");
            }
        },
        error: function (e) {
            toastr['error']("No se actualizó el estatus de la incapacidad", "Aviso");
        }
    });
}
//Retorna la fecha a formato año mes dia
function convertirFechaAnioMesDia(fecha) {
    const date = new Date(fecha);
    const año = date.getFullYear();
    const mes = String(date.getMonth() + 1).padStart(2, "0");
    const día = String(date.getDate()).padStart(2, "0");
    const fechaFormateada = `${año}-${mes}-${día}`;
    return fechaFormateada;
}

function rechazaIncapacidad() {
    let id_incapacidad = $('#id_incapacidad').val();
    let foliosPosibles = [];
    let folioActual = $('#lblFolio').text();
    //Motivo de la incapacidad proveniente de la tabla catalogo_motivo_incapacidad
    let cmbMotivoIncapacidad = $('#cmbMotivoIncapacidad').val();
    let expedienteTrabajadorActual = $('#campo_expediente').val();
    //Proveniente de la tabla catalogo_motivo_incapacidad, solo en la enfermedad general se consideran los tipos
    let tipoIncapacidad = (cmbMotivoIncapacidad !== '1') ? '' : $('#cmbTipoIncapacidad').val();
    let fechaInicioIncapacidad = formatDate($('#fechaInicialIncapacidadHidden').val());
    let fechaFinIncapacidad = formatDate($('#fechaFinalIncapacidadHidden').val());
    let fechaLiberacionIncapacidad = formatDate($('#fecha_liberacion').val());
    let autorizar = 0;
    let datos = {"autorizar": autorizar};
    $.ajax({
        type: "GET",
        url: "incidencias/validarIncapacidad?expediente=" + expedienteTrabajadorActual +
                "&fecha_fin_incapacidad=" + fechaFinIncapacidad +
                "&fecha_inicio_incapacidad=" + fechaInicioIncapacidad +
                "&fecha_liberacion_incapacidad=" + fechaLiberacionIncapacidad +
                "&motivo_incapacidad_id=" + cmbMotivoIncapacidad +
                "&tipo_incapacidad_id=" + tipoIncapacidad,
        dataType: 'json',
        success: function (dataValidacion) {
            foliosPosibles.push(folioActual);
            if (dataValidacion.incapacidadContinua !== null) {
                folioActual += ', ' + dataValidacion.incapacidadContinua.folio;
                foliosPosibles.push(folioActual);
                rechazarIncapacidad(dataValidacion.incapacidadContinua.id_incapacidad, datos);
            }
            if (dataValidacion.incapacidadInicial !== null) {
                dataValidacion.incapacidadInicial.folio += ', ' + folioActual;
                foliosPosibles.push(dataValidacion.incapacidadInicial.folio);
                rechazarIncapacidad(dataValidacion.incapacidadInicial.id_incapacidad, datos);
            }
            foliosPosibles.forEach(function (folio) {
                buscarIdIncidenciaPorFolio(folio);
            });
            rechazarIncapacidad(id_incapacidad, datos);
        },
        error: function (e) {
            toastr["error"]("Error al recuperar validaciones: " + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

//Busca las incidencias por folio en la descripción de la incidencia y las rechaza todas las que encuentre, se mantiene el registro de la incidencia con estatus rechazado
function buscarIdIncidenciaPorFolio(folio) {
    $.ajax({
        type: "GET",
        url: "incidencias/buscarFolioIncidencia/" + folio,
        dataType: 'json',
        success: function (data) {
            if (!$.isEmptyObject(data)) {
                data.forEach(function (incidencia) {
                    rechazarIncidenciaKardex(incidencia.id_incidencia);
                });
            }
        },
        error: function (e) {
            toastr['error']("Ocurrió un error al buscar folio en kárdex " + e, "Aviso");
        }
    });
}
//Cambia el estatus de la incidencia en kárdex
function rechazarIncidenciaKardex(id_incidencia) {
    let estatus = 0;
    $.ajax({
        type: "POST",
        url: "incidencias/estatusIncidencia/" + id_incidencia + "/" + estatus,
        contentType: "application/json",
        success: function (data) {
            toastr['success']("Las incidencias relacionadas fueron eliminadas del kárdex", "Aviso");
        },
        error: function (e) {
            toastr['error']("No se actualizó la incidencia del kárdex " + e, "Aviso");
        }
    });
}

//Se marca como autorizada/ no autorizada en la tabla
function rechazarIncapacidad(id_incapacidad, datos) {
    $.ajax({
        type: "POST",
        url: "incidencias/autorizarIncapacidad/" + id_incapacidad,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#incapacidadIMSS").modal("hide");
            if (data.error === 0) {
                toastr.options.preventDuplicates = true;
                toastr['success']("Se modificó el estatus de la incapacidad exitosamente", "Aviso");
                $('#tabla_incapacidades').DataTable().ajax.reload();
                //Reset de identificador de incapacidad
                id_incapacidad = null;
            }
            if (data.error !== 0) {
                toastr['error'](data.data, "Aviso");
            }
        },
        error: function (e) {
            toastr['error']("No se actualizó el estatus de la incapacidad", "Aviso");
        }
    });
}

$('#incapacidadIMSS').on('hidden.bs.modal', function () {
    $('#chkGeneraIncidencia').prop('checked', false);
});


// Función para formatear fecha a 'yyyy-MM-dd'
function formatDate(date) {
    let d = new Date(date);
    let year = d.getFullYear();
    let month = (d.getMonth() + 1).toString().padStart(2, '0');
    let day = d.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
}
