$(document).ready(function () {
    mostrarRecordatorioPeriodoPago().then(function () {
        mostrarEstadoCirrePlantillaMensual();
    });
});

function mostrarRecordatorioPeriodoPago() {
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: "GET",
            url: "periodosPago/recordatorioPeriodosSinPagar",
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                let recordatoriosDiv = $('#recordatorios');
                recordatoriosDiv.empty(); // Limpiar cualquier contenido previo
                let col1 = $('<div>').css('float', 'left').css('width', '55%');
                let col2 = $('<div>').css('float', 'right').css('width', '45%');
                recordatoriosDiv.append(col1).append(col2);
                let currentNomina = null;
                for (let i = 0; i < data.length; i++) {
                    let descNomina = data[i][0];
                    let mes = data[i][1];
                    let fechaInicial = new Date(data[i][2]);
                    let fechaFinal = new Date(data[i][3]);
                    let fechaCorte = new Date(data[i][4]);
                    let periodo = data[i][5];
                    let strong, ul;
                    if (currentNomina !== descNomina) {
                        if (i > 0) {
                            // Si no es el primer registro, agregar un salto de línea
                            // recordatoriosDiv.append($('<br>'));
                        }
                        currentNomina = descNomina;
                        strong = $('<strong>').text("Nómina: " + descNomina);
                        ul = $('<ul>');
                        //Alterna entre columnas dependiendo los datos recibidos
                        if (i % 2 === 0) {
                            col1.append(strong).append(ul);
                        } else {
                            col2.append(strong).append(ul);
                        }
                    }
                    let li = $('<li>').html(
                            "<strong>Periodo: </strong>" + periodo + "<br>" +
                            "Fecha Inicial: " + fechaInicial.toLocaleDateString() + "<br>" +
                            "Fecha Final: " + fechaFinal.toLocaleDateString() + "<br>" +
                            "Fecha de Corte: " + fechaCorte.toLocaleDateString() + "<br>"
                            );
                    if (i % 2 === 0) {
                        col1.find('ul').last().append(li);
                    } else {
                        col2.find('ul').last().append(li);
                    }
                }
                resolve(); // Resuelve la promesa cuando se completa la función
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar los datos del recordatorio: " + e, " error", {progressBar: true, closeButton: true});
                reject(); // Rechaza la promesa en caso de error
            }
        });
    });
}

function mostrarEstadoCirrePlantillaMensual() {
    let fechaActual = new Date();
    let anio = fechaActual.getFullYear();
    let mes = fechaActual.getMonth() + 1;
    $.ajax({
        type: "GET",
        url: "cierrePlantilla/ejecutoCierreMensual/" + anio + "/" + mes,
        dataType: 'json',
        contentType: "application/json",
        success: function (data) {
            let recordatoriosDiv = $('#recordatorios');
            let estadoLista = $('<ul>');
            let icono = data ? "check" : "times";
            let estadoTexto = data ? " Hecho" : " Pendiente";
            let estadoItem = $('<li>').html("Estado: <i class='fas fa-" + icono + "'></i>" + estadoTexto);
            estadoLista.append(estadoItem);
            recordatoriosDiv.append($('<strong>').text("Cierre de Plantilla Mensual"));
            recordatoriosDiv.append(estadoLista);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos del estado de cierre mensual: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

