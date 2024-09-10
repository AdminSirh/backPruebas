document.addEventListener('DOMContentLoaded', () => {     
    
});
// Lista la tabla de los montos
$tablaTrabajadores = $('#tabla_Montos').dataTable({
    "ajax": {
        url: "vales/listarMontos",
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
        {data: "id_monto_vales"},
        {data: "dia_inicio"},
        {data: "dia_fin"},
        {data: "monto_confianza"},
        {data: "monto_base"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#editarMontoModal" onclick="cargaDatos(' + r.id_monto_vales + ');editarMonto(' + r.id_monto_vales + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        }]
});
// Guardar los datos de los montos del modal
$("#formGuardarMonto").submit(function (e) {
    event.preventDefault();
    var dia_inicio = $("#dia_inicio").val();
    var dia_fin = $("#dia_fin").val();
    var monto_confianza = $("#monto_confianza").val();
    var monto_base = $("#monto_base").val();
    
    if ($.trim(dia_inicio) === "" ) {
        return false;
    }if ($.trim(dia_fin) === "") {
        return false;
    }if ($.trim(monto_confianza) === "") {
        return false;
    }if ($.trim(monto_base) === "") {
        return false;
    }
    
    var datos = {"dia_fin": dia_fin,"dia_inicio": dia_inicio, "monto_base": monto_base, "monto_confianza": monto_confianza};
    
    $.ajax({
        type: "POST",
        url: "vales/guardarMonto",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $("#agregarMontoModal").modal('hide');
            limpiarParametros();
            toastr['success'](data.data, "Se ha agregado correctamente el elemento");
            $('#tabla_Montos').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
});
//Editar los parametros del monto
function editarMonto(id_monto_vales) {
    $('#editarMontoModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_monto_vales = null;
    }); 
    $("#editarMontoModal").submit(function (e) {
        event.preventDefault();
        
        var dia_inicio = $("#dia_inicio_edita").val();
        var dia_fin = $("#dia_fin_edita").val();
        var monto_confianza = $("#monto_confianza_edita").val();
        var monto_base = $("#monto_base_edita").val();
        if ($.trim(dia_inicio) === "" ) {
            return false;
        }if ($.trim(dia_fin) === "") {
            return false;
        }if ($.trim(monto_confianza) === "") {
            console.log(1);
            return false;
        }if ($.trim(monto_base) === "") {
            console.log(2);
            return false;
        }
        
        var datos = {"dia_fin": dia_fin,"dia_inicio": dia_inicio, "monto_base": monto_base, "monto_confianza": monto_confianza};
        
        if (id_monto_vales !== null) {
            $.ajax({
                type: "POST",
                url: "vales/editarMonto/" + id_monto_vales,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(datos),
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información para modificar", "Aviso", {progressBar: true, closeButton: true});
                    }
                    $("#editarMontoModal").modal('hide');
                    $('#tabla_Montos').DataTable().ajax.reload();
                    toastr['success'](data.data, "Se modificó con éxito");
                    id_monto_vales = null;
                },
                error: function (e) {
                        toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
                }
            });
    }
    });
}

//Función para trae el valor de la base de datos para edición sobre el mismo
function cargaDatos(id_monto_vales) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "vales/buscarMonto/" + id_monto_vales,
        dataType: 'json',
        success: function (data) {
            $("#dia_inicio_edita").val(data.data.dia_inicio);
            $("#dia_fin_edita").val(data.data.dia_fin);
            $("#monto_confianza_edita").val(data.data.monto_confianza);
            $("#monto_base_edita").val(data.data.monto_base);
        },
        error: function (e) {
            alert(e);
        }
    });
}
//Limpiar los parametros
function limpiarParametros(){
    $("#dia_inicio").val("");
    $("#dia_fin").val("");
    $("#monto_confianza").val("");
    $("#monto_base").val("");
    
}

//Limpieza de agrega
$('#agregarMontoModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   limpiarParametros();
});

//Validación de la longitud de los inputs
function validacion(object) {
    if (object.value.length > 6) {
        object.value = object.value.slice(0, 6);
    }
}