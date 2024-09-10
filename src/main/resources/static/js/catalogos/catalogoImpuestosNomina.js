/* global Intl */

document.addEventListener('DOMContentLoaded', () => {
    $tabla_Impuestos;
    impuestoConcepto(0,"#conceptoImpuesto");
    listarNominas(0,"#nomina");
    listarPeriodos(0,"#periodo_nomina");
    
});


// =============================================================================
//               MOSTRAR LOS REGISTROS DE LA TABLA IMPUESTOS
// =============================================================================
$tabla_Impuestos = $('#tabla_Impuestos').dataTable({
    "ajax": {
        url: "impuestosNomina/listarImpuestos",
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
        {data: "periodo", sTitle: "Periodo", width: 100,sClass: 'text-center', visible: true, render: function (data, type, row) {
                var he="";
                if (data==="1") {
                    he = '<label class="text-danger"><strong>' + 'Anual' + '</strong></label>';
                }else if (data==="2") {
                    he = '<label class="text-success"><strong>Mensual</strong></label>';

                }else if (data==="3") {
                    he = '<label class= "text-primary"><strong>Quincenal</strong></label>';

                }else if (data==="4") {
                    he = '<label class="text-warning"><strong>Semanal</strong></label>';

                }
                return he;
            }},

        {data: "limite_inferior",sTitle: "Lím. Inferior",sClass: 'text-center'},
        {data: "limite_superior",sTitle: "Lím. Superior",sClass: 'text-center'},
        {data: "cuota_fija_porcentaje",sTitle: "Cuota Fija %",sClass: 'text-center'},
        {data: "cuota_fija_dinero",sTitle: "Cuota Fija $",sClass: 'text-center'},
        {data: "porcentaje_excedente_limite_inferior",sTitle: "% Exc.Lím.Inf.",sClass: 'text-center'},
        {data: "anio",sTitle: "Año",sClass: 'text-center'},
        {data: "fecha_inicio",sTitle: "Fecha Inicio",sClass: 'text-center',render: function (data, type, row) {
                return new Date(data).toLocaleDateString('es-CL', { year: 'numeric',month: 'numeric',day: 'numeric' });
            }
        },
        {data: "fecha_fin",sTitle: "Fecha Fin",sClass: 'text-center',render: function (data, type, row) {
                return new Date(data).toLocaleDateString('es-CL', { year: 'numeric',month: 'numeric',day: 'numeric' });
            }},
        {data: "impuesto_concepto_id", sTitle: "Tipo Impuesto", width: 100,sClass: 'text-center', visible: true, render: function (data, type, row) {
                var he="";
                if (data===1) {
                    he = '<div style="background-color: #D4EFDF; color: #17202A; "><strong><span>ISPT</span></strong></div>';
                }else if (data===2) {
                    he = '<div style="background-color: #F9F5B0; color: #17202A; "><strong><span>Cred. Salario</span></strong></div>';

                }else if (data===3) {
                    he = '<div style="background-color: #C1E9F8; color: #17202A; "><strong><span>Subsidio</span></strong></div>';

                }
                return he;
            }},
        {data: "", sTitle: "Editar",sClass: 'text-center', width: 100, visible: true, render: function (d, t, r) {
                var he;
                he = '<button type="button" class="btn btn-outline-primary" onclick="EditarImpuesto(' + r.id_impuesto + ')"><span class="fa fa-edit"></span>Editar</button>';
                return he;
            }
        },
        {data: "", sTitle: "Estatus",sClass: 'text-center', width: 100, visible: true, render: function (d, t, r) {
                var he;
                if (r.estatus === 1) {
                    he = '<button type="button" id="btndistrict"' + r.id_banco + '  class="btn btn-outline-info"class="btn btn-outline-info" style="width: 120px" onclick="inhabilitar(' + r.id_impuesto + ',' + r.estatus + ');"> ' + '<span class="fa fa-minus-circle"></span>Desactivar</button>';
                } else {
                    he = '<button type="button" id="btndistrict"' + r.id_banco + ' class="btn btn-outline-danger" style="width: 120px" onclick="activar(' + r.id_impuesto + ',' + r.estatus + ')"> ' + '<span class="fa fa-minus-circle"></span> Activar</button>';
                }
                return he;
            }
        }
        ]
});

// =============================================================================
//               FUNCIÓN PARA DESACTIVAR (ESTATUS 0) EL IMPUESTO
// =============================================================================
function inhabilitar(id_modelo) {
    $("#modalStatusDesactivar").modal("toggle");
    $('#modalStatusDesactivar').on('hidden.bs.modal', function () {
        id_modelo = null;
    });
    
    $("#botonDeshabilitar").click(function (e) {
        if (id_modelo !== null) {
            $.ajax({
                type: "GET",
                url: "impuestosNomina/estatusImpuesto/" + id_modelo + "/" + 0,
                data: "id_modelo=" + id_modelo, "activo": 0,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusDesactivar").modal('hide');
                        toastr['warning']("El registro ha sido inhabilitado", "Aviso");
                        $('#tabla_Impuestos').DataTable().ajax.reload();
                        id_modelo = null;
                    }
                }
            });
        }
    });
}


// =============================================================================
//               FUNCIÓN PARA ACTIVAR (ESTATUS 1) EL IMPUESTO
// =============================================================================
function activar(id_modelo) {
    $("#modalStatusActivar").modal("toggle");
    $('#modalStatusActivar').on('hidden.bs.modal', function () {
        id_modelo = null;
    });
    
    $("#botonCambiar").click(function (e) {
        if (id_modelo !== null) {
            $.ajax({
                type: "GET",
                url: "impuestosNomina/estatusImpuesto/" + id_modelo + "/" + 1,
                data: "id_modelo=" + id_modelo, "activo": 1,
                success: function (data) {
                    if ($.isEmptyObject(data)) {
                        toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
                    } else {
                        $("#modalStatusActivar").modal('hide');
                        toastr['success']("El registro ha sido habilitado", "Aviso");
                        
                        $('#tabla_Impuestos').DataTable().ajax.reload();
                        id_modelo = null;
                    }
                }
            });
        }
    });
}

// =============================================================================
//            REDIRIGIR A UNA NUEVA PANTALLA PARA CARGAR EL EXCEL
// =============================================================================
function cargarExcel() {
    window.location.href = 'cargarImpuestos';
}




/*==============================================================================
                            EDITAR IMPUESTO
       Obtiene la información del elemento seleccionado en la tabla
 =============================================================================*/
function EditarImpuesto(id_impuesto) {
    document.getElementById("id_impuesto").value = id_impuesto;
    $("#modificarImpuestoModal").modal("toggle");
     $.ajax({
        type: "GET",
        url: "impuestosNomina/buscarImpuestoNomina/" + id_impuesto,
        dataType: 'json',
        success: function (data) {
            $("#limite_inferiorEdita").val(data.data.limite_inferior);
            $("#limite_superiorEdita").val(data.data.limite_superior);
            $("#cuotaFijaPorcentajeEdita").val(data.data.cuota_fija_porcentaje);
            $("#cuotaFijaDineroEdita").val(data.data.cuota_fija_dinero);
            $("#porcentaje_excedenteEdita").val(data.data.porcentaje_excedente_limite_inferior);
            $("#articuloEdita").val(data.data.articulo);
            $("#origenEdita").val(data.data.origen);
            $("#fechaInicioEdita").val(formatoFecha(data.data.fecha_inicio));
            $("#fechaFinEdita").val(formatoFecha(data.data.fecha_fin));
            $("#vigenciaEdita").val(data.data.vigencia);
           
            data.data.impuesto_concepto_id === null ? impuestoConcepto(0, "#conceptoImpuestoEdita") : impuestoConcepto(parseInt(data.data.impuesto_concepto_id), "#conceptoImpuestoEdita");
            data.data.nomina_id === null ? listarNominas(0, "#nominaEdita") : listarNominas(parseInt(data.data.nomina_id), "#nominaEdita");
            data.data.periodo === null ? listarPeriodos(0, "#periodo_nominaEdita") : listarPeriodos(parseInt(data.data.periodo), "#periodo_nominaEdita");
     },
        error: function (e) {
            alert(e);
        }
    });
}

/*==============================================================================
                FUNCIÓN PARA CAMBIAR EL FORMATO DE FECHA
 =============================================================================*/
function formatoFecha(fecha) {
    const date = new Date(fecha);
    const dia = String(date.getDate()).padStart(2, '0');
    const mes = String(date.getMonth() + 1).padStart(2, '0');
    const año = date.getFullYear();
    return `${año}-${mes}-${dia}`;
}

/*==============================================================================
                            ACTUALIZAR IMPUESTOS
                Obtiene la información del elemento a actualizar
 =============================================================================*/
 function actualizar_DatosImpuesto(id_impuesto) {
    event.preventDefault();
    
    var periodo_nomina = $("#periodo_nominaEdita").val();
    var nomina = $("#nominaEdita").val();
    var limite_inferior = $("#limite_inferiorEdita").val();
    var limite_superior = $("#limite_superiorEdita").val();
    var cuotaFijaPorcentaje = $("#cuotaFijaPorcentajeEdita").val();
    var cuotaFijaDinero = $("#cuotaFijaDineroEdita").val();
    var porcentaje_excedente = $("#porcentaje_excedenteEdita").val();
    var articulo = $("#articuloEdita").val();
    var origen = $("#origenEdita").val();
    var fechaInicio = $("#fechaInicioEdita").val();
    var fechaFin = $("#fechaFinEdita").val();
    var vigencia = $("#vigenciaEdita").val();
    var conceptoImpuesto = $("#conceptoImpuestoEdita").val();
     
    var date = new Date(fechaInicio);
    var formattedFechaInicio = date.toISOString();
    
    var dateFechaFin = new Date(fechaFin);
    var formattedFechaFin = dateFechaFin.toISOString();
    
    if ($.trim(periodo_nomina)=== "" )  {
        toastr['error']("Selecciona el Período", "Aviso");
        return false;
    }
    
    if ($.trim(nomina)=== "" )  {
        toastr['error']("Selecciona el tipo de Nómina", "Aviso");
        return false;
    }
    if ($.trim(limite_inferior)=== "" || parseFloat(limite_inferior)<=0)  {
        toastr['error']("Ingresa un Límite Inferior correcto. No debe tener valores vacíos o iguales a 0", "Aviso");
        return false;
    }
    
    if ($.trim(limite_superior)=== "" || parseFloat(limite_superior)===0)  {
        toastr['error']("Ingresa un Límite Superior correcto. No debe tener valores vacíos o iguales a 0", "Aviso");
        return false;
    }
    if ($.trim(cuotaFijaPorcentaje)=== "" || parseFloat(cuotaFijaPorcentaje)===0)  {
        toastr['error']("Ingresa la Cuota Fija de Porcentaje correcta. No debe tener valores vacíos o iguales a 0", "Aviso");
        return false;
    }
    if ($.trim(cuotaFijaDinero)=== "" || parseFloat(cuotaFijaDinero)===0)  {
        toastr['error']("Ingresa la Cuota Fija de Dinero correcta. No debe tener valores vacíos o iguales a 0", "Aviso");
        return false;
    }
    if ($.trim(porcentaje_excedente)=== "" || parseFloat(porcentaje_excedente)===0)  {
        toastr['error']("Ingresa un Porcentaje Excedente correcto. No debe tener valores vacíos o iguales a 0", "Aviso");
        return false;
    }
    if ($.trim(articulo)=== "" )  {
        toastr['error']("Ingresa el Artículo", "Aviso");
        return false;
    }
    if ($.trim(origen)=== "" )  {
        toastr['error']("Ingresa el origen", "Aviso");
        return false;
    }
    if ($.trim(fechaInicio)=== "" )  {
        toastr['error']("Ingresa la Fecha de Inicio", "Aviso");
        return false;
    }
    
    if ($.trim(fechaFin)=== "" )  {
        toastr['error']("Ingresa la Fecha Final", "Aviso");
        return false;
    }
    if ($.trim(vigencia)=== "" )  {
        toastr['error']("Ingresa la vigencia", "Aviso");
        return false;
    }
    if ($.trim(conceptoImpuesto)=== "" )  {
        toastr['error']("Selecciona el tipo de impuesto", "Aviso");
        return false;
    }
    
    var fechaActual = new Date();
    var anioActual = fechaActual.getFullYear();

    var datos = {"anio": anioActual, "articulo":articulo, "cuota_fija_dinero": parseFloat(cuotaFijaDinero), "cuota_fija_porcentaje": parseFloat(cuotaFijaPorcentaje), "fecha_fin": formattedFechaFin,
                 "fecha_inicio":formattedFechaInicio, "limite_inferior": parseFloat(limite_inferior),"limite_superior": parseFloat(limite_superior),"nomina_id": nomina,"origen": origen,
                 "periodo": periodo_nomina, "porcentaje_excedente_limite_inferior": parseFloat(porcentaje_excedente),"vigencia": vigencia, "impuesto_concepto_id":conceptoImpuesto};
   
   
    $.ajax({
        type: "POST",
        url: "impuestosNomina/editarCatImpuesto/" + id_impuesto,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            $('#tabla_Impuestos').DataTable().ajax.reload();
            $("#modificarImpuestoModal").modal('hide');
            toastr["success"]("La información ha sido actualizada...", "Aviso", {progressBar: true, closeButton: true});
        },
        error: function (e) {
            toastr['error']("No se actualizo la información", "Aviso");
        }
    }); 
    }
    

/*=============================================
        CATALOGO IMPUESTO CONCEPTO
 =============================================*/
function impuestoConcepto(id_impuesto,selectNombre) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarImpuestoConcepto",
        dataType: 'json',
        success: function (data) {
            
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $(selectNombre).empty().append("<option value=''>Impuesto Concepto </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        var p= data[x].id_impuesto_concepto;
                       
                        if (p === id_impuesto) {
                            vselected = "selected";
                        } else {
                            vselected = " ";
                        }
                        $(selectNombre).append('<option value="' + data[x].id_impuesto_concepto + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Unidades de Medida : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*==============================================================================
                        CATALOGO TIPOS DE NÓMINA
 =============================================================================*/
function listarNominas(idNomina,selectNombre) {
     $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
           
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $(selectNombre).empty().append("<option value=''>Tipo Nómina</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        var p= data[x].id_tipo_nomina;
                       
                        if (p === idNomina) {
                            vselected = "selected";
                        } else {
                            vselected = " ";
                        }
                        $(selectNombre).append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Unidades de Medida : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*==============================================================================
                        CATALOGO TIPOS DE PERÍODO
 =============================================================================*/
function listarPeriodos(id_periodo_impuesto,selectNombre) {
     $.ajax({
        type: "GET",
        url: "catalogos/listarPeriodoImpuesto",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $(selectNombre).empty().append("<option value=''>Tipo Período</option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        var p= data[x].id_periodo_impuesto;
                       
                        if (p === id_periodo_impuesto) {
                            vselected = "selected";
                        } else {
                            vselected = " ";
                        }
                        $(selectNombre).append('<option value="' + data[x].id_periodo_impuesto + '"' + vselected + '>' + data[x].descripcion + '</option>');
                    }
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Unidades de Medida : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


/*==============================================================================
                        GUARDAR UN NUEVO IMPUESTO
 =============================================================================*/
$("#FormGuardarImpuesto").submit(function (e) {
    event.preventDefault();
    
    var periodo_nomina = $("#periodo_nomina").val();
    var nomina = $("#nomina").val();
    var limite_inferior = $("#limite_inferior").val();
    var limite_superior = $("#limite_superior").val();
    var cuotaFijaPorcentaje = $("#cuotaFijaPorcentaje").val();
    var cuotaFijaDinero = $("#cuotaFijaDinero").val();
    var porcentaje_excedente = $("#porcentaje_excedente").val();
    var articulo = $("#articulo").val();
    var origen = $("#origen").val();
    var fechaInicio = $("#fechaInicio").val();
    var fechaFin = $("#fechaFin").val();
    var vigencia = $("#vigencia").val();
    var conceptoImpuesto = $("#conceptoImpuesto").val();
     
    var date = new Date(fechaInicio);
    var formattedFechaInicio = date.toISOString();
    
    var dateFechaFin = new Date(fechaFin);
    var formattedFechaFin = dateFechaFin.toISOString();
    if ($.trim(periodo_nomina)=== "" )  {
        toastr['error']("Selecciona el Período", "Aviso");
        return false;
    }
    
    if ($.trim(nomina)=== "" )  {
        toastr['error']("Selecciona el tipo de Nómina", "Aviso");
        return false;
    }
    
    if ($.trim(limite_inferior)=== "" )  {
        toastr['error']("Selecciona el Período", "Aviso");
        return false;
    }
    
    if ($.trim(limite_superior)=== "" )  {
        toastr['error']("Selecciona el Período", "Aviso");
        return false;
    }
    if ($.trim(cuotaFijaPorcentaje)=== "" )  {
        toastr['error']("Selecciona el Período", "Aviso");
        return false;
    }
    if ($.trim(cuotaFijaDinero)=== "" )  {
        toastr['error']("Selecciona el Período", "Aviso");
        return false;
    }
    if ($.trim(porcentaje_excedente)=== "" )  {
        toastr['error']("Selecciona el Período", "Aviso");
        return false;
    }
    if ($.trim(articulo)=== "" )  {
        toastr['error']("Selecciona el Período", "Aviso");
        return false;
    }
    if ($.trim(origen)=== "" )  {
        toastr['error']("Selecciona el Período", "Aviso");
        return false;
    }
    if ($.trim(fechaInicio)=== "" )  {
        toastr['error']("Selecciona el Período", "Aviso");
        return false;
    }
    
    if ($.trim(fechaFin)=== "" )  {
        toastr['error']("Selecciona el Período", "Aviso");
        return false;
    }
    if ($.trim(vigencia)=== "" )  {
        toastr['error']("Selecciona el Período", "Aviso");
        return false;
    }
    if ($.trim(conceptoImpuesto)=== "" )  {
        toastr['error']("Selecciona el Período", "Aviso");
        return false;
    }
    
    var fechaActual = new Date();
    var anioActual = fechaActual.getFullYear();

    var datos = {"anio": anioActual, "articulo":articulo, "cuota_fija_dinero": cuotaFijaDinero, "cuota_fija_porcentaje": cuotaFijaPorcentaje, "estatus": 1, "fecha_fin": formattedFechaFin,
                 "fecha_inicio":formattedFechaInicio, "limite_inferior": limite_inferior,"limite_superior": limite_superior,"nomina_id": nomina,"origen": origen,
                 "periodo": periodo_nomina, "porcentaje_excedente_limite_inferior": porcentaje_excedente,"vigencia": vigencia, "impuesto_concepto_id":conceptoImpuesto};
   
    $.ajax({
        type: "POST",
        url: "impuestosNomina/guardarCatImpuestosNomina",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(datos),

        success: function (data) {
            $('#tabla_Impuestos').DataTable().ajax.reload();
            $("#agregarImpuestoModal").modal("hide");
            toastr["success"]("La información ha sido registrada...", "Aviso", {progressBar: true, closeButton: true});
            
        },
        error: function (e) {
            toastr['error'](e.responseJSON.messages, "Aviso");
        }
    });
});


/*==============================================================================
                    VALIDACIÓN PARA NÚMEROS DECIMALES 
 =============================================================================*/
function validacion(object) {
    if (object.value.includes(".")) {
        var indice = object.value.indexOf(".");
        object.value = object.value.slice(0, indice + 3);
    } else {
        if (object.value.length > 6) {
            object.value = object.value.slice(0, 6);
        }
    }
}

/*==============================================================================
                    VALIDACIÓN PARA NÚMEROS (PORCENTAJE)
 =============================================================================*/
function validacionPorcentaje(object) {
    if (object.value.includes(".")) {
        var indice = object.value.indexOf(".");
        var parteDecimal = object.value.slice(indice + 1);

        if (parteDecimal.length > 5) {
            object.value = object.value.slice(0, indice + 6);
        }
    } else {
        if (object.value.length > 6) {
            object.value = object.value.slice(0, 6);
        }
    }
}


/*==============================================================================
                    VALIDACIÓN PARA NÚMEROS ENTEROS 
 =============================================================================*/
function validacionEntero(object) {
    if (object.value.length > 6) {
        object.value = object.value.slice(0, 6);
    }
}

/*==============================================================================
                    LIMPIAR DATOS DEL FORMULARIO
 =============================================================================*/
$('#agregarImpuestoModal').on('hidden.bs.modal', function () {
    const formulario = document.getElementById('FormGuardarImpuesto');
    formulario.reset();
    formulario.classList.remove('was-validated'); 
});


//$(document).ready(function () {
//    // Detecta la carga del archivo
//    $('#fileInput').on("change", function (event) {
//        const file = event.target.files[0];
//        if (file) {
//            if (!file.name.endsWith(".xlsx")) {
//                toastr.error("Sólo se permite la carga de archivos xlsx");
//                // Se elimina el archivo cargado
//                $("#fileInput").val("");
//                return;
//            }
//            let formData = new FormData();
//                        formData.append('archivo', file);
//
//                        // Envía los datos al backend
//                        $.ajax({
//                            url: 'impuestosNomina/importarImpuestosExcel',
//                            type: 'POST',
//                            data: formData,
//                            contentType: false,
//                            processData: false,
//                            success: function (response) {
//                                // Maneja la respuesta exitosa
//                                $('#tabla_Impuestos').DataTable().ajax.reload();
//                                toastr["success"]("Se importaron correctamente los impuestos", "Éxito", {progressBar: true, closeButton: true});
//                            },
//                            error: function (xhr, status, error) {
//                                var errorMessage;
//                                if (xhr.responseJSON && xhr.responseJSON.message) {
//                                    errorMessage = xhr.responseJSON.message;
//                                } else {
//                                    errorMessage = "Error del servidor: " + error;
//                                }
//                                toastr["error"]("No se cargaron las plantas de la RA15. <br>" + errorMessage, "Error", {progressBar: true, closeButton: true});
//                            }
//
//                        });
//            
//    }
//});
//});

