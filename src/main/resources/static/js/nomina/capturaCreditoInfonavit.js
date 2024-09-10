$(document).ready(function () {
    //Paso de id del trabajador a las funciones que requieren el mismo
    vizualizarTrabajador(new URLSearchParams(decodeURIComponent(window.location.search)).get('id_trabajador'));
    cargaTablaCreditoInfonavit(new URLSearchParams(decodeURIComponent(window.location.search)).get('id_trabajador'));
    vincularEventosInputs();
    //Manejador de click en los botones de Radios y sus inputs correspomdientes
    handleRadioClick();
});

function vincularEventosInputs() {
    //Bloqueo de inputs para que el usuario de click en el parámetro que desea editar (Tipo de crédito)
    $("#inputCuotaFija, #inputCuotaPesos, #inputPorcentaje, #inputCuotaFija_edit, #inputCuotaPesos_edit, #inputPorcentaje_edit ").prop("disabled", true);
    $("#inputCuotaFija, #inputCuotaPesos, #inputCuotaFija_edit, #inputCuotaPesos_edit").keyup(function (e) {
        $(this).val($(this).val().replace(/[^0-9.]/g, ''));
        $('#agregrarCreditoModal').on('hidden.bs.modal', function () {
            limpiar();
            valorSeleccionado = '';
        });
        $('#editarCreditoModal').on('hidden.bs.modal', function () {
            limpiar();
            valorSeleccionado = '';
        });
        //Destrucción de tabla para traer más datos en la misma sesión
        $('#modalHistorico').on('hidden.bs.modal', function () {
            $('#tablaCreditosHistoricos').DataTable().destroy();
        });
    });
}

//Variable global con el Identificador del Trabajador
const id_trabajador = (new URLSearchParams(decodeURIComponent(window.location.search)).get('id_trabajador'));

//****************************VISTA DE LOS ELEMENTOS********************************
//Coloca dentro del HTML el nombre y el expediente del trabajador
const vizualizarTrabajador = (id_trabajador) => {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + id_trabajador,
        dataType: "json",
        success: (data) => {
            $('#nombre_Trabajador').text(`Nombre: ${data.data.persona.nombre} ${data.data.persona.apellido_paterno}`);
            $('#numero_Expediente').text(`No. Expediente: ${data.data.cat_expediente.numero_expediente}`);
        },
        error: (e) => {
            alert(e);
        }
    });
};

//Realiza la carga del listado de Créditos históricos del trabajador correpondiente
function cargaTablaHistorico() {
    //Se destruye la tabla cada que se llama a la función
    if ($.fn.DataTable.isDataTable('#tablaCreditosHistoricos')) {
        $('#tablaCreditosHistoricos').DataTable().destroy();
    }
    tablaCreditosHistoricos = $('#tablaCreditosHistoricos').dataTable({
        "ajax": {
            url: "creditoInfonavit/buscarCreditoHistoricoInfonavitTrabajador/" + id_trabajador,
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
            {data: "credito_infonavit_id"},
            {data: "numero_de_credito"},
            {data: "cat_tipo_credito.descripcion"},
            {data: "descripcion_tipo_credito"},
            {data: "fecha_de_recepcion"},
            {data: "fecha_de_aplicacion"},
            {data: "cat_Estatus_Credito.descripcion"},
            {data: "fecha_movimiento"},
            {data: "fecha_evento",
                defaultContent: "N/A"},
            {data: "cat_Motivo_Baja.descripcion",
                defaultContent: "N/A"}
        ],

        //Comprueba si la tabla está vacía para mandar un aviso
        "fnInitComplete": function () {
            var table = $('#tablaCreditosHistoricos').DataTable();
            var data = table.rows().data();
            if (data.length === 0) {
                toastr["warning"]("No se encontró Histórico de Créditos para este trabajador");
            }
        }
    });
}
//Carga la lista de créditos Activos o Inactivos que estén relacionados con el ID del trabajador
function cargaTablaCreditoInfonavit(id_trabajador) {
    tablaCreditos = $('#tablaCreditos').dataTable({
        "ajax": {
            url: "creditoInfonavit/buscarCreditoInfonavitTrabajador/" + id_trabajador,
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
            {data: "id_credito_infonavit"},
            {data: "numero_de_credito"},
            {data: "cat_tipo_credito.descripcion"},
            {data: "descripcion_tipo_credito"},
            {data: "fecha_de_recepcion"},
            {data: "fecha_de_aplicacion"},
            {data: "cat_Estatus_Credito.descripcion"},
            {data: "fecha_movimiento"},
            {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#editarCreditoModal" onclick="editarCreditoInfonavit(' + r.id_credito_infonavit + ') ;vizualizarCreditoInfonavit(' + r.id_credito_infonavit + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                    return he;
                }
            }
        ],
        //Comprueba si la tabla está vacía para mandar un aviso
        "fnInitComplete": function () {
            var table = $('#tablaCreditos').DataTable();
            var data = table.rows().data();
            if (data.length === 0) {
                toastr["warning"]("No se encontraron créditos activos para este trabajador");
            }
        }
    });
}

//Coloca los datos en el modal de edición para podes hacer cambios sobre los mismos campos guardados
function vizualizarCreditoInfonavit(id_credito_infonavit) {
    //Lenado de inputs y campos correspondientes si el trabajador ya tiene un crédito asignado
    $.ajax({
        type: "GET",
        url: "creditoInfonavit/buscarCreditoInfonavit/" + id_credito_infonavit,
        dataType: "json",
        success: (data) => {
            //Se selecciona el radio que corresponda dependiendo del tipo de credito que se encuentre en la DB

            // Cache Selectors
            $("#id_credito_infonavit").val(id_credito_infonavit);
            var porcentajeRadio = $("#porcentajeRadio_edit");
            var inputPorcentaje = $('#inputPorcentaje_edit');
            var inputCuotaPesos = $("#inputCuotaPesos_edit");
            var inputCuotaFija = $("#inputCuotaFija_edit");
            var cuotaFijaRadio = $("#cuotaFijaRadio_edit");
            var cuotaPesosRadio = $("#cuotaPesosRadio_edit");
            // Switch basado en data.cat_tipo_credito.id_tipo_credito
            switch (data.data.cat_tipo_credito.id_tipo_credito) {
                case 1:
                    porcentajeRadio.prop("checked", true);
                    inputPorcentaje.val(data.data.descripcion_tipo_credito);
                    inputCuotaPesos.prop("disabled", true);
                    inputCuotaFija.prop("disabled", true);
                    break;
                case 2:
                    cuotaFijaRadio.prop("checked", true);
                    inputCuotaFija.val(data.data.descripcion_tipo_credito);
                    inputCuotaPesos.prop("disabled", true);
                    inputPorcentaje.prop("disabled", true);
                    break;
                case 3:
                    cuotaPesosRadio.prop("checked", true);
                    inputCuotaPesos.val(data.data.descripcion_tipo_credito);
                    inputCuotaFija.prop("disabled", true);
                    inputPorcentaje.prop("disabled", true);
                    break;
                default:

            }

            //Asignación de valores provenientes de la base de datos al resto de Inputs
            $('#inputNoCredito_edit').val(data.data.numero_de_credito);
            $('#fechaRecepcion_edit').val(data.data.fecha_de_recepcion);
            $('#fechaAplicacion_edit').val(data.data.fecha_de_aplicacion);
        },
        error: (e) => {
            alert(e);
        }
    });
}

// Código para radio buttons y selección de inputs a editar con base a los radios
let valorSeleccionado = '';

function handleRadioClick() {
    const $inputCuotaFija = $("#inputCuotaFija, #inputCuotaFija_edit");
    const $inputCuotaPesos = $("#inputCuotaPesos, #inputCuotaPesos_edit");
    const $inputPorcentaje = $("#inputPorcentaje, #inputPorcentaje_edit");
    const $porcentajeRadio = $("#porcentajeRadio, #porcentajeRadio_edit");
    const $cuotaFijaRadio = $("#cuotaFijaRadio, #cuotaFijaRadio_edit");
    const $cuotaPesosRadio = $("#cuotaPesosRadio, #cuotaPesosRadio_edit");
    $porcentajeRadio.on("click", function () {
        if (!$(this).prop("checked")) {
            valorSeleccionado = "";
            $inputPorcentaje.prop("disabled", true);
            $inputCuotaFija.val("");
            $inputCuotaPesos.val("");
            return;
        }
        valorSeleccionado = $(this).val();
        $inputCuotaFija.prop("disabled", true);
        $inputCuotaPesos.prop("disabled", true);
        $inputPorcentaje.prop("disabled", false);
        $inputCuotaFija.val("");
        $inputCuotaPesos.val("");
    });

    $cuotaFijaRadio.on("click", function () {
        if (!$(this).prop("checked")) {
            valorSeleccionado = "";
            $inputCuotaFija.prop("disabled", true);
            $inputPorcentaje.val("");
            $inputCuotaPesos.val("");
            return;
        }
        valorSeleccionado = $(this).val();
        $inputPorcentaje.prop("disabled", true);
        $inputCuotaPesos.prop("disabled", true);
        $inputCuotaFija.prop("disabled", false);
        $inputPorcentaje.val("");
        $inputCuotaPesos.val("");
    });

    $cuotaPesosRadio.on("click", function () {
        if (!$(this).prop("checked")) {
            valorSeleccionado = "";
            $inputCuotaPesos.prop("disabled", true);
            $inputPorcentaje.val("");
            $inputCuotaFija.val("");
            return;
        }
        valorSeleccionado = $(this).val();
        $inputCuotaFija.prop("disabled", true);
        $inputPorcentaje.prop("disabled", true);
        $inputCuotaPesos.prop("disabled", false);
        $inputPorcentaje.val("");
        $inputCuotaFija.val("");
    });
}

//****************************AGREGADO Y MODIFICADO DE CRÉDITO********************************

//Guarda un nuevo crédito para el trabajador
$('#frm_guarda_credito').on('submit', function (event) {
    event.preventDefault();
    let numero_de_credito = $("#inputNoCredito").val();
    let descripcion_tipo_credito; //Cualquiera de los inputs que estén seleccionados, se va a jalar el valor dependiendo del input seleccionado. 
    let fecha_de_recepcion = $("#fechaRecepcion").val();
    let fecha_de_aplicacion = $("#fechaAplicacion").val();
    let id_estatus_credito = 2; //Activar al guardar
    let fecha_evento; //A definir de donde se obtiene este valor

    //Selección de input a guardar dependiendo del radio seleccionado
    switch (valorSeleccionado) {
        case "1":
            descripcion_tipo_credito = $("#inputPorcentaje").val();
            break;
        case "2":
            descripcion_tipo_credito = $("#inputCuotaFija").val();
            break;
        case "3":
            descripcion_tipo_credito = $("#inputCuotaPesos").val();
            break;
        default:
            toastr["warning"]("Selecciona un tipo de crédito", "Aviso", {progressBar: true, closeButton: true});
            return false;
    }

    if (!descripcion_tipo_credito.trim()) {
        toastr['warning']('Debe colocar el valor para el tipo de crédito', 'Advertencia');
        return;
    }

    if (!numero_de_credito.trim()) {
        toastr['warning']('Debe colocar el número de crédito', 'Advertencia');
        return;
    }
    if (!fecha_de_recepcion.trim()) {
        toastr['warning']('Debe colocar la fecha de recepcion', 'Advertencia');
        return;
    }
    if (!fecha_de_aplicacion.trim()) {
        toastr['warning']('Debe colocar la fecha de aplicación', 'Advertencia');
        return;
    }

    let datos = {"trabajador": {"id_trabajador": id_trabajador}, "numero_de_credito": numero_de_credito, "cat_tipo_credito": {"id_tipo_credito": valorSeleccionado}, "descripcion_tipo_credito": descripcion_tipo_credito,
        "fecha_de_recepcion": fecha_de_recepcion, "fecha_de_aplicacion": fecha_de_aplicacion, "cat_Estatus_Credito": {"id_estatus_credito": id_estatus_credito}, "fecha_evento": fecha_evento};

    $.ajax({
        type: "POST",
        url: "creditoInfonavit/GuardarCreditoInfonavit",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(datos),
        success: (data) => {
            toastr['success'](data.data, 'Se ha agregado correctamente el Crédito Infonavit');
            $("#agregrarCreditoModal").modal('hide');
            $('#tablaCreditos').DataTable().ajax.reload();
        },
        error: (e) => {
            toastr['error']('Ocurrió un error' + e, 'Error');
        }
    });
});

/****************************************************************************
 LIMPIEZA DE INPUTS AGREGA Y EDITA 
 ****************************************************************************/

function limpiar() {
    $('#inputPorcentaje, #inputPorcentaje_edit, #inputCuotaFija, #inputCuotaFija_edit, #inputCuotaPesos, #inputCuotaPesos_edit')
            .val('')
            .prop('disabled', true);
    $('#inputNoCredito, #inputNoCredito_edit').val('');
    $('#porcentajeRadio, #porcentajeRadio_edit, #cuotaFijaRadio, #cuotaFijaRadio_edit, #cuotaPesosRadio, #cuotaPesosRadio_edit').prop('checked', false);
}

//Se guarda la fecha generada en edición para recuperarla si se da de baja un credito que fue editado 
let fecha_movimiento_global;

//Edición de un crédito del trabajador
function editarCreditoInfonavit(id_credito_infonavit) {
    //Cache Selectors
    const $inputPorcentaje_edit = $('#inputPorcentaje_edit');
    const $inputCuotaFija_edit = $('#inputCuotaFija_edit');
    const $inputCuotaPesos_edit = $('#inputCuotaPesos_edit');
    const $porcentajeRadio_edit = $('#porcentajeRadio_edit');
    const $cuotaFijaRadio_edit = $('#cuotaFijaRadio_edit');
    const $cuotaPesosRadio_edit = $('#cuotaPesosRadio_edit');
    //Almacena los valores que trae el form originalmente para realizar el guardado del histórico
    let originalFormData;
    setTimeout(function () {
        originalFormData = {
            // GUARDADO DE CAMPOS ORIGINALES
            fechaAplicacionHistorico: $("#fechaAplicacion_edit").val(),
            fechaRecepcionHistorico: $("#fechaRecepcion_edit").val(),
            numeroCreditoHistorico: $("#inputNoCredito_edit").val(),
            valorSeleccionadoHistorico: getSelectedValue($porcentajeRadio_edit, $cuotaFijaRadio_edit, $cuotaPesosRadio_edit),
            descTipoCredito: getSelectedValueInput($porcentajeRadio_edit, $inputPorcentaje_edit, $cuotaFijaRadio_edit, $inputCuotaFija_edit, $cuotaPesosRadio_edit, $inputCuotaPesos_edit)
        };
    }, 500);
    //Al cerrar el modal se invalida el identificador para evitar errores al realizar ediciones continuas
    $('#editarCreditoModal').on('hidden.bs.modal', function () {
        id_credito_infonavit = null;
        //Limpieza de inputs al cerrar el modal
        $inputPorcentaje_edit.val("");
        $inputCuotaFija_edit.val("");
        $inputCuotaPesos_edit.val("");
        originalFormData = {};
    });
    $("#guardaEditado").click(function (e) {
        event.preventDefault();
        let numero_de_credito = $("#inputNoCredito_edit").val();
        let descripcion_tipo_credito = $("#descripcionTipoCredito_edit").val();
        let fecha_de_recepcion = $("#fechaRecepcion_edit").val();
        let fecha_de_aplicacion = $("#fechaAplicacion_edit").val();
        let fecha_movimiento = new Date().toISOString().slice(0, 10);
        let fechaEvento; //Solo se asigna al dar de baja un crédito
        fecha_movimiento_global = fecha_movimiento; //Para su uso fuera de la función

        //Comprobación de radio Activo al darle click
        switch (valorSeleccionado) {
            case "1":
                descripcion_tipo_credito = $inputPorcentaje_edit.val();
                break;
            case "2":
                descripcion_tipo_credito = $inputCuotaFija_edit.val();
                break;
            case "3":
                descripcion_tipo_credito = $inputCuotaPesos_edit.val();
                break;
            default:
                if ($porcentajeRadio_edit.prop("checked")) {
                    valorSeleccionado = 1;
                    descripcion_tipo_credito = $inputPorcentaje_edit.val();
                } else if ($cuotaFijaRadio_edit.prop("checked")) {
                    valorSeleccionado = 2;
                    descripcion_tipo_credito = $inputCuotaFija_edit.val();
                } else if ($cuotaPesosRadio_edit.prop("checked")) {
                    valorSelecionado = 3;
                    descripcion_tipo_credito = $inputCuotaPesos_edit.val();
                }
        }
        let datos = {"trabajador": {"id_trabajador": id_trabajador}, "numero_de_credito": numero_de_credito, "cat_tipo_credito": {"id_tipo_credito": valorSeleccionado}, "descripcion_tipo_credito": descripcion_tipo_credito,
            "fecha_de_recepcion": fecha_de_recepcion, "fecha_de_aplicacion": fecha_de_aplicacion, "fecha_movimiento": fecha_movimiento};
        if (id_credito_infonavit !== null) {
            $.ajax({
                type: "POST",
                url: "creditoInfonavit/actualizarCreditoInfonavit/" + id_credito_infonavit,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(datos),
                success: (data) => {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información para modificar", "Aviso", {progressBar: true, closeButton: true});
                    }
                    $("#editarCreditoModal").modal('hide');
                    $('#tablaCreditos').DataTable().ajax.reload();
                    toastr['success']("Se modificó con éxito");
                    //Llamada a función para registrar histórico con motivo baja nulo, ya que ese se coloca en la función bajaCredito()
                    let motivo_bajaEdit = 0;
                    //Se manda un uno en estatusCredito para inicar que este registro editado pasa a ser un histórico
                    guardarHistoricoCredito(id_trabajador, id_credito_infonavit, originalFormData.descTipoCredito, 1, originalFormData.fechaAplicacionHistorico, originalFormData.fechaRecepcionHistorico,
                            fecha_movimiento, originalFormData.numeroCreditoHistorico, originalFormData.valorSeleccionadoHistorico, fechaEvento, /*motivo_bajaEdit*/);
                },
                error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});

                }
            });
        }
    });
}

function getSelectedValue($porcentajeRadio_edit, $cuotaFijaRadio_edit, $cuotaPesosRadio_edit) {
    if ($porcentajeRadio_edit.is(':checked')) {
        return 1;
    } else if ($cuotaFijaRadio_edit.is(':checked')) {
        return 2;
    } else if ($cuotaPesosRadio_edit.is(':checked')) {
        return 3;
    } else {
        return 0; // Default value if no radio button is selected
    }
}

function getSelectedValueInput($porcentajeRadio_edit, $inputPorcentaje_edit, $cuotaFijaRadio_edit, $inputCuotaFija_edit, $cuotaPesosRadio_edit, $inputCuotaPesos_edit) {
    if ($porcentajeRadio_edit.is(':checked')) {
        return  $inputPorcentaje_edit.val();
    } else if ($cuotaFijaRadio_edit.is(':checked')) {
        return $inputCuotaFija_edit.val();
    } else if ($cuotaPesosRadio_edit.is(':checked')) {
        return $inputCuotaPesos_edit.val();
    } else {
        return 0; // Default value if no radio button is selected
    }
}

/*=============================================
 GUARDA EL REGISTRO HISTORICO DEL CREDITO
 =============================================*/

//Realiza el guardado del histórico, ya sea de campos editados o de créditos que fueron dados de baja
function guardarHistoricoCredito(trabajador_id, credito_infonavit_id, descripcion_tipo_credito, estatus_credito_id, fecha_de_aplicacion, fecha_de_recepcion,
        fecha_movimiento, numero_de_credito, tipo_credito_id, fecha_evento, motivo_baja_id) {

    var datos = {
        "trabajador_id": trabajador_id,
        "credito_infonavit_id": credito_infonavit_id,
        "descripcion_tipo_credito": descripcion_tipo_credito,
        "cat_Estatus_Credito": {"id_estatus_credito": estatus_credito_id},
        "fecha_de_aplicacion": fecha_de_aplicacion,
        "fecha_de_recepcion": fecha_de_recepcion,
        "fecha_movimiento": fecha_movimiento,
        "numero_de_credito": numero_de_credito,
        "cat_tipo_credito": {"id_tipo_credito": tipo_credito_id},
        "fecha_evento": fecha_evento
    };

    /*Comprobación de parámetro opcional cuando se ingresa el motivo de la baja. 
     Al editar no se le ingresa el parámetro del motivo de la baja, al darlo de baja si se enviará el parámetero opcional con el id del motivo de la baja*/

    if (motivo_baja_id) {
        //Se agrega el catálogo con el motivo de la baja si recibe ese parámetro
        datos["cat_Motivo_Baja"] = {"id_motivo_baja": motivo_baja_id};
    }

    $.ajax({
        type: "POST",
        url: "catalogos/guardaHistoricoCreditoInfonavit",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),
        async: false,

        success: function (data) {
            toastr['success'](data.data, "Registro Histórico de manera exitosa");

        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });

    //Regresa los datos dependiendo si se le mandó o no el motivo de la baja
    return datos;
}

//******************************************************
//             BAJA DE CRÉDITO
//******************************************************

const motivoLiberacion = 1;
const motivoBajaTrabajador = 2;
const motivoJubilacion = 3;
const id_estatus_credito_baja = 1;
let fecha_evento = new Date().toISOString().slice(0, 10);
let datos = {"fecha_evento": fecha_evento, "cat_Estatus_Credito": {"id_estatus_credito": id_estatus_credito_baja}};
//Mantener valores al Bajar el Crédito
let informacionModal;

//Aquí se manejan todas las llamadas a la api para cualquiera de los motivos de baja de crédito
function bajaCredito(id_Credito) {
    //Guarda los datos del modal Editar para mandarlos al Histórico con la baja que corresponda
    $('#id_credito_infonavit').val('');
    let valTipoCreditoSeleccionado;
    let descripcionTipoCredito;
    if ($('#inputPorcentaje_edit').val().trim().length !== 0) {
        valTipoCreditoSeleccionado = 1;
        descripcionTipoCredito = $('#inputPorcentaje_edit').val();
    } else if ($('#inputCuotaFija_edit').val().trim().length !== 0) {
        valTipoCreditoSeleccionado = 2;
        descripcionTipoCredito = $('#inputCuotaFija_edit').val();
    } else if ($('#inputCuotaPesos_edit').val().trim().length !== 0) {
        valTipoCreditoSeleccionado = 3;
        descripcionTipoCredito = $('#inputCuotaPesos_edit').val();
    } else {
    }

    //Guardado de valores al dar de baja el crédito
    informacionModal = {
        noCredito: $('#inputNoCredito_edit').val(),
        fechaRecepcion: $('#fechaRecepcion_edit').val(),
        fechaAplicacion: $('#fechaAplicacion_edit').val(),
        valTipoCreditoSelecionado: valTipoCreditoSeleccionado,
        descripcionTipoCredito: descripcionTipoCredito
    };
    //Detecta click en liberación de crédito (1)
    $('#libCredito').off('click').on('click', function (event) {
        event.stopPropagation();
        $.ajax({
            type: "GET",
            url: "creditoInfonavit/actualizaEstatusMotivoBaja/" + id_Credito + "/" + motivoLiberacion,
            dataType: "json",
            async: false,
            success: (data) => {
                toastr['success']("Se dio de baja con motivo Liberación de Crédito");
                $('#tablaCreditos').DataTable().ajax.reload();
                $("#modalBaja").modal('hide');
                //Se llama al histórico para poder guardar los créditos que se dieron de baja
                guardarHistoricoCredito(id_trabajador, id_Credito, informacionModal.descripcionTipoCredito, 1, informacionModal.fechaAplicacion, informacionModal.fechaRecepcion,
                        fecha_movimiento_global, informacionModal.noCredito, informacionModal.valTipoCreditoSelecionado, fecha_evento, motivoLiberacion);
            },
            error: (xhr, status, error) => {
                alert("Error : " + error);
            }
        });

        $.ajax({
            type: "POST",
            url: "creditoInfonavit/actualizarFechaEventoYEstatusCredito/" + id_Credito,
            dataType: 'json',
            contentType: "application/json",
            async: false,
            data: JSON.stringify(datos),
            success: (data) => {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información para modificar", "Aviso", {progressBar: true, closeButton: true});
                }
                $('#tablaCreditos').DataTable().ajax.reload();
            },
            error: function (e) {
                toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    });
    // Detecta click en baja del trabajador (2)
    $('#bajaTrabajador').off('click').on('click', function (event) {
        event.stopPropagation();
        //Colocación de motivo de baja dentro de la tabla
        $.ajax({
            type: "GET",
            url: "creditoInfonavit/actualizaEstatusMotivoBaja/" + id_Credito + "/" + motivoBajaTrabajador,
            dataType: "json",
            async: false,
            success: (data) => {
                toastr['success']("Se dio de baja con motivo Baja del Trabajador");
                $('#tablaCreditos').DataTable().ajax.reload();
                $("#modalBaja").modal('hide');
                //Se llama al histórico para poder guardar los créditos que se dieron de baja
                guardarHistoricoCredito(id_trabajador, id_Credito, informacionModal.descripcionTipoCredito, 1, informacionModal.fechaAplicacion, informacionModal.fechaRecepcion,
                        fecha_movimiento_global, informacionModal.noCredito, informacionModal.valTipoCreditoSelecionado, fecha_evento, motivoBajaTrabajador);
            },
            error: (xhr, status, error) => {
                alert("Error : " + error);
            }
        });
        $.ajax({
            type: "POST",
            url: "creditoInfonavit/actualizarFechaEventoYEstatusCredito/" + id_Credito,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(datos),
            async: false,
            success: (data) => {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información para modificar", "Aviso", {progressBar: true, closeButton: true});
                }
                $('#tablaCreditos').DataTable().ajax.reload();
            },
            error: function (e) {
                toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
            }
        });

    });
    // Detecta un click en jubilación (3)
    $('#jubilación').off('click').on('click', function (event) {
        event.stopPropagation();
        //Colocación de motivo de baja dentro de la tabla
        $.ajax({
            type: "GET",
            url: "creditoInfonavit/actualizaEstatusMotivoBaja/" + id_Credito + "/" + motivoJubilacion,
            dataType: "json",
            async: false,
            success: (data) => {
                toastr['success']("Se dio de baja con motivo Jubilación");
                $('#tablaCreditos').DataTable().ajax.reload();
                $("#modalBaja").modal('hide');
                //Se llama al histórico para poder guardar los créditos que se dieron de baja
                guardarHistoricoCredito(id_trabajador, id_Credito, informacionModal.descripcionTipoCredito, 1, informacionModal.fechaAplicacion, informacionModal.fechaRecepcion,
                        fecha_movimiento_global, informacionModal.noCredito, informacionModal.valTipoCreditoSelecionado, fecha_evento, motivoJubilacion);
            },
            error: (xhr, status, error) => {
                alert("Error : " + error);
            }
        });

        $.ajax({
            type: "POST",
            url: "creditoInfonavit/actualizarFechaEventoYEstatusCredito/" + id_Credito,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(datos),
            async: false,
            success: (data) => {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información para modificar", "Aviso", {progressBar: true, closeButton: true});
                }
                $('#tablaCreditos').DataTable().ajax.reload();
            },
            error: function (e) {
                toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
            }
        });

    });
}

function validacionDecimal(object) {
    ;
    if (object.value.includes(".")) {
        var indice = object.value.indexOf(".");
        object.value = object.value.slice(0, indice + 3);
    } else {
        if (object.value.length > 9) {
            object.value = object.value.slice(0, 9);
        }
    }
}

function validacion(object) {
    if (object.value.length > 7) {
        object.value = object.value.slice(0, 7);

    }
}
