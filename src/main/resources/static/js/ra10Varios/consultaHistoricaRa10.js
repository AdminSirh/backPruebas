let idTrabajador;
//******************************************CARGA DEL DOM**********************************
document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    idTrabajador = urlParams.get('id_trabajador');
    listarRegistrosTrabajador(idTrabajador);
    obtenerInformacionTrabajador(idTrabajador, '#campo_expediente', '#campo_nombre');
    obtenerInformacionPuestoTrabajador(idTrabajador);
    cargaPeriodoAplicacion();
});

$('#fechaInicialSuplencia, #fechaFinalSuplencia').change(function () {
    var fechaInicial = $('#fechaInicialSuplencia').val();
    var fechaFinal = $('#fechaFinalSuplencia').val();
    //Se manda a llamar a la creación de la tabla
    listarRegistrosTrabajador(idTrabajador, fechaInicial, fechaFinal);
});

$("input[type='checkbox']").on("change", function () {
    contarChecksSeleccionados();
});
//Se obtiene la información general del trabajador
function obtenerInformacionTrabajador(id_trabajador, campoExpdte, campoNom) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            $(campoExpdte).val(data.data.cat_expediente.numero_expediente);
            $(campoNom).val(data.data.persona.nombre + " " + data.data.persona.apellido_paterno + " " + data.data.persona.apellido_materno);
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener información del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Se obtiene la información de los puesto que se estarán supliendo
function obtenerInformacionPuestoSuplencia(id_puesto) {
    $.ajax({
        type: "GET",
        url: "catalogos/encontrarPuesto/" + id_puesto,
        dataType: 'json',
        success: function (dataSupl) {
            $('#campo_puesto_trabajador_supl').val(dataSupl.puesto);
            $('#nivel_supl').val(dataSupl.nivel);
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener datos del puesto del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Se obtiene la información del puesto actual del trabajador
function obtenerInformacionPuestoTrabajador(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "plaza/encontrarSueldo/" + id_trabajador,
        dataType: 'json',
        success: function (dataPuestoTrabajador) {
            let calculoSueldoHora8Trabajador = dataPuestoTrabajador.sueldoHora8;
            let calculoSueldoHora7Trabajador = dataPuestoTrabajador.sueldoHora7;
            calculoSueldoHora8Trabajador = calculoSueldoHora8Trabajador.toFixed(2);
            calculoSueldoHora7Trabajador = calculoSueldoHora7Trabajador.toFixed(2);
            $('#puestoActual8Hrs').val(calculoSueldoHora8Trabajador);
            $('#puestoActual7Hrs').val(calculoSueldoHora7Trabajador);
            $('#campo_puesto_trabajador').val(dataPuestoTrabajador.puesto);
            $('#nivel').val(dataPuestoTrabajador.nivel);
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener datos del puesto del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Coloca en el select los periodos de aplicación
function cargaPeriodoAplicacion() {
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
                    $('#periodo_aplicacion').empty().append(`<option value="${periodo.periodo}" ${vselected}>${opcion}</option>`);
                    periodoSeleccionado = periodo.periodo;
                    fechaInicialPeriodoSeleccionado = periodo.fecha_inicial;
                    fechaFinalPeriodoSeleccionado = periodo.fecha_final;
                    break;
                } else if (hoy > fechaCorte) {
                    const siguientePeriodo = data[data.indexOf(periodo) + 1];
                    if (siguientePeriodo) {
                        const opcion = crearOpcion(siguientePeriodo.periodo, fechaInicial, fechaFinal);
                        $('#periodo_aplicacion').empty().append(`<option value="${siguientePeriodo.periodo}" ${vselected}>${opcion}</option>`);
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
                        $('#periodo_aplicacion').append(`<option value="${periodo.periodo}" ${vselected}>${opcion}</option>`);
                    }
                });
            }
        },
        error: function (e) {
            toastr["error"](e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Formatea el select del periodo de aplicación en el formato deseado
function crearOpcion(periodo, fechaInicial, fechaFinal) {
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
}
//Para calcular los totales a pagar, falta obtener la diferencia a pagar, depndiendo del sueldo del trabajador y del sueldo de la persona que cubre
function contarChecksSeleccionados() {
    const booleanOperativo = $('#booleanOperativo').val();
    const booleanAdministrativo = $('#booleanAdministrativo').val();
    const totalChecks = $("input[type='checkbox']:checked").length;
    let sueldoActual8Hrs = $('#puestoActual8Hrs').val();
    let sueldoActual7Hrs = $('#puestoActual7Hrs').val();
    let sueldoSuplencia, diferenciaPagar, totalaPagar;
    //Los operativos se les pagan 8 horas
    if (booleanOperativo) {
        sueldoSuplencia = parseFloat($('#puestoSuplencia8Hrs').val());
        diferenciaPagar = sueldoSuplencia - sueldoActual8Hrs;
        //Los administrativos se les paga 7 horas
    } else if (booleanAdministrativo) {
        sueldoSuplencia = parseFloat($('#puestoSuplencia7Hrs').val());
        diferenciaPagar = sueldoSuplencia - sueldoActual7Hrs;
    } else {
        toastr["warning"]("No se pudo determinar si el trabajador es operativo o administrativo", "Advertencia", {progressBar: true, closeButton: true});
        return; // Salir de la función si no se puede determinar el tipo de trabajador
    }
    //Validar que no sea un número negativo
    if (diferenciaPagar < 0) {
        toastr["error"]("La diferencia a pagar es negativa", "Error", {progressBar: true, closeButton: true});
        return;
    }
    if (!isNaN(diferenciaPagar)) {
        totalaPagar = (diferenciaPagar * totalChecks).toFixed(2);
        $("#celdaTotal").text("$" + totalaPagar);
    } else {
        toastr["error"]("El sueldo de suplencia no es un número válido", "Error", {progressBar: true, closeButton: true});
    }
}
//Obtiene el histórico de las capturas de ra10 generadas para el trabajador
function listarRegistrosTrabajador(id_trabajador) {
    let url;
    url = 'ra10/capturaSemanal?trabajador_id=' + id_trabajador;
    let fechaInicial = $('#fechaInicialSuplencia').val();
    let fechaFinal = $('#fechaFinalSuplencia').val();

    if (fechaInicial) {
        url = 'ra10/capturaSemanal?fechaInicial=' + fechaInicial + '&trabajador_id=' + id_trabajador;
    }
    if (fechaFinal) {
        url = 'ra10/capturaSemanal?fechaFinal=' + fechaFinal + '&trabajador_id=' + id_trabajador;
    }
    if (fechaInicial && fechaFinal) {
        url = 'ra10/capturaSemanal?fechaFinal=' + fechaFinal + '&fechaInicial=' + fechaInicial + '&trabajador_id=' + id_trabajador;
    }
    //Se destruye la instancia de la tabla si ya fue creada
    if ($.fn.DataTable.isDataTable('#tabla_historico_capturas_ra10')) {
        $('#tabla_historico_capturas_ra10').DataTable().destroy();
    }
    $('#tabla_historico_capturas_ra10').dataTable({
        "ajax": {
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
            {data: "fecha_inicial"},
            {data: "fecha_final"},
            {data: "periodo_generacion"},
            {data: "periodo_aplicacion"},
            {data: "comentario"},
            {data: "total_semana"},
            {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarCapturaModal" onclick="editarCaptura(' + r.id_ra_10 + ') ;cargaDatosCaptura(' + r.id_ra_10 + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                    return he;
                }
            },
            {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                    var he;
                    if (r.estatus === 1) {
                        he = '<button type="button" id="btndistrict"' + r.id_ra_10 + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusCaptura(' + r.id_ra_10 + ',' + r.estatus + ');"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                    } else {
                        he = '<button type="button" id="btndistrict"' + r.id_ra_10 + ' class="btn btn-outline-danger" onclick="estatusCaptura(' + r.id_ra_10 + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                    }
                    return he;
                }
            }
        ]
    });
}
//Retorna a la generación de ra10 para el trabajador actual
function regresarTrabajador() {
    window.location.href = 'guardaMovimientoProvisional?id_trabajador=' + idTrabajador;
}
//Edita la captura de ra10, se le asignan los días, comentarios u otros campos
function editarCaptura(idRa10) {
    $("#modificarCapturaModal").off('submit').submit(function (event) {
        event.preventDefault();
        let periodoApl = $('#periodo_aplicacion').val();
        let comentario = $('#campo_motivo').val();
        let miercoles = $('#registroDia1').prop('checked');
        let jueves = $('#registroDia2').prop('checked');
        let viernes = $('#registroDia3').prop('checked');
        let sabado = $('#registroDia4').prop('checked');
        let domingo = $('#registroDia5').prop('checked');
        let lunes = $('#registroDia6').prop('checked');
        let martes = $('#registroDia7').prop('checked');
        let textoCeldaTotal = $('#celdaTotal').text();
        let valorNumericoTotal = parseFloat(textoCeldaTotal.replace('$', ''));
        var datos = {"periodo_aplicacion": periodoApl,
            "comentario": comentario,
            "miercoles": miercoles,
            "jueves": jueves,
            "viernes": viernes,
            "sabado": sabado,
            "domingo": domingo,
            "lunes": lunes,
            "martes": martes,
            "total_semana": valorNumericoTotal};
        if (idRa10 !== null) {
            $.ajax({
                type: "POST",
                url: "ra10/actualizarRa10/" + idRa10,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(datos),
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información para modificar", "Aviso", {progressBar: true, closeButton: true});
                    }
                    $("#modificarCapturaModal").modal('hide');
                    $('#tabla_historico_capturas_ra10').DataTable().ajax.reload();
                    toastr['success']("Se modificó con éxito", "Éxito");
                    //Reset de Variable para evitar duplicados al editar de manera continua.
                    idRa10 = null;
                },
                error: function (e) {
                    toastr["warning"]("Error al modificar los datos : " + e, " Error", {progressBar: true, closeButton: true});

                }
            });
        }
    });
}
//Cam,bia el estatus de la captura, dependiendo del estatus actual se muesta un modal de confirmación de activación o desactivación
function estatusCaptura(id_ra10, estatus) {
    if (estatus === 1) {
        $("#modalDesactiva").modal("toggle");
        desactivaCaptura(id_ra10, 0);
    } else if (estatus === 0) {
        $("#modalActiva").modal("toggle");
        activaCaptura(id_ra10, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}
//Cambia el estatus de la captura a cero
function desactivaCaptura(id_ra10, estatus) {
    $('#modalDesactiva').on('hidden.bs.modal', function () {
        id_ra10 = null;
    });
    $("#bajaCaptura").click(function (e) {
        ajaxEstatus(id_ra10, estatus, "#modalDesactiva");
    });
}
//Cambia el estatus de la captura a uno
function activaCaptura(id_ra10, estatus) {
    $('#modalActiva').on('hidden.bs.modal', function () {
        id_ra10 = null;
    });
    $("#activaCaptura").click(function (e) {
        ajaxEstatus(id_ra10, estatus, "#modalActiva");
    });
}
//Llama al servicio que modifica el valor del estatus
function ajaxEstatus(id, estatus, modal) {
    if (id !== null) {
        $.ajax({
            type: "POST",
            url: "ra10/cambioEstatusCapturaRA10/" + id + "/" + estatus,
            //data: "id_banco=" + id_banco, "estatus": estatus,
            success: function (data) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    $(modal).modal('hide');
                    toastr['warning']("Se actualizó el estatus", "Aviso");
                    $('#tabla_historico_capturas_ra10').DataTable().ajax.reload();
                    id = null;
                }
            }
        });
    }
}
//Obttiene la información de la captura semanal para mostrarla en el modal con la información guardada
function cargaDatosCaptura(idRa10) {
    $.ajax({
        type: "GET",
        url: "ra10/buscarCapturaSemanal/" + idRa10,
        dataType: 'json',
        success: function (data) {
            $('#periodo_aplicacion').val(data.data.periodo_aplicacion);
            $('#campo_motivo').val(data.data.comentario);
            $('#celdaNivel').text(data.data.cat_Puesto.nivel);
            $('#celdaPuesto').text(data.data.cat_Puesto.puesto);
            $('#registroDia1').prop('checked', data.data.miercoles ? true : false);
            $('#registroDia2').prop('checked', data.data.jueves ? true : false);
            $('#registroDia3').prop('checked', data.data.viernes ? true : false);
            $('#registroDia4').prop('checked', data.data.sabado ? true : false);
            $('#registroDia5').prop('checked', data.data.domingo ? true : false);
            $('#registroDia6').prop('checked', data.data.lunes ? true : false);
            $('#registroDia7').prop('checked', data.data.martes ? true : false);
            $('#celdaTotal').text(data.data.total_semana !== null ? "$" + data.data.total_semana : "");
            //Datos hidden para cálculos de pagos
            $('#puestoSuplencia8Hrs').val(data.data.cat_Puesto.sueldo_hora.toFixed(2));
            $('#puestoSuplencia7Hrs').val(data.data.cat_Puesto.sueldoHora7.toFixed(2));
            $('#booleanOperativo').val(data.data.operativo);
            $('#booleanAdministrativo').val(data.data.administrativo);
            //Se muestra si el trabajador es operativo administrativo deppendiendo de su área
            if (data.data.operativo) {
                $('#tipo').val('Operativo');
            } else if (data.data.administrativo) {
                $('#tipo').val('Administrativo');
            } else {
                $('#tipo').val('Error');
            }
            //Si la suplencia fue generada con un trabajador y no con un puesto, se comprueba su valor antes de hacer la búsqueda
            if (data.data.trabajador_ausente_id !== null || '') {
                obtenerInformacionTrabajador(data.data.trabajador_ausente_id, '#campo_expediente_supl', '#campo_nombre_supl');
            } else {
                $('#campo_expediente_supl').val('N/A');
                $('#campo_nombre_supl').val('N/A');
            }
            obtenerInformacionPuestoSuplencia(data.data.cat_Puesto.id_puesto);
            //Se buscan las fechas del periodo para obtener las incidencias correspondientes
            buscarFechasPeriodoGeneracion(data.data.periodo_generacion);
        },
        error: function (e) {
            alert(e);
        }
    });
}
//Coloca las fechas de inicio y fin del periodo en el encabezado del modal 
function buscarFechasPeriodoGeneracion(periodo) {
    let fechaInicialPeriodoSeleccionado;
    let fechaFinalPeriodoSeleccionado;
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPeriodoPago/" + periodo + "/1",
        dataType: 'json',
        success: function (dataPeriodo) {
            //Se asigna el valor al encabzado del modal
            $(".modal-title#titulo_edicion").append("Semana del Periodo de Generación: "
                    + "<strong>" + periodo + "</strong> "
                    + "S. <strong>" + convertirFecha(dataPeriodo.data.fecha_inicial) + "</strong> al <strong>"
                    + convertirFecha(dataPeriodo.data.fecha_final) + "</strong>");

            fechaInicialPeriodoSeleccionado = dataPeriodo.data.fecha_inicial;
            fechaFinalPeriodoSeleccionado = dataPeriodo.data.fecha_final;
            buscarIncidenciasKardexPeriodo(fechaInicialPeriodoSeleccionado, fechaFinalPeriodoSeleccionado);
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener datos del puesto del trabajador" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Obtiene las incidencias del kardex para la fecha de inicio y fin especificadas
function buscarIncidenciasKardexPeriodo(fechaInicio, fechaFin) {
    limpiarIncidenciasPeriodo();
    $.ajax({
        type: "GET",
        url: "incidencias/buscarIncidenciasTrabajadorTransportacion/" + fechaInicio + "/" + fechaFin + "/" + idTrabajador,
        dataType: 'json',
        success: function (dataIncidenciasKardexPeriodo) {
            if ($.isEmptyObject(dataIncidenciasKardexPeriodo)) {
                toastr["info"]("No hay inasistencias en el periodo actual", "Información", {progressBar: true, closeButton: true});
            } else {
                toastr["info"]("Existen inasistencias en el periodo actual", "Información", {progressBar: true, closeButton: true});
                colocarIncidenciasKardexSemana(dataIncidenciasKardexPeriodo, fechaInicio, fechaFin);
            }
        },
        error: function (e) {
            toastr["error"]("Ocurrió un error al obtener incidencias del kardex en el periodo especificado" + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Coloca las iniciales de las incidencias en los campos correspondientes al día de la semana de la nómina de varios
function colocarIncidenciasKardexSemana(data, fechaInicioSinFormato, fechaFinSinFormato) {
    const inicioSemana = convertirFecha(fechaInicioSinFormato);
    const finSemana = convertirFecha(fechaFinSinFormato);
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
                        $(`#incidencia_dia1`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                        break;
                    case rangoDias[1]:
                        $(`#incidencia_dia2`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                        break;
                    case rangoDias[2]:
                        $(`#incidencia_dia3`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                        break;
                    case rangoDias[3]:
                        $(`#incidencia_dia4`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                        break;
                    case rangoDias[4]:
                        $(`#incidencia_dia5`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                        break;
                    case rangoDias[5]:
                        $(`#incidencia_dia6`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                        break;
                    case rangoDias[6]:
                        $(`#incidencia_dia7`).css('font-weight', 'bold').val(data[i].inicial_descripcion);

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
                    $(`#incidencia_dia1`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                    break;
                case rangoDias[1]:
                    $(`#incidencia_dia2`).css('font-weight', 'bold').val(data[i].inicial_descripcion);

                    break;
                case rangoDias[2]:
                    $(`#incidencia_dia3`).css('font-weight', 'bold').val(data[i].inicial_descripcion);

                    break;
                case rangoDias[3]:
                    $(`#incidencia_dia4`).css('font-weight', 'bold').val(data[i].inicial_descripcion);

                    break;
                case rangoDias[4]:
                    $(`#incidencia_dia5`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                    break;
                case rangoDias[5]:
                    $(`#incidencia_dia6`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                    break;
                case rangoDias[6]:
                    $(`#incidencia_dia7`).css('font-weight', 'bold').val(data[i].inicial_descripcion);
                    break;
                default:
                    break;
            }
        }
    }
}
// Calcular la diferencia en días entre dos fechas
function obtenerDiferenciaDias(fechaInicio, fechaFin) {
    let diferenciaEnTiempo = fechaFin - fechaInicio;
    let diferenciaEnDias = Math.floor(diferenciaEnTiempo / (1000 * 3600 * 24));
    return diferenciaEnDias;
}
//Genera una fecha en formato año mes dia
function convertirFecha(fechaISO) {
    const fecha = new Date(fechaISO); // Crear objeto Date a partir de la fecha ISO
    const dia = String(fecha.getUTCDate()).padStart(2, '0'); // Obtener día y formatear
    const mes = String(fecha.getUTCMonth() + 1).padStart(2, '0'); // Obtener mes y formatear
    const anio = fecha.getUTCFullYear(); // Obtener año
    return `${anio}-${mes}-${dia}`; // Formatear fecha
}
//Borra de los campos las incidencias del kárdex recuperadas
function limpiarIncidenciasPeriodo() {
    for (let i = 1; i <= 7; i++) {
        $(`#incidencia_dia${i}`).val('');
    }
}
//Evento de escucha al cierre del modal
$('#modificarCapturaModal').on('hidden.bs.modal', function (e) {
    $(".modal-title#titulo_edicion").empty();
    $('.needs-validation').removeClass("was-validated");
    $('#formCapturaSemanal').trigger('reset');
    $("#suplenciaForm")[0].reset();
    idRa10 = null;
});