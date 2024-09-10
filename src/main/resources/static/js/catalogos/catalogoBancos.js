document.addEventListener('DOMContentLoaded', () => {
    punto();
});

/*=================Carga de tabla bancos============================ */
tabla_Bancos = $('#tabla_Bancos').dataTable({
    "ajax": {
        url: "catalogos/listarBancos",
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
        {data: "id_banco"},
        {data: "nombre_banco"},
        {data: "comision"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarBancoModal" onclick="EditarBanco(' + r.id_banco + ') ;bancoCargaDatosForm(' + r.id_banco + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_banco + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusBanco(' + r.id_banco + ',' + r.estatus + ');"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_banco + ' class="btn btn-outline-danger" onclick="estatusBanco(' + r.id_banco + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});

function punto() {
    $("#comision_agrega").on("input", function () {
        var v = $(this).val();
        if (v.toString().length > 2)
        {
            if (v.indexOf(".") > 0)
                v = v.slice(0, v.indexOf(".")) + v.slice(v.indexOf(".") + 1);
            $(this).val(v.slice(0, -2) + "." + v.slice(-2));
        }
    });
    $("#desc_comision_edi").on("input", function () {
        var v = $(this).val();
        if (v.toString().length > 2)
        {
            if (v.indexOf(".") > 0)
                v = v.slice(0, v.indexOf(".")) + v.slice(v.indexOf(".") + 1);
            $(this).val(v.slice(0, -2) + "." + v.slice(-2));
        }
    });
}

/*=================Añadir elementos a tabla Banco============================ */
//=============Limpieza de formulario Agrega===================
$('#agregarBancoModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#nombre_banco").val("");
   $("#comision_agrega").val("");
}); 
$("#agregarBancoModal").submit(function (e) {
    event.preventDefault();
    var nombre_banco = $("#nombre_banco").val();
    var comision = $("#comision_agrega").val();

    const num = parseFloat(comision);

    // check if the input comision is a number and not NaN
    if (!isNaN(num)) {
        var datos = {"nombre_banco": nombre_banco, "comision": comision};
        $.ajax({
            type: "POST",
            url: "catalogos/GuardarBanco",
            data: JSON.stringify(datos),
            contentType: "application/json",
            success: function (data) {
                $("#agregarBancoModal").modal('hide');
                $("#nombre_banco").val("");
                $("#comision_agrega").val("");
                toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
                $('#tabla_Bancos').DataTable().ajax.reload();
            },
            error: function (e) {
                toastr["warning"]("Error al agregar elemento al catálogo: " + e, " error", {progressBar: true, closeButton: true});
            }
        });
        return true; // input is a valid double
    } else {
        toastr["warning"]("Error al guardar los datos: Se reciibió un parámetro de comisión no valido", {progressBar: true, closeButton: true});
        return false; // input is not a valid double
    }
});

/*=================Edición de Banco============================ */
function EditarBanco(id_banco) {
    $('#modificarBancoModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validació
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
        id_banco = null;
    }); 
    $("#modificarBancoModal").submit(function (e) {
        event.preventDefault();
        var nombre_banco = $("#desc_banco_edi").val();
        var comision = $("#desc_comision_edi").val();
        var datos = {"nombre_banco": nombre_banco, "comision": comision};

        const num = parseFloat(comision);

        // check if the input is a number and not NaN
        if (!isNaN(num)) {
            var direccionURL = "";
            direccionURL = "catalogos/editarBanco/" + id_banco;

            if (id_banco !== null) {
                $.ajax({
                    type: "POST",
                    url: direccionURL,
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(datos),
                    success: function (data) {
                        if ($.isEmptyObject(data)) {
                            toastr["warning"]("No se encontro información para modificar", "Aviso", {progressBar: true, closeButton: true});
                        }
                        $("#modificarBancoModal").modal('hide');
                        $('#tabla_Bancos').DataTable().ajax.reload();
                        toastr['success']("Se modificó con éxito");

                        //Reset de Variable para evitar duplicados al editar de manera continua.
                        id_banco = null;
                    },
                    error: function (e) {
                        toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
                        
                    }
                });
            }
            return true; // input is a valid double
        } else {
            toastr["warning"]("Error al guardar los datos: Se recibió un parámetro de comisión no valido", {progressBar: true, closeButton: true});
            return false; // input is not a valid double
        }
    });
}

//Función para trae el valor de la base de datos para edición sobre el mismo
function bancoCargaDatosForm(id_banco) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarBanco/" + id_banco,
        dataType: 'json',
        success: function (data) {
            (data.data.nombre_banco === null) ? $('#desc_banco_edi').val("") : $('#desc_banco_edi').val(data.data.nombre_banco);
            (data.data.comision === null) ? $('#desc_comision_edi').val("") : $('#desc_comision_edi').val(data.data.comision);
        },
        error: function (e) {
            alert(e);
        }
    });
}

/*=================VERIFICACION DE ESTATUS PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusBanco(id_banco, estatus) {
    if (estatus === 1) {
        $("#modalStatusBanco").modal("toggle");
        inhabilitarBanco(id_banco, 0);
    } else if (estatus === 0) {
        $("#modalStatusBancoActiva").modal("toggle");
        activarBanco(id_banco, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}
/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarBanco(id_banco, estatus) {
    $('#modalStatusBanco').on('hidden.bs.modal', function () {
        id_banco = null;
    });
    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_banco !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusBanco/" + id_banco + "/" + estatus,
                data: "id_banco=" + id_banco, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusBanco").modal('hide');
                        toastr['warning']("Este Banco ha sido inhabilitado", "Aviso");
                        $('#tabla_Bancos').DataTable().ajax.reload();
                        id_banco = null;
                    }
                }
            });
        }
    });
}

/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarBanco(id_banco, estatus) {
    $('#modalStatusBancoActiva').on('hidden.bs.modal', function () {
        id_banco = null;
    }); 
    $("#botonActivarBanco").click(function (e) {
        if (id_banco !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusBanco/" + id_banco + "/" + estatus,
                data: "id_banco=" + id_banco, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusBancoActiva").modal('hide');
                        toastr['success']("Este Banco ha sido activado", "Aviso");
                        $('#tabla_Bancos').DataTable().ajax.reload();
                        id_banco = null;
                    }
                }
            });
        }
    });
}
