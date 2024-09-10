document.addEventListener('DOMContentLoaded', () => {
    cmbTipoNomina();
    const tipoNominaSelect = $('#tipo_nomina');
    const id_inicial = '1';

    tipoNominaSelect.val(id_inicial);
    tablaTrabajadores(id_inicial);

    tipoNominaSelect.on('change', function () {
        tablaTrabajadores($(this).val());
    });
});

function tablaTrabajadores(id_nomina) {
    const url = id_nomina === '0'
            ? "trabajador/listarTrabajadoresNominaOpcional"
            : `trabajador/listarTrabajadoresNominaOpcional?tipo_nomina_id=${id_nomina}`;
    $('#tabla_Trabajadores').DataTable({
        destroy: true, // Destruye cualquier instancia previa de la tabla
        ajax: {
            url: url,
            method: 'GET',
            dataSrc: ''
        },
        responsive: true,
        processing: true,
        select: true,
        language: {
            processing: 'Procesando espera...',
            sLengthMenu: "Mostrar _MENU_ registros",
            sZeroRecords: "No se encontraron resultados",
            sEmptyTable: " ",
            sInfo: " _START_ al _END_ Total: _TOTAL_",
            sInfoEmpty: " ",
            sInfoFiltered: "(filtrado de un total de _MAX_ registros)",
            search: "Buscar",
            paginate: {
                previous: 'Anterior',
                next: 'Siguiente'
            },
            scrollY: "300px",
            sLoadingRecords: "Cargando...",
            oAria: {
                sSortAscending: ": Activar para ordenar la columna de manera ascendente",
                sSortDescending: ": Activar para ordenar la columna de manera descendente"
            }
        },
        columns: [
            {data: "cat_expediente.numero_expediente"},
            {data: "persona.nombre"},
            {data: "persona.apellido_paterno"},
            {data: "persona.apellido_materno"},
            {data: "persona.fecha_nacimiento"},
            {data: "persona.cat_genero.desc_genero"},
            {data: "persona.curp"},
            {data: "fecha_antiguedad"},
            {
                data: null,
                title: "Información complementaria",
                width: 100,
                render: function (d, t, r) {
                    return `<button type="button" class="btn btn-outline-primary" onclick="EditarTrabajador(${r.id_trabajador})"><span class="fa fa-edit"></span>Editar</button>`;
                }
            },
            {
                data: null,
                title: "Información personal",
                width: 100,
                render: function (d, t, r) {
                    return `<button type="button" class="btn btn-outline-primary" onclick="editarPersona(${r.id_trabajador})"><span class="fa fa-edit"></span>Editar</button>`;
                }
            },
            {
                data: null,
                title: "Información personal",
                width: 100,
                render: function (d, t, r) {
                    return `<button type="button" class="btn btn-outline-primary" onclick="verPersona(${r.id_trabajador})"><span class="fa fa-edit"></span>Ver</button>`;
                }
            }
        ]
    });
}

function EditarTrabajador(id_trabajador) {
    window.location.href = `trabajadorDatosGenerales?id_trabajador=${id_trabajador}`;
}

function verPersona(id_trabajador) {
    obtenerDatosTrabajador(id_trabajador, 'personaDatosGeneralesVer');
}

function editarPersona(id_trabajador) {
    obtenerDatosTrabajador(id_trabajador, 'personaDatosGenerales');
}

function obtenerDatosTrabajador(id_trabajador, urlBase) {
    $.ajax({
        type: "GET",
        url: `trabajador/buscarTrabajador/${id_trabajador}`,
        dataType: 'json',
        success: function (data) {
            window.location.href = `${urlBase}?id_persona=${data.data.persona.id_persona}`;
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

function cmbTipoNomina() {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                data.forEach(tipoNomina => {
                    if (tipoNomina.id_tipo_nomina !== 1) {
                        $('#tipo_nomina').append(`<option value="${tipoNomina.id_tipo_nomina}">${tipoNomina.desc_nomina}</option>`);
                    }
                });
                $('#tipo_nomina').append('<option value="0">Todos los trabajadores</option>').val("1");
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Orden: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
