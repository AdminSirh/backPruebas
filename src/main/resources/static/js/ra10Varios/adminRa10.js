//************VARIABLE GLOBAL PARA ID TRABAJADOR************
let idTrabajador;
let operativo;
let diferenciaSueldoSuplencia;
//******************************************CARGA DEL DOM**********************************
document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    idTrabajador = urlParams.get('id_trabajador');
    obtenerInformacionTrabajador();
    obtenerInformacionPuestoTrabajador(idTrabajador, '#campo_puesto_trabajador', '#campo_sueldo_hora_8', '#campo_sueldo_hora_7', '#nivel', '#id_puesto');
    cargarPeriodos();
    consultarOperativoAdministrativo();
    cargaMotivosRa10();
});
//Redirecciona al usuario a adminRa10
function verTrabajadores() {
    window.location.href = 'adminRa10';
}
//Redirecciona al usuario a la página que contiene todos los movimientos provisionales registrados para ese trabajador
function registrosExistentesRa10() {
    window.location.href = 'consultarMovimientosProvisionales?id_trabajador=' + idTrabajador;
}

//Evento de escucha para la selección del periodo de generación
$('#periodo_generacion').change(function () {
    let texto = $(this).find(":selected").text();
    // Dividir el texto en partes usando la cadena ' : ' como separador
    let partes = texto.split(' : ');

    if (partes.length === 2) {
        // Obtener la segunda parte, que contiene las fechas
        let fechas = partes[1];
        // Dividir las fechas en fecha inicial y fecha final usando '--' como separador
        let fechasSeparadas = fechas.split('--');

        if (fechasSeparadas.length === 2) {
            let fechaInicialStr = fechasSeparadas[0].trim(); // Obtener la fecha inicial como cadena
            let fechaFinalStr = fechasSeparadas[1].trim();   // Obtener la fecha final como cadena
            // Función para convertir cadena de fecha a objeto Date
            const parseDate = (dateStr) => {
                const [day, month, year] = dateStr.split('/').map(Number);
                return new Date(year, month - 1, day); // mes - 1 porque en JavaScript los meses van de 0 a 11
            };
            // Convertir las cadenas de fecha a objetos Date
            let fechaInicial = parseDate(fechaInicialStr);
            let fechaFinal = parseDate(fechaFinalStr);
            // Verificar si las fechas son válidas
            if (!isNaN(fechaInicial.getTime()) && !isNaN(fechaFinal.getTime())) {
                // Formatear las fechas en el formato "año-mes-día"
                let fechaInicialFormato = `${fechaInicial.getFullYear()}-${String(fechaInicial.getMonth() + 1).padStart(2, '0')}-${String(fechaInicial.getDate()).padStart(2, '0')}`;
                let fechaFinalFormato = `${fechaFinal.getFullYear()}-${String(fechaFinal.getMonth() + 1).padStart(2, '0')}-${String(fechaFinal.getDate()).padStart(2, '0')}`;
                // Imprimir las fechas formateadas
                buscarIncidenciasKardexPeriodo(fechaInicialFormato, fechaFinalFormato);
            } else {
                console.error("Fechas no válidas");
            }
        }
    }
});

$('#inputPuesto').on('input', function () {
    alert("Se ha detectado un cambio en el campo 'inputPuesto'");
});
//Obtiene unformación básica del trabajador, nomre y expediente
function obtenerInformacionTrabajador() {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + idTrabajador,
        dataType: 'json',
        success: function (data) {
            $('#campo_expediente').val(data.data.cat_expediente.numero_expediente);
            $('#campo_nombre').val(data.data.persona.nombre + " " + data.data.persona.apellido_paterno + " " + data.data.persona.apellido_materno);
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener información del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Obtiene toda la información relacionada al puesto
function obtenerInformacionPuestoTrabajador(id_trabajador, campoPuesto, campoSueldoHora8, campoSueldoHora7, campoNivel, campoPuestoSuplente) {
    $.ajax({
        type: "GET",
        url: "plaza/encontrarSueldo/" + id_trabajador,
        dataType: 'json',
        success: function (dataPuestoTrabajador) {
            let calculoSueldoHora8Trabajador = dataPuestoTrabajador.sueldoHora8;
            let calculoSueldoHora7Trabajador = dataPuestoTrabajador.sueldoHora7;
            calculoSueldoHora8Trabajador = calculoSueldoHora8Trabajador.toFixed(2);
            calculoSueldoHora7Trabajador = calculoSueldoHora7Trabajador.toFixed(2);
            $(campoPuesto).val(dataPuestoTrabajador.puesto);
            $(campoSueldoHora8).val(calculoSueldoHora8Trabajador);
            $(campoSueldoHora7).val(calculoSueldoHora7Trabajador);
            $(campoNivel).val(dataPuestoTrabajador.nivel);
            $(campoPuestoSuplente).val(dataPuestoTrabajador.idPuesto);
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener datos del puesto del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Obtiene los periodos para listarlos en el select correspondiente
function cargarPeriodos() {

    const crearOpcion = (periodo, fechaInicial, fechaFinal) => {
        const periodoStr = String(periodo); // Convertir a string
        const diasInicial = fechaInicial.getDate();
        const mesesInicial = fechaInicial.getMonth() + 1;
        const aniosInicial = fechaInicial.getFullYear();
        const diasFinal = fechaFinal.getDate();
        const mesesFinal = fechaFinal.getMonth() + 1;
        const aniosFinal = fechaFinal.getFullYear();
        return 'P. ' + periodoStr.substr(4, 5) + " : " +
                diasInicial + '/' + mesesInicial + '/' + aniosInicial +
                '--' + diasFinal + '/' + mesesFinal + '/' + aniosFinal;
    };

    $.ajax({
        type: "GET",
        url: "trabajador/buscaPeriodosFechas/1",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                return;
            }
            let vselected = "";
            let periodoSeleccionado = null;
            let fechaInicialPeriodoSeleccionado = null;
            let fechaFinalPeriodoSeleccionado = null;
            const hoy = new Date();
            hoy.setHours(0, 0, 0, 0);
            for (const periodo of data) {
                const fechaInicial = new Date(periodo.fecha_inicial);
                const fechaFinal = new Date(periodo.fecha_final);
                const fechaCorte = new Date(periodo.fecha_corte);
                fechaInicial.setHours(0, 0, 0, 0);
                fechaFinal.setHours(0, 0, 0, 0);
                fechaCorte.setHours(0, 0, 0, 0);
                if (hoy <= fechaCorte) {
                    const opcion = crearOpcion(periodo.periodo, fechaInicial, fechaFinal);
                    $('#periodo_generacion, #periodo_aplicacion').empty().append(`<option value="${periodo.periodo}" ${vselected}>${opcion}</option>`);
                    periodoSeleccionado = periodo.periodo;
                    fechaInicialPeriodoSeleccionado = periodo.fecha_inicial;
                    fechaFinalPeriodoSeleccionado = periodo.fecha_final;
                    break;
                } else if (hoy > fechaCorte) {
                    const siguientePeriodo = data[data.indexOf(periodo) + 1];
                    if (siguientePeriodo) {
                        const opcion = crearOpcion(siguientePeriodo.periodo, fechaInicial, fechaFinal);
                        $('#periodo_generacion, #periodo_aplicacion').empty().append(`<option value="${siguientePeriodo.periodo}" ${vselected}>${opcion}</option>`);
                        periodoSeleccionado = siguientePeriodo.periodo;
                        fechaInicialPeriodoSeleccionado = periodo.fecha_inicial;
                        fechaFinalPeriodoSeleccionado = periodo.fecha_final;
                        break;
                    }
                }
            }
            if (periodoSeleccionado) {
                data.forEach(periodo => {
                    if (periodo.periodo !== periodoSeleccionado) {
                        const fechaInicial = new Date(periodo.fecha_inicial);
                        const fechaFinal = new Date(periodo.fecha_final);
                        const opcion = crearOpcion(periodo.periodo, fechaInicial, fechaFinal);
                        $('#periodo_generacion, #periodo_aplicacion').append(`<option value="${periodo.periodo}" ${vselected}>${opcion}</option>`);
                    }
                });
            }
        },
        error: function (e) {
            toastr["error"](e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Carga los motivos de suplencia de la RA10 en el select correspondiente
function cargaMotivosRa10(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarMotivosRa10",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_motivo === id) ? vselected = "selected" : vselected = " ";
                        $('#cmb_motivo').append('<option value="' + data[x].id_motivo + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Motivo: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//Servicio que obtiene si el trabajador es operativo o administrativo según su área o ubicación 
function consultarOperativoAdministrativo() {
    $.ajax({
        type: "GET",
        url: "trabajador/esOperativo/" + idTrabajador,
        dataType: 'json',
        success: function (dataOperativo) {
            if (dataOperativo !== null) {
                if (dataOperativo) {
                    $("#campo_sueldo_hora_7").parent().hide();
                    $('#tipo').val('Operativo');
                    $('#operativo').val(true);
                    operativo = dataOperativo;
                } else {
                    $("#campo_sueldo_hora_8").parent().hide();
                    $('#tipo').val('Administrativo');
                    $('#administrativo').val(true);
                    operativo = dataOperativo;
                }
            }
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al definir si el trabajador es operativo o administrativo, verificar informacion plaza", "Error", {progressBar: true, closeButton: true});
        }
    });
}