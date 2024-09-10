document.addEventListener('DOMContentLoaded', () => {
    cmbAreas();
    cmbTipoNomina();
    vincularEventosCampos();
});

function vincularEventosCampos() {
    //Hididng the div with the worker record (Expediente) so it can olny be used in the Trust payroll
    const expedienteDiv = $("#busquedaExpediente");
    expedienteDiv.hide();
    const cmbAreasBaseSelect = $("#cmbAreasBase");
    expedienteDiv.on("show.bs.collapse", function () {
        cmbAreasBaseSelect.prop("disabled", true);
        cmbAreasBaseSelect.val("9");
    });
    expedienteDiv.on("hide.bs.collapse", function () {
        cmbAreasBaseSelect.prop("disabled", false);
    });
    //This function checks if the length of the input is 6, then it search the corresponding period
    $("#inputPeriodoAplicación").on("input", function () {
        var tipoNomina_id = $("#cmbTipoNomina").val();
        var inputValue = $(this).val();
        toastr.options = {
            "preventDuplicates": true
        };
        toastr.info("Ingresa los 6 números correspondientes al periodo de pago para rellenar fecha inicial y fecha final");
        if (inputValue.length === 6) {
            buscarPeriodoPago(inputValue, tipoNomina_id);
        }
        if (inputValue.length >= 5) {
            $("#dateFechaInicial").val('');
            $("#dateFechaFinal").val('');
        }
    });
    $("#cmbTipoNomina").on("change", function () {
        var selectedValue = $("#cmbTipoNomina").val();
        $("#inputPeriodoAplicación, #dateFechaInicial, #dateFechaFinal").val('');
        if (selectedValue === "1") {
            toastr.info("Ingresa el periodo para rellenar el campo de la fecha inicial y fecha final");
            $("#dateFechaInicial, #dateFechaFinal").prop("readonly", false);
        } else {
            $("#dateFechaInicial, #dateFechaFinal").prop("readonly", false);
            expedienteDiv.hide();
        }

        if (selectedValue === null || selectedValue === 'null') {
            toastr.info("Ingresa el periodo para rellenar el campo de la fecha inicial y fecha final");
            $("#dateFechaInicial, #dateFechaFinal, #inputPeriodoAplicación").prop("readonly", true);
        } else {
            $("#inputPeriodoAplicación").prop("readonly", false);
        }

        if (selectedValue === "3" || selectedValue === "1") {
            expedienteDiv.show();
        } else if (selectedValue === "null") {
            expedienteDiv.hide();
        }
    });
}
function buscarPeriodoPago(inputValue, idNomina) {
    // Realizar solicitud AJAX para obtener las fechas
    $.ajax({
        url: "trabajador/buscarPeriodoPago/" + inputValue + "/" + idNomina,
        dataType: "json",
        success: function (data) {
            // Formatear las fechas al formato "yyyy-MM-dd"
            if (data.data && data.data.fecha_inicial !== null) {
                toastr.success("Periodo Encontrado");
                var formattedFechaInicial = new Date(data.data.fecha_inicial).toISOString().split("T")[0];
                var formattedFechaFinal = new Date(data.data.fecha_final).toISOString().split("T")[0];
                // Asignar las fechas formateadas a los campos de fecha
                $("#dateFechaInicial").val(formattedFechaInicial);
                $("#dateFechaFinal").val(formattedFechaFinal);
            } else {
                toastr.warning("Venció la fecha de corte para este periodo o el periodo no existe para la nómina seleccionada");
                $("#dateFechaInicial").val('');
                $("#dateFechaFinal").val('');
            }
        },
        error: function () {
            console.log("Error al obtener las fechas");
        }
    });

}
//Se listan las áreas provenientes de la base de datos dentro del combo
function cmbAreas(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarAreas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                //Seleccion de todas las áreas id 9
                $('#cmbAreasBase').empty().append("<option value='9'>*Todas las Áreas </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].idArea === id) ? vselected = "selected" : vselected = "----";
                        $('#cmbAreasBase').append('<option value="' + data[x].idArea + '"' + vselected + '>' + data[x].desc_area + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//Se listan los tipos de nómina provenientes de la base de datos dentro del combo
function cmbTipoNomina(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                //Seleccion de las nominas con ID 1 y 3
                $('#cmbTipoNomina').empty().append('<option value="' + null + '">*Selecciona una nómina</option>');
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        if (data[x].id_tipo_nomina === 1 || data[x].id_tipo_nomina === 3) {
                            var vselected = (data[x].id_tipo_nomina === id) ? "selected" : "";
                            $('#cmbTipoNomina').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                        }
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//Generación de formatos de Control de asistencia con los parámetros obtenidos
$("#formControlAsistencia").submit(function (e) {
    e.preventDefault();
    let periodoAplicacionInput = $('#inputPeriodoAplicación').val();
    let cmbIdArea = $('#cmbAreasBase').val();
    let cmbTipoNominaID = $('#cmbTipoNomina').val();
    switch (cmbTipoNominaID) {
        case '1': //Nómina de Varios
            // Verificar el formato del período
            if (verificarInicio(periodoAplicacionInput)) {
                let fecha_inicio = $('#dateFechaInicial').val();
                let fecha_final = $('#dateFechaFinal').val();
                let expediente = $('#inputExpediente').val();
                let comentario = $('#comentarioOpcional').val();

                let fecha_inicial_formateada = formatearFecha(fecha_inicio);
                let fecha_final_formateada = formatearFecha(fecha_final);
                //Si selecciona 9 se seleccionarán todas las áreas
                if (expediente.trim() === '') {
                    if (cmbIdArea === '9') {
                        //Se envía id área como nulo para hacer una seleccion de todas las áreas de Varios (Base)
                        generarControlesAsistenciaBase(periodoAplicacionInput, null, fecha_inicial_formateada, fecha_final_formateada, null, null);
                        comprobarDepositosTrabajadores();
                        //Documento complementario
                        generarControlesAsistenciaComplementoBase(periodoAplicacionInput);
                    } else {
                        //Se envía el área seleccionada en el combo box
                        generarControlesAsistenciaBase(periodoAplicacionInput, cmbIdArea, fecha_inicial_formateada, fecha_final_formateada, null, null);
                        comprobarDepositosTrabajadores();
                        //Documento complementario
                        generarControlesAsistenciaComplementoBase(periodoAplicacionInput);
                    }
                } else {
                    generarControlesAsistenciaBase(periodoAplicacionInput, null, fecha_inicial_formateada, fecha_final_formateada, expediente, comentario);
                    comprobarDepositosTrabajadores();
                }
            } else {
                toastr.warning("El formato del período no es válido.", "Advertencia");
                return;
            }
            break;
        case '3': // Confianza
            if (verificarInicio(periodoAplicacionInput)) {
                let fecha_inicio = $('#dateFechaInicial').val();
                let fecha_final = $('#dateFechaFinal').val();
                let expediente = $('#inputExpediente').val();
                let comentario = $('#comentarioOpcional').val();

                let fecha_inicial_formateada = formatearFecha(fecha_inicio);
                let fecha_final_formateada = formatearFecha(fecha_final);
                if (expediente.trim() === '') {
                    //If 9 is selected it means that the users whant to select all the areas
                    if (cmbIdArea === '9') {
                        //We send the id_area ass null so it can be interpreted as a selection of all areas
                        generarControlesAsistenciaConfianza(periodoAplicacionInput, null, fecha_inicial_formateada, fecha_final_formateada, null, null);
                    } else {
                        //We send the elected area as a parameter
                        generarControlesAsistenciaConfianza(periodoAplicacionInput, cmbIdArea, fecha_inicial_formateada, fecha_final_formateada, null, null);
                    }
                } else {
                    generarControlesAsistenciaConfianza(periodoAplicacionInput, null, fecha_inicial_formateada, fecha_final_formateada, expediente, comentario);
                }
            } else {
                toastr.warning("El formato del período no es válido.", "Advertencia");
                return;
            }
            break;
        default:
            toastr.warning("Selecciona un tipo de Nómina.", "Advertencia");
    }
});
// Expresión regular para verificar los primeros dos dígitos como "20"
const formatoInicio = /^20/;

// Función para verificar el input del período
function verificarInicio(input) {
    return formatoInicio.test(input);
}
function formatearFecha(fecha) {
    let partesFecha = fecha.split('-');
    let año = partesFecha[0];
    let mes = partesFecha[1];
    let dia = partesFecha[2];

    let mesesCortos = [
        "ene", "feb", "mar", "abr", "may", "jun",
        "jul", "ago", "sep", "oct", "nov", "dic"
    ];

    return `${dia}/${mesesCortos[parseInt(mes) - 1]}/${año}`;
}
function generarControlesAsistenciaConfianza(periodoAplicacionInput, cmbIdArea, fecha_inicio, fecha_final, expediente, comentario) {
    const url = "report/controlAsistenciaPersonalConfianza?periodo=" + periodoAplicacionInput + "&area_id=" + cmbIdArea + "&fecha_inicio=" + fecha_inicio + "&fecha_fin=" + fecha_final + "&expediente=" + expediente + "&comentario=" + comentario + "&tipo=PDF";
    let nombreArchivo;
    if (expediente !== null) {
        nombreArchivo = "ControlAsistenciaConfianzaExpediente" + expediente + ".pdf";
    } else {
        nombreArchivo = "ControlAsistenciaConfianza" + periodoAplicacionInput + ".pdf";
    }
    llamadaAjax(url, nombreArchivo);
}
//Se genera la url para el control de asistencia base, el nombre del archivo a generar y se realiza la llamada a la funcion ajax
function generarControlesAsistenciaBase(periodoAplicacionInput, id_area, fecha_inicio, fecha_final, expediente, comentario) {
    const url = "report/controlAsistenciaPersonalBase?periodo=" + periodoAplicacionInput + "&area_id=" + id_area + "&fecha_inicio=" + fecha_inicio + "&fecha_fin=" + fecha_final + "&expediente=" + expediente + "&comentario=" + comentario + "&tipo=PDF";
    let nombreArchivo;
    if (expediente !== null) {
        nombreArchivo = "ControlAsistenciaBaseExpediente" + expediente + ".pdf";
    } else {
        nombreArchivo = "ControlAsistenciaBase" + periodoAplicacionInput + ".pdf";
    }
    llamadaAjax(url, nombreArchivo);
}
//Comprueba los trabajadores que no tienen depósito
function comprobarDepositosTrabajadores() {
    $.ajax({
        type: 'GET',
        url: 'horarios/buscarTrabajadoresBaseSinDepositoAsignado',
        success: function (data) {
            if ($.isEmptyObject(data)) {
            } else {
                $("#modalExpedientesSinDeposito").modal("show");
                generarTxtExpedientesSinDeposito(data);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al consultar depósitos trabajadores", "Error", {
                progressBar: true,
                closeButton: true
            });
        }
    });
}
//Genera un txt que contiene los expedientes sin depósito asignado
function generarTxtExpedientesSinDeposito(data) {
    $("#modalExpedientesSinDeposito").submit(function (event) {
        event.preventDefault();
        var fecha = new Date();
        var options = {year: 'numeric', month: 'long', day: 'numeric', weekday: 'long'};
        var linea1 = new Intl.DateTimeFormat('es-ES', options).format(fecha);
        var linea2 = "Expedientes de la nómina de varios sin depósito asignado (No se generan controles de asistencia): ";
        var lineasRegistros = [];

        data.forEach(function (registro) {
            var expedienteTexto = registro.numero_expediente + " " + registro.nombre + " " +
                    registro.apellido_paterno + " " + registro.apellido_materno;
            lineasRegistros.push(expedienteTexto);
        });

        var contenido = linea1 + "\n" + linea2 + "\n" + lineasRegistros.join('\n');
        var nombreTxt = "expedientesSinDepositoAsignado.txt";

        var blob = new Blob([contenido], {type: "text/plain"});
        var url = window.URL.createObjectURL(blob);
        var a = document.createElement("a");
        a.href = url;
        a.download = nombreTxt;
        a.click();
        window.URL.revokeObjectURL(url);
        $("#modalExpedientesSinDeposito").modal("hide");
    });
}
//Genera el archivo de complemento que se genera solo para la nómina de varios/base
function generarControlesAsistenciaComplementoBase(periodoAplicacionInput) {
    const url = "report/complementoControlAsistenciaPersonalBase?periodo=" + periodoAplicacionInput + "&tipo=PDF";
    const nombreArchivo = "ComplementoCaBase" + periodoAplicacionInput + ".pdf";
    llamadaAjax(url, nombreArchivo);
}
//Se hace la llamada al back con el servicio correspondiente para obtener el pdf generado
function llamadaAjax(url, nombreArchivo) {
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
                resetFormAndValidation();
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
}
// Resetting the form
function resetFormAndValidation() {
    $('#formControlAsistencia')[0].reset();
    $("#dateFechaInicial, #dateFechaFinal, #inputPeriodoAplicación").prop("readonly", true);
    //$('.needs-validation').removeClass('was-validated');
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    const expedienteDiv = $("#busquedaExpediente");
    expedienteDiv.hide();
}