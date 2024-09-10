$(document).ready(function () {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParametros = new URLSearchParams(d);
    let id_trabajador = urlParametros.get('id_trabajador');
    fechasDefault(id_trabajador);
    opcionesAdicionales();
    marcarTodosChkBox();
    recuperaDatosPlazaTrabajador(id_trabajador);
});
//Variables globales
const percepcionesMap = {
    'chkIndemnizacion': 'total_indemnizacion',
    'chkVeniteDiasAnio': 'total_veinte_dias_anio',
    'chkPrimaAntiguedad': 'total_prima_antiguedad',
    'chkComPorAnt': 'total_comp_antiguedad',
    'chkDiasVacaciones': 'total_vacaciones',
    'chkPrimaVacacional': 'total_prima_vacacional',
    'chkAguinaldo': 'total_aguinaldo'
};

const deduccionesMap = {
    'chkIsrIndemnizacion': 'isr_indemnizacion',
    'chkIsrFiniquito': 'isr_finiquito'
};

function fechasDefault(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            let fecha = new Date(data.data.fecha_ingreso);
            let formattedFecha = fecha.toISOString().split('T')[0];
            $('#fecha_ingreso').val(formattedFecha);
            let fecha_hoy = new Date();
            let formattedFechaHoy = fecha_hoy.toISOString().split('T')[0];
            $('#fecha_baja').val(formattedFechaHoy);
            //REcuperando información para modal de detalle
            $('#campo_expediente').val(data.data.cat_expediente.numero_expediente);
            $('#campo_nombre').val(data.data.persona.apellido_paterno + " " + data.data.persona.apellido_materno + " " + data.data.persona.nombre);
            $('#campo_rfc').val(data.data.persona.rfc);
            $('#campo_num_imss').val(data.data.persona.num_imss);
            $('#campo_fecha_nacimiento').val(data.data.persona.fecha_nacimiento);
            $('#campo_prestaciones_si_no').val(data.data.prestaciones_si_no === 1 ? 'Si' : 'No');
            calculoFechas(fecha_hoy, fecha);
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al recuperar los datos del trabajador", "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Recupera la información relevante de la plaza del trabajador
function recuperaDatosPlazaTrabajador(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "plaza/buscarPlazaTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            $('#campo_adscripcion').val(data[0].descAreaDto);
            $('#campo_puesto').val(data[0].puestoDto);
            $('#campo_nomina').val(data[0].tipoNomina);
            $('#idNomina').val(data[0].idTipoNomina);
            recuperarSalarioMinimoFiniquitos();
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al recuperar los datos de la plaza del trabajador", "Error", {progressBar: true, closeButton: true});
        }
    });
}

//Recupera del catalogo de incidencias el valor variable de la incidencia con id 217 que corresponde al concepto 'Salario Mínimo Finiquitos'
function recuperarSalarioMinimoFiniquitos() {
    let idTipoNomina = $('#idNomina').val();
    $.ajax({
        type: "GET",
        url: "propiedadesNomina/findSalMinFiniqPropiedad/" + idTipoNomina,
        dataType: 'json',
        success: function (data) {
            $('#campo_salario_minimo').val('$' + data);
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al recuperar el salario mínimo para finiquito", "Error", {progressBar: true, closeButton: true});
        }
    });
}

function reajustaAntiguedad() {
    let fecha_ingreso_val = $('#fecha_ingreso').val();
    let fecha_baja_val = $('#fecha_baja').val();
    if (!fecha_ingreso_val || !fecha_baja_val) {
        $('label[for="anios"]').text(`Años:`);
        $('label[for="meses"]').text(`Meses:`);
        $('label[for="dias"]').text(`Días:`);
        return;
    }
    let fecha_ingreso = new Date(fecha_ingreso_val);
    let fecha_baja = new Date(fecha_baja_val);
    calculoFechas(fecha_baja, fecha_ingreso);
}


function calculoFechas(fecha_baja, fecha_ingreso) {
    let tiempoTranscurrido = fecha_baja.getTime() - fecha_ingreso.getTime();
    let milisegundosPorDia = 1000 * 60 * 60 * 24;
    let milisegundosPorMes = milisegundosPorDia * 30.4375;
    let milisegundosPorAnio = milisegundosPorDia * 365.25;

    let anios = Math.floor(tiempoTranscurrido / milisegundosPorAnio);
    let tiempoRestante = tiempoTranscurrido % milisegundosPorAnio;
    let meses = Math.floor(tiempoRestante / milisegundosPorMes);
    tiempoRestante = tiempoRestante % milisegundosPorMes;
    let dias = Math.floor(tiempoRestante / milisegundosPorDia);
    $('label[for="anios"]').text(`Años: ${anios}`);
    $('label[for="meses"]').text(`Meses: ${meses}`);
    $('label[for="dias"]').text(`Días: ${dias}`);
}
//Se genera para el tipo finiquito y liquidación, es el único que tiene opciones adicionales
function opcionesAdicionales() {
    // Obtener la referencia al input 
    let opcion1 = $('input[value="opcion1"]');
    let opcion2 = $('input[value="opcion2"]');
    let opcion3 = $('input[value="opcion3"]');

    let opcion4 = $('input[value="opcion4"]');
    let opcion5 = $('input[value="opcion5"]');
    let opcion6 = $('input[value="opcion6"]');

    let opcionesAdicionales = $('#opcionesAdicionales');

    opcion1.on('change', function () {
        if (opcion1.is(':checked')) {
            opcionesAdicionales.show();
            //Se desmarcan si fueron marcadas anteriormente
            resetOpcionesAdicionales(opcion4, opcion5, opcion6);
        }
    });

    opcion2.on('change', function () {
        if (opcion2.is(':checked')) {
            opcionesAdicionales.hide();
            //Se desmarcan si fueron marcadas anteriormente
            resetOpcionesAdicionales(opcion4, opcion5, opcion6);
        }
    });

    opcion3.on('change', function () {
        if (opcion3.is(':checked')) {
            opcionesAdicionales.hide();
            //Se desmarcan si fueron marcadas anteriormente
            resetOpcionesAdicionales(opcion4, opcion5, opcion6);

        }
    });
}
//Marca todos los checkbox con los conceptos a considerar en el cálculo por separación
function marcarTodosChkBox() {
    $('input[type="checkbox"]').prop('checked', true);
}
//Hace reset de las opciones adicionales a finiquito 
function resetOpcionesAdicionales(renunVol, recisionCont, remoCarg) {
    renunVol.prop('checked', false);
    recisionCont.prop('checked', false);
    remoCarg.prop('checked', false);
}

//Submit al detalle de finiquito
$("#formCalculo").submit(function (e) {
    e.preventDefault();
    let fechaIngreso = $('#fecha_ingreso').val();
    let fechaBaja = $('#fecha_baja').val();
    let finiquitoChecked = false;
    let jubilacionChecked = false;
    let pagoMarchaChecked = false;
    let finiqRenunciaVol = false;
    let finiqRecisionContrato = false;
    let finiqRemocionCargo = false;

    if (!fechaIngreso || !fechaBaja) {
        toastr.warning('Las fechas no deben estar vacías', 'Advertencia');
        return;
    }

    $('input[type="radio"]').each(function () {
        switch ($(this).attr('id')) {
            case 'finiquito':
                finiquitoChecked = $(this).prop('checked');
                break;
            case 'jubilacion':
                jubilacionChecked = $(this).prop('checked');
                break;
            case 'pagoMarcha':
                pagoMarchaChecked = $(this).prop('checked');
                break;
            case 'finiqRenunciaVol':
                finiqRenunciaVol = $(this).prop('checked');
                break;
            case 'finiqRecisionContrato':
                finiqRecisionContrato = $(this).prop('checked');
                break;
            case 'finiqRemocionCargo':
                finiqRemocionCargo = $(this).prop('checked');
                break;
        }
    });
    // Verificar si solo está seleccionado jubilación o pago marcha
    if ((jubilacionChecked && !pagoMarchaChecked) || (!jubilacionChecked && pagoMarchaChecked)) {
        // Mostrar el modal
        $("#detalleSeparacionModal").modal('show');
        mostrarModalConDetalles(jubilacionChecked, pagoMarchaChecked, finiquitoChecked, finiqRenunciaVol, finiqRecisionContrato, finiqRemocionCargo);
    } else if (finiquitoChecked) {
        // Verificar si finiquito está seleccionado y al menos uno de los elementos adicionales también lo está
        if (finiqRenunciaVol || finiqRecisionContrato || finiqRemocionCargo) {
            // Mostrar el modal
            $("#detalleSeparacionModal").modal('show');
            mostrarModalConDetalles(jubilacionChecked, pagoMarchaChecked, finiquitoChecked, finiqRenunciaVol, finiqRecisionContrato, finiqRemocionCargo);
        } else {
            toastr.warning('Por favor, selecciona el tipo de finiquito y liquidación', 'Advertencia');
        }
    } else {
        toastr.error('Por favor, selecciona el tipo de cálculo a realizar', 'Error');
    }
});

function mostrarModalConDetalles(jubilacionChecked, pagoMarchaChecked, finiquitoChecked, finiqRenunciaVol, finiqRecisionContrato, finiqRemocionCargo) {
    // Mostrar el modal
    $("#detalleSeparacionModal").modal('show');
    // Coloca el texto en el título del modal para indicar la operación que se estará realizando
    colocarTipoCalculoTituloModal(jubilacionChecked, pagoMarchaChecked, finiqRenunciaVol, finiqRecisionContrato, finiqRemocionCargo);
    // Se colocan los booleans en inputs para enviar el tipo de cálculo a realizar al backend 
    colocarBooleanosEnInput(finiquitoChecked, jubilacionChecked, pagoMarchaChecked, finiqRenunciaVol, finiqRecisionContrato, finiqRemocionCargo);
    // Recuperar información para el modal
    recuperarInformacionModal();
}

// Función para colocar el tipo de cálculo en el título del modal
function colocarTipoCalculoTituloModal(jubilacionChecked, pagoMarchaChecked,
        finiqRenunciaVol, finiqRecisionContrato, finiqRemocionCargo) {
    let modalTitle = $('#tituloModal');
    modalTitle.empty(); // Limpiar el contenido del título antes de agregar texto adicional
    if (jubilacionChecked) {
        $('#tituloModal').text('Jubilacion');
        //Para la renuncia jubilación no se consideran estos conceptos
        $('#chkIndemnizacion').prop('checked', false);
        $('#chkVeniteDiasAnio').prop('checked', false);
    }
    if (pagoMarchaChecked) {
        $('#tituloModal').text('Pago Marcha/Fallecimiento');
    }
    if (finiqRenunciaVol) {
        $('#tituloModal').text('Renuncia Voluntaria');
        //Para la renuncia voluntaria no se consideran estos conceptos
        $('#chkIndemnizacion').prop('checked', false);
        $('#chkVeniteDiasAnio').prop('checked', false);
        $('#chkPrimaAntiguedad').prop('checked', false);
    }
    if (finiqRecisionContrato) {
        $('#tituloModal').text('Recisión de Contrato');
    }
    if (finiqRemocionCargo) {
        $('#tituloModal').text('Remoción de Cargo');
    }
}
//Se colocan los booleanos para indicar que cálculo se va a realizar, estos serán utilizados en el backend
function colocarBooleanosEnInput(finiquitoChecked, jubilacionChecked, pagoMarchaChecked,
        finiqRenunciaVol, finiqRecisionContrato, finiqRemocionCargo) {
    $('#boolFiniquito').val(finiquitoChecked);
    $('#boolJubilacion').val(jubilacionChecked);
    $('#boolPagoMarcha').val(pagoMarchaChecked);
    $('#boolFiniqRenuncia').val(finiqRenunciaVol);
    $('#boolFiniqRecision').val(finiqRecisionContrato);
    $('#boolFiniqRemocion').val(finiqRemocionCargo);
}

function recuperarInformacionModal() {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParametros = new URLSearchParams(d);
    let id_trabajador = urlParametros.get('id_trabajador');
    //Booleanos para determinar el tipo de cálculo a realizar
    let finiquito = $('#boolFiniquito').val();
    let jubilacion = $('#boolJubilacion').val();
    let pagoMarcha = $('#boolPagoMarcha').val();
    let finiqRenuncuia = $('#boolFiniqRenuncia').val();
    let finiqRecision = $('#boolFiniqRecision').val();
    let finiqRemocionCargo = $('#boolFiniqRemocion').val();
    let textoAnios = $('label[for="anios"]').text();
    let textoMeses = $('label[for="meses"]').text();
    let textoDias = $('label[for="dias"]').text();
    // Extraer solo los números de los textos
    let valorAnios = parseFloat(textoAnios.match(/\d+/)[0]);
    let valorMeses = parseFloat(textoMeses.match(/\d+/)[0]);
    let valorDias = parseFloat(textoDias.match(/\d+/)[0]);
    let fechaIngreso = $('#fecha_ingreso').val();
    let fechaBaja = $('#fecha_baja').val();
    $.ajax({
        type: "GET",
        url: "calculoSeparacion/calculoFiniquito/" + id_trabajador + "/" + finiquito + "/" + jubilacion + "/" + pagoMarcha + "/" + finiqRenuncuia + "/" + finiqRecision + "/" + finiqRemocionCargo + "/" + valorAnios + "/" + valorMeses + "/" + valorDias + "/" + fechaIngreso + "/" + fechaBaja,
        dataType: 'json',
        success: function (data) {
            //Datos relacionados al trabajador
            $('#campo_sueldo_diario_tab').val(formateaMoneda(data.sueldoDiarioTabulado));
            $('#campo_sueldo_mensual_tab').val(formateaMoneda(data.sueldoMensualTabulado));
            $('#campo_salario_integrado_diario').val(formateaMoneda(data.salarioDiarioIntegrado));
            $('#campo_salario_integrado_mensual').val(formateaMoneda(data.salarioMensualIntegrado));
            //Datos relacionados al cálculo realizado en el backend
            $("#meses_indemnizacion").text(data.mesesIndemnizacion);
            $('#dias_veinte_dias_anio').text(data.veinteDiasAnio);
            $('#dias_prima_antiguedad').text(data.diasPrimaAntiguedad);
            $('#meses_comp_antiguedad').text(data.mesesCompensacionAntiguedad);
            $('#dias_vacaciones').text(data.diasVacaciones.toFixed(2));
            $('#dias_prima_vacacional').text(data.diasPrimaVacacional.toFixed(2));
            $('#periodo_vacaciones').text(data.periodoVacacionalStr);
            $('#periodo_prima_vacacional').text(data.periodoVacacionalStr);
            $('#periodo_aguinaldo').text(data.periodoAguinaldoAnio);
            $('#dias_aguinaldo').text(data.diasAguinaldo.toFixed(2));
            $('#total_indemnizacion').text(formateaMoneda(data.totalMesesIndemnizacion));
            $('#total_veinte_dias_anio').text(formateaMoneda(data.totalVeinteDiasAnio));
            $('#total_prima_antiguedad').text(formateaMoneda(data.totalDiasPrimaAntiguedad));
            $('#total_comp_antiguedad').text(formateaMoneda(data.totalMesesCompAntiguedad));
            $('#total_vacaciones').text(formateaMoneda(data.totalDiasVacaciones));
            $('#total_prima_vacacional').text(formateaMoneda(data.totaDiasPrimaVacacional));
            $('#total_aguinaldo').text(formateaMoneda(data.totalDiasAguinaldo));
            //Deducciones
            $('#isr_indemnizacion').text(formateaMoneda(data.isrIndemnizacion));
            $('#isr_finiquito').text(formateaMoneda(data.isrFiniquito));
            // Agregar pensiones no nulas a la tabla de deducciones
            agregarPensionesATablaDeducciones(data);
            //Se hacen las sumas o restas con los datos obtenidos del backend
            sumasRestasConceptosSeleccionados(data);
            toastr["success"]("Se obtuvieron los datos del cálculo por separación", "Éxito", {progressBar: true, closeButton: true});
        },
        error: function (jqXHR, textStatus, errorThrown) {
            toastr["error"]("Ocurrió un error al procesar el cálculo por separación", "Error", {progressBar: true, closeButton: true});
        }
    });
}

function agregarPensionesATablaDeducciones(data) {
    const tablaDeducciones = $('#tablaDeducciones tbody');
    for (let i = 1; i <= 5; i++) {
        let pensionPorcentaje = data[`pension${i}`];
        let pensionCuotaFija = data[`pension${i}Fija`];
        if (pensionPorcentaje !== null && $(`#pension${i}`).length === 0) {
            const rowPorcentaje = `<tr id="pension${i}">
                                      <td>${i}a. Pensión Alim. ${pensionPorcentaje}%</td>
                                      <td id="importe${i}"></td>
                                   </tr>`;
            tablaDeducciones.append(rowPorcentaje);
        }
        if (pensionCuotaFija !== null) {
            if ($(`#pension${i}Fija`).length === 0) {
                const rowFija = `<tr id="pension${i}Fija">
                                    <td>Pensión Alim. Cuota Fija</td>
                                    <td id="importeFijo${i}"></td>
                                 </tr>`;
                tablaDeducciones.append(rowFija);
            }
            $(`#importeFijo${i}`).text(formateaMoneda(pensionCuotaFija));
        }
    }
}


//Cuando se da click en cualquier checkbox se realiza el cálculo de los conceptos seleccionados por el usuario
$('input[type="checkbox"]').on('click', function () {
    recuperarInformacionModal();
});

async function sumasRestasConceptosSeleccionados(data) {
    let percepcionesTotal = 0;
    let deduccionesTotal = 0;
    let totalIndemnizacion = 0;
    let totalFiniquito = 0;
    let totalPensionesAlim = 0;
    // Procesar checkboxes de indemnización
    totalIndemnizacion = procesarCheckboxesIsr(['chkIndemnizacion', 'chkVeniteDiasAnio', 'chkPrimaAntiguedad', 'chkComPorAnt'], percepcionesMap, totalIndemnizacion);
    // Procesar checkboxes de finiquito
    totalFiniquito = procesarCheckboxesIsr(['chkDiasVacaciones', 'chkPrimaVacacional', 'chkAguinaldo'], percepcionesMap, totalFiniquito);
    // Procesar los checkboxes de percepciones
    $.each(percepcionesMap, function (checkboxId, cellId) {
        if ($('#' + checkboxId).is(':checked')) {
            percepcionesTotal += getCellValue(cellId);
        }
    });
    // Llamar a la función ajustarIsrAnual usando async/await para esperar las respuestas, esto se realiza para ajustar el isr con el valor real de la suma de indemnizacion o finiquito
    try {
        await ajustarIsrAnual(totalIndemnizacion, totalFiniquito);
        // Procesar los checkboxes de deducciones una vez ajustados los valores de isr en la función ajustarIsrAnual
        $.each(deduccionesMap, function (checkboxId, cellId) {
            if ($('#' + checkboxId).is(':checked')) {
                deduccionesTotal += getCellValue(cellId);
            }
        });
        // Una vez completadas las llamadas AJAX, calcular la diferencia a percibir
        let totalPercibir = percepcionesTotal - deduccionesTotal;
        // Validar que el total a percibir no sea menor a cero
        if (totalPercibir < 0) {
            totalPercibir = 0;
        }
        //Una vez obtenido el total a percibir gravado se descuentan los porcentajes correspondientes;
        for (let i = 1; i <= 5; i++) {
            let pension = data[`pension${i}`];
            let cuotaFija = data[`pension${i}Fija`];
            if (pension !== null) {
                totalPensionesAlim += totalPercibir * (pension / 100);
                $(`#importe${i}`).text(`${formateaMoneda(totalPercibir * (pension / 100))}`);
            }
            if (cuotaFija !== null) {
                totalPensionesAlim += cuotaFija;
            }
        }
        // Mostrar los totales seleccionados por el usuario en los labels correspondientes
        $('#total_percepciones_label').text('TOTAL DE PERCEPCIONES: ' + formateaMoneda(percepcionesTotal));
        $('#total_deducciones_label').text('TOTAL DEDUCCIONES: ' + formateaMoneda(deduccionesTotal + totalPensionesAlim));
        $('#total_percibir_label').text('TOTAL A PERCIBIR: ' + formateaMoneda(totalPercibir - totalPensionesAlim));
    } catch (error) {
        toastr["error"]("Ocurrió un error al calcular el finiquito", "Error", {progressBar: true, closeButton: true});
    }
}

// Función para procesar checkboxes genérica
function procesarCheckboxesIsr(checkboxIds, map, acumulador) {
    $.each(checkboxIds, function (index, checkboxId) {
        if ($('#' + checkboxId).is(':checked')) {
            acumulador += getCellValue(map[checkboxId]);
        }
    });
    return acumulador;
}

// Función ajustarIsrAnual usa async/await para esperar la respuesta de las llamadas AJAX
async function ajustarIsrAnual(totalIndemnizacion, totalFiniquito) {
    let idNomina = $('#idNomina').val();
    // Extraer solo el número de años del texto
    let valorAnios = parseFloat($('label[for="anios"]').text().match(/\d+/)[0]);
    let isrIndemnizacionPromise = new Promise((resolve, reject) => {
        $.ajax({
            type: "GET",
            url: "calculoSeparacion/calcularIsrAnual/" + totalIndemnizacion + "/" + idNomina + "/" + valorAnios + "/" + true,
            dataType: 'json',
            success: function (data) {
                $('#isr_indemnizacion').text(formateaMoneda(data));
                resolve();
            },
            error: function (e) {
                reject(e);
            }
        });
    });
    let isrFiniquitoPromise = new Promise((resolve, reject) => {
        $.ajax({
            type: "GET",
            url: "calculoSeparacion/calcularIsrAnual/" + totalFiniquito + "/" + idNomina + "/" + valorAnios + "/" + false,
            dataType: 'json',
            success: function (data) {
                $('#isr_finiquito').text(formateaMoneda(data));
                resolve();
            },
            error: function (e) {
                reject(e);
            }
        });
    });
    await Promise.all([isrIndemnizacionPromise, isrFiniquitoPromise]);
}

// Función para obtener el valor numérico de una celda
function getCellValue(cellId) {
    var cellValue = $('#' + cellId).text();
    var numericValue = parseFloat(cellValue.replace(/[$,]/g, ''));
    return isNaN(numericValue) ? 0 : numericValue;
}

function formateaMoneda(value) {
    return '$' + value.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
}

// Limpiar el título del modal cuando se oculta
$('#detalleSeparacionModal').on('hidden.bs.modal', function () {
    limpiarCamposPercepciones();
    marcarTodosChkBox();
    let modalTitle = $('#tituloModal');
    modalTitle.empty();
    $(this).find('form').each(function () {
        this.classList.remove('was-validated');
    });
});

function limpiarCamposPercepciones() {
    $('#tabla_percepciones td[id]').empty();
}

function definirReciboDescargar() {
    let prestaciones = $('#campo_prestaciones_si_no').val();
    if (prestaciones === 'Si') {
        descargarReciboFiniquitoPrestaciones();
    } else {
        descargarReciboFiniquito();
    }
}

function descargarReciboFiniquitoPrestaciones() {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParametros = new URLSearchParams(d);
    let id_trabajador = urlParametros.get('id_trabajador');
    // Objeto para almacenar las checkbox marcadas y sus valores
    let conceptos = {};
    let expediente = $('#campo_expediente').val();
    let fechaBaja = $('#fecha_baja').val();
    let textoAnios = $('label[for="anios"]').text();
    let textoMeses = $('label[for="meses"]').text();
    let textoDias = $('label[for="dias"]').text();
    // Extraer solo los números de los textos
    let valorAnios = parseFloat(textoAnios.match(/\d+/)[0]);
    let valorMeses = parseFloat(textoMeses.match(/\d+/)[0]);
    let valorDias = parseFloat(textoDias.match(/\d+/)[0]);
    let salarioIntegDiario = parseFloat($('#campo_salario_integrado_diario').val().replace(/[^\d.]+/g, ''));
    let salarioIntegMensual = parseFloat($('#campo_salario_integrado_mensual').val().replace(/[^\d.]+/g, ''));
    let periodoVacaciones = $('#periodo_vacaciones').text();
    let periodoAguinaldo = $('#periodo_aguinaldo').text();
    let diasPrimaAntig = $('#dias_prima_antiguedad').text();
    let mesesCompAntCCt = $('#meses_comp_antiguedad').text();
    let mesesIndemnizacion = $("#meses_indemnizacion").text();
    let diaVeinte = $('#dias_veinte_dias_anio').text();
    let diasVacaciones = $('#dias_vacaciones').text();
    let diasPrimaVacacional = $('#dias_prima_vacacional').text();
    let diasAguinaldo = $('#dias_aguinaldo').text();
    // Procesa los checkboxes de percepciones
    $.each(percepcionesMap, function (checkboxId, cellId) {
        if ($('#' + checkboxId).is(':checked')) {
            conceptos[checkboxId] = getCellValue(cellId);
        }
    });

    // Procesa los checkboxes de deducciones
    $.each(deduccionesMap, function (checkboxId, cellId) {
        if ($('#' + checkboxId).is(':checked')) {
            conceptos[checkboxId] = getCellValue(cellId);
        }
    });
    //Llmada ajax para generar el reporte con los valores seleccionados por el usuario, sumas y restas se relizan desde el reporte
    $.ajax({
        type: "GET",
        url: "report/reciboFiniquitoPrestaciones?trabajadorId=" + id_trabajador +
                "&fechaBaja=" + fechaBaja +
                "&aniosAntig=" + valorAnios +
                "&mesesAntig=" + valorMeses +
                "&diasAntig=" + valorDias +
                "&salDiarioInteg=" + salarioIntegDiario +
                "&salMensInteg=" + salarioIntegMensual +
                "&periodoVacaciones=" + periodoVacaciones +
                "&periodoAguinaldo=" + periodoAguinaldo +
                "&diasPrimaAntig=" + (conceptos.chkPrimaAntiguedad ? diasPrimaAntig : 0.0) +
                "&mesesIndemnizacion=" + (conceptos.chkIndemnizacion ? mesesIndemnizacion : 0.0) +
                "&diasVeinte=" + (conceptos.chkVeniteDiasAnio ? diaVeinte : 0.0) +
                "&diasVacaciones=" + (conceptos.chkDiasVacaciones ? diasVacaciones : 0.0) +
                "&diasPrimaVacacional=" + (conceptos.chkPrimaVacacional ? diasPrimaVacacional : 0.0) +
                "&diasAguinaldo=" + (conceptos.chkAguinaldo ? diasAguinaldo : 0.0) +
                "&importePrimaAntig=" + (conceptos.chkPrimaAntiguedad ? conceptos.chkPrimaAntiguedad : 0.0) +
                "&importeIndemnizacionLegal=" + (conceptos.chkIndemnizacion ? conceptos.chkIndemnizacion : 0.0) +
                "&importeDiasVeinte=" + (conceptos.chkVeniteDiasAnio ? conceptos.chkVeniteDiasAnio : 0.0) +
                "&importeVacaciones=" + (conceptos.chkDiasVacaciones ? conceptos.chkDiasVacaciones : 0.0) +
                "&importePrimaVacacional=" + (conceptos.chkPrimaVacacional ? conceptos.chkPrimaVacacional : 0.0) +
                "&importeAguinaldo=" + (conceptos.chkAguinaldo ? conceptos.chkAguinaldo : 0.0) +
                "&isrFiniquito=" + (conceptos.chkIsrFiniquito ? conceptos.chkIsrFiniquito : 0.0) +
                "&isrIndemnizacion=" + (conceptos.chkIsrIndemnizacion ? conceptos.chkIsrIndemnizacion : 0.0) +
                //Compensación según el contrato colectivo de trabajo
                "&mesesCompAntigCct=" + (conceptos.chkComPorAnt ? mesesCompAntCCt : 0.0) +
                "&importeMesesCompAntigCct=" + (conceptos.chkComPorAnt ? conceptos.chkComPorAnt : 0.0) +
                "&tipo=EXCEL",
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
            link.download = 'reciboFiniquitoTrabajador' + expediente + '.xlsx';
            link.click();
        }, error: function (e) {
            toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
        }
    });

}

function descargarReciboFiniquito() {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParametros = new URLSearchParams(d);
    let id_trabajador = urlParametros.get('id_trabajador');
    // Objeto para almacenar las checkbox marcadas y sus valores
    let conceptos = {};
    let expediente = $('#campo_expediente').val();
    let fechaBaja = $('#fecha_baja').val();
    let textoAnios = $('label[for="anios"]').text();
    let textoMeses = $('label[for="meses"]').text();
    let textoDias = $('label[for="dias"]').text();
    // Extraer solo los números de los textos
    let valorAnios = parseFloat(textoAnios.match(/\d+/)[0]);
    let valorMeses = parseFloat(textoMeses.match(/\d+/)[0]);
    let valorDias = parseFloat(textoDias.match(/\d+/)[0]);
    let salarioIntegDiario = parseFloat($('#campo_salario_integrado_diario').val().replace(/[^\d.]+/g, ''));
    let salarioIntegMensual = parseFloat($('#campo_salario_integrado_mensual').val().replace(/[^\d.]+/g, ''));
    let periodoVacaciones = $('#periodo_vacaciones').text();
    let periodoAguinaldo = $('#periodo_aguinaldo').text();
    let diasPrimaAntig = $('#dias_prima_antiguedad').text();
    let mesesIndemnizacion = $("#meses_indemnizacion").text();
    let diaVeinte = $('#dias_veinte_dias_anio').text();
    let diasVacaciones = $('#dias_vacaciones').text();
    let diasPrimaVacacional = $('#dias_prima_vacacional').text();
    let diasAguinaldo = $('#dias_aguinaldo').text();
    // Procesa los checkboxes de percepciones
    $.each(percepcionesMap, function (checkboxId, cellId) {
        if ($('#' + checkboxId).is(':checked')) {
            conceptos[checkboxId] = getCellValue(cellId);
        }
    });

    // Procesa los checkboxes de deducciones
    $.each(deduccionesMap, function (checkboxId, cellId) {
        if ($('#' + checkboxId).is(':checked')) {
            conceptos[checkboxId] = getCellValue(cellId);
        }
    });
    //Llmada ajax para generar el reporte con los valores seleccionados por el usuario, sumas y restas se relizan desde el reporte
    $.ajax({
        type: "GET",
        url: "report/reciboFiniquito?trabajadorId=" + id_trabajador +
                "&fechaBaja=" + fechaBaja +
                "&aniosAntig=" + valorAnios +
                "&mesesAntig=" + valorMeses +
                "&diasAntig=" + valorDias +
                "&salDiarioInteg=" + salarioIntegDiario +
                "&salMensInteg=" + salarioIntegMensual +
                "&periodoVacaciones=" + periodoVacaciones +
                "&periodoAguinaldo=" + periodoAguinaldo +
                "&diasPrimaAntig=" + (conceptos.chkPrimaAntiguedad ? diasPrimaAntig : 0.0) +
                "&mesesIndemnizacion=" + (conceptos.chkIndemnizacion ? mesesIndemnizacion : 0.0) +
                "&diasVeinte=" + (conceptos.chkVeniteDiasAnio ? diaVeinte : 0.0) +
                "&diasVacaciones=" + (conceptos.chkDiasVacaciones ? diasVacaciones : 0.0) +
                "&diasPrimaVacacional=" + (conceptos.chkPrimaVacacional ? diasPrimaVacacional : 0.0) +
                "&diasAguinaldo=" + (conceptos.chkAguinaldo ? diasAguinaldo : 0.0) +
                "&importePrimaAntig=" + (conceptos.chkPrimaAntiguedad ? conceptos.chkPrimaAntiguedad : 0.0) +
                "&importeIndemnizacionLegal=" + (conceptos.chkIndemnizacion ? conceptos.chkIndemnizacion : 0.0) +
                "&importeDiasVeinte=" + (conceptos.chkVeniteDiasAnio ? conceptos.chkVeniteDiasAnio : 0.0) +
                "&importeVacaciones=" + (conceptos.chkDiasVacaciones ? conceptos.chkDiasVacaciones : 0.0) +
                "&importePrimaVacacional=" + (conceptos.chkPrimaVacacional ? conceptos.chkPrimaVacacional : 0.0) +
                "&importeAguinaldo=" + (conceptos.chkAguinaldo ? conceptos.chkAguinaldo : 0.0) +
                "&isrFiniquito=" + (conceptos.chkIsrFiniquito ? conceptos.chkIsrFiniquito : 0.0) +
                "&isrIndemnizacion=" + (conceptos.chkIsrIndemnizacion ? conceptos.chkIsrIndemnizacion : 0.0) +
                "&tipo=EXCEL",
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
            link.download = 'reciboFiniquitoTrabajador' + expediente + '.xlsx';
            link.click();
        }, error: function (e) {
            toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
        }
    });
}