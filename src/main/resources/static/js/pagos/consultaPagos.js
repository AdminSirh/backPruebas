document.addEventListener('DOMContentLoaded', () => {
    listarNominas();
    document.getElementById("btnDeposito").disabled = true;
    document.getElementById("btnDepositoPaVales").disabled = true;
    document.getElementById("btnDepositoPension").disabled = true;
    document.getElementById("btnDepositoVales").disabled = true;
});

// MOSTRAR LA TABLA EN FUNCIÓN DE LOS PERIODOS Y LA NOMINA QUE SE SELECCIONE
function validaTabla() {
    var nomina = $("#nomina").val();
    var periodo = $("#periodo").val();
    // Manejar el evento de cambio del select para obtener el valor seleccionado
    var selectElement = document.getElementById("periodo");
    if (selectElement.selectedIndex > 0) { // Asegurarse de que hay una opción seleccionada
        var selectedOptionText = selectElement.options[selectElement.selectedIndex].text;
        var partes = selectedOptionText.split(" : ")[1].split("-");
        var fecha_inicial = partes[0].trim();
        var fecha_final = partes[1].trim();

        $("#fecha_inicial").val(fecha_inicial);
        $("#fecha_final").val(fecha_final);
    } else {
        $("#fecha_inicial").val("");
        $("#fecha_final").val("");
    }
    
    var url = 
        nomina === "1" ? "pagos/listarPagosVarios/" + periodo :
        nomina === "2" ? "pagos/listarPagosTransportacion/" + periodo :
        nomina === "3" ? "pagos/listarPagosConfianza/" + periodo :
        nomina === "4" ? "pagos/listarPagosJubilados/" + periodo :
        nomina === "5" ? "pagos/listarPagosEstructura/" + periodo :
        "";
    
    $.ajax({
            type: "GET",
            url: url,
            dataType: 'json',
        success: function (data) {
            if (data !== undefined) {
                muestraTabla(url);
                document.getElementById("btnDeposito").disabled = false;
                document.getElementById("btnDepositoPaVales").disabled = false;
                document.getElementById("btnDepositoPension").disabled = false;
                document.getElementById("btnDepositoVales").disabled = false;
                buscarNomina();
            }else{
                toastr["warning"]("No hay datos para este periodo");
                $('#tabla_Pagos').DataTable().clear().draw();
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Nómina: " + e, " error", {progressBar: true, closeButton: true});
        }
    });

}
// MUESTRA LA TABLA ASEGURANDOSE QUE NO HAYA DATOS INDEFINIDOS DEVUELTOS DE LA CONSULTA Y EVITAR ERRORES
function muestraTabla(url){
    // Verificar si la tabla ya tiene una instancia DataTable
    if ($.fn.DataTable.isDataTable('#tabla_Pagos')) {
        // Destruir la instancia DataTable existente
        $('#tabla_Pagos').DataTable().destroy();
    }
    
    // Inicializar la tabla DataTable
    tabla_Pagos = $('#tabla_Pagos').dataTable({
        "ajax": {
            url: url,
            method: 'GET',
            dataSrc: ''
        },
        responsive: true,
        bProcessing: true,
        select: true,
        "language": {
            'processing': 'Procesando espera...',
            "sProcessing": "Procesando...",
            "sLengthMenu": "Mostrar _MENU_ registros",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": " ",
            "sInfo": " _START_ al _END_ Total: _TOTAL_",
            "sInfoEmpty": " ",
            "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
            "sInfoPostFix": "",
            "search": "Buscar",
            "paginate": {
                "previous": 'Anterior',
                "next": 'Siguiente'
            },
            "paging": false,
            "bPaging": false,
            "scrollY": "300px",
            "sUrl": "",
            "sInfoThousands": ",",
            "sLoadingRecords": "Cargando...",
            "oAria": {
                "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
            }
        },
        columns: [
            { title: "Expediente" },
            { title: "Nombre" },
            { title: "Apellido Paterno" },
            { title: "Apellido Materno" },
            { title: "Sueldo" },
            { title: "Puesto" },
            { data: "", sTitle: "Detalle", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                return '<button type="button" class="btn btn-outline-info ver-detalle-btn" data-datos=\'' + JSON.stringify(r) + '\'><span class="fa fa-search"></span>Ver Detalle</button>';
                }
            }
        ]
    });
    // Agregar evento de clic a los botones Ver Detalle
    $('#tabla_Pagos').on('click', '.ver-detalle-btn', function() {
        var datos = $(this).data('datos');
        verDetalle(datos);
    });
}

//LISTA LOS TIPOS DE NOMINAS 
function listarNominas(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
    success: function (data) {
            if ($.isEmptyObject(data)) {
            toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#nomina").empty().append("<option value=''>* Nomina </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_tipo_nomina === id) ? vselected = "selected" : vselected = " ";
                        $("#nomina").append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                    }
                }
                
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Nómina: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//Lista los periodos de Pago aunque ya hayan sido pagados
function listarPeriodos() {
    var nomina = $("#nomina").val();
    $.ajax({
        type: "GET",
        url: "periodosPago/listarAllPeriodos/" + nomina,
        dataType: 'json',
    success: function (data) {
            if ($.isEmptyObject(data)) {
            toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#periodo").empty().append("<option value=''>* Periodos </option> ");
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        var fecha_inicial_formateada = formatearFecha(data[x].fecha_inicial);
                        var fecha_final_formateada = formatearFecha(data[x].fecha_final);
                        $("#periodo").append('<option value="' + data[x].periodo + '"' + '>' + data[x].periodo + " : " + fecha_inicial_formateada + "-" + fecha_final_formateada + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los Periodos: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

function formatearFecha(fechaOriginal) {
    // Crear un objeto Date a partir de la cadena de fecha original
    var fecha = new Date(fechaOriginal);

    // Obtener el día, mes y año
    var dia = fecha.getDate();
    var mes = fecha.getMonth() + 1; // Los meses comienzan desde 0, por lo que sumamos 1
    var año = fecha.getFullYear();

    // Agregar un cero inicial si el día o el mes es menor que 10
    dia = (dia < 10) ? '0' + dia : dia;
    mes = (mes < 10) ? '0' + mes : mes;

    // Formatear la fecha
    var fechaFormateada = dia + "/" + mes + "/" + año;

    return fechaFormateada;
}

//BUSCAR NOMINA POR ID DE LA NOMINA
function buscarNomina() {
    var id_nomina = $("#nomina").val();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarNomina/" + id_nomina,
        dataType: 'json',
    success: function (data) {
        $("#desc_nomina").val(data.data.desc_nomina);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Nómina: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

// GENERA EL REPORTE DE DIFERENTES BANCOS PARA QUE SE PUEDA VISUALIZAR
function reporteDiferenteBanco(){
    var periodo = $("#periodo").val();
    var nomina = $("#desc_nomina").val();
    var nomina_id = $("#nomina").val();
    var tabla = "pagos_" + nomina_id;
    var fecha_inicial = $("#fecha_inicial").val();
    var fecha_final = $("#fecha_final").val();
    $.ajax({
        type: "GET",
        url: "report/listadoDepositosPagoDiferentesBancos?periodo=" + periodo + "&nomina=" + nomina + "&tabla=" + tabla + "&fecha_inicial=" + fecha_inicial + "&fecha_final=" + fecha_final + "&tipo=PDF",
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, status, xhr) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#reportesModal").modal("toggle");
                var contentType = xhr.getResponseHeader("Content-Type");
                var filename = xhr.getResponseHeader("Content-disposition");
                filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                var url = window.URL.createObjectURL(data);
                var frame = $('#reportes_frame');
                var url = url;
                frame.attr('src', url).show();
                descargaDiferenteBanco(periodo,nomina,tabla,fecha_inicial,fecha_final);
            }
        },
        error: function (e) {
            toastr["warning"]("Asegurese que todos los parametros estén llenos", {
                progressBar: true,
                closeButton: true
            });
        }
    });
}

// GENERA EL REPORTE DE DIFERENTES BANCOS PENSIÓN ALIMENTICIA VALES PARA QUE SE PUEDA VISUALIZAR
function reporteDiferenteBancoPAVales(){
    var periodo = $("#periodo").val();
    var nomina = $("#desc_nomina").val();
    var nomina_id = $("#nomina").val();
    var fecha_inicial = $("#fecha_inicial").val();
    var fecha_final = $("#fecha_final").val();
    $.ajax({
        type: "GET",
        url: "report/listadoDepositosPagoDiferentesBancosPaVales?periodo=" + periodo + "&nomina=" + nomina + "&tabla=" + nomina_id + "&fecha_inicial=" + fecha_inicial + "&fecha_final=" + fecha_final + "&tipo=PDF",
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, status, xhr) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#reportesModal").modal("toggle");
                var contentType = xhr.getResponseHeader("Content-Type");
                var filename = xhr.getResponseHeader("Content-disposition");
                filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                var url = window.URL.createObjectURL(data);
                var frame = $('#reportes_frame');
                var url = url;
                frame.attr('src', url).show();
                descargaDiferenteBancoPAVales(periodo,nomina,nomina_id,fecha_inicial,fecha_final);
            }
        },
        error: function (e) {
            toastr["warning"]("Asegurese que todos los parametros estén llenos", {
                progressBar: true,
                closeButton: true
            });
        }
    });
}

// GENERA EL REPORTE DE DIFERENTES BANCOS PENSIÓN ALIMENTICIA PARA QUE SE PUEDA VISUALIZAR
function reporteDiferenteBancoPension(){
    var periodo = $("#periodo").val();
    var nomina = $("#desc_nomina").val();
    var nomina_id = $("#nomina").val();
    var fecha_inicial = $("#fecha_inicial").val();
    var fecha_final = $("#fecha_final").val();
    $.ajax({
        type: "GET",
        url: "report/listadoDepositosPagoDiferentesBancosPension?periodo=" + periodo + "&nomina=" + nomina + "&tabla=" + nomina_id + "&fecha_inicial=" + fecha_inicial + "&fecha_final=" + fecha_final + "&tipo=PDF",
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, status, xhr) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#reportesModal").modal("toggle");
                var contentType = xhr.getResponseHeader("Content-Type");
                var filename = xhr.getResponseHeader("Content-disposition");
                filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                var url = window.URL.createObjectURL(data);
                var frame = $('#reportes_frame');
                var url = url;
                frame.attr('src', url).show();
                descargaDiferenteBancoPension(periodo,nomina,nomina_id,fecha_inicial,fecha_final);
            }
        },
        error: function (e) {
            toastr["warning"]("Asegurese que todos los parametros estén llenos", {
                progressBar: true,
                closeButton: true
            });
        }
    });
}

// GENERA EL REPORTE DE DIFERENTES BANCOS VALES PARA QUE SE PUEDA VISUALIZAR
function reporteDiferenteBancoVales(){
    var periodo = $("#periodo").val();
    var nomina = $("#desc_nomina").val();
    var nomina_id = $("#nomina").val();
    var fecha_inicial = $("#fecha_inicial").val();
    var fecha_final = $("#fecha_final").val();
    $.ajax({
        type: "GET",
        url: "report/listadoDepositosPagoDiferentesBancosVales?periodo=" + periodo + "&nomina=" + nomina + "&tabla=" + nomina_id + "&fecha_inicial=" + fecha_inicial + "&fecha_final=" + fecha_final + "&tipo=PDF",
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, status, xhr) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#reportesModal").modal("toggle");
                var contentType = xhr.getResponseHeader("Content-Type");
                var filename = xhr.getResponseHeader("Content-disposition");
                filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                var url = window.URL.createObjectURL(data);
                var frame = $('#reportes_frame');
                var url = url;
                frame.attr('src', url).show();
                descargaDiferenteBancoVales(periodo,nomina,nomina_id,fecha_inicial,fecha_final);
            }
        },
        error: function (e) {
            toastr["warning"]("Asegurese que todos los parametros estén llenos", {
                progressBar: true,
                closeButton: true
            });
        }
    });
}

// DESCARGA EL REPORTE EN EXCEL DE PAGOS DE DIFERENTES BANCOS Y VALES
function descargaDiferenteBancoVales(periodo,nomina,nomina_id,fecha_inicial,fecha_final){
    $.ajax({
        type: "GET",
        url: "report/listadoDepositosPagoDiferentesBancosValesE?periodo=" + periodo + "&nomina=" + nomina + "&tabla=" + nomina_id + "&fecha_inicial=" + fecha_inicial + "&fecha_final=" + fecha_final + "&tipo=EXCEL", // Cambia "PDF" a "EXCEL"

        xhrFields: {
            responseType: 'blob'
        },
        success: function(data, textStatus, jqXHR) {
            $("#depositoDiferentesBancosModal").modal("hide");
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
                link.download = 'diferenteBancosVales_' + periodo + '_' + nomina + '.xlsx'; // Cambia la extensión del archivo a ".xlsx"
                link.click();
            }
        },
        error: function(e) {
            toastr["warning"]("Error al recuperar información de reportes", {
                progressBar: true,
                closeButton: true
            });
        }
    }); 
}

// DESCARGA EL REPORTE EN EXCEL DE PAGOS DE DIFERENTES BANCOS Y PENSIÓN ALIMENTICIA VALES
function descargaDiferenteBancoPAVales(periodo,nomina,nomina_id,fecha_inicial,fecha_final){
    $.ajax({
        type: "GET",
        url: "report/listadoDepositosPagoDiferentesBancosPaValesE?periodo=" + periodo + "&nomina=" + nomina + "&tabla=" + nomina_id + "&fecha_inicial=" + fecha_inicial + "&fecha_final=" + fecha_final + "&tipo=EXCEL", // Cambia "PDF" a "EXCEL"

        xhrFields: {
            responseType: 'blob'
        },
        success: function(data, textStatus, jqXHR) {
            $("#depositoDiferentesBancosModal").modal("hide");
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
                link.download = 'diferenteBancosPAVales_' + periodo + '_' + nomina + '.xlsx'; // Cambia la extensión del archivo a ".xlsx"
                link.click();
            }
        },
        error: function(e) {
            toastr["warning"]("Error al recuperar información de reportes", {
                progressBar: true,
                closeButton: true
            });
        }
    }); 
}
// DESCARGA EL REPORTE EN EXCEL DE PAGOS DE DIFERENTES BANCOS Y PENSIÓN ALIMENTICIA 
function descargaDiferenteBancoPension(periodo,nomina,nomina_id,fecha_inicial,fecha_final){
    $.ajax({
        type: "GET",
        url: "report/listadoDepositosPagoDiferentesBancosPensionE?periodo=" + periodo + "&nomina=" + nomina + "&tabla=" + nomina_id + "&fecha_inicial=" + fecha_inicial + "&fecha_final=" + fecha_final + "&tipo=EXCEL", // Cambia "PDF" a "EXCEL"

        xhrFields: {
            responseType: 'blob'
        },
        success: function(data, textStatus, jqXHR) {
            $("#depositoDiferentesBancosModal").modal("hide");
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
                link.download = 'diferenteBancosPensiones_' + periodo + '_' + nomina + '.xlsx'; // Cambia la extensión del archivo a ".xlsx"
                link.click();
            }
        },
        error: function(e) {
            toastr["warning"]("Error al recuperar información de reportes", {
                progressBar: true,
                closeButton: true
            });
        }
    }); 
}
// DESCARGA EL REPORTE EN EXCEL DE PAGOS DE DIFERENTES BANCOS
function descargaDiferenteBanco(periodo,nomina,tabla,fecha_inicial,fecha_final){
    $.ajax({
        type: "GET",
        url: "report/listadoDepositosPagoDiferentesBancosE?periodo=" + periodo + "&nomina=" + nomina + "&tabla=" + tabla + "&fecha_inicial=" + fecha_inicial + "&fecha_final=" + fecha_final + "&tipo=EXCEL", // Cambia "PDF" a "EXCEL"

        xhrFields: {
            responseType: 'blob'
        },
        success: function(data, textStatus, jqXHR) {
            $("#depositoDiferentesBancosModal").modal("hide");
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
                link.download = 'diferenteBancos_' + periodo + '_' + nomina + '.xlsx'; // Cambia la extensión del archivo a ".xlsx"
                link.click();
            }
        },
        error: function(e) {
            toastr["warning"]("Error al recuperar información de reportes", {
                progressBar: true,
                closeButton: true
            });
        }
    }); 
}

// NAVEGACIÓN AL DETALLE DE PAGOS INCIDENCIAS
function verDetalle(datos){
    // Hacer lo que necesites con el último parámetro
    var id_pago = datos[datos.length - 2];
    var id_trabajador = datos[datos.length - 1];
    window.location.href = 'detallePagosIncidencias?id_pago=' + id_pago + "&id_trabajador=" + id_trabajador;
}
