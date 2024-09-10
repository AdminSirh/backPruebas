const verFormatoIncidencia = (id_incidencia) => {
    const url = "formatosIncidencias/autorizacionVacaciones?incidencias_id=" + id_incidencia + "&tipo=PDF";
    llamadaAjaxVizualizacionFormatos(url);
};

const generarFormatoIncidencia = (id_incidencia) => {
    const url = "formatosIncidencias/autorizacionVacaciones?incidencias_id=" + id_incidencia + "&tipo=PDF";
    const nombreDescarga = "AutorizacionIncidencia" + id_incidencia + ".pdf";
    llamadaAjaxDescargaFormatos(url, nombreDescarga);
};

const llamadaAjaxVizualizacionFormatos = (url) => {
    $.ajax({
        type: "GET",
        url: url,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, status, xhr) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#formatosModal").modal("toggle");
                var contentType = xhr.getResponseHeader("Content-Type");
                var filename = xhr.getResponseHeader("Content-disposition");
                filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                var url = window.URL.createObjectURL(data);
                var frame = $('#formatos_frame');
                var url = url;
                frame.attr('src', url).show();
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar informacion de documentos cargados: General 1", {progressBar: true, closeButton: true});
        }
    });
};

const llamadaAjaxDescargaFormatos = (url, nombreDescarga) => {
    $.ajax({
        type: 'GET',
        contentType: 'application/pdf',
        url: url,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, textStatus, jqXHR) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            toastr["success"]("Reporte Generado con éxito", "Aviso", {progressBar: true, closeButton: true});
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(data);
            link.download = nombreDescarga;
            link.click();
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
        }
    });
};
    