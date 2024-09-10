let trabajador_id;
document.addEventListener('DOMContentLoaded', () => {
    // Obtener parámetros de la URL y realizar operaciones
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    trabajador_id = urlParams.get('id_trabajador');
    buscarTrabajador(trabajador_id);
    listadoIncidenciasTrabajador(trabajador_id);
});

//Busca los datos del trabajador para posteriores operaciones con los mismos
function buscarTrabajador (trabajador_id) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + trabajador_id,
        dataType: 'json',
        success: function (data) {
            $('#campo_expediente').val(data.data.cat_expediente.numero_expediente);
            $('#campo_nombre').val(data.data.persona.nombre + " " + data.data.persona.apellido_paterno + " " + data.data.persona.apellido_materno);
            $('#comisionado_si_no').val(data.data.comisionado_si_no === 1 ? 'Si' : data.data.comisionado_si_no === 2 ? 'No' : data.data.comisionado_si_no ? data.data.comisionado_si_no : 'N/D');
        },
        error: function (e) {
            alert(e);
        }
    });
}

function home () {
    window.location.href = 'registroInasistencias';
}

function listadoIncidenciasTrabajador(trabajador_id) {
    $.ajax({
        type: "GET",
        url: "incidencias/buscarIncidencias/" + trabajador_id,
        dataType: 'json',
        success: function (data) {
            //populateTable(data);
        },
        error: function () {
            toastr["error"]("Error al encontrar periodo de pago", " Error", {progressBar: true, closeButton: true});
        }
    });
}

function administrarIncidencias () {
    let idIncidencia = $('#event-index').val();
    window.location.href = 'trabajadorIncidencias?id_trabajador=' + trabajador_id + '&idIncidencia=' + idIncidencia;
}

