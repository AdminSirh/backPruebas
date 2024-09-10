//******************************************CARGA DEL DOM**********************************
const nominaTransportacion = 2;
document.addEventListener('DOMContentLoaded', () => {
    cargarPeriodos();
});
//Retorno al home
function adminRa15() {
    window.location.href = 'adminListadoNombTransp?tab=tab-1';
}

function cargarPeriodos() {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTodosLosPeriodosPorNomina/" + nominaTransportacion,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#periodoTransportacion, #periodoTransportacionTxt, #periodoTransportacionReporte, #periodoTransportacionResumen, #periodoTransportacionResumenTxt').empty();
                var fechaActual = new Date();
                if (data.length > 0) {
                    var minDiferencia = Number.MAX_SAFE_INTEGER;
                    var indexMinDiferencia = 0;
                    for (var i = 0; i < data.length; i++) {
                        var fechaInicial = new Date(data[i].fecha_inicial);
                        var diferencia = Math.abs(fechaActual - fechaInicial);
                        if (diferencia < minDiferencia) {
                            minDiferencia = diferencia;
                            indexMinDiferencia = i;
                        }
                        agregarOpcionPeriodo(data[i]);
                    }
                    $('#periodoTransportacion, #periodoTransportacionTxt, #periodoTransportacionReporte, #periodoTransportacionResumen, #periodoTransportacionResumenTxt').val(data[indexMinDiferencia].periodo);
                }
            }
        },
        error: function (e) {
            toastr["error"](e, "Error al obtener periodos de la nómina", {progressBar: true, closeButton: true});
        }
    });
}

function agregarOpcionPeriodo(periodo) {
    var fechaInicial = new Date(periodo.fecha_inicial);
    var fechaFinal = new Date(periodo.fecha_final);
    var n_periodo = periodo.periodo;
    var dias_inicial = fechaInicial.getDate();
    var meses_inicial = fechaInicial.getMonth() + 1;
    var anios_inicial = fechaInicial.getFullYear();
    var dias_final = fechaFinal.getDate();
    var meses_final = fechaFinal.getMonth() + 1;
    var anios_final = fechaFinal.getFullYear();

    var option = '<option value="' + n_periodo + '">' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
            '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>';

    $('#periodoTransportacion, #periodoTransportacionTxt, #periodoTransportacionReporte, #periodoTransportacionResumen, #periodoTransportacionResumenTxt').append(option);
}

function generarCSVReporteCapturaSemanal() {
    //Espera al click del usuario
    $("#modalBuscarCSVPeriodo").submit(function (event) {
        event.preventDefault();
        let idPeriodoSeleccionado = $('#periodoTransportacion').val();
        $.ajax({
            type: 'GET',
            url: "ra15/obtenerDatosCSVPeriodo/" + idPeriodoSeleccionado,
            dataType: 'json',
            success: function (contenido) {
                if ($.isEmptyObject(contenido)) {
                    toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    const encabezados = ['Expediente', 'Nombre', 'Puesto', 'COMISIONADO', 'Sueldo Hora',
                        'LunesJN', 'MartesJN', 'MiercolesJN', 'JuevesJN', 'ViernesJN', 'SabadoJN', 'DomingoJN',
                        'LunesExcedenteJ', 'MartesExcedenteJ', 'MiercolesExcedenteJ', 'JuevesExcedenteJ', 'ViernesExcedenteJ', 'SabadoExcedenteJ', 'DomingoExcedenteJ',
                        'LunesTExtra', 'MartesTExtra', 'MiercolesTExtra', 'JuevesTExtra', 'ViernesTExtra', 'SabadoTExtra', 'DomingoTExtra',
                        'LunesPLab', 'MartesPLab', 'MiercolesPLab', 'JuevesPLab', 'ViernesPLab', 'SabadoPLab', 'DomingoPLab',
                        'LunesInasist', 'MartesInasist', 'MiercolesInasist', 'JuevesInasist', 'ViernesInasist', 'SabadoInasist', 'DomingoInasist',
                        'LunesSupl', 'MartesSupl', 'MiercolesSupl', 'JuevesSupl', 'ViernesSupl', 'SabadoSupl', 'DomingoSupl',
                        'DiasLab', 'Diaspago', 'DiasCve27', 'Jornada', 'DifSueldo', 'PrimaDom', 'DescLab', 'HorasDoble', 'HorasTriple', 'Cve19', 'Festivo', 'Omisiones', 'TotFaltas'];

                    // Crear contenido CSV
                    let csvContent = encabezados.join(',') + '\n';
                    contenido.forEach(item => {
                        let row = [];
                        row.push(item.numero_expediente);
                        row.push(item.nombre_completo);
                        row.push(item.puesto);
                        row.push(item.comisionado);
                        row.push(item.sueldo_hora);
                        row.push(item.lunes_jn);
                        row.push(item.martes_jn);
                        row.push(item.miercoles_jn);
                        row.push(item.jueves_jn);
                        row.push(item.viernes_jn);
                        row.push(item.sabado_jn);
                        row.push(item.domingo_jn);
                        //Excedente de jornada
                        row.push(item.lunes_ej);
                        row.push(item.martes_ej);
                        row.push(item.miercoles_ej);
                        row.push(item.jueves_ej);
                        row.push(item.viernes_ej);
                        row.push(item.sabado_ej);
                        row.push(item.domingo_ej);
                        //Tiempo extra 
                        row.push(item.lunes_te);
                        row.push(item.martes_te);
                        row.push(item.miercoles_te);
                        row.push(item.jueves_te);
                        row.push(item.viernes_te);
                        row.push(item.sabado_te);
                        row.push(item.domingo_te);
                        //Descansos laborados 
                        row.push(item.lunes_pl);
                        row.push(item.martes_pl);
                        row.push(item.miercoles_pl);
                        row.push(item.jueves_pl);
                        row.push(item.viernes_pl);
                        row.push(item.sabado_pl);
                        row.push(item.domingo_pl);
                        //Inasistencias
                        row.push(item.lunes_inci);
                        row.push(item.martes_inci);
                        row.push(item.miercoles_inci);
                        row.push(item.jueves_inci);
                        row.push(item.viernes_inci);
                        row.push(item.sabado_inci);
                        row.push(item.domingo_inci);
                        //Suplencias
                        row.push(item.lunes_sup);
                        row.push(item.martes_sup);
                        row.push(item.miercoles_sup);
                        row.push(item.jueves_sup);
                        row.push(item.viernes_sup);
                        row.push(item.sabado_sup);
                        row.push(item.domingo_sup);
                        //Resto de los datos
                        row.push(item.dias_laborados);
                        row.push(item.dias_pago || '');
                        row.push(item.dias_cve_27 || '');
                        row.push(item.horas_turno || '');
                        row.push(item.dif_sueldo || '');
                        row.push(item.prima_dominical || '');
                        row.push(item.total_paseos || '');
                        row.push(item.horas_doble || '');
                        row.push(item.horas_triple || '');
                        row.push(item.cve19 || '');
                        row.push(item.festivo || '');
                        row.push(item.omisiones || '');
                        row.push(item.total_faltas || '');

                        csvContent += row.join(',') + '\n';
                    });

                    // Crear un Blob con el contenido CSV
                    const blob = new Blob([csvContent], {type: 'text/csv'});

                    // Crear un objeto URL para el Blob
                    const url = window.URL.createObjectURL(blob);

                    // Crear un enlace para descargar el archivo
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = 'reporte_captura_semanal' + idPeriodoSeleccionado + '.csv';

                    // Agregar el enlace al documento y hacer clic automáticamente
                    document.body.appendChild(a);
                    a.click();

                    // Limpiar el objeto URL y eliminar el enlace del documento
                    window.URL.revokeObjectURL(url);
                    document.body.removeChild(a);
                    toastr["success"]("Reporte Generado con éxito", "Aviso", {progressBar: true, closeButton: true});
                    $('#modalBuscarCSVPeriodo').modal('hide');
                }
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar datos para CSV: " + e, " Error", {progressBar: true, closeButton: true});
            }
        });
    });
}

function generarTXTReporteCapturaSemanal() {
    //Espera al click del usuario
    $("#modalBuscarTXTPeriodo").submit(function (event) {
        event.preventDefault();
        let idPeriodoSeleccionado = $('#periodoTransportacionTxt').val();
        $.ajax({
            type: 'GET',
            url: "ra15/obtenerDatosCSVPeriodo/" + idPeriodoSeleccionado,
            dataType: 'json',
            success: function (contenido) {
                if ($.isEmptyObject(contenido)) {
                    toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    // Crear contenido TXT
                    let txtContent = 'Expediente;Nombre;Puesto;COMISIONADO;SueldoHora;LunesJN;MartesJN;MiercolesJN;JuevesJN;ViernesJN;SabadoJN;DomingoJN;LunesEJ;MartesEJ;MiercolesEJ;JuevesEJ;ViernesEJ;SabadoEJ;DomingoEJ;LunesTExtra;MartesTExtra;MiercolesTExtra;JuevesTExtra;ViernesTExtra;SabadoTExtra;DomingoTExtra;LunesPLab;MartesPlab;MiercolesPlab;JuevesPlab;ViernesPLab;SabadoPlab;DomingoPLab;LunesInci;MartesInci;MiercolesInci;JuevesInci;ViernesInci;SabadoInci;DomingoInci;LunesSup;MartesSup;MiercolesSup;JuevesSup;ViernesSup;SabadoSup;DomingoSup;DiasLab;DiasPago;DiasCve27;Jornada;DifSueldo;PrimaDom;DescLab;HorasDoble;HorasTriple;cve19;Festivo;Omisiones;TotFaltas\n';
                    contenido.forEach(item => {
                        txtContent += `${item.numero_expediente ?? ''};${item.nombre_completo ?? ''};${item.puesto ?? ''};${item.comisionado ?? ''};${item.sueldo_hora ?? ''};${item.lunes_jn ?? ''};${item.martes_jn ?? ''};${item.miercoles_jn ?? ''};${item.jueves_jn ?? ''};${item.viernes_jn ?? ''};${item.sabado_jn ?? ''};${item.domingo_jn ?? ''}; ${item.lunes_ej ?? ''};${item.martes_ej ?? ''};${item.miercoles_ej ?? ''};${item.jueves_ej ?? ''};${item.viernes_ej ?? ''};${item.sabado_ej ?? ''};${item.domingo_ej ?? ''};${item.lunes_te ?? ''};${item.martes_te ?? ''};${item.miercoles_te ?? ''};${item.jueves_te ?? ''};${item.viernes_te ?? ''};${item.sabado_te ?? ''};${item.domingo_te ?? ''};${item.lunes_pl ?? ''};${item.martes_pl ?? ''};${item.miercoles_pl ?? ''};${item.jueves_pl ?? ''};${item.viernes_pl ?? ''};${item.sabado_pl ?? ''};${item.domingo_pl ?? ''}; ${item.lunes_inci ?? ''};${item.martes_inci ?? ''};${item.miercoles_inci ?? ''};${item.jueves_inci ?? ''};${item.viernes_inci ?? ''};${item.sabado_inci ?? ''};${item.domingo_inci ?? ''}; ${item.lunes_sup ?? ''};${item.martes_sup ?? ''};${item.miercoles_sup ?? ''};${item.jueves_sup ?? ''};${item.viernes_sup ?? ''};${item.sabado_sup ?? ''};${item.domingo_sup ?? ''};${item.dias_laborados ?? ''};${item.dias_pago ?? ''};${item.dias_cve_27 ?? ''};${item.horas_turno ?? ''};${item.dif_sueldo ?? ''};${item.prima_dominical ?? ''};${item.total_paseos ?? ''};${item.horas_doble ?? ''};${item.horas_triple ?? ''};${item.cve19 ?? ''};${item.festivo ?? ''};${item.omisiones ?? ''};${item.total_faltas ?? ''}\n`;
                    });

                    // Crear un Blob con el contenido TXT
                    const blob = new Blob([txtContent], {type: 'text/plain'});

                    // Crear un objeto URL para el Blob
                    const url = window.URL.createObjectURL(blob);

                    // Crear un enlace para descargar el archivo
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = 'reporte_captura_semanal' + idPeriodoSeleccionado + '.txt';

                    // Agregar el enlace al documento y hacer clic automáticamente
                    document.body.appendChild(a);
                    a.click();

                    // Limpiar el objeto URL y eliminar el enlace del documento
                    window.URL.revokeObjectURL(url);
                    document.body.removeChild(a);
                    toastr["success"]("Reporte Generado con éxito", "Aviso", {progressBar: true, closeButton: true});
                    $('#modalBuscarTXTPeriodo').modal('hide');
                }

            },
            error: function (e) {
                toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
            }
        });
    });
}

function generarResumenCSV() {
    $("#modalResumenCSV").submit(function (event) {
        event.preventDefault();
        let idPeriodoSeleccionado = $('#periodoTransportacionResumen').val();
        $.ajax({
            type: 'GET',
            url: "ra15/obtenerDatosCSVResumen/" + idPeriodoSeleccionado,
            dataType: 'json',
            success: function (contenido) {
                if ($.isEmptyObject(contenido)) {
                    toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    const encabezados = ['Expediente', 'Nombre Trabajador', 'Sueldo Hora', 'Horas Turno', 'Horas Doble', 'Horas Triple', 'Dias Laborados', 'Total Faltas',
                        'Total paseos', 'Prima Dominical', 'Festivo', 'Omisiones', 'Paseos Suplencia', 'Prima Suplencia',
                        'Tiempo Extra Suplencia', 'Sueldo Suplencia', 'Dias Pago', 'Dias Clave 27'];

                    // Crear contenido CSV
                    let csvContent = encabezados.join(',') + '\n';
                    contenido.forEach(item => {
                        let row = [];
                        row.push(item.numero_expediente);
                        row.push(item.nombre_completo);
                        row.push(item.sueldo_hora);
                        row.push(item.horas_turno);
                        row.push(item.horas_doble);
                        row.push(item.horas_triple);
                        row.push(item.dias_laborados);
                        row.push(item.total_faltas);
                        row.push(item.total_paseos);
                        row.push(item.prima_dominical);
                        row.push(item.festivo);
                        row.push(item.omisiones);
                        row.push(item.dif_paseos);
                        row.push(item.dif_prima);
                        row.push(item.dif_tiempo_extra);
                        row.push(item.dif_sueldo);
                        row.push(item.dias_pago);
                        row.push(item.dias_cve_27);
                        csvContent += row.join(',') + '\n';
                    });

                    // Crear un Blob con el contenido CSV
                    const blob = new Blob([csvContent], {type: 'text/csv'});

                    // Crear un objeto URL para el Blob
                    const url = window.URL.createObjectURL(blob);

                    // Crear un enlace para descargar el archivo
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = 'resumen_incidencias_captura_semanal' + idPeriodoSeleccionado + '.csv';

                    // Agregar el enlace al documento y hacer clic automáticamente
                    document.body.appendChild(a);
                    a.click();

                    // Limpiar el objeto URL y eliminar el enlace del documento
                    window.URL.revokeObjectURL(url);
                    document.body.removeChild(a);
                    toastr["success"]("Reporte Generado con éxito", "Aviso", {progressBar: true, closeButton: true});
                    $('#modalResumenCSV').modal('hide');
                }
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar datos para CSV: " + e, " Error", {progressBar: true, closeButton: true});
            }
        });
    });
}

function generarResumenTxt() {
    $("#modalResumenTxt").submit(function (event) {
        event.preventDefault();
        let idPeriodoSeleccionado = $('#periodoTransportacionResumenTxt').val();
        $.ajax({
            type: 'GET',
            url: "ra15/obtenerDatosCSVResumen/" + idPeriodoSeleccionado,
            dataType: 'json',
            success: function (contenido) {
                if ($.isEmptyObject(contenido)) {
                    toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    // Crear contenido TXT
                    let txtContent = 'Expediente;Nombre;SueldoHora;HorasTurno;HorasDoble;HorasTriple;DiasLaborados;TotalFaltas;TotalPaseos;PrimaDominical;Festivo;Omisiones;PaseosSuplencia;PrimaSuplencia;TiempoExtraSuplencia;SueldoSuplencia;DiasPago;DiasCVE27\n';
                    contenido.forEach(item => {
                        txtContent += `${item.numero_expediente ?? ''};${item.nombre_completo ?? ''};${item.sueldo_hora ?? ''};${item.horas_turno ?? ''};${item.horas_doble ?? ''};${item.horas_triple ?? ''};${item.dias_laborados ?? ''};${item.total_faltas ?? ''};${item.total_paseos ?? ''};${item.prima_dominical ?? ''};${item.festivo ?? ''};${item.omisiones ?? ''};${item.dif_paseos ?? ''};${item.dif_prima ?? ''};${item.dif_tiempo_extra ?? ''};${item.dif_sueldo ?? ''};${item.dias_pago ?? ''};${item.dias_cve_27 ?? ''}\n`;
                    });

                    // Crear un Blob con el contenido TXT
                    const blob = new Blob([txtContent], {type: 'text/plain'});

                    // Crear un objeto URL para el Blob
                    const url = window.URL.createObjectURL(blob);

                    // Crear un enlace para descargar el archivo
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = 'resumen_incidencias_captura_semanal' + idPeriodoSeleccionado + '.txt';

                    // Agregar el enlace al documento y hacer clic automáticamente
                    document.body.appendChild(a);
                    a.click();

                    // Limpiar el objeto URL y eliminar el enlace del documento
                    window.URL.revokeObjectURL(url);
                    document.body.removeChild(a);
                    toastr["success"]("Reporte Generado con éxito", "Aviso", {progressBar: true, closeButton: true});
                    $('#modalResumenTxt').modal('hide');
                }

            },
            error: function (e) {
                toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
            }
        });
    });
}

function vizualizarReporteSinCapturaEnElPeriodo() {
    $("#modalPeriodoReporte").submit(function (event) {
        event.preventDefault();
        let idPeriodoSeleccionado = $('#periodoTransportacionReporte').val();
        const url = "report/reporteSinTurnoEnElPeriodoRA15?periodo_id=" + idPeriodoSeleccionado + "&tipo=PDF";
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
                    $('#modalPeriodoReporte').modal('hide');
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
    });
}

function descargarReporteSinCapturaEnElPeriodo() {
    $("#modalPeriodoReporte").submit(function (event) {
        event.preventDefault();
        let idPeriodoSeleccionado = $('#periodoTransportacionReporte').val();
        $.ajax({
            type: "GET",
            contentType: 'application/pdf',
            url: "report/reporteSinTurnoEnElPeriodoRA15?periodo_id=" + idPeriodoSeleccionado + "&tipo=PDF",
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
                link.download = 'reporteSinCapturaPeriodo' + idPeriodoSeleccionado;
                link.click();
            }, error: function (e) {
                toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
            }
        });
    });
}

function vizualizarReporteCapturaSemanalRA15() {
    $("#modalPeriodoReporte").submit(function (event) {
        event.preventDefault();
        let idPeriodoSeleccionado = $('#periodoTransportacionReporte').val();
        const url = "report/reporteCapturaSemanalRA15?periodo_id=" + idPeriodoSeleccionado + "&tipo=PDF";
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
                    $('#modalPeriodoReporte').modal('hide');
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
    });
}

function descargarReporteCapturaSemanalRA15() {
    $("#modalPeriodoReporte").submit(function (event) {
        event.preventDefault();
        let idPeriodoSeleccionado = $('#periodoTransportacionReporte').val();
        $.ajax({
            type: "GET",
            contentType: 'application/pdf',
            url: "report/reporteCapturaSemanalRA15?periodo_id=" + idPeriodoSeleccionado + "&tipo=PDF",
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
                link.download = 'reporteCapturaSemanalRA15' + idPeriodoSeleccionado;
                link.click();
            }, error: function (e) {
                toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
            }
        });
    });
}

//Limpiar validación al cerrar el modal de cualquier forma
$('#modalBuscarCSVPeriodo').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
});