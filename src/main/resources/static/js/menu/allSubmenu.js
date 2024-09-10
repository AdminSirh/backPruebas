document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_menu = urlParams.get('id_menu');
    sb(id_menu);
});


function sb(id_menu) {
    $tablasubMenu = $('#noteTable').dataTable({
        "ajax": {
            url: "submenu/listarSubMenuAll/" + id_menu,
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
            "searchPlaceholder": "Buscar",
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
            {data: "id"},
            {data: "submenuNombre"},
            {data: "descripcion"},
            {data: "", sTitle: "activo", width: 100, visible: true, render: function (d, t, r) {
                    var he;
                    if (r.activo === 1) {
                        he = '<button type="button" id="btndistrict"' + r.id + '  class="btn btn-outline-success"class="btn btn-outline-info"onclick="estatusSubmenu(' + r.id + ',' + r.activo + ')"> ' + '<span class="fa fa-minus-circle"></span>Desactivar</button>';
                    } else {
                        he = '<button type="button" id="btndistrict"' + r.id + ' class="btn btn-outline-danger" onclick="estatusSubmenu(' + r.id + ',' + r.activo + ')"> ' + '<span class="fa fa-minus-circle"></span>Activar</button>';
                    }

                    return he;
                }
            }, {data: "", sTitle: "Asignar Roles", width: 150, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-warning" onclick="funcionRoles(' + r.id + ')"> ' + '<span class="fa fa-check-circle"> </span> Asignar Roles</button>';
                    return he;
                }
            },
            {data: "", sTitle: "Editar", width: 90, visible: true, render: function (d, t, r) {
                    var he;
                    he = '<button type="button" class="btn btn-outline-primary" onclick="obtener_datos(' + r.id + ')"><span class="fa fa-edit"></span>Editar</button>';
                    return he;
                }
            }

        ]
    });
}

/*=============================================
 Obtiene datos de Submenu para inputs
 =============================================*/

function obtener_datos(id) {
    
    $("#modificarSubmenuModal").modal("toggle");
    document.getElementById("id_submenu").value = id;
    $.ajax({
        type: "GET",
        url: "submenu/buscarSubmenu/" + id,
        dataType: 'json',
        success: function (data) {
            $('#menuNombre_edita').val(data.submenuNombre);
            $('#descripcion_edita').val(data.descripcion);
            
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=============================================
 Actualizar datos de Submenu
 =============================================*/
function actualizar_Datos(id) {
    event.preventDefault();
    $("#modificarSubmenuModal").modal("toggle");
    //  console.log(id_menu);

    var submenuNombre = $('#menuNombre_edita').val();
    var descripcion = $('#descripcion_edita').val();

    var datos = {"submenuNombre": submenuNombre, "descripcion": descripcion};
    //console.log(datos);
    $.ajax({
        type: "POST",
        url: "submenu/actualizarSubmenu/" + id,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#modificarSubmenuModal").modal('hide');
            toastr['success'](data.data, "Aviso");
            $tablasubMenu.DataTable().ajax.reload();
        },
        error: function (e) {
            alert("No se actualizo el Submenu" + e);
        }
    });
}

/*=============================================
 Guardar Submenu nuevo
 =============================================*/
$("#formGuardarMenu").submit(function (e) {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_menu = urlParams.get('id_menu');
    event.preventDefault();
    var submenuNombre = $('#submenuNombre').val();
    var descripcion = $('#descripcion').val();
    var activo = $('#activo').val();

    var datos = {"submenuNombre": submenuNombre, "menu": {"id": id_menu}, "descripcion": descripcion, "activo": activo};
    //console.log(datos);

    if ($.trim(submenuNombre) === "" || submenuNombre.length < 3) {
        return false;
    }
    if ($.trim(descripcion) === "" || descripcion.length < 3) {
        return false;
    }
    $.ajax({
        type: "POST",
        url: "submenu/guardaSubmenu",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $("#agregarSubenuModal").modal('hide');
            toastr['success']("El submenu ha sido agregado", "Aviso");
            $tablasubMenu.DataTable().ajax.reload();
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
});

/*=============================================
 Activa y desactiva submenu
 =============================================*/
function estatusSubmenu(id, activo) {

    if (activo === 1) {
        $("#modalStatus").modal("toggle");
        inhabilitar(id, 0);

    } else if (activo === 0) {
        $("#modalStatusActivar").modal("toggle");
        activar(id, 1);
    }
}


/*=============================================
 Inhabilitar Rol
 =============================================*/
function inhabilitar(id, activo) {
    $("#modalStatus").modal("toggle");
    $("#botonDeshabilitar").click(function (e) {
        // $('#prueba').empty();
        if (id !== null) {
            $.ajax({
                type: "GET",
                url: "submenu/estatusSubmenu/" + id + "/" + activo,
                data: "id=" + id, "activo": activo,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatus").modal('hide');
                        toastr['warning']("El submenu ha sido inhabilitado", "Aviso");
                        // $tablaUsuarios.ajax.reload();

                        $('#noteTable').DataTable().ajax.reload();
                        id = null;
                        // console.log(id);
                    }
                }
            });
        }
    });
}


/*=============================================
 Activar Rol
 =============================================*/
function activar(id, activo) {
    $("#modalStatusActivar").modal("toggle");
    $("#botonCambiar").click(function (e) {
        if (id !== null) {
            $.ajax({
                type: "GET",
                url: "submenu/estatusSubmenu/" + id + "/" + activo,
                data: "id=" + id, "activo": activo,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusActivar").modal('hide');
                        toastr['success']("El submenu ha sido activado", "Aviso");
                        $('#noteTable').DataTable().ajax.reload();
                        id = null;
                    }
                }
            });
        }
    });
}

/*=============================================
 Asignar roles
 =============================================*/
function funcionRolesSM(submenu_id) {

    $.ajax({
        type: "GET",
        url: "submenu/buscarSubmenuRol/" + submenu_id,
        dataType: 'json',
        success: function (data) {

            var array_Roles = [];
            if (data.data.length > 0) {
                for (var i = 0; i < data.data.length; i++) {
                    var roles = data.data[i].rolNombre;
                    array_Roles.push(roles);
                }
            }
            funcionListarRoles(submenu_id, array_Roles);
        },
        error: function (e) {
            alert(e);
        }
    });
}


/*=============================================
 Buscar sub menu rol
 =============================================*/
function funcionRoles(id) {
    $("#RolesUsuarioModal").modal("toggle");

    $.ajax({
        type: "GET",
        url: "submenu/buscarSubmenuRol/" + id,
        dataType: 'json',
        success: function (data) {

            var array_Roles = [];
            if (data.data.length > 0) {
                for (var i = 0; i < data.data.length; i++) {
                    var roles = data.data[i].rolNombre;
                    array_Roles.push(roles);
                }
            }
            funcionListarRoles(id, array_Roles);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=============================================
 Listar Roles
 =============================================*/
function funcionListarRoles(submenu_id, roles) {
    $.ajax({
        type: "GET",
        url: "rol/listarRoles",
        dataType: 'json',
        success: function (data) {
            //console.log(data.data);
            var html = '';
            $('#rolesLista').empty();
            var len = data.data.length;

            for (var i = 0; i < len; i++) {
                var id_rol = data.data[i].id;

                html += '<div class="form-inline mx-auto">'
                        + '<div class="form-group row justify-content-between mx-auto">' +
                        '<label for="primary" class="btn btn-outline-info col-6" style="width: 200px" >' + data.data[i].rolNombre + '</label>';

                if (roles.includes(data.data[i].rolNombre)) {
                    html += '<button type="button" name="botonDesactivar" class="btn btn-danger col-auto" id="rolDesactivar"  style="width: 150px" onclick="desactivarRol(' + submenu_id + ',' + id_rol + ')"> Desactivar Rol </button></div></div><br>';
                } else {
                    html += '<button type="button" name="botonAsignar" class="btn btn-success col-auto" id="rolActivar"  style="width: 150px"onclick="asignarRol(' + submenu_id + ',' + id_rol + ')"> Activar Rol </button></div></div><br>';
                }
            }
            $("#rolesLista").append(html);
        },
        error: function (e) {
            alert(e);
        }
    });
}


/*=============================================
 Asignar Rol
 =============================================*/
function asignarRol(submenu_id, id_Rol) {
    event.preventDefault();
    var datos = {"rol": {"id": id_Rol}, "submenu": {"id": submenu_id}};
    $.ajax({
        type: "POST",
        url: "submenu/guardarSubmenuRol",
        dataType: 'json',
        data: JSON.stringify(datos),
        contentType: "application/json",

        success: function (data) {
            toastr['success'](data.data, "Aviso");
            event.preventDefault();   
            funcionRolesSM(submenu_id);

        },
        error: function (e) {
            alert(e);
        }
    });
}


/*=============================================
 Desactivar Rol
 =============================================*/
function desactivarRol(submenu_id, id_Rol) {
    event.preventDefault();
    
    var datos = {"submenu": {"id": submenu_id}, "rol": {"id": id_Rol}};

    $.ajax({
        type: "POST",
        url: "submenu/eliminarRol/" + submenu_id + "/" + id_Rol,
        dataType: 'json',
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            toastr['error']("El rol ha sido eliminado", "Aviso");
            funcionRolesSM(submenu_id);
        },
        error: function (e) {
            alert(e);
        }
    });
}

$("#btn_Cancelar").on("click", function (event) {
    $("div#alerta").removeClass("#invalid-feedback");
    $("div#alerta").html("");
    $("#formGuardarMenu")[0].reset();
});
