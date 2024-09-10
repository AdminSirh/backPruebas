document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    //rellenaCampos(trabajador_id);
    cmbTipoNomina();
    cmbAreas();
    cmbMotivoIncapacidad();
    vincularEventosInputs();
});

function vincularEventosInputs() {
    // Detectar el cambio en el combo de nómina
    $("#cmbNomina").change(function () {
        var selectedValue = $(this).val();
        obtenPeriodos(selectedValue);
    });
    // Escucha el evento 'input' en el campo de expediente para seleccionar automáticamente la nómina del trabajador
    $('#expediente').on('input', function () {
        let inputExpediente = $(this).val();
        // Validar que el expediente no sea mayor a 5 caracteres
        if (inputExpediente.length > 5) {
            toastr.warning('El expediente no puede tener más de 5 caracteres', 'Advertencia');
            $(this).val(inputExpediente.substring(0, 5));
            return;
        }
        if (inputExpediente.length > 4) {
            let url = 'trabajador/buscarNominaPorExpediente/' + inputExpediente;
            $.ajax({
                url: url,
                method: 'GET',
                success: function (data) {
                    if (data !== undefined) {
                        $('#cmbNomina').prop('disabled', true);
                        $('#cmbNomina').val(data);
                    } else {
                        toastr.error('No se encontró la nómina para este expediente', 'Error');
                        $('#cmbNomina').prop('disabled', false);
                        $('#cmbNomina').val('');
                    }
                },
                error: function (error) {
                    if (error.status === 404) {
                        toastr.error('No se encontró la nómina para este expediente', 'Error');
                        $('#cmbNomina').val('');
                    } else {
                        //console.error(error);
                    }
                }
            });
        } else {
            $('#cmbNomina').prop('disabled', false);
            $('#cmbNomina').val('');
        }
    });
    //Limpia la validación al cerrar el modal
    $('#reporteIncapacidades').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
        $('#formGenerarReporteIncapacidades')[0].reset();
    });

    // Agregar eventos change a ambos campos de fecha
    $('#fecha_inicio, #fecha_final').on('change', function () {
        let inputFechaInicialLiberacion = $('#fecha_inicio').val();
        let inputFechaFinallLiberacion = $('#fecha_final').val();
        // Verificar que ambas fechas estén presentes
        if (inputFechaInicialLiberacion && inputFechaFinallLiberacion) {
            // Convertir las fechas a objetos Date para compararlas
            let fechaInicial = new Date(inputFechaInicialLiberacion);
            let fechaFinal = new Date(inputFechaFinallLiberacion);
            // Validar que la fecha inicial no sea mayor a la fecha final
            if (fechaInicial > fechaFinal) {
                toastr.warning('La fecha inicial no puede ser mayor a la fecha final', 'Advertencia');
                $('#fecha_inicio').val('');
            }
        }
    });
}

function redireccionTabIncapacidades() {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    window.location.href = 'trabajadorIncapacidades?id_trabajador=' + trabajador_id;
}

//Se listan los periodos disponibles para el tipo de nómina correspondiente
function obtenPeriodos(id_nomina) {
    let cmbNomina = $('#cmbNomina').val() || null;
    if (cmbNomina === null) {
        $('#cmbPeriodo').empty().append('<option value="">*Selecciona un periodo</option>');
        return;
    }
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTodosLosPeriodosPorNomina/" + id_nomina,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                var vselected = "";
                //Variable que almacena la fecha mínima encontrada en el select con el listado de periodos de pago
                var fechaMinima = null;
                //Variable que almacena la fehca mínima de corte encontrada en el periodo listado
                var fechaMinimaCorte = null;
                //Ordenamiento de manera ascendente 
                data.sort((a, b) => new Date(a.fecha_inicial) - new Date(b.fecha_inicial));
                $('#cmbPeriodo').empty().append('<option value="">*Selecciona un periodo</option>');
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        var n_periodo = data[x].periodo;
                        var fecha_inicial_formato = new Date(data[x].fecha_inicial);
                        const fecha_corte = new Date(data[x].fecha_corte);
                        var dias_inicial = fecha_inicial_formato.getDate();
                        var meses_inicial = fecha_inicial_formato.getMonth() + 1;
                        var anios_inicial = fecha_inicial_formato.getFullYear();
                        var fecha_final_formato = new Date(data[x].fecha_final);
                        var dias_final = fecha_final_formato.getDate();
                        var meses_final = fecha_final_formato.getMonth() + 1;
                        var anios_final = fecha_final_formato.getFullYear();
                        var fechaInicial = new Date(data[x].fecha_inicial);
                        //Actualización de fecha mínima acorde a las fechas iniciales que se van recorriendo
                        if (fechaMinima === null || fechaInicial < fechaMinima) {
                            fechaMinima = fechaInicial;
                        }
                        //Obtención de la fecha mínima de corte encontrada
                        if (fechaMinimaCorte === null || fecha_corte < fechaMinimaCorte) {
                            fechaMinimaCorte = fecha_corte;
                        }
                        $('#cmbPeriodo').append('<option value="' + data[x].periodo + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
                                '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            alert(e);
        }
    });
}
//Se listan los tipos de nómina provenientes de la base de datos dentro del combo
function cmbTipoNomina(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                //Seleccion de las nominas con ID 1 y 3
                $('#cmbNomina').empty().append('<option value="">*Selecciona una nómina</option>');
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        var vselected = (data[x].id_tipo_nomina === id) ? "selected" : "";
                        $('#cmbNomina').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//Se listan las áreas provenientes de la base de datos dentro del combo
function cmbAreas(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarAreas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbArea').empty().append('<option value="">*Selecciona un Área</option>');
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

//Se listan los motivis de incapacidad provenientes de la base de datos dentro del combo
function cmbMotivoIncapacidad(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarMotivosIncapacidad",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                //Seleccion de las nominas con ID 1 y 3
                $('#cmbMotivoIncapacidad').empty().append('<option value="">*Selecciona un Motivo</option>');
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        var vselected = (data[x].id_motivo_incapacidad === id) ? "selected" : "";
                        $('#cmbMotivoIncapacidad').append('<option value="' + data[x].id_motivo_incapacidad + '"' + vselected + '>' + data[x].descripcion_incapacidad + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

$("#formGenerarReporteIncapacidades").submit(function (e) {
    e.preventDefault();
    let cmbTipoReporte = $('#cmbTipoReporte').val() || null;
    let inputExpediente = $('#expediente').val() || null;
    let inputFolio = $('#folio').val() || null;
    let cmbNomina = $('#cmbNomina').val() || null;
    let cmbArea = $('#cmbArea').val() || null;
    let cmbPeriodo = $('#cmbPeriodo').val() || null;
    let cmbMotivoIncapacidad = $('#cmbMotivoIncapacidad').val() || null;
    let inputFechaInicialLiberacion = $('#fecha_inicio').val() || null;
    let inputFechaFinallLiberacion = $('#fecha_final').val() || null;
    
    if ((new Date(inputFechaFinallLiberacion)) < (new Date(inputFechaInicialLiberacion))) {
        toastr["warning"]('La fecha final no puede ser mayor a la fecha inicial.');
        $('#fecha_final').val(''); 
        return;
    }

    if (inputExpediente !== null && inputExpediente.trim() !== "") {
        // Si se ingresó el expediente, entonces la nómina y las fechas son obligatorias
        if (!cmbNomina || !inputFechaInicialLiberacion || !inputFechaFinallLiberacion) {
            toastr["warning"]("Rellena todos los campos obligatorios: nómina, fecha inicial y fecha final", "Aviso", {progressBar: true, closeButton: true});
            return;
        }
    } else {
        // Si no se ingresó el expediente, no es obligatorio seleccionar la nómina ni las fechas
        if ((!inputFechaInicialLiberacion || !inputFechaFinallLiberacion) || !cmbNomina) {
            toastr["warning"]("Rellena todos los campos obligatorios: nómina, fecha inicial y fecha final", "Aviso", {progressBar: true, closeButton: true});
            return;
        }
    }
    //Comprobación de que el reporte a generar está seleccionado
    if (cmbTipoReporte === null) {
        toastr["warning"]("Selecciona el reporte a generar", "Aviso", {progressBar: true, closeButton: true});
        return;
    }
    //Relación de incapacidades por fecha y nómina
    if (cmbTipoReporte === '1') {
        generaReporteRelacionIncapacidades(inputExpediente, inputFolio, cmbNomina, cmbArea, cmbPeriodo, cmbMotivoIncapacidad, inputFechaInicialLiberacion, inputFechaFinallLiberacion);
        ajaxVer("report/incapacidadesExpedidasIMSS?" + // Base URL
                "periodo=" + cmbPeriodo + "&" + // cmbPeriodo parameter
                "expediente=" + inputExpediente + "&" + // inputExpediente parameter
                "folio=" + inputFolio + "&" + // inputFolio parameter
                "id_nomina=" + cmbNomina + "&" + // cmbNomina parameter
                "id_area=" + cmbArea + "&" + // cmbArea parameter
                "motivo_incapacidad=" + cmbMotivoIncapacidad + "&" + // cmbMotivoIncapacidad parameter
                "fecha_inicial_liberacion=" + inputFechaInicialLiberacion + "&" + // inputFechaInicialLiberacion parameter
                "fecha_final_liberacion=" + inputFechaFinallLiberacion + "&" + // inputFechaFinallLiberacion parameter
                "tipo=PDF");
        //Incapacidades del periodo por motivo de incapacidad
    } else if (cmbTipoReporte === '2') {
        generaReporteIncapacidadesPeriodoMotivo(inputExpediente, inputFolio, cmbNomina, cmbArea, cmbPeriodo, cmbMotivoIncapacidad, inputFechaInicialLiberacion, inputFechaFinallLiberacion);
        ajaxVer("report/incapacidadePeriodoPorMotivoIMSS?" + // Base URL
                "periodo=" + cmbPeriodo + "&" + // cmbPeriodo parameter
                "expediente=" + inputExpediente + "&" + // inputExpediente parameter
                "folio=" + inputFolio + "&" + // inputFolio parameter
                "id_nomina=" + cmbNomina + "&" + // cmbNomina parameter
                "id_area=" + cmbArea + "&" + // cmbArea parameter
                "motivo_incapacidad=" + cmbMotivoIncapacidad + "&" + // cmbMotivoIncapacidad parameter
                "fecha_inicial=" + inputFechaInicialLiberacion + "&" + // inputFechaInicialLiberacion parameter
                "fecha_final=" + inputFechaFinallLiberacion + "&" + // inputFechaFinallLiberacion parameter
                "tipo=PDF");
    }

});

function ajaxVer(url) {
    $.ajax({
        type: "GET",
        url: url,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, status, xhr) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#incapacidadesModal").modal("toggle");
                var contentType = xhr.getResponseHeader("Content-Type");
                var filename = xhr.getResponseHeader("Content-disposition");
                filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                var url = window.URL.createObjectURL(data);
                var frame = $('#formatos_frame');
                var url = url;
                frame.attr('src', url).show();
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar informacion de documentos cargados: General 1", {progressBar: true, closeButton: true});
        }
    });
}

function generaReporteRelacionIncapacidades(inputExpediente, inputFolio, cmbNomina, cmbArea, cmbPeriodo, cmbMotivoIncapacidad, inputFechaInicialLiberacion, inputFechaFinallLiberacion) {
    const url = "report/incapacidadesExpedidasIMSS?" + // Base URL
            "periodo=" + cmbPeriodo + "&" + // cmbPeriodo parameter
            "expediente=" + inputExpediente + "&" + // inputExpediente parameter
            "folio=" + inputFolio + "&" + // inputFolio parameter
            "id_nomina=" + cmbNomina + "&" + // cmbNomina parameter
            "id_area=" + cmbArea + "&" + // cmbArea parameter
            "motivo_incapacidad=" + cmbMotivoIncapacidad + "&" + // cmbMotivoIncapacidad parameter
            "fecha_inicial_liberacion=" + inputFechaInicialLiberacion + "&" + // inputFechaInicialLiberacion parameter
            "fecha_final_liberacion=" + inputFechaFinallLiberacion + "&" + // inputFechaFinallLiberacion parameter
            "tipo=PDF"; // Fixed parameter specifying the type
    let nombreArchivo = 'RelacionIncapacidadesFechaNomina';
    llamadaAjax(url, nombreArchivo);
}
;

function generaReporteIncapacidadesPeriodoMotivo(inputExpediente, inputFolio, cmbNomina, cmbArea, cmbPeriodo, cmbMotivoIncapacidad, inputFechaInicialLiberacion, inputFechaFinallLiberacion) {
    const url = "report/incapacidadePeriodoPorMotivoIMSS?" + // Base URL
            "periodo=" + cmbPeriodo + "&" + // cmbPeriodo parameter
            "expediente=" + inputExpediente + "&" + // inputExpediente parameter
            "folio=" + inputFolio + "&" + // inputFolio parameter
            "id_nomina=" + cmbNomina + "&" + // cmbNomina parameter
            "id_area=" + cmbArea + "&" + // cmbArea parameter
            "motivo_incapacidad=" + cmbMotivoIncapacidad + "&" + // cmbMotivoIncapacidad parameter
            "fecha_inicial=" + inputFechaInicialLiberacion + "&" + // inputFechaInicialLiberacion parameter
            "fecha_final=" + inputFechaFinallLiberacion + "&" + // inputFechaFinallLiberacion parameter
            "tipo=PDF"; // Fixed parameter specifying the type
    let nombreArchivo = 'IncapacidadesDelPeriodoPorMotivo';
    llamadaAjax(url, nombreArchivo);
}
;

function llamadaAjax(url, nombreArchivo) {
    $.ajax({
        type: 'GET',
        url: url,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, textStatus, jqXHR) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {
                    progressBar: true,
                    closeButton: true
                });
            } else {
                resetFormAndValidation();
                toastr.success("Documentos generados con éxito");
                var link = document.createElement('a');
                link.href = window.URL.createObjectURL(data);
                link.download = nombreArchivo;
                link.click();
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar información, documento vacío", {
                progressBar: true,
                closeButton: true
            });
        }
    });
}

// Resetting the form
function resetFormAndValidation() {
    $("#reporteIncapacidades").modal('hide');
    $('#formGenerarReporteIncapacidades')[0].reset();
    //$('.needs-validation').removeClass('was-validated');
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
}

function validacion(object) {
    if (object.value.length > 5) {
        object.value = object.value.slice(0, 5);
    }
}