document.addEventListener('DOMContentLoaded', () => {
});

//Declaración de variables globales para conocer el tipo de constancia seleccionado y el número de expediente
let tipoConstancia;
let numeroExpediente;
//Declaración de checkboxes para su posterior manejo
const checkboxes = $("[name='opcion']");


//Selección única de checkboxes
const cambioCheckboxes = (selectedId) => {
    checkboxes.each(function () {
        if (this.id !== selectedId) {
            $(this).prop('checked', false);
        } else if ($(this).prop('checked')) {
            tipoConstancia = $(this).val();
        }
    });
};

/* Evento change para los checkboxes
   Detecta si se deja de seleccionar un checkbox para vaciar su valor */
checkboxes.change(function () {
    if (!$(this).prop('checked')) {
        tipoConstancia = '';
    }
});

/*Se genera la descarga al dar click en el botón teniendo en cuenta la variable global 
 con el tipo de constancia seleccionada */
const generarDescarga = () => {
    const numeroExpediente = $('#numero_plaza').val();

    if (numeroExpediente !== '') {
        let url, nombreDescarga;

        switch (tipoConstancia) {
            case "1":
                url = `report/constancias/obtenerConstanciaMensualIntegrado?expediente=${numeroExpediente}&tipo=PDF`;
                nombreDescarga = `ConstanciaMensualIntegrado${numeroExpediente}.pdf`;
                break;
            case "2":
                url = `report/constancias/obtenerConstanciaSinSalario?expediente=${numeroExpediente}&tipo=PDF`;
                nombreDescarga = `ConstanciaSinSalario${numeroExpediente}.pdf`;
                break;
            case "3":
                url = `report/constancias/obtenerConstanciaFallecimiento?expediente=${numeroExpediente}&tipo=PDF`;
                nombreDescarga = `ConstanciaDefuncion${numeroExpediente}.pdf`;
                break;
            default:
                toastr["warning"]("Error, selecciona una opción de impresión o ingresa el número de expediente ", {progressBar: true, closeButton: true});
                return;
        }
        llamadaAjax(url, nombreDescarga);
    } else {
        toastr["warning"]("Error, selecciona una opción de impresión o ingresa el número de expediente ", {progressBar: true, closeButton: true});
    }
};

const llamadaAjax = (url, nombreDescarga) => {
    $.ajax({
        type: "GET",
        contentType: 'application/pdf',
        url: url,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (blob) {
            if ($.isEmptyObject(blob)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            toastr["success"]("Reporte Generado con éxito", "Aviso", {progressBar: true, closeButton: true});
            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = nombreDescarga;
            link.click();
        }, error: function (e) {
            toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
        }
    });
};