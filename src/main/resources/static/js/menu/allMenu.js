document.addEventListener('DOMContentLoaded', () => {
   
    
});

$tablaMenu = $('#tablaMenu').DataTable({
    "ajax": {
        url: 'menu/listarMenu',
        method: 'GET',
        dataSrc: ''
    },
    responsive: true,
    bProcessing: true,
    select: true,
    "language": {
        "processing": "Procesando espera...",
        "sProcessing": "Procesando...",
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
        {data: "id"},
        {data: "menuNombre"},
        {data: "descripcion"},
        {data: "icono"},
        {data: "orden"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="recuperarMenu(' + r.id + ')" > ' + '<span class="fa fa-edit"> </span> Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Sub Menú", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<td style="text-align:center;"><a  class="btn btn-outline-info" onclick="verSubMenu(' + r.id + ')"><span class="fa fa-search"></span>Administrar</a></td>';
                     
                return he;
            }
        }
        ]
});

//Limpieza de agrega
$('#agregarMenuModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#menuNombre").val("");
   $("#descripcion").val("");
   $("#orden").val("");
}); 

$("#formGuardarMenu").submit(function (e) {
    
    event.preventDefault();
    
    var menu_nombre = $("#menuNombre").val();
    var descripcion = $("#descripcion").val();
    var orden = $("#orden").val();
  
   
    if ($.trim(menu_nombre) === "") {
        return false;
    }
    if ($.trim(descripcion) === "") {
        return false;
    }
    if ($.trim(orden) === "" ) {
        return false;
    }
    
    var datos = {"menuNombre": menu_nombre, "descripcion": descripcion, "orden":orden};
    $.ajax({
        type: "POST",
        url: "menu/guardarMenu",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {

            $("#agregarMenuModal").modal('hide');
            toastr['success'](data.data, "Menú guardado correctamente");
            $tablaMenu.ajax.reload(null, false);
            $("#formGuardarMenu")[0].reset();  
},
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
    
});

function recuperarMenu(id){
    $("#modificarMenuModal").modal("toggle");
    
    $.ajax({
        type: "GET",
        url: "menu/buscarMenu/" + id,
        dataType: 'json',
        success: function (data) {
            $('#menuNombre_edita').val(data.data.menuNombre);
            $('#descripcion_edita').val(data.data.descripcion);
            $('#orden_edita').val(data.data.orden);
            $('#id_menu').val(data.data.id);
       },
        error: function (e) {
            alert(e);
        }
    });
    
}

function actualizar_Menu(id) {
    event.preventDefault();
    var menu_nombre = $("#menuNombre_edita").val();
    var descripcion = $("#descripcion_edita").val();
    var orden = $("#orden_edita").val();
    
    var datos = {"menuNombre": menu_nombre, "descripcion": descripcion, "orden":orden};
   
    $.ajax({
        type: "POST",
        url: "menu/actualizarMenu/" + id,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#modificarMenuModal").modal('hide');
            if (data.error === 0) {
                toastr['success'](data.data, "Menú actualizado");
                $tablaMenu.ajax.reload(null, false);
            }
            if (data.error !== 0) {
                toastr['error'](data.data, "Aviso");
            }
        },
        error: function (e) {
            toastr['error']("No se actualizó el Menú", "Aviso");
        }
    });
}

function verSubMenu(id) {
    window.location.href =  'subMenuAdmin?id_menu=' + id;
}


$("#btn_Cancelar").on("click", function (event) {
    $("div#alerta").removeClass("#invalid-feedback");
    $("div#alerta").html("");
    $("#formGuardarMenu")[0].reset();
});