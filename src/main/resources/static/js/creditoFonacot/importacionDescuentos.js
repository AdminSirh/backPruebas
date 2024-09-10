/* global Intl */
document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    validaBotonFile();
    ultimaActualizacion();
});
//Manda mensaje con las nominas activas y cargadas en el mismo día
function ultimaActualizacion(){
    $.ajax({
        type: "GET",
        url: "creditoFonacot/listarCuentasFonacot",
        dataType: 'json',
        success: function (data) {
            var fecha = new Date();
            var options = { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' };
            var fecha_f = new Intl.DateTimeFormat('es-ES', options).format(fecha);
            const actualizacion = document.getElementById("actualizacion");
            const fecha_carga = document.getElementById("fecha_carga");
            console.log(data);
//            if (data === undefined) {
//                actualizacion.textContent = "NO HAY NÓMINAS ACTIVAS CARGADAS : " + fecha_f;
//                const idsSinDuplicados = [];
//                listarNominas(idsSinDuplicados);
//            }else{
//                const nombres_nominas = new Set();
//                const id_nominas = new Set();
//                const fecha_modificacion = new Set();
//                for (var i = 0; i < data.length; i++) {
//                    nombres_nominas.add(data[i].cat_Tipo_Nomina.desc_nomina);
//                    id_nominas.add(data[i].cat_Tipo_Nomina.id_tipo_nomina);
//                    fecha_modificacion.add(data[i].fecha_movimiento);
//                }
//                const nombresSinDuplicados = [...nombres_nominas];
//                const idsSinDuplicados = [...id_nominas];
//                const fechasSinDuplicados = [...fecha_modificacion];
//                actualizacion.textContent = "NÓMINAS CARGADAS :  " + nombresSinDuplicados;
//                fecha_carga.textContent = "DÍA: " + fechasSinDuplicados;
//                listarNominas(idsSinDuplicados);
//            }
            
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar el Periodo");
        }
    });
}


//Esta funcion inhabilita los botones de guardar, eliminar y cargar archivo cuando el select nomina no está seleccionado
function validaBotonFile(){
    if ($("#tipo_nomina").val() === "" || $("#tipo_nomina").val() === null) {
        document.getElementById("file").disabled = true;
        document.getElementById("guarda").disabled = true;
        document.getElementById("elimina").disabled = true;
        
    }else{
        document.getElementById("file").disabled = false;
        
    } 
}
//Busca el periodo de pago para colocarlo en la tabla en formato Ej:"202315"
function buscaIdPeriodo(id_periodo_pago){
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPeriodoPagoId/" + id_periodo_pago,
        dataType: 'json',
        success: function (data) {
            $('#id_periodos').val(data.data.periodo);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar el Periodo");
        }
    });
}

//Llena el select de los diferentes nominas que hay
function listarNominas(id_nominas) {
    $.ajax({
        type: "GET",
        url: "catalogos/listarTipoNominas",
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#tipo_nomina').empty().append("<option value=''>* Tipo de Nomina </option> ");
                var vselected = "";
                if (data.length > 0) {
                    for (var x = 0; x < data.length; x++) {
                        //Lista las nominas que no han sido guardadas, para evitar duplicidad
                        if (!id_nominas.includes(data[x].id_tipo_nomina)|| id_nominas.length===0) {
                            $('#tipo_nomina').append('<option value="' + data[x].id_tipo_nomina + '"' + vselected + '>' + data[x].desc_nomina + '</option>');
                        }
                    }
                }
                
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Nómina: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//Obtiene el nombre de la nomina para mostrar el modal al usuario
function obtenerNominaNombre(){
    var id_nomina = $('#tipo_nomina').val();
    $.ajax({
        type: "GET",
        url: "catalogos/buscarNomina/" + id_nomina,
        dataType: 'json',
        success: function (data) {
            $('#nombre_nomina').val(data.data.desc_nomina);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar Select Nómina: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}


//Función para obtener los periodos de pago dependiendo de la nomina seleccionada
function obtenPeriodos() {
    var id_nomina = $("#tipo_nomina").val();
    if (id_nomina === null || id_nomina === "") {
        validaBotonFile();
    }else{
        $.ajax({
        type: "GET",
        url: "trabajador/buscaPeriodosFechas/" + id_nomina,
        dataType: 'json',
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            }else{
                
                var vselected = "";
                var compara_periodo = comparaFechas(data,vselected);
                    if (data.length > 0) {
                        for (var x = 0; x < data.length; x++) {
                            var n_periodo = data[x].periodo;
                            var fecha_inicial_formato = new Date(data[x].fecha_inicial);
                            var dias_inicial = fecha_inicial_formato.getDate();
                            var meses_inicial = fecha_inicial_formato.getMonth() + 1; 
                            var anios_inicial = fecha_inicial_formato.getFullYear(); 

                            var fecha_final_formato = new Date(data[x].fecha_final);
                            var dias_final = fecha_final_formato.getDate();
                            var meses_final = fecha_final_formato.getMonth() + 1; 
                            var anios_final = fecha_final_formato.getFullYear();
                            if (compara_periodo !== data[x].periodo) {
                                
                                $('#periodo_generacion').append('<option value="' + data[x].id_periodo_pago + '"' + vselected + '>'+ 'P. '+ (n_periodo.toString()).substr(4,5) +" : "+ " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial + 
                                     '--' + dias_final + '/' + meses_final + '/' + anios_final +'</option>');
                             
                                $('#periodo_aplicacion').append('<option value="' + data[x].id_periodo_pago + '"' + vselected + '>'+ 'P. '+ (n_periodo.toString()).substr(4,5) +" : "+ " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial + 
                                     '--' + dias_final + '/' + meses_final + '/' + anios_final +'</option>');
                            }
                        }
                    }
                }
            },
            error: function (e) {
            alert(e);
            }
        });
    }
}

// ASIGNA EL PERIODO CORRESPONDIENTE A LA FECHA DE SOLICITUD DEL DESCUENTO FONACOT
function comparaFechas(data,vselected){
    var hoy = new Date();
    hoy.setHours(0, 0, 0, 0);
    
    for (var i = 0; i < data.length; i++) {
        var n_periodo = data[i].periodo;
        var fecha_inicial_formato = new Date(data[i].fecha_inicial);
        var dias_inicial = fecha_inicial_formato.getDate();
        var meses_inicial = fecha_inicial_formato.getMonth() + 1; 
        var anios_inicial = fecha_inicial_formato.getFullYear(); 

        var fecha_final_formato = new Date(data[i].fecha_final);
        var dias_final = fecha_final_formato.getDate();
        var meses_final = fecha_final_formato.getMonth() + 1; 
        var anios_final = fecha_final_formato.getFullYear();
        fecha_inicial_formato.setHours(0,0,0,0);
        fecha_final_formato.setHours(0,0,0,0);
        var fecha_corte_formato = new Date(data[i].fecha_corte);
        fecha_corte_formato.setHours(0,0,0,0);
        
        if (hoy <= fecha_corte_formato) {
            
            $('#periodo_generacion').empty().append('<option value="' + data[i].id_periodo_pago + '"' + vselected + '>'+ 'P. '+ (n_periodo.toString()).substr(4,5) +" : "+ " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial + 
                                 '--' + dias_final + '/' + meses_final + '/' + anios_final +'</option>');
                         
            $('#periodo_aplicacion').empty().append('<option value="' + data[i].id_periodo_pago + '"' + vselected + '>'+ 'P. '+ (n_periodo.toString()).substr(4,5) +" : "+ " " + dias_inicial + '/' + meses_inicial + '/' + anios_inicial + 
                                 '--' + dias_final + '/' + meses_final + '/' + anios_final +'</option>');
            return data[i].periodo;
            
        }else if(hoy > fecha_corte_formato){
            var contador = i + 1;
            n_periodo = data[contador].periodo;
            fecha_inicial_formato = new Date(data[contador].fecha_inicial);
            fecha_final_formato = new Date(data[contador].fecha_final);
            
            $('#periodo_generacion').empty().append('<option value="' + data[contador].id_periodo_pago + '"' + vselected + '>'+ 'P. '+ (n_periodo.toString()).substr(4,5) +" : "+ " " + fecha_inicial_formato.getDate() + '/' + (fecha_inicial_formato.getMonth()+1) + '/' + fecha_inicial_formato.getFullYear() + 
                                 '--' + fecha_final_formato.getDate() + '/' + (fecha_final_formato.getMonth()+1) + '/' + fecha_final_formato.getFullYear() +'</option>');
                      
            $('#periodo_aplicacion').empty().append('<option value="' + data[contador].id_periodo_pago + '"' + vselected + '>'+ 'P. '+ (n_periodo.toString()).substr(4,5) +" : "+ " " + fecha_inicial_formato.getDate() + '/' + (fecha_inicial_formato.getMonth()+1) + '/' + fecha_inicial_formato.getFullYear() + 
                                 '--' + fecha_final_formato.getDate() + '/' + (fecha_final_formato.getMonth()+1) + '/' + fecha_final_formato.getFullYear() +'</option>');
            
            return data[contador].periodo;
        }
    }
}
//Valida la cantidad de columnas en el CSV para poder procesarlo y detecta los expedientes desconocidos para llenar un arreglo
function revisaExpedientes() {
    document.getElementById("file").disabled = false;

    var files = $('#file').prop('files')[0];
    var formData = new FormData();
    formData.append('file', files);
    var csvData = [];
    var reader = new FileReader();
    buscaIdPeriodo($('#periodo_generacion').val());
    var nombreArchivo = files.name; // Obtener el nombre del archivo
    var ajaxRequests = []; // Array para almacenar las promesas de las peticiones AJAX
    var num_imssArray = []; // Arreglo para almacenar los valores num_imss

    reader.onload = function(event) {
        var csvContent = event.target.result;
        var lines = csvContent.split('\n'); // Dividir el contenido en líneas
        var rows = lines.map(line => line.split(',')); // Dividir cada línea en filas
        csvData = rows;
        var headerRow = csvData[0];
        var noSSIndex = headerRow.indexOf("NO_SS");
        for (var i = 1; i < csvData.length - 1; i++) {
            (function(index) {
                var num_imss = csvData[index][noSSIndex];
                num_imssArray.push(num_imss); // Agregar el valor num_imss al arreglo

                var ajaxPromise = $.ajax({
                    type: "GET",
                    url: "trabajador/buscarTrabajadorFonacot/" + num_imss,
                    dataType: 'json',
                    success: function(data) {
                        if (data.data === null) {
                            var id_expedientes = document.getElementById("numeros_desconocidos");
                            var valorActual = id_expedientes.value; // Obtener el valor actual
                            var nuevoValor = num_imss;
                            if (valorActual) {
                                id_expedientes.value = valorActual + "," + nuevoValor; // Agregar el nuevo valor con coma y espacio
                            } else {
                                id_expedientes.value = nuevoValor; // Si no hay valor actual, simplemente establece el nuevo valor
                            }
                        }
                    },
                    error: function(e) {
                        toastr["warning"]("Error al cargar el File");
                    }
                });

                ajaxRequests.push(ajaxPromise); // Almacenar la promesa en el array
            })(i);
        }

        $.when.apply($, ajaxRequests).done(function() {
            
            ultimaValidacion(nombreArchivo, num_imssArray); // Llamar a ultimaValidacion() una vez que todas las peticiones AJAX se completen
        });
        
    };

    reader.readAsText(files);
}
//Busca elementos duplicados en el csv
function encontrarDuplicados(arreglo) {
  const elementoRepetido = {};
  const duplicados = [];

  for (const elemento of arreglo) {
    if (elementoRepetido[elemento]) {
      if (!duplicados.includes(elemento)) {
        duplicados.push(elemento);
      }
    } else {
      elementoRepetido[elemento] = true;
    }
  }

  return duplicados.length > 0 ? duplicados : null; // Devolver arreglo de duplicados o null si no se encontraron
}

//Valida si existen números desconocidos o duplicados en el input, si es el caso, muestra modal para escribirlos en TXT, sino llena la tabla
function ultimaValidacion(nombreArchivo,num_imssArray) {
    var ex = $('#numeros_desconocidos').val();
    var arreglo_numeros_desconocidos = ex.split(",");
    if (arreglo_numeros_desconocidos[0]==="") {
        const duplicado = encontrarDuplicados(num_imssArray);
        if (duplicado !== null) {
            $("#alertaDuplicadosModal").modal("show");
            escribeTxtDuplicados(nombreArchivo,duplicado);
          } else {
            muestraDatos();
          }
        
    } else {
        $("#alertaNumDescModal").modal("show");
        escribeTxtDesconocidos(nombreArchivo,arreglo_numeros_desconocidos);
        limpiarTabla();
        var tipo_nomina = document.getElementById("tipo_nomina");
        tipo_nomina.selectedIndex = 0;
        resetSelect();
    }  
}

//Recetea los select para evitar errores y bloquea boton file
function resetSelect(){
    var tipo_nomina = document.getElementById("tipo_nomina");
    if (tipo_nomina.selectedIndex === 0) {
        var periodo_generacion = document.getElementById("periodo_generacion");
        periodo_generacion.selectedIndex = -1; // Establece el valor por defecto
        var periodo_aplicacion = document.getElementById("periodo_aplicacion");
        periodo_aplicacion.selectedIndex = -1; // Establece el valor por defecto
        document.getElementById("file").disabled = true;
    }
}

//Escribe el TXT con fecha, nombre del archivo y números desconocidos, lo descarga automaticamente
function escribeTxtDesconocidos(nombreArchivo,arreglo_numeros_desconocidos){
    $("#alertaNumDescModal").submit(function (event) { 
            event.preventDefault();
            var fecha = new Date();
            var options = { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' };
            var linea1 = new Intl.DateTimeFormat('es-ES', options).format(fecha);
            var linea2 = "Números incorrectos encontrados en el archivo: " + nombreArchivo;
            var contenido = linea1 + "\n" + linea2 + "\n" + arreglo_numeros_desconocidos; 
            var nombreTxt = "errorNumeros.txt";
            var blob = new Blob([contenido], { type: "text/plain" });
            var url = window.URL.createObjectURL(blob);

            var a = document.createElement("a");
            a.href = url;
            a.download = nombreTxt;

            // Simular un clic en el enlace para iniciar la descarga
            a.click();

            // Liberar el objeto URL
            window.URL.revokeObjectURL(url);
            $("#alertaNumDescModal").modal("hide");
        });
    
}
//Escribe el TXT con fecha, nombre del archivo y números repetidos, lo descarga automaticamente
function escribeTxtDuplicados(nombreArchivo,duplicados){
    $("#alertaDuplicadosModal").submit(function (event) { 
            event.preventDefault();
            var fecha = new Date();
            var options = { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' };
            var linea1 = new Intl.DateTimeFormat('es-ES', options).format(fecha);
            var linea2 = "Números duplicados encontrados en el archivo: " + nombreArchivo;
            var contenido = linea1 + "\n" + linea2 + "\n" + duplicados; 
            var nombreTxt = "errorDuplicados.txt";
            var blob = new Blob([contenido], { type: "text/plain" });
            var url = window.URL.createObjectURL(blob);

            var a = document.createElement("a");
            a.href = url;
            a.download = nombreTxt;

            // Simular un clic en el enlace para iniciar la descarga
            a.click();

            // Liberar el objeto URL
            window.URL.revokeObjectURL(url);
            $("#alertaDuplicadosModal").modal("hide");
        });
    
}

//Muestra los datos en la tabla blancos los pertenecientes a la nomina seleccionada y rojos los diferentes
function muestraDatos(){
    document.getElementById("file").disabled = false;
    var files = $('#file').prop('files')[0];
    var formData = new FormData();
    formData.append('file', files);
    var csvData = [];
    var reader = new FileReader();
    buscaIdPeriodo($('#periodo_generacion').val());
    reader.onload = function(event) {
        var csvContent = event.target.result;
        var lines = csvContent.split('\n'); // Dividir el contenido en líneas
        var rows = lines.map(line => line.split(',')); // Dividir cada línea en filas
        csvData = rows;
        // Ahora puedes acceder a csvData en cualquier parte de tu código
        var headerRow = csvData[0];
        var noSSIndex = headerRow.indexOf("NO_SS");
        var montoIndex = headerRow.indexOf("RETENCION_REAL");
        for (var i = 1; i < csvData.length - 1; i++) {
            (function(index) {
                var num_imss = csvData[index][noSSIndex];
                var monto = csvData[index][montoIndex];
                $.ajax({
                    type: "GET",
                    url: "trabajador/buscarTrabajadorFonacot/" + num_imss,
                    dataType: 'json',
                    success: function (data) {
                        document.getElementById("guarda").disabled = false;
                        var table = $('#tabla_Descuentos').DataTable();
                        var nombre_completo = data.data.nombreDTO + " " + data.data.apellido_paternoDTO + " " + data.data.apellido_maternoDTO;
                        var nomina_id = data.data.id_tipo_nominaDTO;
                        var periodo_generacion =  $('#id_periodos').val();
                        var periodo_aplicacion =  $('#id_periodos').val();

                        var row = table.row.add([
                            nomina_id + "-" + data.data.expedienteDTO,
                            nombre_completo,
                            monto,
                            periodo_generacion,
                            periodo_aplicacion,
                            '',
                            0
                        ]).draw().node();

                        if ($('#tipo_nomina').val() === nomina_id.toString()) {
                            // Dejar el fondo de la celda blanco
                            row.cells[0].style.backgroundColor = 'white';
                            row.cells[5].innerHTML = 1; // Modifica el valor en la sexta columna


                        } else {
                            // Cambiar el fondo de la celda a rojo
                            row.cells[0].style.backgroundColor = 'aquamarine';
                            row.cells[5].innerHTML = 0; // Modifica el valor en la sexta columna
                        }
                        if (row.cells[5].innerHTML === "1") {
                            llenaIdTrabajador(data.data.expedienteDTO);
                        }
                    },
                    error: function (e) {
                        alert(e);
                    }
                });
            })(i);
        }
    };
    document.getElementById("periodo_aplicacion").disabled = true;
    document.getElementById("periodo_generacion").disabled = true;
    reader.readAsText(files);
}

//Guarda los movimientos para los descuentos en "Cuentas por cobrar"
function guardaMovimientos(){
    var tabla = document.getElementById("tabla_Descuentos");
    var datosFiltrados = [];

    for (var i = 0; i < tabla.rows.length; i++) {
        var fila = tabla.rows[i];
        var celdas = fila.cells;

        var expediente = celdas[0].textContent;
        var trabajador = celdas[1].textContent;
        var cantidad = celdas[2].textContent;
        var generacion = celdas[3].textContent;
        var aplicacion = celdas[4].textContent;
        var procesado = celdas[5].textContent;
        var numPeriodos = celdas[6].textContent;
    
        if (procesado === "1") {
            datosFiltrados.push([expediente, trabajador, cantidad, generacion, aplicacion, procesado, numPeriodos]);
        }
    }
    var longitud_datos = datosFiltrados.length;
    var ajaxPromises = [];
    for (var i = 0; i < longitud_datos; i++) {
        var t = $('#id_trabajador').val();
        var arreglo_trabajador = t.split(",");
        var s_a_c = $('#saldo_actual_consulta').val();
        var arreglo_saldo_actual = s_a_c.split(",");
        var valor_inicial_actual = datosFiltrados[i][2];
        var valor_inicial_nuevo = valor_inicial_actual + arreglo_saldo_actual[i];
        var nomina_expediente = datosFiltrados[i][0];
        var partes = nomina_expediente.split("-");
        var nomina = partes[0]; 
        var expediente = partes[1];
        var periodo_pago_actual = datosFiltrados[i][4];
        var periodos = 0;
        periodos = (nomina < 3) ? 4 : 2;
        var monto = valor_inicial_actual/periodos;
        var remanente = valor_inicial_actual -(monto*periodos);
        if (remanente < 0) {
            remanente=0;
        }else{
            remanente =(valor_inicial_actual -(monto*periodos)).toFixed(2);
        }
        
        var datos = {"trabajador":{"id_trabajador": arreglo_trabajador[i]}, "cat_Tipo_Nomina": {"id_tipo_nomina":nomina}, "saldo_inicial":parseFloat(valor_inicial_nuevo), "saldo_actual":parseFloat(valor_inicial_nuevo), "monto_pago":monto,
                "numero_periodos_pago":periodos, "periodo_pago_actual":periodo_pago_actual,"remanente_ultimo_pago":remanente, "remanente_ultimo_pago_pr":remanente,
                "periodo_inicial":periodo_pago_actual, "monto_criterio_pago":periodos,"saldo_actual_pr":valor_inicial_actual,"solicitado_fonacot":valor_inicial_actual};
        
        var ajaxPromise = new Promise(function(resolve, reject) {
            $.ajax({
                type: "POST",
                url: "creditoFonacot/guardarDescuentoFonacot",
                data: JSON.stringify(datos),
                contentType: "application/json",
                success: function (data) {
                    document.getElementById("elimina").disabled = false;
                    document.getElementById("guarda").disabled = true;
                    resolve();
                },
                error: function (e) {
                    reject(e);
                }
            });
        });
        ajaxPromises.push(ajaxPromise); 
        
    }
    
    Promise.all(ajaxPromises)
        .then(function() {
            $("#modalMensaje").modal("show");
            document.getElementById("alerta").textContent = "Se Guardaron " + longitud_datos + " Trabajadores de la Nómina " + $('#nombre_nomina').val();
            toastr['success']("Datos Guardados Correctamente");
            ultimaActualizacion();
            $('#id_trabajador').val('');
            $('#saldo_actual_consulta').val('');
        })
        .catch(function(error) {
            toastr["warning"]("Error al guardar Descuento " + error, " error", {progressBar: true, closeButton: true});
        });
}


//Limpia la tabla y borrra el archivo cargado en el input
function limpiarTabla(){
    //Limpia la Tabla
    var tabla = $('#tabla_Descuentos').DataTable();
    // Borrar todas las filas y re-renderizar la tabla
    tabla.clear().draw();
    
    //Reemplaza el input tipo File
    var fileInput = document.getElementById("file");
    var newFileInput = document.createElement("input");
    newFileInput.type = "file";
    newFileInput.className = "btn btn-primary";
    newFileInput.id = "file";
    newFileInput.style.width = "200px";
    newFileInput.addEventListener("change", revisaExpedientes);

    // Reemplazar el campo de archivo existente con el nuevo campo
    fileInput.parentNode.replaceChild(newFileInput, fileInput);
    
    //Habilitar los selects
    document.getElementById("periodo_aplicacion").disabled = false;
    document.getElementById("periodo_generacion").disabled = false;
    
    //Inhabilitar botón guardar
    document.getElementById("guarda").disabled = true;
}


//Llena en un input los id de trabajadores para ingresarlos en la tabla
function llenaIdTrabajador(expediente){
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador_NumExpediente/" + expediente,
        dataType: 'json',
        success: function (data) {
            var id_trabajadores = document.getElementById("id_trabajador");
            var valorActual = id_trabajadores.value; // Obtener el valor actual
            var nuevoValor = data.data.id_trabajador;
            buscaSaldoActual(nuevoValor);
            if (valorActual) { 
                id_trabajadores.value = valorActual + "," + nuevoValor; // Agregar el nuevo valor con coma y espacio
            } else {
                id_trabajadores.value = nuevoValor; // Si no hay valor actual, simplemente establece el nuevo valor
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar El Trabajador: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//Llena en un input los saldos actuales en caso que existan de los trabajadores procesados
function buscaSaldoActual(trabajador_id){
    $.ajax({
        type: "GET",
        url: 'creditoFonacot/listarCuentaPorTrabajador/'+trabajador_id,
        dataType: 'json',
        success: function (data) {
            if (data===undefined) {
                var saldos_actuales = document.getElementById("saldo_actual_consulta");
                var saldoActual = saldos_actuales.value; // Obtener el valor actual
                var nuevoSaldo = "0";
                if (saldoActual) { 
                    saldos_actuales.value = saldoActual + "," + nuevoSaldo; // Agregar el nuevo valor con coma y espacio
                } else {
                    saldos_actuales.value = nuevoSaldo; // Si no hay valor actual, simplemente establece el nuevo valor
                }
            }else{
                var saldos_actuales = document.getElementById("saldo_actual_consulta");
                var saldoActual = saldos_actuales.value; // Obtener el valor actual
                var nuevoSaldo = data[0].saldo_actual;
                if (saldoActual) { 
                    saldos_actuales.value = saldoActual + "," + nuevoSaldo; // Agregar el nuevo valor con coma y espacio
                } else {
                    saldos_actuales.value = nuevoSaldo; // Si no hay valor actual, simplemente establece el nuevo valor
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar La Cuenta: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//Detecta las nominas cargadas para tener la opcion de eliminar movimientos en caso de error
function detectaMovimientos(){
    event.preventDefault();
    $.ajax({
        type: "GET",
        url: "creditoFonacot/nominasCargadas",
        dataType: 'json',
        success: function (data) {
            llenaModal(data);
            
        },
        error: function (e) {
            alert(e);
        }
    });
}
//Llena el select en el modal para poder eliminar los movimientos
function llenaModal(arreglo_nomina){
    $('#nominas_cargadas').empty().append("<option value=''>* Tipo de Nomina </option>");
    for (var i = 0; i < arreglo_nomina.length; i++) {
        (function (i) {
            $.ajax({
                type: "GET",
                url: "catalogos/buscarNomina/" + arreglo_nomina[i],
                dataType: 'json',
                success: function (data) {
                    var vselected = "";
                    if (arreglo_nomina.length > 0) {
                        $('#nominas_cargadas').append('<option value="' + data.data.id_tipo_nomina + '"' + vselected + '>' + data.data.desc_nomina + '</option>');
                    }
                    // Si esta es la última iteración, realizar alguna acción después de cargar todas las opciones
                    if (i === arreglo_nomina.length - 1) {
                        // Aquí puedes hacer algo después de cargar todas las opciones
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });
        })(i);
    }
}
//Elimina los movimientos por nomina en caso de error
function eliminaMovimientos() {
    $("#eliminaMovimientosModal").submit(function (event) { 
        event.preventDefault();
        var nomina_id = $("#nominas_cargadas").val(); 
        $.ajax({
            type: "POST",
            url: "creditoFonacot/eliminarDescuentoFonacot/" + nomina_id,
            dataType: 'json',
            contentType: "application/json",
            success: function (data) {
                toastr["success"]("La nomina " + nomina_id + " fue borrada de la base");
                $("#eliminaMovimientosModal").modal('hide');
                limpiarTabla();
                ultimaActualizacion();
                document.getElementById("elimina").disabled = true;
            },
            error: function (e) {
                toastr["warning"]("Error al eliminar los datos: " + e.responseText, "Error", {progressBar: true, closeButton: true});
            }
        });
    });
}

function fonacotAdmin() {
    window.location.href = 'fonacotAdmin';
}