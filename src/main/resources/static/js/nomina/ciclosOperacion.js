document.addEventListener('DOMContentLoaded', () => {
    listarNominas(0,'#nomina');
    bloqueaBotones();
});
//Tabla que lista los ciclos guardados de la base de datos
$tablaCiclos = $('#tabla_Ciclos').DataTable({
    "ajax": {
        url: 'periodosPago/listarCiclos',
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
    "order": [[4, 'asc']], // Ordena por la segunda columna (anio) de forma ascendente
    columns: [
        {data: "cat_Tipo_Nomina.desc_nomina"},
        {data: "anio"},
        {data: "mes"},
        {data: "semana"},
        {data: "fecha_inicial"},
        {data: "fecha_final"},
        {data: "periodo_aplicacion"},
        {data: "", sTitle: "Editar", width: 100, visible: true, render: function (d, t, r) {
            var he;
            he = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#editarCicloModal" onclick="editarCiclo(' + r.id_ciclo + '); cicloCargaDatos(' + r.id_ciclo + ')"  ><span class="fa fa-edit"></span>Editar</button>';
            return he;
        }
    }]
});

// Función que edita edita el ciclo seleciconado
function editarCiclo(id_ciclo) {
    $('#editarCicloModal').on('hidden.bs.modal', function () {
        //Remoción de mensajes de validación
        document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
	//Invalida el id para evitar inconsistencias
        id_ciclo = null;
    }); 
    $("#editarCicloModal").submit(function (e) {
        event.preventDefault();
        
        var nomina = $('#nomina_edita').val();
        var anio = $('#anio_edita').val();
        var mes = $('#mes_edita').val();
        var semana = $('#semana_edita').val();
        var fecha_inicial = $('#fecha_inicial_edita').val();
        var fecha_final = $('#fecha_final_edita').val();
        var periodo_aplicacion = $('#periodo_aplicacion_edita').val();
        if (anio.length !== 4) {
            return;
        }
        var datos = {"semana":semana, "mes":mes, "anio":anio, "cat_Tipo_Nomina": {"id_tipo_nomina":nomina}, "fecha_inicial" :new Date(fecha_inicial), "fecha_final":new Date(fecha_final), "periodo_aplicacion":periodo_aplicacion};
    

        if (id_ciclo !== null) {
        $.ajax({
            type: "POST",
            url: "periodosPago/editarCiclo/" + id_ciclo,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(datos),
            success: function (data) {
                if ($.isEmptyObject(data)) {
                    toastr["warning"]("No se encontro información para modificar", "Aviso", {progressBar: true, closeButton: true});
                }
                $("#editarCicloModal").modal('hide');
                $('#tabla_Ciclos').DataTable().ajax.reload();
                toastr['success'](data.data, "Se modificó con éxito");
                id_ciclo = null;
            },
            error: function (e) {
                    toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
            }
        });
    }
    });
}

//Limpieza del modal para agregar ciclos
$('#agregarCicloModal').on('hidden.bs.modal', function () {
    //Remoción de mensajes de validación
    document.getElementsByClassName('needs-validation')[0].classList.remove("was-validated");
    $("#desc_tipo_beneficiario_agregar").val("");
    $('#nomina').val("");
    $('#anio').val("");
    $('#mes').val("");
    $('#semana').val("");
    $('#fecha_inicial').val("");
    $('#fecha_final').val("");
    $('#periodo_aplicacion').val("");
});

//Función para traer el valor de la base de datos para edición sobre el mismo
function cicloCargaDatos(id_ciclo) {
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "periodosPago/buscarCiclo/" + id_ciclo,
        dataType: 'json',
        success: function (data) {
            listarNominas(data.data.cat_Tipo_Nomina.id_tipo_nomina,'#nomina_edita');
            (data.data.anio === null) ? $('#anio_edita').val("") : $('#anio_edita').val(data.data.anio);
            (data.data.mes === null) ? $('#mes_edita').val("") : $('#mes_edita').val(data.data.mes);
            (data.data.semana === null) ? $('#semana_edita').val("") : $('#semana_edita').val(data.data.semana);
            (formatoFecha(data.data.fecha_inicial) === null) ? $('#fecha_inicial_edita').val("") : $('#fecha_inicial_edita').val(formatoFecha(data.data.fecha_inicial));
            (formatoFecha(data.data.fecha_final) === null) ? $('#fecha_final_edita').val("") : $('#fecha_final_edita').val(formatoFecha(data.data.fecha_final));
            (data.data.periodo_aplicacion === null) ? $('#periodo_aplicacion_edita').val("") : $('#periodo_aplicacion_edita').val(data.data.periodo_aplicacion);
        },
        error: function (e) {
            alert(e);
        }
    });
}
// Función para que el formato de fecha corresponda con el input
function formatoFecha(fecha) {
    const date = new Date(fecha);
    const dia = String(date.getDate()).padStart(2, '0');
    const mes = String(date.getMonth() + 1).padStart(2, '0');
    const año = date.getFullYear();
    return `${año}-${mes}-${dia}`; // Usando string template correctamente
}

//Llena el select de los diferentes nominas cargadas en la base
function listarNominas(id_nomina, etiqueta) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $(etiqueta).empty().append("<option value=''>* Tipo de Nomina </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        vselected = (data[x].id_tipo_nomina === id_nomina) ? vselected = "selected" : vselected = " ";
                        $(etiqueta).append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Nómina: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
// Guardar ciclos en la tabla con la información llena
function guardarCiclo(){
    event.preventDefault();
    
    var nomina = $('#nomina').val();
    var anio = $('#anio').val();
    var mes = $('#mes').val();
    var semana = $('#semana').val();
    var fecha_inicial = $('#fecha_inicial').val();
    var fecha_final = $('#fecha_final').val();
    var periodo_aplicacion = $('#periodo_aplicacion').val();
    
    if ($.trim(nomina) === "") {
        return false;
    }if ($.trim(anio) === "" || anio.length === 4) {
        return false;
    }if ($.trim(mes) === "") {
        return false;
    }if ($.trim(semana) === "") {
        return false;
    }if ($.trim(fecha_inicial) === "") {
        return false;
    }if ($.trim(fecha_final) === "") {
        return false;
    }if ($.trim(periodo_aplicacion) === "") {
        return false;
    }
     
    var datos = {"semana":semana, "mes":mes, "anio":anio, "cat_Tipo_Nomina": {"id_tipo_nomina":nomina}, "fecha_inicial" :new Date(fecha_inicial), "fecha_final":new Date(fecha_final), "periodo_aplicacion":periodo_aplicacion};
    
    $.ajax({
        type: "POST",
        url: "periodosPago/guardarCiclo",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            toastr['success'](data.data, "Datos Guardados Correctamente");
            $("#agregarCicloModal").modal('hide');
            $('#tabla_Ciclos').DataTable().ajax.reload();
        },
        error: function (e) {
            toastr["warning"]("Error al guardar el Ciclo " + e, " error", {progressBar: true, closeButton: true});
        }
    }); 
}

// Función para abrir el modal de contraseña
function modalPassword(){
    $("#compararContraseniaModal").modal();
}

//Compara la contraseña ingresada con la del sistema
function comparaPassword(){
    var password = $("#contrasenia").val();
    if (password !== "") {
        $.ajax({
            type: "GET",
            url: "usuarios/verificarPassword/" + password,
            dataType: 'json',
            success: function (data) {
                if (data.data === true) {
                    $("#compararContraseniaModal").modal('hide');
                    eliminarCiclos();
                }else{
                    $('#alerta').html("<br>" + "<h6>La Contraseña es Incorrecta, Intente nuevamente.</h6>");
                    $("#contrasenia").val('');
                }
            },
            error: function (e) {
                alert(e);
            }
        });
    }else{
        toastr["warning"]("Ingresar la Contraseña");
    }
}
// Elimina todos los registros con estatus 1 de la tabla Ciclos
function eliminarCiclos() {
    $.ajax({
        type: "POST",
        url: "periodosPago/eliminarCiclos",
        dataType: 'json',
        contentType: "application/json",
        success: function (data) {
            toastr["success"]("Los ciclos fueron eliminados permanentemente");
            $('#tabla_Ciclos').DataTable().ajax.reload();
            $("#contrasenia").val('');
            document.getElementById("elimina").disabled = true;
        },
        error: function (e) {
            toastr["warning"]("Error al eliminar los datos: " + e.responseText, "Error", {progressBar: true, closeButton: true});
        }
    });
}
// Calcula las fechas de los Vales Mensuales de Confianza
function ciclosValesMensuales(){
    var fechaActual = new Date();

    // Obtener el año actual
    var añoActual = fechaActual.getFullYear();

    // Obtener el año anterior
    var añoAnterior = añoActual - 1;

    // Crear fechas para noviembre y diciembre del año anterior
    var noviembre_inicial = new Date(añoAnterior, 10, 16); // El segundo parámetro es el mes, donde 10 representa noviembre
    noviembre_inicial.setHours(0, 0, 0, 0);
    var noviembre_final = new Date(añoActual, 10, 15);
    var fecha_inicial = new Date(noviembre_inicial);
    var semana = 1;
    var complemento_periodo = 2;
    var mes = 11;
    //Avanza las fechas desde el 16 de Noviembre del año anterior al 15 de Noviembre del año actual
    for (var fecha = new Date(noviembre_inicial); fecha <= noviembre_final; fecha.setDate(fecha.getDate() + 1)) {
        var dia_mes = fecha.getDate();
        var dia_semana = fecha.getDay();
        if (mes === 13) {
            mes = 1;
        }
        // Si el dia de la semana es domingo o día 15, realizará el guardado de ese periodo
        if (dia_semana === 0 || dia_mes === 15) {
            var periodo;
            //Acompletar en formato "01" los digitos con una sola longitud
            if (complemento_periodo.toString().length === 1) {
                periodo = fecha_inicial.getFullYear().toString() + "0" + complemento_periodo.toString();
            }else{
                periodo = fecha_inicial.getFullYear().toString()  + complemento_periodo.toString();
            }
            guardaRegistros(fecha_inicial.getFullYear().toString(), mes, semana, fecha_inicial, fecha, parseInt(periodo));
            //Asignar la semana siguiente si el día es 15 o empezarla desde 1 en el siguiente corte, avanzar el periodo en numeros pares si hay un corte y aumentar en 1 el mes
            if (dia_mes === 15) {
                semana = 1;
                complemento_periodo += 2;
                periodo = fecha_inicial.getFullYear() + complemento_periodo;
                mes++;
            }else{
                semana ++;
            }
            //Asignarle nuevo valor a la fecha inicial
            fecha_inicial = new Date(fecha); // Crear una nueva instancia de Date basada en fecha
            fecha_inicial.setDate(fecha_inicial.getDate() + 1); // Avanzar un día
        } 
    }
}

//Guarda los registros de los Vales Mensuales de Confianza
function guardaRegistros(anio, mes, semana, fecha_inicial, fecha_final, periodo){
    var datos = {"cat_Tipo_Nomina":{"id_tipo_nomina": 3}, "anio": anio, "fecha_inicial":fecha_inicial, "fecha_final":fecha_final, "mes":mes, "periodo_aplicacion":periodo, "semana":semana};
    $.ajax({
        type: "POST",
        url: "periodosPago/guardarCiclo",
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $('#tabla_Ciclos').DataTable().ajax.reload();
            toastr['success'](data.data, "Se agregaron los Ciclos con éxito");
        },
        error: function (e) {
            toastr["warning"]("Error al agregar elemento al catálogo : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//Bloquea las acciones para cargar ciclos nuevos si el mes es mayor a Enero
function bloqueaBotones(){
    var fechaActual = new Date();
    var btnNuevoCiclo = document.getElementById("btnNuevoCiclo");
    var btnCargarExcel = document.getElementById("btnCargarExcel");
    var btnEliminarCiclos = document.getElementById("btnEliminarCiclos");
    var btnCiclosVales = document.getElementById("btnCiclosVales");
    // Obtener el año actual
    var mes_actual = fechaActual.getMonth();
    if (mes_actual !== 0) {
        // Deshabilitar los botones
        btnNuevoCiclo.disabled = true;
        btnCargarExcel.disabled = true;
        btnEliminarCiclos.disabled = true;
        btnCiclosVales.disabled = true;
    }else{
        btnNuevoCiclo.disabled = false;
        btnCargarExcel.disabled = false;
        btnEliminarCiclos.disabled = false;
        btnCiclosVales.disabled = false;
    }
}
//Limitar el numero de enteros en el input
function validacion(object) {
    if (object.value.length > 4) {
        object.value = object.value.slice(0, 4);
    }
}

//Limitar el numero de enteros en el input del Periodo
function validacionPeriodo(object) {
    if (object.value.length > 6) {
        object.value = object.value.slice(0, 6);
    }
}

function importarCiclos(){
    window.location.href = 'importarCiclos';
}

