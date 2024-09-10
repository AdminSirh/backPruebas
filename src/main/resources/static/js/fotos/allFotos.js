document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');
    mostrarFoto(id_trabajador);
});

function ocultarImagen() {
    $('#fotoContainer').hide();
}

function mostrarFoto(trabajador_id) {
    $.ajax({
        type: "GET",
        url: "foto/buscarFotoCargada/" + trabajador_id,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, status, xhr) {
            var contentType = xhr.getResponseHeader("Content-Type");
            if (contentType.startsWith('image/')) {
                var imageUrl = URL.createObjectURL(data);
                var imageElement = document.getElementById('fotoElement');
                imageElement.src = imageUrl;
                $("#fotoContainer").show(); // Mostrar el contenedor de la imagen
            }
        },
        error: function (xhr, status, error) {
            ocultarImagen();
            toastr["warning"]("No hay foto almacenada");
        }
    });
}

function guardaFoto() {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');
    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];

    if (file) {
        const reader = new FileReader();

        reader.onload = function(e) {
            const imageUrl = e.target.result;

            // Crear un elemento de imagen y mostrarlo en la página (opcional)
            const imgElement = document.createElement('img');
            imgElement.src = imageUrl;
            imgElement.style.width = '200px'; // Ajusta el tamaño según sea necesario
            document.body.appendChild(imgElement);

            // Convertir la imagen a Blob
            fetch(imageUrl).then(res => res.blob()).then(blob => {
                const formData = new FormData();
                const fecha_formateada = new Date().toISOString().split('T')[0]; // Formatear la fecha

                formData.append('foto', blob, id_trabajador + "_" + fecha_formateada + '_foto.png'); 

                $.ajax({
                    url: 'foto/guardarFoto/' + id_trabajador,
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    cache: false,
                    success: function (data, textStatus, jqXHR) {
                        toastr['success'](data.data, "Foto Guardada Correctamente");
                        mostrarFoto(id_trabajador);
                    },
                    error: function (jqXHR, textStatus) {
                        toastr['error']("Ocurrió un error al guardar", "Aviso");
                    }
                });
            });
        };

        reader.readAsDataURL(file);
    } else {
        console.log("No se ha seleccionado ningún archivo.");
    }
}