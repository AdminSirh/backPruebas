document.addEventListener('DOMContentLoaded', () => {
   orden(); 
});

/*==========================================================
 FUNCIONES COMBO ORDEN
 ==========================================================*/
function orden(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarDatosOrdenReporteMonitor",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#orden').empty().append("<option value=''>* Orden </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_reporte_monitor === id) ? vselected = "selected" : vselected = " ";
                        $('#orden').append('<option value="' + data[x].id_reporte_monitor + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar el área: " + e, " error", {progressBar: true, closeButton: true});
        }
 
    });
}

/*==========================================================
 FUNCIONES VISUALIZADORAS DE REPORTES 
 ==========================================================*/
const llamadaAjaxOrden = (url, modalOrden) => {
    
    $.ajax({
        type: "GET",
        url: url,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, status, xhr) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                modalOrden.modal('hide');
                $("#reportesModal").modal("toggle");
                var contentType = xhr.getResponseHeader("Content-Type");
                var filename = xhr.getResponseHeader("Content-disposition");
                filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                var url = window.URL.createObjectURL(data);
                var frame = $('#reportesFem_frame');
                var url = url;
                frame.attr('src', url).show();
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar informacion de documentos cargados", {progressBar: true, closeButton: true});
        }
    });
    };   

/*==========================================================
    VER REPORTE POR ORDEN 
 ==========================================================*/
function vizualizarReporteFemeninoArea () {
    var combo_orden = $('#orden').val();
    var orden = "";
    

    // Definición de orden en que se generará el reporte (Codigo SQL que pasa como parámetro para definir el orden en el que se generará el reporte)
    if (combo_orden === "1") {
        orden = "nombre_completo ASC";
    } else if (combo_orden === "2") {
        orden = "desc_area ASC";
    } else if (combo_orden === "3") {
        orden = "catalogo_expedientes.numero_expediente ASC";
    }
    
    const url = "report/reportePersonalFemeninoPorArea?Orden=" + orden + "&tipo=PDF";
    const modalOrden = $('#reportesModal');
    if (combo_orden === "" ) {
        toastr["warning"]("Selecciona una opción"); 
    }
    else { 
        $('#reportesModal').modal('show');
        llamadaAjaxOrden(url, modalOrden);
            }
    
};

/*==========================================================
    DESCARGAR REPORTE POR ORDEN 
 ==========================================================*/
function descargarReporteFemeninoArea(){
    var combo_orden = $('#orden').val();
    var orden = "";
    if (combo_orden === "1") {
        orden = "nombre_completo ASC";
    } else if (combo_orden === "2") {
        orden = "desc_area ASC";
    } else if (combo_orden === "3") {
        orden = "catalogo_expedientes.numero_expediente ASC";
    }
    AjaxReporteFemeninoArea(orden); 
};


function AjaxReporteFemeninoArea(orden){
    
    $.ajax({
        type: "GET",
        url: "report/reportePersonalFemeninoPorArea?Orden=" + orden + "&tipo=PDF",
        
        xhrFields: {
            responseType: 'blob'
        },
        success: function(data, textStatus, jqXHR) {
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
                link.download = "reportePersonalFemeninoPorArea" + orden + ".pdf";
                link.click();
            }
        },
        error: function(e) {
            toastr["warning"]("Selecciona una opción para realizar la descarga", {
                progressBar: true,
                closeButton: true
            });
        }
    });
};
