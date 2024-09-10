
/* global bootstrap */

document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_persona = urlParams.get('id_persona');
    cargaDatosBeneficiario(id_persona);
    cargaDatosBeneficiarioSec(id_persona);
    tabs(id_persona);
  ;

});
function tabs(id_persona) {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=personaDatosGenerales('" + id_persona + "')>1.-DATOS GENERALES</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaContacto('" + id_persona + "')>2.-DATOS DE CONTACTO</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaDireccion('" + id_persona + "')>3.-DIRECCION</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaLicencia('" + id_persona + "')>4.-LICENCIA</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaDocumentos('" + id_persona + "')>5.-DOCUMENTOS</li>&nbsp;" +
            "<li class='btn btn-primary'  onclick=personaMedico('" + id_persona + "')>6.-DATOS MEDICOS</li>&nbsp;"+
            "<li><a href='#tabs-8' class='btn btn-info'>7.-CARTA DESIGNATARIA</a></li>&nbsp;");

}
 
function personaDatosGenerales(id_persona) {
    window.location.href = 'personaDatosGenerales?id_persona=' + id_persona;
}
function personaContacto(id_persona) {
    window.location.href = 'personaContacto?id_persona=' + id_persona;
}
function personaDireccion(id_persona) {
    window.location.href = 'personaDireccion?id_persona=' + id_persona;
}
function personaLicencia(id_persona) {
    window.location.href = 'personaLicencia?id_persona=' + id_persona;
}
function personaDocumentos(id_persona) {
    window.location.href = 'personaDocumentos?id_persona=' + id_persona;
}
function personaMedico(id_persona) {
    window.location.href = 'personaMedico?id_persona=' + id_persona;
}

function verCandidatos() {
    window.location.href = 'candidatos';
}

function verTrabajadores() {
    window.location.href = 'trabajadoresAdmin';
}


//******************************************************************************//
//                 FUNCIONES PARA BENEFICIARIOS PRIMARIOS                       // 
//******************************************************************************//


function cargaDatosBeneficiario(id_persona) {
  // console.log(id_persona);
    $.ajax({
        type: "GET",
        url: "personas/buscarBeneficiarioCarta/" + id_persona,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }

            if (data.data.length >= 1 && data.data.length <= 6) { // Longitud del objeto --> Recorre hasta la longitud de la consulta
                for (var i = 0; i < data.data.length; i++) {
                    $('#nombre_beneficiario' + (i + 1)).val(data.data[i].nombre);
                    $('#apellido_paterno' + (i + 1)).val(data.data[i].apellido_paterno);
                    $('#apellido_materno' + (i + 1)).val(data.data[i].apellido_materno);
                    $('#porcentajeBeneficiario' + (i + 1)).val(data.data[i].porcentaje);
                    $('#id_beneficiario' + (i + 1)).val(data.data[i].id_beneficiario_carta);
                    parentesco(data.data[i].cat_Parentesco.id_parentesco, (i + 1));
                    //tipoBeneficiario(data.data[i].cat_tipo_beneficiario.id_tipo_beneficiario, (i + 1));

                    //Habilita el botón "Eliminar" de los campos que ten gan datos
                    const btnEliminar = document.getElementById('btn_Bef' + (i + 1));
                    btnEliminar.disabled = false;
                }
                //Llenar combo de beneficiarios faltantes
                for (var j = data.data.length; j < 6; j++) { // For comienza desde data.data.length para llenar los combos faltantes
                    parentesco(0, (j + 1));
                    //tipoBeneficiario(0, (j + 1));

                    //Desactiva los botones "Eliminar" de los campos que esten vacíos
                    const btnEliminar = document.getElementById('btn_Bef' + (j + 1));
                    btnEliminar.disabled = true;

                    //Limpia los campos
                    $("#nombre_beneficiario" + (j + 1)).val("");
                    $("#apellido_paterno" + (j + 1)).val("");
                    $("#apellido_materno" + (j + 1)).val("");
                    $("#porcentajeBeneficiario" + (j + 1)).val("");
                    $('#id_beneficiario' + (j + 1)).val("");
                }
            } else if (data.data.length === 0) { // Condición para una consulta vacía 
                for (var i = 0; i < 6; i++) { //Llenar todos los combos vacíos 
                    parentesco(0, (i + 1));
                    //tipoBeneficiario(0, (i + 1));

                    //Desactiva los botones "Eliminar" de los campos que esten vacíos
                    const btnEliminar = document.getElementById('btn_Bef' + (i + 1));
                    btnEliminar.disabled = true;

                    //Limpia los campos
                    $("#nombre_beneficiario" + (j + 1)).val("");
                    $("#apellido_paterno" + (j + 1)).val("");
                    $("#apellido_materno" + (j + 1)).val("");
                    $("#porcentajeBeneficiario" + (j + 1)).val("");
                    $('#id_beneficiario' + (j + 1)).val("");
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos de Beneficiarios : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//******************************************************************************
//                             COMBO PARENTESCO
//******************************************************************************
function parentesco(id, i) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarDatosCat_Parentesco",
        dataType: 'json',
        success: function (data) {
//            console.log(data);
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                
            } else {
                $('#cmbParentesco' + i).empty().append("<option value=''>* Parentesco </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_parentesco === id) ? vselected = "selected" : vselected = " ";
                        $('#cmbParentesco' + i).append('<option value="' + data[x].id_parentesco + '"' + vselected + '>' + data[x].desc_parentesco + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Parentesco : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//******************************************************************************
//                         COMBO TIPO BENEFICIARIO
//******************************************************************************
/*
function tipoBeneficiario(id, i) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarDatosCat_Tipo_Beneficiario",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbBeneficiario' + i).empty().append("<option value=''>* Tipo Beneficiario </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_tipo_beneficiario === id) ? vselected = "selected" : vselected = " ";
                        $('#cmbBeneficiario' + i).append('<option value="' + data[x].id_tipo_beneficiario + '"' + vselected + '>' + data[x].desc_tipo_beneficiario + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Beneficiario : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
*/

//******************************************************************************
//                             GUARDAR BENEFICIARIOS
//          Guarda o Edita datos dependiendo del "id_beneficiario" 
//          Si el beneficiario ya tiene un "ID" se edita el registro               
//       Si el beneficiario no tiene un "ID" se guarda como nuevo registro               
//******************************************************************************
function guardarBeneficiarios() {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_persona = urlParams.get('id_persona');
//    console.log(id_persona);
    var contador = 0;
    for (var i = 0; i <= 6; i++) { //CONTADOR DE BENEFICIARIOS --> Depende del número de "INPUTS NOMBRE" que contengan datos 
        var nombre_beneficiario = $("#nombre_beneficiario" + (i + 1)).val();
        if (nombre_beneficiario !== "" && nombre_beneficiario !== null && nombre_beneficiario !== undefined) {
            contador++;
        }
    }
    
    var datos = {"apellido_materno": "", "apellido_paterno": "", "cat_Parentesco": {"id_parentesco": 0},  "tipo_beneficiario_carta_id": 1, "estatus": 1,
        "nombre": "", "persona_id": id_persona, "porcentaje": 0};
    //console.log(datos);
    var rutaGuardar = '';
    let arrEditar = [];
    let arrGuardar = [];

    var patron = /^[A-Za-záéíóúÁÉÍÓÚñ\s]+$/;

    // Condición Para Guardar o Editar Un Solo Beneficiario
    if (contador === 1) {
        var sumaPorcentaje = 0;
        var nombre_beneficiario = $("#nombre_beneficiario1").val();
        var apellido_paterno = $("#apellido_paterno1").val();
        var apellido_materno = $("#apellido_materno1").val();
        var porcentajeBeneficiario = $("#porcentajeBeneficiario1").val();
        var parentesco = $("#cmbParentesco1").val();
        //var tipoBeneficiario = $("#cmbBeneficiario1").val();
        var id_beneficiario_carta = $("#id_beneficiario1").val();

        //Validación de datos - Evitar datos nulos
        if ($.trim(nombre_beneficiario) === "") {
            $("#alertaNombre1").append(" <label class='text-danger'><small>Ingresa el Nombre del beneficiario</small></label>");
            return false;
        }

        if (patron.test(nombre_beneficiario) === false) {
            $("#alertaNombre1").append(" <label class='text-danger'><small>Solo ingresa letras para el nombre del beneficiario</small></label>");
            toastr["warning"]("Para los campos 'Nombre, Apellido Paterno y Apellido Materno' no esta permitido ingresar dígitos o caracteres diferentes a letras ", "Aviso", {progressBar: true, closeButton: true});
            return false;
        }

        if ($.trim(apellido_paterno) === "") {
            $("#alertaApellidoPat1").append(" <label class='text-danger'><small>Ingresa el Apellido Paterno</small></label>");
            return false;
        }

        if (patron.test(apellido_paterno) === false) {
            $("#alertaApellidoPat1").append(" <label class='text-danger'><small>Solo ingresa letras para el Apellido Paterno del beneficiario</small></label>");
            toastr["warning"]("Para los campos 'Nombre, Apellido Paterno y Apellido Materno' no esta permitido ingresar dígitos o caracteres diferentes a letras ", "Aviso", {progressBar: true, closeButton: true});
            return false;
        }

        if ($.trim(apellido_materno) === "") {
            $("#alertaApellidoMat1").append(" <label class='text-danger'><small>Ingresa el Apellido Materno</small></label>");
            return false;
        }

        if (patron.test(apellido_materno) === false) {
            $("#alertaApellidoMat1").append(" <label class='text-danger'><small>Solo ingresa letras para el Apellido Materno del beneficiario</small></label>");
            toastr["warning"]("Para los campos 'Nombre, Apellido Paterno y Apellido Materno' no esta permitido ingresar dígitos o caracteres diferentes a letras ", "Aviso", {progressBar: true, closeButton: true});
            return false;
        }

        if ($.trim(parentesco) === "") {
            $("#alertaCmbParentesco1").append("<label class='text-danger'><small>Selecciona el tipo de parentesco</small></label>");
            return false;
        }

        if ($.trim(porcentajeBeneficiario) === "") {
            $("#alertaPorcentaje1").append("<label class='text-danger'><small>Ingresa el Porcentaje</small></label>");
            return false;
        }

        /*if ($.trim(tipoBeneficiario) === "") {
            $("#alertaCmbBeneficiario1").append("<label class='text-danger'><small>Selecciona el tipo de beneficiario</small></label>");
            return false;
        }*/
        
        //Eliminar alertas cuando los campos sean válidos
        $("#alertaNombre1").html("");
        $("#alertaApellidoPat1").html("");
        $("#alertaApellidoMat1").html("");
        $("#alertaCmbParentesco1").html("");
        $("#alertaCmbBeneficiario1").html("");
        console.log(id_beneficiario_carta);
        //Sumar los porcentajes 
        sumaPorcentaje = parseInt(porcentajeBeneficiario) + sumaPorcentaje;

        if (sumaPorcentaje === 100) { //Validar que el porcentaje de la suma siempre sea 100
            if (id_beneficiario_carta === "" || id_beneficiario_carta === null) {
                
                rutaGuardar = 'personas/guardarBeneficiarioPersonaCarta'; // Ruta para "Guardar" un nuevo beneficiario en la base

            } else {
                rutaGuardar = 'personas/editarPersonaBeneficiarioCarta/' + id_beneficiario_carta; // Ruta para "Editar" registro (ya se encuentra en la base) 
            }

            //Se asigna al objeto los datos que se modificarán o guardarán
            datos["nombre"] = nombre_beneficiario.toString().toUpperCase();
            datos["apellido_paterno"] = apellido_paterno.toString().toUpperCase();
            datos["apellido_materno"] = apellido_materno.toString().toUpperCase();
            datos["porcentaje"] = porcentajeBeneficiario;
            datos.cat_Parentesco["id_parentesco"] = parentesco;
            //datos.cat_tipo_beneficiario["id_tipo_beneficiario"] = tipoBeneficiario;
            arrGuardar.push({datos});

            // -------------- Invocar función para Ajax -------------------
            invocarAJAX(rutaGuardar, arrGuardar, id_persona);

            // Condición para verificar que la suma del porcentaje sea diferente a 100
        } else if (sumaPorcentaje !== 100) {
            toastr["warning"]("El porcentaje del beneficiario debe ser el 100%", "Aviso", {progressBar: true, closeButton: true});
            return false;
        }

        // Condición Para Guardar o Editar a Partir De Dos Beneficiarios
    } else if (contador !== 1) {
        var sumaPorcentaje = 0;

        for (var i = 0; i < contador; i++) {
            var datos = {"apellido_materno": "", "apellido_paterno": "", "cat_Parentesco": {"id_parentesco": 0},"tipo_beneficiario_carta_id": 1, "estatus": 1,
                "nombre": "", "persona_id": id_persona, "porcentaje": 0};

            var nombre_beneficiario = $("#nombre_beneficiario" + (i + 1)).val();
            var apellido_paterno = $("#apellido_paterno" + (i + 1)).val();
            var apellido_materno = $("#apellido_materno" + (i + 1)).val();
            var porcentajeBeneficiario = $("#porcentajeBeneficiario" + (i + 1)).val();
            var parentesco = $("#cmbParentesco" + (i + 1)).val();
           // var tipoBeneficiario = $("#cmbBeneficiario" + (i + 1)).val();
            var id_beneficiario_carta = $("#id_beneficiario" + (i + 1)).val();
            console.log(id_beneficiario_carta);
            sumaPorcentaje = parseInt(porcentajeBeneficiario) + sumaPorcentaje; //Suma los valores del porcentaje de cada beneficiario

            //Validación de datos - Evitar datos nulos
            if ($.trim(nombre_beneficiario) === "") {
                $("#alertaNombre" + (i + 1)).append(" <label class='text-danger'><small>Ingresa el Nombre del beneficiario</small></label>");
                return false;
            }

            if (patron.test(nombre_beneficiario) === false) {
                $("#alertaNombre" + (i + 1)).append(" <label class='text-danger'><small>Solo ingresa letras para el nombre del beneficiario</small></label>");
                toastr["warning"]("Para los campos 'Nombre, Apellido Paterno y Apellido Materno' no esta permitido ingresar dígitos o caracteres diferentes a letras ", "Aviso", {progressBar: true, closeButton: true});
                return false;
            }

            if ($.trim(apellido_paterno) === "") {
                $("#alertaApellidoPat" + (i + 1)).append(" <label class='text-danger'><small>Ingresa el Apellido Paterno</small></label>");
                return false;
            }

            if (patron.test(apellido_paterno) === false) {
                $("#alertaApellidoPat" + (i + 1)).append(" <label class='text-danger'><small>Solo ingresa letras para el Apellido Paterno del beneficiario</small></label>");
                toastr["warning"]("Para los campos 'Nombre, Apellido Paterno y Apellido Materno' no esta permitido ingresar dígitos o caracteres diferentes a letras ", "Aviso", {progressBar: true, closeButton: true});
                return false;
            }

            if ($.trim(apellido_materno) === "") {
                $("#alertaApellidoMat" + (i + 1)).append(" <label class='text-danger'><small>Ingresa el Apellido Materno</small></label>");
                return false;
            }

            if (patron.test(apellido_materno) === false) {
                $("#alertaApellidoMat" + (i + 1)).append(" <label class='text-danger'><small>Solo ingresa letras para el Apellido Materno del beneficiario</small></label>");
                toastr["warning"]("Para los campos 'Nombre, Apellido Paterno y Apellido Materno' no esta permitido ingresar dígitos o caracteres diferentes a letras ", "Aviso", {progressBar: true, closeButton: true});
                return false;
            }

            if ($.trim(parentesco) === "") {
                $("#alertaCmbParentesco" + (i + 1)).append("<label class='text-danger'><small>Selecciona el tipo de parentesco</small></label>");
                return false;
            }

            if ($.trim(porcentajeBeneficiario) === "") {
                $("#alertaPorcentaje" + (i + 1)).append("<label class='text-danger'><small>Ingresa el Porcentaje</small></label>");
                return false;
            }

          

            //Eliminar alertas cuando los campos sean válidos
            $("#alertaNombre" + (i + 1)).html("");
            $("#alertaApellidoPat" + (i + 1)).html("");
            $("#alertaApellidoMat" + (i + 1)).html("");

            datos["nombre"] = nombre_beneficiario.toString().toUpperCase();
            datos["apellido_paterno"] = apellido_paterno.toString().toUpperCase();
            datos["apellido_materno"] = apellido_materno.toString().toUpperCase();
            datos["porcentaje"] = porcentajeBeneficiario;
            datos["id_persona"] = id_persona;
            datos.cat_Parentesco["id_parentesco"] = parentesco;
           

            if (id_beneficiario_carta === null || id_beneficiario_carta === "") {
                arrGuardar.push(datos); // Guardar nuevos registros (registros que no tienen un id_beneficiario)
            } else {
                datos["id_beneficiario_carta"] = id_beneficiario_carta;
                arrEditar.push(datos);//Lista para editar (registros que ya tienen un id_beneficiario)
            }
        }
        if (sumaPorcentaje !== 100) {
            toastr["warning"]("El porcentaje entre los beneficiarios debe sumar el 100%", "Aviso", {progressBar: true, closeButton: true});
            return false;
        }
        if (arrEditar.length !== 0) {
            for (var i = 0; i < arrEditar.length; i++) {
                rutaGuardar = 'personas/editarPersonaBeneficiarioCarta/' + arrEditar[i].id_beneficiario_carta;
                var a = arrEditar[i];
                invocarAJAX(rutaGuardar, a, id_persona);
            }
        }
        if (arrGuardar.length > 1) {
            rutaGuardar = 'personas/guardarPersonasBeneficiarioCarta'; //Ruta para guardar más de un registro
            invocarAJAX(rutaGuardar, arrGuardar, id_persona);

        } else if (arrGuardar.length === 1) {
            rutaGuardar = 'personas/guardarBeneficiarioPersonaCarta'; //Ruta para guardar solo un registro
            invocarAJAX(rutaGuardar, arrGuardar[0], id_persona);
        }
    }
  
};

//******************************************************************************
//                             FUNCIÓN AJAX
//    Parametros
//      rutaGuardar -- Ruta del servicio que se va a ejecutar             
//      arrGuardar -- Contiene los datos para ejecutar el servicio  
//      id_persona -- Id de la persona en que se están realizando las acciones
//******************************************************************************
function invocarAJAX(rutaGuardar, datos, id_persona) {
    var dts;
    

    if (datos.length === 1) {
        dts = datos[0].datos;
    } else if (datos.length > 1) {
        dts = datos;
    } else {
        dts = datos;
    }

    $.ajax({
        type: "POST",
        url: rutaGuardar,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(dts),
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            cargaDatosBeneficiario(id_persona);
            toastr['success']("Datos Guardados", "Aviso");
         // Cerrar el modal después de mostrar la alerta de "Datos Guardados"
            $("#agregarBenefiSecModal").modal('hide');
            $("#agregarCandidatoModal").modal('hide'); 
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos AQUI: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};

//******************************************************************************************
//                      FUNCIÓN ELIMINAR BENEFICIARIO
//    Parametros
//      id_beneficiario -- Obtiene el id desde el html "personaBeneficiario"             
//      noBeneficiarioFormulario -- Número del input, de acuerdo al orden del formulario
//******************************************************************************************
function eliminarBeneficiarioCarta(id_beneficiario_carta, noBeneficiarioFormulario) {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_persona = urlParams.get('id_persona');
   //console.log(id_persona);
    var datos = {"estatus": 0};

    $.ajax({
        type: "POST",
        url: "personas/editarEstatusBeneficiarioCarta/" + id_beneficiario_carta,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),
        success: function (data) {
            toastr['error']("El beneficiario ha sido eliminado", "Aviso");
            cargaDatosBeneficiario(id_persona);

            $("#nombre_beneficiario" + noBeneficiarioFormulario).val("");
            $("#apellido_paterno" + noBeneficiarioFormulario).val("");
            $("#apellido_materno" + noBeneficiarioFormulario).val("");
            $("#porcentajeBeneficiario" + noBeneficiarioFormulario).val("");
            $("#id_beneficiario_carta" + noBeneficiarioFormulario).val("");
        },
        error: function (e) {
            toastr["warning"]("Error al eliminar el Beneficiario : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};

//******************************************************************************//
//                 FUNCIONES PARA BENEFICIARIOS SECUNDARIOS                       // 
//******************************************************************************//

function cargaDatosBeneficiarioSec(id_persona) {
  // console.log(id_persona);
    $.ajax({
        type: "GET",
        url: "personas/buscarBeneficiarioCartaSec/" + id_persona,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }

            if (data.data.length >= 1 && data.data.length <= 6) { // Longitud del objeto --> Recorre hasta la longitud de la consulta
                for (var i = 0; i < data.data.length; i++) {
                    $('#nombre_beneficiarios' + (i + 1)).val(data.data[i].nombre);
                    $('#apellido_paternos' + (i + 1)).val(data.data[i].apellido_paterno);
                    $('#apellido_maternos' + (i + 1)).val(data.data[i].apellido_materno);
                    $('#porcentajeBeneficiarios' + (i + 1)).val(data.data[i].porcentaje);
                    $('#id_beneficiarios' + (i + 1)).val(data.data[i].id_beneficiario_carta);
                    parentescoSec(data.data[i].cat_Parentesco.id_parentesco, (i + 1));
                    //tipoBeneficiario(data.data[i].cat_tipo_beneficiario.id_tipo_beneficiario, (i + 1));


                    //Habilita el botón "Eliminar" de los campos que ten gan datos
                    const btnEliminar = document.getElementById('btn_Befs' + (i + 1));
                    btnEliminar.disabled = false;
                }
                //Llenar combo de beneficiarios faltantes
                for (var j = data.data.length; j < 6; j++) { // For comienza desde data.data.length para llenar los combos faltantes
                    parentescoSec(0, (j + 1));
                    //tipoBeneficiario(0, (j + 1));

                    //Desactiva los botones "Eliminar" de los campos que esten vacíos
                    const btnEliminar = document.getElementById('btn_Befs' + (j + 1));
                    btnEliminar.disabled = true;

                    //Limpia los campos
                    $("#nombre_beneficiarios" + (j + 1)).val("");
                    $("#apellido_paternos" + (j + 1)).val("");
                    $("#apellido_maternos" + (j + 1)).val("");
                    $("#porcentajeBeneficiarios" + (j + 1)).val("");
                    $('#id_beneficiarios' + (j + 1)).val("");
                }
            } else if (data.data.length === 0) { // Condición para una consulta vacía 
                for (var i = 0; i < 6; i++) { //Llenar todos los combos vacíos 
                    parentescoSec(0, (i + 1));
                    //tipoBeneficiario(0, (i + 1));

                    //Desactiva los botones "Eliminar" de los campos que esten vacíos
                    const btnEliminar = document.getElementById('btn_Befs' + (i + 1));
                    btnEliminar.disabled = true;

                    //Limpia los campos
                    $("#nombre_beneficiarios" + (j + 1)).val("");
                    $("#apellido_paternos" + (j + 1)).val("");
                    $("#apellido_maternos" + (j + 1)).val("");
                    $("#porcentajeBeneficiarios" + (j + 1)).val("");
                    $('#id_beneficiarios' + (j + 1)).val("");
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos de Beneficiarios : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};
function parentescoSec(id, i) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarDatosCat_Parentesco",
        dataType: 'json',
        success: function (data) {
//            console.log(data);
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                
            } else {
                $('#cmbParentescos' + i).empty().append("<option value=''>* Parentesco </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_parentesco === id) ? vselected = "selected" : vselected = " ";
                        $('#cmbParentescos' + i).append('<option value="' + data[x].id_parentesco + '"' + vselected + '>' + data[x].desc_parentesco + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Parentesco : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};

function guardarBeneficiariosSec() {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_persona = urlParams.get('id_persona');
//    console.log(id_persona);
    var contador = 0;
    for (var i = 0; i <= 6; i++) { //CONTADOR DE BENEFICIARIOS --> Depende del número de "INPUTS NOMBRE" que contengan datos 
        var nombre_beneficiario = $("#nombre_beneficiarios" + (i + 1)).val();
        if (nombre_beneficiario !== "" && nombre_beneficiario !== null && nombre_beneficiario !== undefined) {
            contador++;
        }
    }
    
    var datos = {"apellido_materno": "", "apellido_paterno": "", "cat_Parentesco": {"id_parentesco": 0},  "tipo_beneficiario_carta_id": 2, "estatus": 1,
        "nombre": "", "persona_id": id_persona, "porcentaje": 0};
    //console.log(datos);
    var rutaGuardar = '';
    let arrEditar = [];
    let arrGuardar = [];

    var patron = /^[A-Za-záéíóúÁÉÍÓÚñ\s]+$/;

    // Condición Para Guardar o Editar Un Solo Beneficiario
    if  (contador === 1) {
        var sumaPorcentaje = 0;
        var nombre_beneficiario = $("#nombre_beneficiarios1").val();
        var apellido_paterno = $("#apellido_paternos1").val();
        var apellido_materno = $("#apellido_maternos1").val();
        var porcentajeBeneficiario = $("#porcentajeBeneficiarios1").val();
        var parentesco = $("#cmbParentescos1").val();
        //var tipoBeneficiario = $("#cmbBeneficiario1").val();
        var id_beneficiario_carta = $("#id_beneficiarios1").val();

        //Validación de datos - Evitar datos nulos
        if ($.trim(nombre_beneficiario) === "") {
            $("#alertaNombres1").append(" <label class='text-danger'><small>Ingresa el Nombre del beneficiario</small></label>");
            return false;
        }

        if (patron.test(nombre_beneficiario) === false) {
            $("#alertaNombres1").append(" <label class='text-danger'><small>Solo ingresa letras para el nombre del beneficiario</small></label>");
            toastr["warning"]("Para los campos 'Nombre, Apellido Paterno y Apellido Materno' no esta permitido ingresar dígitos o caracteres diferentes a letras ", "Aviso", {progressBar: true, closeButton: true});
            return false;
        }

        if ($.trim(apellido_paterno) === "") {
            $("#alertaApellidoPats1").append(" <label class='text-danger'><small>Ingresa el Apellido Paterno</small></label>");
            return false;
        }

        if (patron.test(apellido_paterno) === false) {
            $("#alertaApellidoPats1").append(" <label class='text-danger'><small>Solo ingresa letras para el Apellido Paterno del beneficiario</small></label>");
            toastr["warning"]("Para los campos 'Nombre, Apellido Paterno y Apellido Materno' no esta permitido ingresar dígitos o caracteres diferentes a letras ", "Aviso", {progressBar: true, closeButton: true});
            return false;
        }

        if ($.trim(apellido_materno) === "") {
            $("#alertaApellidoMats1").append(" <label class='text-danger'><small>Ingresa el Apellido Materno</small></label>");
            return false;
        }

        if (patron.test(apellido_materno) === false) {
            $("#alertaApellidoMats1").append(" <label class='text-danger'><small>Solo ingresa letras para el Apellido Materno del beneficiario</small></label>");
            toastr["warning"]("Para los campos 'Nombre, Apellido Paterno y Apellido Materno' no esta permitido ingresar dígitos o caracteres diferentes a letras ", "Aviso", {progressBar: true, closeButton: true});
            return false;
        }

        if ($.trim(parentesco) === "") {
            $("#alertaCmbParentescos1").append("<label class='text-danger'><small>Selecciona el tipo de parentesco</small></label>");
            return false;
        }

        if ($.trim(porcentajeBeneficiario) === "") {
            $("#alertaPorcentajes1").append("<label class='text-danger'><small>Ingresa el Porcentaje</small></label>");
            return false;
        }

        /*if ($.trim(tipoBeneficiario) === "") {
            $("#alertaCmbBeneficiario1").append("<label class='text-danger'><small>Selecciona el tipo de beneficiario</small></label>");
            return false;
        }*/
        
        //Eliminar alertas cuando los campos sean válidos
        $("#alertaNombres1").html("");
        $("#alertaApellidoPats1").html("");
        $("#alertaApellidoMats1").html("");
        $("#alertaCmbParentescos1").html("");
        $("#alertaCmbBeneficiarios1").html("");
       // console.log(id_beneficiario_carta);
        //Sumar los porcentajes 
        sumaPorcentaje = parseInt(porcentajeBeneficiario) + sumaPorcentaje;

        if (sumaPorcentaje === 100) { //Validar que el porcentaje de la suma siempre sea 100
            if (id_beneficiario_carta === "" || id_beneficiario_carta === null) {
                
                rutaGuardar = 'personas/guardarBeneficiarioPersonaCarta'; // Ruta para "Guardar" un nuevo beneficiario en la base

            } else {
                rutaGuardar = 'personas/editarPersonaBeneficiarioCarta/' + id_beneficiario_carta; // Ruta para "Editar" registro (ya se encuentra en la base) 
            }

            //Se asigna al objeto los datos que se modificarán o guardarán
            datos["nombre"] = nombre_beneficiario.toString().toUpperCase();
            datos["apellido_paterno"] = apellido_paterno.toString().toUpperCase();
            datos["apellido_materno"] = apellido_materno.toString().toUpperCase();
            datos["porcentaje"] = porcentajeBeneficiario;
            datos.cat_Parentesco["id_parentesco"] = parentesco;
            //datos.cat_tipo_beneficiario["id_tipo_beneficiario"] = tipoBeneficiario;
            arrGuardar.push({datos});

            // -------------- Invocar función para Ajax -------------------
            invocarAJAX(rutaGuardar, arrGuardar, id_persona);

            // Condición para verificar que la suma del porcentaje sea diferente a 100
        } else if (sumaPorcentaje !== 100) {
            toastr["warning"]("El porcentaje del beneficiario debe ser el 100%", "Aviso", {progressBar: true, closeButton: true});
            return false;
        }

        // Condición Para Guardar o Editar a Partir De Dos Beneficiarios
    } else if (contador !== 1) {
        var sumaPorcentaje = 0;

        for (var i = 0; i < contador; i++) {
            var datos = {"apellido_materno": "", "apellido_paterno": "", "cat_Parentesco": {"id_parentesco": 0},"tipo_beneficiario_carta_id": 2, "estatus": 1,
                "nombre": "", "persona_id": id_persona, "porcentaje": 0};

            var nombre_beneficiario = $("#nombre_beneficiarios" + (i + 1)).val();
            var apellido_paterno = $("#apellido_paternos" + (i + 1)).val();
            var apellido_materno = $("#apellido_maternos" + (i + 1)).val();
            var porcentajeBeneficiario = $("#porcentajeBeneficiarios" + (i + 1)).val();
            var parentesco = $("#cmbParentescos" + (i + 1)).val();
           // var tipoBeneficiario = $("#cmbBeneficiario" + (i + 1)).val();
            var id_beneficiario_carta = $("#id_beneficiarios" + (i + 1)).val();
            console.log(id_beneficiario_carta);
            sumaPorcentaje = parseInt(porcentajeBeneficiario) + sumaPorcentaje; //Suma los valores del porcentaje de cada beneficiario

            //Validación de datos - Evitar datos nulos
            if ($.trim(nombre_beneficiario) === "") {
                $("#alertaNombres" + (i + 1)).append(" <label class='text-danger'><small>Ingresa el Nombre del beneficiario</small></label>");
                return false;
            }

            if (patron.test(nombre_beneficiario) === false) {
                $("#alertaNombres" + (i + 1)).append(" <label class='text-danger'><small>Solo ingresa letras para el nombre del beneficiario</small></label>");
                toastr["warning"]("Para los campos 'Nombre, Apellido Paterno y Apellido Materno' no esta permitido ingresar dígitos o caracteres diferentes a letras ", "Aviso", {progressBar: true, closeButton: true});
                return false;
            }

            if ($.trim(apellido_paterno) === "") {
                $("#alertaApellidoPats" + (i + 1)).append(" <label class='text-danger'><small>Ingresa el Apellido Paterno</small></label>");
                return false;
            }

            if (patron.test(apellido_paterno) === false) {
                $("#alertaApellidoPats" + (i + 1)).append(" <label class='text-danger'><small>Solo ingresa letras para el Apellido Paterno del beneficiario</small></label>");
                toastr["warning"]("Para los campos 'Nombre, Apellido Paterno y Apellido Materno' no esta permitido ingresar dígitos o caracteres diferentes a letras ", "Aviso", {progressBar: true, closeButton: true});
                return false;
            }

            if ($.trim(apellido_materno) === "") {
                $("#alertaApellidoMats" + (i + 1)).append(" <label class='text-danger'><small>Ingresa el Apellido Materno</small></label>");
                return false;
            }

            if (patron.test(apellido_materno) === false) {
                $("#alertaApellidoMats" + (i + 1)).append(" <label class='text-danger'><small>Solo ingresa letras para el Apellido Materno del beneficiario</small></label>");
                toastr["warning"]("Para los campos 'Nombre, Apellido Paterno y Apellido Materno' no esta permitido ingresar dígitos o caracteres diferentes a letras ", "Aviso", {progressBar: true, closeButton: true});
                return false;
            }

            if ($.trim(parentesco) === "") {
                $("#alertaCmbParentescos" + (i + 1)).append("<label class='text-danger'><small>Selecciona el tipo de parentesco</small></label>");
                return false;
            }

            if ($.trim(porcentajeBeneficiario) === "") {
                $("#alertaPorcentajes" + (i + 1)).append("<label class='text-danger'><small>Ingresa el Porcentaje</small></label>");
                return false;
            }

          

            //Eliminar alertas cuando los campos sean válidos
            $("#alertaNombres" + (i + 1)).html("");
            $("#alertaApellidoPats" + (i + 1)).html("");
            $("#alertaApellidoMats" + (i + 1)).html("");

            datos["nombre"] = nombre_beneficiario.toString().toUpperCase();
            datos["apellido_paterno"] = apellido_paterno.toString().toUpperCase();
            datos["apellido_materno"] = apellido_materno.toString().toUpperCase();
            datos["porcentaje"] = porcentajeBeneficiario;
            datos["id_persona"] = id_persona;
            datos.cat_Parentesco["id_parentesco"] = parentesco;
           

            if (id_beneficiario_carta === null || id_beneficiario_carta === "") {
                arrGuardar.push(datos); // Guardar nuevos registros (registros que no tienen un id_beneficiario)
            } else {
                datos["id_beneficiario_carta"] = id_beneficiario_carta;
                arrEditar.push(datos);//Lista para editar (registros que ya tienen un id_beneficiario)
            }
        }
        if (sumaPorcentaje !== 100) {
            toastr["warning"]("El porcentaje entre los beneficiarios debe sumar el 100%", "Aviso", {progressBar: true, closeButton: true});
            return false;
        }
        if (arrEditar.length !== 0) {
            for (var i = 0; i < arrEditar.length; i++) {
                rutaGuardar = 'personas/editarPersonaBeneficiarioCarta/' + arrEditar[i].id_beneficiario_carta;
                var a = arrEditar[i];
                invocarAJAX(rutaGuardar, a, id_persona);
            }
        }
        if (arrGuardar.length > 1) {
            rutaGuardar = 'personas/guardarPersonasBeneficiarioCarta'; //Ruta para guardar más de un registro
            invocarAJAX(rutaGuardar, arrGuardar, id_persona);

        } else if (arrGuardar.length === 1) {
            rutaGuardar = 'personas/guardarBeneficiarioPersonaCarta'; //Ruta para guardar solo un registro
            invocarAJAX(rutaGuardar, arrGuardar[0], id_persona);
        }
    }
  
};

//******************************************************************************************
//                      FUNCIÓN ELIMINAR BENEFICIARIO SECUNDARIO
//    Parametros
//      id_beneficiario -- Obtiene el id desde el html "personaBeneficiario"             
//      noBeneficiarioFormulario -- Número del input, de acuerdo al orden del formulario
//******************************************************************************************
function eliminarBeneficiarioCartaSec(id_beneficiario_carta, noBeneficiarioFormulario) {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_persona = urlParams.get('id_persona');
   console.log(id_persona);
    var datos = {"estatus": 0};

    $.ajax({
        type: "POST",
        url: "personas/editarEstatusBeneficiarioCarta/" + id_beneficiario_carta,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),
        success: function (data) {
            toastr['error']("El beneficiario ha sido eliminado", "Aviso");
            cargaDatosBeneficiario(id_persona);

            $("#nombre_beneficiarios" + noBeneficiarioFormulario).val("");
            $("#apellido_paternos" + noBeneficiarioFormulario).val("");
            $("#apellido_maternos" + noBeneficiarioFormulario).val("");
            $("#porcentajeBeneficiarios" + noBeneficiarioFormulario).val("");
            $("#id_beneficiario_cartas" + noBeneficiarioFormulario).val("");
        },
        error: function (e) {
            toastr["warning"]("Error al eliminar el Beneficiario : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
};
