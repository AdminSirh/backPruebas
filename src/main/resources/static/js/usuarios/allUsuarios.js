/* global data, NaN, Document */
/* Cargar DOM antes de JS */
/*=============================================
 Ejectuar Tabla al hacer cambios en Usuarios 
 =============================================*/
document.addEventListener('DOMContentLoaded', () => {
    $tablaUsuarios;
});

$tablaUsuarios = $('#noteTable').DataTable({
    "ajax": {
        url: 'usuarios/listarUsuarios',
        method: 'GET',
        dataSrc: ''
    },
    responsive: true,
    bProcessing: true,
    select: true,
    "language": {
        "processing": "Procesando espera...",
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
        {data: "ap_paterno"},
        {data: "ap_materno"},
        {data: "username"},
        //{defaultContent: "<button type='button' class='btn btn-outline-primary' onclick='EditarPersona('" + id_persona + "')'><span class='fa fa-edit'></span>Editar</button>"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="funcionEditar(' + r.id + ')" > ' + '<span class="fa fa-edit"> </span> Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Cambiar Activo/inactivo", width: 150, visible: true, render: function (d, t, r) {
                var he;
                if (r.activo === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id + '  class="btn btn-outline-success"class="btn btn-outline-info"onclick="inhabilitar(' + r.id + ')"> ' + '<span class="fa fa-minus-circle"></span>Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id + ' class="btn btn-outline-danger" onclick="activar(' + r.id + ')"> ' + '<span class="fa fa-minus-circle"></span>Activar</button>';
                }
                return he;
            }
        },
        {data: "", sTitle: "Asignar Roles", width: 150, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-warning" onclick="funcionRoles(' + r.id + ')"> ' + '<span class="fa fa-check-circle"> </span> Asignar Roles</button>';
                return he;
            }
        },
        {data: "", sTitle: "Modificar Password", width: 200, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="funcionModificarContrasena(' + r.id + ')"><span class="fa fa-lock"></span> Modificar Contraseña</button>';
                return he;
            }
        }

    ]
});


//Limpieza de agrega
$('#agregarUsuarioExpedienteModal').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    $("#expediente").val("");
    $("#nombre_expediente").val("");
    $("#apellido_paterno_expediente").val("");
    $("#apellido_materno_expediente").val("");
    $("#nombre_usuario").val("");
    $("#password_usuario").val("");
   
});

$('#agregarUsuarioModal').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    $("#nombre_agregar").val("");
    $("#ap_Paterno_agregar").val("");
    $("#ap_Materno_agregar").val("");
    $("#username").val("");
    $("#password_agregar").val("");
   
});


/*=============================================
 ACTIVAR O DESACTIVAR USUARIOS
 =============================================*/

function inhabilitar(id) {
    $("#modalStatus").modal("toggle");
    $("#botonDeshabilitar").click(function (e) {
        if (id !== null) {
            $.ajax({
                type: "GET",
                url: "usuarios/estadoUsuario/" + id + "/" + 0,
                data: "id=" + id, "activo": 0,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatus").modal('hide');
                        toastr['warning']("El usuario ha sido inhabilitado", "Aviso");
                        // $tablaUsuarios.ajax.reload();

                        $('#noteTable').DataTable().ajax.reload();
                        id = null;
                        
                    }
                }
            });
        }
    });


}

function activar(id) {
    $("#modalStatusActivar").modal("toggle");
    $("#botonCambiar").click(function (e) {
        if (id !== null) {
            $.ajax({
                type: "GET",
                url: "usuarios/estadoUsuario/" + id + "/" + 1,
                data: "id=" + id, "activo": 1,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatusActivar").modal('hide');
                        toastr['success']("El usuario ha sido habilitado", "Aviso");
                        // $tablaUsuarios.ajax.reload();
                        $('#noteTable').DataTable().ajax.reload();
                        id = null;
                        
                    }
                }
            });
        }
    });
}

/*=============================================
 AGREGAR USUARIOS
 =============================================*/

$("#formGuardarUsuario").submit(function (e) {
    event.preventDefault();
    var nombre = $("#nombre_agregar").val();
    var ap_paterno = $("#ap_Paterno_agregar").val();
    var ap_materno = $("#ap_Materno_agregar").val();
    var username = $("#username_agregar").val();
    var password = $("#password_agregar").val();
    

    var datos = {"nombre": nombre, "ap_paterno": ap_paterno, "ap_materno": ap_materno, "username": username, "password": password};

    const patron = /^(?=.*[A-Z])(?=.*[!@#$&%"'()*+-./_])(?=.*[0-9])(?=.*[a-z]).{8,}$/;

    if ($.trim(nombre) === "" || nombre.length < 4) {
        return false;
    }
    if ($.trim(ap_paterno) === "" || ap_paterno.length < 4) {
        return false;
    }
    if ($.trim(ap_materno) === "" || ap_materno.length < 4) {
        return false;
    }
    if ($.trim(username) === "" || username.length < 4) {
        return false;
    }
    if ($.trim(password) === "" || $("#password_agregar").val().length < 8 || (!password.match(patron))) {
        toastr['error']("La contraseña ingresada no es segura, coloca caracteres, números, letras mayúsculas y minúsculas ", "Aviso");
        return false;
    }

    $.ajax({
        type: "POST",
        url: "usuarios/guardarUsuario",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $("#agregarUsuarioModal").modal('hide');
            toastr['success'](data.data, "Aviso");
            $tablaUsuarios.ajax.reload(null, false);
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
});


/*=============================================
 LIMPIAR FORMULARIO
 =============================================*/
$("#btn_Cancelar").on("click", function (event) {
    $("div#alerta").removeClass("#invalid-feedback");
    $("div#alerta").html("");
    $("#formGuardarUsuario")[0].reset();
});
/*=============================================
 LIMPIAR FORMULARIO AGREGAR EXPEDIENTE
 =============================================*/
$("#boton_Cancelar").on("click", function (event) {
    $("div#alerta").removeClass("#invalid-feedback");
    $("div#alerta").html("");
    $("#formGuardarUsuarioExpediente")[0].reset();
});

/*=============================================
 EDITAR USUARIOS
 =============================================*/
function funcionEditar(id) {
    $("#editarUsuarioModal").modal("toggle");
    document.getElementById("idUsuario").value = id;
    $.ajax({
        type: "GET",
        url: "usuarios/buscarUsuario/" + id,
        dataType: 'json',
        success: function (data) {
            $('#nombre_edita').val(data.nombre);
            $('#ap_paterno_edita').val(data.ap_paterno);
            $('#ap_materno_edita').val(data.ap_materno);
            $('#username_edita').val(data.username);
        },
        error: function (e) {
            alert(e);
        }
    });
}

function actualizar_Datos(id) {
    event.preventDefault();
    var nombre_edita = $("#nombre_edita").val();
    var ap_paterno_edita = $("#ap_paterno_edita").val();
    var ap_materno_edita = $("#ap_materno_edita").val();
    var username_edita = $("#username_edita").val();
    var datos = {"nombre": nombre_edita, "ap_paterno": ap_paterno_edita, "ap_materno": ap_materno_edita, "username": username_edita};
    $.ajax({
        type: "POST",
        url: "usuarios/actualizarUsuario/" + id,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#editarUsuarioModal").modal('hide');
            if (data.error === 0) {
                toastr['success'](data.data, "Aviso");
                $tablaUsuarios.ajax.reload(null, false);
            }
            if (data.error !== 0) {
                toastr['error'](data.data, "Aviso");
            }
        },
        error: function (e) {
            toastr['error']("No se actualizo el usuario", "Aviso");
        }
    });
}



/*=============================================
 ASIGNAR ROLES DE USUARIO NAYELI
 =============================================*/

function funcionRolesU(idUsuario) {

    $.ajax({
        type: "GET",
        url: "usuarios/buscarUsuarioRol/" + idUsuario,
        dataType: 'json',
        success: function (data) {

            var array_Roles = [];
            if (data.data.length > 0) {
                for (var i = 0; i < data.data.length; i++) {
                    var roles = data.data[i].rolNombre;
                    array_Roles.push(roles);
                }
            }
            funcionListarRoles(idUsuario, array_Roles);
        },
        error: function (e) {
            alert(e);
        }
    });
}

function funcionRoles(idUsuario) {
    $("#RolesUsuarioModal").modal("toggle");

    $.ajax({
        type: "GET",
        url: "usuarios/buscarUsuarioRol/" + idUsuario,
        dataType: 'json',
        success: function (data) {

            var array_Roles = [];
            if (data.data.length > 0) {
                for (var i = 0; i < data.data.length; i++) {
                    var roles = data.data[i].rolNombre;
                    array_Roles.push(roles);
                }
            }
            funcionListarRoles(idUsuario, array_Roles);
        },
        error: function (e) {
            alert(e);
        }
    });
}

function funcionListarRoles(id_Usuario, roles) {
    $.ajax({
        type: "GET",
        url: "rol/listarRoles",
        dataType: 'json',
        success: function (data) {
            
            var html = '';
            $('#rolesLista').empty();
            var len = data.data.length;

            for (var i = 0; i < len; i++) {
                var id_rol = data.data[i].id;

                html += '<div class="form-inline mx-auto">'
                        + '<div class="form-group row justify-content-between mx-auto">' +
                        '<label for="primary" class="btn btn-outline-info col-6" style="width: 200px" >' + data.data[i].rolNombre + '</label>';

                if (roles.includes(data.data[i].rolNombre)) {
                    html += '<button type="button" name="botonDesactivar" class="btn btn-danger col-auto" id="rolDesactivar"  style="width: 150px" onclick="desactivarRol(' + id_Usuario + ',' + id_rol + ')"> Desactivar Rol </button></div></div><br>';
                } else {
                    html += '<button type="button" name="botonAsignar" class="btn btn-success col-auto" id="rolActivar"  style="width: 150px"onclick="asignarRol(' + id_Usuario + ',' + id_rol + ')"> Activar Rol </button></div></div><br>';
                }
            }
            $("#rolesLista").append(html);
        },
        error: function (e) {
            alert(e);
        }
    });
}

function asignarRol(id_Usuario, id_Rol) {
    event.preventDefault();
    
    var datos = {"usuario_id": id_Usuario, "rol_id": id_Rol};
    $.ajax({
        type: "POST",
        url: "usuarios/guardarUsuarioRol",
        dataType: 'json',
        data: JSON.stringify(datos),
        contentType: "application/json",

        success: function (data) {
            toastr['success'](data.data, "Aviso");
            event.preventDefault();
            //$("#RolesUsuarioModal").modal("toggle");
            funcionRolesU(id_Usuario);

        },
        error: function (e) {
            alert(e);
        }
    });
}

function desactivarRol(id_Usuario, id_Rol) {
    event.preventDefault();
    
    var datos = {"usuario_id": id_Usuario, "rol_id": id_Rol};

    $.ajax({
        type: "POST",
        url: "usuarios/eliminarRol/" + id_Usuario + "/" + id_Rol,
        dataType: 'json',
        data: JSON.stringify(datos),
        contentType: "application/json",

        success: function (data) {
            toastr['error']("El rol ha sido eliminado", "Aviso");
            funcionRolesU(id_Usuario);

        },
        error: function (e) {
            alert(e);
        }
    });

}



/*=============================================
 MODIFICAR CONTRASEÑA
 =============================================*/
function funcionModificarContrasena(id) {
    //event.preventDefault();
    $("#modificarcontrasenaModal").modal("toggle");

    $("#btn_Modificar").click(function (e) {
        event.preventDefault();
        var password_update = $("#password_update").val();
        const patron = /^(?=.*[A-Z])(?=.*[!@#$&%"'()*+-./_])(?=.*[0-9])(?=.*[a-z]).{8,}$/;
        var datos = {"password": password_update};
        if ($.trim(password_update) === "" || $("#password_update").val().length < 8 || (!password_update.match(patron))) {
            toastr['error']("La contraseña ingresada no es segura, coloca caracteres, números, letras mayúsculas y minúsculas ", "Aviso");
            $("#alerta_Modificar").addClass("invalid-feedback");
            return false;
        }

        $.ajax({
            type: "POST",
            url: "usuarios/actualizarPassword/" + id,
            data: JSON.stringify(datos),
            contentType: "application/json",
            success: function (data) {
                $("#modificarcontrasenaModal").modal('hide');
                toastr['success'](data.data, "Aviso");
                $tablaUsuarios.ajax.reload(null, false);
                $("#formModificarContrasena")[0].reset();
            },
            error: function (e) {
                toastr['error']("No se actualizo la contraseña", "Aviso");
            }
        });
    });
}
/*=============================================
 BUSCAR TRABAJADOR POR NO.EXPEDIENTE
 =============================================*/
function buscaTrabajador(){
    
    var id_expediente = $("#id_expediente").val();
    
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador_IdExpediente/" + id_expediente,
        dataType: 'json',
        success: function (data) {
            if (data.data!==null) {
                $("#nombre_expediente").val(data.data.persona.nombre);
                $("#apellido_paterno_expediente").val(data.data.persona.apellido_paterno);
                $("#apellido_materno_expediente").val(data.data.persona.apellido_materno);
                $("#trabajador_id").val(data.data.id_trabajador);
                
            }else{
                toastr['error']("No existe el expediente ingresado");
                $("#nombre_expediente").val("");
                $("#apellido_paterno_expediente").val("");
                $("#apellido_materno_expediente").val("");
                $("#trabajador_id").val("");   
            }
        },
        error: function (e) {
            
            $("#nombre_expediente").val("");
            $("#apellido_paterno_expediente").val("");
            $("#apellido_materno_expediente").val("");
        }
    });
}

function buscarExpediente(){
    var expediente = $("#expediente").val();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarIdExpediente/"+expediente,
        dataType: 'json',
        success: function (data) {
            $("#id_expediente").val(data.data);
            buscaTrabajador();
        },
        error: function (e) {
            
        }
    });
}




$("#formGuardarUsuarioExpediente").submit(function (e) {
    event.preventDefault();
    var nombre = $("#nombre_expediente").val();
    var apellido_paterno = $("#apellido_paterno_expediente").val();
    var apellido_materno = $("#apellido_materno_expediente").val();
    var username = $("#nombre_usuario").val();
    var password = $("#password_usuario").val();
    var trabajador_id = $("#trabajador_id").val();

    var datos = {"nombre": nombre, "ap_paterno": apellido_paterno, "ap_materno": apellido_materno, "username": username, "password": password, "trabajador_id": trabajador_id};

    const patron = /^(?=.*[A-Z])(?=.*[!@#$&%"'()*+-./_])(?=.*[0-9])(?=.*[a-z]).{8,}$/;

    if ($.trim(nombre) === "" || nombre.length < 4) {
        return false;
    }
    if ($.trim(apellido_paterno) === "" || apellido_paterno.length < 4) {
        return false;
    }
    if ($.trim(apellido_materno) === "" || apellido_materno.length < 4) {
        return false;
    }
    if ($.trim(username) === "" || username.length < 4) {
        return false;
    }
    
    if ($.trim(password) === "" || $("#password_usuario").val().length < 8 || (!password.match(patron))) {
        toastr['error']("La contraseña ingresada no es segura, coloca caracteres, números, letras mayúsculas y minúsculas ", "Aviso");
        return false;
    }

    $.ajax({
        type: "POST",
        url: "usuarios/guardarUsuario",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $("#agregarUsuarioExpedienteModal").modal('hide');
            toastr['success'](data.data, "Aviso");
            $tablaUsuarios.ajax.reload(null, false);
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
});