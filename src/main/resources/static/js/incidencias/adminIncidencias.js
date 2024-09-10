/* global moment */
/* global data, NaN, Document, */
/* Cargar DOM antes de JS */
document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    verificaHorario(trabajador_id);
    buscarContrato(trabajador_id);
    rellenaCampos(trabajador_id);
    var idIncidencia = urlParams.get('idIncidencia');
    //Se envía a la edición de la incidencia en específico relacionada con ese trabajador si se recibe el id_incidencia desde la url (Cuando se da clic desde el kárdex)
    if (idIncidencia) {
        //Elimina el parámetro id_incidencia después de mostrar el modal
        const state = {};
        const title = '';
        const newUrl = window.location.pathname + '?id_trabajador=' + trabajador_id;
        history.replaceState(state, title, newUrl);

        $('#modificarPensionAlimenticia').modal('show');
        setTimeout(function () {
            // Espera para cargar el combo
            verIncidencia(idIncidencia);
        }, 100);
    }
});

function rellenaCampos(trabajador_id) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + trabajador_id,
        dataType: 'json',
        success: function (data) {
            $('#campo_expediente').val(data.data.cat_expediente.numero_expediente);
            $('#campo_nombre').val(data.data.persona.nombre + " " + data.data.persona.apellido_paterno + " " + data.data.persona.apellido_materno);
        },
        error: function (e) {
            toastr["error"]("Error al recuperar los datos del trabajador : " + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}

function trabajadorIncidencias(trabajador_id) {

    tabla_Incidencias = $('#tabla_Incidencias').DataTable({
        "ajax": {
            url: "incidencias/buscarIncidencias/" + trabajador_id,
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
            {data: "cat_incidencias.descripcion",  width: "100px"},
            {data: "fecha_inicio", width: "80px"},
            {data: "fecha_fin", width: "80px"},
            {data: "folio", width: "70px"},
            {data: "", sTitle: "CORRIDA", width: "100px", visible: true, render: function (d, t, r) {
                    var he;
                    if (r.estado_incidencia_id === 1) {
                        if (r.cat_incidencias.id_incidencia === 7) {
                            he = '<button type="button" id="btndistrict"' + r.id_incidencia + '  class="btn btn-outline-success"class="btn btn-outline-info" onclick="asignarCorrida(' + r.id_incidencia + ')"> ' + '<span class="fa fa-plus-circle"></span>Asignar</button>';
                        }else {
                            he = '<button type="button" disabled="true" id="btndistrict"' + r.id_incidencia + '  class="btn btn-outline-success"class="btn btn-outline-info"> ' + '<span class="fa fa-plus-circle"></span>Asignar</button>';
                        }
                    } else {
                        he = '<button type="button" disabled="true" id="btndistrict"' + r.id_incidencia + '  class="btn btn-outline-success"class="btn btn-outline-info"> ' + '<span class="fa fa-plus-circle"></span>Asignar</button>';
                    }
                    return he;
                }
            },
            {data: "", sTitle: "ESTATUS", width: "100px", visible: true, render: function (d, t, r) {
                    var he;
                    if (r.estado_incidencia_id === 1) {
                        if (r.cat_incidencias.id_incidencia === 7) {
                            he = '<button type="button" id="btndistrict"' + r.id_incidencia + '  class="btn btn-outline-danger"class="btn btn-outline-info"onclick="verCancelacion(' + r.id_incidencia + ')"> ' + '<span class="fa fa-minus-circle"></span>Rechazar</button>';
                        } else if([27, 14, 31, 15, 16, 17, 22].includes(r.cat_incidencias.id_incidencia)){
                             he = '<button type="button"  disabled = "true"' + r.id_incidencia + '  class="btn btn-outline-danger"class="btn btn-outline-info"onclick="estadoIncidencia(' + r.id_incidencia + ',' + r.estado_incidencia_id + ')"> ' + '<span class="fa fa-minus-circle"></span>Rechazar</button>';
                        }else {
                            he = '<button type="button"  id="btndistrict"' + r.id_incidencia + '  class="btn btn-outline-danger"class="btn btn-outline-info"onclick="estadoIncidencia(' + r.id_incidencia + ',' + r.estado_incidencia_id + ')"> ' + '<span class="fa fa-minus-circle"></span>Rechazar</button>';
                        }
                    } else {
                        if (r.cat_incidencias.id_incidencia === 7) {
                            he = '<button type="button" disabled="true" ' + r.id_incidencia + ' class="btn btn-outline-secondary" onclick="estadoIncidencia(' + r.id_incidencia + ',' + r.estado_incidencia_id + ')"> ' + '<span class="fa fa-times-circle"></span>Cancelada</button>';
                        } else if ([27, 14, 31, 15, 16, 17, 22].includes(r.cat_incidencias.id_incidencia)) {
                            he = '<button type="button" disabled="true"' + r.id_incidencia + ' class="btn btn-outline-success" onclick="estadoIncidencia(' + r.id_incidencia + ',' + r.estado_incidencia_id + ')"> ' + '<span class="fa fa-minus-circle"></span>Autorizar</button>';
                        }else {
                            he = '<button type="button" id="btndistrict"' + r.id_incidencia + ' class="btn btn-outline-success" onclick="estadoIncidencia(' + r.id_incidencia + ',' + r.estado_incidencia_id + ')"> ' + '<span class="fa fa-minus-circle"></span>Autorizar</button>';
                        }
                    }
                    return he;
                }
            },
            {data: "", sTitle: "EDITAR", width: 80, visible: true, render: function (d, t, r) {

                    var he;
                    //Condicional para edición de vacaciones y ayudas
                    if (r.cat_incidencias.id_incidencia !== 7 && !(r.cat_incidencias.id_incidencia === 4 && r.estado_incidencia_id === 1)) {

                        he = '<button type="button" id = "btnEdita" class="btn btn-outline-primary" onclick="verIncidencia(' + r.id_incidencia + ')"><span class="fa fa-edit"></span>Editar</button>';

                    } else {

                        he = '<button type="button" id = "btnEdita" disabled="true" class="btn btn-outline-primary" onclick="verIncidencia(' + r.id_incidencia + ')"><span class="fa fa-edit"></span>Editar</button>';
                    }

                    return he;
                }
            },
            {data: "", sTitle: "ELIMINAR", width: 100, visible: true, render: function (d, t, r) {
                    var hi;
                    hi = '<button type="button" class="btn btn-outline-danger" onclick="eliminarIncidencia(' + r.id_incidencia + ', \'' + r.folio + '\',  ' + r.cat_incidencias.id_incidencia + ')"><span class="fa fa-edit"></span>Eliminar</button>';
                    //Se elimina desde la pestaña de incapacidades
                    if ([27, 14, 31, 15, 16, 17].includes(r.cat_incidencias.id_incidencia)) {
                        hi = '<button type="button" disabled = "true" class="btn btn-outline-danger" onclick="eliminarIncidencia(' + r.id_incidencia + ', \'' + r.folio + '\',  ' + r.cat_incidencias.id_incidencia + ')"><span class="fa fa-edit"></span>Eliminar</button>';

                    }
                    return hi;
                }
            },
            {data: "", sTitle: "VISTA", width: 70, visible: true, render: function (d, t, r) {
                    var hi;
                    if (r.estado_incidencia_id === 1) {
                        if (r.cat_incidencias.id_incidencia === 7) {
                            hi = '<button type="button" class="btn btn-primary" onclick="verFormatoIncidencia(' + r.id_incidencia + ')"><span class="fa fa-search"></span> Ver</button>';
                        } else {
                            hi = '';
                        }
                    } else if (r.cat_incidencias.id_incidencia === 7) {
                        // Si el estado de incidencia no es 1 pero la incidencia es 7, el botón estará deshabilitado.
                        hi = '<button type="button" disabled="true" class="btn btn-primary" onclick="verFormatoIncidencia(' + r.id_incidencia + ')"><span class="fa fa-search"></span> Ver</button>';
                    } else {
                        // Si el estado de incidencia no es 1 y la incidencia no es 7, no se mostrará ningún botón.
                        hi = '';
                    }
                    return hi;
                }

            },
            {data: "", sTitle: "DESCARGA", width: 100, visible: true, render: function (d, t, r) {
                    var hi;
                    if (r.estado_incidencia_id === 1) {
                        if (r.cat_incidencias.id_incidencia === 7) {
                            hi = '<button type="button" class="btn btn-primary btn-sm" onclick="generarFormatoIncidencia(' + r.id_incidencia + ')"><span class="fa fa-download"></span> Descargar</button>';
                        } else {
                            hi = '';
                        }
                    } else if (r.cat_incidencias.id_incidencia === 7) {
                        // Si el estado de incidencia no es 1 pero la incidencia es 7, el botón estará deshabilitado.
                        hi = '<button type="button" disabled="true" class="btn btn-primary btn-sm" onclick="generarFormatoIncidencia(' + r.id_incidencia + ')"><span class="fa fa-download"></span> Descargar</button>';
                    } else {
                        // Si el estado de incidencia no es 1 y la incidencia no es 7, no se mostrará ningún botón.
                        hi = '';
                    }
                    return hi;
                }

            }]
    });
}

//Limpieza de agrega
$('#agregarIncidenciaModal').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    $("#cmbInc").val("");
    $("#fecha_inicio").val("");
    $("#fecha_fin").val("");
    $("#folio").val("");
    $("#bis").val("");
    $("#num_dias").val("");
    $('#expediente_id').val("");
    $('#dias_paseos').val("");
    $('#dias_vacaciones').val("");
    $('#periodo_vacacional').val("");
    $('#dias_totales').val("");
    $('#dias_disponibles').val("");
    $('#fecha_inicio_anio_periodo_vacacional').val("");
    $('#fecha_fin_anio_periodo_vacacional').val("");
    $('#dias_proporcionales').val("");
    $('#observaciones').val("");
    $('#periodo_aplicacion').val("");
    $('#genera_pago').val("");
    $('#fecha_recepcion').val("");
    $('#fecha_emision').val("");
    $('#parentesco').val("");
    $('#nombre').val("");
    $('#apellido_paterno').val("");
    $('#apellido_materno').val("");
    $('#origen').val("");
    $('#periodo_pago').val("");
    $('#monto').val("");
    //Se limpia el detalle de las ayudas su fue mostrado anteriormente
    $('#detalleAyudas').html('');
});

//Limpieza de edita
$('#editarIncidenciaModal').on('hidden.bs.modal', function () {
    var formulario = document.getElementById("FormEditarIncidencia");
    formulario.classList.remove("was-validated");
    $('#detalleAyudasEdita').html('');
    $("#txtAreaObservaciones").val("");
});

//Función para verificar que el trabajador tenga un horario asignado
function verificaHorario(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "horarios/buscarDiasTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            for (var i = 0; i <= data.length; i++) {

                if (data.length === 0) {
                    document.body.style.pointerEvents = 'none';
                    $("#modalRedirecciona").modal("toggle");

                } else {
                    //Destruir la tabla para evitar errores
                    if ($.fn.DataTable.isDataTable('#tabla_Incidencias')) {
                        $('#tabla_Incidencias').DataTable().destroy();
                    }
                    //Se invocan en este bloque las funciones principales, una vez verificado el horario del trabajador
                    trabajadorIncidencias(id_trabajador);
                    motivoCancelacion();
                    buscarGenero(id_trabajador);
                    obtenPrestacionesIncidencias(id_trabajador);
                }
            }
            buscarContratoIncidencias(id_trabajador);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Los Días del Trabajador : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


/*=============================================
 COMBO INCIDENCIA (AGREGAR)
 ===============================================*/

function incidencia() {
    var tipo_contrato_id = $('#tipo_contrato_incidencias').val();
    var trabajador_prestaciones = $('#trabajador_prestaciones_incidencias').val();
    var genero = $('#genero').val();
    $.ajax({
        type: "GET",
        url: "incidencias/buscarIncidenciaContrato/" + tipo_contrato_id,
        dataType: 'json',
        success: function (data) {

            if ($.isEmptyObject(data)) {

            } else {
                if (trabajador_prestaciones === "2" && tipo_contrato_id === "2") {
                    $('#cmbInc').empty().append("<option value=''>*Seleccione Incidencia</option> ");
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            if (genero === "MUJER") {
                                // INCIDENCIAS A LAS QUE SOLO TIENEN ACCESO EL PERSONAL DE FEMENINO CONFIANZA SIN PRESTACIONES (PERMISO RETRIBUIDO)
                                if (data[i].cat_incidencias.id_incidencia !== 35 && data[i].cat_incidencias.id_incidencia !== 36 && data[i].cat_incidencias.id_incidencia !== 37 && data[i].cat_incidencias.id_incidencia !== 38) {
                                    $('#cmbInc').append('<option value="' + data[i].cat_incidencias.id_incidencia + '">' + '(' + (data[i].cat_incidencias.cve_nomina ?? 'N/D') + ') - ' + data[i].cat_incidencias.descripcion + '</option>');

                                }
                            } else {
                                // Son las incidencias que no tienen acceso los de confianza sin prestaciones 35,36,37,38,33
                                if (data[i].cat_incidencias.id_incidencia !== 35 && data[i].cat_incidencias.id_incidencia !== 36 && data[i].cat_incidencias.cat_incidencias.id_incidencia !== 37 && data[i].cat_incidencias.id_incidencia !== 38 && data[i].cat_incidencias.id_incidencia !== 33) {
                                    $('#cmbInc').append('<option value="' + data[i].cat_incidencias.id_incidencia + '">' + '(' + (data[i].cat_incidencias.cve_nomina ?? 'N/D') + ') - ' + data[i].cat_incidencias.descripcion + '</option>');

                                }
                            }
                        }
                    } else {
                        $('#cmbInc').empty().append("<option value='0'>*Seleccione Incidencia</option> ");
                    }

                } else {
                    $('#cmbInc').empty().append("<option value=''>*Seleccione Incidencia</option> ");
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            if (data[i].cat_incidencias.id_incidencia !== 33) {
                                $('#cmbInc').append('<option value="' + data[i].cat_incidencias.id_incidencia + '">' + '(' + (data[i].cat_incidencias.cve_nomina ?? 'N/D') + ') - ' + data[i].cat_incidencias.descripcion + '</option>');

                            }
                        }
                    } else {
                        $('#cmbInc').empty().append("<option value='0'>*Seleccione Incidencia</option> ");
                    }
                }
            }
        },
        error: function (e) {
            $('#cmbInc').empty().append("<option value=''>*Seleccione Incidencia</option> ");
        }
    });

}


/*=============================================
 GUARDAR INCIDENCIA
 =============================================*/
$("#FormGuardarIncidencia").submit(function (e) {
    event.preventDefault();
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_incidencia = $("#cmbInc").val();
    var folio = $("#folio").val();
    var bis = $("#bis").val();
    var num_dias = $("#num_dias").val();
    var estado_incidencia_id = 1;
    var trabajador_id = urlParams.get('id_trabajador');
    var expediente = $('#expediente_id').val();
    var dias_paseos = $('#dias_paseos').val();
    var fecha_inicio = moment($('#fecha_inicio').val());
    var fecha_fin = moment($('#fecha_fin').val());
    var periodo_vacacional = $('#periodo_vacacional').val();
    var dias_totales = $('#dias_totales').val();
    var dias_disponibles = $('#dias_disponibles').val();
    var periodo_aplicacion = $('#periodo_aplicacion').val();
    var fecha_inicio_anio_periodo_vacacional = new Date($('#fecha_inicio_anio_periodo_vacacional').val());
    var fecha_fin_anio_periodo_vacacional = new Date($('#fecha_fin_anio_periodo_vacacional').val());
    var dias_proporcionales = $('#dias_proporcionales').val();
    var observaciones = $('#observaciones').val();
    var periodo_aplicacion_fin = $('#periodo_aplicacion').val();
    var genera_pago = $('#genera_pago').val();
    var anios = ($('#anios').val() + " Años, " + $('#meses').val() + " meses y " + $('#dias').val() + " días");
    var fecha_recepcion = $('#fecha_recepcion').val();
    var fecha_emision = $('#fecha_emision').val();
    var parentesco = $('#parentesco').val();
    var nombre = $('#nombre').val();
    var apellido_paterno = $('#apellido_paterno').val();
    var apellido_materno = $('#apellido_materno').val();
    var origen = $('#origen').val();
    var fecha_evento = $('#fecha_evento').val();
    var periodo_pago = $('#periodo_pago').val();
    var id_periodo_pago = $('#id_periodo_pago').val();
    var monto = $('#monto').val();
    //Días de permiso para las incidencias de ayuda por alumbramiento y fallecimiento
    var dia1Permiso = $('#dia1').text();
    var dia2Permiso = $('#dia2').text();
    var dia3Permiso = $('#dia3').text();
    var dia4Permiso = $('#dia4').text();
    var dia5Permiso = $('#dia5').text();
    var dia6Permiso = $('#dia6').text();
    var url = "";
    var datos = "";
    //*********************CASO DE VACACIONES***************************
    if (id_incidencia === "7") {
        if ($.trim(expediente) === "") {
            return false;
        }
        if ($.trim(dias_paseos) === "") {
            return false;
        }
        if ($.trim(fecha_inicio) === "") {
            return false;
        }
        if ($.trim(fecha_fin) === "") {
            return false;
        }
        if ($.trim(periodo_vacacional) === "") {
            return false;
        }
        if ($.trim(dias_totales) === "") {
            return false;
        }
        if ($.trim(dias_disponibles) === "") {
            return false;
        }
        if ($.trim(periodo_aplicacion) === "") {
            return false;
        }
        if ($.trim(fecha_inicio_anio_periodo_vacacional) === "") {
            return false;
        }
        if ($.trim(fecha_fin_anio_periodo_vacacional) === "") {
            return false;
        }
        if ($.trim(dias_proporcionales) === "") {
            return false;
        }
        if ($.trim(periodo_aplicacion_fin) === "") {
            return false;
        }
        if ($.trim(genera_pago) === "") {
            return false;
        }
        if ($.trim(anios) === "") {
            return false;
        }
        if ($.trim(folio) === "") {
            return false;
        }
        url = "incidencias/guardarIncidenciasVacaciones";
        datos = {"num_dias": num_dias, "folio": folio, "bis": bis, "cat_incidencias": {"id_incidencia": id_incidencia}, "cat_estado_incidencia": {"id_estado_incidencia": estado_incidencia_id}, "trabajador_id": trabajador_id,
            "expediente": expediente, "dias_paseos": dias_paseos, "fecha_inicio": fecha_inicio, "fecha_fin": fecha_fin,
            "periodo_vacacional": periodo_vacacional, "antiguedad": anios, "dias_totales": dias_totales, "dias_disponibles": dias_disponibles, "periodo_aplicacion": periodo_aplicacion,
            "fecha_inicio_anio_periodo_vacacional": fecha_inicio_anio_periodo_vacacional,
            "fecha_fin_anio_periodo_vacacional": fecha_fin_anio_periodo_vacacional, "dias_proporcionales": dias_proporcionales, "observaciones": observaciones, "periodo_aplicacion_fin": periodo_aplicacion_fin,
            "genera_pago_si_no": genera_pago};

        /*********************CASO ALUMBRAMIENTO, FALLECIMIENTO**************************
         No se generan ayudas para el fallecimiento de un hermano pero si se puede generar la incidencia en kárdex*/
    } else if (id_incidencia === "3" || id_incidencia === "4" && parentesco !== '1') {
        //Se convierten a date las fechas correspondientes para poder así verificar si no se superan los 30 días a partir de la fecha de fallecimiento o alumbramiento que es el lapso en el cual se puede solicitar la ayuda
        var fechaActual = new Date();
        const fecha_evento_formateado = new Date(
                parseInt(fecha_evento.substring(0, 4)), // Año
                parseInt(fecha_evento.substring(5, 7)) - 1, // Mes (restar 1 ya que los meses se indexan desde 0)
                parseInt(fecha_evento.substring(8)) // Día
                );

        if ($.trim($('#fecha_inicio').val()) === "") {
            return false;
        }
        if ($.trim($('#fecha_fin').val()) === "") {
            return false;
        }
        if ($.trim(folio) === "") {
            return false;
        }
//        if ($.trim(bis) === "") {
//            return false;
//        }
        if ($.trim(fecha_recepcion) === "") {
            return false;
        }
        if ($.trim(fecha_emision) === "") {
            return false;
        }
        if ($.trim(parentesco) === "") {
            return false;
        }
        if ($.trim(nombre) === "") {
            return false;
        }
        if ($.trim(apellido_paterno) === "") {
            return false;
        }
        if ($.trim(apellido_materno) === "") {
            return false;
        }
        if ($.trim(origen) === "") {
            return false;
        }

        if ($.trim(fecha_evento) === "") {
            return false;
        }
        //Se colocan a la media noche para hacer la comparación basándonos únicamente en el día sin tener en cuenta las horas
        fecha_evento_formateado.setHours(0, 0, 0, 0);
        fechaActual.setHours(0, 0, 0, 0);
        fecha_evento_formateado.setDate(fecha_evento_formateado.getDate() + 30);
        if (fechaActual > fecha_evento_formateado) {
            toastr["warning"]("Se han superado los 30 días para solicitar la ayuda. Fecha límite para registrar esta ayuda basado en la fecha del suceso: " + fecha_evento_formateado.toLocaleDateString(), "Advertencia", {progressBar: true, closeButton: true});
            return false;
        }

//        if ($.trim(dia1Permiso) === "") {
//            toastr["warning"]("Por favor, agrega al menos un día de permiso", "Advertencia", {progressBar: true, closeButton: true});
//            return false;
//        }
        //Esta url hace el guardado en las tres tablas, Incidencias, Ayuda y Ayuda Días permiso
        url = "incidencias/guardarIncidenciasAyudaDiaPermiso";
        datos = {"monto": monto, "num_dias": num_dias, "periodo_pago_id": id_periodo_pago, "fecha_recepcion": fecha_recepcion, "fecha_emision": fecha_recepcion, "incidencia_id": id_incidencia,
            "parentesco_id": parentesco, "nombre": nombre, "apellido_paterno": apellido_paterno,
            "apellido_materno": apellido_materno, "origen": origen, "fecha_evento": fecha_evento, "trabajador_id": trabajador_id,
            "cat_estado_incidencia": {"id_estado_incidencia": 2},
            "cat_incidencias": {"id_incidencia": id_incidencia},
            "fecha_inicio": fecha_inicio,
            "fecha_fin": fecha_fin,
            "folio": folio, "bis": bis, "observaciones": observaciones,
            //Días de permiso de la ayuda
            "dia_1": dia1Permiso,
            "dia_2": dia2Permiso,
            "dia_3": dia3Permiso,
            "dia_4": dia4Permiso,
            "dia_5": dia5Permiso,
            "dia_6": dia6Permiso
        };
        //Creo mi objeto para mandar todos los días de parámetro a las siguientes funciones y facilitar su manejo
        let diasDePermiso = {
            dia1: dia1Permiso,
            dia2: dia2Permiso,
            dia3: dia3Permiso,
            dia4: dia4Permiso,
            dia5: dia5Permiso,
            dia6: dia6Permiso
        };

        /****************************COMPROBACIONES PARA AYUDA EN CASO DÍAS DE DESCANSO, VACACIONES Y DISTINTOS AÑOS***********************************
         Estas funciones está en generaAyuda.js y realizan varias verificaciones antes de guardar la ayuda
         Variable que detiene la ejecución del código en caso de no pasar las comprobaciones correspondientes*/
        let detenerEjecucion = false;
        let todosLosDiasEnRangoAnual = true;
        //Esta función se encuentra en generaAyuda.js y verifica que, de la fecha inicial y final, no se coloquen días de permiso de diferentes años a estas
        for (const dia in diasDePermiso) {
            if (diasDePermiso.hasOwnProperty(dia) && diasDePermiso[dia] !== "") {
                let estaEnRangoAnual = diasDePermisoEstanMismoAnioQueFechaInicialYFechaFinal(diasDePermiso[dia], $('#fecha_inicio').val(), $('#fecha_fin').val());
                todosLosDiasEnRangoAnual = todosLosDiasEnRangoAnual && estaEnRangoAnual;
            }
        }
        //Si la negación de los días en rango actual diferente a true entonces hay días de permiso agregados en diferentes años a la fecha inicial y la fecha final
        if (!todosLosDiasEnRangoAnual) {
            detenerEjecucion = true;
            toastr["warning"](
                    "Se encontraron días de permiso agregados en diferentes años a la fecha inicial y fecha final ",
                    " Advertencia",
                    {progressBar: true, closeButton: true}
            );
        }
        //Esta función se encuentra en generaAyuda.js y verifica que los días de permiso no sean iguales a los días de vacaciones que pueda tener el trabajador
        seTienenDiasDeVacacionesEnDiasPermisoAyuda(diasDePermiso, trabajador_id, function (result, diasVacacionesCoincidentes) {
            valorBanderaDiasVacacionesEnAyuda = result;
            if (valorBanderaDiasVacacionesEnAyuda === true) {
                detenerEjecucion = true;
                toastr["warning"](
                        "Se encontraron días de permiso agregados en estos días de vacaciones del trabajador: " + Array.from(diasVacacionesCoincidentes).join(", "),
                        " Advertencia",
                        {progressBar: true, closeButton: true}
                );
            }
        });
        //Esta función está en generaAyuda.js y verifica que los días de permiso no sean iguales a los días de descanso que tenga registrados el trabajador
        seTienenDiasDescansoEnDiasPermisoAyuda(diasDePermiso, trabajador_id, function (result, nombresDias) {
            //Si el resultado es true es porque los días de permiso contienen días de descanso del trabajador
            valorBanderaDiasDescansoEnAyuda = result;
            if (valorBanderaDiasDescansoEnAyuda === true) {
                detenerEjecucion = true;
                toastr["warning"](
                        "Se encontraron días de permiso agregados en estos días de descanso del trabajador: " + nombresDias.join(", "),
                        " Advertencia",
                        {progressBar: true, closeButton: true}
                );
            }
        });
        //Se detiene la ejecución de la función para que el usuario tenga que ingresar los datos de manera correcta
        if (detenerEjecucion) {
            return;
        }
        generarIncidenciasParaCadaDiaDePermiso(estado_incidencia_id, id_incidencia, folio, bis, trabajador_id, dia1Permiso, dia2Permiso, dia3Permiso, dia4Permiso, dia5Permiso, dia6Permiso);
        //*********************PERMISO RETRIBUIDO ***************************
    } else if (id_incidencia === "33") {
        if ($.trim($('#fecha_inicio').val()) === "") {
            return false;
        }
        if ($.trim($('#fecha_fin').val()) === "") {
            return false;
        }
        if ($.trim(folio) === "") {
            return false;
        }
//        if ($.trim(bis) === "") {
//            return false;
//        }
        if ($.trim(fecha_recepcion) === "") {
            return false;
        }
        if ($.trim(fecha_emision) === "") {
            return false;
        }
        if ($.trim(origen) === "") {
            return false;
        }
        //Esta url hace el guardado en dos trablas, ayuda e incidencias
        url = "incidencias/guardarIncidenciasAyuda";

        datos = {"monto": monto, "num_dias": num_dias, "periodo_pago_id": id_periodo_pago, "fecha_recepcion": fecha_recepcion, "fecha_emision": fecha_recepcion, "incidencia_id": id_incidencia, "parentesco_id": parentesco, "nombre": nombre, "apellido_paterno": apellido_paterno,
            "apellido_materno": apellido_materno, "origen": origen, "trabajador_id": trabajador_id, "cat_estado_incidencia": {"id_estado_incidencia": estado_incidencia_id},
            "cat_incidencias": {"id_incidencia": id_incidencia}, "fecha_inicio": fecha_inicio, "fecha_fin": fecha_fin, "folio": folio, "bis": bis, "observaciones": observaciones};

        //*********************INCIDENCIAS SIN MANEJOS DATOS ADICIONALES***************************
    } else {
        if ($.trim(estado_incidencia_id) === "") {
            return false;
        }
        if ($.trim(id_incidencia) === "") {
            return false;
        }
        if ($.trim($('#fecha_inicio').val()) === "") {
            return false;
        }
        if ($.trim($('#fecha_fin').val()) === "") {
            return false;
        }
        if ($.trim(folio) === "") {
            return false;
        }
        
        url = "incidencias/guardarIncidencia";
        datos = {"num_dias": num_dias, "cat_estado_incidencia": {"id_estado_incidencia": estado_incidencia_id}, "cat_incidencias": {"id_incidencia": id_incidencia}, "fecha_inicio": fecha_inicio,
            "fecha_fin": fecha_fin, "folio": folio, "bis": bis, "observaciones": observaciones, "trabajador_id": trabajador_id};
    }
    ajaxAgregarIncidencia(datos, url, trabajador_id);
});

function ajaxAgregarIncidencia (datos, url, trabajador_id) {
    console.log(url);
    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            toastr['success']("Datos guardados correctamente");
            $("#agregarIncidenciaModal").modal('hide');
            $('#tabla_Incidencias').DataTable().ajax.reload();
            if (url === "incidencias/guardarIncidenciasVacaciones") {
                buscarTrabajador(trabajador_id);
            }
            //Detecta las ayudas por fallecimiento y alumbramiento para no generarlas autorizadas/cambiar su estado, solo aplica a la incidencia que contiene la ayuda
            if (datos.cat_incidencias.id_incidencia === "3" || datos.cat_incidencias.id_incidencia === "4" && url === "incidencias/guardarIncidenciasAyudaDiaPermiso") {
                //Se actualiza el estado de la incidencia pasándole el id generado proveniente del data.data
                estadoIncidencia(data.data, 1);
                //Detecta el permiso retribuido para generarlo no autorizado
            } else if (datos.cat_incidencias.id_incidencia === "33" && url === "incidencias/guardarIncidenciasAyuda") {
                estadoIncidencia(data.data, 1);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//Esta función genera las incidencias para cada día de permiso al momento de agregar un permiso por fallecimiento
function generarIncidenciasParaCadaDiaDePermiso (estado_incidencia_id, id_incidencia, folio, bis, trabajador_id, dia1, dia2, dia3, dia4, dia5, dia6){
    // Se crea un arreglo que contiene toodos los días recibidos
    const todosLosDias = [dia1, dia2, dia3, dia4, dia5, dia6];
    // Se filtran las fechas queno están vacías
    const diasConDatos = todosLosDias.filter(dia => dia.trim() !== "");
    // Recorrer el arreglo de los días que contienen datos
    for (const dia of diasConDatos) {
        fecha_inicio = new Date(dia);
        fecha_fin = new Date(dia);
        url = "incidencias/guardarIncidencia";
        datos = {"num_dias": 1, "cat_estado_incidencia": {"id_estado_incidencia": estado_incidencia_id}, "cat_incidencias": {"id_incidencia": id_incidencia}, "fecha_inicio": fecha_inicio,
            "fecha_fin": fecha_fin, "folio": folio, "bis": bis, "observaciones": "Día de permiso de la ayuda con folio " + folio, "trabajador_id": trabajador_id};
        //Se llama al servicio para generar las incidencias en los días de permiso y estos días son los que se motrarán en el kárdex 
        ajaxAgregarIncidencia(datos, url, trabajador_id);
    }
};

function verKardexTrabajador() {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    window.location.href = 'guardaInasistencias?id_trabajador=' + trabajador_id;
}


/*=============================================
 VER INCIDENCIA
 ===============================================*/
function verIncidencia(id_incidencia) {
    $("#editarIncidenciaModal").modal("toggle");
    $.ajax({
        type: "GET",
        url: "incidencias/buscarIncidenciaId/" + id_incidencia,
        dataType: 'json',
        success: function (data) {
            (data.data.cat_incidencias === null) ? incidencia(0) : incidencia_edita(data.data.cat_incidencias.id_incidencia);
            (data.data.observaciones === null) ? $('#txtAreaObservaciones').val("") : $('#txtAreaObservaciones').val(data.data.observaciones);
            (data.data.fecha_inicio === null) ? $('#fecha_inicio_edita').val("") : $('#fecha_inicio_edita').val(data.data.fecha_inicio);
            (data.data.fecha_fin === null) ? $('#fecha_fin_edita').val("") : $('#fecha_fin_edita').val(data.data.fecha_fin);
            (data.data.folio === null) ? $('#folio_edita').val("") : $('#folio_edita').val(data.data.folio);
            (data.data.num_dias === null) ? $('#num_dias_edita').val("") : $('#num_dias_edita').val(data.data.num_dias);
            $("#id_incidencia").val(id_incidencia);
            //Lo coloqué en un timeout ya que la asincronía no permitia obtener el id del combo de la incidencia de manera correcta
            setTimeout(function () {
                agregarDatosAdicionalesAyudaEdicionSiAplica(id_incidencia, data.data.cat_incidencias.id_incidencia);
            }, 100);
        },
        error: function (e) {
            alert(e);
        }
    });
}

function agregarDatosAdicionalesAyudaEdicionSiAplica (id_incidencia, idCatalogoIncidencia) {
    let idIncidenciaCatalogo = $("#cmbInc_edita").val();
    $.ajax({
        type: "GET",
        url: "ayuda/buscarAyudaSiExiste/" + id_incidencia,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data.data)) {
                //No hacer nada
            } else {
                if (idIncidenciaCatalogo === "33") {
                    $('#detalleAyudasEdita').html(detalleHtmlAyudaPermisoRetribuidoEdita);
                    //Id de la ayuda
                    (data.data[0].id_ayuda === null) ? $('#id_ayuda_edita').val("") : $('#id_ayuda_edita').val(data.data[0].id_ayuda);
                    //Agregar datos de ayuda al formulario
                    (data.data[0].fecha_recepcion === null) ? $('#fecha_recepcion_edita_ayuda').val("") : $('#fecha_recepcion_edita_ayuda').val(data.data[0].fecha_recepcion);
                    (data.data[0].fecha_emision === null) ? $('#fecha_emision_edita_ayuda').val("") : $('#fecha_emision_edita_ayuda').val(data.data[0].fecha_emision);
                } else {
                    //Se llama a la función que llena los datos del combo parentesco
                    parentesco();
                    //Mostrar los datos de la ayuda relacionada creando los campos en el html
                    $('#detalleAyudasEdita').html(detalleHtmlAyudaFallecimientoAlumbramientoEdita);
                    //Se rellenan los campos escondidos para actualizar las diferentes tablas
                    //Id de la ayuda
                    (data.data[0].id_ayuda === null) ? $('#id_ayuda_edita').val("") : $('#id_ayuda_edita').val(data.data[0].id_ayuda);
                    //Id de los días de permiso de la ayuda
                    (data.data[0].ayuda_dias_permiso_id === null) ? $('#id_ayuda_dias_permiso_edita').val("") : $('#id_ayuda_dias_permiso_edita').val(data.data[0].ayuda_dias_permiso_id);
                    //Agregar datos de ayuda al formulario
                    (data.data[0].fecha_recepcion === null) ? $('#fecha_recepcion_edita_ayuda').val("") : $('#fecha_recepcion_edita_ayuda').val(data.data[0].fecha_recepcion);
                    (data.data[0].fecha_emision === null) ? $('#fecha_emision_edita_ayuda').val("") : $('#fecha_emision_edita_ayuda').val(data.data[0].fecha_emision);
                    //Timoeut para dar tiempo al select que rellene los datos y se haga la selección correctamente
                    setTimeout(() => {
                        (data.data[0].parentesco_id === null) ? $('#cmb_parentesco_edita_ayuda').val("") : $('#cmb_parentesco_edita_ayuda').val(data.data[0].parentesco_id);
                    }, 100);
                    (data.data[0].nombre === null) ? $('#nombre_edita_ayuda').val("") : $('#nombre_edita_ayuda').val(data.data[0].nombre);
                    (data.data[0].apellido_paterno === null) ? $('#apellido_paterno_edita_ayuda').val("") : $('#apellido_paterno_edita_ayuda').val(data.data[0].apellido_paterno);
                    (data.data[0].apellido_materno === null) ? $('#apellido_materno_edita_ayuda').val("") : $('#apellido_materno_edita_ayuda').val(data.data[0].apellido_materno);
                    (data.data[0].origen === null) ? $('#origen_edita_ayuda').val("") : $('#origen_edita_ayuda').val(data.data[0].origen);
                    (data.data[0].fecha_evento === null) ? $('#fecha_evento_edita_ayuda').val("") : $('#fecha_evento_edita_ayuda').val(data.data[0].fecha_evento);
                    (data.data[0].dia_1 === null) ? $('#fecha_permiso1_edita_ayuda').val("") : $('#fecha_permiso1_edita_ayuda').val(data.data[0].dia_1);
                    (data.data[0].dia_2 === null) ? $('#fecha_permiso2_edita_ayuda').val("") : $('#fecha_permiso2_edita_ayuda').val(data.data[0].dia_2);
                    (data.data[0].dia_3 === null) ? $('#fecha_permiso3_edita_ayuda').val("") : $('#fecha_permiso3_edita_ayuda').val(data.data[0].dia_3);
                    (data.data[0].dia_4 === null) ? $('#fecha_permiso4_edita_ayuda').val("") : $('#fecha_permiso4_edita_ayuda').val(data.data[0].dia_4);
                    (data.data[0].dia_5 === null) ? $('#fecha_permiso5_edita_ayuda').val("") : $('#fecha_permiso5_edita_ayuda').val(data.data[0].dia_5);
                    (data.data[0].dia_6 === null) ? $('#fecha_permiso6_edita_ayuda').val("") : $('#fecha_permiso6_edita_ayuda').val(data.data[0].dia_6);
                    //Se deshabilita el campo del 6 día ya que permiso por fallecimiento es el único que cuenta con 6 días de permiso
                    if (idCatalogoIncidencia === 4) {
                        $('#fecha_permiso6_edita_ayuda').prop('disabled', false);
                    } else {
                        $('#fecha_permiso6_edita_ayuda').prop('disabled', true);
                    }
                }


            }
        },
        error: function (xhr) {
            toastr["error"]("Ocurrió un error: " + xhr.responseText, "Error", {progressBar: true, closeButton: true});
        }
    });
}

const detalleHtmlAyudaPermisoRetribuidoEdita = `<input type="hidden" id="id_ayuda_edita">
<input type="hidden" id="id_ayuda_edita">
  <b> <label style="margin-left: 160px;">Campos de Ayuda</label></b>
    <div class="row">
        <b style="margin-left: 50px;">Fecha Recepción</b>
        <br><input type="date" id="fecha_recepcion_edita_ayuda" style="margin-left:45px;width:160px;" class="form-control" name="fecha_recepcion_edita_ayuda">
        <hr style="width:400px; margin-left:50px">
    </div>
    <div class="row">
        <b style="margin-left: 50px;">Fecha Emisión</b>
        <br><input type="date" id="fecha_emision_edita_ayuda" style="margin-left:62px;width:160px;" class="form-control" name="fecha_emision_edita_ayuda">
        <hr style="width:400px; margin-left:50px">
    </div>`;

//Se añaden los campos al html si la incidencia corresponde con una ayuda que esté registrada en la tabla de ayuda y ayuda días permiso
const detalleHtmlAyudaFallecimientoAlumbramientoEdita = `
<input type="hidden" id="id_ayuda_edita">
<input type="hidden" id="id_ayuda_dias_permiso_edita">
  <b> <label style="margin-left: 160px;">Campos de Ayuda</label></b>
    <div class="row">
        <b style="margin-left: 50px;">Fecha Recepción</b>
        <br><input type="date" id="fecha_recepcion_edita_ayuda" style="margin-left:45px;width:160px;" class="form-control" name="fecha_recepcion_edita_ayuda">
        <hr style="width:400px; margin-left:50px">
    </div>
    <div class="row">
        <b style="margin-left: 50px;">Fecha Emisión</b>
        <br><input type="date" id="fecha_emision_edita_ayuda" style="margin-left:62px;width:160px;" class="form-control" name="fecha_emision_edita_ayuda">
        <hr style="width:400px; margin-left:50px">
    </div>
    <div class="row">
        <b style="margin-left: 50px;">Parentesco</b>
        <br>
        <select id='cmb_parentesco_edita_ayuda' name='cmb_parentesco_edita_ayuda' maxlength='10' type='number' class='form-control'  style="margin-left:85px;width:160px;" placeholder='' disabled></select>
        <hr style="width:400px; margin-left:50px">
    </div>
    <div class="row">
        <b style="margin-left: 50px;">Nombre</b>
        <input type="text" id="nombre_edita_ayuda" style="margin-left:105px;width:160px;" class="form-control" name="nombre_edita_ayuda" min="0" max="20" required>
        <hr style="width:400px; margin-left:50px">
    </div>   
    <div class="row">
        <b style="margin-left: 50px;">Apellido Paterno</b>
        <input type="text" id="apellido_paterno_edita_ayuda" style="margin-left:40px;width:160px;" class="form-control" name="apellido_paterno_edita_ayuda" min="0" max="20" required>
        <hr style="width:400px; margin-left:50px">
    </div>  
    <div class="row">
        <b style="margin-left: 50px;">Apellido Materno</b>
        <input type="text" id="apellido_materno_edita_ayuda" style="margin-left:40px;width:160px;" class="form-control" name="apellido_materno_edita_ayuda" min="0" max="20" required>
        <hr style="width:400px; margin-left:50px">
    </div>  
    <div class="row">
        <b style="margin-left: 50px;">Origen</b>
        <input type="text" id="origen_edita_ayuda" style="margin-left:120px;width:160px;" class="form-control" name="origen_edita_ayuda" min="0" max="20" required>
        <hr style="width:400px; margin-left:50px">
    </div> 
   <div class="row">
        <b style="margin-left: 50px;">Fecha Suceso</b>
           <br><input type="date" id="fecha_evento_edita_ayuda" style="margin-left:70px;width:160px;" class="form-control" name="fecha_evento_edita_ayuda" disabled>
        <hr style="width:400px; margin-left:50px">
    </div> 
    <div class="row">
        <b style="margin-left: 50px;">Día Permiso 1</b>
        <br><input type="date" id="fecha_permiso1_edita_ayuda" style="margin-left:70px;width:160px;" class="form-control" name="fecha_permiso1_edita_ayuda">
        <hr style="width:400px; margin-left:50px">
    </div>
    <div class="row">
        <b style="margin-left: 50px;">Día Permiso 2</b>
        <br><input type="date" id="fecha_permiso2_edita_ayuda" style="margin-left:70px;width:160px;" class="form-control" name="fecha_permiso2_edita_ayuda">
        <hr style="width:400px; margin-left:50px">
    </div>
    <div class="row">
        <b style="margin-left: 50px;">Día Permiso 3</b>
        <br><input type="date" id="fecha_permiso3_edita_ayuda" style="margin-left:70px;width:160px;" class="form-control" name="fecha_permiso3_edita_ayuda">
        <hr style="width:400px; margin-left:50px">
    </div>
    <div class="row">
        <b style="margin-left: 50px;">Día Permiso 4</b>
        <br><input type="date" id="fecha_permiso4_edita_ayuda" style="margin-left:70px;width:160px;" class="form-control" name="fecha_permiso4_edita_ayuda">
        <hr style="width:400px; margin-left:50px">
    </div>
    <div class="row">
        <b style="margin-left: 50px;">Día Permiso 5</b>
        <br><input type="date" id="fecha_permiso5_edita_ayuda" style="margin-left:70px;width:160px;" class="form-control" name="fecha_permiso5_edita_ayuda">
        <hr style="width:400px; margin-left:50px">
    </div>
    <div class="row">
        <b style="margin-left: 50px;">Día Permiso 6</b>
        <br><input type="date" id="fecha_permiso6_edita_ayuda" style="margin-left:70px;width:160px;" class="form-control" name="fecha_permiso6_edita_ayuda">
        <hr style="width:400px; margin-left:50px">
    </div>`;

/*=============================================
 EDITAR LAS FECHAS EN EL MODAL DE EDITAR
 ===============================================*/
function recalculaFechas(fecha_inicio_edita, fecha_fin_edita) {
    var fecha_inicio_nueva = new Date(fecha_inicio_edita);
    var fecha_fin_edita_nueva = new Date(fecha_fin_edita);

    // Calcula la diferencia en días hábiles
    var diasHabiles = 0;
    var contador = 0;

    while (fecha_inicio_nueva < fecha_fin_edita_nueva) {
        if (fecha_inicio_nueva.getDay() !== 0 && fecha_inicio_nueva.getDay() !== 6) {
            diasHabiles++;
        }
        fecha_inicio_nueva.setDate(fecha_inicio_nueva.getDate() + 1);
        contador++;
    }

    $('#num_dias_edita').val(diasHabiles);
}

/*=============================================
 COMBO INCIDENCIA ACTUALIZAR
 ===============================================*/
function incidencia_edita(id_incidencia) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarCatIncidencias",
        dataType: 'json',
        success: function (data) {

            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbInc_edita').empty().append("<option value=''>* Incidencia</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_incidencia === id_incidencia) ? vselected = "selected" : vselected = "";
                        $('#cmbInc_edita').append('<option value="' + data[x].id_incidencia + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                    /*Habilitado de incidencias relacionadas a las incapacidades para permitir su modificación
                     EG0, EG50, EG60, EG75, EG100, RT100, COMPENSACIONES EG, RT,PRE,POS,EG60*/
                    let idsPermitidos = [27, 14, 31, 15, 16, 17, 28, 29, 30, 31];
                    let valorCmbIncidencia = $('#cmbInc_edita').val();
                    if (idsPermitidos.includes(parseInt(valorCmbIncidencia))) {
                        $('#cmbInc_edita').prop('disabled', false);
                        let valorCmbIncidencia = $('#cmbInc_edita').val();
                        let cmbInc_edita = $('#cmbInc_edita');

                        cmbInc_edita.find('option').each(function () {
                            let optionValue = parseInt($(this).val());
                            if (idsPermitidos.includes(optionValue)) {
                                $(this).prop('disabled', false);
                            } else {
                                $(this).css('display', 'none');
                            }
                        });
                        if (!idsPermitidos.includes(parseInt(valorCmbIncidencia))) {
                            cmbInc_edita.prop('disabled', true);
                        } else {
                            cmbInc_edita.prop('disabled', false);
                        }
                    } else {
                        $('#cmbInc_edita').prop('disabled', true);
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Día de la Semana : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 ACTUALIZAR INCIDENCIA
 =============================================*/
$("#FormEditarIncidencia").submit(function (e) {
    event.preventDefault();
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    const trabajador_id = urlParams.get('id_trabajador');
    var id_incidencia = $("#id_incidencia").val();
    var id_ayuda_edita = $('#id_ayuda_edita').val();
    var id_ayuda_dias_permiso_edita = $('#id_ayuda_dias_permiso_edita').val();
    var cmbInc_edita = $("#cmbInc_edita").val();
    var txtArea = $("#txtAreaObservaciones").val();
    var fecha_inicio = new Date($("#fecha_inicio_edita").val());
    var fecha_fin = new Date($("#fecha_fin_edita").val());
    var folio = $("#folio_edita").val();
    var num_dias = $("#num_dias_edita").val();
    //CAMPOS DE LA TABLA AYUDA 
    var fecha_recepcion_edita_ayuda = $("#fecha_recepcion_edita_ayuda").val();
    var fecha_emision_edita_ayuda = $("#fecha_emision_edita_ayuda").val();
    var nombre_edita_ayuda = $("#nombre_edita_ayuda").val();
    var apellido_paterno_edita_ayuda = $("#apellido_paterno_edita_ayuda").val();
    var apellido_materno_edita_ayuda = $("#apellido_materno_edita_ayuda").val();
    var origen_edita_ayuda = $("#origen_edita_ayuda").val();
    //CAMPOS DE LA TABLA AYUDA DÍAS
    var fecha_permiso1_edita_ayuda = $("#fecha_permiso1_edita_ayuda").val();
    var fecha_permiso2_edita_ayuda = $("#fecha_permiso2_edita_ayuda").val();
    var fecha_permiso3_edita_ayuda = $("#fecha_permiso3_edita_ayuda").val();
    var fecha_permiso4_edita_ayuda = $("#fecha_permiso4_edita_ayuda").val();
    var fecha_permiso5_edita_ayuda = $("#fecha_permiso5_edita_ayuda").val();
    var fecha_permiso6_edita_ayuda = $("#fecha_permiso6_edita_ayuda").val();
    //Se detecta el ID_AYUDA y AYUDA_DIAS_PERMISO de la tabla ayuda si existe
    if (id_ayuda_edita !== undefined && id_ayuda_dias_permiso_edita !== undefined && id_ayuda_edita.trim() !== '' && id_ayuda_dias_permiso_edita.trim() !== '') {
        //**********COMPROBACIONES DE LA TABLA DE INCIDENCIAS ********************
        if ($.trim(cmbInc_edita) === "") {
            return false;
        }
        if ($.trim(txtArea) === "") {
            return false;
        }
        if (isNaN(fecha_inicio)) {
            return false;
        }
        if (isNaN(fecha_fin)) {
            return false;
        }
        if ($.trim(folio) === "") {
            return false;
        }
        if ($.trim(num_dias) === "") {
            return false;
        }
        // **************COMPROBACIONES DE LA TABLA AYUDA ***************
        if ($.trim(fecha_recepcion_edita_ayuda) === "") {
            return false;
        }
        if ($.trim(fecha_emision_edita_ayuda) === "") {
            return false;
        }
        if ($.trim(nombre_edita_ayuda) === "") {
            return false;
        }
        if ($.trim(apellido_paterno_edita_ayuda) === "") {
            return false;
        }
        if ($.trim(apellido_materno_edita_ayuda) === "") {
            return false;
        }
        if ($.trim(origen_edita_ayuda) === "") {
            return false;
        }
        //***********COMPROBACIONES TABLA AYUDA DIAS DE PERMISO (Al menos el primer dia seleccionado)**************
//        if ($.trim(fecha_permiso1_edita_ayuda) === "") {
//            return false;
//        }
        //GUARDA LA EDICION DE LA INCIENCIA GENERAL
        var datos = {"cat_incidencias": {
                "id_incidencia": cmbInc_edita
            }, "observaciones": txtArea,
            "fecha_inicio": fecha_inicio,
            "fecha_fin": fecha_fin,
            "folio": folio,
            "num_dias": num_dias};
        ajaxEditarIncidencia(id_incidencia, "incidencias/editarIncidencia/", datos);
        //GUARDA EDICIONES DE LA AYUDA
        datosAyuda = {"fecha_recepcion": fecha_recepcion_edita_ayuda,
            "fecha_emision": fecha_emision_edita_ayuda,
            "nombre": nombre_edita_ayuda,
            "apellido_paterno": apellido_paterno_edita_ayuda,
            "apellido_materno": apellido_materno_edita_ayuda,
            "origen": origen_edita_ayuda
        };
        ajaxEditarIncidencia(id_ayuda_edita, "ayuda/editarAyuda/", datosAyuda);
        //GUARDA EDICIONES DE LOS DIAS DE PERMISO DE LA AYUDA
        datosAyudaDias = {
            "dia_1": fecha_permiso1_edita_ayuda,
            "dia_2": fecha_permiso2_edita_ayuda,
            "dia_3": fecha_permiso3_edita_ayuda,
            "dia_4": fecha_permiso4_edita_ayuda,
            "dia_5": fecha_permiso5_edita_ayuda,
            "dia_6": fecha_permiso6_edita_ayuda
        };
        ajaxEditarIncidencia(id_ayuda_dias_permiso_edita, "ayuda/editarAyudaDiasPermiso/", datosAyudaDias);
        //Se buscan las incidencias relacionadas que ya fueron generadas anteriormente para generarlas o realizar la edición en su defecto
        buscarIncidenciasPorFolioyEditarDiasPermiso(id_incidencia, folio, datosAyudaDias);
        buscarIncidenciasPorFolioyAgregarDiasPermiso(id_incidencia, id_ayuda_dias_permiso_edita, folio, txtArea, trabajador_id);
    } else if (cmbInc_edita === "33") {
        //**********COMPROBACIONES DE LA TABLA DE INCIDENCIAS ********************
        if ($.trim(cmbInc_edita) === "") {
            return false;
        }
        if ($.trim(txtArea) === "") {
            return false;
        }
        if (isNaN(fecha_inicio)) {
            return false;
        }
        if (isNaN(fecha_fin)) {
            return false;
        }
        if ($.trim(folio) === "") {
            return false;
        }
        if ($.trim(num_dias) === "") {
            return false;
        }
        // **************COMPROBACIONES DE LA TABLA AYUDA ***************
        if ($.trim(fecha_recepcion_edita_ayuda) === "") {
            return false;
        }
        if ($.trim(fecha_emision_edita_ayuda) === "") {
            return false;
        }
        //GUARDA EDICIONES A LA INCIDENCIA
        var datos = {"cat_incidencias": {
                "id_incidencia": cmbInc_edita
            }, "observaciones": txtArea,
            "fecha_inicio": fecha_inicio,
            "fecha_fin": fecha_fin,
            "folio": folio,
            "num_dias": num_dias};
        ajaxEditarIncidencia(id_incidencia, "incidencias/editarIncidencia/", datos);
        //GUARDA EDICIONES DE LA AYUDA
        datosAyuda = {"fecha_recepcion": fecha_recepcion_edita_ayuda,
            "fecha_emision": fecha_emision_edita_ayuda
        };
        ajaxEditarIncidencia(id_ayuda_edita, "ayuda/editarAyuda/", datosAyuda);
        //Si los elementos ayuda id y ayuda dias id son undefined o vacíos y no corrresponden a un permiso retribuido entonces se maneja la incidencia sin los datos adicionales
    } else {
        //**********COMPROBACIONES DE LA TABLA DE INCIDENCIAS ********************
        if ($.trim(cmbInc_edita) === "") {
            return false;
        }
        if ($.trim(txtArea) === "") {
            return false;
        }
        if ($.trim(fecha_inicio) === "") {
            return false;
        }
        if ($.trim(fecha_fin) === "") {
            return false;
        }
        if ($.trim(folio) === "") {
            return false;
        }
        if ($.trim(num_dias) === "") {
            return false;
        }
        //GUARDA EDICIONES DE LOS DATOS GENERALES DE LA INCIDENCIA
        var datos = {"cat_incidencias": {
                "id_incidencia": cmbInc_edita
            }, "observaciones": txtArea,
            "fecha_inicio": fecha_inicio,
            "fecha_fin": fecha_fin,
            "folio": folio,
            "num_dias": num_dias};
        ajaxEditarIncidencia(id_incidencia, "incidencias/editarIncidencia/", datos);
    }
});

function buscarIncidenciasPorFolioyEditarDiasPermiso (id_incidencia, folio, datosAyudaDias) {
    let id_incidencia_entero = parseInt(id_incidencia);
    let arregloIdIncidencias = [];
    $.ajax({
        type: "GET",
        url: "incidencias/buscarFolioIncidencia/" + folio,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontraron los días de permiso para su modificación", "Advertencia", {progressBar: true, closeButton: true});
            } else {
                data.forEach((incidencia) => {
                    //Si corresponde a la incidencia que tiene la ayuda registrada se omite
                    if (incidencia.id_incidencia === id_incidencia_entero) {
                    } else {
                        //Se guardan las incidencias relacionadas en un arreglo
                        arregloIdIncidencias.push(incidencia.id_incidencia);
                    }
                });
                //Se hace el arreglo que trae los id y las fechas a editar
                const arregloRelacionado = arregloIdIncidencias.map((id_incidencia, index) => {
                    const nombreDia = `dia_${index + 1}`;
                    const fechaCorrespondiente = datosAyudaDias[nombreDia];

                    // Crear un objeto con id_incidencia y fecha
                    return {id_incidencia, fecha: fechaCorrespondiente};
                });

                //Para cada elemento del arreglo relacionado se hace la actualización de fechas correspondientes que ya existían anteriormente
                arregloRelacionado.forEach((elemento) => {
                    const id_incidencia_actualizar = elemento.id_incidencia;
                    const fecha_inicio_actualizado = new Date(
                            parseInt(elemento.fecha.substring(0, 4)), // Año
                            parseInt(elemento.fecha.substring(5, 7)) - 1, // Mes (restar 1 ya que los meses se indexan desde 0)
                            parseInt(elemento.fecha.substring(8)) // Día
                            );
                    const datos = {
                        "fecha_inicio": fecha_inicio_actualizado,
                        "fecha_fin": fecha_inicio_actualizado
                    };
                    //Si la instancia de la fecha es válida entonces se actualiza la fecha correspondiente
                    if (fecha_inicio_actualizado instanceof Date && !isNaN(fecha_inicio_actualizado)) {
                        ajaxEditarIncidencia(id_incidencia_actualizar, "incidencias/editarFechasIncidencias/", datos);
                        //Si la instancia de la fecha es eliminada desde la incidencia con la ayuda entonces se elimina el día correspondiente
                    } else {
                        eliminarIncidencia(id_incidencia_actualizar);
                    }
                });
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar folios relacionados: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};
//Se agregan los días adicionales como incidencias de kárdex si es que el usuario al realizar la edición agregó días de permiso
function buscarIncidenciasPorFolioyAgregarDiasPermiso(id_incidencia, id_ayuda_dias_permiso_edita, folio, observaciones, trabajador_id) {
    let id_incidencia_entero = parseInt(id_incidencia);
    //Arreglo que contendrá las incidencias relacionadas pero sin la ayuda
    let arregloIdIncidencias = [];
    //Ajax para buscar las incidencias relacionadas por folio
    $.ajax({
        type: "GET",
        url: "incidencias/buscarFolioIncidencia/" + folio,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontraron los folios de permiso para su modificación", "Advertencia", {progressBar: true, closeButton: true});
            } else {
                let idincidenciaGenerada = data[0].cat_incidencias.id_incidencia;
                data.forEach((incidencia) => {
                    if (incidencia.id_incidencia === id_incidencia_entero) {
                    } else {
                        arregloIdIncidencias.push(incidencia.id_incidencia);
                    }
                });
                const arregloRelacionado = arregloIdIncidencias.map((id_incidencia, index) => {
                    const nombreDia = `dia_${index + 1}`;
                    const fechaCorrespondiente = datosAyudaDias[nombreDia];
                    return {id_incidencia, fecha: fechaCorrespondiente};
                });
                buscarAyudaDiasPermiso(id_ayuda_dias_permiso_edita, arregloRelacionado, folio, observaciones, trabajador_id, idincidenciaGenerada);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar folios relacionados: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

function buscarAyudaDiasPermiso(id_ayuda_dias_permiso_edita, arregloRelacionado, folio, observaciones, trabajador_id, idincidenciaGenerada) {
    //Ajax para traer los días de permiso registrados en la ayuda
    $.ajax({
        type: "GET",
        url: "ayuda/buscarAyudaDiasPermiso/" + id_ayuda_dias_permiso_edita,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontraron los días de permiso para su modificación", "Advertencia", {progressBar: true, closeButton: true});
            } else {
                // Obtener las fechas de los objetos y del objeto 'data' normalizando las fechas del data para poder obtener las fechas adicionales
                const fechasIncidencias = arregloRelacionado.map(incidencia => incidencia.fecha.substring(0, 10));
                const fechasData = Object.entries(data.data)
                        .filter(([key, value]) => key !== 'id_ayuda_dias' && value !== null)
                        .map(([key, value]) => value.substring(0, 10));
                // Verificar si hay días adicionales
                const resultado = obtenerDiasAdicionales(fechasIncidencias, fechasData);
                //Si el resultado es mayor a cero, resultado traerá los dias adicionales agregados
                if (resultado.length > 0) {
                    //Por cada día adicional encontrado se generará la incidencia correspondiente para generar los días en el kárdex
                    resultado.forEach((fecha, index) => {
                        url = "incidencias/guardarIncidencia";
                        datos = {"num_dias": 1, "cat_estado_incidencia": {"id_estado_incidencia": 1}, "cat_incidencias": {"id_incidencia": idincidenciaGenerada}, "fecha_inicio": moment(fecha),
                            "fecha_fin": moment(fecha), "folio": folio, "observaciones": observaciones, "trabajador_id": trabajador_id};
                        //Se agregan las incidencias adicionales que se encontraron en la tabla días de permiso y que no están registradas como incidencias identificando el folio
                        ajaxAgregarIncidencia(datos, url, trabajador_id);
                    });
                    //En caso contrario no se manejan los días adicionales agregados desde la incidencia
                } else {
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar folios relacionados: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

// Función para verificar si hay días adicionales
function obtenerDiasAdicionales(fechas1, fechas2) {
    const diasAdicionales = fechas2.filter(fecha2 => !fechas1.includes(fecha2));
    return diasAdicionales;
}

function ajaxEditarIncidencia(id, url, datos) {
    $.ajax({
        type: "POST",
        url: url + id,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                toastr['success']("Los datos han sido actualizados", "Aviso", {progressBar: true, closeButton: true});
                $("#editarIncidenciaModal").modal('hide');
                $("#corridaModal").modal("hide");
                $('#tabla_Incidencias').DataTable().ajax.reload();
            }
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 ELIMINAR INCIDENCIA
 =============================================*/
// Definir la función handleEliminarIncidencias fuera de la función eliminarIncidencia
function handleEliminarIncidencias(event, id_incidencia, folio, incidencia_id) {
    event.preventDefault();
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var estatus = 0;
    if (incidencia_id === 3 || incidencia_id === 4) {
        //Se eliminan las incidencias que tengan el mismo folio, solo para las ayudas por alumbramiento y fallecimiento
        eliminarIncidenciasRelacionadasAyudas(folio);
        return;
    }
    $.ajax({
        type: "POST",
        url: "incidencias/estatusIncidencia/" + id_incidencia + "/" + estatus,
        contentType: "application/json",
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                toastr['error']("La incidencia ha sido eliminada", "Aviso");
                $('#tabla_Incidencias').DataTable().ajax.reload();
                $("#eliminarIncidenciasModal").modal("hide");
            }
        },
        error: function (e) {
            alert(e);
        }
    });
}

// La función eliminarIncidencia sigue funcionando igual pero sin la definición de handleEliminarIncidencias
function eliminarIncidencia(id_incidencia, folio, incidencia_id) {
    if (incidencia_id === 3 || incidencia_id === 4) {
        $('#detalleCancelacionAyudas').html('<label class="form-label" for="addon-wrapping-left">Se eliminarán las ayudas con el folio relacionado</label>');
    }
    // Modal para la confirmación de la eliminación de la incidencia seleccionada
    $("#eliminarIncidenciasModal").modal("show");

    // Vincular el evento submit
    $("#eliminarIncidenciasModal form").submit(function (event) {
        handleEliminarIncidencias(event, id_incidencia, folio, incidencia_id);
    });

    // Desvincular el evento submit cuando se cierra el modal
    $("#eliminarIncidenciasModal").on('hidden.bs.modal', function () {
        $("#eliminarIncidenciasModal form").off('submit');
        $('#detalleCancelacionAyudas').html('');
    });
}

function eliminarIncidenciasRelacionadasAyudas (folio) {
    $.ajax({
        type: "POST",
        url: "incidencias/actualizarEstadoIncidenciasFolio/" + folio,
        contentType: "application/json",
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                toastr['error']("Las incidencias relacionadas por folio fueron eliminadas", "Aviso");
                $('#tabla_Incidencias').DataTable().ajax.reload();
                $("#eliminarIncidenciasModal").modal("hide");
            }
        },
        error: function (e) {
            alert(e);
        }
    });
}
/*=============================================
 ESTADO INCIDENCIA
 =============================================*/
function estadoIncidencia(id_incidencia, estado_incidencia_id) {
    event.preventDefault();

    if (estado_incidencia_id === 1) {
        estado_incidencia_id = 2;
    } else {
        estado_incidencia_id = 1;
    }

    $.ajax({
        type: "POST",
        url: "incidencias/cancelarIncidencia/" + id_incidencia + "/" + estado_incidencia_id,
        contentType: "application/json",
        success: function (data) {

            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                toastr['error']("Estatus de incidencia modificado", "Aviso");
                $('#tabla_Incidencias').DataTable().ajax.reload();
            }
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=============================================
 VER CANCELACIÓN
 ===============================================*/
function verCancelacion(id_incidencia) {
    $("#cancelarVacacionesModal").modal("toggle");
    $("#incidencia_cancelada").val(id_incidencia);
}

$("#cancelarVacaciones").submit(function (e) {
    event.preventDefault();
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');
    var motivo_cancelacion = $('#motivo_cancelacion').val();
    var referencia_cancelacion = $('#referencia_cancelacion').val();
    var id_incidencia_cancelada = $("#incidencia_cancelada").val();


    if ($.trim(motivo_cancelacion) === "") {
        return false;
    }
    if ($.trim(referencia_cancelacion) === "") {
        return false;
    }


    var datos = {"motivo_cancelacion": motivo_cancelacion, "referencia_cancelacion": referencia_cancelacion};
    $.ajax({
        type: "POST",
        url: "incidencias/editarVacacionesTrabajador/" + id_trabajador + "/" + id_incidencia_cancelada,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            toastr['success']("Se ha cancelado con éxito");
            estadoIncidencia(id_incidencia_cancelada, 1);
            $("#cancelarVacacionesModal").modal('hide');
            $("#referencia_cancelacion").html('');
            $("#incidencia_cancelada").html('');



        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos de Cancelación");
        }
    });

});

/*=============================================
 COMBO MOTIVO DE CANCELACIÓN
 ===============================================*/
function motivoCancelacion(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarMotivoCancelacion",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#motivo_cancelacion').empty().append("<option value=''>* Motivo de Cancelación </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_motivo_cancelacion === id) ? vselected = "selected" : vselected = " ";
                        $('#motivo_cancelacion').append('<option value="' + data[x].id_motivo_cancelacion + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Genera Pago: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

// Abrir Modal para guardar corrida de vacaciones
function asignarCorrida(id_incidencia){
    $('#incidencia_corrida_id').val(id_incidencia);
    $("#corridaModal").modal("toggle");
    $.ajax({
        type: "GET",
        url: "incidencias/buscarIncidenciaId/" + id_incidencia,
        dataType: 'json',
        success: function (data) {
            (data.data.observaciones === null) ? $('#corrida').val("") : $('#corrida').val(data.data.corrida_vacaciones);
        },
        error: function (e) {
            alert(e);
        }
    });
}

// Guarda la corrida de las vacaciones y se puede editar 
function guardarCorrida(){
    var id_incidencia = $('#incidencia_corrida_id').val();
    var corrida = $('#corrida').val();
    var datos = {"corrida_vacaciones":corrida};
    $.ajax({
        type: "POST",
        url: "incidencias/agregarCorrida/" + id_incidencia,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                toastr['success']("Los datos han sido actualizados", "Aviso", {progressBar: true, closeButton: true});
                $("#corridaModal").modal("hide");
                $('#tabla_Incidencias').DataTable().ajax.reload();
            }
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//--------Limpiar los datos de un formulario---------
$("#btn_Cancelar").on("click", function (event) {
    $("div#alerta").removeClass("#invalid-feedback");
    $("#FormGuardarIncidencia")[0].reset();
    $('#detalleVacaciones').html('');
    $('#detalleAyudas').html('');
});
$("#btn_Close").on("click", function (event) {
    $("div#alerta").removeClass("#invalid-feedback");
    $("#FormGuardarIncidencia")[0].reset();
    $('#detalleVacaciones').html('');
    $('#detalleAyudas').html('');
});

/*=============================================
 EXTIENDE EL DETALLE DE LAS VACACIONES
 =============================================*/

function detalleVacaciones() {
    var id_incidencia = $("#cmbInc").val();
    if (id_incidencia === "7") {
        $('#detalleVacaciones').html(
                "<div class='row m-lg-4'>" +
                "<div class='col'>" +
                "<input type='hidden' id='registro_periodo' name='registro_periodo' class='form-control' required>" +
                "<label class='form-label' for='addon-wrapping-left'>*Número de expediente</label>" +
                "<input id='expediente_id' style='width: 200px' name='expediente_id' type='number' class='form-control'  placeholder='' required disabled>" +
                "</div>" +
                "<div class='col'>" +
                "<label class='form-label' for='addon-wrapping-left'>*Tipo Contrato</label>" +
                "<input id='tipo_contrato' name='tipo_contrato' maxlength='10' type='text' class='form-control'  placeholder='' readonly required>" +
                "<input type='hidden' id='tipo_contrato_id' name='tipo_contrato_id' class='form-control' required>" +
                "<input type='hidden' id='trabajador_prestaciones' name='trabajador_prestaciones' class='form-control' required>" +
                "</div>" +
                "<div class='col'>" +
                "<label class='form-label' for='addon-wrapping-left'>*Fecha de Antigüedad</label>" +
                "<input id='fecha_antiguedad' name='fecha_antiguedad' type='text'  class='form-control'  placeholder='' readonly required >" +
                "</div>" +
                "<div class='col'>" +
                "<label class='form-label' for='addon-wrapping-left'>*Fecha de ingreso</label>" +
                "<input id='fecha_ingreso' name='fecha_ingreso' type='text'  class='form-control'  placeholder='' readonly required >" +
                "</div>" +
                "</div>" +
                "<div class='row  m-lg-4'>" +
                "<div class='col'>" +
                "<label class='form-label' for='addon-wrapping-left'>*Periodo Vacacional</label>" +
                "<input id='periodo_vacacional' name='periodo_vacacional' maxlength='10' type='number' class='form-control'  placeholder='' readonly oninput='calculaVacaciones()' required>" +
                "</div>" +
                "<div class='col'>" +
                "<label class='form-label' for='addon-wrapping-left'>*Años</label>" +
                "<input id='anios' name='anios' type='number' step='any' class='form-control'  placeholder='' readonly required >" +
                "</div>" +
                "<div class='col'>" +
                "<label class='form-label' for='addon-wrapping-left'>*Meses</label>" +
                "<input id='meses' name='meses' maxlength='10' type='text' class='form-control'  placeholder=''  readonly required >" +
                "</div>" +
                "<div class='col'>" +
                "<label class='form-label' for='addon-wrapping-left'>*Días</label>" +
                "<input id='dias' name='dias' maxlength='10' type='number' class='form-control'  placeholder='' readonly required>" +
                "</div>" +
                "</div>" +
                "<div class='row  m-lg-4'>" +
                "<div class='col'>" +
                "<label class='form-label' for='addon-wrapping-left'>*Días Vacaciones</label>" +
                "<input id='dias_vacaciones' name='dias_vacaciones' maxlength='10' type='text' class='form-control'  placeholder='' readonly required>" +
                "</div>" +
                "<div class='col'>" +
                "<label class='form-label' for='addon-wrapping-left'>*Días Paseos</label>" +
                "<input id='dias_paseos' name='dias_paseos' maxlength='10' type='number' class='form-control'  placeholder='' readonly required>" +
                "</div>" +
                "<div class='col'>" +
                "<label class='form-label' for='addon-wrapping-left'>*Días Totales</label>" +
                "<input id='dias_totales' name='dias_totales' maxlength='10' type='number' class='form-control'  placeholder='' readonly required>" +
                "</div>" +
                "<div class='col'>" +
                "<label class='form-label' for='addon-wrapping-left'>*Días Disponibles</label>" +
                "<input id='dias_disponibles' name='dias_disponibles' maxlength='10' type='number' class='form-control'  placeholder='' readonly required>" +
                "<input type='hidden' id='dias_disponibles_base' name='dias_disponibles_base' class='form-control' required>" +
                "</div>" +
                "</div>" +
                "<div class='row  m-lg-4'>" +
                "<div class='col'>" +
                "<input id='periodo_aplicacion' name='periodo_aplicacion' maxlength='10' type='hidden' class='form-control'  placeholder='' required>" +
                "<label class='form-label' for='addon-wrapping-left'>*Inicio Periodo Vacacional</label>" +
                "<input id='fecha_inicio_anio_periodo_vacacional' name='fecha_inicio_anio_periodo_vacacional' type='text' step='any' class='form-control'  placeholder='' readonly required >" +
                "</div>" +
                "<div class='col'>" +
                "<label class='form-label' for='addon-wrapping-left'>*Fin Periodo Vacacional</label>" +
                "<input id='fecha_fin_anio_periodo_vacacional' name='fecha_fin_anio_periodo_vacacional' type='text' class='form-control'  placeholder='' readonly required>" +
                "</div>" +
                "<div class='col'>" +
                "<label class='form-label' for='addon-wrapping-left'>*Días Proporcionales</label>" +
                "<input id='dias_proporcionales' name='dias_proporcionales' maxlength='10' type='number' class='form-control'  placeholder='' required>" +
                "</div>" +
                "<div class='col'>" +
                "<label class='form-label' for='addon-wrapping-left'>*Genera Pago</label>" +
                "<select id='genera_pago' name='genera_pago' maxlength='10' type='number' class='form-control'  style='width: 250px' placeholder='' required></select>" +
                "</div>" +
                "</div>"
                );
        const valores = window.location.search;
        const d = decodeURIComponent(valores);
        const urlParams = new URLSearchParams(d);
        var id_trabajador = urlParams.get('id_trabajador');
        buscarTrabajador(id_trabajador);
        obtenPrestaciones(id_trabajador);
        generaPago();
        buscarContrato(id_trabajador);
    } else {
        $('#detalleVacaciones').html('');
    }
}

/*=============================================
 EXTIENDE EL DETALLE DE LAS AYUDAS
 =============================================*/

function detalleAyudas() {
    var fechaActual = new Date();
    var formattedDate = fechaActual.getFullYear() + '-' + (fechaActual.getMonth() + 1).toString().padStart(2, '0') + '-' + fechaActual.getDate().toString().padStart(2, '0');
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');
    var id_incidencia = $("#cmbInc").val();
    if (id_incidencia === "3" || id_incidencia === "4") {
        $('#detalleAyudas').html(detalleHtmlAyudaFallecimientoAlumbramiento);
        parentesco();
        buscarContratoAyudas(id_trabajador);
        manejoDiasDePermisoEnAlumbramientoyFallecimiento(id_incidencia);
        //Se colocan los campos con la fecha de hoy
        $('#fecha_recepcion').val(formattedDate);
        $('#fecha_emision').val(formattedDate);
    } else if (id_incidencia === "33") {
        $('#detalleAyudas').html(detalleHtmlAyudaPermisoRetribuido);
        buscarContratoAyudas(id_trabajador);
    } else {
        $('#detalleAyudas').html('');
    }
}

function manejoDiasDePermisoEnAlumbramientoyFallecimiento (id_incidencia) {
    var fechasAgregadas = [];
// Evento de escucha al día agregado
    $('#btnAgregaDiaPermiso').on('click', function () {
        const fecha = $('#fechas_permisos').val();
        //Comprobación de fecha vacía
        if (fecha === '') {
            toastr["warning"]("Por favor, selecciona un día de permiso", "Advertencia", {progressBar: true, closeButton: true});
            return;
        }
        // Se define la cantifad máxima de días de permiso para cada incidencia
        const maxFechas = (id_incidencia === "3") ? 5 : (id_incidencia === "4") ? 6 : 0;
        // Verificar que no existan más fechas permitidas por el máximo de fechas egún la incidencia
        if (maxFechas > 0 && fechasAgregadas.length < maxFechas && fecha !== '' && !fechasAgregadas.includes(fecha)) {
            //Se asigna el id para cada día que se genera para obtener su valor posteriormente
            const idDia = 'dia' + (fechasAgregadas.length + 1);
            $('#listaFechas').append('<li id="' + idDia + '">' + fecha + '</li>');
            fechasAgregadas.push(fecha);
        } else {
            const errorMessage = (maxFechas > 0)
                    ? "No se pueden agregar más de " + maxFechas + " fechas o la fecha ya fue agregada."
                    : "Número máximo de fechas no definido para la incidencia.";
            toastr["warning"](errorMessage, "Advertencia", {progressBar: true, closeButton: true});
        }
    });

// Evento de escucha para eliminar las fechas agregadas
    $('#btnRemueveDiaPermiso').on('click', function () {
        const ultimoItem = $('#listaFechas li:last-child');
        if (ultimoItem.length > 0) {
            const fechaEliminada = ultimoItem.text();
            fechasAgregadas = fechasAgregadas.filter(fecha => fecha !== fechaEliminada);
            ultimoItem.remove();
            // Asigna nuevos ids a los elementos restantes
            $('#listaFechas li').each(function (index) {
                const nuevoId = 'dia' + (index + 1);
                $(this).attr('id', nuevoId);
            });
        }
    });
    //Evento de escucha de parentesco para manejar el fallecimiento de un hermano
    $("#parentesco").change(function () {
        var valorSeleccionado = $(this).val();
        /*Si el valor seleccionado es HERMANO se genera la incidencia del permiso por fallecimiento sin generar la ayuda correspondiente ya que la ayuda solo
         * se puede generar ayuda para familiares de línea directa */
        if (valorSeleccionado === '1' && id_incidencia === '4') {
            toastr["warning"]("El fallecimiento de un hermano no genera ayuda, sin embargo puede registrar la incidencia correspondiente para que se refleje en kárdex", " Aviso", {progressBar: true, closeButton: true, timeOut: 15000});
            bloquearInputsAlRegistrarPermisoFallecimientoHermano();
        } else {
            desbloquearInputsCualquierOtroFamiliar();
        }
    });
};

function bloquearInputsAlRegistrarPermisoFallecimientoHermano () {
    $("#fecha_recepcion").prop("disabled", true).val('');
    $("#fecha_emision").prop("disabled", true).val('');
    $("#nombre").prop("disabled", true).val('');
    $("#apellido_paterno").prop("disabled", true).val('');
    $("#apellido_materno").prop("disabled", true).val('');
    $("#origen").prop("disabled", true).val('');
    $("#fechas_permisos").prop("disabled", true).val('');
    $("#btnAgregaDiaPermiso").prop("disabled", true);
    $("#btnRemueveDiaPermiso").prop("disabled", true);
    $("#listaFechas").empty();
}

function desbloquearInputsCualquierOtroFamiliar () {
    $("#fecha_recepcion").prop("disabled", false);
    $("#fecha_emision").prop("disabled", false);
    $("#nombre").prop("disabled", false);
    $("#apellido_paterno").prop("disabled", false);
    $("#apellido_materno").prop("disabled", false);
    $("#origen").prop("disabled", false);
    $("#fechas_permisos").prop("disabled", false);
    $("#btnAgregaDiaPermiso").prop("disabled", false);
    $("#btnRemueveDiaPermiso").prop("disabled", false);
}

const detalleHtmlAyudaFallecimientoAlumbramiento =
        "<hr>" +
        "<div class='row m-lg-4'>" +
        "<div class='col'>" +
        "<input id='periodo_pago' name='periodo_pago' type='hidden' class='form-control'>" +
        "<input id='id_periodo_pago' name='periodo_pago' type='hidden' class='form-control'>" +
        "<input id='tipo_nomina_ayudas' name='tipo_nomina_ayudas' type='hidden' class='form-control' required >" +
        "<label class='form-label' for='addon-wrapping-left'>*Fecha de Recepción</label>" +
        "<input id='fecha_recepcion' style='width: 200px' name='fecha_recepcion' type='date' class='form-control'  placeholder='' required >" +
        "</div>" +
        "<div class='col'>" +
        "<label class='form-label' for='addon-wrapping-left'>*Fecha de Emisión</label>" +
        "<input id='fecha_emision' style='width: 200px' name='fecha_emision' type='date' class='form-control'  placeholder='' required >" +
        "</div>" +
        "<div class='col'>" +
        "<label class='form-label' for='addon-wrapping-left'>*Monto</label>" +
        "<input id='monto' name='monto' maxlength='10' type='number' class='form-control' style='width: 200px' placeholder='' readonly >" +
        "</div>" +
        "<div class='col'>" +
        "<label class='form-label' for='addon-wrapping-left'>*Parentesco</label>" +
        "<select id='parentesco' name='parentesco' maxlength='10' type='number' class='form-control'  style='width: 200px' placeholder='' required></select>" +
        "</div>" +
        "</div>" +
        "<div class='row m-lg-4'>" +
        "<div class='col'>" +
        "<label class='form-label' for='addon-wrapping-left'>*Nombre</label>" +
        "<input id='nombre' style='width: 200px' name='nombre' type='text' class='form-control' placeholder='' maxlength='30' required >" +
        "</div>" +
        "<div class='col'>" +
        "<label class='form-label' for='addon-wrapping-left'>*Apellido Paterno</label>" +
        "<input id='apellido_paterno' style='width: 200px' name='apellido_paterno' type='text' class='form-control' maxlength='30' placeholder='' required >" +
        "</div>" +
        "<div class='col'>" +
        "<label class='form-label' for='addon-wrapping-left'>*Apellido Materno</label>" +
        "<input id='apellido_materno' style='width: 200px' name='apellido_materno' type='text' class='form-control' maxlength='30' placeholder='' required >" +
        "</div>" +
        "<div class='col'>" +
        "<label class='form-label' for='addon-wrapping-left'>*Origen</label>" +
        "<input id='origen' style='width: 200px' name='origen' type='text' class='form-control' maxlength='30' placeholder='' required >" +
        "</div>" +
        "</div>" +
        "<div class='row m-lg-4'>" +
        "<div class='col'>" +
        "<label class='form-label' for='addon-wrapping-left'>*Fecha del Suceso</label>" +
        "<input id='fecha_evento' style='width: 200px' name='fecha_evento' type='date' class='form-control'  placeholder='' required >" +
        "</div>" +
        "</div>" +
        "<hr>" +
        "<div class='row m-lg-4'>" +
        "<div class='col-lg-3'>" +
        "<br>" +
        "<label class='form-label' for='addon-wrapping-left'>*Días de permiso</label>" +
        "<input id='fechas_permisos' style='width: 200px' name='fechas_permisos' type='date' class='form-control'  placeholder='' >" +
        "</div>" +
        "<div class='col-lg-2'>" +
        "<br>" +
        "<button id='btnAgregaDiaPermiso' type='button' class='btn btn-success'><i class='fa fa-plus-circle' aria-hidden='true'></i></button>" +
        "<br>" +
        "<br>" +
        "<button id='btnRemueveDiaPermiso' type='button' class='btn btn-danger'><i class='fa fa-minus-circle' aria-hidden='true'></i></button>" +
        "</div>" +
        "<div class='col'>" +
        "<br>" +
        "<label class='form-label' for='addon-wrapping-left'>*Días con permiso</label>" +
        "<div class='card'>" +
        "<div class='card-body'>" +
        "<ul id='listaFechas'></ul>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>";

const detalleHtmlAyudaPermisoRetribuido =
        "<hr>" +
        "<div class='row m-lg-4'>" +
        "<div class='col'>" +
        "<input id='periodo_pago' name='periodo_pago' type='hidden' class='form-control'>" +
        "<input id='id_periodo_pago' name='periodo_pago' type='hidden' class='form-control'>" +
        "<input id='tipo_nomina_ayudas' name='tipo_nomina_ayudas' type='hidden' class='form-control' required >" +
        "<label class='form-label' for='addon-wrapping-left'>*Fecha de Recepción</label>" +
        "<input id='fecha_recepcion' style='width: 200px' name='fecha_recepcion' type='date' class='form-control'  placeholder='' required >" +
        "</div>" +
        "<div class='col'>" +
        "<label class='form-label' for='addon-wrapping-left'>*Fecha de Emisión</label>" +
        "<input id='fecha_emision' style='width: 200px' name='fecha_emision' type='date' class='form-control'  placeholder='' required >" +
        "</div>" +
        "<div class='col'>" +
        "<label class='form-label' for='addon-wrapping-left'>*Monto</label>" +
        "<input id='monto' name='monto' maxlength='10' type='number' class='form-control' style='width: 200px' placeholder='' readonly >" +
        "</div>" +
        "<div class='col'>" +
        "<label class='form-label' for='addon-wrapping-left'>*Origen</label>" +
        "<input id='origen' style='width: 200px' name='origen' type='text' class='form-control'  placeholder='' required >" +
        "</div>" +
        "</div>";

function asignaHorarios() {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');
    window.location.href = 'trabajadorHorario?id_trabajador=' + id_trabajador;
}

function loadPage() {
    location.reload();
}