document.addEventListener('DOMContentLoaded', () => {
 
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');
    tabs(id_trabajador);
    cargaDatosBeneficiario(id_trabajador);
    console.log(id_trabajador);
});

function tabs(id_trabajador) {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=trabajadorDatosGenerales('" + id_trabajador + "')>1.- DATOS DEL TRABAJADOR</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=trabajadorBancarios('" + id_trabajador + "')>2.- DATOS BANCARIOS</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=trabajadorPuesto('" + id_trabajador + "')>3.- PLAZA</li>&nbsp;"+
            "<li class='btn btn-primary' onclick=verValidaciones('" + id_trabajador + "')>4.- VALIDACIÓN</li>&nbsp;" +
            "<li><a href='#tabs-5' class='btn btn-primary'>5.-BENEFICIARIOS - SEGURO DE VIDA</a></li>&nbsp;" );
            
    tipoBeneficiario();
};


function trabajadorDatosGenerales(id_trabajador) {
    window.location.href = 'trabajadorDatosGenerales?id_trabajador=' + id_trabajador;
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
function verTrabajadores() {
    window.location.href = 'trabajadoresAdmin';
}

function cargaDatosBeneficiario(id_trabajador) {
    $.ajax({
        type: "GET",
        url: "personas/buscarBeneficiario/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }

            if (data.data.length >= 1 && data.data.length <= 4) { // Longitud del objeto --> Recorre hasta la longitud de la consulta
                for (var i = 0; i < data.data.length; i++) {
                    $('#nombre_beneficiario' + (i + 1)).val(data.data[i].nombre);
                    $('#apellido_paterno' + (i + 1)).val(data.data[i].apellido_paterno);
                    $('#apellido_materno' + (i + 1)).val(data.data[i].apellido_materno);
                    $('#porcentajeBeneficiario' + (i + 1)).val(data.data[i].porcentaje);
                    $('#id_beneficiario' + (i + 1)).val(data.data[i].id_beneficiario);
                    parentesco(data.data[i].cat_Parentesco.id_parentesco, (i + 1));
                    tipoBeneficiario(data.data[i].cat_tipo_beneficiario.id_tipo_beneficiario, (i + 1));

                    //Habilita el botón "Eliminar" de los campos que ten gan datos
                    const btnEliminar = document.getElementById('btn_Bef' + (i + 1));
                    btnEliminar.disabled = false;
                }
                //Llenar combo de beneficiarios faltantes
                for (var j = data.data.length; j < 4; j++) { // For comienza desde data.data.length para llenar los combos faltantes
                    parentesco(0, (j + 1));
                    tipoBeneficiario(0, (j + 1));

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
                for (var i = 0; i < 4; i++) { //Llenar todos los combos vacíos 
                    parentesco(0, (i + 1));
                    tipoBeneficiario(0, (i + 1));

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


//******************************************************************************
//                             GUARDAR BENEFICIARIOS
//          Guarda o Edita datos dependiendo del "id_beneficiario" 
//          Si el beneficiario ya tiene un "ID" se edita el registro               
//       Si el beneficiario no tiene un "ID" se guarda como nuevo registro               
//******************************************************************************
function guardarBeneficiarios() {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_trabajador = urlParams.get('id_trabajador');

    var contador = 0;
    for (var i = 0; i <= 4; i++) { //CONTADOR DE BENEFICIARIOS --> Depende del número de "INPUTS NOMBRE" que contengan datos 
        var nombre_beneficiario = $("#nombre_beneficiario" + (i + 1)).val();
        if (nombre_beneficiario !== "" && nombre_beneficiario !== null && nombre_beneficiario !== undefined) {
            contador++;
        }
    }
    var datos = {"apellido_materno": "", "apellido_paterno": "", "cat_Parentesco": {"id_parentesco": 0}, "cat_tipo_beneficiario": {"id_tipo_beneficiario": 0}, "estatus": 1,
        "nombre": "", "trabajador_id": id_trabajador, "porcentaje": 0};

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
        var tipoBeneficiario = $("#cmbBeneficiario1").val();
        var id_beneficiario = $("#id_beneficiario1").val();

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

        if ($.trim(tipoBeneficiario) === "") {
            $("#alertaCmbBeneficiario1").append("<label class='text-danger'><small>Selecciona el tipo de beneficiario</small></label>");
            return false;
        }

        //Eliminar alertas cuando los campos sean válidos
        $("#alertaNombre1").html("");
        $("#alertaApellidoPat1").html("");
        $("#alertaApellidoMat1").html("");
        $("#alertaCmbParentesco1").html("");
        $("#alertaCmbBeneficiario1").html("");

        //Sumar los porcentajes 
        sumaPorcentaje = parseInt(porcentajeBeneficiario) + sumaPorcentaje;

        if (sumaPorcentaje === 100) { //Validar que el porcentaje de la suma siempre sea 100
            if (id_beneficiario === "" || id_beneficiario === null) {
                rutaGuardar = 'personas/guardarBeneficiarioPersona'; // Ruta para "Guardar" un nuevo beneficiario en la base

            } else {
                rutaGuardar = 'personas/editarPersonaBeneficiario/' + id_beneficiario; // Ruta para "Editar" registro (ya se encuentra en la base) 
            }

            //Se asigna al objeto los datos que se modificarán o guardarán
            datos["nombre"] = nombre_beneficiario.toString().toUpperCase();
            datos["apellido_paterno"] = apellido_paterno.toString().toUpperCase();
            datos["apellido_materno"] = apellido_materno.toString().toUpperCase();
            datos["porcentaje"] = porcentajeBeneficiario;
            datos.cat_Parentesco["id_parentesco"] = parentesco;
            datos.cat_tipo_beneficiario["id_tipo_beneficiario"] = tipoBeneficiario;
            arrGuardar.push({datos});

            // -------------- Invocar función para Ajax -------------------
            invocarAJAX(rutaGuardar, arrGuardar, id_trabajador);

            // Condición para verificar que la suma del porcentaje sea diferente a 100
        } else if (sumaPorcentaje !== 100) {
            toastr["warning"]("El porcentaje del beneficiario debe ser el 100%", "Aviso", {progressBar: true, closeButton: true});
            return false;
        }

        // Condición Para Guardar o Editar a Partir De Dos Beneficiarios
    } else if (contador !== 1) {
        var sumaPorcentaje = 0;

        for (var i = 0; i < contador; i++) {
            var datos = {"apellido_materno": "", "apellido_paterno": "", "cat_Parentesco": {"id_parentesco": 0}, "cat_tipo_beneficiario": {"id_tipo_beneficiario": 0}, "estatus": 1,
                "nombre": "", "trabajador_id": id_trabajador, "porcentaje": 0};

            var nombre_beneficiario = $("#nombre_beneficiario" + (i + 1)).val();
            var apellido_paterno = $("#apellido_paterno" + (i + 1)).val();
            var apellido_materno = $("#apellido_materno" + (i + 1)).val();
            var porcentajeBeneficiario = $("#porcentajeBeneficiario" + (i + 1)).val();
            var parentesco = $("#cmbParentesco" + (i + 1)).val();
            var tipoBeneficiario = $("#cmbBeneficiario" + (i + 1)).val();
            var id_beneficiario = $("#id_beneficiario" + (i + 1)).val();

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

            if ($.trim(tipoBeneficiario) === "") {
                $("#alertaCmbBeneficiario" + (i + 1)).append("<label class='text-danger'><small>Selecciona el tipo de beneficiario</small></label>");
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
            datos["id_trabajador"] = id_trabajador;
            datos.cat_Parentesco["id_parentesco"] = parentesco;
            datos.cat_tipo_beneficiario["id_tipo_beneficiario"] = tipoBeneficiario;

            if (id_beneficiario === null || id_beneficiario === "") {
                arrGuardar.push(datos); // Guardar nuevos registros (registros que no tienen un id_beneficiario)
            } else {
                datos["id_beneficiario"] = id_beneficiario;
                arrEditar.push(datos);//Lista para editar (registros que ya tienen un id_beneficiario)
            }
        }
        if (sumaPorcentaje !== 100) {
            toastr["warning"]("El porcentaje entre los beneficiarios debe sumar el 100%", "Aviso", {progressBar: true, closeButton: true});
            return false;
        }
        if (arrEditar.length !== 0) {
            for (var i = 0; i < arrEditar.length; i++) {
                rutaGuardar = 'personas/editarPersonaBeneficiario/' + arrEditar[i].id_beneficiario;
                var a = arrEditar[i];
                invocarAJAX(rutaGuardar, a, id_trabajador);
            }
        }
        if (arrGuardar.length > 1) {
            rutaGuardar = 'personas/guardarPersonasBeneficiario'; //Ruta para guardar más de un registro
            invocarAJAX(rutaGuardar, arrGuardar, id_trabajador);

        } else if (arrGuardar.length === 1) {
            rutaGuardar = 'personas/guardarBeneficiarioPersona'; //Ruta para guardar solo un registro
            invocarAJAX(rutaGuardar, arrGuardar[0], id_trabajador);
        }
    }
}
;

//******************************************************************************
//                             FUNCIÓN AJAX
//    Parametros
//      rutaGuardar -- Ruta del servicio que se va a ejecutar             
//      arrGuardar -- Contiene los datos para ejecutar el servicio  
//      id_persona -- Id de la persona en que se están realizando las acciones
//******************************************************************************
function invocarAJAX(rutaGuardar, datos, id_trabajador) {
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
            cargaDatosBeneficiario(id_trabajador);
            toastr['success']("Datos Guardados", "Aviso");

        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
;

//******************************************************************************************
//                      FUNCIÓN ELIMINAR BENEFICIARIO
//    Parametros
//      id_beneficiario -- Obtiene el id desde el html "personaBeneficiario"             
//      noBeneficiarioFormulario -- Número del input, de acuerdo al orden del formulario
//******************************************************************************************
function eliminarBeneficiario(id_beneficiario, noBeneficiarioFormulario) {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_trabajador = urlParams.get('id_trabajador');

    var datos = {"estatus": 0};

    $.ajax({
        type: "POST",
        url: "personas/editarEstatusBeneficiario/" + id_beneficiario,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),
        success: function (data) {
            toastr['error']("El beneficiario ha sido eliminado", "Aviso");
            cargaDatosBeneficiario(id_trabajador);

            $("#nombre_beneficiario" + noBeneficiarioFormulario).val("");
            $("#apellido_paterno" + noBeneficiarioFormulario).val("");
            $("#apellido_materno" + noBeneficiarioFormulario).val("");
            $("#porcentajeBeneficiario" + noBeneficiarioFormulario).val("");
            $("#id_beneficiario" + noBeneficiarioFormulario).val("");
        },
        error: function (e) {
            toastr["warning"]("Error al eliminar el Beneficiario : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}