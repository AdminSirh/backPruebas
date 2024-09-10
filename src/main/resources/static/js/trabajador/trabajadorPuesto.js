document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');
    tabs(id_trabajador);
    listarContrato();
    areas();
    puestos();
    buscarPlaza(id_trabajador);
});


function tabs(id_trabajador) {
    $('#myTabTrabajador').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=trabajadorDatosGenerales('" + id_trabajador + "')>1.- DATOS DEL TRABAJADOR</a></li>&nbsp;" +
            "<li class='btn btn-primary' onclick=trabajadorBancarios('" + id_trabajador + "')>2.- DATOS BANCARIOS</li>&nbsp;" +
            "<li><a href='#tabs-1' class='btn btn-info'>3.- PLAZA</a></li>&nbsp;" +
            "<li class='btn btn-primary' onclick=verValidaciones('" + id_trabajador + "')>4.- VALIDACIÓN</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=trabajadorBeneficiarios('" + id_trabajador + "')>5.- BENEFICIARIOS - SEGURO DE VIDA</li>&nbsp;");
}

function trabajadorDatosGenerales(id_trabajador) {
    window.location.href = 'trabajadorDatosGenerales?id_trabajador=' + id_trabajador;
}

function trabajadorBancarios(id_trabajador) {
    window.location.href = 'trabajadorBancarios?id_trabajador=' + id_trabajador;
}

function verTrabajadores() {
    window.location.href = 'trabajadoresAdmin';
}

function verValidaciones(id_trabajador) {
    window.location.href = 'trabajadorValidacion?id_trabajador=' + id_trabajador;
}

/*=============================================
 URL de validaciones del beneficiarios  
 =============================================*/
function trabajadorBeneficiarios(id_trabajador) {
    window.location.href = 'personaBeneficiarios?id_trabajador=' + id_trabajador;
}



function bloqueaInput(id_plaza_movimientos){
    if (id_plaza_movimientos === "" || id_plaza_movimientos === null) {
        var input = document.getElementById("fecha_inicial");
        input.disabled = true;
    }
}

/*=============================================
 COMBO MOVIMIENTO DE PLAZA
 =============================================*/
function movimientos(id_plaza_movimientos) {
    
    $.ajax({
        type: "GET",
        url: "catalogos/listarDatosPlazaMovimientos",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }else{
                if (id_plaza_movimientos === "" || id_plaza_movimientos === null) {
                    
                    $('#plaza_movimientos').append('<option value="' + data[31].id_plaza_movimientos + '"' + vselected + '>' + data[31].descripcion + '</option>');
                    
                }else{
                    
                    $('#plaza_movimientos').empty().append("<option value=''>* Movimientos </option> ");
                    var vselected = "";
                    if (data.length > 0) {
                        for (var x = 0; x < data.length; x++) {
                            if (x !== 31) {
                                $('#plaza_movimientos').append('<option value="' + data[x].id_plaza_movimientos + '"' + vselected + '>' + data[x].descripcion + '</option>');
                            }
                        }
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar el área: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


/*=============================================
 COMBO AREAS
 =============================================*/
function areas(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarAreas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#areas_id').empty().append("<option value=''>* Areas </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].idArea === id) ? vselected = "selected" : vselected = " ";
                        $('#areas_id').append('<option value="' + data[x].idArea + '"' + vselected + '>' + data[x].desc_area + '</option>');
                        
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar el área: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 COMBO CONTRATO
 =============================================*/
function listarContrato(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarContrato",
        dataType: 'json',
        success: function (data) {
            
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#tipo_contrato_id").empty().append("<option value=''>* Tipo de Contrato</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_tipo_contrato === id) ? vselected = "selected" : vselected = "";
                        $("#tipo_contrato_id").append('<option value="' + data[x].id_tipo_contrato + '"' + vselected + '>' + data[x].tipo_nomina + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Seleccione una Opción", " error", {progressBar: true, closeButton: true});
        }
    });
    
}

/*=============================================
 COMBO NOMINA
 =============================================*/
function listarNomina(id) {
    var id_contrato = $("#tipo_contrato_id").val();
    
    $.ajax({
        type: "GET",
        url: "catalogos/listarIdContrato/" + id_contrato,
        dataType: 'json',
        success: function (data) {
            
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#tipo_nomina_id").empty().append("<option value=''>* Tipo de Nomina</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].cat_Tipo_Nomina.id_tipo_nomina === id) ? vselected = "selected" : vselected = "";
                        $("#tipo_nomina_id").append('<option value="' + data[x].cat_Tipo_Nomina.id_tipo_nomina + '"' + vselected + '>' + data[x].cat_Tipo_Nomina.desc_nomina + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Seleccione una Opción", " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 COMBO UBICACIÓN
 =============================================*/
function listarUbicacion(id) {
    var id_nomina = $("#tipo_nomina_id").val();
   
    $.ajax({
        type: "GET",
        url: "catalogos/listarIdNomina/" + id_nomina,
        dataType: 'json',
        success: function (data) {
            
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#ubicacion_id").empty().append("<option value=''>* Ubicación</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].cat_Ubicacion.id_ubicacion === id) ? vselected = "selected" : vselected = "";
                        $("#ubicacion_id").append('<option value="' + data[x].cat_Ubicacion.id_ubicacion + '"' + vselected + '>' + data[x].cat_Ubicacion.desc_ubicacion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Seleccione una Opción", " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 GUARDAR PLAZA
 =============================================*/

$("#formGuardarPlaza").submit(function (e) {
    event.preventDefault();
    /*=============================================
       VALORES ANTIGUOS DE LAS VARIABLES
      =============================================*/
    var tipo_contrato_id_DB = $('#tipo_contrato_id_DB').val();
    var tipo_nomina_id_DB = $('#tipo_nomina_id_DB').val();
    var ubicacion_id_DB = $('#ubicacion_id_DB').val();
    var areas_id_DB = $('#areas_id_DB').val();
    var plazasDisponibles_DB = $('#plazasDisponibles_DB').val();
    var fecha_inicial_DB = $('#fecha_inicial_DB').val();
    var observaciones_DB = $('#observaciones_DB').val();
    var plaza_movimientos_id_DB = $("#plaza_movimientos_DB").val();
    /*=============================================
       VALORES INGRESADOS
      =============================================*/
    var id_tabajador_plaza = $('#id_trabajador_plaza').val();
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');
    var plaza_id = $("#plazasDisponibles").val();
    var areas_id = $("#areas_id").val();
    var tipo_contrato_id = $("#tipo_contrato_id").val();
    var tipo_nomina_id = $("#tipo_nomina_id").val();
    var ubicacion_id = $("#ubicacion_id").val();
    var noPlaza = $("#noPlaza").val();
    var observaciones = $("#observaciones").val();
    var plaza_movimientos_id = $("#plaza_movimientos").val();
    var fecha_inicial = $("#fecha_inicial").val();
    var fecha_final = $("#fecha_final").val();
     
    if ($.trim(id_trabajador) === "") {
        return false;
    }
    if ($.trim(plaza_id) === "") {
        return false;
    }
    if ($.trim(areas_id) === "" ) {
        return false;
    }
    if ($.trim(tipo_contrato_id) === "") {
        return false;
    }
    if ($.trim(tipo_nomina_id) === "") {
        return false;
    }
    if ($.trim(ubicacion_id) === "") {
        return false;
    }
    if ($.trim(observaciones) === "") {
        return false;
    }
    if ($.trim(plaza_movimientos_id) === "") {
        return false;
    }
    if ($.trim(fecha_final) === "") {
        return false;
    }
    var datos = {"trabajador_id": id_trabajador,"plaza_id":plaza_id,"areas_id": areas_id,"cat_tipo_contrato": {"id_tipo_contrato": tipo_contrato_id},"cat_Tipo_Nomina":{"id_tipo_nomina": tipo_nomina_id},"cat_Ubicacion": {"id_ubicacion": ubicacion_id},
                 "observaciones":observaciones, "plaza_movimientos_id":plaza_movimientos_id, "fecha_final":fecha_final};
    
    var url_accion = "";
    
    if (id_tabajador_plaza === "" || id_tabajador_plaza === null) {
        url_accion = "trabajador/guardarTrabajadorPlaza";
        

    } else if (id_tabajador_plaza !== "" ||id_tabajador_plaza !== null) {
        datos.fecha_inicial = fecha_inicial;
        url_accion = "trabajador/editarPlazaTrabajador/" + id_trabajador;
        if (tipo_contrato_id_DB !== tipo_contrato_id || tipo_nomina_id_DB !== tipo_nomina_id || ubicacion_id_DB !== ubicacion_id || areas_id_DB !== areas_id || plazasDisponibles_DB !== plaza_id) {
                guardarHistorico(id_tabajador_plaza, id_trabajador, tipo_contrato_id_DB, tipo_nomina_id_DB, ubicacion_id_DB, areas_id_DB, plazasDisponibles_DB, plaza_movimientos_id_DB, observaciones_DB, fecha_inicial_DB, fecha_inicial);}
        
    }
    $.ajax({
        type: "POST",
        url: url_accion,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            
            event.preventDefault();
            toastr['success'](data.data, "Plaza guardada correctamente");
            buscarPlaza(id_trabajador);
            asignarStatusPlazaDisponible(plaza_id);
            asignarStatusPlazaOcupada(noPlaza);
            $('#ubicacion_id').html('');
            $('#tipo_nomina_id').html('');
            $('#plazasDisponibles').html('');
            
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
});

/*=============================================
 BUSCAR LA PLAZA POR EL ID TRABAJADOR
 =============================================*/
function buscarPlaza(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPlazaTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            movimientos(data.data);
            bloqueaInput(data.data);
            /*=============================================
              VARIABLES DE LA BASE ANTES DE CAMBIAR AL VALOR
             =============================================*/
            $('#tipo_contrato_id_DB').val(data.data.cat_tipo_contrato.id_tipo_contrato);
            $('#tipo_nomina_id_DB').val(data.data.cat_Tipo_Nomina.id_tipo_nomina);
            $('#ubicacion_id_DB').val(data.data.cat_Ubicacion.id_ubicacion);
            $('#areas_id_DB').val(data.data.areas_id);
            $('#plazasDisponibles_DB').val(data.data.plaza_id);
            $('#fecha_inicial_DB').val(data.data.fecha_inicial);
            $('#fecha_final_DB').val(data.data.fecha_final);
            $('#observaciones_DB').val(data.data.observaciones);
            $('#plaza_movimientos_DB').val(data.data.plaza_movimientos_id);
            
            buscarPlazaIdPlaza(data.data.plaza_id);
            $('#id_trabajador_plaza').val(data.data.id_trabajador_plaza);
            $('#noPlaza').val(data.data.plaza_id);
            $('#plazasDisponibles').append('<option value="' + data.data.plaza_id + '"' + " " + '>' + data.data.plaza_id + '</option>');;
            areas(data.data.areas_id);
            listarContrato(data.data.cat_tipo_contrato.id_tipo_contrato);
            $("#ubicacion_id").append('<option value="' + data.data.cat_Ubicacion.id_ubicacion + '"' + " " + '>' + data.data.cat_Ubicacion.desc_ubicacion + '</option>');
            $("#tipo_nomina_id").append('<option value="' + data.data.cat_Tipo_Nomina.id_tipo_nomina + '"' + " " + '>' + data.data.cat_Tipo_Nomina.desc_nomina + '</option>');   
            
        },
        error: function (e) {
            alert(e);
        }
    });
}
/*=============================================
 COMBO PLAZAS DISPONIBLES
 =============================================*/
function plazas(id){
    var id_puesto = $("#puestos").val();
    
    $.ajax({
        type: "GET",
        url: "catalogos/buscarPlazasDisponibles/" + id_puesto,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No hay plazas disponibles para este puesto", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#plazasDisponibles').empty().append("<option value=''>* Plazas Disponibles </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_plaza === id) ? vselected = "selected" : vselected = " ";
                        $('#plazasDisponibles').append('<option value="' + data[x].id_plaza + '"' + vselected + '>' + data[x].id_plaza + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar la Plaza: " + e, " error", {progressBar: true, closeButton: true});
        }
    });  
}

/*=============================================
 COMBO PUESTOS
 =============================================*/
function puestos(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarPuestos",
        dataType: 'json',
        success: function (data) {
            
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                
                $('#puestos').empty().append("<option value=''>* Puestos </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for ( var x =0; x < data.length; x++) {
                        vselected = (data[x].id_puesto === id) ? vselected = "selected" : vselected = " ";
                        $('#puestos').append('<option value="' + data[x].id_puesto + '"' + vselected + '>' + "Nivel: " + data[x].nivel + " | " + data[x].puesto + '</option>');
                    }   
                } 
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar el Puesto" + e, " error", {progressBar: true, closeButton: true});
        }
    });
 }

/*=============================================
 BUSCAR LA PLAZA POR EL ID PLAZA
 =============================================*/
function buscarPlazaIdPlaza(id_plaza){
    $.ajax({
        type: "GET",
        url: "catalogos/buscarPlaza/" + id_plaza,
        dataType: 'json',
        success: function (data) {
            puestos(data.data.cat_puesto.id_puesto);
      }, error: function (e) {
            alert(e);
        }
    });
    
}

/*=============================================
 ASIGNA EL VALOR 1 A LA PLAZA DISPONIBLE
 =============================================*/
function asignarStatusPlazaDisponible(id_plaza){
    
    var datos = {"estatus_plaza_id": 1};
    $.ajax({
        type: "POST",
        url: "catalogos/editarPlaza/" + id_plaza,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            event.preventDefault();
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
}
/*=============================================
 ASIGNA EL VALOR 3 A LA PLAZA OCUPADA
 =============================================*/
function asignarStatusPlazaOcupada(id_plaza){
    
    var datos = {"estatus_plaza_id": 3};
    $.ajax({
        type: "POST",
        url: "catalogos/editarPlaza/" + id_plaza,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            event.preventDefault();
            
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
}

/*=============================================
 GUARDA EL REGISTRO HISTORICO DEL TRABAJADOR PLAZA
 =============================================*/
function guardarHistorico(id_tabajador_plaza,id_trabajador_DB,tipo_contrato_id_DB,tipo_nomina_id_DB,ubicacion_id_DB, areas_id_DB, plazasDisponibles_DB, plaza_movimientos_id_DB, observaciones_DB, fecha_inicial_DB, fecha_inicial){
    event.preventDefault();
 
    var datos = {"trabajador_plaza_id":id_tabajador_plaza, "trabajador_id":id_trabajador_DB, "tipo_contrato_id":tipo_contrato_id_DB, "tipo_nomina_id":tipo_nomina_id_DB, "ubicacion_id":ubicacion_id_DB,
                 "areas_id":areas_id_DB, "plaza_id":plazasDisponibles_DB, "observaciones":observaciones_DB, "plaza_movimientos_id":plaza_movimientos_id_DB, "fecha_inicial":fecha_inicial_DB, "fecha_final":fecha_inicial};
    $.ajax({
        type: "POST",
        url: "catalogos/guardaHistoricoTrabajadorPlaza",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            toastr['success'](data.data, "El registro se generó con Éxito");
            
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
    
}