document.addEventListener('DOMContentLoaded', () => {
    cmbPuesto();
    cmbOrden();
});
//Muestra de días de la semana en el modal
const daysOfWeek = ["Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"];
const monthsOfYear = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];

$(document).ready(function () {
    //Implementación de la fecha escrita en el modal
    const currentDate = new Date();
    const dayOfWeek = daysOfWeek[currentDate.getDay()];
    const day = currentDate.getDate();
    const month = monthsOfYear[currentDate.getMonth()];
    const year = currentDate.getFullYear();
    const dateString = `${dayOfWeek}, ${day} de ${month} de ${year}`;
    $("#datetime").text(dateString);

    /*Botón Buscar destruye datatable al segundo click para evitar errores en pantalla
     Inicializa el contador en 0 */
    $('#buscar-btn').data('click-count', 0);
    // Adjunta el event handler al botón
    $('#buscar-btn').on('click', function () {
        // Inncrementa el contador de click
        let clickCount = $('#buscar-btn').data('click-count') + 1;
        $('#buscar-btn').data('click-count', clickCount);

        // Si es el segundo click o más destruye la tabla
        if (clickCount >= 2) {
            $('#tablaReporte').DataTable().destroy();
        }
        // Llamada a la función buscarParametros()
        buscarParametros();
    });

    // Referencia al botón de reportes
    var $reporteBtn = $('#reporte-btn');

    // Deshabilita el botón de reportes inicialmente
    $reporteBtn.prop('disabled', true);

    // Agrega un evento change al elemento cmbOrden
    $('#cmbOrden').on('change', function () {
        // Verifica si se ha seleccionado un valor
        if ($(this).val() !== '') {
            // Habilita el botón de reportes si se ha seleccionado un valor
            $reporteBtn.prop('disabled', false);
        } else {
            // Deshabilita el botón de reportes si no se ha seleccionado un valor
            $reporteBtn.prop('disabled', true);
        }
    });

    //Generación de reporte dependiendo de la tabla mostrada    
    $reporteBtn.on('click', function () {
        generarReporte();
    });

    //Limpieza de inputs de Plazas
    $('input[type="radio"]').click(function () {
        if ($(this).attr('id') === 'radioPlazasOcupadas') {
            $('#inputPlazasVacantes').val('');
        } else if ($(this).attr('id') === 'radioPlazasVacantes') {
            $('#inputPlazasOcupadas').val('');
        }
    });
});

/*====================================================
 FUNCIÓN GENERADORA DE REPORTES
 =====================================================*/
//La idea de esta función es que genere reportes acorde a lo mostrado en la tabla de ajax generada al dar cilck en buscar o simplemente al dar click en reporte
const generarReporte = () => {
    //Se detectan los campos seleccionados en front
    detectarInputs();

    //Se Obtiene el valor seleccionado para el orden del reporte de la variable valorSeleccionadoOrdenReporte

    //ComboBox (Puesto) y checkBoxes (Tipo de Contrato) seleccionados
    if (valorSeleccionadoPuesto && valoresCheckedTipoContrato.length > 0 && $('#radioPlazasOcupadas').is(':not(:checked)') && $('#radioPlazasVacantes').is(':not(:checked)')) {
        //URL basándonos en el orden deseado del reporte (1. Alfabética, 2. Organigrama 3. Expediente)
        let url;
        if (valorSeleccionadoOrdenReporte === "1") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoTipoContratoAlfabet?id_puesto=" + valorSeleccionadoPuesto.toString() + "&tipo_contrato_id=" + valoresCheckedTipoContrato + "&tipo=PDF";
        } else if (valorSeleccionadoOrdenReporte === "2") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoTipoContratoOrganigrama?id_puesto=" + valorSeleccionadoPuesto.toString() + "&tipo_contrato_id=" + valoresCheckedTipoContrato + "&tipo=PDF";
        } else if (valorSeleccionadoOrdenReporte === "3") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoTipoContratoExpediente?id_puesto=" + valorSeleccionadoPuesto.toString() + "&tipo_contrato_id=" + valoresCheckedTipoContrato + "&tipo=PDF";
        }

        //Llamada asíncrona a reporte puesto y Tipo de Contrato donde se recibe el id del puesto y el arreglo de tipo de contrato para generar el reporte correspondiente en PDF
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
                link.download = "ReporteMonitorPlazasAutorizadasIdPuesto_" + valorSeleccionadoPuesto.toString() + "TiposContrato_" + valoresCheckedTipoContrato.toString() + ".pdf";
                link.click();
            }, error: function (e) {
                toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
            }
        });

        //Solo el ComboBox (Puesto) está seleccionados
    } else if (valorSeleccionadoPuesto && valoresCheckedTipoContrato.length === 0 && $('#radioPlazasOcupadas').is(':not(:checked)') && $('#radioPlazasVacantes').is(':not(:checked)')) {
        //URL basándonos en el orden deseado del reporte (1. Alfabética, 2. Organigrama 3. Expediente)
        let url;
        if (valorSeleccionadoOrdenReporte === "1") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoAlfabet?id_puesto=" + valorSeleccionadoPuesto.toString() + "&tipo=PDF";
        } else if (valorSeleccionadoOrdenReporte === "2") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoOrganigrama?id_puesto=" + valorSeleccionadoPuesto.toString() + "&tipo_contrato_id=" + valoresCheckedTipoContrato + "&tipo=PDF";
        } else if (valorSeleccionadoOrdenReporte === "3") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoExpediente?id_puesto=" + valorSeleccionadoPuesto.toString() + "&tipo_contrato_id=" + valoresCheckedTipoContrato + "&tipo=PDF";
        }
        //Llamada asíncrona a reporte de puesto donde se recibe el id del puesto para generar el reporte correspondiente en PDF
        $.ajax({
            type: "GET",
            contentType: 'application/pdf',
            //Genera la búsqueda del puesto ordenando de manera alfabética
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
                link.download = "ReporteMonitorPlazasAutorizadasIdPuesto_" + valorSeleccionadoPuesto.toString() + ".pdf";
                link.click();
            }, error: function (e) {
                toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
            }
        });

        //Solo los Checkboxes (Tipo de contrato) están seleccionados
    } else if (!valorSeleccionadoPuesto && valoresCheckedTipoContrato.length > 0 && $('#radioPlazasOcupadas').is(':not(:checked)') && $('#radioPlazasVacantes').is(':not(:checked)')) {
        //URL basándonos en el orden deseado del reporte (1. Alfabética, 2. Organigrama 3. Expediente)
        let url;
        if (valorSeleccionadoOrdenReporte === "1") {
            url = "report/monitorPlazasAutorizadas/descargaTipoContratoAlfabet?tipo_contrato_id=" + valoresCheckedTipoContrato + "&tipo=PDF";
        } else if (valorSeleccionadoOrdenReporte === "2") {
            url = "report/monitorPlazasAutorizadas/descargaTipoContratoOrganigrama?id_puesto=" + valorSeleccionadoPuesto.toString() + "&tipo_contrato_id=" + valoresCheckedTipoContrato + "&tipo=PDF";
        } else if (valorSeleccionadoOrdenReporte === "3") {
            url = "report/monitorPlazasAutorizadas/descargaTipoContratoExpediente?id_puesto=" + valorSeleccionadoPuesto.toString() + "&tipo_contrato_id=" + valoresCheckedTipoContrato + "&tipo=PDF";
        }
        //Llamada asíncrona a reporte Tipo de contrato donde se reciben los id´s del tipo de contrato para generar el reporte correspondiente en PDF
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
                link.download = "ReporteMonitorPlazasAutorizadasTipoContrato" + valoresCheckedTipoContrato.toString() + ".pdf";
                link.click();
            }, error: function (e) {
                toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
            }
        });
    }

    /*******************************
     Casos de radio buttons
     ******************************/
    //Condicional de Puesto y Radio plazas ocupadas seleccionado
    if (valorSeleccionadoPuesto && valoresCheckedTipoContrato.length === 0 && $('#radioPlazasOcupadas').is(':checked')) {
        //URL basándonos en el orden deseado del reporte (1. Alfabética, 2. Organigrama 3. Expediente)
        let url;
        if (valorSeleccionadoOrdenReporte === "1") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoEstatusAlfabet?id_puesto=" + valorSeleccionadoPuesto.toString() + "&estatus_plaza_id=" + estatusPlazasOcupadas.toString() + "&tipo=PDF";
        } else if (valorSeleccionadoOrdenReporte === "2") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoEstatusOrganigrama?id_puesto=" + valorSeleccionadoPuesto.toString() + "&estatus_plaza_id=" + estatusPlazasOcupadas.toString() + "&tipo=PDF";
        } else if (valorSeleccionadoOrdenReporte === "3") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoEstatusExpediente?id_puesto=" + valorSeleccionadoPuesto.toString() + "&estatus_plaza_id=" + estatusPlazasOcupadas.toString() + "&tipo=PDF";
        }
        //Llamada asíncrona a reporte Puesto y Estatus plazas donde se recibe el id del puesto y el estatus de la plaza "asignada" (1) para generar el reporte correspondiente en PDF
        $.ajax({
            type: "GET",
            contentType: 'application/pdf',
            //Genera la búsqueda del puesto con tipo de contrato ordenando de manera alfabética con criterio Nombre del trabajador
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
                link.download = "ReporteMonitorPlazasAutorizadasIdPuesto_" + valorSeleccionadoPuesto.toString() + "EstatusPlaza_" + estatusPlazasOcupadas.toString() + ".pdf";
                link.click();
            }, error: function (e) {
                toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
            }
        });
    }

    //Condicional de Puesto y Radio plazas Vacantes seleccionado
    if (valorSeleccionadoPuesto && valoresCheckedTipoContrato.length === 0 && $('#radioPlazasVacantes').is(':checked')) {
        //URL basándonos en el orden deseado del reporte (1. Alfabética, 2. Organigrama 3. Expediente)
        let url;
        if (valorSeleccionadoOrdenReporte === "1") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoEstatusAlfabet?id_puesto=" + valorSeleccionadoPuesto.toString() + "&estatus_plaza_id=" + estatusPlazasVacantes.toString() + "&tipo=PDF";
        } else if (valorSeleccionadoOrdenReporte === "2") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoEstatusOrganigrama?id_puesto=" + valorSeleccionadoPuesto.toString() + "&estatus_plaza_id=" + estatusPlazasVacantes.toString() + "&tipo=PDF";
        } else if (valorSeleccionadoOrdenReporte === "3") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoEstatusExpediente?id_puesto=" + valorSeleccionadoPuesto.toString() + "&estatus_plaza_id=" + estatusPlazasVacantes.toString() + "&tipo=PDF";
        }
        //Llamada asíncrona a reporte Puesto y Estatus plazas donde se recibe el id del puesto y el estatus de la plaza "disponible" (3) para generar el reporte correspondiente en PDF
        $.ajax({
            type: "GET",
            contentType: 'application/pdf',
            //Genera la búsqueda del puesto con tipo de contrato ordenando de manera alfabética con criterio Nombre del trabajador
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
                link.download = "ReporteMonitorPlazasAutorizadasIdPuesto_" + valorSeleccionadoPuesto.toString() + "EstatusPlaza_" + estatusPlazasVacantes.toString() + ".pdf";
                link.click();
            }, error: function (e) {
                toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
            }
        });
    }

    //Condicional de Puesto, Tipo de Contrato y Plazas ocupadas seleccionados
    if (valorSeleccionadoPuesto && valoresCheckedTipoContrato.length > 0 && $('#radioPlazasOcupadas').is(':checked')) {
        //URL basándonos en el orden deseado del reporte (1. Alfabética, 2. Organigrama 3. Expediente)
        let url;
        if (valorSeleccionadoOrdenReporte === "1") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoTipoContratoEstatusAlfabet?id_puesto=" + valorSeleccionadoPuesto.toString() + "&tipo_contrato_id=" + valoresCheckedTipoContrato + "&estatus_plaza_id=" + estatusPlazasOcupadas.toString() + "&tipo=PDF";
        } else if (valorSeleccionadoOrdenReporte === "2") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoTipoContratoEstatusOrganigrama?id_puesto=" + valorSeleccionadoPuesto.toString() + "&tipo_contrato_id=" + valoresCheckedTipoContrato + "&estatus_plaza_id=" + estatusPlazasOcupadas.toString() + "&tipo=PDF";
        } else if (valorSeleccionadoOrdenReporte === "3") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoTipoContratoEstatusExpediente?id_puesto=" + valorSeleccionadoPuesto.toString() + "&tipo_contrato_id=" + valoresCheckedTipoContrato + "&estatus_plaza_id=" + estatusPlazasOcupadas.toString() + "&tipo=PDF";
        }
        //Llamada asíncrona a reporte Puesto, Tipo de contrato y Estatus plazas donde se recibe el id del puesto y el estatus de la plaza "asignadas" (1) para generar el reporte correspondiente en PDF
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
                link.download = "ReporteMonitorPlazasAutorizadasIdPuesto_" + valorSeleccionadoPuesto.toString() + "TiposContrato_" + valoresCheckedTipoContrato.toString() + "EstatusPlaza_" + estatusPlazasOcupadas + ".pdf";
                link.click();
            }, error: function (e) {
                toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
            }
        });
    }

    //Condicional de Puesto, Tipo de Contrato y Plazas Vacantes Seleccionadas
    if (valorSeleccionadoPuesto && valoresCheckedTipoContrato.length > 0 && $('#radioPlazasVacantes').is(':checked')) {
        //URL basándonos en el orden deseado del reporte (1. Alfabética, 2. Organigrama 3. Expediente)
        let url;
        if (valorSeleccionadoOrdenReporte === "1") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoTipoContratoEstatusAlfabet?id_puesto=" + valorSeleccionadoPuesto.toString() + "&tipo_contrato_id=" + valoresCheckedTipoContrato + "&estatus_plaza_id=" + estatusPlazasVacantes.toString() + "&tipo=PDF";
        } else if (valorSeleccionadoOrdenReporte === "2") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoTipoContratoEstatusOrganigrama?id_puesto=" + valorSeleccionadoPuesto.toString() + "&tipo_contrato_id=" + valoresCheckedTipoContrato + "&estatus_plaza_id=" + estatusPlazasVacantes.toString() + "&tipo=PDF";
        } else if (valorSeleccionadoOrdenReporte === "3") {
            url = "report/monitorPlazasAutorizadas/descargaPuestoTipoContratoEstatusExpediente?id_puesto=" + valorSeleccionadoPuesto.toString() + "&tipo_contrato_id=" + valoresCheckedTipoContrato + "&estatus_plaza_id=" + estatusPlazasVacantes.toString() + "&tipo=PDF";
        }
        //Llamada asíncrona a reporte Puesto, Tipo de contrato y Estatus plazas donde se recibe el id del puesto y el estatus de la plaza "disponible" (3) para generar el reporte correspondiente en PDF
        $.ajax({
            type: "GET",
            contentType: 'application/pdf',
            //Genera la búsqueda del puesto con tipo de contrato ordenando de manera alfabética con criterio Nombre del trabajador
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
                link.download = "ReporteMonitorPlazasAutorizadasIdPuesto_" + valorSeleccionadoPuesto.toString() + "TiposContrato_" + valoresCheckedTipoContrato.toString() + "EstatusPlaza_" + estatusPlazasVacantes + ".pdf";
                link.click();
            }, error: function (e) {
                toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
            }
        });
    }

};

/*=============================================
 SELECCIÓN DE TIPO DE CONTRATO
 =============================================*/
//OJO, selecciona todos los  input de tipo checkbox
const seleccionarTodos = () => {
    var checkboxes = document.querySelectorAll('input[type=checkbox]');
    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].checked = true;
    }
};

const deseleccionarTodos = () => {
    var checkboxes = document.querySelectorAll('input[type=checkbox]');
    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].checked = false;
    }
};

/*=============================================
 DISPLAY DE COMBOS PUESTO Y ORDEN
 =============================================*/
//Coloca los puestos dependiendo de los valores provenientes de la base de datos
const cmbPuesto = (id) => {
    $.ajax({
        type: "GET",
        url: "catalogos/listarDatosCatPuesto",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {

                $('#cmbPuesto').empty().append("<option value=''>* Puesto</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_puesto === id) ? vselected = "selected" : vselected = "----";
                        $('#cmbPuesto').append('<option value="' + data[x].id_puesto + '"' + vselected + '>' + data[x].puesto + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Puesto : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};

const cmbOrden = (id) => {
    $.ajax({
        type: "GET",
        url: "catalogos/listarDatosOrdenReporteMonitor",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {

                $('#cmbOrden').empty().append("<option value=''>* Orden</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_reporte_monitor === id) ? vselected = "selected" : vselected = "----";
                        $('#cmbOrden').append('<option value="' + data[x].id_reporte_monitor + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};
/*===================================================================================
 VARIABLES QUE CONTIENEN EL PUESTO Y TIPOS DE CONTRATO Y ORDEN DEL REPORTE A GENERAR
 ====================================================================================*/
//Variable que contiene el puesto seleccionado del combo
let valorSeleccionadoPuesto;
//Arreglo que contiene el/los tipos de contrato que se seleccionen
let valoresCheckedTipoContrato;
//Variable que contiene el orden con el que se desea generar el reporte
let valorSeleccionadoOrdenReporte;

const detectarInputs = () => {
    //Conseguir el ID del combo seleccionada
    valorSeleccionadoPuesto = $('#cmbPuesto').val();
    //Conseguir el ID del orden selecionado
    valorSeleccionadoOrdenReporte = $('#cmbOrden').val();
    //Arreglo para listado de selección de checkbox´s que contiene el valor del ID del tipo de contrato
    valoresCheckedTipoContrato = [];
    //Revisión de checkbox Seleccionado
    $('input[type=checkbox]:checked').each(function () {
        valoresCheckedTipoContrato.push($(this).val());
    });
};
/*====================================================
 BÚSQUEDA DE PARÁMETROS DEL PUESTO Y TIPO DE CONTRATO
 =====================================================*/
//Constantes correspondientes a los estatus de la plaza en la Base de Datos
const estatusPlazasOcupadas = 1; //1 para asignado
const estatusPlazasVacantes = 3; //3 para disponible

const buscarParametros = () => {
    //Detección de casos para asignar la tabla correspondiente
    detectarInputs();
    //ComboBox (Puesto) y checkBoxes (Tipo de Contrato) seleccionados
    if (valorSeleccionadoPuesto && valoresCheckedTipoContrato.length > 0 && $('#radioPlazasOcupadas').is(':not(:checked)') && $('#radioPlazasVacantes').is(':not(:checked)')) {
        // URL base 
        const baseUrl = "plaza/tipoDeContratoAndPuesto/";

        //Definición de query dinámicos como un objeto
        const queryParams = {
            puestoIds: [valorSeleccionadoPuesto],
            tipoContratoIds: valoresCheckedTipoContrato
        };

        //Conversión de querys a Strings
        const queryString = Object.entries(queryParams)
                .map(([key, values]) => values.map(value => `${key}=${value}`).join("&"))
                .join("&");

        //Combinación de URL Base con los querys
        const url = `${baseUrl}?${queryString}`;

        //AJAX
        tablaReporte = $('#tablaReporte').dataTable({
            "ajax": {
                url: url,
                method: 'GET',
                dataSrc: '',
                error: function (xhr, error, thrown) {
                    // Manejo de error en caso de no encontrar datos válidos
                    toastr["warning"]("No se encontraron registros con estos parámetros de búsqueda", {progressBar: true, closeButton: true});
                }
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
                //"searchPlaceholder": "Buscar",
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
                {data: "tipoNomina"},
                {data: "numeroPlaza"},
                {data: "nivelDto"},
                {data: "puestoDto"},
                {data: "numeroExpediente",
                    defaultContent: ""},
                {data: "", sTitle: "Nombre del Trabajador", width: 100, visible: true, render: function (d, t, r) {
                        var he;
                        he = r.nombreDto + ' ' + r.apellidoPaterno + ' ' + r.apellidoMaterno;
                        return he;
                    }
                }
            ]
        });

        //Solo el ComboBox (Puesto) está seleccionados
    } else if (valorSeleccionadoPuesto && valoresCheckedTipoContrato.length === 0 && $('#radioPlazasOcupadas').is(':not(:checked)') && $('#radioPlazasVacantes').is(':not(:checked)')) {
        //AJAX
        tablaReporte = $('#tablaReporte').dataTable({
            "ajax": {
                url: "plaza/buscarPorPuesto/{puestoId}?puesto_id=" + valorSeleccionadoPuesto,
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
                //"searchPlaceholder": "Buscar",
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
                {data: "tipoNomina"},
                {data: "numeroPlaza"},
                {data: "nivelDto"},
                {data: "puestoDto"},
                {data: "numeroExpediente",
                    defaultContent: ""},
                {data: "", sTitle: "Nombre del Trabajador", width: 100, visible: true, render: function (d, t, r) {
                        var he;
                        he = r.nombreDto + ' ' + r.apellidoPaterno + ' ' + r.apellidoMaterno;
                        return he;
                    }
                }
            ]
        });

        //Solo los Checkboxes (Tipo de contrato) están seleccionados
    } else if (!valorSeleccionadoPuesto && valoresCheckedTipoContrato.length > 0 && $('#radioPlazasOcupadas').is(':not(:checked)') && $('#radioPlazasVacantes').is(':not(:checked)')) {

        /*En este caso se debe de detectar los checkbox seleccionados para poder añadirlos al parámetro de búsqueda, es por eso que se crea una URL "dinámica" que 
         irá agregando acorde a los checkbox seleccionados los parámetros correspondientres al ID del Tipo de Contrato */

        // URL base 
        const baseUrl = "plaza/tiposDecontratos/";

        //Definición de query dinámicos como un objeto
        const queryParams = {
            tipoContratoIds: valoresCheckedTipoContrato
        };

        //Conversión de querys a Strings
        const queryString = Object.entries(queryParams)
                .map(([key, values]) => values.map(value => `${key}=${value}`).join("&"))
                .join("&");

        //Combinación de URL Base con los querys
        const url = `${baseUrl}?${queryString}`;

        //AJAX
        tablaReporte = $('#tablaReporte').dataTable({
            "ajax": {
                //Se pasa la URL creada de manera dinámica
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
                //"searchPlaceholder": "Buscar",
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
                {data: "tipoNomina"},
                {data: "numeroPlaza"},
                {data: "nivelDto"},
                {data: "puestoDto"},
                {data: "numeroExpediente",
                    defaultContent: ""},
                {data: "", sTitle: "Nombre del Trabajador", width: 100, visible: true, render: function (d, t, r) {
                        var he;
                        he = r.nombreDto + ' ' + r.apellidoPaterno + ' ' + r.apellidoMaterno;
                        return he;
                    }
                }
            ]
        });

    }

    //Condicional de Puesto y Radio plazas ocupadas seleccionado
    if (valorSeleccionadoPuesto && valoresCheckedTipoContrato.length === 0 && $('#radioPlazasOcupadas').is(':checked')) {
        /*AJAX Muestra de Plazas ocupadas
         Colocación de número de plazas ocupadas en el input*/
        $.ajax({
            type: "GET",
            url: "catalogos/contarPlazasAsignadas/" + valorSeleccionadoPuesto,
            dataType: "json",
            success: (data) => {

                // Cache Selectors
                var inputPlazasOcupadas = $("#inputPlazasOcupadas");
                inputPlazasOcupadas.val(data.data);
            },
            error: (e) => {
                alert(e);
            }
        });

        //AJAX
        tablaReporte = $('#tablaReporte').dataTable({
            "ajax": {
                url: "plaza/buscarPorPuestoYEstatus/{puestoId}/{estatusPlazaId}?estatus_plaza_id=" + estatusPlazasOcupadas + "&puesto_id=" + valorSeleccionadoPuesto,
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
                //"searchPlaceholder": "Buscar",
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
                {data: "tipoNomina"},
                {data: "numeroPlaza"},
                {data: "nivelDto"},
                {data: "puestoDto"},
                {data: "numeroExpediente",
                    defaultContent: ""},
                {data: "", sTitle: "Nombre del Trabajador", width: 100, visible: true, render: function (d, t, r) {
                        var he;
                        he = r.nombreDto + ' ' + r.apellidoPaterno + ' ' + r.apellidoMaterno;
                        return he;
                    }
                }
            ]
        });
    }

    //Condicional de Puesto y Radio plazas Vacantes seleccionado
    if (valorSeleccionadoPuesto && valoresCheckedTipoContrato.length === 0 && $('#radioPlazasVacantes').is(':checked')) {
        //Conteo de plazas vacantes acorde al puesto
        $.ajax({
            type: "GET",
            url: "catalogos/contarPlazasDisponibles/" + valorSeleccionadoPuesto,
            dataType: "json",
            success: (data) => {

                // Cache Selectors
                var inputPlazasVacantes = $("#inputPlazasVacantes");
                inputPlazasVacantes.val(data.data);
            },
            error: (e) => {
                alert(e);
            }
        });

        tablaReporte = $('#tablaReporte').dataTable({
            "ajax": {
                url: "/plaza/buscarPorPuestoYEstatus/{puestoId}/{estatusPlazaId}?estatus_plaza_id=" + estatusPlazasVacantes + "&puesto_id=" + valorSeleccionadoPuesto,
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
                //"searchPlaceholder": "Buscar",
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
                {data: "tipoNomina"},
                {data: "numeroPlaza"},
                {data: "nivelDto"},
                {data: "puestoDto"},
                {data: "numeroExpediente",
                    defaultContent: ""},
                {data: "", sTitle: "Nombre del Trabajador", width: 100, visible: true, render: function (d, t, r) {
                        var he;
                        he = r.nombreDto + ' ' + r.apellidoPaterno + ' ' + r.apellidoMaterno;
                        return he;
                    }
                }
            ]
        });

    }

    //Condicional de Puesto, Tipo de Contrato y Plazas ocupadas seleccionados
    if (valorSeleccionadoPuesto && valoresCheckedTipoContrato.length > 0 && $('#radioPlazasOcupadas').is(':checked')) {

        //Conteo de plazas vacantes acorde al Puesto, Tipo de Contrato y Plazas Ocupadas
        $.ajax({
            type: "GET",
            url: "catalogos/contarPlazasDisponibles/" + valorSeleccionadoPuesto,
            dataType: "json",
            success: (data) => {

                // Cache Selectors
                var inputPlazasOcupadas = $("#inputPlazasOcupadas");
                inputPlazasOcupadas.val(data.data);
            },
            error: (e) => {
                alert(e);
            }
        });

        // URL base para Plazas, Puesto y Estatus
        const baseUrl = "plaza/buscarEstatusPuestoPlaza?estatus_plaza_id=" + estatusPlazasOcupadas + "&puesto_id=" + valorSeleccionadoPuesto + "&";

        //Definición de query dinámicos como un objeto
        const parametrosQuery = {
            tipoContratoId: valoresCheckedTipoContrato
        };

        //Conversión de querys a Strings
        const cadenaQuery = Object.entries(parametrosQuery)
                .map(([key, values]) => values.map(value => `${key}=${value}`).join("&"))
                .join("&");

        const urlAjax = `${baseUrl}${cadenaQuery}`;

        tablaReporte = $('#tablaReporte').dataTable({
            "ajax": {
                url: urlAjax,
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
                //"searchPlaceholder": "Buscar",
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
                {data: "tipoNomina"},
                {data: "numeroPlaza"},
                {data: "nivelDto"},
                {data: "puestoDto"},
                {data: "numeroExpediente",
                    defaultContent: ""},
                {data: "", sTitle: "Nombre del Trabajador", width: 100, visible: true, render: function (d, t, r) {
                        var he;
                        he = r.nombreDto + ' ' + r.apellidoPaterno + ' ' + r.apellidoMaterno;
                        return he;
                    }
                }
            ]
        });

    }

    //Condicional de Puesto, Tipo de Contrato y Plazas Vacantes Seleccionadas
    if (valorSeleccionadoPuesto && valoresCheckedTipoContrato.length > 0 && $('#radioPlazasVacantes').is(':checked')) {
        //Conteo de plazas vacantes acorde al Puesto, Tipo de Contrato y Plazas Ocupadas
        $.ajax({
            type: "GET",
            url: "catalogos/contarPlazasDisponibles/" + valorSeleccionadoPuesto,
            dataType: "json",
            success: (data) => {

                // Cache Selectors
                var inputPlazasVacantes = $("#inputPlazasVacantes");
                inputPlazasVacantes.val(data.data);

            },
            error: (e) => {
                alert(e);
            }
        });

        // URL base para Plazas, Puesto y Estatus
        const baseUrl = "plaza/buscarEstatusPuestoPlaza?estatus_plaza_id=" + estatusPlazasVacantes + "&puesto_id=" + valorSeleccionadoPuesto + "&";

        //Definición de query dinámicos como un objeto
        const parametrosQuery = {
            tipoContratoId: valoresCheckedTipoContrato
        };

        //Conversión de querys a Strings
        const cadenaQuery = Object.entries(parametrosQuery)
                .map(([key, values]) => values.map(value => `${key}=${value}`).join("&"))
                .join("&");

        const urlAjax = `${baseUrl}${cadenaQuery}`;

        tablaReporte = $('#tablaReporte').dataTable({
            "ajax": {
                url: urlAjax,
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
                //"searchPlaceholder": "Buscar",
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
                {data: "tipoNomina"},
                {data: "numeroPlaza"},
                {data: "nivelDto"},
                {data: "puestoDto"},
                {data: "numeroExpediente",
                    defaultContent: ""},
                {data: "", sTitle: "Nombre del Trabajador", width: 100, visible: true, render: function (d, t, r) {
                        var he;
                        he = r.nombreDto + ' ' + r.apellidoPaterno + ' ' + r.apellidoMaterno;
                        return he;
                    }
                }
            ]
        });

    }
};