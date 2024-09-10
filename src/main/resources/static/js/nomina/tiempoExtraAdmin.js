document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    let idTrabajador = urlParams.get('id_trabajador');
    rellenaCamposTrabajador(idTrabajador);
    rellenaHorarioTrabajador(idTrabajador);
    rellenarDiasDescansoTrabajador(idTrabajador);
    rellenarDatosPuestoTrabajador(idTrabajador);
    //Se coloca el timneout para poder obtener el campo del id de la nómina de manera correcta
    setTimeout(() => {
        obtenPeriodoAplicacion();
        obtenPeriodoGeneracion();
        buscarSemanasDeLaNomina();
    }, 1000);
    //Se coloca el timeout para obtener correctamente los periodos 
    setTimeout(() => {
        buscarRegistroExistente(idTrabajador);
        buscarCapturaRa10(idTrabajador);
    }, 1500);

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

});

const verTrabajadores = () => {
    window.location.href = 'tiempoExtraAdmin';
};

//Evento de escucha al select de la semana para actualizar los encabezados de la tabla
$("#cmbSemanasNomina").change(function () {
    definirDiasEncabezadoTabla();
});

const manejarEventos = (e) => {
    e.preventDefault();

    //Función que da el formnato de hora al input recibido
    const formatearHora = (valor, input) => {
        let limiteHorasPorDia;
        //Si el id comienza por paseosLaborados se limita a 8 horas
        if (input[0].id.startsWith("paseosLab")) {
            limiteHorasPorDia = 8;
            //Si no, se limita a 3 horas
        } else {
            limiteHorasPorDia = 3;
        }

        if (isNaN(valor)) {
            return '';
        }
        let horas = Math.floor(valor);
        let minutos = Math.round((valor % 1) * 100);
        const horasOriginales = horas;
        const minutosOriginales = minutos;
        horas = Math.min(horas, limiteHorasPorDia);
        minutos = Math.min(minutos, 59);
        // Si las horas se limitan a 3, forzar los minutos a 00
        if (horas === limiteHorasPorDia) {
            minutos = 0;
        }
        // Verifica si las horas o minutos han cambiado debido a la limitación
        if (horas !== horasOriginales || minutos !== minutosOriginales) {
            toastr["warning"]("Verificar datos ingresados, se realizó el ajuste al máximo diario permitido", "Advertencia", {progressBar: true, closeButton: true});
        }
        const horasFormateadas = horas.toString().padStart(2, '0');
        const minutosFormateados = minutos.toString().padStart(2, '0');
        return `${horasFormateadas}:${minutosFormateados}`;
    };

    //Función que aplica el formato y llama a la función de formateo para colocar el valor ya formateado en el input
    const aplicarFormato = (input) => {
        let valor = $(input).val();

        //Verificar si el input no viene vacío
        if (valor.trim() === '') {
            return;
        }
        if (/^\d+:\d{2}$/.test(valor)) {
            // Dividir el valor en horas y minutos
            const [horas, minutos] = valor.split(':').map(Number);

            // Verificar límites de horas y minutos
            if (horas < 16 || (horas === 16 && minutos <= 0)) {
                // Formatear la hora si el formato es correcto
                $(input).val(valor);
            } else {
                toastr["error"]("No se deben exceder las 16 horas", "Error", {progressBar: true, closeButton: true});
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
            $(input).val(formatearHora(valor, $(input)));
            calcularExtraDobleoTriple();
        }
    };

    aplicarFormato(e.target);
};

//Eventos de escucha al input de número de horas y paseos laborados ingresados por el usuario
$('#numeroHorasDia1, #numeroHorasDia2, #numeroHorasDia3, #numeroHorasDia4, #numeroHorasDia5, #numeroHorasDia6, #numeroHorasDia7').on('keypress change', function (e) {
    if (e.which === 13 || e.type === 'change') {
        manejarEventos(e);
        //Para borrar cálculos si se eliminó la hora del input
        calcularExtraDobleoTriple();
        //Para ajustar que no se excedan más de 9 horas el cálculo
        ajustarExtraDoble();
        //Calcular totales
        calcularyMostrarTotales();
    }
});

// Eventos de escucha para los inputs de paseos laborados
$('#paseosLabDia1, #paseosLabDia2, #paseosLabDia3, #paseosLabDia4, #paseosLabDia5, #paseosLabDia6, #paseosLabDia7').on('keypress change', function (e) {
    if (e.which === 13 || e.type === 'change') {
        manejarEventos(e);
        //Para borrar cálculos si se eliminó la hora del input
        calcularExtraDobleoTriple();
        //Para ajustar que no se excedan más de 9 horas el cálculo
        ajustarExtraDoble();
        //CalcularTotales
        calcularyMostrarTotales();
    }
});

const actualizarTotales = () => {
    //Para borrar cálculos si se eliminó la hora del input
    calcularExtraDobleoTriple();
    //Para ajustar que no se excedan más de 9 horas el cálculo
    ajustarExtraDoble();
    //Calcular totales
    calcularyMostrarTotales();
};

//Eventos de escucha a los combos para realizar la búsqueda de registros existentes
$("#periodoAplicacion, #periodoGeneracion, #cmbSemanasNomina").on("change", function () {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    let idTrabajador = urlParams.get('id_trabajador');
    limpiarTabla();
    buscarCapturaRa10(idTrabajador);
    buscarRegistroExistente(idTrabajador);
});

const ajustarExtraDoble = () => {
    // Obtener los valores de los campos
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
    const sumaTotal = sumarHoras(valores);

    // Verificar si la suma excede las 9:00 horas
    if (compararHoras(sumaTotal, "09:00")) {
        // Ajustar el último input para que la suma sea 9:00 horas
        for (let i = 6; i >= 0; i--) {
            const inputActual = $(`#extraDobleDia${i + 1}`);
            const valorActual = inputActual.val();
            if (valorActual !== "") {
                const ajuste = restarHoras(sumaTotal, "09:00");
                const valorFinal = restarHoras(valorActual, ajuste);
                if (valorFinal === "00:00") {
                    inputActual.val('');
                } else {
                    inputActual.val(valorFinal);
                }
                break;
            }
        }
    }
};

//Bandera de aplicación de horas extras triples
let flagAplicanHoraExtraTriple = false;
//Esta bandera sirve para identificar el día del recorrido del for para eliminarlo si el usuario excede las 9 horas y cancela la aplicación de horas extras triples
let flagDiaActual = false;
//Dia actual recorrido donde comienzan a aplicar horas triples
let numeroDiaActual;

const calcularExtraDobleoTriple = () => {
    let horasArray = [];
    let horasExtrasDoble = 0;
    let horasExtraTriple = 0;
    let nueveHorasEnMinutos = 540;

    const procesarDia = (i, horasDiaFormateado) => {

        if (horasDiaFormateado === '' || horasDiaFormateado === '00:00') {
            $(`#extraDobleDia${i}`).val('');
            $(`#extraTripleDia${i}`).val('');
            return;
        }

        horasArray.push(horasDiaFormateado);

        let resultadoSumaTotalHoras = sumarHoras(horasArray);
        //Si se superan 9 horas comienzan a aplicar las horas triples, se mandará a llamar en la i donde se superan las 9 horas
        if (compararHoras(resultadoSumaTotalHoras, "09:00")) {
            mostrarModalConfirmacionHorasTriples(i, resultadoSumaTotalHoras);
            //Si no se superan las 9 horas se mantienen dobles 
        } else {
            $(`#extraDobleDia${i}`).val(horasDiaFormateado);
        }
    };

    const mostrarModalConfirmacionHorasTriples = (i, resultadoSumaTotalHoras) => {
        $('#horasTriplesModal').modal('show');
        if (flagDiaActual === false) {
            $('#diaActualRecorrido').val(i);
        }
        //Si se confirma el modal se procede a calcular las horas triples
        $("#horasTriplesModal").off("submit").on("submit", function (event) {
            event.preventDefault();
            numDiaActual = $('#diaActualRecorrido').val();
            $(`#extraTripleDia${numDiaActual}`).val('');
            //Si ya comenzaron a aplicar horas extras la segunda vez 
            if (flagAplicanHoraExtraTriple === true) {
                let minutosInput = obtenerMinutos($(`#numeroHorasDia${numDiaActual}`).val());
                horasExtraTriple = minutosInput;
            } else {
                horasExtraTriple += obtenerMinutos(resultadoSumaTotalHoras) - nueveHorasEnMinutos;
            }
            horasExtrasDoble += obtenerMinutos($(`#numeroHorasDia${numDiaActual}`).val()) - horasExtraTriple;
            $(`#extraDobleDia${numDiaActual}`).val(horasExtrasDoble > 0 ? minutosAHoras(horasExtrasDoble) : '');
            $(`#extraTripleDia${numDiaActual}`).val(horasExtraTriple > 0 ? minutosAHoras(horasExtraTriple) : '');
            $('#horasTriplesModal').modal('hide');
            calcularyMostrarTotales();
            flagDiaActual = false;
            flagAplicanHoraExtraTriple = true;
            $('#diaActualRecorrido').val('');
        });
        //Si se cancela se borra el input del usuario
        $("#btn_Cancelar_Extras").on("click", function (event) {
            let diaActualAlExceder9Horas = $('#diaActualRecorrido').val();
            $(`#numeroHorasDia${diaActualAlExceder9Horas}`).val('');
            $(`#extraDobleDia${diaActualAlExceder9Horas}`).val('');
            $(`#extraTripleDia${diaActualAlExceder9Horas}`).val('');
            flagDiaActual = false;
            flagAplicanHoraExtraTriple = false;
        });
    };

    // Procesar días
    for (let i = 1; i <= 7; i++) {
        let horasDiaFormateado = $(`#numeroHorasDia${i}`).val() || '';
        procesarDia(i, horasDiaFormateado);
    }
    //Reset a la bandera para realizar los cálculos correctamente
    flagAplicanHoraExtraTriple = false;
};

const calcularyMostrarTotales = () => {
    let extraDobleArray = [];
    let extraTripleArray = [];
    let extraDoblePaseosArray = [];
    let extraDobleFestivosArray = [];
    for (let i = 1; i <= 7; i++) {
        //Si el color del encabezado es azul se trata entonces de un día festivo
        let colorEncabezado = $(`#dia${i}Encabezado`).css('color');
        if (colorEncabezado === 'rgb(0, 0, 255)') {
            let extraDoblePaseosEnDiaFestivo = ($(`#paseosLabDia${i}`).val()) || '';
            extraDobleFestivosArray.push(extraDoblePaseosEnDiaFestivo);
            //Si no es azul es un día normal
        } else {
            let extraDoblePaseos = ($(`#paseosLabDia${i}`).val()) || '';
            //Generando totales de horas extras paseos dobles
            extraDoblePaseosArray.push(extraDoblePaseos);
        }
        let extraDobleDias = ($(`#extraDobleDia${i}`).val()) || '';
        let extraTripleDias = ($(`#extraTripleDia${i}`).val()) || '';

        //Generando totales de horas extras dobles
        extraDobleArray.push(extraDobleDias);
        //Generando totales de horas extras triples
        extraTripleArray.push(extraTripleDias);
    }

    let totalTiempoExtraDoble = sumarHoras(extraDobleArray);
    let totalTiempoExtraTriple = sumarHoras(extraTripleArray);
    let totalTiempoExtraDoblePaseos = sumarHoras(extraDoblePaseosArray);
    let totalTiempoExtraDobleFestivos = sumarHoras(extraDobleFestivosArray);

    $('#totalTiempoExtraDoble').val(totalTiempoExtraDoble === '00:00' ? '' : totalTiempoExtraDoble);
    $('#totalTiempoExtraTriple').val(totalTiempoExtraTriple === '00:00' ? '' : totalTiempoExtraTriple);
    $('#totalTiempoExtraDescansosLaborados').val(totalTiempoExtraDoblePaseos === '00:00' ? '' : totalTiempoExtraDoblePaseos);
    $('#totalTiempoExtraDiaFestivo').val(totalTiempoExtraDobleFestivos === '00:00' ? '' : totalTiempoExtraDobleFestivos);


};

const guardarTiempoExtra = () => {
    //Para obtener la información actualizada acorde a los inputs que se muestran sin necesidad de interacción con el usuario
    actualizarTotales();
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    let idTrabajador = urlParams.get('id_trabajador');

    let periodoAplicacion = parseInt($('#periodoAplicacion').val(), 10);
    let periodoGeneracion = parseInt($('#periodoGeneracion').val(), 10);
    let semanaIncidencias = $("#cmbSemanasNomina option:selected").text();
    let idNomina = $('#id_nomina').val();

    let extraDobleArray = [];
    let extraTripleArray = [];
    let extraDoblePaseosArray = [];
    let tiempoExtraDesgloseArray = [];
    let tiempoExtraPaseosDesgloseArray = [];

    let totalTiempoExtraDoble = $("#totalTiempoExtraDoble").val();
    let totalTiempoExtraTriple = $("#totalTiempoExtraTriple").val();
    let totalTiempoExtraDescansosLaborados = $('#totalTiempoExtraDescansosLaborados').val();
    let totalTiempoExtraDiaFestivo = $('#totalTiempoExtraDiaFestivo').val();


    //Se no se cuenta con ningún registro de hora extra doble es porque el usuario no ha ingresado ningún dato
    if ($.trim(totalTiempoExtraDoble) === "") {
        return false;
    }
    //Ciclo que recorre los días y obtiene los datos a guardar
    for (let i = 1; i <= 7; i++) {
        let extraDobleDias = ($(`#extraDobleDia${i}`).val()) || '';
        let extraTripleDias = ($(`#extraTripleDia${i}`).val()) || '';
        let extraDoblePaseos = ($(`#paseosLabDia${i}`).val()) || '';
        let numHorasDias = ($(`#numeroHorasDia${i}`).val()) || '';
        let numHorasDiasPaseos = ($(`#paseosLabDia${i}`).val()) || '';
        //Generando totales de horas extras dobles
        extraDobleArray.push(extraDobleDias);
        //Generando totales de horas extras triples
        extraTripleArray.push(extraTripleDias);
        //Generando totales de horas extras paseos dobles
        extraDoblePaseosArray.push(extraDoblePaseos);
        //Generando el desglose de horas ingresadas por día
        tiempoExtraDesgloseArray.push(numHorasDias);
        //Generando el desglose de horas de paseos ingresadas por día
        tiempoExtraPaseosDesgloseArray.push(numHorasDiasPaseos);
    }

    var datos = {"trabajador_id": idTrabajador,
        "periodo_aplicacion_id": periodoAplicacion,
        "periodo_generacion_id": periodoGeneracion,
        "semana_incidencias": semanaIncidencias,
        "nomina_id": idNomina,
        //Desglose horas extras ingresadas
        "tiempo_extra_1": tiempoExtraDesgloseArray[0],
        "tiempo_extra_2": tiempoExtraDesgloseArray[1],
        "tiempo_extra_3": tiempoExtraDesgloseArray[2],
        "tiempo_extra_4": tiempoExtraDesgloseArray[3],
        "tiempo_extra_5": tiempoExtraDesgloseArray[4],
        "tiempo_extra_6": tiempoExtraDesgloseArray[5],
        "tiempo_extra_7": tiempoExtraDesgloseArray[6],
        //Desglose de paseos
        "paseos_lab_1": tiempoExtraPaseosDesgloseArray[0],
        "paseos_lab_2": tiempoExtraPaseosDesgloseArray[1],
        "paseos_lab_3": tiempoExtraPaseosDesgloseArray[2],
        "paseos_lab_4": tiempoExtraPaseosDesgloseArray[3],
        "paseos_lab_5": tiempoExtraPaseosDesgloseArray[4],
        "paseos_lab_6": tiempoExtraPaseosDesgloseArray[5],
        "paseos_lab_7": tiempoExtraPaseosDesgloseArray[6],
        //Desglose cálculo de horas dobles
        "te_doble_1": extraDobleArray[0],
        "te_doble_2": extraDobleArray[1],
        "te_doble_3": extraDobleArray[2],
        "te_doble_4": extraDobleArray[3],
        "te_doble_5": extraDobleArray[4],
        "te_doble_6": extraDobleArray[5],
        "te_doble_7": extraDobleArray[6],
        //Desglose de cálculo de horas triples
        "te_triple_1": extraTripleArray[0],
        "te_triple_2": extraTripleArray[1],
        "te_triple_3": extraTripleArray[2],
        "te_triple_4": extraTripleArray[3],
        "te_triple_5": extraTripleArray[4],
        "te_triple_6": extraTripleArray[5],
        "te_triple_7": extraTripleArray[6],
        //Guardado de cálculos totales
        "total_te_doble": isNaN(convertirTiempoDecimal(totalTiempoExtraDoble)) ? 0 : convertirTiempoDecimal(totalTiempoExtraDoble),
        "total_te_triple": isNaN(convertirTiempoDecimal(totalTiempoExtraTriple)) ? 0 : convertirTiempoDecimal(totalTiempoExtraTriple),
        "total_te_paseos_doble": isNaN(convertirTiempoDecimal(totalTiempoExtraDescansosLaborados)) ? 0 : convertirTiempoDecimal(totalTiempoExtraDescansosLaborados),
        "total_te_festivo_doble": isNaN(convertirTiempoDecimal(totalTiempoExtraDiaFestivo)) ? 0 : convertirTiempoDecimal(totalTiempoExtraDiaFestivo)
    };
    ajaxGuardarTExtra(datos, idTrabajador);
    //Ya que se hace el guardado se procede a calcular la clave 19 si exste un registro que corresponda al periodo de generación en la tabla de captura semnanal RA10
    setTimeout(() => {
        calcularCve19Ra10(idTrabajador,
                periodoGeneracion,
                convertirTiempoDecimal(totalTiempoExtraDoble),
                convertirTiempoDecimal(totalTiempoExtraDescansosLaborados),
                convertirTiempoDecimal(totalTiempoExtraDiaFestivo),
                convertirTiempoDecimal(datos.te_doble_5));
    }, 500);
};

const editarTiempoExtra = () => {
    //Para obtener la información actualizada acorde a los inputs que se muestran sin necesidad de interacción con el usuario
    actualizarTotales();
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    let idTrabajador = urlParams.get('id_trabajador');
    let periodoAplicacion = parseInt($('#periodoAplicacion').val(), 10);
    let periodoGeneracion = parseInt($('#periodoGeneracion').val(), 10);
    let semanaIncidencias = $("#cmbSemanasNomina option:selected").text();
    let idNomina = $('#id_nomina').val();

    let extraDobleArray = [];
    let extraTripleArray = [];
    let extraDoblePaseosArray = [];
    let tiempoExtraDesgloseArray = [];
    let tiempoExtraPaseosDesgloseArray = [];

    let totalTiempoExtraDoble = $("#totalTiempoExtraDoble").val();
    let totalTiempoExtraTriple = $("#totalTiempoExtraTriple").val();
    let totalTiempoExtraDescansosLaborados = $('#totalTiempoExtraDescansosLaborados').val();
    let totalTiempoExtraDiaFestivo = $('#totalTiempoExtraDiaFestivo').val();


    //Se no se cuenta con ningún registro de hora extra doble es porque el usuario no ha ingresado ningún dato
    if ($.trim(totalTiempoExtraDoble) === "") {
        toastr["warning"]("Se debe capturar al menos un dato ", "Advertencia", {progressBar: true, closeButton: true});
        return false;
    }
    //Ciclo que recorre los días y obtiene los datos a guardar
    for (let i = 1; i <= 7; i++) {
        let extraDobleDias = ($(`#extraDobleDia${i}`).val()) || '';
        let extraTripleDias = ($(`#extraTripleDia${i}`).val()) || '';
        let extraDoblePaseos = ($(`#paseosLabDia${i}`).val()) || '';
        let numHorasDias = ($(`#numeroHorasDia${i}`).val()) || '';
        let numHorasDiasPaseos = ($(`#paseosLabDia${i}`).val()) || '';
        //Generando totales de horas extras dobles
        extraDobleArray.push(extraDobleDias);
        //Generando totales de horas extras triples
        extraTripleArray.push(extraTripleDias);
        //Generando totales de horas extras paseos dobles
        extraDoblePaseosArray.push(extraDoblePaseos);
        //Generando el desglose de horas ingresadas por día
        tiempoExtraDesgloseArray.push(numHorasDias);
        //Generando el desglose de horas de paseos ingresadas por día
        tiempoExtraPaseosDesgloseArray.push(numHorasDiasPaseos);
    }

    var datos = {"trabajador_id": idTrabajador,
        "periodo_aplicacion_id": periodoAplicacion,
        "periodo_generacion_id": periodoGeneracion,
        "semana_incidencias": semanaIncidencias,
        "nomina_id": idNomina,
        //Desglose horas extras ingresadas
        "tiempo_extra_1": tiempoExtraDesgloseArray[0],
        "tiempo_extra_2": tiempoExtraDesgloseArray[1],
        "tiempo_extra_3": tiempoExtraDesgloseArray[2],
        "tiempo_extra_4": tiempoExtraDesgloseArray[3],
        "tiempo_extra_5": tiempoExtraDesgloseArray[4],
        "tiempo_extra_6": tiempoExtraDesgloseArray[5],
        "tiempo_extra_7": tiempoExtraDesgloseArray[6],
        //Desglose de paseos
        "paseos_lab_1": tiempoExtraPaseosDesgloseArray[0],
        "paseos_lab_2": tiempoExtraPaseosDesgloseArray[1],
        "paseos_lab_3": tiempoExtraPaseosDesgloseArray[2],
        "paseos_lab_4": tiempoExtraPaseosDesgloseArray[3],
        "paseos_lab_5": tiempoExtraPaseosDesgloseArray[4],
        "paseos_lab_6": tiempoExtraPaseosDesgloseArray[5],
        "paseos_lab_7": tiempoExtraPaseosDesgloseArray[6],
        //Desglose cálculo de horas dobles
        "te_doble_1": extraDobleArray[0],
        "te_doble_2": extraDobleArray[1],
        "te_doble_3": extraDobleArray[2],
        "te_doble_4": extraDobleArray[3],
        "te_doble_5": extraDobleArray[4],
        "te_doble_6": extraDobleArray[5],
        "te_doble_7": extraDobleArray[6],
        //Desglose de cálculo de horas triples
        "te_triple_1": extraTripleArray[0],
        "te_triple_2": extraTripleArray[1],
        "te_triple_3": extraTripleArray[2],
        "te_triple_4": extraTripleArray[3],
        "te_triple_5": extraTripleArray[4],
        "te_triple_6": extraTripleArray[5],
        "te_triple_7": extraTripleArray[6],
        //Guardado de cálculos totales
        "total_te_doble": isNaN(convertirTiempoDecimal(totalTiempoExtraDoble)) ? 0 : convertirTiempoDecimal(totalTiempoExtraDoble),
        "total_te_triple": isNaN(convertirTiempoDecimal(totalTiempoExtraTriple)) ? 0 : convertirTiempoDecimal(totalTiempoExtraTriple),
        "total_te_paseos_doble": isNaN(convertirTiempoDecimal(totalTiempoExtraDescansosLaborados)) ? 0 : convertirTiempoDecimal(totalTiempoExtraDescansosLaborados),
        "total_te_festivo_doble": isNaN(convertirTiempoDecimal(totalTiempoExtraDiaFestivo)) ? 0 : convertirTiempoDecimal(totalTiempoExtraDiaFestivo)
    };
    ajaxEditarCaptura(datos);
    //Si se hace la edición se procede a calcular la clave 19 si exste un registro que corresponda al periodo de generación en la tabla de captura semnanal RA10
    setTimeout(() => {
        calcularCve19Ra10(idTrabajador,
                periodoGeneracion,
                convertirTiempoDecimal(totalTiempoExtraDoble),
                convertirTiempoDecimal(totalTiempoExtraDescansosLaborados),
                convertirTiempoDecimal(totalTiempoExtraDiaFestivo),
                convertirTiempoDecimal(datos.te_doble_5));
    }, 500);

};

const ajaxGuardarTExtra = (datos, idTrabajador) => {
    $.ajax({
        type: "POST",
        url: "tiempoExtra/guardarTiempoExtra",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            toastr['success']("Se ha guardado correctamente el tiempo extra", "Aviso");
            mostrarOcultarBotonGuardado(true);
            mostrarOcultarBotonesActualizar(false);
            buscarRegistroExistente(idTrabajador);
        },
        error: function (e) {
            console.log(e);
            toastr["warning"]("Error al agregar tiempo extra: " + e, "Error", {progressBar: true, closeButton: true});
        }
    });
};

const ajaxEditarCaptura = (datos) => {
    let idTiempoExtra = $('#idTiempoExtraEncontrado').val();
    $.ajax({
        type: "POST",
        url: "tiempoExtra/editarTiempoExtra/" + idTiempoExtra,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            toastr['success']("Se ha editado correctamente el tiempo extra", "Aviso");
            buscarRegistroExistente(datos.trabajador_id);
        },
        error: function (e) {
            toastr["warning"]("Error al editar tiempo extra: " + e, "Error", {progressBar: true, closeButton: true});
        }
    });
};

const calcularCve19Ra10 = (idTrabajador, periodoGeneracion, totalTeDoble, totalTePaseosDoble, totalFestivoDoble, totalTrabajadodomingo) => {
    //Se busca el id del registro existente para hacer la actualización de la clave
    buscarRegistroExistente(idTrabajador);
    //Función para la actualización de la diferencia de tiempo extra
    const actualizarCve19 = (diferenciaCve19, idTiempoExtra) => {
        $.ajax({
            type: "POST",
            url: "tiempoExtra/updateCve19/" + diferenciaCve19 + "/" + idTiempoExtra,
            contentType: "application/json",
            success: function (data) {
                toastr['success'](data.data, "Aviso");
            },
            error: function (e) {
                toastr["warning"]("Error al editar cve19: " + e, "Error", {progressBar: true, closeButton: true});
            }
        });
    };
    //Función de cálculo de la clave 19 en el backend
    $.ajax({
        type: "GET",
        url: "ra10/calcularCve19TiempoExtra/" + idTrabajador + "/" + periodoGeneracion + "/" + totalTeDoble + "/"
                + totalTePaseosDoble + "/" + totalFestivoDoble + "/" + totalTrabajadodomingo,
        dataType: 'json'
    })
            .done(function (data) {
                let idTiempoExtra = $('#idTiempoExtraEncontrado').val();
                $("#advertenciaRA10").modal("toggle");
                colocarInformacionCalculoModalRA10(data.sueldo_hora_suplencia, data.sueldo_hora_actual,
                        data.total_horas_dobles, data.diferencia_prima_dominical, data.diferencia_pagar_tiempo_extra);

                $("#advertenciaRA10").off('submit').submit(function (event) {
                    event.preventDefault();
                    $("#advertenciaRA10").modal("hide");
                    actualizarCve19(data.diferencia_pagar_tiempo_extra, idTiempoExtra);
                    setTimeout(() => {
                        buscarRegistroExistente(idTrabajador);
                    }, 500);
                });
                //Maneja la cancelación de cálculo del usuario
                $("#btn_Cancelar_Calculo_RA10").off('click').click(function () {
                    $("#advertenciaRA10").modal("hide");
                    actualizarCve19(0, idTiempoExtra);
                    setTimeout(() => {
                        buscarRegistroExistente(idTrabajador);
                    }, 500);
                });
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                //El estado not found, ya que no se encontró un registro en el periodo de generación y trabajador en RA10
                if (jqXHR.status === 404) {
                } else {
                    toastr['error']("Ocurrió un error al calcular la clave 19", "Error");
                }
            });
};

const colocarInformacionCalculoModalRA10 = (sueldoHoraSuplencia, sueldoHoraActual, totalSemana, diferenciaPrimasDominicales, diferenciaPagarHoras) => {
    $('#sueldoHoraSupl').val(!isNaN(sueldoHoraSuplencia) ? Number(sueldoHoraSuplencia).toFixed(2) : '');
    $('#sueldoHora').val(!isNaN(sueldoHoraActual) ? Number(sueldoHoraActual).toFixed(2) : '');
    $('#totalHorasDobles').val(!isNaN(totalSemana) ? Number(totalSemana).toFixed(2) : '');
    $('#diferenciaPrima').val(!isNaN(diferenciaPrimasDominicales) ? Number(diferenciaPrimasDominicales).toFixed(2) : '');
    $('#diferenciaRA10').val(!isNaN(diferenciaPagarHoras) ? Number(diferenciaPagarHoras).toFixed(2) : '');
};

const convertirTiempoDecimal = (cadenaTiempo) => {
    if (cadenaTiempo === '') {
        return 0;
    }
    // Dividir la cadena en horas y minutos
    const [horas, minutos] = cadenaTiempo.split(':');

    // Convertir las horas y minutos a números y calcular el tiempo decimal
    const tiempoDecimal = parseInt(horas, 10) + parseInt(minutos, 10) / 60;

    // Retornar el tiempo decimal con un solo decimal
    return tiempoDecimal;
};

const mostrarOcultarBotonGuardado = (boolean) => {
    if (boolean === true) {
        $("#btnGuardar").attr("hidden", true);
    } else {
        $("#btnGuardar").attr("hidden", false);
    }
};

const mostrarOcultarBotonesActualizar = (boolean) => {
    if (boolean === true) {
        $("#btnEditar").attr("hidden", true);
        $("#elimina").attr("hidden", true);
    } else {
        $("#btnEditar").attr("hidden", false);
        $("#elimina").attr("hidden", false);
    }
};
const minutosAHoras = (minutos) => {
    let horas = Math.floor(minutos / 60);
    let minutosRestantes = minutos % 60;
    return `${horas.toString().padStart(2, '0')}:${minutosRestantes.toString().padStart(2, '0')}`;
};

const sumarHoras = (horasArray) => {
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

    // Formatear el resultado
    return `${horas.toString().padStart(2, '0')}:${minutos.toString().padStart(2, '0')}`;
};

// Función para restar horas en formato HH:mm
const restarHoras = (hora1, hora2) => {
    let minutos1 = obtenerMinutos(hora1);
    let minutos2 = obtenerMinutos(hora2);
    let restaMinutos = minutos1 - minutos2;

    let horas = Math.floor(restaMinutos / 60);
    let minutos = restaMinutos % 60;

    return `${horas.toString().padStart(2, '0')}:${minutos.toString().padStart(2, '0')}`;
};

const compararHoras = (hora1, hora2) => {
    let minutos1 = obtenerMinutos(hora1);
    let minutos2 = obtenerMinutos(hora2);
    return minutos1 > minutos2;
};

const obtenerMinutos = (hora) => {
    return parseInt(hora.split(":")[0]) * 60 + parseInt(hora.split(":")[1]);
};

//Carga los campos del trabajador que son mostrados en la página
const rellenaCamposTrabajador = (trabajador_id) => {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + trabajador_id,
        dataType: 'json',
        success: function (data) {
            $('#campo_expediente').val(data.data.cat_expediente.numero_expediente);
            $('#campo_nombre').val(data.data.persona.nombre + " " + data.data.persona.apellido_paterno + " " + data.data.persona.apellido_materno);
            rellenaNominaTrabajador(data.data.cat_expediente.numero_expediente);
        },
        error: function (e) {
            console.error(e);
        }
    });
};

const rellenaNominaTrabajador = (expediente) => {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarNombreNominaPorExpediente/" + expediente,
        dataType: 'text',
        success: function (nombreNom) {
            $('#campo_nomina').val(nombreNom);

        },
        error: function (e) {
            console.error(e);
        }
    });

    $.ajax({
        type: "GET",
        url: "trabajador/buscarNominaPorExpediente/" + expediente,
        dataType: 'text',
        success: function (idNomina) {
            $('#id_nomina').val(idNomina);
        },
        error: function (e) {
            console.error(e);
        }
    });
};

const rellenaHorarioTrabajador = (id_trabajador) => {
    $.ajax({
        type: "GET",
        url: "horarios/buscarHorario/" + id_trabajador,
        dataType: 'json',
        success: function (dataHorarios) {
            const encontrarHorariosNoVacios = (datos) => {
                var horariosNoVacios = [];
                for (var i = 0; i < datos.length; i++) {
                    var horarioEntrada = datos[i].horario_entrada;
                    var horarioSalida = datos[i].horario_salida;

                    if (horarioEntrada && horarioSalida && horarioEntrada !== "" && horarioSalida !== "") {
                        horariosNoVacios.push(horarioEntrada + " - " + horarioSalida);
                    }
                }
                return horariosNoVacios;
            };

            var horariosNoVacios = encontrarHorariosNoVacios(dataHorarios);

            if (horariosNoVacios.length > 0) {
                // Verificar si todos los horarios son iguales
                var todosIguales = horariosNoVacios.every(function (horario) {
                    return horario === horariosNoVacios[0];
                });
                //Se verifica si el trabajador tiene mismo horario toda la semana
                if (todosIguales) {
                    $("#campo_horario_trabajador").val(horariosNoVacios[0]);
                    //Si no lo tiene se indica que el horario es variable 
                } else {
                    $("#campo_horario_trabajador").val("Variable");
                }
            } else {
                $("#campo_horario_trabajador").val("Sin horario");
            }
        },
        error: function (e) {
            console.error(e);
        }
    });
};

const rellenarDiasDescansoTrabajador = (id_trabajador) => {
    $.ajax({
        type: "GET",
        url: "horarios/buscarDiasDescansoTrabajador/" + id_trabajador,
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
            }
        },
        error: function (e) {
            console.error(e);
        }
    });
};

const rellenarDatosPuestoTrabajador = (id_trabajador) => {
    $.ajax({
        type: "GET",
        url: "plaza/buscarPlazaTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (dataPuestoTrabajador) {
            $('#campo_sueldo_diario_trabajador').val(dataPuestoTrabajador[0].sueldoDiarioDto);
            $('#campo_area_trabajador').val(dataPuestoTrabajador[0].descAreaDto);
            $('#campo_puesto_trabajador').val(dataPuestoTrabajador[0].puestoDto);
        },
        error: function (e) {
            console.error(e);
        }
    });
};

const obtenPeriodoAplicacion = () => {
    var idNomina = $("#id_nomina").val();
    $.ajax({
        type: "GET",
        url: "trabajador/buscaPeriodosFechas/" + idNomina,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#periodoAplicacion').empty();
                var vselected = "";
                //var compara_periodo = comparaFechas(data, vselected);
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        var n_periodo = data[x].periodo;
                        var fecha_inicial_formato = new Date(data[x].fecha_inicial);
                        var dias_inicial = fecha_inicial_formato.getDate();
                        var meses_inicial = fecha_inicial_formato.getMonth() + 1;
                        var anios_inicial = fecha_inicial_formato.getFullYear();

                        var fecha_final_formato = new Date(data[x].fecha_final);
                        var dias_final = fecha_final_formato.getDate();
                        var meses_final = fecha_final_formato.getMonth() + 1;
                        var anios_final = fecha_final_formato.getFullYear();
                        //if (compara_periodo !== data[x].periodo) {
                        $('#periodoAplicacion').append('<option value="' + data[x].periodo + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
                                '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>');
                        //}
                    }
                }
            }
        },
        error: function (e) {
            toastr["error"](e, "Error al obtener periodos de aplicación", {progressBar: true, closeButton: true});
        }
    });
};

const obtenPeriodoGeneracion = () => {
    var idNomina = $("#id_nomina").val();
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPeriodosGeneracionCercanos/" + idNomina,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#periodoGeneracion').empty();
                var vselected = "";
                //var compara_periodo = comparaFechas(data, vselected);
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        var n_periodo = data[x].periodo;
                        var fecha_inicial_formato = new Date(data[x].fecha_inicial);
                        var dias_inicial = fecha_inicial_formato.getDate();
                        var meses_inicial = fecha_inicial_formato.getMonth() + 1;
                        var anios_inicial = fecha_inicial_formato.getFullYear();

                        var fecha_final_formato = new Date(data[x].fecha_final);
                        var dias_final = fecha_final_formato.getDate();
                        var meses_final = fecha_final_formato.getMonth() + 1;
                        var anios_final = fecha_final_formato.getFullYear();
                        //if (compara_periodo !== data[x].periodo) {
                        $('#periodoGeneracion').append('<option value="' + data[x].periodo + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
                                '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>');
                        //}
                    }
                }
            }
        },
        error: function (e) {
            toastr["error"](e, "Error al obtener periodos de generación", {progressBar: true, closeButton: true});
        }
    });
};

// Asigna los periodos
const comparaFechas = (data, vselected) => {
    var hoy = new Date();
    hoy.setHours(0, 0, 0, 0);
    for (var i = 0; i < data.length; i++) {
        var n_periodo = data[i].periodo;
        //Fechas iniciales
        var fecha_inicial_formato = new Date(data[i].fecha_inicial);
        var dias_inicial = fecha_inicial_formato.getDate();
        var meses_inicial = fecha_inicial_formato.getMonth() + 1;
        var anios_inicial = fecha_inicial_formato.getFullYear();
        //Fechas finales
        var fecha_final_formato = new Date(data[i].fecha_final);
        var dias_final = fecha_final_formato.getDate();
        var meses_final = fecha_final_formato.getMonth() + 1;
        var anios_final = fecha_final_formato.getFullYear();
        fecha_inicial_formato.setHours(0, 0, 0, 0);
        fecha_final_formato.setHours(0, 0, 0, 0);
        var fecha_corte_formato = new Date(data[i].fecha_corte);
        fecha_corte_formato.setHours(0, 0, 0, 0);
        if (hoy <= fecha_corte_formato) {
            $('#periodo_generacion').empty().append('<option value="' + data[i].periodo + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
                    '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>');
            $('#periodo_aplicacion').empty().append('<option value="' + data[i].periodo + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
                    '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>');
            return data[i].periodo;
        } else if (hoy > fecha_corte_formato) {
            var contador = i + 1;
            n_periodo = data[contador].periodo;
            fecha_inicial_formato = new Date(data[contador].fecha_inicial);
            fecha_final_formato = new Date(data[contador].fecha_final);
            $('#periodo_generacion').empty().append('<option value="' + data[contador].periodo + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + fecha_inicial_formato.getDate() + '/' + (fecha_inicial_formato.getMonth() + 1) + '/' + fecha_inicial_formato.getFullYear() +
                    '--' + fecha_final_formato.getDate() + '/' + (fecha_final_formato.getMonth() + 1) + '/' + fecha_final_formato.getFullYear() + '</option>');
            $('#periodo_aplicacion').empty().append('<option value="' + data[contador].periodo + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + fecha_inicial_formato.getDate() + '/' + (fecha_inicial_formato.getMonth() + 1) + '/' + fecha_inicial_formato.getFullYear() +
                    '--' + fecha_final_formato.getDate() + '/' + (fecha_final_formato.getMonth() + 1) + '/' + fecha_final_formato.getFullYear() + '</option>');
            return data[contador].periodo;
        }
    }
};

const buscarSemanasDeLaNomina = () => {
    let idNomina = $("#id_nomina").val();
    let url;
    // Nómina de varios
    if (idNomina === "1") {
        url = "trabajador/obtenerFechasSemanalesVarios";
        // Nómina de Transportación     
    } else if (idNomina === "2") {
        url = "trabajador/obtenerFechasSemanalesTransportacion";
        // Nómina de Confianza    
    } else if (idNomina === "3") {
        url = "trabajador/obtenerFechasSemanalesConfianza";
    }
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información de las semanas de la nómina", "Aviso", {progressBar: true, closeButton: true});
            } else {
                // Limpiar el combo antes de llenarlo
                $('#cmbSemanasNomina').empty();

                // Llenar el combo con las fechas formateadas
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        let fechaInicialStr = data[x].fecha_inicial_semana;
                        let fechaFinalStr = data[x].fecha_final_semana;

                        // Convertir las fechas a objetos Date
                        const fechaInicial = new Date(fechaInicialStr);
                        const fechaFinal = new Date(fechaFinalStr);

                        // Verificar si la conversión fue exitosa
                        if (isNaN(fechaInicial.getTime()) || isNaN(fechaFinal.getTime())) {
                            console.warn(`Fecha inválida: ${fechaInicialStr} - ${fechaFinalStr}`);
                            continue;
                        }

                        const formattedFechaInicial = `S. ${fechaInicial.getUTCDate()}-${fechaInicial.getUTCMonth() + 1}-${fechaInicial.getUTCFullYear()}`;
                        const formattedFechaFinal = `${fechaFinal.getUTCDate()}-${fechaFinal.getUTCMonth() + 1}-${fechaFinal.getUTCFullYear()}`;

                        const optionText = `${formattedFechaInicial} al ${formattedFechaFinal}`;
                        $('#cmbSemanasNomina').append('<option value="' + x + '">' + optionText + '</option>');
                    }

                    // Seleccionar automáticamente la fecha más cercana a la actual
                    const fechaActual = new Date();
                    const fechaMasCercana = data.reduce((closest, week, index) => {
                        let inicioSemanaStr = week.fecha_inicial_semana;
                        let finSemanaStr = week.fecha_final_semana;

                        const inicioSemana = new Date(inicioSemanaStr);
                        const finSemana = new Date(finSemanaStr);

                        // Verificar si la conversión fue exitosa
                        if (isNaN(inicioSemana.getTime()) || isNaN(finSemana.getTime())) {
                            console.warn(`Fecha inválida: ${inicioSemanaStr} - ${finSemanaStr}`);
                            return closest;
                        }

                        const diffInicio = Math.abs(inicioSemana - fechaActual);
                        const diffFin = Math.abs(finSemana - fechaActual);
                        if (index === 0 || diffInicio < closest.diffInicio || diffFin < closest.diffFin) {
                            return {
                                index: index,
                                diffInicio: diffInicio,
                                diffFin: diffFin
                            };
                        }
                        return closest;
                    }, {});
                    $('#cmbSemanasNomina').val(fechaMasCercana.index);
                    definirDiasEncabezadoTabla();
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Semanas de la Nómina : " + e, "Error", {progressBar: true, closeButton: true});
        }
    });
};

let toastrShown = false;

const buscarRegistroExistente = (idTrabajador) => {
    let periodoAplicacion = $('#periodoAplicacion').val();
    let periodoGeneracion = $('#periodoGeneracion').val();
    let lapsoSemanas = $("#cmbSemanasNomina option:selected").text();
    //Se limpia la tabla para obtener los registros actualizados en la base de datos
    limpiarTabla();
    $.ajax({
        type: "GET",
        url: "tiempoExtra/encontrarTiempoExtra/" + idTrabajador + "/" + periodoAplicacion + "/" + periodoGeneracion + "/" + lapsoSemanas,
        dataType: 'json',
        success: function (dataRegistroExistente) {
            if (dataRegistroExistente.data === null) {
                //Si no hay registro existente se genera un nuevo registro
                $("#btnGuardar").removeAttr("hidden");
                $("#btnEditar").attr("hidden", true);
                $("#elimina").attr("hidden", true);
            } else {
                //Se oculta el botón de guardado si había sido mostrado anteriormente
                $("#btnGuardar").attr("hidden", true);
                //Se abre la opción de elmiminar el registro
                $("#elimina").removeAttr("hidden");
                //O de editarlo
                $("#btnEditar").removeAttr("hidden");
                toastr.options = {
                    maxOpened: 1,
                    preventDuplicates: 1,
                    autoDismiss: true
                };
                if (!toastrShown) {
                    toastrShown = true;
                    toastr.options = {
                        progressBar: true,
                        closeButton: true
                    };
                    toastr.info("Se encontró el registro actual para semanas, periodos de aplicación y generación seleccionados", "Información");
                }
                //Se coloca el id del tiempo extra encontrado para efectos de elimunación o actualización deñ registro existente en la semana, periodo de aplicación y generación encontrado
                $('#idTiempoExtraEncontrado').val(dataRegistroExistente.data.id_tiempo_extra);
                for (let i = 1; i <= 7; i++) {
                    $(`#numeroHorasDia${i}`).val(dataRegistroExistente.data[`tiempo_extra_${i}`]);
                    $(`#paseosLabDia${i}`).val(dataRegistroExistente.data[`paseos_lab_${i}`]);
                    $(`#extraDobleDia${i}`).val(dataRegistroExistente.data[`te_doble_${i}`]);
                    $(`#extraTripleDia${i}`).val(dataRegistroExistente.data[`te_triple_${i}`]);
                }
                $('#totalTiempoExtraDoble').val(dataRegistroExistente.data.total_te_doble.toFixed(1));
                $('#totalTiempoExtraTriple').val(dataRegistroExistente.data.total_te_triple.toFixed(1));
                $('#totalTiempoExtraDescansosLaborados').val(dataRegistroExistente.data.total_te_paseos_doble.toFixed(1));
                $('#totalTiempoExtraDiaFestivo').val(dataRegistroExistente.data.total_te_festivo_doble.toFixed(1));
                if (dataRegistroExistente.data.dif_cve_19 !== null) {
                    $('#diferenciaTiempoExtraRA10').val(dataRegistroExistente.data.dif_cve_19.toFixed(1));
                } else {
                    $('#diferenciaTiempoExtraRA10').val('');
                }

            }
        },
        error: function (e) {
            console.error(e);
        }
    });
};

const buscarCapturaRa10 = (idTrabajador) => {
    let periodoGeneracion = $('#periodoGeneracion').val();
    $.ajax({
        type: "GET",
        url: "ra10/existeCapturaRA10/" + idTrabajador + "/" + periodoGeneracion,
        dataType: 'json',
        success: function (dataRa10) {
            if (dataRa10) {
                toastr["warning"]("Existe un registro activo de RA10 para el periodo: " + periodoGeneracion, "Advertencia", {progressBar: true, closeButton: true, timeOut: 0});
            }
        },
        error: function (e) {
            console.error(e);
        }
    });
};

const trueFalseCapturaRa10 = (idTrabajador) => {
    let periodoGeneracion = $('#periodoGeneracion').val();
    // Devolver una promesa
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "GET",
            url: "ra10/existeCapturaRA10/" + idTrabajador + "/" + periodoGeneracion,
            dataType: 'json',
            success: function (dataRa10) {
                // Resolvemos la promesa con el valor booleano
                resolve(dataRa10);
            },
            error: function (e) {
                console.error(e);
                // En caso de error, rechazamos la promesa
                reject(e);
            }
        });
    });
};

const definirDiasEncabezadoTabla = () => {
    //Se bloquean los paseos por si ha habían sido desbloqueados antes en un festivo
    bloquearPaseos();
    const idNomina = $("#id_nomina").val();
    const diasSemana = $("#cmbSemanasNomina option:selected").text();
    // Eliminar la "S." al principio de la cadena
    const cadenaLimpia = diasSemana.replace("S. ", "");

    // Dividir la cadena en dos partes usando el delimitador '-'
    const fechas = cadenaLimpia.split('al').map(fecha => fecha.trim());

    let fechasActualizadas = fechas.map(fecha => fecha.replace(/-/g, '/'));

    // Extraer el día de inicio y fin del rango
    const fechaInicio = parseFecha(fechasActualizadas[0]);
    const fechaFin = parseFecha(fechasActualizadas[1]);

    // Calcular la diferencia en días entre las fechas
    const diferenciaEnTiempo = fechaFin - fechaInicio;
    const diferenciaEnDias = Math.floor(diferenciaEnTiempo / (1000 * 3600 * 24));

    // Crear un array con el rango de días
    const rangoDias = [];

    for (let i = 0; i <= diferenciaEnDias; i++) {
        const dia = new Date(fechaInicio);
        dia.setDate(fechaInicio.getDate() + i);
        // Obtener solo el día
        const numeroDia = dia.getDate();
        //Se realiza la búsqueda de días festivo con base a la nómina seleccionada y el día que se está mapeando en el encabezado
        buscarDiasFestivos(idNomina, dia, i + 1);
        rangoDias.push(numeroDia);
    }

    const cambiarOrdenEncabezados = (nuevosDias) => {
        habilitarCamposDiasDescansoTrabajador(nuevosDias);
        for (let i = 0; i < 7; i++) {
            $(`#dia${i + 1}Encabezado`).text(nuevosDias[i]);
        }
    };

    switch (idNomina) {
        case "1":
            cambiarOrdenEncabezados(["MIERCOLES " + rangoDias[0], "JUEVES " + rangoDias[1], "VIERNES " + rangoDias[2], "SABADO " + rangoDias[3], "DOMINGO " + rangoDias[4], "LUNES " + rangoDias[5], "MARTES " + rangoDias[6]]);
            break;
        case "2":
            cambiarOrdenEncabezados(["DOMINGO " + rangoDias[0], "LUNES " + rangoDias[1], "MARTES " + rangoDias[2], "MIERCOLES " + rangoDias[3], "JUEVES " + rangoDias[4], "VIERNES " + rangoDias[5], "SABADO " + rangoDias[6]]);
            break;
        case "3":
            cambiarOrdenEncabezados(["LUNES " + rangoDias[0], "MARTES " + rangoDias[1], "MIERCOLES " + rangoDias[2], "JUEVES " + rangoDias[3], "VIERNES " + rangoDias[4], "SABADO " + rangoDias[5], "DOMINGO " + rangoDias[6]]);
            break;
        default:
            console.error("Valor de idNomina no reconocido");
            break;
    }
};

const bloquearPaseos = () => {
    for (let i = 0; i < 7; i++) {
        $(`#paseosLabDia${i + 1}`).prop('disabled', true);
    }
};

const habilitarCamposDiasDescansoTrabajador = (nuevosDias) => {
    let inicialesDias = [];
    let inicialesDescansoTrabajador = $('#campo_descansos_trabajador').val();
    for (let i = 0; i < 7; i++) {
        //Obteniendo iniciales de los días para limitar los campos de paseos
        const diaCompleto = nuevosDias[i];
        const inicialDia = diaCompleto.substring(0, 1);
        inicialesDias.push(inicialDia);
        if (inicialesDescansoTrabajador.includes(inicialDia)) {
            $(`#paseosLabDia${i + 1}`).prop('disabled', false);
        }
    }
};

const buscarDiasFestivos = (idNomina, fecha, numeroDia) => {
    const fechaFormateada = fecha.toISOString().split('T')[0];
    $.ajax({
        type: "GET",
        url: "catalogos/encontrarDiasFestivos/" + idNomina + "/" + fechaFormateada,
        dataType: 'json',
        success: function (dataDiaFestivo) {
            if (dataDiaFestivo !== undefined && dataDiaFestivo.length > 0) {
                //Se marca de azul el día identificado como festivo
                $(`#dia${numeroDia}Encabezado`).css('color', 'blue');
                //Se habilita el día del paseo que fue festivo
                $(`#paseosLabDia${numeroDia}`).prop('disabled', false);
                toastr["info"]("El día marcado en azul fue festivo", "Información", {progressBar: true, closeButton: true});
            } else {
                //Si se había buscado antes un festivo y cambia la semana se elimina el color que tenía
                $(`#dia${numeroDia}Encabezado`).css('color', '');
            }
        },
        error: function (e) {
            console.error(e);
            toastr["error"]("Error al buscar días festivos", "Error", {progressBar: true, closeButton: true});
        }
    });
};

const parseFecha = (fechaStr) => {
    const [dia, mes, anio] = fechaStr.split('/').map(Number);
    return new Date(anio, mes - 1, dia);
};


const eliminarTiempoExtraActual = () => {
    $("#eliminarTiempoExtraModal").submit(function (event) {
        event.preventDefault();
        const valores = window.location.search;
        const d = decodeURIComponent(valores);
        const urlParams = new URLSearchParams(d);
        let idTrabajador = urlParams.get('id_trabajador');
        let estatus = 0;
        let idTiempoExtra = $('#idTiempoExtraEncontrado').val();
        $.ajax({
            type: "POST",
            url: "tiempoExtra/estatusTiempoExtra/" + idTiempoExtra + "/" + estatus,
            contentType: "application/json",
            success: function (data) {
                toastr['success']("Se ha eliminado correctamente el tiempo extra", "Aviso");
                //Eliminar referencia al id encontrado
                $('#idTiempoExtraEncontrado').val('');
                //Se ocullta el botón de elminiación
                $("#elimina").attr("hidden", true);
                //Se oculta la edición del elemento
                $("#btnEditar").attr("hidden", true);
                //Se oculta el modal de eliminar
                $("#eliminarTiempoExtraModal").modal('hide');
                limpiarTabla();
                //Para ajustar botones mostrados (Gurdar en nuevo registro y edición en registro existente)
                buscarRegistroExistente(idTrabajador);
            },
            error: function (e) {
                toastr["error"](e, "Error", {progressBar: true, closeButton: true});
            }
        });
    });
};

const limpiarTabla = () => {
    //Se vacían los campos de la tabla
    for (let i = 1; i <= 7; i++) {
        $(`#numeroHorasDia${i}`).val('');
        $(`#paseosLabDia${i}`).val('');
        $(`#extraDobleDia${i}`).val('');
        $(`#extraTripleDia${i}`).val('');
    }
    $('#totalTiempoExtraDoble').val('');
    $('#totalTiempoExtraTriple').val('');
    $('#totalTiempoExtraDescansosLaborados').val('');
    $('#totalTiempoExtraDiaFestivo').val('');
    $('#diferenciaTiempoExtraRA10').val('');
    //$('#idTiempoExtraEncontrado').val('');
};

