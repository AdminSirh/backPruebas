document.addEventListener('DOMContentLoaded', () => {
    listarNominas(0);
    
});
let totalTrabajadores = 0;
let progresoActual = 0;
//Llena el select de las diferentes nominas que hay excepto Estructura "5"
function listarNominas(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
    success: function (data) {
            if ($.isEmptyObject(data)) {
            toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#tipo_nomina").empty().append("<option value=''>* Nomina </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        if (data[x].id_tipo_nomina!==5) {
                            vselected = (data[x].id_tipo_nomina === id) ? vselected = "selected" : vselected = " ";
                            $("#tipo_nomina").append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                        }
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Nómina: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//LISTA LOS PERIODOS DE VALES DE DESPENSA DEL AÑO ACTUAL
function listarPeriodos() {
    var id_nomina = $("#tipo_nomina").val();
    $.ajax({
        type: "GET",
        url: "vales/buscarPeriodosVales/" + id_nomina,
        dataType: 'json',
    success: function (data) {
        if ($.isEmptyObject(data)) {
            toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#periodo").empty().append("<option value=''>* Periodos </option> ");
                var vselected = "";
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
                                $('#periodo').append('<option value="' + data[x].id_periodo_pago + '"' + vselected + '>'+ 'P. '+ (n_periodo.toString()).substr(4,5) +" : "+ " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial + 
                                     '--' + dias_final + '/' + meses_final + '/' + anios_final +'</option>'); 
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar la Nómina", {progressBar: true, closeButton: true});
        }
    });
}

//OBTIENE EL PERIODO DE PAGO POR ID PARA ASIGNAR EL 91 A LOS NIVELES 26 O MENOS Y EL 93 A LOS NIVELES 27
function obtenPeriodoPago(){
    var id_periodo = $("#periodo").val();
    var id_nomina = $("#tipo_nomina").val();
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPeriodoPagoId/" + id_periodo,
        dataType: 'json',
        success: function (data) {
            var anio = new Date().getFullYear();
            var periodo_91 = anio+'91';
            var periodo_93 = anio+'93';
            if (data.data.periodo === parseInt(periodo_91)) {
                obtenTrabajadoresNivel26(id_nomina, data.data.fecha_inicial, data.data.fecha_final, periodo_91);
            }else if(data.data.periodo === parseInt(periodo_93)){
                obtenTrabajadoresNivel27(id_nomina, data.data.fecha_inicial, data.data.fecha_final, periodo_93);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Periodo", {progressBar: true, closeButton: true});
        }
    });
}

//BUSCA LOS TRABAJADORES POR NOMINA Y NIVELES 26 O MENORES PARA INVOCAR FUNCION QUE OBTIENE INCIDENCIAS POR TRABAJADOR EN BUCLE
function obtenTrabajadoresNivel26(id_nomina, fecha_inicial, fecha_final, periodo) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajadorNominaNivel26/" + id_nomina,
        dataType: 'json',
        success: function (data) {
            totalTrabajadores += data.length; // Actualizar el total de trabajadores
            for (let a = 0; a < data.length; a++) {
                obtenIncidenciasPeriodoVales(data[a].trabajador_id, new Date(fecha_inicial), new Date(fecha_final), periodo);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los trabajadores 26", {progressBar: true, closeButton: true});
        }
    });
}

//BUSCA LOS TRABAJADORES POR NOMINA Y NIVELES 27 PARA INVOCAR FUNCION QUE OBTIENE INCIDENCIAS POR TRABAJADOR EN BUCLE
function obtenTrabajadoresNivel27(id_nomina, fecha_inicial, fecha_final, periodo) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajadorNominaNivel27/" + id_nomina,
        dataType: 'json',
        success: function (data) {
            totalTrabajadores += data.length; // Actualizar el total de trabajadores
            for (let a = 0; a < data.length; a++) {
                obtenIncidenciasPeriodoVales(data[a].trabajador_id, new Date(fecha_inicial), new Date(fecha_final), periodo);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los trabajadores 27", {progressBar: true, closeButton: true});
        }
    });
}
//BUSCA LA CANTIDAD DE INCIDENCIAS POR TRABAJADOR PARA RESTARLAS A LA CANTIDAD DE DIAS PAGADOS Y OBTENER POSTERIORMENTE EL MONTO CORRESPONDIENTE POR TRABAJADOR
function obtenIncidenciasPeriodoVales(id_trabajador,fecha_inicial,fecha_fin, periodo){
    //SI LA FECHA DE INGRESO DEL TRABAJADOR ES DEL AÑO EN CURSO, REALIZAR EL CALCULO CON LOS DIAS DEL AÑO QUE LLEVA LABORADOS
    //SI LA FECHA DE INGRESO ES DE AÑOS ANTERIORES AL ACTUAL, SUS DIAS PAGADOS SON 334
    var anio_actual = new Date().getFullYear();
    $.ajax({
        type: "GET",
        url: "incidencias/buscarIncidenciasPeriodoVales/" + id_trabajador + "/" + fecha_inicial + "/" + fecha_fin,
        dataType: 'json',
    success: function (data1) {
            var total_incidencias = data1;
            var dias_trabajados = 0;
            $.ajax({
                type: "GET",
                url: "trabajador/buscarTrabajador/" + id_trabajador,
                dataType: 'json',
            success: function (data2) {
                var fecha_ingreso = new Date(data2.data.fecha_ingreso); 
                var anio_ingreso = fecha_ingreso.getFullYear();
                
                // Calcular la diferencia en milisegundos (sin ajustar las horas)
                var diferenciaEnMilisegundos = fecha_fin-fecha_ingreso;
                // Convertir la diferencia a días
                var diferenciaEnDias = Math.round(diferenciaEnMilisegundos / (1000 * 60 * 60 * 24));
                //SI EL AÑO DE INGRESO ES DIFERENTE AL AÑO ACTUAL SIGNIFICA QUE TIENE TODOS LOS DÍAS DE VALES
                if (anio_ingreso !== anio_actual) {
                    dias_trabajados = 334 - total_incidencias;
                }
                //SI EL AÑO DE INGRESO ES IGUAL AL AÑO ACTUAL SIGNIFICA QUE TIENE MENOS DE UN AÑO DE ANTIGUEDAD Y TENEMOS QUE CALCULAR LOS DÍAS
                else{
                    dias_trabajados = diferenciaEnDias - total_incidencias;
                }
                obtenMonto(dias_trabajados,id_trabajador,periodo,anio_actual,total_incidencias);
            },
                error: function (e) {
                    toastr["warning"]("Error al recuperar la fecha de ingreso", {progressBar: true, closeButton: true});
                }
            });
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar las incidencias por trabajador", {progressBar: true, closeButton: true});
        }
    });
}

//OBTIENE EL MONTO CORRESPONDIENTE A LOS DÍAS TRABAJADOS POR TRABAJADOR Y LO CLASIFICA EN BASE O CONFIANZA PARA ASIGNARLE EL MONTO
function obtenMonto(dias_trabajados,id_trabajador,periodo,anio_actual,total_incidencias){
    $.ajax({
        type: "GET",
        url: "vales/buscarMontoDias/" + dias_trabajados,
        dataType: 'json',
        success: function (data1) {
            var monto = 0;
            $.ajax({
                type: "GET",
                url: "trabajador/buscarPlazaTrabajador/" + id_trabajador,
                dataType: 'json',
                success: function (data2) {
                    if (data2.data.cat_Tipo_Nomina.cat_Tipo_Tabulador.clave_tipo_tabulador === "B") {
                        monto = data1.monto_base;
                    }else if(data2.data.cat_Tipo_Nomina.cat_Tipo_Tabulador.clave_tipo_tabulador === "C"){
                        monto = data1.monto_confianza;
                    }
                    guardaImporteVales(id_trabajador,monto,periodo,anio_actual,dias_trabajados,total_incidencias);
                },
                error: function (e) {
                    toastr["warning"]("Error al recuperar el monto", {progressBar: true, closeButton: true});
                }
            });
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los trabajadores 27", {progressBar: true, closeButton: true});
        }
    });
}

//GUARDA LA INCIDENCIA 2 QUE ES DIAS LABORADOS EN LA TABLA TMP MOVIMIENTOS
function guardaDiasLaborados(id_trabajador,periodo,anio_actual,dias_trabajados,total_incidencias){
    var id_nomina = $("#tipo_nomina").val();
    
    var datos = {"anio_aplicacion":anio_actual, "periodo_aplicacion": periodo, "periodo_generacion": periodo,trabajador:{"id_trabajador":id_trabajador}, 
                cat_Tipo_Nomina:{"id_tipo_nomina":id_nomina}, "valor_1":dias_trabajados};
    
    $.ajax({
        type: "POST",
        url: "vales/guardarMovimientos2",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            $("#confirmacionModal").modal('hide');
            guardaIncidencias(id_trabajador,periodo,anio_actual,total_incidencias);
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
}
//GUARDA LA INCIDENCIA 244 QUE ES INCIDENCIAS EN LA TABLA TMP MOVIMIENTOS
function guardaIncidencias(id_trabajador,periodo,anio_actual,total_incidencias){
    var id_nomina = $("#tipo_nomina").val();
    
    var datos = {"anio_aplicacion":anio_actual, "periodo_aplicacion": periodo, "periodo_generacion": periodo,trabajador:{"id_trabajador":id_trabajador}, 
                cat_Tipo_Nomina:{"id_tipo_nomina":id_nomina}, "valor_1":total_incidencias};
    
    $.ajax({
        type: "POST",
        url: "vales/guardarMovimientos244",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
}
//GUARDA LA INCIDENCIA 249 QUE ES LOS VALES DE DESPENSA EN LA TABLA TMP MOVIMIENTOS
function guardaImporteVales(id_trabajador, monto, periodo, anio_actual, dias_trabajados, total_incidencias) {
    var id_nomina = $("#tipo_nomina").val();
    
    var datos = {"anio_aplicacion": anio_actual, "periodo_aplicacion": periodo, "periodo_generacion": periodo, trabajador: {"id_trabajador": id_trabajador}, 
                 cat_Tipo_Nomina: {"id_tipo_nomina": id_nomina}, "valor_1": monto};
    
    $.ajax({
        type: "POST",
        url: "vales/guardarMovimientos249",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            guardaDiasLaborados(id_trabajador, periodo, anio_actual, dias_trabajados, total_incidencias);

            progresoActual++;
            let porcentajeProgreso = (progresoActual / totalTrabajadores) * 100;
            $('#barraProgreso').css('width', porcentajeProgreso + '%').attr('aria-valuenow', porcentajeProgreso);
            $('#barraProgreso').text(Math.round(porcentajeProgreso) + '%');
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
}

//MENSAJE DE CONFIRMACION PARA GENERAR LOS DESCUENTOS DE FONDO DE VALES
function confirmacion() {
    if ($("#tipo_nomina").val() !== "" && $("#periodo").val() !== "" ) {
        $("#confirmacionModal").modal();
    }else{
        toastr['warning']("Seleccione una nómina y periodo");
    }
}
//LIMPIA LA BARRA DE CARGA CUANDO CAMBIA LA NOMINA DEL SELECT
function limpiarBarraDeCarga() {
    $("#barraProgreso").text('0%');
    $("#barraProgreso").css('width', '0%');
    $("#barraProgreso").removeClass("progress-bar-success progress-bar-danger"); // Elimina cualquier clase de éxito o error
}
