/* global $tablaExpediente, finalName, cadena3, slice, consoloe, $tablaHorarios */
document.addEventListener('DOMContentLoaded', () => {
    cargaCmbTipoNomina();
    cargaCmbIncidenciasKardex();
    cargaCmbPeriodos(1);
    //Evento de escucha al cambio de la nómina 
    $('#cmbTipoNomina').on('change', function () {
        const valorSeleccionadoComboTipoNomina = $(this).val();
        cargaCmbPeriodos(valorSeleccionadoComboTipoNomina);
        filtradoDatosActuales($('#cmbTipoNomina').val(), $('#cmbIncidenciasKardex').val(), $('#cmbPeriodos').val());
    });
    //Evento de escucha a las incidencias del kárdex
    $('#cmbIncidenciasKardex').on('change', function () {
        const valorSeleccionadoComboIncidenciasKardex = $(this).val();
        filtradoDatosActuales($('#cmbTipoNomina').val(), $('#cmbIncidenciasKardex').val(), $('#cmbPeriodos').val());
    });
    //Evento de escucha al cambio a los periodos de pago
    $('#cmbPeriodos').on('change', function () {
        const valorSeleccionadoComboPeriodos = $(this).val();
        filtradoDatosActuales($('#cmbTipoNomina').val(), $('#cmbIncidenciasKardex').val(), $('#cmbPeriodos').val());
    });
    ;

});

const cargaCmbTipoNomina = (id) => {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_tipo_nomina === id) ? "selected" : "";
                        $('#cmbTipoNomina').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                    }
                    $('#cmbTipoNomina').val("1");
                    filtradoDatosActuales($('#cmbTipoNomina').val());
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Tipo Nómina : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};

const cargaCmbIncidenciasKardex = (id) => {
    $.ajax({
        type: "GET",
        url: "catalogos/listarIncidenciasKardex",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbIncidenciasKardex').empty().append("<option value=''> Todas las incidencias / Sin incidencias</option>");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_incidencia === id) ? "selected" : "";
                        var option = $('<option value="' + data[x].id_incidencia + '"' + vselected + '>' + data[x].descripcion + '</option>');
                        $('#cmbIncidenciasKardex').append(option);
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Incidencias Kardex : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};

const cargaCmbPeriodos = (valorSeleccionadoComboTipoNomina) => {
    $('#cmbPeriodos').empty();
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTodosLosPeriodosPorNomina/" + valorSeleccionadoComboTipoNomina,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                var vselected = "";
                if (data.length > 0) {
                    // Agrega la opción por defecto al inicio
                    $('#cmbPeriodos').append('<option value="" selected>Todos los periodos</option>');
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
                        $('#cmbPeriodos').append('<option value="' + data[x].id_periodo_pago + '"' + vselected + '>' + 'P. ' + (n_periodo.toString()).substr(4, 5) + " : " + " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial +
                                '--' + dias_final + '/' + meses_final + '/' + anios_final + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["error"](e, "Error", {progressBar: true, closeButton: true});
        }
    });
};


const filtradoDatosActuales = (idNomina, idIncidenciaKardex, periodoPago) => {
    let url;
    //Url filtrado de solo nómina
    if (idNomina && !idIncidenciaKardex && !periodoPago) {
        url = "trabajador/listadoTrabajadoresPlazaTipoNomina/" + idNomina;
        generarTabla(url);
    }
    //Url para filtrado solo de incidencias y nómina
    else if (idNomina && idIncidenciaKardex && !periodoPago) {
        url = "trabajador/filtradoIncidenciasTrabajadoresKardex/" + idNomina + "/" + idIncidenciaKardex;
        generarTabla(url);
    }
    //Url para filtrado de incidencias, periodos y nómina
    else if (idNomina && idIncidenciaKardex && periodoPago) {
        obtenerFechasPeriodosPago(periodoPago)
                .then((fechaPeriodoPago) => {
                    let fechaInicio = convertirFechaFormato(fechaPeriodoPago.fechaInicio);
                    let fechaFin = convertirFechaFormato(fechaPeriodoPago.fechaFin);
                    url = "trabajador/filtradoGeneralTrabajadoresKardex/" + idNomina + "/" + idIncidenciaKardex + "/" + fechaInicio + "/" + fechaFin;
                    generarTabla(url);
                })
                .catch((error) => {
                    toastr["error"]("Error al obtener fechas: " + error, "Error", {progressBar: true, closeButton: true});
                });
    }
    //Url para filtrado de periodos
    else if (idNomina && periodoPago && !idIncidenciaKardex) {
        obtenerFechasPeriodosPago(periodoPago)
                .then((fechaPeriodoPago) => {
                    let fechaInicio = convertirFechaFormato(fechaPeriodoPago.fechaInicio);
                    let fechaFin = convertirFechaFormato(fechaPeriodoPago.fechaFin);
                    url = "trabajador/filtradoPeriodosTrabajadoresIncidenciasKardex/" + idNomina + "/" + fechaInicio + "/" + fechaFin;
                    generarTabla(url);
                })
                .catch((error) => {
                    toastr["error"]("Error al obtener fechas: " + error, "Error", {progressBar: true, closeButton: true});
                });
    }

};

const generarTabla = (url) => {
    //Se destruye la instancia de la tabla en caso de que haya sido generada anteriormente
    if ($.fn.DataTable.isDataTable('#tabla_Trabajadores')) {
        $('#tabla_Trabajadores').DataTable().destroy();
    }
    $('#tabla_Trabajadores').dataTable({
        "ajax": {
            url: url,
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

            {data: "cat_expediente.numero_expediente"},
            {data: "persona.nombre"},
            {data: "persona.apellido_paterno"},
            {data: "persona.apellido_materno"},
            {data: "persona.fecha_nacimiento", render: function (data, type, row) {
                    // Formatear la fecha de nacimiento
                    var fechaNacimiento = new Date(data);
                    return fechaNacimiento.toLocaleDateString();
                }},
            {data: "persona.cat_genero.desc_genero"},
            {data: "persona.cat_estado_civil.desc_edo_civil"},
            {data: "persona.curp"},
            {data: "persona.fecha_captura", render: function (data, type, row) {
                    var fechaCaptura = new Date(data);
                    return fechaCaptura.toLocaleDateString();
                }},
            {data: "fecha_antiguedad", render: function (data, type, row) {
                    var fechaAntiguedad = new Date(data);
                    return fechaAntiguedad.toLocaleDateString();
                }},
            {data: "", sTitle: "Administrar Incidencias", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" onclick="administrarIncidencias(' + r.id_trabajador + ')"><span class="fa fa-edit"></span>Incidencias</button>';
                    return he;
                }
            }]
    });
};

const convertirFechaFormato = (fechaString) => {
    const fecha = new Date(fechaString);
    const year = fecha.getFullYear();
    const month = (fecha.getMonth() + 1).toString().padStart(2, '0');
    const day = fecha.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
};

const obtenerFechasPeriodosPago = (idPeriodoPago) => {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "GET",
            url: "trabajador/buscarPeriodoPagoId/" + idPeriodoPago,
            dataType: 'json',
            success: function (data) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontró periodo de pago...", "Aviso", {progressBar: true, closeButton: true});
                    reject("No se encontró periodo de pago");
                } else {
                    const startDate = data.data.fecha_inicial;
                    const endDate = data.data.fecha_final;

                    const fechaPeriodoPago = {
                        fechaInicio: startDate,
                        fechaFin: endDate
                    };

                    resolve(fechaPeriodoPago);
                }
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar Select Incidencias Kardex : " + e, " error", {progressBar: true, closeButton: true});
                reject("Error al recuperar periodo de pago");
            }
        });
    });
};

function administrarIncidencias(id_trabajador) {
    window.location.href = 'trabajadorIncidencias?id_trabajador=' + id_trabajador;
}

function importacionIncidencias() {
    window.location.href = 'importacionIncidenciasKardex';
}