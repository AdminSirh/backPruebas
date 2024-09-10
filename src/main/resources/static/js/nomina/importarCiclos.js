document.addEventListener('DOMContentLoaded', () => {
//    validacion();
});
function importarCiclos() {
    event.preventDefault();
    var archivoInput = document.getElementById('archivo_input');
    var archivo = archivoInput.files[0];
    var formData = new FormData();
    formData.append('archivo', archivo);
    // Envía los datos al backend
    $.ajax({
        url: 'periodosPago/importarCiclos',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function (response) {
            // Maneja la respuesta exitosa
            toastr["success"]("Se importaron correctamente los ciclos", "Éxito", {progressBar: true, closeButton: true});
            $("#archivo_input").val("");
        },
        error: function (xhr, status, error) {
            var errorMessage;
            if (xhr.responseJSON && xhr.responseJSON.message) {
                errorMessage = xhr.responseJSON.message;
            } else {
                errorMessage = "Error del servidor: " + error;
            }
            toastr["error"]("No se cargaron los ciclos <br>" + errorMessage, "Error", {progressBar: true, closeButton: true});
        }
    });
}

//function validacion(){
//    var file = $("#archivo_input").val();
//    if (file === "") {
//        document.getElementById("boton_importar").disabled = true;
//    }else{
//        document.getElementById("boton_importar").disabled = false;
//    }
//}

function ciclosOperacion(){
    window.location.href = 'ciclosOperacion';
}

