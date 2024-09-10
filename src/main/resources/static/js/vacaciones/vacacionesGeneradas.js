/* global moment, Intl */
document.addEventListener('DOMContentLoaded', () => {
 
});


/*=============================================
 BUSCAR TRABAJADOR POR EL ID TRABAJADOR
 =============================================*/
function buscarTrabajador(id_trabajador) {
    
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            $('#expediente_id').val(data.data.cat_expediente.numero_expediente);
            var fecha_antiguedad = new Date(data.data.fecha_antiguedad);
            $('#fecha_antiguedad').val(fecha_antiguedad.getUTCDate()+"/"+(fecha_antiguedad.getUTCMonth()+1)+"/"+fecha_antiguedad.getUTCFullYear());
            var fecha_ingreso = new Date(data.data.fecha_ingreso);
            $('#fecha_ingreso').val(fecha_ingreso.getUTCDate()+"/"+(fecha_ingreso.getMonth()+1)+"/"+fecha_ingreso.getFullYear());
            var hoy = new Date(); 
            var tiempoTranscurrido = hoy.getTime() - fecha_antiguedad.getTime(); 
            var milisegundosPorDia = 1000 * 60 * 60 * 24;
            var milisegundosPorMes = milisegundosPorDia * 30.4375;
            var milisegundosPorAnio = milisegundosPorDia * 365.25; 

            var anios = Math.floor(tiempoTranscurrido / milisegundosPorAnio);
            var tiempoRestante = tiempoTranscurrido % milisegundosPorAnio;
            var meses = Math.floor(tiempoRestante / milisegundosPorMes);
            tiempoRestante = tiempoRestante % milisegundosPorMes;
            var dias = Math.floor(tiempoRestante / milisegundosPorDia);
            
            $('#anios').val(anios);
            $('#meses').val(meses);
            $('#dias').val(dias);
            if(meses >= 6 && dias > 0) {
                $('#periodo_vacacional').val(anios+0.5);
            }else{
                $('#periodo_vacacional').val(anios);
            }
            
            if (($("#tipo_nomina").val() === "3" || $("#trabajador_prestaciones_incidencias").val() === "2") || $("#tipo_nomina").val() === "5") {
                var fecha_inicio_periodo = new Date(fecha_ingreso);
                fecha_inicio_periodo.setMonth(fecha_inicio_periodo.getMonth() + 6); // Agregar 6 meses
                fecha_inicio_periodo.setFullYear(hoy.getFullYear());
                $('#fecha_inicio_anio_periodo_vacacional').val(fecha_inicio_periodo.getUTCDate() + "/" + (fecha_inicio_periodo.getUTCMonth() + 1) + "/" + fecha_inicio_periodo.getUTCFullYear());
                var fecha_fin_periodo = new Date(hoy.getFullYear() + 1, fecha_ingreso.getMonth(), fecha_ingreso.getDate());
                $('#fecha_fin_anio_periodo_vacacional').val(fecha_fin_periodo.getUTCDate() + "/" + (fecha_fin_periodo.getUTCMonth() + 1) + "/" + fecha_fin_periodo.getUTCFullYear());
            }else{
                fecha_antiguedad.setFullYear(hoy.getFullYear());
                var fecha_inicio_periodo = fecha_antiguedad;
                $('#fecha_inicio_anio_periodo_vacacional').val(fecha_inicio_periodo.getUTCDate() + "/" + (fecha_inicio_periodo.getUTCMonth() + 1) + "/" + fecha_inicio_periodo.getUTCFullYear());
                var fecha_fin_periodo = new Date(fecha_inicio_periodo); // Clonamos la fecha de inicio
                fecha_fin_periodo.setFullYear(fecha_fin_periodo.getFullYear() + 1); // Añadimos un año
                $('#fecha_fin_anio_periodo_vacacional').val(fecha_fin_periodo.getUTCDate() + "/" + (fecha_fin_periodo.getUTCMonth() + 1) + "/" + fecha_fin_periodo.getUTCFullYear());
            }
            //Obtener los días del periodo actual o null 
            buscaUltimoRegistro(id_trabajador);
            
        },
        error: function (e) {
            alert(e);
        }
    });
}


/*=============================================
 BUSCA EL ULTIMO REGISTRO EN ESE PERIODO, DEL TRABAJADOR
 =============================================*/
function buscaUltimoRegistro(trabajador_id){
    var periodo_vacacional = $('#periodo_vacacional').val();
    
    $.ajax({
        type: "GET",
        url: "incidencias/buscaPeriodoVacacional/"+periodo_vacacional+"/"+trabajador_id,
        dataType: 'json',
        success: function (data) {
            $('#registro_periodo').val(String(data.data));
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar el registro" + e);
        }
    });
}

//Limpieza de agrega
$('#cancelarVacacionesModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#motivo_cancelacion").val("");
   $("#incidencia_cancelada").val("");
   $("#referencia_cancelacion").val("");
}); 


/*=============================================
 BUSCAR EL CONTRATO DEL TRABAJADOR
 =============================================*/
function buscarContrato(id_trabajador){
     $.ajax({
        type: "GET",
        url: "trabajador/buscarPlazaTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            $('#tipo_contrato_id').val(data.data.cat_tipo_contrato.id_tipo_contrato);
            $('#tipo_contrato').val(data.data.cat_tipo_contrato.tipo_nomina);
            $('#tipo_nomina').val(data.data.cat_Tipo_Nomina.id_tipo_nomina);
            buscaDiasFestivos(data.data.cat_Tipo_Nomina.id_tipo_nomina);
            buscaDiasPaseos(id_trabajador);
        },
        error: function (e) {
            alert(e);
        }
    });
    
}
/*=============================================
 BUSCAR LOS DÍAS FESTIVOS DE LA NOMINA
 =============================================*/
function buscaDiasFestivos(nomina_id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarDiasNomina/" + nomina_id,
        dataType: 'json',
        success: function (data) {
            var dias_festivos = [];
            for (var i = 0; i < data.length; i++) {
                const fechaPartes = data[i].fecha.split('-'); // Divide la cadena de fecha en partes
                const anio = parseInt(fechaPartes[0]);
                const mes = parseInt(fechaPartes[1]) - 1; // Los meses en JavaScript son 0-indexados
                const dia = parseInt(fechaPartes[2]);
                const fecha = new Date(anio, mes, dia, 12, 0, 0); // Establece la hora a mediodía
                fecha.setHours(0,0,0,0);
                dias_festivos.push(fecha);
            }
            
            $('#arreglo_dias_festivos').val(dias_festivos);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Genera Pago: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 BUSCAR LOS DÍAS FESTIVOS DE LA NOMINA
 =============================================*/
function buscaDiasPaseos(trabajador_id) {
    $.ajax({
        type: "GET",
        url: "horarios/buscarDiasTrabajador/"+trabajador_id,
        dataType: 'json',
        success: function (data) {
            var dias_habiles = [];
            var dias_paseos = [];
            for (var i = 0; i < data.length; i++) {
                
                //Dia descanso 0 si es su dia habil
                if (data[i].dia_descanso === 0) {
                    dias_habiles.push(data[i].cat_dias.id_dia);
                }//Día Descanso 1 si es su paseo
                else if(data[i].dia_descanso === 1){
                    dias_paseos.push(data[i].cat_dias.id_dia);
                }
            }
            $('#arreglo_dias_habiles').val(dias_habiles);
            $('#arreglo_dias_paseos').val(dias_paseos);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Los Días y Paseos: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 LISTAR COMBO DE GENERA PAGO SI NO
 =============================================*/
function generaPago(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarSi_No",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#genera_pago').empty().append("<option value=''>* Genera Pago </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id === id) ? vselected = "selected" : vselected = " ";
                        $('#genera_pago').append('<option value="' + data[x].id + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Genera Pago: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


/*=============================================
 OBTIENE LA INFORMACION DE LAS PRESTACIONES DEL TRABAJADOR
 =============================================*/
function obtenPrestaciones(id_trabajador){
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            $('#trabajador_prestaciones').val(data.data.prestaciones_si_no);
    },
        error: function (e) {
            toastr["warning"]("Error al recuperar las prestaciones");
        }
    });
    
}

//*=============================================
// CALCULA LOS DIAS  DE VACACIONES CORRESPONDIENTES A CADA TRABAJADOR
// =============================================*/
function calculaDias(){
    var tipo_contrato = $('#tipo_contrato_id').val();
    var prestaciones = $('#trabajador_prestaciones').val();
    var registro_periodo = $('#registro_periodo').val();
    var tipo_dias_vacaciones_id = 0;
    var antiguedad = $('#anios').val();
    var meses = $('#meses').val();
    var dias_vacaciones = $('#dias_vacaciones').val();
    if (antiguedad === "0") {
        antiguedad = meses/12;
    }
    if (registro_periodo === "null" || registro_periodo === "") {
        
        if((tipo_contrato === "2" && prestaciones === "2")||(tipo_contrato=== "3" && prestaciones === "2")){
            var dias_correspondientes = 10;
            var dias_disponibles = dias_correspondientes - dias_vacaciones;
            $('#dias_disponibles').val(dias_disponibles);
            if (dias_disponibles < 0) {
                toastr['error']("No tienes los suficientes días");
                limpiaCampos();
            }
            
        }else if(tipo_contrato === "1" || (tipo_contrato === "2" && prestaciones === "1") || tipo_contrato === "4" || tipo_contrato === "5" || tipo_contrato === "6" || tipo_contrato === "7"){
            
            tipo_dias_vacaciones_id = 1;
            if (meses <6) {
                diasTabulador(tipo_dias_vacaciones_id,antiguedad,dias_vacaciones);
            }
            else{
                toastr['error']("Ya no es posible tomar tus vacaciones");
                limpiaCampos();
            }
                
            
        }else if(tipo_contrato === "3" && prestaciones === "1"){
            tipo_dias_vacaciones_id = 2;
            diasTabulador(tipo_dias_vacaciones_id,antiguedad,dias_vacaciones);
            
        }
        
    }else{
        var dias_disponibles = registro_periodo-dias_vacaciones;
        $('#dias_disponibles').val(dias_disponibles);
        if (dias_disponibles < 0) {
            toastr['error']("No tienes los suficientes días");
            limpiaCampos();
        }
    }
}

//Obten Periodo de Pago, en una Funcion oninput, hacer controlador y consulta

function obtenPeriodoPago(){
    if ($("#cmbInc").val()=== "7") {
        var fecha_inicio = $('#fecha_inicio').val();
        var tipo_nomina = $('#tipo_nomina').val();

        $.ajax({
            type: "GET",
            url: "trabajador/buscaPeriodoPago/" +tipo_nomina + "/" + fecha_inicio,
            dataType: 'json',
            success: function (data) {
                $('#periodo_aplicacion').val(data.data);

            },error: function () {
                toastr['error']("Error al encontrar periodo de pago");
            }
        });
    }
}


/*=============================================
 OBTIENE LOS DIAS DE VACACIONES CON BASE EN LAS FECHAS INGRESADAS
 =============================================*/
function calculaVacaciones(){
    if ($('#fecha_inicio').val() !== "" && $('#fecha_fin').val() !== "") {
        var fecha_inicio = moment($('#fecha_inicio').val());
        var fecha_fin = moment($('#fecha_fin').val());
        let dias_paseos_contador = 0;
        let dias_vacaciones = 0;
        var diasFestivosEnEstePeriodo = 0;
        var validacion = fecha_fin.diff(fecha_inicio, 'days'); //Esta Validación funciona para que la fecha inicio no sea mayor a la fecha final y evitar errores 
        var tipo_contrato = $('#tipo_contrato').val();  //Obtiene el tipo de contrato del trabajador
        var p = $('#arreglo_dias_paseos').val(); //Obtiene en String los paseos registrados del trabajador por indice
        var paseos = p.split(","); //Separa el arreglo de los paseos por comas
        var h = $('#arreglo_dias_habiles').val();//Obtiene en String los dias habiles registrados del trabajador por indice
        var habiles = h.split(",");//Separa el arreglo de los dias habiles por comas
        var f = $('#arreglo_dias_festivos').val();//Obtiene en String los dias festivos en formato de fecha registrados por nomina
        var festivos = f.split(",");//Separa el arreglo de los dias festivos por comas
        if ($("#cmbInc").val() === "7") {
            ultimaFechaVacaciones();
        }
        if (validacion < 0) {
            toastr['error']("Las Fechas Ingresadas son incorrectas, ingrese otras");
            limpiaCampos();
        }else {
            for (fecha_inicio; fecha_inicio <= fecha_fin; fecha_inicio.add(1, 'days')) { //Ciclo For para recorrer los días uno por uno desde la fecha inicio hasta fecha final
                const dia_semana = fecha_inicio.day() + 1; //Obtiene el indice del día de la semana recorrido, sumandole 1 para que corresponda con los indices de los días de la Base D
                const dia_formato_fecha = new Date(fecha_inicio); //Obtiene el día recorrido en formato de fecha ej:Lunes Agosto 4 del 2023
                //Confirma si dentro de los días festivos se encuentra el día recorrido en formato fecha
                if(festivos.includes(dia_formato_fecha.toString()) && $("#cmbInc").val()=== "7"){
                    //Si es el caso, aumenta en 1 el contador de dias festivos
                    diasFestivosEnEstePeriodo++;
                }
                //Confirma si dentro de los paseos se encuentra el día de la semana recorrido Ej: Día 5 dentro de Paseos [0,5]
                if (paseos.includes(dia_semana.toString())) {
                    //Si es el caso, aumenta en 1 el contador de paseos
                    dias_paseos_contador++;
                }
                //Confirma si dentro de los habiles se encuentra el día de la semana recorrido Ej: Día 3 dentro de Paseos [1,2,3,4]
                else if(habiles.includes(dia_semana.toString())){
                    //Si es el caso, aumenta en 1 el contador de vacaciones
                    dias_vacaciones++;
                }
            }
            //Confirma si se encontró algún día festivo en el periodo seleccionado de fecha inicio y fecha_fin
            if (diasFestivosEnEstePeriodo !== 0) {
                //Si es el caso, invoca la función ajusteDiasFestivosPeriodo y la retorna en dos variables
                var ajuste = ajusteDiasFestivosPeriodo(diasFestivosEnEstePeriodo,dias_paseos_contador,fecha_fin,habiles,paseos);
                toastr['error']("Existen días festivos en tu periodo");
                //Separa la fecha que devuelve el ajuste en año, mes y dia
                const year = ajuste.fecha_final.getFullYear();
                const month = String(ajuste.fecha_final.getMonth() + 1).padStart(2, '0'); // Sumar 1 al mes, ya que en JavaScript los meses van de 0 a 11
                const day = String(ajuste.fecha_final.getDate()).padStart(2, '0');

                // Construir la fecha en el formato requerido
                const fecha_final_Convertida = `${year}-${month}-${day}`;
                //Asiga al input tipo Date la fecha convertida en el formato correcto
                $('#fecha_fin').val(fecha_final_Convertida);
                //Asigna un valor nuevo al contador de paseos, que es uno de los valores que retornó el ajuste
                dias_paseos_contador = ajuste.paseos_acumulados;
            }
            
            //Si el tipo de contrato es Base o Provisional Base, manda los paseos cargados para que se sumen en los días totales y tenga efectos de pago
            if (tipo_contrato === "Base" || tipo_contrato === "Provisional Base") {
                $('#dias_paseos').val(dias_paseos_contador);
            }
            //Si el contrato es de Confiaza o derivados asigna en 0 a los dias de paseo, pues a ellos no se le pagan dichos días
            else {
                $('#dias_paseos').val(dias_paseos_contador);
                dias_paseos_contador = 0;
            }
            
            var dias_totales = dias_vacaciones + dias_paseos_contador;//Los días totales es la suma de los días de vacaciones mas los paseos (Cambia dependiendo el contrato)
            $('#dias_vacaciones').val(dias_vacaciones); 
            $('#dias_totales').val(dias_totales);
            $('#dias_proporcionales').val(dias_totales);
            $('#num_dias').val(dias_vacaciones);
            if ($('#num_dias').val() !== null) {
                obtenPlazaTrabajador($('#num_dias').val());
            }
            
            if ($('#periodo_vacacional').val() !== "0") {//Verifica el periodo es el correcto para que puedas tomar vacaciones
                calculaDias();
                
            }else{
                toastr['error']("Aún no puedes tomar Vacaciones");//Si no lo es, limpia los campos
                limpiaCampos();
            }
            
            
        }
    }
}

/*=============================================
 ACTUALIZA LOS DATOS EN CASO QUE EL PERIODO VACACIONAL CONTENGA DIAS FESTIVOS
 =============================================*/
function ajusteDiasFestivosPeriodo(diasFestivosEnEstePeriodo, dias_paseos_contador, fecha_fin, habiles, paseos) {
    var dias_festivos_pagados = 0;
    var fecha_final_nueva = new Date(fecha_fin);//Regresa la fecha fin a formato Date
    const fecha_final_aux = new Date(fecha_fin);//Crea una variable con el mismo valor de fecha fin para recorrer el ciclo
    var dia_semana = fecha_final_nueva.getDay() + 1; //Avanza en uno el dia de la semana de la fecha fin
    
    //Inicia el ciclo While siempre que la fecha final nueva sea menor o igual a la fecha final aux
    while (fecha_final_nueva <= fecha_final_aux) {
        if (paseos.includes(dia_semana.toString())) { //Confirma si dentro de los paseos se encuentra el día de la semana recorrido Ej: Día 5 dentro de Paseos [0,5]
            fecha_final_aux.setDate(fecha_final_aux.getDate() + 1);//Si es el caso, aumenta en uno la fecha auxiliar para que vuelva a entrar al ciclo
            dia_semana = fecha_final_aux.getDay() + 1;//Aumenta en uno el día de la semana para preguntar por el siguiente día
            dias_paseos_contador ++; //Aumenta en uno el contador de los paseos
        }else if (habiles.includes(dia_semana.toString()) && dias_festivos_pagados<diasFestivosEnEstePeriodo) {//Confirma si dentro de los habiles se encuentra el día de la semana recorrido y si se le deben días festivos para recorrer
            dias_festivos_pagados++;//Aumenta en uno el contador lo que limitaria los dias que se van a recorrer
            fecha_final_aux.setDate(fecha_final_aux.getDate() + 1); //Si es el caso, aumenta en uno la fecha auxiliar para que vuelva a entrar al ciclo
            dia_semana = fecha_final_aux.getDay() + 1;//Aumenta en uno el día de la semana para preguntar por el siguiente día
            dias_paseos_contador ++;//Aumenta en uno el contador de los paseos
        }
        
        fecha_final_nueva.setDate(fecha_final_nueva.getDate() + 1);
        dia_semana = fecha_final_nueva.getDay() + 1;
    }
    
    return {fecha_final: fecha_final_aux, paseos_acumulados:dias_paseos_contador};
}

/*=============================================
 CALCULA LOS DIAS DE TRABAJADORES DE BASE Y ESTRUCTURA c/PRESTACIONES
 =============================================*/
function diasTabulador(tipo_dias_vacaciones_id,antiguedad,dias_vacaciones){
    $.ajax({
        type: "GET",
        url: "catalogos/listarDiasDisponibles/" +tipo_dias_vacaciones_id + "/" + antiguedad,
        dataType: 'json',
        success: function (data) {
            var dias_correspondientes_estructura = data.data;
            var dias_disponibles = dias_correspondientes_estructura - dias_vacaciones;
            $('#dias_disponibles').val(dias_disponibles);
            if(dias_disponibles < 0) {
                toastr['error']("No tienes los suficientes días");
                limpiaCampos();
            }
        },error: function () {
            toastr['error']("Aún no tienes días disponibles");
        }
    });
}
//Obtiene la ultima fecha del periodo que tomó vacaciones
function ultimaFechaVacaciones(){
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');

    $.ajax({
        type: "GET",
        url: "incidencias/buscaUltimaFechaVacaciones/" + id_trabajador ,
        dataType: 'json',
        success: function (data) {
            $("#fecha_ultima_vacacion").val(data.data);
            faltasEnPeriodo(id_trabajador);
        },error: function () {
            toastr['error']("Error al contar las faltas");
        }
    });
    
}
//Obtiene las faltas en el periodo de sus vacaciones anteriores
function faltasEnPeriodo(id_trabajador){
    var fecha_ultima_vacacion = $("#fecha_ultima_vacacion").val();
    var fecha_inicio = new Date($('#fecha_inicio').val());
    var fecha_string = $('#fecha_ingreso').val(); 
    var partes = fecha_string.split('/');
    var dia = parseInt(partes[0], 10);
    var mes = parseInt(partes[1], 10) - 1; // Restamos 1 ya que en JavaScript los meses van de 0 a 11
    var anio = parseInt(partes[2], 10);

    // Crear la fecha
    var fecha_ingreso = new Date(anio, mes, dia);
    var tipo_nomina = $("#tipo_nomina").val();
    var prestaciones = $("#trabajador_prestaciones_incidencias").val();
    var periodo_uno;
    var periodo_dos;
    var hoy = new Date();
    if ((tipo_nomina === "3" || prestaciones === "2") || tipo_nomina === "5") {
        
        periodo_uno = new Date (fecha_ingreso.setFullYear(hoy.getFullYear()));
        periodo_dos = new Date(fecha_ingreso);
        periodo_dos.setMonth(periodo_dos.getMonth() + 6);
        periodo_dos.setFullYear(hoy.getFullYear());
        if (hoy < periodo_uno) {
            fecha_ultima_vacacion = new Date (periodo_dos.setFullYear(hoy.getFullYear() - 1));
        }else if (hoy >= periodo_uno && hoy < periodo_dos) {
            fecha_ultima_vacacion = periodo_uno;
        } else {
            fecha_ultima_vacacion = periodo_dos;
        }
    }
    $.ajax({
        type: "GET",
        url: "incidencias/buscarFaltasPeriodo/" + id_trabajador + "/" + new Date(fecha_ultima_vacacion) + "/" + fecha_inicio,
        dataType: 'json',
        success: function (data) {
            if (data !== undefined) {
                var cadena = data.map(function(item) {
                if (item[1] !== 0) { // Verificar si el segundo elemento no es igual a 0
                    var fecha = new Date(item[2]);
                    var mes = ('0' + (fecha.getMonth() + 1)).slice(-2); // obtener mes con dos dígitos
                    return item[0] + ' : ' + item[1] + ' : ' + fecha.getFullYear() + '-' + mes;
                    }
                }).filter(Boolean).join(', ');
                $('#observaciones').val(cadena);
            }
        },error: function () {
            toastr['error']("Error al contar las faltas");
        }
    });
}

function limpiaCampos(){
    $('#fecha_inicio').val("");
    $('#fecha_fin').val("");
    $('#dias_vacaciones').val("");
    $('#dias_paseos').val("");
    $('#dias_totales').val("");
    $('#dias_disponibles').val("");
    $('#num_dias').val("");
    $('#monto').val("");
    $('#folio').val("");
    $('#bis').val("");
    $('#observaciones').val("");

}

function adminVacaciones() {
    window.location.href = 'incidenciasAdmin';
}

