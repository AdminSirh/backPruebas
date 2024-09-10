/* global XLSX, Swal */

document.addEventListener('DOMContentLoaded', () => {
   listarPeriodos(0,"#cmbPeriodoCarga");
   impuestoConcepto(0,"#cmbImpuestoConcepto");
    var startYear = 2022;
    for (i = new Date().getFullYear(); i > startYear; i--){
        $('#cmbAnio').append($('<option />').val(i).html(i));
    }
    
});

/*==============================================================================
                REDIGIR A NUEVA PANTALLA 
 =============================================================================*/
function verImpuesto() {
    window.location.href = 'catalogoImpuestosNomina';
}


/*==============================================================================
                FUNCIÓN LISTAR TIPOS DE PERÍODOS DE IMPUESTOS
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
                DETECTAR CAMBIOS EN EL SELECT DE PERÍODO
 =============================================================================*/
var select = document.getElementById('cmbPeriodoCarga');
var label = document.getElementById('descripcionIncidencia');
select.addEventListener('change', function () {// Agregar un event listener para el evento change
    var selectedOption = select.options[select.selectedIndex].text;// Obtener el valor seleccionado del select
    
    if (selectedOption=== "Tipo Período") {
        label.textContent = "SIN PERIODO SELECCIONADO " ; // Actualizar el contenido del label
    }else {
        label.textContent = "PERIODO: " + selectedOption.toString().toUpperCase(); // Actualizar el contenido del label
    }
});



/*==============================================================================
                       FUNCIÓN CARGA IMPUESTOS EXCEL
  Esta función maneja la carga de un archivo Excel.Verifica la selección de 
  opciones y el archivo cargado. Luego, realiza validaciones específicas y 
  muestra mensajes correspondientes. Finalmente, guarda los datos del archivo 
  si se confirma la carga.
 =============================================================================*/
function cargaImpExcel(){
    var cmbPeriodoCarga = $("#cmbPeriodoCarga").val();
    var cmbAnio = $("#cmbAnio").val();
    var cmbImpuestoConcepto = $("#cmbImpuestoConcepto").val();
    
    var file = $('#file').prop('files')[0];
    var formData = new FormData();
    formData.append('file', file);
   
    if ($.trim(cmbPeriodoCarga)=== "" )  {
        toastr['error']("Selecciona el tipo de Período", "Aviso");
        return false;
    }
    
    if ($.trim(cmbAnio)=== "" )  {
        toastr['error']("Selecciona el Año", "Aviso");
        return false;
    }
    
    if ($.trim(cmbImpuestoConcepto)=== "" )  {
        toastr['error']("Selecciona el Tipo de Impuesto", "Aviso");
        return false;
    }
    
    if (file === undefined || file === null) {
        toastr['error']("Selecciona un archivo. El campo NO debe estar Vacio", "Aviso");
        return false;
    }
    
    var numeroColumnasRequeridas = 11; // Columnas requeridas
    // La función validarArchivoExcel verificar si el archivo cargado cumple con el requisito de columnas requeridas.
    validarArchivoExcel(file, numeroColumnasRequeridas)
        .then((archivoValido) => {
            //Se agregan datos al objeto FormData para enviar al servidor.
            formData.append('archivo', file);
            formData.append('anio', cmbAnio);
            formData.append('periodo', cmbPeriodoCarga);
            formData.append('impuestoConcepto', cmbImpuestoConcepto);
            
            // Validación para la carga anual de impuestos y concepto igual 1
            if (cmbPeriodoCarga === "1" && cmbImpuestoConcepto === "1") { 
                $.ajax({
                    type: "GET",
                    url: 'impuestosNomina/buscarImpuestosStatus/' + parseInt(cmbAnio),
                    dataType: 'json',
                    success: function (data) {
                        //Si se encuentran registros de impuestos, se muestra un mensaje de advertencia al usuario
                        // Es decir, existen registros del año actual
                        if (data > 0) {
                            Swal.fire({
                                title: 'Existen registros de impuestos en este año.',
                                text: '¿Te gustaría eliminarlos y guardar los registros actuales?',
                                icon: 'warning',
                                showCancelButton: true,
                                confirmButtonColor: '#3085d6',
                                cancelButtonColor: '#d33',
                                confirmButtonText: 'Guardar Registros',
                                cancelButtonText: 'Cancelar'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    // Cambiar estatus de los registros y luego se guardan los nuevos registros mediante la función guardarRegistrosExcel
                                    cambiarEstatusRegistrosAnuales(cmbAnio);
                                    guardarRegistrosExcel(formData);
                                    Swal.fire('Archivo Cargado', 'Los datos han sido actualizados', 'success');
                                    // Limpiar Formulario
                                    listarPeriodos(0, "#cmbPeriodoCarga");
                                    impuestoConcepto(0, "#cmbImpuestoConcepto");
                                    document.getElementById("file").value = "";
                                } else {
                                    Swal.fire('Cancelado', 'Los datos no fueron actualizados', 'error');
                                }
                            });
                        }else {
                                // Hacer la carga de los archivos para las otras opciones del select (Subsidio y crédito al salario)
                                Swal.fire({
                                    title: 'Carga De Impuestos',
                                    text: '¿Deseas continuar con la carga del archivo de Excel?',
                                    iconHtml: '<i class="fas fa-exclamation-circle" style="color: #6CC3E3; font-size: 85px;"></i>',
                                    showCancelButton: true,
                                    confirmButtonColor: '#3085d6',
                                    cancelButtonColor: '#d33',
                                    confirmButtonText: 'Guardar Registros',
                                    cancelButtonText: 'Cancelar'
                                }).then((result) => {
                                    if (result.isConfirmed) {
                                        guardarRegistrosExcel(formData);
                                        Swal.fire('Archivo Cargado', 'Los datos han sido actualizados', 'success');
                                        listarPeriodos(0, "#cmbPeriodoCarga");
                                        impuestoConcepto(0, "#cmbImpuestoConcepto");
                                        document.getElementById("file").value = "";
                                    } else {
                                        Swal.fire('Cancelado', 'Los datos no fueron actualizados', 'error');
                                    }
                                });
                            }
                    },
                    error: function (e) {
                        toastr["warning"]("Error al recuperar Unidades de Medida : " + e, " error", {progressBar: true, closeButton: true});
                    }
                });
            } else {
                // Hacer la carga de los archivos para las otras opciones del select (Subsidio y crédito al salario)
                Swal.fire({
                    title: 'Carga De Impuestos',
                    text: '¿Deseas continuar con la carga del archivo de Excel?',
                    iconHtml: '<i class="fas fa-exclamation-circle" style="color: #6CC3E3; font-size: 85px;"></i>',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Guardar Registros',
                    cancelButtonText: 'Cancelar'
                }).then((result) => {
                    if (result.isConfirmed) {
                        guardarRegistrosExcel(formData);
                        Swal.fire('Archivo Cargado', 'Los datos han sido actualizados', 'success');
                        listarPeriodos(0, "#cmbPeriodoCarga");
                        impuestoConcepto(0, "#cmbImpuestoConcepto");
                        document.getElementById("file").value = "";
                    } else {
                        Swal.fire('Cancelado', 'Los datos no fueron actualizados', 'error');
                    }
                });
            }
        })
        .catch((error) => {
            console.error(error);
        });
}

/*==============================================================================
                         GUARDAR REGISTROS EXCEL
          Invoca al servicio para guardar los impuestos en la tabla
 =============================================================================*/
function guardarRegistrosExcel(formData) {
    $.ajax({
        url: 'impuestosNomina/importarImpuestosExcel',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function (response) {
            toastr["success"]("Se importaron correctamente los impuestos", "Éxito", {progressBar: true, closeButton: true});
        },
        error: function (xhr, status, error) {
            var errorMessage;
            if (xhr.responseJSON && xhr.responseJSON.message) {
                errorMessage = xhr.responseJSON.message;
            } else {
                errorMessage = "Error del servidor: " + error;
            }
            toastr["error"]("No se cargaron los Impuestos. <br>" + errorMessage, "Error", {progressBar: true, closeButton: true});
        }

    });

}




/*==============================================================================
           CAMBIAR ESTATUS A 0 DE LOS REGISTROS DE LA BASE
 =============================================================================*/
function cambiarEstatusRegistrosAnuales(anio) {
    $.ajax({
        url: 'impuestosNomina/actualizarStatusImpuestosAnuales/'+ anio,
        type: 'POST',
        success: function (response) {
            toastr["success"]("Se importaron correctamente los impuestos", "Éxito", {progressBar: true, closeButton: true});
        },
        error: function (status, error) {
            toastr["error"]("No se actualizaron los Impuestos.", "Error", {progressBar: true, closeButton: true});
        }

    });
}



/*==============================================================================
             VALIDACIÓN DE FORMATO DEL ARCHIVO DE EXCEL
 =============================================================================*/
function validarArchivoExcel(files, numeroColumnas) {
    return new Promise((resolve, reject) => {
        var reader = new FileReader();
        reader.onload = function(e) {
            var data = new Uint8Array(e.target.result);
            var workbook = XLSX.read(data, {type: 'array'});
            
            // Obtener la primera hoja del libro de trabajo
            var firstSheetName = workbook.SheetNames[0];
            var worksheet = workbook.Sheets[firstSheetName];
            
            // Contar el número de columnas en la primera fila
            var range = XLSX.utils.decode_range(worksheet['!ref']);
            var numeroColumnasArchivo = range.e.c + 1;
            
            // Verificar si el número de columnas coincide con el requerido
            if (numeroColumnasArchivo !== numeroColumnas) {
                Swal.fire('FORMATO DE EXCEL INVÁLIDO', 'El archivo Excel debe contener exactamente ' + numeroColumnas+ " columnas. Por favor verifica que el archivo este correcto... ", 'error');
//                  
            } else {
                resolve(true);
            }
        };
        reader.readAsArrayBuffer(files);
    });
}