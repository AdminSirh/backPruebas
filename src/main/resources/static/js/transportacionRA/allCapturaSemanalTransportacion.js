const nominaTransportacion = 2;

document.addEventListener('DOMContentLoaded', () => {
    obtenerPeriodoActual();
});
function obtenerPeriodoActual() {
    $.ajax({
        type: "GET",
        url: "trabajador/buscaPeriodosFechas/" + nominaTransportacion,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                if (data.length > 0) {
                    // Selecciona automáticamente la opción del periodo más cercano.
                    $('#labelPeriodoActual').text('Periodo Actual: ' + data[0].periodo);
                    $('#periodoActual').val(data[0].periodo);
                    generarTabla(data[0].periodo);
                }
            }
        },
        error: function (e) {
            toastr["error"](e, "Error al obtener periodos de la nómina", {progressBar: true, closeButton: true});
        }
    });
}

function generarTabla(periodo) {
    //Se genera la nueva instancia de la tabla
    $('#tabla_Trabajadores').dataTable({
        "ajax": {
            url: "ra15/listadoTrabajadoresRA15/" + periodo,
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

            {data: "numero_expediente"},
            {data: "", sTitle: "Nombre", width: 250, visible: true, render: function (d, t, r) {

                    return r.nombre + ' ' + r.apellido_paterno + ' ' + r.apellido_materno;
                }
            },
            {data: "", sTitle: "Lunes", width: 100, visible: true, render: function (d, t, r) {
                    let he;
                    let esDiaDescanso = r.lunes_descanso === 1;
                    if (esDiaDescanso) {
                        he = '<i class="fa fa-bed" aria-hidden="true"></i>';
                        return he;
                    }
                    if (r.lunes !== null) {
                        he = '<button type="button" class="btn btn-outline-primary" disabled><span class="fa fa-check"></span></button>';
                        return he;
                    } else {

                        he = '<button type="button" class="btn btn-outline-danger" disabled><span class="fa fa-times"></span></button>';
                        return he;
                    }
                }
            },
            {data: "", sTitle: "Martes", width: 100, visible: true, render: function (d, t, r) {
                    let he;
                    let esDiaDescanso = r.martes_descanso === 1;
                    if (esDiaDescanso) {
                        he = '<i class="fa fa-bed" aria-hidden="true"></i>';
                        return he;
                    }
                    if (r.martes !== null) {
                        he = '<button type="button" class="btn btn-outline-primary" disabled><span class="fa fa-check"></span></button>';
                        return he;
                    } else {

                        he = '<button type="button" class="btn btn-outline-danger" disabled><span class="fa fa-times"></span></button>';
                        return he;
                    }
                }
            },
            {data: "", sTitle: "Miércoles", width: 100, visible: true, render: function (d, t, r) {
                    let he;
                    let esDiaDescanso = r.miercoles_descanso === 1;
                    if (esDiaDescanso) {
                        he = '<i class="fa fa-bed" aria-hidden="true"></i>';
                        return he;
                    }
                    if (r.miercoles !== null) {
                        he = '<button type="button" class="btn btn-outline-primary" disabled><span class="fa fa-check"></span></button>';
                        return he;
                    } else {

                        he = '<button type="button" class="btn btn-outline-danger" disabled><span class="fa fa-times"></span></button>';
                        return he;
                    }
                }
            },
            {data: "", sTitle: "Jueves", width: 100, visible: true, render: function (d, t, r) {
                    let he;
                    let esDiaDescanso = r.jueves_descanso === 1;
                    if (esDiaDescanso) {
                        he = '<i class="fa fa-bed" aria-hidden="true"></i>';
                        return he;
                    }
                    if (r.jueves !== null) {
                        he = '<button type="button" class="btn btn-outline-primary" disabled><span class="fa fa-check"></span></button>';
                        return he;
                    } else {

                        he = '<button type="button" class="btn btn-outline-danger" disabled><span class="fa fa-times"></span></button>';
                        return he;
                    }
                }
            },
            {data: "", sTitle: "Viernes", width: 100, visible: true, render: function (d, t, r) {
                    let he;
                    let esDiaDescanso = r.viernes_descanso === 1;
                    if (esDiaDescanso) {
                        he = '<i class="fa fa-bed" aria-hidden="true"></i>';
                        return he;
                    }
                    if (r.viernes !== null) {
                        he = '<button type="button" class="btn btn-outline-primary" disabled><span class="fa fa-check"></span></button>';
                        return he;
                    } else {

                        he = '<button type="button" class="btn btn-outline-danger" disabled><span class="fa fa-times"></span></button>';
                        return he;
                    }
                }
            },
            {data: "", sTitle: "Sábado", width: 100, visible: true, render: function (d, t, r) {
                    let he;
                    let esDiaDescanso = r.sabado_descanso === 1;
                    if (esDiaDescanso) {
                        he = '<i class="fa fa-bed" aria-hidden="true"></i>';
                        return he;
                    }
                    if (r.sabado !== null) {
                        he = '<button type="button" class="btn btn-outline-primary" disabled><span class="fa fa-check"></span></button>';
                        return he;
                    } else {

                        he = '<button type="button" class="btn btn-outline-danger" disabled><span class="fa fa-times"></span></button>';
                        return he;
                    }
                }
            },
            {data: "", sTitle: "Domingo", width: 100, visible: true, render: function (d, t, r) {
                    let he;
                    let esDiaDescanso = r.domingo_descanso === 1;
                    if (esDiaDescanso) {
                        he = '<i class="fa fa-bed" aria-hidden="true"></i>';
                        return he;
                    }
                    if (r.domingo !== null) {
                        he = '<button type="button" class="btn btn-outline-primary" disabled><span class="fa fa-check"></span></button>';
                        return he;
                    } else {

                        he = '<button type="button" class="btn btn-outline-danger" disabled><span class="fa fa-times"></span></button>';
                        return he;
                    }
                }
            },
            {data: "", sTitle: "Administrar Asistencias", width: 200, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" onclick="administrarAsistenciasTrabajador(' + r.id_trabajador + ')"><span class="fa fa-edit"></span>Registro de Asistencias</button>';
                    return he;
                }
            }]
    });
}

function administrarAsistenciasTrabajador(id_trabajador) {
    window.location.href = 'capturaIncidenciasTransportacion?id_trabajador=' + id_trabajador;
}

function adminRa15() {
    window.location.href = 'adminListadoNombTransp';
}


