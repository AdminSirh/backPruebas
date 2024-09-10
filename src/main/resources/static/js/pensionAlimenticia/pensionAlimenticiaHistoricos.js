let trabajador_id;
document.addEventListener('DOMContentLoaded', () => {
    // Obtener parámetros de la URL y realizar operaciones
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    trabajador_id = urlParams.get('id_trabajador');
    cmbBancos_historico();
    //tablaHistoricoEdiciones(trabajador_id);
    buscarTrabajador(trabajador_id);
    buscarNomina(trabajador_id);
    pensionAlimenticiaHistoricoInactivas(trabajador_id);
    vincularEventosInputs();
});

function vincularEventosInputs() {
    //Deshabilitar los inputs
    $('input[type=radio]').attr('disabled', true);
    $('input[type=checkbox]').attr('disabled', true);
    // Agregar eventos de entrada a los campos de fecha
    $('#fechaInicialHistorico, #fechaFinalHistorico').on('input', actualizarEstadoBotonFiltrar);

    //Comprobación para no enviar la solicitud si las fehcas ya fueron filtradas
    let fechaInicialAnterior = null;
    let fechaFinalAnterior = null;
    //Vincula el evento click al filtrado
    $('#btnFiltrar').on('click', function () {
        let fechaInicial = $('#fechaInicialHistorico').val();
        let fechaFinal = $('#fechaFinalHistorico').val();

        if (fechaInicial === fechaInicialAnterior && fechaFinal === fechaFinalAnterior) {
            toastr["warning"]("Se está mostrando el filtrado actual", "Aviso", {progressBar: true, closeButton: true});
            return;
        }

        fechaInicialAnterior = fechaInicial;
        fechaFinalAnterior = fechaFinal;

        tablaHistoricoEdiciones(trabajador_id, fechaInicial, fechaFinal);
    });
}
// Función para habilitar/deshabilitar el botón "Filtrar" según las fechas
function actualizarEstadoBotonFiltrar() {
    toastr["info"]("Selecciona ambas fechas para habilitar el filtrado", "Información", {progressBar: true, closeButton: true});
    var fechaInicial = $('#fechaInicialHistorico').val();
    var fechaFinal = $('#fechaFinalHistorico').val();

    if (fechaInicial && fechaFinal) {
        $('#btnFiltrar').prop('disabled', false);
    } else {
        $('#btnFiltrar').prop('disabled', true);
    }
}

//Busca los datos del trabajador para posteriores operaciones con los mismos
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
//Encuentra datos relacionados con la nómina del trabajador
function buscarNomina(trabajador_id) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPlazaTrabajador/" + trabajador_id,
        dataType: 'json',
        success: function (data) {
            $('#campo_nomina').val(data.data.cat_Tipo_Nomina.desc_nomina);
            $('#id_tipo_nomina').val(data.data.cat_Tipo_Nomina.id_tipo_nomina);
            obtenPeriodos(data.data.cat_Tipo_Nomina.id_tipo_nomina);
        },
        error: function (e) {
            alert(e);
        }
    });
}
//Obtener periodos de pago
function obtenPeriodos(id_nomina) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscaPeriodosFechas/" + id_nomina,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                var vselected = "";
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
                        $('#cmbAplicacion_editar').append('<option value="' + data[x].id_periodo_pago + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
                                '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>');
                        $('#cmbAplicacion_historico').append('<option value="' + data[x].id_periodo_pago + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
                                '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            alert(e);
        }
    });
}
//Se lista en la tabla correspondiente las incidencias inactivas
function pensionAlimenticiaHistoricoInactivas(trabajador_id) {
    tablaPensionInactivas = $('#tablaPensionInactivas').DataTable({
        "ajax": {
            url: "pensionAlimenticia/buscartrabajadorOrdenJudicialInactiva/" + trabajador_id,
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
            {data: "", sTitle: "Ver", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#verPensionAlimenticiaInactivas" onclick="cargaDatosInactivo(' + r.id_pension + ')" > ' + '<span class="fa fa-search"> </span> Ver</button>';
                    return he;
                }}
        ]
    });
}

//Se listan en una tabla todas las ediciones realizadas a creditos de un trabajador en específico
function tablaHistoricoEdiciones(trabajador_id, fechaInicial, fechaFinal) {
    // Obtener la referencia a la tabla existente, si existe
    let tablaEdiciones = $('#tablaEdiciones').DataTable();

    // Destruir la tabla existente si está inicializada
    if ($.fn.DataTable.isDataTable('#tablaEdiciones')) {
        tablaEdiciones.destroy();
    }

    const url = "pensionAlimenticia/buscarTrabajadorPensionAHistorico/" + trabajador_id + "/" + fechaInicial + "/" + fechaFinal;
    tablaEdiciones = $('#tablaEdiciones').DataTable({
        "ajax": {
            url: url,
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
            {data: "anualidad"},
            {data: "nombre",
                render: function (data, type, row) {
                    var nombreCompleto = row.nombre + " " + row.apellido_paterno + " " + row.apellido_materno;
                    return nombreCompleto;
                }
            },
            {data: "periodo_aplicacion"},
            {data: "fecha_recepcion"},
            {data: "referencia"},
            {data: "", sTitle: "Ver", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#verHistoricoPensionAlimenticia" onclick="cargaDatosHistorico(' + r.id_pension_historico + ')" > ' + '<span class="fa fa-eye"> </span> Ver</button>';
                    return he;
                }},
            {data: "fecha_movimiento"}
        ]
    });
}
//Se listan los bancos para poder seleccionarlos según el valor proveniente de la base de datos
function cmbBancos_historico(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarBancos",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbBancos_historico').empty().append("<option value=''>* </option> ");
                $('#cmbBancos_editar').empty().append("<option value=''>* </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_banco === id) ? vselected = "selected" : vselected = "----";
                        $('#cmbBancos_historico').append('<option value="' + data[x].id_banco + '"' + vselected + '>' + data[x].nombre_banco + '</option>');
                    }
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_banco === id) ? vselected = "selected" : vselected = "----";
                        $('#cmbBancos_editar').append('<option value="' + data[x].id_banco + '"' + vselected + '>' + data[x].nombre_banco + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//Se completan los campos correspondientes para la ubicación
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

                $('#inputColonia').val(descColonia);
                $('#inputColonia_editar').val(descColonia);
                $('#inputPoblacionMunicipio').val(municipio);
                $('#inputEstado').val(estado);
                $('#inputPoblacionMunicipio_historico').val(municipio);
                $('#inputPoblacionMunicipio_editar').val(municipio);
                $('#inputEstado_historico').val(estado);
                $('#inputEstado_editar').val(estado);
                $('#inputColonia, #inputColonia_historico, #inputColonia_editar').empty().append("<option value= ''>*Selecciona la Colonia</option> ");

                for (let i = 0; i < data.data.length; i++) {
                    $('#inputColonia, #inputColonia_historico, #inputColonia_editar').append('<option value="' + data.data[i].id_colonia + '"' + '>' + data.data[i].desc_colonia + '</option>');
                }
                //Selecciona el campo al momento de realizar la edición
                $('#inputColonia_historico').val(idColonia).change();
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
//Se cargan los datos en el formulario para ver los datos de ediciones de una pensión en específico 
function cargaDatosHistorico(id_pension_historico) {
    $.ajax({
        type: "GET",
        url: "pensionAlimenticia/buscarPensionAHistorico/" + id_pension_historico,
        dataType: 'json',
        success: function (data) {
            //Obtener ids para edición
            $('#inputHiddenIdBeneficiario').val(data[0].id_beneficiario_pa);
            $('#inputHiddenIdMontos').val(data[0].id_montos);
            //Comprobación de nulos en llaves foráneas
            if (data[0].cat_Colonia !== null) {
                $('#inputHiddenColoniaEditar').val(data[0].cat_Colonia.id_colonia);
                $('#inputCp_historico').val(data[0].cat_Colonia.cod_postal);
                rellenarUbicacion(data[0].cat_Colonia.cod_postal);
            }
            if (data[0].cat_Banco !== null) {
                $('#cmbBancos_historico').val(data[0].cat_Banco.id_banco);
            }
            $('#checkfdoAhorro_historico').prop('checked', data[0].id_fdo_ahorro === 1 ? true : false);
            $('#checkfdoEmpresa_historico').prop('checked', data[0].fdo_empresa === true ? true : false);
            $('#checkfdoInter_historico').prop('checked', data[0].fdo_interes === true ? true : false);
            $('#checkAplNomina_historico').prop('checked', data[0].apl_nomina === true ? true : false);
            $('#checkAplFiniq_historico').prop('checked', data[0].apl_finiq === true ? true : false);
            $('#checkValesFinA_historico').prop('checked', data[0].vales_fin_anio === true ? true : false);
            $('#checkDepositoBancario_historico').prop('checked', data[0].id_deposito_bancario === true ? true : false);
            $('#checkfdoTrab_historico').prop('checked', data[0].fdo_trabajador === true ? true : false);
            $('#chkInputMontoPagoExceso_historico').val(data[0].descuento);
            $('#checkAplDescSobreAguinaldo_historico').prop('checked', data[0].pago_descuento === 1 ? true : false);
            $('#checkAplDescSobreFondoDeAhorro_historico').prop('checked', data[0].pago_descuento === 2 ? true : false);

            $('#inputPorcentaje_historico').val(data[0].porcentaje);
            $('#inputCuotaFija_historico').val(data[0].cuota_fija);
            $('#inputPorcentaje_historico').prop('disabled', data[0].modalidad === 1 ? false : true);
            $('#inputCuotaFija_historico').prop('disabled', data[0].modalidad === 2 ? false : true);
            $('#radioPorcentajeEdita').prop('checked', data[0].modalidad === 1 ? true : false);
            $('#radioCuotaFijaEdita').prop('checked', data[0].modalidad === 2 ? true : false);
            $('#juzgado_historico').val(data[0].juzgado);
            $('#fechaRecepcion_historico').val(data[0].fecha_recepcion);
            $('#noExpdt_historico').val(data[0].expediente_caso);
            $('#cmbAplicacion_historico').append(new Option(data[0].periodo_aplicacion, data[0].periodo_aplicacion)).val(data[0].periodo_aplicacion);
            $('#inputReferencia_historico').val(data[0].referencia);
            $('#InputNOficio_historico').val(data[0].oficio);
            $('#inputNombreBeneficiario_historico').val(data[0].nombre);
            $('#inputApellidoPaternoBeneficiario_historico').val(data[0].apellido_paterno);
            $('#inputApellidoMaternoBeneficiario_historico').val(data[0].apellido_materno);
            $('#inputRfc_historico').val(data[0].rfc);
            $('#inputCalleBeneficiario_historico').val(data[0].calle);
            $('#inputNumeroOficial_historico').val(data[0].numero_oficial);
            $('#inputCtaBancariaBeneficiario_historico').val(data[0].cuentabeneficiario);
            $('#inputDescuentoAnual_historico').val(data[0].anualidad);
            $('#inputPorcD_historico').val(data[0].porcentaje_descuent);
            $('#inputPerGen_historico').val(data[0].periodo_gen);
        },
        error: function (e) {
            alert(e);
        }
    });
}
//Se muestran los datos dentro de las pensiones innactivas
function cargaDatosInactivo(id_pension_inactiva) {
    $.ajax({
        type: "GET",
        url: "pensionAlimenticia/buscartrabajadorPensionA/" + id_pension_inactiva,
        dataType: 'json',
        success: function (data) {
            //Obtener ids para edición
            $('#inputHiddenIdPension').val(id_pension_inactiva);
            $('#inputHiddenIdBeneficiario').val(data[0].pension_Alimenticia_B.id_beneficiario_pa);
            $('#inputHiddenIdMontos').val(data[0].pension_Alimenticia_Montos.id_montos);
            //Se revisan las llaves foráneas para colocar o no los datos provenientes del json en el formulario
            if (data[0].pension_Alimenticia_B.cat_Colonia !== null) {
                $('#inputHiddenColoniaEditar').val(data[0].pension_Alimenticia_B.cat_Colonia.id_colonia);
                $('#inputCp_editar').val(data[0].pension_Alimenticia_B.cat_Colonia.cod_postal);
                rellenarUbicacion(data[0].pension_Alimenticia_B.cat_Colonia.cod_postal);
            }
            if ((data[0].pension_Alimenticia_B.cat_Banco) !== null) {
                $('#cmbBancos_editar').val(data[0].pension_Alimenticia_B.cat_Banco.id_banco);
            }

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
            $('#cmbAplicacion_editar').val(data[0].periodo_aplicacion);
            $('#inputReferencia_editar').val(data[0].referencia);
            $('#InputNOficio_editar').val(data[0].oficio);
            $('#inputNombreBeneficiario_editar').val(data[0].pension_Alimenticia_B.nombre);
            $('#inputApellidoPaternoBeneficiario_editar').val(data[0].pension_Alimenticia_B.apellido_paterno);
            $('#inputApellidoMaternoBeneficiario_editar').val(data[0].pension_Alimenticia_B.apellido_materno);
            $('#inputRfc_editar').val(data[0].pension_Alimenticia_B.rfc);
            $('#inputCalleBeneficiario_editar').val(data[0].pension_Alimenticia_B.calle);
            $('#inputNumeroOficial_editar').val(data[0].pension_Alimenticia_B.numero_oficial);

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
//Limpieza de datos al cerrar el modal
$('#verPensionAlimenticiaInactivas').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación y limpieza de campos
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    $("#formPensionAlimenticiaEdita")[0].reset();
    $("#inputHiddenColoniaEditar").val('');
    $('#inputColonia_editar').empty();
});