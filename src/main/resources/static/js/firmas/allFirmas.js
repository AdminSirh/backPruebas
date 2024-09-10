/* global $canvas, contexto, COLOR_PINCEL, GROSOR */

document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');
    buscaFirma(id_trabajador);
    buscaTrabajador(id_trabajador);
    buscaPlazaTrabajador(id_trabajador);
    mostrarFirma(id_trabajador);
    dibujaFirma();
    llenaFecha();
});
//Fill the input "fecha_expedicion" with today's date
function llenaFecha(){
    var fecha_expedicion = document.getElementById('fecha_expedicion');
    var fechaActual = new Date();
    var fecha_formato = fechaActual.toISOString().split('T')[0];
    fecha_expedicion.value = fecha_formato;
}
//Search to record in the data base by id_trabajador to enable or disabled button "botonModificado"
function buscaFirma(id_trabajador){
    
    $.ajax({
        type: "GET",
        url: "firma/buscarFirma/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            if (data.data === null) {
                $('#botonModificado').html("<div class='col d-flex align-items-center'>"
                                    +" <button class='btn btn-primary' id='btnGuardar' type='button' onclick='guardaFirma()' style='width: 200px'>Guardar</button>"
                                    +"</div>");
            }else{
                $('#botonModificado').html("<div class='col d-flex align-items-center'>"
                                    +" <button class='btn btn-primary' id='btnReemplazar' type='button' onclick='reemplazaFirma()' style='width: 200px'>Reemplazar</button>"
                                    +"</div>");
                        }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar la firma", {progressBar: true, closeButton: true});
        }
    });
}

//Look for Trabajador to fill out the labels so we can see your information
function buscaTrabajador(id_trabajador){
    $.ajax({
        type: "GET",
        url: "trabajador/buscarTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            var nombre = document.getElementById("nombre");
            nombre.textContent = data.data.persona.apellido_paterno + " " +data.data.persona.apellido_materno + " " +data.data.persona.nombre;
            var rfc = document.getElementById("rfc");
            rfc.textContent = data.data.persona.rfc;
            var imss = document.getElementById("imss");
            imss.textContent = data.data.persona.num_imss;
            $('#expediente').val(data.data.cat_expediente.numero_expediente);
            var antiguedad_formato = new Date(data.data.fecha_antiguedad);
            var dia = antiguedad_formato.getDate();
            var mes = antiguedad_formato.getMonth() +1;
            var anio = antiguedad_formato.getFullYear();
            
            if (dia < 10) {
                dia = "0"+dia;
            }else if(mes<10){
                mes = "0"+mes;
            }
            var antiguedad = document.getElementById("antiguedad");
            antiguedad.textContent = dia + "/" + mes + "/" + anio;
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar el Trabajador: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//Find the "Plaza_Trabajador" to can fill more information in the labels and call inside the method "buscaPlazas"
function buscaPlazaTrabajador(id_trabajador){
    $.ajax({
        type: "GET",
        url: "trabajador/buscarPlazaTrabajador/" + id_trabajador,
        dataType: 'json',
        success: function (data) {
            var nomina = document.getElementById("nomina");
            nomina.textContent = data.data.cat_Tipo_Nomina.desc_nomina;
            buscaPlazas(data.data.plaza_id);
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar la Plaza de Trabajador: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
//Provides additional information
function buscaPlazas(id_plaza){
    $.ajax({
        type: "GET",
        url: "catalogos/buscarPlaza/" + id_plaza,
        dataType: 'json',
        success: function (data) {
            var puesto = document.getElementById("puesto");
            puesto.textContent = data.data.cat_puesto.codigo_puesto +" - "+ data.data.cat_puesto.puesto;
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar la Plaza: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}
 
//Draw the signature, create events to detect mouse movement, adjust thickness, the bottom of the canvas and the color of pencil
function dibujaFirma(){
    const $canvas = document.querySelector("#canvas");
    const contexto = $canvas.getContext("2d");
    $canvas.style.border = "2px solid black";
    const color_pincel = "black";
    const grosor = 2;
    let xAnterior = 0, yAnterior = 0, xActual = 0, yActual = 0;
    const obtenerXReal = (clientX) => clientX - $canvas.getBoundingClientRect().left;
    const obtenerYReal = (clientY) => clientY - $canvas.getBoundingClientRect().top;
    let haComenzadoDibujo = false;
    window.haDibujado = false;
    
    $canvas.addEventListener("mousedown", evento => {
        // En este evento solo se ha iniciado el clic, así que dibujamos un punto
        xAnterior = xActual;
        yAnterior = yActual;
        xActual = obtenerXReal(evento.clientX);
        yActual = obtenerYReal(evento.clientY);
        contexto.beginPath();
        contexto.fillStyle = color_pincel;
        contexto.fillRect(xActual, yActual, grosor, grosor);
        contexto.closePath();
        // Y establecemos la bandera
        haComenzadoDibujo = true;
        window.haDibujado = true;
    });

    $canvas.addEventListener("mousemove", (evento) => {
        if (!haComenzadoDibujo) {
            return;
        }
        // El mouse se está moviendo y el usuario está presionando el botón, así que dibujamos todo

        xAnterior = xActual;
        yAnterior = yActual;
        xActual = obtenerXReal(evento.clientX);
        yActual = obtenerYReal(evento.clientY);
        contexto.beginPath();
        contexto.moveTo(xAnterior, yAnterior);
        contexto.lineTo(xActual, yActual);
        contexto.strokeStyle = color_pincel;
        contexto.lineWidth = grosor;
        contexto.stroke();
        contexto.closePath();
    });
    ["mouseup", "mouseout"].forEach(nombreDeEvento => {
        $canvas.addEventListener(nombreDeEvento, () => {
            haComenzadoDibujo = false;
        });
    });
}
//Clean the canvas
function limpiaFirma(){
    const $canvas = document.querySelector("#canvas");
    const contexto = $canvas.getContext("2d");
    const color_fondo = "white";
    // Colocar color blanco en fondo de canvas
    contexto.fillStyle = color_fondo;
    contexto.fillRect(0, 0, $canvas.width, $canvas.height);
    window.haDibujado = false;
}

//Save the signature in the table "trabajador_firma"
function guardaFirma() {
    if (!window.haDibujado) {
        toastr['error']("No se puede guardar una firma vacia");
        return;
    }
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');
    var canvas = document.getElementById("canvas");
    // Convertir el lienzo en una imagen de datos URL (PNG en este caso)
    var firmaDataURL = canvas.toDataURL('image/png');
    var fecha_expedicion = new Date();
    var fecha_formateada = `${fecha_expedicion.getDate()}-${fecha_expedicion.getMonth() + 1}-${fecha_expedicion.getFullYear()}_${fecha_expedicion.getHours()}-${fecha_expedicion.getMinutes()}-${fecha_expedicion.getSeconds()}`;
    
    // Convertir la URL de datos en un Blob
    var firmaBlob = dataURLtoBlob(firmaDataURL);
    
    // Adjuntar el Blob como archivo en FormData
    var formData = new FormData();
    formData.append('firma', firmaBlob,id_trabajador + "_" + fecha_formateada + '_firma.png'); // 'firma.png' es el nombre del archivo
    
    $.ajax({
        url: 'firma/guardarFirma/' + id_trabajador,
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        cache: false,
        success: function (data, textStatus, jqXHR) {
            toastr['success'](data.data, "Firma Guardada Correctamente");
            $("#ingresaFirmaModal").modal('hide');
            console.log("Ellou");
             mostrarFirma(id_trabajador);
             buscaFirma(id_trabajador);
        },
        error: function (jqXHR, textStatus) {
            toastr['error']("Ocurrió un error al guardar", "Aviso");
        }
    });
}

function reemplazaFirma(){
    if (!window.haDibujado) {
        toastr['error']("No se puede guardar una firma vacia");
        return;
    }
    const valores = window.location.search;
    const d = decodeURIComponent(valores);
    const urlParams = new URLSearchParams(d);
    var id_trabajador = urlParams.get('id_trabajador');
    var datos = {};
    $.ajax({
        type: "POST",
        url: "firma/cambioEstatusFirma/"+id_trabajador,
        data: JSON.stringify(datos),
        contentType: "application/json",
        success: function (data) {
            guardaFirma();
        },
        error: function (e) {
            toastr["warning"]("Error al guardar los datos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
    
}
//Change a dataURL object to a blob object
function dataURLtoBlob(dataURL) {
    var arr = dataURL.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], { type: mime });
}
/*=============================================
 Ver Firma en recuadro
 =============================================*/
function mostrarFirma(trabajador_id) {
    $.ajax({
        type: "GET",
        url: "firma/buscarFirmaCargada/" + trabajador_id,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, status, xhr) {
            var contentType = xhr.getResponseHeader("Content-Type");
            if (contentType.startsWith('image/')) {
                
                $('#imagen').html("<div id='imageContainer'>"+
                                        "<img id='imageElement' alt='Imagen' style='max-width: 50%; max-height: 50%;'>"
                                  +"</div");
                          
                var imageUrl = URL.createObjectURL(data);
                var imageElement = document.getElementById('imageElement');
                imageElement.style.border = "1px solid black";
                imageElement.src = imageUrl;
                $("#imageContainer").show(); // Mostrar el contenedor de la imagen
            }
        },
        error: function (xhr, status, error) {
            ocultarImagen();
            toastr["warning"]("No hay firma almacenada");
        }
    });
}

function ocultarImagen(){
    $('#imageContainer').html('');
}


$('#ingresaFirmaModal').on('hidden.bs.modal', function (e) {
    // Verificar si se está cerrando el modal
    if (e.target === this) {
        limpiaFirma();
    }
});


function verTrabajadores(){
    window.location.href = 'firmasAdmin';
    
}