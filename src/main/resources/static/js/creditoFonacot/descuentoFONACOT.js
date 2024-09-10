document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    buscaExpediente(trabajador_id);
    habilitaCampos(); 
    obtenNomina(trabajador_id);
    bloqueaBotonGuardar(trabajador_id);
});


//REDIRECCIONA A CADA OPCIÓN SEGÚN EL BOTÓN SELECCIONADO
function saldarCuenta(){
    var id_opcion = 1;
    $("#tipo").val(id_opcion);
    $("#compararContraseniaModal").modal();
}
//REDIRECCIONA A LA OPCIÓN DE ELIMINAR CUENTA
function eliminarCuenta(){
    var id_opcion = 2;
    $("#tipo").val(id_opcion);
    $("#compararContraseniaModal").modal();
}


//Limpieza de Compara Contraseña
$('#compararContraseniaModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#contrasenia").val("");
   $("#alerta").html("");
   
}); 


//Compara la contraseña ingresada con la del USUARIO Y abre el modal seleccionado
function comparaPassword(){
    var password = $("#contrasenia").val();
    if (password !== "") {
        $.ajax({
            type: "GET",
            url: "usuarios/verificarPassword/" + password,
            dataType: 'json',
            success: function (data) {
                if (data.data === true) {
                    if ($("#tipo").val() === "1") {
                        $("#compararContraseniaModal").modal('hide');
                        $("#saldarCuentaModal").modal();
                        llenaModalSaldar();

                    }else if($("#tipo").val() === "2") {
                        eliminaCuenta();
                    }

                }else{
                    $('#alerta').html("<br>" + "<h6>La Contraseña es Incorrecta, Intente nuevamente.</h6>");
                    $("#contrasenia").val('');
                }


            },
            error: function (e) {
                alert(e);
            }
        });
    }else {
        $('#alerta').html("<br>" + "<h6>La Contraseña es Incorrecta, Intente nuevamente.</h6>");
    }
}
// LLENA INFORMACION DEL TRABAJADOR EN EL MODAL SALDAR CUENTA
function llenaModalSaldar(){
    var expediente = $('#expediente').val();
    var expediente_saldar_cuenta = document.getElementById('expediente_saldar_cuenta');
    expediente_saldar_cuenta.textContent = expediente;
    var nombre = $('#nombre').val();
    var nombre_saldar_cuenta = document.getElementById('nombre_saldar_cuenta');
    nombre_saldar_cuenta.textContent = nombre;
    
}


// GUARDA LOS DATOS INGRESADOS EN LA TABLA "CUENTAS POR COBRAR"
function guardarDosificacion(){
    event.preventDefault();
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    
    var saldo_inicial = $("#saldo_inicial").val();
    var saldo_actual = parseFloat(document.querySelector('label[for="saldo_actual_pesos"]').innerText);
    var monto_pago = parseFloat(document.querySelector('label[for="pago_pesos"]').innerText);
    var numero_periodos_pago = parseInt(document.querySelector('label[for="periodos_restantes_entero"]').innerText);
    var id_nomina = $('#id_nomina').val();
    var periodo = $('#periodo_oculto').val();
    
    if ($.trim(saldo_inicial) === "") {
        return false;
    }
    if ($.trim(saldo_actual) === "0.00") {
        return false;
    }
    if ($.trim(monto_pago) === "0.00") {
        return false;
    }
    if ($.trim(numero_periodos_pago) === "0") {
        return false;
    }
    var remanente = saldo_inicial -(monto_pago*numero_periodos_pago);
    if (remanente < 0) {
        remanente=0;
    }else{
        remanente =(saldo_inicial -(monto_pago*numero_periodos_pago)).toFixed(2);
    }
    
    var datos = {"trabajador":{"id_trabajador": trabajador_id}, "cat_Tipo_Nomina": {"id_tipo_nomina":id_nomina}, "saldo_inicial" :saldo_inicial, "saldo_actual":parseFloat(saldo_actual), "monto_pago":monto_pago,
                "numero_periodos_pago":numero_periodos_pago, "periodo_pago_actual":periodo,"remanente_ultimo_pago":remanente, "remanente_ultimo_pago_pr":remanente,
                "periodo_inicial":periodo, "monto_criterio_pago":numero_periodos_pago,"saldo_actual_pr":saldo_inicial,"solicitado_fonacot":saldo_inicial};
    $.ajax({
        type: "POST",
        url: "creditoFonacot/guardarDescuentoFonacot",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            toastr['success'](data.data, "Datos Guardados Correctamente");
            afterSave(trabajador_id);
        },
        error: function (e) {
            toastr["warning"]("Error al guardar Descuento " + e, " error", {progressBar: true, closeButton: true});
        }
    }); 
}

function afterSave(trabajador_id){
    var radioInputs = document.getElementsByName('opcion');
    var periodos_fijos = document.getElementById('periodos_fijos');
    var monto_fijo = document.getElementById('monto_fijo');
    var porcentaje = document.getElementById('porcentaje');
    porcentaje.readOnly = true;
    monto_fijo.readOnly = true;
    periodos_fijos.readOnly = false;
    radioInputs[0].checked = true;
    
    
    $('#saldo_inicial').val('');
    $('#periodos_fijos').val('');
    $('#monto_fijo').val('');
    $('#porcentaje').val('');
    bloqueaBotonGuardar(trabajador_id);
}

// HABILITA LOS CAMPOS AL CLICKEAR EL RADIO E INHABILITA Y LIMPIA EL RESTO
function habilitaCampos(){
    // Obtén una lista de todos los elementos de entrada de radio
    
    var radioInputs = document.getElementsByName('opcion');
    var periodos_fijos = document.getElementById('periodos_fijos');
    var porcentaje = document.getElementById('porcentaje');
    var monto_fijo = document.getElementById('monto_fijo');
    
    radioInputs[0].checked = true;
    periodos_fijos.readOnly = false;
    // Agrega un controlador de eventos al evento "click" de cada elemento de entrada de radio
    for (var i = 0; i < radioInputs.length; i++) {
      radioInputs[i].addEventListener('click', function() {
        // Verifica qué elemento de entrada de radio se ha seleccionado
        for (var j = 0; j < radioInputs.length; j++) {
          if (radioInputs[j].checked) {
            var valorSeleccionado = radioInputs[j].value;
            if (valorSeleccionado === 'opcion1') {
                periodos_fijos.readOnly = false;
                porcentaje.readOnly = true;
                monto_fijo.readOnly = true;
                $('#porcentaje').val("");
                $('#monto_fijo').val("");
                receteaLabels();
                
            }else if(valorSeleccionado === 'opcion2'){
                periodos_fijos.readOnly = true;
                porcentaje.readOnly = false;
                monto_fijo.readOnly = true;
                $('#periodos_fijos').val("");
                $('#monto_fijo').val("");
                receteaLabels();
            }else if(valorSeleccionado === 'opcion3'){
                periodos_fijos.readOnly = true;
                porcentaje.readOnly = true;
                monto_fijo.readOnly = false;
                $('#periodos_fijos').val("");
                $('#porcentaje').val("");
                receteaLabels();
            }
            break;
          }
        }
      });
    }
}

//REALIZA LOS CALCULOS DE LOS MONTOS, PERIODOS Y POCENTAJE
function calculos(){
    var saldo_inicial = $('#saldo_inicial').val();
    var periodos_fijos = $('#periodos_fijos').val();
    var monto_fijo = $('#monto_fijo').val();
    var porcentaje = $('#porcentaje').val();
    var saldo_actual_pesos = document.getElementById('saldo_actual_pesos');
    var pago_pesos = document.getElementById('pago_pesos');
    var periodos_restantes_entero = document.getElementById('periodos_restantes_entero');
    
    if (saldo_inicial !== "" && periodos_fijos !== "") {
        saldo_actual_pesos.textContent = saldo_inicial.toString();
        var pagos = saldo_inicial/periodos_fijos;
        pago_pesos.textContent = pagos.toFixed(2).toString();
        var periodos_restantes = saldo_inicial/pagos;
        var periodo_redondeado = Math.ceil(periodos_restantes);
        periodos_restantes_entero.textContent = periodo_redondeado.toString();
        
    }else if (saldo_inicial !== "" && monto_fijo !== "") {
        saldo_actual_pesos.textContent = saldo_inicial.toString();
        var periodos_restantes = saldo_inicial/monto_fijo;
        var periodo_redondeado = Math.ceil(periodos_restantes);
        periodos_restantes_entero.textContent = periodo_redondeado.toString();
        var pagos = saldo_inicial/periodos_restantes;
        pago_pesos.textContent = pagos.toFixed(2).toString();
    
     }else if (saldo_inicial !== "" && porcentaje !== "") {
        saldo_actual_pesos.textContent = saldo_inicial.toString();
        var pagos = saldo_inicial*(porcentaje/100);
        pago_pesos.textContent = pagos.toFixed(1).toString();
        var periodos_restantes = saldo_inicial/pagos;
        if (periodos_restantes % 1 !== 0) {
            var periodo_redondeado = Math.ceil(periodos_restantes);
            periodos_restantes_entero.textContent = periodo_redondeado.toString();
        }else{
            periodos_restantes_entero.textContent = periodos_restantes.toString();
        }
    }
    else{
        receteaLabels();
    }
  
}
//Asigna el valor por default a los labels
function receteaLabels(){
    var saldo_actual_pesos = document.getElementById('saldo_actual_pesos');
    var pago_pesos = document.getElementById('pago_pesos');
    var periodos_restantes_entero = document.getElementById('periodos_restantes_entero');
    
    saldo_actual_pesos.textContent = '0.00';
    pago_pesos.textContent = '0.00';
    periodos_restantes_entero.textContent = '0';
    
}

//Función para trae la nomina del trabajador
function obtenNomina(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPlazaTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            $('#id_nomina').val(data.data.cat_Tipo_Nomina.id_tipo_nomina);
            obtenPeriodos(data.data.cat_Tipo_Nomina.id_tipo_nomina);
            
        },
        error: function (e) {
            alert(e);
        }
    });
}
//Función para obtener los periodos de pago
function obtenPeriodos(id_nomina) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscaPeriodosFechas/" + id_nomina,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }else{
                
                var vselected = "";
                var compara_periodo = comparaFechas(data,vselected);
                    if (data.length > 0) {
                        for (var x = 0; x < data.length; x++) {
                            var n_periodo = data[x].periodo;
                            var fecha_inicial_formato = new Date(data[x].fecha_inicial);
                            var dias_inicial = fecha_inicial_formato.getDate();
                            var meses_inicial = fecha_inicial_formato.getMonth() + 1; 
                            var anios_inicial = fecha_inicial_formato.getFullYear(); 

                            var fecha_final_formato = new Date(data[x].fecha_final);
                            var dias_final = fecha_final_formato.getDate();
                            var meses_final = fecha_final_formato.getMonth() + 1; 
                            var anios_final = fecha_final_formato.getFullYear();
                            if (compara_periodo !== data[x].periodo) {
                                $('#periodo_aplicacion').append('<option value="' + data[x].id_periodo_pago + '"' + vselected + '>'+ 'P. '+ (n_periodo.toString()).substr(4,5) +" : "+ " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial + 
                                     '--' + dias_final + '/' + meses_final + '/' + anios_final +'</option>');
                        }
                                
                    }
                }
            }
        buscaPeriodoPago();
        },
        error: function (e) {
            alert(e);
        }
    });
}

// ASIGNA EL PERIODO CORRESPONDIENTE A LA FECHA DE SOLICITUD DEL DESCUENTO FONACOT
function comparaFechas(data,vselected){
    var hoy = new Date();
    hoy.setHours(0, 0, 0, 0);
    
    for (var i = 0; i < data.length; i++) {
        var n_periodo = data[i].periodo;
        var fecha_inicial_formato = new Date(data[i].fecha_inicial);
        var dias_inicial = fecha_inicial_formato.getDate();
        var meses_inicial = fecha_inicial_formato.getMonth() + 1; 
        var anios_inicial = fecha_inicial_formato.getFullYear(); 

        var fecha_final_formato = new Date(data[i].fecha_final);
        var dias_final = fecha_final_formato.getDate();
        var meses_final = fecha_final_formato.getMonth() + 1; 
        var anios_final = fecha_final_formato.getFullYear();
        fecha_inicial_formato.setHours(0,0,0,0);
        fecha_final_formato.setHours(0,0,0,0);
        var fecha_corte_formato = new Date(data[i].fecha_corte);
        fecha_corte_formato.setHours(0,0,0,0);
        
        if (hoy <= fecha_corte_formato) {
            
            $('#periodo_aplicacion').empty().append('<option value="' + data[i].id_periodo_pago + '"' + vselected + '>'+ 'P. '+ (n_periodo.toString()).substr(4,5) +" : "+ " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial + 
                                 '--' + dias_final + '/' + meses_final + '/' + anios_final +'</option>');
            return data[i].periodo;
            
        }else if(hoy > fecha_corte_formato){
            var contador = i + 1;
            n_periodo = data[contador].periodo;
            fecha_inicial_formato = new Date(data[contador].fecha_inicial);
            fecha_final_formato = new Date(data[contador].fecha_final);
            
            $('#periodo_aplicacion').empty().append('<option value="' + data[contador].id_periodo_pago + '"' + vselected + '>'+ 'P. '+ (n_periodo.toString()).substr(4,5) +" : "+ " " + fecha_inicial_formato.getDate() + '/' + (fecha_inicial_formato.getMonth()+1) + '/' + fecha_inicial_formato.getFullYear() + 
                                 '--' + fecha_final_formato.getDate() + '/' + (fecha_final_formato.getMonth()+1) + '/' + fecha_final_formato.getFullYear() +'</option>');
            
            return data[contador].periodo;
        }
    }
}

// BUSCA EL PERIODO DE PAGO PARA LLENAR LOS INPUTS QUE SE NECESITAN PARA GUARDAR LA INFORMACIÓN
function buscaPeriodoPago(){
    var periodo_id = $('#periodo_aplicacion').val();
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPeriodoPagoId/" + periodo_id,
        dataType: 'json',
        success: function (data) {
            $('#periodo_oculto').val(data.data.periodo); 
        },
        error: function (e) {
            alert(e);
        }
    });   
}

// BUSCA EL EXPEDIENTE DEL TRABAJADOR PARA LISTARLO EN LA TABLA
function buscaExpediente(trabajador_id){
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + trabajador_id,
        dataType: 'json',
        success: function (data) {
            $('#nombre').val(data.data.persona.nombre + " " + data.data.persona.apellido_paterno + " " +data.data.persona.apellido_materno);
            $('#expediente').val(data.data.cat_expediente.numero_expediente);
            
        },
        error: function (e) {
            alert(e);
        }
    });   
}
// BLOQUEA EL BOTÓN DE GUARDAR SI EXISTE UN REGISTRO ANTERIOR ACTIVO Y LLENA LOS CAMPOS DEL MODAL SALDAR CUENTA
function bloqueaBotonGuardar(id_trabajador){
    $.ajax({
        type: "GET",
        url: 'creditoFonacot/listarCuentaPorTrabajador/'+id_trabajador,
        dataType: 'json',
        success: function (data) {
            
            if (data !== undefined) {
                if (data[0].id_cuentas_cobrar !== null && data[0].estatus === 1) {
                    $('#id_cuenta_por_cobrar').val(data[0].id_cuentas_cobrar);
                    //LLENA MODAL SALDAR CUENTA
                    var saldo_inicial = data[0].saldo_inicial;
                    var saldo_inicial_saldar_cuenta = document.getElementById('saldo_inicial_saldar_cuenta');
                    saldo_inicial_saldar_cuenta.textContent = saldo_inicial;
                    
                    var monto_pago = data[0].monto_pago;
                    var importe_abono_saldar_cuenta = document.getElementById('importe_abono_saldar_cuenta');
                    importe_abono_saldar_cuenta.textContent = monto_pago;
                    
                    var saldo_actual = data[0].saldo_actual;
                    var saldo_actual_saldar_cuenta = document.getElementById('saldo_actual_saldar_cuenta');
                    saldo_actual_saldar_cuenta.textContent = saldo_actual;
                    
                    //Llena Inputs Saldo_inicial, Periodos y Monto
                    var periodos_pago = data[0].numero_periodos_pago;
                    //Si el calculo está equivocado, es por realizar pruebas de descuentos en el mismo periodo
                    //El periodo actual de descuento debe ser mayor que el periodo inicial
                    var nomina = data[0].cat_Tipo_Nomina.id_tipo_nomina;
                    var ultimo_periodo = 0;
                    if (nomina === 1 || nomina === 2 || nomina === 4) {
                        ultimo_periodo = 52;
                    }else{
                        ultimo_periodo = 24;
                    }
                    
                    let anio_pago_actual = data[0].periodo_pago_actual.toString().substring(0, 4);
                    let anio_periodo_inicial = data[0].periodo_inicial.toString().substring(0, 4);
                    let periodo_inicial = data[0].periodo_inicial.toString().substring(4, 6);
                    let periodo_actual = data[0].periodo_pago_actual.toString().substring(4, 6);
                    var periodos_restantes = 0;
                    if (anio_pago_actual !== anio_periodo_inicial) {
                        periodos_restantes = data[0].numero_periodos_pago - ((ultimo_periodo-periodo_inicial) + (+periodo_actual));
                    }else {
                        periodos_restantes = data[0].numero_periodos_pago - (data[0].periodo_pago_actual - data[0].periodo_inicial);
                    }
                    $('#saldo_inicial').val(saldo_inicial);
                    $('#periodos_fijos').val(periodos_pago);
                    document.getElementById('saldo_actual_pesos').textContent = saldo_actual;
                    document.getElementById('pago_pesos').textContent = monto_pago;
                    document.getElementById('periodos_restantes_entero').textContent =  periodos_restantes;
                    
                    
                    //BLOQUEA BOTONES
                    const boton_guardar = document.getElementById('botonGuardar');
                    boton_guardar.disabled = true;
                    const boton_saldar = document.getElementById('botonSaldar');
                    boton_saldar.disabled = false;
                    const boton_eliminar = document.getElementById('botonEliminar');
                    boton_eliminar.disabled = false;
                }  
                
            }else{
                const boton_guardar = document.getElementById('botonGuardar');
                boton_guardar.disabled = false;
                const boton_saldar = document.getElementById('botonSaldar');
                boton_saldar.disabled = true;
                const boton_eliminar = document.getElementById('botonEliminar');
                boton_eliminar.disabled = true;
            }
        },
        error: function (e) {
            alert(e);
        }
    });
}
//Cambia a 0 el estatus de la cuenta para que simule la eliminación
function eliminaCuenta(){
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    $.ajax({
        type: "POST",
        url: "creditoFonacot/estatusCuentaPorCobrar/" + trabajador_id,
        data: "trabajador_id=" + trabajador_id,
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                toastr['warning']("La cuenta se ha eliminado", "Aviso");
                $("#compararContraseniaModal").modal("hide");
                receteaLabels();
                $('#saldo_inicial').val('');
                $('#periodos_fijos').val('10');
                bloqueaBotonGuardar(trabajador_id);
            }
        }
    });
}
//Genera el registro de la cuenta saldada en la tabla "Registro Pagos Deudas"
$("#saldarCuentaModal").submit(function (e) {
    event.preventDefault();
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    var monto_pago = document.getElementById("importe_abono_saldar_cuenta").textContent;
    var saldo_actual = document.getElementById("saldo_actual_saldar_cuenta").textContent;
    var justificacion = $('#justificacion').val();
    var periodo_saldar_cuenta = $('#periodo_saldar_cuenta').val();
    var id_cuenta_por_cobrar =  $('#id_cuenta_por_cobrar').val();
    
    var datos = {"monto_pago": monto_pago, "trabajador_id": trabajador_id, "periodo_pago":periodo_saldar_cuenta, "saldo_deuda":saldo_actual, "cuentas_Por_Cobrar":{"id_cuentas_cobrar":id_cuenta_por_cobrar}, "justificacion":justificacion};
    
    $.ajax({
        type: "POST",
        url: "creditoFonacot/guardarRegistroPagosDeudas",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                toastr['warning']("La cuenta se ha Saldado", "Aviso");
                $("#saldarCuentaModal").modal("hide");
                actualizaEstatusCuentaSaldada(id_cuenta_por_cobrar,trabajador_id);
                
            }
        }
    });
});
//Cambia estatus a 4, para designar la cuenta saldada
function actualizaEstatusCuentaSaldada(id_cuenta_por_cobrar,trabajador_id){
    $.ajax({
        type: "POST",
        url: "creditoFonacot/estatusCuentaSaldada/" + id_cuenta_por_cobrar,
        data: "trabajador_id=" + id_cuenta_por_cobrar,
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                toastr['success']("Se actualizó cuentas por cobrar", "Aviso");
                receteaLabels();
                $('#saldo_inicial').val('');
                $('#periodos_fijos').val('10');
                bloqueaBotonGuardar(trabajador_id);
            }
        }
    });
}



// LIMITA LA CANTIDAD DE DINERO INGRESADA EN LOS INPUTS
function validacionPesos(object){;
    if (object.value.includes(".")){
        var indice = object.value.indexOf(".");
        object.value = object.value.slice(0,  indice+3);
    }
    else{
        if (object.value.length > 6){
            object.value = object.value.slice(0,  6);
        }
    }
}
// LIMITA LA CANTIDAD DE PERIODOS/PORCENTAJE INGRESADA EN LOS INPUTS
function validacionNumero(object){;
    
    var nuevo_numero = object.value.substring(0, 2);
    object.value = nuevo_numero;
}

function validacionPeriodo(object){;
    
    var nuevo_numero = object.value.substring(0, 6);
    object.value = nuevo_numero;
}

function fonacotAdmin() {
    window.location.href = 'fonacotAdmin';
}

