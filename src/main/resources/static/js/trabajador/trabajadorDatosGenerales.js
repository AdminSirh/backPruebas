/* global data, NaN, Document */
/* Cargar DOM antes de JS */
document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');
    buscarDatosTrabajador(id_trabajador);
    prestaciones_Datos();
    comisionado_Datos();
    buscarSinContrato(id_trabajador);
    tabs(id_trabajador);
});

/*=============================================
 Mostramos el Menu de datos del trabajador
 =============================================*/
function tabs(id_trabajador) {
    $('#myTabTrabajador').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li><a href='#tabs-1' class='btn btn-info'>1.- DATOS DEL TRABAJADOR</a></li>&nbsp;" +
            "<li class='btn btn-primary' onclick=trabajadorBancarios('" + id_trabajador + "')>2.- DATOS BANCARIOS</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=trabajadorPuesto('" + id_trabajador + "')>3.- PLAZA</li>&nbsp;"+
            "<li class='btn btn-primary' onclick=verValidaciones('" + id_trabajador + "')>4.- VALIDACIÓN</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=trabajadorBeneficiarios('" + id_trabajador + "')>5.- BENEFICIARIOS - SEGURO DE VIDA</li>&nbsp;");
}

/*=============================================
 URL de datos bancarios del trabajador
 =============================================*/
function trabajadorBancarios(id_trabajador) {
    window.location.href = 'trabajadorBancarios?id_trabajador=' + id_trabajador;
}

/*=============================================
 URL de datos del puesto
 =============================================*/
function trabajadorPuesto(id_trabajador) {
    window.location.href = 'trabajadorPuesto?id_trabajador=' + id_trabajador;
}

/*=============================================
 URL de validaciones del trabajador
 =============================================*/
function verValidaciones(id_trabajador) {
    window.location.href = 'trabajadorValidacion?id_trabajador=' + id_trabajador;
}

/*=============================================
 URL de validaciones del beneficiarios  
 =============================================*/
function trabajadorBeneficiarios(id_trabajador) {
    window.location.href = 'personaBeneficiarios?id_trabajador=' + id_trabajador;
}
/*=============================================
 Carga los datos del trabajador en el formulario
 =============================================*/
function buscarDatosTrabajador(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            event.preventDefault();
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            $('#id_Persona').val(data.data.persona.id_persona);
            $('#id_Expediente').val(data.data.cat_expediente.id_expediente);
            
            if (data.data.prestaciones_si_no !== null) {
                prestaciones_Datos(data.data.prestaciones_si_no);
            }
            if (data.data.comisionado_si_no !== null) {
                comisionado_Datos(data.data.comisionado_si_no);
            }
            if (data.data.fecha_antiguedad !== null) {
                var formato_FechaUno = data.data.fecha_antiguedad.split('-');
                var formato_FechaDos = formato_FechaUno[2].split('T');
                $("#fecha_antiguedad").val(formato_FechaUno[0] + "-" + formato_FechaUno[1] + "-" + formato_FechaDos[0]);
            }
            if (data.data.fecha_ingreso !== null) {
                var formato_FechaUno = data.data.fecha_ingreso.split('-');
                var formato_FechaDos = formato_FechaUno[2].split('T');
                $("#fecha_ingreso").val(formato_FechaUno[0] + "-" + formato_FechaUno[1] + "-" + formato_FechaDos[0]);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

function buscarSinContrato(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "incidencias/buscarSinContrato/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            if (data.data !== null) {
                $("#ruta").val("incidencias/editarIncidencia/"+ data.data.id_incidencia);
            }else{
                $("#ruta").val("incidencias/guardarIncidencia");
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
/*=============================================
 Guarda datos del trabajador - datos generales
 =============================================*/
$("#formGuardarTrabajador").submit(function (e) {
    event.preventDefault();
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_trabajador = urlParams.get('id_trabajador');
    var prestaciones = $("#prestaciones").val();
    var comisionado = $("#comisionado").val();
    var fecha_antiguedad = $("#fecha_antiguedad").val();
    var fecha_ingreso = $("#fecha_ingreso").val();
    
    if ($.trim(prestaciones) === "") {
        return false;
    }
    if ($.trim(comisionado) === "") {
        return false;
    }
    if ($.trim(fecha_antiguedad) === "") {
        return false;
    }
    if ($.trim(fecha_ingreso) === "") {
        return false;
    }
    var datos = {"prestaciones_si_no": prestaciones, "comisionado_si_no": comisionado,
        "fecha_antiguedad": fecha_antiguedad, "fecha_ingreso": fecha_ingreso};
    $.ajax({
        type: "POST",
        url: "trabajador/editarTrabajador/" + id_trabajador,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            guardarSinContrato(id_trabajador,fecha_ingreso);
            toastr['success'](data.data, "Se guardó correctamente la Información");
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});



//==================== LISTAR SI_NO PRESTACIONES DATOS =========================
function prestaciones_Datos(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarSi_No",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#prestaciones').empty().append("<option value=''>* Prestaciones </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id === id) ? vselected = "selected" : vselected = " ";
                        $('#prestaciones').append('<option value="' + data[x].id + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select prestaciones: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//==================== LISTAR SI_NO COMISIONADO DATOS ===========================
function comisionado_Datos(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarSi_No",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#comisionado').empty().append("<option value=''>* Comisionado </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id === id) ? vselected = "selected" : vselected = " ";
                        $('#comisionado').append('<option value="' + data[x].id + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select comisionado: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

function guardarSinContrato(trabajador_id,fecha_ingreso){
    var ruta = $("#ruta").val();
    var anio = new Date(fecha_ingreso).getFullYear();
    var fecha_enero = new Date(anio, 0, 1);
    fecha_enero.setHours(0, 0, 0, 0);

    // Calcular la diferencia en milisegundos (sin ajustar las horas)
    var diferenciaEnMilisegundos = new Date(fecha_ingreso) - fecha_enero;

    // Convertir la diferencia a días
    var diferenciaEnDias = Math.round(diferenciaEnMilisegundos / (1000 * 60 * 60 * 24));
    datos = {"num_dias": diferenciaEnDias, "cat_estado_incidencia": {"id_estado_incidencia": 1}, "cat_incidencias": {"id_incidencia": 20}, "fecha_inicio": fecha_enero,
            "fecha_fin": new Date(fecha_ingreso), "trabajador_id": trabajador_id};
    $.ajax({
        type: "POST",
        url: ruta,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            
            toastr['success'](data.data, "Se guardó correctamente la Información");
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 Boton para ver listado de todos los trabajadores
 =============================================*/
function verTrabajadores() {
    window.location.href = 'trabajadoresAdmin';
}