document.addEventListener('DOMContentLoaded', () => {
    listarNominas(0);
    bloquearTodas();
});

//Llena el select de las diferentes nominas 
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
                        vselected = (data[x].id_tipo_nomina === id) ? vselected = "selected" : vselected = " ";
                        $("#tipo_nomina").append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Nómina: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

// OBTEN LOS PERIODOS DE PAGO DE AGUINALDO
function periodosAguinaldo(){
    var nomina = $("#tipo_nomina").val();
    $.ajax({
        type: "GET",
        url: "periodosPago/listarPeriodosAguinaldo/" + nomina,
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
                        $("#periodo").append('<option value="' + data[x].periodo + '"' + '>'+ "P. " + data[x].periodo + " : " + fecha_inicial_formateada + "-" + fecha_final_formateada + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Nómina: " + e, " error", {progressBar: true, closeButton: true});
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

//Función para confirmar la nomina y el perido seleccionados y procesar de diferente manera
function confirmacion() {
    var nomina = $("#tipo_nomina").val();
    var periodo = $("#periodo").val();
    let periodo_corto = periodo.slice(-2);

    if ((["1", "2", "3", "4"].includes(nomina) && ["85", "86", "87"].includes(periodo_corto))) {
        baseConfianzaCPJubilados(nomina, periodo);
    } else if ((["3", "5"].includes(nomina) && ["88", "89"].includes(periodo_corto))) {
        confianzaSPEstructura(nomina, periodo);
    }
}

// BUSCA LOS TRABAJADORES POR NOMINA PARA INVOCAR FUNCIÓN QUE OBTIENE INCIDENCIAS POR TRABAJADOR EN BUCLE (BASE, CONFIANZA CON PRESTACIONES Y JUBILADOS)
function baseConfianzaCPJubilados(nomina, periodo) {
    $.ajax({
        type: "GET",
        url: "trabajador/listadoTrabajadoresPlazaTipoNomina/" + nomina,
        dataType: 'json',
        success: function(data) {
            var totalTrabajadores = data.length;
            var completedTrabajadores = 0;

            if (totalTrabajadores === 0) {
                toastr["warning"]("No hay trabajadores para procesar", {progressBar: true, closeButton: true});
                return;
            }

            for (var i = 0; i < data.length; i++) {
                if (data[i].prestaciones_si_no === 1) {
                    buscaPeriodoPago(nomina, periodo, data[i].fecha_antiguedad, data[i].id_trabajador).then(function() {
                        completedTrabajadores++;
                        updateProgressBar(completedTrabajadores, totalTrabajadores);

                        if (completedTrabajadores === totalTrabajadores) {
                            toastr["success"]("Proceso completado", {progressBar: true, closeButton: true});
                        }
                    }).catch(function(error) {
                        console.error("Error en el proceso para el trabajador " + data[i].id_trabajador + ": ", error);
                    });
                } else {
                    totalTrabajadores--;
                    if (totalTrabajadores === 0) {
                        toastr["warning"]("No hay trabajadores con prestaciones para procesar", {progressBar: true, closeButton: true});
                        return;
                    }
                }
            }
        },
        error: function(e) {
            toastr["warning"]("Error al recuperar Trabajadores", {progressBar: true, closeButton: true});
        }
    });
}

function updateProgressBar(completed, total) {
    if (total > 0) {
        var perc = Math.round((completed / total) * 100);
        $("#progressBar1").text(perc + '%');
        $("#progressBar1").css('width', perc + '%');

        if (perc === 100) {
            $("#progressBar1").addClass("progress-bar-success");
        }
    }
}
//BUSCA LOS TRABAJADORES POR NOMINA PARA INVOCAR FUNCION QUE OBTIENE INCIDENCIAS POR TRABAJADOR EN BUCLE(CONFIANZA SIN PRESTACIONES, ESTRUCTURA)
function confianzaSPEstructura(nomina, periodo) {
    $.ajax({
        type: "GET",
        url: "trabajador/listadoTrabajadoresPlazaTipoNomina/" + nomina,
        dataType: 'json',
        success: function(data) {
            var totalTrabajadores = data.length;
            var completedTrabajadores = 0;

            if (totalTrabajadores === 0) {
                toastr["warning"]("No hay trabajadores que procesar.", { progressBar: true, closeButton: true });
                return;
            }
            for (var i = 0; i < data.length; i++) {
                if ((nomina === "3" && data[i].prestaciones_si_no === 2) || nomina === "5") {
                    buscaPeriodoPagoConfianza(nomina, periodo, data[i].id_trabajador).then(function() {
                        completedTrabajadores++;
                        updateProgressBar(completedTrabajadores, totalTrabajadores);

                        if (completedTrabajadores === totalTrabajadores) {
                            toastr["success"]("Proceso completado", { progressBar: true, closeButton: true });
                        }
                    }).catch(function(error) {
                        console.error("Error en el proceso para el trabajador " + data[i].id_trabajador + ": ", error);
                    });
                } else {
                    totalTrabajadores--; // Restar trabajadores que no se procesan
                }
            }
        },
        error: function(e) {
            toastr["warning"]("Error al recuperar Trabajadores", { progressBar: true, closeButton: true });
        }
    });
}

// BUSCAR PERIODOS FECHAS DE PAGO Y DETERMINA LOS DIAS DE CONTRATO CORRESPONDIENTES
function buscaPeriodoPagoConfianza(nomina, periodo, id_trabajador) {
    return new Promise(function(resolve, reject) {
        $.ajax({
            type: "GET",
            url: "trabajador/buscarPeriodoPago/" + periodo + "/" + nomina,
            dataType: 'json',
            success: function(data) {
                obtenPuestosDiferentes(nomina, periodo, id_trabajador, data.data.fecha_inicial, data.data.fecha_final);
                resolve(); // Resuelve la promesa cuando la función obtenPuestosDiferentes se haya ejecutado
            },
            error: function(e) {
                toastr["warning"]("Error al recuperar ", { progressBar: true, closeButton: true });
                reject(e); // Rechaza la promesa en caso de error
            }
        });
    });
}

// OBTIENE DE LA TABLA DE PAGOS LOS TRABAJADORES QUE HAYAN CAMBIADO DE PUESTOS EN EL PERIODO DE DICIEMBRE DEL AÑO ANTERIOR A NOVIEMBRE DEL AÑO ACTUAL
function obtenPuestosDiferentes(nomina,periodo,trabajador_id,fecha_inicial,fecha_final) {
    var url = "";
    if (nomina === "3") {
        url = "pagos/listarDiferentesPuestosConfianza/";
    } else if (nomina === "5") {
        url = "pagos/listarDiferentesPuestosEstructura/";
    }
    $.ajax({
        type: "GET",
        url: url + new Date(fecha_inicial) + "/" + new Date(fecha_final) + "/" + trabajador_id,
        dataType: 'json',
        success: function (data) {
            if (data !== undefined) {
                var promises = [];
                for (var i = 0; i < data.length; i++) {
                    if (i === (data.length - 1)) {
                        promises.push(obtenPlazasPrimerPuesto(data[i].cat_Puesto.id_puesto,trabajador_id,fecha_final,nomina));
                    } else {
                        promises.push(obtenPlazasPuestosPosteriores(data[i].cat_Puesto.id_puesto,trabajador_id,fecha_inicial,nomina));
                    }
                }

                Promise.all(promises).then(function() {
                    guardaDiasContratoConfianza(trabajador_id,nomina,periodo);
                    
                }).catch(function(error) {
                    console.error("Error en las promesas: ", error);
                });
            } else {    
                // Nunca cambiaron de puesto en el periodo
                buscaPlazaTrabajador(trabajador_id,fecha_inicial,fecha_final,nomina,periodo);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Puestos", {progressBar: true, closeButton: true});
        }
    });
}

// BUSCA LAS PLAZAS PERTENECIENTES AL PUESTO MAS ACTUAL DEL TRABAJADOR
function obtenPlazasPrimerPuesto(puesto_id,trabajador_id,fecha_final,nomina){
    return new Promise(function(resolve, reject) {
        $.ajax({
            type: "GET",
            url: "catalogos/listarPlazas/" + puesto_id,
            dataType: 'json',
            success: function (data) {
                var innerPromises = [];
                for (var i = 0; i < data.length; i++) {
                    innerPromises.push(buscaTrabajadorPlaza(data[i].id_plaza,trabajador_id,fecha_final,nomina));
                }
                Promise.all(innerPromises).then(resolve).catch(reject);
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar Puestos", {progressBar: true, closeButton: true});
                reject(e);
            }
        });
    });
}

//BUSCA EN TRABAJADOR PLAZA CON CADA UNA DE LAS PLAZAS QUE TRAEMOS DEL PUESTO HASTA QUE ENCUENTRE UNA (PARA TRABAJADOR CON CAMBIO DE PUESTO)
function buscaTrabajadorPlaza(plaza_id,trabajador_id,fecha_final,nomina){
    return new Promise(function(resolve, reject) {
        $.ajax({
            type: "GET",
            url: "trabajador/obtenPlazaTrabajador/" + plaza_id + "/" + trabajador_id + "/" + new Date(fecha_final) + "/" + nomina,
            dataType: 'json',
            success: function (data) {
                resolve(data);
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar Puestos", {progressBar: true, closeButton: true});
                reject(e);
            }
        });
    });
}
//BUSCA EN TRABAJADOR PLAZA CON CADA UNA DE LAS PLAZAS QUE TRAEMOS DEL PUESTO HASTA QUE ENCUENTRE UNA (PARA TRABAJADOR CON EL MISMO PUESTO)
function buscaPlazaTrabajador(trabajador_id,fecha_inicial,fecha_final,nomina,periodo){
    var fecha_actual = new Date();
    var anio_actual = fecha_actual.getFullYear();
    
    var datos = {"anio_aplicacion":anio_actual, "cat_Tipo_Nomina": {"id_tipo_nomina": nomina}, "periodo_aplicacion":periodo, "periodo_generacion": periodo, 
                        "trabajador": {"id_trabajador": trabajador_id}};
    $.ajax({
        type: "GET",
        url: "trabajador/obtenPlazaTrabajadoPorId/" + trabajador_id + "/" + new Date(fecha_inicial) + "/" + new Date(fecha_final) + "/" + nomina + "/" + periodo,
        dataType: 'json',
        success: function (data) {
            if (periodo === anio_actual+"89") {
                guardaimporteAguinaldoAnteriorConfianza(datos);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Plaza Trabajador", {progressBar: true, closeButton: true});
        }
    });
}

// BUSCA LAS PLAZAS PERTENECIENTES AL PUESTO MAS ACTUAL PERO CON LOS PUESTOS ANTERIORES DEL TRABAJADOR
function obtenPlazasPuestosPosteriores(puesto_id,trabajador_id,fecha_inicial,nomina){
    return new Promise(function(resolve, reject) {
        $.ajax({
            type: "GET",
            url: "catalogos/listarPlazas/" + puesto_id,
            dataType: 'json',
            success: function (data) {
                if (data !== undefined) {
                    var innerPromises = [];
                    for (var i = 0; i < data.length; i++) {
                        innerPromises.push(buscaHistoricoTrabajadorPlaza(data[i].id_plaza,trabajador_id,fecha_inicial,nomina));
                    }
                    Promise.all(innerPromises).then(resolve).catch(reject);
                }else {
                    toastr["warning"]("El puesto no contiene ninguna plaza");
                }

                
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar Puestos", {progressBar: true, closeButton: true});
                reject(e);
            }
        });
    });
}

//BUSCA EN HISTORICO TRABAJADOR PLAZA CON CADA UNA DE LAS PLAZAS QUE TRAEMOS DEL PUESTO HASTA QUE ENCUENTRE UNA QUE COINCIDA (PARA TRABAJADOR CON CAMBIO DE PUESTO)
function buscaHistoricoTrabajadorPlaza(plaza_id,trabajador_id,fecha_inicial,nomina){
    return new Promise(function(resolve, reject) {
        $.ajax({
            type: "GET",
            url: "trabajador/obtenPlazaTrabajadorHistorico/" + plaza_id + "/" + trabajador_id + "/" + new Date(fecha_inicial) + "/" + nomina,
            dataType: 'json',
            success: function (data) {
                resolve(data);
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar Puestos", {progressBar: true, closeButton: true});
                reject(e);
            }
        });
    });
}

// BUSCAR PERIODOS FECHAS DE PAGO Y DETERMINA LOS DIAS DE CONTRATO CORRESPONDIENTES
function buscaPeriodoPago(nomina, periodo, fecha_antiguedad, id_trabajador) {
    return new Promise(function(resolve, reject) {
        var fecha_actual = new Date();
        var anio_actual = fecha_actual.getFullYear();

        $.ajax({
            type: "GET",
            url: "trabajador/buscarPeriodoPago/" + periodo + "/" + nomina,
            dataType: 'json',
            success: function(data) {
                var fecha_antiguedad_formato = new Date(fecha_antiguedad);
                var fecha_final_formato = new Date(data.data.fecha_final);
                var anios = fecha_final_formato.getFullYear() - fecha_antiguedad_formato.getFullYear();
                var meses = (fecha_final_formato.getMonth() + 1) - (fecha_antiguedad_formato.getMonth() + 1);
                var dias = fecha_final_formato.getDate() - fecha_antiguedad_formato.getDate();

                // Ajustar los años si el mes y día de la fecha final están antes que el mes y día de la fecha de antigüedad
                if (meses < 0 || (meses === 0 && dias < 0)) {
                    anios--;
                }

                var anios_entero = anios;
                var datos = {
                    "anio_aplicacion": anio_actual,
                    "cat_Tipo_Nomina": { "id_tipo_nomina": nomina },
                    "periodo_aplicacion": periodo,
                    "periodo_generacion": periodo,
                    "trabajador": { "id_trabajador": id_trabajador }
                };

                // Invoca la función de guardar días de contrato con los datos recopilados del front
                guardaTmpMovimientosDiasContrato(datos, anios_entero, fecha_antiguedad_formato, fecha_final_formato);

                // Invoca la función de buscar días de inasistencias
                obtenIncidenciasPeriodo(id_trabajador, new Date(data.data.fecha_inicial), new Date(data.data.fecha_final), nomina, periodo, anio_actual);

                // Resuelve la promesa cuando todo esté completado
                resolve();
            },
            error: function(e) {
                toastr["warning"]("Error al recuperar ", { progressBar: true, closeButton: true });
                reject(e);
            }
        });
    });
}

//BUSCA LA CANTIDAD DE INCIDENCIAS POR TRABAJADOR PARA RESTARLAS A LA CANTIDAD DE DIAS PAGADOS Y OBTENER POSTERIORMENTE EL MONTO CORRESPONDIENTE POR TRABAJADOR
function obtenIncidenciasPeriodo(id_trabajador, fecha_inicial, fecha_fin, nomina, periodo, anio_actual){
    $.ajax({
        type: "GET",
        url: "incidencias/buscarIncidenciasPeriodoVales/" + id_trabajador + "/" + fecha_inicial + "/" + fecha_fin,
        dataType: 'json',
        success: function (data) {
            var inasistencias = data;
            var datos = {"anio_aplicacion":anio_actual, "cat_Tipo_Nomina": {"id_tipo_nomina": nomina}, "periodo_aplicacion":periodo, "periodo_generacion": periodo, 
                        "trabajador": {"id_trabajador": id_trabajador},"valor_1": inasistencias};
                    
            //INVOCA LA FUNCIÓN DE GUARDAR DIAS DE INASISTENCIAS CON LOS DATOS RECOPILADOS DE FRONT
            guardaTmpMovimientosInasistencias(datos);

        },
        error: function (e) {
            toastr["warning"]("Error al recuperar las incidencias por trabajador", {progressBar: true, closeButton: true});
        }
    });
}

// GUARDAR EN LA TABLA TMP MOVIMIENTOS INASISTENCIAS
function guardaTmpMovimientosInasistencias(datos){
    $.ajax({
        type: "POST",
        url: "pagos/guardarMovimientos244",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            
        },
        error: function (e) {
            toastr["warning"]("Error al guardar movimientos", {progressBar: true, closeButton: true});
        }
    });
}

// GUARDAR EN LA TABLA TMP MOVIMIENTOS DIAS DE CONTRATO
function guardaTmpMovimientosDiasContrato(datos, anios, fecha_antiguedad_formato, fecha_final_formato){
    $.ajax({
        type: "POST",
        url: "pagos/guardarMovimientos243/" + anios + "/" + fecha_antiguedad_formato + "/" + fecha_final_formato,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
        },
        error: function (e) {
            toastr["warning"]("Error al guardar movimientos", {progressBar: true, closeButton: true});
        }
    });
}

//GUARDA LOS DÍAS DE CONTRATO DE LOS TRABAJADORES DE CONFIANZA
function guardaDiasContratoConfianza(id_trabajador, nomina, periodo){
     var fecha_actual = new Date();
    var anio_actual = fecha_actual.getFullYear();
    
    var datos = {"anio_aplicacion":anio_actual, "cat_Tipo_Nomina": {"id_tipo_nomina": nomina}, "periodo_aplicacion":periodo, "periodo_generacion": periodo, 
                        "trabajador": {"id_trabajador": id_trabajador}};
    $.ajax({
        type: "POST",
        url: "trabajador/guardarMovimientos243Confianza",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            guardaIncidenciasConfianza(datos);
        },
        error: function (e) {
            toastr["warning"]("Error al guardar movimientos", {progressBar: true, closeButton: true});
        }
    });
}

// GUARDA LAS INCIDENCIAS DE CONFIANZA
function guardaIncidenciasConfianza(datos){
    $.ajax({
        type: "POST",
        url: "trabajador/guardarMovimientos244Confianza",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            guardaimporteAguinaldoConfianza(datos);
        },
        error: function (e) {
            toastr["warning"]("Error al guardar movimientos", {progressBar: true, closeButton: true});
        }
    });
}

//GUARDA EL IMPORTE DEL AGUINALDO DE CONFIANZA
function guardaimporteAguinaldoConfianza(datos){
    $.ajax({
        type: "POST",
        url: "trabajador/guardarMovimientos12Confianza",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            if (datos.periodo_aplicacion === datos.anio_aplicacion+"89") {
                guardaimporteAguinaldoAnteriorConfianza(datos);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al guardar movimientos", {progressBar: true, closeButton: true});
        }
    });
}
//GUARDA EL IMPORTE DEL AGUINALDO ANTERIOR DE CONFIANZA
function guardaimporteAguinaldoAnteriorConfianza(datos){
    $.ajax({
        type: "POST",
        url: "trabajador/guardarMovimientos330Confianza",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            guardaDevolucionISRConfianza(datos);
        },
        error: function (e) {
            toastr["warning"]("Error al guardar movimientos", {progressBar: true, closeButton: true});
        }
    });
}

//GUARDA LA DEVOLUCIÓN DE ISR DE LA NOMINA DE CONFIANZA
function guardaDevolucionISRConfianza(datos){
    $.ajax({
        type: "POST",
        url: "trabajador/guardarMovimientos339Confianza",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            
        },
        error: function (e) {
            toastr["warning"]("Error al guardar movimientos", {progressBar: true, closeButton: true});
        }
    });
}

//BLOQUEA TODAS LAS CASILLAS DEL CHECK
function bloquearTodas() {
    // Obtiene todas las casillas de verificación en el formulario
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    // Desmarca cada casilla de verificación
    checkboxes.forEach(function(checkbox) {
        checkbox.disabled = true;
    });
}

//LIMPIA LA BARRA DE CARGA CUANDO CAMBIA LA NOMINA DEL SELECT
function limpiarBarraDeCarga() {
    $("#progressBar1").text('0%');
    $("#progressBar1").css('width', '0%');
    $("#progressBar1").removeClass("progress-bar-success progress-bar-danger"); // Elimina cualquier clase de éxito o error
}