function generarExcelPlantas() {
    $.ajax({
        url: "ra15/exportarPlantasAExcel",
        type: "GET",
        xhrFields: {
            responseType: 'blob' // Indicar que la respuesta es un blob (archivo)
        },
        success: function (data, textStatus, xhr) {
            var filename = "";
            var disposition = xhr.getResponseHeader('Content-Disposition');
            if (disposition && disposition.indexOf('attachment') !== -1) {
                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                var matches = filenameRegex.exec(disposition);
                if (matches != null && matches[1])
                    filename = matches[1].replace(/['"]/g, '');
            }
            var url = window.URL.createObjectURL(data);
            var a = document.createElement('a');
            a.href = url;
            //Se obtiene el nombre que se genera en el servidor
            a.download = filename || 'plantas.xlsx';
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
            toastr["success"]("Se generó correctamente el archivo excel", "Aviso", {progressBar: true, closeButton: true});
        },
        error: function (xhr, status, error) {
            toastr["error"]("Ocurrió un error " + error, "Error", {progressBar: true, closeButton: true});
        }
    });
}
