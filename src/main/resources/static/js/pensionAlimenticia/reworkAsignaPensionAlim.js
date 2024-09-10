$(document).ready(function () {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    buscarTrabajador(trabajador_id);
    buscarNomina(trabajador_id);
    contabilizaPensiones(trabajador_id);
    cmbBancos();
    mostrarTablaPensionesTrabajador(trabajador_id);
    handleRadioClick();
    asignarEventos();
});

function buscarTrabajador(trabajador_id) {
    $.ajax({
type: "GET",
url: "trabajador/buscarTrabajador/" + trabajador_id,
dataType: 'json',
success: function (data) {
    $('#campo_expediente').val(data.data.cat_expediente.numero_expediente);
    $('#campo_nombre').val(data.data.persona.nombre + " " + data.data.persona.apellido_paterno + " " + data.data.persona.apellido_materno);
},
error: function (e) {
    alert(e);
}
    });
}

//Recupera la nómina del trabajador y llena el combo de los periodos del formulario
function buscarNomina(trabajador_id) {
    $.ajax({
type: "GET",
url: "trabajador/buscarPlazaTrabajador/" + trabajador_id,
dataType: 'json',
success: function (data) {
    $('#campo_nomina').val(data.data.cat_Tipo_Nomina.desc_nomina);
    $('#id_tipo_nomina').val(data.data.cat_Tipo_Nomina.id_tipo_nomina);
    //Se llama a obtención de periodos para listar los periodos
    obtenPeriodos(data.data.cat_Tipo_Nomina.id_tipo_nomina);
},
error: function (e) {
    toastr["error"]("Error al recuperar nómina del trabajador", " Error", {progressBar: true, closeButton: true});
}
    });
}

//Se listan los periodos disponibles para el tipo de nómina correspondiente
function obtenPeriodos(id_nomina) {
    $.ajax({
type: "GET",
url: `trabajador/buscaPeriodosFechas/${id_nomina}`,
dataType: 'json',
success: function (data) {
    if ($.isEmptyObject(data)) {
toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
    } else {
let fechaMinima = null;
let fechaMinimaCorte = null;
const $cmbAplicacion = $('#cmbAplicacion');
const $cmbAplicacionEditar = $('#cmbAplicacion_editar');
$cmbAplicacion.empty();
$cmbAplicacionEditar.empty();

data.sort((a, b) => new Date(a.fecha_inicial) - new Date(b.fecha_inicial));

data.forEach(periodo => {
    const fechaInicial = new Date(periodo.fecha_inicial);
    const fechaCorte = new Date(periodo.fecha_corte);
    const fechaFinal = new Date(periodo.fecha_final);

    if (!fechaMinima || fechaInicial < fechaMinima) {
        fechaMinima = fechaInicial;
    }

    if (!fechaMinimaCorte || fechaCorte < fechaMinimaCorte) {
        fechaMinimaCorte = fechaCorte;
    }
    const optionText = `P. ${String(periodo.periodo).substr(4, 5)} : ${formatDate(fechaInicial)} -- ${formatDate(fechaFinal)}`;
    const option = `<option value="${periodo.id_periodo_pago}">${optionText}</option>`;

    $cmbAplicacion.append(option);
    $cmbAplicacionEditar.append(option);
});

if (fechaMinimaCorte) {
    const hoy = new Date();
    const formatDateForInput = (date) => date.toISOString().split('T')[0];
    $('#fechaRecepcion').val(formatDateForInput(hoy));

    if (fechaMinima) {
        $('#fechaRecepcion').attr('min', formatDateForInput(fechaMinima));
    }
}
    }
},
error: function (e) {
    toastr["error"]("Error al recuperar periodos de pago", " Error", {progressBar: true, closeButton: true});
}
    });
}
//Formatea las fechas de los periodos a día, mes, año
function formatDate(date) {
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
}

function contabilizaPensiones(trabajador_id) {
    // Verificar si el usuario se encuentra en la página "asignacionPension" para solo mostrar info de número de créditos en esta página
    if (window.location.pathname.endsWith('/pAlimenticiaTabla')) {
$.ajax({
    type: "GET",
    url: `pensionAlimenticia/cuentaPensionesTrabajador/${trabajador_id}`,
    dataType: 'json',
    success: function (data) {
if (data.data === 0) {
    toastr.info("El trabajador no tiene pensiones registradas", "Información", {progressBar: true, closeButton: true});
} else {
    toastr.info(`El trabajador tiene ${data.data} pensiones registradas`, "Información", {progressBar: true, closeButton: true});
}
    },
    error: function (xhr, status, error) {
const errorMessage = xhr.responseJSON && xhr.responseJSON.message ? xhr.responseJSON.message : "Ha ocurrido un error en la solicitud.";
toastr.error(errorMessage, "Error", {progressBar: true, closeButton: true});
    }
});
    }
}

//Carga los bancos en el select con los bancos provenientes del catálogo bancos
function cmbBancos(id) {
    $.ajax({
type: "GET",
url: "catalogos/listarBancos",
dataType: 'json',
success: function (data) {
    if ($.isEmptyObject(data)) {
toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
    } else {
$('#cmbBancos').empty().append("<option value=''>* </option> ");
$('#cmbBancosEditar').empty().append("<option value=''>* </option> ");
var vselected = "";
if (data.length > 0) {
    for (var x = 0; x < data.length; x++) {
        vselected = (data[x].id_banco === id) ? vselected = "selected" : vselected = "----";
        $('#cmbBancos').append('<option value="' + data[x].id_banco + '"' + vselected + '>' + data[x].nombre_banco + '</option>');
        $('#cmbBancosEditar').append('<option value="' + data[x].id_banco + '"' + vselected + '>' + data[x].nombre_banco + '</option>');
    }
}
    }
},
error: function (e) {
    toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
}
    });
}

//Recupera las pensiones activas del trabajador
function mostrarTablaPensionesTrabajador(trabajador_id) {
    tablaPensionActiva = $('#tablaPensionActiva').DataTable({
"ajax": {
    url: "pensionAlimenticia/buscartrabajadorOrdenJudicial/" + trabajador_id,
    method: 'GET',
    dataSrc: ''
},
responsive: true,
bProcessing: true,
select: true,
"language": {
    "processing": "Procesando espera...",
    "sProcessing": "Procesando...",
    "sLengthMenu": "Mostrar _MENU_ Registros",
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
    {data: "porcentaje"},
    {data: "cuota_fija"},
    {data: "pension_Alimenticia_Montos.anualidad"},
    {data: "pension_Alimenticia_B.nombre",
render: function (data, type, row) {
    var nombreCompleto = row.pension_Alimenticia_B.nombre + " " + row.pension_Alimenticia_B.apellido_paterno + " " + row.pension_Alimenticia_B.apellido_materno;
    return nombreCompleto;
}
    },
    {data: "periodo_aplicacion"},
    {data: "fecha_recepcion"},
    {data: "referencia"},
    {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
    var he;
    he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarPensionAlimenticia" onclick="cargaDatosForm(' + r.id_pension + ')" > ' + '<span class="fa fa-edit"> </span> Editar</button>';
    return he;
}},
    {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
    var he;
    he = '<button type="button" id="btndistrict" class="btn btn-outline-danger" onclick="estatusPension(' + r.id_pension + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
    return he;
}
    }
]
    });
}

function handleRadioClick() {
    const inputElements = [
{radio: "#radioPorcentaje", cuotaFija: "#inputCuotaFija", porcentaje: "#inputPorcentaje"},
{radio: "#radioCuotaFija", cuotaFija: "#inputCuotaFija", porcentaje: "#inputPorcentaje"},
{radio: "#radioPorcentajeEdita", cuotaFija: "#inputCuotaFija_editar", porcentaje: "#inputPorcentaje_editar"},
{radio: "#radioCuotaFijaEdita", cuotaFija: "#inputCuotaFija_editar", porcentaje: "#inputPorcentaje_editar"}
    ];
    inputElements.forEach(({ radio, cuotaFija, porcentaje }) => {
handleRadioChange(radio, cuotaFija, porcentaje);
    });
}

function handleRadioChange(radioSelector, cuotaFijaSelector, porcentajeSelector) {
    const $radio = $(radioSelector);
    const $inputCuotaFija = $(cuotaFijaSelector);
    const $inputPorcentaje = $(porcentajeSelector);
    $radio.on("click", function () {
if (!$(this).prop("checked")) {
    $inputCuotaFija.prop("disabled", true).val("");
    $inputPorcentaje.prop("disabled", true).val("");
    return;
}
if (radioSelector.includes("Porcentaje")) {
    $inputCuotaFija.prop("disabled", true).val("");
    $inputPorcentaje.prop("disabled", false);
} else {
    $inputCuotaFija.prop("disabled", false);
    $inputPorcentaje.prop("disabled", true).val("");
}
    });
}

function asignarEventos() {
    const $inputCp = $('#inputCp');
    const $inputCpEditar = $('#inputCp_editar');
    const $checkDepositoBancario = $('#checkDepositoBancario');

    handleCpInput($inputCp, $('#inputColonia'), $('#inputPoblacionMunicipio'), $('#inputEstado'), $('#inputHiddenColonia'));
    handleCpInput($inputCpEditar, $('#inputColonia_editar'), $('#inputPoblacionMunicipio_editar'), $('#inputEstado_editar'), $('#inputHiddenColoniaEditar'));

    $checkDepositoBancario.on('click', function () {
        toastr.info($(this).data('message'));
    });

    // Detectar cuando se oculta el modal
    $('#agregarPensionModal,#modificarPensionAlimenticia').on('hidden.bs.modal', function () {
// Marcar ciertos checkboxes al cargar el DOM
        marcarCheckboxes();
        $(".needs-validation").removeClass("was-validated");
        $("#formPensionAlimenticia")[0].reset();
        $("#formPensionAlimenticiaEdita")[0].reset();
    });

    // Deshabilitar inputs al cargar el DOM
    $("#inputCuotaFija, #inputPorcentaje, #inputPorcentaje_editar, #inputCuotaFija_editar, #fechaRecepcion_editar, #cmbAplicacion_editar, #checkDepositoBancario, #checkDepositoBancario_editar").prop("disabled", true);

    // Marcar ciertos checkboxes al cargar el DOM
    marcarCheckboxes();

    handleFieldClick($("#inputColonia, #inputPoblacionMunicipio, #inputEstado, #inputColonia_editar, #inputPoblacionMunicipio_editar, #inputEstado_editar"));

    // Exclusión mutua de checkboxes
    $('.exclusion-mutua').on('change', function () {
        if ($(this).prop('checked')) {
            $('.exclusion-mutua').not(this).prop('checked', false);
        }
    });

    handleColoniaChange($('#inputColonia'), $('#inputHiddenColonia'));
    handleColoniaChange($('#inputColonia_editar'), $('#inputHiddenColoniaEditar'));
    //Valida que el input del porcentaje nu supere el 100%
    $('#inputPorcentaje, #inputPorcentaje_editar').on('input', function () {
        // Obtén el valor del campo
        let valor = parseFloat($(this).val());
        // Verifica si el valor es mayor a 100
        if (valor > 100) {
            toastr["warning"]("El porcentaje no debe ser mayor al 100%", "Advertencia", {progressBar: true, closeButton: true});
            // Restablece el valor a 100
            $(this).val(100);
        }
    });
    //Cada que se esconde el modal
    $('#agregarPensionModal, #modificarPensionAlimenticia').on('hidden.bs.modal', function () {
        $("#inputCuotaFija, #inputPorcentaje, #inputPorcentaje_editar, #inputCuotaFija_editar").prop("disabled", true);
    });
}

const calculaCuentaB = (() => {
    let mensajeMostrado = false;
    return () => {
        var inputCtaBancariaBeneficiario = $("#inputCtaBancariaBeneficiario").val();
        var miCheckbox = $("#checkDepositoBancario");
        // Mostrar el mensaje inicial solo si aún no se ha mostrado
        if (!mensajeMostrado) {
            toastr['warning']('Ingresa los 10 dígitos de la cuenta o los 18 dígitos de la CLABE');
            mensajeMostrado = true; // Cambiar el estado de la variable para que el mensaje no se muestre nuevamente
        }
        if (inputCtaBancariaBeneficiario.length === 10 || inputCtaBancariaBeneficiario.length === 18) {
            miCheckbox.prop("checked", true);
            if (inputCtaBancariaBeneficiario.length === 10) {
toastr['success']('Ingresaste los 10 dígitos de la Cuenta');
            }
            if (inputCtaBancariaBeneficiario.length === 18) {
toastr['success']('Ingresaste los 18 dígitos de la CLABE');
            }
        } else {
            miCheckbox.prop("checked", false);
            miCheckbox.prop("disabled", true);
        }
    };
})();

//Función para cálculo de dígitos dentro de la cuenta bancaria (Edición)
const calculaCuentaBEditado = (() => {
    let mensajeMostrado = false;
    return () => {
        var inputCtaBancariaBeneficiario = $("#inputCtaBancariaBeneficiario_editar").val();
        var miCheckbox = $("#checkDepositoBancario_editar");
        // Mostrar el mensaje inicial solo si aún no se ha mostrado
        if (!mensajeMostrado) {
            toastr['warning']('Ingresa los 10 dígitos de la cuenta o los 18 dígitos de la CLABE');
            mensajeMostrado = true; // Cambiar el estado de la variable para que el mensaje no se muestre nuevamente
        }
        if (inputCtaBancariaBeneficiario.length === 10 || inputCtaBancariaBeneficiario.length === 18) {
            miCheckbox.prop("checked", true);
            if (inputCtaBancariaBeneficiario.length === 10) {
toastr['success']('Ingresaste los 10 dígitos de la Cuenta');
            }
            if (inputCtaBancariaBeneficiario.length === 18) {
toastr['success']('Ingresaste los 18 dígitos de la CLABE');
            }
        } else {
            miCheckbox.prop("checked", false);
            miCheckbox.prop("disabled", true);
        }
    };
})();

function marcarCheckboxes() {
    $("#checkfdoAhorro, #checkfdoTrab, #checkfdoEmpresa, #checkfdoInter, #checkAplNomina, #checkAplFiniq, #checkValesFinA").prop('checked', true);
}

// Función auxiliar para manejar el evento 'input' en los campos de código postal
function handleCpInput($input, $inputColonia, $inputPoblacion, $inputEstado, $inputHiddenColonia) {
    $input.on('input', function () {
if ($input.val().length === 5) {
    rellenarUbicacion($input.val());
} else {
    $inputColonia.empty();
    $inputPoblacion.val('');
    $inputEstado.val('');
    $inputHiddenColonia.val('');
}
    });
}

// Función para manejar cambios en el select de Colonia
function handleColoniaChange($inputColonia, $inputHiddenColonia) {
    $inputColonia.on('change', function () {
$inputHiddenColonia.val(this.value);
    });
}

// Función para mostrar mensaje si se intenta dar click a algún campo que no permite edición
function handleFieldClick($fields) {
    let mensajeMostrado = false;
    $fields.click(function () {
if (!mensajeMostrado) {
    toastr["info"]("Ingrese el código postal para rellenar los campos", "Información", {progressBar: true, closeButton: true});
    mensajeMostrado = true;
    setTimeout(() => {
mensajeMostrado = false;
    }, 5000);
}
    });
}

//Rellena campos de Estado, Municipio y Colonia
function rellenarUbicacion(cp) {
    $.ajax({
type: "GET",
url: "codigoPostal/buscarCP/" + cp,
dataType: 'json',
success: function (response) {
    const data = response.data;
    if (data.length > 0) {
const descColonia = data[0].desc_colonia;
const municipio = data[0].cat_municipio.desc_municipio;
const estado = data[0].cat_municipio.cat_estado.desc_estado;
const $inputColonia = $('#inputColonia');
const $inputColoniaEditar = $('#inputColonia_editar');
const idColonia = $('#inputHiddenColoniaEditar').val();
$('#inputPoblacionMunicipio, #inputPoblacionMunicipio_editar').val(municipio);
$('#inputEstado, #inputEstado_editar').val(estado);
const options = data.map(item => `<option value="${item.id_colonia}">${item.desc_colonia}</option>`).join('');
$inputColonia.empty().append("<option value=''>*Selecciona la Colonia</option>" + options);
$inputColoniaEditar.empty().append("<option value=''>*Selecciona la Colonia</option>" + options);
$inputColonia.val(idColonia).change();
$inputColoniaEditar.val(idColonia).change();
    } else {
toastr.warning("No se encontró información para el código postal ingresado", "Aviso", {
    progressBar: true,
    closeButton: true
});
    }
},
error: function (e) {
    alert(e);
}
    });
}

//Guardado de una nueva pensión alimenticia
$("#formPensionAlimenticia").submit(function (e) {
    e.preventDefault();
    // Comprobación de envío de números positivos
    let invalidNumber = false;
    $('input[type="number"]').each(function () {
if (parseFloat($(this).val()) < 0) {
    invalidNumber = true;
    return false;
}
    });
    if (invalidNumber) {
toastr.error('No se permiten números negativos. Por favor, ingresa valores válidos.');
return;
    }
    // Obtener el id del trabajador desde la URL
    const urlParams = new URLSearchParams(decodeURIComponent(window.location.search));
    const trabajador_id = urlParams.get('id_trabajador');
    // Validación de campos para Orden Judicial
    const radioPorcentaje = $("#radioPorcentaje").is(":checked");
    const radioCuotaFija = $("#radioCuotaFija").is(":checked");
    const inputPorcentaje = $("#inputPorcentaje").val();
    const inputCuotaFija = $("#inputCuotaFija").val();
    const inputReferencia = $("#inputReferencia").val();
    const juzgado = $("#juzgado").val();
    const noExpdt = $("#noExpdt").val();
    const inputNOficio = $("#inputNOficio").val();
    const fechaRecepcion = $("#fechaRecepcion").val();
    const cmbAplicacion = $("#cmbAplicacion").val();
    if (!inputPorcentaje && !inputCuotaFija) {
toastr.error("Complete los campos Cuota Fija o Porcentaje.", "Aviso", {progressBar: true, closeButton: true});
return;
    }
    if (!inputReferencia || !juzgado || !noExpdt || !inputNOficio || !fechaRecepcion || !cmbAplicacion) {
toastr.error("Complete todos los campos de Orden Judicial", "Aviso", {progressBar: true, closeButton: true});
return;
    }
    // Validación de campos Beneficiario
    const camposBeneficiario = [
"#inputNombreBeneficiario",
"#inputApellidoPaternoBeneficiario",
"#inputApellidoMaternoBeneficiario",
"#inputRfc",
"#inputCalleBeneficiario",
"#inputNumeroOficial",
"#inputColonia",
"#inputPoblacionMunicipio",
"#inputEstado",
"#inputCp",
"#inputCtaBancariaBeneficiario"
    ];
    const camposVacios = camposBeneficiario.some(selector => !$(selector).val()) || !$("#cmbBancos").val();
    if (camposVacios) {
toastr.warning('Hay campos vacíos de Beneficiario');
    }
    // Validación de campos Montos
    const datosMontos = {
"id_fdo_ahorro": $("#checkfdoAhorro").is(":checked") ? 1 : 0,
"anualidad": $("#inputDescuentoAnual").val(),
"apl_finiq": $("#checkAplFiniq").is(":checked") ? 1 : 0,
"apl_nomina": $("#checkAplNomina").is(":checked") ? 1 : 0,
"descuento": $("#inputMontoPagoExceso").val(),
"fdo_empresa": $("#checkfdoEmpresa").is(":checked") ? 1 : 0,
"fdo_interes": $("#checkfdoInter").is(":checked") ? 1 : 0,
"fdo_trabajador": $("#checkfdoTrab").is(":checked") ? 1 : 0,
"id_deposito_bancario": $("#checkDepositoBancario").is(":checked") ? 1 : 0,
"pago_descuento": $("#checkAplDescSobreAguinaldo").is(":checked") ? $("#checkAplDescSobreAguinaldo").val() : ($("#checkAplDescSobreFondoDeAhorro").is(":checked") ? $("#checkAplDescSobreFondoDeAhorro").val() : 0),
"periodo_gen": $("#inputPerGen").val(),
"porcentaje_descuent": $("#inputPorcD").val(),
"vales_fin_anio": $("#checkValesFinA").is(":checked") ? 1 : 0
    };
    //Campos de beneficiario
    const datosBeneficiario = {
"apellido_materno": $("#inputApellidoMaternoBeneficiario").val(),
"apellido_paterno": $("#inputApellidoPaternoBeneficiario").val(),
"calle": $("#inputCalleBeneficiario").val(),
"cuentabeneficiario": $("#inputCtaBancariaBeneficiario").val(),
"nombre": $("#inputNombreBeneficiario").val(),
"numero_oficial": $("#inputNumeroOficial").val(),
"rfc": $("#inputRfc").val(),
...($("#cmbBancos").val() ? {"cat_Banco": {"id_banco": $("#cmbBancos").val() }} : {}),
    ...($("#inputHiddenColonia").val() ? {"cat_Colonia": {"id_colonia": $("#inputHiddenColonia").val() }} : {})
    };
    //Conjunto completo de datos, orden judicial, beneficiario y montos
    const datos = {
"cuota_fija": inputCuotaFija,
"expediente_caso": noExpdt,
"fecha_recepcion": fechaRecepcion,
"juzgado": juzgado,
"modalidad": radioPorcentaje ? $("#radioPorcentaje").val() : $("#radioCuotaFija").val(),
"oficio": inputNOficio,
"pension_Alimenticia_B": datosBeneficiario,
"pension_Alimenticia_Montos": datosMontos,
"periodo_aplicacion": cmbAplicacion,
"porcentaje": inputPorcentaje,
"referencia": inputReferencia,
"trabajador_id": trabajador_id
    };

        console.log(datos)
    // Guardado de todos los datos mediante AJAX
    $.ajax({
type: "POST",
url: "pensionAlimenticia/guardaPensionAlimenticia",
data: JSON.stringify(datos),
contentType: "application/json",
success: function (data) {
    $('#inputColonia').empty();
    $("#inputCuotaFija, #inputPorcentaje, #inputPorcentaje_editar, #inputCuotaFija_editar").prop("disabled", true);
    $("#agregarPensionModal").modal('hide');
    toastr.success(data.data, 'Se han guardado correctamente todos los datos');
    $('#tablaPensionActiva').DataTable().ajax.reload();
},
error: function (xhr) {
    toastr.warning("Error al agregar elemento: " + xhr.responseText, "Error", {progressBar: true, closeButton: true});
}
    });
});

//Edición de pension Alimenticia y guardado de histórico
$("#formPensionAlimenticiaEdita").submit(function (e) {
    e.preventDefault();
    //Validación de no envío de datos negativos
    let invalidNumber = false;
    $('input[type="number"]').each(function () {
if (parseFloat($(this).val()) < 0) {
    invalidNumber = true;
}
    });
    if (invalidNumber) {
toastr.error('No se permiten números negativos. Por favor, ingresa valores válidos.');
return;
    }
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');

    /*VALIDACION DE CAMPOS PARA ORDEN JUDICIAL*/
    let idPension = $("#inputHiddenIdPension").val();
    let radioPorcentajeEdita = $("#radioPorcentajeEdita").is(":checked");
    let inputPorcentaje_editar = $("#inputPorcentaje_editar").val();
    let inputCuotaFija_editar = $("#inputCuotaFija_editar").val();
    let inputReferencia_editar = $("#inputReferencia_editar").val();
    let juzgado_editar = $("#juzgado_editar").val();
    let noExpdt_editar = $("#noExpdt_editar").val();
    let InputNOficio_editar = $("#InputNOficio_editar").val();
    let fechaRecepcion_editar = $("#fechaRecepcion_editar").val();
    let cmbAplicacion_editar = $("#cmbAplicacion_editar").val();

    // Validar si algún campo está vacío
    if (inputPorcentaje_editar === '' & inputCuotaFija_editar === '') {
toastr.error('Complete los campos Cuota Fija o Porcentaje.');
return;
    }
    if (inputReferencia_editar === '' || juzgado_editar === '' || noExpdt_editar === '' || InputNOficio_editar === '' || fechaRecepcion_editar === '' || cmbAplicacion_editar === '') {
toastr.error('Complete todos los campos de Orden Judicial');
return;
    }

    /*VALIDACION DE CAMPOS BENEFICIARIO*/
    let inputNombreBeneficiario_editar = $("#inputNombreBeneficiario_editar").val();
    let inputApellidoPaternoBeneficiario_editar = $("#inputApellidoPaternoBeneficiario_editar").val();
    let inputApellidoMaternoBeneficiario_editar = $("#inputApellidoMaternoBeneficiario_editar").val();
    let inputRfc_editar = $("#inputRfc_editar").val();
    let inputCalleBeneficiario_editar = $("#inputCalleBeneficiario_editar").val();
    let inputNumeroOficial_editar = $("#inputNumeroOficial_editar").val();
    let inputColoniaEditar = $("#inputColonia_editar").val();
    let inputPoblacionMunicipio_editar = $("#inputPoblacionMunicipio_editar").val();
    let inputEstado_editar = $("#inputEstado_editar").val();
    let inputCp_editar = $("#inputCp_editar").val();
    let cmbBancosEditar = $("#cmbBancosEditar").val();
    let inputCtaBancariaBeneficiario_editar = $("#inputCtaBancariaBeneficiario_editar").val();

    if (inputNombreBeneficiario_editar === '' || inputApellidoPaternoBeneficiario_editar === '' || inputApellidoMaternoBeneficiario_editar === '' || inputRfc_editar === '' || inputCalleBeneficiario_editar === ''
    || inputNumeroOficial_editar === '' || inputColoniaEditar === ''
    || inputPoblacionMunicipio_editar === '' || inputEstado_editar === '' || inputCp_editar === '' || cmbBancosEditar.length === 0 || inputCtaBancariaBeneficiario_editar === '') {
toastr.warning('Hay campos vacíos de Beneficiario');

    }

    /*VALIDACION DE CAMPOS MONTOS*/
    let checkfdoAhorro_editar = $("#checkfdoAhorro_editar").is(":checked");
    let checkfdoTrab_editar = $("#checkfdoTrab_editar").is(":checked");
    let checkfdoEmpresa_editar = $("#checkfdoEmpresa_editar").is(":checked");
    let checkfdoInter_editar = $("#checkfdoInter_editar").is(":checked");
    let checkAplNomina_editar = $("#checkAplNomina_editar").is(":checked");
    let checkAplFiniq_editar = $("#checkAplFiniq_editar").is(":checked");
    let checkValesFinA = $("#checkValesFinA_editar").is(":checked");
    let checkDepositoBancario_editar = $("#checkDepositoBancario_editar").is(":checked");
    let inputDescuentoAnual_editar = $("#inputDescuentoAnual_editar").val();
    //let checkAplDescSobreAguinaldo_editar = $("#checkAplDescSobreAguinaldo_editar").is(":checked");
    //let valorCheckboxDescSobreAguinaldo_editar = checkAplDescSobreAguinaldo_editar ? $("#checkAplDescSobreAguinaldo_editar").val() : 0;
    //let checkAplDescSobreFondoDeAhorro_editar = $("#checkAplDescSobreFondoDeAhorro_editar").is(":checked");
    //let valorCheckboxDescSobreFondoAhorro_editar = checkAplDescSobreFondoDeAhorro_editar ? $("#checkAplDescSobreFondoDeAhorro_editar").val() : 0;
    let chkInputMontoPagoExceso_editar = $("#chkInputMontoPagoExceso_editar").val();
    let inputPorcD = $("#inputPorcD_editar").val();
    let inputPerGen_editar = $("#inputPerGen_editar").val();

    // Validación de campos Montos
    const datosMontos = {
"id_fdo_ahorro": checkfdoAhorro_editar ? 1 : 0,
"anualidad": inputDescuentoAnual_editar,
"apl_finiq": checkAplFiniq_editar,
"apl_nomina": checkAplNomina_editar,
"descuento": chkInputMontoPagoExceso_editar,
"fdo_empresa": checkfdoEmpresa_editar,
"fdo_interes": checkfdoInter_editar,
"fdo_trabajador": checkfdoTrab_editar,
"id_deposito_bancario": checkDepositoBancario_editar,
"pago_descuento": $("#checkAplDescSobreAguinaldo_editar").is(":checked") ? $("#checkAplDescSobreAguinaldo_editar").val() : ($("#checkAplDescSobreFondoDeAhorro_editar").is(":checked") ? $("#checkAplDescSobreFondoDeAhorro_editar").val() : 0),
"periodo_gen": inputPerGen_editar,
"porcentaje_descuent": inputPorcD,
"vales_fin_anio": checkValesFinA
};
//Campos de beneficiario
const datosBeneficiario = {
"apellido_materno": inputApellidoMaternoBeneficiario_editar,
"apellido_paterno": inputApellidoPaternoBeneficiario_editar,
"calle": inputCalleBeneficiario_editar,
"cuentabeneficiario": inputCtaBancariaBeneficiario_editar,
"nombre": inputNombreBeneficiario_editar,
"numero_oficial": inputNumeroOficial_editar,
"rfc": inputRfc_editar,
...($("#cmbBancosEditar").val() ? {"cat_Banco": {"id_banco": $("#cmbBancosEditar").val() }} : {}),
    ...($("#inputHiddenColoniaEditar").val() ? {"cat_Colonia": {"id_colonia": $("#inputHiddenColoniaEditar").val() }} : {})
    };
    //Conjunto completo de datos, orden judicial, beneficiario y montos
    const datos = {
        "cuota_fija": inputCuotaFija_editar,
        "expediente_caso": noExpdt_editar,
        "fecha_recepcion": fechaRecepcion_editar,
        "juzgado": juzgado_editar,
        "modalidad": radioPorcentajeEdita ? $("#radioPorcentajeEdita").val() : $("#radioCuotaFijaEdita").val(),
        "oficio": InputNOficio_editar,
        "pension_Alimenticia_B": datosBeneficiario,
        "pension_Alimenticia_Montos": datosMontos,
        "periodo_aplicacion": cmbAplicacion_editar,
        "porcentaje": inputPorcentaje_editar,
        "referencia": inputReferencia_editar,
        "trabajador_id": trabajador_id
    };

    //Update en pensión original
    $.ajax({
        type: "POST",
        url: "pensionAlimenticia/actualizarPensionAlimenticia/" + idPension,
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(datos),
        success: function (data) {
            $("#modificarPensionAlimenticia").modal('hide');
            //Remover mensajes de validación cuando el guardado es correcto
            toastr['success']('Se han guardado correctamente todas las ediciones');
            $('#tablaPensionActiva').DataTable().ajax.reload();
        },
        error: function (e) {
            var errorMessage = "Error al agregar elemento: ";
            if (e.responseText) {
                errorMessage += e.responseText;
            } else {
                errorMessage += "No se recibió una respuesta del servidor.";
            }
            toastr["warning"](errorMessage, "Error", {progressBar: true, closeButton: true});
        }
    });
});

function validacion(object) {
    if (object.value.length > 5) {
        object.value = object.value.slice(0, 5);
    }
}

//Da de baja el estatus de una pensión, guarda la fecha de la baja desde el servicio Implementado
function estatusPension(id_pension) {
    $("#modalEstatusPa").modal("toggle");
    $('#modalmodalEstatusPaStatusBanco').on('hidden.bs.modal', function () {
        id_pension = null;
    });

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_pension !== null) {
            $.ajax({
                type: 'GET',
                url: 'pensionAlimenticia/updateEstatusPension/' + id_pension,
                dataType: 'json',
                success: function (data) {
                    $('#tablaPensionActiva').DataTable().ajax.reload();
                    $("#modalEstatusPa").modal("hide");
                    toastr["warning"]("Se ha Desactivado la Pensión...", "Aviso", {progressBar: true, closeButton: true});
                    id_pension = null;
                },
                error: function (e) {
                    toastr["warning"]("Error" + e, " error", {progressBar: true, closeButton: true});
                }
            });
        }
    });
}

//Rellena los campos del modal de edición
function cargaDatosForm(id) {
    $.ajax({
        type: "GET",
        url: "pensionAlimenticia/buscartrabajadorPensionA/" + id,
        dataType: 'json',
        success: function (data) {
            //Obtener ids para edición
            $('#inputHiddenIdPension').val(id);
            $('#inputHiddenIdBeneficiario').val(data[0].pension_Alimenticia_B.id_beneficiario_pa);
            $('#inputHiddenIdMontos').val(data[0].pension_Alimenticia_Montos.id_montos);

            $('#checkfdoAhorro_editar').prop('checked', data[0].pension_Alimenticia_Montos.id_fdo_ahorro === 1 ? true : false);
            $('#checkfdoEmpresa_editar').prop('checked', data[0].pension_Alimenticia_Montos.fdo_empresa === true ? true : false);
            $('#checkfdoInter_editar').prop('checked', data[0].pension_Alimenticia_Montos.fdo_interes === true ? true : false);
            $('#checkAplNomina_editar').prop('checked', data[0].pension_Alimenticia_Montos.apl_nomina === true ? true : false);
            $('#checkAplFiniq_editar').prop('checked', data[0].pension_Alimenticia_Montos.apl_finiq === true ? true : false);
            $('#checkValesFinA_editar').prop('checked', data[0].pension_Alimenticia_Montos.vales_fin_anio === true ? true : false);
            $('#checkDepositoBancario_editar').prop('checked', data[0].pension_Alimenticia_Montos.id_deposito_bancario === true ? true : false);
            $('#checkfdoTrab_editar').prop('checked', data[0].pension_Alimenticia_Montos.fdo_trabajador === true ? true : false);
            $('#chkInputMontoPagoExceso_editar').val(data[0].pension_Alimenticia_Montos.descuento);
            $('#checkAplDescSobreAguinaldo_editar').prop('checked', data[0].pension_Alimenticia_Montos.pago_descuento === 1 ? true : false);
            $('#checkAplDescSobreFondoDeAhorro_editar').prop('checked', data[0].pension_Alimenticia_Montos.pago_descuento === 2 ? true : false);

            $('#inputPorcentaje_editar').val(data[0].porcentaje);
            $('#inputCuotaFija_editar').val(data[0].cuota_fija);
            $('#inputPorcentaje_editar').prop('disabled', data[0].modalidad === 1 ? false : true);
            $('#inputCuotaFija_editar').prop('disabled', data[0].modalidad === 2 ? false : true);
            $('#radioPorcentajeEdita').prop('checked', data[0].modalidad === 1 ? true : false);
            $('#radioCuotaFijaEdita').prop('checked', data[0].modalidad === 2 ? true : false);
            $('#juzgado_editar').val(data[0].juzgado);
            $('#fechaRecepcion_editar').val(data[0].fecha_recepcion);
            $('#noExpdt_editar').val(data[0].expediente_caso);
            //$('#cmbAplicacion_editar').val(data[0].periodo_aplicacion);
            $('#cmbAplicacion_editar').append(new Option(data[0].periodo_aplicacion, data[0].periodo_aplicacion)).val(data[0].periodo_aplicacion);
            $('#inputReferencia_editar').val(data[0].referencia);
            $('#InputNOficio_editar').val(data[0].oficio);
            $('#inputNombreBeneficiario_editar').val(data[0].pension_Alimenticia_B.nombre);
            $('#inputApellidoPaternoBeneficiario_editar').val(data[0].pension_Alimenticia_B.apellido_paterno);
            $('#inputApellidoMaternoBeneficiario_editar').val(data[0].pension_Alimenticia_B.apellido_materno);
            $('#inputRfc_editar').val(data[0].pension_Alimenticia_B.rfc);
            $('#inputCalleBeneficiario_editar').val(data[0].pension_Alimenticia_B.calle);
            $('#inputNumeroOficial_editar').val(data[0].pension_Alimenticia_B.numero_oficial);
            //Se revisa si las llaves foráneas son nulas para manejar errores
            if (data[0].pension_Alimenticia_B.cat_Colonia !== null) {
                $('#inputHiddenColoniaEditar').val(data[0].pension_Alimenticia_B.cat_Colonia.id_colonia);
                $('#inputCp_editar').val(data[0].pension_Alimenticia_B.cat_Colonia.cod_postal);
                rellenarUbicacion(data[0].pension_Alimenticia_B.cat_Colonia.cod_postal);
            }
            if ((data[0].pension_Alimenticia_B.cat_Banco) !== null) {
                $('#cmbBancosEditar').val(data[0].pension_Alimenticia_B.cat_Banco.id_banco);
            }
            $('#inputCtaBancariaBeneficiario_editar').val(data[0].pension_Alimenticia_B.cuentabeneficiario);
            $('#inputDescuentoAnual_editar').val(data[0].pension_Alimenticia_Montos.anualidad);
            $('#inputPorcD_editar').val(data[0].pension_Alimenticia_Montos.porcentaje_descuent);
            $('#inputPerGen_editar').val(data[0].pension_Alimenticia_Montos.periodo_gen);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar información del formulario" + e, " error", {progressBar: true, closeButton: true});
        }
    });
}