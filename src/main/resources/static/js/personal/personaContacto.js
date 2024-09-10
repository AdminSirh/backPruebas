document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_persona = urlParams.get('id_persona');
    cargaDatosContacto(id_persona);
    tabs(id_persona);
});

function tabs(id_persona) {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=personaDatosGenerales('" + id_persona + "')>1.-DATOS GENERALES</li>&nbsp;" +
            "<li><a href='#tabs-2' class='btn btn-info'>2.- DATOS DE CONTACTO</a></li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaDireccion('" + id_persona + "')>3.-DIRECCION</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaLicencia('" + id_persona + "')>4.-LICENCIA</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaDocumentos('" + id_persona + "')>5.-DOCUMENTOS</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaMedico('" + id_persona + "')>6.-DATOS MEDICOS</li>&nbsp;"+
            "<li class='btn btn-primary' onclick=personaCarta('" + id_persona + "')>7.- CARTA DESIGNATARIA</li>&nbsp;");
}

function personaDatosGenerales(id_persona) {
    window.location.href = 'personaDatosGenerales?id_persona=' + id_persona;
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
function personaCarta(id_persona) {
    window.location.href = 'personaCarta?id_persona=' + id_persona;
}


/*=============================================
 Busca datos de contacto 
 =============================================*/
function cargaDatosContacto(id_persona) {
    $.ajax({
        type: "GET",
        url: "personas/buscarContacto/" + id_persona,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }//console.log(data.data);

            if (data.data !== null) {
                $('#id_Contacto').val(data.data.id_contacto);
                $('#mail').val(data.data.mail);
                $('#telefono_celular').val(data.data.telefono_celular);
                $('#telefono_emergencias').val(data.data.telefono_emergencias);
                $('#telefono_particular').val(data.data.telefono_particular);
                $('#nombre_emergencias').val(data.data.nombre_emergencias);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos de dirección : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 Valida si contacto esta null para guardar o editar datos
 =============================================*/
$("#formContactoPersona").submit(function (e) {
    event.preventDefault();
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_persona = urlParams.get('id_persona');

    var mail = $("#mail").val();
    var telefono_celular = $("#telefono_celular").val();
    var telefono_emergencias = $("#telefono_emergencias").val();
    var telefono_particular = $("#telefono_particular").val();
    var nombre_emergencias = $("#nombre_emergencias").val();
    var id_Contacto = $("#id_Contacto").val();

   var validEmail = /^\w+([.-_+]?\w+)*@\w+([.-]?\w+)*(\.\w{2,10})+$/;
    //var validaTelefono =  /^\d{9}$/; 

    //console.log(validEmail.test(mail));
  /* if (validEmail.test(mail) === false) {
        toastr['error']("El correo ingresado no tiene un formato correcto", "Aviso");
        return false;
    }*/
    if ($.trim(telefono_celular) === null || telefono_celular.length < 10) {
        toastr['error']("El telefono ingresado no tiene un formato correcto", "Aviso");
        return false;
    }
    if ($.trim(telefono_emergencias) === null || telefono_emergencias.length < 10) {
        console.log("validEmail.test(mail)");
        return false;
    }
    if ($.trim(telefono_particular) === null || telefono_particular.length < 10) {
        return false;
    }
    if ($.trim(nombre_emergencias) === null) {
        return false;
    }

    var datos = {"mail": mail, "telefono_celular": telefono_celular, "telefono_emergencias": telefono_emergencias, "telefono_particular": telefono_particular, "nombre_emergencias": nombre_emergencias, "persona_id": id_persona};

    var url_accion = "";

    if (id_Contacto === "" || id_Contacto === null) {
        url_accion = "personas/guardarContactoPersona";

    } else if (id_Contacto !== "" || id_Contacto !== null) {
        url_accion = "personas/editarPersonaContacto/" + id_persona;
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
            cargaDatosContacto(id_persona);

            toastr['success'](data.data, "Aviso");
            event.preventDefault();
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

function verCandidatos() {
    window.location.href = 'candidatos';
}

function verTrabajadores() {
    window.location.href = 'trabajadoresAdmin';
}