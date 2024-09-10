document.addEventListener('DOMContentLoaded', () => {
    buscarValidacion();
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');
    tabs(id_trabajador);
    conContrato();
    activoNomina();
    activoPersonal();
    habilita();
    sancionSindical();
});

function tabs(id_trabajador) {
    $('#myTabTrabajador').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=trabajadorDatosGenerales('" + id_trabajador + "')>1.- DATOS DEL TRABAJADOR</a></li>&nbsp;" +
            "<li class='btn btn-primary' onclick=trabajadorBancarios('" + id_trabajador + "')>2.- DATOS BANCARIOS</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=trabajadorPuesto('" + id_trabajador + "')>3.- PLAZA</li>&nbsp;" +
            "<li><a href='#tabs-1' class='btn btn-info'>4.- VALIDACIÓN</a></li>&nbsp;"   +
            "<li class='btn btn-primary' onclick=trabajadorBeneficiarios('" + id_trabajador + "')>5.- BENEFICIARIOS - SEGURO DE VIDA</li>&nbsp;");
}
/*=============================================
 URL DATOS GENERALES DEL TRABAJADOR
 =============================================*/
function trabajadorDatosGenerales(id_trabajador) {
    window.location.href = 'trabajadorDatosGenerales?id_trabajador=' + id_trabajador;
}
/*=============================================
 URL DATOS BANCARIOS DEL TRABAJADOR
 =============================================*/
function trabajadorBancarios(id_trabajador) {
    window.location.href = 'trabajadorBancarios?id_trabajador=' + id_trabajador;
}
/*=============================================
 URL DATOS DE PLAZA Y PUESTO DEL TRABAJADOR
 =============================================*/
function trabajadorPuesto(id_trabajador) {
    window.location.href = 'trabajadorPuesto?id_trabajador=' + id_trabajador;
}
/*=============================================
 URL de validaciones del beneficiarios  
 =============================================*/
function trabajadorBeneficiarios(id_trabajador) {
    window.location.href = 'personaBeneficiarios?id_trabajador=' + id_trabajador;
}



//==================== LISTAR SI_NO CON CONTRATO ===========================
function conContrato(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarSi_No",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#con_contrato_si_no').empty().append("<option value=''>* Con Contrato </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id === id) ? vselected = "selected" : vselected = " ";
                        $('#con_contrato_si_no').append('<option value="' + data[x].id + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Con Contrato: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//==================== LISTAR SI_NO ACTIVO EN NOMINA ===========================
function activoNomina(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarSi_No",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#activo_en_nomina_si_no').empty().append("<option value=''>* Activo en Nómina </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id === id) ? vselected = "selected" : vselected = " ";
                        $('#activo_en_nomina_si_no').append('<option value="' + data[x].id + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Activo Nómina: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//==================== LISTAR SI_NO ACTIVO EN PERSONAL ===========================
function activoPersonal(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarSi_No",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#activo_en_personal_si_no').empty().append("<option value=''>* Activo en Personal </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id === id) ? vselected = "selected" : vselected = " ";
                        $('#activo_en_personal_si_no').append('<option value="' + data[x].id + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Activo Personal: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//==================== LISTAR SI_NO HABILITA ===========================
function habilita(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarSi_No",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#habilita_si_no').empty().append("<option value=''>* Habilita emición de gafete </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id === id) ? vselected = "selected" : vselected = " ";
                        $('#habilita_si_no').append('<option value="' + data[x].id + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Habilita Emición: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//==================== LISTAR SI_NO SANCION ===========================
function sancionSindical(id) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarSi_No",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#sancion_si_no').empty().append("<option value=''>* Sanción Sindical </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id === id) ? vselected = "selected" : vselected = " ";
                        $('#sancion_si_no').append('<option value="' + data[x].id + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Sanción Sindical: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 GUARDAR VALIDACIONES DEL TRABAJADOR
 =============================================*/

$("#formGuardarValidacion").submit(function (e) {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_trabajador = urlParams.get('id_trabajador');
    
    event.preventDefault();
    var id_validaciones = $("#id_validaciones").val();
    var con_contrato = $("#con_contrato_si_no").val();
    var activo_en_nomina = $("#activo_en_nomina_si_no").val();
    var activo_en_personal = $("#activo_en_personal_si_no").val();
    var habilita = $("#habilita_si_no").val();
    var sancion = $("#sancion_si_no").val();
  
    
    if ($.trim(con_contrato) === "" ) {
        return false;
    }
    if ($.trim(activo_en_nomina) === "") {
        return false;
    }
    if ($.trim(activo_en_personal) === "") {
        return false;
    }
    if ($.trim(habilita) === "") {
        return false;
    }
    if ($.trim(sancion) === "") {
        return false;
    }
    if ($.trim(id_trabajador) === "") {
        return false;
    }
    var datos = {"con_contrato_si_no":con_contrato , "activo_en_nomina_si_no": activo_en_nomina, "activo_en_personal_si_no": activo_en_personal, "habilita_si_no": habilita, "sancion_si_no": sancion,"trabajador_id":id_trabajador};
    
    var url_accion = "";

    if (id_validaciones === "" || id_validaciones === null) {
        
        url_accion = "trabajador/guardarValidacion";

    } else if (id_validaciones !== "" ||id_validaciones !== null) {
        url_accion = "trabajador/editarValidacion/" + id_trabajador;
    }
    
    $.ajax({
        type: "POST",
        url: url_accion,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            buscarValidacion(id_trabajador);

            toastr['success'](data.data, "Se guardó correctamente la Información");
            event.preventDefault();
            
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
});

/*=============================================
 BUSCAR LA VALIDACIÓN
 =============================================*/
function buscarValidacion() {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id = urlParams.get('id_trabajador');
  
    $.ajax({
        type: "GET",
        url: "trabajador/buscarValidacionTrabajador/" + id,
        dataType: 'json',
        success: function (data) {
            $('#id_validaciones').val(data.data.id_validaciones);
            conContrato(data.data.con_contrato_si_no);
            activoNomina(data.data.activo_en_nomina_si_no);
            activoPersonal(data.data.activo_en_personal_si_no);
            habilita(data.data.habilita_si_no);
            sancionSindical(data.data.sancion_si_no);
     
        },
        error: function (e) {
            alert(e);
        }
    });
}




/*=============================================
 URL listado de los trabajadores
 =============================================*/
function verTrabajadores() {
    window.location.href = 'trabajadoresAdmin';
}
