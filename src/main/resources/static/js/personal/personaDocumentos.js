/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    const valores = window.location.search;
    const urlParams = new URLSearchParams(valores);
    var id_persona = urlParams.get('id_persona');
    tablaTiposDocumentos(id_persona);
    tablaDocumentosCargados(id_persona);
    tabs(id_persona);
});

function tabs(id_persona) {
    $('#myTab').html("<ul class='nav nav-tabs' role='tablist'>" +
            "<li class='btn btn-primary' onclick=personaDatosGenerales('" + id_persona + "')>1.-DATOS GENERALES</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaContacto('" + id_persona + "')>2.-DATOS DE CONTACTO</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaDireccion('" + id_persona + "')>3.-DIRECCION</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaLicencia('" + id_persona + "')>4.-LICENCIA</li>&nbsp;" +
            "<li><a href='#tabs-5' class='btn btn-info'>5.-DOCUMENTOS</a></li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaMedico('" + id_persona + "')>6.-DATOS MEDICOS</li>&nbsp;" +
            "<li class='btn btn-primary' onclick=personaCarta('" + id_persona + "')>7.- CARTA DESIGNATARIA</li>&nbsp;");
}

function personaDatosGenerales(id_persona) {
    window.location.href = 'personaDatosGenerales?id_persona=' + id_persona;
}
function personaContacto(id_persona) {
    window.location.href = 'personaContacto?id_persona=' + id_persona;
}
function personaDireccion(id_persona) {
    window.location.href = 'personaDireccion?id_persona=' + id_persona;
}
function personaLicencia(id_persona) {
    window.location.href = 'personaLicencia?id_persona=' + id_persona;
}

function personaMedico(id_persona) {
    window.location.href = 'personaMedico?id_persona=' + id_persona;
}
function personaCarta(id_persona) {
    window.location.href = 'personaCarta?id_persona=' + id_persona;
}


/*=============================================
 Ejectuar Tabla Para subir documentos
 =============================================*/

function tablaTiposDocumentos(id_persona) {
    $.ajax({
        type: "GET",
        url: "documentacion/listarTiposDocumento",
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#tablaDocumentos tbody').empty();
                $.each(data.data, (i, note) => {
                    let noteRow = '<tr><td style="text-align:center;">' + note.documento + '</td><td><form id="frmFormulario' + note.id_tipo_documento + id_persona + '" enctype="multipart/form-data"><input  onchange="cargarDocumento(' + note.id_tipo_documento + "," + id_persona + ');" type="file" id="file' + note.id_tipo_documento + id_persona + '" name="file' + note.id_tipo_documento + id_persona + '"/><input type="hidden" id="id_tipo_documento" value="' + note.id_tipo_documento + '" name="id_tipo_documento"/><input type="hidden" id="persona_id" value="' + id_persona + '" name="persona_id"/></form></td><td><div id="progressBar' + note.id_tipo_documento + '" class="progress-bar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%">0%</div></td></tr>';
                    $('#tablaDocumentos tbody').append(noteRow);
                });
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar informacion de documentos : " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 Cargar Documentacion  
 =============================================*/

function cargarDocumento(id_tipo_documento, id_persona) {
    var tamanoMax = 10096;//Tamaño en kilobytes
    var tamanoMin = 10;//Tamaño en kilobytes
    var files = $('#file' + id_tipo_documento + id_persona + '').prop('files')[0];
    var formData = new FormData();
    formData.append('file', files);

    const fsize = files.size;
    const file = Math.round((fsize / 1024));
    

    if (!stringEndsWithValidExtension($('#file' + id_tipo_documento + id_persona + '').val(), [".pdf", ".jpeg", ".jpg", ".bmp"], false)) {
        toastr['error']("Formato de archivo NO permitido", "Aviso");
        files.value = '';
        return false;
    }
    if (file >= tamanoMax || file < tamanoMin) {
        toastr['error']("Archivo no permitido NO debe estar Vacio y con tamaño Maximo de 10Mb", "Aviso");
        files.value = '';
        return false;
    } else {
        $.ajax({
            url: 'documentacion/guardarDocumentacion/' + id_tipo_documento + '/' + id_persona,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            dataType: "html",
            xhr: function () {
                
                var xhr = $.ajaxSettings.xhr();
                xhr.upload.onprogress = function (event) {
                    var perc = Math.round((event.loaded / event.total) * 100);
                    $("#nombreArchivo").text(files.name);
                    $("#progressBar" + id_tipo_documento).text(perc + '%');
                    $("#progressBar" + id_tipo_documento).css('width', perc + '%');
                };
                return xhr;
            },
            beforeSend: function (xhr) {
                $("#progressBar" + id_tipo_documento).text('0%');
                $("#progressBar" + id_tipo_documento).css('width', '0%');
            },
            success: function (data, textStatus, jqXHR)
            {
                $("#progressBar" + id_tipo_documento).addClass("progress-bar-success");
                $("#progressBar" + id_tipo_documento).text('100% - Carga realizada');
                tablaDocumentosCargados(id_persona);
            },
            error: function (jqXHR, textStatus) {
                $("#progressBar" + id_tipo_documento).text('100% - Error al cargar el archivo');
                $("#progressBar" + id_tipo_documento).removeClass("progress-bar-success");
                $("#progressBar" + id_tipo_documento).addClass("progress-bar-danger");
            }
        });
    }
}

/*=============================================
 Validar extencion del archivo a cargar
 =============================================*/

function stringEndsWithValidExtension(stringToCheck, acceptableExtensionsArray, required) {
    if (required === false && stringToCheck.length === 0) {
        return true;
    }
    for (var i = 0; i < acceptableExtensionsArray.length; i++) {
        if (stringToCheck.toLowerCase().endsWith(acceptableExtensionsArray[i].toLowerCase())) {
            return true;
        }
    }
    return false;
}


/*=============================================
 Documentos cargados de la persona
 =============================================*/

function tablaDocumentosCargados(id_persona) {
    $.ajax({
        type: "GET",
        url: "documentacion/listarDocumentoCargados/" + id_persona,
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $('#tablaDocumentosCargados tbody').empty();
                if (data.data.length === 0) {
                    $('#tablaDocumentosCargados tbody').append("<tr><td colspan='4'><strong><font color='red'>Sin documentación</font></strong></td></tr>");
                } else {
                    $.each(data.data, (i, note) => {
                        let noteRow = "<tr><td>" + note.nombre + "</td><td><button type='button' class='btn btn-primary' onclick='return verDocumentos(" + note.id_documentacion + ")'><span class='fa fa-search'></span> Ver</button></td><td><button type='button' class='btn btn-primary' onclick='descargarDocumentos(" + note.id_documentacion + ")'><span class='fa fa-download'></span> Descargar</button></td><td><button type='button' class='btn btn-danger' onclick='eliminarDocumentos(" + note.id_documentacion + ")'><span class='fa fa-minus-circle'></span> Eliminar</button></td></tr>'";
                        $('#tablaDocumentosCargados tbody').append(noteRow);
                        
                    });
                }
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar informacion de documentos cargados: " + e, " error", {progressBar: true, closeButton: true});
        }
    });
}

/*=============================================
 Descargar Documentos
 =============================================*/
function descargarDocumentos(id_documentacion) {
  $.ajax({
    type: "GET",
    url: "documentacion/buscarDocumentoCargado/" + id_documentacion,
    xhrFields: {
      responseType: 'blob'
    },
    success: function(data, status, xhr) {
      var filename = "";
      var disposition = xhr.getResponseHeader('Content-Disposition');
      if (disposition && disposition.indexOf('attachment') !== -1) {
        var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
        var matches = filenameRegex.exec(disposition);
        if (matches !== null && matches[1]) {
          filename = matches[1].replace(/['"]/g, '');
        }
      }
      var blob = new Blob([data], { type: 'application/pdf' });
      var link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.download = filename || 'documento.pdf';
      link.click();
    },
    error: function(e) {
      console.log("Error al descargar el documento: " + e);
    }
  });
}

/*=============================================
 Ver Documentos en modal
 =============================================*/
function verDocumentos(id_documentacion){
    
    $.ajax({
        type: "GET",
        url: "documentacion/buscarDocumentoCargado/" + id_documentacion,
        xhrFields: {
            responseType: 'blob'
        },
        success: function (data, status, xhr) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                $("#DocumentosModal").modal("toggle");
                var contentType = xhr.getResponseHeader("Content-Type");
                var filename = xhr.getResponseHeader("Content-disposition");
                filename = filename.substring(filename.lastIndexOf("=") + 1) || "download";
                var url = window.URL.createObjectURL(data);
                var frame = $('#documents_frame');
                var url = url;
                frame.attr('src', url).show();
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar informacion de documentos cargados: General 1", {progressBar: true, closeButton: true});
        }
    });
    // alert(id_documentacion);
}

/*=============================================
 Eliminar Documentos
 =============================================*/

function eliminarDocumentos(id_documentacion)
{
    $.ajax({
        type: "POST",
        url: "documentacion/eliminarDocumento/" + id_documentacion,
        success: function (data) {
            if ($.isEmptyObject(data)) {
                toastr["warning"]("No se encontro información...", "Aviso", {progressBar: true, closeButton: true});
            } else {
                toastr['success'](data.data, "Aviso");
                const valores = window.location.search;
                const urlParams = new URLSearchParams(valores);
                var id_persona = urlParams.get('id_persona');
                tablaDocumentosCargados(id_persona);
            }
        },
        error: function (e) {
            toastr["warning"]("Error al recuperar informacion de documentos cargados: General 1", {progressBar: true, closeButton: true});
        }
    });
}

function verCandidatos() {
    window.location.href = 'candidatos';
}

function verTrabajadores() {
    window.location.href = 'trabajadoresAdmin';
}