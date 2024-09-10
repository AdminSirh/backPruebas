document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var codigo_puesto = urlParams.get('codigo_puesto');
    buscarDatosPuesto(codigo_puesto);
    tabs();

});

function tabs() {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li><a href='#tabs-1' class='btn btn-primary'>1.- DATOS GENERALES DEL PUESTO</a></li>");
}


function buscarDatosPuesto(cod_puesto) {
    $.ajax({
        type: "GET",
        url: "catalogos/buscarCodPuesto/" + cod_puesto,
        dataType: 'json',
        success: function (data) {
            
            event.preventDefault();
            
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            /*=============================================
              VARIABLES PARA EL REGISTRO HISTORICO
             =============================================*/
            $("#id_puesto_DB").val(data.data.id_puesto);
            $("#sueldo_mensual_DB").val(data.data.sueldo_mensual);
            $("#nivelPuesto_DB").val(data.data.nivel);
            $("#puesto_DB").val(data.data.puesto);
            $("#sueldoDiario_DB").val(data.data.sueldo_diario);
            $("#sueldoDiarioDoble_DB").val(data.data.sueldo_diario_doble);
            $("#sueldoHora_DB").val(data.data.sueldo_hora);
            $("#sueldoHoraDoble_DB").val(data.data.sueldo_hora_doble);
            $("#sueldoHoraTriple_DB").val(data.data.sueldo_hora_triple);
            $("#cantidadAdicionalMen_DB").val(data.data.cantidad_adicional_mensual);
            $("#sueldoMensualNeto_DB").val(data.data.sueldo_mensual_neto);
            $("#noPlazasAut_DB").val(data.data.num_plazas_autorizadas);
            $("#sueldoQuicenalTab_DB").val(data.data.sueldo_quincenal_tabulado);
            $("#difSueldoDiario_DB").val(data.data.dif_sueldo_diario);
            $("#difSueldoHora_DB").val(data.data.dif_sueldo_hora);
            $("#difSueldoMensual_DB").val(data.data.dif_sueldo_mensual);
            $("#difCantAdMensual_DB").val(data.data.dif_cant_adicmens);
            $("#isrMensualPrestacion_DB").val(data.data.isr_mens_prest);
            $("#isrMensual_DB").val(data.data.isr_mensual);
            $("#cuotaImss_DB").val(data.data.cuota_imss);
            $("#codigoRH_DB").val(data.data.codigo_rh);
            $("#sueldoEstructura_DB").val(data.data.sueldo_estructura);
            $("#sueldoHora7_DB").val(data.data.sueldoHora7);
            $("#status_DB").val(data.data.status);
            $("#fecha_captura_DB").val(data.data.fecha_captura);
            $("#fecha_movimiento_DB").val(data.data.fecha_movimiento);
            
            
            if (data.data.codigo_puesto !== null) {
                $("#cod_Puesto").val(data.data.codigo_puesto);
            }
            if (data.data.nivel !== null) {
                $("#nivelPuesto").val(data.data.nivel);
            }
            if (data.data.puesto !== null) {
                $("#puesto").val(data.data.puesto);
            }
            if (data.data.sueldo_diario !== null) {
                $("#sueldoDiario").val(data.data.sueldo_diario);
            }
            if (data.data.sueldo_diario_doble !== null) {
                $("#sueldoDiario_Doble").val(data.data.sueldo_diario_doble);
            }
            if (data.data.sueldo_hora !== null) {
                $("#sueldoHora").val(data.data.sueldo_hora);
            }
            if (data.data.sueldo_hora_doble !== null) {
                $("#sueldoHoraDoble").val(data.data.sueldo_hora_doble);
            }
            if (data.data.sueldo_hora_triple !== null) {
                $("#sueldoHoraTriple").val(data.data.sueldo_hora_triple);
            }
            if (data.data.sueldo_mensual !== null) {
                $("#sueldoMensual").val(data.data.sueldo_mensual);
            }
            if (data.data.cantidad_adicional_mensual !== null) {
                $("#cantidadAdMensual").val(data.data.cantidad_adicional_mensual);
            }
            if (data.data.num_plazas_autorizadas !== null) {
                $("#noPlazasAut").val(data.data.num_plazas_autorizadas);
            }
            if (data.data.sueldo_quincenal_tabulado !== null) {
                $("#sueldoQuicenalTab").val(data.data.sueldo_quincenal_tabulado);
            }
            if (data.data.dif_sueldo_diario !== null) {
                $("#difSueldoDiario").val(data.data.dif_sueldo_diario);
            }
            if (data.data.dif_sueldo_hora !== null) {
                $("#dif_SueldoHora").val(data.data.dif_sueldo_hora);
            }
            if (data.data.dif_sueldo_mensual !== null) {
                $("#difSueldoMensual").val(data.data.dif_sueldo_mensual);
            }
            if (data.data.cantidad_adicional_mensual !== null) {
                $("#difCantAdMensual").val(data.data.dif_cant_adicmens);
            }
            if (data.data.isr_mens_prest !== null) {
                $("#isrMensualPrestacion").val(data.data.isr_mens_prest);
            }
            if (data.data.isr_mensual !== null) {
                $("#isrMensual").val(data.data.isr_mensual);
            }
            if (data.data.cuota_imss !== null) {
                $("#cuotaImss").val(data.data.cuota_imss);
            }
            if (data.data.sueldo_mensual_neto !== null) {
                $("#sueldoMensualNeto").val(data.data.sueldo_mensual_neto);
            }
            if (data.data.codigo_rh !== null) {
                $("#codigoRH").val(data.data.codigo_rh);
            }
            if (data.data.sueldo_estructura !== null) {
                $("#sueldoEstructura").val(data.data.sueldo_estructura);
            }
            if (data.data.sueldoHora7 !== null) {
                $("#sueldoHora7").val(data.data.sueldoHora7);
            }

        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


/*=============================================
 EDITAR DATOS DE UN PUESTO
 =============================================*/
$("#formPuesto").submit(function (e) {
    event.preventDefault();
    var sueldo_mensualDB = $("#sueldo_mensual_DB").val();
    var cantidadAdicionalMen_DB = $("#cantidadAdicionalMen_DB").val();
    
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var codigo_puesto = urlParams.get('codigo_puesto');
    var patron = /^[A-Za-zÁÉÍÓÚáéíóú\s]+$/;
    var cod_puesto = $("#cod_Puesto").val();
    var nivelPuesto = $("#nivelPuesto").val();
    var puesto = $("#puesto").val();
    var sueldoDiario = $("#sueldoDiario").val();
    var sueldoDiarioDoble = $("#sueldoDiario_Doble").val();
    var sueldoHora = $("#sueldoHora").val();
    var sueldoHoraDoble = $("#sueldoHoraDoble").val();
    var sueldoHoraTriple = $("#sueldoHoraTriple").val();
    var sueldoMensual = $("#sueldoMensual").val();
    var cantidadAdMensual = $("#cantidadAdMensual").val();
    var noPlazasAut = $("#noPlazasAut").val();
    var sueldoQuincenal = $("#sueldoQuicenalTab").val();
    var diferenciaSueldoDiario = $("#difSueldoDiario").val();
    var diferenciaSueldoHora = $("#dif_SueldoHora").val();
    var diferenciaSueldoMensual = $("#difSueldoMensual").val();
    var diferenciaCantidadAdMensual = $("#difCantAdMensual").val();
    var isrMensualPrestacion = $("#isrMensualPrestacion").val();
    var isrMensual = $("#isrMensual").val();
    var cuotaImss = $("#cuotaImss").val();
    var sueldoMensualNeto = $("#sueldoMensualNeto").val();
    var codigoRh = $("#codigoRH").val();
    var sueldoEstructura = $("#sueldoEstructura").val();
    var sueldoHora7 = $("#sueldoHora7").val();
    
    ;
    if ($.trim(cod_puesto) === "") {
        return false;
    }
    if ($.trim(nivelPuesto) === "" || $.trim(nivelPuesto) < "1") {
        return false;
    }
    if ($.trim(puesto) === "" || $.trim(puesto) < "1") {
        return false;
    }
    if (!patron.test(puesto)) {
        toastr["warning"]("Para el campo 'Puesto' no esta permitido ingresar dígitos o caracteres diferentes a letras ", "Aviso", {progressBar: true, closeButton: true});
        return false;
    }
    if ($.trim(sueldoDiario) === "" || $.trim(sueldoDiario) < "0") {
        return false;
    }
    if ($.trim(sueldoDiarioDoble) === "" || $.trim(sueldoDiarioDoble) < "0") {
        return false;
    }
    if ($.trim(sueldoHora) === "" || $.trim(sueldoHora) < "0") {
        return false;
    }
    if ($.trim(sueldoHoraDoble) === "" || $.trim(sueldoHoraDoble) < "0") {
        return false;
    }
    if ($.trim(sueldoHoraTriple) === "" || $.trim(sueldoHoraTriple) < "0") {
        return false;
    }
    if ($.trim(sueldoMensual) === "" || $.trim(sueldoMensual) < "1") {
        return false;
    }
    if ($.trim(cantidadAdMensual) === "" || $.trim(cantidadAdMensual) < "0") {
        return false;
    }
    if ($.trim(sueldoQuincenal) === "" || $.trim(sueldoQuincenal) < "1") {
        return false;
    }
    if ($.trim(diferenciaSueldoDiario) === "" || $.trim(diferenciaSueldoDiario) < "0") {
        return false;
    }
    if ($.trim(diferenciaSueldoHora) === "" || $.trim(diferenciaSueldoHora) < "0") {
        return false;
    }
    if ($.trim(diferenciaSueldoMensual) === "" || $.trim(diferenciaSueldoMensual) < "0") {
        return false;
    }
    if ($.trim(diferenciaCantidadAdMensual) === "" || $.trim(diferenciaCantidadAdMensual) < "0") {
        return false;
    }
    if ($.trim(isrMensualPrestacion) === "" || $.trim(isrMensualPrestacion) < "0") {
        return false;
    }
    if ($.trim(isrMensual) === "" || $.trim(isrMensual) < "0") {
        return false;
    }
    if ($.trim(cuotaImss) === "" || $.trim(cuotaImss) < "0") {
        return false;
    }
    if ($.trim(sueldoMensualNeto) === "" || $.trim(sueldoMensualNeto) < "1") {
        return false;
    }
    if ($.trim(codigoRh) === "") {
        return false;
    }
    if ($.trim(sueldoEstructura) === "" || $.trim(sueldoEstructura) < "1") {
        return false;
    }
    if ($.trim(sueldoHora7) === "" || $.trim(sueldoHora7) < "0") {
        return false;
    }
    if ($.trim(noPlazasAut) === "" || $.trim(noPlazasAut) < "1") {
        return false;
    }
    var datos = { "codigo_puesto": parseInt(cod_puesto), "codigo_rh": codigoRh, "cantidad_adicionalMensual": cantidadAdMensual, "cuota_imss": cuotaImss, "dif_cantAdicMens": diferenciaCantidadAdMensual,
        "dif_sueldoDiario": diferenciaSueldoDiario, "dif_sueldoHora":diferenciaSueldoHora, "dif_sueldoMensual": diferenciaSueldoMensual,
        "isr_mensPrest": isrMensualPrestacion, "isr_mensual": isrMensual, "nivel": nivelPuesto, "num_plazasAutorizadas": noPlazasAut, "puesto": puesto,
        "status": 1, "sueldoHora7": sueldoHora7, "sueldo_diario": sueldoDiario, "sueldo_diarioDoble": sueldoDiarioDoble, "sueldo_estructura": sueldoEstructura,
        "sueldo_hora": sueldoHora, "sueldo_horaDoble": sueldoHoraDoble, "sueldo_horaTriple": sueldoHoraTriple, "sueldo_mensual": sueldoMensual, "sueldo_mensualNeto": sueldoMensualNeto, "sueldo_quincenalTabulado": sueldoQuincenal};
    
    
    $.ajax({
        type: "POST",
        url: "catalogos/editarPuesto/" + codigo_puesto,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),
        
        success: function (data) {
            
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            if (sueldoMensual!==sueldo_mensualDB || cantidadAdicionalMen_DB !== cantidadAdMensual) {
                guardarHistorico();   
            }
            toastr['success']("Los datos han sido actualizados", "Aviso", {progressBar: true, closeButton: true});
            
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

function verPuesto() {
    window.location.href = 'adminPuestos';
}


/*=============================================
 GUARDA EL REGISTRO HISTORICO DEL PUESTO
 =============================================*/
function guardarHistorico(){
    
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_puesto_DB = $("#id_puesto_DB").val();
    var codigo_puesto = urlParams.get('codigo_puesto');
    var sueldo_mensual_DB = $("#sueldo_mensual_DB").val();
    var nivelPuesto_DB = $("#nivelPuesto_DB").val();
    var puesto_DB = $("#puesto_DB").val();
    var sueldoDiario_DB = $("#sueldoDiario_DB").val();
    var sueldoDiarioDoble_DB = $("#sueldoDiarioDoble_DB").val();
    var sueldoHora_DB = $("#sueldoHora_DB").val();
    var sueldoHoraDoble_DB = $("#sueldoHoraDoble_DB").val();
    var sueldoHoraTriple_DB = $("#sueldoHoraTriple_DB").val();
    var cantidadAdicionalMen_DB = $("#cantidadAdicionalMen_DB").val();
    var sueldoMensualNeto_DB = $("#sueldoMensualNeto_DB").val();
    var noPlazasAut_DB = $("#noPlazasAut_DB").val();
    var sueldoQuicenalTab_DB = $("#sueldoQuicenalTab_DB").val();
    var difSueldoDiario_DB = $("#difSueldoDiario_DB").val();
    var difSueldoHora_DB = $("#difSueldoHora_DB").val();
    var difSueldoMensual_DB = $("#difSueldoMensual_DB").val();
    var difCantAdMensual_DB = $("#difCantAdMensual_DB").val();
    var isrMensualPrestacion_DB = $("#isrMensualPrestacion_DB").val();
    var isrMensual_DB = $("#isrMensual_DB").val();
    var cuotaImss_DB = $("#cuotaImss_DB").val();
    var codigoRH_DB = $("#codigoRH_DB").val();
    var sueldoEstructura_DB = $("#sueldoEstructura_DB").val();
    var sueldoHora7_DB = $("#sueldoHora7_DB").val();
    var status_DB = $("#status_DB").val();
    var fecha_captura_DB = $("#fecha_captura_DB").val();
    var fecha_movimiento_DB = $("#fecha_movimiento_DB").val();
    
    
    var datos = {"id_puesto":id_puesto_DB, "codigo_puesto":codigo_puesto, "sueldo_mensual":sueldo_mensual_DB, "nivel":nivelPuesto_DB, "puesto":puesto_DB, "sueldo_diario":sueldoDiario_DB, "sueldo_diario_doble":sueldoDiarioDoble_DB,
                 "sueldo_hora":sueldoHora_DB, "sueldo_hora_doble":sueldoHoraDoble_DB, "sueldo_hora_triple":sueldoHoraTriple_DB, "cantidad_adicional_mensual":cantidadAdicionalMen_DB, "sueldo_mensual_neto":sueldoMensualNeto_DB,
                 "num_plazas_autorizadas":noPlazasAut_DB, "sueldo_quincenal_tabulado":sueldoQuicenalTab_DB, "dif_sueldo_diario":difSueldoDiario_DB, "dif_sueldo_hora":difSueldoHora_DB, "dif_sueldo_mensual":difSueldoMensual_DB,
                 "dif_cant_adicmens":difCantAdMensual_DB, "isr_mens_prest":isrMensualPrestacion_DB, "isr_mensual":isrMensual_DB, "cuota_imss":cuotaImss_DB, "codigo_rh":codigoRH_DB, "sueldo_estructura":sueldoEstructura_DB,
                 "sueldo_hora7": sueldoHora7_DB, "status":status_DB, "fecha_captura":fecha_captura_DB, "fecha_movimiento":fecha_movimiento_DB
    };
    
    $.ajax({
        type: "POST",
        url: "catalogos/guardaHistoricoPuesto",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            toastr["success"]("Se guardó registro historico", {progressBar: true, closeButton: true});
            
            
        },
        error: function (e) {
             toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
    
    
}