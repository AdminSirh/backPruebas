document.addEventListener('DOMContentLoaded', () => {
    
});



/*=============================================
 SE CONSULTA SI EL TRABAJADOR TIENE UN REGISTRO EN EL HISTORICO 
 PARA SABER SI ES INGRESO O CAMBIO DE PLAZA Y VISUALIZAR
 =============================================*/
function consultarHistoricoVer(id_trabajador){
    var url = "";
    var numero_expediente = $('#expediente').val();
    var numero = $('#numero_reporte').val();
    
    var parametro_base = $('#parametro_base').val();
    var parametro_confianza = $('#parametro_confianza').val();
    var parametro_funcionarios = $('#parametro_funcionarios').val();
    
    if (numero_expediente !== "" && numero !== "") {
        $.ajax({
            type: "GET",
            url: "trabajador/buscarHistoricoPlazaTrabajador/"+id_trabajador,
            dataType: 'json',
            success: function (data) {
                if (data.data === null) {
                    url = "report/aviso/obtenAvisoMovimientoPersonal?numero_expediente=" + numero_expediente + "&numero=" + numero + "&parametro_base=" + parametro_base + "&parametro_confianza="+ parametro_confianza+ "&parametro_estructura=" + parametro_funcionarios +"&tipo=pdf";
                    visualizar(url);
                    //Es un ingreso, primer reporte
                }else{
                    url = "report/aviso/obtenAvisoMovimientoPersonalCambio?numero_expediente=" + numero_expediente + "&numero=" + numero + "&parametro_base=" + parametro_base + "&parametro_confianza="+ parametro_confianza+ "&parametro_estructura=" + parametro_funcionarios +"&tipo=pdf";
                    visualizar(url);
                    //Es un cambio, segundo reporte
                }
            },
            error: function (e) {

            }
        });
    }else{
        toastr["warning"]("Por favor llene los campos solicitados");
    }       
}

/*=============================================
 SE CONSULTA SI EL TRABAJADOR TIENE UN REGISTRO EN EL HISTORICO 
 PARA SABER SI ES INGRESO O CAMBIO DE PLAZA Y DESCARGAR
 =============================================*/
function consultarHistoricoDescargar(id_trabajador){
    var url = "";
    var numero_expediente = $('#expediente').val();
    var numero = $('#numero_reporte').val();
    
    var parametro_base = $('#parametro_base').val();
    var parametro_confianza = $('#parametro_confianza').val();
    var parametro_funcionarios = $('#parametro_funcionarios').val();
    if (numero_expediente !== "" && numero !== "") {
        $.ajax({
            type: "GET",
            url: "trabajador/buscarHistoricoPlazaTrabajador/"+id_trabajador,
            dataType: 'json',
            success: function (data) {
                if (data.data === null) {
                    url = "report/aviso/obtenAvisoMovimientoPersonal?numero_expediente=" + numero_expediente + "&numero=" + numero + "&parametro_base=" + parametro_base + "&parametro_confianza="+ parametro_confianza+ "&parametro_estructura=" + parametro_funcionarios +"&tipo=PDF";

                    descargar(url,numero_expediente,numero);
                    //Es un ingreso, primer reporte
                }else{
                    url = "report/aviso/obtenAvisoMovimientoPersonalCambio?numero_expediente=" + numero_expediente + "&numero=" + numero + "&parametro_base=" + parametro_base + "&parametro_confianza="+ parametro_confianza+ "&parametro_estructura=" + parametro_funcionarios +"&tipo=PDF";

                    descargar(url,numero_expediente,numero);
                    //Es un cambio, segundo reporte
                }
            },
            error: function (e) {
                
            }
        });
    }else{
        toastr["warning"]("Por favor llene los campos solicitados");
    }
}

/*=============================================
 OBTENER EL TABULADOR DE NOMINA DEL TRABAJADOR
 =============================================*/
function obtenNomina(id_trabajador){
    $.ajax({
            type: "GET",
            url: "trabajador/buscarPlazaTrabajador/"+id_trabajador,
            dataType: 'json',
            success: function (data) {
                $('#nomina_tabulador').val(data.data.cat_Tipo_Nomina.cat_Tipo_Tabulador.clave_tipo_tabulador);
                valoresBooleanos();
            },
            error: function (e) {
                
            }
        });   
}

/*=============================================
 LLENA LOS PARAMETROS QUE SERÁN INGRESADOS EN JASPER
 =============================================*/
function valoresBooleanos(){
    var nomina_tabulador =  $('#nomina_tabulador').val();
    var parametro_base = false;
    var parametro_confianza = false;
    var parametro_funcionarios = false;
    if (nomina_tabulador === "B") {
        parametro_base = true;
    }else if(nomina_tabulador === "F"){
        parametro_funcionarios = true; 
    }else if(nomina_tabulador === "C"){
        parametro_confianza = true;
    }
    $('#parametro_base').val(parametro_base);
    $('#parametro_confianza').val(parametro_confianza);
    $('#parametro_funcionarios').val(parametro_funcionarios);
    
    
}
/*=============================================
 BUSCAR EL EXPEDIENTE Y OBTIENE SU ID
 =============================================*/
function buscaIdExpediente(){
    var expediente = $('#expediente').val();
    if (expediente.length === 5) {
        $.ajax({
            type: "GET",
            url: "catalogos/buscarIdExpediente/"+expediente,
            dataType: 'json',
            success: function (data) {
                if (data.data!==null) {
                    buscaTrabajador(data.data);
                }else{
                    toastr['error']("No existe el expediente ingresado");
                    $("#expediente").val("");
                }
            },
            error: function (e) {
                
            }
        });
    }else {
        $('#nombre_trabajador').html(
                        "<div class='row m-lg-4'>" +
                            "<label class='form-label' for='addon-wrapping-left'> </label>" +
                        "</div>" );
    }
}

/*=============================================
 BUSCAR TRABAJADOR POR ID EXPEDIENTE
 =============================================*/
function buscaTrabajador(id_expediente){
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador_IdExpediente/" + id_expediente,
        dataType: 'json',
        success: function (data) {
            if (data.data!==null) {
                $('#nombre_trabajador').html(
                        "<div class='row m-lg-4'>" +
                            "<label class='form-label' for='addon-wrapping-left'>"+ data.data.persona.nombre + " " + data.data.persona.apellido_paterno + " " + data.data.persona.apellido_materno +"</label>" +
                        "</div>"
                );
                $('#id_trabajador').val(data.data.id_trabajador);
                obtenNomina(data.data.id_trabajador);
            }else{
                
                
            }
        },
        error: function (e) {
        }
    });
}


/*=============================================
 VISUALIZAR EL REPORTE GENERADO
 =============================================*/
function visualizar(url){
    
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
            toastr["warning"]("Error al recuperar informacion de documentos ", {progressBar: true, closeButton: true});
        }
    });
};

/*=============================================
 DESCARGAR EL REPORTE GENERADO
 =============================================*/
function descargar(url,numero_expediente,numero){
    $.ajax({
        type: "GET",
        url: url,
        
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
                link.download = 'reporteAvisoMovimientoPersonal_' + numero_expediente + '_' + numero + '.pdf';
                link.click();
            }
        },
        error: function(e) {
            toastr["warning"]("Error al recuperar información de documentos", {
                progressBar: true,
                closeButton: true
            });
        }
    });
};

