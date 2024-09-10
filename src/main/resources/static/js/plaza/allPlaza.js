document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_puesto = urlParams.get('id_puesto');
    var no_plazas = urlParams.get('no_plazas');
    validarNoPlazas(id_puesto, no_plazas);
    tbPlaza(id_puesto);
    obtenerUltimoId();
    
});
//******************************************************************************
//                        FUNCIÓN PARA CARGAR LAS PLAZAS       
//******************************************************************************
function tbPlaza(id_puesto) {

    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_puesto = urlParams.get('id_puesto');
    var no_plazas = urlParams.get('no_plazas');

    $tablaPlaza = $('#noteTable').dataTable({
        "ajax": {
            url: "catalogos/listarPlazas/" + id_puesto,
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
            {data: "id_plaza", className: "text-center"},
            {data: "cat_puesto.puesto", className: "text-center"},
            {data: "", sTitle: "Cancelar", className: "text-center", width: 120, visible: true, render: function (d, t, r) {
                    var he;
                   
                    if (r.estatus_plaza_id === 1 || r.estatus_plaza_id === 3) {
                        
                        he = '<button type="button" id="btndistrict"' + '  class="btn btn-outline-warning"class="btn btn-outline-info "onclick="estatusPlaza(' + r.id_plaza + ',' + r.estatus_plaza_id + ')" > ' + '<span class="fa fa-minus-circle"></span> Cancelar</button>';

                    } else {
                        he = '<button type="button" id="btnDesactivar"' + ' class="btn btn-outline-info" style="width:120px" onclick="estatusPlaza(' + r.id_plaza + ',' + r.estatus_plaza_id + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                    }
                    return he;
                }
            }
        ]
    });
}


//******************************************************************************
//                        VERIFICAR EL ESTATUS DE LA PLAZA   
//  Dependiendo del estatus de la plaza se invoca la función correspondiente            
//******************************************************************************
function estatusPlaza(id_plaza, estatus_plaza_id) {
    if (estatus_plaza_id === 3 || estatus_plaza_id === 1) {
        $("#modalStatusPuesto").modal("toggle");
        cancelar(id_plaza, 2);

    } else if (estatus_plaza_id === 2) {
        $("#modalStatusPuestoActivar").modal("toggle");
        activar(id_plaza, 3);
    }
}


//******************************************************************************
//                          FUNCIÓN INHABILITAR
//                       Cambia el estatus de 0 a 2             
//******************************************************************************
function cancelar(id, activo) {
    event.preventDefault();
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_puesto = urlParams.get('id_puesto');
    var no_plazas = urlParams.get('no_plazas');

    // Si se presiona el botón "Cancelar" se invalida (null) el id
    const boton = document.querySelector("#btnCancelarDesactivar");
    boton.addEventListener("click", function (evento) {
        id = null;
    });

    $("#modalStatusPlaza").modal("toggle");
    $("#botonDeshabilitarPlaza").click(function (e) {

        //Condición para evitar id de tipo null
        if (id !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambiarEstatusPlaza/" + id + "/" + activo,
                data: {"estatus": 0, "id_plaza": 0},
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {

                        $("#modalStatusPlaza").modal('hide');
                        toastr['error']("La 'Plaza' ha sido Cancelada", "Aviso");
                        $tablaPlaza.DataTable().ajax.reload();
                        id = null;

                        //Se invoca la función "ValidarNoPlazas" para verificar cambios en el estatus de las plazas
                        validarNoPlazas(id_puesto, no_plazas, id);
                    }
                }
            });
        }
    });
}


//******************************************************************************
//                          FUNCIÓN HABILITAR
//                       Cambia el estatus de 2 a 0             
//******************************************************************************
function activar(id, activo) {
    event.preventDefault();
    var estadoBoton = $('#estadoBoton').val();
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_puesto = urlParams.get('id_puesto');
    var no_plazas = urlParams.get('no_plazas');

    // Si se presiona el botón "Cancelar" se invalida (null) el id
    const boton = document.querySelector("#btnCancelarActivar");
    boton.addEventListener("click", function (evento) {
        id = null;
    });
    
    //Condición para verificar el estado del botón activar, si es igual a "enable" signiica que esta permitido activar esa plaza, de lo contrario el estad 
    
    if (estadoBoton === "enabled") {
        $("#modalStatusPuestoActivar").modal("toggle");
        $("#botonActivarPuesto").click(function (e) {

            if (id !== null) {
                $.ajax({
                    type: "POST",
                    url: "catalogos/cambiarEstatusPlaza/" + id + "/" + activo,
                    data: {"estatus": 0, "id_plaza": 0},
                    success: function (data) {
                        if ($.isEmptyObject(data)) {
                            toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});

                        } else {
                            $("#modalStatusPuestoActivar").modal('hide');
                            toastr['success']("La 'Plaza' ha sido activada", "Aviso");
                            $tablaPlaza.DataTable().ajax.reload();
                            id = null;
                            validarNoPlazas(id_puesto, no_plazas, id);
                        }
                    }
                });
            }
        });
    }
}




//******************************************************************************
//                          VALIDAR NÚMERO DE PLAZAS
//             Obtiene el número de plazas que tiene un puesto  
//    Parametros
//      -> no_plazasPermitidas: Registradas en la base para el puesto actual
//      -> puesto_id: Id del puesto         
//******************************************************************************
function validarNoPlazas(puesto_id, no_plazasPermitidas, id) { //
    event.preventDefault();
    $('#alertaActivar').html("");
    $.ajax({
        type: "GET",
        url: "catalogos/contarPlazas/" + puesto_id, // Cuenta el número de plazas activas (Estatus 1)
        dataType: 'json',
        success: function (data) {
            
            $('#alertaActivar').html("");
            
            event.preventDefault();
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }
            var plazasRegistradas = data.data;
            
            // Si el número de plazas registradas (plazasRegistradas) es menor al numero de plazas permitidas (no_plazasPermitidas)
            // Se pueden agregar más plazas al puesto actual
            if (parseInt(plazasRegistradas) < parseInt(no_plazasPermitidas)) {
                
                //Se habilitan los botones 
                const btnGenerarPlaza = document.getElementById('btnGenerarPlaza');
                btnGenerarPlaza.disabled = false;

                const botonActivarPuesto = document.getElementById('botonActivarPuesto');
                botonActivarPuesto.disabled = false;
                $('#estadoBoton').val("enabled");
                

                // Si ambas variables tienen el mismo valor, ya no se pueden agregar más plazas. 
            } else if (parseInt(plazasRegistradas) === parseInt(no_plazasPermitidas)) {
                
                //Se desabilitan los botones 
                const btnGenerarPlaza = document.getElementById('btnGenerarPlaza');
                btnGenerarPlaza.disabled = true;

                const botonActivarPuesto = document.getElementById('botonActivarPuesto');
                botonActivarPuesto.disabled = true;

                $('#estadoBoton').val("disabled");
                $('#alertaActivar').append('<div class="icon text-danger"><span class="fa-stack fa-fw" style= "color:#DA0C2B "><i class="fa fa-circle fa-stack-2x"></i><i class="fa fa-exclamation-triangle fa-stack-1x fa-inverse"></i></span> <strong class="text-danger"> ¡Alerta! </strong> <em>No se puede activar esta plaza. Excede el número permitido de plazas.</em> </div><br>');
                toastr["warning"]("En este puesto no se pueden agregar más plazas", " Aviso", {progressBar: true, closeButton: true});

                // Si el número de plazas registradas es mayor a las plazas permitidas ya no se pueden agregar más plazas
            } else if (parseInt(plazasRegistradas) > parseInt(no_plazasPermitidas)) {
                
                //Se desabilitan los botones 
                const btnGenerarPlaza = document.getElementById('btnGenerarPlaza');
                btnGenerarPlaza.disabled = true;

                const botonActivarPuesto = document.getElementById('botonActivarPuesto');
                botonActivarPuesto.disabled = true;

                $('#estadoBoton').val("disabled");
                toastr["warning"]("En este puesto no se pueden agregar más plazas", " Aviso", {progressBar: true, closeButton: true});
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar los datos: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


//******************************************************************************
//                          GUARDAR UNA NUEVA PLAZA        
//******************************************************************************

$("#formGenerarPlaza").submit(function (e) {
    
    event.preventDefault();
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_puesto = urlParams.get('id_puesto');
    var no_plazas = urlParams.get('no_plazas');
    var numero_plaza = parseInt($("#ultimoRegistro").val())+1;
    if ($.trim(numero_plaza) === "") {
        return false;
    }
    var datos = {"numero_plaza": numero_plaza,"cat_puesto":{"id_puesto": id_puesto}};
    $.ajax({
        type: "POST",
        url: "catalogos/guardarPlaza",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),
        
        success: function (data) {
            toastr['success'](data.data, "Plaza generada correctamente");
            $tablaPlaza.DataTable().ajax.reload();
            validarNoPlazas(id_puesto, no_plazas);
            obtenerUltimoId();
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
});


function obtenerUltimoId(){
    $.ajax({
        type: "GET",
        url: "catalogos/ultimoRegistro",
        dataType: 'json',
        contentType: "application/json",
        success: function (data) {
            $('#ultimoRegistro').val(data.data);
            
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });  
}

//******************************************************************************
//                          FUNCIÓN ADMINISTRAR PLAZAS
//     Redirecciona a la página 'administrarPlazas' para realizar cambios 
//                          en las plazas de un puesto           
//******************************************************************************
function adminPuestos() {
    window.location.href = 'adminPuestos';
}



