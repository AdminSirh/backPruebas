function visualizarReporteJubilados(){
    url  = 'report/jubilados/personalJubiladoActivo?tipo=PDF';
    $.ajax({
        type: 'GET',
        url: url,
        xhrFields: {
          responseType: 'blob'  
        },
        success: function (data, textStatus, xhr) {
            if($.isEmptyObject(data)){
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }  else {
                    $("#reportesModal").modal("show");
                    var contentType = xhr.getResponseHeader("Content-Type");
                    var filename = xhr.getResponseHeader("Content-disposition");
                    filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                    var url = window.URL.createObjectURL(data);
                    var frame = $('#reportes_frame');
                    var url = url;
                    frame.attr('src', url).show();
                }
        },
        error: function (e){
            toastr["warning"]("Error al recuperar informacion de documentos cargados: General 1", {progressBar: true, closeButton: true});
        }
    });
}

function descargarReporteJubilados(){
        $.ajax({
        type: 'GET',
        contentType: 'application/pdf',
        url: 'report/jubilados/personalJubiladoActivo?tipo=PDF',
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
            //Nombre de la descarga
            link.download = "personalJubiladosActivos.pdf";
            link.click();
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
        }
    });
}


