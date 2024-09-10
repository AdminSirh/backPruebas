/* global moment, Intl */
document.addEventListener('DOMContentLoaded', () => {

});

/*=============================================
 LISTAR COMBO DE PARENTESCO
 =============================================*/
function parentesco(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarParentesco",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#parentesco').empty().append("<option value=''>* Parentesco </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_parentesco === id) ? vselected = "selected" : vselected = " ";
                        $('#parentesco').append('<option value="' + data[x].id_parentesco + '"' + vselected + '>' + data[x].desc_parentesco + '</option>');
                        $('#cmb_parentesco_edita_ayuda').append('<option value="' + data[x].id_parentesco + '"' + vselected + '>' + data[x].desc_parentesco + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Parentesco: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 BUSCAR EL CONTRATO DEL TRABAJADOR
 =============================================*/
function buscarContratoAyudas(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPlazaTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            $('#tipo_nomina_ayudas').val(data.data.cat_Tipo_Nomina.id_tipo_nomina);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=============================================
 OBTENER EL PERIODO DE PAGO DEL TRABAJADOR
 =============================================*/
function obtenPeriodoPagoAyudas() {
    if ($("#cmbInc").val() === "3" || $("#cmbInc").val() === "4" || $("#cmbInc").val() === "33") {
        var fecha_inicio = $('#fecha_inicio').val();
        var tipo_nomina = $('#tipo_nomina_ayudas').val();
        $.ajax({
            type: "GET",
            url: "trabajador/buscaPeriodoPago/" + tipo_nomina + "/" + fecha_inicio,
            dataType: 'json',
            success: function (data) {
                $('#periodo_pago').val(data.data);
                $.ajax({
                    type: "GET",
                    url: "trabajador/buscarPeriodoPago/" + data.data + "/" + tipo_nomina,
                    dataType: 'json',
                    success: function (data) {
                        $('#id_periodo_pago').val(data.data.id_periodo_pago);

                    }, error: function (e) {
                        toastr['error']("Error al encontrar id del periodo de pago");
                    }
                });

            }, error: function (e) {
                toastr['error']("Error al encontrar periodo de pago");
            }
        });
    }
}

/*=============================================
 OBTENER LA PLAZA DEL TRABAJADOR
 =============================================*/
function obtenPlazaTrabajador(num_dias) {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var trabajador_id = urlParams.get('id_trabajador');
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPlazaTrabajador/" + trabajador_id,
        dataType: 'json',
        success: function (data) {
            obtenSueldoTrabajador(data.data.plaza_id, num_dias);

        }, error: function (e) {
            toastr['error']("Este trabajador no tiene plaza asignada");
        }
    });
}
/*=============================================
 OBTENER EL SUELDO DIARIO DEL TRABAJADOR
 =============================================*/
function obtenSueldoTrabajador(plaza_id, num_dias) {
    $.ajax({
        type: "GET",
        url: "catalogos/buscarPlaza/" + plaza_id,
        dataType: 'json',
        success: function (data) {
            var sueldo_diario = data.data.cat_puesto.sueldo_diario;

            var monto = (sueldo_diario * num_dias);
            $('#monto').val(monto);
            calculaDiasAyuda();

        }, error: function (e) {
            toastr['error']("Error al encontrar el sueldo diario");
        }
    });

}
/*=============================================
 BUSCAR EL GENERO DEL TRABAJADOR
 =============================================*/
function buscarGenero(trabajador_id) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + trabajador_id,
        dataType: 'json',
        success: function (data) {
            $('#genero').val(data.data.persona.cat_genero.desc_genero);

        }, error: function (e) {
            toastr['error']("Error al encontrar el genero");
        }
    });
}

/*=============================================
 BUSCAR EL CONTRATO DEL TRABAJADOR
 =============================================*/
function buscarContratoIncidencias(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPlazaTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            $('#tipo_contrato_incidencias').val(data.data.cat_tipo_contrato.id_tipo_contrato);
            incidencia();
        },
        error: function (e) {
            alert(e);
        }
    });

}

/*=============================================
 OBTIENE LA INFORMACION DE LAS PRESTACIONES DEL TRABAJADOR
 =============================================*/
function obtenPrestacionesIncidencias(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            $('#trabajador_prestaciones_incidencias').val(data.data.prestaciones_si_no);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar las prestaciones");
        }
    });

}

/*=============================================
 VALIDA LOS DIAS INGRESADOS DE LAS INCIDENCIAS 3,4 Y 33 DEPENDIENDO LOS TIPOS DE CONTRATO
 =============================================*/
function calculaDiasAyuda() {
    var tipo_contrato = $('#tipo_contrato_incidencias').val();
    var prestaciones = $('#trabajador_prestaciones_incidencias').val();
    var num_dias = $('#num_dias').val();
    var genero = $('#genero').val();

    var dias_correspondientes = 0;
    if (tipo_contrato === "1" || (tipo_contrato === "2" && prestaciones === "1") || tipo_contrato === "4" || tipo_contrato === "5" || tipo_contrato === "6" || tipo_contrato === "7") {
        if ($("#cmbInc").val() === "3") {
            dias_correspondientes = 8;
            if (num_dias > dias_correspondientes) {
                toastr['error']("El limite de días es: " + dias_correspondientes);
                limpiaCampos();
            }
        } else if ($("#cmbInc").val() === "4") {
            dias_correspondientes = 9;
            if (num_dias > dias_correspondientes) {
                toastr['error']("El limite de días es: " + dias_correspondientes);
                limpiaCampos();
            }
        }

    } else if ((tipo_contrato === "2" && prestaciones === "2") || tipo_contrato === "3") {
        if ($("#cmbInc").val() === "3") {
            dias_correspondientes = 15;
            if (num_dias > dias_correspondientes) {
                toastr['error']("El limite de días es: " + dias_correspondientes);
                limpiaCampos();

            }
        } else if ($("#cmbInc").val() === "4") {
            dias_correspondientes = 9;
            if (num_dias > dias_correspondientes) {
                toastr['error']("El limite de días es: " + dias_correspondientes);
                limpiaCampos();
            }
        }
    } else if ((tipo_contrato === "2" && prestaciones === "2" && genero === "MUJER") || (tipo_contrato === "3" && genero === "MUJER")) {
        if ($("#cmbInc").val() === "") {
            dias_correspondientes = 131;
            if (num_dias > dias_correspondientes) {
                toastr['error']("El limite de días es: " + dias_correspondientes);
                limpiaCampos();
            }
        }
    }

}

/*=============================================
 VALIDA LOS DIAS INGRESADOS DE LAS INCIDENCIAS DE LA BASE DE DATOS A EXCEPCION 3,4 Y 33
 =============================================*/
function buscaDiasIncidencia() {
    if ($('#fecha_inicio').val() !== "" && $('#fecha_fin').val() !== "" && $("#cmbInc").val() !== "3" && $("#cmbInc").val() !== "4" && $("#cmbInc").val() !== "33") {
        var contrato_id = $('#tipo_contrato_incidencias').val();
        var incidencias_id = $("#cmbInc").val();
        var num_dias = $('#num_dias').val();
        $.ajax({
            type: "GET",
            url: "incidencias/buscarDiasIncidencia/" + contrato_id + "/" + incidencias_id,
            dataType: 'json',
            success: function (data) {
                if (data.data !== null) {
                    var dias_disponibles = parseInt(data.data) - parseInt(num_dias);
                    if (dias_disponibles < 0) {
                        toastr["warning"]("Solo se pueden ingresar " + data.data + " días");
                        limpiaCampos();
                    }
                } else {
                    toastr["warning"]("No hay días cargados para esta incidencia");
                }
            },
            error: function (e) {
                toastr["warning"]("Error al recuperar los días");
            }
        });
    }
}

const comboIncidencias = document.getElementById('cmbInc');
comboIncidencias.addEventListener('change', function () {
    const selectedValue = this.value;
    limpiaCampos();
});
//Se valida que los días de permiso se encuentren dentro del mismo año que la fecha inicial y la fecha final
function diasDePermisoEstanMismoAnioQueFechaInicialYFechaFinal(dia, fecha_inicio, fecha_fin) {
    let fechaDia = new Date(
            parseInt(dia.substring(0, 4)),
            parseInt(dia.substring(5, 7)) - 1,
            parseInt(dia.substring(8))
            );
    let fechaInicio = new Date(
            parseInt(fecha_inicio.substring(0, 4)),
            parseInt(fecha_inicio.substring(5, 7)) - 1,
            parseInt(fecha_inicio.substring(8))
            );
    let fechaFin = new Date(
            parseInt(fecha_fin.substring(0, 4)),
            parseInt(fecha_fin.substring(5, 7)) - 1,
            parseInt(fecha_fin.substring(8))
            );

    let anioInicio = fechaInicio.getFullYear();
    let anioFin = fechaFin.getFullYear();
    let anioDia = fechaDia.getFullYear();

    return anioDia >= anioInicio && anioDia <= anioFin;
}
//Se buscan días de permiso que pudieran haber sido registrados en días de vacaciones
function seTienenDiasDeVacacionesEnDiasPermisoAyuda(diasDePermiso, trabajador_id, callback) {
    //Flag para indicar que existe un día de vacación en los días de permiso
    let flagDiaVacaciones = false;
    // Se buscan las vacaciones activas del trabajador
    $.ajax({
        type: "GET",
        url: "incidencias/buscarIncidenciaEspecificaTrabajador/" + trabajador_id + "/" + 7,
        dataType: 'json',
        async: false,
        success: function (data) {
            const diasVacacionesCoincidentes = new Set();
            // Recorrer cada incidencia
            data.forEach(function (incidencia) {
                // Verificar si la incidencia tiene fechas de inicio y fin
                if (incidencia.fecha_inicio && incidencia.fecha_fin) {
                    const fechaInicio = new Date(incidencia.fecha_inicio);
                    const fechaFin = new Date(incidencia.fecha_fin);
                    // Recorrer los días de permiso
                    Object.values(diasDePermiso).forEach(function (valorPermiso) {
                        const fechaPermiso = new Date(valorPermiso);
                        // Comparar si el día de permiso está dentro del rango de la incidencia
                        if (fechaPermiso >= fechaInicio && fechaPermiso <= fechaFin) {
                            // Se levanta la bandera para indicar que hay vacaciones activas dentro de algún día del permiso
                            flagDiaVacaciones = true;
                            // Se obtienen los días que se interponen con las vacaciones del trabajador
                            diasVacacionesCoincidentes.add(valorPermiso);
                        }
                    });
                }
            });
            callback(flagDiaVacaciones, diasVacacionesCoincidentes);
        },
        error: function (e) {
            toastr["error"]("Error al recuperar los días de vacaciones del trabajador", e);
            callback(false);
        }
    });
}

//Se busacan días de permiso que pudieran haber sido registrados en días de descanso
function seTienenDiasDescansoEnDiasPermisoAyuda(diasDePermiso, trabajador_id, callback) {
    // Verificar si diasDePermiso está vacío
    if (Object.keys(diasDePermiso).length === 0) {
        return false;
    }
    //Flag para indicar que existe un día de descanso
    let flagDiaDescanso = false;

    //Llamada AJAX para verificar los días de descanso que tenga asignado el trabajador
    $.ajax({
        type: "GET",
        url: "horarios/buscarDiasDescansoTrabajador/" + trabajador_id,
        dataType: 'json',
        async: false,
        success: function (data) {
            const nombreDiasDescanso = data.map(elemento => elemento.cat_dias.dia);
            const nombresDiasCoincidentes = [];
            // Se recorre el objeto para hacer las asignaciones de los nombres de cada día
            Object.keys(diasDePermiso).forEach(function (dia) {
                const fecha = diasDePermiso[dia];
                const nombreDia = obtenerNombreDia(fecha);
                if (nombreDia !== undefined) {
                    // Verificar si el nombre del día está en la lista de días de descanso
                    if (nombreDiasDescanso.includes(nombreDia)) {
                        //Se levanta la bandera puesto que se identificó un día de descanso en los permisos registrados
                        flagDiaDescanso = true;
                        //Se guardan en un arreglo los días de descanso que se colocaron como días de permiso
                        nombresDiasCoincidentes.push(nombreDia);
                    }
                }
            });
            callback(flagDiaDescanso, nombresDiasCoincidentes);
        },
        error: function (e) {
            toastr["error"]("Error al recuperar los días de descanso del trabajador");
            callback(false);
        }
    });
}
// Se obtienen los nombres de los días elegidos
function obtenerNombreDia(fecha) {
    const diasSemana = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];
    // Verificar si la fecha no es una cadena vacía
    if (fecha === "") {
        return;
    }
    const fechaObjeto = new Date(
            parseInt(fecha.substring(0, 4)), // Año
            parseInt(fecha.substring(5, 7)) - 1, // Mes (restar 1 ya que los meses se indexan desde 0)
            parseInt(fecha.substring(8)) // Día
            );
    const numeroDia = fechaObjeto.getDay();
    return diasSemana[numeroDia];
}

