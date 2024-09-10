document.addEventListener('DOMContentLoaded', () => {
    
    $tablaPuesto;

});
/*=============================================
 Ejectuar Tabla al hacer cambios en Personal 
 =============================================*/

$tablaPuesto = $('#noteTable').dataTable({
    "ajax": {
        url: "catalogos/listarPuestos",
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
        {data: "codigo_puesto", className: "text-center"},
        {data: "nivel", className: "text-center"},
        {data: "puesto", className: "text-center"},
        {data: "sueldo_diario", className: "text-center"},
        {data: "sueldo_hora", className: "text-center"},
        {data: "sueldo_hora_doble", className: "text-center"},
        {data: "num_plazas_autorizadas", className: "text-center"},

        {data: "", sTitle: "Estatus", className: "text-center", width: 110, visible: true, render: function (d, t, r) {
                var he;
                if (r.status === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_puesto + '  class="btn btn-outline-info"class="btn btn-outline-info"onclick="estatusPuesto(' + r.id_puesto + ',' + r.status + ')"> ' + '<span class="fa fa-minus-circle"></span> Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_puesto + ' class="btn btn-outline-danger" onclick="estatusPuesto(' + r.id_puesto + ',' + r.status + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        },
        {data: "", sTitle: "Editar", className: "text-center", width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="EditarPuesto(' + r.codigo_puesto + ')"><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Plazas", className: "text-center", width: 120, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-success" onclick="administrarPlazas(' + r.id_puesto + ',' + r.num_plazas_autorizadas + ')"><span class="fa fa-id-card-alt"></span> Administrar</button>';
                return he;
            }
        }]
});


//******************************************************************************
//                        VERIFICAR EL ESTATUS DE LA PLAZA   
//  Dependiendo del estatus del puesto se invoca la función correspondiente            
//******************************************************************************
function estatusPuesto(id_puesto, estatus) {
    if (estatus === 1) {
        $("#modalStatusPuesto").modal("toggle");
        inhabilitar(id_puesto, 0);

    } else if (estatus === 0) {
        $("#modalStatusPuestoActivar").modal("toggle");
        activar(id_puesto, 1);
    }
}


//******************************************************************************
//                          FUNCIÓN INHABILITAR
//                       Cambia el estatus de 1 a 0             
//******************************************************************************
function inhabilitar(id_puesto, estatus) {
    $("#modalStatusPuesto").modal("toggle");
    
    // Si se presiona el botón "Cancelar" se invalida (null) el id
    const boton = document.querySelector("#btnEstDesactivar");
    boton.addEventListener("click", function (evento) {
        id_puesto = null;
    });
    
    $("#botonDeshabilitarEstatus").click(function (e) {
        // $('#prueba').empty();
        if (id_puesto !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambiarEstatus/" + id_puesto + "/" + estatus,
                data: "id_puesto=" + id_puesto, "status": estatus,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusPuesto").modal('hide');
                        toastr['warning']("El Puesto ha sido inhabilitado", "Aviso");
                        $('#noteTable').DataTable().ajax.reload();
                        id_puesto = null;
                    }
                }
            });
        }
    });
}


//******************************************************************************
//                          FUNCIÓN HABILITAR
//                       Cambia el estatus de 0 a 1             
//******************************************************************************
function activar(id_puesto, status) {
    $("#modalStatusPuestoActivar").modal("toggle");
    
    // Si se presiona el botón "Cancelar" se invalida (null) el id
    const boton = document.querySelector("#btnEstActivar");
    boton.addEventListener("click", function (evento) {
        id_puesto = null;
    });    
    
    $("#botonActivarPuesto").click(function (e) {
        if (id_puesto !== null) {
            $.ajax({
                type: "POST",
                url: "catalogos/cambiarEstatus/" + id_puesto + "/" + status,
                data: "id_puesto=" + id_puesto, "status": status,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusPuestoActivar").modal('hide');
                        toastr['success']("El Puesto ha sido activado", "Aviso");
                        $('#noteTable').DataTable().ajax.reload();
                        id_puesto = null;
                    }
                }
            });
        }
    });
}

//******************************************************************************
//                          FUNCIÓN EDITAR PUESTO
//   Redirecciona a la página 'puestoDatosGenerales' para editar los datos de 
//                                un puesto.       
//******************************************************************************
function EditarPuesto(codigo_puesto) {
    window.location.href = 'puestoDatosGenerales?codigo_puesto=' + codigo_puesto;
}

//******************************************************************************
//                          FUNCIÓN ADMINISTRAR PLAZAS
//     Redirecciona a la página 'administrarPlazas' para realizar cambios 
//                          en las plazas de un puesto           
//******************************************************************************
function administrarPlazas(id_puesto,no_plazas) {
    window.location.href = 'administrarPlazas?id_puesto=' + id_puesto+ '&no_plazas=' + no_plazas;
}


//******************************************************************************
//                          GUARDAR UN NUEVO PUESTO          
//******************************************************************************
$("#FormGuardarPuesto").submit(function (e) {
    event.preventDefault();
    
    var codigoPuesto = $("#codPuesto").val();
    var nivelPuesto = $("#nivelPuesto").val();
    var puesto = $("#puesto").val();
    var sueldoDiario = $("#sueldoDiario").val();
    var sueldoDiarioDoble = $("#sueldoDiario_Doble").val();
    var sueldoHora = $("#sueldoHora").val();
    var sueldoHoraDoble = $("#sueldoHoraDoble").val();
    var sueldoHoraTriple = $("#sueldoHoraTriple").val();
    var sueldoMensual = $("#sueldoMensual").val();
    var cantidadAdMensual = $("#cantidadAdMensual").val();
    var noPlazasAut = $("#noPlazasAutorizadas").val();
    var sueldoQuincenal = $("#sueldoQuincenalTab").val();
    var diferenciaSueldoDiario = $("#diferenciaSueldoD").val();
    var diferenciaSueldoHora = $("#diferenciaSueldoH").val();
    var diferenciaSueldoMensual = $("#diferenciaSueldoM").val();
    var diferenciaCantidadAdMensual = $("#diferenciaCantidadAdM").val();
    var isrMensualPrestacion = $("#isrMensualP").val();
    var isrMensual = $("#isrMensual").val();
    var cuotaImss = $("#cuotaImss").val();
    var sueldoMensualNeto = $("#sueldoMensualNeto").val();
    var codigoRh = $("#codigoRh").val();
    var sueldoEstructura = $("#sueldoEstructura").val();
    var sueldoHora7 = $("#sueldoHora7").val();
    
    var patron = /^[A-Za-zÁÉÍÓÚáéíóú\s]+$/;
    if ($.trim(codigoPuesto) === "")      {   return false;  }
    if ($.trim(nivelPuesto) === "")       {   return false;  }
    if ($.trim(puesto) === "")            {   return false;  }
    
    if (!patron.test(puesto)) {
        toastr["warning"]("Para el campo 'Puesto' no esta permitido ingresar dígitos o caracteres diferentes a letras ", "Aviso", {progressBar: true, closeButton: true});
        return false;
    }
    if ($.trim(sueldoDiario) === "")                {   return false;  }
    if ($.trim(sueldoDiarioDoble) === "")           {   return false;  }
    if ($.trim(sueldoHora) === "")                  {   return false;  }
    if ($.trim(sueldoHoraDoble) === "")             {   return false;  }
    if ($.trim(sueldoHoraTriple) === "")            {   return false;  }
    if ($.trim(sueldoMensual) === "")               {   return false;  }
    if ($.trim(cantidadAdMensual) === "")           {   return false;  }
    if ($.trim(sueldoQuincenal) === "")             {   return false;  }
    if ($.trim(diferenciaSueldoDiario) === "")      {   return false;  }
    if ($.trim(diferenciaSueldoHora) === "")        {   return false;  }
    if ($.trim(diferenciaSueldoMensual) === "")     {   return false;  }
    if ($.trim(diferenciaCantidadAdMensual) === "") {   return false;  }
    if ($.trim(isrMensualPrestacion) === "")        {   return false;  }
    if ($.trim(isrMensual) === "")                  {   return false;  }
    if ($.trim(cuotaImss) === "")                   {   return false;  }
    if ($.trim(sueldoMensualNeto) === "")           {   return false;  }
    if ($.trim(codigoRh) === "")                    {   return false;  }
    if ($.trim(sueldoEstructura) === "")            {   return false;  }
    if ($.trim(sueldoHora7) === "")                 {   return false;  }
    if ($.trim(noPlazasAut) === "")                 {   return false;  }
    
    var datos = {"codigo_puesto": codigoPuesto,"codigo_rh": codigoRh,"cantidad_adicional_mensual": cantidadAdMensual,"cuota_imss": cuotaImss,"dif_cant_adicmens": diferenciaCantidadAdMensual,
        "dif_sueldo_diario": diferenciaSueldoDiario,"dif_sueldo_hora": diferenciaSueldoHora,"dif_sueldo_mensual": diferenciaSueldoMensual, "isr_mens_prest": isrMensualPrestacion,"isr_mensual": isrMensual,"nivel": nivelPuesto,
        "num_plazas_autorizadas": noPlazasAut,"puesto": puesto, "status": 1,"sueldoHora7": sueldoHora7,"sueldo_diario": sueldoDiario,"sueldo_diario_doble": sueldoDiarioDoble,"sueldo_estructura": sueldoEstructura,
        "sueldo_hora": sueldoHora,"sueldo_hora_doble": sueldoHoraDoble,"sueldo_hora_triple": sueldoHoraTriple,"sueldo_mensual": sueldoMensual,"sueldo_mensual_neto": sueldoMensualNeto,"sueldo_quincenal_tabulado": sueldoQuincenal};
   
    $.ajax({
        type: "POST",
        url: "catalogos/guardarPuesto",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            toastr["success"]("La información se guardo corretamente", {progressBar: true, closeButton: true});
            $("#agregarPuestoModal").modal('hide');
            $('#noteTable').DataTable().ajax.reload();
        },
        error: function (e) {
             toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
});

function calculaSueldos(){
    var sueldo_mensual = $("#sueldoMensual").val();
    var sueldo_diario = sueldo_mensual/30;
    var sueldo_diario_doble = (sueldo_diario*2);
    var sueldo_hora = sueldo_diario/8;
    var sueldo_hora_doble = sueldo_hora*2;
    var sueldo_hora_triple = sueldo_hora*3;
    var sueldo_hora7 = sueldo_diario/7;
    var sueldo_estructura = parseInt(sueldo_mensual) + parseInt($("#cantidadAdMensual").val());
    
    $("#sueldoDiario").val(sueldo_diario.toFixed(2));
    $("#sueldoDiario_Doble").val(sueldo_diario_doble.toFixed(2));
    $("#sueldoHora").val(sueldo_hora.toFixed(2));
    $("#sueldoHoraDoble").val(sueldo_hora_doble.toFixed(2));
    $("#sueldoHoraTriple").val(sueldo_hora_triple.toFixed(2));
    $("#sueldoHora7").val(sueldo_hora7.toFixed(2));
    $("#sueldoEstructura").val(sueldo_estructura);
 
}

//******************************************************************************
//                          FUNCIÓN LIMPIAR FORMULARIO
//          Limpia el formulario al dar click en el botón Cancelar       
//******************************************************************************
$("#btn_Cancelar").on("click", function (event) {
    $("#FormGuardarPuesto")[0].reset();
});

function validacionEntero(object) {
    if (object.value.length > 5) {
        object.value = object.value.slice(0, 5);

    }
}

function validacion(object){
    if (object.value.includes(".")){
        var indice = object.value.indexOf(".");
        object.value = object.value.slice(0,  indice+3);
    }else{
        if (object.value.length > 9){
            object.value = object.value.slice(0,  9); 
        }
    }
}

//Limpieza de agrega
$('#agregarPuestoModal').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    limpiarParametros();
});

function limpiarParametros(){
    $("#codPuesto").val("");
    $("#nivelPuesto").val("");
    $("#puesto").val("");
    $("#sueldoDiario").val("");
    $("#sueldoDiario_Doble").val("");
    $("#sueldoHora").val("");
    $("#sueldoHoraDoble").val("");
    $("#sueldoHoraTriple").val("");
    $("#sueldoMensual").val("");
    $("#cantidadAdMensual").val("");
    $("#noPlazasAutorizadas").val("");
    $("#sueldoQuincenalTab").val("");
    $("#diferenciaSueldoD").val("");
    $("#diferenciaSueldoH").val("");
    $("#diferenciaSueldoM").val("");
    $("#diferenciaCantidadAdM").val("");
    $("#isrMensualP").val("");
    $("#isrMensual").val("");
    $("#cuotaImss").val("");
    $("#sueldoMensualNeto").val("");
    $("#codigoRh").val("");
    $("#sueldoEstructura").val("");
    $("#sueldoHora7").val("");
}