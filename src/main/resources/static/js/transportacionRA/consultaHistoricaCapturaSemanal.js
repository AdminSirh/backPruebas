//************VARIABLE GLOBAL PARA NÓMINA DE TRANSPORTACION************
const nominaTransportacion = 2;
let idTrabajador;
document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    idTrabajador = urlParams.get('id_trabajador');
    bucarDatosTrabajador(idTrabajador);
    cargarPeriodos();
});

function adminRa15() {
    window.location.href = 'consultaHistoricaTransportacion?tab=tab-2';
}

function cargarPeriodos() {
    let fechaInicialSeleccionada;
    let fechaFinalSeleccionada;
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTodosLosPeriodosPorNomina/" + nominaTransportacion,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#periodoTransportacion').empty();
                var vselected = "";
                var fechaActual = new Date();
                if (data.length > 0) {
                    // Inicializa la diferencia mínima con un valor grande para la primera comparación.
                    var minDiferencia = Number.MAX_SAFE_INTEGER;
                    var indexMinDiferencia = 0;
                    for (var i = 0; i < data.length; i++) {
                        var fechaInicial = new Date(data[i].fecha_inicial);
                        // Calcula la diferencia en milisegundos entre la fecha actual y la fecha inicial del periodo.
                        var diferencia = Math.abs(fechaActual - fechaInicial);
                        if (diferencia < minDiferencia) {
                            // Actualiza la diferencia mínima y el índice del periodo seleccionado.
                            minDiferencia = diferencia;
                            indexMinDiferencia = i;
                        }
                        var n_periodo = data[i].periodo;
                        var dias_inicial = fechaInicial.getDate();
                        var meses_inicial = fechaInicial.getMonth() + 1;
                        var anios_inicial = fechaInicial.getFullYear();

                        var fecha_final_formato = new Date(data[i].fecha_final);
                        var dias_final = fecha_final_formato.getDate();
                        var meses_final = fecha_final_formato.getMonth() + 1;
                        var anios_final = fecha_final_formato.getFullYear();

                        $('#periodoTransportacion').append('<option value="' + data[i].periodo + '">' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
                                '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>');
                        // Asigna las fechas del periodo seleccionado.
                        fechaInicialSeleccionada = fechaInicial;
                        fechaFinalSeleccionada = fecha_final_formato;
                    }
                    // Selecciona automáticamente la opción del periodo más cercano.
                    $('#periodoTransportacion').val(data[indexMinDiferencia].periodo);
                }
            }
        },
        error: function (e) {
            toastr["error"](e, "Error al obtener periodos de la nómina", {progressBar: true, closeButton: true});
        }
    });
}

function buscarCapturaSemanal() {
    let periodoId = $('#periodoTransportacion').val();
    let expediente = $('#campo_expediente').val();
    limpiaTodosLosCampos();
    //Llamada de ajax para recuperar todos los campos y formatearlos en los campos que representan horas ya que en la base de datos se guardan como decimales.
    $.ajax({
        type: "GET",
        url: "ra15/buscarPorExpedienteyPeriodo/" + expediente + "/" + periodoId,
        dataType: 'json',
        success: function (data) {
            if (!$.isEmptyObject(data)) {
                const DiaSemana = ['Domingo', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'];
                for (let i = 1; i <= DiaSemana.length; i++) {
                    const nombreDia = DiaSemana[i - 1].toLowerCase();
                    const valorDiaExcedente = data[0].captura_Semanal_Excedente_Jornada_RA15[nombreDia];
                    const valorDiaJornadaNormal = data[0].captura_Semanal_Jornada_Normal_RA15[nombreDia];
                    const valorTiempoExtra = data[0].captura_Semanal_Tiempo_Extra_RA15[nombreDia];
                    const valorPaseosLaborados = data[0].captura_Semanal_Paseos_Laborados_RA15[nombreDia];
                    const valorInasistencias = data[0].captura_Semanal_Inasistencias_RA15[nombreDia];
                    const valorSuplencias = data[0].captura_Semanal_Suplencias_RA15[nombreDia];
                    $(`#excedenteDia${i}`).val(formateaInputsBack(valorDiaExcedente));
                    //Se mantiene la indicación del descanso del trabajador aunque esos días vengan nulos en el data
                    $(`#jornadaNormalDia${i}`).val($(`#jornadaNormalDia${i}`).val() === 'Descanso' ? 'Descanso' : formateaInputsBack(valorDiaJornadaNormal));
                    $(`#tiempoExtraLabDia${i}`).val(formateaInputsBack(valorTiempoExtra));
                    $(`#paseosLabDia${i}`).val(formateaInputsBack(valorPaseosLaborados));
                    $(`#inasistenciasDia${i}`).val(valorInasistencias);
                    $(`#suplenciasDia${i}`).val(valorSuplencias);
                }
                //JORNADA
                $(`#abonosJornada`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_jornada));
                $(`#descuentosJornada`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_jornada));
                //HORAS DOBLE
                $(`#abonosHoraDoble`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_doble));
                $(`#descuentosHoraDoble`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_doble));
                //HORAS TRIPLE
                $(`#abonosHorasTriples`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_triple));
                $(`#descuentosHoraTriple`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_triple));
                //DESCANSOS LABORADOS 
                $(`#abonosDescansosLaborados`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_descanso));
                $(`#descuentosDescansosLaborados`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_descanso));
                //PRIMA DOMINICAL 
                $(`#abonosPrimaDominical`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_prima);
                $(`#descuentosPrimaDominical`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_prima);
                //FESTIVO
                $(`#abonosFestivos`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_festivo));
                $(`#descuentosFestivos`).val(formateaInputsBack(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_festivo));
                //DIAS LABORADOS
                $(`#abonosDiasLaborados`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_dias_lab);
                $(`#descuentosDiasLaborados`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_dias_lab);
                //DIFERENCIA SUPLENCIA
                $(`#abonosDiferenciaSuplencia`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.abono_dif_suplencia);
                $(`#descuentosDiferenciaSuplencia`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_dif_suplencia);
                //COMENTARIO ABONOS Y DESCUENTOS
                $(`#observacionesAbonosyDescuentos`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.observaciones);
                //OMISIONES
                $(`#omisionPorImporte`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.descuento_omisiones);
                $(`#descripcionOmisionPorImporte`).val(data[0].captura_Semanal_Abonos_Descuentos_RA15.observaciones_omisiones);
                //RESUMEN DE INCIDENCIAS
                $(`#calculoJornada`).val(formateaInputsBack(data[0].captura_Semanal_Resumen_IncidenciasRA15.horas_turno));
                $(`#calculoTiempoDoble`).val(formateaInputsBack(data[0].captura_Semanal_Resumen_IncidenciasRA15.horas_doble));
                $(`#calculoTiempoTriple`).val(formateaInputsBack(data[0].captura_Semanal_Resumen_IncidenciasRA15.horas_triple));
                $(`#calculoDiasLaborados`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.dias_laborados);
                $(`#calculoFaltas`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.total_faltas);
                $(`#calculoDescLaborados`).val(formateaInputsBack(data[0].captura_Semanal_Resumen_IncidenciasRA15.total_paseos));
                $(`#calculoPrimaDominical`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.prima_dominical);
                $(`#calculoFestivos`).val(formateaInputsBack(data[0].captura_Semanal_Resumen_IncidenciasRA15.festivo));
                $(`#calculoOmisiones`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.omisiones);
                $(`#calculoDescLaboradosSuplencia`).val(formateaInputsBack(data[0].captura_Semanal_Resumen_IncidenciasRA15.dif_paseos));
                $(`#calculoPrimaDominicalSuplencia`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.dif_prima);
                //$(`#calculoTiempoExtra`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.);
                $(`#calculoSueldo`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.dif_sueldo);
                $(`#calculoPago`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.dias_pago);
                $(`#calculoCve27`).val(data[0].captura_Semanal_Resumen_IncidenciasRA15.dias_cve_27);
            } else {
                toastr["warning"]("No se encontraron registros para el trabajador en el periodo correspondiente", "Aviso", {progressBar: true, closeButton: true});
            }
        },
        error: function (e) {
            toastr["error"]("Error al buscar registros existentes", "Error", {progressBar: true, closeButton: true});
        }
    });
}

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

function confirmarNominaTrabajador(expediente) {
    let flagIncorrecto = false;
    $.ajax({
        type: "GET",
        url: "trabajador/buscarNominaPorExpediente/" + expediente,
        dataType: 'json',
        async: false,
        success: function (data) {
            if (data !== 2) {
                toastr["error"]("El trabajador no corresponde a la nómina de transportación o no fue encontrado", "Error", {progressBar: true, closeButton: true});
            } else {
                flagIncorrecto = true;
            }
        },
        error: function (e) {
            toastr["error"]("Error al buscar la nómina del trabajador", "Error", {progressBar: true, closeButton: true});
        }
    });
    return flagIncorrecto;
}

function bucarDatosTrabajador(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            $('#campo_expediente').val(data.data.cat_expediente.numero_expediente);
            $('#campo_nombre').val(data.data.persona.nombre + " " + data.data.persona.apellido_paterno + " " + data.data.persona.apellido_materno);
            obtenerInformacionHorarioTrabajador(id_trabajador);
            obtenerInformacionDescansosTrabajador(id_trabajador);
            obtenerInformacionPuestoTrabajador(id_trabajador);
        },
        error: function (e) {
            toastr["error"]("Error al buscar registros existentes", "Error", {progressBar: true, closeButton: true});
        }
    });
}

function obtenerInformacionPuestoTrabajador(idTrabajador) {
    $.ajax({
        type: "GET",
        url: "plaza/buscarPlazaTrabajador/" + idTrabajador,
        dataType: 'json',
        success: function (dataPuestoTrabajador) {
            let sueldoDiarioTrabajador = dataPuestoTrabajador[0].sueldoDiarioDto;
            let calculoSueldoHoraTrabajador = sueldoDiarioTrabajador / 8;
            $('#campo_puesto_trabajador').val(dataPuestoTrabajador[0].puestoDto);
            $('#campo_sueldo_diario_trabajador').val(dataPuestoTrabajador[0].sueldoDiarioDto);
            $('#campo_sueldo_hora').val(calculoSueldoHoraTrabajador);
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener plaza del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

function obtenerInformacionHorarioTrabajador(idTrabajador) {
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
                });

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

function obtenerInformacionDescansosTrabajador(idTrabajador) {
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

                    var dia = dataDiasDescanso[i].cat_dias.dia;
                    var inicialDia = dia ? dia.charAt(0) : '';
                    inicialesDias[dia] = inicialDia;
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
//                let horasDiaDescanso = '01:35';
//                let totalDescansos = '3:10';
//                $("#dias_descanso_trabajador").text(dataDiasDescanso[0].cat_dias.dia + ': ' + horasDiaDescanso + ' ' + dataDiasDescanso[1].cat_dias.dia + ': ' + horasDiaDescanso +
//                        " " + "| Hrs. Desc. = " + totalDescansos);
            }
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener descansos del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

function limpiaTodosLosCampos() {
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

    $(`#abonosJornada`).val('');
    $(`#descuentosJornada`).val('');
    $(`#abonosHoraDoble`).val('');
    $(`#descuentosHoraDoble`).val('');
    $(`#abonosHorasTriples`).val('');
    $(`#descuentosHoraTriple`).val('');
    $(`#abonosDescansosLaborados`).val('');
    $(`#descuentosDescansosLaborados`).val('');
    $(`#abonosPrimaDominical`).val('');
    $(`#descuentosPrimaDominical`).val('');
    $(`#abonosFestivos`).val('');
    $(`#descuentosFestivos`).val('');
    $(`#abonosDiasLaborados`).val('');
    $(`#descuentosDiasLaborados`).val('');
    $(`#abonosDiferenciaSuplencia`).val('');
    $(`#descuentosDiferenciaSuplencia`).val('');
    $(`#observacionesAbonosyDescuentos`).val('');

    $(`#omisionPorImporte`).val('');
    $(`#descripcionOmisionPorImporte`).val('');

    $(`#calculoJornada`).val('');
    $(`#calculoTiempoDoble`).val('');
    $(`#calculoTiempoTriple`).val('');
    $(`#calculoDiasLaborados`).val('');
    $(`#calculoFaltas`).val('');
    $(`#calculoDescLaborados`).val('');
    $(`#calculoPrimaDominical`).val('');
    $(`#calculoFestivos`).val('');
    $(`#calculoOmisiones`).val('');
    $(`#calculoDescLaboradosSuplencia`).val('');
    $(`#calculoPrimaDominicalSuplencia`).val('');
    $(`#calculoTiempoExtra`).val('');
    $(`#calculoSueldo`).val('');
    $(`#calculoPago`).val('');
    $(`#calculoCve27`).val('');
}