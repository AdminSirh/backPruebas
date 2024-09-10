document.addEventListener('DOMContentLoaded', () => {
    contratoTabulador();
});
//Limpiar el modal de parametros del reporte historico de Fonacot
$('#parametrosModal').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    $('#expediente').val("");
    $('#periodo').val("");
});
//Limpiar el modal de parametros del formato de prestamos
$('#prestamoPersonalModal').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    $('#contratos').val("");
});

$('#depositoDiferentesBancosModal').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    $('#periodo_deposito').val("");
    $('#nomina').val("");
});

$('#depositoBanorteModal').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    $('#periodo_banorte').val("");
    $('#nomina_banorte').val("");
});

$('#parametrosPersonalModal').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    $('#expediente_personal').val("");
    $('#periodo_personal').val("");
});
//****************Reporte Historico Prestamo FONACOT*************************
function abrirModalParametros(){
    $('#parametrosModal').modal('show');
}
//MOSTRAR EL REPORTE DE HISTORICO FONACOT
function verReporteFonacot(){
    var expediente = $('#expediente').val();
    var periodo = $('#periodo').val();
    if (expediente !== "" && periodo!=="") {
        
        $.ajax({
            type: "GET",
            url: "reportPrestamo/obtenPrestamoFonacot?expediente=" + expediente + "&periodo=" + periodo + "&tipo=PDF",
            xhrFields: {
                responseType: 'blob'
            },
            success: function (data, status, xhr) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    $("#parametrosModal").modal("hide");
                    $("#reportesModal").modal("toggle");
                    var contentType = xhr.getResponseHeader("Content-Type");
                    var filename = xhr.getResponseHeader("Content-disposition");
                    filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                    var url = window.URL.createObjectURL(data);
                    var frame = $('#reportes_frame');
                    var url = url;
                    frame.attr('src', url).show();
                }
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar informacion de reportes");
            }
        });
    }else{
        toastr["warning"]("Debe llenar los campos");
    }
}
//DESCARGAR EL REPORTE DEL HISTORICO DE FONACOT
function descargarReporteFornacot(){
    var expediente = $('#expediente').val();
    var periodo = $('#periodo').val();
    
    if (expediente !== "" && periodo!=="") {
    
        $.ajax({
            type: "GET",
            url: "reportPrestamo/obtenPrestamoFonacot?expediente=" + expediente + "&periodo=" + periodo + "&tipo=PDF",

            xhrFields: {
                responseType: 'blob'
            },
            success: function(data, textStatus, jqXHR) {
                $("#parametrosModal").modal("hide");
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
                    link.download = 'reportePrestamosFonacotExpediente_' + expediente + '_' + periodo + '.pdf';
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
    }else{
        toastr["warning"]("Debe llenar los campos");
    }
}

//****************Reporte Formato de Prestamos Personales*************************
function modalFormatoPrestamos(){
    $('#prestamoPersonalModal').modal('show');
}
//LISTAR EN EL SELECT LOS TIPO DE CONTRATOS DE TRABAJADORES
function contratoTabulador(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTabuladores",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#contratos').empty().append("<option value=''>*Contrato </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_tipo_tabulador === id) ? vselected = "selected" : vselected = " ";
                        $('#contratos').append('<option value="' + data[x].id_tipo_tabulador + '"' + vselected + '>' + data[x].desc_tipo_tabulador + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Con Contrato: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//ASIGNAR LA URL CORRESPONDIENTE CON LA OPCIÓN SELECCIONADA EN EL SELECT PARA VER EL REPORTE
function verificaOpcionVer(){
    var contrato = $('#contratos').val();
    var url = '';
    if (contrato === "1") {
        url = "reportPrestamo/obtenFormatoPrestamoB?tipo=PDF";
        verReportePrestamoPersonal(url);
    }else if (contrato === "2") {
        url = "reportPrestamo/obtenFormatoPrestamoC?tipo=PDF";
        verReportePrestamoPersonal(url);
    }else if (contrato === "3") {
        url = "reportPrestamo/obtenFormatoPrestamoF?tipo=PDF";
        verReportePrestamoPersonal(url);
    }
    
}
//ASIGNAR LA URL CORRESPONDIENTE CON LA OPCIÓN SELECCIONADA EN EL SELECT PARA DESCARGAR EL REPORTE
function verificaOpcionDescarga(){
    var contrato = $('#contratos').val();
    var url = '';
    if (contrato === "1") {
        url = "reportPrestamo/obtenFormatoPrestamoB?tipo=PDF";
        descargaReportePrestamoPersonal(url,contrato);
    }else if (contrato === "2") {
        url = "reportPrestamo/obtenFormatoPrestamoC?tipo=PDF";
        descargaReportePrestamoPersonal(url,contrato);
    }else if (contrato === "3") {
        url = "reportPrestamo/obtenFormatoPrestamoF?tipo=PDF";
        descargaReportePrestamoPersonal(url,contrato);
    }
    
}
//TRAER EL REPORTE SELECCIONADO Y MOSTRARLO
function verReportePrestamoPersonal(url){
    $.ajax({
            type: "GET",
            url:url,
            xhrFields: {
                responseType: 'blob'
            },
            success: function (data, status, xhr) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    $("#prestamoPersonalModal").modal("hide");
                    $("#reportesModal").modal("toggle");
                    var contentType = xhr.getResponseHeader("Content-Type");
                    var filename = xhr.getResponseHeader("Content-disposition");
                    filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                    var url = window.URL.createObjectURL(data);
                    var frame = $('#reportes_frame');
                    var url = url;
                    frame.attr('src', url).show();
                }
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar informacion de reportes");
            }
        });
}
//TRAER EL REPORTE SELECCIONADO Y DESCARGARLO
function descargaReportePrestamoPersonal(url,contrato){
    
    $.ajax({
        type: "GET",
        url: url,
        
        xhrFields: {
            responseType: 'blob'
        },
        success: function(data, textStatus, jqXHR) {
            $("#prestamoPersonalModal").modal("hide");
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
                link.download = 'reportePrestamoPersonal_' + contrato + '.pdf';
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
//****************Reporte Deposito Diferentes Bancos*************************
function modalOtrosBancos(){
    $('#depositoDiferentesBancosModal').modal('show');
    listarNominas();
}
//LISTAR LAS NOMINAS DE LOS TRABAJADORES
function listarNominas() {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#nomina').empty().append("<option value=''>* Tipo de Nomina </option> ");
                $('#nomina_banorte').empty().append("<option value=''>* Tipo de Nomina </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        $('#nomina').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                        $('#nomina_banorte').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                        
                    }
                }
                
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Nómina: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//VER EL REPORTE DE DEPOSITO GENERADO PARA OTROS BANCOS
function verReporteOtrosBancos(){
    
    var id_nomina = $('#nomina').val();
    var opcionSeleccionada = $('#nomina option[value="' + id_nomina + '"]');
    var desc_nomina = opcionSeleccionada.text();
    var periodo = $('#periodo_deposito').val();
    
    if (id_nomina !== "" && periodo !== "") {
        $.ajax({
            type: "GET",
            url:"reportPrestamo/obtenDepositoDiferentesBancos?nomina=" + desc_nomina + "&periodo=" + periodo + "&id_nomina=" + id_nomina + "&tipo=PDF",
            xhrFields: {
                responseType: 'blob'
            },
            success: function (data, status, xhr) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    $("#depositoDiferentesBancosModal").modal("hide");
                    $("#reportesModal").modal("toggle");
                    var contentType = xhr.getResponseHeader("Content-Type");
                    var filename = xhr.getResponseHeader("Content-disposition");
                    filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                    var url = window.URL.createObjectURL(data);
                    var frame = $('#reportes_frame');
                    var url = url;
                    frame.attr('src', url).show();
                }
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar informacion de reportes");
            }
        });
    }else{
        toastr["warning"]("Debe llenar los campos");
    }
}
//DESCARGAR EL REPORTE DE DEPOSITO GENERADO PARA OTROS BANCOS Y EN EXCEL
function descargarOtrosBancos(){
    //El reporte "Exporta Préstamos Personales a Excel Para Diferentes Bancos" cambia el estatus deposito = 1 cuando se descarga en Excel y PDF para no generar doble descuentos (Solo se descarga una vez)
    var id_nomina = $('#nomina').val();
    var opcionSeleccionada = $('#nomina option[value="' + id_nomina + '"]');
    var desc_nomina = opcionSeleccionada.text();
    var periodo = $('#periodo_deposito').val();
    if (id_nomina !== "" && periodo !== "") {
        
        $.ajax({
            type: "GET",
            url:"reportPrestamo/obtenDepositoDiferentesBancos?nomina=" + desc_nomina + "&periodo=" + periodo + "&id_nomina=" + id_nomina + "&tipo=PDF",

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
                    link.download = 'depositoDiferentesBancos_' + desc_nomina + '_'+ periodo + '.pdf';
                    link.click();
                    cambiaEstatusDeposito(id_nomina, periodo);
                }
            },
            error: function(e) {
                toastr["warning"]("Error al recuperar información de reportes", {
                    progressBar: true,
                    closeButton: true
                });
            }
        });
    
        $.ajax({
            type: "GET",
            url: "reportPrestamo/obtenDiferentesBancosExcel?nomina=" + desc_nomina + "&periodo=" + periodo + "&id_nomina=" + id_nomina + "&tipo=EXCEL", // Cambia "PDF" a "EXCEL"

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
                    link.download = 'depositoDiferentesBancos_' + desc_nomina + '_' + periodo + '.xlsx'; // Cambia la extensión del archivo a ".xlsx"
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
    }else{
        toastr["warning"]("Debe llenar los campos");
    }    
}
//****************Reporte Deposito BANORTE*************************
function modalBanorte(){
    $('#depositoBanorteModal').modal('show');
    listarNominas();
}
//VER EL REPORTE DE DEPOSITO GENERADO PARA BANORTE
function verReporteBanorte(){
    
    var id_nomina = $('#nomina_banorte').val();
    var opcionSeleccionada = $('#nomina_banorte option[value="' + id_nomina + '"]');
    var desc_nomina = opcionSeleccionada.text();
    var periodo = $('#periodo_banorte').val();
    
    if (id_nomina !== "" && periodo !== "") {
        $.ajax({
            type: "GET",
            url:"reportPrestamo/obtenDepositoBanorte?nomina=" + desc_nomina + "&periodo=" + periodo + "&id_nomina=" + id_nomina +"&tipo=PDF",
            xhrFields: {
                responseType: 'blob'
            },
            success: function (data, status, xhr) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    $("#depositoBanorteModal").modal("hide");
                    $("#reportesModal").modal("toggle");
                    var contentType = xhr.getResponseHeader("Content-Type");
                    var filename = xhr.getResponseHeader("Content-disposition");
                    filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                    var url = window.URL.createObjectURL(data);
                    var frame = $('#reportes_frame');
                    var url = url;
                    frame.attr('src', url).show();
                }
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar informacion de reportes");
            }
        });
    }else{
        toastr["warning"]("Debe llenar los campos");
    }
}
//DESCARGA EL REPORTE DE DEPOSITO GENERADO PARA OTROS BANCOS
function descargarBanorte(){
    var id_nomina = $('#nomina_banorte').val();
    var opcionSeleccionada = $('#nomina_banorte option[value="' + id_nomina + '"]');
    var desc_nomina = opcionSeleccionada.text();
    var periodo = $('#periodo_banorte').val();
    
    if (id_nomina !== "" && periodo !== "") {
        
        $.ajax({
            type: "GET",
            url:"reportPrestamo/obtenDepositoBanorte?nomina=" + desc_nomina + "&periodo=" + periodo + "&id_nomina=" + id_nomina +"&tipo=PDF",

            xhrFields: {
                responseType: 'blob'
            },
            success: function(data, textStatus, jqXHR) {
                $("#depositoBanorteModal").modal("hide");
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
                    link.download = 'depositoBanorte_' + desc_nomina + '_'+ periodo + '.pdf';
                    link.click();
                    cambiaEstatusDeposito(id_nomina, periodo);
                }
            },
            error: function(e) {
                toastr["warning"]("Error al recuperar información de reportes", {
                    progressBar: true,
                    closeButton: true
                });
            }
        });
    }else{
        toastr["warning"]("Debe llenar los campos");
    }
}
//CAMBIA EL ESTATUS DE DEPOSITO PARA NO IMPRIMIR DOS VECES EL MISMO REPORTE CON LAS MISMAS PERSONAS Y EVITAR DOBLES DEPOSITOS
function cambiaEstatusDeposito(id_nomina, periodo_actual){
    $.ajax({
        type: "GET",
        url: "creditoPersonal/estatusDepositoPrestamos/"+id_nomina+"/"+periodo_actual,
        dataType: 'json',
        success: function (data) {
            toastr["success"]("El estatus fue cambiado");
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar información");
        }
    });
    
}

//****************Reporte Historico Prestamo Personal*************************
function abrirModalParametrosPersonal(){
    $('#parametrosPersonalModal').modal('show');
}
//MOSTRAR EL REPORTE DE HISTORICO DE PRESTAMOS PERSONALES
function verReportePersonal(){
    var expediente = $('#expediente_personal').val();
    var periodo = $('#periodo_personal').val();
    if (expediente !== "" && periodo!=="") {
        
        $.ajax({
            type: "GET",
            url: "reportPrestamo/obtenPrestamoPersonal?expediente=" + expediente + "&periodo=" + periodo + "&tipo=PDF",
            xhrFields: {
                responseType: 'blob'
            },
            success: function (data, status, xhr) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                } else {
                    $("#parametrosPersonalModal").modal("hide");
                    $("#reportesModal").modal("toggle");
                    var contentType = xhr.getResponseHeader("Content-Type");
                    var filename = xhr.getResponseHeader("Content-disposition");
                    filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                    var url = window.URL.createObjectURL(data);
                    var frame = $('#reportes_frame');
                    var url = url;
                    frame.attr('src', url).show();
                }
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar informacion de documentos cargados: General 1", {progressBar: true, closeButton: true});
            }
        });
    }else{
        toastr["warning"]("Debe llenar los campos");
    }
}

//DESCARGAR EL REPORTE DEL HISTORICO DE PRESTAMOS PERSONALES
function descargarReportePersonal(){
    var expediente = $('#expediente_personal').val();
    var periodo = $('#periodo_personal').val();
    
    if (expediente !== "" && periodo!=="") {
    
        $.ajax({
            type: "GET",
            url: "reportPrestamo/obtenPrestamoPersonal?expediente=" + expediente + "&periodo=" + periodo + "&tipo=PDF",

            xhrFields: {
                responseType: 'blob'
            },
            success: function(data, textStatus, jqXHR) {
                $("#parametrosPersonalModal").modal("hide");
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
                    link.download = 'reporteHistoricoPrestamosPersonalExpediente_' + expediente + '_' + periodo + '.pdf';
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
    }else{
        toastr["warning"]("Debe llenar los campos");
    }
}

function validacion(object){;
    
    var nuevo_numero = object.value.substring(0, 6);
    object.value = nuevo_numero;
}