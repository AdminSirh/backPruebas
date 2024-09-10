/* Parámetros mandados a las funciones para obtener el mes en curso
 Obtener el mes actual en número */
const mesActual = new Date().getMonth() + 1;
// Convertir el mes actual a una cadena de texto
const valorActualMes = mesActual.toString();

//SubReportes
const manejoDescargaSub = (mes, sub) => {
    const url = "reportCierrePlantilla/reporteCierrePlantillaSub" + sub + "?mes=" + mes + "&tipo=PDF";
    const nombreArchivoDescargado = "PlantillaPersonalSub" + sub + "MesNumero" + mes + ".pdf";
    generarReporte(url, nombreArchivoDescargado);
};

//Reportes Género
const generarURLyNombreArchivo = (tipoReporte, descripcion) => {
    const url = "reportCierrePlantilla/" + tipoReporte + "?mes=" + valorActualMes + "&tipo=PDF";
    const nombreArchivoDescargado = descripcion + valorActualMes + ".pdf";
    generarReporte(url, nombreArchivoDescargado);
};

const generarReporte = (url, nombreArchivodescargado) => {
    $.ajax({
        type: "GET",
        contentType: 'application/pdf',
        url: url,
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
            link.download = nombreArchivodescargado;
            link.click();
        }, error: function (e) {
            toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
        }
    });
};

//SubReporte II
$('#descargaSubII').click(function () {
    manejoDescargaSub(valorActualMes, "II");
});
//SubReporte III
$('#descargaSubIII').click(function () {
    manejoDescargaSub(valorActualMes, "III");
});

$('#descargaGeneroIngresos').click(function () {
    generarURLyNombreArchivo("obtenerResumenGeneroeIngresos", "ResumenGeneroeIngresos_Mes");
});

$('#descargaGeneroRangoEdades').click(function () {
    generarURLyNombreArchivo("reporteGeneroRangoEdades", "GeneroRangoEdadesMes");
});

$('#descargaGeneroEdades').click(function () {
    generarURLyNombreArchivo("reporteGeneroEdades", "GeneroEdadMes");
});

/*==========================================================
 FUNCIONES VISUALIZADORAS DE REPORTES 
 ==========================================================*/
const llamadaAjaxOrden = (url, modalOrden) => {
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
                modalOrden.modal('hide');
                $("#reportesModal").modal("toggle");
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
            toastr["warning"]("Error al recuperar informacion de documentos cargados: General 1", {progressBar: true, closeButton: true});
        }
    });
};

const llamadaAjax = (url) => {
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
                $("#reportesModal").modal("toggle");
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
            toastr["warning"]("Error al recuperar informacion de documentos cargados: General 1", {progressBar: true, closeButton: true});
        }
    });
};

const vizualizarReporteCierrePlantilla = () => {
    $('#ordenModal').modal('show');
    const cmbOrdenReport = $('#cmbOrdenReport');
    let valorSeleccionadoOrden = cmbOrdenReport.val();
    cmbOrdenReport.on('change', function () {
        // Obtener el valor seleccionado del select ORDEN
        valorSeleccionadoOrden = cmbOrdenReport.val();

        let orden;
        // Definición de orden en que se generará el reporte (Codigo SQL que pasa como parámetro para definir el orden en que se generará el reporte)
        if (valorSeleccionadoOrden === "1") {
            orden = "consulta_original.id_area ASC";
        } else if (valorSeleccionadoOrden === "2") {
            orden = "consulta_original.id_area DESC";
        } else {
            orden = "consulta_original.apellido_paterno ASC";
        }
        const url = "reportCierrePlantilla/reporteCierrePlantilla?mes=" + valorActualMes + "&orden=" + orden + "&tipo=PDF";
        const modalOrden = $('#ordenModal');
        llamadaAjaxOrden(url, modalOrden);
        // Desvincular los eventos
        cmbOrdenReport.off('change');
    });
};

const visualizarSubReporteI = () => {
    $('#ordenModalSubI').modal('show');
    const cmbOrdenReportSubI = $('#cmbOrdenReportSubI');
    let valorSeleccionadoOrden = cmbOrdenReportSubI.val();
    cmbOrdenReportSubI.on('change', function () {
        // Obtener el valor seleccionado del select ORDEN
        valorSeleccionadoOrden = cmbOrdenReportSubI.val();

        let orden;
        // Definición de orden en que se generará el reporte (Codigo SQL que pasa como parámetro para definir el orden en que se generará el reporte)
        if (valorSeleccionadoOrden === "1") {
            orden = "consulta_original.codigo_puesto";
        } else if (valorSeleccionadoOrden === "2") {
            orden = "consulta_original.puesto";
        } else {
            orden = "consulta_original.tipo_nomina";
        }
        const url = "reportCierrePlantilla/reporteCierrePlantillaSubI?mes=" + valorActualMes + "&orden=" + orden + "&tipo=PDF";
        const modalOrden = $('#ordenModalSubI');
        llamadaAjaxOrden(url, modalOrden);
        // Desvincular los eventos
        cmbOrdenReportSubI.off('change');
    });
};

const visualizarSubReporteII = () => {
    const url = "reportCierrePlantilla/reporteCierrePlantillaSubII?mes=" + valorActualMes + "&tipo=PDF";
    llamadaAjax(url);
};

const visualizarSubReporteIII = () => {
    const url = "reportCierrePlantilla/reporteCierrePlantillaSubIII?mes=" + valorActualMes + "&tipo=PDF";
    llamadaAjax(url);
};

const visualizarReporteGeneroIngresos = () => {
    const url = "reportCierrePlantilla/obtenerResumenGeneroeIngresos?mes=" + valorActualMes + "&tipo=PDF";
    llamadaAjax(url);
};

const visualizarReporteGeneroRangoEdades = () => {
    const url = "reportCierrePlantilla/reporteGeneroRangoEdades?mes=" + valorActualMes + "&tipo=PDF";
    llamadaAjax(url);
};

const visualizarReporteGeneroEdades = () => {
    const url = "reportCierrePlantilla/reporteGeneroEdades?mes=" + valorActualMes + "&tipo=PDF";
    llamadaAjax(url);
};

const visualizarReportePuestoGenero = () => {
    $('#ordenModalPuestoGenero').modal('show');
    const cmbOrdenPuestoGenero = $('#cmbOrdenPuestoGenero');
    let valorSeleccionadoOrden = cmbOrdenPuestoGenero.val();
    cmbOrdenPuestoGenero.on('change', function () {
        // Obtener el valor seleccionado del select ORDEN
        valorSeleccionadoOrden = cmbOrdenPuestoGenero.val();

        let orden;
        // Definición de orden en que se generará el reporte (Codigo SQL que pasa como parámetro para definir el orden en que se generará el reporte)
        if (valorSeleccionadoOrden === "1") {
            orden = "nivel";
        } else if (valorSeleccionadoOrden === "2") {
            orden = "catalogoPuesto.puesto";
        } else {
            orden = "desc_area";
        }

        const url = "reportCierrePlantilla/reportePuestoGenero?mes=" + valorActualMes + "&orden=" + orden + "&tipo=PDF";
        const modalOrden = $('#ordenModalPuestoGenero');
        llamadaAjaxOrden(url, modalOrden);
        // Desvincular los eventos
        cmbOrdenPuestoGenero.off('change');
    });
};

function visualizarReporteGeneroYGradoDeEstudios(){
            $.ajax({
            type: "GET",
            url: "reportCierrePlantilla/reportePorGeneroYGradoMaximoDeEstudios?tipo=PDF",
            xhrFields: {
                responseType: 'blob'
            },
            success: function (data, status, xhr) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    $("#reportesModal").modal("toggle");
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
                toastr["warning"]("Error al recuperar informacion de documentos cargados: General 1", {progressBar: true, closeButton: true});
            }
        });
}

const visualizarReportePlazaMovimientoMesEspecifico = () => {
    $('#mesModal').modal('show');
    const cmbMes = $('#cmbMes');
    const cmbOrden = $('#cmbOrden');
    let valorSeleccionadoMes = cmbMes.val();
    let valorSeleccionadoOrden = cmbOrden.val();

    // Desactivar cmbOrden inicialmente
    cmbOrden.prop('disabled', true);

    // Agregar evento de escucha al cambio de selección en el select
    cmbMes.on('change', function () {
        // Obtener el valor seleccionado del select
        valorSeleccionadoMes = cmbMes.val();
        // Activar o desactivar cmbOrden según si se seleccionó un valor en cmbMes
        cmbOrden.prop('disabled', !valorSeleccionadoMes);
    });

    cmbOrden.on('change', function () {
        // Obtener el valor seleccionado del select ORDEN
        valorSeleccionadoOrden = cmbOrden.val();

        let orden;
        // Definición de orden en que se generará el reporte (Codigo SQL que pasa como parámetro para definir el orden en que se generará el reporte)
        if (valorSeleccionadoOrden === "1") {
            orden = "asCatalogoPuesto.Nivel ASC";
        } else if (valorSeleccionadoOrden === "2") {
            orden = "asCatalogoPuesto.Nivel DESC";
        } else {
            orden = "asPersona.apellido_paterno ASC";
        }

        //Se ejecuta cuando ambos combos tengn valores
        if (valorSeleccionadoMes && valorSeleccionadoOrden) {
            const url = "reportCierrePlantilla/obtenerMovimientosPlazasMes?mes=" + valorSeleccionadoMes + "&orden=" + orden + "&tipo=PDF";
            const modalOrden = $('#mesModal');
            llamadaAjaxOrden(url, modalOrden);
        }
        // Desvincular los eventos
        cmbMes.off('change');
        cmbOrden.off('change');
    });
};

function visualizarReporteMensualDePlazasOcupadaas() {
    $('#dateModal').modal('show');

    $('#btnGenerarReporte').click(function () {
        const fechaInicio = $('#fechaInicio').val();
        const fechaFin = $('#fechaFin').val();
        const numeroExpediente = $('#numero_expediente').val();

        generarReporte(fechaInicio, fechaFin, numeroExpediente, true);
    });

    $('#btnDescargarReporte').click(function () {
        const fechaInicio = $('#fechaInicio').val();
        const fechaFin = $('#fechaFin').val();
        const numeroExpediente = $('#numero_expediente').val();

        generarReporte(fechaInicio, fechaFin, numeroExpediente, false);
    });
}

function visualizarReporteGeneroYGradoDeEstudios(){
            $.ajax({
            type: "GET",
            url: "reportCierrePlantilla/reportePorGeneroYGradoMaximoDeEstudios?tipo=PDF",
            xhrFields: {
                responseType: 'blob'
            },
            success: function (data, status, xhr) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    $('#mesModal').modal('hide');
                    $('#cmbMes').val("*");
                    $("#reportesModal").modal("toggle");
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
                toastr["warning"]("Error al recuperar informacion de documentos cargados: General 1", {progressBar: true, closeButton: true});
            }
        });
}

function visualizarReporteMensualDePlazasOcupadaas() {
    $('#dateModal').modal('show');

    $('#btnGenerarReporte').click(function () {
        const fechaInicio = $('#fechaInicio').val();
        const fechaFin = $('#fechaFin').val();
        const numeroExpediente = $('#numero_expediente').val();

        generarReportePlaza(fechaInicio, fechaFin, numeroExpediente, true);
    });

    $('#btnDescargarReporte').click(function () {
        const fechaInicio = $('#fechaInicio').val();
        const fechaFin = $('#fechaFin').val();
        const numeroExpediente = $('#numero_expediente').val();

        generarReportePlaza(fechaInicio, fechaFin, numeroExpediente, false);
    });
}

function generarReporteMensualDePlazasOcupadas() {
    $('#dateModal').modal('show');

    $('#btnGenerarReporte').click(function () {
        const fechaInicio = $('#fechaInicio').val();
        const fechaFin = $('#fechaFin').val();
        const numeroExpediente = $('#numero_expediente').val();

        generarReportePlaza(fechaInicio, fechaFin, numeroExpediente, true);
    });

    $('#btnDescargarReporte').click(function () {
        const fechaInicio = $('#fechaInicio').val();
        const fechaFin = $('#fechaFin').val();
        const numeroExpediente = $('#numero_expediente').val();

        generarReportePlaza(fechaInicio, fechaFin, numeroExpediente, false);
    });
}
/*==========================================================
 FUNCIONES GENERADORAS DE REPORTES 
 ==========================================================*/
const llamadaAjaxDescargaOrden = (url, ordenModal, nombreDescarga) => {
    $.ajax({
        type: "GET",
        contentType: 'application/pdf',
        url: url,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (blob) {
            if ($.isEmptyObject(blob)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            ordenModal.modal('hide');
            toastr["success"]("Reporte Generado con éxito", "Aviso", {progressBar: true, closeButton: true});
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            //Nombre de la descarga
            link.download = nombreDescarga;
            link.click();
        }, error: function (e) {
            toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
        }
    });
};

const generarReporteCierrePlantilla = () => {
    $('#ordenModal').modal('show');
    const cmbOrdenReport = $('#cmbOrdenReport');
    let valorSeleccionadoOrden = cmbOrdenReport.val();
    cmbOrdenReport.on('change', function () {
        // Obtener el valor seleccionado del select ORDEN
        valorSeleccionadoOrden = cmbOrdenReport.val();

        let orden;
        // Definición de orden en que se generará el reporte (Codigo SQL que pasa como parámetro para definir el orden en que se generará el reporte)
        if (valorSeleccionadoOrden === "1") {
            orden = "consulta_original.id_area ASC";
        } else if (valorSeleccionadoOrden === "2") {
            orden = "consulta_original.id_area DESC";
        } else {
            orden = "consulta_original.apellido_paterno ASC";
        }

        const url = "reportCierrePlantilla/reporteCierrePlantilla?mes=" + valorActualMes + "&orden=" + orden + "&tipo=PDF";
        const ordenModal = $('#ordenModal');
        const nombreDescarga = "PlantillaPersonalMesNumero" + valorActualMes + ".pdf";
        llamadaAjaxDescargaOrden(url, ordenModal, nombreDescarga);
        // Desvincular los eventos
        cmbOrdenReport.off('change');
    });
};

const generarSubReporteI = () => {
    $('#ordenModalSubI').modal('show');
    const cmbOrdenReportSubI = $('#cmbOrdenReportSubI');
    let valorSeleccionadoOrden = cmbOrdenReportSubI.val();
    cmbOrdenReportSubI.on('change', function () {
        // Obtener el valor seleccionado del select ORDEN
        valorSeleccionadoOrden = cmbOrdenReportSubI.val();

        let orden;
        // Definición de orden en que se generará el reporte (Codigo SQL que pasa como parámetro para definir el orden en que se generará el reporte)
        if (valorSeleccionadoOrden === "1") {
            orden = "consulta_original.codigo_puesto";
        } else if (valorSeleccionadoOrden === "2") {
            orden = "consulta_original.puesto";
        } else {
            orden = "consulta_original.tipo_nomina";
        }

        const url = "reportCierrePlantilla/reporteCierrePlantillaSubI?mes=" + valorActualMes + "&orden=" + orden + "&tipo=PDF";
        const ordenModal = $('#ordenModalSubI');
        const nombreDescarga = "PlantillaPersonalSubIMesNumero" + valorActualMes + ".pdf";
        llamadaAjaxDescargaOrden(url, ordenModal, nombreDescarga);
        // Desvincular los eventos
        cmbOrdenReportSubI.off('change');
    });
};

const generarReportePuestoGenero = () => {
    $('#ordenModalPuestoGenero').modal('show');
    const cmbOrdenPuestoGenero = $('#cmbOrdenPuestoGenero');
    let valorSeleccionadoOrden = cmbOrdenPuestoGenero.val();
    cmbOrdenPuestoGenero.on('change', function () {
        // Obtener el valor seleccionado del select ORDEN
        valorSeleccionadoOrden = cmbOrdenPuestoGenero.val();

        let orden;
        // Definición de orden en que se generará el reporte (Codigo SQL que pasa como parámetro para definir el orden en que se generará el reporte)
        if (valorSeleccionadoOrden === "1") {
            orden = "nivel";
        } else if (valorSeleccionadoOrden === "2") {
            orden = "catalogoPuesto.puesto";
        } else {
            orden = "desc_area";
        }

        const url = "reportCierrePlantilla/reportePuestoGenero?mes=" + valorActualMes + "&orden=" + orden + "&tipo=PDF";
        const ordenModal = $('#ordenModalPuestoGenero');
        const nombreDescarga = "ResumenTrabajadoresPuestoGenero_Mes" + valorActualMes + ".pdf";
        llamadaAjaxDescargaOrden(url, ordenModal, nombreDescarga);
        // Desvincular los eventos
        cmbOrdenPuestoGenero.off('change');
    });
};

function generarReporteGeneroYGradoDeEstudios() {
    $.ajax({
        type: 'GET',
        contentType: 'application/pdf',
        url: "reportCierrePlantilla/reportePorGeneroYGradoMaximoDeEstudios?tipo=PDF",
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
            //Nombre de la descarga
            link.download = "ResumenDeGeneroYGradoDeEstudios.pdf";
            link.click();
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
        }
    });

}

const generarReportePlazaMovimientoMesEspecifico = () => {
    $('#mesModal').modal('show');
    const cmbMes = $('#cmbMes');
    const cmbOrden = $('#cmbOrden');
    let valorSeleccionadoMes = cmbMes.val();
    let valorSeleccionadoOrden = cmbOrden.val();

    // Desactivar cmbOrden inicialmente
    cmbOrden.prop('disabled', true);

    // Agregar evento de escucha al cambio de selección en el select
    cmbMes.on('change', function () {
        // Obtener el valor seleccionado del select
        valorSeleccionadoMes = cmbMes.val();
        // Activar o desactivar cmbOrden según si se seleccionó un valor en cmbMes
        cmbOrden.prop('disabled', !valorSeleccionadoMes);
    });

    cmbOrden.on('change', function () {
        // Obtener el valor seleccionado del select ORDEN
        valorSeleccionadoOrden = cmbOrden.val();

        let orden;
        // Definición de orden en que se generará el reporte (Codigo SQL que pasa como parámetro para definir el orden en que se generará el reporte)
        if (valorSeleccionadoOrden === "1") {
            orden = "asCatalogoPuesto.Nivel ASC";
        } else {
            orden = "asCatalogoPuesto.Nivel DESC";
        }


        //Se ejecuta cuando ambos combos tengn valores
        if (valorSeleccionadoMes && valorSeleccionadoOrden) {
            const url = "reportCierrePlantilla/obtenerMovimientosPlazasMes?mes=" + valorSeleccionadoMes + "&orden=" + orden + "&tipo=PDF";
            const ordenModal = $('#mesModal');
            const nombreDescarga = "ReporteMovimientosMes" + valorSeleccionadoMes + ".pdf";
            llamadaAjaxDescargaOrden(url, ordenModal, nombreDescarga);
        }
        // Desvincular los eventos
        cmbMes.off('change');
        cmbOrden.off('change');
    });
};

function generarReportePlaza(fechaInicio, fechaFin, numeroExpediente, visualizar) {
    $.ajax({
        type: 'GET',
        url: "reportCierrePlantilla/reporteMesualDePLazasOcupadasPorNumeroExpediente?fecha_inicio=" + fechaInicio + "&fecha_fin=" + fechaFin + "&numero_expediente=" + numeroExpediente + "&tipo=PDF",
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, textStatus, jqXHR) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                if (visualizar) {
                    const url = window.URL.createObjectURL(data);
                    const embed = document.createElement('iframe');
                    embed.src = url;
                    embed.style.width = "100%";
                    embed.style.height = "500px";
                    document.getElementById('pdfContainer').innerHTML = '';
                    document.getElementById('pdfContainer').appendChild(embed);
                } else {
                    const link = document.createElement('a');
                    link.href = window.URL.createObjectURL(data);
                    link.download = "ReporteMensualPlazasDe" + numeroExpediente + ".pdf";
                    link.click();
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar información de documentos cargados: " + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

function visualizarReporteMovimientosRegistradosPlantilla() {
    $('#movimientosPlantillaModal').modal('show');

    const mes = $('#mes');
    const year = $('#year');

    mes && year.on('change', function() {
        const selectedMes = mes.val();
        const selectedYear = year.val();
        $.ajax({
            type: 'GET',
            url: "reportCierrePlantilla/reporteMovimientosRegistradosPlantilla?mes=" + selectedMes + "&year=" + selectedYear + "&tipo=PDF",
            xhrFields: {
                responseType: 'blob'
            },
            success: function(data, textStatus, jqXHR) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontró información...", "Aviso", {
                        progressBar: true,
                        closeButton: true
                    });
                } else {
                    $('#movimientosPlantillaModal').modal('hide');
                    mes.val('');
                    year.val('');
                    $('#reportesModal').modal('show');
                    var contentType = jqXHR.getResponseHeader("Content-Type");
                    var filename = jqXHR.getResponseHeader("Content-disposition");
                    filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                    //Customize the filename here
                    filename = 'MovimientosRegistradosPlantilla del mes ' + mes + ' del año ' + year + ".pdf";
                    var url = window.URL.createObjectURL(data);
                    var frame = $('#reportes_frame');
                    frame.attr('src', url).show();
                }
            },
            error: function(e) {
                toastr["warning"]("Error al recuperar información de documentos cargados: General 1", {
                    progressBar: true,
                    closeButton: true
                });
            }
        });
    });
}

function descargarReporteMovimientosRegistradosPlantilla(selectedMes, selectedYear) {
    $.ajax({
        type: 'GET',
        url: "reportCierrePlantilla/reporteMovimientosRegistradosPlantilla?mes=" + selectedMes + "&year=" + selectedYear + "&tipo=PDF",
        xhrFields: {
            responseType: 'blob'
        },
        success: function(data, textStatus, jqXHR) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {
                    progressBar: true,
                    closeButton: true
                });
            } else {
                var contentType = jqXHR.getResponseHeader("Content-Type");

                // Create a temporary <a> element to initiate the download
                var link = document.createElement('a');
                link.href = window.URL.createObjectURL(data);
                link.download = 'MovimientosRegistradosPlantilla_' + selectedMes + '_' + selectedYear + '.pdf';
                link.click();
            }
        },
        error: function(e) {
            toastr["warning"]("Error al recuperar información de documentos cargados: General 1", {
                progressBar: true,
                closeButton: true
            });
        }
    });
}

function generarReporteMovimientosRegistradosPlantilla() {
    $('#movimientosPlantillaModal').modal('show');

    const mes = $('#mes');
    const year = $('#year');

    mes && year.on('change', function() {
        const selectedMes = mes.val();
        const selectedYear = year.val();
        descargarReporteMovimientosRegistradosPlantilla(selectedMes, selectedYear);
    });
}

/*=========================================================================
 LIMPIEZA DE INPUTS
 ==========================================================================*/
$('#mesModal').on('hidden.bs.modal', function () {
    $("#cmbMes").val("*");
    $("#cmbOrden").val("*");
});
$('#ordenModal').on('hidden.bs.modal', function () {
    $("#cmbOrdenReport").val("*");
});
$('#ordenModalSubI').on('hidden.bs.modal', function () {
    $("#cmbOrdenReportSubI").val("*");
});
$('#ordenModalPuestoGenero').on('hidden.bs.modal', function () {
    $("#cmbOrdenPuestoGenero").val("*");
});
$('#dateModal').on('show.bs.modal', function () {
    $('#fechaInicio').val("");
    $("#fechaFin").val("");
    $('#numero_expediente').val("");
    $('#pdfContainer').empty();
});
