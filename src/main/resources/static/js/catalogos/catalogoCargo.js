/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
//Limpieza de agrega
$('#agregarCargoModal').on('hidden.bs.modal', function () {
   //Remoción de mensajes de validación
   document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
   $("#cargo_admon_pub").val("");
}); 

//******************************************************************************
//                      CATÁLOGO CARGO            
//******************************************************************************
/*=================Carga de tabla Cargos============================ */
tabla_Cargos = $('#tabla_Cargos').dataTable({
    "ajax": {
        url: "catalogos/listarDatosCargo",
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
        {data: "id_cargo"},
        {data: "cargo_admon_pub"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarCargoModal" onclick="EditarCargo(' + r.id_cargo + ');cargoCargaDatosForm(' + r.id_cargo + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_cargo + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusCargo(' + r.id_cargo + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_cargo + ' class="btn btn-outline-danger" onclick="estatusCargo(' + r.id_cargo + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*=================Añadir elementos a tabla Cargo============================ */
$("#agregarCargoModal").submit(function (e) {
    event.preventDefault();
    var cargo_admon_pub = $("#cargo_admon_pub").val();

    var datos = {"cargo_admon_pub": cargo_admon_pub};
    $.ajax({
        type: "POST",
        url: "catalogos/guardarCargo",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $("#agregarCargoModal").modal('hide');
            $("#cargo_admon_pub").val("");
            toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
            $('#tabla_Cargos').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
});

/*=================Edicion de cargo============================ */
function EditarCargo(id_cargo) {
    $('#modificarCargoModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
        //Invalida el id para evitar inconsistencias
        id_cargo = null;
    }); 
    
    $("#modificarCargoModal").submit(function (e) {
        event.preventDefault();
        var cargo_admon_publica = $("#cargo_admon_pub_edi").val();
        var datos = {"cargo_admon_pub": cargo_admon_publica};
  
        var direccionURL = "";
        direccionURL = "catalogos/editarCargo/" + id_cargo;

        if (id_cargo !== null) {
            $.ajax({
                type: "POST",
                url: direccionURL,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(datos),
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información para modificar", "Aviso", {progressBar: true, closeButton: true});
                        //Reset de Variable para evitar duplicados al editar de manera continua.
                    }
                    $("#modificarCargoModal").modal('hide');
                    $('#tabla_Cargos').DataTable().ajax.reload();
                    toastr['success'](data.data, "Se modificó con éxito");
                    id_cargo = null;
                },
                error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
                }
            });
        }
    });
}

//Función para trae el valor de la base de datos para edición sobre el mismo
function cargoCargaDatosForm(id_cargo) {
    $.ajax({
        type: "GET",
        url: "catalogos/buscarCargo/" + id_cargo,
        dataType: 'json',
        success: function (data) {
            (data.data.cargo_admon_pub === null) ? $('#cargo_admon_pub_edi').val("") : $('#cargo_admon_pub_edi').val(data.data.cargo_admon_pub);
        },
        error: function (e) {
            alert(e);
        }
    });
}


/*=================VERIFICACION DE ESTATUS DEL CARGO PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusCargo(id_cargo, estatus) {
    if (estatus === 1) {
        $("#modalStatusCargo").modal("toggle");
        inhabilitarCargo(id_cargo, 0);

    } else if (estatus === 0) {
        $("#modalStatusCargoActiva").modal("toggle");
        activarCargo(id_cargo, 1);
    } else {
        toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}

/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarCargo(id_cargo, estatus) {
    $('#modalStatusCargo').on('hidden.bs.modal', function () {
        id_cargo = null;
    });	
    
    $("#modalStatusCargo").modal("toggle");
    // Si se presiona el botón "Cancelar" se invalida (null) el id
    const boton = document.querySelector("#btnEstDesactivar");
    boton.addEventListener("click", function (evento) {
        id_cargo = null;
    });

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_cargo !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusCargo/" + id_cargo + "/" + estatus,
                data: "id_cargo=" + id_cargo, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusCargo").modal('hide');
                        toastr['warning']("El Cargo ha sido inhabilitado", "Aviso");
                        $('#tabla_Cargos').DataTable().ajax.reload();

                        //Reset de variable para evitar inconsistencias al realizar modificaciones continuas
                        id_cargo = null;
                    }
                }
            });
        }
    });
}

/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarCargo(id_cargo, estatus) {
    $('#modalStatusCargoActiva').on('hidden.bs.modal', function () {
        id_cargo = null;
    }); 

    $("#botonActivarCargo").click(function (e) {
        if (id_cargo !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusCargo/" + id_cargo + "/" + estatus,
                data: "id_cargo=" + id_cargo, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusCargoActiva").modal('hide');
                        toastr['success']("El Cargo ha sido activado", "Aviso");
                        $('#tabla_Cargos').DataTable().ajax.reload();
                        id_cargo = null;
                    }
                }
            });
        }
    });
}
