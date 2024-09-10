//Arreglos globales para obtener periodos para cada nómina 
let periodosVarios = [];
let periodosTransportacion = [];
let periodosConfianza = [];
let periodosEstructura = [];
document.addEventListener('DOMContentLoaded', () => {
    cmbBimestres();
    cmbTipoNomina();
    cmbTimoMovImss();
    vincularEventosInputs();
});

function vincularEventosInputs() {
    $('#txtExpediente').on('input', function () {
        let inputExpediente = $(this).val();
        // Limita la entrada a 5 dígitos
        if ($(this).val().length > 5) {
            $(this).val($(this).val().slice(0, 5));
        }
        //Si la longitud es mayor a cuatro entonces hace la búsqueda del trabajador por expediente
        if (inputExpediente.length > 4) {
            buscaTrabajadorPorExpediente(inputExpediente);
        } else {
            $('#nombreCompleto').val('');
            $('#idTrabajador').val('');
        }
    });
    //Añade los periodos de pago que abarcan el bimestre para cada nómina
    $('#cmbBimestre').change(function () {
        $('#tabla_periodos_nomina_varios_bimestre tbody').empty();
        resetTablaProgresoNominas();
        resetBarraProgreso();
                $("#btn_elimina_sdi").hide();
        // Obtener el valor seleccionado
        var id_bimestre = $(this).val();
        //Se valida si se retorna al valor por defecto para no mandar el id del bimestre vacío
        if (id_bimestre === '') {
            return;
        }
        buscarDatosBimestre(id_bimestre);
    });
}
//Resetea y oculta la tabla de progreso de cada nómina
function resetTablaProgresoNominas() {
    //Oculta la tabla si se realizó el proceso de generación
    $('#nominasTable').hide();
    // Limpiar el contenido de la tabla para evitar filas duplicadas
    $('#nominasTable tbody').empty();
}
//Muestra la barra de progreso y coloca el procentaje de avance en 0%
function resetBarraProgreso() {
    $('#progressBar1').parent().show();
    $('#progressBar1').css('width', '0%').text('0%');
}

function buscaTrabajadorPorExpediente(numeroExpediente) {
    $.ajax({
        url: 'trabajador/buscarTrabajador_NumExpediente/' + numeroExpediente,
        method: 'GET',
        success: function (data) {
            if (data.data !== null) {
                $('#nombreCompleto').val([data.data.persona.apellido_paterno, data.data.persona.apellido_materno, data.data.persona.nombre].join(' '));
                $('#idTrabajador').val(data.data.id_trabajador);
            } else {
                toastr.warning('No se encontró información para este expediente', 'Advertencia');
            }
        },
        error: function (error) {
            toastr.error('Ocurrió un error al buscar el expediente' + error, 'Error');
        }
    });
}

function consultaSdiTrabajadores() {
    window.location.href = 'sdiTrabajdores';
}

function cmbBimestres(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarBimestres",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información de bimestres...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbBimestre').empty().append("<option value=''>* Selecciona</option> ");
                $('#cmbBimestreAltaTrabajador').empty().append("<option value=''>* Selecciona</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_bimestre === id) ? "selected" : "";
                        $('#cmbBimestre').append('<option value="' + data[x].id_bimestre + '"' + vselected + '>' + data[x].bimestre + '</option>');
                        $('#cmbBimestreAltaTrabajador').append('<option value="' + data[x].id_bimestre + '"' + vselected + '>' + data[x].bimestre + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Bimestre: " + e.responseText, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Obtiene el combo con el tipo de nóminas y genera un arreglo con el id de las nóminas que es usado en la función buscar datos bimestre
let nominas = [];
function cmbTipoNomina(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                //Seleccion de las nominas con ID 1 y 3
                $('#cmbNomina').empty().append('<option value="">*Selecciona una nómina</option>');
                $('#cmbNominaTxtSua').empty().append('<option value="">*Selecciona una nómina</option>');
                $('#cmbNominaTxtIdse').empty().append('<option value="">*Selecciona una nómina</option>');
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        if (data[x].id_tipo_nomina !== 4) {
                            var vselected = (data[x].id_tipo_nomina === id) ? "selected" : "";
                            $('#cmbNomina').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                            $('#cmbNominaTxtSua').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                            $('#cmbNominaTxtIdse').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                            //Se guardan los id en el arreglo de nóminas que se utiliza en la función buscar datos bimestre
                            nominas.push(data[x].id_tipo_nomina);
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

function cmbTimoMovImss(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarMotivosImss",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información de bimestres...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbTipoMovIdse').empty().append("<option value=''>* Selecciona</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_movimiento === id) ? "selected" : "";
                        $('#cmbTipoMovIdse').append('<option value="' + data[x].id_movimiento + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Bimestre: " + e.responseText, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Obtiene la información del bimestre seleccionado, su fecha de inicio y fin 
function buscarDatosBimestre(id_bimestre) {
    $.ajax({
        type: "GET",
        url: "catalogos/encuentraUnBimestre/" + id_bimestre,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información del bimestre seleccionado ...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                let fechaInicio = data.data.fecha_inicio;
                let fechaFin = data.data.fecha_fin;
                //Por cada nómina busca los periodos que se cruzan entre las fechas de inicio y fecha de fin del bimestre
                nominas.forEach(function (nominaId) {
                    obtenerPeriodosNomina(nominaId, fechaInicio, fechaFin);
                });
                validarRegistrosExistentesBimestre(id_bimestre);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Bimestre: " + e.responseText, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Valida que no se haya corrido el proceso anteriormente
function validarRegistrosExistentesBimestre(id_bimestre) {
    //Se valida que no exista el bimestre a calcuar
    //let existeCalculoBimestral = existeCalculoBimestre(id_bimestre);
    /*Solo se validan los acumulados que corresponden al proceso completo del S.D.I
     * los trabajadores de nuevo ingreso no se registran en la tabla de acumulados ya que no cuentan aún con información
     * en las tablas de pago*/
    //Se valida que no existan acumulados en el bimestre
    let existeAcumuladoBimestral = existeAcumuladoBimestre(id_bimestre);
    if (existeAcumuladoBimestral) {
        toastr["info"]("El cálculo de S.D.I de todas las nóminas y bimestre seleccionado ya fue generado", "Información", {progressBar: true, closeButton: true});
        $("#btn_sdi").prop("disabled", true);
        $("#btn_elimina_sdi").show();
        resetTablaProgresoNominas();
        resetBarraProgreso();
    } else {
        $("#btn_sdi").prop("disabled", false);
        $("#btn_elimina_sdi").hide();
        resetTablaProgresoNominas();
        resetBarraProgreso();
    }
}
//Obtiene los periodos de nómina que se crruzan en las fechas de inicio y fin proporcionadas
function obtenerPeriodosNomina(id_nomina, fecha_inicio, fecha_fin) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscaLapsoPeriodos/" + id_nomina + "/" + fecha_inicio + "/" + fecha_fin,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información de bimestres...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                let nombreNomina;
                if (id_nomina === 1) {
                    nombreNomina = "Varios";
                    periodosVarios = data.data;
                } else if (id_nomina === 2) {
                    nombreNomina = "Transportacion";
                    periodosTransportacion = data.data;
                } else if (id_nomina === 3) {
                    nombreNomina = "Confianza";
                    periodosConfianza = data.data;
                } else if (id_nomina === 5) {
                    nombreNomina = "Estructura";
                    periodosEstructura = data.data;
                }
                let fila = "<tr>";
                fila += "<td>" + nombreNomina + "</td>";
                fila += "<td>" + data.data.length + "</td>";
                for (let i = 0; i < 12; i++) {
                    fila += "<td>";
                    if (i < data.data.length) {
                        fila += data.data[i];
                    }
                    fila += "</td>";
                }
                fila += "</tr>";
                $("#tabla_periodos_nomina_varios_bimestre tbody").append(fila);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Bimestre: " + e.responseText, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Se valida que el usuario haya generado un listado de nóminas por el bimestre
function validaBimestre() {
    // Verificar si la tabla tiene contenido utilizando jQuery
    if ($('#tabla_periodos_nomina_varios_bimestre tbody').children().length > 0) {
        generarSdi();
    } else {
        toastr["warning"]("Debe seleccionar un bimestre", "Advertencia", {progressBar: true, closeButton: true});
    }
}
//Corre el proceso de cálculo para sdi en el backend
async function generarSdi() {
    let cmbBimestreId = parseInt($('#cmbBimestre').val());
    $('#btn_sdi').hide();
    $('#btn_carga_sdi').show();

    try {
        // Ejecutar las solicitudes secuencialmente
        for (let nominaId of nominas) {
            switch (nominaId) {
                case 1:
                    await calcularSdiNomina(nominaId, periodosVarios, cmbBimestreId);
                    break;
                case 2:
                    await calcularSdiNomina(nominaId, periodosTransportacion, cmbBimestreId);
                    break;
                case 3:
                    await calcularSdiNomina(nominaId, periodosConfianza, cmbBimestreId);
                    break;
                case 5:
                    await calcularSdiNomina(nominaId, periodosEstructura, cmbBimestreId);
                    break;
            }
        }
        // Una vez que todas las solicitudes se hayan completado, validar el bimestre
        await validarRegistrosExistentesBimestre(cmbBimestreId);
    } catch (error) {
        toastr["error"]("Ocurrió un error al procesar las nóminas", "Error", {progressBar: true, closeButton: true});
    } finally {
        // Al final, muestra el botón de nuevo y oculta el de carga
        $('#btn_carga_sdi').hide();
        $('#btn_sdi').show();
    }
}
//Aquí se llama al servicio que genera la url para cada nómina al servidor con promesas y asíncrono con la finalidad de devolver el progreso al usuario en tiempo real
async function calcularSdiNomina(idNomina, periodos, idBimestre) {
    const periodosConsiderados = periodos.map(periodo => `periodos_considerados=${periodo}`).join('&');
    const url = `/calculoSdi/calculoSdiNomina/${idNomina}/${idBimestre}?${periodosConsiderados}`;
    const nominaNombre = idNomina === 1 ? 'Varios' :
            idNomina === 2 ? 'Transportación' :
            idNomina === 3 ? 'Confianza' :
            idNomina === 5 ? 'Estructura' : 'Desconocida';
    // Mostrar la tabla antes de comenzar
    $('#nominasTable').show();
    // Añadir una fila a la tabla para la nómina si no existe
    let row = $(`#row-${idNomina}`);
    if (row.length === 0) {
        $('#nominasTable tbody').append(`
        <tr id="row-${idNomina}">
            <td style="height: 30px; padding: 5px; text-align: center;">${nominaNombre}</td>
            <td id="status-${idNomina}" style="height: 30px; padding: 5px; text-align: center;">
                <div class="spinner-border text-primary" role="status"></div>
            </td>
        </tr>
    `);
    }
    // Reiniciar la barra de progreso antes de comenzar
    $('#progressBar1').css('width', '0%').text('0%');

    return new Promise((resolve, reject) => {
        const eventSource = new EventSource(url);
        eventSource.onmessage = (event) => {
            const progreso = parseInt(event.data, 10);
            if (!isNaN(progreso)) {
                // Actualizar el progreso en la barra de progreso
                $('#progressBar1').css('width', progreso + '%').text(progreso + '%');
                if (progreso >= 100) {
                    // Asumimos que el progreso del 100% indica que el proceso está completo
                    toastr["success"](`Se procesaron correctamente todos los registros para la nómina de ${nominaNombre}`, "Éxito", {progressBar: true, closeButton: true});
                    $('#progressBar1').css('width', '100%').text('100%');
                    // Actualizar el estado de la nómina en la tabla
                    $(`#status-${idNomina}`).text('100%');
                    if (idNomina === 5) {
                        // Ocultar la barra de progreso si es la última nómina
                        $('#progressBar1').parent().hide();
                    }
                    eventSource.close();
                    resolve();
                }
            }
        };

        eventSource.onerror = (event) => {
            //console.error('Error en EventSource:', event);
            $('#nominasTable').hide(); //Se oculta la tabla de progreso para evitar confusión
            $('#progressBar1').css('width', '0%').text('0%'); // Reiniciar la barra en caso de error
            toastr["error"]("No se pudo procesar el S.D.I, verificar información en periodos de pago", "Error", {progressBar: true, closeButton: true});
            eventSource.close();
            reject();
        };

        eventSource.onopen = () => {
            //console.log("Conexión establecida para recibir el progreso del proceso.");
        };
    });
}

//Submit al generar excel
$("#importarPagoBimestralNomina").submit(function (e) {
    event.preventDefault();
    let idNomina = parseInt($("#cmbNomina").val());
    let url;
    if ($('#tabla_periodos_nomina_varios_bimestre tbody').children().length > 0) {
        switch (idNomina) {
            case 1:
                periodos = periodosVarios.map(periodo => `periodos=${periodo}`).join('&');
                url = "calculoSdi/exportarCalculosVarios?" + periodos;
                ajaxExcelSdiBimestral(url);
                break;
            case 2:
                periodos = periodosTransportacion.map(periodo => `periodos=${periodo}`).join('&');
                url = "calculoSdi/exportarCalculosObtenidosSdiTransportacion?" + periodos;
                ajaxExcelSdiBimestral(url);
                break;
            case 3:
                periodos = periodosConfianza.map(periodo => `periodos=${periodo}`).join('&');
                url = "calculoSdi/exportarCalculosObtenidosConfianza?" + periodos;
                ajaxExcelSdiBimestral(url);
                break;
            case 5:
                periodos = periodosEstructura.map(periodo => `periodos=${periodo}`).join('&');
                url = "calculoSdi/exportarCalculosObtenidosSdiEstructura?" + periodos;
                ajaxExcelSdiBimestral(url);
                break;
        }
    } else {
        toastr["warning"]("Debe seleccionar un bimestre", "Advertencia", {progressBar: true, closeButton: true});
    }
});

$("#deleteForm").submit(async function (event) {
    event.preventDefault(); // Evitar el envío normal del formulario
    let idBimestre = $('#cmbBimestre').val();
    let urlEliminaAcumulados = "calculoSdi/borraAcumuladoBimestre/" + idBimestre;
    let urlEliminaCalculos = "calculoSdi/borraCalculoBimestre/" + idBimestre;
    if (!idBimestre) {
        $("#confirmModal").modal('hide');
        toastr["warning"]("Debe seleccionar un bimestre a eliminar", "Advertencia", {
            progressBar: true,
            closeButton: true
        });
        return;
    }
    try {
        // Primera solicitud AJAX
        await $.ajax({
            type: "POST",
            url: urlEliminaAcumulados,
            contentType: "application/json"
        });
        toastr['success']("Se eliminó el acumulado del bimestre", "Aviso");
        // Segunda solicitud AJAX
        await $.ajax({
            type: "POST",
            url: urlEliminaCalculos,
            contentType: "application/json"
        });
        toastr['success']("Se eliminó el cálculo del bimestre", "Aviso");
        // Ocultar el modal después de completar ambas solicitudes
        $("#confirmModal").modal('hide');
        //Se llama a la función que valida el bimestre
        await validarRegistrosExistentesBimestre(idBimestre);
    } catch (e) {
        toastr["error"]("Error al realizar la operación: " + e, "Error", {
            progressBar: true,
            closeButton: true
        });
    }
});

let ajaxRequest = null;
//Genera el excel correspondiente para las nóminas y periodos seleccionados
function ajaxExcelSdiBimestral(url) {
    // Mostrar el botón de carga y ocultar el botón de generar Excel, con botón de carga
    $('#btn_GenerarExcel').hide();
    $('#btn_Cargando').show();
    ajaxRequest = $.ajax({
        url: url,
        type: "GET",
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, textStatus, xhr) {
            var filename = "";
            var disposition = xhr.getResponseHeader('Content-Disposition');
            if (disposition && disposition.indexOf('attachment') !== -1) {
                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                var matches = filenameRegex.exec(disposition);
                if (matches != null && matches[1])
                    filename = matches[1].replace(/['"]/g, '');
            }
            var url = window.URL.createObjectURL(data);
            var a = document.createElement('a');
            a.href = url;
            a.download = filename || 'sdiBimestre.xlsx';
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
            toastr["success"]("Se generó correctamente el archivo Excel", "Aviso", {progressBar: true, closeButton: true});
        },
        error: function (xhr, status, error) {
            toastr["error"]("Ocurrió un error " + error, "Error", {progressBar: true, closeButton: true});
        },
        complete: function () {
            // Ocultar el botón de carga y mostrar el botón de generar Excel
            $('#btn_Cargando').hide();
            $('#btn_GenerarExcel').show();
        }
    });
}

// Manejar el cierre del modal para cancelar la solicitud AJAX de importación
$('#importarPagoBimestralNomina').on('hidden.bs.modal', function () {
    if (ajaxRequest) {
        ajaxRequest.abort();
        $('#btn_Cargando').hide();
        $('#btn_GenerarExcel').show();
    }
});

//Consulta en la tabla tmp_sdi_acumulados_bimestre si ya existen registros con el id del bimestre, con un solo registro que exista se marca como true
function existeAcumuladoBimestre(idBimestre) {
    let responseData;
    $.ajax({
        type: "GET",
        url: "calculoSdi/existeBimestreAcumulado/" + idBimestre,
        dataType: 'json',
        async: false,
        success: function (data) {
            responseData = data;
        },
        error: function (e) {
            toastr["warning"]("Error al consultar bimestres acumulados: " + e.responseText, "Error", {progressBar: true, closeButton: true});
        }
    });
    return responseData;
}

////Consulta en la tabla tmp_sdi_calculos_bimestre si ya existen registros con el id del bimestre, con un solo registro que exista se marca como true
//function existeCalculoBimestre(idBimestre) {
//    let responseData;
//    $.ajax({
//        type: "GET",
//        url: "calculoSdi/existeBimestreCalculado/" + idBimestre,
//        dataType: 'json',
//        async: false,
//        success: function (data) {
//            responseData = data;
//        },
//        error: function (e) {
//            toastr["warning"]("Error al consultar bimestres calculados: " + e.responseText, "Error", {progressBar: true, closeButton: true});
//        }
//    });
//    return responseData;
//}

//Consulta en la tabla tmp_sdi_calculos_bimestre si ya existen registros con el id del bimestre y trabajador, con un solo registro que exista se marca como true
function existeCalculoBimestreTrabajador(idTrabajador, idBimestre) {
    let responseData;
    $.ajax({
        type: "GET",
        url: "calculoSdi/existeBimestreCalculadoTrabajador/" + idTrabajador + "/" + idBimestre,
        dataType: 'json',
        async: false,
        success: function (data) {
            responseData = data;
        },
        error: function (e) {
            toastr["warning"]("Error al consultar bimestres calculados trabajador: " + e.responseText, "Error", {progressBar: true, closeButton: true});
        }
    });
    return responseData;
}

//Genera el txt que es cargado al SUA, su proceso de generación se encuentra en el backend
$("#importarTxtSua").submit(function (e) {
    event.preventDefault();
    let idNomina = $('#cmbNominaTxtSua').val();
    let idBimestre = $('#cmbBimestre').val();
    if (!idBimestre) {
        toastr["warning"]("Debe seleccionar un bimestre", "Advertencia", {progressBar: true, closeButton: true});
        return;
    }
    $.ajax({
        url: 'calculoSdi/txtSuaModifSalarioBimestre/' + idBimestre + '/' + idNomina,
        type: "GET",
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, textStatus, xhr) {
            var filename = "";
            var disposition = xhr.getResponseHeader('Content-Disposition');
            if (disposition && disposition.indexOf('attachment') !== -1) {
                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                var matches = filenameRegex.exec(disposition);
                if (matches != null && matches[1])
                    filename = matches[1].replace(/['"]/g, '');
            }
            var url = window.URL.createObjectURL(data);
            var a = document.createElement('a');
            a.href = url;
            //Se obtiene el nombre que se genera en el servidor o se le coloca el nombre por defecto
            a.download = filename || 'sdiSua.txt';
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
            toastr["success"]("Se generó correctamente el archivo txt", "Aviso", {progressBar: true, closeButton: true});
        },
        error: function (xhr, status, error) {
            toastr["error"]("Ocurrió un error " + error, "Error", {progressBar: true, closeButton: true});
        }
    });
});

//Genera el txt que es cargado al idse, su proceso de generación se encuentra en el backend
$("#importarTxtIdse").submit(function (e) {
    event.preventDefault();
    let idNomina = $('#cmbNominaTxtIdse').val();
    let idBimestre = $('#cmbBimestre').val();
    let idMovimiento = $('#cmbTipoMovIdse').val();
    if (!idBimestre) {
        toastr["warning"]("Debe seleccionar un bimestre", "Advertencia", {progressBar: true, closeButton: true});
        return;
    }
    $.ajax({
        url: 'calculoSdi/txtIdseModifSalarioBimestre/' + idBimestre + '/' + idNomina + '/' + idMovimiento,
        type: "GET",
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, textStatus, xhr) {
            var filename = "";
            var disposition = xhr.getResponseHeader('Content-Disposition');
            if (disposition && disposition.indexOf('attachment') !== -1) {
                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                var matches = filenameRegex.exec(disposition);
                if (matches != null && matches[1])
                    filename = matches[1].replace(/['"]/g, '');
            }
            var url = window.URL.createObjectURL(data);
            var a = document.createElement('a');
            a.href = url;
            //Se obtiene el nombre que se genera en el servidor o se le coloca el nombre por defecto
            a.download = filename || 'sdiIDSE.txt';
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
            toastr["success"]("Se generó correctamente el archivo txt", "Aviso", {progressBar: true, closeButton: true});
        },
        error: function (xhr, status, error) {
            toastr["error"]("Ocurrió un error " + error, "Error", {progressBar: true, closeButton: true});
        }
    });
});

//Submit al generar sdi a trabajador de nuevo ingreso
$("#sdiNuevoIngreso").submit(function (e) {
    e.preventDefault();
    let idTrabajador = $('#idTrabajador').val();
    let idBimestre = $('#cmbBimestreAltaTrabajador').val();
    if (!idTrabajador) {
        toastr.warning('Debe colocar el expediente correcto del trabajador', 'Advertencia');
        return;
    }
    //Se valida que no se haya registrado anteriormente el cálculo del trabajador en el bimestre seleccionado
    validarRegistrosExistentesTrabajadorBimestre(idTrabajador, idBimestre);
});

//Valida que no se haya corrido el proceso de salario fijo para el trabajador y bimestre anteriormente
function validarRegistrosExistentesTrabajadorBimestre(idTrabajador, id_bimestre) {
    //Se valida que no exista el bimestre y trabajador a calcuar
    let existeCalculoBimestralTrabajador = existeCalculoBimestreTrabajador(idTrabajador, id_bimestre);
    if (existeCalculoBimestralTrabajador) {
        toastr["error"]("Ya se generó el cálculo de S.D.I. para el trabajador y bimestre seleccionado", "Error", {progressBar: true, closeButton: true});
        $("#sdiNuevoIngreso").modal('hide');
    } else {
        //Se llama al servicio que genera el cálculo y guardado en la tabla tmp_sdi_calculos_bimestre
        ajaxGenerarSdiTrabajadorNuevo(idTrabajador, id_bimestre);
    }
}

//Calcula y guarda el salario fijo para el nuevo trabajador en el backend
function ajaxGenerarSdiTrabajadorNuevo(idTrabajador, idBimestre) {
    $.ajax({
        type: "GET",
        url: 'calculoSdi/calculaSdiNuevoIngreso/' + idTrabajador + '/' + idBimestre,
        success: function (data) {
            toastr["success"]("Se registró el cálculo fijo del trabajador como S.D.I.", "Aviso", {progressBar: true, closeButton: true});
            $("#sdiNuevoIngreso").modal('hide');
        },
        error: function (error) {
            toastr["error"]("Ocurrió un error " + error, "Error", {progressBar: true, closeButton: true});
        }
    });
}

//Limpia las validaciones de todos los modales de la página al ocultarse
$('.modal').on('hidden.bs.modal', function () {
    $(this).find('form').each(function () {
        this.classList.remove('was-validated');
        this.reset();
    });
});