document.addEventListener('DOMContentLoaded', () => {
    $tablaTrabajadores;
    $tablaBeneficiarios;
    cmbBancos();
    agregarEventoCp();
});

function agregarEventoCp() {
    // Asignar evento de escucha al campo de código postal para rellenado automático de campos
    let inputCpEditar = $('#inputCp_editar');
    //Input Código Postal Edición
    inputCpEditar.on('input', function () {
        let value = inputCpEditar.val();
        if (value.length === 5) {
            let cp = inputCpEditar.val().toString();
            rellenarUbicacion(cp);
        } else {
            $('#inputColonia_editar').empty();
            $('#inputPoblacionMunicipio_editar').val('');
            $('#inputEstado_editar').val('');
            $('#inputHiddenColoniaEditar').val('');
        }
    });
}

//Se genera el listado de Beneficiarios Pensión alimenticia
$tablaBeneficiarios = $('#tablaBeneficiarios').dataTable({
    "ajax": {
        url: "pensionAlimenticia/listarPensionAlimenticiaActivas",
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
        "searchPlaceholder": "Buscar",
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

        {data: "nombre"},
        {data: "apellido_paterno"},
        {data: "apellido_materno"},
        {data: null, sTitle: "Nombre Trabajador", render: function (data, type, row) {
                return data.nombreT + " " + data.apellidoPatT + " " + data.apellidoMatT;
            }},
        {data: "", sTitle: "Pensión Alimenticia", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarPensionAlimenticia" onclick="cargaDatosForm(' + r.id_pension + ')"><span class="fa fa-edit"></span>Editar Pensión</button>';
                return he;
            }
        }
    ]
});

//Rellena los campos del modal de edición
function cargaDatosForm(id) {
    $.ajax({
        type: "GET",
        url: "pensionAlimenticia/buscartrabajadorPensionA/" + id,
        dataType: 'json',
        success: function (data) {
            //Obtener ids para edición
            $('#trabajador_id').val(data[0].trabajador_id);
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
            alert(e);
        }
    });
}

//Rellena campos de Estado, Municipio y Colonia
function rellenarUbicacion(cp) {
    $.ajax({
        type: "GET",
        url: "codigoPostal/buscarCP/" + cp,
        dataType: 'json',
        success: function (data) {
            if (data.data.length > 0) {
                //Colonia
                //let idColonia = data.data[0].id_colonia;
                let descColonia = data.data[0].desc_colonia;
                //Municipio    
                let municipio = data.data[0].cat_municipio.desc_municipio;
                //Estado
                let estado = data.data[0].cat_municipio.cat_estado.desc_estado;
                //Selección de colonia en edición al cargar el form (Colonias con mismo código postal)
                let idColonia = $('#inputHiddenColoniaEditar').val();
                $('#inputPoblacionMunicipio_editar').val(municipio);
                $('#inputEstado_editar').val(estado);
                $('#inputColonia, #inputColonia_editar').empty().append("<option value= ''>*Selecciona la Colonia</option> ");
                for (let i = 0; i < data.data.length; i++) {
                    $('#inputColonia, #inputColonia_editar').append('<option value="' + data.data[i].id_colonia + '"' + '>' + data.data[i].desc_colonia + '</option>');
                }
                //Selecciona el campo al momento de realizar la edición
                $('#inputColonia_editar').val(idColonia).change();
            } else {
                toastr["warning"]("No se encontro información para el código postal ingresado", "Aviso", {progressBar: true, closeButton: true});
            }
        },
        error: function (e) {
            alert(e);
        }
    });
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
                $('#cmbBancosEditar').empty().append("<option value=''>* </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_banco === id) ? vselected = "selected" : vselected = "----";
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

//Guardado de Objeto Historico al abrir el modal en un objeto (originalFormData)
$("#modificarPensionAlimenticia").on("show.bs.modal", function () {
    //Espera un segundo para poder recolectar los campos de manera correcta
    setTimeout(function () {
        guardarObjetoHistorico();
    }, 1000);
});
//Variable global para mantener valores originales para su posterior comparación en los datos editados
let originalFormData = {};
//Llenado de Objeto con los datos originales del formulario para mantener rastreo de las ediciones
function guardarObjetoHistorico() {
    //Campos originales antes de modificación
    originalFormData = {
        // GUARDADO DE CAMPOS ORIGINALES ORDEN JUDICIAL
        idPension_historico: $("#inputHiddenIdPension").val(),
        radioPorcentajeEdita_historico: $("#radioPorcentajeEdita").is(":checked"),
        radioCuotaFijaEdita_historico: $("#radioCuotaFijaEdita").is(":checked"),
        valorRadioPorcentajeEdita_historico: $("#radioPorcentajeEdita").is(":checked") ? $("#radioPorcentajeEdita").val() : 0,
        valorRadioCuotaFijaEdita_historico: $("#radioCuotaFijaEdita").is(":checked") ? $("#radioCuotaFijaEdita").val() : 0,
        inputPorcentaje_editar_historico: $("#inputPorcentaje_editar").val(),
        inputCuotaFija_editar_historico: $("#inputCuotaFija_editar").val(),
        inputReferencia_editar_historico: $("#inputReferencia_editar").val(),
        juzgado_editar_historico: $("#juzgado_editar").val(),
        noExpdt_editar_historico: $("#noExpdt_editar").val(),
        InputNOficio_editar_historico: $("#InputNOficio_editar").val(),
        fechaRecepcion_editar_historico: $("#fechaRecepcion_editar").val(),
        cmbAplicacion_editar_historico: $("#cmbAplicacion_editar").val(),

        // GUARDADO DE CAMPOS ORIGINALES BENEFICIARIO
        idBeneficiario_historico: $("#inputHiddenIdBeneficiario").val(),
        inputNombreBeneficiario_editar_historico: $("#inputNombreBeneficiario_editar").val(),
        inputApellidoPaternoBeneficiario_editar_historico: $("#inputApellidoPaternoBeneficiario_editar").val(),
        inputApellidoMaternoBeneficiario_editar_historico: $("#inputApellidoMaternoBeneficiario_editar").val(),
        inputRfc_editar_historico: $("#inputRfc_editar").val(),
        inputCalleBeneficiario_editar_historico: $("#inputCalleBeneficiario_editar").val(),
        inputNumeroOficial_editar_historico: $("#inputNumeroOficial_editar").val(),
        idColoniaEditar_historico: $('#inputHiddenColoniaEditar').val(),
        inputColoniaEditar_historico: $("#inputColonia_editar").val(),
        inputPoblacionMunicipio_editar_historico: $("#inputPoblacionMunicipio_editar").val(),
        inputEstado_editar_historico: $("#inputEstado_editar").val(),
        inputCp_editar_historico: $("#inputCp_editar").val(),
        cmbBancosEditar_historico: $("#cmbBancosEditar").val(),
        inputCtaBancariaBeneficiario_editar_historico: $("#inputCtaBancariaBeneficiario_editar").val(),

        // GUARDADO DE CAMPOS ORIGINALES MONTOS
        id_montos_historico: $("#inputHiddenIdMontos").val(),
        checkfdoAhorro_editar_historico: $("#checkfdoAhorro_editar").is(":checked"),
        checkfdoTrab_editar_historico: $("#checkfdoTrab_editar").is(":checked"),
        checkfdoEmpresa_editar_historico: $("#checkfdoEmpresa_editar").is(":checked"),
        checkfdoInter_editar_historico: $("#checkfdoInter_editar").is(":checked"),
        checkAplNomina_editar_historico: $("#checkAplNomina_editar").is(":checked"),
        checkAplFiniq_editar_historico: $("#checkAplFiniq_editar").is(":checked"),
        checkValesFinA_editar_historico: $("#checkValesFinA_editar").is(":checked"),
        checkDepositoBancario_editar_historico: $("#checkDepositoBancario_editar").is(":checked"),
        inputDescuentoAnual_editar_historico: $("#inputDescuentoAnual_editar").val(),
        checkAplDescSobreAguinaldo_editar_historico: $("#checkAplDescSobreAguinaldo_editar").is(":checked"),
        valorCheckboxDescSobreAguinaldo_editar_historico: $("#checkAplDescSobreAguinaldo_editar").is(":checked") ? $("#checkAplDescSobreAguinaldo_editar").val() : 0,
        checkAplDescSobreFondoDeAhorro_editar_historico: $("#checkAplDescSobreFondoDeAhorro_editar").is(":checked"),
        valorCheckboxDescSobreFondoAhorro_editar_historico: $("#checkAplDescSobreFondoDeAhorro_editar").is(":checked") ? $("#checkAplDescSobreFondoDeAhorro_editar").val() : 0,
        chkInputMontoPagoExceso_editar_historico: $("#chkInputMontoPagoExceso_editar").val(),
        inputPorcD_editar_historico: $("#inputPorcD_editar").val(),
        inputPerGen_editar_historico: $("#inputPerGen_editar").val()
    };
}
;

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
    var trabajador_id = $('#trabajador_id').val();

    /*VALIDACION DE CAMPOS PARA ORDEN JUDICIAL*/
    let idPension = $("#inputHiddenIdPension").val();
    let radioPorcentajeEdita = $("#radioPorcentajeEdita").is(":checked");
    let radioCuotaFijaEdita = $("#radioCuotaFijaEdita").is(":checked");
    let valorRadioPorcentajeEdita = radioPorcentajeEdita ? $("#radioPorcentajeEdita").val() : 0;
    let valorRadioCuotaFijaEdita = radioCuotaFijaEdita ? $("#radioCuotaFijaEdita").val() : 0;
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
    let idBeneficiario = $("#inputHiddenIdBeneficiario").val();
    let inputNombreBeneficiario_editar = $("#inputNombreBeneficiario_editar").val();
    let inputApellidoPaternoBeneficiario_editar = $("#inputApellidoPaternoBeneficiario_editar").val();
    let inputApellidoMaternoBeneficiario_editar = $("#inputApellidoMaternoBeneficiario_editar").val();
    let inputRfc_editar = $("#inputRfc_editar").val();
    let inputCalleBeneficiario_editar = $("#inputCalleBeneficiario_editar").val();
    let inputNumeroOficial_editar = $("#inputNumeroOficial_editar").val();
    let idColoniaEditar = $('#inputHiddenColoniaEditar').val();
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
    let id_montos = $("#inputHiddenIdMontos").val();
    let checkfdoAhorro_editar = $("#checkfdoAhorro_editar").is(":checked");
    let checkfdoTrab_editar = $("#checkfdoTrab_editar").is(":checked");
    let checkfdoEmpresa_editar = $("#checkfdoEmpresa_editar").is(":checked");
    let checkfdoInter_editar = $("#checkfdoInter_editar").is(":checked");
    let checkAplNomina_editar = $("#checkAplNomina_editar").is(":checked");
    let checkAplFiniq_editar = $("#checkAplFiniq_editar").is(":checked");
    let checkValesFinA_editar = $("#checkValesFinA_editar").is(":checked");
    let checkDepositoBancario_editar = $("#checkDepositoBancario_editar").is(":checked");
    let inputDescuentoAnual_editar = $("#inputDescuentoAnual_editar").val();
    let checkAplDescSobreAguinaldo_editar = $("#checkAplDescSobreAguinaldo_editar").is(":checked");
    let valorCheckboxDescSobreAguinaldo_editar = checkAplDescSobreAguinaldo_editar ? $("#checkAplDescSobreAguinaldo_editar").val() : 0;
    let checkAplDescSobreFondoDeAhorro_editar = $("#checkAplDescSobreFondoDeAhorro_editar").is(":checked");
    let valorCheckboxDescSobreFondoAhorro_editar = checkAplDescSobreFondoDeAhorro_editar ? $("#checkAplDescSobreFondoDeAhorro_editar").val() : 0;
    let chkInputMontoPagoExceso_editar = $("#chkInputMontoPagoExceso_editar").val();
    let inputPorcD_editar = $("#inputPorcD_editar").val();
    let inputPerGen_editar = $("#inputPerGen_editar").val();

    /*Guardado de Beneficiario vacío o lleno
     Set de llaves foráneas en null para evitar errores de guardado*/

    const idColonia = idColoniaEditar.length === 0 ? null : idColoniaEditar;
    const cmbBancos = cmbBancosEditar.length === 0 ? null : cmbBancosEditar;

    let datosBeneficiario;

    //Si se envían vacíos en llaves foráneas se ajustan los datos
    if (idColonia === null && cmbBancos === null) {
        datosBeneficiario = {"nombre": inputNombreBeneficiario_editar,
            "apellido_paterno": inputApellidoPaternoBeneficiario_editar,
            "apellido_materno": inputApellidoMaternoBeneficiario_editar,
            "rfc": inputRfc_editar,
            "calle": inputCalleBeneficiario_editar,
            "numero_oficial": inputNumeroOficial_editar,
            "cuentabeneficiario": inputCtaBancariaBeneficiario_editar};

    } else if (idColonia === null) {
        datosBeneficiario = {"nombre": inputNombreBeneficiario_editar,
            "apellido_paterno": inputApellidoPaternoBeneficiario_editar,
            "apellido_materno": inputApellidoMaternoBeneficiario_editar,
            "rfc": inputRfc_editar,
            "calle": inputCalleBeneficiario_editar,
            "numero_oficial": inputNumeroOficial_editar,
            "cat_Banco": cmbBancos,
            "cuentabeneficiario": inputCtaBancariaBeneficiario_editar};

    } else if (cmbBancos === null) {
        datosBeneficiario = {"nombre": inputNombreBeneficiario_editar,
            "apellido_paterno": inputApellidoPaternoBeneficiario_editar,
            "apellido_materno": inputApellidoMaternoBeneficiario_editar,
            "rfc": inputRfc_editar,
            "calle": inputCalleBeneficiario_editar,
            "numero_oficial": inputNumeroOficial_editar,
            "cat_Colonia": idColonia,
            "cuentabeneficiario": inputCtaBancariaBeneficiario_editar};
    } else {
        datosBeneficiario = {"nombre": inputNombreBeneficiario_editar,
            "apellido_paterno": inputApellidoPaternoBeneficiario_editar,
            "apellido_materno": inputApellidoMaternoBeneficiario_editar,
            "rfc": inputRfc_editar,
            "calle": inputCalleBeneficiario_editar,
            "numero_oficial": inputNumeroOficial_editar,
            "cat_Colonia": idColonia,
            "cat_Banco": cmbBancos,
            "cuentabeneficiario": inputCtaBancariaBeneficiario_editar};
    }

    //Objeto datosMontos 
    let datosMontos = {
        "id_fdo_ahorro": checkfdoAhorro_editar ? 1 : 0,
        "fdo_trabajador": checkfdoTrab_editar ? 1 : 0,
        "fdo_empresa": checkfdoEmpresa_editar ? 1 : 0,
        "fdo_interes": checkfdoInter_editar ? 1 : 0,
        "descuento": chkInputMontoPagoExceso_editar,
        "apl_nomina": checkAplNomina_editar ? 1 : 0,
        "apl_finiq": checkAplFiniq_editar ? 1 : 0,
        "vales_fin_anio": checkValesFinA_editar ? 1 : 0,
        "id_deposito_bancario": checkDepositoBancario_editar ? 1 : 0,
        "anualidad": inputDescuentoAnual_editar,
        "pago_descuento": checkAplDescSobreAguinaldo_editar ? valorCheckboxDescSobreAguinaldo_editar : valorCheckboxDescSobreFondoAhorro_editar,
        "porcentaje_descuent": inputPorcD_editar,
        "periodo_gen": inputPerGen_editar};

    //Objeto Orden Judicial (Tabla Principal) 
    let ordenJudicial = {
        "porcentaje": inputPorcentaje_editar,
        "cuota_fija": inputCuotaFija_editar,
        "referencia": inputReferencia_editar,
        "modalidad": radioPorcentajeEdita ? valorRadioPorcentajeEdita : valorRadioCuotaFijaEdita,
        "juzgado": juzgado_editar,
        "expediente_caso": noExpdt_editar,
        "oficio": InputNOficio_editar,
        "fecha_recepcion": fechaRecepcion_editar,
        "periodo_aplicacion": cmbAplicacion_editar,
        "trabajador_id": trabajador_id,
        "pension_Alimenticia_B": {"id_beneficiario_pa": idBeneficiario},
        "pension_Alimenticia_Montos": {"id_montos": id_montos}
    };

    //Campos actuales en el formulario
    currentFormData = {
        // GUARDADO DE CAMPOS ACTUALES ORDEN JUDICIAL
        idPension_historico: $("#inputHiddenIdPension").val(),
        radioPorcentajeEdita_historico: $("#radioPorcentajeEdita").is(":checked"),
        radioCuotaFijaEdita_historico: $("#radioCuotaFijaEdita").is(":checked"),
        valorRadioPorcentajeEdita_historico: $("#radioPorcentajeEdita").is(":checked") ? $("#radioPorcentajeEdita").val() : 0,
        valorRadioCuotaFijaEdita_historico: $("#radioCuotaFijaEdita").is(":checked") ? $("#radioCuotaFijaEdita").val() : 0,
        inputPorcentaje_editar_historico: $("#inputPorcentaje_editar").val(),
        inputCuotaFija_editar_historico: $("#inputCuotaFija_editar").val(),
        inputReferencia_editar_historico: $("#inputReferencia_editar").val(),
        juzgado_editar_historico: $("#juzgado_editar").val(),
        noExpdt_editar_historico: $("#noExpdt_editar").val(),
        InputNOficio_editar_historico: $("#InputNOficio_editar").val(),
        fechaRecepcion_editar_historico: $("#fechaRecepcion_editar").val(),
        cmbAplicacion_editar_historico: $("#cmbAplicacion_editar").val(),

        // GUARDADO DE CAMPOS ACTUALES BENEFICIARIO
        idBeneficiario_historico: $("#inputHiddenIdBeneficiario").val(),
        inputNombreBeneficiario_editar_historico: $("#inputNombreBeneficiario_editar").val(),
        inputApellidoPaternoBeneficiario_editar_historico: $("#inputApellidoPaternoBeneficiario_editar").val(),
        inputApellidoMaternoBeneficiario_editar_historico: $("#inputApellidoMaternoBeneficiario_editar").val(),
        inputRfc_editar_historico: $("#inputRfc_editar").val(),
        inputCalleBeneficiario_editar_historico: $("#inputCalleBeneficiario_editar").val(),
        inputNumeroOficial_editar_historico: $("#inputNumeroOficial_editar").val(),
        idColoniaEditar_historico: $('#inputHiddenColoniaEditar').val(),
        inputColoniaEditar_historico: $("#inputColonia_editar").val(),
        //idPoblacionMunicipioEditar_historico: $('#inputHiddenMunicipioEditar').val(),
        inputPoblacionMunicipio_editar_historico: $("#inputPoblacionMunicipio_editar").val(),
        //idEstadoEditar_historico: $('#inputHiddenEstadoEditar').val(),
        inputEstado_editar_historico: $("#inputEstado_editar").val(),
        inputCp_editar_historico: $("#inputCp_editar").val(),
        cmbBancosEditar_historico: $("#cmbBancosEditar").val(),
        inputCtaBancariaBeneficiario_editar_historico: $("#inputCtaBancariaBeneficiario_editar").val(),

        // GUARDADO DE CAMPOS ACTUALES MONTOS
        id_montos_historico: $("#inputHiddenIdMontos").val(),
        checkfdoAhorro_editar_historico: $("#checkfdoAhorro_editar").is(":checked"),
        checkfdoTrab_editar_historico: $("#checkfdoTrab_editar").is(":checked"),
        checkfdoEmpresa_editar_historico: $("#checkfdoEmpresa_editar").is(":checked"),
        checkfdoInter_editar_historico: $("#checkfdoInter_editar").is(":checked"),
        checkAplNomina_editar_historico: $("#checkAplNomina_editar").is(":checked"),
        checkAplFiniq_editar_historico: $("#checkAplFiniq_editar").is(":checked"),
        checkValesFinA_editar_historico: $("#checkValesFinA_editar").is(":checked"),
        checkDepositoBancario_editar_historico: $("#checkDepositoBancario_editar").is(":checked"),
        inputDescuentoAnual_editar_historico: $("#inputDescuentoAnual_editar").val(),
        checkAplDescSobreAguinaldo_editar_historico: $("#checkAplDescSobreAguinaldo_editar").is(":checked"),
        valorCheckboxDescSobreAguinaldo_editar_historico: $("#checkAplDescSobreAguinaldo_editar").is(":checked") ? $("#checkAplDescSobreAguinaldo_editar").val() : 0,
        checkAplDescSobreFondoDeAhorro_editar_historico: $("#checkAplDescSobreFondoDeAhorro_editar").is(":checked"),
        valorCheckboxDescSobreFondoAhorro_editar_historico: $("#checkAplDescSobreFondoDeAhorro_editar").is(":checked") ? $("#checkAplDescSobreFondoDeAhorro_editar").val() : 0,
        chkInputMontoPagoExceso_editar_historico: $("#chkInputMontoPagoExceso_editar").val(),
        inputPorcD_editar_historico: $("#inputPorcD_editar").val(),
        inputPerGen_editar_historico: $("#inputPerGen_editar").val()
    };

    //Corroborar que no se envíen datos sin modificación para evitar guardados históricos y ediciones innecesaria
    if (JSON.stringify(originalFormData) === JSON.stringify(currentFormData)) {
        toastr["warning"]("No hay datos modificados", "Aviso", {"progressBar": true, "closeButton": true});
        return;
    }

    //Si los campos son diferentes se genera el guardado histórico y actualización del registro actual
    /***********************************************************************
     GUARDADO DE HISTORICO
     ***********************************************************************/
    let jsonBeneficiario;

    //Asignacion de casos historicos para llaves foráneas de Beneficiario
    if (originalFormData.cmbBancosEditar_historico === '' && originalFormData.idColoniaEditar_historico === '') {
        jsonBeneficiario = {
            "id_historico": originalFormData.idBeneficiario_historico,
            "nombre": originalFormData.inputNombreBeneficiario_editar_historico,
            "apellido_paterno": originalFormData.inputApellidoPaternoBeneficiario_editar_historico,
            "apellido_materno": originalFormData.inputApellidoMaternoBeneficiario_editar_historico,
            "rfc": originalFormData.inputRfc_editar_historico,
            "calle": originalFormData.inputCalleBeneficiario_editar_historico,
            "numero_oficial": originalFormData.inputNumeroOficial_editar_historico,
            "cuentabeneficiario": originalFormData.inputCtaBancariaBeneficiario_editar_historico
        };

    } else if (originalFormData.idColoniaEditar_historico === '') {
        jsonBeneficiario = {
            "id_historico": originalFormData.idBeneficiario_historico,
            "nombre": originalFormData.inputNombreBeneficiario_editar_historico,
            "apellido_paterno": originalFormData.inputApellidoPaternoBeneficiario_editar_historico,
            "apellido_materno": originalFormData.inputApellidoMaternoBeneficiario_editar_historico,
            "rfc": originalFormData.inputRfc_editar_historico,
            "calle": originalFormData.inputCalleBeneficiario_editar_historico,
            "numero_oficial": originalFormData.inputNumeroOficial_editar_historico,
            "cat_Banco": {
                "id_banco": originalFormData.cmbBancosEditar_historico
            },
            "cuentabeneficiario": originalFormData.inputCtaBancariaBeneficiario_editar_historico
        };

    } else if (originalFormData.cmbBancosEditar_historico === '') {
        jsonBeneficiario = {
            "id_historico": originalFormData.idBeneficiario_historico,
            "nombre": originalFormData.inputNombreBeneficiario_editar_historico,
            "apellido_paterno": originalFormData.inputApellidoPaternoBeneficiario_editar_historico,
            "apellido_materno": originalFormData.inputApellidoMaternoBeneficiario_editar_historico,
            "rfc": originalFormData.inputRfc_editar_historico,
            "calle": originalFormData.inputCalleBeneficiario_editar_historico,
            "numero_oficial": originalFormData.inputNumeroOficial_editar_historico,
            "cat_Colonia": {
                "id_colonia": originalFormData.idColoniaEditar_historico
            },
            "cuentabeneficiario": originalFormData.inputCtaBancariaBeneficiario_editar_historico
        };

    } else {
        jsonBeneficiario = {
            "id_historico": originalFormData.idBeneficiario_historico,
            "nombre": originalFormData.inputNombreBeneficiario_editar_historico,
            "apellido_paterno": originalFormData.inputApellidoPaternoBeneficiario_editar_historico,
            "apellido_materno": originalFormData.inputApellidoMaternoBeneficiario_editar_historico,
            "rfc": originalFormData.inputRfc_editar_historico,
            "calle": originalFormData.inputCalleBeneficiario_editar_historico,
            "numero_oficial": originalFormData.inputNumeroOficial_editar_historico,
            "cat_Colonia": {
                "id_colonia": originalFormData.idColoniaEditar_historico
            },
            "cat_Banco": {
                "id_banco": originalFormData.cmbBancosEditar_historico
            },
            "cuentabeneficiario": originalFormData.inputCtaBancariaBeneficiario_editar_historico
        };
    }

    let jsonMontos = {
        "anualidad": originalFormData.inputDescuentoAnual_editar_historico,
        "apl_finiq": originalFormData.checkAplFiniq_editar_historico ? 1 : 0,
        "apl_nomina": originalFormData.checkAplNomina_editar_historico ? 1 : 0,
        "descuento": originalFormData.chkInputMontoPagoExceso_editar_historico,
        "fdo_empresa": originalFormData.checkfdoEmpresa_editar_historico ? 1 : 0,
        "fdo_interes": originalFormData.checkfdoInter_editar_historico ? 1 : 0,
        "fdo_trabajador": originalFormData.checkfdoTrab_editar_historico ? 1 : 0,
        "id_deposito_bancario": originalFormData.checkDepositoBancario_editar_historico ? 1 : 0,
        "id_fdo_ahorro": originalFormData.checkfdoAhorro_editar_historico ? 1 : 0,
        "id_historico": originalFormData.id_montos_historico,
        //"id_montos_historico": 0,
        "pago_descuento": originalFormData.checkAplDescSobreAguinaldo_editar_historico ? originalFormData.valorCheckboxDescSobreAguinaldo_editar_historico : originalFormData.valorCheckboxDescSobreFondoAhorro_editar_historico,
        "periodo_gen": originalFormData.inputPerGen_editar_historico,
        "porcentaje_descuent": originalFormData.inputPorcD_editar_historico,
        "vales_fin_anio": originalFormData.checkValesFinA_editar_historico ? 1 : 0
    };

    //Ajax Beneficiario
    let idBeneficiarioHistorico;

    $.ajax({
        type: "POST",
        async: false,
        url: "pensionAlimenticia/guardarBHistorico",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(jsonBeneficiario),
        success: function (data) {
            idBeneficiarioHistorico = data.id_beneficiario_pa_historico;
            //toastr['success']('Se ha guardado el Histórico de Beneficiario');
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

    //Ajax Montos
    let idMontosHistorico;

    $.ajax({
        type: "POST",
        async: false,
        url: "pensionAlimenticia/guardarMHistorico",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(jsonMontos),
        success: function (data) {
            idMontosHistorico = data.id_montos_historico;
            //toastr['success']('Se ha guardado el Histórico de Montos');
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

    let jsonOrdenJudicial = {
        "pension_Alimenticia_B_Historico": {
            "id_beneficiario_pa_historico": idBeneficiarioHistorico
        },
        "cuota_fija": originalFormData.inputCuotaFija_editar_historico,
        "expediente_caso": originalFormData.noExpdt_editar_historico,
        "fecha_recepcion": originalFormData.fechaRecepcion_editar_historico,
        "id_historico": originalFormData.idPension_historico,
        "juzgado": originalFormData.juzgado_editar_historico,
        "modalidad": originalFormData.radioPorcentajeEdita_historico ? originalFormData.valorRadioPorcentajeEdita_historico : originalFormData.valorRadioCuotaFijaEdita_historico,
        "pension_Alimenticia_Montos_Historico": {
            "id_montos_historico": idMontosHistorico
        },
        "oficio": originalFormData.InputNOficio_editar_historico,
        "periodo_aplicacion": originalFormData.cmbAplicacion_editar_historico,
        "porcentaje": originalFormData.inputPorcentaje_editar_historico,
        "referencia": originalFormData.inputReferencia_editar_historico,
        "trabajador_id": trabajador_id
    };

    //Ajax Orden Judicial
    $.ajax({
        type: "POST",
        url: "pensionAlimenticia/guardarOJHistorico",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(jsonOrdenJudicial),
        success: function (data) {
            toastr['success']('Se ha guardado el Histórico de Ediciones');
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

    /***********************************************************************
     UPDATE 
     ***********************************************************************/
    let json;
    if (cmbBancos === null && idColonia === null) {
        json = {
            "cuota_fija": ordenJudicial.cuota_fija,
            "estatus": true,
            "expediente_caso": ordenJudicial.expediente_caso,
            "fecha_baja_pension": null,
            //"fecha_movimiento": null, Viene de la implementación del servicio
            "fecha_recepcion": ordenJudicial.fecha_recepcion,
            //"id_pension": 0, 
            "juzgado": ordenJudicial.juzgado,
            "modalidad": ordenJudicial.modalidad,
            "oficio": ordenJudicial.oficio,
            "pension_Alimenticia_B": {
                "apellido_materno": datosBeneficiario.apellido_materno,
                "apellido_paterno": datosBeneficiario.apellido_paterno,
                "calle": datosBeneficiario.calle,
                "cuentabeneficiario": datosBeneficiario.cuentabeneficiario,
                "id_beneficiario_pa": idBeneficiario,
                "nombre": datosBeneficiario.nombre,
                "numero_oficial": datosBeneficiario.numero_oficial,
                "rfc": datosBeneficiario.rfc
            },
            "pension_Alimenticia_Montos": {
                "anualidad": datosMontos.anualidad,
                "apl_finiq": datosMontos.apl_finiq,
                "apl_nomina": datosMontos.apl_nomina,
                "descuento": datosMontos.descuento,
                "fdo_empresa": datosMontos.fdo_empresa,
                "fdo_interes": datosMontos.fdo_interes,
                "fdo_trabajador": datosMontos.fdo_trabajador,
                "id_deposito_bancario": datosMontos.id_deposito_bancario,
                "id_fdo_ahorro": datosMontos.id_fdo_ahorro,
                "id_montos": id_montos,
                "pago_descuento": datosMontos.pago_descuento,
                "periodo_gen": datosMontos.periodo_gen,
                "porcentaje_descuent": datosMontos.porcentaje_descuent,
                "vales_fin_anio": datosMontos.vales_fin_anio
            },
            "periodo_aplicacion": ordenJudicial.periodo_aplicacion,
            "porcentaje": ordenJudicial.porcentaje,
            "referencia": ordenJudicial.referencia,
            "trabajador_id": ordenJudicial.trabajador_id
        };
    } else if (idColonia === null) {
        json = {
            "cuota_fija": ordenJudicial.cuota_fija,
            "estatus": true,
            "expediente_caso": ordenJudicial.expediente_caso,
            "fecha_baja_pension": null,
            //"fecha_movimiento": null, Viene de la implementación del servicio
            "fecha_recepcion": ordenJudicial.fecha_recepcion,
            //"id_pension": 0, 
            "juzgado": ordenJudicial.juzgado,
            "modalidad": ordenJudicial.modalidad,
            "oficio": ordenJudicial.oficio,
            "pension_Alimenticia_B": {
                "apellido_materno": datosBeneficiario.apellido_materno,
                "apellido_paterno": datosBeneficiario.apellido_paterno,
                "calle": datosBeneficiario.calle,
                "cat_Banco": {
                    "id_banco": cmbBancos
                },
                "cuentabeneficiario": datosBeneficiario.cuentabeneficiario,
                "id_beneficiario_pa": idBeneficiario,
                "nombre": datosBeneficiario.nombre,
                "numero_oficial": datosBeneficiario.numero_oficial,
                "rfc": datosBeneficiario.rfc
            },
            "pension_Alimenticia_Montos": {
                "anualidad": datosMontos.anualidad,
                "apl_finiq": datosMontos.apl_finiq,
                "apl_nomina": datosMontos.apl_nomina,
                "descuento": datosMontos.descuento,
                "fdo_empresa": datosMontos.fdo_empresa,
                "fdo_interes": datosMontos.fdo_interes,
                "fdo_trabajador": datosMontos.fdo_trabajador,
                "id_deposito_bancario": datosMontos.id_deposito_bancario,
                "id_fdo_ahorro": datosMontos.id_fdo_ahorro,
                "id_montos": id_montos,
                "pago_descuento": datosMontos.pago_descuento,
                "periodo_gen": datosMontos.periodo_gen,
                "porcentaje_descuent": datosMontos.porcentaje_descuent,
                "vales_fin_anio": datosMontos.vales_fin_anio
            },
            "periodo_aplicacion": ordenJudicial.periodo_aplicacion,
            "porcentaje": ordenJudicial.porcentaje,
            "referencia": ordenJudicial.referencia,
            "trabajador_id": ordenJudicial.trabajador_id
        };
    } else if (cmbBancos === null) {
        json = {
            "cuota_fija": ordenJudicial.cuota_fija,
            "estatus": true,
            "expediente_caso": ordenJudicial.expediente_caso,
            "fecha_baja_pension": null,
            //"fecha_movimiento": null, Viene de la implementación del servicio
            "fecha_recepcion": ordenJudicial.fecha_recepcion,
            //"id_pension": 0, 
            "juzgado": ordenJudicial.juzgado,
            "modalidad": ordenJudicial.modalidad,
            "oficio": ordenJudicial.oficio,
            "pension_Alimenticia_B": {
                "apellido_materno": datosBeneficiario.apellido_materno,
                "apellido_paterno": datosBeneficiario.apellido_paterno,
                "calle": datosBeneficiario.calle,
                "cat_Colonia": {
                    "id_colonia": idColonia
                },
                "cuentabeneficiario": datosBeneficiario.cuentabeneficiario,
                "id_beneficiario_pa": idBeneficiario,
                "nombre": datosBeneficiario.nombre,
                "numero_oficial": datosBeneficiario.numero_oficial,
                "rfc": datosBeneficiario.rfc
            },
            "pension_Alimenticia_Montos": {
                "anualidad": datosMontos.anualidad,
                "apl_finiq": datosMontos.apl_finiq,
                "apl_nomina": datosMontos.apl_nomina,
                "descuento": datosMontos.descuento,
                "fdo_empresa": datosMontos.fdo_empresa,
                "fdo_interes": datosMontos.fdo_interes,
                "fdo_trabajador": datosMontos.fdo_trabajador,
                "id_deposito_bancario": datosMontos.id_deposito_bancario,
                "id_fdo_ahorro": datosMontos.id_fdo_ahorro,
                "id_montos": id_montos,
                "pago_descuento": datosMontos.pago_descuento,
                "periodo_gen": datosMontos.periodo_gen,
                "porcentaje_descuent": datosMontos.porcentaje_descuent,
                "vales_fin_anio": datosMontos.vales_fin_anio
            },
            "periodo_aplicacion": ordenJudicial.periodo_aplicacion,
            "porcentaje": ordenJudicial.porcentaje,
            "referencia": ordenJudicial.referencia,
            "trabajador_id": ordenJudicial.trabajador_id
        };
    } else {
        json = {
            "cuota_fija": ordenJudicial.cuota_fija,
            "estatus": true,
            "expediente_caso": ordenJudicial.expediente_caso,
            "fecha_baja_pension": null,
            //"fecha_movimiento": null, Viene de la implementación del servicio
            "fecha_recepcion": ordenJudicial.fecha_recepcion,
            //"id_pension": 0, 
            "juzgado": ordenJudicial.juzgado,
            "modalidad": ordenJudicial.modalidad,
            "oficio": ordenJudicial.oficio,
            "pension_Alimenticia_B": {
                "apellido_materno": datosBeneficiario.apellido_materno,
                "apellido_paterno": datosBeneficiario.apellido_paterno,
                "calle": datosBeneficiario.calle,
                "cat_Banco": {
                    "id_banco": cmbBancos
                },
                "cat_Colonia": {
                    "id_colonia": idColonia
                },
                "cuentabeneficiario": datosBeneficiario.cuentabeneficiario,
                "id_beneficiario_pa": idBeneficiario,
                "nombre": datosBeneficiario.nombre,
                "numero_oficial": datosBeneficiario.numero_oficial,
                "rfc": datosBeneficiario.rfc
            },
            "pension_Alimenticia_Montos": {
                "anualidad": datosMontos.anualidad,
                "apl_finiq": datosMontos.apl_finiq,
                "apl_nomina": datosMontos.apl_nomina,
                "descuento": datosMontos.descuento,
                "fdo_empresa": datosMontos.fdo_empresa,
                "fdo_interes": datosMontos.fdo_interes,
                "fdo_trabajador": datosMontos.fdo_trabajador,
                "id_deposito_bancario": datosMontos.id_deposito_bancario,
                "id_fdo_ahorro": datosMontos.id_fdo_ahorro,
                "id_montos": id_montos,
                "pago_descuento": datosMontos.pago_descuento,
                "periodo_gen": datosMontos.periodo_gen,
                "porcentaje_descuent": datosMontos.porcentaje_descuent,
                "vales_fin_anio": datosMontos.vales_fin_anio
            },
            "periodo_aplicacion": ordenJudicial.periodo_aplicacion,
            "porcentaje": ordenJudicial.porcentaje,
            "referencia": ordenJudicial.referencia,
            "trabajador_id": ordenJudicial.trabajador_id
        };
    }

    //Update en pensión original
    $.ajax({
        type: "POST",
        url: "pensionAlimenticia/actualizarPensionAlimenticia/" + idPension,
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(json),
        success: function (data) {
            $("#formPensionAlimenticiaEdita")[0].reset();
            $("#formPensionAlimenticiaEdita").removeClass("was-validated");
            $("#modificarPensionAlimenticia").modal('hide');
            //Remover mensajes de validación cuando el guardado es correcto
            toastr['success']('Se han guardado correctamente todas las ediciones');
            $('#tablaBeneficiarios').DataTable().ajax.reload();
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

$tablaTrabajadores = $('#tabla_Trabajadores').dataTable({
    "ajax": {
        url: "trabajador/listadoTrabajadoresPlaza",
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
        "searchPlaceholder": "Buscar",
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

        {data: "cat_expediente.numero_expediente"},
        {data: "persona.nombre"},
        {data: "persona.apellido_paterno"},
        {data: "persona.apellido_materno"},
        {data: "persona.fecha_nacimiento"},
        {data: "persona.cat_genero.desc_genero"},
        {data: "persona.cat_estado_civil.desc_edo_civil"},
        {data: "persona.curp"},
        {data: "persona.fecha_captura", render: function (data, type, row) {
                var fechaCaptura = new Date(data);
                return fechaCaptura.toLocaleDateString();
            }},
        {data: "fecha_antiguedad", render: function (data, type, row) {
                var fechaCaptura = new Date(data);
                return fechaCaptura.toLocaleDateString();
            }},
        {data: "", sTitle: "Pensión Alimenticia", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="asignacionPension(' + r.id_trabajador + ')"><span class="fa fa-edit"></span>Asignación de Pensión</button>';
                return he;
            }
        }]
});

function edicionBeneficiario(id_trabajador, id_pension) {
    console.log(id_trabajador, id_pension);
    window.location.href = 'pAlimenticiaTabla?tab=tab-2' + "&id_trabajador=" + id_trabajador + "&id_pension=" + id_pension;
}

function asignacionPension(id_trabajador) {
    window.location.href = 'pAlimenticiaTabla?tab=tab-2' + "&id_trabajador=" + id_trabajador;
}

const url = "report/allPensionesAlimenticias?tipo=PDF";
const nombreArchivo = "PensionAlimenticia.pdf";

function mostrarMensajeSiVacio(data) {
    if ($.isEmptyObject(data)) {
        toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
        return true;
    }
    return false;
}

function realizarSolicitudAjax(successCallback, errorCallback) {
    $.ajax({
        type: "GET",
        url: url,
        xhrFields: {
            responseType: 'blob'
        },
        success: successCallback,
        error: errorCallback
    });
}

function visualizarReportePensionAlimenticia() {
    realizarSolicitudAjax(function (data, status, xhr) {
        if (mostrarMensajeSiVacio(data)) {
            return;
        }

        $("#pensionAlimenticiaModal").modal("toggle");
        var contentType = xhr.getResponseHeader("Content-Type");
        var filename = xhr.getResponseHeader("Content-disposition");
        filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
        var url = window.URL.createObjectURL(data);
        var frame = $('#reportes_frame');
        frame.attr('src', url).show();
    }, function (e) {
        toastr["warning"]("Error al recuperar información de documentos cargados: ", {progressBar: true, closeButton: true});
    });
}

function generarReportePensionAlimenticia() {
    realizarSolicitudAjax(function (data, textStatus, jqXHR) {
        if (mostrarMensajeSiVacio(data)) {
            return;
        }
        toastr["success"]("Reporte generado con éxito", "Aviso", {progressBar: true, closeButton: true});
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(data);
        link.download = nombreArchivo;
        link.click();
    }, function (e) {
        toastr["warning"]("Error al recuperar datos: " + e, "Error", {progressBar: true, closeButton: true});
    });
}

function validacion(object) {
    if (object.value.length > 7) {
        object.value = object.value.slice(0, 7);

    }
}