//************VARIABLE GLOBAL PARA ID TRABAJADOR************
let idTrabajador;
const nominaTransportacion = 2;
//******************************************CARGA DEL DOM**********************************
document.addEventListener('DOMContentLoaded', () => {
//    //Ajustar el scroll para mostrar toda la pantalla de captura
//    window.scrollTo(0, 245);
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    idTrabajador = urlParams.get('id_trabajador');
    obtenerInformacionTrabajador();
    obtenerInformacionPuestoTrabajador();
    obtenerInformacionHorarioTrabajador();
    obtenerInformacionDescansosTrabajador();
    buscarPeriodosTransportacion();
    $tablaPuestosSuplencias;
    habilitarNavegacionConFlechasTabla();
    vinculaEventosTabla();
    //Espera para llamar a la función con la finalidad de dar tiempo de recuperar los datos que requiere para funcionar correctamente
    setTimeout(() => calcularFaltasyAsistenciasTrabajador(), 500);
});

// Recargar la página si regresa de asignar los horarios al trabajador
if (String(window.performance.getEntriesByType("navigation")[0].type) === "back_forward") {
    location.reload(true);
}

//Retorno al listado de trabajadores
function verTrabajadores() {
    window.location.href = 'adminListadoNombTransp?tab=tab-1';
}
//Redireccion al kardex del trabajador
function verKardexTrabajador() {
    window.location.href = 'guardaInasistencias?id_trabajador=' + idTrabajador;
}
//Redirección a los horarios del trabajador
function redireccionHorariosTrabajador() {
    window.location.href = 'trabajadorHorario?id_trabajador=' + idTrabajador;
}
//Permite al usuario moverse con las felchas de dirección sobre la tabla
function habilitarNavegacionConFlechasTabla() {
    // Navegación con arrow keys en la tabla
    $('input').keydown(function (e) {
        var currentRow = $(this).closest('tr');
        var currentCol = $(this).closest('td').index();
        switch (e.which) {
            case 37: // Flecha izquierda
                if (currentCol > 0) {
                    currentRow.find('input').eq(currentCol - 1).focus();
                }
                break;
            case 39: // Flecha derecha
                if (currentCol < currentRow.find('input').length - 1) {
                    currentRow.find('input').eq(currentCol + 1).focus();
                }
                break;
            case 38: // Flecha arriba
                var prevRow = currentRow.prev('tr');
                if (prevRow.length > 0) {
                    prevRow.find('input').eq(currentCol).focus();
                }
                break;
            case 40: // Flecha abajo
                var nextRow = currentRow.next('tr');
                if (nextRow.length > 0) {
                    nextRow.find('input').eq(currentCol).focus();
                }
                break;
            default:
                return;
        }
        e.preventDefault();
    });
}
//Se listan todos los eventos de escucha que son colocados a la tabla
function vinculaEventosTabla() {
    for (let i = 1; i <= 7; i++) {
        $(`#excedenteDia${i}`).on('keypress change', function (e) {
            if (e.which === 13 || e.type === 'change') {
                //El excedente de jornada debe estar limitado a 8 horas
                let limiteHoras = 8;
                manejarEventos(e, limiteHoras);
                calcularExcedenteJornada();
            }
        });
        $(`#jornadaNormalDia${i}`).on('keypress change', function (e) {
            if (e.which === 13 || e.type === 'change') {
                //JornadaNormal por dia está limitado a 16 horas
                let limiteHoras = 13;
                let minimoHorasJornadaNormal = 8;
                //Se crea esta bandera para indicar que la llamada a calcularyMostrarTotalesJornada se hace desde los campos de la jornada normal y no desde los abonos o descuentos, mismo caso para calcularFaltasyAsistenciasTrabajador()
                let flagLlamadaDesdeJornada = true;
                manejarEventos(e, limiteHoras, minimoHorasJornadaNormal);
                calcularyMostrarTotalesJornada(flagLlamadaDesdeJornada);
                calcularFaltasyAsistenciasTrabajador(flagLlamadaDesdeJornada);
                calcularYMostrarTotalesSuplencias();
            }
            //Calculando primas dominicales en el día domingo
            if (i === 1) {
                //Se crea esta bandera para indicar que la llamada a calcularPrimaDominical se hace desde los campos de la jornada normal y no desde los abonos o descuentos
                let flagLlamadaDesdeJornada = true;
                calcularPrimaDominical(flagLlamadaDesdeJornada);
            }
        });
        $(`#tiempoExtraLabDia${i}`).on('keypress change', function (e) {
            if (e.which === 13 || e.type === 'change') {
                //Tiempo extra por día está limitado a 16 horas 
                let limiteHoras = 16;
                //Se crea esta bandera para indicar que la llamada a calcularyMostrarTotalesTiempoExtra se hace desde los campos de tiempo extra y no desde los abonos o descuentos
                let flagLlamadaDesdeTiempoExtra = true;
                manejarEventos(e, limiteHoras);
                //Coloca la sumatoria de horas en los campos de horas extras triples o dobles según corresponda
                calcularExtraDobleoTriple();
                //Para ajustar que no se excedan más de 9 horas el cálculo de horas dobles
                ajustarExtraDoble();
                //Calcular totales relacionados a tiempo extra
                calcularyMostrarTotalesTiempoDoble(flagLlamadaDesdeTiempoExtra);
                calcularyMostrarTotalesTiempoTriple(flagLlamadaDesdeTiempoExtra);
                calcularYMostrarTotalesSuplencias();
            }
        });
        $(`#paseosLabDia${i}`).on('keypress change', function (e) {
            if (e.which === 13 || e.type === 'change') {
                //Tiempo extra por día está limitado a 16 horas 
                let limiteHoras = 16;
                //Se crea esta bandera para indicar que la llamada a calcularyMostrarTotalesDescansosLaborados se hace desde los campos de descansos laborados y no desde los abonos o descuentos
                let flagLlamadaDesdeDescansosLaborados = true;
                manejarEventos(e, limiteHoras);
                calcularyMostrarTotalesDescansosLaborados(flagLlamadaDesdeDescansosLaborados);
                calcularYMostrarTotalesSuplencias();
            }
        });
    }
//Abonos y descuentos por tiempo
    $("#abonosHoraDoble, #abonosHorasTriples, #abonosDescansosLaborados, #abonosFestivos, #descuentosHoraDoble, #descuentosHoraTriple, #descuentosDescansosLaborados, #descuentosFestivos").on('keypress change', function (e) {
        if (e.which === 13 || e.type === 'change') {
            let limiteHoras = 100;
            //Formatea los campos como (HH:MM)
            manejarEventos(e, limiteHoras);
            calcularAbonosDescuentos();
        }
    });
//Abonos y descuentos por unidad
    $("#abonosJornada,#descuentosJornada,#abonosPrimaDominical, #abonosDiasLaborados,#omisionPorImporte, #abonosDiferenciaSuplencia,#descuentosPrimaDominical, #descuentosDiasLaborados,#omisionPorImporte, #descuentosDiferenciaSuplencia ").on('keypress change', function (e) {
        if (e.which === 13 || e.type === 'change') {
            calcularAbonosDescuentos();
        }
    });

    $("#periodoTransportacion").change(function () {
        let valorSeleccionado = $(this).val();
        buscarPeriodoPagoId(valorSeleccionado);
        limpiarDatos();
    });

//Captura abierta para remover o agregar los descansos a la captura semanal
    $('#capturaAbierta').change(function () {
        if ($(this).is(':checked')) {
            removerDescansosTrabajador();
        } else {
            obtenerInformacionDescansosTrabajador();
        }
    });

    //*************************************TECLAS CON ACCESOS RÁPIDOS*************************************
    let keys = {};
    $(document).keydown(function (e) {
        keys[e.which] = true;
        //Mostrar modal de suplencias
        if (keys[18] && keys[83]) { // Alt + S
            $('#suplenciaModal').modal('show');
        }
        //Refrescar incidencias 
        if (keys[18] && keys[73]) { // Alt + I
            $('#confirmacionBuscarIncidenciasNuevas').modal('show');
            //Se llama a la función que espera el submit del confirmar
            actualizarRegistroIncidencias();
        }
        //Guardado rápido de registro para la captura semanal
        if (keys[18] && keys[35]) { // Alt + End
            if ($("#btnGuardar").is(":hidden")) {
                $("#btnEditar").click();
            } else {
                $("#btnGuardar").click();
            }
        }
        //Atajo para alternar la captura abierta
        if (keys[18] && keys[67]) { // Alt + C
            $('#capturaAbierta').prop('checked', function (index, checked) {
                return !checked;
            }).change();
        }
        //Atajo para pasar al siguiente trabajador
        if (keys[18] && e.which === 38) { // Alt + Flecha arriba
            $("#tabla_datos_trabajador_next").click();
        }
        //Atajo para el trabajador previo
        if (keys[18] && e.which === 40) { // Alt + Flecha hacia abajo
            $("#tabla_datos_trabajador_previous").click();
        }
    });
//Se borra la vinculación al evento
    $(document).keyup(function (e) {
        delete keys[e.which];
    });
}

function convertirDosPuntosAPuntos(hora) {
    if (hora.includes('.')) {
        return hora;
    }
    if (hora === 'Descanso' || hora === '') {
        return '';
    }
    let [horas, minutos] = hora.split(':').map(Number);
    if (minutos === undefined) {
        minutos = 0;
    }
    return `${horas}.${minutos < 10 ? '0' : ''}${minutos}`;
}
;

const obtenerValorJquery = (elementoId) => $(`#${elementoId}`).val();

function definirDiasEncabezadoTabla(textoDelSelect) {
    const fechasEncontradas = textoDelSelect.match(/\d{1,2}\/\d{1,2}\/\d{4}/g);
    // Formatear las fechas con guiones y guardarlas en un arreglo
    const fechasFormateadas = fechasEncontradas.map(fecha => {
        const partes = fecha.split('/');
        return partes[0].padStart(2, '0') + '/' + partes[1].padStart(2, '0') + '/' + partes[2];
    });

    // Extraer el día de inicio y fin del rango
    const fechaInicio = parseFecha(fechasFormateadas[0]);
    const fechaFin = parseFecha(fechasFormateadas[1]);
    let diferenciaEnDias = obtenerDiferenciaDias(fechaInicio, fechaFin);
    // Crear un array con el rango de días
    const rangoDias = [];
    for (let i = 0; i <= diferenciaEnDias; i++) {
        const dia = new Date(fechaInicio);
        dia.setDate(fechaInicio.getDate() + i);
        // Obtener solo el día
        const numeroDia = dia.getDate();
        //Se realiza la búsqueda de días festivo con base a la nómina seleccionada y el día que se está mapeando en el encabezado
        buscarDiasFestivos(dia, i + 1);
        rangoDias.push(numeroDia);
    }
    //Este va acorde a los días en los que transportación genera su periodo de pago (Domingo a Sábado)
    cambiarOrdenEncabezados(["DOMINGO " + rangoDias[0], "LUNES " + rangoDias[1], "MARTES " + rangoDias[2], "MIERCOLES " + rangoDias[3], "JUEVES " + rangoDias[4], "VIERNES " + rangoDias[5], "SABADO " + rangoDias[6]]);
}

function cambiarOrdenEncabezados(nuevosDias) {
    for (let i = 0; i < 7; i++) {
        $(`#dia${i + 1}Encabezado`).text(nuevosDias[i]);
    }
}

function buscarDiasFestivos(fecha, numeroDia) {
    const fechaFormateada = fecha.toISOString().split('T')[0];
    $.ajax({
        type: "GET",
        url: "catalogos/encontrarDiasFestivos/" + nominaTransportacion + "/" + fechaFormateada,
        dataType: 'json',
        success: function (dataDiaFestivo) {
            if (dataDiaFestivo !== undefined && dataDiaFestivo.length > 0) {
                //Se marca de rojo el día identificado como festivo
                $(`#dia${numeroDia}Encabezado`).css('color', 'red');
                $(`#inasistenciasDia${numeroDia}`).val('DFEST');
                $(`#calculoFestivos`).val('08:00');
                toastr["info"]("El día marcado en rojo fue festivo", "Información", {progressBar: true, closeButton: true});
            }
        },
        error: function (e) {
            console.error(e);
            toastr["error"]("Error al buscar días festivos", "Error", {progressBar: true, closeButton: true});
        }
    });
}

function parseFecha(fechaStr) {
    const [dia, mes, anio] = fechaStr.split('/').map(Number);
    return new Date(anio, mes - 1, dia);
}

//********************************FORMATEO DE LOS INPUTS CORRESPONDIENTES (HH:MM)*****************************
function manejarEventos(e, limiteHorasPorDia, limiteInferiorHoras) {
    e.preventDefault();
    aplicarFormato(e.target, limiteHorasPorDia);
}
//Función que aplica el formato y llama a la función de formateo para colocar el valor ya formateado en el input
function aplicarFormato(input, limiteHorasPorDia) {
    let valor = $(input).val();
    //Verificar si el input no viene vacío
    if (valor.trim() === '') {
        return;
    }
    //Si el usuario ingresa la hora ya formateada (HH:MM)
    if (/^\d+:\d{2}$/.test(valor)) {
        // Dividir el valor en horas y minutos
        const [horas, minutos] = valor.split(':').map(Number);
        // Verificar límites de horas y minutos
        if (horas < limiteHorasPorDia || (horas === limiteHorasPorDia && minutos <= 0)) {
            // Formatear la hora si el formato es correcto
            $(input).val(valor);
        } else {
            toastr["error"]("No se deben exceder las " + limiteHorasPorDia + " horas", "Error", {progressBar: true, closeButton: true});
            // Limpiar el input y salir de la función si los límites son excedidos
            $(input).val('');
        }
        return;
    }
    // Verificar si el valor ingresado es un número
    if (!/^\d*(\.\d+)?$/.test(valor) && !/^\d+:\d{2}$/.test(valor) && !/^\d+:(?:[0-5]\d|60)$/.test(valor)) {
        toastr["error"]("Formato incorrecto. Ingrese solo números o en formato HH:mm.", "Error", {progressBar: true, closeButton: true});
        // Limpiar el input y salir de la función si el formato es incorrecto
        $(input).val('');
        return;
    }
    // Convertir el valor a un número si es un formato decimal (por ejemplo, 0.30)
    if (/^\d+(\.\d+)?$/.test(valor)) {
        valor = parseFloat(valor);
    }
    //Aquí se coloca el valor formateado en el input
    if (!isNaN(valor)) {
        $(input).val(formatearHora(valor, limiteHorasPorDia));
    }
}
//Función que da el formato de hora al input recibido
function formatearHora(valor, limiteHorasPorDia) {
    if (isNaN(valor) || valor === 0) {
        toastr["error"]("Valor inválido", "Error", {progressBar: true, closeButton: true});
        return '';
    }
//        // Verifica si el valor es menor que el límite inferior (Casos en los que aplique conmo la jornada normal)
//        if (valor < limiteInferiorHoras) {
//            toastr["warning"]("El valor ingresado es menor que el límite inferior permitido de " + limiteInferiorHoras + " horas", "Advertencia", {progressBar: true, closeButton: true});
//            valor = limiteInferiorHoras;
//        }
    let horas = Math.floor(valor);
    let minutos = Math.round((valor % 1) * 100);
    const horasOriginales = horas;
    const minutosOriginales = minutos;
    horas = Math.min(horas, limiteHorasPorDia);
    minutos = Math.min(minutos, 59);
    if (horas === limiteHorasPorDia) {
        minutos = 0;
    }
    // Verifica si las horas o minutos han cambiado debido a la limitación
    if (horas !== horasOriginales || minutos !== minutosOriginales) {
        toastr["warning"]("Verificar datos ingresados, se realizó el ajuste al máximo diario permitido de " + limiteHorasPorDia + " horas", "Advertencia", {progressBar: true, closeButton: true});
    }
    const horasFormateadas = horas.toString().padStart(2, '0');
    const minutosFormateados = minutos.toString().padStart(2, '0');
    return `${horasFormateadas}:${minutosFormateados}`;
}

//*******************************************SUMAS Y CÁLCULOS DE HORAS******************************************************
//Bandera de aplicación de horas extras triples
let flagAplicanHoraExtraTriple = false;
//Esta bandera sirve para identificar el día del recorrido del for para eliminarlo si el usuario excede las 9 horas y cancela la aplicación de horas extras triples
let flagDiaActual = false;
//Dia actual recorrido donde comienzan a aplicar horas triples
let numeroDiaActual;

//Para la parte de abonos y descuentos, calcula las horas
function calculo(diferencia, funcionALlamarCalcularGeneral, inputTotal) {
    let resultado;
    let arregloHoras = [];
    // Se vuelven a calcular los totales para verificar si se hizo algún cambio
    funcionALlamarCalcularGeneral();
    if (diferencia.flag === 'suma' || diferencia.flag === 'Diferencia') {
        arregloHoras = [$('#' + inputTotal).val(), diferencia.result];
        $('#' + inputTotal).val(sumarHorasArray(arregloHoras));
    } else {
        //Si no hay un total a restar entonces se coloca la hora vacía ya que entraría a los negativos
        if ($('#' + inputTotal).val() === '') {
            $('#' + inputTotal).val('');
            //Si si hay un total a restar se realiza la resta correspondiente
        } else {
            resultado = restarHoras($('#' + inputTotal).val(), diferencia.result);
            $('#' + inputTotal).val(resultado.result);
        }
    }
}

//Para la parte de abonos y descuentos, calcula las unidades con base a los inputs registrados anteriormente por el usuario
function calculoUnidades(diferencia, funcionALlamarCalcularGeneral, inputTotal, flagJornada) {
    //Se obtiene el valor actual para el campo acorde a los inputs colocados
    funcionALlamarCalcularGeneral();
    let diferenciaEntero = parseFloat(diferencia.result);
    let valorTotalActual = parseFloat($('#' + inputTotal).val(), 10) || 0;
    let resultado;
    if (diferencia.flag === 'suma' || diferencia.flag === 'Diferencia') {
        resultado = valorTotalActual + diferenciaEntero;
    } else {
        resultado = valorTotalActual - diferenciaEntero;
    }
    if (!isNaN(resultado) && resultado > 0) {
        $('#' + inputTotal).val(resultado);
    } else {
        funcionALlamarCalcularGeneral();
    }
}

function calcularExtraDobleoTriple() {
    let horasArray = [];
    // Procesar días
    for (let i = 1; i <= 7; i++) {
        let horasDiaFormateado = $(`#tiempoExtraLabDia${i}`).val() || '';
        procesarDia(i, horasDiaFormateado, horasArray);
    }
}

function procesarDia(i, horasDiaFormateado, horasArray) {
    if (horasDiaFormateado === '' || horasDiaFormateado === '00:00') {
        $(`#extraDobleDia${i}`).val('');
        $(`#extraTripleDia${i}`).val('');
        return;
    }
    horasArray.push(horasDiaFormateado);
    let resultadoSumaTotalHoras = sumarHorasArray(horasArray);
    //Si se superan 9 horas comienzan a aplicar las horas triples, se mandará a llamar en la i donde se superan las 9 horas
    if (compararHoras(resultadoSumaTotalHoras, "09:00")) {
        mostrarModalConfirmacionHorasTriples(i, resultadoSumaTotalHoras);
        //Si no se superan las 9 horas se mantienen dobles 
    } else {
        $(`#extraDobleDia${i}`).val(horasDiaFormateado);
    }
}

function mostrarModalConfirmacionHorasTriples(i, resultadoSumaTotalHoras) {
    let flagLlamadaDesdeTiempoExtra = true;
    let horasExtrasDoble = 0;
    let horasExtraTriple = 0;
    let nueveHorasEnMinutos = 540;
    //Regresar flag al estado original
    $('#horasTriplesModal').modal('show');
    if (flagDiaActual === false) {
        $('#diaActualRecorrido').val(i);
    }
    //Si se confirma el modal se procede a calcular las horas triples
    $("#horasTriplesModal").off("submit").on("submit", function (event) {
        event.preventDefault();
        numDiaActual = $('#diaActualRecorrido').val();
        $(`#extraTripleDia${numDiaActual}`).val('');
        //Si ya comenzaron a aplicar horas extras triples la segunda vez 
        if (flagAplicanHoraExtraTriple === true) {
            let minutosInput = obtenerHorasAMinutos($(`#tiempoExtraLabDia${numDiaActual}`).val());
            horasExtraTriple = minutosInput;
        } else {
            horasExtraTriple += obtenerHorasAMinutos(resultadoSumaTotalHoras) - nueveHorasEnMinutos;
        }
        horasExtrasDoble += obtenerHorasAMinutos($(`#tiempoExtraLabDia${numDiaActual}`).val()) - horasExtraTriple;
        $(`#extraDobleDia${numDiaActual}`).val(horasExtrasDoble > 0 ? minutosAHoras(horasExtrasDoble) : '');
        $(`#extraTripleDia${numDiaActual}`).val(horasExtraTriple > 0 ? minutosAHoras(horasExtraTriple) : '');
        $('#horasTriplesModal').modal('hide');
        //calcularyMostrarTotalesTiempoExtra(flagLlamadaDesdeTiempoExtra);
        calcularyMostrarTotalesTiempoDoble(flagLlamadaDesdeTiempoExtra);
        calcularyMostrarTotalesTiempoTriple(flagLlamadaDesdeTiempoExtra);
        flagDiaActual = false;
        flagAplicanHoraExtraTriple = true;
        $('#diaActualRecorrido').val('');
    });
    //Si se cancela se borra el input del usuario
    $("#btn_Cancelar_Extras").on("click", function (event) {
        let diaActualAlExceder9Horas = $('#diaActualRecorrido').val();
        $(`#tiempoExtraLabDia${diaActualAlExceder9Horas}`).val('');
        $(`#extraDobleDia${diaActualAlExceder9Horas}`).val('');
        $(`#extraTripleDia${diaActualAlExceder9Horas}`).val('');
        flagDiaActual = false;
        flagAplicanHoraExtraTriple = false;
    });
}


function calcularPrimaDominical(flagLlamadaDesdeJornada) {
    let valorInputJornadaNormalDomingo = ($(`#jornadaNormalDia1`).val()) || '';
    let valorJornadanormalEnMinutos = obtenerHorasAMinutos(valorInputJornadaNormalDomingo);
    //Si la jornada normal en domingo supera las 7 horas y no es un dia de descanso del trabajador entonces se suma la unidad de la prima dominical
    if (valorInputJornadaNormalDomingo !== "Descanso" && valorJornadanormalEnMinutos > 420) {
        let resultadoPrimaDominical = 1;
        $('#calculoPrimaDominical').val(resultadoPrimaDominical);
    } else {
        $('#calculoPrimaDominical').val('');
    }
    //Si se hace la llamada a la funcion desde los campos de la jornada normal entonces se verifican los abonos y descuentos que puedieran estar presentes para considerarlos
    if (flagLlamadaDesdeJornada === true) {
        let diferencia = restarNumeros($('#abonosPrimaDominical').val(), $('#descuentosPrimaDominical').val());
        calculoUnidades(diferencia, calcularPrimaDominical, 'calculoPrimaDominical');
    }
}
;

//Suma al cálculo de la jornada normal el excedente, considerar descanso
function calcularExcedenteJornada() {
    let calculoTotalJornada = $(`#calculoJornada`).val();
    let jornadaNormalMasExcedenteJornadaArray = [];
    //Si el valor total para el cálculo de la jornada está vacío entonces se limpian los campos con el excedente de la jornada
    if (!calculoTotalJornada) {
        toastr["warning"]("Se debe calcular primero la jornada normal para sumar el excedente", "Advertencia", {progressBar: true, closeButton: true});
        for (let i = 1; i <= 7; i++) {
            $(`#excedenteDia${i}`).val('');
        }
    } else {
        for (let i = 1; i <= 7; i++) {
            let valorElementoActual = $(`#excedenteDia${i}`).val();
            if (valorElementoActual !== '') {
                jornadaNormalMasExcedenteJornadaArray.push(valorElementoActual);
            }
        }
        //Se agrega al arreglo el calculo total de la jornada
        jornadaNormalMasExcedenteJornadaArray.push(recalculaTotalHoras('#jornadaNormalDia'));
        //Se le agregan los posibles abonos que puedan existir
        jornadaNormalMasExcedenteJornadaArray.push($("#abonosJornada").val());
        //Se coloca el valor total ya calculado aplicando si correspondiente redondeo
        let calculoJornadaFinal = ajustarTotalDeJornadaSegunCriteriosEstablecidos(sumarHorasArray(jornadaNormalMasExcedenteJornadaArray));
        $('#calculoJornada').val(calculoJornadaFinal);
    }
}
;

//Verificar porcentajes de suma, se le suma cierto procentaje respecto al valor ingresado, sigue pendiente
function calcularyMostrarTotalesJornada(flagLlamadaDesdeJornadaNormal) {
    //Si se hace la llamada desde la jornada normal entonces se consideran los posibles abonos o descuentos
    if (flagLlamadaDesdeJornadaNormal === true) {
        let diferenciaJornada = restarNumeros($('#abonosJornada').val(), $('#descuentosJornada').val());
        calculoUnidades(diferenciaJornada, calcularyMostrarTotalesJornada, 'calculoJornada');
        //Si no se hace la llamada desde los campos jornada normal entonces se realiza el calculo sin añadir los posibles descuentos, este caso solo aplica al llamar a la función desde calcularAbonosDescuentos()
    } else {
        let jornadaNormalArray = [];
        let totalesHorasDescanso = [];
        for (let i = 1; i <= 7; i++) {
            //La variable jornada normal contiene el día actual del recorrido
            let jornadaNormal = $(`#jornadaNormalDia${i}`).val();
            //La variable excedente que contiene el excedente de la jornada
            let excedenteJornada = $(`#excedenteDia${i}`).val();
            //Incidencias a considerar como descansos
            let incidenciasDia = $(`#inasistenciasDia${i}`).val();
            jornadaNormalArray.push(excedenteJornada || '');
            // Verificar si el valor es igual a "Descanso" antes de hacer push
            if (jornadaNormal !== "Descanso") {
                jornadaNormalArray.push(jornadaNormal || '');
                //Se obtienen las horas de descanso totales correspondientes por cada día ingresado
                if (jornadaNormal !== "") {
                    //Se agregan las horas de descanso si superan los 360 min (6 horas)
                    jornadaNormalArray.push(calcularHorasDescansoPorDia(jornadaNormal, i));
                    //Se colocan en un arreglo las horas de descanso asignadas por día
                    totalesHorasDescanso.push(calcularHorasDescansoPorDia(jornadaNormal));
                }
                //Si las incidencias son diferentes de el valor vacío entonces se busca si las incidencias son justificadas
                if (incidenciasDia !== "") {
                    //Si el trabajador tiene una incidencia que corresponde a una falta justiciada entonces se le suma el valor del descanso al día
                    jornadaNormalArray.push(sumarHorasDescansosEnFaltasJustificadas(incidenciasDia));
                    totalesHorasDescanso.push(sumarHorasDescansosEnFaltasJustificadas(incidenciasDia));
                }
            }
        }
        // Contar cuántas veces aparece "03:10" o los descansos en el arreglo
        let contador0310 = contarOcurrencias(jornadaNormalArray, "03:10");
        /* Si hay más de dos elementos que contienen "03:10", agregar "00:10" al final
         * Es decir, si se asignan más de dos descansos se agrega la diferencia del tercer día */
        if (contador0310 > 2) {
            jornadaNormalArray.push("00:10");
        }
        //Esta función solo coloca la cadena de las horas de descanso desglosadas por día
        calcularHorasDescansoTotalesTrabajadorLabel(totalesHorasDescanso);
        let totalJornadaNormal = sumarHorasArray(jornadaNormalArray);
        let calculoJornadaFinal = ajustarTotalDeJornadaSegunCriteriosEstablecidos(totalJornadaNormal === '00:00' ? '' : totalJornadaNormal);
        $('#calculoJornada').val(calculoJornadaFinal);
    }
}
;


//Regla de negocio, acorde a los minutos se realiza el ajuste correspondiente al total de la jornada
function ajustarTotalDeJornadaSegunCriteriosEstablecidos(hora) {
    //Si se recibe una hora vacía
    if (hora.trim() === '') {
        return;
    }
    let horasDelTotalDeLaJornada = obtenerHora(hora);
    let minutosDelTotalDeLaJornada = obtenerMinutos(hora);
    //Le corresponde el 0 
    if (minutosDelTotalDeLaJornada >= 0 && minutosDelTotalDeLaJornada <= 2) {
        minutosDelTotalDeLaJornada = 0;
        //Le corresponde el .3  
    } else if (minutosDelTotalDeLaJornada >= 3 && minutosDelTotalDeLaJornada <= 24) {
        minutosDelTotalDeLaJornada = 3;
        //Le corresponde el .5
    } else if (minutosDelTotalDeLaJornada >= 25 && minutosDelTotalDeLaJornada <= 39) {
        minutosDelTotalDeLaJornada = 5;
        //Le corresponde el .7
    } else if (minutosDelTotalDeLaJornada >= 40 && minutosDelTotalDeLaJornada <= 54) {
        minutosDelTotalDeLaJornada = 7;
        //Le corresponde el .0 y se le suma una hora al turno
    } else if (minutosDelTotalDeLaJornada >= 55) {
        horasDelTotalDeLaJornada += 1;
        minutosDelTotalDeLaJornada = 0;
    } else {
        console.error('No entró a ninguna condición el calculo de proporciones de tiempo total');
        return;
    }
    return horasDelTotalDeLaJornada + '.' + minutosDelTotalDeLaJornada;
}
;

//Pära identificar cuántos días de descanso se agregan al arreglo de la jornada normal y sumarle los 10 min al tercer día
function contarOcurrencias(arr, elemento) {
    return arr.reduce((contador, valor) => valor === elemento ? contador + 1 : contador, 0);
}
;

//Para indicar los totales en el label correspondiente
function calcularHorasDescansoTotalesTrabajadorLabel(totalesHorasDescanso) {
    var diasDescanso = $('#dias_descanso_trabajador').text();
    var diasArray = diasDescanso.split(' ');
    var primerDia, segundoDia;
    for (var i = 0; i < diasArray.length; i++) {
        // Verificar si la palabra actual es un día
        if (diasArray[i].endsWith(':')) {
            var nombreDia = diasArray[i];
            if (!primerDia) {
                primerDia = nombreDia;
            } else if (!segundoDia) {
                segundoDia = nombreDia;
                break;
            }
        }
    }
    // Verificar que el arreglo totalesHorasDescanso no contenga cadenas vacías
    totalesHorasDescanso = totalesHorasDescanso.filter(function (hora) {
        return hora.trim() !== '';
    });

    // 5 Días que superan las 6 horas 
    if (totalesHorasDescanso.length === 5) {
        $('#dias_descanso_trabajador').text(primerDia + ' 08:00 ' + segundoDia + ' 08:00 | Hrs. Desc. = 16:00');
    }
    // 4 días que superan las 6 horas
    else if (totalesHorasDescanso.length === 4) {
        $('#dias_descanso_trabajador').text(primerDia + ' 06:25 ' + segundoDia + ' 06:25 | Hrs. Desc. = 12:50');
    }
    // 3 días que superan las 6 horas
    else if (totalesHorasDescanso.length === 3) {
        $('#dias_descanso_trabajador').text(primerDia + ' 04:45 ' + segundoDia + ' 04:45 | Hrs. Desc. = 09:30');
    }
    // 2 Días que superan las 6 horas
    else if (totalesHorasDescanso.length === 2) {
        $('#dias_descanso_trabajador').text(primerDia + ' 03:10 ' + segundoDia + ' 03:10 | Hrs. Desc. = 06:20');
    }
    // 1 Día que supera las 6 horas
    else if (totalesHorasDescanso.length === 1) {
        $('#dias_descanso_trabajador').text(primerDia + ' 01:35 ' + segundoDia + ' 01:35 | Hrs. Desc. = 03:10');
    } else {
        $('#dias_descanso_trabajador').text(primerDia + ' 01:35 ' + segundoDia + ' 01:35 | Hrs. Desc. = 03:10');
    }
}

function calcularHorasDescansoPorDia(jornadaNormal) {
    let totalMinutosDia = obtenerHorasAMinutos(jornadaNormal);
    let horasDescansoCorrespondientesPorDia;
    if (totalMinutosDia >= 360) {
        horasDescansoCorrespondientesPorDia = "03:10";
        return horasDescansoCorrespondientesPorDia;
    } else {
        return'';
    }
}

function sumarHorasDescansosEnFaltasJustificadas(incidenciasDia) {
    switch (incidenciasDia) {
        case 'P.F':
        case 'P.A':
        case 'V':
        case 'RT100':
        case 'POS100':
        case 'PRE100':
        case 'EG100':
            return '03:10';
        default:
            return '';
    }
}

//Verificar las faltas del trabajador acorde a sus horas trabajadas e incidencias presentes
function calcularFaltasyAsistenciasTrabajador(flagLlamadaDesdeJornadaNormal) {
    let arrayDiasSinIncidenciasTrabajador = [];
    let arrayDiasConIncidenciasTrabajador = [];
    let arrayDiasDescansoaDescontarDeIncidencias = [];
    let acumuladores = {faltas: 0, asistencias: 0};
    // Se consulta si la captura abierta está seleccionada para descontar las faltas de los dos días de descanso
    let isCapturaAbiertaChecked = $('#capturaAbierta').prop("checked");
    // Buscamos los descansos previamente colocados en otra función así como las incidencias
    for (let i = 1; i <= 7; i++) {
        let jornadaNormal = ($(`#jornadaNormalDia${i}`).val()) || '';
        let incidenciasTrabajador = ($(`#inasistenciasDia${i}`).val()) || '';
        if (jornadaNormal === "Descanso") {
            arrayDiasDescansoaDescontarDeIncidencias.push(i);
        }
        if (incidenciasTrabajador === "") {
            arrayDiasSinIncidenciasTrabajador.push(i);
        }
        if (incidenciasTrabajador !== "") {
            arrayDiasConIncidenciasTrabajador.push(i);
        }
    }
    // Se descuentan los días de descanso del conteo
    let arrayDiasIncidenciasSinDescansos = arrayDiasSinIncidenciasTrabajador.filter(
            dia => !arrayDiasDescansoaDescontarDeIncidencias.includes(dia)
    );
    // Para contabilizar los días de paga si se superan las 8 horas
    arrayDiasIncidenciasSinDescansos.forEach((dia) => {
        verificaSeisHoras(dia, acumuladores);
    });
    // Para contabilizar las faltas acorde a los valores de las incidencias para cada día
    arrayDiasConIncidenciasTrabajador.forEach((dia) => {
        verificaSeisHoras(dia, acumuladores);
    });
    // Se descuentan los dos días de descanso si se realiza la captura abierta, ya que los descansos no se despliegan en estos casos pero siguen descontándose de las faltas
    if (isCapturaAbiertaChecked) {
        acumuladores.faltas -= 2;
    }
    // Se coloca el contador de faltas o asistencias
    $('#calculoFaltas').val(acumuladores.faltas <= 0 ? '' : acumuladores.faltas);
    $('#calculoDiasLaborados').val(acumuladores.asistencias <= 0 ? '' : acumuladores.asistencias);
    // Los días laborados son los días pagados
    $('#calculoPago').val(acumuladores.asistencias <= 0 ? '' : acumuladores.asistencias);
    // Los días de cve 27 son iguales a los días de pago y deben de restarse por cada día festivo en la semana 
    calcularCve27(acumuladores.asistencias <= 0 ? '' : acumuladores.asistencias);
    // Esta bandera es levantada solo cuando se realizan cambios directamente sobre la jornada normal y se hace para considerar los campos de abonos o descuentos si ya están ingresados
    if (flagLlamadaDesdeJornadaNormal === true) {
        let diferencia = restarNumeros($('#abonosDiasLaborados').val(), $('#descuentosDiasLaborados').val());
        calculoUnidades(diferencia, calcularFaltasyAsistenciasTrabajador, 'calculoDiasLaborados');
    }
}

function verificaSeisHoras(dia, acumuladores) {
    const valor = $(`#jornadaNormalDia${dia}`).val();
    const valorIncidencia = $(`#inasistenciasDia${dia}`).val();
    // Descuento de faltas para las incidencias que no generan falta
    const incidenciasQueNoGeneranFalta = ["EG0", "EG50", "EG60", "EG75", "EG100", "PRE100",
        "POS100", "RT0", "RT100", "V", "P.SINDICAL", "P.F", "P.A"];
    if (incidenciasQueNoGeneranFalta.includes(valorIncidencia)) {
        acumuladores.faltas -= 1;
    }
    // Se omiten los descansos en la jornada normal
    if (valor === 'Descanso') {
        return;
    }
    if (valor === '') {
        acumuladores.faltas += 1;
        return;
    }
    const [horas, minutos] = valor.split(':').map(Number);
    const totalMinutos = horas * 60 + minutos;

    // El valor del día es menor a 6 horas
    if (totalMinutos < 360) {
        acumuladores.faltas += 1;
        // Validar que solo sean 5 turnos
    } else if (acumuladores.asistencias < 5) {
        acumuladores.asistencias += 1;
    } else {
        toastr["warning"]("Se superaron los 5 turnos", "Advertencia", {progressBar: true, closeButton: true});
    }
}

function calcularCve27(acumAsistencias) {
    let acumFestivos = 0;
    let acumPermisos = 0;
    let totalCve27;
    //SE recorren las inasistencias para encontrar dias festivos
    for (let i = 1; i <= 7; i++) {
        if ($(`#inasistenciasDia${i}`).val() === 'DFEST') {
            acumFestivos += 1;
        }
        if ($(`#inasistenciasDia${i}`).val() === 'P.A') {
            acumPermisos += 1;
        }
        if ($(`#inasistenciasDia${i}`).val() === 'P.F') {
            acumPermisos += 1;
        }
    }
    totalCve27 = acumAsistencias - acumFestivos;
    totalCve27 = acumAsistencias - acumPermisos;
    // Validación ternaria para evitar valores negativos
    $('#calculoCve27').val(totalCve27 > 0 ? totalCve27 : '');
}

function calcularYMostrarTotalesSuplencias() {
    //Se limpia el valor del cálculo para realizarlo correctamente cada vez con los dstos actuales
    $('#calculoDescLaboradosSuplencia').val('');
    $('#calculoPrimaDominicalSuplencia').val('');
    $('#calculoTiempoExtra').val('');
    $('#calculoSueldo').val('');
    const horasATrabajar = 8;
    let diasConSuplenciaArray = [];
    let diasSeleccionados = [];
    let sueldoPorHoraTrabajador = $('#campo_sueldo_hora').val();
    let sueldoPorHoraSuplencia;
    let diferenciaSueldoHoraSuplenciaConSueldoTrabajador;
    let sueldoDiarioTrabajador = sueldoPorHoraTrabajador * horasATrabajar;
    let sueldoDiarioSuplencia;
    for (let i = 1; i <= 7; i++) {
        let diasConSuplencia = $(`#suplenciasDia${i}`).val() || '';
        if (diasConSuplencia !== '') {
            diasConSuplenciaArray.push(diasConSuplencia);
            diasSeleccionados.push(i.toString());
        }
    }
    if (diasConSuplenciaArray.length === 0) {
        return;
    }
    for (let i = 0; i < diasConSuplenciaArray.length; i++) {
        if (diasConSuplenciaArray[i] !== '') {
            sueldoPorHoraSuplencia = diasConSuplenciaArray[i];
            break;
        }
    }
    sueldoDiarioSuplencia = sueldoPorHoraSuplencia * horasATrabajar;
    //Se realiza el cálculo del valor de la suplencia
    diferenciaSueldoHoraSuplenciaConSueldoTrabajador = sueldoPorHoraSuplencia - sueldoPorHoraTrabajador;
    //Se calculan los descansos laborados
    calcularDescansosLaboradosSuplencia(diferenciaSueldoHoraSuplenciaConSueldoTrabajador);
    //Se calcula la prima dominical
    calcularPrimaDominicalSuplencia(sueldoDiarioTrabajador, sueldoDiarioSuplencia, diasSeleccionados);
    //Se calcula el tiempo extra
    calcularTiempoExtraSuplencia(diferenciaSueldoHoraSuplenciaConSueldoTrabajador);
    //Ya que se termina de asignar el sueldo de suplencia se pasa a la realización del cálculo del sueldo en la diferencia por suplencia
    calcularSueldoSuplencia(diferenciaSueldoHoraSuplenciaConSueldoTrabajador, diasSeleccionados);
}

function recalculaTotalHoras(inputSelector) {
    let horasArray = [];
    for (let i = 1; i <= 7; i++) {
        horasArray.push($(`${inputSelector}${i}`).val() || '');
        horasArray.push(calcularHorasDescansoPorDia($(`${inputSelector}${i}`).val() || ''));
    }
    let totalHoras = sumarHorasArray(horasArray);

    if (totalHoras !== "00:00") {
        return totalHoras;
    } else {
        return '';
    }
}

//Calcula los totales correspondientes a tiempo extra doble
function calcularyMostrarTotalesTiempoDoble(flagLlamadaDesdeTiempoExtra) {
    if (flagLlamadaDesdeTiempoExtra === true) {
        let diferenciaHoraDoble = restarHoras($('#abonosHoraDoble').val(), $('#descuentosHoraDoble').val());
        calculo(diferenciaHoraDoble, calcularyMostrarTotalesTiempoDoble, 'calculoTiempoDoble');
    } else {
        let extraDobleArray = [];
        for (let i = 1; i <= 7; i++) {
            let extraDobleDias = ($(`#extraDobleDia${i}`).val()) || '';
            //Generando totales de horas extras dobles
            extraDobleArray.push(extraDobleDias);
        }
        let totalTiempoExtraDoble = sumarHorasArray(extraDobleArray);
        $('#calculoTiempoDoble').val(totalTiempoExtraDoble);
    }
}
//Calcula los totales correspondientes a tiempo extra triple
function calcularyMostrarTotalesTiempoTriple(flagLlamadaDesdeTiempoExtra) {
    if (flagLlamadaDesdeTiempoExtra === true) {
        let diferenciaHoraTriple = restarHoras($('#abonosHorasTriples').val(), $('#descuentosHoraTriple').val());
        calculo(diferenciaHoraTriple, calcularyMostrarTotalesTiempoTriple, 'calculoTiempoTriple');
    } else {
        let extraTripleArray = [];
        for (let i = 1; i <= 7; i++) {
            let extraTripleDias = ($(`#extraTripleDia${i}`).val()) || '';
            //Generando totales de horas extras triples
            extraTripleArray.push(extraTripleDias);
        }
        let totalTiempoExtraTriple = sumarHorasArray(extraTripleArray);
        $('#calculoTiempoTriple').val(totalTiempoExtraTriple);
    }
}

//Verificar porcentajes de suma, se le suma cierto procentaje respecto al valor ingresado
function calcularyMostrarTotalesDescansosLaborados(flagLlamadaDesdeDescansosLaborados) {
    if (flagLlamadaDesdeDescansosLaborados === true) {
        let diferenciaDescansosLaborados = restarHoras($('#abonosDescansosLaborados').val(), $('#descuentosDescansosLaborados').val());
        calculo(diferenciaDescansosLaborados, calcularyMostrarTotalesDescansosLaborados, 'calculoDescLaborados');
    } else {
        let descLaboradosArray = [];
        for (let i = 1; i <= 7; i++) {
            let diasPaseosLaborados = ($(`#paseosLabDia${i}`).val()) || '';
            //Generando totales de descansos laborados
            descLaboradosArray.push(diasPaseosLaborados);
        }
        let totalDescansosLaborados = sumarHorasArray(descLaboradosArray);
        $('#calculoDescLaborados').val(totalDescansosLaborados);
    }
}

//Para abonos y descuentos
function calculaDiasFestivos() {
    let diasFestivosArray = [];
    for (let i = 1; i <= 7; i++) {
        if ($(`#inasistenciasDia${i}`).val() === 'DFEST') {
            diasFestivosArray.push('08:00');
        }
    }
    let totalHorasDiasFestivos = sumarHorasArray(diasFestivosArray);
    $('#calculoFestivos').val(totalHorasDiasFestivos);
}

function calcularAbonosDescuentos() {
    //ABONOS Y DESCUENTOS JORNADA
    let inputAbonoJornada = obtenerValorJquery("abonosJornada");
    let inputDescuentoJornada = obtenerValorJquery("descuentosJornada");
    //ABONOS Y DESCUENTOS HORAS EXTRAS DOBLES Y TRIPLES
    let inputAbonoHoraDoble = obtenerValorJquery("abonosHoraDoble");
    let inputAbonoHoraTriple = obtenerValorJquery("abonosHorasTriples");
    let inputDescuentoHoraDoble = obtenerValorJquery("descuentosHoraDoble");
    let inputDescuentoHoraTriple = obtenerValorJquery("descuentosHoraTriple");
    //ABONOS Y DESCUENTOS DESCANSOS LABORADOS
    let inputAbonoDescansosLaborados = obtenerValorJquery("abonosDescansosLaborados");
    let inputDescuentoDescansosLaborados = obtenerValorJquery("descuentosDescansosLaborados");
    //ABONOS Y DESCUENTOS A LA PRIMA DOMINICAL
    let inputAbonoPrimaDominical = obtenerValorJquery("abonosPrimaDominical");
    let inputDescuentosPrimaDominical = obtenerValorJquery("descuentosPrimaDominical");
    //ABONOS Y DESCUENTOS AL DIA FESTIVO
    let inputAbonoFestivos = obtenerValorJquery("abonosFestivos");
    let inputDescuentosFestivos = obtenerValorJquery("descuentosFestivos");
    //ABONOS Y DESCUENTOS A LOS DIAS LABORADOS
    let inputAbonoDiasLaborados = obtenerValorJquery("abonosDiasLaborados");
    let inputDescuentoDiasLaborados = obtenerValorJquery("descuentosDiasLaborados");
    //ABONOS Y DESCUENTOS A LA DIFERENCIA POR SUPLENCIA 
    let inputAbonosDiferenciaSuplencia = obtenerValorJquery("abonosDiferenciaSuplencia");
    let inputDescuentoDiferenciaSuplencia = obtenerValorJquery("descuentosDiferenciaSuplencia");
    //OMISIONES POR IMPORTE 
    let inputOmisionPorImporte = obtenerValorJquery("omisionPorImporte");
    /*TOTAL DE ABONOS O DESCUENTOS A REALIZAR 
     * JORNADA*/
    let diferenciaJornada = restarNumeros(inputAbonoJornada, inputDescuentoJornada);
    calculoUnidades(diferenciaJornada, calcularyMostrarTotalesJornada, 'calculoJornada', true);
    //HORAS DOBLES
    let diferenciaHorasDobles = restarHoras(inputAbonoHoraDoble, inputDescuentoHoraDoble);
    calculo(diferenciaHorasDobles, calcularyMostrarTotalesTiempoDoble, 'calculoTiempoDoble');
    //HORAS TRIPLES
    let diferenciaHoraTriple = restarHoras(inputAbonoHoraTriple, inputDescuentoHoraTriple);
    calculo(diferenciaHoraTriple, calcularyMostrarTotalesTiempoTriple, 'calculoTiempoTriple');
    //DESCANSOS LABORADOS
    let diferenciaDescansosLaborados = restarHoras(inputAbonoDescansosLaborados, inputDescuentoDescansosLaborados);
    calculo(diferenciaDescansosLaborados, calcularyMostrarTotalesDescansosLaborados, 'calculoDescLaborados');
    //PRIMA DOMINICAL
    let diferenciaPrimaDominical = restarNumeros(inputAbonoPrimaDominical, inputDescuentosPrimaDominical);
    calculoUnidades(diferenciaPrimaDominical, calcularPrimaDominical, 'calculoPrimaDominical');
    //FESTIVOS
    let diferenciaDiasFestivos = restarHoras(inputAbonoFestivos, inputDescuentosFestivos);
    calculo(diferenciaDiasFestivos, calculaDiasFestivos, 'calculoFestivos');
    //OMISIONES
    let valorDeLaOmision = inputOmisionPorImporte;
    $('#calculoOmisiones').val(valorDeLaOmision);
    //DIAS LABORADOS
    let diferenciaDiasLaborados = restarNumeros(inputAbonoDiasLaborados, inputDescuentoDiasLaborados);
    calculoUnidades(diferenciaDiasLaborados, calcularFaltasyAsistenciasTrabajador, 'calculoDiasLaborados');
    //DIFERENCIAS EN SUPLENCIA
    let diferenciaSuplencia = restarNumeros(inputAbonosDiferenciaSuplencia, inputDescuentoDiferenciaSuplencia);
    calculoUnidades(diferenciaSuplencia, calcularYMostrarTotalesSuplencias, 'calculoSueldo');
}

function ajustarExtraDoble() {
    const valores = [
        $("#extraDobleDia1").val() || "0",
        $("#extraDobleDia2").val() || "0",
        $("#extraDobleDia3").val() || "0",
        $("#extraDobleDia4").val() || "0",
        $("#extraDobleDia5").val() || "0",
        $("#extraDobleDia6").val() || "0",
        $("#extraDobleDia7").val() || "0"
    ];

    // Sumar las horas
    const sumaTotal = sumarHorasArray(valores);

    // Verificar si la suma excede las 9:00 horas
    if (compararHoras(sumaTotal, "09:00")) {
        // Ajustar el último input para que la suma sea 9:00 horas
        for (let i = 6; i >= 0; i--) {
            const inputActual = $(`#extraDobleDia${i + 1}`);
            const valorActual = inputActual.val();
            if (valorActual !== "") {
                const ajuste = restarHoras(sumaTotal, "09:00");
                const valorFinal = restarHoras(valorActual, ajuste);
                if (valorFinal.result === "00:00") {
                    inputActual.val('');
                } else {
                    inputActual.val(valorFinal);
                }
                break;
            }
        }
    }
}

//********************************************CONVERSIONES, SUMAS Y RESTAS DE HORAS*****************************************
function minutosAHoras(minutos) {
    let horas = Math.floor(minutos / 60);
    let minutosRestantes = minutos % 60;
    return `${horas.toString().padStart(2, '0')}:${minutosRestantes.toString().padStart(2, '0')}`;
}

function sumarHorasArray(horasArray) {
    // Inicializar minutos totales
    let sumaMinutos = 0;
    // Recorrer todas las horas en el array
    horasArray.forEach(hora => {
        // Verificar si la cadena de horas no está vacía
        if (hora.trim() !== '') {
            // Convertir la cadena de horas en minutos
            let minutos = parseInt(hora.split(":")[0]) * 60 + parseInt(hora.split(":")[1]);
            // Verificar si el resultado de parseInt no es NaN
            if (!isNaN(minutos)) {
                // Sumar los minutos
                sumaMinutos += minutos;
            }
        }
    });
    // Calcular las horas y minutos finales
    let horas = Math.floor(sumaMinutos / 60);
    let minutos = sumaMinutos % 60;
    //Si la suma de los elementos es cero entonces se retorna la cadena vacía
    if (horas === 0 && minutos === 0) {
        return '';
    }
    // Formatear el resultado
    return `${horas.toString().padStart(2, '0')}:${minutos.toString().padStart(2, '0')}`;
}
// Función para restar horas en formato HH:mm, dependiendo de lo que se reciba indica si es una suma, resta para la parte de abonos y descuentos
function restarHoras(hora1, hora2) {
    if (!hora1 && !hora2) {
        return {flag: 'ambas horas vacías', result: ''};// Ambas horas no están presentes, retornar cadena vacía
    }
    if (!hora1) {
        return {flag: 'resta', result: hora2 || ''}; // Solo se proporcionó hora2, retorna una bandera 'resta' y la hora2 o cadena vacía si hora2 no está presente
    }
    if (!hora2) {
        return {flag: 'suma', result: hora1 || ''}; // Solo se proporcionó hora1, retorna una bandera 'suma' y la hora1 o cadena vacía si hora1 no está presente
    }
    let minutos1 = obtenerHorasAMinutos(hora1 || '00:00');
    let minutos2 = obtenerHorasAMinutos(hora2 || '00:00');
    let restaMinutos = minutos1 - minutos2;

    if (restaMinutos < 0 || isNaN(restaMinutos)) {
        return '';
    }

    let horas = Math.floor(restaMinutos / 60);
    let minutos = restaMinutos % 60;
    return {flag: 'Diferencia', result: `${horas.toString().padStart(2, '0')}:${minutos.toString().padStart(2, '0')}`};
}

function restarNumeros(num1, num2) {
    if (!num1 && !num2) {
        return {flag: 'ambos números vacíos', result: ''}; // Ambos números no están presentes, retornar objeto con bandera y cadena vacía
    }
    if (!num1) {
        return {flag: 'resta', result: num2 || ''}; // Solo se proporcionó num2, retorna objeto con bandera 'resta' y num2 o cadena vacía si num2 no está presente
    }
    if (!num2) {
        return {flag: 'suma', result: num1 || ''}; // Solo se proporcionó num1, retorna objeto con bandera 'suma' y num1 o cadena vacía si num1 no está presente
    }
    const numero1 = num1 || 0;
    const numero2 = num2 || 0;
    const resta = numero1 - numero2;
    return resta < 0 || isNaN(resta) ? '' : {flag: 'Diferencia', result: resta.toString()};
}

function compararHoras(hora1, hora2) {
    let minutos1 = obtenerHorasAMinutos(hora1);
    let minutos2 = obtenerHorasAMinutos(hora2);
    return minutos1 > minutos2;
}

function obtenerHorasAMinutos(hora) {
    if (hora === undefined) {
        return;
    }
    if (hora === '') {
        return;
    }
    return parseInt(hora.split(":")[0]) * 60 + parseInt(hora.split(":")[1]);
}

function obtenerHora(hora) {
    if (hora === undefined) {
        return;
    }

    return parseInt(hora.split(":")[0]);
}

function obtenerMinutos(hora) {
    if (hora === undefined) {
        return;
    }

    return parseInt(hora.split(":")[1]);
}

// Calcular la diferencia en días entre dos fechas
function obtenerDiferenciaDias(fechaInicio, fechaFin) {
    let diferenciaEnTiempo = fechaFin - fechaInicio;
    let diferenciaEnDias = Math.floor(diferenciaEnTiempo / (1000 * 3600 * 24));
    return diferenciaEnDias;
}

//*******************************************INFORMACIÓN A MOSTRAR DEL TRABAJADOR ******************************************

function obtenerInformacionTrabajador() {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + idTrabajador,
        dataType: 'json',
        success: function (data) {
            $('#campo_expediente').val(data.data.cat_expediente.numero_expediente);
            $('#campo_nombre').val(data.data.persona.nombre + " " + data.data.persona.apellido_paterno + " " + data.data.persona.apellido_materno);
            //Se busca la línea del trabajador por el expediente dado de alta en la tabla historico_tmp_libro_ra15 
            obtenerInformacionLineaTrabajador(data.data.cat_expediente.numero_expediente);
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener información del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

function obtenerInformacionLineaTrabajador(expediente) {
    let expedienteEntero = parseInt(expediente);
    $.ajax({
        type: "GET",
        url: "ra15/encontrarLineaPorExpediente/" + expedienteEntero,
        success: function (data) {
            if (!$.isEmptyObject(data)) {
                $('#campo_linea').val(data);
            } else {
                $('#campo_linea').val("Sin línea");
            }
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener línea del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

function obtenerInformacionPuestoTrabajador() {
    $.ajax({
        type: "GET",
        url: "plaza/buscarPlazaTrabajador/" + idTrabajador,
        dataType: 'json',
        success: function (dataPuestoTrabajador) {
            let sueldoDiarioTrabajador = parseFloat(dataPuestoTrabajador[0].sueldoDiarioDto);
            sueldoDiarioTrabajador = sueldoDiarioTrabajador.toFixed(2);
            let calculoSueldoHoraTrabajador = sueldoDiarioTrabajador / 8;
            calculoSueldoHoraTrabajador = calculoSueldoHoraTrabajador.toFixed(2);
            $('#campo_puesto_trabajador').val(dataPuestoTrabajador[0].puestoDto);
            $('#campo_sueldo_diario_trabajador').val(sueldoDiarioTrabajador);
            $('#campo_sueldo_hora').val(calculoSueldoHoraTrabajador);
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener plaza del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Para obtener el total de horas autorizadas para el turno a pagar
function obtenerInformacionHorarioTrabajador() {
    let arrayHorasAPagarTrabajador = [];
    let horaFormateada;
    let horas;
    let minutos;
    $.ajax({
        type: "GET",
        url: "horarios/buscarHorario/" + idTrabajador,
        dataType: 'json',
        success: function (dataHorario) {
            if (dataHorario.length === 0) {
                toastr["warning"]("El trabajador no tiene un horario asignado", "Advertencia", {progressBar: true, closeButton: true});
            } else {
                //Suma de horas totales para el turno autorizado
                dataHorario.forEach(elemento => {
                    //Se convierten las horas de decimal a formato (HH:MM)
                    horas = Math.floor(elemento.horas_pago);
                    minutos = Math.round((elemento.horas_pago % 1) * 100);
                    horas = Math.min(horas);
                    minutos = Math.min(minutos, 59);
                    const horasFormateadas = horas.toString().padStart(2, '0');
                    const minutosFormateados = minutos.toString().padStart(2, '0');
                    horaFormateada = `${horasFormateadas}:${minutosFormateados}`;
                    if (horaFormateada !== '00:00') {
                        arrayHorasAPagarTrabajador.push(horaFormateada);
                    }
                    if (elemento.horas_pago === null && elemento.dia_descanso === 1) {
                        //Se agregan las 8 horas de cada día de descanso que tiene el trabajador
                        arrayHorasAPagarTrabajador.push('08:00');
                    }
                });
                $('#total_horas_turno_autorizado').val(sumarHorasArray(arrayHorasAPagarTrabajador));
                let arregloDepositosNoVacios = encontrarDepositosNoVacios(dataHorario);
                if (arregloDepositosNoVacios.length > 0) {
                    // Verificar si todos los depósitos son iguales
                    var todosIguales = arregloDepositosNoVacios.every(function (horario) {
                        return horario === arregloDepositosNoVacios[0];
                    });
                    //Se verifica si el trabajador tiene mismo depósito toda la semana
                    if (todosIguales) {
                        $("#campo_deposito").val(arregloDepositosNoVacios[0]);
                    } else {
                        $("#campo_deposito").val("Variable");
                    }
                } else {
                    $("#campo_deposito").val("Sin depósito");
                }
            }
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener horario del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

//Colocar información disponible del depósito
function encontrarDepositosNoVacios(datos) {
    var depositosNoVacios = [];
    for (var i = 0; i < datos.length; i++) {
        if (datos[i].cat_Depositos !== null) {
            var depositos = datos[i].cat_Depositos.desc_deposito;
            depositosNoVacios.push(depositos);
        }
    }
    return depositosNoVacios;
}

function removerDescansosTrabajador() {
    let valorJornada;
    for (let i = 1; i <= 7; i++) {
        valorJornada = $(`#jornadaNormalDia${i}`).val();
        if (valorJornada === 'Descanso') {
            $(`#jornadaNormalDia${i}`).val('');
            $(`#jornadaNormalDia${i}`).prop('disabled', false).css('font-weight', '');
            $(`#excedenteDia${i}`).prop('disabled', false);
        }
    }
}

function obtenerInformacionDescansosTrabajador() {
    $.ajax({
        type: "GET",
        url: "horarios/buscarDiasDescansoTrabajador/" + idTrabajador,
        dataType: 'json',
        success: function (dataDiasDescanso) {
            if (dataDiasDescanso.length === 0) {
                $("#campo_descansos_trabajador").val('No hay días registrados');
            } else {
                var inicialesDias = {};
                var ordenDiasSemana = ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"];

                for (var i = 0; i < dataDiasDescanso.length; i++) {
                    var idDia = dataDiasDescanso[i].cat_dias.id_dia;
                    var dia = dataDiasDescanso[i].cat_dias.dia;
                    var inicialDia = dia ? dia.charAt(0) : '';
                    if (dia === "Miércoles") {
                        inicialDia = "Mi";
                    }
                    inicialesDias[dia] = inicialDia;
                    //Se identifican los días de descanso comenzando en domingo = 1
                    $(`#jornadaNormalDia${idDia}`).prop('disabled', true).css('font-weight', 'bold').val('Descanso');
                    //Se deshabilitan los campos que corresponden al día de descanso
                    $(`#jornadaNormalDia${idDia}`).prop('disabled', true);
                    $(`#excedenteDia${idDia}`).prop('disabled', true);
                    //Verificar si se deshabilita tiempo extra también
                    //$(`#tiempoExtraLabDia${idDia}`).prop('disabled', true);
                }
                // Ordenar el objeto inicialesDias según el orden de los días de la semana
                var diasOrdenados = ordenDiasSemana.reduce(function (acc, dia) {
                    if (inicialesDias[dia]) {
                        acc[dia] = inicialesDias[dia];
                    }
                    return acc;
                }, {});

                var cadenaDescansos = Object.values(diasOrdenados).join('-');
                $("#campo_descansos_trabajador").val(cadenaDescansos);
            }
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener descansos del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

//***************************************BUSQUEDA DE INCIDENCIAS EN KARDEX PARA EL PERIODO*********************************

function actualizarRegistroIncidencias() {
    $("#confirmacionBuscarIncidenciasNuevas").submit(function (event) {
        event.preventDefault();
        let valorPeriodoActual = $('#periodoTransportacion').val();
        let textoDelSelect = $("#periodoTransportacion option:selected").text();
        //Para colocar días festivos en caso de que sean agregados después al actualizar el registro de las inasistencias
        definirDiasEncabezadoTabla(textoDelSelect);
        //Se busca el periodo para formatear las fechas iniciales y finales y desde la función se manda a llamar a la función que busca las incidencias del kardex del periodo que recibe las fechas formateadas
        buscarPeriodoPagoId(valorPeriodoActual);
        $("#confirmacionBuscarIncidenciasNuevas").modal("hide");
    });
}

function buscarIncidenciasKardexPeriodo(fechaInicio, fechaFin) {
    /*Se llama a la función que limpia todos los campos de inasistencias manteniendo los días festivos
     * si se llama a la función desde la actualización de incidencias
     */
    limpiarInasistenciasManteniendoFestivos();
    $.ajax({
        type: "GET",
        url: "incidencias/buscarIncidenciasTrabajadorTransportacion/" + fechaInicio + "/" + fechaFin + "/" + idTrabajador,
        dataType: 'json',
        success: function (dataIncidenciasKardexPeriodo) {
            if ($.isEmptyObject(dataIncidenciasKardexPeriodo)) {
                toastr["info"]("No hay inasistencias en el periodo actual", "Información", {progressBar: true, closeButton: true});
            } else {
                toastr["info"]("Existen inasistencias en el periodo actual", "Información", {progressBar: true, closeButton: true});
                colocarIncidenciasKardexEnTabla(dataIncidenciasKardexPeriodo, fechaInicio, fechaFin);
                /*Se vuelven a calcular las faltas y asistencias del trabajador por si se están actualizando los registros
                 desde el botón de inasistencias*/
                calcularFaltasyAsistenciasTrabajador();
                /*Se vuelve a calcular la jornada normal del trabajador por si se están actualizando los registros
                 desde el botón de inasistencias*/
                calcularyMostrarTotalesJornada();
                /*Se vuelve a calcular la prima dominical del trabajador por si se están actualizando los registros
                 desde el botón de inasistencias*/
                calcularPrimaDominical();
            }
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener incidencias del kardex en el periodo especificado" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

function buscarPeriodosTransportacion() {
    let fechaInicialSeleccionada;
    let fechaFinalSeleccionada;
    $.ajax({
        type: "GET",
        url: "trabajador/buscaPeriodosFechas/" + nominaTransportacion,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#periodoTransportacion').empty();
                var vselected = "";
                if (data.length > 0) {
                    //Se utiliza data en la posicion cero para obtener el primer periodo no pagado
                    for (var x = 0; x < data.length; x++) {
                        var n_periodo = data[x].periodo;
                        var fecha_inicial_formato = new Date(data[0].fecha_inicial);
                        var dias_inicial = fecha_inicial_formato.getDate();
                        var meses_inicial = fecha_inicial_formato.getMonth() + 1;
                        var anios_inicial = fecha_inicial_formato.getFullYear();

                        var fecha_final_formato = new Date(data[0].fecha_final);
                        var dias_final = fecha_final_formato.getDate();
                        var meses_final = fecha_final_formato.getMonth() + 1;
                        var anios_final = fecha_final_formato.getFullYear();
                        $('#periodoTransportacion').append('<option value="' + data[0].periodo + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
                                '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>');
                        //Se obtiene el primer registro que corresponde al periodo no pagado más cercano
                        if (x === 0) {
                            var formattedFechaInicial = fecha_inicial_formato.getFullYear() + '-' + (fecha_inicial_formato.getMonth() + 1).toString().padStart(2, '0') + '-' + fecha_inicial_formato.getDate().toString().padStart(2, '0');
                            var formattedFechaFinal = fecha_final_formato.getFullYear() + '-' + (fecha_final_formato.getMonth() + 1).toString().padStart(2, '0') + '-' + fecha_final_formato.getDate().toString().padStart(2, '0');
                            fechaInicialSeleccionada = formattedFechaInicial;
                            fechaFinalSeleccionada = formattedFechaFinal;
                            //Cuando se completa la llamada se buscan las incidencias del trabajador para el periodo correspondiente
                            buscarIncidenciasKardexPeriodo(fechaInicialSeleccionada, fechaFinalSeleccionada);
                            //Se coloca el día del encabezado correspondiente 
                            let textoDelSelect = $("#periodoTransportacion option:selected").text();
                            definirDiasEncabezadoTabla(textoDelSelect);
                        }
                    }
                    //Al terminar de recuperar los periodos se obtienen los registros que pudieran existir con base en el id del trabajador y el periodo actual
                    recuperarCapturaSemanal(data[0].periodo);
                }
            }
        },
        error: function (e) {
            toastr["error"](e, "Error al obtener periodos de la nómina", {progressBar: true, closeButton: true});
        }
    });
}

function buscarPeriodoPagoId(idPeriodoPago) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPeriodoPago/" + idPeriodoPago + "/" + nominaTransportacion,
        dataType: 'json',
        success: function (dataPeriodoPagoId) {
            var fecha_inicial_formato = new Date(dataPeriodoPagoId.data.fecha_inicial);
            var fecha_final_formato = new Date(dataPeriodoPagoId.data.fecha_final);
            var formattedFechaInicial = fecha_inicial_formato.getFullYear() + '-' + (fecha_inicial_formato.getMonth() + 1).toString().padStart(2, '0') + '-' + fecha_inicial_formato.getDate().toString().padStart(2, '0');
            var formattedFechaFinal = fecha_final_formato.getFullYear() + '-' + (fecha_final_formato.getMonth() + 1).toString().padStart(2, '0') + '-' + fecha_final_formato.getDate().toString().padStart(2, '0');
            buscarIncidenciasKardexPeriodo(formattedFechaInicial, formattedFechaFinal);
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener plaza del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

function colocarIncidenciasKardexEnTabla(data, inicioSemana, finSemana) {
    let partesFechaInicio = inicioSemana.split("-");
    let fechaInicio = new Date(partesFechaInicio[0], partesFechaInicio[1] - 1, partesFechaInicio[2]);
    let partesFechaFin = finSemana.split("-");
    let fechaFin = new Date(partesFechaFin[0], partesFechaFin[1] - 1, partesFechaFin[2]);

    let diferenciaEnDias = obtenerDiferenciaDias(fechaInicio, fechaFin);

    // Crear un array con el rango de días
    let rangoDias = [];
    //Crear un array con el rango de días de la incidencia
    let rangoDiasIncidencia = [];
    //Obtener los dias del periodo 
    for (let i = 0; i <= diferenciaEnDias; i++) {
        const dia = new Date(fechaInicio);
        dia.setDate(fechaInicio.getDate() + i);
        const numeroDia = dia.getDate();
        rangoDias.push(numeroDia);
    }

    for (let i = 0; i < data.length; i++) {
        //Inicio y fin de la incidencia
        let fechaInicio = new Date(data[i].fecha_inicio);
        let fechaFin = new Date(data[i].fecha_fin);
        //const diaSemana = fechaInicio.getDay();
        let diferenciaEnDias = obtenerDiferenciaDias(fechaInicio, fechaFin);

        //Si la diferencia en días es mayor a cero entonces es una incidencia que tiene una fecha de fin diferente a la inicial
        if (diferenciaEnDias > 0) {
            for (let i = 0; i <= diferenciaEnDias; i++) {
                const dia = new Date(fechaInicio);
                dia.setDate(fechaInicio.getDate() + i);
                const numeroDia = dia.getDate();
                rangoDiasIncidencia.push(numeroDia);
            }
            //Para cada item se evalua si corresponde a un día del periodo
            rangoDiasIncidencia.forEach((item) => {
                switch (item) {
                    case rangoDias[0]:
                        $(`#inasistenciasDia1`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                        $(`#jornadaNormalDia1`).val('').prop('disabled', true);
                        $(`#excedenteDia1`).val('').prop('disabled', true);
                        $(`#tiempoExtraLabDia1`).val('').prop('disabled', true);
                        $(`#paseosLabDia1`).val('').prop('disabled', true);
                        break;
                    case rangoDias[1]:
                        $(`#inasistenciasDia2`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                        $(`#jornadaNormalDia2`).val('').prop('disabled', true);
                        $(`#excedenteDia2`).val('').prop('disabled', true);
                        $(`#tiempoExtraLabDia2`).val('').prop('disabled', true);
                        $(`#paseosLabDia2`).val('').prop('disabled', true);
                        break;
                    case rangoDias[2]:
                        $(`#inasistenciasDia3`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                        $(`#jornadaNormalDia3`).val('').prop('disabled', true);
                        $(`#excedenteDia3`).val('').prop('disabled', true);
                        $(`#tiempoExtraLabDia3`).val('').prop('disabled', true);
                        $(`#paseosLabDia3`).val('').prop('disabled', true);
                        break;
                    case rangoDias[3]:
                        $(`#inasistenciasDia4`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                        $(`#jornadaNormalDia4`).val('').prop('disabled', true);
                        $(`#excedenteDia4`).val('').prop('disabled', true);
                        $(`#tiempoExtraLabDia4`).val('').prop('disabled', true);
                        $(`#paseosLabDia4`).val('').prop('disabled', true);
                        break;
                    case rangoDias[4]:
                        $(`#inasistenciasDia5`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                        $(`#jornadaNormalDia5`).val('').prop('disabled', true);
                        $(`#excedenteDia5`).val('').prop('disabled', true);
                        $(`#tiempoExtraLabDia5`).val('').prop('disabled', true);
                        $(`#paseosLabDia5`).val('').prop('disabled', true);
                        break;
                    case rangoDias[5]:
                        $(`#inasistenciasDia6`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                        $(`#jornadaNormalDia6`).val('').prop('disabled', true);
                        $(`#excedenteDia6`).val('').prop('disabled', true);
                        $(`#tiempoExtraLabDia6`).val('').prop('disabled', true);
                        $(`#paseosLabDia6`).val('').prop('disabled', true);
                        break;
                    case rangoDias[6]:
                        $(`#inasistenciasDia7`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                        $(`#jornadaNormalDia7`).val('').prop('disabled', true);
                        $(`#excedenteDia7`).val('').prop('disabled', true);
                        $(`#tiempoExtraLabDia7`).val('').prop('disabled', true);
                        $(`#paseosLabDia7`).val('').prop('disabled', true);
                        break;
                    default:
                        break;
                }
            });
            //Se trata de una incidencia de un solo día
        } else {
            let diaSemana = fechaInicio.getDate();
            switch (diaSemana) {
                case rangoDias[0]:
                    $(`#inasistenciasDia1`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                    $(`#jornadaNormalDia1`).val('').prop('disabled', true);
                    $(`#excedenteDia1`).val('').prop('disabled', true);
                    $(`#tiempoExtraLabDia1`).val('').prop('disabled', true);
                    $(`#paseosLabDia1`).val('').prop('disabled', true);
                    break;
                case rangoDias[1]:
                    $(`#inasistenciasDia2`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                    $(`#jornadaNormalDia2`).val('').prop('disabled', true);
                    $(`#excedenteDia2`).val('').prop('disabled', true);
                    $(`#tiempoExtraLabDia2`).val('').prop('disabled', true);
                    $(`#paseosLabDia2`).val('').prop('disabled', true);
                    break;
                case rangoDias[2]:
                    $(`#inasistenciasDia3`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                    $(`#jornadaNormalDia3`).val('').prop('disabled', true);
                    $(`#excedenteDia3`).val('').prop('disabled', true);
                    $(`#tiempoExtraLabDia3`).val('').prop('disabled', true);
                    $(`#paseosLabDia3`).val('').prop('disabled', true);
                    break;
                case rangoDias[3]:
                    $(`#inasistenciasDia4`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                    $(`#jornadaNormalDia4`).val('').prop('disabled', true);
                    $(`#excedenteDia4`).val('').prop('disabled', true);
                    $(`#tiempoExtraLabDia4`).val('').prop('disabled', true);
                    $(`#paseosLabDia4`).val('').prop('disabled', true);
                    break;
                case rangoDias[4]:
                    $(`#inasistenciasDia5`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                    $(`#jornadaNormalDia5`).val('').prop('disabled', true);
                    $(`#excedenteDia5`).val('').prop('disabled', true);
                    $(`#tiempoExtraLabDia5`).val('').prop('disabled', true);
                    $(`#paseosLabDia5`).val('').prop('disabled', true);
                    break;
                case rangoDias[5]:
                    $(`#inasistenciasDia6`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                    $(`#jornadaNormalDia6`).val('').prop('disabled', true);
                    $(`#excedenteDia6`).val('').prop('disabled', true);
                    $(`#tiempoExtraLabDia6`).val('').prop('disabled', true);
                    $(`#paseosLabDia6`).val('').prop('disabled', true);
                    break;
                case rangoDias[6]:
                    $(`#inasistenciasDia7`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                    $(`#jornadaNormalDia7`).val('').prop('disabled', true);
                    $(`#excedenteDia7`).val('').prop('disabled', true);
                    $(`#tiempoExtraLabDia17`).val('').prop('disabled', true);
                    $(`#paseosLabDia7`).val('').prop('disabled', true);
                    break;
                default:
                    break;
            }
        }
    }
}

//********************************GUARDADO, EDICIÓN Y RECUPERACIÓN DE LA CAPTURA SEMANAL DEL TRABAJADOR*************************
function guardarCapturaSemanal() {
    let periodoAplicacionId = obtenerValorJquery('periodoTransportacion');
    periodoAplicacionId = parseInt(periodoAplicacionId);
    //*******************EXCEDENTES JORNADA*****************
    let excedenteJornadaDomingo = obtenerValorJquery('excedenteDia1');
    let excedenteJornadaLunes = obtenerValorJquery('excedenteDia2');
    let excedenteJornadaMartes = obtenerValorJquery('excedenteDia3');
    let excedenteJornadaMiercoles = obtenerValorJquery('excedenteDia4');
    let excedenteJornadaJueves = obtenerValorJquery('excedenteDia5');
    let excedenteJornadaViernes = obtenerValorJquery('excedenteDia6');
    let excedenteJornadaSabado = obtenerValorJquery('excedenteDia7');
    //*******************JORNADA NORMAL*********************
    let jornadaNormalDomingo = obtenerValorJquery('jornadaNormalDia1');
    let jornadaNormalLunes = obtenerValorJquery('jornadaNormalDia2');
    let jornadaNormalMartes = obtenerValorJquery('jornadaNormalDia3');
    let jornadaNormalMiercoles = obtenerValorJquery('jornadaNormalDia4');
    let jornadaNormalJueves = obtenerValorJquery('jornadaNormalDia5');
    let jornadaNormalViernes = obtenerValorJquery('jornadaNormalDia6');
    let jornadaNormalSabado = obtenerValorJquery('jornadaNormalDia7');
    //*******************TIEMPO EXTRA***************************
    let tiempoExtraLaboradoDomingo = obtenerValorJquery('tiempoExtraLabDia1');
    let tiempoExtraLaboradoLunes = obtenerValorJquery('tiempoExtraLabDia2');
    let tiempoExtraLaboradoMartes = obtenerValorJquery('tiempoExtraLabDia3');
    let tiempoExtraLaboradoMiercoles = obtenerValorJquery('tiempoExtraLabDia4');
    let tiempoExtraLaboradoJueves = obtenerValorJquery('tiempoExtraLabDia5');
    let tiempoExtraLaboradoViernes = obtenerValorJquery('tiempoExtraLabDia6');
    let tiempoExtraLaboradoSabado = obtenerValorJquery('tiempoExtraLabDia7');
    //******************TIEMPO EXTRA DOBLE*********************************
    let tiempoExtraDobleDomingo = obtenerValorJquery('extraDobleDia1');
    let tiempoExtraDobleLunes = obtenerValorJquery('extraDobleDia2');
    let tiempoExtraDobleMartes = obtenerValorJquery('extraDobleDia3');
    let tiempoExtraDobleMiercoles = obtenerValorJquery('extraDobleDia4');
    let tiempoExtraDobleJueves = obtenerValorJquery('extraDobleDia5');
    let tiempoExtraDobleViernes = obtenerValorJquery('extraDobleDia6');
    let tiempoExtraDobleSabado = obtenerValorJquery('extraDobleDia7');
    //********************TIEMPO EXTRA TRIPLE****************************
    let tiempoExtraTripleDomingo = obtenerValorJquery('extraTripleDia1');
    let tiempoExtraTripleLunes = obtenerValorJquery('extraTripleDia2');
    let tiempoExtraTripleMartes = obtenerValorJquery('extraTripleDia3');
    let tiempoExtraTripleMiercoles = obtenerValorJquery('extraTripleDia4');
    let tiempoExtraTripleJueves = obtenerValorJquery('extraTripleDia5');
    let tiempoExtraTripleViernes = obtenerValorJquery('extraTripleDia6');
    let tiempoExtraTripleSabado = obtenerValorJquery('extraTripleDia7');
    //******************DESCANSOS LABORADOS*******************************
    let descansoLaboradoDomingo = obtenerValorJquery('paseosLabDia1');
    let descansoLaboradoLunes = obtenerValorJquery('paseosLabDia2');
    let descansoLaboradoMartes = obtenerValorJquery('paseosLabDia3');
    let descansoLaboradoMiercoles = obtenerValorJquery('paseosLabDia4');
    let descansoLaboradoJueves = obtenerValorJquery('paseosLabDia5');
    let descansoLaboradoViernes = obtenerValorJquery('paseosLabDia6');
    let descansoLaboradoSabado = obtenerValorJquery('paseosLabDia7');
    //*******************INICIAL INASISTENCIAS************************
    let inasistenciaDomingo = obtenerValorJquery('inasistenciasDia1');
    let inasistenciasLunes = obtenerValorJquery('inasistenciasDia2');
    let inasistenciaMartes = obtenerValorJquery('inasistenciasDia3');
    let inasistenciaMiercoles = obtenerValorJquery('inasistenciasDia4');
    let inasistenciaJueves = obtenerValorJquery('inasistenciasDia5');
    let inasistenciaViernes = obtenerValorJquery('inasistenciasDia6');
    let inasistenciaSabado = obtenerValorJquery('inasistenciasDia7');
    //******************SUPLENCIAS***********************
    let suplenciasDomingo = obtenerValorJquery('suplenciasDia1');
    let suplenciasLunes = obtenerValorJquery('suplenciasDia2');
    let suplenciasMartes = obtenerValorJquery('suplenciasDia3');
    let suplenciasMiercoles = obtenerValorJquery('suplenciasDia4');
    let suplenciasJueves = obtenerValorJquery('suplenciasDia5');
    let suplenciasViernes = obtenerValorJquery('suplenciasDia6');
    let suplenciasSabado = obtenerValorJquery('suplenciasDia7');
    //*******************ABONOS***********************
    let abonoJornada = obtenerValorJquery('abonosJornada');
    let abonoHrsDoble = obtenerValorJquery('abonosHoraDoble');
    let abonoHrsTriple = obtenerValorJquery('abonosHorasTriples');
    let abonoDecansosLaborados = obtenerValorJquery('abonosDescansosLaborados');
    let abonoPrimaDominical = obtenerValorJquery('abonosPrimaDominical');
    let abonoFestivos = obtenerValorJquery('abonosFestivos');
    let abonoDiasLaborados = obtenerValorJquery('abonosDiasLaborados');
    let abonoDiferenciaSuplencia = obtenerValorJquery('abonosDiferenciaSuplencia');
    //*******************DESCUENTOS***********************
    let descuentoJornada = obtenerValorJquery('descuentosJornada');
    let descuentoHrsDoble = obtenerValorJquery('descuentosHoraDoble');
    let descuentoHrsTriple = obtenerValorJquery('descuentosHoraTriple');
    let descuentoDecansosLaborados = obtenerValorJquery('descuentosDescansosLaborados');
    let descuentoPrimaDominical = obtenerValorJquery('descuentosPrimaDominical');
    let descuentoFestivos = obtenerValorJquery('descuentosFestivos');
    let descuentoDiasLaborados = obtenerValorJquery('descuentosDiasLaborados');
    let descuentoDiferenciaSuplencia = obtenerValorJquery('descuentosDiferenciaSuplencia');
    let observacionAbonosDescuentos = obtenerValorJquery('observacionesAbonosyDescuentos');
    //**********************OMISIONES POR IMPORTE**********************
    let omisionPorImporte = obtenerValorJquery('omisionPorImporte');
    let observacionOmisionPorImporte = obtenerValorJquery('descripcionOmisionPorImporte');
    //************************RESUMEN DE INCIDENCIAS***********************
    let totalJornada = obtenerValorJquery('calculoJornada');
    let totalHrsDoble = obtenerValorJquery('calculoTiempoDoble');
    let totalHrsTriple = obtenerValorJquery('calculoTiempoTriple');
    let totalDiasLaborados = obtenerValorJquery('calculoDiasLaborados');
    let totalFaltas = obtenerValorJquery('calculoFaltas');
    let totalDescansosLaborados = obtenerValorJquery('calculoDescLaborados');
    let totalPrimaDominical = obtenerValorJquery('calculoPrimaDominical');
    let totalFestivo = obtenerValorJquery('calculoFestivos');
    let totalOmisiones = obtenerValorJquery('calculoOmisiones');
    let totalDescansosLaboradosSuplencia = obtenerValorJquery('calculoDescLaboradosSuplencia');
    let totalPrimaDominicalSuplencia = obtenerValorJquery('calculoPrimaDominicalSuplencia');
    let totalTiempoExtraSuplencias = obtenerValorJquery('calculoTiempoExtra');
    let totalSueldoSuplencia = obtenerValorJquery('calculoSueldo');
    let calculoPago = obtenerValorJquery('calculoPago');
    let calculoCve27 = obtenerValorJquery('calculoCve27');
    //**********************Asignación de valores a objeto*******************
    let datosCapturaSemanal;
    datosCapturaSemanal = {
        "a1": "string",
        "a10": "string",
        "a11": "string",
        "a12": "string",
        "a13": "string",
        "a14": "string",
        "a15": "string",
        "a16": "string",
        "a17": "string",
        "a18": "string",
        "a19": "string",
        "a2": "string",
        "a20": "string",
        "a21": "string",
        "a22": "string",
        "a23": "string",
        "a24": "string",
        "a25": "string",
        "a26": "string",
        "a27": "string",
        "a28": "string",
        "a3": "string",
        "a4": "string",
        "a5": "string",
        "a6": "string",
        "a7": "string",
        "a8": "string",
        "a9": "string",
        "activo_ra15": 0,
        "anio_planta": 0,
        "area": 0,
        "captura": "string",
        "captura_Semanal_Excedente_Jornada_RA15": {
            //"id_excedente_jornada": 0,
            "domingo": convertirDosPuntosAPuntos(excedenteJornadaDomingo),
            "lunes": convertirDosPuntosAPuntos(excedenteJornadaLunes),
            "martes": convertirDosPuntosAPuntos(excedenteJornadaMartes),
            "miercoles": convertirDosPuntosAPuntos(excedenteJornadaMiercoles),
            "jueves": convertirDosPuntosAPuntos(excedenteJornadaJueves),
            "viernes": convertirDosPuntosAPuntos(excedenteJornadaViernes),
            "sabado": convertirDosPuntosAPuntos(excedenteJornadaSabado)

        },
        "captura_Semanal_Jornada_Normal_RA15": {
            //"id_jornada_normal": 0,
            "domingo": convertirDosPuntosAPuntos(jornadaNormalDomingo),
            "lunes": convertirDosPuntosAPuntos(jornadaNormalLunes),
            "martes": convertirDosPuntosAPuntos(jornadaNormalMartes),
            "miercoles": convertirDosPuntosAPuntos(jornadaNormalMiercoles),
            "jueves": convertirDosPuntosAPuntos(jornadaNormalJueves),
            "viernes": convertirDosPuntosAPuntos(jornadaNormalViernes),
            "sabado": convertirDosPuntosAPuntos(jornadaNormalSabado)
        },
        "captura_Semanal_Tiempo_Extra_RA15": {
            //"id_tiempo_extra": 0,
            "domingo": convertirDosPuntosAPuntos(tiempoExtraLaboradoDomingo),
            "lunes": convertirDosPuntosAPuntos(tiempoExtraLaboradoLunes),
            "martes": convertirDosPuntosAPuntos(tiempoExtraLaboradoMartes),
            "miercoles": convertirDosPuntosAPuntos(tiempoExtraLaboradoMiercoles),
            "jueves": convertirDosPuntosAPuntos(tiempoExtraLaboradoJueves),
            "viernes": convertirDosPuntosAPuntos(tiempoExtraLaboradoViernes),
            "sabado": convertirDosPuntosAPuntos(tiempoExtraLaboradoSabado)
        },
        "captura_Semanal_Paseos_Laborados_RA15": {
            //"id_paseos_laborados": 0,
            "domingo": convertirDosPuntosAPuntos(descansoLaboradoDomingo),
            "lunes": convertirDosPuntosAPuntos(descansoLaboradoLunes),
            "martes": convertirDosPuntosAPuntos(descansoLaboradoMartes),
            "miercoles": convertirDosPuntosAPuntos(descansoLaboradoMiercoles),
            "jueves": convertirDosPuntosAPuntos(descansoLaboradoJueves),
            "viernes": convertirDosPuntosAPuntos(descansoLaboradoViernes),
            "sabado": convertirDosPuntosAPuntos(descansoLaboradoSabado)
        },
        "captura_Semanal_Inasistencias_RA15": {
            //"id_inasistencia": 0,
            "domingo": inasistenciaDomingo,
            "lunes": inasistenciasLunes,
            "martes": inasistenciaMartes,
            "miercoles": inasistenciaMiercoles,
            "jueves": inasistenciaJueves,
            "viernes": inasistenciaViernes,
            "sabado": inasistenciaSabado
        },
        "captura_Semanal_Suplencias_RA15": {
            // "id_suplencia": 0,
            "domingo": convertirDosPuntosAPuntos(suplenciasDomingo),
            "lunes": convertirDosPuntosAPuntos(suplenciasLunes),
            "martes": convertirDosPuntosAPuntos(suplenciasMartes),
            "miercoles": convertirDosPuntosAPuntos(suplenciasMiercoles),
            "jueves": convertirDosPuntosAPuntos(suplenciasJueves),
            "viernes": convertirDosPuntosAPuntos(suplenciasViernes),
            "sabado": convertirDosPuntosAPuntos(suplenciasSabado)
        },
        "captura_Semanal_Abonos_Descuentos_RA15": {
            //"id_abono_descuento": 0,
            "abono_descanso": convertirDosPuntosAPuntos(abonoDecansosLaborados),
            "abono_dias_lab": abonoDiasLaborados,
            "abono_dif_suplencia": abonoDiferenciaSuplencia,
            "abono_doble": convertirDosPuntosAPuntos(abonoHrsDoble),
            "abono_festivo": convertirDosPuntosAPuntos(abonoFestivos),
            "abono_jornada": convertirDosPuntosAPuntos(abonoJornada),
            "abono_prima": convertirDosPuntosAPuntos(abonoPrimaDominical),
            "abono_triple": convertirDosPuntosAPuntos(abonoHrsTriple),
            "descuento_descanso": convertirDosPuntosAPuntos(descuentoDecansosLaborados),
            "descuento_dias_lab": descuentoDiasLaborados,
            "descuento_dif_suplencia": descuentoDiferenciaSuplencia,
            "descuento_festivo": convertirDosPuntosAPuntos(descuentoFestivos),
            "descuento_jornada": convertirDosPuntosAPuntos(descuentoJornada),
            "descuento_prima": descuentoPrimaDominical,
            "descuento_triple": convertirDosPuntosAPuntos(descuentoHrsTriple),
            "descuento_doble": convertirDosPuntosAPuntos(descuentoHrsDoble),
            "observaciones": observacionAbonosDescuentos,
            "descuento_omisiones": omisionPorImporte,
            "observaciones_omisiones": observacionOmisionPorImporte
        },
        "captura_Semanal_Resumen_IncidenciasRA15": {
            //"id_resumen": 0,
            "dias_cve_27": calculoCve27,
            "dias_laborados": totalDiasLaborados,
            "dias_pago": calculoPago,
            "dif_paseos": convertirDosPuntosAPuntos(totalDescansosLaboradosSuplencia),
            "dif_prima": totalPrimaDominicalSuplencia,
            "dif_sueldo": totalSueldoSuplencia,
            "dif_tiempo_extra": convertirDosPuntosAPuntos(totalTiempoExtraSuplencias),
            "festivo": convertirDosPuntosAPuntos(totalFestivo),
            "horas_doble": convertirDosPuntosAPuntos(totalHrsDoble),
            "horas_triple": convertirDosPuntosAPuntos(totalHrsTriple),
            "horas_turno": totalJornada,
            "omisiones": totalOmisiones,
            "prima_dominical": totalPrimaDominical,
            "total_faltas": totalFaltas,
            "total_paseos": convertirDosPuntosAPuntos(totalDescansosLaborados)
        },
        "cod_puesto_suplencia": 0,
        "estado": "string",
        //"estatus": 0,
        "fecha_final_captura": "2024-01-24",
        "fecha_inicial_captura": "2024-01-24",
        "fecha_vigencia_planta": "2024-01-24",
        "horas_planta": 0,
        "id_ra": 0,
        "periodo_aplicacion_id": periodoAplicacionId,
        "prop_desc_1": 0,
        "prop_desc_2": 0,
        "ruta": "string",
        "ruta_id": 0,
        "tipo_id": 0,
        "trabajador_id": idTrabajador,
        "turno_id": 0
    };

    $.ajax({
        type: "POST",
        url: "ra15/guardarCapturaSemanal",
        data: JSON.stringify(datosCapturaSemanal),
        contentType: "application/json",
        success: function () {
            toastr['success']("Se ha registrado correctamente la captura semanal", "Éxito");
            mostrarOcultarBotonesActualizar(false);
            mostrarOcultarBotonGuardado(true);
            //Se llama para recuperar el id generado
            recuperarCapturaSemanal(periodoAplicacionId);
        },
        error: function (e) {
            toastr["warning"]("Error al registrar la captura semanal", "Error", {progressBar: true, closeButton: true});
        }
    });
}
;

//Convierte las horas a decimales
function convertirHoraADecimal(hora) {
    const [horas, minutos] = hora.split(':').map(Number);
    const fraccionHora = minutos / 60;
    const horaDecimal = horas + fraccionHora;
    // Redondear a dos decimales
    const horaDecimalRedondeada = horaDecimal.toFixed(2);
    return parseFloat(horaDecimalRedondeada);
}

function editarCapturaSemanal() {
    let idCapturaSemanalRA = $(`#idCalculoSemanal`).val();
    let periodoAplicacionId = obtenerValorJquery('periodoTransportacion');
    periodoAplicacionId = parseInt(periodoAplicacionId);
    //*******************EXCEDENTES JORNADA*****************
    let excedenteJornadaDomingo = obtenerValorJquery('excedenteDia1');
    let excedenteJornadaLunes = obtenerValorJquery('excedenteDia2');
    let excedenteJornadaMartes = obtenerValorJquery('excedenteDia3');
    let excedenteJornadaMiercoles = obtenerValorJquery('excedenteDia4');
    let excedenteJornadaJueves = obtenerValorJquery('excedenteDia5');
    let excedenteJornadaViernes = obtenerValorJquery('excedenteDia6');
    let excedenteJornadaSabado = obtenerValorJquery('excedenteDia7');
    //*******************JORNADA NORMAL*********************
    let jornadaNormalDomingo = obtenerValorJquery('jornadaNormalDia1');
    let jornadaNormalLunes = obtenerValorJquery('jornadaNormalDia2');
    let jornadaNormalMartes = obtenerValorJquery('jornadaNormalDia3');
    let jornadaNormalMiercoles = obtenerValorJquery('jornadaNormalDia4');
    let jornadaNormalJueves = obtenerValorJquery('jornadaNormalDia5');
    let jornadaNormalViernes = obtenerValorJquery('jornadaNormalDia6');
    let jornadaNormalSabado = obtenerValorJquery('jornadaNormalDia7');
    //*******************TIEMPO EXTRA***************************
    let tiempoExtraLaboradoDomingo = obtenerValorJquery('tiempoExtraLabDia1');
    let tiempoExtraLaboradoLunes = obtenerValorJquery('tiempoExtraLabDia2');
    let tiempoExtraLaboradoMartes = obtenerValorJquery('tiempoExtraLabDia3');
    let tiempoExtraLaboradoMiercoles = obtenerValorJquery('tiempoExtraLabDia4');
    let tiempoExtraLaboradoJueves = obtenerValorJquery('tiempoExtraLabDia5');
    let tiempoExtraLaboradoViernes = obtenerValorJquery('tiempoExtraLabDia6');
    let tiempoExtraLaboradoSabado = obtenerValorJquery('tiempoExtraLabDia7');
    //******************TIEMPO EXTRA DOBLE*********************************
    let tiempoExtraDobleDomingo = obtenerValorJquery('extraDobleDia1');
    let tiempoExtraDobleLunes = obtenerValorJquery('extraDobleDia2');
    let tiempoExtraDobleMartes = obtenerValorJquery('extraDobleDia3');
    let tiempoExtraDobleMiercoles = obtenerValorJquery('extraDobleDia4');
    let tiempoExtraDobleJueves = obtenerValorJquery('extraDobleDia5');
    let tiempoExtraDobleViernes = obtenerValorJquery('extraDobleDia6');
    let tiempoExtraDobleSabado = obtenerValorJquery('extraDobleDia7');
    //********************TIEMPO EXTRA TRIPLE****************************
    let tiempoExtraTripleDomingo = obtenerValorJquery('extraTripleDia1');
    let tiempoExtraTripleLunes = obtenerValorJquery('extraTripleDia2');
    let tiempoExtraTripleMartes = obtenerValorJquery('extraTripleDia3');
    let tiempoExtraTripleMiercoles = obtenerValorJquery('extraTripleDia4');
    let tiempoExtraTripleJueves = obtenerValorJquery('extraTripleDia5');
    let tiempoExtraTripleViernes = obtenerValorJquery('extraTripleDia6');
    let tiempoExtraTripleSabado = obtenerValorJquery('extraTripleDia7');
    //******************DESCANSOS LABORADOS*******************************
    let descansoLaboradoDomingo = obtenerValorJquery('paseosLabDia1');
    let descansoLaboradoLunes = obtenerValorJquery('paseosLabDia2');
    let descansoLaboradoMartes = obtenerValorJquery('paseosLabDia3');
    let descansoLaboradoMiercoles = obtenerValorJquery('paseosLabDia4');
    let descansoLaboradoJueves = obtenerValorJquery('paseosLabDia5');
    let descansoLaboradoViernes = obtenerValorJquery('paseosLabDia6');
    let descansoLaboradoSabado = obtenerValorJquery('paseosLabDia7');
    //*******************INICIAL INASISTENCIAS************************
    let inasistenciaDomingo = obtenerValorJquery('inasistenciasDia1');
    let inasistenciasLunes = obtenerValorJquery('inasistenciasDia2');
    let inasistenciaMartes = obtenerValorJquery('inasistenciasDia3');
    let inasistenciaMiercoles = obtenerValorJquery('inasistenciasDia4');
    let inasistenciaJueves = obtenerValorJquery('inasistenciasDia5');
    let inasistenciaViernes = obtenerValorJquery('inasistenciasDia6');
    let inasistenciaSabado = obtenerValorJquery('inasistenciasDia7');
    //******************SUPLENCIAS***********************
    let suplenciasDomingo = obtenerValorJquery('suplenciasDia1');
    let suplenciasLunes = obtenerValorJquery('suplenciasDia2');
    let suplenciasMartes = obtenerValorJquery('suplenciasDia3');
    let suplenciasMiercoles = obtenerValorJquery('suplenciasDia4');
    let suplenciasJueves = obtenerValorJquery('suplenciasDia5');
    let suplenciasViernes = obtenerValorJquery('suplenciasDia6');
    let suplenciasSabado = obtenerValorJquery('suplenciasDia7');
    //*******************ABONOS***********************
    let abonoJornada = obtenerValorJquery('abonosJornada');
    let abonoHrsDoble = obtenerValorJquery('abonosHoraDoble');
    let abonoHrsTriple = obtenerValorJquery('abonosHorasTriples');
    let abonoDecansosLaborados = obtenerValorJquery('abonosDescansosLaborados');
    let abonoPrimaDominical = obtenerValorJquery('abonosPrimaDominical');
    let abonoFestivos = obtenerValorJquery('abonosFestivos');
    let abonoDiasLaborados = obtenerValorJquery('abonosDiasLaborados');
    let abonoDiferenciaSuplencia = obtenerValorJquery('abonosDiferenciaSuplencia');
    //*******************DESCUENTOS***********************
    let descuentoJornada = obtenerValorJquery('descuentosJornada');
    let descuentoHrsDoble = obtenerValorJquery('descuentosHoraDoble');
    let descuentoHrsTriple = obtenerValorJquery('descuentosHoraTriple');
    let descuentoDecansosLaborados = obtenerValorJquery('descuentosDescansosLaborados');
    let descuentoPrimaDominical = obtenerValorJquery('descuentosPrimaDominical');
    let descuentoFestivos = obtenerValorJquery('descuentosFestivos');
    let descuentoDiasLaborados = obtenerValorJquery('descuentosDiasLaborados');
    let descuentoDiferenciaSuplencia = obtenerValorJquery('descuentosDiferenciaSuplencia');
    let observacionAbonosDescuentos = obtenerValorJquery('observacionesAbonosyDescuentos');
    //**********************OMISIONES POR IMPORTE**********************
    let omisionPorImporte = obtenerValorJquery('omisionPorImporte');
    let observacionOmisionPorImporte = obtenerValorJquery('descripcionOmisionPorImporte');
    //************************RESUMEN DE INCIDENCIAS***********************
    let totalJornada = obtenerValorJquery('calculoJornada');
    let totalHrsDoble = obtenerValorJquery('calculoTiempoDoble');
    let totalHrsTriple = obtenerValorJquery('calculoTiempoTriple');
    let totalDiasLaborados = obtenerValorJquery('calculoDiasLaborados');
    let totalFaltas = obtenerValorJquery('calculoFaltas');
    let totalDescansosLaborados = obtenerValorJquery('calculoDescLaborados');
    let totalPrimaDominical = obtenerValorJquery('calculoPrimaDominical');
    let totalFestivo = obtenerValorJquery('calculoFestivos');
    let totalOmisiones = obtenerValorJquery('calculoOmisiones');
    let totalDescansosLaboradosSuplencia = obtenerValorJquery('calculoDescLaboradosSuplencia');
    let totalPrimaDominicalSuplencia = obtenerValorJquery('calculoPrimaDominicalSuplencia');
    let totalTiempoExtraSuplencias = obtenerValorJquery('calculoTiempoExtra');
    let totalSueldoSuplencia = obtenerValorJquery('calculoSueldo');
    let calculoPago = obtenerValorJquery('calculoPago');
    let calculoCve27 = obtenerValorJquery('calculoCve27');
    //**********************Asignación de valores a objeto*******************
    let datosCapturaSemanal;
    datosCapturaSemanal = {
        "a1": "string",
        "a10": "string",
        "a11": "string",
        "a12": "string",
        "a13": "string",
        "a14": "string",
        "a15": "string",
        "a16": "string",
        "a17": "string",
        "a18": "string",
        "a19": "string",
        "a2": "string",
        "a20": "string",
        "a21": "string",
        "a22": "string",
        "a23": "string",
        "a24": "string",
        "a25": "string",
        "a26": "string",
        "a27": "string",
        "a28": "string",
        "a3": "string",
        "a4": "string",
        "a5": "string",
        "a6": "string",
        "a7": "string",
        "a8": "string",
        "a9": "string",
        "activo_ra15": 0,
        "anio_planta": 0,
        "area": 0,
        "captura": "string",
        "captura_Semanal_Excedente_Jornada_RA15": {
            //"id_excedente_jornada": 0,
            "domingo": convertirDosPuntosAPuntos(excedenteJornadaDomingo),
            "lunes": convertirDosPuntosAPuntos(excedenteJornadaLunes),
            "martes": convertirDosPuntosAPuntos(excedenteJornadaMartes),
            "miercoles": convertirDosPuntosAPuntos(excedenteJornadaMiercoles),
            "jueves": convertirDosPuntosAPuntos(excedenteJornadaJueves),
            "viernes": convertirDosPuntosAPuntos(excedenteJornadaViernes),
            "sabado": convertirDosPuntosAPuntos(excedenteJornadaSabado)

        },
        "captura_Semanal_Jornada_Normal_RA15": {
            //"id_jornada_normal": 0,
            "domingo": convertirDosPuntosAPuntos(jornadaNormalDomingo),
            "lunes": convertirDosPuntosAPuntos(jornadaNormalLunes),
            "martes": convertirDosPuntosAPuntos(jornadaNormalMartes),
            "miercoles": convertirDosPuntosAPuntos(jornadaNormalMiercoles),
            "jueves": convertirDosPuntosAPuntos(jornadaNormalJueves),
            "viernes": convertirDosPuntosAPuntos(jornadaNormalViernes),
            "sabado": convertirDosPuntosAPuntos(jornadaNormalSabado)
        },
        "captura_Semanal_Tiempo_Extra_RA15": {
            //"id_tiempo_extra": 0,
            "domingo": convertirDosPuntosAPuntos(tiempoExtraLaboradoDomingo),
            "lunes": convertirDosPuntosAPuntos(tiempoExtraLaboradoLunes),
            "martes": convertirDosPuntosAPuntos(tiempoExtraLaboradoMartes),
            "miercoles": convertirDosPuntosAPuntos(tiempoExtraLaboradoMiercoles),
            "jueves": convertirDosPuntosAPuntos(tiempoExtraLaboradoJueves),
            "viernes": convertirDosPuntosAPuntos(tiempoExtraLaboradoViernes),
            "sabado": convertirDosPuntosAPuntos(tiempoExtraLaboradoSabado)
        },
        "captura_Semanal_Paseos_Laborados_RA15": {
            //"id_paseos_laborados": 0,
            "domingo": convertirDosPuntosAPuntos(descansoLaboradoDomingo),
            "lunes": convertirDosPuntosAPuntos(descansoLaboradoLunes),
            "martes": convertirDosPuntosAPuntos(descansoLaboradoMartes),
            "miercoles": convertirDosPuntosAPuntos(descansoLaboradoMiercoles),
            "jueves": convertirDosPuntosAPuntos(descansoLaboradoJueves),
            "viernes": convertirDosPuntosAPuntos(descansoLaboradoViernes),
            "sabado": convertirDosPuntosAPuntos(descansoLaboradoSabado)
        },
        "captura_Semanal_Inasistencias_RA15": {
            //"id_inasistencia": 0,
            "domingo": inasistenciaDomingo,
            "lunes": inasistenciasLunes,
            "martes": inasistenciaMartes,
            "miercoles": inasistenciaMiercoles,
            "jueves": inasistenciaJueves,
            "viernes": inasistenciaViernes,
            "sabado": inasistenciaSabado
        },
        "captura_Semanal_Suplencias_RA15": {
            // "id_suplencia": 0,
            "domingo": convertirDosPuntosAPuntos(suplenciasDomingo),
            "lunes": convertirDosPuntosAPuntos(suplenciasLunes),
            "martes": convertirDosPuntosAPuntos(suplenciasMartes),
            "miercoles": convertirDosPuntosAPuntos(suplenciasMiercoles),
            "jueves": convertirDosPuntosAPuntos(suplenciasJueves),
            "viernes": convertirDosPuntosAPuntos(suplenciasViernes),
            "sabado": convertirDosPuntosAPuntos(suplenciasSabado)
        },
        "captura_Semanal_Abonos_Descuentos_RA15": {
            //"id_abono_descuento": 0,
            "abono_descanso": convertirDosPuntosAPuntos(abonoDecansosLaborados),
            "abono_dias_lab": abonoDiasLaborados,
            "abono_dif_suplencia": abonoDiferenciaSuplencia,
            "abono_doble": convertirDosPuntosAPuntos(abonoHrsDoble),
            "abono_festivo": convertirDosPuntosAPuntos(abonoFestivos),
            "abono_jornada": convertirDosPuntosAPuntos(abonoJornada),
            "abono_prima": convertirDosPuntosAPuntos(abonoPrimaDominical),
            "abono_triple": convertirDosPuntosAPuntos(abonoHrsTriple),
            "descuento_descanso": convertirDosPuntosAPuntos(descuentoDecansosLaborados),
            "descuento_dias_lab": descuentoDiasLaborados,
            "descuento_dif_suplencia": descuentoDiferenciaSuplencia,
            "descuento_festivo": convertirDosPuntosAPuntos(descuentoFestivos),
            "descuento_jornada": convertirDosPuntosAPuntos(descuentoJornada),
            "descuento_prima": descuentoPrimaDominical,
            "descuento_triple": convertirDosPuntosAPuntos(descuentoHrsTriple),
            "descuento_doble": convertirDosPuntosAPuntos(descuentoHrsDoble),
            "observaciones": observacionAbonosDescuentos,
            "descuento_omisiones": omisionPorImporte,
            "observaciones_omisiones": observacionOmisionPorImporte
        },
        "captura_Semanal_Resumen_IncidenciasRA15": {
            //"id_resumen": 0,
            "dias_cve_27": calculoCve27,
            "dias_laborados": totalDiasLaborados,
            "dias_pago": calculoPago,
            "dif_paseos": convertirDosPuntosAPuntos(totalDescansosLaboradosSuplencia),
            "dif_prima": totalPrimaDominicalSuplencia,
            "dif_sueldo": totalSueldoSuplencia,
            "dif_tiempo_extra": convertirDosPuntosAPuntos(totalTiempoExtraSuplencias),
            "festivo": convertirDosPuntosAPuntos(totalFestivo),
            "horas_doble": convertirDosPuntosAPuntos(totalHrsDoble),
            "horas_triple": convertirDosPuntosAPuntos(totalHrsTriple),
            "horas_turno": totalJornada,
            "omisiones": totalOmisiones,
            "prima_dominical": totalPrimaDominical,
            "total_faltas": totalFaltas,
            "total_paseos": convertirDosPuntosAPuntos(totalDescansosLaborados)
        },
        "cod_puesto_suplencia": 0,
        "estado": "string",
        //"estatus": 0,
        "fecha_final_captura": "2024-01-24",
        "fecha_inicial_captura": "2024-01-24",
        "fecha_vigencia_planta": "2024-01-24",
        "horas_planta": 0,
        "id_ra": 0,
        "periodo_aplicacion_id": periodoAplicacionId,
        "prop_desc_1": 0,
        "prop_desc_2": 0,
        "ruta": "string",
        "ruta_id": 0,
        "tipo_id": 0,
        "trabajador_id": idTrabajador,
        "turno_id": 0
    };

    $.ajax({
        type: "POST",
        url: "ra15/actualizarCapturaSemanal/" + idCapturaSemanalRA,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datosCapturaSemanal),
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información para modificar", "Aviso", {progressBar: true, closeButton: true});
            }
            toastr['success']("Se modificó el registro", "Éxito");
            //Se llama para recuperar los datos y colorear los campos editados
            recuperarCapturaSemanal(periodoAplicacionId);
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//Se recupera y se llama a la función consultar registro histórico con la finalidad de buscar registros que pudieran haber sido modificados
function recuperarCapturaSemanal(periodoId) {
    //Se recupera el registro histórico del trabajador para el periodo en curso mediante la promesa de la función devolverRegistroHistorico()
    devolverRegistroHistorico(periodoId)
            .then(dataHistorico => {
                //Llamada de ajax para recuperar todos los campos y formatearlos en los campos que representan horas ya que en la base de datos se guardan como decimales.
                $.ajax({
                    type: "GET",
                    url: "ra15/buscarPorTrabajadoryPeriodo/" + idTrabajador + "/" + periodoId,
                    dataType: 'json',
                    success: function (data) {
                        if (!$.isEmptyObject(data)) {
                            colocarInputsEnCamposYColorearCamposEditados(data, dataHistorico);
                        } else {
                            mostrarOcultarBotonesActualizar(true);
                            mostrarOcultarBotonGuardado(false);
                        }
                    },
                    error: function (e) {
                        console.error(e);
                        toastr["error"]("Error al buscar registros existentes", "Error", {progressBar: true, closeButton: true});
                    }
                });
            })
            .catch(error => {
                toastr["error"]("Error resolver promesa del registro histórico" + error, "Error", {progressBar: true, closeButton: true});
            });
}
;

//Esta funcion se encarga de colocar campos y colorearlos si sufrieron una modificación después del registro inicial
function colocarInputsEnCamposYColorearCamposEditados(data, dataHistorico) {
    $(`#idCalculoSemanal`).val(data[0].id_ra);
    mostrarOcultarBotonesActualizar(false);
    mostrarOcultarBotonGuardado(true);
    const DiaSemana = ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'];
    for (let i = 1; i <= DiaSemana.length; i++) {
        const nombreDia = DiaSemana[i - 1].toLowerCase();
        const valorDiaExcedente = data[0].captura_Semanal_Excedente_Jornada_RA15[nombreDia];
        //Colorear campo editado para el excedente de jornada
        colorearCeldaCampoEditado(valorDiaExcedente, dataHistorico.data[nombreDia + '_ej'], `#celdaExcedenteDia${i}`);
        const valorDiaJornadaNormal = data[0].captura_Semanal_Jornada_Normal_RA15[nombreDia];
        //Colorear campo editado para la jornada normal
        colorearCeldaCampoEditado(valorDiaJornadaNormal, dataHistorico.data[nombreDia + '_jn'], `#celdaJornadaNormalDia${i}`);
        const valorTiempoExtra = data[0].captura_Semanal_Tiempo_Extra_RA15[nombreDia];
        //Colorear campo editado para el tiempo extra
        colorearCeldaCampoEditado(valorTiempoExtra, dataHistorico.data[nombreDia + '_te'], `#celdaTiempoExtraLabDia${i}`);
        const valorPaseosLaborados = data[0].captura_Semanal_Paseos_Laborados_RA15[nombreDia];
        //Colorear campo editado para paseos laborados
        colorearCeldaCampoEditado(valorPaseosLaborados, dataHistorico.data[nombreDia + '_pl'], `#celdaPaseosLabDia${i}`);
        const valorInasistencias = data[0].captura_Semanal_Inasistencias_RA15[nombreDia];
        //Colorear campo editado para inasistencias
        colorearCeldaCampoEditado(valorInasistencias, dataHistorico.data[nombreDia + '_inc'], `#celdaInasistenciasDia${i}`);
        const valorSuplencias = data[0].captura_Semanal_Suplencias_RA15[nombreDia];
        //Colorear campo editado para suplencias
        colorearCeldaCampoEditado(valorSuplencias, dataHistorico.data[nombreDia + '_sup'], `#celdaSuplenciasDia${i}`);
        //----------------------------------SE COLOCAN LOS VALORES SEMANALES EN LOS INPUTS-----------------------------
        $(`#excedenteDia${i}`).val(formateaInputsBack(valorDiaExcedente));
        //Se trata de un día realizado con captura abierta o el descanso cambió
        if ($(`#jornadaNormalDia${i}`).val() === 'Descanso' && (valorDiaJornadaNormal !== null && valorDiaJornadaNormal !== '')) {
            $(`#jornadaNormalDia${i}`).val(formateaInputsBack(valorDiaJornadaNormal)).prop('disabled', false).css('font-weight', '');
            $(`#excedenteDia${i}`).prop('disabled', false);
            //Se hace check de la captura abierta
            $('#capturaAbierta').prop('checked', true);
            //Se trata de una captura de jornada normal realizada con los descansos correspondientes
        } else {
            $(`#jornadaNormalDia${i}`).val($(`#jornadaNormalDia${i}`).val() === 'Descanso' ? 'Descanso' : formateaInputsBack(valorDiaJornadaNormal));
        }
        $(`#tiempoExtraLabDia${i}`).val(formateaInputsBack(valorTiempoExtra));
        $(`#paseosLabDia${i}`).val(formateaInputsBack(valorPaseosLaborados));
        $(`#inasistenciasDia${i}`).val(valorInasistencias);
        $(`#suplenciasDia${i}`).val(valorSuplencias);
    }
    //JORNADA
    $(`#abonosJornada`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_jornada));
    //Colorear campo editado para abono de jornada 
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_jornada, dataHistorico.data.abono_jornada_historico, `#celdaAbonosJornada`);
    $(`#descuentosJornada`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_jornada));
    //Colorear campo editado para descuento de jornada 
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_jornada, dataHistorico.data.descuento_jornada_historico, `#celdaDescuentosJornada`);
    //HORAS DOBLE
    $(`#abonosHoraDoble`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_doble));
    //Colorear campo editado para abono de hora doble 
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_doble, dataHistorico.data.abono_doble_historico, `#celdaAbonosHoraDoble`);
    $(`#descuentosHoraDoble`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_doble));
    //Colorear campo editado para descuento de hora doble 
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_doble, dataHistorico.data.descuento_doble_historico, `#celdaDescuentosHoraDoble`);
    //HORAS TRIPLE
    $(`#abonosHorasTriples`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_triple));
    //Colorear campo editado para abono de hora triple 
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_triple, dataHistorico.data.abono_triple_historico, `#celdaAbonosHoraTriple`);
    $(`#descuentosHoraTriple`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_triple));
    //Colorear campo editado para descuento de hora triple 
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_triple, dataHistorico.data.descuento_triple_historico, `#celdaDescuentosHoraTriple`);
    //DESCANSOS LABORADOS 
    $(`#abonosDescansosLaborados`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_descanso));
    //Colorear campo editado para abono a descansos laborados
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_descanso, dataHistorico.data.abono_descanso_historico, `#celdaAbonosDescansosLaborados`);
    $(`#descuentosDescansosLaborados`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_descanso));
    //Colorear campo editado para descuento a descansos laborados
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_descanso, dataHistorico.data.descuento_descanso_historico, `#celdaDescuentosDescansosLaborados`);
    //PRIMA DOMINICAL 
    $(`#abonosPrimaDominical`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_prima);
    //Colorear campo editado para abono a la prima dominical
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_prima, dataHistorico.data.abono_prima_historico, `#celdaAbonosPrimaDominical`);
    $(`#descuentosPrimaDominical`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_prima);
    //Colorear campo editado para descuento a la prima dominical
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_prima, dataHistorico.data.descuento_prima_historico, `#celdaDescuentosPrimaDominical`);
    //FESTIVO
    $(`#abonosFestivos`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_festivo));
    //Colorear campo editado para abono a días festivos
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_festivo, dataHistorico.data.abono_festivo_historico, `#celdaAbonosFestivos`);
    $(`#descuentosFestivos`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_festivo));
    //Colorear campo editado para descuento a días festivos
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_festivo, dataHistorico.data.descuento_festivo_historico, `#celdaDescuentosFestivos`);
    //DIAS LABORADOS
    $(`#abonosDiasLaborados`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_dias_lab);
    //Colorear campo editado para abono a días laborados
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_dias_lab, dataHistorico.data.abono_dias_lab_historico, `#celdaAbonosDiasLaborados`);
    $(`#descuentosDiasLaborados`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_dias_lab);
    //Colorear campo editado para descuento a días laborados
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_dias_lab, dataHistorico.data.descuento_dias_lab_historico, `#celdaDescuentosDiasLaborados`);
    //DIFERENCIA SUPLENCIA
    $(`#abonosDiferenciaSuplencia`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_dif_suplencia);
    //Colorear campo editado abonos a diferencia suplencia
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_dif_suplencia, dataHistorico.data.abono_dif_suplencia_historico, `#celdaAbonosDiferenciaSuplencia`);
    $(`#descuentosDiferenciaSuplencia`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_dif_suplencia);
    //Colorear campo editado descuentos a diferencia suplencia
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_dif_suplencia, dataHistorico.data.descuento_dif_suplencia_historico, `#celdaDescuentosDiferenciaSuplencia`);
    //COMENTARIO ABONOS Y DESCUENTOS
    $(`#observacionesAbonosyDescuentos`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.observaciones);
    //Colorear campo editado observaciones para abonos y descuentos
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.observaciones, dataHistorico.data.observaciones_historico, `#celdaObservacionesAbonosyDescuentos`);
    //OMISIONES
    $(`#omisionPorImporte`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_omisiones);
    //Colorear campo editado omisión por importe
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_omisiones, dataHistorico.data.descuento_omisiones_historico, `#omisionPorImporte`);
    $(`#descripcionOmisionPorImporte`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.observaciones_omisiones);
    //Colorear campo editado observación omisión
    colorearCeldaCampoEditado(data[0].captura_Semanal_Abonos_Descuentos_RA15.observaciones_omisiones, dataHistorico.data.observaciones_omisiones_historico, `#descripcionOmisionPorImporte`);
    //RESUMEN DE INCIDENCIAS
    $(`#calculoJornada`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.horas_turno);
    //Colorear campo editado calculo total jornada
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.horas_turno, dataHistorico.data.horas_turno_r, `#celdaCalculoJornada`);
    $(`#calculoTiempoDoble`).val(formateaInputsBack(data[0].captura_Semanal_Resumen_IncidenciasRA15.horas_doble));
    //Colorear campo editado calculo total tiempo extra doble
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.horas_doble, dataHistorico.data.horas_doble_r, `#celdaCalculoTiempoDoble`);
    $(`#calculoTiempoTriple`).val(formateaInputsBack(data[0].captura_Semanal_Resumen_IncidenciasRA15.horas_triple));
    //Colorear campo editado calculo total tiempo extra triple
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.horas_triple, dataHistorico.data.horas_triple_r, `#celdaCalculoTiempoTriple`);
    $(`#calculoDiasLaborados`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.dias_laborados);
    //Colorear campo editado calculo total dias laborados
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.dias_laborados, dataHistorico.data.dias_laborados_r, `#celdaCalculoDiasLaborados`);
    $(`#calculoFaltas`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.total_faltas);
    //Colorear campo editado calculo total faltas
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.total_faltas, dataHistorico.data.total_faltas_r, `#celdaCalculoFaltas`);
    $(`#calculoDescLaborados`).val(formateaInputsBack(data[0].captura_Semanal_Resumen_IncidenciasRA15.total_paseos));
    //Colorear campo editado calculo total descansos laborados
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.total_paseos, dataHistorico.data.total_paseos_r, `#celdaCalculoDescLaborados`);
    $(`#calculoPrimaDominical`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.prima_dominical);
    //Colorear campo editado calculo total de la prima dominical
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.prima_dominical, dataHistorico.data.prima_dominical_r, `#celdaCalculoPrimaDominical`);
    $(`#calculoFestivos`).val(formateaInputsBack(data[0].captura_Semanal_Resumen_IncidenciasRA15.festivo));
    //Colorear campo editado calculo total de dias festivos
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.festivo, dataHistorico.data.festivo_r, `#celdaCalculoFestivos`);
    $(`#calculoOmisiones`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.omisiones);
    //Colorear campo editado calculo omisiones
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.omisiones, dataHistorico.data.omisiones_r, `#celdaCalculoOmisiones`);
    $(`#calculoDescLaboradosSuplencia`).val(formateaInputsBack(data[0].captura_Semanal_Resumen_IncidenciasRA15.dif_paseos));
    //Colorear campo editado calculo descansos laborados en suplencia
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.dif_paseos, dataHistorico.data.dif_paseos_r, `#celdaCalculoDescLaboradosSuplencia`);
    $(`#calculoPrimaDominicalSuplencia`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.dif_prima);
    //Colorear campo editado calculo prima dominical en suplencia
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.dif_prima, dataHistorico.data.dif_prima_r, `#celdaCalculoPrimaDominicalSuplencia`);
    $(`#calculoTiempoExtra`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.dif_tiempo_extra);
    //Colorear campo editado calculo tiempo extra en suplencia
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.dif_tiempo_extra, dataHistorico.data.dif_tiempo_extra_r, `#celdaCalculoTiempoExtra`);
    $(`#calculoSueldo`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.dif_sueldo);
    //Colorear campo editado sueldo en suplencia
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.dif_sueldo, dataHistorico.data.dif_sueldo_r, `#celdaCalculoSueldo`);
    $(`#calculoPago`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.dias_pago);
    //Colorear campo editado dias de pago
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.dias_pago, dataHistorico.data.dias_pago_r, `#celdaCalculoPago`);
    $(`#calculoCve27`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.dias_cve_27);
    //Colorear campo cv27
    colorearCeldaCampoEditado(data[0].captura_Semanal_Resumen_IncidenciasRA15.dias_cve_27, dataHistorico.data.dias_cve_27_r, `#celdaCalculoCve27`);
    //Para mostrar los totales de horas por descanso acorde a los datos recuperados
    calcularyMostrarTotalesJornada();
}

//Formatea las horas que vienen en decimal de la base de datos para que se desplieguen en formato (HH:MM)
function formateaHoraBack(valor, limiteHorasPorDia) {
    let horas = Math.floor(valor);
    let minutos = Math.round((valor % 1) * 100);
    const horasOriginales = horas;
    const minutosOriginales = minutos;
    horas = Math.min(horas, limiteHorasPorDia);
    minutos = Math.min(minutos, 59);
    if (horas === limiteHorasPorDia) {
        minutos = 0;
    }
    // Verifica si las horas o minutos han cambiado debido a la limitación
    if (horas !== horasOriginales || minutos !== minutosOriginales) {
        toastr["warning"]("Verificar datos ingresados, se realizó el ajuste al máximo diario permitido de " + limiteHorasPorDia + " horas", "Advertencia", {progressBar: true, closeButton: true});
    }
    const horasFormateadas = horas.toString().padStart(2, '0');
    const minutosFormateados = minutos.toString().padStart(2, '0');
    return `${horasFormateadas}:${minutosFormateados}`;
}
function formateaInputsBack(valor) {
    let limiteHorasPorDia = 1000;
    valor = String(valor);
    //Verificar si el input no viene vacío
    if (valor.trim() === '') {
        return;
    }
    // Convertir el valor a un número si es un formato decimal (por ejemplo, 0.30)
    if (/^\d+(\.\d+)?$/.test(valor)) {
        valor = parseFloat(valor);
    }
    //Aquí se coloca el valor formateado en el input
    if (!isNaN(valor)) {
        let resultado = formateaHoraBack(valor, limiteHorasPorDia);
        return resultado;
    }
}

//Esta función colorea la celda de los campos si son diferentes al primer registro generado
function colorearCeldaCampoEditado(valorDelCampoRecuperado, valorHistorico, celdaColorear) {
    //Si son diferentes los valores se colorean de azul celeste
    if (valorDelCampoRecuperado !== valorHistorico) {
        $(celdaColorear).css("background-color", "#00FFFF");
        //Si se regresa al valor original se le remueve el color
    } else {
        $(celdaColorear).css("background-color", "transparent");
    }
}
//Esta función retorna el primer registro creado para el periodo y trabajador actual
function devolverRegistroHistorico(periodo_id) {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "GET",
            url: "ra15/buscarHistoricoRA15/" + idTrabajador + "/" + periodo_id,
            dataType: 'json',
            success: function (data) {
                resolve(data); // Resuelve la promesa con los datos recibidos
            },
            error: function (e) {
                toastr["error"]("Error al buscar registro histórico", "Error", {progressBar: true, closeButton: true});
                reject(e);
            }
        });
    });
}

function mostrarOcultarBotonGuardado(boolean) {
    if (boolean === true) {
        $("#btnGuardar").attr("hidden", true);
    } else {
        $("#btnGuardar").attr("hidden", false);
    }
}

function mostrarOcultarBotonesActualizar(boolean) {
    if (boolean === true) {
        $("#btnEditar").attr("hidden", true);
        $("#elimina").attr("hidden", true);
    } else {
        $("#btnEditar").attr("hidden", false);
        $("#elimina").attr("hidden", false);
    }
}

function limpiarInasistenciasManteniendoFestivos() {
    //Se limpian las incidencias que no incluyan días festivos para colocar los nuevos registros para incidencias que se puedieran haber generado o eliminado desde el modulo de incidencias
    for (let i = 1; i <= 7; i++) {
        if ($(`#inasistenciasDia${i}`).val() !== 'DFEST') {
            $(`#inasistenciasDia${i}`).val('');
        }
    }
}

function limpiarDatos() {
    for (let i = 1; i <= 7; i++) {
        $(`#excedenteDia${i}`).val('');
        $(`#jornadaNormalDia${i}`).val('');
        $(`#tiempoExtraLabDia${i}`).val('');
        $(`#extraDobleDia${i}`).val('');
        $(`#extraTripleDia${i}`).val('');
        $(`#paseosLabDia${i}`).val('');
        $(`#inasistenciasDia${i}`).val('');
        $(`#suplenciasDia${i}`).val('');
    }
}

//********************************LISTADO DE PUESTOS PARA SUPLENCIAS**************************

function convertirHoraADecimalSuplencia(hora) {
    return hora.replace(':', '.');
}

$tablaPuestosSuplencias = $('#tabla_listado_puestos').DataTable({
    "ajax": {
        url: 'catalogos/listarPuestos',
        method: 'GET',
        dataSrc: ''
    },
    responsive: true,
    bProcessing: true,
    select: true,
    "lengthMenu": [5],
    "language": {
        "processing": "Procesando espera...",
        "sProcessing": "Procesando...",
        "sLengthMenu": "Mostrar _MENU_ Puestos",
        "sZeroRecords": "No se encontraron resultados",
        "sEmptyTable": " ",
        "sInfo": "_START_ al _END_ Total: _TOTAL_",
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
    },
    columns: [
        {data: "puesto", defaultContent: ""},
        {data: "sueldo_diario", defaultContent: ""},
        {data: "sueldo_hora", defaultContent: ""},

        {data: "", sTitle: "Suplencia", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="asignarDiasSuplencia(' + r.sueldo_diario + ', \'' + r.sueldo_hora + '\', \'' + r.puesto + '\')" > ' + '<span class="fa fa-edit"> </span> Generar Suplencia</button>';
                return he;
            }
        }
    ]
});

function asignarDiasSuplencia(sueldo_diario, sueldoPorHora, puesto) {
    //Sueldos por hora
    let valorSueldoPorHoraPuestoSuplencia;
    let valorSueldoPorHoraPuestoTrabajador;
    //Sueldos diarios
    let valorSueldoDiarioSuplencia;
    let valorSueldoDiarioTrabajador = $('#campo_sueldo_diario_trabajador').val();
    let diferenciaSueldoSuplenciaConSueldoTrabajador;
    let i;
    let diasSeleccionados = [];
    //Detalle extra para la asignación de días de suplencia
    $('#detalleDiasSuplencia').html(detalleHtmlDiasAplicarSuplencia);
    asignarEventosACamposGeneradosParaSuplencia();
    $('#sueldoPorHoraSuplencia').val(sueldoPorHora);
    $('#sueldoDiarioSuplencia').val(sueldo_diario);
    valorSueldoPorHoraPuestoSuplencia = $('#sueldoPorHoraSuplencia').val();
    valorSueldoDiarioSuplencia = $('#sueldoDiarioSuplencia').val();
    valorSueldoPorHoraPuestoTrabajador = $('#campo_sueldo_hora').val();
    diferenciaSueldoSuplenciaConSueldoTrabajador = valorSueldoPorHoraPuestoSuplencia - valorSueldoPorHoraPuestoTrabajador;
    //Si se genera un valor negativo es porque el sueldo del puesto a cubrir es menor que el puesto actual del trabajador
    if (diferenciaSueldoSuplenciaConSueldoTrabajador < 0) {
        $("#suplenciaModal").modal('hide');
        toastr["error"]('El sueldo por hora de la suplencia ' + '($' + valorSueldoPorHoraPuestoSuplencia + ')' + ' debe ser mayor al sueldo por hora del trabajador ' + '($' + valorSueldoPorHoraPuestoTrabajador + ')', 'Error', {progressBar: true, closeButton: true});
        return;
    }
    $("#puestoSeleccionado").text("Puesto Seleccionado: " + puesto);
    //Lógica de asignación de suplencias a los días seleccionados
    $("#btnAceptarSuplencia").click(function () {
        diasSeleccionados = [];
        $("input[name='dias[]']:checked").each(function () {
            diasSeleccionados.push($(this).val());
        });
        //Si la longitud de los días seleccionados es cero entonces se limpian los campos para la suplencia
        if (diasSeleccionados.length === 0) {
            for (i = 1; i <= 7; i++) {
                $(`#suplenciasDia${i}`).val('');
            }
            //Se llama a la función que calcula todos los valores de las suplencias para limpiar los campos relacionados
            calcularYMostrarTotalesSuplencias();
            return;
        } else {
            //Si la longitud de los campos seleccionados es mayor a cero entonces se procede a asignar los días a los campos de suplencia
            diasSeleccionados.forEach(function (dia) {
                $(`#suplenciasDia${dia}`).val($('#sueldoPorHoraSuplencia').val());
            });
        }
        //Se calculan los descansos laborados
        calcularDescansosLaboradosSuplencia(diferenciaSueldoSuplenciaConSueldoTrabajador);
        //Se calcula la prima dominical
        calcularPrimaDominicalSuplencia(valorSueldoDiarioTrabajador, valorSueldoDiarioSuplencia, diasSeleccionados);
        //Se calcula el tiempo extra
        calcularTiempoExtraSuplencia(diferenciaSueldoSuplenciaConSueldoTrabajador);
        //Ya que se termina de asignar el sueldo de suplencia se pasa a la realización del cálculo del sueldo en la diferencia por suplencia
        calcularSueldoSuplencia(diferenciaSueldoSuplenciaConSueldoTrabajador, diasSeleccionados);
    });
}

function asignarEventosACamposGeneradosParaSuplencia() {
    $('#selectAll').click(function () {
        if ($(this).prop('checked')) {
            $('input[name="dias[]"]').prop('checked', true);
        }
    });
    $('#deselectAll').click(function () {
        if ($(this).prop('checked')) {
            $('input[name="dias[]"]').prop('checked', false);
        }
    });
    $('#suplenciaModal').on('hidden.bs.modal', function () {
        $('#detalleDiasSuplencia').html('');
    });
}

//Verificar cantidad adicional por fracciones de tiempo... 
function calcularDescansosLaboradosSuplencia(diferenciaSueldoSuplenciaConSueldoTrabajador) {
    let totalHorasDescansoRegistradas = $('#calculoDescLaborados').val();
    let totalDescansosDecimal = convertirHoraADecimalSuplencia(totalHorasDescansoRegistradas);
    let resultado = diferenciaSueldoSuplenciaConSueldoTrabajador * totalDescansosDecimal;
    //Al decimal más cercano
    resultado = Math.round(resultado * 100) / 100;
    //Se coloca el cálculo de descanso laborado en suplencia en el campo correspondiente
    $('#calculoDescLaboradosSuplencia').val(resultado === 0 ? '' : resultado);
}

function calcularPrimaDominicalSuplencia(valorSueldoDiarioTrabajador, valorSueldoDiarioSuplencia, diasSeleccionados) {
    let valorJornadaNormalDomingo = $('#jornadaNormalDia1').val();
    let valorDescansoLaboradoDomingo = $('#paseosLabDia1').val();
    // Verifica si el arreglo contiene el valor "1" que representa el día domingo y si cualquiera de los dos campos estan llenos con cualquier hora entonces se coloca la prima dominical
    if (diasSeleccionados.includes("1") && (valorJornadaNormalDomingo !== '' || valorDescansoLaboradoDomingo !== '')) {
        let mitadDelSueldoDiarioSuplencia = valorSueldoDiarioSuplencia / 2;
        let mitadDelSueldoDiarioTrabajador = valorSueldoDiarioTrabajador / 2;
        let diferenciaAPagarPorSuplenciaConPrimaDominical = mitadDelSueldoDiarioSuplencia - mitadDelSueldoDiarioTrabajador;
        diferenciaAPagarPorSuplenciaConPrimaDominical = Math.round(diferenciaAPagarPorSuplenciaConPrimaDominical * 100) / 100;
        $('#calculoPrimaDominicalSuplencia').val(diferenciaAPagarPorSuplenciaConPrimaDominical === 0 ? '' : diferenciaAPagarPorSuplenciaConPrimaDominical);
    } else {
        $('#calculoPrimaDominicalSuplencia').val('');
    }
}

function calcularTiempoExtraSuplencia(diferenciaSueldoSuplenciaConSueldoTrabajador) {
    let totalHorasDoblesRegistradas = $('#calculoTiempoDoble').val();
    let totalHorasTriplesRegistradas = $('#calculoTiempoTriple').val();
    //Conversion a decimal
    let totalHorasDoblesDecimal = convertirHoraADecimalSuplencia(totalHorasDoblesRegistradas);
    let totalHorasTriplesDecimal = convertirHoraADecimalSuplencia(totalHorasTriplesRegistradas);
    //Total de horas a multiplicar
    let totalHorasDoblesYTriplesAMultiplicar = (totalHorasDoblesDecimal * 2) + (totalHorasTriplesDecimal * 3);
    //Cálculo final
    let resultadoTiempoExtraSuplencia = diferenciaSueldoSuplenciaConSueldoTrabajador * totalHorasDoblesYTriplesAMultiplicar;
    //Al decimal más cercano
    resultadoTiempoExtraSuplencia = Math.round(resultadoTiempoExtraSuplencia * 100) / 100;
    $('#calculoTiempoExtra').val(resultadoTiempoExtraSuplencia === 0 ? '' : resultadoTiempoExtraSuplencia);
}

//Esta funcion realiza el tottal del cálculo a pagar dependiendo de los dias seleccionados y campos presentes en la jornada normal
function calcularSueldoSuplencia(diferenciaSueldoSuplenciaConSueldoTrabajador, diasSeleccionados) {
    let acumuladorSuplencias = 0;
    let valorJornadaNormal;
    let horasJornadaConDescansos = [];
    const horaDescansoPorDia = '03:10';
    diasSeleccionados.forEach((numDia) => {
        horasJornadaConDescansos = [];
        valorJornadaNormal = $(`#jornadaNormalDia${numDia}`).val() === "Descanso" ? '' : $(`#jornadaNormalDia${numDia}`).val();
        if (valorJornadaNormal === '') {
            //toastr["warning"]('No está registrado el valor de la jornada normal para el día de suplencia', 'Advertencia', {progressBar: true, closeButton: true});
            return;
        }
        horasJornadaConDescansos.push(valorJornadaNormal);
        horasJornadaConDescansos.push(horaDescansoPorDia);
        let resultadoHorasPorDiaConsiderandoDescansos = sumarHorasArray(horasJornadaConDescansos);
        let horaDecimal = convertirHoraADecimalSuplencia(resultadoHorasPorDiaConsiderandoDescansos);
        acumuladorSuplencias += horaDecimal * diferenciaSueldoSuplenciaConSueldoTrabajador;
        //Al decimal más cercano
        acumuladorSuplencias = Math.round(acumuladorSuplencias * 100) / 100;
    });
    //Se coloca el cálculo del sueldo en el campo correspondiente
    $('#calculoSueldo').val(acumuladorSuplencias === 0 ? '' : acumuladorSuplencias);

}

const detalleHtmlDiasAplicarSuplencia = ` <br>
                                                            <hr>
                                                            <strong>Días de aplicación</strong>
                                                            <br>
                                                            <strong><label id="puestoSeleccionado"  style="float: right;"></label></strong> 
                                                            <br>    
                                                            <label for="selectAll">Seleccionar Todo</label>
                                                            <input type="radio" id="selectAll" name="selectAll"> &nbsp&nbsp&nbsp&nbsp;
                                                            <label for="deselectAll">Deseleccionar Todo</label>
                                                            <input type="radio" id="deselectAll" name="selectAll">
                                                            <form>
                                                                <input type="text" id="sueldoDiarioSuplencia" hidden>           
                                                                <input type="text" id="sueldoPorHoraSuplencia" hidden>              
                                                                <label for="lunes">Lunes</label>
                                                                <input type="checkbox" id="lunes" name="dias[]" value="2">&nbsp&nbsp

                                                                <label for="martes">Martes</label>
                                                                <input type="checkbox" id="martes" name="dias[]" value="3">&nbsp&nbsp

                                                                <label for="miercoles">Miércoles</label>
                                                                <input type="checkbox" id="miercoles" name="dias[]" value="4">&nbsp&nbsp

                                                                <label for="jueves">Jueves</label>
                                                                <input type="checkbox" id="jueves" name="dias[]" value="5">&nbsp&nbsp

                                                                <label for="viernes">Viernes</label>
                                                                <input type="checkbox" id="viernes" name="dias[]" value="6">&nbsp&nbsp

                                                                <label for="sabado">Sábado</label>
                                                                <input type="checkbox" id="sabado" name="dias[]" value="7">&nbsp&nbsp
                                                                <label for="domingo">Domingo</label>
                                                                <input type="checkbox" id="domingo" name="dias[]" value="1">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                                                <button id="btnAceptarSuplencia" type="type="submit" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-check" aria-hidden="true"></i>Aceptar</button>
                                                            </form>`;