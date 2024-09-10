document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    const folioDiv = $("#busquedaFolio");
    var trabajador_id = urlParams.get('id_trabajador');
    var checkboxFechas = $('#filtradoFechas');
    var checkboxFolio = $('#filtradoFolio');
    var fechaInicial = $('#fechaInicialLiberacion');
    var fechaFinal = $('#fechaFinalLiberacion');
    var inputFolio = $('#inputFolio');
    var fechaInicial = $('#fechaInicialLiberacion');
    var fechaFinal = $('#fechaFinalLiberacion');
    var botonFiltrar = $('#btnFiltrar');
    rellenaCampos(trabajador_id);
    cmbMotivoIncapacidad();
    cmbTipoIncapacidad();
    cmbTipoRiesgo();
    cmbSecuelas();
    cmbControlIncapacidad();
//    trabajadorIncapacidades('2023-01-01', '2023-12-31');
    //Función de filtrado
    checkboxFechas.change(function () {
        if (checkboxFechas.is(':checked')) {
            fechaInicial.prop('disabled', false);
            fechaFinal.prop('disabled', false);
            checkboxFolio.prop('checked', false);
            inputFolio.prop('disabled', true);
            resetCampos();
        } else {
            fechaInicial.prop('disabled', true);
            fechaFinal.prop('disabled', true);
            resetCampos();
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
            resetCampos();
        }
    });

    //Reset de los campos de filtrado
    const resetCampos = () => {
        fechaInicial.val('');
        fechaFinal.val('');
        inputFolio.val('');
        botonFiltrar.prop('disabled', true);
    };
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

// Función para habilitar/deshabilitar el botón "Filtrar" según las fechas
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

// Agregar eventos de entrada a los campos de fecha
$('#fechaInicialLiberacion, #fechaFinalLiberacion, #inputFolio').on('input', actualizarEstadoBotonFiltrar);
//Se llama a la funcion si se regresa a la página y ya se habían ingresado parámetros de búsqueda de fecha
setTimeout(function () {
    let valFechaInicialLiberacion = $('#fechaInicialLiberacion').val();
    let valFechaFinalLiberacion = $('#fechaFinalLiberacion').val();
    if (valFechaInicialLiberacion && valFechaFinalLiberacion) {
        trabajadorIncapacidades(valFechaInicialLiberacion, valFechaFinalLiberacion);
    }
}, 100);

//Comprobación para no enviar la solicitud si las fechas ya fueron filtradas
let fechaInicialAnterior = null;
let fechaFinalAnterior = null;
let inputFolioAnterior = null;

//Filtrado de datos de las incapacidades del trabajador al dar click en el boton de filtrado
$('#btnFiltrar').on('click', function () {
    filtrar();
});

// Filtrado de datos de las incapacidades del trabajador al dar enter en el campo folio
$('#inputFolio').on('keyup', function (event) {
    // Verifica si la tecla presionada es 'Enter' (código 13)
    if (event.keyCode === 13) {
        let inputFolio = $('#inputFolio').val().trim();
        if (inputFolio !== '') {
            filtrar();
        }
    }
});

const filtrar = () => {
    let fechaInicial = $('#fechaInicialLiberacion').val();
    let fechaFinal = $('#fechaFinalLiberacion').val();
    let inputFolio = $('#inputFolio').val();

    if (fechaInicial === fechaInicialAnterior && fechaFinal === fechaFinalAnterior) {
        toastr["warning"]("Se está mostrando el filtrado actual", "Aviso", {progressBar: true, closeButton: true});
        return;
    } else {
        fechaInicialAnterior = null;
        fechaFinalAnterior = null;
    }
    if (inputFolio === inputFolioAnterior) {
        toastr["warning"]("Se está mostrando el filtrado actual", "Aviso", {progressBar: true, closeButton: true});
        return;
    }
    if (inputFolio.trim() !== '') {
        trabajadorIncapacidades(null, null, inputFolio);
        inputFolioAnterior = inputFolio;
    } else {
        fechaInicialAnterior = fechaInicial;
        fechaFinalAnterior = fechaFinal;
        trabajadorIncapacidades(fechaInicial, fechaFinal);
    }
};

//Redirección al kárdex
const verKardexTrabajador = () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    window.location.href = 'guardaInasistencias?id_trabajador=' + trabajador_id;
};
/*Función verificadora de todos los casos posibles, llama a las funciones que manejan los casos correspondientes 
 * 
 * El flujo del js va de la siguiente forma: 
 *      ------AUTORIZACIÓN DE LA INCAPACIDAD------
 *      
 *      +autorizarIncapacidad() =>
 *                             + creaDosGruposFechas() => 
 *                                                       + verificarContinuidadInicial() //Incapacidades iniciales + continuas (Inicial fecha de fin + 1 será igual a la fecha inicial de la incapacidad continua por EG)
 *                             + creaTresGruposFechas() => 
 *                                                       + verificarContinuidadInicial ()
 *                                                       + crearTresGruposEspecialesFechas() //Incapacidades que cambian de año
 *                                                       + crearDosGruposEspecialesFechas //Incapacidades que cambian de año
 *                             + generarIncidencia()  => ajaxEstatusIncapacidad ()
 *                             + creaGruposFechasExtemporaneos() //Para nómina de varios ya que varía la agrupación de días, aplica al entregar una incapacidad 72 horas después de establecida la fecha inicial
 *     ------RECHAZO DE LA INCAPACIDAD/DESHABILITADO DE ESTATUS INCIDENCIA------
 *     +bajarIncapacidad() =>
 *                             + rechazarIncapacidad() => //Verifica si la incapacidad fue dada de alta como inicial + continua para dar de baja ambas, sea cual sea la que se rechaze
 *                                                      + verificarContinuidadInicial()
 *                                                      + verificarFechaFinalInicial()   
 *                                                      + ajaxEstatusIncapacidad() 
 * */

const autorizarIncapacidad = () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');

    //Se llama a la función que recupera las prestaciones del trabajador
    prestaciones(trabajador_id);
    //Obtiene la nómina del trabajador
    buscarNomina(trabajador_id);
    //Espera a que carguen los datos del modal para obtenerlos sin errores
    setTimeout(function () {
        //Transformación a objeto date de js
        const fechaInicialStr = $('#fechaInicialIncapacidadHidden').val();
        let [year, month, day] = fechaInicialStr.split('T')[0].split('-');
        let [hour, minute, second] = fechaInicialStr.split('T')[1].split('.')[0].split(':');
        //==============OBJETO DATE FECHA INICIAL========================
        const fechaInicial = new Date(year, month - 1, day, hour, minute, second);

        const fechaFinalStr = $('#fechaFinalIncapacidadHidden').val();
        [year, month, day] = fechaFinalStr.split('T')[0].split('-');
        [hour, minute, second] = fechaFinalStr.split('T')[1].split('.')[0].split(':');
        //==============OBJETO DATE FECHA FINAL============================
        const fechaFinal = new Date(year, month - 1, day, hour, minute, second);

        const fechaLiberacionStr = $('#fecha_liberacion').val();
        [year, month, day] = fechaLiberacionStr.split('T')[0].split('-');
        [hour, minute, second] = fechaLiberacionStr.split('T')[1].split('.')[0].split(':');
        //==============OBJETO DATE FECHA LIBERACION===========================
        const fechaLiberacion = new Date(year, month - 1, day, hour, minute, second);
        //=============OBJETO DATE PARA FECHA EXTEMPORÁNEA=====================
        const fechaMaximaExtemporanea = new Date(fechaInicial);
        fechaMaximaExtemporanea.setDate(fechaMaximaExtemporanea.getDate() + 3);
        // Obtener la hora y los minutos de fechaLiberacion
        const horaLiberacion = fechaLiberacion.getHours();
        const minutosLiberacion = fechaLiberacion.getMinutes();
        //Se coloca la misma hora para evitar errores de cálculo
        fechaMaximaExtemporanea.setHours(horaLiberacion, minutosLiberacion, 0, 0);
        let prestacion = $('#prestaciones').val();
        let nomina = $('#id_nomina').val();
        //Se obtiene el motivo de la incapacidad
        let motivoIncapacidad = $('#cmbMotivoIncapacidad').val();
        //Se obtiene el tipo de incapacidad
        let tipoIncapacidad = $('#cmbTipoIncapacidad').val();
        // Declaración de id para incidencias correspondiente
        let idIncapacidadPorEg0 = 27;
        let idIncapacidadPorEg50 = 14;
        let idIncapacidadPorEg60 = 31;
        let idIncapacidadPorEg75 = 15;
        let idIncapacidadPorEg100 = 16;
        let idIncapacidadRT100 = 17;
        let idIncapacidadPrp = 34;
        let idIncapacidadPos100 = 22;
        let idIncapacidadEN100 = 26;

        //=============================== CONFIANZA ENFERMEDAD GENERAL Y RIESGO DE TRABAJO =========================================
        switch (true) {
            //CONFIANZA EG sin prestación/inicial (Se verifica si se cuenta con una incapacidad continua inmediata)
            case prestacion === '2' && nomina === '3' && motivoIncapacidad === '1' && tipoIncapacidad === '1':
                creaDosGruposFechas(idIncapacidadPorEg0, idIncapacidadPorEg60, fechaInicial, fechaFinal);
                break;
                //CONFIANZA EG con prestación/inicial
            case prestacion !== '2' && nomina === '3' && motivoIncapacidad === '1' && tipoIncapacidad === '1':
                //Se genera el manejo para las incapacidades con más de tres días de retraso entre la fecha inicial y la liberacion VERIFICAR CONFIANZA CON PRESTACIONES
                if (fechaLiberacion > fechaMaximaExtemporanea) {
                    toastr["warning"]("Incapacidad Extemporánea", "Aviso", {progressBar: true, closeButton: true});
                    creaTresGruposFechas(fechaInicial, fechaFinal, idIncapacidadPorEg0, idIncapacidadPorEg50, idIncapacidadPorEg75);
                    return;
                }
                creaTresGruposFechas(fechaInicial, fechaFinal, idIncapacidadPorEg50, idIncapacidadPorEg75, idIncapacidadPorEg100);
                break;
                //CONFIANZA EG sin prestación/contiua
            case prestacion === '2' && nomina === '3' && motivoIncapacidad === '1' && tipoIncapacidad === '2':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadPorEg60, true);
                break;
                //CONFIANZA EG con prestación/contiua
            case prestacion !== '2' && nomina === '3' && motivoIncapacidad === '1' && tipoIncapacidad === '2':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadPorEg100, true);
                break;
                //CONFIANZA Riesgo de trabajo pagado al 100%
            case nomina === '3' && motivoIncapacidad === '2':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadRT100);
                break;
                //CONFIANZA Pre-Natal al 100% (Pr.P.)
            case nomina === '3' && motivoIncapacidad === '3':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadPrp);
                break;
                //CONFIANZA Post-Natal al 100% (Ps.P.)
            case nomina === '3' && motivoIncapacidad === '4':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadPos100);
                break;
                //CONFIANZA Enlace (E.)
            case nomina === '3' && motivoIncapacidad === '5':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadEN100);
                break;
                //=============================== VARIOS/BASE ENFERMEDAD GENERAL Y RIESGO DE TRABAJO (TODOS TIENEN PRESTACIONES) =========================================
                //VARIOS EG Inicial (Se verifica si se cuenta con una incapacidad continua inmediata)
            case nomina === '1' && motivoIncapacidad === '1' && tipoIncapacidad === '1':
                //Se genera el manejo para las incapacidades con más de tres días de retraso entre la fecha inicial y la liberacion
                if (fechaLiberacion > fechaMaximaExtemporanea) {
                    toastr["warning"]("Incapacidad Extemporánea", "Aviso", {progressBar: true, closeButton: true});
                    creaGruposFechasExtemporaneos(fechaInicial, fechaFinal, idIncapacidadPorEg0, idIncapacidadPorEg50, idIncapacidadPorEg75, idIncapacidadPorEg100);
                    return;
                }
                creaTresGruposFechas(fechaInicial, fechaFinal, idIncapacidadPorEg50, idIncapacidadPorEg75, idIncapacidadPorEg100);
                break;
                //VARIOS EG Continua
            case nomina === '1' && motivoIncapacidad === '1' && tipoIncapacidad === '2':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadPorEg100, true);
                break;
                //VARIOS Riesgo de trabajo
            case nomina === '1' && motivoIncapacidad === '2':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadRT100);
                break;
                //VARIOS Pre-Natal
            case nomina === '1' && motivoIncapacidad === '3':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadPrp);
                break;
                //VARIOS Post-Natal
            case nomina === '1' && motivoIncapacidad === '4':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadPos100);
                break;
                //VARIOS Enlace
            case nomina === '1' && motivoIncapacidad === '5':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadEN100);
                break;
                //=============================== TRANSPORTACIÓN ENFERMEDAD GENERAL Y RIESGO DE TRABAJO (TODOS TIENEN PRESTACIONES) =========================================
                //TRANSPORTACIÓN EG Inicial
            case nomina === '2' && motivoIncapacidad === '1' && tipoIncapacidad === '1':
                //Se genera el manejo para las incapacidades con más de tres días de retraso entre la fecha inicial y la liberación
                if (fechaLiberacion > fechaMaximaExtemporanea) {
                    toastr["warning"]("Incapacidad Extemporánea", "Aviso", {progressBar: true, closeButton: true});
                    creaTresGruposFechas(fechaInicial, fechaFinal, idIncapacidadPorEg0, idIncapacidadPorEg75, idIncapacidadPorEg100);
                    return;
                }
                creaTresGruposFechas(fechaInicial, fechaFinal, idIncapacidadPorEg50, idIncapacidadPorEg75, idIncapacidadPorEg100);
                break;
                //TRANSPORTACIÓN EG Continua
            case nomina === '2' && motivoIncapacidad === '1' && tipoIncapacidad === '2':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadPorEg100, true);
                break;
                //TRANSPORTACIÓN Riesgo de trabajo
            case nomina === '2' && motivoIncapacidad === '2':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadRT100);
                break;
                //TRANSPORTACIÓN Pre-Natal
            case nomina === '2' && motivoIncapacidad === '3':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadPrp);
                break;
                //TRANSPORTACIÓN Post-Natal
            case nomina === '2' && motivoIncapacidad === '4':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadPos100);
                break;
                //TRANSPORTACIÓN Enlace
            case nomina === '2' && motivoIncapacidad === '5':
                generarIncidencia(trabajador_id, fechaInicial, fechaFinal, idIncapacidadEN100);
                break;
            default:
                break;
        }
    }, 250);
};
//Se generan estos grupos de fechas extemporaneos para la nómina de varios. Estos se crean al obtener una fecha de liberación mayor a tres días después de la fecha inicial
const creaGruposFechasExtemporaneos = (fechaInicial, fechaFinal, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadGrupo3, idIncapacidadGrupo4) => {
    //Se elimina el mensaje de bootstrap que hace referencia a las incapacidades iniciales + continuas ya que las incapacidades extemporáneas solo aplican a las EG Inicial
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    // Calcular la duración total de la incapacidad en días
    let diffMilliseconds = fechaFinal - fechaInicial;
    let duracionTotal = Math.floor(diffMilliseconds / (1000 * 60 * 60 * 24)) + 1;
    //Verificar continuidad inicial necesario? 
    // Definir las duraciones para cada grupo de días sin contar el primer día
    let diasPrimerGrupo = 0;
    let diasSegundoGrupo = 1;
    let diasTercerGrupo = 3;
    if (duracionTotal >= 1) {
        //PRIMER GRUPO
        let fechaInicioPrimerGrupo = new Date(fechaInicial);
        let fechaFinPrimerGrupo = new Date(fechaInicioPrimerGrupo);
        fechaFinPrimerGrupo.setDate(fechaInicioPrimerGrupo.getDate() + (diasPrimerGrupo));
        // Verificar que no se exceda la fecha final establecida para esa incapacidad
        if (fechaFinPrimerGrupo > fechaFinal) {
            fechaFinPrimerGrupo = new Date(fechaFinal);
        }
        //Generar incidencia para primer grupo (Un solo día)
        generarIncidencia(trabajador_id, fechaInicioPrimerGrupo, fechaFinPrimerGrupo, idIncapacidadGrupo1);
        // VERIFICAR SI HAY MÁS DIAS QUE CORRESPONDAN AL SEGUNDO GRUPO
        if (duracionTotal > diasPrimerGrupo + 1) {
            //SEGUNDO GRUPO
            let fechaInicioSegundoGrupo = new Date(fechaFinPrimerGrupo);
            fechaInicioSegundoGrupo.setDate(fechaFinPrimerGrupo.getDate() + 1);
            let fechaFinSegundoGrupo = new Date(fechaFinal);
            fechaFinSegundoGrupo.setDate(fechaInicioSegundoGrupo.getDate() + diasSegundoGrupo);
            // Verificar que no se exceda la fecha final establecida para esa incapacidad
            if (fechaFinSegundoGrupo > fechaFinal) {
                fechaFinSegundoGrupo = new Date(fechaFinal);
            }
            //Generar incidencia para segundo grupo
            generarIncidencia(trabajador_id, fechaInicioSegundoGrupo, fechaFinSegundoGrupo, idIncapacidadGrupo2);
            // TERCER GRUPO
            if (duracionTotal > diasPrimerGrupo + 1 + diasSegundoGrupo + 1) {
                let fechaInicioTercerGrupo = new Date(fechaFinSegundoGrupo);
                fechaInicioTercerGrupo.setDate(fechaFinSegundoGrupo.getDate() + 1);
                let fechaFinTercerGrupo = new Date(fechaFinal);
                fechaFinTercerGrupo.setDate(fechaInicioTercerGrupo.getDate() + diasTercerGrupo);
                // Verificar que no se exceda la fecha final establecida para esa incapacidad
                if (fechaFinTercerGrupo > fechaFinal) {
                    fechaFinTercerGrupo = new Date(fechaFinal);
                }
                //Generar incidencia para tercer grupo
                generarIncidencia(trabajador_id, fechaInicioTercerGrupo, fechaFinTercerGrupo, idIncapacidadGrupo3);
                //CUARTO GRUPO (Pagado al 100%)
                if (duracionTotal > diasPrimerGrupo + 1 + diasSegundoGrupo + 1 + diasTercerGrupo + 1) {
                    let fechaInicioCuartoGrupo = new Date(fechaFinTercerGrupo);
                    fechaInicioCuartoGrupo.setDate(fechaFinTercerGrupo.getDate() + 1);
                    let fechaFinCuartoGrupo = new Date(fechaFinal);
                    // Verificar que no se exceda la fecha final establecida para esa incapacidad
                    if (fechaFinTercerGrupo > fechaFinal) {
                        fechaFinTercerGrupo = new Date(fechaFinal);
                    }
                    //Generar incidencia para cuarto grupo
                    generarIncidencia(trabajador_id, fechaInicioCuartoGrupo, fechaFinCuartoGrupo, idIncapacidadGrupo4);
                }
            }
        }
    } else {
        // Si la duración total es menor que 1 día
        toastr["warning"]("Duración de la incapacidad no válida ", {progressBar: true, closeButton: true});
    }
};

//Esta función genera los grupos de dias para después generar la incidencia correspondiente, conecta la continua si se encuentra inmediatamente un día después de la fecha final de la inicial
const creaTresGruposFechas = (fechaInicial, fechaFinal, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadGrupo3) => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    // Calcular la duración total de la incapacidad en días
    let diffMilliseconds = fechaFinal - fechaInicial;
    let duracionTotal = Math.floor(diffMilliseconds / (1000 * 60 * 60 * 24)) + 1;
    // Fecha inicial tentativa incapacidad continua
    const fechaFinalMasUnDia = new Date(fechaFinal);
    fechaFinalMasUnDia.setDate(fechaFinalMasUnDia.getDate() + 1);
    //==========================COMPROBAR CONTINUIDAD=======================
    verificarContinuidadInicial(fechaFinalMasUnDia)
            .then((data) => {
                //Si data es diferente de nulo es porque encontró una incapacidad continua inmediatamente después del fin de la incapacidad inicial
                if (data !== null) {
                    // Se muestra el modal de confirmación para fusionar las incapacidades
                    $('#modalAlertaFusionIncapacidades').modal('show');
                    // Maneja el clic en "Sí" dentro del modal
                    $('#modalAlertaFusionIncapacidades #formAvisoAlertaDeContinuidadIncapacidades button.btn-primary').off('click').on('click', function (e) {
                        e.stopImmediatePropagation();
                        fusionarIncapacidadParaTresGruposFechas(data, trabajador_id, fechaInicial, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadGrupo3, duracionTotal);
                        $('#modalAlertaFusionIncapacidades').modal('hide');
                    });
                    // Maneja el clic en "No" dentro del modal
                    $('#modalAlertaFusionIncapacidades #cancelarFusion').off('click').on('click', function (e) {
                        e.stopImmediatePropagation();
                        noFusionarIncapacidadParaTresGruposDeFechas(fechaInicial, fechaFinal, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadGrupo3, trabajador_id, duracionTotal);
                        $('#modalAlertaFusionIncapacidades').modal('hide');
                    });
                    //Si data es nulo es porque no encontró una incapacidad continua el día siguiente a la fecha final de la inicial, se maneja como incapacidad inicial única
                } else {
                    noFusionarIncapacidadParaTresGruposDeFechas(fechaInicial, fechaFinal, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadGrupo3, trabajador_id, duracionTotal);
                }
            })
            .catch((error) => {
                toastr.error("Se ha producido un error: " + error.message, "Error");
            });
};

const fusionarIncapacidadParaTresGruposFechas = (data, trabajador_id, fechaInicial, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadGrupo3, duracionTotal) => {
//Se asigna la fecha final para que sea la fecha final de la incapacidad continua para asignar de manera correcta los porcentajes
    //Transformación a objeto date de js de la fecha final de la incapacidad continua
    const fechaFinalStr = (data.fecha_final);
    const [year, month, day] = fechaFinalStr.split('T')[0].split('-');
    const [hour, minute, second] = fechaFinalStr.split('T')[1].split('.')[0].split(':');
    //DEJO DE MANEJARSE COMO FECHA FINAL STR, CAMBIAR A FECHA FINAL
    const fechaFinal = new Date(year, month - 1, day, hour, minute, second);
    // ===================VERIFICAR SI CORRESPONDEN AL MISMO AÑO==============================
    const yearInicial = fechaInicial.getFullYear();
    const yearFinal = fechaFinal.getFullYear();
    //***************************************DIFERENTE AÑO INICIAL + CONTINUA ***************************************
    if (yearInicial !== yearFinal) {
        //Identificador de la incapacidad continua
        let idIncapacidadContinua = data.id_incapacidad;
        //Se define la fecha final como el fin de año (31 de Diciembre)
        const ultimoDiaDelAnio = new Date(yearInicial, 11, 31);
        // Crear una fecha con el primer día del próximo año (1 de Enero)
        const primerDiaDelProximoAnio = new Date(yearFinal, 0, 01);
        //Cuenta los días que corresponden al primer año
        let conteoGrupoAnio1 = Math.floor((ultimoDiaDelAnio - fechaInicial) / (1000 * 60 * 60 * 24)) + 1;
        //Cuenta los días que corresponden al segudo año
        let conteoGrupoAnio2 = Math.floor((fechaFinal - primerDiaDelProximoAnio) / (1000 * 60 * 60 * 24)) + 1;
        //si diferenciaConteoPrimerAnio + diferenciaConteoSegundoAnio >200 aplicar reglas día 201 en adelante
        // Fechas Generadas
//                        console.log('Fecha inicial de la incapacidad:', fechaInicial.toString());
//                        console.log('Último día del año en curso:', ultimoDiaDelAnio.toString());
//                        console.log('Primer día del próximo año:', primerDiaDelProximoAnio.toString());
//                        console.log('Fecha final de la incapacidad:', fechaFinal.toString());
//                        console.log('Conteo primer año:', conteoGrupoAnio1);
//                        console.log('Conteo segundo año:', conteoGrupoAnio2);
        //Se utiliza para generar las incapacidades que cambian de año (Actual hasta fin de año);
        crearTresGruposEspecialesFechas(fechaInicial, ultimoDiaDelAnio, conteoGrupoAnio1, null, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadGrupo3, idIncapacidadContinua);
        //Se utiliza para generar las incapacidades que corresponden al siguiente año (Reinicio de incapacidad, se vuelve a tratar como inicial)
        crearTresGruposEspecialesFechas(primerDiaDelProximoAnio, fechaFinal, null, conteoGrupoAnio2, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadGrupo3, idIncapacidadContinua);
        //***************************************MISMO AÑO INICIAL + CONTINUA***************************************
    } else {
        //Id de la incapacidad continua
        let idIncapacidadContinua = data.id_incapacidad;
        let diasPrimerGrupo = 2;
        let diasSegundoGrupo = 3;
        //Se recalculan con la nueva fecha final de la incapacidad continua
        diffMilliseconds = fechaFinal - fechaInicial;
        duracionTotal = Math.floor(diffMilliseconds / (1000 * 60 * 60 * 24)) + 1;
        if (duracionTotal >= 1) {
            //PRIMER GRUPO
            let fechaInicioPrimerGrupo = new Date(fechaInicial);
            let fechaFinPrimerGrupo = new Date(fechaInicioPrimerGrupo);
            fechaFinPrimerGrupo.setDate(fechaInicioPrimerGrupo.getDate() + (diasPrimerGrupo));
            // Verificar que no se exceda la fecha final establecida para esa incapacidad
            if (fechaFinPrimerGrupo > fechaFinal) {
                fechaFinPrimerGrupo = new Date(fechaFinal);
            }
            //Generar incidencia para primer grupo
            generarIncidencia(trabajador_id, fechaInicioPrimerGrupo, fechaFinPrimerGrupo, idIncapacidadGrupo1);
            // VERIFICAR SI HAY MÁS DIAS QUE CORRESPONDAN AL SEGUNDO GRUPO
            if (duracionTotal > diasPrimerGrupo + 1) {
                //SEGUNDO GRUPO
                let fechaInicioSegundoGrupo = new Date(fechaFinPrimerGrupo);
                fechaInicioSegundoGrupo.setDate(fechaFinPrimerGrupo.getDate() + 1);
                let fechaFinSegundoGrupo = new Date(fechaInicioSegundoGrupo);
                fechaFinSegundoGrupo.setDate(fechaInicioSegundoGrupo.getDate() + diasSegundoGrupo);
                // Verificar que no se exceda la fecha final establecida para esa incapacidad
                if (fechaFinSegundoGrupo > fechaFinal) {
                    fechaFinSegundoGrupo = new Date(fechaFinal);
                }
                //Generar incidencia para segundo grupo
                generarIncidencia(trabajador_id, fechaInicioSegundoGrupo, fechaFinSegundoGrupo, idIncapacidadGrupo2);
                // TERCER GRUPO
                if (duracionTotal > diasPrimerGrupo + 1 + diasSegundoGrupo + 1) {
                    let fechaInicioTercerGrupo = new Date(fechaFinSegundoGrupo);
                    fechaInicioTercerGrupo.setDate(fechaFinSegundoGrupo.getDate() + 1);
                    let fechaFinTercerGrupo = new Date(fechaFinalStr);
                    // Verificar que no se exceda la fecha final establecida para esa incapacidad
                    if (fechaFinTercerGrupo > fechaFinalStr) {
                        fechaFinTercerGrupo = new Date(fechaFinalStr);
                    }
                    // ==============Caso 201 días en adelante================
                    diffMilliseconds = fechaFinTercerGrupo - fechaInicioPrimerGrupo;
                    conteoDiasTranscurridos = Math.floor(diffMilliseconds / (1000 * 60 * 60 * 24)) + 1;
                    if (conteoDiasTranscurridos > 200) {
                        // Se superaron los 200 días, por lo que ajustamos fechaFinSegundoGrupo a fechaInicioSegundoGrupo + 200 días
                        fechaFinTercerGrupo = new Date(fechaInicioTercerGrupo);
                        //192 días ya contabilizando los primeros 7
                        fechaFinTercerGrupo.setDate(fechaInicioTercerGrupo.getDate() + 192);
                        //Se genera la incidencia que abarca los 200 días
                        generarIncidencia(trabajador_id, fechaInicioTercerGrupo, fechaFinTercerGrupo, idIncapacidadGrupo3);
                        let fechaInicioDia200 = new Date(fechaFinTercerGrupo);
                        fechaInicioDia200.setDate(fechaFinTercerGrupo.getDate() + 1);
                        let fechaFinDia200 = new Date(fechaFinalStr);
                        let idIncidenciaDia201 = 31; //Compensación por EG al 60
                        //Se genera la incidencia para el dia 201 en adelante
                        generarIncidencia(trabajador_id, fechaInicioDia200, fechaFinDia200, idIncidenciaDia201);
                    } else {
                        //Generar incidencia para tercer grupo sin consideración para los 200 días
                        generarIncidencia(trabajador_id, fechaInicioTercerGrupo, fechaFinTercerGrupo, idIncapacidadGrupo3);
                    }
                }
            }
            /*Se agina automáticamente la fecha_autorización cuando se llame a este servicio 
             * Se autoriza la incapacidad continua*/
            var autorizar = 1;
            var datos = {"autorizar": autorizar};
            ajaxEstatusIncapacidad(idIncapacidadContinua, datos);
        } else {
            // Si la duración total es menor que 1 día
            toastr["warning"]("Duración de la incapacidad no válida ", {progressBar: true, closeButton: true});
        }
    }
};

const noFusionarIncapacidadParaTresGruposDeFechas = (fechaInicial, fechaFinal, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadGrupo3, trabajador_id, duracionTotal) => {
    // ===================VERIFICAR SI CORRESPONDEN AL MISMO AÑO==============================
    const yearInicial = fechaInicial.getFullYear();
    const yearFinal = fechaFinal.getFullYear();
    //***************************************DIFERENTE AÑO ***************************************
    if (yearInicial !== yearFinal) {
        //Se define la fecha final como el fin de año (31 de Diciembre)
        const ultimoDiaDelAnio = new Date(yearInicial, 11, 31);
        // Crear una fecha con el primer día del próximo año (1 de Enero)
        const primerDiaDelProximoAnio = new Date(yearFinal, 0, 01);
        //Cuenta los días que corresponden al primer año
        let conteoGrupoAnio1 = Math.floor((ultimoDiaDelAnio - fechaInicial) / (1000 * 60 * 60 * 24)) + 1;
        //Cuenta los días que corresponden al segudo año
        let conteoGrupoAnio2 = Math.floor((fechaFinal - primerDiaDelProximoAnio) / (1000 * 60 * 60 * 24)) + 1;
        //si diferenciaConteoPrimerAnio + diferenciaConteoSegundoAnio >200 aplicar reglas día 201 en adelante
        // Fechas Generadas
//                        console.log('Fecha inicial de la incapacidad:', fechaInicial.toString());
//                        console.log('Último día del año en curso:', ultimoDiaDelAnio.toString());
//                        console.log('Primer día del próximo año:', primerDiaDelProximoAnio.toString());
//                        console.log('Fecha final de la incapacidad:', fechaFinal.toString());
//                        console.log('Conteo primer año:', conteoGrupoAnio1);
//                        console.log('Conteo segundo año:', conteoGrupoAnio2);
        //Se utiliza para generar las incapacidades que cambian de año (Actual hasta fin de año);
        crearTresGruposEspecialesFechas(fechaInicial, ultimoDiaDelAnio, conteoGrupoAnio1, null, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadGrupo3, null);
        //Se utiliza para generar las incapacidades que cambian de año (Nuevo año hasta fin de la incapacidad);
        crearTresGruposEspecialesFechas(primerDiaDelProximoAnio, fechaFinal, null, conteoGrupoAnio2, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadGrupo3, null);
        //***************************************MISMO AÑO ***************************************
    } else {
        // Definir las duraciones para cada grupo de días sin contar el primer día
        let diasPrimerGrupo = 2;
        let diasSegundoGrupo = 3;
        if (duracionTotal >= 1) {
            //PRIMER GRUPO
            let fechaInicioPrimerGrupo = new Date(fechaInicial);
            let fechaFinPrimerGrupo = new Date(fechaInicioPrimerGrupo);
            fechaFinPrimerGrupo.setDate(fechaInicioPrimerGrupo.getDate() + (diasPrimerGrupo));
            // Verificar que no se exceda la fecha final establecida para esa incapacidad
            if (fechaFinPrimerGrupo > fechaFinal) {
                fechaFinPrimerGrupo = new Date(fechaFinal);
            }
            //Generar incidencia para primer grupo
            generarIncidencia(trabajador_id, fechaInicioPrimerGrupo, fechaFinPrimerGrupo, idIncapacidadGrupo1);
            // VERIFICAR SI HAY MÁS DIAS QUE CORRESPONDAN AL SEGUNDO GRUPO
            if (duracionTotal > diasPrimerGrupo + 1) {
                //SEGUNDO GRUPO
                let fechaInicioSegundoGrupo = new Date(fechaFinPrimerGrupo);
                fechaInicioSegundoGrupo.setDate(fechaFinPrimerGrupo.getDate() + 1);
                let fechaFinSegundoGrupo = new Date(fechaFinal);
                fechaFinSegundoGrupo.setDate(fechaInicioSegundoGrupo.getDate() + diasSegundoGrupo);
                // Verificar que no se exceda la fecha final establecida para esa incapacidad
                if (fechaFinSegundoGrupo > fechaFinal) {
                    fechaFinSegundoGrupo = new Date(fechaFinal);
                }
                //Generar incidencia para segundo grupo
                generarIncidencia(trabajador_id, fechaInicioSegundoGrupo, fechaFinSegundoGrupo, idIncapacidadGrupo2);
                // TERCER GRUPO
                if (duracionTotal > diasPrimerGrupo + 1 + diasSegundoGrupo + 1) {
                    let fechaInicioTercerGrupo = new Date(fechaFinSegundoGrupo);
                    fechaInicioTercerGrupo.setDate(fechaFinSegundoGrupo.getDate() + 1);
                    let fechaFinTercerGrupo = new Date(fechaFinal);
                    // Verificar que no se exceda la fecha final establecida para esa incapacidad
                    if (fechaFinTercerGrupo > fechaFinal) {
                        fechaFinTercerGrupo = new Date(fechaFinal);
                    }
                    //********************VERIFICAR SI EXISTEN INCAPACIDADES INICIALES DE MAS DE 200 DÍAS
                    //Generar incidencia para tercer grupo
                    generarIncidencia(trabajador_id, fechaInicioTercerGrupo, fechaFinTercerGrupo, idIncapacidadGrupo3);
                }
            }
        } else {
            // Si la duración total es menor que 1 día
            toastr["warning"]("Duración de la incapacidad no válida ", {progressBar: true, closeButton: true});
        }
    }
};

//Utilizado para manejar las incidencias que cambian de año
const crearTresGruposEspecialesFechas = (fechaInicio, fechaFin, diferenciaConteoPrimerAnio, diferenciaConteoSegundoAnio, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadGrupo3, idIncapacidadContinua) => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    let diasPrimerGrupo = 2;
    let diasSegundoGrupo = 3;
    //Se recalculan con la nueva fecha final de la incapacidad continua
    diffMilliseconds = fechaFin - fechaInicio;
    //Dureción total de cada grupo de fechas, primer y segundo año
    duracionTotal = Math.floor(diffMilliseconds / (1000 * 60 * 60 * 24)) + 1;
    if (duracionTotal >= 1) {
        //PRIMER GRUPO
        let fechaInicioPrimerGrupo = new Date(fechaInicio);
        let fechaFinPrimerGrupo = new Date(fechaInicioPrimerGrupo);
        fechaFinPrimerGrupo.setDate(fechaInicioPrimerGrupo.getDate() + (diasPrimerGrupo));
        // Verificar que no se exceda la fecha final establecida para esa incapacidad
        if (fechaFinPrimerGrupo > fechaFin) {
            fechaFinPrimerGrupo = new Date(fechaFin);
        }
        //Generar incidencia para primer grupo
        generarIncidencia(trabajador_id, fechaInicioPrimerGrupo, fechaFinPrimerGrupo, idIncapacidadGrupo1);
        // VERIFICAR SI HAY MÁS DIAS QUE CORRESPONDAN AL SEGUNDO GRUPO
        if (duracionTotal > diasPrimerGrupo + 1) {
            //SEGUNDO GRUPO
            let fechaInicioSegundoGrupo = new Date(fechaFinPrimerGrupo);
            fechaInicioSegundoGrupo.setDate(fechaFinPrimerGrupo.getDate() + 1);
            let fechaFinSegundoGrupo = new Date(fechaInicioSegundoGrupo);
            fechaFinSegundoGrupo.setDate(fechaInicioSegundoGrupo.getDate() + diasSegundoGrupo);
            // Verificar que no se exceda la fecha final establecida para esa incapacidad
            if (fechaFinSegundoGrupo > fechaFin) {
                fechaFinSegundoGrupo = new Date(fechaFin);
            }
            //Generar incidencia para segundo grupo
            generarIncidencia(trabajador_id, fechaInicioSegundoGrupo, fechaFinSegundoGrupo, idIncapacidadGrupo2);
            // TERCER GRUPO
            if (duracionTotal > diasPrimerGrupo + 1 + diasSegundoGrupo + 1) {
                let fechaInicioTercerGrupo = new Date(fechaFinSegundoGrupo);
                fechaInicioTercerGrupo.setDate(fechaFinSegundoGrupo.getDate() + 1);
                let fechaFinTercerGrupo = new Date(fechaFin);
                // Verificar que no se exceda la fecha final establecida para esa incapacidad
                if (fechaFinTercerGrupo > fechaFin) {
                    fechaFinTercerGrupo = new Date(fechaFin);
                }
                // ==============Caso 201 días en adelante, conteo de 200 días solo aplica para un año, al siguiente año se hace reset (Primer año)================
                if (diferenciaConteoPrimerAnio > 200 && diferenciaConteoPrimerAnio !== null) {
                    //Fecha de inicio siempre será el inicio de año del segundo grupo
                    fechaFinTercerGrupo = new Date(fechaInicioPrimerGrupo);
                    fechaFinTercerGrupo.setDate(fechaInicioPrimerGrupo.getDate() + 199);
                    //Se genera la incidencia que abarca los 200 días
                    generarIncidencia(trabajador_id, fechaInicioTercerGrupo, fechaFinTercerGrupo, idIncapacidadGrupo3);
                    //Se pone la fecha de inicio como fecha final más uno
                    let fechaInicioDia200 = new Date(fechaFinTercerGrupo);
                    fechaInicioDia200.setDate(fechaFinTercerGrupo.getDate() + 1);
                    //Se asigna la fecha final absoluta como fin
                    let fechaFinDia200 = new Date(fechaFin);
                    let idIncidenciaDia201 = 31; //Compensación por EG al 60
                    //Se genera la incidencia para el dia 201 en adelante
                    generarIncidencia(trabajador_id, fechaInicioDia200, fechaFinDia200, idIncidenciaDia201);
                    // ==============Caso 201 días en adelante, conteo de 200 días solo aplica para un año, al siguiente año se hace reset (Segundo año)================
                } else if (diferenciaConteoSegundoAnio > 200 && diferenciaConteoSegundoAnio !== null) {
                    //Fecha de inicio siempre será el inicio de año del segundo grupo
                    fechaFinTercerGrupo = new Date(fechaInicioPrimerGrupo);
                    fechaFinTercerGrupo.setDate(fechaInicioPrimerGrupo.getDate() + 199);
                    //Se genera la incidencia que abarca los 200 días
                    generarIncidencia(trabajador_id, fechaInicioTercerGrupo, fechaFinTercerGrupo, idIncapacidadGrupo3);
                    //Se pone la fecha de inicio como fecha final más uno
                    let fechaInicioDia200 = new Date(fechaFinTercerGrupo);
                    fechaInicioDia200.setDate(fechaFinTercerGrupo.getDate() + 1);
                    //Se asigna la fecha final absoluta como fin
                    let fechaFinDia200 = new Date(fechaFin);
                    let idIncidenciaDia201 = 31; //Compensación por EG al 60
                    //Se genera la incidencia para el dia 201 en adelante
                    generarIncidencia(trabajador_id, fechaInicioDia200, fechaFinDia200, idIncidenciaDia201);
                } else {
                    //Se colocan las hora de termino a medio dia para evitar problemas con la fecha(Se desfasa un día)
                    fechaFinTercerGrupo.setHours(12, 0, 0, 0);
                    //Generar incidencia para tercer grupo de manera normal
                    generarIncidencia(trabajador_id, fechaInicioTercerGrupo, fechaFinTercerGrupo, idIncapacidadGrupo3);
                }
            }
        }
        /*Se agina automáticamente la fecha_autorización cuando se llame a este servicio 
         * Se autoriza la incapacidad continua*/
        if (idIncapacidadContinua !== null) {
            var autorizar = 1;
            var datos = {"autorizar": autorizar};
            ajaxEstatusIncapacidad(idIncapacidadContinua, datos);
        }
    } else {
        // Si la duración total es menor que 1 día
        toastr["warning"]("Duración de la incapacidad no válida ", {progressBar: true, closeButton: true});
    }
};
//Utilizado para manejar las incidencias que cambian de año
const crearDosGruposEspecialesFechas = (fechaInicio, fechaFin, diferenciaConteoPrimerAnio, diferenciaConteoSegundoAnio, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadContinua, flagSegundoGrupo) => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    diffMilliseconds = fechaFin - fechaInicio;
    duracionTotal = Math.floor(diffMilliseconds / (1000 * 60 * 60 * 24)) + 1;
    let diasPrimerGrupo = 2;
    //Se comprueba que la incapacidad sea mayor a un día
    if (duracionTotal >= 1) {
        //PRIMER GRUPO
        let fechaInicioPrimerGrupo = new Date(fechaInicio);
        let fechaFinPrimerGrupo = new Date(fechaInicioPrimerGrupo);
        fechaFinPrimerGrupo.setDate(fechaInicioPrimerGrupo.getDate() + (diasPrimerGrupo));
        // Verificar que no se exceda la fecha final establecida para esa incapacidad
        if (fechaFinPrimerGrupo > fechaFin) {
            fechaFinPrimerGrupo = new Date(fechaFin);
        }
        //Generar incidencia para primer grupo
        generarIncidencia(trabajador_id, fechaInicioPrimerGrupo, fechaFinPrimerGrupo, idIncapacidadGrupo1);
        // VERIFICAR SI HAY MÁS DIAS QUE CORRESPONDAN AL SEGUNDO GRUPO
        if (duracionTotal > diasPrimerGrupo + 1) {
            //SEGUNDO GRUPO
            let fechaInicioSegundoGrupo = new Date(fechaFinPrimerGrupo);
            fechaInicioSegundoGrupo.setDate(fechaFinPrimerGrupo.getDate() + 1);
            let fechaFinSegundoGrupo = new Date(fechaFin); //Hasta el fin de año
            // ==============Caso 201 días en adelante, funciona solo en el siguiente año================
            if (diferenciaConteoPrimerAnio + diferenciaConteoSegundoAnio > 200 && flagSegundoGrupo === true && diferenciaConteoPrimerAnio <= 200) {
                // Se superaron los 200 días, por lo que se ajusta los 200 días menos los días pasados del primer año
                fechaFinSegundoGrupo = new Date(fechaInicioPrimerGrupo);
                fechaFinSegundoGrupo.setDate(fechaInicioPrimerGrupo.getDate() + 199 - diferenciaConteoPrimerAnio);
                //Se genera la incidencia que abarca el resto de los 200 días (199 - diferenciaConteoPrimerAnio)
                generarIncidencia(trabajador_id, fechaInicioSegundoGrupo, fechaFinSegundoGrupo, idIncapacidadGrupo2);
                let fechaInicioDia200 = new Date(fechaFinSegundoGrupo);
                fechaInicioDia200.setDate(fechaFinSegundoGrupo.getDate() + 1);
                let fechaFinDia200 = new Date(fechaFin);
                let idIncidenciaDia201 = 31; //Compensación por EG al 60
                //Se genera la incidencia para el dia 201 en adelante
                generarIncidencia(trabajador_id, fechaInicioDia200, fechaFinDia200, idIncidenciaDia201);
            } else {
                //Generar incidencia para segundo grupo
                generarIncidencia(trabajador_id, fechaInicioSegundoGrupo, fechaFinSegundoGrupo, idIncapacidadGrupo2);
            }
        }
    } else {
        // Si la duración total es menor que 1 día
        toastr["warning"]("Duración de la incapacidad no válida ", {progressBar: true, closeButton: true});
    }

    /*Se agina automáticamente la fecha_autorización cuando se llame a este servicio 
     * Se autoriza la incapacidad continua*/
    if (idIncapacidadContinua !== null) {
        var autorizar = 1;
        var datos = {"autorizar": autorizar};

        ajaxEstatusIncapacidad(idIncapacidadContinua, datos);
    }
};

//Esta función genera los grupos de dias para después generar la incidencia correspondiente, verifica continuidades de incapacidades iniciales
const creaDosGruposFechas = (idIncapacidadGrupo1, idIncapacidadGrupo2, fechaInicial, fechaFinal) => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    // Calcular la duración total de la incapacidad en días
    const diferenciaEnMilisegundos = fechaFinal - fechaInicial;
    let duracionTotal = diferenciaEnMilisegundos / 86400000 + 1;
    // Fecha inicial tentativa incapacidad continua
    const fechaFinalMasUnDia = new Date(fechaFinal);
    fechaFinalMasUnDia.setDate(fechaFinalMasUnDia.getDate() + 1);
    //==========================COMPROBAR CONTINUIDAD=======================
    verificarContinuidadInicial(fechaFinalMasUnDia)
            .then((data) => {
                //==========================DATA DIFERENTE DE NULO, CONTINUIDAD A UNA INICIAL=======================
                if (data !== null) {
                    // Se muestra el modal de confirmación para fusionar las incapacidades
                    $('#modalAlertaFusionIncapacidades').modal('show');
                    // Maneja el clic en "Sí" dentro del modal
                    $('#modalAlertaFusionIncapacidades #formAvisoAlertaDeContinuidadIncapacidades button.btn-primary').off('click').on('click', function (e) {
                        e.stopImmediatePropagation();
                        fusionarIncapacidadParaDosGruposDeFechas(data, fechaInicial, idIncapacidadGrupo1, idIncapacidadGrupo2, trabajador_id);
                        $('#modalAlertaFusionIncapacidades').modal('hide');
                    });
                    // Maneja el clic en "No" dentro del modal
                    $('#modalAlertaFusionIncapacidades #cancelarFusion').off('click').on('click', function (e) {
                        e.stopImmediatePropagation();
                        noFusionarIncapacidadParaDosGruposDeFechas(fechaInicial, fechaFinal, idIncapacidadGrupo1, idIncapacidadGrupo2, trabajador_id, duracionTotal);
                        $('#modalAlertaFusionIncapacidades').modal('hide');
                    });
                }
                //==========================DATA ES NULO, NO HAY CONTINUIDAD A UNA INICIAL=======================
                else {
                    noFusionarIncapacidadParaDosGruposDeFechas(fechaInicial, fechaFinal, idIncapacidadGrupo1, idIncapacidadGrupo2, trabajador_id, duracionTotal);
                }
            })
            .catch((error) => {
                toastr.error("Se ha producido un error: " + error.message, "Error");
            });
};

const fusionarIncapacidadParaDosGruposDeFechas = (data, fechaInicial, idIncapacidadGrupo1, idIncapacidadGrupo2, trabajador_id) => {
    //Transformación a objeto date de js de la fecha final de la incapacidad continua
    const fechaFinalStr = (data.fecha_final);
    const [year, month, day] = fechaFinalStr.split('T')[0].split('-');
    const [hour, minute, second] = fechaFinalStr.split('T')[1].split('.')[0].split(':');
    ///Se asigna la fecha final para que sea la fecha final de la incapacidad continua para asignar de manera correcta los porcentajes 
    //DEJO DE MANEJARSE COMO FECHA FINAL STR, CAMBIAR A FECHA FINAL
    const fechaFinal = new Date(year, month - 1, day, hour, minute, second);
    // ===================VERIFICAR SI CORRESPONDEN AL MISMO AÑO==============================
    const yearInicial = fechaInicial.getFullYear();
    const yearFinal = fechaFinal.getFullYear();
    //***************************************DIFERENTE AÑO ***************************************
    if (yearInicial !== yearFinal) {
        //Identificador de la incapacidad continua
        let idIncapacidadContinua = data.id_incapacidad;
        //Se define la fecha final como el fin de año (31 de Diciembre)
        const ultimoDiaDelAnio = new Date(yearInicial, 11, 31);
        // Crear una fecha con el primer día del próximo año (1 de Enero)
        const primerDiaDelProximoAnio = new Date(yearFinal, 0, 01);
        let conteoGrupoAnio1 = Math.floor((ultimoDiaDelAnio - fechaInicial) / (1000 * 60 * 60 * 24)) + 1;
        let conteoGrupoAnio2 = Math.floor((fechaFinal - primerDiaDelProximoAnio) / (1000 * 60 * 60 * 24)) + 1;
        // Fechas Generadas
//                        console.log('Fecha inicial de la incapacidad:', fechaInicial);
//                        console.log('Último día del año en curso:', ultimoDiaDelAnio);
//                        console.log('Primer día del próximo año:', primerDiaDelProximoAnio);
//                        console.log('Fecha final de la incapacidad:', fechaFinal);
        //Se utiliza para generar las incapacidades que cambian de año (Actual hasta fin de año);
        crearDosGruposEspecialesFechas(fechaInicial, ultimoDiaDelAnio, conteoGrupoAnio1, conteoGrupoAnio2, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadContinua, false);
        //Se utiliza para generar las incapacidades que corresponden al siguiente año (Reinicio de incapacidad, se vuelve a tratar como inicial)
        crearDosGruposEspecialesFechas(primerDiaDelProximoAnio, fechaFinal, conteoGrupoAnio1, conteoGrupoAnio2, idIncapacidadGrupo1, idIncapacidadGrupo2, idIncapacidadContinua, true);
        //***************************************MISMO AÑO INICIAL + CONTINUA ***************************************
    } else {
        //Id de la incapacidad continua
        let idIncapacidadContinua = data.id_incapacidad;
        //Se recalculan con la nueva fecha final de la incapacidad continua
        diffMilliseconds = fechaFinal - fechaInicial;
        duracionTotal = Math.floor((fechaFinal - fechaInicial) / (1000 * 60 * 60 * 24)) + 1;
        let diasPrimerGrupo = 2;
        if (duracionTotal >= 1) {
            //PRIMER GRUPO
            let fechaInicioPrimerGrupo = fechaInicial;
            let fechaFinPrimerGrupo = new Date(fechaInicioPrimerGrupo);
            fechaFinPrimerGrupo.setDate(fechaInicioPrimerGrupo.getDate() + (diasPrimerGrupo));
            // Verificar que no se exceda la fecha final establecida para esa incapacidad
            if (fechaFinPrimerGrupo > fechaFinal) {
                fechaFinPrimerGrupo = fechaFinal;
            }
            //Generar incidencia para primer grupo
            generarIncidencia(trabajador_id, fechaInicioPrimerGrupo, fechaFinPrimerGrupo, idIncapacidadGrupo1);
            // VERIFICAR SI HAY MÁS DIAS QUE CORRESPONDAN AL SEGUNDO GRUPO
            if (duracionTotal > diasPrimerGrupo + 1) {
                //SEGUNDO GRUPO
                let fechaInicioSegundoGrupo = fechaFinPrimerGrupo;
                fechaInicioSegundoGrupo.setDate(fechaFinPrimerGrupo.getDate() + 1);
                let fechaFinSegundoGrupo = fechaFinal;
                // ==============Caso 201 días en adelante================
                diffMilliseconds = fechaFinSegundoGrupo - fechaInicioSegundoGrupo;
                conteoDiasTranscurridos = Math.floor(diffMilliseconds / (1000 * 60 * 60 * 24)) + 1;
                if (conteoDiasTranscurridos > 200) {
                    // Se superaron los 200 días, por lo que ajustamos fechaFinSegundoGrupo a fechaInicioSegundoGrupo + 200 días
                    fechaFinSegundoGrupo = new Date(fechaInicial);
                    fechaFinSegundoGrupo.setDate(fechaInicial.getDate() + 199);
                    //Se genera la incidencia que abarca los 200 días
                    generarIncidencia(trabajador_id, fechaInicioSegundoGrupo, fechaFinSegundoGrupo, idIncapacidadGrupo2);
                    let fechaInicioDia200 = new Date(fechaFinSegundoGrupo);
                    fechaInicioDia200.setDate(fechaFinSegundoGrupo.getDate() + 1);
                    let fechaFinDia200 = new Date(fechaFinal);
                    let idIncidenciaDia201 = 31; //Compensación por EG al 60
                    //Se genera la incidencia para el dia 201 en adelante
                    generarIncidencia(trabajador_id, fechaInicioDia200, fechaFinDia200, idIncidenciaDia201);
                } else {
                    //Generar incidencia para segundo grupo que no excede los 200 días
                    generarIncidencia(trabajador_id, fechaInicioSegundoGrupo, fechaFinSegundoGrupo, idIncapacidadGrupo2);
                }
            }
        } else {
            // Si la duración total es menor que 1 día
            toastr["warning"]("Duración de la incapacidad no válida ", {progressBar: true, closeButton: true});
        }

        /*Se agina automáticamente la fecha_autorización cuando se llame a este servicio 
         * Se autoriza la incapacidad continua*/
        var autorizar = 1;
        var datos = {"autorizar": autorizar};

        ajaxEstatusIncapacidad(idIncapacidadContinua, datos);
    }
};

const noFusionarIncapacidadParaDosGruposDeFechas = (fechaInicial, fechaFinal, idIncapacidadGrupo1, idIncapacidadGrupo2, trabajador_id, duracionTotal) => {
    // ===================VERIFICAR SI CORRESPONDEN AL MISMO AÑO==============================
    const yearInicial = fechaInicial.getFullYear();
    const yearFinal = fechaFinal.getFullYear();
    //***************************************DIFERENTE AÑO ***************************************
    if (yearInicial !== yearFinal) {
        //Se define la fecha final como el fin de año (31 de Diciembre)
        const ultimoDiaDelAnio = new Date(yearInicial, 11, 31);
        // Crear una fecha con el primer día del próximo año (1 de Enero)
        const primerDiaDelProximoAnio = new Date(yearFinal, 0, 01);
        let conteoGrupoAnio1 = Math.floor((ultimoDiaDelAnio - fechaInicial) / (1000 * 60 * 60 * 24)) + 1;
        let conteoGrupoAnio2 = Math.floor((fechaFinal - primerDiaDelProximoAnio) / (1000 * 60 * 60 * 24)) + 1;
        // Fechas Generadas
//                        console.log('Fecha inicial de la incapacidad:', fechaInicialStr.toString());
//                        console.log('Último día del año en curso:', ultimoDiaDelAnio.toString());
//                        console.log('Primer día del próximo año:', primerDiaDelProximoAnio.toString());
//                        console.log('Fecha final de la incapacidad:', fechaFinalStr.toString());
        //Se utiliza para generar las incapacidades que cambian de año (Actual hasta fin de año);
        crearDosGruposEspecialesFechas(fechaInicial, ultimoDiaDelAnio, conteoGrupoAnio1, conteoGrupoAnio2, idIncapacidadGrupo1, idIncapacidadGrupo2, null, false);
        //Se utiliza para generar las incapacidades que corresponden al siguiente año (Reinicio de incapacidad, se vuelve a tratar como inicial)
        crearDosGruposEspecialesFechas(primerDiaDelProximoAnio, fechaFinal, conteoGrupoAnio1, conteoGrupoAnio2, idIncapacidadGrupo1, idIncapacidadGrupo2, null, true);
        //***************************************MISMO AÑO ***************************************
    } else {
        if (duracionTotal >= 1) {
            let diasPrimerGrupo = 2;
            //PRIMER GRUPO
            let fechaInicioPrimerGrupo = fechaInicial;
            let fechaFinPrimerGrupo = new Date(fechaInicioPrimerGrupo);
            fechaFinPrimerGrupo.setDate(fechaInicioPrimerGrupo.getDate() + (diasPrimerGrupo));
            // Verificar que no se exceda la fecha final establecida para esa incapacidad
            if (fechaFinPrimerGrupo > fechaFinal) {
                fechaFinPrimerGrupo = new Date(fechaFinal);
            }
            generarIncidencia(trabajador_id, fechaInicioPrimerGrupo, fechaFinPrimerGrupo, idIncapacidadGrupo1);
            // VERIFICAR SI HAY MÁS DIAS QUE CORRESPONDAN AL SEGUNDO GRUPO
            if (duracionTotal > diasPrimerGrupo + 1) {
                //SEGUNDO GRUPO
                let fechaInicioSegundoGrupo = new Date(fechaFinPrimerGrupo);
                fechaInicioSegundoGrupo.setDate(fechaFinPrimerGrupo.getDate() + 1);
                let fechaFinSegundoGrupo = new Date(fechaFinal);
                //********************VERIFICAR SI EXISTEN INCAPACIDADES INICIALES DE MAS DE 200 DÍAS
                //Generar incidencia para segundo grupo
                generarIncidencia(trabajador_id, fechaInicioSegundoGrupo, fechaFinSegundoGrupo, idIncapacidadGrupo2);
            }
        } else {
            // Si la duración total es menor que 1 día
            toastr["warning"]("Duración de la incapacidad no válida ", {progressBar: true, closeButton: true});
        }
    }
};

//Se verifica en esta función si existe una incapacidad inmediata siguiente para darle el tratamiento inicial al conjunto de incapacidades (Inicial + Continua)
const verificarContinuidadInicial = (fechaInicioIncapacidadContinua) => {
    return new Promise((resolve, reject) => {
        let expediente = $('#lblExpediente').text();
        // Obtiene los componentes de fecha
        let year = fechaInicioIncapacidadContinua.getFullYear();
        let month = fechaInicioIncapacidadContinua.getMonth() + 1;
        let day = fechaInicioIncapacidadContinua.getDate();
        // Formatea la fecha en el formato correcto
        let formattedFecha = `${year}-${month < 10 ? '0' : ''}${month}-${day < 10 ? '0' : ''}${day}`;

        $.ajax({
            type: "GET",
            url: "incidencias/buscarIncapacidadesContinuas/" + expediente + "/" + formattedFecha,
            dataType: 'json',
            success: function (data) {
                if (data.data === null) {
                    resolve(null); // Resuelve la promesa con null si data es nulo.
                } else {
                    resolve(data.data); // Resuelve la promesa con data si no es nulo.
                }
            },
            error: function (e) {
                reject(e);
            }
        });
    });
};
//Se verifica en esta función la fecha final de la incapacidad inicial con la finalidad de cancelarla si hay una continua relacionada a ella
const verificarFechaFinalInicial = (fechaFinIncapacidadInicial) => {
    return new Promise((resolve, reject) => {
        let expediente = $('#lblExpediente').text();
        // Obtiene los componentes de fecha
        let year = fechaFinIncapacidadInicial.getFullYear();
        let month = fechaFinIncapacidadInicial.getMonth() + 1;
        let day = fechaFinIncapacidadInicial.getDate();
        // Formatea la fecha en el formato correcto
        let formattedFecha = `${year}-${month < 10 ? '0' : ''}${month}-${day < 10 ? '0' : ''}${day}`;
        $.ajax({
            type: "GET",
            url: "incidencias/buscarFechaFinIncapacidadInicial/" + expediente + "/" + formattedFecha,
            dataType: 'json',
            success: function (data) {
                if (data.data === null) {
                    resolve(null); // Resuelve la promesa con null si data es nulo.
                } else {
                    resolve(data.data); // Resuelve la promesa con data si no es nulo.
                }
            },
            error: function (e) {
                reject(e); // Rechaza la promesa con el error si la solicitud AJAX falla.
            }
        });
    });
};
//Esta función genera directamente las incidencias dada un rango de fechas, se generan las continuas aquí mismo verificando no exceder de los 200 días, en caso de excederse se maneja como corresponde
const generarIncidencia = (trabajador_id, fechaInicio, fechaFin, idIncidencia, flagContinua) => {
    //Se colocan las horas a la media noche para evitar problemas horarios (Zonas Horarias del Json Stringify)
    fechaInicio.setHours(0, 0, 0, 0);
    fechaFin.setHours(0, 0, 0, 0);
    //Para autorizar incapacidades continuas relacionadas
    var autorizar = 1;
    var datos = {"autorizar": autorizar};
    //Manejo de incapacidades continuas por Enfermedad General
    if (flagContinua === true) {
        let fechaInicioIncapacidadContinua = new Date(fechaInicio);
        let fechaFinIncapacidadContinua = new Date(fechaFin);
        const fechaFinalMasUnDia = new Date(fechaFin);
        fechaFinalMasUnDia.setDate(fechaFinalMasUnDia.getDate() + 1);
        //Se verifica si la incapacidad continua tiene elementos relacionados
        verificarContinuidadInicial(fechaFinalMasUnDia)
                .then((data) => {
                    //Si data es diferente de null es porque hay incapacidad continua relacionada (Fecha Final mas un día = Siguiente incapacidad continua inmediata)
                    if (data !== null) {
                        // Se muestra el modal de confirmación para fusionar las incapacidades
                        $('#modalAlertaFusionIncapacidades').modal('show');
                        // Maneja el clic en "Sí" dentro del modal
                        $('#modalAlertaFusionIncapacidades #formAvisoAlertaDeContinuidadIncapacidades button.btn-primary').off('click').on('click', function (e) {
                            e.stopImmediatePropagation();
                            fusionarIncapacidadContinua(data, fechaInicioIncapacidadContinua, idIncidencia, fechaInicio, fechaFin, trabajador_id, datos, fechaFinIncapacidadContinua);
                            $('#modalAlertaFusionIncapacidades').modal('hide');
                        });
                        // Maneja el clic en "No" dentro del modal
                        $('#modalAlertaFusionIncapacidades #cancelarFusion').off('click').on('click', function (e) {
                            e.stopImmediatePropagation();
                            noFusionarIncapacidadContinua(fechaInicioIncapacidadContinua, idIncidencia, fechaInicio, fechaFin, trabajador_id, datos, fechaFinIncapacidadContinua);
                            $('#modalAlertaFusionIncapacidades').modal('hide');
                        });
                        //Incapacidades Continuas que no tienen relación directa con otra y que no exceden los 200 días
                    } else {
                        noFusionarIncapacidadContinua(fechaInicioIncapacidadContinua, idIncidencia, fechaInicio, fechaFin, trabajador_id, datos, fechaFinIncapacidadContinua);
                    }
                })
                .catch((error) => {
                    toastr.error("Se ha producido un error: " + error.message, "Error");
                });
        //Si se manejó como continua se retorna para no interferir en la generación de incidencias de otro tipo
        return;
    }
    //Guardar incapacidades sin manejos especiales
    guardarIncidencias(fechaFin, fechaInicio, idIncidencia, trabajador_id);
};

const fusionarIncapacidadContinua = (data, fechaInicioIncapacidadContinua, idIncidencia, fechaInicio, fechaFin, trabajador_id, datos, fechaFinIncapacidadContinua) => {
    //Se asigna la fecha final de la siguiente incapacidad con la finalidad de realizar el conteo de manera correcta
    fechaFin = new Date(data.fecha_final);
    //Para autorizar la incapacidad relacionada
    let idIncapacidadContinua = (data.id_incapacidad);
    // ==============Caso 201 días en adelante================
    diffMilliseconds = fechaFin - fechaInicioIncapacidadContinua;
    conteoDiasTranscurridos = Math.floor(diffMilliseconds / (1000 * 60 * 60 * 24)) + 1;
    //Solo se genera el manejo si se exceden los 200 días las incapacidades continuas
    if (conteoDiasTranscurridos > 200) {
        manejoIncapacidadesContinuasMayores200Dias(fechaInicioIncapacidadContinua, idIncidencia, fechaInicio, fechaFin, trabajador_id);
        /*Se agina automáticamente la fecha_autorización cuando se llame a este servicio 
         * Se autoriza la incapacidad continua*/
        ajaxEstatusIncapacidad(idIncapacidadContinua, datos);
        //Si se manejaron las incapacidades con más de 200 días se retorna para evitar la ejecución de la función
        return;
    }
    //Guardado de incapacidades que no exceden los 200 días y que están relacionadas
    guardarIncidencias(fechaFin, fechaInicio, idIncidencia, trabajador_id);
    //Se autoriza la incapacidad relacionada
    ajaxEstatusIncapacidad(idIncapacidadContinua, datos);
};

const noFusionarIncapacidadContinua = (fechaInicioIncapacidadContinua, idIncidencia, fechaInicio, fechaFin, trabajador_id, fechaFinIncapacidadContinua) => {
    diffMilliseconds = fechaFinIncapacidadContinua - fechaInicioIncapacidadContinua;
    conteoDiasTranscurridos = Math.floor(diffMilliseconds / (1000 * 60 * 60 * 24)) + 1;
    if (conteoDiasTranscurridos > 200) {
        manejoIncapacidadesContinuasMayores200Dias(fechaInicioIncapacidadContinua, idIncidencia, fechaInicio, fechaFin, trabajador_id);
        //Si se manejaron las incapacidades con más de 200 días se retorna para evitar la ejecución de la función
        return;
    }
    //Se guardan sin el manejo de los doscientos días para las incapacidades que son continuas y se retorna
    guardarIncidencias(fechaFin, fechaInicio, idIncidencia, trabajador_id);
};

//Generamos el manejo de las incapacidades continuas que superan los 200 días
const manejoIncapacidadesContinuasMayores200Dias = (fechaInicioIncapacidadContinua, idIncidencia, fechaInicio, fechaFin, trabajador_id) => {
    // Se superaron los 200 días, por lo que ajustamos fechaFinSegundoGrupo a fechaInicioSegundoGrupo + 200 días
    fechaFinIncapacidadContinua = new Date(fechaInicioIncapacidadContinua);
    fechaFinIncapacidadContinua.setDate(fechaInicioIncapacidadContinua.getDate() + 199);
    diffMilliseconds = fechaFinIncapacidadContinua - fechaInicioIncapacidadContinua;
    conteoDiasTranscurridos = Math.floor(diffMilliseconds / (1000 * 60 * 60 * 24)) + 1;
    //Se crea el primer grupo que abarca los 200 días
    let datosGrupo1 = {
        num_dias: conteoDiasTranscurridos,
        cat_estado_incidencia: {id_estado_incidencia: 1},
        cat_incidencias: {id_incidencia: idIncidencia},
        fecha_inicio: fechaInicioIncapacidadContinua,
        fecha_fin: fechaFinIncapacidadContinua,
        folio: $('#lblFolio').text(),
        bis: 'N/A',
        observaciones: generarObservacion(idIncidencia, fechaInicio, fechaFin),
        trabajador_id: trabajador_id
    };

    let fechaInicioDia200 = new Date(fechaFinIncapacidadContinua);
    fechaInicioDia200.setDate(fechaFinIncapacidadContinua.getDate() + 1);
    let fechaFinDia200 = new Date(fechaFin);
    let idIncidenciaDia201 = 31; //Compensación por EG al 60
    diffMilliseconds = fechaFinDia200 - fechaInicioDia200;
    conteoDiasTranscurridos = Math.floor(diffMilliseconds / (1000 * 60 * 60 * 24)) + 1;
    //Se genera el segundo grupo que abarca del día 201 en adelante
    let datosGrupo2 = {
        num_dias: conteoDiasTranscurridos,
        cat_estado_incidencia: {id_estado_incidencia: 1},
        cat_incidencias: {id_incidencia: idIncidenciaDia201},
        fecha_inicio: fechaInicioDia200,
        fecha_fin: fechaFinDia200,
        folio: $('#lblFolio').text(),
        bis: 'N/A',
        observaciones: generarObservacion(idIncidenciaDia201, fechaInicio, fechaFin),
        trabajador_id: trabajador_id
    };
    //Lamadas AJAX para guardado de los grupos correspondientes, el primer grupo se usa para guardar las incidencias que abarcan los 200 días
    $.ajax({
        type: "POST",
        url: "incidencias/guardarIncidencia",
        data: JSON.stringify(datosGrupo1),
        contentType: "application/json",
        success: function (data) {
            toastr['success'](data.data, "Incidencia Generada correctamente");
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
    //En este segundo grupo se guardan las incidencias que abarcan del día 201 en adelante
    $.ajax({
        type: "POST",
        url: "incidencias/guardarIncidencia",
        data: JSON.stringify(datosGrupo2),
        contentType: "application/json",
        success: function (data) {
            toastr['success'](data.data, "Incidencia Generada correctamente");
            //Se autoriza la incapacidad en su propia tabla
            let id_incapacidad = $('#id_incapacidad').val();
            var autorizar = 1;
            var datos = {"autorizar": autorizar};

            //Se agina automáticamente la fecha_autorización cuando se llame a este servicio
            ajaxEstatusIncapacidad(id_incapacidad, datos);
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};

//Se obtienen los datos y se guardan las fechas de las incidencias correspondientes con la incidencia y el identificador del trabajador, aquí mismo se autoriza la incapacidad que se envía
const guardarIncidencias = (fechaFin, fechaInicio, idIncidencia, trabajador_id) => {
    let folioIncapacidad = $('#lblFolio').text();
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
            toastr['success'](data.data, "Incidencia Generada correctamente");
            //Se autoriza la incapacidad en su propia tabla
            let id_incapacidad = $('#id_incapacidad').val();
            var autorizar = 1;
            var datos = {"autorizar": autorizar};
            //Se agina automáticamente la fecha_autorización cuando se llame a este servicio
            ajaxEstatusIncapacidad(id_incapacidad, datos);
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};

//Se da el formato a la observacion con la inicial de la Incapacidad  y los periodos que abarca
const generarObservacion = (idCatalogoincidencia, fechaInicio, fechaFin) => {
    let idNomina = $('#id_nomina').val();
    let result;

    let fechaInicialFormateada = convertirFechaAFormatoRequerido(fechaInicio);
    let fechaFinalFormateada = convertirFechaAFormatoRequerido(fechaFin);

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
                            const periodos = periodosData.data; // Ajusta la ruta del dato según tu JSON de respuesta
                            result = descripcionInicial + " " + periodos.join(', '); // Combina la descripción y los periodos
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
};

function convertirFechaAFormatoRequerido(fecha) {
    const date = new Date(fecha);
    const año = date.getFullYear();
    const mes = String(date.getMonth() + 1).padStart(2, "0");
    const día = String(date.getDate()).padStart(2, "0");
    const fechaFormateada = `${año}-${mes}-${día}`;

    return fechaFormateada;
}

//Limpieza de modal al cerrarlo
$('#incapacidadIMSS').on('hidden.bs.modal', function () {
    $("#formIncapacidades")[0].reset();
    $("#btnGeneraIncidenciaIncapacidad").prop("disabled", true);
    //Se elimina el mensaje de bootstrap que hace referencia a las incapacidades iniciales + continuas
    toastr.clear();
});
//Se listan los motivos de las incapacidades
const cmbMotivoIncapacidad = (id) => {
    $.ajax({
        type: "GET",
        url: "catalogos/listarMotivosIncapacidad",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbMotivoIncapacidad').empty().append('<option value="">*Sin motivo seleccionado</option>');
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
};
//Se listan los tipos de incapacidades
const cmbTipoIncapacidad = (id) => {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTiposIncapacidad",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbTipoIncapacidad').empty().append('<option value="">*Sin Tipo seleccionado</option>');
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        var vselected = (data[x].id_tipo_incapacidad === id) ? "selected" : "";
                        $('#cmbTipoIncapacidad').append('<option value="' + data[x].id_tipo_incapacidad + '"' + vselected + '>' + data[x].descripcion_tipo_incapacidad + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};
//Se listan los tipos de riesgo de las incapacidades
const cmbTipoRiesgo = (id) => {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoRiesgoIncapacidad",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbTipoRiesgo').empty().append('<option value="">*Sin Tipo seleccionado</option>');
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        var vselected = (data[x].cve_original === id) ? "selected" : "";
                        $('#cmbTipoRiesgo').append('<option value="' + data[x].id_tipo_riesgo + '"' + vselected + '>' + data[x].descripcion_riesgo + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};
//Se listan las posibles secuelas de la incapacidad
const cmbSecuelas = (id) => {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoSecuelasIncapacidad",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbSecuelas').empty().append('<option value="">*Sin Secuelas seleccionadas</option>');
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        var vselected = (data[x].cve_original === id) ? "selected" : "";
                        $('#cmbSecuelas').append('<option value="' + data[x].id_tipo_secuela + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};
//Se listan los controles de la incapacidad
const cmbControlIncapacidad = (id) => {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoControlIncapacidad",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbControlIncapacidad').empty().append('<option value="">*Sin control seleccionado</option>');
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        var vselected = (data[x].cve_original === id) ? "selected" : "";
                        $('#cmbControlIncapacidad').append('<option value="' + data[x].id_tipo_control + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};

//Home Incidencias
function adminVacaciones() {
    window.location.href = 'incidenciasAdmin';
}
//Regresa al usuario a las incidencias
const redireccionTabIncidencias = () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    window.location.href = 'trabajadorIncidencias?id_trabajador=' + trabajador_id;
    ;
};
//Carga los campos del trabajador que son mostrados en la página
const rellenaCampos = (trabajador_id) => {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + trabajador_id,
        dataType: 'json',
        success: function (data) {
            $('#campo_expediente').val(data.data.cat_expediente.numero_expediente);
            $('#campo_nombre').val(data.data.persona.nombre + " " + data.data.persona.apellido_paterno + " " + data.data.persona.apellido_materno);
            $('#campo_nss').val(data.data.persona.num_imss);
            //Se indica si el trabajador tiene prestaciones o no
            $('#campo_prestaciones_si_no').val(data.data.prestaciones_si_no === 1 ? 'Sí' : 'No');
            //Se busca el nombre de la nómina a la que pertenece el trabajador
            $.ajax({
                type: "GET",
                url: "trabajador/buscarNombreNominaPorExpediente/" + data.data.cat_expediente.numero_expediente,
                dataType: 'text',
                success: function (nombreNom) {
                    $('#campo_nomina').val(nombreNom);
                    // Solo se muestran las pretaciones para el personal de confianza
                    if (!nombreNom.includes("Confianza")) {
                        //Remueve la columna para no dejar el espacio en blanco
                        $('#campo_prestaciones_si_no').closest('.col').remove();
                        //Remueve el label y su input
                        $('#campo_prestaciones_si_no').prev('label').remove();
                        $('#campo_prestaciones_si_no').remove();
                    }
                },
                error: function (e) {
                    console.error(e);
                }
            });
        },
        error: function (e) {
            console.error(e);
        }
    });
};
//Se obtiene si el trabajador tiene prestaciones o no
const prestaciones = (id_trabajador) => {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            $('#prestaciones').val(data.data.prestaciones_si_no);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar las prestaciones");
        }
    });
};
//Se obtiene la nómina a la que pertenece el trabajador
function buscarNomina(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPlazaTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            $('#id_nomina').val(data.data.cat_Tipo_Nomina.id_tipo_nomina);
        },
        error: function (e) {
            alert(e);
        }
    });
}
//Carga la tabla de las incapacidades acorde a los criterios de búsqueda
function trabajadorIncapacidades(fechaInicial, fechaFinal, folio) {
    //Espera para obtener el campo del expediente ya cargado
    setTimeout(function () {
        let url;
        let expedienteTrabajador = $('#campo_expediente').val();
        if ((fechaInicial !== null && fechaFinal !== null) && (fechaInicial !== '' && fechaFinal !== '')) {
            url = 'incidencias/encontrarPeriodosLiberacion/' + expedienteTrabajador + '/' + fechaInicial + '/' + fechaFinal;
        } else {
            url = 'incidencias/buscarIncapacidadesFolio/' + folio + '/' + expedienteTrabajador;
        }
        // Obtener la referencia a la tabla existente, si existe
        const tablaIncapacidades = $('#tabla_incapacidades').DataTable();
        // Destruir la tabla existente si está inicializada
        if ($.fn.DataTable.isDataTable('#tabla_incapacidades')) {
            tablaIncapacidades.destroy();
        }
        const expedienteTrabajadorSafe = encodeURIComponent(expedienteTrabajador);
        //Para pruebas rápidas
//        const fechaInicio = '2013-01-01';
//        const fechaFin = '2013-01-30';
//        const url = `incidencias/encontrarPeriodosLiberacion/${expedienteTrabajadorSafe}/${fechaInicio}/${fechaFin}`;
        tabla_incapacidades = $('#tabla_incapacidades').DataTable({
            "pageLength": 100,
            "ajax": {
                url: url,
                method: 'GET',
                dataSrc: ''
            },
            responsive: true,
            bProcessing: true,
            select: true,
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
                {
                    "data": "fecha_movimiento",
                    "render": function (data, type, full, meta) {
                        if (type === 'display' || type === 'filter') {
                            // Formatear la fecha para mostrarla en "dd/mm/yyyy"
                            var date = new Date(data);
                            var day = date.getDate();
                            var month = date.getMonth() + 1;
                            var year = date.getFullYear();
                            return (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + year;
                        }
                        return data;
                    }
                },
                {
                    "data": "fecha_inicial",
                    "render": function (data, type, full, meta) {
                        if (type === 'display' || type === 'filter') {
                            // Formatear la fecha para mostrarla en "dd/mm/yyyy"
                            var date = new Date(data);
                            var day = date.getDate();
                            var month = date.getMonth() + 1;
                            var year = date.getFullYear();
                            return (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + year;
                        }
                        return data;
                    }
                },
                {
                    "data": "fecha_final",
                    "render": function (data, type, full, meta) {
                        if (type === 'display' || type === 'filter') {
                            // Formatear la fecha para mostrarla en "dd/mm/yyyy"
                            var date = new Date(data);
                            var day = date.getDate();
                            var month = date.getMonth() + 1;
                            var year = date.getFullYear();
                            return (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + year;
                        }
                        return data;
                    }
                },
                {data: "folio"},
                {data: "cat_Motivo_Incapacidad.descripcion_incapacidad"},
                {data: "cat_Tipo_Incapacidad.descripcion_tipo_incapacidad"},
                {data: "",
                    sTitle: "AUTORIZADO/CANCELADO",
                    width: 150,
                    visible: true,
                    render: function (d, t, r) {
                        var he;
                        // Se reciben en cero para incapacidades canceladas o nulo para incapacidades recién recibidas de Servicio Médico
                        if (r.autorizar === 0 || r.autorizar === null) {
                            he = '<button type="button" id="btndistrict' + r.id_incapacidad + '" class="btn btn-outline-success" onclick="estatusIncapacidad(' + r.id_incapacidad + ');"> ' + '<span class="fa fa-minus-circle"></span> Autorizar</button>';
                        } else {
                            if (r.pagada !== 1) {
                                he = '<button type="button" id="btndistrict' + r.id_incapacidad + '" class="btn btn-outline-danger" onclick="estatusIncapacidad(' + r.id_incapacidad + ')"> ' + '<span class="fa fa-minus-circle"></span> Rechazar</button>';
                            } else {
                                he = '<button type="button" id="btndistrict' + r.id_incapacidad + '" class="btn btn-outline-danger" disabled> ' + '<span class="fa fa-minus-circle"></span> Rechazar</button>';
                            }
                        }

                        return he;
                    }
                }

            ]
        });
    }, 250);
}

const estatusIncapacidad = (id_incapacidad, status) => {
    $("#incapacidadIMSS").modal("toggle");
    rellenarModal(id_incapacidad);
};
//Desactiva el estatus de la incidencia mediante la búsqueda de su folio
const bajarIncapacidad = (folioRecibido) => {
    let folio = $('#lblFolio').text();
    let idIncidencias = [];
    let estatus = 0;
    if (folioRecibido !== undefined && folioRecibido !== '') {
        folio = folioRecibido;
    }
    //Se buscan las incidencias generadas como incapacidades mediante el folio para dar de baja la INCIDENCIA, se rechazan las INCIDENCIAS con el folio correspondiente
    $.ajax({
        type: "GET",
        url: "incidencias/buscarFolioIncidencia/" + folio,
        dataType: 'json',
        success: function (data) {
            //Se obtiene los id correspondientes a las incapacidades generadas como incidencias
            for (let i = 0; i < data.length; i++) {
                idIncidencias.push(data[i].id_incidencia);
            }
            if (idIncidencias.length === 0) {
                toastr['warning']("Esta Incapacidad es Continua de una Incapacidad Inicial", "Aviso");
                //Se regresa el estatus de la incapacidad a no autorizada
                let id_incapacidad = $('#id_incapacidad').val();
                var autorizar = 0;
                var datos = {"autorizar": autorizar};
                //Cambio de estatus autorizar
                rechazarIncapacidad(id_incapacidad, datos);
            } else {
                //Se da de baja cada incidencia generada que está reñacionada con la incapacidad seleccionada
                toastr.options = {
                    "preventDuplicates": true,
                    "preventOpenDuplicates": true
                };
                //Se cambia el estatus de las incidencias relacionadas
                var totalIncidencias = idIncidencias.length;
                var completadas = 0;
                idIncidencias.forEach(function (id_incidencia) {
                    $.ajax({
                        type: "POST",
                        url: "incidencias/estatusIncidencia/" + id_incidencia + "/" + estatus,
                        contentType: "application/json",
                        success: function (data) {
                            toastr['success']("Las incidencias relacionadas fueron eliminadas", "Aviso");
                            //Se regresa el estatos de la incapacidad a no autorizada
                            completadas++;
                            if (completadas === totalIncidencias) {
                                // Termina solicitud AJAX
                                let id_incapacidad = $('#id_incapacidad').val();
                                var autorizar = 0;
                                var datos = {"autorizar": autorizar};
                                // Llama a rechazarIncapacidad después de que se completen todas las solicitudes
                                rechazarIncapacidad(id_incapacidad, datos);
                                //Se limpia el valor de la incapacidad después de completar el rechazo
                                id_incapacidad = null;
                            }
                        },
                        error: function (e) {
                            toastr['error']("No se actualizó la Autorización " + e, "Aviso");
                        }
                    });
                });
            }
        },
        error: function (e) {
            alert(e);
        }
    });
};

//Cambio de estatus autorizado en la incapacidad
const rechazarIncapacidad = (id_incapacidad, datos) => {
    let fechaInicialStr = $('#fechaInicialIncapacidadHidden').val();
    let fechaFinalStr = $('#fechaFinalIncapacidadHidden').val();
    //Comprobación de valores en los campos de las fechas
    if (fechaInicialStr && fechaFinalStr) {
        //Transformación a objeto date de js
        let [year, month, day] = fechaInicialStr.split('T')[0].split('-');
        let [hour, minute, second] = fechaInicialStr.split('T')[1].split('.')[0].split(':');
        //==============OBJETO DATE FECHA INICIAL========================
        let fechaInicial = new Date(year, month - 1, day, hour, minute, second);

        [year, month, day] = fechaFinalStr.split('T')[0].split('-');
        [hour, minute, second] = fechaFinalStr.split('T')[1].split('.')[0].split(':');
        //==============OBJETO DATE FECHA FINAL============================
        let fechaFinal = new Date(year, month - 1, day, hour, minute, second);

        //Se la suma un dia a la fecha final de la inicial
        let fechaFinalMasUnDia = new Date(fechaFinal);
        fechaFinalMasUnDia.setDate(fechaFinalMasUnDia.getDate() + 1);
        //Se le resta un dia a la fecha Inicial de la continua para encontrar su inicial relacionada
        let fechaInicialMenosUnDia = new Date(fechaInicial);
        fechaInicialMenosUnDia.setDate(fechaInicialMenosUnDia.getDate() - 1);

        //Se verifica si una incapacidad inicial tiene una continua para poder darla de baja de manera conjunta
        verificarContinuidadInicial(fechaFinalMasUnDia)
                .then((data) => {
                    if (data !== null && data.autorizar === 1) {
                        // Se muestra el modal de confirmación para rechazar las incapacidades
                        $('#modalAlertaRechazarIncapacidadesConjuntamente').modal('show');
                        // Maneja el clic en "Sí" dentro del modal
                        $('#modalAlertaRechazarIncapacidadesConjuntamente #formAvisoRechazoIncapacidadesFusionadas button.btn-primary').off('click').on('click', function (e) {
                            e.stopImmediatePropagation();
                            rechazarConjuntamente(data, datos);
                            $('#modalAlertaRechazarIncapacidadesConjuntamente').modal('hide');
                        });
                        // Maneja el clic en "No" dentro del modal
                        $('#modalAlertaRechazarIncapacidadesConjuntamente #cancelarRechazoFusion').off('click').on('click', function (e) {
                            e.stopImmediatePropagation();
                            ajaxEstatusIncapacidad(id_incapacidad, datos);
                            $('#modalAlertaRechazarIncapacidadesConjuntamente').modal('hide');
                        });
                    }
                })
                .catch((error) => {
                    console.error("Error al verificar continuidad inicial:", error);
                });
        //Verifica si una incapacidad continua tiene una inicial para poder darla de baja de manera conjunta
        verificarFechaFinalInicial(fechaInicialMenosUnDia)
                .then((data) => {
                    if (data !== null && data.autorizar === 1) {
                        // Se muestra el modal de confirmación para rechazar las incapacidades
                        $('#modalAlertaRechazarIncapacidadesConjuntamente').modal('show');
                        // Maneja el clic en "Sí" dentro del modal
                        $('#modalAlertaRechazarIncapacidadesConjuntamente #formAvisoRechazoIncapacidadesFusionadas button.btn-primary').off('click').on('click', function (e) {
                            e.stopImmediatePropagation();
                            rechazarConjuntamenteContinuaeInicial(data, datos);
                            $('#modalAlertaRechazarIncapacidadesConjuntamente').modal('hide');
                        });
                        // Maneja el clic en "No" dentro del modal
                        $('#modalAlertaRechazarIncapacidadesConjuntamente #cancelarRechazoFusion').off('click').on('click', function (e) {
                            e.stopImmediatePropagation();
                            ajaxEstatusIncapacidad(id_incapacidad, datos);
                            $('#modalAlertaRechazarIncapacidadesConjuntamente').modal('hide');
                        });
                    }
                })
                .catch((error) => {
                    console.error("Error al verificar continuidad inicial:", error);
                });
        //Si no entra en las verificaciones anteriores se ejecuta normalmente
        ajaxEstatusIncapacidad(id_incapacidad, datos);
    } else {
        //console.error("Las cadenas de fecha están vacías o no son válidas.");
    }
};

const rechazarConjuntamente = (data, datos) => {
    let idIncapacidadContinua = data.id_incapacidad;
    //Se actualiza el estatus de autorizar a 0
    ajaxEstatusIncapacidad(idIncapacidadContinua, datos);
};

const rechazarConjuntamenteContinuaeInicial = (data, datos) => {
    let idIncapacidadInicial = data.id_incapacidad;
    let folioIncapacidadInicial = data.folio;
    //Se actualiza el estatus en su tabla
    ajaxEstatusIncapacidad(idIncapacidadInicial, datos);
    //Borra las incidencias relacionadas en la tabla de Incidencias haciendo referencia al folio inicial que es con el que se registran
    bajarIncapacidad(folioIncapacidadInicial);
};

//Se marca como autorizada/ no autorizada en la tabla
const ajaxEstatusIncapacidad = (id_incapacidad, datos) => {
    //Se agina automáticamente la fecha_autorización cuando se llame a este servicio
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
            toastr['error']("No se actualizó la Autorización", "Aviso");
        }
    });
};
//Se obtienen los datos de la incapacidad para mostrarlos
const rellenarModal = (id_incapacidad) => {
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
            //Se toma la fecha de liberacion para comprobar que esta no sea mayor a 72 horas a partir de la fecha inicial de la incapacidad, de lo contrario entrará a caso especial
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

            //Se manda el aviso de que la incapacidad inicial tiene una continuación

            //Transformación a objeto date de js
            const fechaInicialStr = $('#fechaInicialIncapacidadHidden').val();
            let [year, month, day] = fechaInicialStr.split('T')[0].split('-');
            let [hour, minute, second] = fechaInicialStr.split('T')[1].split('.')[0].split(':');
            //==============OBJETO DATE FECHA INICIAL========================
            const fechaInicial = new Date(year, month - 1, day, hour, minute, second);

            const fechaFinalStr = $('#fechaFinalIncapacidadHidden').val();
            [year, month, day] = fechaFinalStr.split('T')[0].split('-');
            [hour, minute, second] = fechaFinalStr.split('T')[1].split('.')[0].split(':');
            //==============OBJETO DATE FECHA FINAL============================
            const fechaFinal = new Date(year, month - 1, day, hour, minute, second);

            const fechaLiberacionStr = $('#fecha_liberacion').val();
            [year, month, day] = fechaLiberacionStr.split('T')[0].split('-');
            [hour, minute, second] = fechaLiberacionStr.split('T')[1].split('.')[0].split(':');
            //==============OBJETO DATE FECHA LIBERACION===========================
            const fechaLiberacion = new Date(year, month - 1, day, hour, minute, second);

            //Se la suma un dia a la fecha final de la inicial
            let fechaFinalMasUnDia = new Date(fechaFinal);
            fechaFinalMasUnDia.setDate(fechaFinalMasUnDia.getDate() + 1);
            //Se le resta un dia a la fecha Inicial de la continua para encontrar su inicial relacionada
            let fechaInicialMenosUnDia = new Date(fechaInicial);
            fechaInicialMenosUnDia.setDate(fechaInicialMenosUnDia.getDate() - 1);
            //Se considera la fecha máxima extemporánea para darle un manejo especial y no mostrar las continuaciones aunque existan
            let fechaMaximaExtemporanea = new Date(fechaInicial);
            fechaMaximaExtemporanea.setDate(fechaMaximaExtemporanea.getDate() + 3);

            // Crear copias de las fechas originales y establecer la hora en ambas a las 00:00:00
            let fechaLiberacionSinHora = new Date(fechaLiberacion);
            fechaLiberacionSinHora.setHours(0, 0, 0, 0);
            //Se ponen las horas a la media noche para evitar problemas de comparación
            let fechaMaximaExtemporaneaSinHora = new Date(fechaMaximaExtemporanea);
            fechaMaximaExtemporaneaSinHora.setHours(0, 0, 0, 0);
            if (fechaLiberacionSinHora <= fechaMaximaExtemporaneaSinHora) {
                //Se verifica si la incapacidad inicial tiene una incapacidad continua por EG al día siguiente de la fecha de fin de la inicial, si la incapacidad es continua ya no se verifica
                verificarContinuidadInicial(fechaFinalMasUnDia)
                        .then((data) => {
                            if (data !== null) {
                                // Formatear las fechas en el formato dd/mm/yy con números
                                const fechaInicial = new Date(data.fecha_inicial);
                                const fechaFinal = new Date(data.fecha_final);
                                const fechaInicialFormateada = `${fechaInicial.getDate()}/${fechaInicial.getMonth() + 1}/${fechaInicial.getFullYear()}`;
                                const fechaFinalFormateada = `${fechaFinal.getDate()}/${fechaFinal.getMonth() + 1}/${fechaFinal.getFullYear()}`;
                                toastr.info(`Existe una incapacidad que puede estar relacionada.<br> Folio encontrado: <strong> ${data.folio} </strong> <br>
                 Fecha Inicial: ${fechaInicialFormateada} <br>
                 Fecha Final: ${fechaFinalFormateada}`,
                                        "Información", {
                                            timeOut: 20000,
                                            closeButton: true
                                        });
                            }
                        })
                        .catch((error) => {
                            console.error("Error al verificar continuidad inicial:", error);
                        });
                //Se verifica si la incapacidad continua tiene una inicial anterior para avisar al usuario, sin embargo se maneja como continua
                verificarFechaFinalInicial((fechaInicialMenosUnDia))
                        .then((data) => {
                            if (data !== null) {
                                // Formatear las fechas en el formato dd/mm/yy con números
                                const fechaInicial = new Date(data.fecha_inicial);
                                const fechaFinal = new Date(data.fecha_final);
                                const fechaInicialFormateada = `${fechaInicial.getDate()}/${fechaInicial.getMonth() + 1}/${fechaInicial.getFullYear()}`;
                                const fechaFinalFormateada = `${fechaFinal.getDate()}/${fechaFinal.getMonth() + 1}/${fechaFinal.getFullYear()}`;
                                toastr.info(`Existe una incapacidad que puede estar relacionada. Si deseas autorizar ambas incapacidades, por favor autoriza la incapacidad inicial relacionada.<br> Folio encontrado: <strong> ${data.folio} </strong> <br>
                 Fecha Inicial: ${fechaInicialFormateada} <br>
                 Fecha Final: ${fechaFinalFormateada}`,
                                        "Información", {
                                            timeOut: 10000,
                                            closeButton: true
                                        });
                            }
                        })
                        .catch((error) => {
                            console.error("Error al verificar continuidad inicial:", error);
                        });
            } else {
                toastr.warning('Incapacidad extemporánea, la fecha de liberación fue el ' + fechaLiberacionSinHora.toLocaleDateString() + ' y la fecha máxima permitida es ' + fechaMaximaExtemporaneaSinHora.toLocaleDateString() +
                        ', 3 días después de la fecha inicial de la incapacidad', 'Advertencia', {
                            timeOut: 10000,
                            closeButton: true
                        });
            }
        },
        error: function (e) {
            alert(e);
        }
    });
};
