let flagAnioIncorrecto = false;
document.addEventListener('DOMContentLoaded', () => {
    inicializarElementos();
});

function inicializarElementos() {
    $('#btn_cargando').hide();
    var startYear = 2019;
    for (i = new Date().getFullYear(); i > startYear; i--)
    {
        $('#yearpicker').append($('<option />').val(i).html(i));
    }
    // Detecta la carga del archivo
    $('#file').on("change", function (event) {
        const file = event.target.files[0];
        let url;
        if (file) {
            if (!file.name.endsWith(".xlsx")) {
                toastr.error("Sólo se permite la carga de archivos xlsx");
                // Se elimina el archivo cargado
                $("#file").val("");
                return;
            }
            const reader = new FileReader();
            // Operación de lectura del archivo se completó de manera exitosa 
            reader.onload = function (e) {
                const data = new Uint8Array(e.target.result);
                const workbook = XLSX.read(data, {type: 'array'});
                // Obtener las hojas del libro de trabajo
                const sheetNames = workbook.SheetNames;
                // Obtener el elemento select del HTML
                const $selectElement = $("#cmbHojasExcel");
                // Limpiar cualquier opción previa del select
                $selectElement.empty();
                // Agregar la opción por defecto
                $selectElement.append('<option value="" selected disabled>*Seleccione la hoja a importar</option>');
                // Agregar cada hoja como una opción en el select
                sheetNames.forEach(function (sheetName) {
                    $selectElement.append($('<option>', {
                        value: sheetName,
                        text: sheetName
                    }));
                });
                // Mostrar el combo box
                $selectElement.removeAttr("hidden");
                //Indicar al usuario los nombres requeridos para las hojas
                toastr["info"](
                        "Para asegurar una carga exitosa, asegúrate de nombrar las hojas de acuerdo a las plantas que deseas cargar.<br> Utiliza los siguientes nombres para las hojas: <br> -Operadores <br> -Tren Ligero <br> -General Patio",
                        "Información",
                        {progressBar: true, closeButton: true, timeOut: 0, escapeHtml: false});
                // Agregar un evento change al combo box para manejar la selección de la hoja
                $selectElement.on('change', function () {
                    const selectedSheetName = $(this).val();
                    const normalizedSheetName = selectedSheetName.toLowerCase().replace(/\s+/g, '');
                    const worksheet = workbook.Sheets[selectedSheetName];
                    //Se verifica la hoja recibida para determinar la url de la validación de año a consultar
                    url = normalizedSheetName === "trenligero" ? "ra15/existsByAnioAndEstatusTmpHistoricoTren/" : "ra15/existsByAnioAndEstatusTmpHistorico/";
                    $('#seleccionAnioCarga').modal('show');
                    //Detecta el guardar del usuario
                    $("#seleccionAnioCarga").submit(function (e) {
                        e.preventDefault();
                        let selectedYear = parseInt($('#yearpicker').val());
                        // Crear FormData y añadir el archivo y el nombre de la hoja
                        let formData = new FormData();
                        formData.append('archivo', file);
                        formData.append('nombreHoja', selectedSheetName);
                        formData.append('anio', selectedYear);
                        //Se valida si el año seleccionado no está cargado y se envía a la función del back
                        validarSiAnioSeleccionadoNoEstaCargado(url, selectedYear, formData);
                    });
                });
            };
            // Manejo de errores de lectura del archivo
            reader.onerror = function (e) {
                toastr.error("Error al leer el archivo" + e, "Error");
            };
            reader.readAsArrayBuffer(file);
        }
    });
}
/*Aquí se valida si el año seleccionado está presente y activo en la tabla tmp_libro_historico_ra15. 
 * Se considera aceptar la carga ya que se puede cargar personal de patio para el mismo añopero que viene en 
 * diferente hoja
 */
function validarSiAnioSeleccionadoNoEstaCargado(url, anio, formData) {
    $.ajax({
        type: "GET",
        url: url + anio,
        dataType: 'json',
        success: (data) => {
            if (data) {
                booleanAnio = data;
                $("#modalAnioSeleccionadoDectado").modal("show");
                $("#modalAnioSeleccionadoDectado").submit(function (event) {
                    event.preventDefault();
                    ajaxEnviarExcel(formData);
                    $("#modalAnioSeleccionadoDectado").modal("hide");
                });
            } else {
                ajaxEnviarExcel(formData);
            }
        },
        error: (e) => {
            console.error(e);
        }
    });
}

function ajaxEnviarExcel(formData) {
    // Envía los datos del excel al backend
    $.ajax({
        url: 'ra15/importarPlantas',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        beforeSend: function () {
            // Mostrar el botón y el spinner cuando comienza la llamada AJAX
            $('#btn_cargando').show();
        },
        success: function (response) {
            // Maneja la respuesta exitosa
            $("#seleccionAnioCarga").modal('hide');
            toastr["success"]("Se importaron correctamente las plantas de la RA15", "Éxito", {progressBar: true, closeButton: true});
        },
        error: function (xhr, status, error) {
            var errorMessage;
            if (xhr.responseJSON && xhr.responseJSON.message) {
                errorMessage = xhr.responseJSON.message;
            } else {
                errorMessage = "Error del servidor: " + error;
            }
            toastr["error"]("No se cargaron las plantas de la RA15. <br>" + errorMessage, "Error", {progressBar: true, closeButton: true});
        },
        complete: function () {
            $('#btn_cargando').hide();
        }
    });

}

// Ocultar el select de las hojas al esconder el modal
$('#seleccionAnioCarga').on('hidden.bs.modal', function () {
    // Limpiar el select de las hojas
    $("#cmbHojasExcel").empty();
    // Ocultar el select de las hojas
    $("#cmbHojasExcel").attr("hidden", true);
    // Limpiar el archivo seleccionado del input file
    $("#file").val("");
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    toastr.clear();
});
