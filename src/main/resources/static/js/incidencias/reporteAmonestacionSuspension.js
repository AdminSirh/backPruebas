document.addEventListener('DOMContentLoaded', () => {
    $('#parametrosModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
        $('#expediente').val("");
        $('#asunto').val("");
        $('#falta').val("");
        $('#motivo').val("");
    }); 
});

function abrirModalParametros(){
    $('#parametrosModal').modal('show');
} 

function verReporte(){
    var expediente = $('#expediente').val();
    var asunto = $('#asunto').val();
    var falta = $('#falta').val();
    var motivo = $('#motivo').val();
    $.ajax({
            type: "GET",
            url: "report/registraSuspensionAmonestacion?expediente=" + expediente + "&motivo=" + motivo + "&falta=" + falta + "&asunto=" + asunto + "&tipo=PDF",
            xhrFields: {
                responseType: 'blob'
            },
            success: function (data, status, xhr) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    $("#parametrosModal").modal("hide");
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
}

function descargarReporte(){
    var expediente = $('#expediente').val();
    var asunto = $('#asunto').val();
    var falta = $('#falta').val();
    var motivo = $('#motivo').val();
    $.ajax({
        type: "GET",
        url: "report/registraSuspensionAmonestacion?expediente=" + expediente + "&motivo=" + motivo + "&falta=" + falta + "&asunto=" + asunto + "&tipo=PDF",
        
        xhrFields: {
            responseType: 'blob'
        },
        success: function(data, textStatus, jqXHR) {
            $("#parametrosModal").modal("hide");
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontró información...", "Aviso", {
                    progressBar: true,
                    closeButton: true
                });
            } else {
                var contentType = jqXHR.getResponseHeader("Content-Type");

                // Create a temporary <a> element to initiate the download
                var link = document.createElement('a');
                link.href = window.URL.createObjectURL(data);
                link.download = 'formatosSuspensionAmonestacion_' + expediente + '_' + asunto + '.pdf';
                link.click();
            }
        },
        error: function(e) {
            toastr["warning"]("Error al recuperar información de documentos", {
                progressBar: true,
                closeButton: true
            });
        }
    });
}