/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
//******************************************************************************
//                      CATÁLOGO HORARIO         
//******************************************************************************

/*=================Carga de tabla Horario============================ */
tabla_Horario = $('#tabla_Horario').dataTable({
    "ajax": {
        url: "catalogos/listarHorario",
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
        {data: "id_horario"},
        {data: "ca"},
        {data: "horas_acumuladas"},
        {data: "lunes"},
        {data: "martes"},
        {data: "miercoles"},
        {data: "jueves"},
        {data: "viernes"},
        {data: "sabado"},
        {data: "domingo"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#modificarHorarioModal" onclick="editarHorario(' + r.id_horario + '); horarioCargaDatosForm(' + r.id_horario + ')"  ><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
         {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_horario + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusHorario(' + r.id_horario + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_horario + ' class="btn btn-outline-danger" onclick="estatusHorario(' + r.id_horario + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
    ]
});

/*=================Añadir elementos a tabla Horario============================ */
$("#agregarHorarioModal").submit(function (e) {
    event.preventDefault();
    var ca = $("#desc_Ca").val();
    var horas_acumuladas = $("#desc_Horas_Acum").val();
    var lunes = $("#desc_Lunes").val();
    var martes = $("#desc_Martes").val();
    var miercoles = $("#desc_Miercoles").val();
    var jueves = $("#desc_Jueves").val();
    var viernes = $("#desc_Viernes").val();
    var sabado = $("#desc_Sabado").val();
    var domingo = $("#desc_Domingo").val();
    var estatus = 1;//Se asigna el valor 1 (Activado por defecto al crear un nuevo registro)
    //Validación de no envío de datos vacíos Validación de enteros donde corresponde

    if ($.trim(ca) === "") {
        $("#alertaAgregaHorario").append(" <label class='text-danger'><small>El campo 'Ca' no puede estar vacío,ingresa los datos correctos</small></label>");
        //return false;
    } 
    if ($.trim(horas_acumuladas) === "") {
        $("#alertaAgregaHorario").append(" <label class='text-danger'><small>El campo 'Horas Acumuladas' no puede estar vacío,ingresa los datos correctos</small></label>");
        return false;
    } 
    if ($.trim(lunes) === "") {
        $("#alertaAgregaHorario").append(" <label class='text-danger'><small>El campo 'Lunes' no puede estar vacío,ingresa los datos correctos</small></label>");
        return false;
    } 
    if ($.trim(martes) === "") {
        $("#alertaAgregaHorario").append(" <label class='text-danger'><small>El campo 'Martes' no puede estar vacío,ingresa los datos correctos</small></label>");
        return false;
    } 
    if ($.trim(miercoles) === "") {
        $("#alertaAgregaHorario").append(" <label class='text-danger'><small>El campo 'Miércoles' no puede estar vacío,ingresa los datos correctos</small></label>");
        return false;
    } 
    if ($.trim(jueves) === "") {
        $("#alertaAgregaHorario").append(" <label class='text-danger'><small>El campo 'Jueves' no puede estar vacío,ingresa los datos correctos</small></label>");
        return false;
    } 
    if ($.trim(viernes) === "") {
        $("#alertaAgregaHorario").append(" <label class='text-danger'><small>El campo 'Viernes' no puede estar vacío,ingresa los datos correctos</small></label>");
        return false;
    } 
    if ($.trim(sabado) === "") {
        $("#alertaAgregaHorario").append(" <label class='text-danger'><small>El campo 'Sábado' no puede estar vacío,ingresa los datos correctos</small></label>");
        return false;
    } 
    if ($.trim(domingo) === "") {
        $("#alertaAgregaHorario").append(" <label class='text-danger'><small>El campo 'Domingo' no puede estar vacío,ingresa los datos correctos</small></label>");
        return false;
    } 
        var datos = {"ca": ca, "horas_acumuladas": horas_acumuladas, "lunes": lunes, "martes": martes, "miercoles": miercoles, "jueves": jueves, "viernes": viernes, "sabado": sabado, "domingo": domingo, "estatus": estatus};
        
        $.ajax({
            type: "POST",
            url: "catalogos/GuardarHorario",
            data: JSON.stringify(datos),
            contentType: "application/json",
            success: function (data) {
                $("#agregarHorarioModal").modal('hide');
                toastr['success'](data.data, "Se ha agregado corretcamente el elemento");
                $('#tabla_Horario').DataTable().ajax.reload();
            },
            error: function (e) {
                toastr["warning"]("Error al agregar elemento al catálogo, ingresar valores enteros a los campos Lunes - Domingo : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    
});

/*=================Edición de Horario============================ */
function editarHorario(id_horario) {
    event.preventDefault();
    $("#FormModificarHorario").submit(function (e) {
        var ca = $("#desc_Ca_Edi").val();
        var horas_acumuladas = $("#desc_Horas_Acum_Edi").val();
        var lunes = $("#desc_Lunes_Edi").val();
        var martes = $("#desc_Martes_Edi").val();
        var miercoles = $("#desc_Miercoles_Edi").val();
        var jueves = $("#desc_Jueves_Edi").val();
        var viernes = $("#desc_Viernes_Edi").val();
        var sabado = $("#desc_Sabado_Edi").val();
        var domingo = $("#desc_Domingo_Edi").val();
        var estatus = 1;//Se asigna el valor 1 (Activa al editar un registro)
        var datos = {"ca": ca, "horas_acumuladas": horas_acumuladas, "lunes": lunes, "martes": martes, "miercoles": miercoles, "jueves": jueves, "viernes": viernes, "sabado": sabado, "domingo": domingo, "estatus": estatus};
        
        //Validación de no envío de casilla vacía
        if ($.trim(ca) === "") {
            $("#alertaHorario_edi").append(" <label class='text-danger'><small>El campo 'Ca' no puede estar vacío,ingresa los datos correctos</small></label>");
            //return false;
        } 
        if ($.trim(horas_acumuladas) === "") {
            $("#alertaHorario_edi").append(" <label class='text-danger'><small>El campo 'Horas Acumuladas' no puede estar vacío,ingresa los datos correctos</small></label>");
            return false;
        } 
        if ($.trim(lunes) === "") {
            $("#alertaHorario_edi").append(" <label class='text-danger'><small>El campo 'Lunes' no puede estar vacío,ingresa los datos correctos</small></label>");
            return false;
        } 
        if ($.trim(martes) === "") {
            $("#alertaHorario_edi").append(" <label class='text-danger'><small>El campo 'Martes' no puede estar vacío,ingresa los datos correctos</small></label>");
            return false;
        } 
        if ($.trim(miercoles) === "") {
            $("#alertaHorario_edi").append(" <label class='text-danger'><small>El campo 'Miércoles' no puede estar vacío,ingresa los datos correctos</small></label>");
            return false;
        } 
        if ($.trim(jueves) === "") {
            $("#alertaHorario_edi").append(" <label class='text-danger'><small>El campo 'Jueves' no puede estar vacío,ingresa los datos correctos</small></label>");
            return false;
        } 
        if ($.trim(viernes) === "") {
            $("#alertaHorario_edi").append(" <label class='text-danger'><small>El campo 'Viernes' no puede estar vacío,ingresa los datos correctos</small></label>");
            return false;
        } 
        if ($.trim(sabado) === "") {
            $("#alertaHorario_edi").append(" <label class='text-danger'><small>El campo 'Sábado' no puede estar vacío,ingresa los datos correctos</small></label>");
            return false;
        } 
        if ($.trim(domingo) === "") {
            $("#alertaHorario_edi").append(" <label class='text-danger'><small>El campo 'Domingo' no puede estar vacío,ingresa los datos correctos</small></label>");
            return false;
        } 
            var direccionURL = "";
            direccionURL = "catalogos/editarHorario/" + id_horario;

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
                    $("#modificarHorarioModal").modal('hide');
                    toastr['success'](data.data, "Se modificó con éxito");
                },
                error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
                }
            });
    });
}
//Función para trae el valor de la base de datos para edición sobre el mismo
function horarioCargaDatosForm(id_horario) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarHorario/" + id_horario,
        dataType: 'json',
        success: function (data) {
            (data.data.ca === null) ? $('#desc_Ca_Edi').val("") : $('#desc_Ca_Edi').val(data.data.ca);
            (data.data.horas_acumuladas === null) ? $('#desc_Horas_Acum_Edi').val("") : $('#desc_Horas_Acum_Edi').val(data.data.horas_acumuladas);
            (data.data.lunes === null) ? $('#desc_Lunes_Edi').val("") : $('#desc_Lunes_Edi').val(data.data.lunes);
            (data.data.martes === null) ? $('#desc_Martes_Edi').val("") : $('#desc_Martes_Edi').val(data.data.martes);
            (data.data.miercoles === null) ? $('#desc_Miercoles_Edi').val("") : $('#desc_Miercoles_Edi').val(data.data.miercoles);
            (data.data.jueves === null) ? $('#desc_Jueves_Edi').val("") : $('#desc_Jueves_Edi').val(data.data.jueves);
            (data.data.viernes === null) ? $('#desc_Viernes_Edi').val("") : $('#desc_Viernes_Edi').val(data.data.viernes);
            (data.data.sabado === null) ? $('#desc_Sabado_Edi').val("") : $('#desc_Sabado_Edi').val(data.data.sabado);
            (data.data.domingo === null) ? $('#desc_Domingo_Edi').val("") : $('#desc_Domingo_Edi').val(data.data.domingo);    
        },
        error: function (e) {
            alert(e);
        }
    });
}
/*=================VERIFICACION DE ESTATUS PARA ACTIVAR MODAL CORRESPONDIENTE============================ */
function estatusHorario(id_horario, estatus) {
    if (estatus === 1) {
        $("#modalStatusHorario").modal("toggle");
        inhabilitarHorario(id_horario, 0);
    } else if (estatus === 0) {
        $("#modalStatusHorarioActiva").modal("toggle");
        activarHorario(id_horario, 1);
    }
     else{
          toastr["warning"]("Error, se recibió un parámetro de estatus no válido : " + estatus, " error", {progressBar: true, closeButton: true});
    }
}

/*=================FUNCIÓN INHABILITAR (ESTATUS 1->0)============================ */
function inhabilitarHorario(id_horario, estatus) {
    // Si se presiona el botón "Cancelar" se invalida (null) el id
    const boton = document.querySelector("#btnEstDesactivar");
    boton.addEventListener("click", function (evento) {
        id_horario = null;
    });

    $("#botonDeshabilitarEstatus").click(function (e) {
        if (id_horario !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusHorario/" + id_horario + "/" + estatus,
                data: "id_horario=" + id_horario, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusHorario").modal('hide');
                        toastr['warning']("Este Horario ha sido inhabilitado", "Aviso");
                        $('#tabla_Horario').DataTable().ajax.reload();
                        id_horario = null;
                    }
                }
            });
        }
    });
}
/*=================FUNCIÓN HABILITAR (ESTATUS 0->1)============================ */
function activarHorario(id_horario, estatus) {
    // Si se presiona el botón "Cancelar" se invalida (null) el id
    const boton = document.querySelector("#btnEstDesactivar");
    boton.addEventListener("click", function (evento) {
        id_horario = null;
    });

    $("#botonActivar").click(function (e) {
        if (id_horario !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambioEstatusHorario/" + id_horario + "/" + estatus,
                data: "id_horario=" + id_horario, "estatus": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusHorarioActiva").modal('hide');
                        toastr['success']("Este Horario ha sido activado", "Aviso");
                        $('#tabla_Horario').DataTable().ajax.reload();
                        id_horario = null;
                    }
                }
            });
        }
    });
}