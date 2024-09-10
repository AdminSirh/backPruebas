document.addEventListener('DOMContentLoaded', () => {
    cargaCmbPeriodos();
    const camposPuntoDecimal = [
        "#incremento_pension",
        "#incremento_transporte",
        "#incremento_vales",
        "#incremento_pension_atrasada"
    ];
    puntoDecimalPorcentajes(camposPuntoDecimal);
});

function cargaCmbPeriodos() {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTodosLosPeriodosPorNomina/4",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
            } else {

                data.forEach(periodo => {
                    const n_periodo = periodo.periodo.toString().substr(4, 5);
                    const fecha_inicial = formatFecha(periodo.fecha_inicial);
                    const fecha_final = formatFecha(periodo.fecha_final);
                    const option = `<option value="${periodo.periodo}">P. ${n_periodo} : ${fecha_inicial}--${fecha_final}</option>`;
                    $('#cmbPeriodoInicio').append(option);
                    $('#cmbPeriodoFinal').append(option);
                });
            }
        },
        error: function (e) {
            toastr["error"](e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

function formatFecha(fecha) {
    const date = new Date(fecha);
    return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;
}

// Iterar sobre el array de selectores y aplicar el formato decimal a cada uno
function puntoDecimalPorcentajes(selectores) {
    selectores.forEach(function (selector) {
        $(selector).on("input", function () {
            var v = $(this).val();
            if (v.toString().length > 2) {
                if (v.indexOf(".") > 0) {
                    v = v.slice(0, v.indexOf(".")) + v.slice(v.indexOf(".") + 1);
                }
                $(this).val(v.slice(0, -2) + "." + v.slice(-2));
            }
        });
    });
}

$("#form_calcula_retro_jub").submit(function (e) {
    e.preventDefault();
    const expedientesInput = $('#expedientes').val();
    const periodoInicial = $('#cmbPeriodoInicio').val();
    const periodoFinal = $('#cmbPeriodoFinal').val();
    const incrPension = $('#incremento_pension').val();
    const incrAyudaTransp = $('#incremento_transporte').val();
    const incrVales = $('#incremento_vales').val();
    const incrPensionAtrasada = $('#incremento_pension_atrasada').val();
    const expedientesArray = expedientesInput.split(',').map(expediente => expediente.trim());
    const expedienteSet = new Set(expedientesArray);
    // Validar si hay campos vacíos
    const errores = [];
    expedientesArray.forEach(expediente => {
        if (!expediente) {
            errores.push("Uno o más expedientes están vacíos.");
        } else if (expediente.length !== 5) {
            errores.push("El expediente '" + expediente + "' debe tener 5 caracteres.");
        }
    });

    // Validar duplicados
    if (expedienteSet.size !== expedientesArray.length) {
        errores.push("Hay expedientes duplicados.");
    }
    if (errores.length > 0) {
        $('#resultado').html(errores.join('<br>')).show();
        $('#expedientes').addClass('is-invalid');
        $('#correcto').hide();
        return;
    } else {
        $('#resultado').hide();
        $('#expedientes').removeClass('is-invalid').addClass('is-valid');
        $('#correcto').show();
    }

    // Verificar si todos los campos de porcentajes están completos
    if (!incrPension || !incrAyudaTransp || !incrVales || !incrPensionAtrasada) {
        return;
    }

    // Construir la URL dinámicamente
    const urlBase = 'calculoRetroactivo/calcularJubilados';
    const parametrosExpedientes = expedientesArray.map(expediente => 'expedientesDescartados=' + encodeURIComponent(expediente)).join('&');
    const urlCompleta = `${urlBase}?${parametrosExpedientes}&periodoFin=${periodoFinal}&periodoInicio=${periodoInicial}&porcentajeIncrAyudaTransp=${encodeURIComponent(incrAyudaTransp)}&porcentajeIncrPensAtrasada=${encodeURIComponent(incrPensionAtrasada)}&porcentajeIncrPension=${encodeURIComponent(incrPension)}&porcentajeIncrVales=${encodeURIComponent(incrVales)}`;

    $.ajax({
        url: urlCompleta,
        type: 'POST',
        dataType: 'text',
        success: function (data) {
            document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
            toastr['success'](data, "Aviso");
        },
        error: function (xhr, status, error) {
            const errorMessage = xhr.responseText || "Ocurrió un error al procesar el cálculo de retroactivo para jubilados";
            toastr['error'](errorMessage, "Error");
        }
    });

});

function visualizarReporteRetroJub() {
    // Obtén el año actual
    var currentYear = new Date().getFullYear();
    $.ajax({
        type: "GET",
        url: `report/reporteRetroJubilados?anioParam=${currentYear}&tipo=PDF`,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, status, xhr) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#reporteModal").modal("toggle");
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
            toastr["warning"]("Error al recuperar documento cargado" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

function generarReporteRetroJub() {
    // Obtén el año actual
    var currentYear = new Date().getFullYear();
    $.ajax({
        type: "GET",
        contentType: 'application/pdf',
        url: `report/reporteRetroJubilados?anioParam=${currentYear}&tipo=PDF`,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (blob) {
            if ($.isEmptyObject(blob)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            toastr["success"]("Reporte Generado con éxito", "Aviso", {progressBar: true, closeButton: true});
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            //Nombre de la descarga
            link.download = 'retroactivoJubiladosAnio' + currentYear;
            link.click();
        }, error: function (e) {
            toastr["warning"]("Error al recuperar documento cargado" + e, " Error", {progressBar: true, closeButton: true});
        }
    });
}

function importarMovimientos() {
    $.ajax({
        url: 'calculoRetroactivo/insertarTmpMovimientosJubilados',
        type: 'POST',
        dataType: 'text',
        success: function (data) {
            document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
            toastr['success'](data, "Aviso");
            $("#confirmModal").modal('hide');
        },
        error: function (xhr, status, error) {
            $("#confirmModal").modal('hide');
            const errorMessage = xhr.responseText || "Ocurrió un error al procesar el cálculo de retroactivo para jubilados";
            toastr['error'](errorMessage, "Error");
        }
    });
}