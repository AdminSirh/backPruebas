

$("#formReporteFechaCaptura").submit(function (e) {
    event.preventDefault();
    var fecha_captura = $("#fecha_captura").val();
   
    if ($.trim(fecha_captura) === "") {
        toastr["warning"]("Ingresa la fecha para generar el reporte ","Aviso", {progressBar: true, closeButton: true});
        return false;
    }
   

    $.ajax({
        type: "GET",
        contentType: 'application/pdf',
//        url: "report/persona/descargaReporte/" + fecha_final,
        url: "report/persona/descarga?fecha_captura="+ fecha_captura.toString()+"&tipo=PDF",
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
            link.download = "reporteSIRH_FechaCaptura" + fecha_captura.toString() + ".pdf";
            link.click();
        
        }, error: function (e) {
            toastr["warning"]("Error al recuperar datos : " + e, " Error", {progressBar: true, closeButton: true});
        }
    });
});

