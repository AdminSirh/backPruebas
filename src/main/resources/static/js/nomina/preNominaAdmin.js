document.addEventListener('DOMContentLoaded', () => {
    mostrarRecordatorioCalculo();
});

function mostrarRecordatorioCalculo() {
    $.ajax({
        type: "GET",
        url: "periodosPago/recordatorioPeriodosSinPagar",
        dataType: 'json',
        contentType: "application/json",
        success: function (data) {
            console.log(data);
            let toastrContent = "";
            let currentNomina = null;
            let mes = "";
            // Recorrer los datos obtenidos
            for (let i = 0; i < data.length; i++) {
                let descNomina = data[i][0];
                mes = data[i][1];
                let fechaInicial = new Date(data[i][2]);
                let fechaFinal = new Date(data[i][3]);
                let fechaCorte = new Date(data[i][4]);
                let periodo = data[i][5];
                // Ajustar todas las fechas por la diferencia de zona horaria
                fechaInicial.setHours(fechaInicial.getHours() + fechaInicial.getTimezoneOffset() / 60);
                fechaFinal.setHours(fechaFinal.getHours() + fechaFinal.getTimezoneOffset() / 60);
                fechaCorte.setHours(fechaCorte.getHours() + fechaCorte.getTimezoneOffset() / 60);
                // Verificar si cambia la nómina actual
                if (currentNomina !== descNomina) {
                    // Agregar salto de línea si no es el primer registro
                    if (toastrContent !== "") {
                        toastrContent += "<br>";
                    }
                    toastrContent += "<strong>Nómina: " + descNomina + "</strong><br>";
                    currentNomina = descNomina;
                }
                toastrContent +=
                        "Fecha Inicial: " + fechaInicial.toLocaleDateString() + "<br>" +
                        "Fecha Final: " + fechaFinal.toLocaleDateString() + "<br>" +
                        "Fecha de Corte: " + fechaCorte.toLocaleDateString() + "<br>" +
                        "Periodo: " + periodo + "<br>";
            }
            toastr["info"](toastrContent, "Periodos sin pago del mes de " + mes, {progressBar: true, closeButton: true, timeOut: 20000});
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos del recordatorio: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
