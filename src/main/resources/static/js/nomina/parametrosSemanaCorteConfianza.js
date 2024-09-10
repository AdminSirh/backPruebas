$(document).ready(function () {
    $('#uploadForm').on('submit', function (event) {
        event.preventDefault();

        var formData = new FormData();
        var file = $('#fileInput')[0].files[0];
        if (!file) {
            toastr["warning"]("Por favor, selecciona un archivo.", "Advertencia", {progressBar: true, closeButton: true});
            return;
        }

        formData.append('file', file);

        $.ajax({
            url: 'periodosPago/cargaSemanasCorteTiempoExtra',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function (response) {
                toastr["success"](response, "Éxito", {progressBar: true, closeButton: true});
                // Limpiar el campo de entrada
                $('#fileInput').val('');
            },
            error: function (xhr) {
                var errorMessage = xhr.responseText || "Error desconocido.";
                toastr["error"](errorMessage, "Error", {progressBar: true, closeButton: true});
            }
        });
    });
});