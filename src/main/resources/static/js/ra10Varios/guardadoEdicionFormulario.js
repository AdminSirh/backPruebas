document.addEventListener('DOMContentLoaded', () => {
    listarPuestos();
});
//Lista los puestos provenientes del catalogo de puestos
function listarPuestos() {
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
            {data: "nivel", defaultContent: ""},
            {data: "sueldo_diario", defaultContent: ""},
            {data: "sueldo_hora", defaultContent: ""},

            {data: "", sTitle: "Suplencia", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" onclick="asignarSuplencia(' + r.sueldo_diario + ', \'' + r.sueldo_hora + '\',  \'' + r.sueldoHora7 + '\', \'' + r.puesto + '\' , \'' + r.nivel + '\',\'' + r.id_puesto + '\')" > ' + '<span class="fa fa-edit"> </span> Generar Suplencia</button>';
                    return he;
                }
            }
        ]
    });
}
//Se ejecuta cuando al usuario selecciona un puesto para mostrar los datos que estará cubriendo
function asignarSuplencia(sueldoDiario, sueldoHora8, sueldoHora7, puesto, nivel, id_puesto) {
    let sueldoHora8Actual = parseFloat($('#campo_sueldo_hora_8').val());
    let sueldoHora7Actual = parseFloat($('#campo_sueldo_hora_7').val());
    let sueldoHora8Suplencia = parseFloat(sueldoHora8);
    let sueldoHora7Suplencia = parseFloat(sueldoHora7);
    if (sueldoHora8Actual >= sueldoHora8Suplencia) {
        $("#suplenciaModal").modal('hide');
        toastr["error"]('El sueldo por hora (8) de la suplencia ' + '($' + sueldoHora8Suplencia + ')' + ' debe ser mayor al sueldo por hora del trabajador ' + '($' + sueldoHora8Actual + ')', 'Error', {progressBar: true, closeButton: true});
        return;
    }
    if (sueldoHora7Actual >= sueldoHora7Suplencia) {
        $("#suplenciaModal").modal('hide');
        toastr["error"]('El sueldo por hora (7) de la suplencia ' + '($' + sueldoHora7Suplencia + ')' + ' debe ser mayor al sueldo por hora del trabajador ' + '($' + sueldoHora7Actual + ')', 'Error', {progressBar: true, closeButton: true});
        return;
    }
    $('#id_puesto_ausente').val(id_puesto);
    $('#inputNivel').val(nivel);
    $('#inputPuesto').val(puesto);
    //Se oculta el modal
    $("#suplenciaModal").modal('hide');
}
;

//Aquí se obtienen todos los campos necesarios para el guardado
$('#frmMovimiento').submit(function (event) {
    console.log(flagTrabajador, flagPuesto);
    event.preventDefault();
    let valoresNoVacios;
    let fiValidacion = new Date($('#fechaInicialSuplencia').val());
    let ffValidacion = new Date($('#fechaFinalSuplencia').val());
    let fechaInicial = $('#fechaInicialSuplencia').val();
    let fechaFinal = $('#fechaFinalSuplencia').val();
    let periodoGen = $('#periodo_generacion').val();
    let booleanOperativo = $('#operativo').val();
    let booleanAdministrativo = $('#administrativo').val();
    //idTrabajador del principal
    let idTrabajadorAusente = $('#trabajador_ausente_id').val();
    let cmbMotivo = $('#cmb_motivo').val();
    let motivo = $('#campo_motivo').val();
    let campoExpedienteAusente = $('#campo_expediente_ausente').val();
    let idPuestoACubrir = $('#id_puesto_ausente').val();
    // Para validar que no se envíen datos necesarios vacíos para cada caso, personal ausente o puesto
    if (flagTrabajador) {
        valoresNoVacios = [campoExpedienteAusente, fechaInicial, fechaFinal, periodoGen, motivo, cmbMotivo];
    }
    if (flagPuesto) {
        valoresNoVacios = [fechaInicial, fechaFinal, periodoGen, motivo, cmbMotivo, idPuestoACubrir];
    }
    const valoresGuardar = [fechaInicial, fechaFinal, periodoGen, motivo, campoExpedienteAusente,
        booleanOperativo, booleanAdministrativo, idTrabajador, idTrabajadorAusente, cmbMotivo, idPuestoACubrir];
    let valido = true;
    valoresNoVacios.forEach(valor => {
        if ($.trim(valor) === "") {
            valido = false;
            return false;
        }
    });
    // Verifica que la fecha inicial no sea mayor que la fecha final
    if (fiValidacion > ffValidacion) {
        toastr["warning"]("La fecha inicial no puede ser mayor que la fecha final", "Aviso", {progressBar: true, closeButton: true});
        return;
    }
    //Si pasó las validaciones
    if (valido) {
        obtenerPeriodosEntreFechas(fechaInicial, fechaFinal, valoresGuardar);
    } else {
        toastr["warning"]("Por favor, complete todos los campos requeridos", "Aviso", {progressBar: true, closeButton: true});
    }
});

// Función para generar el evento de escucha al campo Expediente del Personal Ausente
function generarEventoExpediente($input) {
    $input.on("input", function () {
        const buscarTrabajadorAusente = (valor) => {
            $.ajax({
                type: "GET",
                url: "trabajador/buscarTrabajador_NumExpediente/" + valor,
                dataType: 'json',
                success: (data) => {
                    if ($.isEmptyObject(data.data)) {
                        toastr["warning"]("No se encontró información del trabajador ausente", "Aviso", {progressBar: true, closeButton: true});
                        limpiarPersonalAusente();
                    } else {
                        let idTrabajadorBuscado = data.data.id_trabajador;
                        idTrabajador = parseInt(idTrabajador, 10);
                        if (idTrabajadorBuscado === idTrabajador) {
                            toastr["error"]("No se puede colocar al mismo trabajador", "Error", {progressBar: true, closeButton: true});
                            limpiarPersonalAusente();
                        } else {
                            const {nombre, apellido_paterno: apellidoPaterno, apellido_materno: apellidoMaterno} = data.data.persona;
                            const nombreCompleto = `${nombre} ${apellidoPaterno} ${apellidoMaterno}`;
                            $('#campo_nombre_ausente').val(nombreCompleto);
                            $('#trabajador_ausente_id').val(idTrabajadorBuscado);
                            obtenerInformacionPuestoTrabajador(idTrabajadorBuscado, '#campo_puesto_trabajador_ausente', '#campo_sueldo_hora_8_ausente', '#campo_sueldo_hora_7_ausente', '#nivel_ausente', '#id_puesto_ausente');
                        }
                    }
                },
                error: (e) => {
                    toastr["error"]("Ocurrió un error al obtener información del trabajador" + e, "Error", {progressBar: true, closeButton: true});
                }
            });
        };

        const valor = $(this).val().toString();
        if (valor.length === 5) {
            buscarTrabajadorAusente(valor);
        }
        if (valor.length === 0) {
            limpiarPersonalAusente();
        }
    });
}

//Limpia los valores por si fueron seleccionados antes para evitar generar registros con residuos
function limpiarDatosTrabajadorAusente() {
    $('#id_puesto_ausente').val('');
    $('#trabajador_ausente_id').val('');
}

let flagPuesto = false;
let flagTrabajador = false;

$('input[name="opcionTrabajadorPuesto"]').on('change', function () {
    //Se selecciona el radio de personal ausente
    if ($(this).val() === 'pa') {
        const detalleHtmlTrabajador = `
               <div class="row">
                                                <div class="col-lg-3">
                                                    <label for="campo_expediente_ausente" class="m-0 font-weight-bold text-dark">*Expediente:</label>
                                                    <input class="form-control" type="number" step=1 min="0"  max="9999999" id="campo_expediente_ausente" name="campo_expediente_ausente" style="width: 150px; height: 25px;"  required>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="campo_nombre_ausente" class="m-0 font-weight-bold text-dark">Trabajador:</label>
                                                    <input type="text" id="campo_nombre_ausente" class="form-control text-center" style="width: 150px; height: 25px;" readonly="">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="campo_puesto_trabajador_ausente" class="m-0 font-weight-bold text-dark">Puesto:</label>
                                                    <input type="text" id="campo_puesto_trabajador_ausente" class="form-control text-center" style="width: 150px; height: 25px;" readonly="">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label for="nivel_ausente" class="m-0 font-weight-bold text-dark">Nivel:</label>
                                                    <input type="text" id="nivel_ausente" class="form-control text-center" style="width: 100px; height: 25px;" readonly="">
                                                </div>
                                            </div>
                                               <!--     SEGUNDA HILERA AUSENTE -->
                                            <br>
                                            <div class="row">
                                                <div class="col-lg-2">
                                                    <label for="campo_sueldo_hora_8_ausente" class="m-0 font-weight-bold text-dark">Sldo.Hr.8:</label>
                                                    <input type="text" id="campo_sueldo_hora_8_ausente" class="form-control text-center" style="width: 100px; height: 25px;" readonly="" >
                                                </div>
                                                <div class="col-lg-2">
                                                    <label for="campo_sueldo_hora_7_ausente" class="m-0 font-weight-bold text-dark">Sldo.Hr.7:</label>
                                                    <input type="text" id="campo_sueldo_hora_7_ausente" class="form-control text-center" style="width: 100px; height: 25px;" readonly="" >
                                                </div>
                                                    <!--  <div class="col-lg-3">
                                                      <label for="tipo_ausente" class="m-0 font-weight-bold text-dark">Tipo:</label>
                                                    <input type="text" id="tipo_ausente" class="form-control text-center" style="width: 100px; height: 25px;" readonly="">
                                                </div> -->
                                            </div>
                                            <br>`;
        //Se selecciona el radio de puesto
        $('#detalleTrabajadoroPuesto').html(detalleHtmlTrabajador);
        //Evento de escucha al expediente del personal ausente
        const $campoExpediente = $("#campo_expediente_ausente");
        generarEventoExpediente($campoExpediente);
        limpiarDatosTrabajadorAusente();
        //Banderas para determinar campos obliogarotios en cada caso
        flagTrabajador = true;
        flagPuesto = false;
    } else if ($(this).val() === 'pu') {
        const detalleHtmlPuesto = `
          <div class="row">
            <div class="col-lg-3">
                  <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#suplenciaModal"><span class="fa fa-edit"></span>Seleccionar puesto</button>
            </div>
            <div class="col-lg-4">
                 <label for="inputNivel" class="m-0 font-weight-bold text-dark">*Nivel:</label>
                <input type="text" id="inputNivel" class="form-control text-center" style="width: 150px; height: 25px;" onkeydown="detectarInput(event)" required  >
            </div>
            <div class="col-lg-3">
              <label for="inputPuesto" class="m-0 font-weight-bold text-dark">*Puesto:</label>
                <input type="text" id="inputPuesto" class="form-control text-center" style="width: 150px; height: 25px;" onkeydown="detectarInput(event)"" required >
            </div>
        </div>
                <br>`;
        $('#detalleTrabajadoroPuesto').html(detalleHtmlPuesto);
        limpiarDatosTrabajadorAusente();
        //Banderas para determinar campos obliogarotios en cada caso
        flagTrabajador = false;
        flagPuesto = true;
    }
});

//Por si el usuario intenta agregar campos manualmente, se le envia um mensaje indicando como debe de seleccionar los puestos
function detectarInput() {
    toastr["warning"]('De clic en el botón "Seleccionar Puesto" para colocar de manera automática estos campos', 'Aviso', {progressBar: true, closeButton: true});
    event.preventDefault();
}

// Simular un click en el radio button "Personal Ausente"
$('#trabajador').click();
//Se obtienen los periodos que abarca a partir de la fecha inicial y fecha final
function obtenerPeriodosEntreFechas(fechaInicial, fechaFinal, valoresGuardar) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscaLapsoPeriodos/" + 1 + "/" + fechaInicial + "/" + fechaFinal,
        dataType: 'json',
        async: false,
        success: function (periodosData) {
            if ($.isEmptyObject(periodosData)) {
                toastr["warning"]("No se encontraron periodos...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                //Abarca varios periodos, se pregunta al usuario si desea generar un registro para cada periodo
                if (periodosData.data.length > 1) {
                    $('#modalVariosPeriodos').modal('show');
                    //Detecta el submit en el modal de confirmación para abarcar varios periodos
                    $("#modalVariosPeriodos").off('submit').on('submit', function (event) {
                        event.preventDefault();
                        guardarMovimientosParaPeriodos(periodosData.data, valoresGuardar);
                        $("#modalVariosPeriodos").modal("hide");
                    });
                } else {
                    guardarMovimientosParaPeriodos(periodosData.data, valoresGuardar);
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al obtener periodos: " + e, "Error", {progressBar: true, closeButton: true});
        }
    });
}
//Guarda movimiento para cada periodo encontrado entre las fechas
function guardarMovimientosParaPeriodos(periodos, valoresGuardar) {
    periodos.forEach(periodo => {
        guardarMovimientoPersonal(periodo, valoresGuardar);
    });
}
//Hace el guardado en la tabla de ra10 para cada periodo abarcando
function guardarMovimientoPersonal(periodo, valoresGuardar) {
    let datos = {"fecha_inicial": valoresGuardar[0],
        "fecha_final": valoresGuardar[1],
        "periodo_generacion": periodo,
        "periodo_aplicacion": valoresGuardar[2],
        "operativo": valoresGuardar[5],
        "administrativo": valoresGuardar[6],
        "comentario": valoresGuardar[3],
        "trabajador_id": idTrabajador,
        "trabajador_ausente_id": parseInt(valoresGuardar[8], 10),
        "cat_Puesto": {
            "id_puesto": parseInt(valoresGuardar[10], 10)
        },
        "motivo_id": parseInt(valoresGuardar[9], 10)
    };
    $.ajax({
        type: "POST",
        url: "ra10/guardarRa10",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            toastr['success']("Se ha agregado corretcamente el elemento", "Aviso");
            limpiarValidaciones();
            limpiarPersonalAusente();
            limpiarInformacionAdicional();
        },
        error: function (xhr, status, error) {
            let mensaje;
            try {
                mensaje = JSON.parse(xhr.responseText);
            } catch (e) {
                mensaje = xhr.responseText;
            }
            toastr["warning"]("Error al agregar elemento: " + mensaje, "Error", {progressBar: true, closeButton: true});
        }
    });
}

function limpiarInformacionAdicional() {
    $('#campo_motivo').val('');
    $('#fechaInicialSuplencia').val('');
    $('#fechaFinalSuplencia').val('');
}

function limpiarValidaciones() {
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
}

function limpiarPersonalAusente() {
    $('#campo_expediente_ausente').val('');
    $('#campo_nombre_ausente').val('');
    $('#campo_puesto_trabajador_ausente').val('');
    $('#nivel_ausente').val('');
    $('#campo_sueldo_hora_8_ausente').val('');
    $('#campo_sueldo_hora_7_ausente').val('');
    $('#tipo_ausente').val('');
}