$(document).ready(function () {
    let hoy = new Date().toISOString().split('T')[0];
    let fechaInicial = $('#fechaInicial');
    let fechaFinal = $('#fechaFinal');

    // Se coloca el día de hoy en ambos campos de las fechas cuando carga el documento
    fechaInicial.val(hoy);
    fechaFinal.val(hoy);
    //Configuración del combo para mostrar los periodos seleccionados
    $('#cmbPeriodo').on('change', function () {
        let selectedOption = $(this).val();
        //Mes actual
        if (selectedOption === '1') {
            let firstDay = new Date(new Date().getFullYear(), new Date().getMonth(), 1);
            let lastDay = new Date(new Date().getFullYear(), new Date().getMonth() + 1, 0);
            fechaInicial.val(firstDay.toISOString().split('T')[0]);
            fechaFinal.val(lastDay.toISOString().split('T')[0]);
            //Mes anterior
        } else if (selectedOption === '2') {
            let firstDay = new Date(new Date().getFullYear(), new Date().getMonth() - 1, 1);
            let lastDay = new Date(new Date().getFullYear(), new Date().getMonth(), 0);
            fechaInicial.val(firstDay.toISOString().split('T')[0]);
            fechaFinal.val(lastDay.toISOString().split('T')[0]);
            //Siguiente mes
        } else if (selectedOption === '3') {
            let firstDay = new Date(new Date().getFullYear(), new Date().getMonth() + 1, 1);
            let lastDay = new Date(new Date().getFullYear(), new Date().getMonth() + 2, 0);
            fechaInicial.val(firstDay.toISOString().split('T')[0]);
            fechaFinal.val(lastDay.toISOString().split('T')[0]);
        } else {
            // Si se selecciona "Ninguno" se restauran las fechas actuales
            fechaInicial.val(hoy);
            fechaFinal.val(hoy);
        }
    });

});

const generarArchivosSUAInasistencias = () => {
    let fechaInicio = $('#fechaInicial').val();
    let fechaFin = $('#fechaFinal').val();
    if ($.trim(fechaInicio) === "") {
        toastr["warning"]("La Fecha Inicial no debe estar vacía", " Aviso", {progressBar: true, closeButton: true});
        return;
    }
    if ($.trim(fechaFin) === "") {
        toastr["warning"]("La Fecha Final no debe estar vacía", " Aviso", {progressBar: true, closeButton: true});
        return;
    }

    if (fechaFin < fechaInicio) {
        toastr["warning"]("La Fecha Final debe ser mayor a la fecha inicial", " Aviso", {progressBar: true, closeButton: true});
        return;
    }
    /*Se le resta un día a la inicial para obtener las incapacidades del mes pasado que pasarán como días primero en el txt
     Objeto date de la fecha de inicio */
    fechaInicio = new Date(fechaInicio);
    fechaInicio = new Date(fechaInicio.getTime() + Math.abs(fechaInicio.getTimezoneOffset() * 60000));
    //Objeto date de la fecha de fin
    fechaFin = new Date(fechaFin);
    fechaFin = new Date(fechaFin.getTime() + Math.abs(fechaFin.getTimezoneOffset() * 60000));
    //Se le resta un día a la fecha de inicio con la finalidad de abarcar las incapacidades que entran para el primero del mes
    fechaInicio.setDate(fechaInicio.getDate() - 1);

    let añoInicio = fechaInicio.getFullYear();
    let mesInicio = (fechaInicio.getMonth() + 1).toString().padStart(2, '0');
    let diaInicio = fechaInicio.getDate().toString().padStart(2, '0');
    let fechaInicialFormateada = añoInicio + "-" + mesInicio + "-" + diaInicio;

    let añoFin = fechaFin.getFullYear();
    let mesFin = (fechaFin.getMonth() + 1).toString().padStart(2, '0');
    let diaFin = fechaFin.getDate().toString().padStart(2, '0');
    let fechaFinalFormateada = añoFin + "-" + mesFin + "-" + diaFin;

    $.ajax({
        type: "GET",
        url: "incidencias/generarTXTIncapacidadesSUA/" + fechaInicialFormateada + "/" + fechaFinalFormateada,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información para las fechas ingresadas...", "Aviso", {progressBar: true, closeButton: true});
                return;
            } else {
                //Formatea el objeto del JSON
                function formatElement(element) {
                    let num_imss = element.num_imss;
                    let fecha_inicial = new Date(element.fecha_inicial);
                    let folio = element.folio;
                    let dias_incapacidad = element.dias_incapacidad.toString().padStart(2, '0');

                    // Obtiene el día, mes, año de la fecha inicial
                    let dia = fecha_inicial.getDate().toString().padStart(2, '0');
                    let mes = (fecha_inicial.getMonth() + 1).toString().padStart(2, '0');
                    let anio = fecha_inicial.getFullYear().toString();

                    /* Se formatea en el formato requerido acorde a documentación encontrada para la importación de Incapacidades para el SUA que es: 
                     * Registro patronal IMSS (11 Alfanumérico)
                     * Num. Seguridad Social (11 Numérico)
                     * Tipo de Movimiento (12 para Incapacidad)
                     * Fecha de Mvto (8 Númerico (ddmmaaaa))
                     * Folio de Incapacidad (5 Alfanumérico)
                     * Dias de Incidencia (2 Númerico)
                     * Salario diario integrado o aportación voluntaria (5 enteros y dos decimales, Numérico)*/
                    return `01080836107${num_imss}12${dia}${mes}${anio}${folio}${dias_incapacidad}0000000`;
                }
                // Se formatean todos los elementos del Json
                let formattedData = data.map(formatElement);
                // Se unen los elementos en un texto único
                let contenido = formattedData.join('\n');
                // Crear un Blob con el contenido y tipo de texto plano
                let blob = new Blob([contenido], {type: "text/plain"});
                // Crear una URL para el Blob
                let url = URL.createObjectURL(blob);
                // Crear un enlace para descargar el archivo
                let nombreMeses = ["ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"];
                let mes = nombreMeses[fechaFin.getMonth()];
                let anio = fechaFin.getFullYear();
                let a = document.createElement("a");
                a.href = url;
                a.download = "IncSUA_" + mes + anio + ".txt";
                // Simular un clic en el enlace para iniciar la descarga
                a.click();
                // Liberar el objeto URL
                URL.revokeObjectURL(url);
            }
        },
        error: function (e) {
            alert(e);
        }
    });
};

const generarArchivoMovimientosSUA = () => {
    let fechaInicio = $('#fechaInicial').val();
    let fechaFin = $('#fechaFinal').val();
    if ($.trim(fechaInicio) === "") {
        toastr["warning"]("La Fecha Inicial no debe estar vacía", " Aviso", {progressBar: true, closeButton: true});
        return;
    }
    if ($.trim(fechaFin) === "") {
        toastr["warning"]("La Fecha Final no debe estar vacía", " Aviso", {progressBar: true, closeButton: true});
        return;
    }

    if (fechaFin < fechaInicio) {
        toastr["warning"]("La Fecha Final debe ser mayor a la fecha inicial", " Aviso", {progressBar: true, closeButton: true});
        return;
    }
    /*Se le resta un día a la inicial para obtener las incapacidades del mes pasado que pasarán como días primero en el txt
     Objeto date de la fecha de inicio */
    fechaInicio = new Date(fechaInicio);
    fechaInicio = new Date(fechaInicio.getTime() + Math.abs(fechaInicio.getTimezoneOffset() * 60000));
    //Objeto date de la fecha de fin
    fechaFin = new Date(fechaFin);
    fechaFin = new Date(fechaFin.getTime() + Math.abs(fechaFin.getTimezoneOffset() * 60000));
    //Se le resta un día a la fecha de inicio con la finalidad de abarcar las incapacidades que entran para el primero del mes
    fechaInicio.setDate(fechaInicio.getDate() - 1);

    let añoInicio = fechaInicio.getFullYear();
    let mesInicio = (fechaInicio.getMonth() + 1).toString().padStart(2, '0');
    let diaInicio = fechaInicio.getDate().toString().padStart(2, '0');
    let fechaInicialFormateada = añoInicio + "-" + mesInicio + "-" + diaInicio;

    let añoFin = fechaFin.getFullYear();
    let mesFin = (fechaFin.getMonth() + 1).toString().padStart(2, '0');
    let diaFin = fechaFin.getDate().toString().padStart(2, '0');
    let fechaFinalFormateada = añoFin + "-" + mesFin + "-" + diaFin;

    $.ajax({
        type: "GET",
        url: "incidencias/generarTXTIncapacidadesMovimientosSUA/" + fechaInicialFormateada + "/" + fechaFinalFormateada,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información para las fechas ingresadas...", "Aviso", {progressBar: true, closeButton: true});
                return;
            } else {
                //Formatea el objeto del JSON
                function formatElement(element) {
                    let num_imss = element.num_imss;
                    let fecha_inicial = new Date(element.fecha_inicial);
                    let fecha_final = new Date(element.fecha_final);
                    let folio = element.folio;
                    let dias_subsidiados = element.dias_incapacidad.toString().padStart(3, '0');
                    let porcentaje_incapacidad = element.porcentaje_cobro_imss.toString().padStart(3, '0');
                    let rama_incapacidad = element.cat_Motivo_Incapacidad.id_motivo_incapacidad; //Verificar Asignación
                    let tipo_riesgo_incapacidad = element.cat_Tipo_Riesgo_Incapacidad.cve_original;
                    let tipo_secuelas_consecuencias = element.cat_Tipo_Secuelas_Incapacidad.cve_original;
                    let tipo_control_incapacidad = element.cat_Tipo_Control_Incapacidad.cve_original;

                    //Se asigna el identificador correspondiente a la rama que maneja el IMMS para Riesgos de Trabajo y Enfermedades Generales (1 para RT y 2 para EG)
                    rama_incapacidad = (rama_incapacidad === 1) ? 2 : (rama_incapacidad === 2) ? 1 : rama_incapacidad;

                    // Obtiene el día, mes, año de la fecha inicial
                    let dia = fecha_inicial.getDate().toString().padStart(2, '0');
                    let mes = (fecha_inicial.getMonth() + 1).toString().padStart(2, '0');
                    let anio = fecha_inicial.getFullYear().toString();

                    // Obtiene el día, mes, año de la fecha final
                    let diaFinal = fecha_final.getDate().toString().padStart(2, '0');
                    let mesFinal = (fecha_final.getMonth() + 1).toString().padStart(2, '0');
                    let anioFinal = fecha_final.getFullYear().toString();

                    /* Se formatea en el formato requerido acorde a documentación encontrada para la importación de Incapacidades para el SUA que es: 
                     * Registro patronal IMSS (11 Alfanumérico)
                     * Num. Seguridad Social (11 Numérico)
                     * Tipo de Incidencia
                     * Fecha de Inicio (ddmmaaaa)
                     * Folio
                     * Dias subsidiados
                     * Porcentaje de la incapacidad (3 dígitos)
                     * Rama de la incapacidad acorde al manejo del SUA 1 para  RT y 2 para EG
                     * Tipo de Riesgo con la clave original
                     * Tipo de Secuela o Consecuencia con la clave original
                     * Tipo de control de incapacidad con la clave original
                     * Fecha de término de la incapacidad*/
                    return `01080836107${num_imss}0${dia}${mes}${anio}${folio}${dias_subsidiados}${porcentaje_incapacidad}${rama_incapacidad}${tipo_riesgo_incapacidad}${tipo_secuelas_consecuencias}${tipo_control_incapacidad}${diaFinal}${mesFinal}${anioFinal}`;
                }
                // Se formatean todos los elementos del Json
                let formattedData = data.map(formatElement);
                // Se unen los elementos en un texto único
                let contenido = formattedData.join('\n');
                // Crear un Blob con el contenido y tipo de texto plano
                let blob = new Blob([contenido], {type: "text/plain"});
                // Crear una URL para el Blob
                let url = URL.createObjectURL(blob);
                // Crear un enlace para descargar el archivo
                let nombreMeses = ["ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"];
                let mes = nombreMeses[fechaFin.getMonth()];
                let anio = fechaFin.getFullYear();
                let a = document.createElement("a");
                a.href = url;
                a.download = "MovSUA_" + mes + anio + ".txt";
                // Simular un clic en el enlace para iniciar la descarga
                a.click();
                // Liberar el objeto URL
                URL.revokeObjectURL(url);
            }
        },
        error: function (e) {
            alert(e);
        }
    });
};

const generarArchivoInasistenciasSUA = () => {
    let fechaInicio = $('#fechaInicial').val();
    let fechaFin = $('#fechaFinal').val();
    if ($.trim(fechaInicio) === "") {
        toastr["warning"]("La Fecha Inicial no debe estar vacía", " Aviso", {progressBar: true, closeButton: true});
        return;
    }
    if ($.trim(fechaFin) === "") {
        toastr["warning"]("La Fecha Final no debe estar vacía", " Aviso", {progressBar: true, closeButton: true});
        return;
    }

    if (fechaFin < fechaInicio) {
        toastr["warning"]("La Fecha Final debe ser mayor a la fecha inicial", " Aviso", {progressBar: true, closeButton: true});
        return;
    }

    $.ajax({
        type: "GET",
        url: "incidencias/generarInasistenciasSUA/" + fechaInicio + "/" + fechaFin,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información para las fechas ingresadas...", "Aviso", {progressBar: true, closeButton: true});
                return;
            } else {
                //Formatea el objeto del JSON
                function formatElement(element) {
                    let num_imss = element.num_imss;
                    let fecha_inicial = new Date(element.fecha_inicio);
                    // Obtiene el día, mes, año de la fecha inicial
                    let dia = fecha_inicial.getDate().toString().padStart(2, '0');
                    let mes = (fecha_inicial.getMonth() + 1).toString().padStart(2, '0');
                    let anio = fecha_inicial.getFullYear().toString();
                    let espacios_en_blanco = '        ';

                    /* Se formatea en el formato requerido acorde a documentación encontrada para la importación de Incapacidades para el SUA que es: 
                     * Registro patronal IMSS (11 Alfanumérico)
                     * Num. Seguridad Social (11 Numérico)
                     * 11 Preguntar por qué 11?
                     * Fecha Inicial de la falta (mmddaaaa)
                     * 8 espacios en blanco
                     * Cadena: 010000000 ¿Significado, cantidad de incidencias?*/
                    return `01080836107${num_imss}11${dia}${mes}${anio}${espacios_en_blanco}010000000`;
                }
                // Se formatean todos los elementos del Json
                let formattedData = data.map(formatElement);
                // Se unen los elementos en un texto único
                let contenido = formattedData.join('\n');
                // Crear un Blob con el contenido y tipo de texto plano
                let blob = new Blob([contenido], {type: "text/plain"});
                // Crear una URL para el Blob
                let url = URL.createObjectURL(blob);
                // Crear un enlace para descargar el archivo
                let a = document.createElement("a");
                a.href = url;
                a.download = "SUA_Faltas" + fechaInicio + fechaFin + ".txt";
                // Simular un clic en el enlace para iniciar la descarga
                a.click();
                // Liberar el objeto URL
                URL.revokeObjectURL(url);
            }
        },
        error: function (e) {
            alert(e);
        }
    });
};

const generarReporteSUA = () => {
    let fechaInicio = $('#fechaInicial').val();
    let fechaFin = $('#fechaFinal').val();
    console.log(fechaInicio, fechaFin);
    let url = "report/inasistenciasSUAIMSS?" +
            "fechaInicio=" + fechaInicio + "&" + // inputFechaInicialLiberacion parameter
            "fechaFin=" + fechaFin + "&" + // inputFechaFinallLiberacion parameter
            "tipo=PDF";
    ajaxVizualizacion(url);
    let nombreArchivo = 'RelacionInasistenciasSUA' + fechaInicio + '_' + fechaFin;
    llamadaAjax(url, nombreArchivo);
};

//Ajax para generar descarga
const llamadaAjax = (url, nombreArchivo) => {
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
};
//Ajax para ver en el modal
const ajaxVizualizacion = (url) => {
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
                $("#reportesSUA").modal("toggle");
                var contentType = xhr.getResponseHeader("Content-Type");
                var filename = xhr.getResponseHeader("Content-disposition");
                filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                var url = window.URL.createObjectURL(data);
                var frame = $('#suaFrame');
                var url = url;
                frame.attr('src', url).show();
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar informacion de documentos cargados: General 1", {progressBar: true, closeButton: true});
        }
    });
};