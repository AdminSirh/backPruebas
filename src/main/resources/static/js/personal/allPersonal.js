/* global data, NaN, Document */
/* Cargar DOM antes de JS */
document.addEventListener('DOMContentLoaded', () => {
    genero();
    estadoCivil();
    $tablaCandidatos;

});
/*=============================================
 Ejectuar Tabla al hacer cambios en Personal 
 Muestra todos los candidatos registrados en una datatable
 =============================================*/

$tablaCandidatos = $('#noteTable').dataTable({
    "ajax": {
        url: "personas/candidatos",
        method: 'GET',
        dataSrc: ''
    },
    responsive: true,
    bProcessing: true,
    select: true,
    "language": {
        'processing': 'Procesando espera...',
        "sProcessing": "Procesando...",
        "sLengthMenu": "Mostrar _MENU_ registros",
        "sZeroRecords": "No se encontraron resultados",
        "sEmptyTable": " ",
        "sInfo": " _START_ al _END_ Total: _TOTAL_",
        "sInfoEmpty": " ",
        "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
        "sInfoPostFix": "",
        //"searchPlaceholder": "Buscar",
        "search": "Buscar",
        "paginate": {
            "previous": 'Anterior',
            "next": 'Siguiente'
        },
        "paging": false,
        "bPaging": false,
        "scrollY": "300px",
        "sUrl": "",
        "sInfoThousands": ",",
        "sLoadingRecords": "Cargando...",
        "oAria": {
            "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
            "sSortDescending": ": Activar para ordenar la columna de manera descendente"
        }
    },
    columns: [
        {data: "nombre"},
        {data: "apellido_paterno"},
        {data: "apellido_materno"},
        {data: "fecha_nacimiento"},
        {data: "cat_genero.desc_genero"},
        {data: "cat_estado_civil.desc_edo_civil"},
        {data: "curp"},
        {data: "fecha_captura", render: function (data, type, row) {
                var fechaCaptura = new Date(data);
                return fechaCaptura.toLocaleDateString();
            }},
        //{defaultContent: "<button type='button' class='btn btn-outline-primary' onclick='EditarPersona('" + id_persona + "')'><span class='fa fa-edit'></span>Editar</button>"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="EditarPersona(' + r.id_persona + ')"><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Expediente", width: 90, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-success" onclick="funcionExpediente(' + r.id_persona + ')"><span class="fa fa-id-card-alt"></span>Asignar</button>';
                return he;
            }
        }

    ]
});

/*=============================================
 AGREGAR CANDIDATOS
 Agrega datos basicos del candidato en la tabla de persona
 =============================================*/
$("#FormGuardarCandidatos").submit(function (e) {
    event.preventDefault();
    var nombre = $("#nombre_candidato").val().toUpperCase();
    var apellido_paterno = $("#apellido_paterno_candidato").val().toUpperCase();
    var apellido_materno = $("#apellido_materno_candidato").val().toUpperCase();
    var genero_id = $("#cmbGenero").val();
    var estado_civil_id = $("#cmbEstadoCivil").val();
    var curp = $("#curp_candidato").val();

    if ($.trim(nombre) === "") {
        $("#alertaNombreCandidato").append(" <label class='text-danger'><small>El campo 'Nombre' no puede estar vacio ingresa los datos correctos</small></label>");
        return false;
    }
    if ($.trim(curp) === "") {
        return false;
    }
    if (!curpValida(curp)) { 
       toastr["warning"]("El CURP no tiene un formato válido o ya existe en la base de datos", "Aviso", {progressBar: true, closeButton: true});
       return false;
    } 
    if ($.trim(genero_id) === "") {
        return false;
    }
    if ($.trim(estado_civil_id) === "") {
        return false;
    }
    let fecha;
    let date = new Date();
    let day = date.getDate();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();

    if (month < 10) {

        fecha = year + "-0" + month + "-" + day;
        
    } else {
        fecha = year + "-" + month + "-" + day;
    }

    var datos = {"nombre": nombre, "apellido_paterno": apellido_paterno, "apellido_materno": apellido_materno,
        "cat_genero": {"id_genero": genero_id}, "cat_estado_civil": {"id_edo_civil": estado_civil_id}, "curp": curp};

    $.ajax({
        type: "POST",
        url: "personas/guardarPersona",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarCandidatoModal").modal('hide');
            completarPersona();
        },
        error: function (e) {
            
            toastr['error'](e.responseJSON.messages, "Aviso");
            $("#personaAntecedente").html("<strong><label class='text-danger'>Candidato encontrado con este CURP</label></strong><br> <strong> Apellido Paterno:</strong> " + e.responseJSON.data.apellido_paterno + "<strong> Apellido Materno:</strong> " + e.responseJSON.data.apellido_materno + "<strong> <br>Nombre(s):</strong> " + e.responseJSON.data.nombre + " <br><button type='button' class='btn btn-outline-primary' onclick='EditarPersona(" + e.responseJSON.data.id_persona + ")'><span class='fa fa-edit'></span>Editar</button>");
        }
    });
});

/*=============================================
 VALIDA INPUT FORMATO CURP
 =============================================*/
function validarInput(input) {
    var curp = input.value.toUpperCase(),
            resultado = document.getElementById("resultado"),
            valido = "No válido";

    if (curpValida(curp)) {
        valido = "Válido";
        resultado.classList.add("ok");
    } else {
        resultado.classList.remove("ok");
    }

    resultado.innerText = "CURP: " + curp + "\nFormato: " + valido;
}

/*=============================================
 Algoritmo para comprobar que el curp es valido 
 =============================================*/

function curpValida(curp) {
    var re = /^([A-Z][AEIOUX][A-Z]{2}\d{2}(?:0\d|1[0-2])(?:[0-2]\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\d])(\d)$/,
            validado = curp.match(re);

    if (!validado)  //Coincide con el formato general?
        return false;

    //Validar que coincida el dígito verificador
    function digitoVerificador(curp17) {
        //Fuente https://consultas.curp.gob.mx/CurpSP/
        var diccionario = "0123456789ABCDEFGHIJKLMNÑOPQRSTUVWXYZ",
                lngSuma = 0.0,
                lngDigito = 0.0;
        for (var i = 0; i < 17; i++)
            lngSuma = lngSuma + diccionario.indexOf(curp17.charAt(i)) * (18 - i);
        lngDigito = 10 - lngSuma % 10;
        if (lngDigito == 10)
            return 0;
        return lngDigito;
    }
    if (validado[2] != digitoVerificador(validado[1]))
        return false;

    return true; //Validado
}
/*=============================================
 EDITAR PERSONA
 Nos envia a la pagina de persona Datos Generales para edita esa informacion
 =============================================*/
function EditarPersona(id_persona) {
    window.location.href = 'personaDatosGenerales?id_persona=' + id_persona;
}

/*=============================================
 COMPLETAR DATOS DEL CANDIDATO
 Obtenermos los datos de la ultima persona registada para completar sus datos
 =============================================*/
function completarPersona() {
    $.ajax({
        type: "GET",
        url: "personas/ultimoIdPersona",
        success: function (data) {
            var id_persona = data.data;
            window.location.href = 'personaDatosGenerales?id_persona=' + id_persona;
            toastr['success'](data.data, "Aviso");
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
}


/*=============================================
 COMBO GENERO
 =============================================*/
function genero() {
    $.ajax({
        type: "GET",
        url: "catalogos/listarGenero",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbGenero').empty().append("<option value=''>* Genero</option> ");
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        $('#cmbGenero').append('<option value="' + data[x].id_genero + '">' + data[x].desc_genero + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Genero : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 COMBO ESTADO CIVIL
 =============================================*/
function estadoCivil() {
    $.ajax({
        type: "GET",
        url: "catalogos/listarEdoCivil",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#cmbEstadoCivil').empty().append("<option value=''>* Estado Civil</option> ");
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        $('#cmbEstadoCivil').append('<option value="' + data[x].id_edo_civil + '">' + data[x].desc_edo_civil + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Estado Civil : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


/*=============================================
 Asignar expediente modal
 Validamos si los datos estan completos para poder asignar expediente
 =============================================*/
function funcionExpediente(id_persona) {
    $("#agregarExpedienteModal").modal("toggle");
    id_grado(id_persona);
    ultimoExpediente();
    ContadorFaltantesPersona(id_persona);
    ContadorFaltantesContacto(id_persona);
    ContadorFaltantesDireccion(id_persona);
    ContadorDocumentosFaltantes(id_persona);
    ContadorFaltantesBeneficiarios(id_persona);
    ContadorFaltantesMedicos(id_persona);

    $("#btn_Guardar").click(function (e) {
        $('#alertaDatosFaltantes').html("");
        if (id_persona !== null) {
            event.preventDefault();
            var faltantesPersona = $("#contadorPersona").val();
            var faltantesContacto = $("#contadorContacto").val();
            var faltantesDireccion = $("#contadorDireccion").val();
            var faltantesDocumentos = $("#contadorDocumentos").val();
            var faltantesBeneficiarios = $("#contadorBeneficiarios").val();
            var faltantesMedicos = $("#contadorMedicos").val();
            var totalFaltantes = Number(faltantesPersona) + Number(faltantesContacto) + Number(faltantesDireccion) + Number(faltantesDocumentos) + Number(faltantesBeneficiarios) + Number(faltantesMedicos);
            if (totalFaltantes === 0) {
                guardarExpediente(id_persona);
            
            } else {
                toastr["warning"]("Proporciona los datos faltantes para poder asignar un expediente al candidato.", " Aviso", {progressBar: true, closeButton: true});
                datosFaltantesPersona(id_persona);
                datosFaltantesContacto(id_persona);
                datosFaltantesDireccion(id_persona);
                documentosFaltantes(id_persona);
                datosFaltantesBeneficiarios(id_persona);
                datosFaltantesMedicos(id_persona);
                $('#alertaDatosFaltantes').append("<br><label class='text-danger'><small><em>  No se puede asignar el 'Expediente' porque existen datos faltantes:</em> </small></label>");
                jQuery('#btn_Guardar').prop('disabled', true);
            }
        }
        id_persona = null;
    });
}

/*=============================================
 Valida si hacen falta datos en datos generales de la persona
 =============================================*/
function datosFaltantesPersona(id_persona) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "personas/buscarPersona/" + id_persona,
        dataType: 'json',
         success: function (data) {
            $('#datos_faltantesPersona').html(""); //---->
            var faltantesPersona = $("#contadorPersona").val();
            //Objeto para mostrar valores faltantes del formulario
            var objeto = {"nombre": "Nombre", "apellido_paterno": 'Apellido Paterno', "apellido_materno": 'Apellido Materno', "cat_estado_civil": 'Estado Civil',
                "cat_estudios": 'Grado Máximo de Estudios', "cat_genero": 'Género', "curp": "CURP", "cat_nacionalidad": 'Nacionalidad', "rfc": 'RFC', "num_imss": 'N° Afiliación IMSS',
                "fecha_nacimiento": 'Fecha Nacimiento',
                "cat_cargo_admon_pub_si_no": 'Cargo en Admon. Pública', "cat_estado": 'Lugar de Nacimiento', "cat_hijos_si_no": '¿Tiene Hijos?',
                "cat_credito_infonavit_si_no": 'Crédito INFONAVIT', "cat_inhabilitado_admon_pub_si_no": 'Inhabilitación Admon. Pública'};
            var prueba = data.data;
            if (faltantesPersona >= 1) {
                $('#datos_faltantesPersona').append("<label class='text-primary'><small><em>  Datos Generales:</em> </small></label>");
            }
            for (let key in prueba) {
                if (prueba[key] === null && key !== "fecha_captura" && key !== "fecha_modificacion" && key !== "fecha_inactividad" || prueba[key] === "") {
                    verificarDatoFormulario(objeto, key, "#datos_faltantesPersona");
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar datos del candidato : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

function verificarDatoFormulario(objeto, key, datosFaltantes) {
    for (let campo in objeto) {
        if (key === campo) {
            $(datosFaltantes).append("<ul style='line-height: 1px;'><li><small>" + objeto[campo] + "</small></li></ul>");
        }
    }
}
/*=============================================
 Valida si hacen falta datos en contacto de la persona
 =============================================*/
function datosFaltantesContacto(id_persona) {
    $.ajax({
        type: "GET",
        url: "personas/buscarContacto/" + id_persona,
        dataType: 'json',
        success: function (data) {
            $('#datos_faltantesContacto').html("");
            var objetoDatos = {"mail": "Correo Electrónico", "nombre_emergencias": "Nombre Contacto emergencias", "telefono_celular": "Teléfono Celular",
                "telefono_emergencias": "Telefono de emergencia", "telefono_particular": "Teléfono Particular"};

            var faltantesContacto = $("#contadorContacto").val();

            if (faltantesContacto >= 1) {
                $('#datos_faltantesContacto').append("<label class='text-primary'><small><em>  Datos De Contacto:</em> </small></label>");
            }

            if (data.data === null) {
                $('#datos_faltantesContacto').append("<ul style='line-height: 0px;'><li><small>Correo Electrónico</small></li>");
                $('#datos_faltantesContacto').append("<ul style='line-height: 0px;'><li><small>Teléfono Celular</small></li>");
                $('#datos_faltantesContacto').append("<ul style='line-height: 0px;'><li><small>Teléfono Particular</small></li>");
                $('#datos_faltantesContacto').append("<ul style='line-height: 0px;'><li><small>Nombre Contacto emergencias </small></li>");
                $('#datos_faltantesContacto').append("<ul style='line-height: 0px;'><li><small>Telefono de emergencia</small></li></ul>");

            } else {
                for (let key in data.data) {
                    if (data.data[key] === null) {
                        verificarDatoFormulario(objetoDatos, key, "#datos_faltantesContacto");
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos de contacto : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 Valida si hacen falta datos en direccion de la persona
 =============================================*/
function datosFaltantesDireccion(id_persona) {
    $.ajax({
        type: "GET",
        url: "personas/buscarDireccion/" + id_persona,
        dataType: 'json',
        success: function (data) {
            $('#datos_faltantesDireccion').html("");
            var objetoDatos = {"calle": "Calle", "num_exterior": "Número Exterior", "num_interior": "Número Interior",
                "colonia_id": "Colonia"};

            var faltantesDireccion = $("#contadorDireccion").val();
            if (faltantesDireccion >= 1) {
                $('#datos_faltantesDireccion').append("<label class='text-primary'><small><em>  Datos Direccion:</em> </small></label>");
            }

            if (data.data === null) {
                $('#datos_faltantesDireccion').append("<ul style='line-height: 0px;'><li><small>Código Postal</small></li>");
                $('#datos_faltantesDireccion').append("<ul style='line-height: 0px;'><li><small>Estado</small></li>");
                $('#datos_faltantesDireccion').append("<ul style='line-height: 0px;'><li><small>Municipio/Alcaldia</small></li>");
                $('#datos_faltantesDireccion').append("<ul style='line-height: 0px;'><li><small>Colonia</small></li>");
                $('#datos_faltantesDireccion').append("<ul style='line-height: 0px;'><li><small>Calle</small></li></ul>");
                $('#datos_faltantesDireccion').append("<ul style='line-height: 0px;'><li><small>Número Exterior</small></li></ul>");
                $('#datos_faltantesDireccion').append("<ul style='line-height: 0px;'><li><small>Número Interior</small></li></ul>");

            } else {
                // $('#datos_faltantesDireccion').append("<label class='text-primary'><small><em>  Datos Direccion:</em> </small></label>");
                for (let key in data.data) {
                    if (data.data[key] === null) {
                        verificarDatoFormulario(objetoDatos, key, "#datos_faltantesDireccion");
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar datos de dirección : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 Valida si hacen falta datos en documnentos de la persona
 =============================================*/
function documentosFaltantes(id_persona) {
    $.ajax({
        type: "GET",
        url: "documentacion/listarDocumentoCargados/" + id_persona,
        dataType: 'json',
        success: function (data) {
            var id_documentos = [];
            $('#documentos_faltantes').html("");
            if (data.data.length > 1) {
                for (let i = 0; i < data.data.length; i++) {
                    if (data.data[i].cat_tipo_documento.id_tipo_documento === 1 || data.data[i].cat_tipo_documento.id_tipo_documento === 2 || data.data[i].cat_tipo_documento.id_tipo_documento === 3 || data.data[i].cat_tipo_documento.id_tipo_documento === 4) {
                        id_documentos.push(data.data[i].cat_tipo_documento.id_tipo_documento);
                    }
                }
                listarDocumentos(id_documentos);
            } else if (data.data.length === 1) {
                id_documentos.push(data.data[0].cat_tipo_documento.id_tipo_documento);
                listarDocumentos(id_documentos);
            } else if (data.data.length === 0) {
                
                $('#documentos_faltantes').append("<label class='text-primary'><small><em>  Documentación Faltante:</em> </small></label>");
                $('#documentos_faltantes').append("<ul style='line-height: 0px;'><li><small>INE (Identificacion)</small></li></ul>");
                $('#documentos_faltantes').append("<ul style='line-height: 0px;'><li><small>Acta de Nacimiento</small></li></ul>");
                $('#documentos_faltantes').append("<ul style='line-height: 0px;'><li><small>Comprobante de domicilio</small></li></ul>");
                $('#documentos_faltantes').append("<ul style='line-height: 0px;'><li><small>Comprobante de estudios</small></li></ul>");
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar la documetación : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


/*=============================================
 Valida si hacen falta datos en beneficiarios de la persona
 =============================================*/
function datosFaltantesBeneficiarios(id_persona) {
    $.ajax({
        type: "GET",
        url: "personas/listarBeneficiarioPrimarios/" + id_persona,
        dataType: 'json',
        success: function (data) {
            $('#datos_faltantesBeneficiarios').html("");
            var faltantesBeneficiarios = $("#contadorBeneficiarios").val();
            if (faltantesBeneficiarios >= 1) {
                $('#datos_faltantesBeneficiarios').append("<label class='text-primary'><small><em>  Datos Beneficiarios:</em> </small></label>");
            }

            if (data === undefined) {
                $('#datos_faltantesBeneficiarios').append("<ul style='line-height: 0px;'><li><small>Nombre</small></li>");
                $('#datos_faltantesBeneficiarios').append("<ul style='line-height: 0px;'><li><small>Apellido Paterno</small></li>");
                $('#datos_faltantesBeneficiarios').append("<ul style='line-height: 0px;'><li><small>Apellido Materno</small></li>");
                $('#datos_faltantesBeneficiarios').append("<ul style='line-height: 0px;'><li><small>Parentesco</small></li>");
                $('#datos_faltantesBeneficiarios').append("<ul style='line-height: 0px;'><li><small>Porcentaje</small></li>");
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar datos de Beneficiario : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 Valida si hacen falta datos en medico de la persona
 =============================================*/
function datosFaltantesMedicos(id_persona) {
    $.ajax({
        type: "GET",
        url: "personas/buscarMedico/" + id_persona,
        dataType: 'json',
        success: function (data) {
            $('#datos_faltantesMedicos').html("");
            var objetoDatos = {"altura": "Altura", "peso": "Peso", "tipo_sangre_id": "Tipo de Sangre", "enfermedades": "Enfermedades", "alergias": "Alergias", "medico_cabecera": "Medico de cabecera",
                "telefono_medico": "Telefono del médico", "contacto_caso_accidente": "Contacto en caso de emergencia", "clinica": "Clinica"};
            var faltantesMedicos = $("#contadorMedicos").val();
            if (faltantesMedicos >= 1) {
                $('#datos_faltantesMedicos').append("<label class='text-primary'><small><em>  Datos Médicos:</em> </small></label>");
            }
            if (data.data === null) {
                $('#datos_faltantesMedicos').append("<ul style='line-height: 0px;'><li><small>Altura</small></li>");
                $('#datos_faltantesMedicos').append("<ul style='line-height: 0px;'><li><small>Peso</small></li>");
                $('#datos_faltantesMedicos').append("<ul style='line-height: 0px;'><li><small>Tipo de Sangre</small></li>");
                $('#datos_faltantesMedicos').append("<ul style='line-height: 0px;'><li><small>Enfermedades</small></li>");
                $('#datos_faltantesMedicos').append("<ul style='line-height: 0px;'><li><small>Alergias</small></li>");
                $('#datos_faltantesMedicos').append("<ul style='line-height: 0px;'><li><small>Medico de cabecera</small></li>");
                $('#datos_faltantesMedicos').append("<ul style='line-height: 0px;'><li><small>Telefono del médico</small></li>");
                $('#datos_faltantesMedicos').append("<ul style='line-height: 0px;'><li><small>Contacto en caso de emergencia</small></li>");
                $('#datos_faltantesMedicos').append("<ul style='line-height: 0px;'><li><small>Clinica</small></li>");
            } else {
                for (let key in data.data) {
                    if (data.data[key] === null) {
                        verificarDatoFormulario(objetoDatos, key, "#datos_faltantesMedicos");
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar datos de Medicos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 Listamos los documentos faltantes
 =============================================*/
function listarDocumentos(id_documentos) {
    $.ajax({
        type: "GET",
        url: "documentacion/listarTiposDocumento",
        dataType: 'json',
        success: function (data) {
            
            $('#documentos_faltantes').html("");
            
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            $('#documentos_faltantes').append("<label class='text-primary'><small><em>  Documentación Faltante:</em> </small></label>");
            for (var i = 0; i < data.data.length; i++) {
                if (data.data[i].id_tipo_documento !== 5) {
                    if (!id_documentos.includes(data.data[i].id_tipo_documento)) {
                        $('#documentos_faltantes').append("<ul style='line-height: 0px;'><li><small>" + data.data[i].documento + "</small></li></ul>");
                    }
                }
                
            }
        },
        error: function (e) {
            alert(e);
        }
    });
}

//*******************************************************************************************************
//                              Cuenta datos que faltan de la persona
//*******************************************************************************************************
function ContadorFaltantesPersona(id_persona) {
    $.ajax({
        type: "GET",
        url: "personas/conteoDatosPersona/" + id_persona,
        dataType: 'json',
        success: function (data) {
            event.preventDefault();
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            $('#contadorPersona').val(data.data);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//*******************************************************************************************************
//                              Cuenta datos que faltan de contacto
//*******************************************************************************************************
function ContadorFaltantesContacto(id_persona) {
    $.ajax({
        type: "GET",
        url: "personas/conteoDatosContacto/" + id_persona,
        dataType: 'json',
        success: function (data) {
            
            event.preventDefault();
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            $('#contadorContacto').val(data.data);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//*******************************************************************************************************
//                              Cuenta datos que faltan de direccion
//*******************************************************************************************************
function ContadorFaltantesDireccion(id_persona) {
    $.ajax({
        type: "GET",
        url: "personas/conteoDatosDireccion/" + id_persona,
        dataType: 'json',
        success: function (data) {
            event.preventDefault();
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            $('#contadorDireccion').val(data.data);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

//*******************************************************************************************************
//                              Cuenta documentos que faltan
//*******************************************************************************************************
function ContadorDocumentosFaltantes(id_persona) {
    $.ajax({
        type: "GET",
        url: "documentacion/conteoDocumentos/" + id_persona,
        dataType: 'json',
        success: function (data) {
            event.preventDefault();
            if ($.isEmptyObject(data)) {
                toastr["error"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            $('#contadorDocumentos').val(data.data);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


//*******************************************************************************************************
//                              Cuenta datos que faltan de Beneficiarios
//*******************************************************************************************************
function ContadorFaltantesBeneficiarios(id_persona) {
    $.ajax({
        type: "GET",
        url: "personas/conteoDatosBeneficiariosCarta/" + id_persona,
        dataType: 'json',
        success: function (data) {
            event.preventDefault();
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            $('#contadorBeneficiarios').val(data.data);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


//*******************************************************************************************************
//                              Cuenta datos que faltan de Médico
//*******************************************************************************************************
function ContadorFaltantesMedicos(id_persona) {
    $.ajax({
        type: "GET",
        url: "personas/conteoDatosMedicos/" + id_persona,
        dataType: 'json',
        success: function (data) {
            event.preventDefault();
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            $('#contadorMedicos').val(data.data);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

function id_grado(id_persona) {
    $.ajax({
        type: "GET",
        url: "documentacion/ObtenGrado/" + id_persona,
        dataType: 'json',
        success: function (data) {
            event.preventDefault();
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            $('#idGrado').val(data.data);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 Asignar expediente Guardar
 =============================================*/
function guardarExpediente(id_persona){
    event.preventDefault();
    var numero_expediente = $('#expediente').val();
    var datos = {"numero_expediente": numero_expediente};
    $.ajax({
        type: "POST",
        url: "catalogos/guardarExpediente",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            ultimoIdExpediente(id_persona);
            
            
            toastr['success'](data.data, "Aviso");
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

///*=============================================
// Guardar en tabla trabajador al asignar expediente 
// =============================================*/
function guardarTrabajador(id_persona,id_expediente) {
    event.preventDefault();
    var datos = {"cat_expediente": {"id_expediente": id_expediente}, "persona": {"id_persona": id_persona}};
    
    $.ajax({
        type: "POST",
        url: "trabajador/guardarTrabajador",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            updateCandidatoExp(id_persona);
            completarTrabajador();
        },
        error: function (e) {
            toastr['error']("El expediente no ha podido ser asignado", "Aviso");
        }
    });
}

/*==================== ACTUALIZAR EXPEDIENTE_SI_NO PERSONA =====================
 Al asignar expediente se actualiza la columna expediente si_no
 ===============================================================================*/
function updateCandidatoExp(id_persona) {
    event.preventDefault();
    var datos = {"expediente_si_no": 1};

    $.ajax({
        type: "POST",
        url: "personas/editarPersonaExpediente/" + id_persona,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información para actualizar el expediente...", "Aviso", {progressBar: true, closeButton: true});
            }
        },
        error: function (e) {
            toastr['error']("No se actualizo el expediente del candidato", "Aviso");
        }
    });
}

/*=============================================
 COMPLETAR DATOS DEL TRABAJADOR
 Obtenermos los datos de la ultima trabajador registada para completar sus datos
 =============================================*/
function completarTrabajador() {
    $.ajax({
        type: "GET",
        url: "trabajador/ultimoIdTrabajador",
        success: function (data) {
            var id_trabajador = data.data;
            window.location.href = 'trabajadorDatosGenerales?id_trabajador=' + id_trabajador;
            //toastr['success'](data.data, "Aviso");
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
}

/*=============================================
 LIMPIAR ALERTA DATOS FALTANTES AL CERRAR MODAL
 =============================================*/
$("#btn_Cancelar_Exp").on("click", function (event) {
    $("#datos_faltantesPersona").html("");
    $("#datos_faltantesContacto").html("");
    $("#datos_faltantesDireccion").html("");
    $("#documentos_faltantes").html("");
    $("#datos_faltantesBeneficiarios").html("");
    $("#datos_faltantesMedicos").html("");
    $("#alertaDatosFaltantes").html("");
    jQuery('#btn_Guardar').prop('disabled', false);
});

/*=============================================
 LIMPIAR FORMULARIO
 =============================================*/
$("#btn_Cancelar").on("click", function (event) {
    $("div#alerta").removeClass("#invalid-feedback");
    $("div#alerta").html("");
    $("div#alertaNombreCandidato").html("");
    $("div#alertaApellidoMCandidato").html("");
    $("div#alertaApellidoPCandidato").html("");
    $("#resultado").html("");
    $("#FormGuardarCandidatos")[0].reset();
});

//*******************************************************************************************************
//                             Consultar el ultimo Expediente guardado
//*******************************************************************************************************
function ultimoExpediente() {
    $.ajax({
        type: "GET",
        url: "catalogos/ultimoExpediente",
        dataType: 'json',
        success: function (data) {
            $('#expediente').val(parseInt(data.numero_expediente)+1);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
} 
function ultimoIdExpediente(id_persona){
    
    $.ajax({
        type: "GET",
        url: "catalogos/ultimoIdExpediente",
        dataType: 'json',
        success: function (data) {
            guardarTrabajador(id_persona,data);
            
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
