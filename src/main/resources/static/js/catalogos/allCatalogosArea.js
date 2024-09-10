document.addEventListener('DOMContentLoaded', () => {
    tabs();
    listarAreas(); 
});


/*=============================================
 Mostramos el Menu de los catalogos
 =============================================*/

//function catalogoAreas() {
//   window.location.href = 'catalogoAreas';
//}
/*=============================================
 MOSTRAMOS LAS AREAS EN LA TABLA
 =============================================*/
function listarAreas(){
    
    $tablaCatalogosA = $('#TablaCatalogo').DataTable({
    "ajax": {
        url: 'catalogos/listarAllAreas',
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
        {data: "desc_area"},
        {data: "determinante"},
        {data: "codigo_area"},
        {data: "presupuesto"},
        {data: "codigo_personal"},
        {data: "hw_status"},
        {data: "hw_area"},
        {data: "hw_bis"},
        {data: "programa"},
        {data: "doc_autorizacion"},
        {data: "fecha_movimiento"},
        {data: "fecha_captura"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="funcionEditar(' + r.idArea + ')" > ' + '<span class="fa fa-edit"> </span> Editar</button>';
                return he;}},
        {data: "", sTitle: "Cambiar Activo/inactivo", width: 150, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.idArea + '  class="btn btn-outline-success"class="btn btn-outline-info"onclick="inhabilitar(' + r.idArea + ')"> ' + '<span class="fa fa-minus-circle"></span>Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.idArea + ' class="btn btn-outline-danger" onclick="activar(' + r.idArea + ')"> ' + '<span class="fa fa-minus-circle"></span>Activar</button>';
                }
                return he;
            }
        }
        ]
});
}
/*=============================================
 GUARDAR NUEVA AREA
 =============================================*/
$("#formGuardarArea").submit(function (e) {
    
    event.preventDefault();
    var descripcion_area = $("#desc_area").val();
    var determinante = $("#determinante").val();
    var codigo_area = $("#codigo_area").val();
    var presupuesto = $("#presupuesto").val();
    var codigo_personal = $("#codigo_personal").val();
    var hw_status = $("#hw_status").val();
    var hw_area = $("#hw_area").val();
    var hw_bis = $("#hw_bis").val();
    var programa = $("#programa").val();
    var doc_autorizacion = $("#doc_autorizacion").val();

    
    if ($.trim(descripcion_area) === "" ) {
        return false;
    }
    if ($.trim(determinante) === "" ) {
        return false;
    }
    if ($.trim(codigo_area) === "") {
        return false;
    }
    if ($.trim(presupuesto) === "") {
        return false;
    }
    if ($.trim(codigo_personal) === "") {
        return false;
    }
    if ($.trim(hw_status) === "") {
        return false;
    }
    if ($.trim(hw_area) === "") {
        return false;
    }
    if ($.trim(hw_bis) === "") {
        return false;
    }
    if ($.trim(programa) === "") {
        return false;
    }
    if ($.trim(doc_autorizacion) === "") {
        return false;
    }
   
    
    var datos = {"desc_area": descripcion_area, "determinante": determinante, "codigo_area":codigo_area, "presupuesto":presupuesto, "codigo_personal":codigo_personal,
        "hw_status":hw_status, "hw_area":hw_area, "hw_bis":hw_bis, "programa":programa, "doc_autorizacion":doc_autorizacion};
    
    $.ajax({
        type: "POST",
        url: "catalogos/GuardarArea",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $("#agregarAreaModal").modal('hide');
            toastr['success'](data.data, "Área Guardada correctamente");
            $tablaCatalogosA.ajax.reload(null, false);
            LimpiarModalAgregar();
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
    
});

/*=============================================
 EDITAR AREAS
 =============================================*/
function funcionEditar(id) {
    $("#editarAreaModal").modal("toggle");
    document.getElementById("id_Area").value = id;
    $.ajax({
        type: "GET",
        url: "catalogos/buscarArea/" + id,
        dataType: 'json',
        success: function (data) {
            $('#desc_area_edita').val(data.data.desc_area);
            $('#determinante_edita').val(data.data.determinante);
            $('#codigo_area_edita').val(data.data.codigo_area);
            $('#presupuesto_edita').val(data.data.presupuesto);
            $('#codigo_personal_edita').val(data.data.codigo_personal);
            $('#hw_status_edita').val(data.data.hw_status);
            $('#hw_area_edita').val(data.data.hw_area);
            $('#hw_bis_edita').val(data.data.hw_bis);
            $('#programa_edita').val(data.data.programa);
            $('#doc_autorizacion_edita').val(data.data.doc_autorizacion);
            
            
            
        },
        error: function (e) {
            alert(e);
        }
    });
}

function actualizar_Datos(id) {
    event.preventDefault();
    var desc_area_edita = $("#desc_area_edita").val();
    var determinante_edita = $("#determinante_edita").val();
    var codigo_area_edita = $("#codigo_area_edita").val();
    var presupuesto_edita = $("#presupuesto_edita").val();
    var codigo_personal_edita = $("#codigo_personal_edita").val();
    var hw_status_edita = $("#hw_status_edita").val();
    var hw_area_edita = $("#hw_area_edita").val();
    var hw_bis_edita = $("#hw_bis_edita").val();
    var programa_edita = $("#programa_edita").val();
    var doc_autorizacion_edita = $("#doc_autorizacion_edita").val();
  
    
    if ($.trim(desc_area_edita) === "" ) {
        
        return false;
    }
    if ($.trim(determinante_edita) === "" ) {
        
        return false;
    }
    if ($.trim(codigo_area_edita) === "") {
        
        return false;
    }
    if ($.trim(presupuesto_edita) === "") {
        
        return false;
    }
    if ($.trim(codigo_personal_edita) === "") {
        
        return false;
    }
    if ($.trim(hw_status_edita) === "") {
        
        return false;
    }
    if ($.trim(hw_area_edita) === "") {
        
        return false;
    }
    if ($.trim(hw_bis_edita) === "") {
        
        return false;
    }
    if ($.trim(programa_edita) === "") {
        
        return false;
    }
    if ($.trim(doc_autorizacion_edita) === "") {
        
        return false;
    }
    
    var datos = {"desc_area": desc_area_edita, "determinante": determinante_edita, "codigo_area":codigo_area_edita, "presupuesto":presupuesto_edita,"codigo_personal":codigo_personal_edita,"hw_status":hw_status_edita,
    "hw_area":hw_area_edita,"hw_bis":hw_bis_edita,"programa":programa_edita,"doc_autorizacion":doc_autorizacion_edita
    };
    $.ajax({
        type: "POST",
        url: "catalogos/editarAreas/" + id,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            
            $("#editarAreaModal").modal('hide');
            if (data.error === 0) {
                toastr['success'](data.data, "Aviso");
                $tablaCatalogosA.ajax.reload(null, false);
            }
            if (data.error !== 0) {
                toastr['error'](data.data, "Datos Actualizados");
            }
        },
        error: function (e) {
            toastr['error']("No se actualizó el área", "Aviso");
        }
    });
}

/*=============================================
 ACTIVAR O DESACTIVAR AREAS
 =============================================*/

function inhabilitar(id) {
    $("#modalStatus").modal("toggle");
    $("#botonDeshabilitar").click(function (e) {
        if (id !== null) {
            $.ajax({
                type: "GET",
                url: "catalogos/estatusArea/" + id + "/" + 0,
                data: "id=" + id, "activo": 0,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatus").modal('hide');
                        toastr['warning']("El área ha sido inhabilitada", "Aviso");
                        $('#TablaCatalogo').DataTable().ajax.reload();
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
                url: "catalogos/estatusArea/" + id + "/" + 1,
                data: "id=" + id, "activo": 1,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusActivar").modal('hide');
                        toastr['success']("El área ha sido habilitado", "Aviso");
                        
                        $('#TablaCatalogo').DataTable().ajax.reload();
                        id = null;
                    }
                }
            });
        }
    });
}
